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
import org.clothocore.api.plugin.ClothoGrammar;
import org.clothocore.core.Hub;

/**
 * Grammars are not fully implemented in Clotho 2.0.
 * @author J. Christopher Anderson
 */
public class Grammar extends ObjBase implements ClothoFormat {

    public Grammar( GrammarDatum d ) {
        super( d );
        _gramDatum = d;
        loadFormat();
    }

    /**Constructor from raw data
     *
     * @param shortDesc
     * @param prefix
     * @param suffix
     * @param selfscar
     * @param longDesc
     */
    private Grammar( String shortDesc, String longDesc ) {
        super( );
        _gramDatum = new GrammarDatum();
        _datum = _gramDatum;
        _datum.name = shortDesc;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _gramDatum.uuid = _uuid;
        _gramDatum._shortDescription = shortDesc;
        _gramDatum._longDescription = longDesc;
        loadFormat();
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
        datahash.put( "uuid", getUUID() );
        datahash.put( "shortDescription", this.getShortDescription() );

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
     * @return
     */
    private boolean loadFormat() {
        // TODO: replace with real code
//        try {
//            //Open the jar and load all classes
//            String jarLocation = "plugins/Grammars/" + _myName + ".jar";
//            pluginClassLoader.addFile( jarLocation );
//
//            //Retrieve the main class of the format
//            String className = "grammar." + _myName;
//            Class ClothoGrammar = Class.forName( className );
//            _interface = (ClothoGrammar) ClothoGrammar.getConstructor().newInstance();
//
//            //If it hasn't failed yet, confirm that it worked
//            return true;
//
//        } catch (InstantiationException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (IllegalAccessException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (IllegalArgumentException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (InvocationTargetException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (NoSuchMethodException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (SecurityException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (ClassNotFoundException ex) {
//            Exceptions.printStackTrace( ex );
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
        JOptionPane.showMessageDialog( null, "Grammar plugin loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
        return false;

    }

    public String getShortDescription() {
        return _gramDatum._shortDescription;
    }

    public String getPluginDownloadLink() {
        return _gramDatum._pluginDownloadLink;
    }

    public String getPluginHelpLink() {
        return _gramDatum._pluginHelpLink;
    }

    public String getPluginId() {
        return _gramDatum._pluginId;
    }

    /*The following methods all relay format interface methods to the ClothoGrammar
    interface plugin registered for this format*/
    @Override
    public void init() {
    }

    @Override
    public boolean checkPart( Part p ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean checkVector( Vector v ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean checkComposite( ArrayList<Part> composition, Object additionalRequirements ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean checkPlasmid( Part p, Vector v, Object additionalRequirements ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public NucSeq generateCompositeSequence( ArrayList<Part> composition, Object additionalRequirements ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public NucSeq generatePlasmidSequence( Plasmid p ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public NucSeq generateSequencingRegion( Plasmid p ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public static Grammar retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.GRAMMAR );
        cq.contains( Grammar.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Grammar p = (Grammar) l.get( 0 );
        return p;
    }

    /*-----------------
    variables
    -----------------*/
    private GrammarDatum _gramDatum;
    transient public ClothoGrammar _interface;

    public static class GrammarDatum extends ObjBaseDatum {

        public String _shortDescription = "";
        public String _longDescription = "";
        public String _pluginDownloadLink;
        public String _pluginHelpLink;
        public String _pluginId;

        @Override
        public ObjType getType() {
            return ObjType.GRAMMAR;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        SHORT_DESCRIPTION,
        LONG_DESCRIPTION,
        DOWNLOAD_LINK,
        HELP_LINK,
        PLUGIN_ID, 
    }
}
