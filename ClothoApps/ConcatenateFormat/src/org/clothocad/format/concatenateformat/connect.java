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

package org.clothocad.format.concatenateformat;

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
        return true;
    }

    @Override
    public boolean checkVector(Vector v) {
        return true;
    }

    @Override
    public boolean checkComposite(ArrayList<Part> composition, Object additionalRequirements) {
        //A BglBrick composite Part should have at least 2 parents
        if(composition.size()<2) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPlasmid(Part p, Vector v, Object additionalRequirements) {
        //Confirm that the Part and Vector exist
        if(p!=null && v!=null) {
            return true;
        }
        return false;
    }

    @Override
    public NucSeq generateCompositeSequence(ArrayList<Part> composition, Object additionalRequirements) {
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
        return p.getPart().getSeq();
    }

}
