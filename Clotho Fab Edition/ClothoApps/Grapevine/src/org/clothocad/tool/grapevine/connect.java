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

package org.clothocad.tool.grapevine;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Factoid;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.plugin.ClothoTool;

/**
 *
 * @author J. Christopher Anderson
 */
public class connect implements ClothoTool {

    @Override
    public void launch() {
        if(!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch this, connect to the database first!",
                                           "Clotho: Note connected", JOptionPane.ERROR_MESSAGE );


            return;
        }
        //NoteEditor.launchListOfNotes(true);
        new BrowseNotes(new JFrame(), false).setVisible(true);
    }

    @Override
    public void launch(ObjBase o) {
        if(!Collector.isConnected()) {
            return;
        }
        if(o.getType().equals(ObjType.FACTOID)) {
            Factoid chosen = (Factoid) o;
            factoidEditor v = new factoidEditor(chosen);
            pig.add(new WeakReference(v));
        } else if(o.getType().equals(ObjType.NOTE)) {
            System.out.println("launching grapevine connect as viewer");
            Note chosen = (Note) o;
            NoteEditor v = new NoteEditor(chosen);
            pig.add(new WeakReference(v));
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
private ArrayList<WeakReference<Window>> pig= new ArrayList<WeakReference<Window>>();

}
