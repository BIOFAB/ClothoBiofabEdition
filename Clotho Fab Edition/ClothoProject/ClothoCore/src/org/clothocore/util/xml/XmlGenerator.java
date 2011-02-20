

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

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import org.openide.util.Exceptions;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 * XmlGenerator is called from an objBase to recursively turn the entire
 * series of linked objects into an XML file.  This is not a generic xml-generating
 * class--it specifically generates a Clotho XML representation of an object.
 * 
 * @author J.Christopher Anderson
 * @author Douglas Densmore
 */
public class XmlGenerator {

    public XmlGenerator(HashMap<String, HashMap<String, Object>> data) {
        write(data);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////


    private void write(HashMap<String, HashMap<String, Object>> data) {
        _dataHash = data;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            //creating a new instance of a DOM to build a DOM tree.
            _theDocument = docBuilder.newDocument();
            Element root = _theDocument.createElement("output");
            //adding a node after the last child node of the specified node.
            _theDocument.appendChild(root);

            parseOutHashMap(root);
            finalizer();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void parseOutHashMap(Element root) {

        //For each ClothoObject in the hash, write its xml
        for(String uuid: _dataHash.keySet()) {
            //Create the object node for each ClothoObject
            Element anelement = _theDocument.createElement("ClothoObject");
            root.appendChild(anelement);

            HashMap<String, Object> objectHash = _dataHash.get(uuid);
      //      System.out.println("Working on " + uuid + " has hash of length " + objectHash.size());

            for(String field: objectHash.keySet()) {
                Object value = objectHash.get(field);
                if(value==null) {
                    addNullElement(anelement,  field);
                    continue;
                }
                //If it's a String, call the terminal subroutine
                try {
                    String stringValue = (String) value;
                    addStringElement(anelement,  field,  stringValue);
                    continue;
                } catch (java.lang.ClassCastException ex)  {
                }

                //If it's a list, call recursion on it
                try {
                    Collection subArray = (Collection) value;
                    addListElements(anelement,  field,  subArray);
                    continue;
                } catch (java.lang.ClassCastException ex2) {
                }
            }
        }
    }

    /**Terminal method of recursive document generation...this adds a field node
     * and its string value
     * @param root
     * @param afield
     * @param stringValue
     */
    private  void addStringElement(Element root, String afield, String stringValue) {
        Element anelement;
        Text theText;

        Element child1 = _theDocument.createElement(afield);
        root.appendChild(child1);

        Text text = _theDocument.createTextNode(stringValue);
        child1.appendChild(text);
    }

    private  void addNullElement(Element root, String field) {
        Element anelement;
        Text theText;

        Element child1 = _theDocument.createElement(field);
        root.appendChild(child1);
    }

    private  void addListElements(Element root, String passedField, Collection passedArray) {
            String afield = passedField;
            Element listNode = _theDocument.createElement(afield);
            root.appendChild(listNode);

            for(Object value: passedArray) {
                //If it's a String, call the terminal subroutine
                try {
                    String stringValue = (String) value;
                    addStringElement(listNode,  "list",  stringValue);
                } catch (java.lang.ClassCastException ex)  {
                }
            }
    }

    private  void finalizer() {
        try {
            //TransformerFactory instance is used to create Transformer objects.
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            // create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(_theDocument);
            transformer.transform(source, result);
            String xmlString = sw.toString();
            File file = new File("../newxml.xml");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(xmlString);
            bw.flush();
            bw.close();
        } catch (Exception ex) {
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         protected variables               ////


    /*The HashMap contains fieldname, fielddata, and the fielddata can be recursive*/
    protected HashMap<String, HashMap<String, Object>> _dataHash;    //HashMap of type <String, (ArrayList, HashMap, or String )>
    protected Document _theDocument;

}