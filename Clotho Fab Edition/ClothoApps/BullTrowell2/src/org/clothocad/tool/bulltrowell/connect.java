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

package org.clothocad.tool.bulltrowell;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.plugin.ClothoTool;
import org.clothocad.tool.bulltrowell.view.hub;


/**
 *
 * @author J. Christopher Anderson
 */
public class connect implements ClothoTool{

    public connect() {
        _prefs = Preferences.userNodeForPackage( connect.class );
        if(!_prefs.get("playBullSound", "true").equals("true")) {
            playBullSound = false;
        }
    }


    @Override
    public void launch() {
        if(playBullSound) {
            new AePlayWave("org/clothocad/tool/bulltrowell/bull.wav").start();
        }
        if (!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch bullTrowell's hub!",
                                           "Clotho: bullTrowell", JOptionPane.ERROR_MESSAGE );
            return;
        }
        currHub = new WeakReference<Window>(new hub());
    }

    @Override
    public void launch(ObjBase o) {
    }

    @Override
    public void close() {
        hub ahub =(hub) currHub.get();
        if(ahub!=null) {
            ahub.dispose();
        }
    }

    @Override
    public void init() {
    }
/*-----------------
     variables
 -----------------*/
    WeakReference<Window> currHub;
    public static Preferences _prefs;
    public static boolean playBullSound = true;
}
