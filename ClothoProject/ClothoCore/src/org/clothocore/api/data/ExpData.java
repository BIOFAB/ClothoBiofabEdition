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
import org.clothocore.api.plugin.ClothoConnection;

/**
 * ExpData is for putting in hard data of a specific type that is something other
 * than a sequence read.  It is not fully implemented in Clotho 2.0.  The idea here
 * is that there is an attachment that holds the schema for the experiment, and then
 * the ExpData object must satisfy that schema.  Stay tuned for full implementation of
 * this in a later version of Clotho.
 *
 * @author J. Christopher Anderson
 */
public class ExpData extends SampleData {

    public ExpData( ExpDatum d ) {
        super( d );
        _expDatum = d;
    }


    /**
     * Public constructor for a sequence read.  You construct the sequenceRead data object and attach
     * it to the sample during submission.  When data is received, you can put in the abi trace using
     * addObject
     *
     * @param asam
     * @param primer
     * @param submitted
     */
    private ExpData(PlasmidSample asam, Attachment schema, Person author) {
        super(asam.getPlasmid().getName(), author, new ExpDatum());
        _expDatum = (ExpDatum) _datum;
        asam.addObject(this);
    }


    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public boolean save ( ClothoConnection conn ) {
        System.out.println( "============ Starting sample save" );
        if (!isChanged()) {
            System.out.println( "sample didn't require saving" );
            return true;
        }

        return super.save( conn );
    }

    @Override
    public  boolean addObject(ObjBase dropObject) {
        switch (dropObject.getType()) {
            default:
                return false;
        }
    }
    
    /* SETTERS
     * */

    /* GETTERS
     * */

    public String getRawData() {
        return _expDatum._xmlText;
    }

    public String getExperimentSet() {
        return _expDatum._experimentSet;
    }


    @Override
    public dataType getDatatype() {
        return dataType.EXP_DATA;
    }

/*-----------------
     variables
 -----------------*/

    private ExpDatum _expDatum;

    public static class ExpDatum extends SampleDataDatum {
        public String _xmlText;
        public String _experimentSet;
        public String _attachmentUUID; //This will hold the schema for the experiment

        @Override
        public dataType getSampleDataType() {
            return dataType.EXP_DATA;
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
