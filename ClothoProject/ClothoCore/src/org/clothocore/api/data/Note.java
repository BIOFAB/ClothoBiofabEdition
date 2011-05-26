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
import java.util.Date;
import java.util.HashSet;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class Note extends ObjBase {

    public Note( NoteDatum d ) {
        super( d );
        _noteDatum = d;
    }

    /**
     * Constructor for a new Factoid.  Title and author must be supplied,
     * but the reference and WikiText can be null.
     *
     * @param title  Short title of factoi "GFP turns green"
     * @param author Person object author of the Factoid
     * @param reference a formatted String reference (a PMID, DOI, Patent).  See
     * changeReference() for description of its formatting.  It can be null or ""
     * and then will be ignored.
     * @param wt The WikiText object for the blurb.  It can be null in which case
     * a new one will be constructed.
     */
    public Note( String title, Person author, WikiText wt ) {
        super( );

        _noteDatum = new NoteDatum();
        _datum = _noteDatum;
        _datum.name = title;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _noteDatum.uuid = _uuid;

        if ( wt == null ) {
            _noteDatum._wikiUUID = new WikiText( "" ).getUUID();
        } else {
            _noteDatum._wikiUUID = wt.getUUID();
        }

        _noteDatum._authorUUID = author.getUUID();
        _noteDatum._parentNotes = new HashSet<String>();
        _noteDatum._childNotes = new HashSet<String>();
        _noteDatum._factoids = new HashSet<String>();
        _noteDatum._strainLinks = new HashSet<String>();
        _noteDatum._featureLinks = new HashSet<String>();
        _noteDatum._familyLinks = new HashSet<String>();
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting NOTE save" );

        if ( Collector.isLocal( _noteDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( this.getWikiText().getUUID() ) ) {
            WikiText wt = getWikiText();
            if ( wt != null ) {
                System.out.println( wt.getUUID() + wt.getAsWikiText() );
                if ( !wt.save( conn ) ) {
                    return false;
                }
            }
        }

        if(!isInDatabase()) {
            HashSet<String> facts = _noteDatum._factoids;
            HashSet<String> childnotes = _noteDatum._childNotes;
            _noteDatum._factoids = new HashSet<String>();
            _noteDatum._childNotes = new HashSet<String>();
            if(!super.save( conn )) {
                return false;
            }
            _noteDatum._isChanged = true;
            _noteDatum._factoids = facts;
            _noteDatum._childNotes = childnotes;
        }

        //Check if any factoids need saving
        for ( String factoidUUID : _noteDatum._factoids ) {
            if ( Collector.isLocal( factoidUUID ) ) {
                Factoid f = Collector.getFactoid( factoidUUID );
                if ( f != null ) {
                    System.out.println( f.getUUID() + f.getName() );
                    if ( !f.save( conn ) ) {
                        return false;
                    }
                }
            }
        }

        /*JCA Note:
         * In the save hierarchy, the children of a Note belong to the Note
         * but not the parents.
         */

        //Check if any child Notes need saving
        for ( String noteUUID : _noteDatum._childNotes ) {
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

        return super.save( conn );
    }

    /**
     * Update the ObjBase.  It pulls the current Datum from
     * the default connection, and then depending on the status
     * of the current datum, will update it and notify listeners.
     *
     * @return false if nothing was updated
     */
    @Override
    public boolean update() {
        if(!super.update()) {
            return false;
        }

        ObjBaseDatum od = Hub.defaultConnection.getDatum(getType(), getUUID());
        if(!(od instanceof NoteDatum)) {
            return false;
        }
        NoteDatum d = (NoteDatum) od;
        if(!d.uuid.equals( _noteDatum.uuid)) {
            return false;
        }

        //If this ObjBase hasn't been changed, just replace the datums
        if(!_noteDatum._isChanged) {
            d._isChanged = false;
            _noteDatum = d;
            _datum = d;
            fireData( new RefreshEvent( this , RefreshEvent.Condition.UPDATE_ALL) );
            return true;
        }

        //Otherwise, do conflict resolution
            //Change these no matter what
            _noteDatum._isChanged = false;
            _noteDatum.dateCreated = d.dateCreated;
            _noteDatum.lastModified = d.lastModified;

            //Resolve name
            if(ConflictResolution.resolve(d.name, _noteDatum.name, d.name, "Name")) {
                _noteDatum.name =  d.name;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.NAME_CHANGED) );
            } else {
                _noteDatum._isChanged = true;
            }

            //Resolve author
            if(ConflictResolution.resolve(d._authorUUID, _noteDatum._authorUUID, d.name, "Author")) {
                _noteDatum._authorUUID = d._authorUUID;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.AUTHOR_CHANGED) );
            } else {
                _noteDatum._isChanged = true;
            }

            //Resolve wikitext
            if(ConflictResolution.resolve(d._wikiUUID, _noteDatum._wikiUUID, d.name, "WikiText")) {
                _noteDatum._wikiUUID = d._wikiUUID;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.WIKITEXT_CHANGED) );
            } else {
                _noteDatum._isChanged = true;
            }

            //Resolve Factoid links
                HashSet<String> removeme = new HashSet<String>();
                //figure out which ones should be removed
                for(String uuid : _noteDatum._factoids) {
                    if(d._factoids.contains(uuid)) {
                        continue;
                    }
                    if(ConflictResolution.resolve(uuid, _noteDatum.name, "Factoid link")) {
                        removeme.add(uuid);
                    } else {
                        _noteDatum._isChanged = true;
                    }
                }

                //Remove those dead links
                for(String uuid: removeme) {
                    _noteDatum._factoids.remove(uuid);
                }

                //Add any new links
                for(String uuid: d._factoids) {
                    _noteDatum._factoids.add(uuid);
                }
                fireData( new RefreshEvent( this , RefreshEvent.Condition.FACTOID_TO_NOTE) );

            //Resolve Child note links
                removeme = new HashSet<String>();
                //figure out which ones should be removed
                for(String uuid : _noteDatum._childNotes) {
                    if(d._childNotes.contains(uuid)) {
                        continue;
                    }
                    if(ConflictResolution.resolve(uuid, _noteDatum.name, "Factoid link")) {
                        removeme.add(uuid);
                    } else {
                        _noteDatum._isChanged = true;
                    }
                }

                //Remove those dead links
                for(String uuid: removeme) {
                    _noteDatum._childNotes.remove(uuid);
                }

                //Add any new links
                for(String uuid: d._childNotes) {
                    _noteDatum._childNotes.add(uuid);
                }
                fireData( new RefreshEvent( this , RefreshEvent.Condition.FACTOID_TO_NOTE) );

        return true;
    }

    @Override
    public ObjType getType() {
        return ObjType.NOTE;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        final Note myself = this;
        switch ( dropObject.getType() ) {
            case FACTOID:
                System.out.println("adding a factoid to a note");
                final Factoid fact = (Factoid) dropObject;

                ActionListener undo = new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _noteDatum._factoids.remove( fact.getUUID() );
                        fact.removeFactoidRelay( myself.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
                    }
                };
                ActionListener redo = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _noteDatum._factoids.add( fact.getUUID() );
                        fact.addFactoidRelay( myself );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
                    }
                };
                addUndo( undo, redo );

                _noteDatum._factoids.add( fact.getUUID() );
                fact.addFactoidRelay( this );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
                return true;
            case NOTE:
                final Note anote = (Note) dropObject;

                ActionListener undo2 = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _noteDatum._childNotes.remove( anote.getUUID() );
                        anote._noteDatum._parentNotes.remove( myself.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                    }
                };
                ActionListener redo2 = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _noteDatum._childNotes.add( anote.getUUID() );
                        anote._noteDatum._parentNotes.add( myself.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                    }
                };
                addUndo( undo2, redo2 );

                _noteDatum._childNotes.add( anote.getUUID() );
                anote._noteDatum._parentNotes.add( this.getUUID() );
                anote.setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                return true;
            default:
                return false;
        }
    }

    /**
     * Relay for Note:Feature XRef
     * @param aThis
     */
    void addFeatureRelay( Feature aThis ) {
        _noteDatum._featureLinks.add( aThis.getUUID() );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
    }

    /**
     * Relay for Note:Feature XRef
     * @param aThis
     */
    void removeFeatureRelay( String uUID ) {
        _noteDatum._featureLinks.remove( uUID );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
    }

    /**
     * Relay for Note:Family XRef
     * @param aThis
     */
    void addFamilyRelay( Family aThis ) {
        _noteDatum._familyLinks.add( aThis.getUUID() );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
    }

    /**
     * Relay for Note:Family XRef
     * @param aThis
     */
    void removeFamilyRelay( String uUID ) {
        _noteDatum._familyLinks.remove( uUID );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
    }

    /**
     * Relay for Note:Strain XRef
     * @param aThis
     */
    void addStrainRelay( Strain aThis ) {
        _noteDatum._strainLinks.add( aThis.getUUID() );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
    }

    /**
     * Relay for Note:Strain XRef
     * @param aThis
     */
    void removeStrainRelay( String uUID ) {
        _noteDatum._strainLinks.remove( uUID );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
    }

    /**
     * Remove a child Note
     * @param anote the Note you wish to remove
     * @return true if the Note was removed, or false if the Note wasn't there
     */
    public boolean removeChildNote( final Note anote ) {
        final Note myself = this;
        String uuid = anote.getUUID();
        if ( _noteDatum._childNotes.contains( uuid ) ) {

            ActionListener undo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _noteDatum._childNotes.add( anote.getUUID() );
                    anote._noteDatum._parentNotes.add( myself.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                }
            };
            ActionListener redo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _noteDatum._childNotes.remove( anote.getUUID() );
                    anote._noteDatum._parentNotes.remove( myself.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                }
            };
            addUndo( undo, redo );

            _noteDatum._childNotes.remove( uuid );
            anote._noteDatum._parentNotes.remove( this.getUUID() );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
            return true;
        }
        return false;
    }

    /**
     * Remove a child Note
     * @param anote the Note you wish to remove
     * @return true if the Note was removed, or false if the Note wasn't there
     */
    public boolean removeFactoid( final Factoid fact ) {
        final Note myself = this;
        String uuid = fact.getUUID();
        if ( this._noteDatum._factoids.contains( uuid ) ) {
            ActionListener undo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _noteDatum._factoids.add( fact.getUUID() );
                    fact.addFactoidRelay( myself );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
                }
            };
            ActionListener redo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _noteDatum._factoids.remove( fact.getUUID() );
                    fact.removeFactoidRelay( myself.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
                }
            };
            addUndo( undo, redo );

            _noteDatum._factoids.remove( uuid );
            fact.removeFactoidRelay( this.getUUID() );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
            return true;
        }
        return false;
    }

//NEED SECOND CONSTRUCTOR FOR NEW NOTE OBJECT IN RAM
    /* SETTERS
     * */
    private void setAuthor( String uuid ) {
        addUndo( "_authorUUID", _noteDatum._authorUUID, uuid );
        _noteDatum._authorUUID = uuid;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeAuthor(Person newauthor) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _noteDatum._authorUUID, newauthor.getUUID() );
        _noteDatum._authorUUID = newauthor.getUUID();
        setChanged(RefreshEvent.Condition.AUTHOR_CHANGED);
    }

    public void setWikiText( String uuid ) {
        addUndo( "_wikiUUID", _noteDatum._wikiUUID, uuid );
        _noteDatum._wikiUUID = uuid;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    /* PUTTERS
     * */

    /* GETTERS
     * */
    public Person getAuthor() {
        return Collector.getPerson( _noteDatum._authorUUID );
    }

    public WikiText getWikiText() {
        return Collector.getWikiText( _noteDatum._wikiUUID );
    }

    /**
     * Get the list of child notes as Note objects from this Note
     * @return
     */
    public HashSet<Note> getChildNotes() {
        HashSet<Note> out = new HashSet<Note>();
        for ( String f : _noteDatum._childNotes ) {
            Note fact = Collector.getNote( f );
            if ( fact != null ) {
                out.add( fact );
            }
        }
        return out;
    }

    /**
     * Get the list of child Note UUIDs from this Note as a HashSet
     * @return
     */
    public HashSet<String> getChildNoteLinks() {
        return _noteDatum._childNotes;
    }

    /**
     * Get the list of parent notes as Note objects from this Note
     * @return
     */
    public HashSet<Note> getParentNotes() {
        HashSet<Note> out = new HashSet<Note>();
        for ( String f : _noteDatum._parentNotes ) {
            Note fact = Collector.getNote( f );
            if ( fact != null ) {
                out.add( fact );
            }
        }
        return out;
    }

    /**
     * Get the list of parent Note UUIDs from this Note as a HashSet
     * @return
     */
    public HashSet<String> getParentNoteLinks() {
        return _noteDatum._parentNotes;
    }

    /**Get the list of child factoids as Factoid objects from this Note
     *
     * @return all this notes Factoid objects as a HashSet
     */
    public HashSet<Factoid> getFactoids() {
        HashSet<Factoid> out = new HashSet<Factoid>();
        for ( String f : _noteDatum._factoids ) {
            Factoid fact = Collector.getFactoid( f );
            if ( fact != null ) {
                out.add( fact );
            }
        }
        return out;
    }

    /**
     * Get the list of Factoid UUIDs from this Note as a HashSet
     * @return
     */
    public HashSet<String> getFactoidsLinks() {
        return _noteDatum._factoids;
    }

    /**Get the list of features that link to this Note
     *
     * @return all Feature objects as a HashSet
     */
    public HashSet<Feature> getFeatures() {
        HashSet<Feature> out = new HashSet<Feature>();
        for ( String f : _noteDatum._featureLinks ) {
            Feature item = Collector.getFeature( f );
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * Get the list of Feature UUIDs from this Note as a HashSet
     * @return
     */
    public HashSet<String> getFeatureLinks() {
        return _noteDatum._featureLinks;
    }

    /**Get the list of families that link to this Note
     *
     * @return all Family objects as a HashSet
     */
    public HashSet<Family> getFamilies() {
        HashSet<Family> out = new HashSet<Family>();
        for ( String f : _noteDatum._familyLinks ) {
            Family item = Collector.getFamily( f );
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * Get the list of Family UUIDs from this Note as a HashSet
     * @return
     */
    public HashSet<String> getFamilyLinks() {
        return _noteDatum._familyLinks;
    }

    /**Get the list of strains that link to this Note
     *
     * @return all Family objects as a HashSet
     */
    public HashSet<Strain> getStrains() {
        HashSet<Strain> out = new HashSet<Strain>();
        for ( String f : _noteDatum._strainLinks ) {
            Strain item = Collector.getStrain( f );
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * Get the list of Strain UUIDs from this Note as a HashSet
     * @return
     */
    public HashSet<String> getStrainLinks() {
        return _noteDatum._strainLinks;
    }

    /*-----------------
    variables
    -----------------*/
    private NoteDatum _noteDatum;


    public static class NoteDatum extends ObjBaseDatum {

        public String _wikiUUID;
        public String _authorUUID;
        public HashSet<String> _factoids;
        public HashSet<String> _parentNotes;
        public HashSet<String> _childNotes;
        //Slave hashes
        public HashSet<String> _familyLinks;
        public HashSet<String> _featureLinks;
        public HashSet<String> _strainLinks;

        @Override
        public ObjType getType() {
            return ObjType.NOTE;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        AUTHOR,
        WIKITEXT
    }
}
