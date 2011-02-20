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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;

/**
 *
 * @author J. Christopher Anderson
 */
public class WikiText extends ObjBase {

    public WikiText( WikiTextDatum d ) {
        super( d );
        _wikiDatum = d;

        //Need to make Collector retrieve all the attachments to download files
        for ( String s : _wikiDatum._attachments ) {
            Attachment att = Collector.getAttachment( s );
            if(att!=null) {
                _wikiDatum.allAvailable.put( att.getName(), att.getUUID());
            }
        }
    }

    public WikiText( String string ) {
        super( );
        _wikiDatum = new WikiTextDatum();
        _datum = _wikiDatum;
        _datum.name = "wikitext";
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _datum.uuid = _uuid;

        _wikiDatum._wikiText = string;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting wikiText save of " + getUUID() );
        if ( !isChanged() ) {
            System.out.println( "wikiText didn't require saving" );
            return true;
        }

        if ( !super.save( conn ) ) {
            return false;
        }

        for ( String s : _wikiDatum._attachments ) {
            //If the item hasn't been pulled into local memory, then don't bother saving
            if ( !Collector.isLocal( s ) ) {
                continue;
            }
            Attachment att = Collector.getAttachment( s );

            //If it's already in the database ignore it
            if ( att.isInDatabase() ) {
                continue;
            }
            System.out.println( "wikitext about to save " + att.getName() );
            if ( !att.save( conn ) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update() {
        if(!super.update()) {
            return false;
        }

        ObjBaseDatum od = Hub.defaultConnection.getDatum(getType(), getUUID());
        if(!(od instanceof WikiTextDatum)) {
            return false;
        }
        WikiTextDatum d = (WikiTextDatum) od;
        if(!d.uuid.equals( _wikiDatum.uuid)) {
            return false;
        }

        //If this ObjBase hasn't been changed, just replace the datums
        if(!_wikiDatum._isChanged) {
            d._isChanged = false;
            _wikiDatum = d;
            _datum = d;
            fireData( new RefreshEvent( this , RefreshEvent.Condition.UPDATE_ALL) );
            return true;
        }

        //Otherwise, do conflict resolution
            //Change these no matter what
            _wikiDatum._isChanged = false;
            _wikiDatum.dateCreated = d.dateCreated;
            _wikiDatum.lastModified = d.lastModified;

            //Resolve name
            if(ConflictResolution.resolve(d.name, _wikiDatum.name, d.name, "Name")) {
                _wikiDatum.name =  d.name;
                fireData( new RefreshEvent( this , RefreshEvent.Condition.NAME_CHANGED) );
            } else {
                _wikiDatum._isChanged = true;
            }

            //Resolve wikitext
            if(ConflictResolution.resolve(d._wikiText, _wikiDatum._wikiText, d.name, "WikiText")) {
                _wikiDatum._wikiText = d._wikiText;
                parseLinks();
                updateAttachments();
                fireData( new RefreshEvent( this , RefreshEvent.Condition.WIKITEXT_CHANGED) );
            } else {
                _wikiDatum._isChanged = true;
            }
        return true;
    }

    
    @Override
    public ObjType getType() {
        return ObjType.WIKITEXT;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String name = (String) objHash.get( "name" );
        String text = (String) objHash.get( "_wikiText" );

        //Pull all the annotations and add them to the list
        @SuppressWarnings (value="unchecked")
        ArrayList<String> arrattach = (ArrayList<String>) objHash.get("_attachments" );
        HashSet<String> attach = new HashSet<String>();
        if ( arrattach != null ) {
            for ( String s : arrattach ) {
                attach.add( s );
            }
        }

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        WikiTextDatum d = new WikiTextDatum();

        d.uuid = uuid;
        d.name = name;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._wikiText = text;
        d._attachments = attach;

        return new WikiText( d );
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
        datahash.put( "uuid", _wikiDatum.uuid );
        datahash.put( "name", _wikiDatum.name );

        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_wikiText",_wikiDatum. _wikiText );
        datahash.put( "_attachments", _wikiDatum._attachments );

        //Add the HashMap to the list
        allObjects.put( getUUID(), datahash );

        //Recursively gather the objects linked to this object
        for ( String s : _wikiDatum._attachments ) {
            allObjects = Collector.getAttachment( s ).generateXml( allObjects );
        }

        //Return the datahash
        return allObjects;
    }

    public HashSet<Attachment> getAttachments() {
        HashSet<Attachment> att = new HashSet<Attachment>();
        for ( String s : _wikiDatum._attachments ) {
            att.add( Collector.getAttachment( s ) );
        }
        return att;
    }

    public HashSet<String> getAttachmentLinks() {
        return _wikiDatum._attachments;
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case ATTACHMENT:
                Attachment att = (Attachment) dropObject;
                _wikiDatum._attachments.add( att.getUUID() );
                att.setWikiText( this );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.ATTACHMENT_LINK_CHANGED);
                _wikiDatum.allAvailable.put( att.getName(), att.getUUID() );
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getAsWikiText();
    }

    /* SETTERS
     * */

    public void changeTextAsWiki( String text ) {
        _wikiDatum._wikiText = text;
        parseLinks();
        updateAttachments();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.WIKITEXT_CHANGED);
    }

    public void appendTextAsWiki(String text) {
        _wikiDatum._wikiText+=text;
        parseLinks();
        updateAttachments();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.WIKITEXT_CHANGED);
    }

    /* GETTERS
     * */
    public String getAsWikiText() {
        return _wikiDatum._wikiText;
    }

    /**
     * Uses textileJ to parse the wikitext into html, it also changes the file
     * paths of images to the correct version for this database and changes the
     * various special tags like "claim" and "evidence" to wikitext
     *
     * @return
     */
    public String getAsHTML() {
        updateAttachments();
        String instring = _wikiDatum._wikiText;
        //Need to convert all the filepaths to be a c:// type
        String oldText = "Image:";
        String newText = "08905320-0482-11df-8a39-0800200c9a66" + "file:\\" + _wikiDatum._imagePath;
        String newsource = replaceAll( instring, oldText, newText );
        oldText = "08905320-0482-11df-8a39-0800200c9a66";
        newText = "Image:";
        instring = replaceAll( newsource, oldText, newText );

        //Pull out any references and make reference objects that link to URLs
        //THIS NEEDS TO PULL OUT <reference> TAGS AND REPLACE THEM WITH URLS VIA REFERENCE OBJECTS

        //Replace the claim and evidence tags
        oldText = "<claim>";
        newText = "<blockquote style=\"background:#BDB76B;\">\nClaim:\n<hr>";
        newsource = replaceAll( instring, oldText, newText );
        oldText = "</claim>";
        newText = "</blockquote>";
        instring = replaceAll( newsource, oldText, newText );
        oldText = "<evidence>";
        newText = "<blockquote style=\"background:#8FBC8F;\">\nEvidence:\n<hr>";
        newsource = replaceAll( instring, oldText, newText );
        oldText = "</evidence>";
        newText = "</blockquote>";
        instring = replaceAll( newsource, oldText, newText );


        //Parse the wikitext into html and display it
        MarkupParser parser = new MarkupParser( new MediaWikiDialect() );
        String parsed = parser.parseToHtml( instring );
        instring = parsed.substring( 22 );
        return instring;
    }

    /**
     * replaceAll does a search-and-replace for an exact string match
     * @param original
     * @param find
     * @param replacement
     * @return
     */
    private static String replaceAll( String original, String find, String replacement ) {
        int index = 0;
        String newstring = "";
        while ( index < original.length() ) {
            index = original.indexOf( find, index );
            if ( index == -1 ) {
                break;
            }
            newstring = original.substring( 0, index ) + replacement + original.substring( index + find.length() );
            index = index + replacement.length();
            original = newstring;
        }
        return original;
    }

    /**
     * Pase out lines like [[MakeLink:PLASMID:pBca9145-sbb03]]
     */
    private void parseLinks() {
        String text = _wikiDatum._wikiText;
        String[] tags = text.split("\\[\\[");
        for(String line : tags) {
            System.out.println("alline: " + line);
        }

        System.out.println("*****parsing it out********");
        for(String line : tags) {
            if(line.indexOf("MakeLink")!=0) {
                continue;
            }
            if(line.indexOf("]]") == -1) {
                continue;
            }
            try {
                String parsed = line.split("\\]\\]")[0];
                String[] colons = parsed.split(":");
                String stype = colons[1];
                ObjType type = ObjType.valueOf(stype);
                String name = colons[2];
                ObjBase obj = null;
                switch(type) {
                    case PART:
                        obj = Part.retrieveByName(name);
                        break;
                    case VECTOR:
                        obj = Vector.retrieveByName(name);
                        break;
                    case PLASMID:
                        obj = Plasmid.retrieveByName(name);
                        break;
                    case STRAIN:
                        obj = Strain.retrieveByName(name);
                        break;
                    case OLIGO:
                        obj = Oligo.retrieveByName(name);
                        break;
                    case FEATURE:
                        obj = Feature.retrieveByName(name);
                        break;
                }
                if(obj!=null) {
                    String newline = "[[Link:" + stype + ":" + obj.getUUID() + ": | " + obj.getName() + "]]";
                    String oldline = "[[" + parsed + "]]";
                    String newwiki = replaceAll( _wikiDatum._wikiText, oldline, newline );
                    _wikiDatum._wikiText = newwiki;
                }
            } catch(Exception e) {
            }
        }
    }

    private void updateAttachments() {
        _wikiDatum._attachments.clear();
        String[] parseArray = _wikiDatum._wikiText.split("\\[\\[");
        bigloop: for ( int i = 0; i < parseArray.length; i++ ) {
            if ( parseArray[i].startsWith( "Media:" ) || parseArray[i].startsWith( "Image:" ) ) {
                if(parseArray[i].indexOf( "]]" )==-1) {
                    continue;
                }
                String filename = parseArray[i].substring( 6, parseArray[i].indexOf( "]]" ) );
                String uuid = _wikiDatum.allAvailable.get( filename );

                //If it has the attachment, use that
                if ( uuid != null ) {
                    System.out.println("WikiText: " + getUUID() + " had attachment: " + uuid + " for " + filename);
                    _wikiDatum._attachments.add(uuid);
                    continue bigloop;
                }

                //Otherwise, try finding the atttachment in the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.ATTACHMENT );
                mainQuery.eq( Attachment.Fields.NAME, filename );
                List results = mainQuery.getResults();

                //If it didn't find anything with that filename, quit
                if(results.isEmpty()) {
                    continue bigloop;
                }

                //See if anything in that list has this as the uuid
                for(Object obj : results) {
                    if(obj==null) {
                        continue;
                    }
                    Attachment att = (Attachment) obj;
                    if(att.getWiki()==null) {
                        att.setWikiText( this );
                        System.out.println("Null wiki on attachment for WikiText: " + getUUID() + " had attachment: " + uuid + " for " + filename);
                        _wikiDatum._attachments.add(att.getUUID());
                        continue bigloop;
                    }
                    if(att.getWiki().getUUID().equals(this.getUUID())) {
                        System.out.println("This wiki matched the att for WikiText: " + getUUID() + " had attachment: " + uuid + " for " + filename);
                        _wikiDatum._attachments.add(att.getUUID());
                        continue bigloop;
                    }
                }

                //Otherwise try to duplicate the first object
                for(Object obj : results) {
                    if(obj==null) {
                        continue;
                    }
                    Attachment att = (Attachment) obj;
                    Attachment newatt = att.duplicate();
                    newatt.setWikiText( this );
                    newatt.isTransient();
                    _wikiDatum._attachments.add(newatt.getUUID());
                    System.out.println("Duplicated att for WikiText: " + getUUID() + " had attachment: " + newatt.getUUID() + " for " + filename);
                    continue bigloop;
                }
            }
        }
    }

    /*-----------------
    variables
    -----------------*/
    private WikiTextDatum _wikiDatum;

    public static class WikiTextDatum extends ObjBaseDatum {

        public String _wikiText = "";
        public static final String _imagePath = new File( "cache" ).getAbsolutePath() + "\\";
        public HashSet<String> _attachments = new HashSet<String>();
        public static String filepath;
        public Hashtable<String, String> allAvailable = new Hashtable<String, String>();

        @Override
        public ObjType getType() {
            return ObjType.WIKITEXT;
        }
    }

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        WIKI_TEXT,
        SAMPLE_DATA,
        ATTACHMENTS,
        NOTES,
        FACTOIDS,
    }
}
