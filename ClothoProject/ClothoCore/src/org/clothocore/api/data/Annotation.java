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

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.clothocore.api.core.Collector;
import org.clothocore.api.plugin.ClothoConnection;

/**
 * An Annotation is a single line of genbank essentially.  It maps a Feature
 * or just something a user has labeled as signficant to a NucSeq.
 */
public class Annotation extends ObjBase {

    public Annotation( AnnotationDatum d ) {
        super( d );
        _annoDatum = d;
    }

    /**
     * Constructor for an Annotation that is not a Feature, just a region of colored sequence
     *
     * @param name
     * @param nucseqid  the NucSeq that you're annotating
     * @param forColor
     * @param revColor
     * @param Start
     * @param End
     * @param user
     * @param plusstrandtrue
     * @param symbol  (can be null)
     */
    public Annotation( String name, NucSeq nucseq, Color forColor, Color revColor, int Start, int End, Person user, boolean plusstrandtrue, String symbol ) {
        super( );
        _annoDatum = new AnnotationDatum();
        _datum = _annoDatum;
        _datum.name = name;
        _datum.uuid = _uuid;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _annoDatum.uuid = _uuid;
        _annoDatum._nucseqID = nucseq.getUUID();
        _annoDatum._forColor = forColor.getRGB();
        _annoDatum._revColor = revColor.getRGB();
        _annoDatum._start = Start;
        _annoDatum._end = End;
        if ( user == null ) {
            _annoDatum._authorUUID = Collector.getCurrentUser().getUUID();
        } else {
            _annoDatum._authorUUID = user.getUUID();
        }
        _annoDatum._forStrand = plusstrandtrue;
        _symbol = symbol;
        nucseq.addAnnotationLink(_datum.uuid);
    }

    /**
     * Constructor for an Annotation that corresponds to a Feature object
     * @param afeature
     * @param nucseqid
     * @param forColor
     * @param revColor
     * @param Start
     * @param End
     * @param user
     * @param plusstrandtrue
     * @param symbol
     */
    public Annotation( Feature afeature, NucSeq nucseq, Color forColor, Color revColor, int Start, int End, Person user, boolean plusstrandtrue, String symbol ) {
        super(  );
        _annoDatum = new AnnotationDatum();
        _datum = _annoDatum;
        _datum.name = afeature.getName();
        _datum.uuid = _uuid;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _annoDatum._nucseqID = nucseq.getUUID();
        _annoDatum._featureID = afeature.getUUID();
        if ( forColor == null ) {
            forColor = afeature.getForwardColor();
        }
        if ( revColor == null ) {
            revColor = afeature.getReverseColor();
        }

        _annoDatum._forColor = forColor.getRGB();
        _annoDatum._revColor = revColor.getRGB();
        _annoDatum._start = Start;
        _annoDatum._end = End;
        if ( user == null ) {
            _annoDatum._authorUUID = Collector.getCurrentUser().getUUID();
        } else {
            _annoDatum._authorUUID = user.getUUID();
        }
        _annoDatum._forStrand = plusstrandtrue;
        _symbol = symbol;
        nucseq.addAnnotationLink(_datum.uuid);
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting annotation save" );

        if ( Collector.isLocal( _annoDatum._featureID ) ) {
            Feature f = getFeature();
            if ( !f.isInDatabase() ) {
                if ( !f.save( conn ) ) {
                    System.out.println( "Feature save failed for annotation" );
                    return false;
                }
            }
        }

        return super.save( conn );
    }

    @Override
    public ObjType getType() {
        return ObjType.ANNOTATION;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        String _authorUUID = (String) objHash.get( "_authorUUID" );
        String _featureID = (String) objHash.get( "_featureID" );

        String sforwardColor = (String) objHash.get( "_forColor" );
        int forwardColor = Integer.parseInt( sforwardColor );
        String sreverseColor = (String) objHash.get( "_revColor" );
        int reverseColor = Integer.parseInt( sreverseColor );
        String sstart = (String) objHash.get( "_start" );
        int start = Integer.parseInt( sstart );
        String send = (String) objHash.get( "_end" );
        int end = Integer.parseInt( send );
        String _symbol = (String) objHash.get( "_symbol" );
        String nucseqID = (String) objHash.get( "_nucseqID" );

        String sStrand = (String) objHash.get( "_forStrand" );
        boolean forStrand = Boolean.parseBoolean( sStrand );

        AnnotationDatum d = new AnnotationDatum();
        d._authorUUID = _authorUUID;
        d._featureID = _featureID;
        d._nucseqID = nucseqID;
        d._forColor = forwardColor;
        d._revColor = reverseColor;
        d._start = start;
        d._end = end;
        d._forStrand = forStrand;
        d.uuid = uuid;
        d.name = name;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;

        return new Annotation( d );
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
        datahash.put( "uuid", _annoDatum.uuid );
        datahash.put( "name", _annoDatum.name );

        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
        String createdate = sdf.format( _annoDatum.dateCreated );
        datahash.put( "_dateCreated", createdate );

        String lastdate = sdf.format( _annoDatum.lastModified );
        datahash.put( "_lastModified", lastdate );

        datahash.put( "_forStrand", _annoDatum._forStrand );
        datahash.put( "_authorUUID", _annoDatum._authorUUID );
        datahash.put( "_featureID", _annoDatum._featureID );
        datahash.put( "_forColor", Integer.toString( _annoDatum._forColor ) );
        datahash.put( "_revColor", Integer.toString( _annoDatum._revColor ) );
        datahash.put( "_start", Integer.toString( _annoDatum._start ) );
        datahash.put( "_end", Integer.toString( _annoDatum._end ) );
        datahash.put( "_symbol", _symbol );
        datahash.put( "_forStrand", _annoDatum._forStrand );
        datahash.put( "_nucseqID", _annoDatum._nucseqID );

        allObjects = getAuthor().generateXml( allObjects );
        if ( _annoDatum._featureID != null ) {
            allObjects = getFeature().generateXml( allObjects );
        }

        //Add the HashMap to the list and return
        allObjects.put( getUUID(), datahash );

        return allObjects;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        return false;
    }

    @Override
    public boolean deleteFromDatabase() {
        //Delete it from database
        return true;
    }

    /**
     * Reverse the orientation of the annotation (reverse complement
     * it and flip flop the start and end sites).  Called from NucSeq
     * when it's reverse complemented
     * @param nucseqLength
     */
    void invert( int nucseqLength ) {
        _annoDatum._forStrand = !_annoDatum._forStrand;
        int s = _annoDatum._start;
        _annoDatum._start = nucseqLength - _annoDatum._end;
        _annoDatum._end = nucseqLength - s;
    }

    /**
     * Get the approriate color for the annoation
     * @return either the forward or reverse color depending
     * on the orientation of the annotation
     */
    public Color getColor() {
        if ( _annoDatum._forStrand ) {
            return new Color( _annoDatum._forColor );
        }
        return new Color( _annoDatum._revColor );
    }

    /**
     * Get the start of the annotation on the target NucSeq
     * @return the start of the annotation
     */
    public int getStart() {
        return _annoDatum._start;
    }

    /**
     * Get the end of the annotation on the target NucSeq
     * @return the end of the annotation
     */
    public int getEnd() {
        return _annoDatum._end;
    }

    /**
     * Get the forward color as an integer code
     * @return an integer of the Color
     */
    public int getForwardColorAsInt() {
        return _annoDatum._forColor;
    }

    /**
     * Get the forward color as a Color object
     * @return the Forward color
     */
    public Color getForwardColor() {
        return new Color(_annoDatum._forColor);
    }
    /**
     * Get the reverse color as an integer code
     * @return an integer of the Color
     */
    public int getReverseColorAsInt() {
        return _annoDatum._revColor;
    }

    /**
     * Get the reverse color as a Color object
     * @return the Reverse color
     */
    public Color getReverseColor() {
        return new Color(_annoDatum._revColor);
    }

    /**
     * Get the symbol for this annotation
     * @return a String for a symbol to describe this annotation (an alternative
     * to using color)
     */
    public String getSymbol() {
        return _symbol;
    }

    /**
     * Get whether the annotion is on sense or antisense strand
     *
     * @return true if it's the sense strand
     */
    public boolean isForwardStrand() {
        return _annoDatum._forStrand;
    }
    /*-----------------
    variables
    -----------------*/
    public String _symbol;
    private AnnotationDatum _annoDatum;


    public static class AnnotationDatum extends ObjBase.ObjBaseDatum {

        public boolean _forStrand;
        public String _authorUUID;
        public String _featureID;
        public String _nucseqID;
        public int _forColor;
        public int _revColor;
        public int _start;
        public int _end;

        @Override
        public ObjType getType() {
            return ObjType.ANNOTATION;
        }
    }

    public Person getAuthor() {
        return Collector.getPerson( _annoDatum._authorUUID );
    }

    public Feature getFeature() {
        return Collector.getFeature( _annoDatum._featureID );
    }

    public NucSeq getSeq() {
        return Collector.getNucSeq( _annoDatum._nucseqID );
    }

    /******* FIELDS *******/
    public enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        FORWARD_COLOR,
        REVERSE_COLOR,
        START,
        END,
        SYMBOL,
        IS_FORWARD_STRAND,
        AUTHOR,
        FEATURE,
        TARGET_SEQUENCE,
        ANNOTATIONS
    }
}
