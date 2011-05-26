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

package org.clothocad.format.bambglunrestricted;

import java.util.ArrayList;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.plugin.ClothoFormat;



/**
 * BamBglUnrestricted is the format for parts/vectors with internal restriction
 * sites but end in Bam/Bgl sites and therefore sortov obey the format.
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
        if(!seq.startsWith("GATC")) {
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
        if(!seq.startsWith("GATC")) {
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

        if(composition.size()==2) {
            String out = "";
            out+=composition.get(0).getSeq().getSeq();
            out+=composition.get(1).getSeq().getSeq();
            NucSeq outnuc = new NucSeq(out);
            outnuc.setTransient();
            return outnuc;
        }
        if(composition.size()>2) {
            StringBuffer sb = new StringBuffer();
            for(Part p: composition) {
                sb.append(p.getSeq().getSeq());
            }
            NucSeq outnuc = new NucSeq(sb.toString());
            outnuc.setTransient();
            return outnuc;
        }
        return null;
    }

    @Override
    public NucSeq generatePlasmidSequence(Plasmid p) {
        String out = "";
        out+=p.getPart().getSeq().getSeq();
        out+=p.getVector().getSeq().getSeq();
        NucSeq outnuc = new NucSeq(out);
        outnuc.setTransient();
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
