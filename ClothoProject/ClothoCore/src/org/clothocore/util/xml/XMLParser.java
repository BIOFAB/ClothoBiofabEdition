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

package org.clothocore.util.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

/**
 * XML parser is a convenience class for reading objects out of an xml file.  You create the
 * parser from the file and whatever root tag it has, then you can later request individual
 * fields of it by tag based on the data structure correlating with those tags
 *
 * To use it, say you've used xmlWriter to generate an xml file from an object and included the
 * variables:
            protected String PMID="";
            protected ArrayList<String> images;
            protected ArrayList<String> searchTags;
            protected String wikiText;

 * To turn that back into an object, you'd call:
            factoid openFact = new factoid(factoidUUID);  //Construct the new empty object
            String filename = factoidUUID + ".xml";
            xmlParser factParser = new xmlParser(filename, "factoid");
                openFact.PMID = factParser.getFirstTag("PMID");
                openFact.wikiText = factParser.getFirstTag("wikiText");
                openFact.images = factParser.getListField("images");
                openFact.searchTags = factParser.getListField("searchTags");
 *
 * @author J. Christopher Anderson
 */
public class XMLParser {

    /**Create the parser from a file and it's root tag
     *
     * @param filepath  path to the xml file
     * @param elementType  the root tag
     */
    public XMLParser(File xmlFile, String elementType) {
        _myXMLFile = xmlFile;
        int outValue=0;
        try {
              DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
              DocumentBuilder db = dbf.newDocumentBuilder();
              Document doc = db.parse(xmlFile);
              doc.getDocumentElement().normalize();
              nodeLst = doc.getElementsByTagName(elementType);
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    /**Create the parser from a input stream
     *
     * @param smlStream  when it's already in memory, use this as source
     * @param elementType  the root tag
     */
    public XMLParser(InputStream xmlStream, String elementType) {
        int outValue=0;
        try {
              DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
              DocumentBuilder db = dbf.newDocumentBuilder();
              Document doc = db.parse(xmlStream);
              doc.getDocumentElement().normalize();
              nodeLst = doc.getElementsByTagName(elementType);
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    /**
     * This method assumes you only have one member to the primary node, and it retrieves
     * all instances of a particular tag (tagName) within that node.  If there are multiple
     * nodes to the XML file, it only operates on the first one.
     * @param tagName
     * @return
     */
    public ArrayList<String> getMultipleTagValues(String tagName) {
        ArrayList<String> outArray = new ArrayList<String>();
        Node currElement = nodeLst.item(0);
        if(currElement==null) {
            return outArray;
        }
        if (currElement.getNodeType() == Node.ELEMENT_NODE) {

          Element firstElement = (Element) currElement;

          NodeList tag1ElementList = firstElement.getElementsByTagName(tagName);
          for(int i=0; i< tag1ElementList.getLength(); i++) {
              Element tag1Element = (Element) tag1ElementList.item(i);
              if(tag1Element!=null) {
                  NodeList childNodes = tag1Element.getChildNodes();
                  String elementValue = (childNodes.item(0)).getNodeValue();
                  outArray.add(elementValue);
              }
          }


        }
        return outArray;
    }


    /**This method goes through a supplied xml file (filepath), parses it by elementType, and returns
     * each one's value in tagName.  So, for example, you'd send it an xml file with lots of parts, vectors,
     * and plasmids, and query for (file.xml, "part", "UUID") and it would return an arrayList of all
     * the UUID's for all the parts in the list
     * @param tagName
     * @return
     */
    public ArrayList<String> getAllOfTag(String tagName) {
        ArrayList<String> outArray = new ArrayList<String>();
        int length = nodeLst.getLength();
        for (int s = 0; s < length; s++) {

            Node currElement = nodeLst.item(s);

            if (currElement.getNodeType() == Node.ELEMENT_NODE) {

              Element firstElement = (Element) currElement;

              NodeList tag1ElementList = firstElement.getElementsByTagName(tagName);
              Element tag1Element = (Element) tag1ElementList.item(0);
              if(tag1Element!=null) {
                  NodeList childNodes = tag1Element.getChildNodes();
                  String elementValue = (childNodes.item(0)).getNodeValue();
                  outArray.add(elementValue);
              }
            }
        }
          return outArray;
    }

    /**
     * Breaks up a list of nodes under a similar tagname into individual nodes that
     * are returned that can be further parsed.
     *
     * @param tagName
     * @return
     */
    public ArrayList<Element> getNodesOfTag(String tagName) {
        ArrayList<Element> outArray = new ArrayList<Element>();
        int length = nodeLst.getLength();
        
        for (int s = 0; s < length; s++) {

            Node currElement = nodeLst.item(s);

            if (currElement.getNodeType() == Node.ELEMENT_NODE) {

              Element firstElement = (Element) currElement;

              NodeList tag1ElementList = firstElement.getElementsByTagName(tagName);
              System.out.println("tag1ElementList" + tag1ElementList.getLength());

              for(int i=0; i< tag1ElementList.getLength(); i++) {
                  Element tag1Element = (Element) tag1ElementList.item(i);
                  if(tag1Element!=null) {
                      outArray.add(tag1Element);
                  }
              }
            }
        }
          return outArray;
    }

/** For data compressed down to an Xml file from an arraylist, this one restores the arraylist
 *  For example, if you generated xml with a root node tag of "book" and there was a list of <author>
 *  tags in it, you'd create a
 *     xmlParser bookparser = new xmlParser("filename.xml", "book") and then call:
 *     ArrayList<String> allauthors = bookparser.getListField("author")
 * @param tagName
 * @return
 */
    public ArrayList<String> getListField(String tagName) {
        ArrayList<String> outArray = new ArrayList<String>();
        Node currElement = nodeLst.item(0);

            if (currElement.getNodeType() == Node.ELEMENT_NODE) {

              Element firstElement = (Element) currElement;

              NodeList tag1ElementList = firstElement.getElementsByTagName(tagName);
              for(int i=0; i<tag1ElementList.getLength(); i++) {
                  Element tag1Element = (Element) tag1ElementList.item(i);
                  NodeList childNodes = tag1Element.getChildNodes();
                  String elementValue = (childNodes.item(0)).getNodeValue();
                  outArray.add(elementValue);
              }
            }
          return outArray;
    }

    /**This one retrieves the value of the first <tagname> it finds
     *
     * @param tagName
     * @return
     */
    public String getFirstTag(String tagName) {
        ArrayList<String> allTags;
        allTags = getAllOfTag( tagName);
        if(allTags.size()==0) {
            return "";
        }
        if(allTags.size()>0) {
            return allTags.get(0);
        } else {
            return "";
        }
    }

    public File getFile() {
        return _myXMLFile;
    }


/**-----------------
     variables
 -----------------*/
private  NodeList nodeLst;
private File _myXMLFile;
}
