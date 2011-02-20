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
import java.util.HashSet;
import java.util.List;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author jcanderson
 * A "Plate" in assemblyManager is any object that fits on a robot deck and contains
 * nxm positions for samples
 */
public class Plate extends ObjBase {

    public Plate( PlateDatum d ) {
        super( d );
        _plateDatum = d;
    }

    /**
     * Constructor of a Plate from scratch
     * @param plateName
     * @param numrows
     * @param numcols
     * @param isFixedContainers
     */
    public Plate( String plateName, PlateType type, Person author ) {
        super( );
        _plateDatum = new PlateDatum();
        _datum = _plateDatum;
        _datum.name = plateName;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _plateDatum._authorUUID = author.getUUID();
        _plateDatum._plateTypeUUID = type.getUUID();
        _plateDatum.myWells = new String[ type.getNumRows() ][ type.getNumCols() ];

        //If it's a fixed-Container Plate, add containers
        if ( type.isContainerFixed() ) {
            int numrows = type.getNumRows();
            int numcols = type.getNumCols();
            for ( short row = 0; row < numrows; row++ ) {
                for ( short col = 0; col < numcols; col++ ) {
                    Container acon = new Container();
                    acon.putContainerToPlate( row, col, this );
                }
            }
        }
    }

    @Override
    public ObjType getType() {
        return ObjType.PLATE;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        String barcode = (String) objHash.get( "_barcode" );
        String location = (String) objHash.get( "_location" );
        String idPerson = (String) objHash.get( "_authorUUID" );
        String idPlateType = (String) objHash.get( "_plateTypeUUID" );

        String srows = (String) objHash.get( "numRows" );
        int rows = Integer.parseInt( srows );
        String scols = (String) objHash.get( "numCols" );
        int cols = Integer.parseInt( scols );

        //Parse all the containers
        String[][] conts = new String[ rows ][ cols ];
        @SuppressWarnings (value="unchecked")
        ArrayList<String> containerlist = (ArrayList<String>) objHash.get( "containers" );
        if ( containerlist != null ) {
            for ( String aline : containerlist ) {
                String[] parsed = aline.split( "\\," );
                int i = Integer.parseInt( parsed[1] );
                int j = Integer.parseInt( parsed[2] );
                conts[i][j] = parsed[0];
            }
        }

        PlateDatum d = new PlateDatum();
        d.uuid = uuid;
        d.name = name;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._barcode = barcode;
        d._location = location;
        d._authorUUID = idPerson;
        d._plateTypeUUID = idPlateType;
        d.myWells = conts;

        return new Plate( d );
    }

    @Override
    protected HashMap<String, HashMap<String, Object>> generateXml( HashMap<String, HashMap<String, Object>> allObjects ) {
        //If the hash already has the object, skip adding anything
        if ( allObjects.containsKey( getUUID() ) ) {
            return allObjects;
        }

        //Fill in the individual fields
        HashMap<String, Object> datahash = new HashMap<String, Object>();
        datahash.put( "objType", getType().toString() );
        datahash.put( "uuid", _plateDatum.uuid );
        datahash.put( "name", _plateDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_barcode", _plateDatum._barcode );
        datahash.put( "_location", _plateDatum._location );
        datahash.put( "_authorUUID", _plateDatum._authorUUID );
        datahash.put( "_plateTypeUUID", _plateDatum._plateTypeUUID );

        datahash.put( "numRows", Integer.toString( getNumRows() ) );
        datahash.put( "numCols", Integer.toString( getNumCols() ) );

        ArrayList<String> containerlist = new ArrayList<String>();
        for ( int i = 0; i < this.getNumRows(); i++ ) {
            for ( int j = 0; j < this.getNumCols(); j++ ) {
                String conuuid = _plateDatum.myWells[i][j];
                Container acon = Collector.getContainer( conuuid );
                if ( acon != null ) {
                    String aline = acon.getUUID() + "," + i + "," + j;
                    containerlist.add( aline );
                }
            }
        }
        datahash.put( "containers", containerlist );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getPlateType().generateXml( allObjects );
        allObjects = getAuthor().generateXml( allObjects );
        for ( int i = 0; i < this.getNumRows(); i++ ) {
            for ( int j = 0; j < this.getNumCols(); j++ ) {
                String conuuid = _plateDatum.myWells[i][j];
                Container acon = Collector.getContainer( conuuid );
                if ( acon != null ) {
                    allObjects = acon.generateXml( allObjects );
                }
            }
        }

        //Return the datahash
        return allObjects;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case PART:
                return true;
            default:
                return false;
        }
    }

    boolean EVT_ContainerToPlate( int row, int col, Container thecontainer ) {
        System.out.println( "I called EVT_ContainerToPlate" );
        boolean outer = false;
        if ( _plateDatum.myWells[row][col] == null ) {
            _plateDatum.myWells[row][col] = thecontainer.getUUID();
            outer = true;
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        } else {
            outer = false;
        }
        return outer;
    }

    /* PUTTERS
     * */
    public String generateBarcode() {
        String outString = "";
        for ( int i = 0; i < 8; i++ ) {
            double rand = Math.floor( 94.0 * Math.random() ) + 33;
            int randInt = (int) rand;
            char achar = (char) randInt;
            if ( achar == ',' ) {
                achar = 'c';
            }
            outString += achar;
        }
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        return outString;
    }

    public void setBarcode( String code ) {
        _plateDatum._barcode = code;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeAuthor( Person newauthor ) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _plateDatum._authorUUID, newauthor.getUUID() );
        _plateDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    public void changePlateType( PlateType pt ) {
        _plateDatum._plateTypeUUID = pt.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }


    /* GETTERS
     * */
    /**
     * Returns the Container present in a well of the Plate as UUID reference
     * @param row
     * @param col
     * @return
     */
    public String getContainerRefAt( int row, int col ) {
        String out = "";
        out += _plateDatum.myWells[row][col];
        return out;
    }

    /**
     * returns the Container present in a well of the Plate as a Container object
     * @param i
     * @param j
     * @return
     */
    public Container getContainerAt( int i, int j ) {
        System.out.println( "Plate retrieving row " + i + " col " + j );
        String containerref = getContainerRefAt( i, j );
        System.out.println( "containerref is: " + containerref );
        if ( containerref.equals( "" ) || containerref.equals( "null" ) ) {
            return null;
        }
        return Collector.getContainer( containerref );
    }

    public PlateType getPlateType() {
        return (PlateType) Collector.get( ObjType.PLATE_TYPE, _plateDatum._plateTypeUUID );
    }

    public int getNumRows() {
        return getPlateType().getNumRows();
    }

    public int getNumCols() {
        return getPlateType().getNumCols();
    }

    public String getBarcode() {
        return _plateDatum._barcode;
    }

    public String getLocation() {
        return _plateDatum._location;
    }

    public Person getAuthor() {
        return Collector.getPerson( _plateDatum._authorUUID );
    }

    public static Plate retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.PLATE );
        cq.eq( Plate.Fields.NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Plate p = (Plate) l.get( 0 );
        return p;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting plate save of " + getUUID() );
        if ( !isChanged() ) {
            System.out.println( "Collection didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( this._plateDatum._authorUUID ) ) {
            if ( !getAuthor().saveDefault() ) {
                return false;
            }
        }

        if ( !super.save( conn ) ) {
            return false;
        }

        for ( int row = 0; row < getNumRows(); row++ ) {
            for ( int col = 0; col < getNumCols(); col++ ) {
                String containerref = this.getContainerRefAt( row, col );
                if ( !Collector.isLocal( containerref ) ) {
                    continue;
                }
                Container acon = Collector.getContainer( containerref );
                System.out.println( "plate about to save " + acon.getName() );

                System.out.println( acon.getUUID() );
                System.out.println( acon.getName() );
                System.out.println( acon.getDateCreated() );
                System.out.println( acon.getLastModified() );
                System.out.println( acon.getRowAsInt() );
                System.out.println( acon.getCol() );
                System.out.println( acon.getBarcode() );
                System.out.println( acon.isFixed() );
                System.out.println( acon.getPlateLink() );

                if ( !acon.save( conn ) ) {
                    return false;
                }
            }
        }
        return true;
    }


    /**-----------------
    variables
    -----------------*/
    private PlateDatum _plateDatum;

    public static class PlateDatum extends ObjBaseDatum {

        public String myWells[][];             //References to Container objects as UUIDs
        public String _barcode = "none entered";
        public String _plateTypeUUID;
        public String _authorUUID;
        public String _location;

        @Override
        public ObjType getType() {
            return ObjType.PLATE;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        BARCODE,
        LOCATION,
        AUTHOR,
        PLATE_TYPE,
        CONTAINERS
    }
}
