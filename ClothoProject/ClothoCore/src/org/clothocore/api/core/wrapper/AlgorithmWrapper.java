/*
Copyright (c) 2010 The Regents of the University of California.
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

package org.clothocore.api.core.wrapper;

import javax.swing.JOptionPane;
import org.clothocore.api.core.PluginTypeEnum;
import org.clothocore.api.plugin.ClothoAlgorithm;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.openide.util.Lookup.Item;
import org.openide.util.Lookup.Template;
import org.openide.util.lookup.Lookups;
import org.openide.filesystems.FileObject;

/**
 * Wrapper for Clotho Algorithms
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */

public class AlgorithmWrapper extends PluginWrapper {

    public AlgorithmWrapper(String uuid, String classname, String displayname, String author,
            String description, String iconpath, String screenshotpath, FileObject file) {
        super(uuid, classname, displayname, author,
                description, iconpath, screenshotpath, file);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////


    /**
     * Get the type of this wrapper
     * @return the PluginTypeEnum for this wrapper
     */
    @Override
    public PluginTypeEnum getType() {
        return PluginTypeEnum.ClothoAlgorithm;
    }


    /**
     * Launches the tool
     * @return true if successful with the class loading
     */
    public ClothoAlgorithm getAlgorithm() {
        //If it's already loaded, return it
        if ( _plugin != null ) {
            return _plugin;
        }

        try {
            Template<? extends ClothoAlgorithm> t = new Template<ClothoAlgorithm>( ClothoAlgorithm.class, "plugins/algorithms/" + _classname, null );
            Item<? extends ClothoAlgorithm> result = Lookups.forPath( "plugins/algorithms" ).lookupItem( t );
            _plugin = result.getInstance();
            return _plugin;
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog( null, "Plugin tool loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
            return null;
        }
    }
    
    @Override
    public void launchHelp() {
        String helpURL = getClothoHelpObj().getHelpUrl();
        if(helpURL != null){
            if (!helpURL.equals("")) {
                BareBonesBrowserLaunch.openURL(helpURL);
                return;
            }
        }
            
        if (_helpLink != null) {
            if (!_helpLink.equals("")) {
                BareBonesBrowserLaunch.openURL(_helpLink);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         protected variables               ////


    /**
     * The actual ClothoAlgoritm object associated with this wrapper
     */
    protected ClothoAlgorithm _plugin = null;
}
