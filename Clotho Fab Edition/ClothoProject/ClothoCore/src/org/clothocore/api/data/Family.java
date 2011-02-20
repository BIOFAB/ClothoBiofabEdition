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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;
import org.clothocore.util.misc.BareBonesBrowserLaunch;

/**
 * Family will undergo significant augmentation before the next Clotho release.
 * In Clotho 2.0, this class is functional, but we do not guarantee that the constructor
 * will be the minimal version represented here.  there's a good chance this will
 * be replaced with a factory method.
 *
 * @author J. Christopher Anderson
 */
public class Family extends ObjBase {

    /**
     * Constructor for ClothoConnections only
     * @param d
     */
    public Family( FamilyDatum d ) {
        super( d );
        _famDatum = d;
        System.out.println( _famDatum._noteLinks.size() + " size of _noteLinks" );
    }

    /**
     * Public constructor for a new Family
     * @param name
     * @param author
     * @param superfam
     */
    public Family( String name, Person author, Family superfam ) {
        super(  );
        _famDatum = new FamilyDatum();
        _datum = _famDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _famDatum.uuid = _uuid;

        /*JCA Note to Saurabh:  eventually superfam shouldn't be allowed to be null, there should be
         * hard-coded or document-coded families such that there is never this chicken-before-the-egg problem
         * so, this if logic is a temporary hack
         */
        if(superfam!=null) {
            _famDatum._superFamilyUUID = superfam.getUUID();
        }
        _famDatum._authorUUID = author.getUUID();
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     * @param conn the ClothoConnection to save
     * @return true if save was successful
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting family save" );
        if ( !isChanged() ) {
            System.out.println( "family didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _famDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _famDatum._superFamilyUUID ) ) {
            Family superfam = this.getSuperFamily();
            if ( !superfam.isInDatabase() ) {
                if ( !superfam.save( conn ) ) {
                    return false;
                }
            }
        }

        if(!isInDatabase()) {
            HashSet<String> notelinks = _famDatum._noteLinks;
            HashSet<String> famlinks = _famDatum.childFamilyLinks;
            _famDatum._noteLinks = new HashSet<String>();
            _famDatum.childFamilyLinks = new HashSet<String>();
            if(!super.save( conn )) {
                return false;
            }
            _famDatum._isChanged = true;
            _famDatum._noteLinks = notelinks;
            _famDatum.childFamilyLinks = famlinks;
        }


        //Check if any notes need saving
        for ( String noteUUID : _famDatum._noteLinks ) {
            if ( Collector.isLocal( noteUUID ) ) {
                Note f = Collector.getNote( noteUUID );
                if ( f != null ) {
                    System.out.println( f.getUUID() + f.getName() );
                    if ( !f.save( conn ) ) {
                        return false;
                    }
                }
            }
        }

        //Check if any child families need saving
        for ( String childfamUUID : _famDatum.childFamilyLinks ) {
            if ( Collector.isLocal( childfamUUID ) ) {
                Family f = Collector.getFamily( childfamUUID );
                if ( f != null ) {
                    System.out.println( f.getUUID() + f.getName() );
                    if ( !f.save( conn ) ) {
                        return false;
                    }
                }
            }
        }

        return super.save( conn );
    }

    @Override
    public ObjType getType() {
        return ObjType.FAMILY;
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
        datahash.put( "uuid", _famDatum.uuid );
        datahash.put( "name", _famDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_riskGroup", _famDatum._riskGroup );
        datahash.put( "_authorUUID", _famDatum._authorUUID );
        datahash.put( "_description", _famDatum._description );
        datahash.put( "_level", _famDatum._level );
        datahash.put( "_dataDescription", _famDatum._dataDescription );
        datahash.put( "_superFamilyUUID", _famDatum._superFamilyUUID );
        datahash.put( "noteLinks", _famDatum._noteLinks );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );


        //Gather up all the notes
        if ( _famDatum._noteLinks != null ) {
            for ( String uuid : _famDatum._noteLinks ) {
                Note anote = Collector.getNote( uuid );
                allObjects = anote.generateXml( allObjects );
            }
        }

        //Recursively gather the objects linked to this object
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getSuperFamily().generateXml( allObjects );

        //Return the datahash
        return allObjects;
    }

    /**
     * General addition of objects to link to this family.  This method
     * directly translates drag-and-drop events but also is the method
     * used for creating links to Families.
     *
     * Notes can be dropped onto Families.
     * @param dropObject
     * @return
     */
    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case NOTE:
                Note item = (Note) dropObject;
                _famDatum._noteLinks.add( item.getUUID() );
                item.addFamilyRelay( this );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                return true;
            default:
                return false;
        }
    }

    /**
     * Remove a Note linked to this Family
     * @param anote the Note you wish to remove
     * @return true if the Note was removed, or false if the Note wasn't there
     */
    public boolean removeNote( Note item ) {
        String uuid = item.getUUID();
        if ( this._famDatum._noteLinks.contains( uuid ) ) {
            _famDatum._noteLinks.remove( uuid );
            item.removeFamilyRelay( this.getUUID() );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
            return true;
        }
        fireData(new RefreshEvent(this, RefreshEvent.Condition.NOTE_LINKED));
        return false;
    }

    /**
     * Relay for Family:Feature XRef
     * @param aThis
     */
    void addFeatureRelay( Feature aThis ) {
        _famDatum._myFeatures.add( aThis.getUUID() );
        fireData(new RefreshEvent(this, RefreshEvent.Condition.FAMILY_TO_FEATURE));
    }

    /**
     * Relay for Family:Feature XRef
     * @param aThis
     */
    void removeFeatureRelay( String uUID ) {
        _famDatum._myFeatures.remove( uUID );
        fireData(new RefreshEvent(this, RefreshEvent.Condition.FAMILY_TO_FEATURE));
    }

    /* SETTERS
     * */

    /**
     * Change the risk group of the Feature.  You can only raise the risk group.
     * @param newrg the new risk group (1 through 5)
     */
    public final void changeRiskGroup( short newrg ) {
        if ( newrg > _famDatum._riskGroup && newrg<=5) {
            addUndo( "_riskGroup", _famDatum._riskGroup, newrg );
            _famDatum._riskGroup = newrg;
        }
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.RISK_GROUP_CHANGED);
    }

    /**
     * Change the display name of this Family
     * @param newname the new name, it must be unique in the database
     */
    @Override
    public void changeName( final String newname ) {
        Family existing = Family.retrieveByName(newname);
        if(existing!=null) {
            if(!existing.getUUID().equals(this.getUUID())) {
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
                return;
            }
        }
        super.changeName(newname);
    }

    /**
     * Change the description of the Family
     * @param desc a String
     */
    public void changeDescription( String desc ) {
        _famDatum._description = desc;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.DESCRIPTION_CHANGED);
    }

    /* GETTERS
     * */

    /**
     * Determine if this Family can have child Families or not
     * @return true if can extend this Family
     */
    public boolean isExtendable() {
        return _famDatum._isExtendable;
    }

    /**
     * Getter for the minimum risk group of the Family
     * @return a Short value 1 to 5
     */
    public short getRiskGroup() {
        return _famDatum._riskGroup;
    }

    /**
     * Getter for the description of this Family
     * @return a String
     */
    public String getDescription() {
        return _famDatum._description;
    }

    /**
     * Returns this Family's immediate superfamily
     * @return a Family ObjBase
     */
    public Family getSuperFamily() {
        return Collector.getFamily( _famDatum._superFamilyUUID );
    }

    /**
     * Get the superfamily of this Family at a specificied hierarchy level
     * @param level how high up the Family tree you want
     * @return a Family ObjBase
     */
    public Family getSuperFamily( int level ) {
        Family parent = Collector.getFamily( _famDatum._superFamilyUUID );
        if ( parent.getLevel() > level ) {
            return parent.getSuperFamily( level );
        } else if ( parent.getLevel() == level ) {
            return parent;
        } else {
            return null;
        }
    }

    /**
     * Getter for the specified hierarchy level of this Family
     * @return an Integer
     */
    public int getLevel() {
        return _famDatum._level;
    }

    /**
     * Getter for SO term as a String
     * @return a String UUID from the Sequence Ontology list
     */
    public String getSOTerm() {
        return _famDatum._SOterm;
    }

    /**
     * Returns the SO term of the Family if it exists, otherwise,
     * climbs up to the first superFamily that has a SO term.
     * @return
     */
    public String getNearestSOTerm() {
        if ( _famDatum._SOterm.equals( "" ) ) {
            Family parent = Collector.getFamily( this._famDatum._superFamilyUUID );
            return parent.getNearestSOTerm();
        } else {
            return _famDatum._SOterm;
        }
    }

    /**
     * Launches the MISO Sequence Ontology browser for the corresponding SO term
     * if it is available
     */
    public void launchMISO() {
        if ( !_famDatum._SOterm.startsWith( "SO:" ) ) {
            return;
        }
        String urlstring = "http://www.sequenceontology.org/miso/current_release/term/" + _famDatum._SOterm;
        BareBonesBrowserLaunch.openURL( urlstring );
    }

    /**
     * Getter for this Family's author as UUID
     * @return
     */
    public String getAuthorUUID() {
        return _famDatum._authorUUID;
    }

    /**
     * Getter for this Family's author as Person object
     * @return
     */
    public Person getAuthor() {
        return Collector.getPerson( _famDatum._authorUUID );
    }

    /**
     * Get the list of notes as Note objects from this Note
     * @return
     */
    public HashSet<Note> getNotes() {
        HashSet<Note> out = new HashSet<Note>();
        for ( String f : _famDatum._noteLinks ) {
            Note item = Collector.getNote( f );
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * Get the list of notes as UUIDs in a HashSet
     * @return a HashSet of uuids for notes
     */
    public HashSet<String> getNoteLinks() {
        return _famDatum._noteLinks;
    }

    /**
     * Query the database and get a Family ObjBase whose name is
     * the argument
     * @param name the desired Family's name
     * @return a Family ObjBase
     */
    public static Family retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.FAMILY );
        cq.eq( Family.Fields.NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Family p = (Family) l.get( 0 );
        return p;
    }

    /*********************************************************************
     * Remainder of Family class is for dealing with biochemical traits
     * of the Family
     *********************************************************************/

    public class Trait {
        /**
         * Constructor for connections only
         * @param plasuuid
         * @param start
         * @param end
         * @param revcomp
         */
        public Trait( int pos, String namer, String kinder, String typer, String val, String constrs, Date create, Date last ) {
            position = pos;
            name = namer;
            kind = kinder;
            type = typer;
            value = val;
            constraint = constrs;
            dateCreated = create;
            lastModified = last;
        }
        
        //VARIABLES//
        public int position;
        public String name;
        public String kind;
        public String type;
        public String value;
        public String constraint;
        public Date dateCreated;
        public Date lastModified;
    }


/*-----------------
    variables
-----------------*/
    private FamilyDatum _famDatum;

    public static class FamilyDatum extends ObjBaseDatum {

        public HashSet<String> _myFeatures = new HashSet<String>();
        public HashSet<String> childFamilyLinks = new HashSet<String>();
        public HashSet<String> _noteLinks = new HashSet<String>();
        public String _description = "";
        public String _superFamilyUUID = "";
        public String _authorUUID = "";
        public String _SOterm = "";
        public Boolean _isExtendable = false;
        public String _dataDescription;
        public Short _riskGroup = -1;
        public int _level;

        public HashSet<Trait> _traits = new HashSet<Trait>();

        @Override
        public ObjType getType() {
            return ObjType.FAMILY;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        DESCRIPTION,
        RISK_GROUP,
        LEVEL,
        SOTERM,
        SCHEMA,
        SUPER_FAMILY,
        AUTHOR,
        IS_EXTENDABLE,
        CHILD_FAMILIES

    }
}
