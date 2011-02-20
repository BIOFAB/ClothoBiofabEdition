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

import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;

/**
 *
 * @author J. Christopher Anderson
 */
public class Comment extends SampleData {

    public Comment( CommentDatum d ) {
        super( d );
        _commentDatum = d;
    }

    /**
     * Construction of a new comment from raw data
     * @param name title for the comment
     * @param author Person author of the comment
     * @param blurb wiki text (as a MediaWiki String) for the comment
     */
    public Comment( String name, Person author, String blurb ) {
        super( name, author, new CommentDatum() );
        _commentDatum = (CommentDatum) _datum;
        putWikiText( blurb );
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting comment save" );
        if ( !isChanged() ) {
            System.out.println( "comment didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _commentDatum._wikiUUID ) ) {
            WikiText wt = getWikiText();
            if ( !wt.isInDatabase() ) {
                if ( !wt.save( conn ) ) {
                    return false;
                }
            }
        }

        return super.save( conn );
    }

    @Override
    public ObjType getType() {
        return ObjType.SAMPLE_DATA;
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

    /* SETTERS
     * */

    /**
     * Set the wikiText blurb for this comment.  If the wikiText hasn't
     * been created yet, this method will generate it.
     * @param wiki
     */
    public void putWikiText( String wiki ) {
        if(getWikiText()!=null) {
            getWikiText().changeTextAsWiki(wiki);
            fireData(new RefreshEvent(this, RefreshEvent.Condition.WIKITEXT_CHANGED));
            return;
        }
        _commentDatum._wikiUUID = new WikiText( wiki ).getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.WIKITEXT_CHANGED);
    }


    /* GETTERS
     * */

    /**
     * Get the wikitext for this comment
     * @return the wikiText blurb
     */
    public WikiText getWikiText() {
        return Collector.getWikiText( _commentDatum._wikiUUID );
    }

    /*-----------------
    variables
    -----------------*/
    private CommentDatum _commentDatum;

    @Override
    public dataType getDatatype() {
        return dataType.COMMENT;
    }

    public static class CommentDatum extends SampleDataDatum {
        public String _wikiUUID;

        @Override
        public dataType getSampleDataType() {
            return dataType.COMMENT;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
    }
}
