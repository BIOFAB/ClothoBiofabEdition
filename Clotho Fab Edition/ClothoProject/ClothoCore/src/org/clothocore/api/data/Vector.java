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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.clothocore.api.core.Collector;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;


/**
 *
 * @author jcanderson
 */
public class Vector extends ObjBase {

    /**
     * Constructor from database
     * @param uuid
     * @param hash
     * @param rg
     * @param dateCreated
     * @param lastModified
     * @param description
     * @param circular
     * @param genomic
     * @param idformat
     * @param idPerson
     * @param idNucseq
     */
    public Vector( VectorDatum d ) {
        super( d );
        _vecDatum = d;

        if ( _vecDatum._riskGroup == -1 ) {
            final Vector item = this;
            Thread bslThread = new Thread() {
                @Override
                public void run() {
                    short newrg = item.getSeq().performBiosafetyCheck();
                    if(item._vecDatum._riskGroup!=newrg) {
                        _vecDatum._riskGroup = newrg;
                        item.setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                    }
                }
            };
            bslThread.start();
            addSaveHold( bslThread );
        }
    }

    private Vector( String name, String desc, String seq, Format form, Person author ) {
        super();
        _vecDatum = new VectorDatum();
        _datum = _vecDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _vecDatum._authorUUID = author.getUUID();
        _vecDatum._formatUUID = form.getUUID();
        _vecDatum._description = desc;
        _vecDatum._seqID = new NucSeq( seq ).getUUID();
    }

   /**
    * Factory method for creating a new vector.
    * @param name
    * @param desc
    * @param seq
    * @param form
    * @param author
    * @return
    */
   public static Vector generateVector( String name, String desc, String seq, Format form, Person author ) {
        //Generate a hash of the sequence and Format
        NucSeq aseq = new NucSeq( seq );
        aseq.setTransient();
        String key = aseq.getSeq() + form.getUUID();
        String uuidKey = generateUUIDAsHash( key );

        //THIS NEEDS TO BE CHANGED--IT NEEDS TO QUERY THE HASH COLUMN OF THE TABLE, NOT PRIIMARY KEY
        //DO A QUERY FOR A PART WHOSE HASH IS THE KEY AND RETURN IT TO prexistingSeq
        Vector prexistingSeq = retrieveByHash( uuidKey );

        //If this sequence/Format is already in the database, return that
        if ( prexistingSeq != null ) {
            int n = JOptionPane.showConfirmDialog( null, "A vector with this sequence already exists.  You should try to use that vector.  Do you want to cancel this new vector?", "Vector"
                    + "already exists", JOptionPane.YES_NO_OPTION );
            if ( n == 0 ) {
                return prexistingSeq;
            }

            //Do a second chance to cancel
            int m = JOptionPane.showConfirmDialog( null, "Are you sure you really want two copies of this sequence?  It isn't recommended, can I please abort this?", "Vector "
                    + "already exists", JOptionPane.YES_NO_OPTION );
            if ( m == 0 ) {
                return prexistingSeq;
            }
        }

        //Check to see if a Part by this hash already exists in the database
        prexistingSeq = retrieveByName( name );
        while ( prexistingSeq != null ) {
            name = JOptionPane.showInputDialog( "A vector named " + name + " already exists, please give me a new name." );
            prexistingSeq = retrieveByName( name );
        }

        Vector v = null;
        try {
            v = new Vector( name, desc, seq, form, author );
        } catch ( java.lang.NullPointerException e ) {
            return null;
        }

        if ( v == null ) {
            return null;
        }

        v.setTransient();

        if ( !form.checkVector( v ) ) {
            return null;
        }

        //If it's gotten this far it's a good Vector, so finish it up and return it
        if ( v.getSeq().getSeq().indexOf( "." ) > -1 ) {
            v._vecDatum._isCircular = false;
        }
        v._vecDatum._hash = uuidKey;
        v._isTransient = false;
        v.getSeq().setLocked( true );

        //Do risk group setting on a new thread
        final Vector vectorout = v;
        Thread bslThread = new Thread() {

            @Override
            public void run() {
                vectorout.setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                Short rg = vectorout.getSeq().performBiosafetyCheck();
                vectorout._vecDatum._riskGroup = rg;
            }
        };
        bslThread.start();
        vectorout.addSaveHold( bslThread );

        return v;
    }

    @Override
    public ObjType getType() {
        return ObjType.VECTOR;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        String description = (String) objHash.get( "_description" );
        String hash = (String) objHash.get( "_hash" );
        String sriskGroup = (String) objHash.get( "_riskGroup" );
        short riskGroup = Short.parseShort( sriskGroup );

        String idformat = (String) objHash.get( "_formatUUID" );
        String idPerson = (String) objHash.get( "_authorUUID" );
        String idNucseq = (String) objHash.get( "_seqID" );
        String sccirular = (String) objHash.get( "_iscircular" );
        boolean circular = Boolean.parseBoolean( sccirular );
        String sgenomic = (String) objHash.get( "_isGenomic" );
        boolean genomic = Boolean.parseBoolean( sgenomic );

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        VectorDatum d = new VectorDatum();

        d.uuid = uuid;
        d.name = name;
        d._riskGroup = riskGroup;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._description = description;
        d._isCircular = circular;
        d._isGenomic = genomic;
        d._formatUUID = idformat;
        d._authorUUID = idPerson;
        d._seqID = idNucseq;
        d._hash = hash;


        return new Vector( d );
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
        datahash.put( "uuid", _vecDatum.uuid );
        datahash.put( "name", _vecDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );


        datahash.put( "_description", _vecDatum._description );
        datahash.put( "_riskGroup", Integer.toString( _vecDatum._riskGroup ) );
        datahash.put( "_isGenomic", Boolean.toString( _vecDatum._isGenomic ) );
        datahash.put( "_iscircular", Boolean.toString( _vecDatum._isCircular ) );
        datahash.put( "_formatUUID", _vecDatum._formatUUID );
        datahash.put( "_authorUUID", _vecDatum._authorUUID );
        datahash.put( "_seqID", _vecDatum._seqID );
        datahash.put( "_hash", _vecDatum._hash );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getFormat().generateXml( allObjects );
        allObjects = getSeq().generateXml( allObjects );

        //Return the datahash
        return allObjects;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting vector save" );
        if ( !isChanged() ) {
            System.out.println( "vector didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _vecDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _vecDatum._seqID ) ) {
            NucSeq seq = getSeq();
            if ( !seq.save( conn ) ) {
                return false;
            }
        }

        return super.save( conn );
    }

    public void printOutInformation() {
    }

    public String stringOutInformation() {
        return "";
    }

    public void uploadPartToClotho() {
    }

    /*
    public container clone_PartIntoContainer(Vector myvector, strain mycell, double myvolume, trajectory traj) {
    return null;
    }
     * */

    /*
    public void EVT_add(plasmid aplasmid) {
    _myPlasmids.add(aplasmid.getUUID().toString());
    }
     *
     */
    public boolean checkFormat() {
        //SEE IF THE NUCSEQ OBEYS THE RULES OF THE FORMAT, IF SO RETURN TRUE
        return true;
    }
    /*
    public ArrayList getPlasmids() {
    ArrayList<plasmid> out = new ArrayList<plasmid>();
    for(String str: _myPlasmids) {
    plasmid aplasmid = (plasmid) line.lineBase.get(str);
    out.add(aplasmid);
    }
    return out;
    }
     *
     *
     */
    /* SETTERS
     * */

    /* PUTTERS
     * */
    @Override
    public void changeName( final String newname ) {
        Vector existing = Vector.retrieveByName(newname);
        if(existing!=null) {
            if(!existing.getUUID().equals(this.getUUID())) {
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
                return;
            }
        }
        super.changeName( newname );
    }

    public void changeShortDescription( String text ) {
        addUndo( "_description", _vecDatum._description, text );
        _vecDatum._description = text;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeSequence( final String newseq ) {
        if ( newseq == null ) {
            return;
        }

        final String oldseq = Collector.getNucSeq( _vecDatum._seqID ).toString();

        Collector.getNucSeq( _vecDatum._seqID ).APIchangeSeq( newseq );

        boolean isok = getFormat().checkVector( this );
        if ( !isok ) {
            Collector.getNucSeq( _vecDatum._seqID ).APIchangeSeq( oldseq );
            return;
        }
        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _vecDatum._seqID ).APIchangeSeq( oldseq );
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _vecDatum._seqID ).APIchangeSeq( newseq );
            }
        };

        //Change the risk group
        _vecDatum._riskGroup = -1;
        final Vector item = this;
        Thread bslThread = new Thread() {

            @Override
            public void run() {
                // setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                setRiskGroup( item.getSeq().performBiosafetyCheck() );
            }
        };
        bslThread.start();
        addSaveHold( bslThread );

        addUndo( undo, redo );
    }

    public void changeFormat( Format f ) {
        if ( f == null ) {
            return;
        }

        boolean ok = f.checkVector( this );
        if ( !ok ) {
            return;
        }

        addUndo( "_formatUUID", _vecDatum._formatUUID, f.getUUID() );
        _vecDatum._formatUUID = f.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeAuthor( Person newauthor ) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _vecDatum._authorUUID, newauthor.getUUID() );
        _vecDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            default:
                return false;
        }
    }

    public void setGenomic( boolean b ) {
        addUndo( "_isGenomic", _vecDatum._isGenomic, b );
        _vecDatum._isGenomic = b;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }
    /* GETTERS
     * */

    public boolean isCircular() {
        return _vecDatum._isCircular;
    }

    public boolean isGenomic() {
        return _vecDatum._isGenomic;
    }

    public NucSeq getSeq() {
        return Collector.getNucSeq( _vecDatum._seqID );
    }

    public Short getRiskGroup() {
        return _vecDatum._riskGroup;
    }

    public String getShortDescription() {
        return _vecDatum._description;
    }

    public Person getAuthor() {
        return Collector.getPerson( _vecDatum._authorUUID );
    }

    public Format getFormat() {
        return Collector.getFormat( _vecDatum._formatUUID );
    }

    /**
     * Method for increasing the risk group of the Vector.  The
     * risk group cannot be decreased from public methods.
     * @param newrg the new risk group (2, 3, 4, or 5)
     */
    public void changeRiskGroup( Short newrg ) {
        changeRiskGroupRelay( newrg );
    }

    private void changeRiskGroupRelay( Short newrg ) {
        if ( newrg > _vecDatum._riskGroup ) {
            addUndo( "_riskGroup", _vecDatum._riskGroup, newrg );
            _vecDatum._riskGroup = newrg;
            setChanged(RefreshEvent.Condition.RISK_GROUP_CHANGED);
            return;
        }
        fireData(new RefreshEvent(this, RefreshEvent.Condition.RISK_GROUP_CHANGED));
    }

    /**
     * Determines the risk group of the Part,
     * relayed from the initial call to NucSeq's
     * call to foreign server
     * @param rg
     */
    private void setRiskGroup( short rg ) {
        if ( rg == 5 ) {
            _vecDatum._riskGroup = 5;
            return;
        }
        _vecDatum._riskGroup = rg;

        //Check it's features to see if any are higher RG
        for ( Annotation an : this.getSeq().getAnnotations() ) {
            Feature afeat = an.getFeature();
            if ( afeat == null ) {
                continue;
            }
            if ( afeat.getRiskGroup() > _vecDatum._riskGroup ) {
                _vecDatum._riskGroup = afeat.getRiskGroup();
            }
        }
    }

    public static Vector retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.VECTOR );
        cq.eq( Vector.Fields.NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Vector p = (Vector) l.get( 0 );
        return p;
    }

    public static Vector retrieveByHash( String hash ) {
        if ( hash.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.VECTOR );
        cq.eq( Vector.Fields.HASH, hash );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Vector p = (Vector) l.get( 0 );
        return p;
    }

    public String getHash() {
        return _vecDatum._hash;
    }
    /**-----------------
    variables
    -----------------*/
    private VectorDatum _vecDatum;

    public static class VectorDatum extends ObjBaseDatum {

        public ArrayList<String> _myPlasmids;
        public String _seqID;
        public String _description;
        public String _authorUUID;
        public String _hash;
        public String _formatUUID;
        public Short _riskGroup = 1;
        public ArrayList<Part> _composition;         //NEED TO FLATTEN THAT
        //For handling genomic stuff
        public boolean _isGenomic;
        public boolean _isCircular = false;

        @Override
        public ObjType getType() {
            return ObjType.VECTOR;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        IS_GENOMIC,
        RISK_GROUP,
        DESCRIPTION,
        TYPE,
        HASH,
        AUTHOR,
        FORMAT,
        SEQUENCE,
        IS_CIRCULAR,
        PLASMIDS
    }
}
