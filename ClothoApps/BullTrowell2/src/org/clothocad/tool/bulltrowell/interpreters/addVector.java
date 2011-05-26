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
import org.clothocad.tool.bulltrowell.view.hub;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Vector;
import org.clothocad.tool.bulltrowell.view.spreadsheet;

/**
 *
 * @authorInputString J. Christopher Anderson
 */
public class addVector implements interpreter {

    public addVector() {
        String[] titles = { "Nickname", "Short Description", "Sequence", "Format", "Author" };
        _numCols = titles.length;
        _numRows = hub.numrows;
        _data = new String[_numRows][_numCols];
        for(int i=0; i<_numRows; i++) {
            for(int j=0; j< _numCols; j++) {
                _data[i][j] = "";
            }
        }
        _mySheet = new spreadsheet(_data, titles, this);
        _mySheet.setTitle("Add Vectors");
        _mySheet.setTitleArea("Add vectors from Excel by Copy and Paste<br>You must supply Nickname, Short Description, and Sequence.<br>Other fields will be set to defaults below.");

        if(Collector.isConnected()) {
            loadingthread = new Thread() {
                @Override
                public void run() {
                    /*
                    //Put in the Collection chooser (field 1)
                    _collections = Collector.getAllLinksOf(ObjType.COLLECTION);
                    final Object[] collChoices =  _collections.toArray();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            _mySheet.putComboField1("Collection", collChoices, collChoices[0].toString());
                        }
                    });
                     */

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
                                _mySheet.putComboField3("Formats", formatChoices, formatChoices[0].toString());
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

        //Get the formatInputString and authorInputString defaults
        ObjLink link = (ObjLink) _mySheet.getComboField2();
        _defaultAuthor = Collector.getPerson(link.uuid);

        link = (ObjLink) _mySheet.getComboField3();
        _defaultFormat = Collector.getFormat(link.uuid);

        _data = (String[][]) data;

        for(int i=0; i<_data.length; i++) {
            try {
                //Retrieve the data in the current line
                String name = _data[i][0];
                String desc = _data[i][1];
                String seq = _data[i][2];

                //If any of the 3 required fields weren't entered, abort this line
                if(desc.equals("") || seq.equals("") || name.equals("")) {
                    continue;
                }

               // "Nickname", "Short Description", "Sequence", "Format", "Author", "Long Description"
                //Retrieve any additional lines entered
                String formatInputString = _data[i][3];
                String authorInputString = _data[i][4];

                //If an authorInputString was typed in, search for that Person
                Person vectorAuthor;
                if(authorInputString.equals("")) {
                    vectorAuthor = _defaultAuthor;
                } else {
                    vectorAuthor = Person.retrieveByName(authorInputString);
                    if(vectorAuthor==null) {
                        continue;
                    }
                }

                //If an formatInputString was typed in, search for that Person
                Format vectorFormat;
                if(formatInputString.equals("")) {
                    vectorFormat = _defaultFormat;
                } else {
                    vectorFormat = Format.retrieveByName(formatInputString);
                    if(vectorFormat==null) {
                        continue;
                    }
                }

                Vector newvector = Vector.generateVector( name, desc,  seq,  vectorFormat,  vectorAuthor);
                if(newvector.saveDefault()) {
                    //Notify user that the Vector was created
                    _mySheet.appendLogMessage(newvector.getName() + "\n");
                    System.out.println("I created vector " + newvector.getName());
                    _outCollection.addObject(newvector);

                    //Clear the data in the data table
                    for(int col=0; col<_numCols; col++) {
                        _data[i][col] = "";
                    }
                }
            } catch(Exception e) {
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
