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
createQuery
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
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author jcanderson
 */
public class Part extends ObjBase {

    public Part( PartDatum d ) {
        super( d );
        _partDatum = d;

        if ( _partDatum._riskGroup == -1 ) {
            final Part item = this;
            Thread bslThread = new Thread() {

                @Override
                public void run() {
                    setRiskGroup( item.getSeq().performBiosafetyCheck() );
                }
            };
            bslThread.start();
            addSaveHold( bslThread );
        }
    }

    @Override
    public ObjType getType() {
        return ObjType.PART;
    }

    /**
     * Create basic from scratch
     *
     * @param name  nickname of Part, like "roo40"
     * @param shortdescription short description, such as "[TetR]"
     * @param seq sequence of the Part like "cgaaggcaggacacacatg"
     * @param form Part Format
     * @param author author of the Part
     * @param partType Basic or Composite
     */
    private Part( String name, String shortdescription, String seq, Format form, Person author ) {
        //Use the transient constructor and add to Collector later if the Part passes checks
        super();
        _partDatum = new PartDatum();
        _datum = _partDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _partDatum._myPlasmids = new ArrayList<String>();
        _partDatum._partType = partType.Basic;
        _partDatum._authorUUID = author.getUUID();
        _partDatum._formatUUID = form.getUUID();
        _partDatum._shortdescription = shortdescription;
        _partDatum._seqID = new NucSeq( seq ).getUUID();
        _isTransient = true;
    }

    /**
     * Create composite from scratch
     *
     * @param name  nickname of Part, like "roo40"
     * @param shortdescription short description, such as "[TetR]"
     * @param seq sequence of the Part like "cgaaggcaggacacacatg"
     * @param form Part Format
     * @param author author of the Part
     * @param partType Basic or Composite
     */
    private Part( String name, String shortdescription, Format form, Person author ) {
        //Use the transient constructor and add to Collector later if the Part passes checks
        super();
        _partDatum = new PartDatum();
        _datum = _partDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _partDatum._myPlasmids = new ArrayList<String>();
        _partDatum._partType = partType.Composite;
        _partDatum._authorUUID = author.getUUID();
        _partDatum._formatUUID = form.getUUID();
        _partDatum._shortdescription = shortdescription;
        _isTransient = true;
    }

    /**
     * Call this method to construct a new basic Part.  It will check that:
     *  1) A sequence in this Format isn't already in the database
     *      otherwise it returns the Part that already exists
     *  2) The Part obeys its Format standard
     *      otherwise it returns null
     *
     * Only if those are satisfied is it added to the Collector and returned to
     * the calling code.
     *
     * @param name  nickname of Part, like "roo40"
     * @param shortdescription short description, such as "[TetR]"
     * @param seq sequence of the Part like "cgaaggcaggacacacatg"
     * @param form Part Format
     * @param author author of the Part
     * @param partType Basic or Composite
     */
    public static Part generateBasic( String name, String shortdescription, String seq, Format form, Person author ) {
        //Generate a hash of the sequence and Format
        NucSeq aseq = new NucSeq( seq );
        aseq.setTransient();
        String key = aseq.getSeq() + form.getUUID();
        String uuidKey = generateUUIDAsHash( key );

        Part prexistingSeq = retrieveByHash( uuidKey );

        //If this sequence/Format is already in the database, return that
        if ( prexistingSeq != null ) {
            int n = JOptionPane.showConfirmDialog( null, "A part with this sequence already exists.  You should try to use that part.  Do you want to cancel this new part?", "Part "
                    + "already exists", JOptionPane.YES_NO_OPTION );
            if ( n == 0 ) {
                return prexistingSeq;
            }

            //Do a second chance to cancel
            int m = JOptionPane.showConfirmDialog( null, "Are you sure you really want two copies of this sequence?  It isn't recommended, can I please abort this?", "Part "
                    + "already exists", JOptionPane.YES_NO_OPTION );
            if ( m == 0 ) {
                return prexistingSeq;
            }
        }

        //Check to see if a Part by this name already exists in the database
        prexistingSeq = retrieveByName( name );
        while ( prexistingSeq != null ) {
            name = JOptionPane.showInputDialog( "A part named " + name + " already exists, please give me a new name." );
            if(name==null) {
                return null;
            }
            prexistingSeq = retrieveByName( name );
        }

        Part pa = null;
        try {
            pa = new Part( name, shortdescription, seq, form, author );
        } catch ( java.lang.NullPointerException e ) {
            return null;
        }

        if ( pa == null ) {
            return null;
        }

        pa.setTransient();

        if ( pa.getSeq().getSeq().equals("!") ) {
            return null;
        }

        if ( !form.checkPart( pa ) ) {
            return null;
        }

        //If it's gotten this far it's a good vector, so finish it up and return it
        pa._partDatum._hash = uuidKey;
        pa._isTransient = false;
        pa.getSeq().setLocked( true );
        Collector.add( pa );

        //Do risk group setting on a new thread
        final Part partout = pa;
        Thread bslThread = new Thread() {

            @Override
            public void run() {
                partout.setChanged(RefreshEvent.Condition.NAME_CHANGED);
                partout.setRiskGroup( partout.getSeq().performBiosafetyCheck() );
            }
        };
        bslThread.start();
        partout.addSaveHold( bslThread );

        return pa;
    }

    public static Part generateComposite( ArrayList<Part> composition, Object additionalRequirements, Format f, Person author, String name, String shortdescription ) {
        if ( !f.checkComposite( composition, additionalRequirements ) ) {
            System.out.println("generateComposite: Doesn't obey format, return null");
            return null;
        }
        String key = f.getUUID();
        for(Part p: composition) {
            key+=p.getUUID();
        }
        String uuidKey = generateUUIDAsHash( key );

        Part prexistingSeq = retrieveByHash( uuidKey );

        //If this composition/Format is already in the database, return that
        if ( prexistingSeq != null ) {
            int n = JOptionPane.showConfirmDialog( null, "A part with this composition already exists.  You should try to use that part.  Do you want to cancel this new part?", "Part "
                    + "already exists", JOptionPane.OK_CANCEL_OPTION );
            if ( n == 0 ) {
                return prexistingSeq;
            }

            //Do a second chance to cancel
            int m = JOptionPane.showConfirmDialog( null, "Are you sure you really want two copies of this sequence?  It isn't recommended, can I please abort this?", "Part "
                    + "already exists", JOptionPane.OK_CANCEL_OPTION );
            if ( m == 0 ) {
                return prexistingSeq;
            }
        }

        //Check to see if a Part by this hash already exists in the database
        prexistingSeq = retrieveByName( name );
        while ( prexistingSeq != null ) {
            name = JOptionPane.showInputDialog( "A part named " + name + " already exists, please give me a new name." );
            if(name==null) {
                return null;
            }
            prexistingSeq = retrieveByName( name );
        }

        //Since it was a proper composition of Format f, make the new Part
        String shortDesc = composition.get( 0 ).getShortDescription() + "." + composition.get( 1 ).getShortDescription();
        Part newComposite = new Part( name, shortDesc, f, author );
        
        ArrayList<String> newcomp = new ArrayList<String>();
        for ( Part p : composition ) {
            newcomp.add( p.getUUID() );
        }
        newComposite._partDatum._composition = newcomp;

        //Do risk group setting on a new thread
        final Part partout = newComposite;
        Thread bslsearch = new Thread() {

            @Override
            public void run() {
                partout.setRiskGroup( partout.getSeq().performBiosafetyCheck() );
            }
        };
        bslsearch.start();
        partout.addSaveHold( bslsearch );
        partout._isTransient = false;

        Collector.add( partout );
        return partout;
    }

    public boolean checkFormat() {
        return getFormat().checkPart( this );
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        String description = (String) objHash.get( "_shortdescription" );
        String hash = (String) objHash.get( "_partHash" );
        String sriskGroup = (String) objHash.get( "_riskGroup" );
        short riskGroup = Short.parseShort( sriskGroup );

        String idformat = (String) objHash.get( "_formatUUID" );
        String idPerson = (String) objHash.get( "_authorUUID" );
        String idNucseq = (String) objHash.get( "_seqID" );
        String isbasic = (String) objHash.get( "isBasic" );
        Byte basic = 0;
        if ( isbasic.equals( "true" ) ) {
            basic = 1;
        }

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        PartDatum d = new PartDatum();

        d.uuid = uuid;
        d.name = name;
        d._shortdescription = description;
        d._hash = hash;
        d._riskGroup = riskGroup;
        d._formatUUID = idformat;
        d._authorUUID = idPerson;
        d._seqID = idNucseq;
        if ( basic > 0 ) {
            d._partType = partType.Composite;
        } else {
            d._partType = partType.Basic;
        }
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;

        return new Part( d );
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
        datahash.put( "uuid", _partDatum.uuid );
        datahash.put( "name", _partDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_shortdescription", _partDatum._shortdescription );
        datahash.put( "_riskGroup", Short.toString( _partDatum._riskGroup ) );
        datahash.put( "_formatUUID", _partDatum._formatUUID );
        datahash.put( "_authorUUID", _partDatum._authorUUID );
        datahash.put( "_partHash", _partDatum._hash );
        datahash.put( "_seqID", _partDatum._seqID );
        if ( _partDatum._partType.equals( partType.Basic ) ) {
            datahash.put( "isBasic", "true" );
        } else {
            datahash.put( "isBasic", "false" );
        }


        if ( _partDatum._partType.equals( partType.Composite ) && _partDatum._composition != null ) {
            datahash.put( "composition", _partDatum._composition );
        }

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getFormat().generateXml( allObjects );
        allObjects = getSeq().generateXml( allObjects );

        //If it's a composite Part, recursively grab all parts in its composition
        if ( _partDatum._partType.equals( partType.Composite ) && _partDatum._composition != null ) {
            for ( String uuid : _partDatum._composition ) {
                Part apart = Collector.getPart( uuid );
                allObjects = apart.generateXml( allObjects );
            }
        }

        //Return the datahash
        return allObjects;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting part save" );
        if ( !isChanged() ) {
            System.out.println( "part didn't require saving" );
            return true;
        }

        if(_partDatum._partType.equals( partType.Composite ) ) {
            for(String uuid : _partDatum._composition) {
                System.out.println("Working on saving " + uuid);
                if ( Collector.isLocal(uuid) ) {
                    Part apart = Collector.getPart(uuid);
                    if ( !apart.isInDatabase() ) {
                        if ( !apart.save( conn ) ) {
                            return false;
                        }
                    }
                }
            }
        }
        
        if ( Collector.isLocal( _partDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _partDatum._seqID ) ) {
            NucSeq seq = getSeq();
            if ( !seq.save( conn ) ) {
                return false;
            }
        }


        return super.save( conn );
    }


    /* PUTTERS
     * */
    @Override
    public void changeName( final String newname ) {
        Part existing = Part.retrieveByName(newname);
        if(existing!=null) {
            if(!existing.getUUID().equals(this.getUUID())) {
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
                return;
            }
        }
        super.changeName( newname );
    }

    public void changeShortDescription( String text ) {
        addUndo( "_shortdescription", ((PartDatum) _datum)._shortdescription, text );
        _partDatum._shortdescription = text;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    /**
     * This is a convenience method, the real change to the sequence
     * happens in the linked NucSeq
     * @param newseq
     */
    public void changeSequence( final String newseq ) {
        if ( newseq.equals( "" ) || newseq == null ) {
            return;
        }

        if ( this._partDatum._partType.equals( partType.Composite ) ) {
            return;
        }

        final String oldseq = Collector.getNucSeq( _partDatum._seqID ).toString();

        Collector.getNucSeq( _partDatum._seqID ).APIchangeSeq( newseq );

        boolean isok = getFormat().checkPart( this );
        if ( !isok ) {
            Collector.getNucSeq( _partDatum._seqID ).APIchangeSeq( oldseq );
            return;
        }
        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _partDatum._seqID ).APIchangeSeq( oldseq );
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _partDatum._seqID ).APIchangeSeq( newseq );
            }
        };

        //Change the risk group
        _partDatum._riskGroup = -1;
        final Part item = this;
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

    /**
     * Change the Format of the Part
     * @param f
     */
    public void changeFormat( Format f ) {
        if ( f == null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.FORMAT_CHANGED));
            return;
        }

        boolean ok = f.checkPart( this );
        if ( !ok ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.FORMAT_CHANGED));
            return;
        }

        addUndo( "_formatUUID", _partDatum._formatUUID, f.getUUID() );
        _partDatum._formatUUID = f.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FORMAT_CHANGED);
    }

    public void changeAuthor( Person newauthor ) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _partDatum._authorUUID, newauthor.getUUID() );
        _partDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            default:
                return false;
        }
    }

    public void changeComposition( ArrayList<Part> comp ) {
        ArrayList<String> newcomp = new ArrayList<String>();
        for ( Part p : comp ) {
            newcomp.add( p.getUUID() );
        }
        addUndo( "_composition", _partDatum._composition, newcomp );

        _partDatum._composition = newcomp;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    /* GETTERS
     * */
    public ArrayList<Plasmid> getPlasmids() {
        ArrayList<Plasmid> out = new ArrayList<Plasmid>();

        return out;
    }

    public short getRiskGroup() {
        return _partDatum._riskGroup;
    }

    public NucSeq getSeq() {
        if ( this._partDatum._partType.equals( partType.Basic ) ) {
            return Collector.getNucSeq( _partDatum._seqID );
        } else {
            if(this._partDatum._seqID==null) {
                NucSeq seq = getFormat().generateCompositeSequence( getCompositeParts(), null );
                _partDatum._seqID = seq.getUUID();
            }
            return Collector.getNucSeq( _partDatum._seqID );
        }
    }

    public partType getPartType() {
        return _partDatum._partType;
    }

    public Person getAuthor() {
        return Collector.getPerson( _partDatum._authorUUID );
    }

    public Format getFormat() {
        return Collector.getFormat( _partDatum._formatUUID );
    }

    public Part getComposition( int i ) {
        if ( _partDatum._composition.get( i ).equals( "" ) ) {
            return null;
        }
        return Collector.getPart( _partDatum._composition.get( i ) );
    }

    public ArrayList<Part> getCompositeParts() {
        ArrayList<Part> out = new ArrayList<Part>();
        for ( String uuid : _partDatum._composition ) {
            Part p = Collector.getPart( uuid );
            out.add( p );
        }
        return out;
    }

    public ArrayList<String> getComposition() {
        return _partDatum._composition;
    }

    public void setComposition( ArrayList<String> comp ) {
        _partDatum._composition = comp;
    }

    void setSeq( NucSeq inseq ) {
        _partDatum._seqID = inseq.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    void setSeq( String inseq ) {
        setSeq( new NucSeq( inseq ) );
    }

    public final void changeRiskGroup( Short newrg ) {
        if ( newrg > _partDatum._riskGroup ) {
            addUndo( "_riskGroup", _partDatum._riskGroup, newrg );
            _partDatum._riskGroup = newrg;
        }
        setChanged(RefreshEvent.Condition.RISK_GROUP_CHANGED);
    }

    /**
     * Determines the risk group of the Part,
     * relayed from the initial call to NucSeq's
     * call to foreign server
     * @param rg
     */
    private void setRiskGroup( short rg ) {
        if ( rg == 5 ) {
            relayRiskGroup((short) 5);
            return;
        }
        if ( this._partDatum._partType.equals( partType.Composite ) ) {
            short currentHighest = rg;
            boolean firsthigher = false;
            for ( Part p : this.getCompositeParts() ) {
                //If the Part's risk group hasn't been determined, this one isn't either
                if ( p.getRiskGroup() == -1 ) {
                    relayRiskGroup((short) -1);
                    return;
                }

                //If a subpart has a 2+ risk group, increment highest
                if ( p.getRiskGroup() > currentHighest ) {
                    currentHighest = p.getRiskGroup();
                    relayRiskGroup(currentHighest);
                }

                //If a subpart has a 2+ risk group
                if ( p.getRiskGroup() > 1 ) {
                    if ( firsthigher ) {
                        //Throw a dialog asking for user to put in the new risk group
                        ButtonGroup group = new javax.swing.ButtonGroup();
                        String msgString = "This composite part joins two subparts with risk groups of 2 or higher.  What should the new value be?";
                        int numelements = 5 - currentHighest;
                        Object[] array = new Object[ numelements + 1 ];
                        JRadioButton[] buttons = new JRadioButton[ numelements ];
                        for ( short i = 0; i < numelements; i++ ) {
                            buttons[i] = new javax.swing.JRadioButton( "Risk Group " + (i + currentHighest) );
                            group.add( buttons[i] );
                            array[i + 1] = buttons[i];
                        }
                        array[0] = msgString;

                        int sel = -1;
                        while ( sel != 0 ) {
                            sel = JOptionPane.showConfirmDialog( null, array, "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE );
                        }
                        scanButtons:
                        for ( short i = 1; i < numelements; i++ ) {
                            if ( buttons[i].isSelected() ) {
                                relayRiskGroup((short) (currentHighest + 1));
                                break scanButtons;
                            }
                        }
                        currentHighest = _partDatum._riskGroup;

                    } else {
                        firsthigher = true;
                    }
                }
            }

        //If it's a basic Part, the risk group is whatever the algorithm said
        } else {
            relayRiskGroup(rg);
        }

        //Check it's features to see if any are higher RG
        for ( Annotation an : this.getSeq().getAnnotations() ) {
            Feature afeat = an.getFeature();
            if ( afeat == null ) {
                continue;
            }
            relayRiskGroup(afeat.getRiskGroup());
        }
    }

    private void relayRiskGroup(short value) {
        if(value > _partDatum._riskGroup) {
            _partDatum._riskGroup = value;
            System.out.println( "Setting risk group to " + _partDatum._riskGroup );
            setChanged(RefreshEvent.Condition.RISK_GROUP_CHANGED);
        } else {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.RISK_GROUP_CHANGED));
        }
    }

    public String getShortDescription() {
        return _partDatum._shortdescription;
    }

    public static Part retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.PART );
        cq.contains( Part.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Part p = (Part) l.get( 0 );
        return p;
    }

    public static Part retrieveByHash( String hash ) {
        if ( hash.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.PART );
        cq.contains( Part.Fields.HASH, hash, true );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Part p = (Part) l.get( 0 );
        return p;
    }

    public String getHash() {
        return _partDatum._hash;
    }
    /**-----------------
    variables
    -----------------*/
    private PartDatum _partDatum;


    public static class PartDatum extends ObjBaseDatum {

        public ArrayList<String> _myPlasmids = new ArrayList<String>();
        public String _seqID = null;
        public String _shortdescription;
        public String _authorUUID;
        public ArrayList<String> _composition = new ArrayList<String>();
        public String _formatUUID;
        public short _riskGroup = -1;
        public String _hash;
        public partType _partType = partType.Basic;

        @Override
        public ObjType getType() {
            return ObjType.PART;
        }
    }

    public static enum partType {

        Basic, Composite
    };

    /******* FIELDS *******/
    public enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        RISK_GROUP,
        DESCRIPTION,
        HASH,
        AUTHOR,
        FORMAT,
        SEQUENCE,
        PLASMIDS
    }
}
