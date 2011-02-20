/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.api.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author jcanderson
 */
public class Institution extends ObjBase {

    /**
     * Constructor from database
     * @param uuid
     * @param name
     * @param city
     * @param country
     * @param stateProvince
     * @param dateCreated
     * @param lastModified
     */
    public Institution( InstitutionDatum d ) {
        super( d );
        _instDatum = d;
    }

    /**
     * Constructor from raw data
     * @param name
     * @param city
     * @param state
     * @param country
     */
    public Institution( String name, String city, String state, String country ) {
        super();
        _instDatum = new InstitutionDatum();
        _datum = _instDatum;

        Institution prexistingSeq = retrieveByName( name );
        String newname = name;
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
        _instDatum.uuid = _uuid;
        _instDatum._city = city;
        _instDatum._state = state;
        _instDatum._country = country;
    }

    @Override
    public ObjType getType() {
        return ObjType.INSTITUTION;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String idInstitution = (String) objHash.get( "uuid" );
        String name = (String) objHash.get( "name" );
        String city = (String) objHash.get( "_city" );
        String country = (String) objHash.get( "_country" );
        String stateProvince = (String) objHash.get( "_state" );

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        InstitutionDatum d = new InstitutionDatum();

        d.uuid = idInstitution;
        d.name = name;
        d._city = city;
        d._country = country;
        d._state = stateProvince;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;

        return new Institution( d );
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
        datahash.put( "uuid", _instDatum.uuid );
        datahash.put( "name", _instDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_city", _instDatum._city );
        datahash.put( "_state", _instDatum._state );
        datahash.put( "_country", _instDatum._country );

        //Add the HashMap to the list and return
        allObjects.put( getUUID(), datahash );

        return allObjects;
    }

    /* SETTERS
     * */

    /* PUTTERS
     * */
    public void changeCity( String city ) {
        addUndo( "_city", _instDatum._city, city );
        _instDatum._city = city;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeState( String st ) {
        addUndo( "_state", _instDatum._state, st );
        _instDatum._state = st;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void changeCountry( String c ) {
        addUndo( "_country", _instDatum._country, c );
        _instDatum._country = c;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case LAB:
                Lab theLab = (Lab) dropObject;
                theLab.changeInstitition( this );
                return true;
            default:
                return false;
        }
    }

    /* GETTERS
     * */
    public String getCity() {
        return _instDatum._city;
    }

    public String getState() {
        return _instDatum._state;
    }

    public String getCountry() {
        return _instDatum._country;
    }

    public static Institution retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.INSTITUTION );
        cq.contains( Institution.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Institution p = (Institution) l.get( 0 );
        return p;
    }

    /*-----------------
    variables
    -----------------*/
    private InstitutionDatum _instDatum;


    public static class InstitutionDatum extends ObjBaseDatum {

        public String _city;		//Berkeley
        public String _state;		//CA
        public String _country;        //USA

        @Override
        public ObjType getType() {
            return ObjType.INSTITUTION;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        CITY,
        STATE,
        COUNTRY,
        LABS
    }
}
