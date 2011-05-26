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
import java.util.HashMap;
import org.clothocore.api.core.Collector;
import org.clothocore.util.xml.XMLParser;
import org.w3c.dom.*;

/**
 * XMLImporter imports a set of linked ObjBase items from XML
 * @author J. Christopher Anderson
 */
public class XMLImporter {
    public XMLImporter(File afile) {
        if(!afile.exists()) {
            System.out.println("importer is a dud!");
            return;
        }

        parser = new XMLParser(afile, "output");

        ArrayList<String> uuids = parser.getMultipleTagValues("uuid");
        ArrayList<Element> nodes = parser.getNodesOfTag("ClothoObject");

        for(Node e: nodes) {
            parseSingleObject(e);
        }

        for(String uuid: importedHash.keySet()) {
            String stype = uuidToTypeHash.get(uuid);
            ObjType type = ObjType.valueOf(stype);
            ObjBase oldobj = Collector.get(type, uuid);
            if(oldobj!=null) {
                System.out.println(uuid + " Already exists");
     //ACTIVATE THIS CONTINUE WHEN DONE EDITING, SHOULD RELAY OVER TO AN UPDATING CODE
     //ALSO NEED TO DATABASE QUERY FOR OBJECTS OF SAME NAME
                //          continue;
            }

            ObjBase o = chooseType(uuid, importedHash.get(uuid) );
            if(o!=null) {
                o._datum._isChanged = true;
                o._inDatabase = false;
                importedObjects.add(o);
            }
        }

        outcollection = new Collection();
        outcollection.setTransient();

        for(ObjBase o: importedObjects) {
            System.out.println(o.getType().toString() + " " + o.getName() + " " + o.getUUID());
            outcollection.addObject(o);
        }
    }

    private void parseSingleObject(Node currelement) {
        NodeList ndlist = currelement.getChildNodes();

   //     System.out.println("************************ndlist length " + ndlist.getLength());
        HashMap<String, Object> clothoObject = new HashMap<String, Object>();
        
        for(int i=0; i< ndlist.getLength(); i++) {
            //Grab all the fields
            Element tag1Element = null;
            try {
                tag1Element = (Element) ndlist.item(i);
            } catch(java.lang.ClassCastException e) {
                continue;
            }


            //If pass this check it processes a single tag line
            if(tag1Element!=null) {
                  NodeList childNodes = tag1Element.getChildNodes();
                  String field = tag1Element.getNodeName();

                  //If the field is null it carries through as null
                  Object value = null;
                  
                  //If single field line parse into String
                  if(childNodes.getLength() == 1) {
                      Node anode = childNodes.item(0);
                      value = anode.getNodeValue();
                  }

                  //Parse it into an ArrayList
                  if(childNodes.getLength() >1) {
                      value = new ArrayList<String>();
                      for(int n=0; n<childNodes.getLength(); n++) {
                          Element listElement = null;
                          try {
                              listElement = (Element) childNodes.item(n);
                          } catch(java.lang.ClassCastException e) {
                              continue;
                          }
                          Node anode = listElement.getChildNodes().item(0);
                          String aval = anode.getNodeValue();
                          @SuppressWarnings (value="unchecked")
                          ArrayList<String> hs = (ArrayList<String>) value;
                          hs.add(aval);
                          System.out.println("aval " + aval);
                      }
                  }

              //    System.out.println(field + " " + value);
                  clothoObject.put(field, value);
            }

        }

            String uuid = (String) clothoObject.get("uuid");
            importedHash.put(uuid, clothoObject);

            String type = (String) clothoObject.get("objType");
            uuidToTypeHash.put(uuid, type);
    }

    private ObjBase chooseType(String uuid, HashMap<String, Object> objHash) {
        String stype = uuidToTypeHash.get(uuid);
        ObjType type = ObjType.valueOf(stype);
        switch (type) {
            case ANNOTATION:
                return Annotation.importFromHashMap(uuid, objHash);
            case ATTACHMENT:
                return Attachment.importFromHashMap(uuid, objHash);
            case COLLECTION:
                return Collection.importFromHashMap(uuid, objHash);
            case CONTAINER:
                return Container.importFromHashMap(uuid, objHash);
            case FACTOID:
                return Factoid.importFromHashMap(uuid, objHash);
            case FAMILY:
                return Family.importFromHashMap(uuid, objHash);
            case FEATURE:
                return Feature.importFromHashMap(uuid, objHash);
            case FLEX_FIELD:
                return FlexField.importFromHashMap(uuid, objHash);
            case FORMAT:
                return Format.importFromHashMap(uuid, objHash);
            case GRAMMAR:
                return Grammar.importFromHashMap(uuid, objHash);
            case INSTITUTION:
                return Institution.importFromHashMap(uuid, objHash);
            case LAB:
                return Lab.importFromHashMap(uuid, objHash);
            case NUCSEQ:
                return NucSeq.importFromHashMap(uuid, objHash);
            case NOTE:
                return Note.importFromHashMap(uuid, objHash);
            case OLIGO:
                return Oligo.importFromHashMap(uuid, objHash);
            case PART:
                return Part.importFromHashMap(uuid, objHash);
            case PERSON:
                return Person.importFromHashMap(uuid, objHash);
            case PLASMID:
                return Plasmid.importFromHashMap(uuid, objHash);
            case PLATE:
                return Plate.importFromHashMap(uuid, objHash);
            case PLATE_TYPE:
                return PlateType.importFromHashMap(uuid, objHash);
            case SAMPLE:
                String sampletype = (String) objHash.get("name");
                Sample.sampleType st = Sample.sampleType.valueOf(sampletype);
                switch(st) {
                    case PLASMID_SAMPLE:
                        return PlasmidSample.importFromHashMap(uuid, objHash);
                    case CELL_SAMPLE:
                        return null;
                    case OLIGO_SAMPLE:
                        return null;
                    default:
                        return null;
                }
            case SAMPLE_DATA:
                return SampleData.importFromHashMap(uuid, objHash);
            case STRAIN:
                return Strain.importFromHashMap(uuid, objHash);
            case WIKITEXT:
                return WikiText.importFromHashMap(uuid, objHash);
            case VECTOR:
                return Vector.importFromHashMap(uuid, objHash);
            default:
                return null;
        }
    }

    public Collection getObjects() {
        return outcollection;
    }

/*-----------------
     variables
 -----------------*/
    private XMLParser parser;
    private HashMap<String, HashMap<String, Object>> importedHash = new HashMap<String, HashMap<String, Object>>();
    private HashMap<String, String> uuidToTypeHash = new HashMap<String, String>();
    private ArrayList<ObjBase> importedObjects = new ArrayList<ObjBase>();
    private Collection outcollection;
}
