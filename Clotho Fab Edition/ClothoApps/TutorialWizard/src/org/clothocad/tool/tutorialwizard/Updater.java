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

package org.clothocad.tool.tutorialwizard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.clothocore.util.xml.XMLParser;

/**
 *
 * @author J. Christopher Anderson
 */
public class Updater {

     /**
     * Called after booting up clotho, on a new thread.  It goes to a static URL and
     * reads some XML.  If the version is newer than the last one it read, it grabs
     * the URL from that site and puts that as the launch target for the thing.
     */
    public boolean checkForUpdate() {
        try {
            URL url = new URL(updateURL);
            URLConnection conn = url.openConnection();
            if(conn.getContentType()==null) {
                System.out.println("Check for update has no connection");
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


	// form URL of the robots.txt file
	URL urlRobot;
	try {
	    urlRobot = new URL(updateURL);
	} catch (Exception e) {
	    e.printStackTrace();
            return false;
	}

	try {
            //Starts reading the URL
	    InputStream urlRobotStream = urlRobot.openStream();

            //Read the file and access it in an xmlParser, then close it
            myParser = new XMLParser(urlRobotStream, "update" );
	    urlRobotStream.close();

	} catch (IOException e) {
	    e.printStackTrace();
            return false;
	}
        if(myParser!=null) {
            return parseTheFile();
        }
        return false;
    }

    /**
     * If things got this far then there was an XML file pulled, now it
     * needs to parse out what's in it and update the lists
     */
    private boolean parseTheFile() {
        try {
            displayNames = myParser.getMultipleTagValues("displayname");
            urlLinks = myParser.getMultipleTagValues("url");
            if(urlLinks==null || urlLinks.isEmpty()) {
                return false;
            }
            if(displayNames==null || displayNames.isEmpty()) {
                return false;
            }
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public ArrayList<String> getNames() {
        return displayNames;
    }

    public ArrayList<String> getLinks() {
        return urlLinks;
    }
/*-----------------
     variables
 -----------------*/
    private String updateURL = "http://andersonlab.qb3.berkeley.edu/Software/UpdateTutorials.xml";
    private XMLParser myParser;
    private ArrayList<String> displayNames;
    private ArrayList<String> urlLinks;

}
