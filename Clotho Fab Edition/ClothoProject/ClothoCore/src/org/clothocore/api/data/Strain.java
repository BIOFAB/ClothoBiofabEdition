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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Attachment.AttachmentType;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class Strain extends ObjBase {

    /**
     * For generation of singleton Strain used by ClothoConnections
     */
    private Strain() {
        super(new StrainDatum());
    }

    public Strain( StrainDatum d ) {
        super( d );
        _strDatum = d;

        if ( _strDatum._riskGroup == -1 ) {
            final Strain item = this;
            Thread bslThread = new Thread() {

                @Override
                public void run() {
                //    changeRiskGroup();
                }
            };
            bslThread.start();
            addSaveHold( bslThread );
        }
    }

    /**
     * Constructor for a all new Strain
     * @param name
     * @param author
     */
    private Strain( String name, Person author ) {
        super( );
        _strDatum = new StrainDatum();
        _datum = _strDatum;
        _datum.name = name;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _strDatum._authorUUID = author.getUUID();
        _strDatum._noteLinks = new HashSet<String>();
    }

    /**
     * Constructor for a composite Strain.  Can start with a basic
     * Strain and add lots of plasmids, or can start with a composite Strain
     * and it will first duplicate it, then can add stuff to it subsequently.
     *
     * @param name name of the Strain
     * @param author author of the Strain
     * @param parent the parent Strain of the new Strain
     */
    public Strain( String name, Person author, Strain parent ) {
        this( name, author );

        _strDatum._isBasic = false; //It's a composite Strain
        _strDatum._strainType = parent.getStrainType();
        _strDatum._parentStrain = parent.getUUID();
        _strDatum._episomeLinks = new HashSet<String>();
        _strDatum._genModLinks = new HashSet<genomeModification>();
    }

    /**
     * Constructor for a basic Strain.  It can take a genbank file
     * with the genome sequence (could parse that from pubmed, or supply as a File).
     * However, the genbank Attachment can be null.
     *
     * @param name
     * @param author
     * @param genbank
     */
    public Strain( String name, Person author, Attachment genbank, strainType type ) {
        this( name, author );
        if ( genbank != null ) {
            _strDatum._genbankFileUUID = genbank.getUUID();
        }
        _strDatum._strainType = type;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting STRAIN save" );
        if ( !isChanged() ) {
            System.out.println( "strain didn't require saving" );
            return true;
        }

        if ( Collector.isLocal( _strDatum._authorUUID ) ) {
            Person aut = getAuthor();
            if ( !aut.isInDatabase() ) {
                if ( !aut.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _strDatum._genbankFileUUID ) ) {
            Attachment att = getGenbankFile();
            if ( !att.isInDatabase() ) {
                if ( !att.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( _strDatum._parentStrain ) ) {
            Strain par = getParentStrain();
            if ( !par.isInDatabase() ) {
                if ( !par.save( conn ) ) {
                    return false;
                }
            }
        }


        if(!isInDatabase()) {
            HashSet<String> notelinks = _strDatum._noteLinks;
            HashSet<String> plaslinks = _strDatum._episomeLinks;
            _strDatum._noteLinks = new HashSet<String>();
            _strDatum._episomeLinks = new HashSet<String>();
            if(!super.save( conn )) {
                return false;
            }
            _strDatum._isChanged = true;
            _strDatum._noteLinks = notelinks;
            _strDatum._episomeLinks = plaslinks;
        }

        //Check if any notes need saving
        for ( String noteUUID : _strDatum._noteLinks ) {
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

        //Check if any plasmids need saving
        for ( String plasUUID : _strDatum._episomeLinks ) {
            if ( Collector.isLocal( plasUUID ) ) {
                Plasmid f = Collector.getPlasmid( plasUUID );
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

    @Override
    public ObjType getType() {
        return ObjType.STRAIN;
    }

    /* SETTERS
     * */
    public final void changeRiskGroup(Short newrg) {
        //NEED TO FIGURE THIS ONE OUT, THREADING IS ALREADY OK
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case ATTACHMENT:
                if ( _strDatum._genbankFileUUID != null ) {
                    return false;
                }
                Attachment att = (Attachment) dropObject;
                return changeGenbank( att );
            case NOTE:
                final Note dropNote = (Note) dropObject;

                ActionListener undo = new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _strDatum._noteLinks.remove( dropNote.getUUID() );
                        dropNote.removeStrainRelay( Strain.this.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                    }
                };
                ActionListener redo = new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _strDatum._noteLinks.add( dropNote.getUUID() );
                        dropNote.addStrainRelay( Strain.this );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                    }
                };
                addUndo( undo, redo );

                _strDatum._noteLinks.add( dropNote.getUUID() );
                System.out.println("Note " + dropNote.getUUID() + " added to this strain");
                dropNote.addStrainRelay( this );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                return true;
            case PLASMID:
                if ( this._strDatum._isBasic ) {
                    return false;
                }

                final Plasmid aplas = (Plasmid) dropObject;

                if ( aplas.getVector().isGenomic() ) {
                    //THROW A DIALOG
                    String changethis = JOptionPane.showInputDialog( "The plasmid you're adding to this strain can be genomic.  Should I add it to the genome or the cytoplasm?" );
                    //IF USER CHOOSES EPISOME, ADD TO EPISOME, OTHERWISE ADD A genomeModification LINK
                }

                ActionListener undo2 = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _strDatum._episomeLinks.remove( aplas.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                    }
                };
                ActionListener redo2 = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _strDatum._episomeLinks.add( aplas.getUUID() );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                    }
                };
                addUndo( undo2, redo2 );


                _strDatum._episomeLinks.add( aplas.getUUID() );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                return true;
            default:
                return false;
        }
    }

    /**
     * Remove a linked Note
     * @param anote the Note you wish to remove
     * @return true if the Note was removed, or false if the Note wasn't there
     */
    public boolean removeNote( final Note item ) {
        String uuid = item.getUUID();
        if ( _strDatum._noteLinks.contains( uuid ) ) {
            ActionListener undo = new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    _strDatum._noteLinks.add( item.getUUID() );
                    item.addStrainRelay( Strain.this );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                }
            };

            ActionListener redo = new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    _strDatum._noteLinks.remove( item.getUUID() );
                    item.removeStrainRelay( Strain.this.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
                }
            };
            addUndo( undo, redo );

            _strDatum._noteLinks.remove( uuid );
            item.removeStrainRelay( this.getUUID() );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NOTE_LINKED);
            return true;
        }
        return false;
    }

    @Override
  public void changeName( final String newname ) {
        Strain existing = Strain.retrieveByName(newname);
        if(existing!=null) {
            if(!existing.getUUID().equals(this.getUUID())) {
                setChanged(RefreshEvent.Condition.NAME_CHANGED);
                return;
            }
        }
        super.changeName(newname);
    }

    public void changeStrainType(strainType strainType) {
        if(strainType==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.TYPE_CHANGED));
            return;
        }
        _strDatum._strainType = strainType;
        setChanged(RefreshEvent.Condition.TYPE_CHANGED);
    }

    public void changeParentStrain(Strain astrain) {
        if(astrain==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.PARENT_STRAIN_CHANGED));
            return;
        }
        _strDatum._parentStrain = astrain.getUUID();
        setChanged(RefreshEvent.Condition.PARENT_STRAIN_CHANGED);
    }

    public boolean changeGenbank( Attachment att ) {
        if ( att.getAttachmentType().equals( AttachmentType.GB ) ) {
            addUndo( "_genbankFileUUID", _strDatum._genbankFileUUID, att.getUUID() );
            _strDatum._genbankFileUUID = att.getUUID();
            setChanged(RefreshEvent.Condition.GENBANK_CHANGED);
            return true;
        }
        return false;
    }

    public void changeDescription(String newdesc) {
        _strDatum._description = newdesc;
        setChanged(RefreshEvent.Condition.DESCRIPTION_CHANGED);
    }

    public void changeGenomeID(String text) {
        if(!isBasic()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.GENBANK_CHANGED));
            return;
        }
        _strDatum._genomeId = text;
        setChanged(RefreshEvent.Condition.GENBANK_CHANGED);
    }

    /**
     * Change the author to the one indicated.
     * Call rejected if a Sample already exists from this Strain
     *
     * @param author
     */
    public void changeAuthor( Person newauthor ) {
        if(newauthor==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
            return;
        }
        addUndo( "_authorUUID", _strDatum._authorUUID, newauthor.getUUID() );
        _strDatum._authorUUID = newauthor.getUUID();
        fireData(new RefreshEvent(this, RefreshEvent.Condition.AUTHOR_CHANGED));
    }

    /**
     * Ehhh...not sure if this should be a public method
     * 
     * Change the short description of the Strain
     * @param text a description of the Strain
     *
    public void changeDescription(String text) {
        if(text==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.DESCRIPTION_CHANGED));
            return;
        }
        addUndo( "_description", _strDatum._description, text );
        _strDatum._description = text;
        fireData(new RefreshEvent(this, RefreshEvent.Condition.DESCRIPTION_CHANGED));
    }
     * */

    /*  NOT SURE WHAT THE CALL SHOULD BE, BUT SCENARIO IS WHERE A SAMPLE IS KNOWN TO REALLY BE SOMETHING ELSE
     * AND YOU WANT TO CHANGE THE COMPOSITION, SO YOU CREATE A NEW STRAIN AND MOVE THE LINKS TO BOTH THE SAMPLE AND THE NEW STRAIN
    public static reassociate(Sample asample, Strain newstrain) {

    }
     */
    /**
     * Reciprocol call from strainSample
     * @param ss
     */
    void addSample( Sample ss ) {
        if ( ss == null ) {
            return;
        }
        if ( _strDatum._sampleLinks == null ) {
            _strDatum._sampleLinks = new HashSet<String>();
            _strDatum._isLocked = true;
        }
        _strDatum._sampleLinks.add( ss.getUUID() );
    }

    /**
     * Remove a Sample of this Strain
     * @param ss
     */
    public void removeSample( Sample ss ) {
        if ( ss == null ) {
            return;
        }
        if ( _strDatum._sampleLinks == null ) {
            return;
        }
        //Remove the Sample
        _strDatum._sampleLinks.remove( ss.getUUID() );
        ss.deleteFromDatabase();

        //If the array is now empty, null and unlock it
        if ( _strDatum._sampleLinks.size() == 0 ) {
            _strDatum._sampleLinks = null;
            _strDatum._isLocked = false;
        }
    }

    /* GETTERS
     * */
    public boolean isBasic() {
        return _strDatum._isBasic;
    }

    public boolean isLocked() {
        return _strDatum._isLocked;
    }

    /**
     * This returns all plasmids, be they genomic
     * or episomal
     *
     * @return a HashSet of all plasmids in this Strain
     */
    public HashSet<Plasmid> getAllPlasmids() {
        HashSet<Plasmid> out = this.getEpisomalPlasmids();
        for ( genomeModification gen : _strDatum._genModLinks ) {
            System.out.println("genomemodificatin gen " + gen);
            Plasmid item = gen.getPlasmid();
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * Remove a Plasmid from the Strain.  This method will remove the Plasmid
     * regardless of whether it is in the genome or in the cytoplasm.  If by chance
     * it was in both the cytoplasm and genome, both would be removed.  If there were
     * multiple plasmids in the genome, they all would be removed.
     *
     * @param p the Plasmid you wish to remove
     * @return true if a Plasmid was removed, false if none were removed
     */
    public boolean removePlasmid( final Plasmid p ) {
        String uuid = p.getUUID();
        boolean out = false;
        if ( this._strDatum._episomeLinks.contains( uuid ) ) {

            ActionListener undo2 = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _strDatum._episomeLinks.add( p.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                }
            };
            ActionListener redo2 = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _strDatum._episomeLinks.remove( p.getUUID() );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                }
            };
            addUndo( undo2, redo2 );

            _strDatum._episomeLinks.remove( uuid );
            out = true;
        }
        for ( final genomeModification gm : this._strDatum._genModLinks ) {
            if ( gm._plasmidLink.equals( uuid ) ) {
                ActionListener undo2 = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _strDatum._genModLinks.add( gm );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                    }
                };
                ActionListener redo2 = new ActionListener() {

                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        _strDatum._genModLinks.remove( gm );
                        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
                    }
                };
                addUndo( undo2, redo2 );

                _strDatum._genModLinks.remove( gm );
                out = true;
            }
        }
        if ( out ) {
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.PLASMID_TO_STRAIN);
        }
        return out;
    }

    /**
     * Remove a Plasmid from the genome.  It will only remove the one instance
     * of the genomeModification in the genome.  So, if there happened to be multiple
     * genome modifications of the same Plasmid at distinct loci, only the one passed
     * as the argument gets removed.
     *
     * @param genomeModification the genome modfication you wish to remove
     * @return true if a genomeModification was removed, false if not removed
     */
    public boolean removeGenomeModfication( final genomeModification gm ) {
        if ( _strDatum._genModLinks.contains( gm ) ) {
            ActionListener undo2 = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _strDatum._genModLinks.add( gm );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                }
            };
            ActionListener redo2 = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _strDatum._genModLinks.remove( gm );
                    setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
                }
            };
            addUndo( undo2, redo2 );

            _strDatum._genModLinks.remove( gm );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
            return true;
        }
        return false;
    }

    /**
     * This returns only episomal plasmids, ignores genomic modifications
     * @return a HashSet of all episomal plasmids in this Strain
     */
    public HashSet<Plasmid> getEpisomalPlasmids() {
        HashSet<Plasmid> out = new HashSet<Plasmid>();
        for ( String s : this._strDatum._episomeLinks ) {
            Plasmid item = Collector.getPlasmid( s );
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * This returns links to all plasmids, be they genomic
     * or episomal
     * @return  a HashSet of uuids for all plasmids
     */
    public HashSet<String> getAllPlasmidLinks() {
        HashSet<String> out = new HashSet<String>();
        for ( String s : _strDatum._episomeLinks ) {
            out.add( s );
        }
        for ( genomeModification gen : _strDatum._genModLinks ) {
            out.add( gen.getPlasmidLink() );
        }
        return out;
    }

    public HashSet<String> getEpisomalPlasmidsLinks() {
        if(_strDatum._episomeLinks==null) {
            return new HashSet<String>();
        }
        return _strDatum._episomeLinks;
    }

    /**
     * Get all the genomic "plasmids" for the Strain
     * @return a HashSet of annotation-like objects that
     * provide the Plasmid, its location relative to the reference
     * genome sequence, and whether it is reverse complemented relative
     * to that sequence.  The genomModifications will not necessarily
     * have genome positioning information (they can be -1 meaning unavailable).
     */
    public HashSet<genomeModification> getGenomeModifications() {
        return this._strDatum._genModLinks;
    }

    /**
     * Get the list of notes as Note objects from this Note
     * @return
     */
    public HashSet<Note> getNotes() {
        HashSet<Note> out = new HashSet<Note>();
        for ( String f : _strDatum._noteLinks ) {
            Note item = Collector.getNote( f );
            if ( item != null ) {
                out.add( item );
            }
        }
        return out;
    }

    /**
     * Get the list of notes as UUIDs in a HashSet
     * @return a HashSet of uuids for notes
     */
    public HashSet<String> getNoteLinks() {
        return _strDatum._noteLinks;
    }

    public Person getAuthor() {
        return Collector.getPerson( _strDatum._authorUUID );
    }

    public String getDescription() {
        if(_strDatum._description==null) {
            return "";
        }
        return _strDatum._description;
    }

    public short getRiskGroup() {
        return _strDatum._riskGroup;
    }

    /**
     * Get the Strain from which this Strain was composed
     * @return the Strain from which it was composed
     */
    public Strain getParentStrain() {
        if ( _strDatum._isBasic ) {
            return null;
        }
        return Collector.getStrain( _strDatum._parentStrain );
    }

    /**
     * Recursively determine the basic Strain from which this is derived
     * @return the root Strain
     */
    public Strain getRootStrain() {
        if ( _strDatum._isBasic ) {
            return this;
        }
        return getParentStrain();
    }

    /**
     * The genbank file may or may not be present.  This therefore may return
     * null.  The genbank file link is only stored for the basic Strain from
     * which a composite Strain is derived.  If the basic Strain had a genbank
     * file, that is the Attachment that is returned.
     * @return genbank file packaged in an Attachment ObjBase if it is available,
     * otherwise null
     */
    public Attachment getGenbankFile() {
        if ( _strDatum._isBasic == false ) {
            return getParentStrain().getGenbankFile();
        }

        if ( _strDatum._genbankFileUUID == null ) {
            return null;
        }
        return Collector.getAttachment( _strDatum._genbankFileUUID );
    }

    public strainType getStrainType() {
        return _strDatum._strainType;
    }

    /**
     * Automatically sets the Strain type based on blast
     * @return true if successfully found the type
     */
    public boolean autoDetectStrainType() {
        //BLAST IT?
        return false;
    }

    public static Strain retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.STRAIN );
        cq.eq( Strain.Fields.NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Strain p = (Strain) l.get( 0 );
        return p;
    }

    /*********************************************************************
     * Remainder of Strain class is for dealing with genomic modifications
     * in composite strains
     *********************************************************************/
    /**
     * Method for adding a genome modification (a Plasmid incorporated in the genome which
     * includes all mutations, insertions, and deletions) when its position in the genome
     * is known
     *
     * @param aplas the Plasmid in the genome
     * @param start start of the sequence in coordinates of the reference genbank file
     * @param end end of the sequence in ccordinates of the reference genbank file
     * @param revcomp whether the sequence is reverse complemented relative to the reference genbank file
     */
    public boolean addGenomeModification( Plasmid aplas, int start, int end, boolean revcomp ) {
        if ( getGenbankFile() == null ) {
            //   return false;
        }
        final genomeModification gm = new genomeModification( aplas.getUUID(), start, end, revcomp );

        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _strDatum._genModLinks.remove( gm );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _strDatum._genModLinks.add( gm );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
            }
        };
        addUndo( undo, redo );

        this._strDatum._genModLinks.add( gm );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        return true;
    }

    /**
     * Method for adding a genome modification (a Plasmid incorporated in the genome which
     * includes all mutations, insertions, and deletions) when it is not known where the
     * sequence is in the genome, or the genbank reference sequence is unavailable
     *
     * @param aplas the Plasmid in the genome
     * @param start start of the sequence in coordinates of the reference genbank file
     * @param end end of the sequence in ccordinates of the reference genbank file
     * @param revcomp whether the sequence is reverse complemented relative to the reference genbank file
     */
    public boolean addGenomeModification( Plasmid aplas ) {
        final genomeModification gm = new genomeModification( aplas.getUUID(), -1, -1, false );

        ActionListener undo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _strDatum._genModLinks.remove( gm );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
            }
        };
        ActionListener redo = new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                _strDatum._genModLinks.add( gm );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
            }
        };
        addUndo( undo, redo );

        _strDatum._genModLinks.add( gm );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        return true;
    }


    public String getGenomeID() {
        return _strDatum._genomeId;
    }

    public class genomeModification {

        /**
         * Constructor for connections only
         * @param plasuuid
         * @param start
         * @param end
         * @param revcomp
         */
        public genomeModification( String plasuuid, int start, int end, boolean revcomp ) {
            _plasmidLink = plasuuid;
            _startOnGenome = start;
            _endOnGenome = end;
            _isRevComp = revcomp;
        }

        public Plasmid getPlasmid() {
            return Collector.getPlasmid( _plasmidLink );
        }

        public String getPlasmidLink() {
            return _plasmidLink;
        }

        public int getStartOnGenome() {
            return _startOnGenome;
        }

        public int getEndOnGenome() {
            return _endOnGenome;
        }

        public boolean isRevComp() {
            return _isRevComp;
        }

        public void changePosition( final int start, final int end, final boolean revcomp ) {
            final int oldstart = _startOnGenome;
            final int oldend = _endOnGenome;
            final boolean oldrevComp = _isRevComp;

            ActionListener undo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _startOnGenome = oldstart;
                    _endOnGenome = oldend;
                    _isRevComp = oldrevComp;
                }
            };
            ActionListener redo = new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    _startOnGenome = start;
                    _endOnGenome = end;
                    _isRevComp = revcomp;
                    _isChanged = true;
                }
            };
            addUndo( undo, redo );
            redo.actionPerformed( null );

            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        }

        public void autoAnnotationOnGenome() {
            throw new UnsupportedOperationException( "Not supported yet." );
        }
        public String _plasmidLink;
        public int _startOnGenome;
        public int _endOnGenome;
        public boolean _isRevComp;
        public boolean _isChanged = false;
    }


    /*-----------------
    variables
    -----------------*/
    private StrainDatum _strDatum;

    public static class StrainDatum extends ObjBaseDatum {

        public boolean _isBasic = true;  //Default is a basic Strain
        public boolean _isLocked = false; //locked when a Sample is made from it
        public short _riskGroup = -1;
        public strainType _strainType;
        public String _genomeId;                  //Only populated for basic
        public String _description;
        public String _genbankFileUUID;           //Only populated for basic
        public String _authorUUID;
        public String _parentStrain;              //Only populated for composite
        public HashSet<String> _noteLinks = new HashSet<String>();
        public HashSet<String> _episomeLinks = new HashSet<String>();             //Only populated for composite, links to episomal plasmids
        public HashSet<genomeModification> _genModLinks = new HashSet<genomeModification>();  //Only populated for composite, links to genomic plasmids
        public HashSet<String> _sampleLinks = new HashSet<String>();

        @Override
        public ObjType getType() {
            return ObjType.STRAIN;
        }

        public static final Strain staticStrain = new Strain();
    }

    public enum strainType {
        BACILLUS_SUBTILIS, ESCHERICHIA_COLI, SACCHAROMYCES_CEREVISIAE, HOMO_SAPIEN, RATTUS_RATTUS,
        MUS_MUSCULUS, MONOSIGA_BREVICOLLIS, TRICHOPLAX_ADHAERENS, SYNECHOCOCCUS_ELONGATUS, SCHIZOSACCHAROMYCES_POMBE,
        MYCOPLASMA_MYCOIDES, MESOPLASMA_FLORUM, CHLAMYDOMONAS_REINHARDTII,
        OTHER
    };

    /******* FIELDS *******/
    public enum Fields {

        NAME,
        DESCRIPTION,
        DATE_CREATED,
        LAST_MODIFIED,
        IS_BASIC,
        STRAIN_TYPE,
        RISK_GROUP,
        GENBANK_ID,
        PARENT_STRAIN,
        CHILD_STRAINS,
        GENBANK_FILE,
        AUTHOR

    }
}
