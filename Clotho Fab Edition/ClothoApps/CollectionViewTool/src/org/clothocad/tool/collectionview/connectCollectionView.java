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

package org.clothocad.tool.collectionview;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.*;
import org.clothocore.api.plugin.ClothoTool;


/**
 *
 * @author J. Christopher Anderson
 */
public class connectCollectionView implements ClothoTool {

    @Override
    public void launch(ObjBase o) {
        if(!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch Collection View's hub!",
                                           "Clotho: Collection View", JOptionPane.ERROR_MESSAGE );
            return;
        }
        if(o.getType().equals(ObjType.COLLECTION)) {
            viewer v = new viewer((Collection) o);
            pig.add(new WeakReference(v));
        }
    }

    @Override
    public void launch() {
        if(!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch Collection View's hub!",
                                           "Clotho: Collection View", JOptionPane.ERROR_MESSAGE );
            return;
        }

        //Throw up a dialog and get user to select the collection stored as 'chosen'
        ArrayList<ObjLink> allColl = Collector.getAllLinksOf(ObjType.COLLECTION);
        if(allColl.isEmpty()) {
            return;
        }
        Object[] allNames = allColl.toArray();
        ObjLink link = (ObjLink) JOptionPane.showInputDialog(null, "Choose one", "Collection",
            JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
        if(link!=null) {
            Collection chosen = Collector.getCollection(link.uuid);
            viewer v = new viewer(chosen);
            pig.add(new WeakReference<Window>(v));
        }
    }

    @Override
    public void close() {
        for(WeakReference<Window> wf: pig) {
            Window gui = wf.get();
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
