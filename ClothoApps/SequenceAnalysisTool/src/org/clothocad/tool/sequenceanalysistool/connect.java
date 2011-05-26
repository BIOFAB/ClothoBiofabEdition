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

package org.clothocad.tool.sequenceanalysistool;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.clothocad.tool.sequenceanalysistool.gui.SeqFrame;
import org.clothocore.api.data.*;
import org.clothocore.api.plugin.ClothoTool;

/**
 *
 * @author J. Christopher Anderson
 */
public class connect implements ClothoTool {

    @Override
    public void launch() {
        System.out.println("launch called");
        SeqFrame c = new SeqFrame();
        guis.add(new WeakReference<Window>(c));
    }


    @Override
    public void launch(ObjBase o) {
        try {
            NucSeq template = null;
            switch(o.getType()) {
                case PART:
                    template = ((Part) o).getSeq();
                    break;
                case VECTOR:
                    template = ((Vector) o).getSeq();
                    break;
                case PLASMID:
                    template = ((Plasmid) o).getSeq();
                    break;
                case NUCSEQ:
                    template = (NucSeq) o;
                    break;
                case FEATURE:
                    template = ((Feature) o).getSeq();
                    break;
                case OLIGO:
                    template = ((Oligo) o).getSeq();
                    break;
                default:
                    return;
            }
            SeqFrame c = new SeqFrame(template);
            guis.add(new WeakReference<Window>(c));
        } catch(Exception e) {
        }
    }

    @Override
    public void close() {
        for(WeakReference<Window> wrw: guis) {
            Window w = wrw.get();
            if(w!=null) {
                w.dispose();
            }
        }
    }

    @Override
    public void init() {
    }

/*-----------------
     variables
 -----------------*/
    private ArrayList<WeakReference<Window>> guis = new ArrayList<WeakReference<Window>>();
}
