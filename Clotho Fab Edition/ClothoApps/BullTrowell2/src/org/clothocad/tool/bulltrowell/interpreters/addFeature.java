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

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.clothocad.tool.bulltrowell.view.hub;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocad.tool.bulltrowell.view.spreadsheet;

/**
 *
 * @authorInputString J. Christopher Anderson
 */
public class addFeature implements interpreter {

    public addFeature() {
        String[] titles = { "Nickname", "Sequence", "Author", "Family", "Risk Group", "Forward Color", "Reverse Color" };
        _numCols = titles.length;
        _numRows = hub.numrows;
        _data = new String[_numRows][_numCols];
        for(int i=0; i<_numRows; i++) {
            for(int j=0; j< _numCols; j++) {
                _data[i][j] = "";
            }
        }
        _mySheet = new spreadsheet(_data, titles, this);
        _mySheet.setTitle("Add Features");
        _mySheet.setTitleArea("Add features from Excel by Copy and Paste<br>You must supply name and sequence.  Other fields are optional.<br>  For coding sequences, enter sequence as ATG...TAA, Feature Type is CDS (or ORF).");

        if(Collector.isConnected()) {
            loadingthread = new Thread() {
                @Override
                public void run() {
                    //Get the current user
                    final Person user = Collector.getCurrentUser();

                    //Put in the authorInputString chooser (field 2)
                    _persons = Collector.getAllLinksOf(ObjType.PERSON);
                    final Object[] authorChoices =  _persons.toArray();
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {

                            @Override
                            public void run() {
                                _mySheet.putComboField2("Author", authorChoices, user.getObjLink());
                            }
                        });
                    } catch (InterruptedException ex) {
                        Logger.getLogger(addFeature.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(addFeature.class.getName()).log(Level.SEVERE, null, ex);
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

        _data = (String[][]) data;

        for(int i=0; i<_data.length; i++) {
            try {
                //Retrieve the data in the current line
                String name = _data[i][0];
                String seq = _data[i][1];

                //If any of the 2 required fields weren't entered, abort this line
                if(name.equals("") || seq.equals("")) {
                    continue;
                }

                //Retrieve any additional lines entered
                String authorInputString = _data[i][2];
                String familyString = _data[i][3];
                String riskgroup = _data[i][4];
                String forcolor = _data[i][5];
                String revcolor = _data[i][6];

                //If an authorInputString was typed in, search for that Person
                Person featureAuthor;
                if(authorInputString.equals("")) {
                    featureAuthor = _defaultAuthor;
                } else {
                    featureAuthor = Person.retrieveByName(authorInputString);
                    if(featureAuthor==null) {
                        continue;
                    }
                }

                boolean isCDS = false;
                if(familyString.toUpperCase().equals("CDS") || familyString.toUpperCase().equals("ORF") || familyString.toUpperCase().equals("PROTEIN")) {
                    isCDS = true;
                }
                Feature newfeature = Feature.generateFeature(name, seq, featureAuthor, isCDS);
                newfeature.changeAuthor(featureAuthor);

                Color forwardColor = null;
                Color reverseColor = null;
                if(newfeature!=null) {
                    //Try to parse the colors
                    try {
                        forwardColor = new Color(Integer.parseInt(forcolor));
                        System.out.println("Setting forward color from parseInt");
                    } catch(NumberFormatException e) {
                    }

                    if(forwardColor==null) {
                        if(forcolor.startsWith("#") && forcolor.length() > 5) {
                            String temp = forcolor.substring(1);
                            forcolor = temp;
                        }
                        try {
                            forwardColor = new Color(Integer.parseInt( forcolor, 16 ));
                            System.out.println("Setting forward color from hexadecimal");
                        } catch(NumberFormatException e) {
                            System.out.println("rejecting forward color");
                        }
                    }

                    if(forwardColor==null) {
                        forwardColor = HTMLColors.decodeColor(forcolor);
                        if(forwardColor!=null) {
                            System.out.println("Setting forward color from html parser");
                        }
                    }

                    try {
                        reverseColor = new Color(Integer.parseInt(revcolor));
                    } catch(NumberFormatException e) {
                    }

                    if(reverseColor==null) {
                        if(revcolor.startsWith("#") && revcolor.length() > 5) {
                            String temp = revcolor.substring(1);
                            revcolor = temp;
                        }
                        try {
                            reverseColor = new Color(Integer.parseInt( revcolor, 16 ));
                        } catch(NumberFormatException e) {
                            System.out.println("rejecting forward color");
                        }

                    }

                    if(reverseColor==null) {
                        reverseColor = HTMLColors.decodeColor(revcolor);
                    }

                    //If Color got parsed, set the colors
                    if(forwardColor!=null) {
                        newfeature.changeForwardColor(forwardColor);
                    }
                    if(reverseColor!=null) {
                        newfeature.changeReverseColor(reverseColor);
                    }

                    //Set the risk group
                    try {
                        newfeature.changeRiskGroup((short) Integer.parseInt(riskgroup));
                    } catch(NumberFormatException e) {
                    }
                }
                //Notify user that the Feature was created
                _mySheet.appendLogMessage(newfeature.getName() + "\n");
                System.out.println("I created feature " + newfeature.getName());
                _outCollection.addObject(newfeature);

                //Clear the data in the data table
                for(int col=0; col<_numCols; col++) {
                    _data[i][col] = "";
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
