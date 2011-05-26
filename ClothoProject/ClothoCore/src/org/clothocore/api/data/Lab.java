/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.api.data;

import java.net.MalformedURLException;
import java.net.URL;
import org.clothocore.api.core.Collector;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;
import org.clothocore.util.misc.BareBonesBrowserLaunch;


/**
 *
 * @author jcanderson
 */
public class Lab extends ObjBase {

    public Lab( LabDatum d ) {
        super( d );
        _labDatum = d;
    }

    public Lab( Institution inst, Person PI, String name, String department, String address ) {
        super();
        _labDatum = new LabDatum();
        _datum = _labDatum;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        Lab prexistingSeq = retrieveByName( name );
        String newname = name;
        while ( prexistingSeq != null ) {
            newname = JOptionPane.showInputDialog( "A lab named " + newname + " already exists, please give me a new name." );
            if(newname==null) {
                return;
            }
            prexistingSeq = retrieveByName( newname );
        }
        _datum.name = newname;

        _labDatum._department = department;
        _labDatum._address = address;
        _labDatum._institutionUUID = inst.getUUID();
        if ( PI != null ) {
            _labDatum._principalInvestigatorUUID = PI.getUUID();
        } else {
        }
    }

    @Override
    public ObjType getType() {
        return ObjType.LAB;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        String department = (String) objHash.get( "_department" );
        String website = (String) objHash.get( "_website" );
        String address = (String) objHash.get( "_address" );
        String piID = (String) objHash.get( "_principalInvestigatorUUID" );
        String idInstitution = (String) objHash.get( "_institutionUUID" );


        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        LabDatum d = new LabDatum();

        d.uuid = uuid;
        d.name = name;
        d._department = department;
        d._website = website;
        d._address = address;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._principalInvestigatorUUID = piID;
        d._institutionUUID = idInstitution;

        return new Lab( d );
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
        datahash.put( "uuid", _labDatum.uuid );
        datahash.put( "name", _labDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_principalInvestigatorUUID", _labDatum._principalInvestigatorUUID );
        datahash.put( "_institutionUUID", _labDatum._institutionUUID );
        datahash.put( "_department", _labDatum._department );
        datahash.put( "_website", _labDatum._website );
        datahash.put( "_address", _labDatum._address );

        allObjects = getInstitution().generateXml( allObjects );

        //Add the HashMap to the list and return
        allObjects.put( getUUID(), datahash );

        return allObjects;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting lab save" );
        if ( !isChanged() ) {
            System.out.println( "lab didn't require saving" );
            return true;
        }

        String tempPI = _labDatum._principalInvestigatorUUID;
        _labDatum._principalInvestigatorUUID =null;

        if ( Collector.isLocal( _labDatum._institutionUUID ) ) {
            Institution inst = getInstitution();
            if ( !inst.isInDatabase() ) {
                if ( !inst.save( conn ) ) {
                    return false;
                }
            }
        }

        if(!super.save( conn )) {
            return false;
        }
        _labDatum._principalInvestigatorUUID = tempPI;
        if ( Collector.isLocal( _labDatum._principalInvestigatorUUID ) ) {
            Person pi = getPI();
            if ( !pi.isInDatabase() ) {
                if ( !pi.save( conn ) ) {
                    return false;
                }
            }
        }

        return super.save( conn );
    }

    /* SETTERS
     * */
    public void setAddress( String addr ) {
        addUndo( "_address", _labDatum._address, addr );
        _labDatum._address = addr;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    public void setDepartment( String depart ) {
        addUndo( "_department", _labDatum._department, depart );
        _labDatum._department = depart;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }


    /* PUTTERS
     * */
    public void changeInstitition( Institution aThis ) {
        addUndo( "_institutionUUID", _labDatum._institutionUUID, aThis.getUUID() );
        _labDatum._institutionUUID = aThis.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case PERSON:
                //PUT THE PERSON IN this LAB, or change their affiliation
                return true;
            default:
                return false;
        }

    }

    public void changeWebsite( String site ) {
        URL url = null;
        while( url==null ) {
            try {
                url = new URL( site );
            } catch ( MalformedURLException ex ) {
                String inputValue = JOptionPane.showInputDialog( "You tried to put in the invalid web address " + site + ".\n\nIt needs to be in the form of http://www.google.com, try again:" );
                if(inputValue==null) {
                    site = _labDatum._website;
                    break;
                } else {
                    site = inputValue;
                }
            }
        }

        addUndo( "_website", _labDatum._website, site );
        _labDatum._website = site;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.WEBSITE_CHANGED);
    }

    public void changePI( Person pi ) {
        addUndo( "_principalInvestigatorUUID", _labDatum._principalInvestigatorUUID, pi.getUUID() );
        _labDatum._principalInvestigatorUUID = pi.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PI_CHANGED);
    }

    public void changeDepartment( String dept ) {
        addUndo( "_department", _labDatum._department, dept );
        _labDatum._department = dept;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.DEPARTMENT_CHANGED);
    }

    public void changeAddress( String address ) {
        addUndo( "_address", _labDatum._address, address );
        _labDatum._address = address;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.ADDRESS_CHANGED);
    }

    /* GETTERS
     * */
    public Institution getInstitution() {
        return Collector.getInstitution( _labDatum._institutionUUID );
    }

    public Person getPI() {
        return Collector.getPerson( _labDatum._principalInvestigatorUUID );
    }

    public String getDepartment() {
        return _labDatum._department;
    }

    public String getAddress() {
        return _labDatum._address;
    }

    public String getWebsite() {
        return _labDatum._website;
    }

    public static Lab retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.LAB );
        cq.contains( Lab.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Lab p = (Lab) l.get( 0 );
        return p;
    }

    public void visitWebsite() {
        if ( !_labDatum._address.equals( "" ) ) {
            BareBonesBrowserLaunch.openURL( _labDatum._website );
        }
    }
    /*-----------------
    variables
    -----------------*/
    private LabDatum _labDatum;

    public static class LabDatum extends ObjBaseDatum {

        public String _principalInvestigatorUUID;
        public String _institutionUUID;
        public String _department;
        public String _website;
        public String _address = "";
        public Set<String> _members = new HashSet<String>();

        @Override
        public ObjType getType() {
            return ObjType.LAB;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        DEPARTMENT,
        WEBSITE,
        ADDRESS,
        PI,
        INSTITUTION,
        PERSONS
    }
}
