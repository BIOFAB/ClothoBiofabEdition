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

/**
 *  I don't think I have this right...it's right in the table, but not right here.
 *
 * a plugin developer should created the FlexField object in the database during installation of the plugin, so that should be
 * a component of the xml, probably, and generated during installation of the plugin, and then the plugin needs to get back the uuid
 * of the FlexField.  So, ideally it could tell the database to put in the uuid explicitly, or we could do it like hashes, where there
 * are in effect two uuid's in there.
 *
 * collector shouldn't know about the flexfields at all, they should work directly off datacore or something like that
 *
 * I don't think this is an objbase at all.  I suspect it is like wikiText...a primitive, but it still needs a datum associated with it
 *
 * When the plugin later uses the FlexField, it creates a flexFieldData which maps the FlexField to the data
 * @author J. Christopher Anderson
 */
public class FlexField extends ObjBase {

    public FlexField( FlexFieldDatum d ) {
        super( d );
        _flexDatum = d;
//        this._myName = name;
//        this._dateCreated = dateCreated;
//        this._lastModified = lastModified;
//        this._pluginId = pluginId;
    }

    public FlexField() {
        super(  );
        _flexDatum = new FlexFieldDatum();
        _datum = _flexDatum;
        _datum.name = "flexField before type";
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _flexDatum.uuid = _uuid;
        this.changeName( this.getClass().toString() );
    }

    public void setData( String data ) {
    }

    public String getData() {
        return "";
    }

    @Override
    public ObjType getType() {
        return ObjType.FLEX_FIELD;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**This is how you would declare a FlexField
     * You'd put in a bunch of these lines for each field,
     * then you can instantiate each one in your class, which would also extend FlexField
     * 
     * In the database, it would associate "barcodeReading" with the UUID of the plugin to guarantee uniqueness
     * In the XRef field, it would associate 
     */
    private class barcodeReading extends FlexField {

        public barcodeReading( String barcodeData ) {
        }

        public void putBarcodeData( String barcodeReader ) {
        }
        private String _barcodeReader;
    }
    barcodeReading thisBarcode = new barcodeReading( "a bunch of data" );


    /* SETTERS
     * */

    /* PUTTERS
     * */

    /* GETTERS
     * */
    public String getPluginId() {
        return _flexDatum._pluginId;
    }

    /*-----------------
    variables
    -----------------*/
    private FlexFieldDatum _flexDatum;

    public static class FlexFieldDatum extends ObjBaseDatum {

        public String _pluginId;

        @Override
        public ObjType getType() {
            return ObjType.FLEX_FIELD;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
    }
}
