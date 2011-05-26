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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class addComposite implements interpreter {

    public addComposite() {
        String[] titles = { "(Vector Name)", "Part Name", "Lefty Part",  "Righty Part", "(Format)", "(Author)", "(Description)" };
        _numCols = titles.length;
        _numRows = hub.numrows;
        _data = new String[_numRows][_numCols];
        for(int i=0; i<_numRows; i++) {
            for(int j=0; j< _numCols; j++) {
                _data[i][j] = "";
            }
        }
        _mySheet = new spreadsheet(_data, titles, this);
        _mySheet.setTitle("Add Composite Parts");
       _mySheet.setTitleArea("Add composite parts by Copy and Paste<br>You must supply Part Name,  Lefty and Righty parts.<br>Other fields are optional.  If a vector is added, a plasmid will also be generated.");

        if(Collector.isConnected()) {
            loadingthread = new Thread() {
                @Override
                public void run() {
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

                    //Put in the formatInputString chooser (field 3)
                    _formats = Collector.getAllLinksOf(ObjType.FORMAT);
                    final Object[] formatChoices =  _formats.toArray();
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                _mySheet.putComboField3("Formats", formatChoices, hub.getPreference("addPart_setFormat"));
                            }
                        });
                    } catch (InterruptedException ex) {
                        Logger.getLogger(addPart.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(addPart.class.getName()).log(Level.SEVERE, null, ex);
                    }

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


        //If it's still loading wait for it to finish loading
        try {
            loadingthread.join();
        } catch (InterruptedException ex) {
        }

        //Get the formatInputString and authorInputString defaults
        ObjLink link = (ObjLink) _mySheet.getComboField2();
        _defaultAuthor = Collector.getPerson(link.uuid);

        link = (ObjLink) _mySheet.getComboField3();
        _defaultFormat = Collector.getFormat(link.uuid);
        
        _data = (String[][]) data;

        for(int i=0; i<_data.length; i++) {
            try {
                //Retrieve the data in the current line
                String vectorname  = _data[i][0];
                String partname  = _data[i][1];
                String lefty  = _data[i][2];
                String righty  = _data[i][3];

                //If any of the 3 required fields weren't entered, abort this line
                if(righty.equals("") || lefty.equals("") || partname.equals("")) {
                    continue;
                } else {
                    System.out.println("Info is sufficient to add composite part");
                }

                //Retrieve any additional lines entered
                String formatInputString = _data[i][4];
                String authorInputString = _data[i][5];
                String description = _data[i][6];


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

                //If an formatInputString was typed in, search for that Format
                Format theformat;
                if(formatInputString.equals("")) {
                    theformat = _defaultFormat;
                } else {
                    theformat = Format.retrieveByName(formatInputString);
                    if(theformat==null) {
                        System.out.println("format is null");
                        continue;
                    }
                }

                //Get the parts
                Part leftypart = Part.retrieveByName(lefty);
                if(leftypart==null) {
                    System.out.println("leftypart is null");
                    continue;
                }
                Part rightypart = Part.retrieveByName(righty);
                if(rightypart==null) {
                    System.out.println("rightypart is null");
                    continue;
                }

                if(partname.length()<1) {
                    System.out.println("no partname provided");
                    continue;
                }

                if(description.equals("")) {
                    description = leftypart.getShortDescription() + "." +rightypart.getShortDescription();
                }

                //Get the Vector, lefty, and righty parts
                Vector thevector = Vector.retrieveByName(vectorname);

                ArrayList<Part> composition = new ArrayList<Part>();
                composition.add(leftypart);
                composition.add(rightypart);

                //Create the composite Part
                Part newcomposite = Part.generateComposite(composition, null, theformat, theauthor, partname, description);

                if(newcomposite==null) {
                    continue;
                }

                _outCollection.addObject(newcomposite);

                //Notify user that the Part was created
                _mySheet.appendLogMessage(newcomposite.getName() + "\n");
                System.out.println("I created composite part " + newcomposite.getName());

                //Create the Plasmid if a Vector was entered
                if(thevector!=null) {
                    Plasmid newplas = Plasmid.generatePlasmid( newcomposite,  thevector,  theauthor, theformat);
                    if(newplas!=null) {
                        _outCollection.addObject(newplas);
                        //Notify user that the Part was created
                        _mySheet.appendLogMessage(newplas.getName() + "\n");
                        System.out.println("I created plasmid " + newplas.getName());
                    }
                }

                //Clear the data in the data table
                for(int col=0; col<_numCols; col++) {
                    _data[i][col] = "";
                }
            } catch(java.lang.ClassCastException e) {
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
    private Format _defaultFormat;
    private int _numCols;
    private int _numRows;

    private String messageText="";
    private Thread loadingthread;
}
