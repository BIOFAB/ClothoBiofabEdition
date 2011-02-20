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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.clothocore.util.xml.XMLParser;

/**
 * MAKE REFERENCE AN ABSTRACT CLASS THAT GETS THE FIRST BIT OF THE STRING (HTTP:, ETC) AND REROUTE IT TO THE RIGHT EXTENDING CLASS OF DOI
 * BOOK, URL, OR PMID
 * 
 * REFERENCE HAS A REFERENCE TYPE
 *
 * REFERENCE, SAMPLE, AND SAMPLE DATA ARE SIMILAR ABSTRACT CLASSES WITH ABSTRACT METHODS FOR GETTING SHARED FIELDS
 * AND OTHER DATA IS IN EXTENDING CLASSES
 *
 * HAS METHODS IN IT FOR THE MAJOR RELEVANT FIELDS, PMID HAS A LOT MORE
 *
 * Reference doesn't need to be an objbase, it has not table in database
 *
 * @author J. Christopher Anderson
 */
public class Reference {

    private Reference ( String refLink ) {
        if (refLink.startsWith( "http:" )) {
            try {
                _webLink = new URL( refLink );
            } catch (MalformedURLException ex) {
                System.out.println( "That was not a valid URL" );
                return;
            }
            _myDoiType = doiType.URL;
            _id = refLink;

        } else if (refLink.startsWith( "DOI:" )) {
            _myDoiType = doiType.DOI;
            _id = refLink.substring( 4 );
        } else if (refLink.startsWith( "PMID:" )) {
            _myDoiType = doiType.PMID;        //Ex: PMID: 20462360 or PMID:20462360
            _id = refLink.substring( 5 );
            try {
                _webLink = new URL( "http://pubget.com/paper/" + _id );
            } catch (MalformedURLException ex) {
                _webLink = null;
            }
        } else if (refLink.startsWith( "ISBN:" )) {
            _myDoiType = doiType.ISBN;
            _id = refLink.substring( 5 );
        } else if (refLink.startsWith( "US_Patent:" )) {
            _myDoiType = doiType.US_PATENT;   //Ex: US_Patent:7,003,515
            _id = refLink.substring( 5 );
            try {
                _webLink = new URL( "http://www.google.com/patents?id=LI54AAAAEBAJ&dq=" + _id );
            } catch (MalformedURLException ex) {
                _webLink = null;
            }
        } else if (refLink.startsWith( "Intl_Patent:" )) {
            _myDoiType = doiType.INTL_PATENT;
            _id = refLink.substring( 11 );
        }

        if(_id!=null) {
            //Remove any spaces
            while(_id.startsWith(" ")) {
                String newid = _id.substring(1);
                _id = newid;
            }
        }
    }

    public static Reference generate(String reflink) {
        if(reflink==null || reflink.equals("")) {
            return null;
        }
        String linky = reflink.trim();
        linky.replaceAll("\\s+", "");
        Reference aref = new Reference(linky);
        if(aref._myDoiType==null) {
            return null;
        }
        return aref;
    }

    public void initiatePMID ( String value ) {
        // form URL of the robots.txt file
        String strRobot = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=" + value + "&retmode=xml";
        URL urlRobot;
        try {
            urlRobot = new URL( strRobot );
        } catch (MalformedURLException e) {
            System.out.println( "I had a malformed URLException on initiate" );
            return;
        }

        try {
            //Starts reading the URL
            InputStream urlRobotStream = urlRobot.openStream();

            //Read the file and access it in an xmlParser, then close it
            myParser = new XMLParser( urlRobotStream, "PubmedArticle" );
            urlRobotStream.close();

        } catch (IOException e) {
            System.out.println( "I wasn't able to read the URL" );
            return;
        }
    }

    /*Does an xml query of ArticleTitle; AbstractText; JournalTitle; Year; Affiliation;  FirstAuthor*/
    private String PMIDget ( String tag ) {
        if (_myDoiType.equals( doiType.PMID )) {
            if (myParser == null) {
                initiatePMID( _id );
            }
            if (myParser != null) {
                return myParser.getFirstTag( tag );
            }
        }
        return "";
    }

    public String getTitle () {
        if (_myDoiType.equals( doiType.PMID )) {
            return PMIDget( "ArticleTitle" );
        }
        if (_myDoiType.equals( doiType.URL )) {
            return _id;
        }
        return "No title available";
    }

    public String getID() {
        String out = "";
        switch(_myDoiType) {
            case URL:
                break;
            case DOI:
                out+= "DOI:";
                break;
            case INTL_PATENT:
                out+= "Intl_Patent:";
                break;
            case ISBN:
                out+= "ISBN:";
                break;
            case PMID:
                out+= "PMID:";
                break;
            case US_PATENT:
                out+= "US_Patent:";
                break;
            default:
                return "";
        }
        out +=_id;
        return out;
    }

    public String getAbstract () {
        if (_myDoiType.equals( doiType.PMID )) {
            return PMIDget( "AbstractText" );
        }
        return "No abstract available";
    }

    public String getJournal () {
        if (_myDoiType.equals( doiType.PMID )) {
            return PMIDget( "JournalTitle" );
        }
        return "No journal available";
    }

    public String getYear () {
        if (_myDoiType.equals( doiType.PMID )) {
            return PMIDget( "Year" );
        }
        return "";
    }

    public String getAffiliation () {
        if (_myDoiType.equals( doiType.PMID )) {
            return PMIDget( "Affiliation" );
        }
        return "Affiliation unavailable";
    }

    public String getFirstAuthor () {
        if (_myDoiType.equals( doiType.PMID )) {
            return PMIDget( "FirstAuthor" );
        }
        return "Author unavailable";
    }

    public void launchWebLink () {
        if (_webLink != null) {
            BareBonesBrowserLaunch.openURL( _webLink.toString() );
        }
    }

    public URL getWebLink () {
        return _webLink;
    }

    /**-----------------
    variables
    -----------------*/
    private String _id;
    private boolean failed = true;
    transient private XMLParser myParser;

    public enum doiType {

        DOI, PMID, URL, ISBN, US_PATENT, INTL_PATENT
    };
    private doiType _myDoiType = null;
    private URL _webLink;

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        FAILED,
    }
}
