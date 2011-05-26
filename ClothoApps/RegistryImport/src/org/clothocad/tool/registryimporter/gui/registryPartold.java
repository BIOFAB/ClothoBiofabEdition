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

import java.awt.Color;
import org.w3c.dom.*;
import java.util.ArrayList;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.Part;
import org.clothocore.util.xml.XMLParser;



/**
 *
 * @author J. Christopher Anderson
 */
public class registryPartold {

    public registryPartold(String partname) {
        partName = partname;
        try {
            retrieveAndParse();
            if(myPart==null) {
                return;
            }
            if(myPart.getSeq().getSeq().length()>0) {
                myPart.launchDefaultViewer();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveAndParse() {

        //This one grabs the sequence
        XMLParser myParser =  parserGrabber.get("http://partsregistry.org/das/parts/dna/?segment=" + partName, "DASDNA");
        if(myParser!=null) {
            String seqy = myParser.getFirstTag("DNA");
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<seqy.length(); i++) {
                Character chr = seqy.charAt(i);
                if(Character.isLetter(chr)) {
                    sb.append(chr);
                }
            }
            myPart = Part.generateBasic( partName, partName, sb.toString(), chosenFormat, Collector.getCurrentUser() );
            partSeq = myPart.getSeq();
            System.out.println(partSeq.toString());
        }

        //This one grabs the features
         myParser =  parserGrabber.get("http://partsregistry.org/das/parts/features/?segment=" + partName, "DASGFF");
        if(myParser!=null) {
            //in reality there are many NOTE fields embedded in the FEATURE, so need to parse feature by feature
            String note = myParser.getFirstTag("NOTE");
            
            ArrayList<Element> nodes = myParser.getNodesOfTag("FEATURE");
            if(nodes.size()<1) {
                return;
            }
            for(int i=1; i<nodes.size(); i++) {
                Element node = nodes.get(i);
                String label =node.getAttribute("label");
                NodeList elements = node.getChildNodes();

                //Parse an annotation
                int start=0;
                int end=0;
                boolean strand = true;
                Color forcolor = getRandomColor();
                Color revcolor = getRandomColor();

                for(int j=0; j<elements.getLength(); j++) {
                    Node labelNode = elements.item(j);
                    
                    NodeList valueNodes = labelNode.getChildNodes();
                    if(valueNodes.getLength()==0) {
                        continue;
                    }
                    String tag = labelNode.getNodeName();
                    String value = valueNodes.item(0).getTextContent();
                    System.out.println(label + "'s " + tag + ": " + value);

                    if(tag.equals("START")) {
                        start = Integer.parseInt(value) - 1;
                    } else if(tag.equals("END")) {
                        end = Integer.parseInt(value);
                    } else if(tag.equals("ORIENTATION")) {
                        if(Integer.parseInt(value)==0) {
                            strand = false;
                        }
                    }
                }

                //Create the annotation
                new Annotation( label, partSeq, forcolor, revcolor, start, end, Collector.getCurrentUser(), strand, "" );
            }
        }

}

    public Color getRandomColor() {
        //HSBtoRGB(float hue, float saturation, float brightness)
        Color newcolor = new Color(Color.HSBtoRGB((float) Math.random()*1.0f, (float) Math.random()*0.5f + 0.5f, (float) Math.random()*0.4f + 0.6f));
        return newcolor;
    }

/*-----------------
     variables
 -----------------*/
    private String partName;
    private NucSeq partSeq;
    private Part myPart;
    private Format chosenFormat = Collector.getFormat("org-clothocad-format-rfc10-connect");
}
