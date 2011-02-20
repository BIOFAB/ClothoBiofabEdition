/*
 Copyright (c) 2009 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS..
 */

package org.clothocad.tool.bulltrowell.interpreters;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocad.tool.bulltrowell.view.hub;
import org.clothocad.tool.bulltrowell.view.spreadsheet;

/**
 *
 * @authorInputString J. Christopher Anderson
 */
public class addPlasmid implements interpreter {

    public addPlasmid() {
        String[] titles = { "Vector Name", "Part Name",  "Plasmid Name", "Format", "Author" };
        _numCols = titles.length;
        _numRows = hub.numrows;
        _data = new String[_numRows][_numCols];
        for(int i=0; i<_numRows; i++) {
            for(int j=0; j< _numCols; j++) {
                _data[i][j] = "";
            }
        }
        _mySheet = new spreadsheet(_data, titles, this);
        _mySheet.setTitle("Add Plasmid");
       _mySheet.setTitleArea(""
               + "Add plasmids from Excel by Copy and Paste You must supply a<br>"
               + "part name and a vector name. Author will be the default<br>"
               + "below.  Unless overriden the Part's Format is used.");

        if(Collector.isConnected()) {
            loadingthread = new Thread() {
                @Override
                public void run() {
                    //Get the current user
                    final Person user = Collector.getCurrentUser();

                    //Put in the authorInputString chooser (field 2)
                    _persons = Collector.getAllLinksOf(ObjType.PERSON);
                    final Object[] authorChoices =  _persons.toArray();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            _mySheet.putComboField2("Author", authorChoices, user.getName());
                        }
                    });
                }
            };
            loadingthread.start();
        }
    }

    @Override
    public void receiveData(Object[][] data) {
        _data = (String[][]) data;
        for(int i=0; i<_data.length; i++) {
            for(int j=0; j<_data[0].length; j++) {
                System.out.print(_data[i][j] + "");
            }
            System.out.println("");
        }

        //Create a new Collection to store everything
        if(_outCollection==null) {
            _outCollection = new Collection();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                   _outCollection.launchDefaultViewer();
                }
            });
        }

        //If it's still loading wait for it to finish loading
        try {
            loadingthread.join();
        } catch (InterruptedException ex) {
        }

        //Get the authorInputString defaults
        ObjLink link = (ObjLink) _mySheet.getComboField2();
        _defaultAuthor = Collector.getPerson(link.uuid);

        _data = (String[][]) data;

        for(int i=0; i<_data.length; i++) {
            try {
                //Retrieve the data in the current line
                String vectorname  = _data[i][0];
                String partname  = _data[i][1];

                //If any of the 3 required fields weren't entered, abort this line
                if(vectorname.equals("") || partname.equals("")) {
                    System.out.println("Insufficient info provided");
                    continue;
                } else {
                    System.out.println("Info is sufficient to add plasmid");
                }

                //Retrieve any additional lines entered
                String plasmidName = _data[i][2];
                String formatInputString = _data[i][3];
                String authorInputString = _data[i][4];


                //If an authorInputString was typed in, search for that Person
                Person theauthor;
                if(authorInputString.equals("")) {
                    theauthor = _defaultAuthor;
                } else {
                    theauthor = Person.retrieveByName(authorInputString);
                    if(theauthor==null) {
                        System.out.println("person is null");
                        continue;
                    }
                }

                //Get the Part
                Part thepart = Part.retrieveByName(partname);
                if(thepart==null) {
                    System.out.println("part is null");
                    continue;
                }

                //Get the Vector
                Vector thevector = Vector.retrieveByName(vectorname);
                if(thevector==null) {
                    System.out.println("vector is null");
                    continue;
                }

                //If an formatInputString was typed in, search for that Format
                Format theformat;
                if(formatInputString.equals("") || formatInputString==null) {
                    theformat = thepart.getFormat();
                } else {
                    theformat = Format.retrieveByName(formatInputString);
                    if(theformat==null) {
                        System.out.println("format is null");
                        continue;
                    }
                }

                //Create the Plasmid name
                if(plasmidName.length()<2) {
                    plasmidName = thevector.getName() + "-" + thepart.getName();
                }

                System.out.println("partFormat: " + theformat.getName());

                Plasmid newplas = Plasmid.generatePlasmid( thepart,  thevector,  theauthor, theformat);

                if(newplas == null) {
                    System.out.println("newPlasmid was null");
                    continue;
                }

                newplas.changeName(plasmidName);
                _outCollection.addObject(newplas);

                //Notify user that the Part was created
                _mySheet.appendLogMessage(newplas.getName() + "\n");
                System.out.println("I created plasmid " + newplas.getName());

                //Clear the data in the data table
                for(int col=0; col<_numCols; col++) {
                    _data[i][col] = "";
                }
            } catch(Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        _mySheet.refreshData(_data);

    }

/*-----------------
     variables
 -----------------*/
    private String[][] _data;
    private spreadsheet _mySheet;
    private ArrayList<ObjLink> _persons;
    private ArrayList<ObjLink> _collections;
    private ArrayList<ObjLink> _formats;
    private Person _defaultAuthor;
    private Collection _outCollection;
    private int _numCols;
    private int _numRows;

    private String messageText="";
    private Thread loadingthread;
}
