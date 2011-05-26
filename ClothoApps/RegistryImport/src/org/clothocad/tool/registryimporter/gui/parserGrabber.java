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

package org.clothocad.tool.registryimporter.gui;

/**
 *
 * @author J. Christopher Anderson
 */

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.clothocore.util.basic.FileUtils;
import org.clothocore.util.xml.XMLParser;

public class parserGrabber {

    @SuppressWarnings("deprecation")
    public static XMLParser get(String url, String primaryID) {
	// form URL of the robots.txt file
        XMLParser myParser=null;
        StringBuffer tempXMLFileStr= new StringBuffer();
        try {
            URL urlsite = new URL(url);
            URLConnection registryConnection = urlsite.openConnection();
            DataInputStream dis = new DataInputStream(registryConnection.getInputStream());
            String inputLine;

            while ((inputLine = dis.readLine()) != null) {
                if(inputLine.startsWith("<?xml")) {
                    inputLine = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
                }
                if(inputLine.startsWith("<!DOCTYPE")) {
                    inputLine = "";
                }
                tempXMLFileStr.append(inputLine);
                tempXMLFileStr.append("\n");
            }
            dis.close();
        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }


        FileUtils.writeFile(tempXMLFileStr.toString(), "temp.xml");

        //Read the file and access it in an xmlParser, then close it
        myParser = new XMLParser(new File("temp.xml"), primaryID );
        return myParser;
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        StringBuffer tempXMLFileStr= new StringBuffer();
        try {
            URL urlsite = new URL("http://partsregistry.org/wiki/index.php?title=Part:BBa_J23119&action=edit");
            URLConnection registryConnection = urlsite.openConnection();
            DataInputStream dis = new DataInputStream(registryConnection.getInputStream());
            String inputLine;

            while ((inputLine = dis.readLine()) != null) {
                tempXMLFileStr.append(inputLine);
                tempXMLFileStr.append("\n");
            }
            dis.close();
            System.out.println(tempXMLFileStr);
        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
}
