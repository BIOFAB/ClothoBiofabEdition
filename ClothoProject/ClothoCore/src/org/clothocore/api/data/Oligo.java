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
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class Oligo extends ObjBase {

    public Oligo( OligoDatum d ) {
        super( d );
        _oliDatum = d;
    }

    public Oligo( String oligoName, String shortDescription, Person author, String sequence ) {
        super( );
        _oliDatum = new OligoDatum();
        _datum = _oliDatum;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _oliDatum.uuid = _uuid;

        Oligo prexistingSeq = retrieveByName( oligoName );
        String newname = oligoName;
        while ( prexistingSeq != null ) {
            newname = JOptionPane.showInputDialog( "An oligo named " + newname + " already exists, please give me a new name." );
            if(newname==null) {
                return;
            }
            prexistingSeq = retrieveByName( newname );
        }
        _datum.name = newname;
        
        _oliDatum._description = shortDescription;
        _oliDatum._authorUUID = author.getUUID();
        NucSeq seq = new NucSeq( sequence );
        seq.setLocked( true );
        _oliDatum._seqId = seq.getUUID();
    }

    @Override
    public ObjType getType() {
        return ObjType.OLIGO;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting oligo save" );
        if ( !isChanged() ) {
            System.out.println( "oligo didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _oliDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _oliDatum._seqId ) ) {
            NucSeq seq = getSeq();
            if ( !seq.save( conn ) ) {
                return false;
            }
        }

        return super.save( conn );
    }

    /* SETTERS
     * */
    @Override
    public boolean addObject( ObjBase dropObject ) {
        return false;
    }

    @Override
    public void changeName( final String newname ) {
        Oligo existing = Oligo.retrieveByName(newname);
        if(existing!=null) {
            if(!existing.getUUID().equals(this.getUUID())) {
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
                return;
            }
        }
        super.changeName( newname );
    }

    public void changeDescription( String text ) {
        addUndo( "_shortdescription", _oliDatum._description, text );
        _oliDatum._description = text;
        setChanged(RefreshEvent.Condition.DESCRIPTION_CHANGED);
    }

    public void changeSequence( final String newseq ) {
        if ( newseq == null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.DESCRIPTION_CHANGED));
            return;
        }

        final String oldseq = Collector.getNucSeq( _oliDatum._seqId ).toString();

        Collector.getNucSeq( _oliDatum._seqId ).APIchangeSeq( newseq );
        setChanged(RefreshEvent.Condition.SEQUENCE_CHANGED);

        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _oliDatum._seqId ).APIchangeSeq( oldseq );
                setChanged(RefreshEvent.Condition.SEQUENCE_CHANGED);
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _oliDatum._seqId ).APIchangeSeq( newseq );
                setChanged(RefreshEvent.Condition.SEQUENCE_CHANGED);
            }
        };
        addUndo( undo, redo );
    }

    public void changeAuthor( Person newauthor ) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _oliDatum._authorUUID, newauthor.getUUID() );
        _oliDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    /* GETTERS
     * */
    public NucSeq getSeq() {
        return Collector.getNucSeq( _oliDatum._seqId );
    }

    public Person getAuthor() {
        return Collector.getPerson( _oliDatum._authorUUID );
    }

    public String getDescription() {
        return _oliDatum._description;
    }

    public static Oligo retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.OLIGO );
        cq.contains( Oligo.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Oligo p = (Oligo) l.get( 0 );
        return p;
    }
    /*-----------------
    variables
    -----------------*/
    private OligoDatum _oliDatum;

    public static class OligoDatum extends ObjBaseDatum {

        public String _seqId;
        public String _description;
        public String _authorUUID;

        @Override
        public ObjType getType() {
            return ObjType.OLIGO;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        DESCRIPTION,
        AUTHOR,
        SEQUENCE
    }
}
