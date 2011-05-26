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

/**
 *
 * @author J. Christopher Anderson
 */
public abstract class SampleData extends ObjBase {

    public SampleData( SampleDataDatum d ) {
        super( d );
        _sDataDatum = d;
    }

    public SampleData( String name, Person author, SampleDataDatum sdd) {
        super( );
        _datum = sdd;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        this._sDataDatum._authorUUID = author.getUUID();
        _sDataDatum.sampleLinks = new HashSet<String>();
    }

    public abstract dataType getDatatype();

    public Person getAuthor() {
        return Collector.getPerson( _sDataDatum._authorUUID );
    }

    /**
     * Relay for Sample:SampleData XRef
     * @param asample
     */
    void addSampleRelay( Sample asample ) {
        _sDataDatum.sampleLinks.add( asample.getUUID() );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public HashSet<String> getSampleLinks() {
        return _sDataDatum.sampleLinks;
    }

    public HashSet<Sample> getSamples() {
        System.out.println("Trying to retrieve samples of the sampleData" );
        HashSet<Sample> out = new HashSet<Sample>();
        for ( String s : _sDataDatum.sampleLinks ) {
            out.add( Collector.getSample( s ) );
            System.out.println("sampledata getSamples added " + Collector.getSample( s ).getName());
        }
        return out;
    }
/*
SR = Collector.getSequenceRead("89cbc78dbaa445709bd6f242a9133f64")
println(SR.getName())
println(SR.getSampleLinks().size())
for(uuid in SR.getSampleLinks()) {
    println uuid
}
 */
    @Override
    public ObjType getType() {
        return ObjType.SAMPLE_DATA;
    }
    /*-----------------
    variables
    -----------------*/
    protected SampleDataDatum _sDataDatum;

    public static abstract class SampleDataDatum extends ObjBaseDatum {

        public String _authorUUID;
        public HashSet<String> sampleLinks = new HashSet<String>();

        @Override
        public ObjType getType() {
            return ObjType.SAMPLE_DATA;
        }

        public abstract dataType getSampleDataType();
    }

    public enum dataType {
        SEQUENCE_READ, COMMENT, EXP_DATA
    };

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        DATA_TYPE,
        AUTHOR
    }
}
