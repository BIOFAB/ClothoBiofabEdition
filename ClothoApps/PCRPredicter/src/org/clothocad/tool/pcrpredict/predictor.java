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

package org.clothocad.tool.pcrpredict;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.clothocore.api.data.NucSeq;

/**
 *
 * @author J. Christopher Anderson
 */
public class predictor {

    public predictor(NucSeq oligo1, NucSeq oligo2, NucSeq template) {
        init(oligo1, oligo2, template);
    }

    private void init(NucSeq oligo1, NucSeq oligo2, NucSeq template) {
        String temp = template.getSeq();
        String revcomp = template.revComp();
        String o1 = oligo1.getMatcher();
        String o2 = oligo2.getMatcher();

        int forIndex = align(o1, temp);
        if(forIndex==-1) {
            //If that didn't match try flipping template around
            temp = revcomp;
            revcomp = template.getSeq();

            //Do the forOligo on the rev comp
            forIndex = align(o1, temp);
        }

        //If forOligo didn't anneal cleanly either way, abort
        if(forIndex==-1) {
            return;
        }

        //Do the reverse complement
        int revIndex = align(o2, revcomp);
        if(revIndex==-1) {
            return;
        }
        revIndex = temp.length() - revIndex;

        System.out.println("forIndex: " + forIndex);
        System.out.println("revIndex: " + revIndex);

        if(forIndex > revIndex) {
            if(secondtry) {
                return;
            }
            int middle = (forIndex + revIndex) /2;
            String newtemp = temp.substring(middle) + temp.substring(0, middle);
            template.changeSeq(newtemp);
            secondtry = true;
            init(oligo1, oligo2, template);
            return;
        }

        out = o1 + temp.substring(forIndex, revIndex) + oligo2.revComp();
    }

    private static int align(String o1, String temp) {
        //Try first in correct orientation
        int index = -1;
        int start = 0;
        int out = -1;
        bigloop: while(index<0) {
            String test = o1.substring(start);
            index = temp.indexOf(test);

            Pattern p = Pattern.compile(test);

            //Check exact matches in forward orientation
            String[] text = {temp};
            Matcher matcher = p.matcher(text[0]);
            ArrayList<Integer> ends = new ArrayList<Integer>();
            while (matcher.find()) {
                ends.add(matcher.end());
            }

            if(ends.size()==1) {
                out = ends.get(0);
                break bigloop;
            }

            start++;
            if(start> (o1.length() - 6)) {
                break;
            }
        }
        return out;
    }

    public String getResult() {
        return out;
    }

/*-----------------
     variables
 -----------------*/
    String out = "";
    private boolean secondtry = false;
}
