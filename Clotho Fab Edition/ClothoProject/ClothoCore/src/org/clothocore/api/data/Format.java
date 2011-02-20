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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.api.plugin.ClothoFormat;
import org.clothocore.core.Hub;
import org.openide.util.Lookup.Item;
import org.openide.util.Lookup.Template;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author J. Christopher Anderson
 */
public class Format extends ObjBase implements ClothoFormat {

    public Format( FormatDatum d ) {
        super( d );
        _formDatum = d;
        loadFormat();
    }
    
    private Format(String pluginId, String name, String shortDescription, String pluginDownloadLink, String pluginHelpLink) {
        super(pluginId);
        _formDatum = new FormatDatum();
        _datum = _formDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = pluginId;
        _formDatum._shortDescription = shortDescription;
        _formDatum._pluginDownloadLink = pluginDownloadLink;
        _formDatum._pluginHelpLink = pluginHelpLink;
        _formDatum._pluginId = pluginId;
    }

    /**
     * Use this to install a new Format plugin into the database.  The example data below is for the RFC10 format.
     * You first need a module with a class that extends ClothoFormat.  The package and classname of that together
     * make up the pluginId which will also become the UUID for the Format object.
     *
     * @param pluginId    "org-clothocad-format-rfc10-connect"
     * @param name    "RFC10"
     * @param shortDescription  "The original Knight standard, this one for all but coding sequences"
     * @param pluginDownloadLink   "some url"
     * @param pluginHelpLink  "some url"
     * @return the new Format ObjBase
     */
    public static Format generateFormat(String pluginId, String name, String shortDescription, String pluginDownloadLink, String pluginHelpLink) {
        //See if there is already a plugin with this Id
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.FORMAT );
        cq.contains( Format.Fields.PLUGIN_ID, pluginId, false );
        List l = cq.getResults();
        if ( !l.isEmpty() ) {
            return (Format) l.get(0);
        }

        //If they put in incomplete data, return null
        if ( pluginId.length() == 0 || pluginId==null) {
            return null;
        }

        //Make sure the name supplied is unique
        Format prexistingSeq = retrieveByName( name );
        while ( prexistingSeq != null ) {
            name = JOptionPane.showInputDialog( "A Format named " + name + " already exists, please give me a new name." );
            if(name==null) {
                return null;
            }
            prexistingSeq = retrieveByName( name );
        }

        Format out= new Format(pluginId, name, shortDescription, pluginDownloadLink, pluginHelpLink);
        try {
            if(out.loadFormat()) {
                return out;
            }
        } catch(Exception e) {
        }
        out.setTransient();
        return null;
    }

    @Override
    public ObjType getType() {
        return ObjType.FORMAT;
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
        datahash.put( "uuid", _formDatum.uuid );
        datahash.put( "name", _formDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_shortDescription", _formDatum._shortDescription );
        datahash.put( "_pluginDownloadLink", _formDatum._pluginDownloadLink );
        datahash.put( "_pluginHelpLink", _formDatum._pluginHelpLink );
        datahash.put( "_pluginId", _formDatum._pluginId );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Return the datahash
        return allObjects;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        return false;
    }

    /* SETTERS
     * */

    /**Loads up the class associated with this format, returns true if it worked, otherwise false
     *
     * @return true if the ClothoFormat is properly loaded
     */
    private boolean loadFormat() {
        if(_interface == null) {
            try {
                System.out.println("Loading: " + _formDatum._pluginId);
                Template<? extends ClothoFormat> t = new Template<ClothoFormat>( ClothoFormat.class, "plugins/formats/" + _formDatum._pluginId, null );
                Item<? extends ClothoFormat> result = Lookups.forPath( "plugins/formats" ).lookupItem( t );
                _interface = result.getInstance();
                return true;
            } catch ( Exception e ) {
                e.printStackTrace();
                JOptionPane.showMessageDialog( null, "Format plugin loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
                return false;
            }
        }
        return true;
    }

    /*The following methods all relay format interface methods to the ClothoFormat
    interface plugin registered for this format*/

    @Override
    public boolean checkPlasmid( Part p, Vector v, Object additionalRequirements ) {
        if ( _interface == null ) {
            return false;
        }
        return _interface.checkPlasmid( p, v, additionalRequirements );
    }

    @Override
    public boolean checkPart( Part p ) {
        if ( _interface == null ) {
            return false;
        }
        return _interface.checkPart( p );
    }

    @Override
    public boolean checkVector( Vector v ) {
        if ( _interface == null ) {
            return false;
        }
        return _interface.checkVector( v );
    }

    @Override
    public boolean checkComposite( ArrayList<Part> composition, Object additionalRequirements ) {
        if ( _interface == null ) {
            return false;
        }
        return _interface.checkComposite( composition, additionalRequirements );
    }

    @Override
    public NucSeq generateCompositeSequence( ArrayList<Part> composition, Object additionalRequirements ) {
        if ( _interface == null ) {
            return null;
        }
        return _interface.generateCompositeSequence( composition, additionalRequirements );
    }

    @Override
    public NucSeq generatePlasmidSequence( Plasmid p ) {
        if ( _interface == null ) {
            return null;
        }
        return _interface.generatePlasmidSequence( p );
    }

    @Override
    public NucSeq generateSequencingRegion( Plasmid p ) {
        if ( _interface == null ) {
            return null;
        }
        return _interface.generateSequencingRegion( p );
    }

    @Override
    public void init() {
    }

    @Override
    public void changeName(String str) {
    }

    /* GETTERS
     * */

    public String getShortDescription() {
        return _formDatum._shortDescription;
    }

    public String getPluginDownloadLink() {
        return _formDatum._pluginDownloadLink;
    }

    public String getPluginHelpLink() {
        return _formDatum._pluginHelpLink;
    }

    public String getPluginId() {
        return _formDatum._pluginId;
    }

    public static Format retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.FORMAT );
        cq.contains( Format.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Format p = (Format) l.get( 0 );
        return p;
    }
    /*-----------------
    variables
    -----------------*/
    private FormatDatum _formDatum;
    transient public ClothoFormat _interface;

    public static class FormatDatum extends ObjBaseDatum {
        public String _shortDescription = "";
        public String _longDescription = "";
        public String _pluginDownloadLink;
        public String _pluginHelpLink;
        public String _pluginId;

        @Override
        public ObjType getType() {
            return ObjType.FORMAT;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        SHORT_DESCRIPTION,
        DOWNLOAD_LINK,
        HELP_LINK,
        PLUGIN_ID,
        VECTORS,
        PARTS,
        PLASMIDS
    }
}
