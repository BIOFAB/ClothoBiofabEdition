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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;
import org.clothocore.util.xml.XMLParser;

/**
 *
 * @author J. Christopher Anderson
 */
public class Feature extends ObjBase {

    /**
     * Constructor for ClothoConnections only
     * @param d
     */
    public Feature( FeatureDatum d ) {
        super( d );
        _featDatum = d;

        if ( _featDatum._riskGroup == -1 ) {
            final Feature afeature = this;
            Thread bslThread = new Thread() {

                @Override
                public void run() {
                    afeature.changeRiskGroup(afeature.getSeq().performBiosafetyCheck());
                }
            };
            bslThread.start();
            afeature.addSaveHold( bslThread );
        }
    }

    /**
     * Relayed constructor of a new Feature
     * @param name
     * @param seq
     * @param author
     */
    private Feature( String name, NucSeq seq, Person author, boolean iscds ) {
        super( );
        _featDatum = new FeatureDatum();
        _datum = _featDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _featDatum.uuid = _uuid;
        _featDatum._seqID = seq.getUUID();
        _featDatum._authorUUID = author.getUUID();
        _featDatum._familyLinks = new HashSet<String>();
        _featDatum._noteLinks = new HashSet<String>();
        _featDatum._isCDS = iscds;
    }

    /**
     * Method for generating a Feature.
     * Features are either CDS's or not.  For non-CDS features, the Feature will have the sequence as
     * explicitly submitted to the method.  For CDS features, you can provide a sequence with start and
     * stop codons on the ends, but they'll get chopped off.  You can also provide them without starts or stops
     * as long as they are in frame from start to end.  Starts and stops are inferred later during
     * autoannotate, so there is no real information loss.  The reason things are this way is to avoid calling
     * multiple subtly-different CDS's different names when they are simply regulatory variants of the same
     * polypeptide-coding sequence.  Different codon usage, however, is regarded as being a separate feature
     * as it may behave differently during translation (folding rates, translation rates, etc.)
     *
     * Regardless of strand, a Feature is considered to be equivalent.  The CDS Features must be passed as if
     * they are encoded on the sense strand (ATG...TAA).  All Features will read 5'
     * to 3', though they can be annotated onto NucSeqs in either orientation.
     *
     * For CDS parts with intentional internal stops (often used during amber suppression, but also might
     * be natural for some organisms), this method will warn the user that they are present, but it is acceptable
     * to have them.
     *
     * If a sequence passed to this method has degeneracy codes (N's R's etc.), it currently will be rejected.
     * Future version of Clotho will support libraries, but for now, Features are exact sequences.
     *
     * After creating a Feature, you can set the Forward and Reverse colors programmatically.  If none are
     * supplied, random colors will be generated when those colors are later requested.
     *
     * You can also add Notes to the Feature after creating the Feature.
     *
     * Setting the Family(s) for a Feature is currently not implemented, but will be implemented in future versions
     * of Clotho.
     *
     * @param name  the Feature's name (should be unique in the database)
     * @param seq the sequence of the Feature (case is ignored)
     * @param author  the Person object to be author of the new Feature
     * @param type
     * @return the new Feature object, a preexisting Feature of the same sequence, or null if the new Feature
     * was rejected for some reason.
     */
    public static Feature generateFeature( String name, String seq, Person author, boolean iscds ) {
        //See if a Feature with the same name already exists
        Feature prexistingSeq = retrieveByName( name );
        while ( prexistingSeq != null ) {
            name = JOptionPane.showInputDialog( "A Feature named " + name + " already exists, please give me a new name." );
            if(name==null) {
                return null;
            }
            prexistingSeq = retrieveByName( name );
        }

        // To find a feature who's sequence matches the above (chop off start and stop for CDS)
        String uppSeq = seq.toUpperCase();
        ClothoConnection c = Collector.getDefaultConnection();
        ClothoQuery mainQuery = c.createQuery( ObjType.FEATURE );
        ClothoQuery seqQuery = mainQuery.createAssociationQuery( Feature.Fields.SEQUENCE );
        String testseq = uppSeq;
        if(uppSeq.startsWith("ATG") || uppSeq.startsWith("GTG")) {
            testseq = uppSeq.substring(3);
        }
        if(uppSeq.endsWith("TAA") || uppSeq.endsWith("TAG") || uppSeq.endsWith("TGA")) {
            String dudly = testseq.substring(0, testseq.length()-3);
            testseq = dudly;
        }
        seqQuery.eq( NucSeq.Fields.SEQUENCE, testseq);
        List results = mainQuery.getResults();
        if(results.size()>0) {
            int n = JOptionPane.showConfirmDialog( null, "On " + name + " a feature with this sequence already exists.  You should try to use that feature.  Do you want to cancel this new feature?", "Feature "
                    + "already exists", JOptionPane.YES_NO_OPTION );
            if ( n == 0 ) {
                return (Feature) results.get(0);
            }

            //Do a second chance to cancel
            int m = JOptionPane.showConfirmDialog( null, "Are you sure you really want two copies of this sequence?  It isn't recommended, can I please abort this?", "Feature "
                    + "already exists", JOptionPane.YES_NO_OPTION );
            if ( m == 0 ) {
                return (Feature) results.get(0);
            }
        }

        //If it's cleared checks for preexisting features, start creating a new one
        NucSeq nseq = new NucSeq( uppSeq );

        if(nseq.isDegenerate()) {
            nseq.setTransient();
            return null;
        }

        //If it starts with a start codon, but isn't stated as a CDS, maybe throw a warning
        if(!iscds) {
            //Check if it's a CDS in forward orientation
            if(uppSeq.startsWith("ATG")) {
                int modulus = nseq.getSeq().length() % 3;
                int extras = nseq.getSeq().length() - modulus;
                String translation = nseq.translate(0, extras);
                if(translation.equals("*")) {
                    System.out.println("Failed translation of " + nseq.getSeq().substring(0, extras));
                }
                //If the first stop codon is near the end, it might be a CDS
                if(translation.indexOf("*") > translation.length()-3 || translation.indexOf("*")==-1) {
                    int n = JOptionPane.showConfirmDialog(
                        null,
                        "Feature " + name + " might be a CDS Feature, is it?",
                        "CDS detected",
                        JOptionPane.YES_NO_OPTION);
                    if(n==0) {
                        iscds = true;
                    }
                }
            }
            //Check if it's a CDS in reverse orientation
            String srevcomp = nseq.revComp();
            NucSeq revcomp = new NucSeq(srevcomp);
            revcomp.setTransient();
            if(srevcomp.startsWith("ATG")) {
                int modulus = revcomp.getSeq().length() % 3;
                int extras = revcomp.getSeq().length() - modulus;
                String translation = revcomp.translate(0, extras);
                if(translation.equals("*")) {
                    System.out.println("Failed translation of " + nseq.getSeq().substring(0, extras));
                }
                //If the first stop codon is near the end, it might be a CDS
                if(translation.indexOf("*") > translation.length()-3 || translation.indexOf("*")==-1) {
                    int n = JOptionPane.showConfirmDialog(
                        null,
                        "Feature " + name + " might be a CDS Feature reverse complemented, is it?",
                        "CDS detected",
                        JOptionPane.YES_NO_OPTION);
                    if(n==0) {
                        iscds = true;
                        nseq.changeSeq(revcomp.getSeq());
                    }
                }
            }
        }

        //If it was stated to be a CDS, make sure it's really a CDS
        if(iscds) {
            System.out.println("doing iscds logic checks");
            String translation = nseq.translate(0);
            //If there's a start codon on it, chop it off
            if(uppSeq.startsWith("ATG") || uppSeq.startsWith("GTG") || uppSeq.startsWith("TTG")) {
                System.out.println("I'm chopping the start codon");
                String tempseq = uppSeq.substring(3);
                uppSeq = tempseq;
                nseq.changeSeq(uppSeq);
                System.out.println("done chopping the start codon");
            }

            //Trim the end if it's out of frame
            int extras = uppSeq.length() % 3;
            if(extras>0) {
                nseq.changeSeq(uppSeq.substring(0, uppSeq.length() - extras));
            }

            //If there's a stop codon at the end, chop it off
            translation = nseq.translate(0);
            int nearend = translation.length()-2;
            int end = translation.indexOf("*");
            System.out.println(translation + " near end " + nearend + " end " + end);
            if(end > nearend) {
                System.out.println("chopping off the stop codon");
                nseq.changeSeq(uppSeq.substring(0, 3*(end)));

            //Otherwise it doesn't translate through, so maybe dump it
            } else if(end==-1) {
                System.out.println("there was no stop codon");
            } else {
                int n = JOptionPane.showConfirmDialog( null, "Feature " + name + " appears to contain an internal start codon.  Should I cancel?", "Internal stop "
                        + "detected", JOptionPane.YES_NO_OPTION );
                if(n==0) {
                    nseq.setTransient();
                    return null;
                }
            }

            //If the thing translates wrong, return null
            if(nseq.translate(0).equals("*")) {
                nseq.setTransient();
                return null;
            }
        }


        final Feature afeature = new Feature( name, nseq, author, iscds );

        //Set the biosafety level of the new Feature
        Thread bslThread = new Thread() {

            @Override
            public void run() {
                afeature.setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                Short rg = afeature.getSeq().performBiosafetyCheck();
                afeature._featDatum._riskGroup = rg;
            }
        };
        bslThread.start();
        afeature.addSaveHold( bslThread );

        nseq.setLocked( true );
     //   NucSeq.addFeatureToTable(afeature);
        return afeature;
    }

    /**
     * This method may exist in future versions of Clotho but is not currently implemented.
     *
     * Method for altering a Feature that is a mutant of the first.  The most likely scenario for this is
     * when a Feature already exists and is linked to a part, but a mutant plasmid is generated, and the author
     * needs to indicate the existence of the mutant and instead of calling it a new Feature, just allow for it
     * to be a little degenerate.
     * 
     * @param name
     * @param seq
     * @param author
     * @return
     *
    public boolean mutateFeature( String name, String seq, Person author ) {
        String old = this.getSeq().toString();
        String nu = "";
        int Ncount = 0;

        for ( int i = 0; i < old.length(); i++ ) {
            if ( old.charAt( i ) == seq.charAt( i ) ) {
                nu += old.charAt( i );
            } else {
                nu += 'N';
                Ncount++;
            }
        }
        if ( Ncount > 25 ) {
            JOptionPane.showMessageDialog( null, "That sequence is pretty far from the original, I count " + Ncount + " differences.  You should make a new feature instead, or check the alignment of the new sequence.", "Error", JOptionPane.ERROR_MESSAGE );
            return false;
        }
        _featDatum._seqID = new NucSeq( nu ).getUUID();
        return true;
    }
*/

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting feature save" );
        if ( !isChanged() ) {
            System.out.println( "feature didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _featDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _featDatum._seqID ) ) {
            NucSeq seq = getSeq();
            if ( !seq.save( conn ) ) {
                return false;
            }
        }

        if(!isInDatabase()) {
            HashSet<String> links = _featDatum._noteLinks;
            _featDatum._noteLinks = new HashSet<String>();
            if(!super.save( conn )) {
                return false;
            }
            _featDatum._isChanged = true;
            _featDatum._noteLinks = links;
        }

        //Check if any notes need saving
        for ( String noteUUID : _featDatum._noteLinks ) {
            if ( Collector.isLocal( noteUUID ) ) {
                Note f = Collector.getNote( noteUUID );
                if ( f != null ) {
                    System.out.println( f.getUUID() + f.getName() );
                    if ( !f.save( conn ) ) {
                        return false;
                    }
                }
            }
        }

        return super.save( conn );
    }

    /**
     * Get the preferred forward color for this Feature.  If no forward color
     * was set, a default color will be returned.
     * @return an AWT Color object.  It won't be null;
     */
    public Color getForwardColor() {
        if ( _featDatum.forwardColor == null ) {
            return new Color( 125, 225, 235 );
        }
        return _featDatum.forwardColor;
    }

    /**
     * Get the preferred reverse color for this Feature.  If no reverse color
     * was set, a default color will be returned.
     * @return an AWT Color object.  It won't be null;
     */
    public Color getReverseColor() {
        if ( _featDatum.reverseColor == null ) {
            return new Color( 125, 225, 235 );
        }
        return _featDatum.reverseColor;
    }

    /**
     * Set the forward and reverse preferred colors for this feature to some
     * random medium-bright color.
     */
    public void setRandomColors() {
        int[][] intVal = new int[ 2 ][ 3 ];
        for ( int j = 0; j < 3; j++ ) {
            double doubVal = Math.floor( Math.random() * 155 + 100 );
            intVal[0][j] = (int) doubVal;
            intVal[1][j] = 255 - intVal[0][j];
        }
        _featDatum.forwardColor = new Color( intVal[0][0], intVal[0][1], intVal[0][2] );
        _featDatum.reverseColor = new Color( intVal[1][0], intVal[1][1], intVal[1][2] );
    }

    @Override
    public ObjType getType() {
        return ObjType.FEATURE;
    }

    /**
     * Retrieve a Feature from the database by its name
     * @param name the name of the desired Feature
     * @return the Feature or null if none was found
     */
    public static Feature retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.FEATURE );
        cq.contains( Feature.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Feature p = (Feature) l.get( 0 );
        return p;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        String description = (String) objHash.get( "_shortdescription" );
        String featuredata = (String) objHash.get( "_featureData" );
        String sriskGroup = (String) objHash.get( "_riskGroup" );
        short riskGroup = Short.parseShort( sriskGroup );
        String sforcol = (String) objHash.get( "forwardColor" );
        int forColor = Integer.parseInt( sforcol );
        String srevcol = (String) objHash.get( "reverseColor" );
        int revColor = Integer.parseInt( srevcol );
        String genbankId = (String) objHash.get( "_GenbankId" );
        String swissprotId = (String) objHash.get( "_swissProtId" );
        String pdbId = (String) objHash.get( "_PDBid" );
        String authorUUID = (String) objHash.get( "_authorUUID" );
        String nucSeqUUID = (String) objHash.get( "_seqID" );

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        //NEEDS NOTE LINKS
        //  return new Feature( uuid, name, featuredata, forColor, revColor, dateCreated, lastModified, riskGroup, genbankId, swissprotId, pdbId, authorUUID, nucSeqUUID);
        return null;
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
        datahash.put( "uuid", _featDatum.uuid );
        datahash.put( "name", _featDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );
        datahash.put( "forwardColor", Integer.toString( _featDatum.forwardColor.getRGB() ) );
        datahash.put( "reverseColor", Integer.toString( _featDatum.reverseColor.getRGB() ) );
        datahash.put( "_riskGroup", Integer.toString( _featDatum._riskGroup ) );
        datahash.put( "_authorUUID", _featDatum._authorUUID );
        datahash.put( "_featureData", _featDatum._featureData );
        datahash.put( "_seqID", _featDatum._seqID );
        datahash.put( "noteLinks", _featDatum._noteLinks );
        datahash.put( "familyLinks", _featDatum._familyLinks );
        datahash.put( "_GenbankId", this._featDatum._GenbankId );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        allObjects = getAuthor().generateXml( allObjects );
        allObjects = getSeq().generateXml( allObjects );

        //Gather up all the notes
        if ( _featDatum._noteLinks != null ) {
            for ( String uuid : _featDatum._noteLinks ) {
                Note anote = Collector.getNote( uuid );
                allObjects = anote.generateXml( allObjects );
            }
        }

        //Gather up all the families
        if ( _featDatum._familyLinks != null ) {
            for ( String uuid : _featDatum._familyLinks ) {
                Family afam = Collector.getFamily( uuid );
                allObjects = afam.generateXml( allObjects );
            }
        }

        //Return the datahash
        return allObjects;
    }

    /**
     * General method for adding things to the Feature.  Currently it only
     * accepts additions of Notes.
     * @param dropObject the ObjBase being added (linked) to this Feature
     * @return true if the drop was accepted
     */
    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case NOTE:
                final Note item = (Note) dropObject;

                ActionListener undo = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _featDatum._noteLinks.remove( item.getUUID() );
                        item.removeFeatureRelay( Feature.this.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                    }
                };
                ActionListener redo = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _featDatum._noteLinks.add( item.getUUID() );
                        item.addFeatureRelay( Feature.this );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                    }
                };
                addUndo( undo, redo );

                _featDatum._noteLinks.add( item.getUUID() );
                item.addFeatureRelay( this );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                return true;
            case FAMILY:
                final Family fitem = (Family) dropObject;

                System.out.println("Addding Family " + fitem.getName() + " to " + Feature.this.getName());
                ActionListener fundo = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _featDatum._familyLinks.remove(fitem.getUUID() );
                        fitem.removeFeatureRelay( Feature.this.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FAMILY_TO_FEATURE);
                    }
                };
                ActionListener fredo = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _featDatum._familyLinks.add( fitem.getUUID() );
                        fitem.addFeatureRelay( Feature.this );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FAMILY_TO_FEATURE);
                    }
                };
                addUndo( fundo, fredo );

                _featDatum._familyLinks.add( fitem.getUUID() );
                fitem.addFeatureRelay( this );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.FAMILY_TO_FEATURE);
                return true;
            default:
                return false;
        }
    }

    /**
     * Remove a Note from this Feature
     * @param anote the Note you wish to remove
     * @return true if the Note was removed, or false if the Note wasn't there
     */
    public boolean removeNote( final Note item ) {
        final Feature myself = this;
        String uuid = item.getUUID();
        if ( this._featDatum._noteLinks.contains( uuid ) ) {
            ActionListener undo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _featDatum._noteLinks.add( item.getUUID() );
                    item.addFeatureRelay( myself );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                }
            };
            ActionListener redo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _featDatum._noteLinks.remove( item.getUUID() );
                    item.removeFeatureRelay( myself.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                }
            };
            addUndo( undo, redo );

            _featDatum._noteLinks.remove( uuid );
            item.removeFeatureRelay( this.getUUID() );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
            return true;
        }
        return false;
    }

    /**
     * Get the sequence object for this Feature.
     * @return the NucSeq for this Feature
     */
    public NucSeq getSeq() {
        return Collector.getNucSeq( _featDatum._seqID );
    }

    /* SETTERS
     * */
    /**
     * Change the name of this Feature.  This name must be unique in the database
     * or the change will be rejected.
     * @param name the new name of the Feature
     */
    @Override
    public void changeName( final String name ) {
        Feature existingpart = Feature.retrieveByName( name );
        if ( existingpart != null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.NAME_CHANGED));
            return;
        }
        super.changeName( name );;
    }

    /**
     * Method for changing the sequence of this Feature.  The sequence of the NucSeq is
     * locked in NucSeq.  You must use this method to alter the sequence instead.
     * @param newseq the new sequence of the Feature
     */
    public void changeSequence( final String newseq ) {
        if ( newseq.equals( "" ) || newseq == null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SEQUENCE_CHANGED));
            return;
        }

        final String oldseq = Collector.getNucSeq( _featDatum._seqID ).toString();
        Collector.getNucSeq( _featDatum._seqID ).APIchangeSeq( newseq );

        ActionListener undo = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _featDatum._seqID ).APIchangeSeq( oldseq );
            }
        };

        ActionListener redo = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Collector.getNucSeq( _featDatum._seqID ).APIchangeSeq( newseq );
            }
        };

        //Change the risk group
        _featDatum._riskGroup = -1;
        final Feature item = this;
        Thread bslThread = new Thread() {
            @Override
            public void run() {
                _featDatum._riskGroup = item.getSeq().performBiosafetyCheck();
            }
        };
        bslThread.start();
        addSaveHold( bslThread );

        addUndo( undo, redo );
    }

    /**
     * Change the author of this Feature
     * @param author
     */
    public void changeAuthor( Person newauthor ) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _featDatum._authorUUID, newauthor.getUUID() );
        _featDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    public void changeGenbankId(String newid) {
        addUndo( "_GenbankId", _featDatum._GenbankId, newid );
        _featDatum._GenbankId = newid;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.GENBANK_CHANGED);
    }

    /**
     * Change the risk group of the Feature.  You can only raise the risk group.
     * @param newrg the new risk group (1 through 5)
     */
    public final void changeRiskGroup( short newrg ) {
        if ( newrg > _featDatum._riskGroup && newrg<=5) {
            addUndo( "_riskGroup", _featDatum._riskGroup, newrg );
            _featDatum._riskGroup = newrg;
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.RISK_GROUP_CHANGED);
            return;
        }
        fireData(new RefreshEvent(this, RefreshEvent.Condition.RISK_GROUP_CHANGED));
        
    }

    /**
     * Change the preferred forward color of the Feature
     * @param acolor a non-null AWT color object
     */
    public void changeForwardColor( Color acolor ) {
        if(acolor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.COLOR_CHANGED));
            return;
        }
        addUndo( "forwardColor", _featDatum.forwardColor, acolor );
        _featDatum.forwardColor = acolor;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.COLOR_CHANGED);
    }

    /**
     * Change the preferred reverse color of the Feature
     * @param acolor a non-null AWT color object
     */
    public void changeReverseColor( Color acolor ) {
        if(acolor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.COLOR_CHANGED));
            return;
        }
        addUndo( "reverseColor", _featDatum.reverseColor, acolor );
        _featDatum.reverseColor = acolor;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.COLOR_CHANGED);
    }

    /* GETTERS
     * */

    /**
     * Get the Author of this Feature as a UUID link.
     * @return a UUID String
     */
    public String getAuthorUUID() {
        return _featDatum._authorUUID;
    }

    /**
     * Get the Genbank ID for this feature
     * @return the UUID for Genbank that is the related sequence to this Feature
     */
    public String getGenbankId() {
        return _featDatum._GenbankId;
    }

    /**
     * Get the risk group of this Feature
     * @return a Short between 1 and 5
     */
    public short getRiskGroup() {
        return _featDatum._riskGroup;
    }

    /**
     * Get the author of this Feature
     * @return a Person object for the Feature's author
     */
    public Person getAuthor() {
        return Collector.getPerson( _featDatum._authorUUID );
    }

    /**
     * If it's a CDS (a protein-coding Feature) as opposed to an origin, promoter, terminator, etc.
     * @return true if is a CDS
     */
    public boolean isCDS() {
        return _featDatum._isCDS;
    }

    /**
     * Get all the UUIDs of Notes linked to this Feature
     * @return a HashSet of UUID Strings
     */
    public HashSet<String> getNoteLinks() {
        return _featDatum._noteLinks;
    }

    /**
     * Get all this Feature's Families as UUID links
     * @return a HashSet of UUID Strings
     */
    public HashSet<String> getFamilyLinks() {
        return _featDatum._familyLinks;
    }

    /**
     * Get all this Feature's Families as Family objects
     * @return a HashSet of Family ObjBases
     */
    public HashSet<Family> getFamilies() {
        HashSet<Family> out = new HashSet<Family>();
        if ( _featDatum._familyLinks == null ) {
            return out;
        }
        for ( String s : _featDatum._familyLinks ) {
            Family afa = Collector.getFamily( s );
            if ( afa != null ) {
                out.add( afa );
            }
        }
        return out;
    }

    /**
     * Get all the Notes attached to this Feature as Feature objects
     * @return a HashSet of Note ObjBases
     */
    public HashSet<Note> getNotes() {
        HashSet<Note> out = new HashSet<Note>();
        if ( _featDatum._noteLinks == null ) {
            return out;
        }
        for ( String s : _featDatum._noteLinks ) {
            Note anote = Collector.getNote( s );
            if ( anote != null ) {
                out.add( anote );
            }
        }
        return out;
    }

/*-----------------
    variables
-----------------*/
    private FeatureDatum _featDatum;

    public static class FeatureDatum extends ObjBaseDatum {

        public String _seqID;
        public Color forwardColor = null;
        public Color reverseColor = null;
        //The following get satisfied as long as they can be located:
        public String _GenbankId;
        public String _SourceOrganism;
        //The following are only properties of CDS parts, and only if can be located:
        private transient XMLParser myParser;
        public String _authorUUID;
        public short _riskGroup = -1;
        public String _featureData;
        public HashSet<String> _noteLinks;
        public HashSet<String> _familyLinks;
        public boolean _isCDS;

        @Override
        public ObjType getType() {
            return ObjType.FEATURE;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        FORWARD_COLOR,
        REVERSE_COLOR,
        GENBANK_ID,
        RISK_GROUP,
        SOURCE_ORGANISM,
        DATA,
        AUTHOR,
        SEQUENCE,
        ANNOTATIONS
    }
}
