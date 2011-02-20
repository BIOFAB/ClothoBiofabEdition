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

import org.clothocore.api.core.Collector;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class Plasmid extends ObjBase {

    public Plasmid( PlasmidDatum d ) {
        super( d );
        _plasDatum = d;

        if ( _plasDatum._riskGroup == -1 ) {
            final Plasmid item = this;
            Thread bslThread = new Thread() {
                @Override
                public void run() {
                    changeRiskGroup();
                }
            };
            bslThread.start();
            addSaveHold( bslThread );
        }
    }

    /**This constructor can only be called with a check to the Format
     *
     * @param p the Part that's been through a Format check
     * @param v the Vector that's been through a Format check
     * @param author
     */
    private Plasmid( Part p, Vector v, Person author, Format f ) {
        super();
        _plasDatum = new PlasmidDatum();
        _datum = _plasDatum;
        _datum.name = v.getName() + "-" + p.getName();
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _plasDatum._partUUID = p.getUUID();
        _plasDatum._vectorUUID = v.getUUID();
        _plasDatum._formatUUID = f.getUUID();
        _plasDatum._authorUUID = author.getUUID();
    }

    @Override
    public ObjType getType() {
        return ObjType.PLASMID;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        String idPerson = (String) objHash.get( "_authorUUID" );
        String sriskGroup = (String) objHash.get( "_riskGroup" );
        short riskGroup = Short.parseShort( sriskGroup );
        String idPart = (String) objHash.get( "_partUUID" );
        String idVector = (String) objHash.get( "_vectorUUID" );
        String idformat = (String) objHash.get( "_formatUUID" );
        String constructionFile = (String) objHash.get( "_constructionFile" );

        PlasmidDatum d = new PlasmidDatum();

        d.uuid = uuid;
        d.name = name;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._authorUUID = idPerson;
        d._partUUID = idPart;
        d._vectorUUID = idVector;
        d._formatUUID = idformat;
        d._constructionFile = constructionFile;
        d._riskGroup = riskGroup;

        return new Plasmid( d );
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
        datahash.put( "uuid", _plasDatum.uuid );
        datahash.put( "name", _plasDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_partUUID", _plasDatum._partUUID );
        datahash.put( "_vectorUUID", _plasDatum._vectorUUID );
        datahash.put( "_formatUUID", _plasDatum._formatUUID );
        datahash.put( "_authorUUID", _plasDatum._authorUUID );
        datahash.put( "_constructionFile", _plasDatum._constructionFile );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getFormat().generateXml( allObjects );
        allObjects = getPart().generateXml( allObjects );
        allObjects = getVector().generateXml( allObjects );

        //Return the datahash
        return allObjects;
    }

    /**
     * In essence, this is the "real" constructor for a new Plasmid, can make other ones if need to
     * use additional options, but regardless, generatePlasmid methods will check that the Part and Vector
     * are compatible with the provided Format, and then the Plasmid gets generated from Part and Vector
     * and finally this method fills in the rest of the information and returns the Plasmid
     *
     * @param p the Part being composed
     * @param v the Vector being composed
     * @param author the author of the Plasmid
     * @param f the Format being used to compose the Plasmid
     * @return
     */
    public static Plasmid generatePlasmid( Part p, Vector v, Person author, Format f ) {
        if(p==null || v==null || f == null || author==null) {
            return null;
        } 
        
        //Try to see if the Plasmid is already in the database
        String name = v.getName() + "-" + p.getName();
        Plasmid prexistingSeq = retrieveByName(name);
        if ( prexistingSeq != null ) {
            if(prexistingSeq.getPart().getUUID().equals(p.getUUID())) {
                if(prexistingSeq.getVector().getUUID().equals(v.getUUID())) {
                    int n = JOptionPane.showConfirmDialog( null, "A plasmid with this composition already exists.  You should try to use that plasmid.  Do you want to cancel this new Plasmid?", "Plasmid "
                            + "already exists", JOptionPane.YES_NO_OPTION );
                    if ( n == 0 ) {
                        return prexistingSeq;
                    }

                    //Do a second chance to cancel
                    int m = JOptionPane.showConfirmDialog( null, "Are you sure you really want two copies of this composition?  It isn't recommended, can I please abort this?", "Plasmid "
                            + "already exists", JOptionPane.YES_NO_OPTION );
                    if ( m == 0 ) {
                        return prexistingSeq;
                    }
                }
            }
        }

        //At least force them to give it a different name
        while ( prexistingSeq != null ) {
            name = JOptionPane.showInputDialog( "A Plasmid named " + name + " already exists, please give me a new name." );
            if(name==null) {
                return null;
            }
            prexistingSeq = retrieveByName( name );
        }

        try {
            if ( f.checkPlasmid( p, v, null ) ) {
                Plasmid newplasmid = new Plasmid( p, v, author, f );
                if ( v.isGenomic() ) {
                    newplasmid.changeName( v.getName() + p.getName() );
                }
                if ( v.getSeq().getSeq().length() == 0 ) {
                    newplasmid.changeName( v.getName() + p.getName() );
                }
                //FOR A VERSION OF GENERATEPLASMID THAT INVOLVES A LIST OF OLIGOS, THIS IS WHERE YOU'D POPULATE THAT

                newplasmid.changeName(name);
                return newplasmid;
            } else {
                JOptionPane.showMessageDialog( null, "I couldn't generate the plasmid", "Error", JOptionPane.ERROR_MESSAGE );
                return null;
            }
        }catch(Exception e) {
            return null;
        }
    }

    public static Plasmid retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.PLASMID );
        cq.eq( Plasmid.Fields.NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Plasmid p = (Plasmid) l.get( 0 );
        return p;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case PART:
                return true;
            default:
                return false;
        }
    }

    /* SETTERS
     * */


    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting plasmid save" );
        if ( !isChanged() ) {
            System.out.println( "plasmid didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _plasDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _plasDatum._partUUID ) ) {
            Part seq = getPart();
            if ( !seq.isInDatabase() ) {
                if ( !seq.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _plasDatum._vectorUUID ) ) {
            Vector seq = getVector();
            if ( !seq.isInDatabase() ) {
                if ( !seq.save( conn ) ) {
                    return false;
                }
            }
        }
        return super.save( conn );
    }

    public void changeAuthor(Person newauthor) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _plasDatum._authorUUID, newauthor.getUUID() );
        _plasDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    public void putConstructionFileAsString( String s ) {
        _plasDatum._constructionFile = s;
        this.setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
    }

    private void changeRiskGroup() {
        Part mypart = getPart();
        Vector myvect = getVector();

        //If either portion's riskgroup is undetermined, keep this undetermined
        if ( mypart.getRiskGroup() == -1 || myvect.getRiskGroup() == -1 ) {
            relayRiskGroup((short) -1);
            return;
        }

        //If either portion's riskgroup is 5, it's 5
        if ( mypart.getRiskGroup() == 5 || myvect.getRiskGroup() == 5 ) {
            relayRiskGroup((short) 5);
            return;
        }

        //If both components are RG2+, ask user what to do
        if ( mypart.getRiskGroup() > 1 && myvect.getRiskGroup() > 1 ) {
            short currentHighest = (short) Math.max( mypart.getRiskGroup(), myvect.getRiskGroup() );
            //Throw a dialog asking for user to put in the new risk group
            ButtonGroup group = new javax.swing.ButtonGroup();
            String msgString = "This plasmid joins a part and vector both with risk groups of 2 or higher.  What should the new risk group be?";
            int numelements = 5 - currentHighest;
            Object[] array = new Object[ numelements + 1 ];
            JRadioButton[] buttons = new JRadioButton[ numelements ];
            for ( short i = 0; i < numelements; i++ ) {
                buttons[i] = new javax.swing.JRadioButton( "Risk Group " + (i + currentHighest) );
                group.add( buttons[i] );
                array[i + 1] = buttons[i];
            }
            array[0] = msgString;

            int sel = -1;
            while ( sel != 0 ) {
                sel = JOptionPane.showConfirmDialog( null, array, "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE );
            }
            scanButtons:
            for ( short i = 1; i < numelements; i++ ) {
                if ( buttons[i].isSelected() ) {
                    relayRiskGroup((short) (currentHighest + i));
                    break scanButtons;
                }
            }
            currentHighest = _plasDatum._riskGroup;
        }
    }

    private void relayRiskGroup(short value) {
        if(value == _plasDatum._riskGroup) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.RISK_GROUP_CHANGED));
            return;
        }
        _plasDatum._riskGroup = value;
        setChanged(RefreshEvent.Condition.RISK_GROUP_CHANGED);
    }

    /* GETTERS
     * */
    public Part getPart() {
        return Collector.getPart( _plasDatum._partUUID );
    }

    public Vector getVector() {
        return Collector.getVector( _plasDatum._vectorUUID );
    }

    public Format getFormat() {
        return Collector.getFormat( _plasDatum._formatUUID );
    }

    public NucSeq getSeq() {
        if ( _plasDatum.tempSeq != null ) {
            return Collector.getNucSeq( _plasDatum.tempSeq );
        }
        NucSeq aseq = getFormat().generatePlasmidSequence( this );
        _plasDatum.tempSeq = aseq.getUUID();
        return aseq;
    }

    public Person getAuthor() {
        return Collector.getPerson( _plasDatum._authorUUID );
    }

    public String getAuthorUUID() {
        return _plasDatum._authorUUID;
    }

    public short getRiskGroup() {
        return _plasDatum._riskGroup;
    }

    public String getConstructionFileAsString() {
        return _plasDatum._constructionFile;
    }

    public ArrayList<Sample> getSamples() {
        System.out.println( "getSamples not implemented, need to do a database query" );
        return null;
    }

    /*-----------------
    variables
    -----------------*/
    private PlasmidDatum _plasDatum;


    public static class PlasmidDatum extends ObjBaseDatum {

        public ArrayList<String> _mySamples = new ArrayList<String>();
        public String _partUUID;
        public String _vectorUUID;
        public String _formatUUID;
        public String _authorUUID;
        public String _constructionFile;
        public String tempSeq;
        public short _riskGroup = -1;

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
        CONSTRUCTION_FILE,
        PART,
        VECTOR,
        AUTHOR,
        FORMAT,
        RISK_GROUP 
    }
}
