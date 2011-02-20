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

import java.text.SimpleDateFormat;
import org.clothocore.api.core.Collector;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;

/**
 * @author J. Christopher Anderson
 */
public class PlasmidSample extends Sample {

    /**
     * Constructor from database
     * @param uuid
     * @param name
     * @param dateCreated
     * @param lastModified
     * @param dateLastUsed
     * @param volume
     * @param concentration
     * @param quality
     * @param idPerson
     * @param idContainer
     * @param idPlasmid
     */
    public PlasmidSample( PlasmidSampleDatum d ) {
        super( d );
        _pSampleDatum = d;

//        this._plasmidUUID = idPlasmid;
//        this._sourceStrainUUID = idStrain;
    }

    /**
     * Private Constructor for a new Sample generated from scratch, use factory method generatePlasmidSample
     * @param myplasmid the Plasmid ("DNA") within the Sample
     * @param mycell the Strain from which the Plasmid is derived
     * @param mycontainer the Container the Sample is being put in
     * @param myvolume how many uL of liquid are in the new Sample
     * @param author who is creating the Sample
     */
    private PlasmidSample( Plasmid myplasmid, Strain mycell, double myvolume, Person author ) {
        super( myplasmid.getName(), myvolume, author );
        _pSampleDatum._plasmidUUID = myplasmid.getUUID();

        if ( mycell != null ) {
            _pSampleDatum._sourceStrainUUID = mycell.getUUID();
        }
    }

    /**
     * Factory method for generating new PlasmidSample
     * @param myplasmid
     * @param mycell
     * @param mycontainer
     * @param myvolume
     * @param author
     * @return
     */
    public static PlasmidSample generatePlasmidSample( Plasmid myplasmid, Strain mycell, Container mycontainer, double myvolume, Person author ) {
        System.out.println("generating a plasmidsample");
        if(myplasmid==null || mycell==null || mycontainer==null) {
            System.out.println("something was null in generatePlasmidSample");
            return null;
        }
        PlasmidSample ps = new PlasmidSample( myplasmid, mycell, myvolume, author );
        if ( ps.PUT_SampleToContainer( mycontainer ) ) {
            System.out.println("PUT_SampleToContainer ok, returning the plasmidsample");
            return ps;
        }
        return null;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting plasmidSample save" );
        if ( !isChanged() ) {
            System.out.println( "plasmidSample didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _pSampleDatum._plasmidUUID ) ) {
            Plasmid plas = getPlasmid();
            if ( !plas.isInDatabase() ) {
                if ( !plas.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _pSampleDatum._sourceStrainUUID ) ) {
            Strain st = getSourceStrain();
            if ( !st.isInDatabase() ) {
                if ( !st.save( conn ) ) {
                    return false;
                }
            }
        }

        return super.save( conn );
    }

    /* SETTERS
     * */
    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        String sdateLastUsed = (String) objHash.get( "_lastUsed" );
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
        Date dateLastUsed = null;
        try {
            dateLastUsed = sdf.parse( sdateLastUsed );
        } catch ( Exception ex ) {
        }

        String svol = (String) objHash.get( "_volume" );
        double volume = Double.parseDouble( svol );
        String sconc = (String) objHash.get( "_concentration" );
        double concentration = Double.parseDouble( sconc );
        String squal = (String) objHash.get( "_quality" );
        Short quality = Short.parseShort( squal );
        String idPerson = (String) objHash.get( "_authorUUID" );
        String idContainer = (String) objHash.get( "_containerUUID" );
        String idPlasmid = (String) objHash.get( "_plasmidUUID" );
        String idStrain = (String) objHash.get( "_sourceStrainUUID" );

        PlasmidSampleDatum d = new PlasmidSampleDatum();

        d.uuid = uuid;
        d.name = name;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._lastUsed = dateLastUsed;
        d._volume = volume;
        d._concentration = concentration;
        d._quality = quality;
        d._authorUUID = idPerson;
        d._containerUUID = idContainer;
        d._plasmidUUID = idPlasmid;
        d._sourceStrainUUID = idStrain;

        return new PlasmidSample( d );
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
        datahash.put( "uuid", _pSampleDatum.uuid );
        datahash.put( "name", _pSampleDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_volume", Double.toString( _pSampleDatum._volume ) );
        datahash.put( "_concentration", Double.toString( _pSampleDatum._concentration ) );
        datahash.put( "_quality", Short.toString( _pSampleDatum._quality ) );
        datahash.put( "_authorUUID", _pSampleDatum._authorUUID );
        datahash.put( "_containerUUID", _pSampleDatum._containerUUID );
        datahash.put( "_plasmidUUID", _pSampleDatum._plasmidUUID );

        String lastUsed = null;
        if ( _pSampleDatum._lastUsed != null ) {
            SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
            lastUsed = sdf.format( _pSampleDatum._lastUsed );
        }
        datahash.put( "_lastUsed", lastUsed );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getPlasmid().generateXml( allObjects );
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getContainer().generateXml( allObjects );

        //Return the datahash
        return allObjects;
    }

    /* SETTERS
     * */

    public void changeSourceStrain( Strain newstrain ) {
        //NEED TO CHECK IF ITS LOCKED HERE
        if ( newstrain == null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.STRAIN_CHANGED));
            return;
        }
        _pSampleDatum._sourceStrainUUID = newstrain.getUUID();
        setChanged(RefreshEvent.Condition.STRAIN_CHANGED);
    }

    public void changePlasmid( Plasmid newplas ) {
        //DO A CHECK OF WHETHER THIS STRAIN IS LOCKED OR NOT
        if ( newplas == null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.PLASMID_CHANGED));
            return;
        }
        _pSampleDatum._plasmidUUID = newplas.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_CHANGED);
    }

    /* GETTERS
     * */

    @Override
    protected Sample duplicateTo( Container acon, Double vol ) {
        //private PlasmidSample(Plasmid myplasmid, Strain mycell, double myvolume, Person author) {
        PlasmidSample ps = generatePlasmidSample( getPlasmid(), getSourceStrain(), acon, vol, getAuthor() );
        return ps;
    }

    public Plasmid getPlasmid() {
        return Collector.getPlasmid( _pSampleDatum._plasmidUUID );
    }

    public Strain getSourceStrain() {
        return Collector.getStrain( _pSampleDatum._sourceStrainUUID );
    }

    @Override
    public sampleType getSampleType() {
        return sampleType.PLASMID_SAMPLE;
    }

    /*-----------------
        variables
    -----------------*/
    private PlasmidSampleDatum _pSampleDatum;

    public static class PlasmidSampleDatum extends SampleDatum {

        public String _plasmidUUID;
        public String _sourceStrainUUID;

        @Override
        public ObjType getType() {
            return ObjType.PLASMID;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
    }
}
