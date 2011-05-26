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
import org.clothocore.api.data.Attachment.AttachmentType;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;

/**
 *
 * @author J. Christopher Anderson
 */
public class SequenceRead extends SampleData {

    public SequenceRead( SequenceReadDatum d ) {
        super( d );
        _seqReadDatum = d;
    }

    /**
     * Public constructor for a sequence read.  You construct the SequenceRead data object and attach
     * it to the sample during submission.  When data is received, you can put in the abi trace using
     * addObject
     *
     * @param asam
     * @param primer
     * @param submitted
     */
    public SequenceRead( PlasmidSample asam, Oligo primer, Date submitted, Person author ) {
        super( asam.getName() + primer.getName(), author, new SequenceReadDatum() );
        _seqReadDatum = (SequenceReadDatum) _datum;

        _seqReadDatum.sampleLinks = new HashSet<String>();

        asam.addObject( this );

        _seqReadDatum._oligoUUID = primer.getUUID();
        _seqReadDatum._dateSubmitted = submitted;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting sample save" );
        if ( !isChanged() ) {
            System.out.println( "sample didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _seqReadDatum._abiUUID ) ) {
            Attachment att = getABITrace();
            if ( !att.isInDatabase() ) {
                if ( !att.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _seqReadDatum._resultWikiUUID ) ) {
            WikiText wt = getResultWiki();
            if ( !wt.isInDatabase() ) {
                if ( !wt.save( conn ) ) {
                    return false;
                }
            }
        }

        return super.save( conn );
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case ATTACHMENT:
                Attachment att = (Attachment) dropObject;
                if ( _seqReadDatum._abiUUID != null ) {
                    return false;
                }
                if ( att.getAttachmentType().equals( AttachmentType.ABI ) ) {
                    _seqReadDatum._abiUUID = att.getUUID();
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /* SETTERS
     * */
    public String generateBarcode() {
        String outString = "";
        for ( int i = 0; i < 8; i++ ) {
            double rand = Math.floor( 94.0 * Math.random() ) + 33;
            int randInt = (int) rand;
            char achar = (char) randInt;

            //Remove any disallowed characters
            if ( achar == ',' ) {
                achar = 'c';
            }
            if ( achar == '*' ) {
                achar = '7';
            }
            if ( achar == '.' ) {
                achar = 'o';
            }
            if ( achar == '"' ) {
                achar = '0';
            }
            if ( achar == '/' ) {
                achar = 's';
            }
            if ( achar == '\\' ) {
                achar = '3';
            }
            if ( achar == '[' ) {
                achar = 'L';
            }
            if ( achar == ']' ) {
                achar = '6';
            }
            if ( achar == ':' ) {
                achar = 'T';
            }
            if ( achar == ';' ) {
                achar = 'I';
            }
            if ( achar == '|' ) {
                achar = '5';
            }
            if ( achar == '=' ) {
                achar = 'q';
            }
            outString += achar;
        }
        _seqReadDatum._barcode = outString;
        return getBarcode();
    }

    public void changeDateSubmitted(Date adate) {
        _seqReadDatum._dateSubmitted = adate;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeOligo(Oligo newoligo) {
        if(newoligo!=null) {
            _seqReadDatum._oligoUUID = newoligo.getUUID();
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        } else {
            fireData(new RefreshEvent(this));
        }
    }

    /* PUTTERS
     * */

    /* GETTERS
     * */
    public Attachment getABITrace() {
        return Collector.getAttachment( _seqReadDatum._abiUUID );
    }

    public String getBarcode() {
        return _seqReadDatum._barcode;
    }

    public Oligo getOligo() {
        return Collector.getOligo( _seqReadDatum._oligoUUID );
    }

    public Date getDateSubmitted() {
        return _seqReadDatum._dateSubmitted;
    }

    public WikiText getResultWiki() {
        if(_seqReadDatum._resultWikiUUID==null) {
            _seqReadDatum._resultWikiUUID = new WikiText("").getUUID();
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        }
        return Collector.getWikiText(_seqReadDatum._resultWikiUUID);
    }

    @Override
    public dataType getDatatype() {
        return dataType.SEQUENCE_READ;
    }
    /*-----------------
    variables
    -----------------*/
    private SequenceReadDatum _seqReadDatum;

    public static class SequenceReadDatum extends SampleDataDatum {

        public String _abiUUID;            //AttachmentID
        public String _barcode = "";         //freestring1
        public Date _dateSubmitted;        //freestring2
        public String _oligoUUID;          //freestring3
        public String _resultWikiUUID;     //WikiText

        @Override
        public dataType getSampleDataType() {
            return dataType.SEQUENCE_READ;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        BARCODE,
    }
}
