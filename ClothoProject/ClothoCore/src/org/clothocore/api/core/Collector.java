/*
Copyright (c) 2010 The Regents of the University of California.
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
package org.clothocore.api.core;

import org.clothocore.api.core.wrapper.ConnectionWrapper;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.clothocore.api.data.*;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.core.Hub;
import org.openide.modules.ModuleInstall;

/**
 * Collector is static for the run of clotho.  It manages the in-memory objects that are created
 * by plugins and makes sure things are disposed of properly to avoid accumulation.
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public class Collector {

    public static void initiate() {
        System.out.println("<<<<<<< Initiating Collector >>>>>>>>");

        //The printing here is just for debugging
        /*
         * Used for debugging...
        ArrayList<PluginWrapper> list = Collator.getAllPlugins();

        for (PluginWrapper pw : list) {
            ClothoHelp ch = pw.getClothoHelpObj();
            ClothoDependency cd = pw.getClothoDependencyObj();

            System.out.print("Dependency " + cd.getDependencies() + "\n");
            System.out.print("Help URL " + ch.getHelpUrl() + "\n");
            System.out.print("Help Desc " + ch.getHelpDescription() + "\n");
            System.out.print("Help Commands " + ch.getCommands() + "\n");
        }
        */

        _everything = new HashMap<String, ObjBase>();
        _uuidTypeHash = new HashMap<String, ObjType>();
        _typeUUIDHash = new HashMap<ObjType, ArrayList<String>>();
        _itemUUIDs = new ArrayList<String>();
        initiateSearchTags();
        _notInitiated = false;
        startTimer();
    }


        /**
     * Method called to safely shut down Clotho and all plugins with a
     * check that all threads are finished and all modified ObjBases are
     * saved to the database
     */
    public static void shutDown() {
        shutDown(null);
    }
    
    /**
     * Method called to safely shut down Clotho and all plugins with a
     * check that all threads are finished and all modified ObjBases are
     * saved to the database
     */
    public static void shutDown(ModuleInstall inst) {
        if (_notInitiated ) {
            finalExit(inst);
        }

        if(_rebootMode) {
            _rebootMode = false;
            return;
        }

        _timer.cancel();

        //Clear the attachment cache
        for(File afile : Attachment.cacheDir.listFiles()) {
            afile.delete();
        }

        ArrayList<ObjBase> changedObjects = new ArrayList<ObjBase>();
        System.out.println( "System shutting down" );
        for ( String uuid : _everything.keySet() ) {
            ObjBase obj = _everything.get( uuid );
            System.out.print( obj.getUUID() + "  " + obj.getName() );
            if ( obj.isChanged() && !obj.isTransient() ) {
                System.out.print( "  ************   isChanged" );
                if ( obj.isInDatabase() ) {
                    System.out.print( "  ************   and is in database" );
                }
                changedObjects.add( obj );
            }
            System.out.println();
        }

        if ( changedObjects.size() > 0 ) {
            Object[] params = new Object[2];
            JCheckBox[] checks = new JCheckBox[ changedObjects.size() ];
            Box abox = new Box(BoxLayout.Y_AXIS);
            JScrollPane scroller = new JScrollPane();
            scroller.setViewportView(abox);
            scroller.setPreferredSize(new Dimension(200,400));
            for ( int i = 0; i < changedObjects.size(); i++ ) {
                ObjBase obj = changedObjects.get( i );
                String displaytext = obj.toString();
                checks[i] = new JCheckBox( obj.getType().toString() + ": " + displaytext.substring( 0, Math.min( displaytext.length(), 35 ) ) );
                checks[i].setSelected( true );
                abox.add(checks[i]);
            }

            String message = "Clotho has some unsaved objects.  Do you want to save the selected items?.";
            params[0] = message;
            params[1] = scroller;
            int n = JOptionPane.showConfirmDialog( null, params, "Save objects before quiting", JOptionPane.OK_CANCEL_OPTION );
            if ( n == 0 ) {
                System.out.println( "You chose to save them" );
                for ( int i = 0; i < checks.length; i++ ) {
                    if ( checks[i].isSelected() ) {
                        changedObjects.get( i ).saveDefault();
                    }
                }
            }
        }
        finalExit(inst);
    }

    private static void finalExit(ModuleInstall inst) {
        if(inst==null) {
            System.exit( 0 );
        } else {
            inst.close();

        }
    }

    /**
     * I'm not sure this should really be part of the API.  It is convenient, but it ends up
     * bypassing any validation and could create duplicate part conflicts.  Well, it's here
     * now, but don't plan on putting it in plugins.
     *
     * @deprecated
     * @param uuid
     * @param type
     * @return
     */
    @Deprecated
    private static ObjBase getFromSerialized(String uuid, ObjType type) {
        ObjectInputStream ois = null;
        ObjBase out = null;
        try {
            FileInputStream fis = new FileInputStream("serials\\" + uuid + "." + type);
            ois = new ObjectInputStream(fis);
            out = (ObjBase) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(out !=null) {
            add(out);
            return out;
        }
        return null;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void populateDatabase() {
        File serials = new File("serials");
        if(!serials.exists()) {
            System.out.println("Serials directory didn't exist");
            return;
        }

        for(File afile : serials.listFiles()) {
            String[] array = afile.getName().split("\\.");
            try {
            ObjBase obj = getFromSerialized(array[0], ObjType.valueOf(array[1]));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save all objects in the current database to a list of files
     * (one file per ObjBase) in a directory called "serials"
     */
    public static void serializeDatabase() {
        for(ObjType type : ObjType.values()) {
            if(type.equals(ObjType.FLEX_FIELD)) {
                continue;
            }
            @SuppressWarnings (value="unchecked")
            ArrayList<ObjBase> listy = Collector.getAll(type);
            for(ObjBase obj : listy) {
                obj.serialize();
                _everything.remove(obj.getUUID());
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void importFromXML() {
        importFromXML( new File( "../newxml.xml" ) );
    }

    /**
     * Import a list of ObjBase from an XML file
     * @param file in Clotho XML format
     */
    public static void importFromXML( final File file ) {
        new Thread() {

            @Override
            public void run() {
                XMLImporter xi = new XMLImporter( file );
                Collection newcol = xi.getObjects();
                newcol.launchDefaultViewer();
                xi = null;
            }
        }.start();
    }

    /** Add takes an ObjBase item and puts it in the Collector
     *
     * @param item = the object you want to put in the Collector
     */
    public static void add( ObjBase item ) {
        resetDelay();
        String uuid = item.getUUID();
        ObjType type = item.getType();

        //If the collector hasn't seen the object yet, add it to all tables
        if ( !_everything.containsKey( uuid ) ) {
            System.out.println( "Added " + type.toString() + " " + uuid + " item to collector" );
            _everything.put( uuid, item );

            _uuidTypeHash.put( uuid, type );
            _itemUUIDs.add( uuid );

            //Put in the type to UUID hash:
            if ( _typeUUIDHash.containsKey( type ) ) {
                ArrayList<String> listy = _typeUUIDHash.get( type );
                if ( listy.contains( uuid ) ) {
                    return;
                } else {
                    listy.add( uuid );
                    _typeUUIDHash.put( type, listy );
                    return;
                }
            } else {
                ArrayList<String> listy = new ArrayList<String>();
                listy.add( uuid );
                _typeUUIDHash.put( type, listy );
            }
        } //If the collector already has the object, refresh the link
        else {
            ObjBase old = _everything.get( item.getUUID() );
            if ( old == null ) {
                _everything.remove( item.getUUID() );
                add( item );
            }
        }
    }

    /**
     * connectToDefault will automatically connect Clotho to the default database
     * or do nothing if no database is set as the preferred database
     */
    public synchronized static void connectToDefault() {
        if ( isConnected() ) {
            JOptionPane.showMessageDialog( null, "You are already connected to a database.  You'll need to restart Clotho if you wish to change connections.", "Already connected", JOptionPane.ERROR_MESSAGE );
            return;
        }

        String databaseID = Collator.getPreference( "DefaultConnectionID" );
        if ( databaseID == null ) {
            return;
        }
        if ( databaseID.equals( "" ) ) {
            return;
        }

        for ( ConnectionWrapper cw : Collator.getAllConnections() ) {
            if ( cw.getUUID().equals( databaseID ) ) {
                if ( cw.connect() ) {
                    Hub.defaultConnection = cw.getConnection();
                    Hub.defaultConnectionWrapper = cw;
                } else {
                    return;
                }
            }
        }
        if(isConnected()) {
            for(WeakReference<ActionListener> wr : connectionListeners) {
                ActionListener al = wr.get();
                if(al!=null) {
                    al.actionPerformed(null);
                }
            }
        }
    }

    public static void logConnectionListener(ActionListener listen) {
        connectionListeners.add(new WeakReference<ActionListener>(listen));
    }

    /* GETTERS
     * */

    /**
     * Call isConnected to determine whether Clotho is currently connected to a database
     * @return
     */
    public synchronized static boolean isConnected() {
        if ( Hub.defaultConnection == null ) {
            return false;
        }
        if ( Hub.defaultConnection.isConnected() ) {
            return true;
        }
        return false;
    }

    public static ClothoConnection getDefaultConnection() {
        return Hub.defaultConnection;
    }

    /**
     * Retrieve a wikiText object by its UUID
     * @param uuid
     * @return
     */
    public static WikiText getWikiText( String uuid ) {
        return (WikiText) get( ObjType.WIKITEXT, uuid );
    }

    /**
     * Retrieve an Attachment object by its UUID
     * @param uuid
     * @return
     */
    public static Attachment getAttachment( String uuid ) {
        return (Attachment) get( ObjType.ATTACHMENT, uuid );
    }

    /**
     * Retrieve a NucSeq object by its UUID
     * @param uuid
     * @return
     */
    public static NucSeq getNucSeq( String uuid ) {
        return (NucSeq) get( ObjType.NUCSEQ, uuid );
    }

    /**
     * Retrieve a Feature object by its UUID
     * @param uuid
     * @return
     */
    public static Feature getFeature( String uuid ) {
        return (Feature) get( ObjType.FEATURE, uuid );
    }

    /**
     * Retrieve a Comment object by its UUID
     * @param uuid
     * @return
     */
    public static Comment getComment( String uuid ) {
        Comment out = null;
        try {
            out = (Comment) get( ObjType.SAMPLE_DATA, uuid );
        } catch ( java.lang.ClassCastException e ) {
            JOptionPane.showMessageDialog( null, "Sorry, you tried to cast a sampleData object into a comment, but it isn't a comment", "Not a comment", JOptionPane.ERROR_MESSAGE );
        }
        return out;
    }

    /**
     * Retrieve a SequenceRead object by its UUID
     * @param uuid
     * @return
     */
    public static SequenceRead getSequenceRead( String uuid ) {
        return (SequenceRead) get( ObjType.SAMPLE_DATA, uuid );
    }

    /**
     * Retrieve a Part object by its UUID
     * @param uuid
     * @return
     */
    public static Part getPart( String uuid ) {
        return (Part) get( ObjType.PART, uuid );
    }

    /**
     * Retrieve a Annotation object by its UUID
     * @param uuid
     * @return
     */
    public static Annotation getAnnotation( String uuid ) {
        return (Annotation) get( ObjType.ANNOTATION, uuid );
    }

    /**
     * Retrieve a Vector object by its UUID
     * @param uuid
     * @return
     */
    public static Vector getVector( String uuid ) {
        return (Vector) get( ObjType.VECTOR, uuid );
    }

    /**
     * Retrieve a Plasmid object by its UUID
     * @param uuid
     * @return
     */
    public static Plasmid getPlasmid( String uuid ) {
        return (Plasmid) get( ObjType.PLASMID, uuid );
    }

    /**
     * Retrieve a Lab object by its UUID
     * @param uuid
     * @return
     */
    public static Lab getLab( String uuid ) {
        return (Lab) get( ObjType.LAB, uuid );
    }

    /**
     * Retrieve a Collection object by its UUID
     * @param uuid
     * @return
     */
    public static Collection getCollection( String uuid ) {
        return (Collection) get( ObjType.COLLECTION, uuid );
    }

    /**
     * Retrieve a Container object by its UUID
     * @param uuid
     * @return
     */
    public static Container getContainer( String uuid ) {
        return (Container) get( ObjType.CONTAINER, uuid );
    }

    /**
     * Retrieve a Factoid object by its UUID
     * @param uuid
     * @return
     */
    public static Factoid getFactoid( String uuid ) {
        return (Factoid) get( ObjType.FACTOID, uuid );
    }

    /**
     * Retrieve a Family object by its UUID
     * @param uuid
     * @return
     */
    public static Family getFamily( String uuid ) {
        return (Family) get( ObjType.FAMILY, uuid );
    }

    /**
     * Retrieve a Format object by its UUID
     * @param uuid
     * @return
     */
    public static Format getFormat( String uuid ) {
        return (Format) get( ObjType.FORMAT, uuid );
    }

    /**
     * Retrieve a Note object by its UUID
     * @param uuid
     * @return
     */
    public static Note getNote( String uuid ) {
        return (Note) get( ObjType.NOTE, uuid );
    }

    /**
     * Retrieve a Oligo object by its UUID
     * @param uuid
     * @return
     */
    public static Oligo getOligo( String uuid ) {
        return (Oligo) get( ObjType.OLIGO, uuid );
    }

    /**
     * Retrieve a Person object by its UUID
     * @param uuid
     * @return
     */
    public static Person getPerson( String uuid ) {
        return (Person) get( ObjType.PERSON, uuid );
    }

    /**
     * Retrieve a Plate object by its UUID
     * @param uuid
     * @return
     */
    public static Plate getPlate( String uuid ) {
        return (Plate) get( ObjType.PLATE, uuid );
    }

    /**
     * Retrieve a PlateType object by its UUID
     * @param uuid
     * @return
     */
    public static PlateType getPlateType( String uuid ) {
        return (PlateType) get( ObjType.PLATE_TYPE, uuid );
    }

    /**
     * Retrieve a Sample object by its UUID
     * @param uuid
     * @return
     */
    public static Sample getSample( String uuid ) {
        return (Sample) get( ObjType.SAMPLE, uuid );
    }

    /**
     * Retrieve a SampleData object by its UUID
     * @param uuid
     * @return
     */
    public static SampleData getSampleData( String uuid ) {
        return (SampleData) get( ObjType.SAMPLE_DATA, uuid );
    }

    /**
     * Retrieve a Strain object by its UUID
     * @param uuid
     * @return
     */
    public static Strain getStrain( String uuid ) {
        return (Strain) get( ObjType.STRAIN, uuid );
    }

    /**
     * Retrieve a Institution object by its UUID
     * @param uuid
     * @return
     */
    public static Institution getInstitution( String uuid ) {
        return (Institution) get( ObjType.INSTITUTION, uuid );
    }

    /**Retrieves the object either 1) from local memory 2) from the database or 3) returns an error
     *
     * @param uuid = UUID of the object your're requesting
     * @return = the object matching that UUID from wherever it can be found
     */
    public synchronized static ObjBase get( ObjType type, String uuid ) {
        //Retrieve from local memory if garbage Collection hasn't cleared it
        if ( _everything.containsKey( uuid ) ) {
            ObjBase item = _everything.get( uuid );
            if ( item != null ) {
                //System.out.println( "\tretrieving " + uuid + " from RAM" );
                return item;
            }
        }

        //Else, retrieve from database
        ObjBase outObject = Hub.defaultConnection.get( type, uuid );
        if ( outObject == null ) {
            return null;
        }
        //System.out.println( "\tretrieving " + uuid + " from database as" + outObject.getUUID() );
        return outObject;

    }

    /**Generic getter for all of whatever type.  This is a heavy method as it will
     * generate complete ObjBases for each object, and there are probably many for
     * some ObjTypes.  In general, it is better to use getAllLinksOf if the name, uuid,
     * and type are sufficient information for your task at hand.
     *
     * @param type the Type of ObjBase
     * @return an ArrayList of some type of ObjBase
     */
    public synchronized static ArrayList getAll( ObjType type ) {
        ArrayList<ObjBase> out = new ArrayList<ObjBase>();

        ArrayList<ObjLink> dbobjs = Hub.defaultConnection.getAllLinks( type );
        for ( ObjLink link : dbobjs ) {
            out.add( Collector.get( type, link.uuid ) );
        }

        ArrayList<String> listy = new ArrayList<String>();
        if ( _typeUUIDHash.containsKey( type ) ) {
            listy = _typeUUIDHash.get( type );
        } else {
            return out;
        }

        for ( String key : listy ) {
            Object obj = Collector.get( type, key );
            if ( !out.contains( obj ) ) {
                out.add( Collector.get( type, key ) );
            }
        }
        return out;
    }

    /**
     * For any object type requested, it returns a hashtable of the objects'
     * name field and uuid, or shortdescription if name is unavailable
     * @param type
     * @return
     */
    public synchronized static ArrayList<ObjLink> getAllLinksOf( ObjType type ) {
        resetDelay();
        return Hub.defaultConnection.getAllLinks( type );
    }

    /**
     * Gets the current user either from memory, file, or user input, or returns
     * null if none of the above worked
     *
     * @return a Person object of the current user
     */
    public static Person getCurrentUser() {
        //Try reading the user from a file
        if ( _currentUserUUID.equals( "" ) ) {
            System.out.println( "getCurrentUser retrieving user from preferences" );
            _currentUserUUID = Collator.getPreference( "CurrentUserUUID" );

            //If preference read failed, try asking the user to select an author
            if ( _currentUserUUID.equals( "" ) ) {
                selectCurrentUser();
            }

            //If the user didn't pick anybody, throw an error and return null
            if ( _currentUserUUID.equals( "" ) ) {
                JOptionPane.showMessageDialog( null, "I don't have a current user.", "No User", JOptionPane.ERROR_MESSAGE );
                return null;
            }
        }
        Person out = getPerson( _currentUserUUID );
        if(out==null) {
            selectCurrentUser();
            out = getPerson( _currentUserUUID );
        }

        return out;
    }

    public static boolean isCurrentUserSet() {
        System.out.println( "getCurrentUser checking user from preferences" );
        _currentUserUUID = Collator.getPreference( "CurrentUserUUID" );

        Person user = Collector.getPerson(_currentUserUUID);
        if(user==null) {
            System.out.println( " checking user from preferences returning false" );
            return false;
        }
        System.out.println( " checking user from preferences returning true" );
        return true;
    }

    /**
     * Use this method to prompt the user to select the current user from a list
     */
    public static void selectCurrentUser() {
        if ( !isConnected() ) {
            JOptionPane.showMessageDialog( null, "You aren't connected to a database", "No connection", JOptionPane.ERROR_MESSAGE );
            return;
        }

        //Grab all the Person from the database
        ArrayList<ObjLink> allPersons = getAllLinksOf( ObjType.PERSON );
        Object[] names = allPersons.toArray();

        //If there were no users found, cancel this
        if(names.length==0) {
            return;
        }
        
        //Throw up a joptiondialog to ask for the user
        Object selectedValue = JOptionPane.showInputDialog( null, "Choose one", "Choose current user", JOptionPane.INFORMATION_MESSAGE, null, names, names[0] );

        //Set the new user
        ObjLink link = (ObjLink) selectedValue;
        Person aperson = null;
        if ( link != null ) {
            aperson = Collector.getPerson( link.uuid );
        }

        if ( aperson == null ) {
            return;
        }

        setCurrentUser( aperson );
    }

    /**
     * Call getObjectsWithSearchTag to grab all ObjBase items that
     * are associated with the searchTag
     *
     * @param tag
     * @return
     */
    public static ArrayList<ObjBase> getObjectsWithSearchTag( String tag ) {
        ArrayList<ObjBase> out = new ArrayList<ObjBase>();
        HashMap<String, String> uuids = _searchTagRef.get( tag );
        for ( String s : uuids.keySet() ) {
            ObjType otype = ObjType.valueOf( uuids.get( s ) );
            String ouuid = s;
            System.out.println( "working on" );
            System.out.println( s );
            System.out.println( uuids.get( s ) );
            System.out.println( ouuid );
            ObjBase o = Collector.get( otype, ouuid );
            out.add( o );
        }
        return out;
    }

    /**
     * Returns all the searchTags in memory (from database or added recently)
     * @return
     */
    //JCA:  LOGICALLY THIS SHOULD RETURN A HASHSET, BUT COMBOBOXES WANT A LIST
    public static ArrayList<String> getAllSearchTags() {
        ArrayList<String> out = new ArrayList<String>();
        for ( String s : _searchTagRef.keySet() ) {
            out.add( s );
        }
        return out;
    }

    /**
     * Reciprocal call to ObjBase's add searchTag that adds the mapping back
     * to a list of ObjBase in local memory
     * (relay method)
     * @param tag the searchTag
     * @param o the ObjBase item being associated with the tag
     * @return true if it added a new line
     */
    public static boolean associateSearchTag( String tag, ObjBase o ) {
        String otype = o.getType().toString();
        String ouuid = o.getUUID();
        return associateSearchTag( tag, ouuid, otype );
    }

    /**
     * Reciprocal call to ObjBase's add searchTag that adds the mapping back
     * to a list of ObjBase in local memory
     * @param tag the searchTag
     * @param ouuid the UUID as a String of the object being associated with the searchTag
     * @param otype the objType of the searchTag entered as a String
     * @return true if it added a new line, false if the tag was already there
     */
    public static boolean associateSearchTag( String tag, String ouuid, String otype ) {
        if ( _searchTagRef.containsKey( tag ) ) {
            HashMap<String, String> duples = _searchTagRef.get( tag );
            //if the searchTag is already associated with this ObjBase
            if ( duples.containsKey( ouuid ) ) {
                return false;
            } //Otherwise, it's a new association so add it
            else {
                duples.put( ouuid, otype );
                _searchTagRef.put( tag, duples );
                return true;
            }
            //Otherwise put a new duples hash within the larger hash for the new tag
        } else {
            System.out.println( "doing new association of " + ouuid + "," + otype );
            HashMap<String, String> duples = new HashMap<String, String>();
            duples.put( ouuid, otype );
            _searchTagRef.put( tag, duples );
            return true;
        }
    }

    /**
     * Remove an associated searchTag/ObjBase combo from the collector (a recipricol call from ObjBase)
     * (relay method)
     * @param tag
     * @param o
     * @return
     */
    public static boolean removeSearchTagAssociation( String tag, ObjBase o ) {
        String otype = o.getType().toString();
        String ouuid = o.getUUID();
        return removeSearchTagAssociation( tag, ouuid, otype );
    }

    /**
     * Remove an associated searchTag/ObjBase combo from the collector (a recipricol call from ObjBase)
     * @param tag
     * @param ouuid
     * @param otype
     * @return
     */
    public static boolean removeSearchTagAssociation( String tag, String ouuid, String otype ) {
        if ( _searchTagRef.containsKey( tag ) ) {
            HashMap<String, String> duples = _searchTagRef.get( tag );
            //if the searchTag has the association, remove it
            if ( duples.containsKey( ouuid ) ) {
                duples.remove( ouuid );
                _searchTagRef.put( tag, duples );
                return true;
            }
        }
        return false;
    }

    private static void initiateSearchTags() {
        _searchTagRef = new HashMap<String, HashMap<String, String>>();
        //GO THROUGH SEARCHTAG XREF IN DATABASE AND GRAB EVERYTHING, CALL associateSearchTag(String tag, String ouuid, String otype)  FOR EACH
    }

    /**
     * Use this method to change the current user directly
     *
     * @param user a Person object of the current user
     */
    public static void setCurrentUser( Person aperson ) {
        if ( aperson != null ) {
            aperson.login();
            if ( aperson.isLoggedIn() ) {
                _currentUserUUID = aperson.getUUID();
                Collator.putPreference( "CurrentUserUUID", _currentUserUUID );
            }
;
        }
    }

    public static void copyToClipboard( ObjBase o ) {
        _clipBoardUUID = o.getUUID();
        _clipBoardType = o.getType();
        //Put some String on the System clipboard
        String str = "";
        switch ( _clipBoardType ) {
            case NUCSEQ:
                str = ((NucSeq) o).toString();
                break;
            case PART:
                str = ((Part) o).getSeq().toString();
                break;
            case FEATURE:
                str = ((Feature) o).getSeq().toString();
                break;
            case VECTOR:
                str = ((Vector) o).getSeq().toString();
                break;
            case PLASMID:
                str = ((Plasmid) o).getSeq().toString();
                break;
            case OLIGO:
                str = ((Oligo) o).getSeq().toString();
                break;
            case NOTE:
                //replace later with the wikitext of header field
                str = ((Note) o).getWikiText().getAsWikiText();
                break;
            case FACTOID:
                str = ((Factoid) o).getWikiText().getAsWikiText();
                break;
            case WIKITEXT:
                str = ((WikiText) o).getAsWikiText();
                break;
            case SAMPLE_DATA:
                SampleData sd = (SampleData) o;
                if ( sd.getDatatype().equals( SampleData.dataType.COMMENT ) ) {
                    str = ((Comment) o).getWikiText().getAsWikiText();
                } else {
                    str = o.getName();
                }

                break;
            default:
                str = o.getName();
                break;
        }
        StringSelection ss = new StringSelection( str );
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents( ss, null );
    }

    /**
     * Get the ObjBase currently copied to the clipboard
     * @return
     */
    public static ObjBase getFromClipboard() {
        if ( _clipBoardUUID == null || _clipBoardUUID.equals( "" ) ) {
            return null;
        }
        return Collector.get( _clipBoardType, _clipBoardUUID );
    }

    /**
     * Determine whether a given ObjBase is currently in the Collector
     * @param uuid the UUID of the ObjBase
     * @return true only if it is in the Collector
     */
    public static boolean isLocal( String uuid ) {
        if ( uuid == null ) {
            return false;
        }
        if ( _uuidTypeHash.containsKey( uuid ) ) {
            return true;
        }
        return false;
    }

    /**
     * Retrieve the list of objects currently in the collector
     * in the form of key = UUID; Object = ObjType
     * @return
     */
    public static Map<String, ObjType>  getLocalLinks() {
        return _uuidTypeHash;
    }

    /**
     * Resets the waiting-for-update delay of the Collector
     */
    public static void resetDelay() {
        _lastTriggered = new Date();
        _currentDelay = 1;
    }

    /**
     * The timer checks the database for updates to objects, and if it finds
     * a newer version automatically refreshes the data
     */
    private static void startTimer() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        _timer = new Timer();
        TimerTask updateTask = new TimerTask() {

            @Override
            public void run() {
                try {
                    if(!_autoRefreshMode) {
                        return;
                    }

                    if(Hub.defaultConnection==null) {
                        return;
                    }

                    //If Collector hasn't been used in a while, double the wait time
                    Date now = new Date();
                    long timedifference = now.getTime() - _lastTriggered.getTime();
                    //System.out.println("timedifference is " + timedifference + " and I need " + currentDelay);
                    _currentDelay = 2* _currentDelay;
                    if(timedifference < _currentDelay*3000) {
                        //System.out.println( "not updating yet" );
                        return;
                    }

                    if ( _everything == null || !Hub.defaultConnection.isConnected() ) {
                        System.exit( 0 );
                    }
                    for ( ObjBase o : _everything.values() ) {
                        if ( !o.isInDatabase() ) {
                            continue;
                        }
                        Date lastmod = Hub.defaultConnection.getTimeModified( o );
                        Date pulled = o.getTimePulled();
                        if ( lastmod == null ) {
                            continue;
                        }
                        if ( lastmod.after( pulled ) ) {
                            o.update();
                        }
                    }
                } catch(Exception e) {
                }
            }
        };
        _timer.scheduleAtFixedRate( updateTask, 20000, 20000 );
    }


     ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////


    private static Timer _timer;
    private static Date _lastTriggered = new Date();;
    private static long _currentDelay = 1;
    private static boolean _notInitiated = true;
    private static HashSet<WeakReference<ActionListener>> connectionListeners = new HashSet<WeakReference<ActionListener>>();
    private static String _currentUserUUID = "";
    private static String _clipBoardUUID;
    private static ObjType _clipBoardType = ObjType.ANNOTATION;
    private static Map<String, ObjBase> _everything;  //This is where all connections to things are held
    //NEED TO PUT IN ADDS FOR THE REST OF THESE
    private static Map<String, ObjType> _uuidTypeHash;
    private static Map<ObjType, ArrayList<String>> _typeUUIDHash;
    private static ArrayList<String> _itemUUIDs;
    //In this hash, the first String is the searchTag, the inner Hashtable is <uuid, objtype>
    private static HashMap<String, HashMap<String, String>> _searchTagRef;
    static boolean _rebootMode = false;

    public static boolean _autoRefreshMode = true;
    static {
        initiate();
    }
}
