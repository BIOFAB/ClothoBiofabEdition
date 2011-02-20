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
import java.util.HashSet;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class Factoid extends ObjBase {

    public Factoid( FactoidDatum d ) {
        super( d );
        _facDatum = d;
    }

    /**
     * Constructor for a new Factoid.  Title and author must be supplied,
     * but the Reference and WikiText can be null.
     *
     * @param title  Short title of factoi "GFP turns green"
     * @param author Person object author of the Factoid
     * @param Reference a formatted String Reference (a PMID, DOI, Patent).  See
     * changeReference() for description of its formatting.  It can be null or ""
     * and then will be ignored.
     * @param wt The WikiText object for the blurb.  It can be null in which case
     * a new one will be constructed.
     */
    public Factoid( String title, Person author, String reference, WikiText wt ) {
        super();
        _facDatum = new FactoidDatum();
        _datum = _facDatum;
        _datum.name = title;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        if ( wt == null ) {
            _facDatum._wikiUUID = new WikiText( "" ).getUUID();
        } else {
            _facDatum._wikiUUID = wt.getUUID();
        }

        if ( reference == null || reference.equals( "" ) ) {
        } else {
            changeReference( reference );
        }

        _facDatum._authorUUID = author.getUUID();
        _facDatum._noteLinks = new HashSet<String>();
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting factoid save" );

        if ( Collector.isLocal( _facDatum._authorUUID ) ) {
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
        boolean out = true;
        if(!super.update()) {
            out = false;
        }
        if(!getWikiText().update()) {
            out = false;
        }
        if(!out) {
            return false;
        }

        ObjBaseDatum od = Hub.defaultConnection.getDatum(getType(), getUUID());
        if(!(od instanceof FactoidDatum)) {
            return false;
        }
        FactoidDatum d = (FactoidDatum) od;
        if(!d.uuid.equals( _facDatum.uuid)) {
            return false;
        }

        //If this ObjBase hasn't been changed, just replace the datums
        if(!_facDatum._isChanged) {
            d._isChanged = false;
            _facDatum = d;
            _datum = d;
            fireData( new RefreshEvent( this , RefreshEvent.Condition.UPDATE_ALL) );
            return true;
        }

        //Otherwise, do conflict resolution
            //Change these no matter what
            _facDatum._isChanged = false;
            _facDatum.dateCreated = d.dateCreated;
            _facDatum.lastModified = d.lastModified;

            //Resolve name
            if(ConflictResolution.resolve(d.name, _facDatum.name, d.name, "Name")) {
                _facDatum.name =  d.name;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.NAME_CHANGED) );
            } else {
                _facDatum._isChanged = true;
            }

            //Resolve author
            if(ConflictResolution.resolve(d._authorUUID, _facDatum._authorUUID, d.name, "Author")) {
                _facDatum._authorUUID = d._authorUUID;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.AUTHOR_CHANGED) );
            } else {
                _facDatum._isChanged = true;
            }

            //Resolve wikitext
            if(ConflictResolution.resolve(d._wikiUUID, _facDatum._wikiUUID, d.name, "WikiText")) {
                _facDatum._wikiUUID = d._wikiUUID;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.WIKITEXT_CHANGED) );
            } else {
                _facDatum._isChanged = true;
            }

            //Resolve Reference
            if(ConflictResolution.resolve(d._reference.getID(), _facDatum._reference.getID(), d.name, "Reference")) {
                _facDatum._reference = d._reference;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.REFERENCE_CHANGED) );
            } else {
                _facDatum._isChanged = true;
            }
        return true;
    }

    @Override
    public ObjType getType() {
        return ObjType.FACTOID;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            default:
                return false;
        }
    }

    /**
     * Relay for Note:Factoid XRef
     * @param aThis
     */
    void addFactoidRelay( Note aThis ) {
        _facDatum._noteLinks.add( aThis.getUUID() );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
    }

    /**
     * Relay for Note:Factoid XRef
     * @param aThis
     */
    void removeFactoidRelay( String uUID ) {
        _facDatum._noteLinks.remove( uUID );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FACTOID_TO_NOTE);
    }

    /* SETTERS
     * */
    /**
     *
     * @param value
     * @return
     */
    public boolean changeReference( String value ) {
        Reference aref = Reference.generate( value );
        if(aref==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.REFERENCE_CHANGED));
            return false;
        }
        _facDatum._reference = aref;

        setChanged(RefreshEvent.Condition.REFERENCE_CHANGED);
        return true;
    }

    /**
     * Change the text of the wikiText
     * @param text the new MediaWiki text string
     */
    public void changeWikitext( String text ) {
        WikiText wt = Collector.getWikiText( _facDatum._wikiUUID );
        wt.changeTextAsWiki( text );
        setChanged(RefreshEvent.Condition.WIKITEXT_CHANGED);
    }

    /**
     * Change the author of this factoid
     * @param newauthor the new author
     */
    public void changeAuthor(Person newauthor) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _facDatum._authorUUID, newauthor.getUUID() );
        _facDatum._authorUUID = newauthor.getUUID();
        setChanged(RefreshEvent.Condition.AUTHOR_CHANGED);
    }

    /* GETTERS
     * */

    /**
     * Get the author of this factoid
     * @return the Person author
     */
    public Person getAuthor() {
        return Collector.getPerson( _facDatum._authorUUID );
    }

    /**
     * Get the Reference object of this factoid
     * @return
     */
    public Reference getReference() {
        return _facDatum._reference;
    }

    /**
     * Get the wikiText
     * @return a WikiText
     */
    public WikiText getWikiText() {
        return Collector.getWikiText( _facDatum._wikiUUID );
    }

    /**Get the list of child factoids as Factoid objects from this Note
     *
     * @return all this notes Factoid objects as a HashSet
     */
    public HashSet<Note> getNotes() {
        HashSet<Note> out = new HashSet<Note>();
        for ( String f : _facDatum._noteLinks ) {
            Note n = Collector.getNote( f );
            if ( n != null ) {
                out.add( n );
            }
        }
        return out;
    }

    /**
     * Get the list of Note UUIDs from this Factoid as a HashSet
     * @return
     */
    public HashSet<String> getNoteLinks() {
        return _facDatum._noteLinks;
    }

    /*-----------------
    variables
    -----------------*/
    private FactoidDatum _facDatum;

    public static class FactoidDatum extends ObjBaseDatum {

        public String _wikiUUID;
        public String _authorUUID;
        transient public Reference _reference;
        public HashSet<String> _noteLinks;

        @Override
        public ObjType getType() {
            return ObjType.FACTOID;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        REFERENCE,
        AUTHOR,
        WIKITEXT,
        NOTES
    }
}
