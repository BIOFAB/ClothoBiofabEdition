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
import java.util.HashSet;
import org.clothocore.api.core.Collector;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class Collection extends ObjBase {

    public Collection( CollectionDatum d ) {
        super( d );
        _colDatum = d;
        for ( String id : _colDatum.uuidTypeHash.keySet() ) {
            _colDatum.itemUUIDs.add( id );
        }
    }

    /**Constructor for collections from raw data
     *
     * @param collectionName = name of the Collection as a String
     * @param myauthor = author of Collection as a Person object
     */
    public Collection( String collectionName, String description, Person myauthor ) {
        super(  );
        _colDatum = new CollectionDatum();
        _datum = _colDatum;

        Collection prexistingSeq = retrieveByName( collectionName );
        String newname = collectionName;
        while ( prexistingSeq != null ) {
            newname = JOptionPane.showInputDialog( "An institution named " + newname + " already exists, please give me a new name." );
            if(newname==null) {
                return;
            }
            prexistingSeq = retrieveByName( newname );
        }
        _datum.name = newname;
        
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _colDatum.uuid = _uuid;
        _colDatum._authorUUID = myauthor.getUUID();
        _colDatum._description = description;
    }

    /**Constructor for a transient Collection.  Transient collections
     * aren't saved to the database ever, but they can be passed around
     * via the Collector (they have UUIDs).
     *
     * @param collectionName = name of the Collection as a String
     * @param myauthor = author of Collection as a Person object
     */
    public Collection() {
        super(  );
        _colDatum = new CollectionDatum();
        _datum = _colDatum;
        _datum.name = "Results";
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _colDatum.uuid = _uuid;
        _colDatum._description = "Transient Collection";
        if ( Collector.isConnected() ) {
            _colDatum._authorUUID = Collector.getCurrentUser().getUUID();
        }
        _isTransient = true;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting collection save of " + getUUID() );
        if ( !isChanged() ) {
            System.out.println( "Collection didn't require saving" );
            return true;
        }

        if(!isInDatabase()) {
            Map<String, ObjType> temphash = _colDatum.uuidTypeHash;
            _colDatum.uuidTypeHash = new HashMap<String, ObjType>();
            if(!super.save( conn )) {
                return false;
            }
            _colDatum._isChanged = true;
            _colDatum.uuidTypeHash = temphash;
        }

        for ( String s : _colDatum.uuidTypeHash.keySet() ) {
            //If the item hasn't been pulled into local memory, then don't bother saving
            if ( !Collector.isLocal( s ) ) {
                continue;
            }
            ObjBase att = Collector.get( _colDatum.uuidTypeHash.get( s ), s );

            //If it's already in the database ignore it
            if ( att.isInDatabase() ) {
                continue;
            }
            System.out.println( "collection about to save " + att.getName() );
            if ( !att.save( conn ) ) {
                return false;
            }
        }

        return super.save( conn );
    }

    @Override
    public ObjType getType() {
        return ObjType.COLLECTION;
    }

    /**
     * Get the description field of this collection
     * @return the description String
     */
    public String getDescription() {
        return _colDatum._description;
    }

    /**Abstract method addObject in general is for drag-and-drop events
     * Method gets called from the receiver of the drop
     *
     * @param dropObject is the object being dropped
     * @return is true if the drop is accepted for the receiver type
     */
    @Override
    public boolean addObject( ObjBase dropObject ) {
        if(dropObject==null) {
            return false;
        }
        if ( _colDatum.uuidTypeHash.containsKey( dropObject.getUUID() ) ) {
            return false;
        }

        AddAnyItem( dropObject.getType(), dropObject.getUUID() );
        System.out.println( "****Collection added an object " + dropObject.getName() );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.COLLECTION_ADD);
        return true;
    }

    /* SETTERS
     * */

    /**Remove an item from the Collection
     *
     * @param item = the item you want to remove
     */
    public boolean removeItem( ObjBase item ) {
        if ( !_colDatum.itemUUIDs.contains( item.getUUID() ) ) {
            return false;
        }

        final String uuid = item.getUUID();
        final ObjType type = item.getType();

        ActionListener undo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Put in the UUID to type hash:
                if ( !_colDatum.uuidTypeHash.containsKey( uuid ) ) {
                    _colDatum.uuidTypeHash.put( uuid, type );
                }

                //Put in the ordered Arraylist:
                if ( !_colDatum.itemUUIDs.contains( uuid ) ) {
                    _colDatum.itemUUIDs.add( uuid );
                }

                //Put in the type to UUID hash:
                if ( _colDatum.typeUUIDHash.containsKey( type ) ) {
                    HashSet<String> listy = _colDatum.typeUUIDHash.get( type );
                    if ( listy.contains( uuid ) ) {
                        return;
                    } else {
                        listy.add( uuid );
                        _colDatum.typeUUIDHash.put( type, listy );
                        return;
                    }
                } else {
                    HashSet<String> listy = new HashSet<String>();
                    listy.add( uuid );
                    _colDatum.typeUUIDHash.put( type, listy );
                }
            }
        };

        ActionListener redo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Remove from  the type to UUID hash:
                if ( _colDatum.typeUUIDHash.containsKey( type ) ) {

                    HashSet<String> listy = _colDatum.typeUUIDHash.get( type );
                    if ( listy.contains( uuid ) ) {
                        listy.remove( uuid );
                    }
                    _colDatum.typeUUIDHash.put( type, listy );
                }

                //Remove from  the UUID to type hash:
                _colDatum.uuidTypeHash.remove( uuid );

                //Remove from the ordered Arraylist:
                _colDatum.itemUUIDs.remove( uuid );
            }
        };

        addUndo(undo, redo);
        redo.actionPerformed(null);
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.COLLECTION_REMOVE);
        return true;
    }

    /* PUTTERS
     * */

    /**
     * Change the description field of the Collection
     * @param text the new description String
     */
    public void changeDescription( String text ) {
        addUndo( "_description", _colDatum._description, text );
        _colDatum._description = text;
        setChanged(RefreshEvent.Condition.DESCRIPTION_CHANGED);
    }

    /**
     * Change the author of the collection
     * @param newauthor the new author Person
     */
    public void changeAuthor(Person newauthor) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _colDatum._authorUUID, newauthor.getUUID() );
        _colDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    /* GETTERS
     * */

    /**
     * Get the Author of this Collection
     * @return the author Person
     */
    public Person getAuthor() {
        if ( _colDatum._authorUUID == null || _colDatum._authorUUID.equals( "" ) ) {
            return null;
        }
        return Collector.getPerson( _colDatum._authorUUID );
    }

    /**
     * Get all objects in the Collection
     * @return a List of ObjBase's in this Collection
     */
    public ArrayList<ObjBase> getAll() {
        ArrayList<ObjBase> out = new ArrayList<ObjBase>();
        for ( String key : _colDatum.itemUUIDs ) {
            ObjType type = _colDatum.uuidTypeHash.get( key );
            ObjBase obj = Collector.get( type, key );
            if ( obj == null ) {
                System.out.println( key + " is a dud link" );
                continue;
            }
            out.add( obj );
        }
        return out;
    }

    /**
     * Get all ObjBase in this Collection and any ObjBases in a Collection
     * within this Collection
     * @param type the type of ObjBase you want
     * @return a HashSet full of ObjBases
     */
    public HashSet<String> recursiveGetAllLinksOf( ObjType type ) {
        return recursiveRelay( type, new HashSet<String>() );
    }

    HashSet<String> recursiveRelay( ObjType type, HashSet<String> tested ) {
        tested.add( getUUID() );
        HashSet<String> out = new HashSet<String>();
        @SuppressWarnings (value="unchecked")
        ArrayList<Collection> allmycollections = (ArrayList<Collection>) getAll( ObjType.COLLECTION );
        for ( Collection childcoll : allmycollections ) {
            if ( tested.contains( childcoll.getUUID() ) ) {
                continue;
            }
            HashSet<String> itscontents = childcoll.recursiveRelay( type, tested );
            for ( String o : itscontents ) {
                out.add( o );
            }
        }

        for ( String s : getAllLinksOf( type ) ) {
            out.add( s );
        }

        return out;
    }

    /**
     * Get a list of UUIDs for ObjBase in this Collection of a given type
     * @param i the integer representation of the ObjType
     * @return a Set of ObjBases
     */
    public HashSet<String> getAllLinksOf( int i ) {
        return getAllLinksOf( ObjType.values()[i] );
    }

    /**
     * Get a list of UUIDs for ObjBase in this Collection of a given type
     * @param type the ObjType desired
     * @return a Set of ObjBases
     */
    public HashSet<String> getAllLinksOf( ObjType type ) {
        HashSet<String> uuidlist = _colDatum.typeUUIDHash.get( type );
        if ( uuidlist == null ) {
            uuidlist = new HashSet<String>();
        }
        return uuidlist;
    }

    /**
     * Get all ObjBases of a given type, where type is given
     * by the Integer value of the ObjType enum
     * @param i
     * @return
     */
    public ArrayList<? extends ObjBase> getAll( int i ) {
        return getAll( ObjType.values()[i] );
    }

    /**Generic getter for all of whatever type
     *
     * @param type the ObjType desired
     * @return a List of ObjBase of that type
     */
    @SuppressWarnings (value="unchecked")
    public ArrayList<? extends ObjBase> getAll( ObjType type ) {
        ArrayList out = new ArrayList();
        HashSet<String> listy = new HashSet<String>();
        if ( _colDatum.typeUUIDHash.containsKey( type ) ) {
            listy = _colDatum.typeUUIDHash.get( type );
        } else {
            return out;
        }

        for ( String key : listy ) {
            ObjBase obj =  Collector.get( type, key );
            if(obj!=null) {
                out.add( obj);
            }
        }
        return out;
    }

    /**
     * This one probably shouldn't exists
     * @deprecated
     * @param _myPart
     * @return
     */
    @Deprecated
    public ArrayList<Plasmid> getPlasmidsOf( Part _myPart ) {
        @SuppressWarnings (value="unchecked")
        ArrayList<Plasmid> allplas = (ArrayList<Plasmid>) getAll( ObjType.PLASMID );
        ArrayList<Plasmid> out = new ArrayList<Plasmid>();
        for ( Plasmid p : allplas ) {
            if ( p.getPart().getUUID().equals( _myPart.getUUID() ) ) {
                out.add( p );
            }
        }
        return out;
    }

    /**
     * This one probably shouldn't exists
     * @deprecated
     * @param _myPart
     * @return
     */
    @Deprecated
    public ArrayList<PlasmidSample> getSamplesOf( Plasmid _myPlasmid ) {
        @SuppressWarnings (value="unchecked")
        ArrayList<Sample> allsam = (ArrayList<Sample>) getAll( ObjType.SAMPLE );
        ArrayList<PlasmidSample> out = new ArrayList<PlasmidSample>();
        for ( Sample p : allsam ) {
            PlasmidSample ps = (PlasmidSample) p;
            System.out.println( "comparing " + ps.getPlasmid().getUUID() + "  " + _myPlasmid.getUUID() );

            if ( ps.getPlasmid().getUUID().equals( _myPlasmid.getUUID() ) ) {
                out.add( ps );
            }
        }
        return out;
    }

    private void AddAnyItem( ObjBase o ) {
        AddAnyItem( o.getType(), o.getUUID() );
    }

    private void AddAnyItem( final ObjType type, final String uuid ) {
        System.out.println("AddanyItem called");
        ActionListener redo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Put in the UUID to type hash:
                if ( !_colDatum.uuidTypeHash.containsKey( uuid ) ) {
                    _colDatum.uuidTypeHash.put( uuid, type );
                }

                //Put in the ordered Arraylist:
                if ( !_colDatum.itemUUIDs.contains( uuid ) ) {
                    _colDatum.itemUUIDs.add( uuid );
                }

                //Put in the type to UUID hash:
                if ( _colDatum.typeUUIDHash.containsKey( type ) ) {
                    HashSet<String> listy = _colDatum.typeUUIDHash.get( type );
                    if ( listy.contains( uuid ) ) {
                        return;
                    } else {
                        listy.add( uuid );
                        _colDatum.typeUUIDHash.put( type, listy );
                        return;
                    }
                } else {
                    HashSet<String> listy = new HashSet<String>();
                    listy.add( uuid );
                    _colDatum.typeUUIDHash.put( type, listy );
                }
            }
        };

        ActionListener undo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Remove from  the type to UUID hash:
                if ( _colDatum.typeUUIDHash.containsKey( type ) ) {

                    HashSet<String> listy = _colDatum.typeUUIDHash.get( type );
                    if ( listy.contains( uuid ) ) {
                        listy.remove( uuid );
                    }
                    _colDatum.typeUUIDHash.put( type, listy );
                }

                //Remove from  the UUID to type hash:
                _colDatum.uuidTypeHash.remove( uuid );

                //Remove from the ordered Arraylist:
                _colDatum.itemUUIDs.remove( uuid );
            }
        };

        addUndo(undo, redo);
        redo.actionPerformed(null);
    }

    public void removeAll() {
        _colDatum.uuidTypeHash.clear();
        _colDatum.typeUUIDHash.clear();
        _colDatum.itemUUIDs.clear();
    }

    /**
     * Returns the list of linked items
     * @return a Map of the  links
     */
    public Map<String, ObjType> getUUIDTypeHash() {
        return _colDatum.uuidTypeHash;
    }

    public static Collection retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.COLLECTION );
        cq.contains( Collection.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Collection p = (Collection) l.get( 0 );
        return p;
    }

    /**
     * This is probably temporary.  It removes any objects that were deleted
     * from the database but weren't removed from the table
     * @deprecated
     */
    @Deprecated
    public void cleanup() {
        HashSet<String> duds = new HashSet<String>();
        for ( String uuid : _colDatum.uuidTypeHash.keySet() ) {
            ObjBase obj = Collector.get( _colDatum.uuidTypeHash.get( uuid ), uuid );
            if ( obj == null ) {
                duds.add( uuid );
            }
        }
        for ( String uuid : duds ) {
            System.out.println( uuid + " was a dud" );
            ObjType type = _colDatum.uuidTypeHash.get( uuid );

            //Remove from  the type to UUID hash:
            if ( _colDatum.typeUUIDHash.containsKey( type ) ) {

                HashSet<String> listy = _colDatum.typeUUIDHash.get( type );
                if ( listy.contains( uuid ) ) {
                    listy.remove( uuid );
                }
                _colDatum.typeUUIDHash.put( type, listy );
            }

            //Remove from  the UUID to type hash:
            _colDatum.uuidTypeHash.remove( uuid );

            //Remove from the ordered Arraylist:
            _colDatum.itemUUIDs.remove( uuid );

            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        }
    }

    /*-----------------
    variables
    -----------------*/
    public static class CollectionDatum extends ObjBaseDatum {

        public Map<String, ObjType> uuidTypeHash = new HashMap<String, ObjType>();
        public Map<ObjType, HashSet<String>> typeUUIDHash = new EnumMap<ObjType, HashSet<String>>( ObjType.class );
        public ArrayList<String> itemUUIDs = new ArrayList<String>();
        public String _authorUUID;
        public String _description;

        @Override
        public ObjType getType() {
            return ObjType.COLLECTION;
        }
    }

    private CollectionDatum _colDatum;

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        DESCRIPTION,
        AUTHOR
    }
}
