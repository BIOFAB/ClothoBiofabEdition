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
import org.clothocore.api.plugin.ClothoConnection;

/**
 * @author J. Christopher Anderson
 */
public class StrainSample extends Sample {

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
     * @param idStrain
     */
    public StrainSample( StrainSampleDatum d ) {
        super( d );
        _strSampleDatum = d;

    }

    /**
     * Private Constructor for a new Sample generated from scratch, use factory method generatePlasmidSample
     * @param myplasmid the plasmid ("DNA") within the Sample
     * @param mycell the Strain from which the plasmid is derived
     * @param mycontainer the Container the Sample is being put in
     * @param myvolume how many uL of liquid are in the new Sample
     * @param author who is creating the Sample
     */
    private StrainSample( Strain mystrain, double myvolume, Person author ) {
        super( null );
        _strSampleDatum = new StrainSampleDatum();
        _samDatum = _strSampleDatum;
        _datum = _strSampleDatum;
        _datum.uuid = _uuid;
        _datum.name = mystrain.getName() + " sample";

        _samDatum._volume = myvolume;
        _samDatum._authorUUID = author.getUUID();
        _samDatum.sampleDataLinks = new HashSet<String>();

        _strSampleDatum._strainUUID = mystrain.getUUID();

    }

    /**
     * Factory method for generating new plasmidSample
     * @param myplasmid
     * @param mycell
     * @param mycontainer
     * @param myvolume
     * @param author
     * @return
     */
    public static StrainSample generateOligoSample( Strain mystrain, Container mycontainer, double myvolume, Person author ) {
        StrainSample ps = new StrainSample( mystrain, myvolume, author );
        if ( ps.PUT_SampleToContainer( mycontainer ) ) {
            return ps;
        }
        return null;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting strainSample save" );
        if ( !isChanged() ) {
            System.out.println( "strainSample didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _strSampleDatum._strainUUID ) ) {
            Strain plas = getStrain();
            if ( !plas.isInDatabase() ) {
                if ( !plas.save( conn ) ) {
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

        StrainSampleDatum d = new StrainSampleDatum();

        d.uuid = uuid;
        d.name = name;

        d._volume = volume;
        d._authorUUID = idPerson;
        d.sampleDataLinks = new HashSet<String>();

        d._strainUUID = idPlasmid;


        return new StrainSample( d );
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
        datahash.put( "uuid", _strSampleDatum.uuid );
        datahash.put( "name", _strSampleDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_volume", Double.toString( _strSampleDatum._volume ) );
        datahash.put( "_concentration", Double.toString( _strSampleDatum._concentration ) );
        datahash.put( "_quality", Short.toString( _strSampleDatum._quality ) );
        datahash.put( "_authorUUID", _strSampleDatum._authorUUID );
        datahash.put( "_containerUUID", _strSampleDatum._containerUUID );
        datahash.put( "_plasmidUUID", _strSampleDatum._strainUUID );

        String lastUsed = null;
        if ( _strSampleDatum._lastUsed != null ) {
            SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
            lastUsed = sdf.format( _strSampleDatum._lastUsed );
        }
        datahash.put( "_lastUsed", lastUsed );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getStrain().generateXml( allObjects );
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getContainer().generateXml( allObjects );

        //Return the datahash
        return allObjects;
    }
    /* SETTERS
     * */

    /* GETTERS
     * */
    @Override
    protected Sample duplicateTo( Container acon, Double vol ) {
        //private plasmidSample(plasmid myplasmid, Strain mycell, double myvolume, Person author) {
        StrainSample ps = generateOligoSample( getStrain(), acon, vol, getAuthor() );
        return ps;
    }

    public Strain getStrain() {
        return Collector.getStrain( _strSampleDatum._strainUUID );
    }

    @Override
    public sampleType getSampleType() {
        return sampleType.CELL_SAMPLE;
    }

    /*-----------------
         variables
    -----------------*/
    private StrainSampleDatum _strSampleDatum;

    public static class StrainSampleDatum extends SampleDatum {

        public String _strainUUID;
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
    }
}
