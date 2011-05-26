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
ENHANCEMENTS, OR MODIFICATIONS.
 */
package org.clothocad.hibernate;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.swing.filechooser.FileFilter;
import org.clothocore.api.data.ObjBase.ObjBaseDatum;
import org.clothocad.hibernate.data.*;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.data.*;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author Bing Xia
 */
public class HibernateConnection implements ClothoConnection {

    public HibernateConnection() {
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean isAClothoDatabase() {
        return true;
    }

    public static List<FileObject> getDefaultMappings() {
        FileObject mappingDir = FileUtil.getConfigFile( "data/private/org.clothocad.hibernate" );
        return Arrays.asList( mappingDir.getChildren() );
    }

    public boolean connect( URL hibernateXML, List<URL> mappings ) {
        // Create a new configuration and configures it.
        Configuration config = new Configuration();
        for ( URL map : mappings ) {
            config.addURL( map );
        }

        try {
            // Attempt to create a session factory from the configuration.
            // As long as this session factory is active, the connection is
            // defined as active.
            config.configure( hibernateXML );
            fac = config.buildSessionFactory();
            HibernateConnection.connection = this;
            return true;
        } catch ( Throwable e ) {
            System.err.println( e );
            e.printStackTrace();
            fac = null;
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            fac.close();
            return true;
        } catch ( Throwable e ) {
            e.printStackTrace();
            System.err.println( e );
            return false;
        } finally {
            fac = null;
        }
    }

    @Override
    public boolean isConnected() {
        return fac != null;
    }

    @Override
    public boolean save( ObjBase obj ) {
        SearchtagTable.updateSearchTags( obj );  //JCA:  Relays to save search tags for object
        if ( obj.getType() == ObjType.PART ) {
            //TODO: figure out a better way...if there is one
            return savePart( (Part) obj );
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = fac.openSession();
            Object toSave = getHibernateDatum( obj, session );  //This will instantiate the objbaseTable constructor
            if ( toSave == null ) {
                return false;
            }

            //Do the save from the hibernateDatum constructor
            transaction = session.beginTransaction();
            hibernateDatum d = (hibernateDatum) toSave;
            System.out.println( "hibernateconnectionplugin will save" + d.toString() + "  " + d.getName() + " " + d.getUUID() ); // FIXME: remove in final version
            session.saveOrUpdate( toSave );
            transaction.commit();

            //Do any secondary processing saves for new objBase's xrefs
            if(d.needsSecondaryProcessing()) {
                transaction = session.beginTransaction();
                d.runSecondaryProcessing(obj);
                session.saveOrUpdate( toSave );
                transaction.commit();
            }
            
            session.close();
            activeSession = null;
            return true;
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( transaction != null && transaction.isActive() ) {
                try {
                    transaction.rollback();
                } catch ( HibernateException e1 ) {
                    System.err.println( "Rollback failed" );
                }
            }
        }
        return false;
    }

    /**
     * For saving a Part, since the order of operations for this save
     * doesn't fit well into the normal save method.
     * @param pt
     * @return
     */
    boolean savePart( Part pt ) {
        Session s = null;
        Transaction t = null;
        try {
            List<String> newXrefs = pt.getComposition();
            s = fac.openSession();

            if ( pt.getPartType() == Part.partType.Composite ) {

                PartTable existingPart = (PartTable) getHibernateDatum( ObjType.PART, pt.getUUID(), s );

                if ( existingPart != null ) {
                    // Part already exists
                    // Check if all the xrefs match perfectly
                    boolean changed = false;
                    Set<CompositeXref> curXrefs = existingPart.getCompositeXrefsForChildPart();

                    System.out.println( curXrefs.size() + " " + newXrefs.size() );
                    if ( curXrefs.size() == newXrefs.size() ) {
                        String[] curXrefUUIDs = new String[ curXrefs.size() ];
                        for ( CompositeXref xr : curXrefs ) {
                            String parentUUID = xr.getPartTableByParentPart().getUUID();
                            curXrefUUIDs[xr.getPosition()] = parentUUID;
                        }

                        for ( int i = 0; i < curXrefUUIDs.length; i++ ) {
                            if ( !newXrefs.get( i ).equals( curXrefUUIDs[i] ) ) {
                                changed = true;
                            }
                        }
                    } else {
                        changed = true;
                    }

                    if ( changed ) {
                        System.out.println( "changed" );
                        for ( CompositeXref xr : curXrefs ) {
                            System.out.println( "deleting " + xr );
                            t = s.beginTransaction();
                            s.delete( xr );
                            t.commit();
                        }

                        for ( int i = 0; i < newXrefs.size(); i++ ) {
                            String uuid = newXrefs.get( i );
                            CompositeXref cxr = new CompositeXref( UUID.randomUUID().toString(), new PartTable( uuid ), existingPart, i );
                            t = s.beginTransaction();
                            s.save( cxr );
                            t.commit();
                        }
                    }
                } else {
                    // Part doesn't exist yet...have to save it, then save the xrefs
                    t = s.beginTransaction();
                    PartTable toSave = new PartTable( pt );
                    s.save( toSave );
                    t.commit();

                    // Save the xrefs

                    for ( int i = 0; i < newXrefs.size(); i++ ) {
                        CompositeXref xr = new CompositeXref( UUID.randomUUID().toString(), new PartTable( newXrefs.get( i ) ), toSave, i );
                        t = s.beginTransaction();
                        s.save( xr );
                        t.commit();
                    }
                }

            } else {
                // Not a composite, can save much more simply
                PartTable toSave = new PartTable( pt );
                t = s.beginTransaction();
                s.saveOrUpdate( toSave );
                t.commit();
            }
            s.close();

            return true;
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( t != null && t.isActive() ) {
                try {
                    t.rollback();
                } catch ( HibernateException e1 ) {
                    System.err.println( "Rollback failed" );
                }
            }
        }

        return false;
    }

    public boolean saveDatum( Object toSave ) {
        Session s = null;
        Transaction t = null;
        try {
            s = fac.openSession();
            t = s.beginTransaction();
            if ( toSave != null ) {
                s.saveOrUpdate( toSave );
            } else {
                return false;
            }
            t.commit();
            s.close();
            return true;
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( t != null && t.isActive() ) {
                try {
                    t.rollback();
                } catch ( HibernateException e1 ) {
                    System.err.println( "Rollback failed" );
                }
            }
        }
        return false;
    }

    public boolean deleteDatum( Object toDelete ) {
        Session s = null;
        Transaction t = null;
        try {
            boolean startedsession = false;;
            if(activeSession!=null) {
                s = activeSession;
            } else {
                s = fac.openSession();
                startedsession = true;
            }
            t = s.beginTransaction();
            if ( toDelete != null ) {
                s.delete( toDelete );
            } else {
                return false;
            }
            t.commit();
            if(startedsession) {
                s.close();
            }
            return true;
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( t != null && t.isActive() ) {
                try {
                    t.rollback();
                } catch ( HibernateException e1 ) {
                    System.err.println( "Rollback failed" );
                }
            }
        }
        return false;
    }

    @Override
    public int save( Collection<ObjBase> objs ) {
        Session session = null;
        Transaction transaction = null;
        int succeeded = 0;
        try {
            session = fac.openSession();
            for ( ObjBase obj : objs ) {
                hibernateDatum toSave = getHibernateDatum( obj, session );
                if ( toSave != null ) {
                    transaction = session.beginTransaction();
                    System.out.println( "hibernateconnectionplugin will save" + toSave.toString() + "  " + toSave.getName() + " " + toSave.getUUID() );
                    session.saveOrUpdate( toSave );
                    succeeded++;
                    transaction.commit();
                }
            }
            session.close();
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( transaction != null && transaction.isActive() ) {
                try {
                    transaction.rollback();
                    return 0;
                } catch ( HibernateException e1 ) {
                    session.close();
                    System.err.println( "Rollback failed" );
                }
            }
        }
        return succeeded;
    }

    @Override
    public ObjBaseDatum getDatum(ObjType type, String uuid) {
        Session s = fac.openSession();
        hibernateDatum data = getHibernateDatum( type, uuid, s );
        ObjBaseDatum out = null;
        if ( data != null ) {
            out = data.getObjBaseDatum();
        } else {
            out = null;
        }
        s.close();
        return out;
    }

    @Override
    public boolean delete( ObjBase obj ) {
        System.out.println( "Delete got called" );
        if ( obj == null ) {
            System.out.println( "Delete but ObjBase is null" );
            return false;
        }

        //Query for any CollectionXref links and delete them
        String query = "from Collectionxref where objectId='" + obj.getType() + "'";
        Iterator xrefs = query( query );
        while ( xrefs.hasNext() ) {
            Object xr = xrefs.next();
            Collectionxref csr = (Collectionxref) xr;
            System.out.println( "Delete found an xref, and its deleting it" );
            HibernateConnection.connection.deleteDatum( csr );
        }

        Session s = null;
        Transaction t = null;
        try {
            System.out.println( "Delete doing a transaction" );
            s = fac.openSession();
            t = s.beginTransaction();
            if ( obj != null ) {
                s.delete( getEmptyDatum( obj ) );
            } else {
                return false;
            }
            t.commit();
            s.close();
            System.out.println( "Delete returning true" );
            return true;
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( t != null && t.isActive() ) {
                try {
                    t.rollback();
                } catch ( HibernateException e1 ) {
                    System.err.println( "Rollback failed" );
                }
            }
        }
        System.out.println( "Delete returning false" );
        return false;
    }

    @Override
    public int delete( Collection<ObjBase> objs ) {
        Session session = null;
        Transaction transaction = null;
        int succeeded = 0;
        try {
            session = fac.openSession();
            transaction = session.beginTransaction();
            for ( ObjBase obj : objs ) {
                Object toDelete = getEmptyDatum( obj );
                if ( toDelete != null ) {
                    session.delete( toDelete );
                    succeeded++;
                }
            }
            transaction.commit();
            session.close();
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            System.err.println( "Commit failed!" );
            if ( transaction != null && transaction.isActive() ) {
                try {
                    transaction.rollback();
                    return 0;
                } catch ( HibernateException e1 ) {
                    session.close();
                    System.err.println( "Rollback failed" );
                }
            }
        }
        return succeeded;
    }

    /**
     * Retrieves an ObjBase object from the database using a uuid
     * @param type
     * @param uuid
     * @return
     */
    @Override
    public ObjBase get( ObjType type, String uuid ) {
        Session s = fac.openSession();
        hibernateDatum d = getHibernateDatum( type, uuid, s );
        ObjBase result = null;
        if ( d != null ) {
            result = d.getObject();
            SearchtagTable.pullSearchTags( result );
        }
        s.close();
        return result;
    }

    @Override
    public Collection<ObjBase> get( String hibQuery ) {
        Collection<ObjBase> objs = new ArrayList<ObjBase>();
        Session s = fac.openSession();

        Transaction transaction = s.beginTransaction();
        Query q = s.createQuery( hibQuery );
        Iterator<hibernateDatum> results = q.iterate();
        while ( results.hasNext() ) {
            hibernateDatum d = results.next();
            ObjBase obj = d.getObject();
            SearchtagTable.pullSearchTags( obj );
            objs.add( obj );
        }
        transaction.commit();
        return objs;
    }

    @Override
    public ArrayList<ObjLink> getAllLinks( ObjType type ) {
        ArrayList<ObjLink> out = new ArrayList<ObjLink>();
        System.out.println( "********* Getting all " + type.toString() + ":" );

        Iterator collIT = findAll( type );
        while ( collIT.hasNext() ) {
            hibernateDatum datum = (hibernateDatum) collIT.next();

            String name = datum.getName();
            String uuid = datum.getUUID();

            out.add( new ObjLink( uuid, type, name ) );
        }
        return out;
    }

    @Override
    public String[][] getTableAsArray( ObjType type ) {
        ArrayList<String[]> templist = new ArrayList<String[]>();

        Iterator collIT = findAll( type );
        while ( collIT.hasNext() ) {
            hibernateDatum datum = (hibernateDatum) collIT.next();

            String[] aline = new String[ 3 ];
            aline[0] = datum.getUUID();
            aline[1] = datum.getName();

            switch ( type ) {
                case FEATURE:
                    FeatureTable ft = (FeatureTable) datum;
                    aline[2] = ft.getNucseqTable().getSequence();
                    break;
                default:
                    aline[2] = "";
            }

            templist.add( aline );
        }

        String[][] out = new String[ templist.size() ][ 3 ];
        for ( int i = 0; i < templist.size(); i++ ) {
            out[i] = templist.get( i );
        }

        return out;
    }

    /**
     * Returns all the objects of the given type.
     * @param type - Object keyword
     * @return - Iterator giving results
     */
    private Iterator<hibernateDatum> findAll( ObjType type ) {
        Session session = null;
        Transaction transaction = null;
        session = fac.openSession();
        Transaction t = session.beginTransaction();
        String tabletoprobe = getObjectTranslation( type );
        System.out.println( "Probing " + tabletoprobe );

        Query q = session.createQuery( "from " + tabletoprobe );
        Iterator<hibernateDatum> it = q.iterate();

        t.commit();
        return it;
    }

    /**
     * JCA added this.  Used for queries within the connection.
     * @param hibQuery
     * @return
     */
    public Iterator query( String hibQuery ) {
        Session session = null;
        Transaction transaction = null;
        session = fac.openSession();
        activeSession = session;
        Transaction t = session.beginTransaction();
        Query q = session.createQuery( hibQuery );
        Iterator it = q.iterate();
        t.commit();
        return it;
    }

    @Override
    public void init() {
        // In the future, add preference loading and such
    }

    public void configure() {
//        _configurationViewer = new ConfigurationView( this );
//        _configurationViewer.setVisible( true );
    }

    @Override
    public Date getTimeModified( ObjBase obj ) {
        String query = "";

        //JCA:  I have this if here since there is a typo in the field name of nucseq.  Worth fixing eventually.
        if ( obj.getType().equals( ObjType.NUCSEQ ) ) {
            query = "select lastmodified from " + getObjectTranslation( obj.getType() ) + " where id='" + obj.getUUID() + "'";
        } else {
            query = "select lastModified from " + getObjectTranslation( obj.getType() ) + " where id='" + obj.getUUID() + "'";
        }

        Session session = null;
        Transaction transaction = null;
        try {
            session = fac.openSession();

            transaction = session.beginTransaction();
            Date lastMod = (Date) session.createQuery( query ).uniqueResult();
            transaction.commit();
            session.close();
            return lastMod;
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ClothoConnection.ClothoQuery createQuery( ObjType type ) {
        return new HibernateQuery( type );
    }

    /**
     * Use this to get a datum object built from a given ObjBase object. The
     * resulting hibernateDatum object can be saved using hibernate. There should be one
     * case here for each API type that is valid.
     * @param obj
     * @return
     */
    hibernateDatum getHibernateDatum( ObjBase obj, Session s ) {
        if ( obj == null ) {
            return null;
        }
        switch ( obj.getType() ) {
            case ANNOTATION:
                return new NucseqAnnotation( (Annotation) obj );
            case ATTACHMENT:
                return new AttachmentTable( (Attachment) obj );
            case COLLECTION:
                return new CollectionTable( (org.clothocore.api.data.Collection) obj );
            case CONTAINER:
                return new ContainerTable( (Container) obj );
            case FACTOID:
                return new FactoidTable( (Factoid) obj );
            case FAMILY:
                return new FamilyTable( (Family) obj );
            case FEATURE:
                return new FeatureTable( (Feature) obj );
            case FLEX_FIELD:
                return new FlexfieldTable( (FlexField) obj );
            case FORMAT:
                return new FormatTable( (Format) obj );
            case GRAMMAR:
                return new GrammarTable( (Grammar) obj );
            case INSTITUTION:
                return new InstitutionTable( (Institution) obj );
            case LAB:
                return new LabTable( (Lab) obj );
            case NUCSEQ:
                return new NucseqTable( (NucSeq) obj );
            case NOTE:
                return new NoteTable( (Note) obj );
            case OLIGO:
                return new OligoTable( (Oligo) obj );
            case PART:
                // ASSERT: never reached, because parts are handled differently
                return new PartTable( (Part) obj );
            case PERSON:
                return new PersonTable( (Person) obj );
            case PLASMID:
                return new PlasmidTable( (Plasmid) obj );
            case PLATE:
                return new PlateTable( (Plate) obj );
            case PLATE_TYPE:
                return new PlateTypeTable( (PlateType) obj );
            case SAMPLE:
                return new SampleTable( (Sample) obj );
            case SAMPLE_DATA:
                return new SampleDataTable( (SampleData) obj );
            case STRAIN:
                return new StrainTable( (Strain) obj );
            case WIKITEXT:
                return new WikitextTable( (WikiText) obj );
            case VECTOR:
                return new VectorTable( (Vector) obj );
            default:
                return null;
        }
    }

    /**
     * Use this to create an empty hibernateDatum object for the purpose of deleting or
     * saving a link. The returned hibernateDatum will be of the right type and only
     * have its UUID field filled in.
     * @param obj
     * @return
     */
    hibernateDatum getEmptyDatum( ObjBase obj ) {
        if ( obj == null ) {
            return null;
        }
        switch ( obj.getType() ) {
            case ANNOTATION:
                return new NucseqAnnotation( obj.getUUID() );
            case ATTACHMENT:
                return new AttachmentTable( obj.getUUID() );
            case COLLECTION:
                return new CollectionTable( obj.getUUID() );
            case CONTAINER:
                return new ContainerTable( obj.getUUID() );
            case FACTOID:
                return new FactoidTable( obj.getUUID() );
            case FAMILY:
                return new FamilyTable( obj.getUUID() );
            case FEATURE:
                return new FeatureTable( obj.getUUID() );
            case FLEX_FIELD:
                return new FlexfieldTable( obj.getUUID() );
            case FORMAT:
                return new FormatTable( obj.getUUID() );
            case GRAMMAR:
                return new GrammarTable( obj.getUUID() );
            case INSTITUTION:
                return new InstitutionTable( obj.getUUID() );
            case LAB:
                return new LabTable( obj.getUUID() );
            case NUCSEQ:
                return new NucseqTable( obj.getUUID() );
            case NOTE:
                return new NoteTable( obj.getUUID() );
            case OLIGO:
                return new OligoTable( obj.getUUID() );
            case PART:
                return new PartTable( obj.getUUID() );
            case PERSON:
                return new PersonTable( obj.getUUID() );
            case PLASMID:
                return new PlasmidTable( obj.getUUID() );
            case PLATE:
                return new PlateTable( obj.getUUID() );
            case PLATE_TYPE:
                return new PlateTypeTable( obj.getUUID() );
            case SAMPLE:
                return new SampleTable( obj.getUUID() );
            case SAMPLE_DATA:
                return new SampleDataTable( obj.getUUID() );
            case STRAIN:
                return new StrainTable( obj.getUUID() );
            case WIKITEXT:
                return new WikitextTable( obj.getUUID() );
            case VECTOR:
                return new VectorTable( obj.getUUID() );
            default:
                return null;
        }
    }

    /**
     * Use this to get the datum object of the given type from the database. The
     * resulting object can be used to create an ObjBase object to return, or to
     * update an existing ObjBase object.
     *
     * openSession specifies whether this method needs to open a session, or whether
     * a session has already been opened outside this method.
     * @param type
     * @param uuid
     * @param openSession
     * @return
     */
    hibernateDatum getHibernateDatum( ObjType type, String uuid, Session session ) {
        try {
            boolean openedSession = false;
            if ( session == null ) {
                session = fac.openSession();
                openedSession = true;
            }
            Transaction transaction = session.beginTransaction();
            Query q = session.createQuery( "from " + getObjectTranslation( type ) + " where id='" + uuid + "'" );
            hibernateDatum d = (hibernateDatum) q.uniqueResult();
            transaction.commit();
            if ( openedSession ) {
                session.close();
            }
            return d;
        } catch ( Throwable e ) {
            System.err.println( e ); // TODO: do some error output
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a select query for the given ObjBase, assuming its in the database.
     * Uses the uuid of the given object along with its type to build the query.
     * @param obj
     * @return
     */
    String getSelectQuery( ObjBase obj ) {
        String uuid = obj.getUUID();
        if ( uuid == null ) {
            return null;
        }
        String typeTranslation = getObjectTranslation( obj.getType() );
        if ( typeTranslation == null ) {
            return null;
        }
        return "from " + typeTranslation + " where id='" + uuid + "'";

    }

    /**
     * Returns the database translation for the given type
     * @param type
     * @return
     */
    @Override
    public String getObjectTranslation( ObjType type ) {
        switch ( type ) {
            case ANNOTATION:
                return "NucseqAnnotation";
            case ATTACHMENT:
                return "AttachmentTable";
            case COLLECTION:
                return "CollectionTable";
            case CONTAINER:
                return "ContainerTable";
            case FACTOID:
                return "FactoidTable";
            case FAMILY:
                return "FamilyTable";
            case FEATURE:
                return "FeatureTable";
            case FLEX_FIELD:
                return "FlexFieldTable";
            case FORMAT:
                return "FormatTable";
            case GRAMMAR:
                return "GrammarTable";
            case INSTITUTION:
                return "InstitutionTable";
            case LAB:
                return "LabTable";
            case NUCSEQ:
                return "NucseqTable";
            case NOTE:
                return "NoteTable";
            case OLIGO:
                return "OligoTable";
            case PART:
                return "PartTable";
            case PERSON:
                return "PersonTable";
            case PLASMID:
                return "PlasmidTable";
            case PLATE:
                return "PlateTable";
            case PLATE_TYPE:
                return "PlateTypeTable";
            case SAMPLE:
                return "SampleTable";
            case SAMPLE_DATA:
                return "SampleDataTable";
            case STRAIN:
                return "StrainTable";
            case VECTOR:
                return "VectorTable";
            case WIKITEXT:
                return "WikitextTable";
            default:
                return null;
        }
    }

    /**
     * Returns the field name of the given field type of the given object type.
     * This isn'transaction needed except for the HibernateQuery type searching of the database.
     * @param type
     * @param field
     * @return
     */
    @Override
    public String getFieldTranslation( ObjType type, Enum field ) {
        String translation = null;
        System.out.println("getfieldTranslation of " + type + field);
        switch ( type ) {
            case ANNOTATION:
                translation = NucseqAnnotation.translate( field );
                break;
            case ATTACHMENT:
                translation = AttachmentTable.translate( field );
                break;
            case COLLECTION:
                translation = CollectionTable.translate( field );
                break;
            case CONTAINER:
                translation = ContainerTable.translate( field );
                break;
            case FACTOID:
                translation = FactoidTable.translate( field );
                break;
            case FAMILY:
                translation = FamilyTable.translate( field );
                break;
            case FEATURE:
                translation = FeatureTable.translate( field );
                break;
            case FLEX_FIELD:
                translation = FlexfieldTable.translate( field );
                break;
            case FORMAT:
                translation = FormatTable.translate( field );
                break;
            case GRAMMAR:
                translation = GrammarTable.translate( field );
                break;
            case INSTITUTION:
                translation = InstitutionTable.translate( field );
                break;
            case LAB:
                translation = LabTable.translate( field );
                break;
            case NOTE:
                translation = NoteTable.translate( field );
                break;
            case NUCSEQ:
                translation = NucseqTable.translate( field );
                break;
            case OLIGO:
                translation = OligoTable.translate( field );
                break;
            case PART:
                translation = PartTable.translate( field );
                break;
            case PERSON:
                translation = PersonTable.translate( field );
                break;
            case PLASMID:
                translation = PlasmidTable.translate( field );
                break;
            case PLATE:
                translation = PlateTable.translate( field );
                break;
            case PLATE_TYPE:
                translation = PlateTypeTable.translate( field );
                break;
            case SAMPLE:
                translation = SampleTable.translate( field );
                break;
            case SAMPLE_DATA:
                translation = SampleDataTable.translate( field );
                break;
            case STRAIN:
                translation = StrainTable.translate( field );
                break;
            case VECTOR:
                translation = VectorTable.translate( field );
                break;
            case WIKITEXT:
                translation = WikitextTable.translate( field );
                break;
            default:
                translation = null;
        }

        if ( translation == null ) {
            // TODO: signal an error somehow, but this should never happen
        }

        return translation;
    }

    public ObjType getFieldType( ObjType type, Enum field ) {
        switch ( type ) {
            case ANNOTATION:
                return NucseqAnnotation.getType( field );
            case ATTACHMENT:
                return AttachmentTable.getType( field );
            case COLLECTION:
                return CollectionTable.getType( field );
            case CONTAINER:
                return ContainerTable.getType( field );
            case FACTOID:
                return FactoidTable.getType( field );
            case FAMILY:
                return FamilyTable.getType( field );
            case FEATURE:
                return FeatureTable.getType( field );
            case FLEX_FIELD:
                return FlexfieldTable.getType( field );
            case FORMAT:
                return FormatTable.getType( field );
            case GRAMMAR:
                return GrammarTable.getType( field );
            case INSTITUTION:
                return InstitutionTable.getType( field );
            case LAB:
                return LabTable.getType( field );
            case NOTE:
                return NoteTable.getType( field );
            case NUCSEQ:
                return NucseqTable.getType( field );
            case OLIGO:
                return OligoTable.getType( field );
            case PART:
                return PartTable.getType( field );
            case PERSON:
                return PersonTable.getType( field );
            case PLASMID:
                return PlasmidTable.getType( field );
            case PLATE:
                return PlateTable.getType( field );
            case PLATE_TYPE:
                return PlateTypeTable.getType( field );
            case SAMPLE:
                return SampleTable.getType( field );
            case SAMPLE_DATA:
                return SampleDataTable.getType( field );
            case STRAIN:
                return StrainTable.getType( field );
            case VECTOR:
                return VectorTable.getType( field );
            case WIKITEXT:
                return WikitextTable.getType( field );
            default:
                return null;
        }
    }
    /**************** PRIVATE VARIABLES ****************/
    private SessionFactory fac;
    private Session activeSession = null;
    private static FileFilter configFileFilter = new FileFilter() {

        public boolean accept( File pathname ) {
            return pathname.getName().endsWith( ".cfg.xml" );
        }

        @Override
        public String getDescription() {
            return "Filters for Hibernate Configuration Files";
        }
    };
    public static HibernateConnection connection;

    /* Preference enums */
    public enum PreferenceKeys {

        CONFIG_DIRECTORY,
    }

    /***************************************************************************
     ***************************** Inner Class **********************************
     ***************************************************************************/
    class HibernateQuery implements ClothoConnection.ClothoQuery {

        private Criteria criteria;
        private ObjType type;
        private Session session;

        HibernateQuery( ObjType type ) {
            session = fac.openSession();
            this.type = type;
            switch ( type ) {
                case ATTACHMENT:
                    criteria = session.createCriteria( AttachmentTable.class );
                    break;
                case COLLECTION:
                    criteria = session.createCriteria( CollectionTable.class );
                    break;
                case CONTAINER:
                    criteria = session.createCriteria( ContainerTable.class );
                    break;
                case FACTOID:
                    criteria = session.createCriteria( FactoidTable.class );
                    break;
                case FAMILY:
                    criteria = session.createCriteria( FamilyTable.class );
                    break;
                case FEATURE:
                    criteria = session.createCriteria( FeatureTable.class );
                    break;
                case FLEX_FIELD:
                    criteria = session.createCriteria( FlexfieldTable.class );
                    break;
                case FORMAT:
                    criteria = session.createCriteria( FormatTable.class );
                    break;
                case GRAMMAR:
                    criteria = session.createCriteria( GrammarTable.class );
                    break;
                case INSTITUTION:
                    criteria = session.createCriteria( InstitutionTable.class );
                    break;
                case LAB:
                    criteria = session.createCriteria( LabTable.class );
                    break;
                case NOTE:
                    criteria = session.createCriteria( NoteTable.class );
                    break;
                case NUCSEQ:
                    criteria = session.createCriteria( NucseqTable.class );
                    break;
                case OLIGO:
                    criteria = session.createCriteria( OligoTable.class );
                    break;
                case PART:
                    criteria = session.createCriteria( PartTable.class );
                    break;
                case PERSON:
                    criteria = session.createCriteria( PersonTable.class );
                    break;
                case PLASMID:
                    criteria = session.createCriteria( PlasmidTable.class );
                    break;
                case PLATE:
                    criteria = session.createCriteria( PlateTable.class );
                    break;
                case PLATE_TYPE:
                    criteria = session.createCriteria( PlateTypeTable.class );
                    break;
                case SAMPLE:
                    criteria = session.createCriteria( SampleTable.class );
                    break;
                case SAMPLE_DATA:
                    criteria = session.createCriteria( SampleDataTable.class );
                    break;
                case STRAIN:
                    criteria = session.createCriteria( StrainTable.class );
                    break;
                case VECTOR:
                    criteria = session.createCriteria( VectorTable.class );
                    break;
                case WIKITEXT:
                    criteria = session.createCriteria( WikitextTable.class );
                    break;
                default:
                    System.err.println( "Unrecognized type" ); // TODO: Change this to a warning dialog
            }
        }

        private HibernateQuery( ObjType t, Criteria crit ) {
            type = t;
            criteria = crit;
        }

        @Override
        public ClothoConnection.ClothoQuery setMaxResults( int max ) {
            criteria = criteria.setMaxResults( max );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery between( Enum field, Object hi, Object lo ) {
            criteria = criteria.add( Restrictions.between( getFieldTranslation( type, field ), lo, hi ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery lessThan( Enum field, Object hi ) {
            criteria = criteria.add( Restrictions.lt( getFieldTranslation( type, field ), hi ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery greaterThan( Enum field, Object lo ) {
            criteria = criteria.add( Restrictions.gt( getFieldTranslation( type, field ), lo ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery equal( Enum field, Object value ) {
            criteria = criteria.add( Restrictions.eq( getFieldTranslation( type, field ), value ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery eq( Enum field, Object value ) {
            return equal( field, value );
        }

        @Override
        public ClothoConnection.ClothoQuery notEqual( Enum field, Object value ) {
            criteria = criteria.add( Restrictions.ne( getFieldTranslation( type, field ), value ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery neq( Enum field, Object value ) {
            return notEqual( field, value );
        }

        @Override
        public ClothoConnection.ClothoQuery isNull( Enum field ) {
            criteria = criteria.add( Restrictions.isNull( getFieldTranslation( type, field ) ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery isNotNull( Enum field ) {
            criteria = criteria.add( Restrictions.isNotNull( getFieldTranslation( type, field ) ) );
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery matches( Enum field, String value, boolean caseSensitive ) {
            if ( caseSensitive ) {
                criteria = criteria.add( Restrictions.like( getFieldTranslation( type, field ), value ) );
            } else {
                criteria = criteria.add( Restrictions.ilike( getFieldTranslation( type, field ), value ) );
            }
            return this;
        }

        @Override
        public ClothoConnection.ClothoQuery contains( Enum field, String value, boolean caseSensitive ) {
            if ( value.charAt( 0 ) != '%' ) {
                value = "%" + value;
            }
            if ( value.charAt( value.length() - 1 ) != '%' ) {
                value = value + "%";
            }
            if ( caseSensitive ) {
                criteria = criteria.add( Restrictions.like( getFieldTranslation( type, field ), value ) );
            } else {
                criteria = criteria.add( Restrictions.ilike( getFieldTranslation( type, field ), value ) );
            }
            return this;
        }

        @Override
        public List<ObjBase> getResults() {
            if ( !session.isOpen() ) {
                System.out.println( "HibernateConnectionPlugin returning null since session isn't open" );
                return null; // This method should only be called once!
            }
            List<ObjBase> results = new ArrayList<ObjBase>();
            List<hibernateDatum> datums = criteria.list();

            for ( hibernateDatum d : datums ) {
                ObjBase obj = d.getObject();
                SearchtagTable.pullSearchTags( obj );
                results.add( obj );
            }
            session.close();

            return results;
        }

        @Override
        public ClothoConnection.ClothoQuery add( ClothoConnection.ClothoCriterion crit ) {
            if ( crit != null && crit.getClass().equals( HibernateCriterion.class ) ) {
                criteria = criteria.add( ((HibernateCriterion) crit)._myCriterion );
            }
            return this;
        }

        @Override
        public ClothoConnection.ClothoCriterion or( Collection<ClothoConnection.ClothoCriterion> criteria ) {
            Iterator<ClothoConnection.ClothoCriterion> it = criteria.iterator();

            Disjunction d = Restrictions.disjunction();
            while ( it.hasNext() ) {
                ClothoConnection.ClothoCriterion cc = it.next();
                if ( it.getClass().equals( HibernateCriterion.class ) ) {
                    d.add( ((HibernateCriterion) cc)._myCriterion );
                }
            }
            return new HibernateCriterion( d );
        }

        @Override
        public ClothoConnection.ClothoCriterion or( ClothoConnection.ClothoCriterion a, ClothoConnection.ClothoCriterion b ) {
            if ( !a.getClass().equals( HibernateCriterion.class )
                    || !b.getClass().equals( HibernateCriterion.class ) ) {
                return null;
            }

            HibernateCriterion ha = (HibernateCriterion) a,
                    hb = (HibernateCriterion) b;

            Disjunction d = Restrictions.disjunction();
            d.add( ha._myCriterion ).add( hb._myCriterion );
            return new HibernateCriterion( d );
        }

        @Override
        public ClothoConnection.ClothoCriterion and( Collection<ClothoConnection.ClothoCriterion> criteria ) {
            Iterator<ClothoConnection.ClothoCriterion> it = criteria.iterator();

            Conjunction d = Restrictions.conjunction();
            while ( it.hasNext() ) {
                ClothoConnection.ClothoCriterion cc = it.next();
                if ( it.getClass().equals( HibernateCriterion.class ) ) {
                    d.add( ((HibernateCriterion) cc)._myCriterion );
                }
            }
            return new HibernateCriterion( d );
        }

        @Override
        public ClothoConnection.ClothoCriterion and( ClothoConnection.ClothoCriterion a, ClothoConnection.ClothoCriterion b ) {
            if ( a == null || b == null
                    || !a.getClass().equals( HibernateCriterion.class )
                    || !b.getClass().equals( HibernateCriterion.class ) ) {
                return null;
            }

            HibernateCriterion ha = (HibernateCriterion) a,
                    hb = (HibernateCriterion) b;

            Conjunction d = Restrictions.conjunction();
            d.add( ha._myCriterion ).add( hb._myCriterion );
            return new HibernateCriterion( d );
        }

        @Override
        public ClothoConnection.ClothoCriterion not( ClothoConnection.ClothoCriterion c ) {
            if ( c != null && c.getClass().equals( HibernateCriterion.class ) ) {
                return new HibernateCriterion(
                        Restrictions.not( ((HibernateCriterion) c)._myCriterion ) );
            }
            return null;
        }

        @Override
        public ClothoConnection.ClothoCriterion getBetweenCrit( Enum field, Object hi, Object lo ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.between( f, lo, hi );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getLessThanCrit( Enum field, Object hi ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.lt( f, hi );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getGreaterThanCrit( Enum field, Object lo ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.gt( f, lo );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getEqualCrit( Enum field, Object value ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.eq( f, value );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getNotEqualCrit( Enum field, Object value ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.ne( f, value );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getIsNullCrit( Enum field ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.isNull( f );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getIsNotNullCrit( Enum field ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.isNotNull( f );

            return new HibernateCriterion( c );
        }

        @Override
        public ClothoConnection.ClothoCriterion getMatchesCrit( Enum field, Object value ) {
            String f = getFieldTranslation( type, field );

            Criterion c = Restrictions.ilike( f, value );

            return new HibernateCriterion( c );
        }

        @Override
        public ObjType getType() {
            return type;
        }

        @Override
        public ClothoQuery createAssociationQuery( Enum field ) {
            return new HibernateQuery( getFieldType( type, field ), criteria.createCriteria( getFieldTranslation( type, field ) ) );
        }
    }

    class HibernateCriterion extends ClothoConnection.ClothoCriterion {

        Criterion _myCriterion;

        protected HibernateCriterion( Criterion c ) {
            _myCriterion = c;
        }
    }
}
