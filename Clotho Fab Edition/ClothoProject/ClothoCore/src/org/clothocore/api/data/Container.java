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
package org.clothocore.api.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.clothocore.api.core.Collector;
import javax.swing.JOptionPane;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;

/**
 *
 * @author jcanderson
 */
public class Container extends ObjBase {

    public Container( ContainerDatum d ) {
        super( d );
        _conDatum = d;
    }

    /**
     * Public constructor for a container.  Containers can exist with
     * no links to other ObjBase's.
     */
    public Container() {
        super();
        _conDatum = new ContainerDatum();
        _datum = _conDatum;
        _datum.name = "Container";
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _conDatum.uuid = _uuid;
    }

    @Override
    public ObjType getType() {
        return ObjType.CONTAINER;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        String barcode = (String) objHash.get( "_containerBarcode" );
        String srow = (String) objHash.get( "_row" );
        short row = Short.parseShort( srow );
        String scol = (String) objHash.get( "_col" );
        short col = Short.parseShort( scol );
        String sfixed = (String) objHash.get( "_isFixed" );
        boolean fixed = Boolean.parseBoolean( sfixed );
        String idPlate = (String) objHash.get( "_plateUUID" );
        String idSample = (String) objHash.get( "_sampleUUID" );

        ContainerDatum d = new ContainerDatum();
        d.uuid = uuid;
        d.name = name;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._row = row;
        d._col = col;
        d._containerBarcode = barcode;
        d._isFixed = fixed;
        d._plateUUID = idPlate;
        d._sampleUUID = idSample;

        return new Container( d );
    }

    @Override
    protected HashMap<String, HashMap<String, Object>> generateXml(HashMap<String, HashMap<String, Object>> allObjects ) {
        //If the hash already has the object, skip adding anything
        if ( allObjects.containsKey( getUUID() ) ) {
            return allObjects;
        }

        //Fill in the individual fields
        HashMap<String, Object> datahash = new HashMap<String, Object>();
        datahash.put( "objType", getType().toString() );
        datahash.put( "uuid", _conDatum.uuid );
        datahash.put( "name", _conDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_containerBarcode", _conDatum._containerBarcode );
        datahash.put( "isEmpty", Boolean.toString( _conDatum.isEmpty ) );
        datahash.put( "_isFixed", Boolean.toString( _conDatum._isFixed ) );
        datahash.put( "_sampleUUID", _conDatum._sampleUUID );
        datahash.put( "_plateUUID", _conDatum._plateUUID );

        datahash.put( "_row", Integer.toString( _conDatum._row ) );
        datahash.put( "_col", Integer.toString( _conDatum._col ) );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        if ( _conDatum._plateUUID != null ) {
            if ( !allObjects.containsKey( _conDatum._plateUUID ) ) {
                allObjects = getPlate().generateXml( allObjects );
            }
        }

        if ( _conDatum._sampleUUID != null ) {
            if ( !allObjects.containsKey( _conDatum._sampleUUID ) ) {
                allObjects = getSample().generateXml( allObjects );
            }
        }

        //Return the datahash
        return allObjects;
    }

    /**
     * Add a Sample or create a new sample of a plasmid and put it into this container
     * @param dropObject whatever object you wish to add
     * @return true if the add occurred
     */
    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case SAMPLE:
                String svol = JOptionPane.showInputDialog( null, "What volume do you wish to transfer?", JOptionPane.OK_CANCEL_OPTION );
                Double dvol;
                try {
                    dvol = Double.parseDouble( svol );
                } catch ( NumberFormatException e ) {
                    return false;
                }
                Sample s = (Sample) dropObject;
                s.transferAnAliquot( this, dvol );

                _conDatum.isEmpty = false;
                return true;
            case PLASMID:
                svol = JOptionPane.showInputDialog( null, "I assume you want to put a sample in this container.  What volume is your sample?", JOptionPane.OK_CANCEL_OPTION );

                try {
                    dvol = Double.parseDouble( svol );
                } catch ( NumberFormatException e ) {
                    return false;
                }

                //Throw up a dialog and get user to select the collection stored as 'chosen'
                Strain chosenstrain = null;
                ArrayList<ObjLink> allstrain = Collector.getAllLinksOf( ObjType.STRAIN );
                Object[] allNames = allstrain.toArray();
                ObjLink selectedValue = (ObjLink) JOptionPane.showInputDialog( null, "What strain is your plasmid sample derived from?", "Choose strain",
                                                                               JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0] );
                if ( selectedValue != null ) {
                    chosenstrain = Collector.getStrain( selectedValue.uuid );
                }
                if ( chosenstrain == null ) {
                    return false;
                }

                Plasmid aplasmid = (Plasmid) dropObject;
                PlasmidSample asam = PlasmidSample.generatePlasmidSample( aplasmid, chosenstrain, this, dvol, Collector.getCurrentUser() );
                _conDatum.isEmpty = false;
                return true;
            default:
                return false;
        }
    }

    public boolean EVT_SampleToContainer( Sample thesample ) {
        boolean outer;
        if ( _conDatum._sampleUUID.equals( "" ) ||_conDatum._sampleUUID==null ) {
            _conDatum._sampleUUID = thesample.getUUID();
            outer = true;
            _conDatum.isEmpty = false;
        } else {
            outer = false;
        }
        System.out.println( "***********EVT_SampleToContainer put _sample as " + _conDatum._sampleUUID );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.SAMPLE_TO_CONTAINER);
        return outer;
    }

    public boolean putContainerToPlate( short row, short col, Plate theplate ) {
        if ( theplate.EVT_ContainerToPlate( row, col, this ) ) {
            _conDatum._row = row;
            _conDatum._col = col;
            _conDatum._plateUUID = theplate.getUUID();
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.CONTAINER_TO_PLATE);
            return true;
        } else {
            JOptionPane.showMessageDialog( null, "You are trying to put a container into an occupied position of a plate, how should I proceed?", "Error", JOptionPane.ERROR_MESSAGE );
            //RESPOND TO THE JOPTIONPANE WITH AN APPROPRIATE RESOLUTION
            return false;
        }
    }

    /* SETTERS
     * */

    /**
     * The barcode for this plate if available
     * @param bar
     */
    public void changeBarcode( String bar ) {
        _conDatum._containerBarcode = bar;
        setChanged(RefreshEvent.Condition.BARCODE_CHANGED);
    }

    /* GETTERS
     * */
    /**
     * Get the UUID link of the sample in this container
     * @return a UUID as a String
     */
    public String getSampleLink() {
        return _conDatum._sampleUUID;
    }

    /**
     * Get the plate that this container is in
     * @return a Plate
     */
    public Plate getPlate() {
        if ( _conDatum._plateUUID.equals( "" ) ) {
            return null;
        }
        return Collector.getPlate( _conDatum._plateUUID );
    }

    /**
     * Get's the row as an integer, so well A1 would return 0
     * @return integer 0 to 7 for a 96-well plate
     */
    public int getRowAsInt() {
        return _conDatum._row;
    }

    /**
     * Get's the row as a String, so well A1 would return A
     * @return String A to H for a 96-well plate
     */
    public String getRowAsString() {
        char row = (char) (getRowAsInt() + 65);
        return "" + row;
    }

    /**
     * Get's the column as an integer, so well A4 returns 3
     * @return int from 0 to 11 for a 96-well plate
     */
    public int getCol() {
        return _conDatum._col;
    }

    /**
     * Get position of a container in a plate
     * @return a String such as "C2"
     */
    public String getWell() {
        String out = "";
        char row = (char) (getRowAsInt() + 65);
        Integer col = getCol() + 1;
        out += row + col.toString();
        return out;
    }

    /**
     * Get the container's barcode
     * @return a String representation of the barcode
     */
    public String getBarcode() {
        if(_conDatum._containerBarcode!=null) {
            return _conDatum._containerBarcode;
        }
        return "";
    }

    /**
     * Get the Sample in the Container (if there is one) otherwise
     * returns null
     * @return a Sample in this container
     */
    public Sample getSample() {
        if ( _conDatum.isEmpty ) {
            return null;
        }
        return Collector.getSample( _conDatum._sampleUUID );
    }

    /**
     * Get the plate that contains this container as its UUID link
     * @return the UUID as a String
     */
    public String getPlateLink() {
        return _conDatum._plateUUID;
    }

    /**
     * Return true if the containers are fixed in this plate
     * @return true if fixed
     */
    public boolean isFixed() {
        return _conDatum._isFixed;
    }

    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting container save of " + getUUID() );
        if ( !isChanged() ) {
            System.out.println( "Container didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _conDatum._plateUUID ) ) {
            Plate aplate = Collector.getPlate( _conDatum._plateUUID );
            if ( !aplate.save( conn ) ) {
                return false;
            }
        }

        if ( !super.save( conn ) ) {
            return false;
        }

        return true;
    }

    /**-----------------
    variables
    -----------------*/
    public static class ContainerDatum extends ObjBaseDatum {

        public short _row = -1;           //Row in Plate in which Container sits
        public short _col = -1;           //Column in Plate in which Container sits
        public String _sampleUUID = "";     //UUID reference for my Sample
        public boolean _isFixed = false;
        public String _plateUUID;      //UUID reference for my Plate
        public String _containerBarcode;
        public boolean isEmpty = true;

        @Override
        public ObjType getType() {
            return ObjType.CONTAINER;
        }
    }
    private ContainerDatum _conDatum;

    public static enum containerType {

        EPPENDORF, WELL_96_BARCODED, WELL_384_BARCODED
    };
    private containerType _containerType;

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        ROW,
        COL,
        IS_FIXED,
        BARCODE,
        PLATE,
        SAMPLE
    }
}
