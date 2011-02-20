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

package org.clothocad.format.freeform;

import java.util.ArrayList;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.Part;
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
        //Anything goes
        return true;
    }

    @Override
    public boolean checkVector(Vector v) {
        String seq = v.getSeq().getSeq();

        //If it's a blank Vector, that's ok
        if(seq.equals("")) {
            return true;
        }

        return false;
    }

    @Override
    public boolean checkComposite(ArrayList<Part> composition, Object additionalRequirements) {
        //Can't composite freeform
        return false;
    }

    @Override
    public boolean checkPlasmid(Part p, Vector v, Object additionalRequirements) {
        //Confirm that the Vector obeys freeform
        if(!checkVector(v)) {
            return false;
        }
        return true;
    }

    @Override
    public NucSeq generateCompositeSequence(ArrayList<Part> composition, Object additionalRequirements) {
        //Can't composite freeform
        return null;
    }

    @Override
    public NucSeq generatePlasmidSequence(Plasmid p) {
        //Return the part's NucSeq
        Part thepart = p.getPart();
        if(thepart==null) {
            return null;
        }
        return thepart.getSeq();
    }

    @Override
    public NucSeq generateSequencingRegion(Plasmid p) {
        return generatePlasmidSequence(p);
    }

}
