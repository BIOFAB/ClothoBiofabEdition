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

package org.clothocad.format.bglbricks;

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

        //Check that it starts with GATCT and ends with G
        if(!seq.startsWith("GATCT")) {
            return false;
        }
        if(!seq.endsWith("G")) {
            return false;
        }

        //Check if the sequence contains any forbidden restriction sites
        if(seq.indexOf("GAATTC") > 0) {
            return false;
        }
        if(seq.indexOf("GGATCC") > 0) {
            return false;
        }
        if(seq.indexOf("AGATCT") > 0) {
            return false;
        }
        if(seq.indexOf("CTCGAG") > 0) {
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

        //Check that it starts with GATCC and ends with A
        if(!seq.startsWith("GATCC")) {
            return false;
        }
        if(!seq.endsWith("A")) {
            return false;
        }

        //Check if the sequence contains any forbidden restriction sites
        if(seq.indexOf("GGATCC") > 0) {
            return false;
        }
        if(seq.indexOf("AGATCT") > 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean checkComposite(ArrayList<Part> composition, Object additionalRequirements) {
        //A BglBrick composite Part should have at least 2 parents
        if(composition.size()<2) {
            return false;
        }

        //Both composite parts need to obey BglBricks, but don't need to explicitly be stated to be BglBricks
        //For example, they could be the AcuI/BseRI variation on BglBricks
        for(Part p: composition) {
            if(!checkPart(p)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean checkPlasmid(Part p, Vector v, Object additionalRequirements) {
        //Confirm that the Part and Vector obey BglBricks
        if(!checkPart(p)) {
            return false;
        }
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

        StringBuffer sb = new StringBuffer();
        for(Part p: composition) {
            sb.append(p.getSeq().getSeq());
        }
        NucSeq outnuc = new NucSeq(sb.toString());
        outnuc.setTransient();

        int offset = 0;
        for(Part p: composition) {
            String parseq = p.getSeq().getSeq();
            for(Annotation ann : p.getSeq().getAnnotations()) {
                Feature afeature = ann.getFeature();
                String name = ann.getName();
                Color forColor = ann.getForwardColor();
                Color revColor = ann.getReverseColor();
                int Start = offset + ann.getStart();
                int End = offset + ann.getEnd();
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
            offset += parseq.length();
        }

        return outnuc;
    }

    @Override
    public NucSeq generatePlasmidSequence(Plasmid p) {
        String out = "";
        out+=p.getPart().getSeq().getSeq();
        out+=p.getVector().getSeq().getSeq();
        NucSeq outnuc = new NucSeq(out);
        outnuc.setTransient();

        
        for(Annotation ann : p.getPart().getSeq().getAnnotations()) {
            Feature afeature = ann.getFeature();
            String name = ann.getName();
            Color forColor = ann.getForwardColor();
            Color revColor = ann.getReverseColor();
            int Start =  ann.getStart();
            int End = ann.getEnd();
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
        int offset = parseq.length();

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
        int bamSite = seq.indexOf("GGATCC");

        //If the Plasmid lacks bam or eco, return the Part sequence
        if(ecoSite <1) {
            return p.getPart().getSeq();
        }
        if(bamSite <1) {
            return p.getPart().getSeq();
        }

        //Most likely the thing needs to be spun around, else grab eco/bam fragment
        String out = "";
        if(ecoSite>bamSite) {
            out += seq.substring(ecoSite);
            out += seq.substring(0,bamSite+6);
        } else {
            out += seq.substring(ecoSite,bamSite+6);
        }
        NucSeq outnuc = new NucSeq(out);
        outnuc.setTransient();
        return outnuc;
    }

}
