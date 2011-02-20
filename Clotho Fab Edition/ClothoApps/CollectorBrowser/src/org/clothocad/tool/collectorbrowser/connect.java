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

package org.clothocad.tool.collectorbrowser;

import java.util.Map;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
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
            JOptionPane.showMessageDialog( null, "Database connection required to launch this tool!",
                                           "Not connected", JOptionPane.ERROR_MESSAGE );
            return;
        }
        Collection acoll = new Collection();
        acoll.changeName("Contents of Collector");
        acoll.changeDescription("Snapshot of the contents of the Collector.  The list will not refresh.");
        Map<String, ObjType> links = Collector.getLocalLinks();
        for(String str : links.keySet()) {
            ObjBase obj = Collector.get(links.get(str), str);
            if(obj!=null) {
                acoll.addObject(obj);
            }
        }
        acoll.launchDefaultViewer();

    }

    @Override
    public void launch(ObjBase o) {
    }


    @Override
    public void close() {
    }

    @Override
    public void init() {
    }

/*-----------------
     variables
 -----------------*/
}
