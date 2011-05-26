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
public class OligoSample extends Sample {

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
    public OligoSample( OligoSampleDatum d ) {
        super( d );
        _oliDatum = d;
    }

    /**
     * Private Constructor for a new Sample generated from scratch, use factory method generatePlasmidSample
     * @param myplasmid the plasmid ("DNA") within the Sample
     * @param mycell the strain from which the plasmid is derived
     * @param mycontainer the Container the Sample is being put in
     * @param myvolume how many uL of liquid are in the new Sample
     * @param author who is creating the Sample
     */
    private OligoSample( Oligo myoligo, double myvolume, Person author ) {
        super( myoligo.getName(), myvolume, author );
        _oliDatum._oligoUUID = myoligo.getUUID();

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
    public static OligoSample generateOligoSample( Oligo myoligo, Container mycontainer, double myvolume, Person author ) {
        OligoSample ps = new OligoSample( myoligo, myvolume, author );
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
        System.out.println( "============ Starting plasmidSample save" );
        if ( !isChanged() ) {
            System.out.println( "plasmidSample didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _oliDatum._oligoUUID ) ) {
            Oligo plas = getOligo();
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

        OligoSampleDatum d = new OligoSampleDatum();

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
        d.sampleDataLinks = new HashSet<String>();

        return new OligoSample( d );
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
        datahash.put( "uuid", _oliDatum.uuid );
        datahash.put( "name", _oliDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_volume", Double.toString( _oliDatum._volume ) );
        datahash.put( "_concentration", Double.toString( _oliDatum._concentration ) );
        datahash.put( "_quality", Short.toString( _oliDatum._quality ) );
        datahash.put( "_authorUUID", _oliDatum._authorUUID );
        datahash.put( "_containerUUID", _oliDatum._containerUUID );
        datahash.put( "_plasmidUUID", _oliDatum._oligoUUID );

        String lastUsed = null;
        if ( _oliDatum._lastUsed != null ) {
            SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
            lastUsed = sdf.format( _oliDatum._lastUsed );
        }
        datahash.put( "_lastUsed", lastUsed );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getOligo().generateXml( allObjects );
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getContainer().generateXml( allObjects );

        //Return the datahash
        return allObjects;
    }

    /* GETTERS
     */

    @Override
    protected Sample duplicateTo( Container acon, Double vol ) {
        //private plasmidSample(plasmid myplasmid, strain mycell, double myvolume, Person author) {
        OligoSample ps = generateOligoSample( getOligo(), acon, vol, getAuthor() );
        return ps;
    }

    public Oligo getOligo() {
        return Collector.getOligo( _oliDatum._oligoUUID );
    }

    @Override
    public sampleType getSampleType() {
        return sampleType.OLIGO_SAMPLE;
    }
    /*-----------------
    variables
    -----------------*/
    private OligoSampleDatum _oliDatum;

    public static class OligoSampleDatum extends SampleDatum {

        public String _oligoUUID;

        @Override
        public ObjType getType() {
            return ObjType.OLIGO;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
    }
}
