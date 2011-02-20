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

package org.clothocad.tool.sledrunner;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.plugin.ClothoTool;



/**
 *
 * @author J. Christopher Anderson
 */
public class connection implements ClothoTool {


    @Override
    public void launch(ObjBase o) {
        hub ahub = null;
        switch(o.getType()) {
            case NUCSEQ:
                ahub = new hub(o, "Nucleic Acid Sequence", ObjType.NUCSEQ);
                break;
            case PART:
                Part p = (Part) o;
                ahub = new hub(o, p.getName(), ObjType.PART);
                break;
            case VECTOR:
                Vector vect = (Vector) o;
                ahub = new hub(o, vect.getName(), ObjType.VECTOR);
                break;
            case OLIGO:
                Oligo anoligo = (Oligo) o;
                ahub = new hub(o, anoligo.getName(), ObjType.OLIGO);
                break;
            case FEATURE:
                Feature feat = (Feature) o;
                ahub = new hub(o, feat.getName(), ObjType.FEATURE);
                break;
            case PLASMID:
                Plasmid plas = (Plasmid) o;
                ahub = new hub(o, plas.getName(), ObjType.PLASMID);
                break;
            default:
                return;
        }
        pig.add(new WeakReference(ahub));
    }

    @Override
    public void launch() {
        if (!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch this tool!",
                                           "Database not connected", JOptionPane.ERROR_MESSAGE );
            return;
        }
        String name = JOptionPane.showInputDialog( "What Feature, Part, Vector, Oligo, or Plasmid do you want to view?" );
        if(name==null) {
            return;
        }

        Part apart = Part.retrieveByName(name);
        if(apart!=null) {
            launch(apart);
            return;
        }

        Vector avect = Vector.retrieveByName(name);
        if(avect!=null) {
            launch(avect);
            return;
        }

        Plasmid aplas = Plasmid.retrieveByName(name);
        if(aplas!=null) {
            launch(aplas);
            return;
        }

        Oligo anoligo = Oligo.retrieveByName(name);
        if(anoligo!=null) {
            launch(anoligo);
            return;
        }

        Feature afeat = Feature.retrieveByName(name);
        if(afeat!=null) {
            launch(afeat);
            return;
        }
    }

    @Override
    public void close() {
        for(WeakReference<Window> wf: pig) {
            Window gui = (Window) wf.get();
            if(gui!=null) {
                gui.dispose();
            }
        }
    }

    @Override
    public void init() {

    }

/*-----------------
     variables
 -----------------*/
        private HashSet<WeakReference<Window>> pig= new HashSet<WeakReference<Window>>();
}
