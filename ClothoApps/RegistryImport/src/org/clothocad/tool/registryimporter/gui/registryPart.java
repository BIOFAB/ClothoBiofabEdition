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
import java.util.List;
import org.clothocore.api.data.ObjBase;
import org.w3c.dom.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Part;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.util.xml.XMLParser;



/**
 *
 * @author J. Christopher Anderson
 */
public class registryPart {

    public registryPart(String partname, Format form, boolean includeAnnotations) {
        partName = partname;
        chosenFormat = form;
        incAnnot = includeAnnotations;
        retrieveAndParse();
    }

    private void retrieveAndParse() {

        try {
            //This one grabs the sequence
            XMLParser myParser =  parserGrabber.get("http://partsregistry.org/xml/part." + partName, "rsbpml");
            if(myParser==null) {
                System.out.println("parser is null");
                return;
            }

            //Determine if it's basic or composite
            ArrayList<Element> nodes = myParser.getNodesOfTag("deep_subparts");
            if(nodes.isEmpty()) {
                return;
            }

            //Make sure it's not a vector
            String parttype = myParser.getFirstTag("part_type");
            if(parttype.equals("Plasmid_Backbone") || parttype.equals("Plasmid")) {
                JOptionPane.showMessageDialog(null,
                    "Registry Importer currently can only import Basic and Composite Parts.  This sppears to be a Vector.  You'll need to import it manually.",
                    "This is a Vector",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            Element deepNode = nodes.get(0);
            NodeList subpartNodes = deepNode.getChildNodes();

            if(subpartNodes.getLength()>0) {
                parseComposite(myParser);
            } else {
                parseBasic(myParser);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
}

    private void parseBasic(XMLParser myParser) {
        System.out.println("parsing basic for " + partName);

        //Grab the sequence of the basic Part
        String seqy = myParser.getFirstTag("seq_data");
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<seqy.length(); i++) {
            Character chr = seqy.charAt(i);
            if(Character.isLetter(chr)) {
                sb.append(chr);
            }
        }

        String basesequence = sb.toString().toUpperCase();
        String partsequence = sb.toString().toUpperCase();
        String parttype = myParser.getFirstTag("part_type");

        //See if it's a Coding part and change RFC10 format
        if(parttype.equals("Coding")) {
            System.out.println("Dealing with a Coding sequence");
            if(chosenFormat.getUUID().equals("org-clothocad-format-rfc10-connect")) {
                if(basesequence.startsWith("ATG")) {
                    chosenFormat = Collector.getFormat("org-clothocad-format-rfctencds-connect");
                    System.out.println("Changed format to " + chosenFormat.getName());
                }
            }
        }

        //See if it's BglBricks and adjust the sequence
        if(chosenFormat.getUUID().equals("org-clothocad-format-bglbricks-connect")) {
            String tempy = basesequence;
            sb = new StringBuffer();
            sb.append("GATCT");
            sb.append(tempy);
            sb.append("G");
            partsequence = sb.toString();
        }


        //See if that Part is already in the database
        Part apart = Part.retrieveByName(partName);
        if(apart!=null) {
            String itsseq = apart.getSeq().getSeq();
            if(itsseq.equals(partsequence)) {
                outPart = apart;
                return;
            }
        }

        //Create the basic part
        System.out.println("about to create basic part for " + partsequence);
        System.out.println("format is " + chosenFormat.getName());
        outPart = Part.generateBasic( partName, partName, partsequence, chosenFormat, Collector.getCurrentUser() );

        //If Part generation failed, just quit
        if(outPart==null) {
            return;
        }

        //Try grabbing the nickname as the description
        String desc  = null;
        try {
            desc = myParser.getFirstTag("part_nickname");
        } catch (Exception err) {
        }
        if(desc==null || desc.equals("")) {
            desc = partName;
        }
        outPart.changeShortDescription(desc);

        //Set the primary Feature associated with the Part

        //See if the Feature already exists
        Feature mainFeature = null;
        try {
            mainFeature = queryForFeature(basesequence);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        //Create a new Feature if none were retrieved
        if(mainFeature==null) {
            mainFeature = createBasicPartsFeature(parttype, basesequence, outPart, seqy);
        }


        //Parse out the non-Feature annotations
        /*
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
         * 
         */
    }

    private Feature createBasicPartsFeature(String parttype, String basesequence, Part outPart, String seqy ) {
        Feature mainFeature = null;
        boolean isATGCDS = false;
        //Set the features if it's a CDS part
        if(parttype.equals("Coding")) {
            //Nevermind if it is incorrectly labeled
            String featSeq = basesequence;
            if(basesequence.startsWith("ATG") || basesequence.startsWith("GTG") || basesequence.startsWith("TTG")) {
                featSeq = basesequence.substring(3);
                isATGCDS = true;
            }
            if(featSeq.endsWith("TAATAA") || featSeq.endsWith("TGATAA") || featSeq.endsWith("TAGTAA")) {
                String tempseq = featSeq.substring(0, featSeq.length()-3);
                featSeq = tempseq;
            }
            mainFeature = Feature.generateFeature(partName, featSeq, Collector.getCurrentUser(), isATGCDS);
            System.out.println("1:  GenerateFeature of " + mainFeature.getSeq());
        }
        //Set the features if it's a Promoter part
        else if(parttype.equals("Regulatory")) {
            //See if it has any known substucture in it and decompose it
            //BLAST SWISSPROT AND SEE IF THERE ARE COMPLETE CDS'S IN IT
            //Otherwise, it's a promoter, so make the Feature
            mainFeature = Feature.generateFeature(partName, basesequence, Collector.getCurrentUser(), isATGCDS);
            System.out.println("1:  GenerateFeature of " + mainFeature.getSeq());
        }
        else if(parttype.equals("Terminator")) {
            mainFeature = Feature.generateFeature(partName, basesequence, Collector.getCurrentUser(), isATGCDS);
            System.out.println("1:  GenerateFeature of " + mainFeature.getSeq());
        }

        if(mainFeature==null) {
            return null;
        }

        try {
            mainFeature.changeForwardColor(getRandomColor());
            mainFeature.changeReverseColor(getRandomColor());
            int Start = outPart.getSeq().getSeq().indexOf(mainFeature.getSeq().getSeq());
            int End = Start + mainFeature.getSeq().getSeq().length();
            if(mainFeature.isCDS()) {
                if(Start > 3) {
                    String upthree = outPart.getSeq().getSeq().substring(Start-3, Start);
                    if(upthree.equals("ATG") || upthree.equals("GTG")) {
                        Start = Start-3;
                    }
                }
                int length = mainFeature.getSeq().getSeq().length();
                if(outPart.getSeq().getSeq().length() >= length + 3) {
                    String downthree = outPart.getSeq().getSeq().substring(Start + length, Start + length + 3);
                    if(downthree.equals("TAA") || downthree.equals("TGA") || downthree.equals("TAG")) {
                        End = End+3;
                    }
                }
            }
            
            new Annotation( mainFeature, outPart.getSeq(),null, null, Start, End, null, true, seqy);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return mainFeature;
    }

    private Feature queryForFeature(String seq) {
        String querystr = seq;

        //Trim off start codons
        if(seq.startsWith("ATG") || seq.startsWith("GTG") || seq.startsWith("CTG"))  {
            querystr = seq.substring(3);
        }

        //Trim off stop codons
        if(seq.endsWith("TAG") || seq.endsWith("TAA") || seq.endsWith("TGA") ) {
            String tempy = querystr.substring(0, querystr.length()-3);
            querystr = tempy;
        }
        if(seq.endsWith("TAG") || seq.endsWith("TAA") || seq.endsWith("TGA") ) {
            String tempy = querystr.substring(0, querystr.length()-3);
            querystr = tempy;
        }

        ClothoConnection c = Collector.getDefaultConnection();
        ClothoQuery mainQuery = c.createQuery( ObjType.FEATURE );
        ClothoQuery seqQuery = mainQuery.createAssociationQuery( Feature.Fields.SEQUENCE );
        seqQuery.contains( NucSeq.Fields.SEQUENCE, querystr, false );
        List<ObjBase> results = mainQuery.getResults();

        if(results.isEmpty()) {
            System.out.println("Didn't find anything for " + seq.substring(0, Math.min(20, seq.length())));
            return null;
        }

        Feature foundfeat = (Feature) results.get(0);
        NucSeq seqf = foundfeat.getSeq();

        try {
            int Start = outPart.getSeq().getSeq().indexOf(foundfeat.getSeq().getSeq());
            int End = Start + seqf.getSeq().length();
            if(foundfeat.isCDS()) {
                if(Start > 3) {
                    String upthree = outPart.getSeq().getSeq().substring(Start-3, Start);
                    if(upthree.equals("ATG") || upthree.equals("GTG")) {
                        Start = Start-3;
                    }
                }
                int length = seqf.getSeq().length();
                if(outPart.getSeq().getSeq().length() >= length + 3) {
                    String downthree = outPart.getSeq().getSeq().substring(Start + length, Start + length + 3);
                    if(downthree.equals("TAA") || downthree.equals("TGA") || downthree.equals("TAG")) {
                        End = End+3;
                    }
                }
            }
            new Annotation( foundfeat, outPart.getSeq(),null, null, Start, End, null, true, null);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return foundfeat;
    }

    private void parseComposite(XMLParser myParser) {
        //Figure out what format to use for the composite part
        ArrayList<String> formatsinvolved = myParser.getMultipleTagValues("part_short_desc");
        for(String str : formatsinvolved) {
            System.out.println("using format " + str);
        }
        if(formatsinvolved.contains("BBb1 scar sequence")) {
            chosenFormat = Collector.getFormat("org-clothocad-format-bglbricks-connect");
        } else {
            chosenFormat = Collector.getFormat("org-clothocad-format-rfc10-connect");
        }
        if(formatsinvolved.contains("RFC 25 Scar Sequence")) {
            JOptionPane.showMessageDialog(null,
                "RFC 25 (Freiberg standard) Parts are not yet supported.",
                "This is RFC 25",
                JOptionPane.WARNING_MESSAGE);
           // chosenFormat = Collector.getFormat("org-clothocad-format-bglbricks-connect");
        } else {
           // chosenFormat = Collector.getFormat("org-clothocad-format-rfc10-connect");
        }


        //Get all the basic parts used to make the composite part
        ArrayList<Element> nodes = myParser.getNodesOfTag("deep_subparts");
        Element deepNode = nodes.get(0);
        NodeList subpartNodes = deepNode.getChildNodes();
        for(int i=0; i<subpartNodes.getLength(); i++) {
            Node nextnode = subpartNodes.item(i);
            NodeList subparts = nextnode.getChildNodes();
            for(int j=0; j<subparts.getLength(); j++) {
                String tagname = subparts.item(j).getNodeName();
                if(!tagname.equals("part_name")) {
                    continue;
                }
                String subpartname = subparts.item(j).getTextContent();
                
                //If the subpart is actually a scar, ignore it
                if(subpartname.equals("BBa_K112999")) {
                    continue;
                }
                System.out.println("I found " + subpartname + " will now parse that");

                //See if they already have a Part by this name in the database
                Part existpart = Part.retrieveByName(subpartname);
                if(existpart==null) {
                    System.out.println("substring version of subpart name is " + subpartname.substring(4));
                    existpart = Part.retrieveByName(subpartname.substring(4));
                }

                //Grab the version in the Registry
                registryPart basicpartgrab = new registryPart(subpartname, chosenFormat, true);
                Part newpart  = basicpartgrab.getPart();

                //If the Parts are the same, then use the existing version
                if(newpart!=null && existpart!=null) {
                    if(newpart.getHash().equals(existpart.getHash())) {
                        newpart = existpart;
                    }
                }

                if(newpart!=null) {
                    composition.add(newpart);
                    System.out.println(newpart.getName() + " added to the composition");
                } else {
                    System.out.println("newpart was null");
                    return;
                }
            }
        }

        //Create the composite part
        outPart = Part.generateComposite(composition, null, chosenFormat, Collector.getCurrentUser(), partName, partName);
        if(outPart ==null) {
            if(chosenFormat.getUUID().equals("org-clothocad-format-rfc10-connect") || chosenFormat.getUUID().equals("org-clothocad-format-rfctencds-connect")) {
                chosenFormat = Collector.getFormat("org-clothocad-format-spexba-connect");
                outPart = Part.generateComposite(composition, null, chosenFormat, Collector.getCurrentUser(), partName, partName);
            }
        }
    }

    public Color getRandomColor() {
        //HSBtoRGB(float hue, float saturation, float brightness)
        Color newcolor = new Color(Color.HSBtoRGB((float) Math.random()*1.0f, (float) Math.random()*0.5f + 0.5f, (float) Math.random()*0.4f + 0.6f));
        return newcolor;
    }

    public Part getPart() {
        return outPart;
    }

/*-----------------
     variables
 -----------------*/
    private String partName;
    private Format chosenFormat;
    private Part outPart;
    private ArrayList<Part> composition = new ArrayList<Part>();
    private boolean incAnnot = false;

}
