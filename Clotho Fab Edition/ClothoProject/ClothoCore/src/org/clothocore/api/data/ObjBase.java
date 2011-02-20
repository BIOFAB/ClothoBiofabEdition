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
import org.clothocore.api.core.Collector;
import java.awt.Component;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import org.clothocore.api.core.Collator;
import org.clothocore.api.dnd.DragHandler;
import org.clothocore.api.dnd.ObjBaseDropTargetListener;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.core.Hub;
import org.clothocore.util.def.DefaultViewer;
import org.clothocore.util.xml.XmlGenerator;
import org.openide.util.Exceptions;

/**
 *
 * @author jcanderson
 * ObjBase is the base class for all assemblyLine objects like plates, samples,
 * parts, robots, etc.  Basically, it just assigns a UUID and creates universally-held
 * variables like myName.
 */
public abstract class ObjBase implements Serializable {

    protected ObjBase( ObjBaseDatum d ) {
        _datum = d;
        _pullTime = new Date();
        _datum._isChanged = false;
        _inDatabase = true;
        Collector.add( this );
    }

    /**Call this constructor if the object is new, ie, not in database
     *
     * @param name = the name of the object, its one required field beyond UUID
     */
    public ObjBase() {
        _pullTime = new Date();
        _inDatabase = false;
        _uuid = UUID.randomUUID().toString().replaceAll( "-", "" );
        Collector.add( this );
    }

    /**Call this constructor if the object is new, ie, not in database, and the
     * uuid needs to be explicitly stated
     *
     * @param name = the name of the object, its one required field beyond UUID
     */
    public ObjBase(String uuid) {
        _pullTime = new Date();
        _inDatabase = false;
        _uuid = uuid;
        Collector.add( this );
    }
    /**
     * Setting an object as transient means that Clotho will always think that
     * the object is already in the database and saved (even if it isn't)
     */
    public void setTransient() {
        _isTransient = true;
    }

    /**
     * Converts a String into a Hash string
     * @param uuidToHash
     * @return
     */
    public static String generateUUIDAsHash( String uuidToHash ) {

        byte[] defaultBytes = uuidToHash.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance( "MD5" );
            algorithm.reset();
            algorithm.update( defaultBytes );
            byte messageDigest[] = algorithm.digest();

            //Convert the MD5 hash to a String
            StringBuffer hexString = new StringBuffer();
            for ( int i = 0; i < messageDigest.length; i++ ) {
                hexString.append( Integer.toHexString( 0xFF & messageDigest[i] ) );
            }
            String out = "";
            out = hexString.toString();

            //Return it
            return out;
        } catch ( NoSuchAlgorithmException nsae ) {
        }
        return "";
    }

    public void serialize() {
        File serials = new File("serials");
        if(!serials.exists()) {
            serials.mkdir();
        }

        boolean tempchanged = _datum._isChanged;
        boolean tempindb = _inDatabase;
        _datum._isChanged = true;
        _inDatabase = false;

        try {
            // Serialize to a file
            String filename = "serials\\" + this.getUUID() + "." + this.getType();
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
            out.close();

            // Serialize to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
            out = new ObjectOutputStream(bos) ;
            out.writeObject(this);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        _datum._isChanged = tempchanged;
  //      _inDatabase = _inDatabase;
    }


    /* SETTERS
     * */
    /**
     * Saves this ObjBase object using the default connection. A default connection
     * must be open for this to succeed.
     * @return true if this ObjBase was saved, false otherwise
     */
    public boolean saveDefault() {
        return save( Hub.defaultConnection );
    }

    public Date getTimePulled() {
        return _pullTime;
    }

    public void addSaveHold( Thread hold ) {
        if ( _saveHold == null ) {
            _saveHold = new HashSet<Thread>();
        }
        _saveHold.add( hold );
    }

    /**
     * Saves this ObjBase object using the given ClothoConnection. conn must be
     * in a connected state for this to succeed.
     * @param conn
     * @return true if this ObjBase was saved, false otherwise
     */
    public synchronized boolean save( ClothoConnection conn ) {
        if ( _saveHold != null ) {
            for ( Thread ahold : _saveHold ) {
                try {
                    ahold.join();
                } catch ( InterruptedException ex ) {
                }
            }
        }

        if ( conn != null && conn.isConnected() ) {


            //If it isn't changed don't bother saving
            if ( !isChanged() ) {
                System.out.println( getType() + "  " + getUUID() + " didn't need resaving" );
                _pullTime = new Date();
                return true;
            }

            //If it's in the database, check its lastModified to see if a newer version is available
            if ( _inDatabase ) {
                Date lastmod = conn.getTimeModified( this );
                System.out.println( "lastmod " + lastmod + " for " + getName() );
                System.out.println( "_pullTime " + _pullTime );
                System.out.println( "\n\n####About to test dates\n" );
                if ( lastmod != null ) {
                    if ( lastmod.after( _pullTime ) ) {
                        int n = JOptionPane.showConfirmDialog( null, "A more recent version of your part is in the database.  What do you want to do?", "Overwrite Error", JOptionPane.OK_CANCEL_OPTION );
                        //NEED TO HAVE A VERSION DIALOG THAT REFLECTIONS ALL FIELDS FOR BOTH OBJECTS
                        //AND ALLOWS USER TO MANUALLY FIX THE DISCREPENCY
                    }
                }
            }

            //Set the lastModified date and dateCreated if it's a new object
            Date date = new Date();
            _datum.lastModified = date;
            if ( _datum.dateCreated == null ) {
                _datum.dateCreated = date;
            }

            //Save to the database
            if ( conn.save( this ) ) {
                _currentConnection = conn;
                _datum._isChanged = false;
                _inDatabase = true;
                System.out.println( "successful save of " + getType() + "  " + getUUID() );
                _pullTime = new Date();
                return true;
            }
            System.out.println( "Tried but failed to save for " + getType() + "  " + getUUID() );
            JOptionPane.showMessageDialog(null,
                "Clotho tried to save " + getName() + " but it failed.",
                "Save Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            System.out.println( "Not connected, save failed for " + getType() + "  " + getUUID() );
            return false;
        }
    }

    public void exportToXML() {
        new Thread() {

            @Override
            public void run() {
                //Load the data into a HashMap
                HashMap<String, HashMap<String, Object>> allObjects = new HashMap<String, HashMap<String, Object>>();
                HashMap<String, HashMap<String, Object>> result = generateXml( allObjects );

                //Do the Writer (takes it to the point of holding a string with the complete xml)
                XmlGenerator xg = new XmlGenerator( result );
                xg = null;
            }
        }.start();
    }

    protected HashMap<String, HashMap<String, Object>> generateXml( HashMap<String, HashMap<String, Object>> allObjects ) {
        return allObjects;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        return null;
    }

    /**Generic method for disposing of an item that contains no dependencies.  If the item
     * has dependencies, it retrieves those dependencies and throws them up in a jdialog menu
     * from which the user can select and press delete to call disposeEverywhere on those
     *
     * @return = true if item was successfully deleted, false otherwise
     */
    public boolean deleteFromDatabase() {
        ///TEMPORARILY REPRESSED///
        return false;
        /*
        if ( Hub.defaultConnection.delete( this ) ) {
            this._inDatabase = false;
            this.setTransient();
            return true;
        }
        return false;
         * */
    }

    public void launchDefaultViewer() {
        ViewerWrapper vw = Collator.getPreferredViewer( getType() );
        if(vw!=null) {
            vw.launch( this );
        } else {
            new DefaultViewer(this);
        }
    }

    /* PUTTERS
     * */
    public void changeName( final String newname ) {

        final String oldname = _datum.name;
        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _datum.name = oldname;
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _datum.name = newname;
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
            }
        };
        addUndo( undo, redo );

        redo.actionPerformed( null );
    }

    public void updateDate() {
        final Date newdate = new Date();
        final Date olddate = _datum.lastModified;

        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _datum.lastModified = olddate;
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _datum.lastModified = newdate;
                setChanged(RefreshEvent.Condition.LAST_DATE_CHANGED);
            }
        };
        addUndo( undo, redo );

        redo.actionPerformed( null );
    }

    /**
     * Call addSearchTag to add a searchTag to the ObjBase item.  It will only
     * add the searchTag if it isn't already associated with the part.
     * Reciprocol changes will be made in the Collector.
     * 
     * @param tag the searchTag
     * @return a boolean as to whether it added anything
     */
    public boolean addSearchTag( final String tag ) {
        final ObjBase obj = this;
        if ( Collector.associateSearchTag( tag, this ) ) {
            ActionListener undo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    Collector.removeSearchTagAssociation( tag, obj );
                    _datum.searchTags.remove( tag );
                    setChanged(RefreshEvent.Condition.SEARCHTAG_REMOVED);
                }
            };
            ActionListener redo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    Collector.associateSearchTag( tag, obj );
                    _datum.searchTags.add( tag );
                    setChanged(RefreshEvent.Condition.SEARCHTAG_ADDED);
                }
            };

            addUndo( undo, redo );
            _datum.searchTags.add( tag );
            setChanged(RefreshEvent.Condition.SEARCHTAG_ADDED);
            return true;
        }
        return false;
    }

    /**
     * Call removeSearchTag to remove a searchTag from the ObjBase item (if it is
     * associated with item).  Reciprocol changes are made in the Collector.
     *
     * @param tag the searchTag
     * @return a boolean as to whether it removed anything
     */
    public boolean removeSearchTag( final String tag ) {
        final ObjBase obj = this;
        Collector.removeSearchTagAssociation( tag, this );

        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.associateSearchTag( tag, obj );
                _datum.searchTags.add( tag );
                setChanged(RefreshEvent.Condition.SEARCHTAG_ADDED);
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.removeSearchTagAssociation( tag, obj );
                _datum.searchTags.remove( tag );
                setChanged(RefreshEvent.Condition.SEARCHTAG_REMOVED);
            }
        };

        addUndo( undo, redo );

        _datum.searchTags.remove( tag );
        setChanged(RefreshEvent.Condition.SEARCHTAG_REMOVED);
        return true;
    }

    /* GETTERS
     * */
    String getDateCreatedAsString() {
        if ( _datum.dateCreated == null ) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
        return sdf.format( _datum.dateCreated );
    }

    static Date getDateFromString( String uuid ) {
        String sdateCreated = uuid;
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
        Date dateCreated = null;
        try {
            dateCreated = sdf.parse( sdateCreated );
        } catch ( Exception ex ) {
        }
        return dateCreated;
    }

    String getLastModifiedAsString() {
        if ( _datum.lastModified == null ) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
        return sdf.format( _datum.lastModified );
    }

    @Override
    public String toString() {
        return getName();
    }

    public ObjLink getObjLink() {
        return new ObjLink(_datum.uuid, getType(), _datum.name);
    }

    public boolean isChanged() {
        return _datum._isChanged;
    }

    public boolean isTransient() {
        return _isTransient;
    }

    public boolean isInDatabase() {
        return _inDatabase;
    }

    public boolean isDeleted() {
        return this._isDeleted;
    }

    public String getName() {
        return _datum.name;
    }

    public String getUUID() {
        if ( _datum == null ) {
            return _uuid;
        }
        return _datum.uuid;
    }

    public Date getDate() {
        return _datum.lastModified;
    }

    public Date getDateCreated() {
        return _datum.dateCreated;
    }

    public Date getLastModified() {
        return _datum.lastModified;
    }

    public abstract ObjType getType();

    public ArrayList<String> getSearchTags() {
        return _datum.searchTags;
    }

    public boolean hasSearchTag( String tag ) {
        return _datum.searchTags.contains( tag );
    }

    /**This is supposed to use reflection to get all the fields
     *
     */
    public Hashtable<String, String> hashOutFields() {

        Hashtable<String, String> out = new Hashtable<String, String>();
        out.put( "_type", getType().toString() );
        out.put( "_myName", _datum.name );
        out.put( "_myUUID", _datum.uuid );
        if ( _datum.lastModified != null ) {
            out.put( "_lastModified", _datum.lastModified.toString() );
        }
        Field[] theFields = this.getClass().getDeclaredFields();
        System.out.println( " # fields  " + theFields.length );
        for ( Field f : theFields ) {
            String fieldName = f.getName();
            String fieldValue = "";
            try {
                if ( f.get( this ) != null ) {
                    fieldValue = f.get( this ).toString();
                }
                out.put( fieldName, fieldValue );
            } catch ( IllegalArgumentException ex ) {
                System.out.println( "IllegalArgumentException" );
            } catch ( IllegalAccessException ex ) {
                System.out.println( "IllegalAccessException" );
            }
        }
        return out;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * The following method is for abstract representation of objects by GUIs
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /**To automatically enable drag and drop as well as auto-data updating capabilities.  Use
     * this one when there is a single GUI component listening.
     *
     * @param observer the object that will call update
     * @param guiElements the specific GUI Component that listens to drags and drops
     */
    public void isRepresentedBy( final ObjBaseObserver observer, Component guiElement ) {
        //Enable data-changed listening
        isObservedBy( observer );

        //Enable drag and drop
        isDropListenedBy( guiElement );
        isDragListenedBy( guiElement );
    }

    /**To automatically enable drag and drop as well as auto-data updating capabilities.  Use
     * this call if you have multiple guiElements that you want drag and drop enabled to listen
     * to the object.
     *
     * @param observer the object that will call update
     * @param guiElements an array of Components
     */
    public void isRepresentedBy( final ObjBaseObserver observer, Component[] guiElements ) {
        //Enable data-changed listening
        isObservedBy( observer );

        //Enable drag and drop
        for ( Component c : guiElements ) {
            isDropListenedBy( c );
            isDragListenedBy(  c );
        }
    }

    /**To automatically enable only auto-data updating capabilities,
     * you would call from a objectRepresenter that implements objectRepresenter
     *                 theObject.isObservedBy(this);
     * @param guiElements
     */
    public void isObservedBy( final ObjBaseObserver observer ) {
        this.addDataListener( new Controller(observer) );
    }

    private class Controller implements DataListener {
        ////Variables:
        private WeakReference<ObjBaseObserver> _rep;

        private Controller(ObjBaseObserver representer) {
            _rep = new WeakReference<ObjBaseObserver>(representer);
        }

        @Override
        public void objectChanged(final RefreshEvent evt) {
            final ObjBaseObserver obr = _rep.get();
            if(obr!=null) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ObjBase callout = null;
                        if(!_isDeleted) {
                            callout = ObjBase.this;
                        }
                        obr.update(callout, evt );
                    }
                });
            }
        }
    }

    /**To automatically enable only drop capabilities,
     * you would call from a objectRepresenter that implements objectRepresenter
     *                 theObject.isDropListenedBy(this);
     * @param guiElements
     */
    public void isDropListenedBy(  Component guiElement ) {
        //DO WHATEVER ASPECTS OF DND CAN BE COMPARTMENTALIZED FROM HERE
        new ObjBaseDropTargetListener( guiElement, this );
    }

    /**To automatically enable only drag capabilities,
     * you would call from a objectRepresenter that implements objectRepresenter
     *                 theObject.canDNDBy(this);
     * @param guiElements
     */
    public void isDragListenedBy( Component guiElement ) {
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer( guiElement, DnDConstants.ACTION_COPY, new DragHandler( this ) );
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * The following methods are all for drag-and-drop events
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /**Abstract method addObject in general is for drag-and-drop events
     * Method gets called from the receiver of the drop
     *
     * @param dropObject is the object being dropped
     * @return is true if the drop is accepted for the receiver datumType
     */
    public abstract boolean addObject( ObjBase dropObject );

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * The following methods are all for data change event
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /**Any method that modifies the data in the object call setChanged(...)
     * which will set the changed boolean and trigger an event to any data
     * listeners that may want to refresh their view in response
     */
    protected void setChanged(  RefreshEvent.Condition cond ) {
        setChanged(true, cond);
    }

    private void setChanged( boolean isit, RefreshEvent.Condition cond ) {
        Collector.resetDelay();
        _datum._isChanged = isit;
        fireData( new RefreshEvent( this, cond ) );
    }

    public void addDataListener(DataListener listener) {
        DataListeners.add(DataListener.class, listener);
    }

    protected void fireData(RefreshEvent DataEvent) {
        Object[] listeners = DataListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == DataListener.class) {
                // pass the event to the listeners event dispatch method
                ((DataListener) listeners[i + 1]).objectChanged(DataEvent);
            }
        }
    }

    /**
     * The following methods are all involved in ObjBase undo/redo
     */
    /**
     * Called by object-changing methods, will log a change value
     * for a field of an ObjBase
     *
     * @param aField
     * @param avalue
     * @param newval
     */
    void addUndo( String aField, Object avalue, Object newval ) {
        try {
            Field myfield = _datum.getClass().getDeclaredField( aField );
            undoList.add( new undoLine( myfield, avalue, newval ) );
            redoList.clear();
        } catch ( NoSuchFieldException ex ) {
            System.out.println( "addundo NoSuchFieldException Typo in the field" );
        } catch ( SecurityException ex ) {
            System.out.println( "addundo SecurityException Typo in the field" );
        }
    }

    /**
     * Called by object-changing methods, use for HashSet<String> fields
     *
     * @param aField
     * @param newval
     */
    void addUndo( ActionListener undo, ActionListener redo ) {
        undoList.add( new undoLine( undo, redo ) );
        redoList.clear();
    }

    /**
     * Public accessible method for undoing the last change to an ObjBase.
     */
    public void undo() {
        if ( undoList.isEmpty() ) {
            return;
        }
        undoLine ul = undoList.get( undoList.size() - 1 );
        undoList.remove( ul );
        redoList.add( ul );

        if ( ul.isList ) {
            ul.undoAction.actionPerformed( null );
            setChanged(RefreshEvent.Condition.UNKNOWN);
        } else {
            try {
                ul.thefield.set( _datum, ul.oldvalue );
                setChanged(RefreshEvent.Condition.UNKNOWN);
            } catch ( IllegalArgumentException ex ) {
                System.out.println("called undo IllegalArgumentException");
                Exceptions.printStackTrace( ex );
            } catch ( IllegalAccessException ex ) {
                System.out.println("called undo IllegalAccessException");
                Exceptions.printStackTrace( ex );
            }
        }
    }

    /**
     * Public accessible method for redoing the last undo change to an ObjBase.
     */
    public void redo() {
        if ( redoList.isEmpty() ) {
            return;
        }
        undoLine ul = redoList.get( redoList.size() - 1 );
        undoList.add( ul );
        redoList.remove( ul );

        if ( ul.isList ) {
            ul.redoAction.actionPerformed( null );
            setChanged(RefreshEvent.Condition.UNKNOWN);
        } else {
            try {
                ul.thefield.set( _datum, ul.newvalue );
                setChanged(RefreshEvent.Condition.UNKNOWN);
            } catch ( IllegalArgumentException ex ) {
                Exceptions.printStackTrace( ex );
            } catch ( IllegalAccessException ex ) {
                Exceptions.printStackTrace( ex );
            }
        }
    }

    /**
     * An individual line of modification to an ObjBase
     */
    private class undoLine {
        //For an undo of a normal data field

        public Field thefield;
        public Object oldvalue;
        public Object newvalue;
        //for undo things that require an action (usually lists)
        public ActionListener undoAction;
        public ActionListener redoAction;
        public boolean isList = false;

        public undoLine( Field aField, Object oldVal, Object newVal ) {
            thefield = aField;
            oldvalue = oldVal;
            newvalue = newVal;
        }

        public undoLine( ActionListener undo, ActionListener redo ) {
            undoAction = undo;
            redoAction = redo;
            isList = true;
        }
    }

    /**
     * Update only changes fields in the object if the object is unchanged
     *
     * @return true if the object was updated
     */
    public boolean update() {
        if(!this._inDatabase) {
            _pullTime = new Date();
            System.out.println("Canceling update since object is new");
            return false;
        }

        //Get the current last modified date, and if that fails cancel the update
        Date lastmod = Hub.defaultConnection.getTimeModified(this);
        if ( lastmod == null ) {
            _pullTime = new Date();
            System.out.println("Canceling update since I couldn't pull the last modified time");
            return false;
        }

        //If the database hasn't been modified, don't bother updating
        if ( lastmod.before( _pullTime ) ) {
            _pullTime = new Date();
            System.out.println("Canceling update since copy is newer than the database copy");
            return false;
        }

        //If it got this far, try doing the update and notify observers
        _pullTime = new Date();
        return true;
    }

    /**
     * Revert will convert the ObjBase back the database version regardless
     * of whether there are local changes or not.  Use revert to remove any
     * changes made in local memory.
     *
     * @return true if the object was reverted
     */
    public boolean revert() {
        if(!this._inDatabase) {
            _pullTime = new Date();
            return false;
        }
        ObjBaseDatum d = Hub.defaultConnection.getDatum(getType(), getUUID());
        if(d==null) {
            return false;
        }

        _datum = d;  //THIS ISN'T HITTING THE RIGHT DATUM, THIS NEEDS TO BE IN EACH OBJBASE
        fireData( new RefreshEvent( this , RefreshEvent.Condition.UPDATE_ALL) );
        this._datum._isChanged = false;
        _pullTime = new Date();
        return true;
    }

    /**-----------------
    variables
    -----------------*/
    private EventListenerList DataListeners = new EventListenerList();
//The three universally-shared fields
    
    private ClothoConnection _currentConnection;
    private transient HashSet<Thread> _saveHold;
    private transient ArrayList<undoLine> undoList = new ArrayList<undoLine>();
    private transient ArrayList<undoLine> redoList = new ArrayList<undoLine>();
    protected ObjBaseDatum _datum;
    protected String _uuid;
    protected boolean _isTransient = false;  //If transient is true Clotho will ignore the object during saves
    protected boolean _inDatabase = false;
    protected boolean _isDeleted = false;
    protected transient Date _pullTime;


    public abstract static class ObjBaseDatum implements Serializable {

        public String name = "";
        public String uuid = "";
        public Date lastModified;        //The last time the object was stored to the database
        public Date dateCreated;         //datecreated DateCreated     Date it was created

        public ArrayList<String> searchTags = new ArrayList<String>();
        public boolean _isChanged = true;

        public abstract ObjType getType();
        public boolean revertRequested = false;
    }
}
