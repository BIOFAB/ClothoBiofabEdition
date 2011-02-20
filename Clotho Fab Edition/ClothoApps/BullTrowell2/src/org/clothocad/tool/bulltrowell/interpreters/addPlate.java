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
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Container;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.PlasmidSample;
import org.clothocore.api.data.Plate;
import org.clothocore.api.data.PlateType;
import org.clothocore.api.data.Strain;
import org.clothocore.api.data.Vector;
import org.clothocad.tool.bulltrowell.view.spreadsheet;

/**
 * @authorInputString J. Christopher Anderson
 */
public class addPlate implements interpreter {

    public addPlate(int numrows, int numcols) {
        _numCols = numcols;
        _numRows = numrows;
        String[] titles = new String[_numCols];
        for(int i=1; i<_numCols+1; i++) {
            titles[i-1] = "" + i;
        }
        _data = new String[_numRows][_numCols];
        for(int i=0; i<_numRows; i++) {
            for(int j=0; j< _numCols; j++) {
                _data[i][j] = "";
            }
        }
        _mySheet = new spreadsheet(_data, titles, this);
        _mySheet.setTitleArea("Add plasmid samples from Excel by Copy and Paste<br>You must supply Nickname, Short Description, and Sequence.<br>Other fields will be set to defaults below.");

        if(Collector.isConnected()) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                     //Put in the Collection chooser (field 1)
                    _collections = Collector.getAllLinksOf(ObjType.COLLECTION);
                    Object[] collChoices =  _collections.toArray();
                    _mySheet.putComboField1("Collection", collChoices, collChoices[0].toString());

                    //Get the current user
                    Person user = Collector.getCurrentUser();

                    //Put in the authorInputString chooser (field 2)
                    _persons = Collector.getAllLinksOf(ObjType.PERSON);
                    Object[] authorChoices =  _persons.toArray();
                    _mySheet.putComboField2("Author", authorChoices, user.getName());

                    //Put in the authorInputString chooser (field 2)
                    _strains = Collector.getAllLinksOf(ObjType.STRAIN);
                    Object[] strainChoices =  _strains.toArray();
                    _mySheet.putComboField3("Strain", strainChoices, strainChoices[0].toString());
                }
            });
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

        //Get the Collection and author defaults
        ObjLink link = (ObjLink) _mySheet.getComboField1();
        _defaultCollection = Collector.getCollection(link.uuid);

        link = (ObjLink) _mySheet.getComboField2();
        _defaultAuthor = Collector.getPerson(link.uuid);

        link = (ObjLink) _mySheet.getComboField3();
        _defaultStrain = Collector.getStrain(link.uuid);

        _data = (String[][]) data;
        boolean successfuladd = false;
        String platename = JOptionPane.showInputDialog("What do you want to name the new plate?");
        if(platename==null || platename.equals("")) {
            return;
        }

        //Throw up a dialog and get user to select the Collection stored as 'platetype'
        ArrayList<ObjLink> allPlateType = Collector.getAllLinksOf(ObjType.PLATE_TYPE);
        Object[] allNames = allPlateType.toArray();
        link = (ObjLink) JOptionPane.showInputDialog(null, "Choose one", "Plate Type",
            JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
        PlateType platetype = null;
        if(link!=null) {
            platetype = Collector.getPlateType(link.uuid);
        }
        if(platetype==null) {
            return;
        }
        Plate aplate = new Plate(platename, platetype, _defaultAuthor);

        for(int i=0; i<_data.length; i++) {
            for(int j=0; j< _data[i].length; j++) {
                String datafield = _data[i][j];

                //If data is blank move on
                if(datafield.equals("")) {
                    continue;
                }

                //If there was a Strain with 'from' parse it out
                String strainValue=null;
                if(datafield.indexOf(" from ")>0) {
                    String[] splitDatafield = datafield.trim().split(" from ");
                    datafield = splitDatafield[0];
                    strainValue = splitDatafield[1];
                    System.out.println("Split datafield into plasmid " + datafield);
                    System.out.println("Split datafield into strain " + strainValue);
                }

                Strain myStrain = _defaultStrain;
                if(strainValue!=null) {
                    System.out.println("strainValue" + strainValue + "strainValue" );
                    myStrain = Strain.retrieveByName(strainValue);
                    if(myStrain==null) {
                        continue;
                    }
                }

                System.out.println("will do plasmid query of" + datafield);
                Plasmid currplas = Plasmid.retrieveByName(datafield);

                //If the Plasmid is null try generating a Plasmid
                if(currplas==null) {
                    String[] anarray = datafield.trim().split("-");
                    Vector avect = Vector.retrieveByName(anarray[0]);
                    if(avect==null) {
                        continue;
                    }
                    Part apart = Part.retrieveByName(anarray[1]);
                    if(apart==null) {
                        continue;
                    }
                    currplas = Plasmid.generatePlasmid(apart, avect, _defaultAuthor, apart.getFormat());
                }

                Container acon=null;
                if(platetype.isContainerFixed()) {
                    acon = aplate.getContainerAt(i, j);
                } else {
                    acon = new Container();
                }

                PlasmidSample asam = PlasmidSample.generatePlasmidSample(currplas, myStrain, acon, 50.0, _defaultAuthor);

                if(asam==null) {
                    return;
                }

                if(!platetype.isContainerFixed()) {
                    if(!acon.putContainerToPlate((short) i, (short) j, aplate)) {
                        return;
                    }
                } else {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!Putting container to plate failed!!!!!!");
                }


                successfuladd = true;
           //     currplas.saveDefault();
           //     acon.saveDefault();
           //     asam.saveDefault();
                _defaultCollection.addObject(currplas);
                _defaultCollection.addObject(asam);
                _data[i][j] = "";
                System.out.println("successful add of sample");
            }
        }

        if(successfuladd) {
            if(platetype.isContainerFixed()) {
                for(int i=0; i<_data.length; i++) {
                    for(int j=0; j< _data[i].length; j++) {
                        Container acon = aplate.getContainerAt(i, j);
                    //    acon.saveDefault();
                    }
                }
            }

        //    aplate.saveDefault();
            _defaultCollection.addObject(aplate);
        //    _defaultCollection.saveDefault();
        }

        aplate.launchDefaultViewer();
        _mySheet.refreshData(_data);

    }


    /* SETTERS
     * */

    /* PUTTERS
     * */

    /* GETTERS
     * */



/*-----------------
     variables
 -----------------*/
    private String[][] _data;
    private spreadsheet _mySheet;
    private ArrayList<ObjLink> _persons;
    private ArrayList<ObjLink> _strains;
    private ArrayList<ObjLink> _collections;
    private ArrayList<ObjLink> _formats;
    private Person _defaultAuthor;
    private Strain _defaultStrain;
    private Collection _defaultCollection;
    private int _numCols;
    private int _numRows;

    private String messageText="";

}
