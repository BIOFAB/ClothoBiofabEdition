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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;
import org.openide.util.Exceptions;
import java.lang.*;
/**
 *
 * @author J. Christopher Anderson
 */
public class Attachment extends ObjBase {

    public Attachment( AttachmentDatum d ) {
        super( d );
        _attachDatum = d;
    }

    /**
     * Constructor from raw data.  The File given as Attachment is copied
     * and then the link is stored.  Will be sent up to database if object is
     * committed.
     *
     * @param attfile the File that will become the Attachment
     * @param filename
     * @param type
     */
    public Attachment( File attfile, String filename, AttachmentType type ) {
        super();
        _attachDatum = new AttachmentDatum();
        _datum = _attachDatum;
        Attachment prexistingSeq = retrieveByName( filename );
        String newname = filename;
        while ( prexistingSeq != null ) {
            newname = JOptionPane.showInputDialog( "An attachment with the filename " + newname + " already exists, please give me a new file name." );
            if(newname==null) {
                return;
            }
            prexistingSeq = retrieveByName( newname );
        }
        _datum.name = newname;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _attachDatum.uuid = _uuid;
        _attachDatum._attType = type;

        /*
        //Copy the _myFile into the new filename in cache directory
        _attachDatum._myFile = new File( cacheDir + "\\" + filename );
        */

        // Do the above, but in a way more portable across operating systems
        if ( cacheDir.getName().endsWith(File.separator) ) {
            // do nothing; path ends with a separator already
        } else {
            _attachDatum._myFile = new File( cacheDir + File.separator + filename );
        }

        copy( attfile, _attachDatum._myFile );

    }

    /**
     * Generic _myFile copier -- doesn't care what type of _myFile things are
     * @param fromFile
     * @param toFile
     * @return
     */
    private static boolean copy( File fromFile, File toFile ) {
        if ( !fromFile.exists() ) {
            System.out.println( "Attachment copy: File didn't exist" );
            return false;
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream( fromFile );
            to = new FileOutputStream( toFile );
            byte[] buffer = new byte[ 4096 ];
            int bytesRead;

            while ( (bytesRead = from.read( buffer )) != -1 ) {
                to.write( buffer, 0, bytesRead ); // write
            }
        } catch ( IOException ex ) {
            Exceptions.printStackTrace( ex );
        } finally {
            if ( from != null ) {
                try {
                    from.close();
                } catch ( IOException e ) {
                }
            }
            if ( to != null ) {
                try {
                    to.close();
                } catch ( IOException e ) {
                }
            }
        }
        return true;
    }

    private byte[] convertToBytes() {
        byte[] b = new byte[ (int) _attachDatum._myFile.length() ];
        try {
            FileInputStream fileInputStream = new FileInputStream( _attachDatum._myFile );
            fileInputStream.read( b );
            for ( int i = 0; i < b.length; i++ ) {
                System.out.print( (char) b[i] );
            }
        } catch ( FileNotFoundException e ) {
            System.out.println( "File Not Found." );
            e.printStackTrace();
        } catch ( IOException e1 ) {
            System.out.println( "Error Reading The File." );
            e1.printStackTrace();
        }
        return b;
    }

    /**
     * Converts a byte array to a file.  (this isn't really an API
     * method, just a utility function)
     * @param file the file to write
     * @param bytes the byte array to write
     */
    public static void convertToFile( File file,byte[] bytes ) {
        try {

            // Get path and convert it into a form that is compatible with the operating system we are running on.
            String tmp = new String( file.getAbsolutePath() );

            // If path contains separators, replace them with the particular separator for the OS we are running on.
            // Use replace instead of replaceAll: match a char rather than a regex, which crashes on Windows.
            if ( tmp.contains("\\/")) {
                // Replace all / with the appropriate separator.
                tmp = tmp.replace("\\/", File.separator);
            }

            if (tmp.contains("\\")) {
                // Replace all \ with the appropriate separator.
                tmp = tmp.replace("\\", File.separator);
            }

            
            // Use the portable name henceforth.
            File portableFile = new File(tmp);
            
            FileOutputStream fos = new FileOutputStream( portableFile );
            fos.write( bytes );
            fos.close();
            
        } catch ( FileNotFoundException ex ) {
            System.out.println( "FileNotFoundException : " + ex );
        } catch ( IOException ioe ) {
            System.out.println( "IOException : " + ioe );
        }
    }

    /* SETTERS
     * */
    public void setWikiText( WikiText wiki ) {
        _attachDatum._wikiUUID = wiki.getUUID();
        setChanged(RefreshEvent.Condition.WIKITEXT_CHANGED);
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        return false;
    }

    /* GETTERS
     * */
    @Override
    public ObjType getType() {
        return ObjType.ATTACHMENT;
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
        datahash.put( "uuid", _attachDatum.uuid );
        datahash.put( "name", _attachDatum.name );

        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss", Locale.US );
        String createdate = sdf.format( _attachDatum.dateCreated );
        datahash.put( "_dateCreated", createdate );

        String lastdate = sdf.format( _attachDatum.lastModified );
        datahash.put( "_lastModified", lastdate );

        datahash.put( "_attType", _attachDatum._attType.toString() );
        datahash.put( "_myFile", _attachDatum._myFile.getName() );

        //NEED TO DO SOMETHING ABOUT THE ATTACHMENT FILE, THINK JUST INCLUDE A COPY OF IT ON SAVE
        //ITS GOING TO BE MESSY TO STUFF IT INTO THIS

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Return the datahash
        return allObjects;
    }

    /**
     * Used to retrieve the _myFile stored in the cache
     * @return the file for this attachment
     */
    public File getFile() {
        return _attachDatum._myFile;
    }

    /**
     * Returns the WikiText that refers to this attachment
     * @return a WikiText that links to this attachment
     */
    public WikiText getWiki() {
        return Collector.getWikiText( _attachDatum._wikiUUID );
    }

    /**
     * Get the type of the attachment
     * @return a String of the attachment type
     */
    public String getAttachmentTypeAsString() {
        return _attachDatum._attType.toString();
    }

    /**
     * Get the type of the attachment as an enum
     * @return the AttachmentType enum
     */
    public AttachmentType getAttachmentType() {
        return _attachDatum._attType;
    }

    /**
     * Get the attachment file as a Byte array
     * @return a Byte array of the attachment
     */
    public byte[] getBytes() {
        return FileToBytes( _attachDatum._myFile );
    }

    /**
     * Utility method for converting a file to a byte array
     * @param file the file to be converted
     * @return a byte array
     */
    public static byte[] FileToBytes( File file ) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( file );
            //InputStream in = resource.openStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[ 1024 ];
            try {
                for ( int readNum; (readNum = fis.read( buf )) != -1; ) {
                    bos.write( buf, 0, readNum );
                    System.out.println( "read " + readNum + " bytes," );
                }
            } catch ( IOException ex ) {
            }
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch ( FileNotFoundException ex ) {
            Exceptions.printStackTrace( ex );
        } finally {
            try {
                fis.close();
            } catch ( IOException ex ) {
                Exceptions.printStackTrace( ex );
            }
        }
        return null;
    }

    public static Attachment retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.ATTACHMENT );
        cq.contains( Attachment.Fields.NAME, name, false );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Attachment p = (Attachment) l.get( 0 );
        return p;
    }

    Attachment duplicate() {
        Attachment att = new Attachment(_attachDatum._myFile, "duplicateattachment", _attachDatum._attType );
        att._attachDatum.name = this._attachDatum.name;
        return att;
    }

    /*-----------------
    variables
    -----------------*/
    public static enum AttachmentType {

        ABI, PDF, PNG, EXCEL, GB, OTHER
    };

    private AttachmentDatum _attachDatum;
    public static final File cacheDir = new File("cache");
    static {
        if(!cacheDir.exists()) {
            cacheDir.mkdir();
        }
    }


    public static class AttachmentDatum extends ObjBaseDatum {

        public String _wikiUUID;
        public AttachmentType _attType;
        public File _myFile;

        @Override
        public ObjType getType() {
            return ObjType.ATTACHMENT;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        ATTACHMENT_TYPE,
        FILE,
        WIKITEXT
    }
}
