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

package org.clothocad.format.rfctencds;

import java.awt.Color;
import java.util.ArrayList;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.plugin.ClothoFormat;



/**
 * Format Interface is what a ClothoFormat plugin must implement
 * @author jcanderson
 */
public class connect implements ClothoFormat {

    @Override
    public void init() {
    }

    @Override
    public boolean checkPart(Part p) {
        String seq = p.getSeq().getSeq();

        //If it's a blank Part, that's ok
        if(seq.equals("")) {
            return true;
        }

        //Check if the sequence contains any forbidden restriction sites
        if(seq.indexOf("GAATTC") > 0) {
            return false;
        }
        if(seq.indexOf("TCTAGA") > 0) {
            return false;
        }
        if(seq.indexOf("ACTAGT") > 0) {
            return false;
        }
        if(seq.indexOf("CTGCAG") > 0) {
            return false;
        }

        //For RFC10cds, the sequence must start with ATG
        if(!seq.startsWith("ATG")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkVector(Vector v) {
        String seq = v.getSeq().getSeq();

        //If it's a blank Vector, that's ok
        if(seq.equals("")) {
            return true;
        }

        //If it's a blank genomic Vector, that's ok
        if(seq.equals(".")) {
            return true;
        }

        //Check that it starts with CTAGT and ends with T
        if(!seq.startsWith("CTAGT")) {
            return false;
        }
        if(!seq.endsWith("T")) {
            return false;
        }

        //Check if the sequence contains any forbidden restriction sites
        if(seq.indexOf("TCTAGA") > 0) {
            return false;
        }
        if(seq.indexOf("ACTAGT") > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkComposite(ArrayList<Part> composition, Object additionalRequirements) {
        //A  composite Part should have at least 2 parents
        if(composition.size()<2) {
            return false;
        }

        //The first part needs to specify whether it's RFC10 or RFC10cds
        String theform = composition.get(0).getFormat().getPluginId();
        if(!theform.equals("org-clothocad-format-rfctencds-connect")) {
            return false;
        }

        //Make sure that all the parts are in one of the RFC10 formats
        for(Part p: composition) {
            theform = p.getFormat().getPluginId();
            if(theform.equals("org-clothocad-format-rfc10-connect")) {
                continue;
            } else if(theform.equals("org-clothocad-format-rfctencds-connect")) {
                continue;
            } else if(theform.equals("org-clothocad-format-spexba-connect")) {
                String seqy = p.getSeq().getSeq();
                if(!seqy.endsWith("TA")) {
                    return false;
                }
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPlasmid(Part p, Vector v, Object additionalRequirements) {
        //Confirm that the Part obeys RFC10cds
        if(!checkPart(p)) {
            return false;
        }

        //RFC10, RFC10cds, and SpeXba all treat the vector the same way
        if(!checkVector(v)) {
            return false;
        }
        return true;
    }

    @Override
    public NucSeq generateCompositeSequence(ArrayList<Part> composition, Object additionalRequirements) {
        if(!checkComposite(composition, additionalRequirements)) {
            return null;
        }

        int[] offsetarray = new int[composition.size()];

        StringBuffer sb = new StringBuffer();
        for(int i=0; i< composition.size(); i++) {
            offsetarray[i] = 0;
            Part p = composition.get(i);

            //For all but first part, put on the 5' extra sequence
            if(i>0) {
                offsetarray[i] = sb.length();
                String formatId = p.getFormat().getPluginId();
                if(formatId.equals("org-clothocad-format-rfc10-connect")) {
                    sb.append("ctagag");
                    offsetarray[i] += 6;
                } else if(formatId.equals("org-clothocad-format-rfctencds-connect")) {
                    sb.append("ctag");
                    offsetarray[i] += 4;
                }
            }

            //Put the part sequence in
            sb.append(p.getSeq().getSeq());

            //for all but the last part, put on the 3' extra sequence
            if(i<composition.size()-1) {
                String formatId = p.getFormat().getPluginId();
                if(formatId.equals("org-clothocad-format-rfc10-connect")) {
                    sb.append("ta");
                } else if(formatId.equals("org-clothocad-format-rfctencds-connect")) {
                    sb.append("ta");
                }
            }
            //If it's the last sequence and is SpeXba, trim it back
            else {
                String formatId = p.getFormat().getPluginId();
                if(formatId.equals("org-clothocad-format-spexba-connect")) {
                    String seqy = sb.toString();
                    sb = new StringBuffer();
                    sb.append(seqy.substring(0,seqy.length()-2));
                }
            }
        }

        //Create the NucSeq
        NucSeq outnuc = new NucSeq(sb.toString());
        outnuc.setTransient();

        //Add all the new annotations to the NucSeq
        for(int i=0; i< composition.size(); i++) {
            Part p = composition.get(i);
            String parseq = p.getSeq().getSeq();
            for(Annotation ann : p.getSeq().getAnnotations()) {
                Feature afeature = ann.getFeature();
                String name = ann.getName();
                Color forColor = ann.getForwardColor();
                Color revColor = ann.getReverseColor();
                int Start = offsetarray[i] + ann.getStart();
                int End = offsetarray[i] + ann.getEnd();
                Person user = ann.getAuthor();
                boolean plusstrandtrue = ann.isForwardStrand();
                String symbol = ann.getSymbol();

                Annotation newann;
                if(afeature==null) {
                    newann = new Annotation(name, outnuc, forColor, forColor, Start, End, user, plusstrandtrue, symbol);
                    newann.setTransient();
                } else {
                    newann = new Annotation(afeature, outnuc, forColor, forColor, Start, End, user, plusstrandtrue, symbol);
                    newann.setTransient();
                }
                System.out.println("Generated annotation: " + newann.getName());
            }
        }

        return outnuc;
    }
    /**
     * GeneratePlasmid is the same for RFC10, rfctencds, and SpeXba
     * @param p
     * @return
     */
    @Override
    public NucSeq generatePlasmidSequence(Plasmid p) {
        System.out.println("Calling rfc10's generatePlasmidSequence");
        String out = "";
        int partoffset = 0;
        int vectoroffset = 0;

        String formatId = p.getPart().getFormat().getPluginId();
        if(formatId.equals("org-clothocad-format-rfc10-connect")) {
            out+="ctagaG" + p.getPart().getSeq().toString() + "Ta";
            out+=p.getVector().getSeq().toString();
            partoffset = 6;
            vectoroffset = 8;
        } else if(formatId.equals("org-clothocad-format-rfctencds-connect")) {
            out+="ctag" + p.getPart().toString().toString() + "Ta";
            out+=p.getVector().getSeq().toString();
            partoffset = 4;
            vectoroffset = 6;
        } else if(formatId.equals("org-clothocad-format-spexba-connect")) {
            out+=p.getPart().getSeq().getSeq();
            out+=p.getVector().getSeq().getSeq();
        }

        NucSeq outnuc = new NucSeq(out);
        outnuc.setTransient();


        for(Annotation ann : p.getPart().getSeq().getAnnotations()) {
            System.out.println(" rfc10's generatePlasmidSequence working on an annotation");
            Feature afeature = ann.getFeature();
            String name = ann.getName();
            Color forColor = ann.getForwardColor();
            Color revColor = ann.getReverseColor();
            int Start =  partoffset + ann.getStart();
            int End = partoffset + ann.getEnd();
            Person user = ann.getAuthor();
            boolean plusstrandtrue = ann.isForwardStrand();
            String symbol = ann.getSymbol();

            Annotation newann;
            if(afeature==null) {
                newann = new Annotation(name, outnuc, forColor, forColor, Start, End, user, plusstrandtrue, symbol);
                newann.setTransient();
            } else {
                newann = new Annotation(afeature, outnuc, forColor, revColor, Start, End, user, plusstrandtrue, symbol);
                newann.setTransient();
            }
            System.out.println("Generated annotation: " + newann.getName());
        }

        String parseq = p.getPart().getSeq().getSeq();
        int offset = parseq.length() + vectoroffset;

        for(Annotation ann : p.getVector().getSeq().getAnnotations()) {
            Feature afeature = ann.getFeature();
            String name = ann.getName();
            Color forColor = ann.getForwardColor();
            Color revColor = ann.getReverseColor();
            int Start =  offset + ann.getStart();
            int End = offset + ann.getEnd();
            Person user = ann.getAuthor();
            boolean plusstrandtrue = ann.isForwardStrand();
            String symbol = ann.getSymbol();

            Annotation newann;
            if(afeature==null) {
                newann = new Annotation(name, outnuc, forColor, forColor, Start, End, user, plusstrandtrue, symbol);
                newann.setTransient();
            } else {
                newann = new Annotation(afeature, outnuc, forColor, revColor, Start, End, user, plusstrandtrue, symbol);
                newann.setTransient();
            }
            System.out.println("Generated annotation: " + newann.getName());
        }
        return outnuc;
    }

    @Override
    public NucSeq generateSequencingRegion(Plasmid p) {
        String seq = generatePlasmidSequence(p).getSeq();
        int ecoSite = seq.indexOf("GAATTC");
        int pstSite = seq.indexOf("CTGCAG");

        //If the Plasmid lacks xba or spe, return the Part sequence
        if(ecoSite <1) {
            return p.getPart().getSeq();
        }
        if(pstSite <1) {
            return p.getPart().getSeq();
        }

        //Most likely the thing needs to be spun around, else grab eco/bam fragment
        String out = "";
        if(ecoSite>pstSite) {
            out += seq.substring(ecoSite);
            out += seq.substring(0,pstSite+6);
        } else {
            out += seq.substring(ecoSite,pstSite+6);
        }
        NucSeq outnuc = new NucSeq(out);
        outnuc.setTransient();
        return outnuc;
    }

}
