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
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.plugin.ClothoTool;
import org.openide.util.Lookup.Item;
import org.openide.util.Lookup.Template;
import org.openide.util.lookup.Lookups;
import org.openide.filesystems.FileObject;

/**
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public class ToolWrapper extends ViewerWrapper {

    public ToolWrapper(String uuid, String classname, String displayname, String author,
            String description, String iconpath, String screenshotpath, FileObject file) {
        super(uuid, classname, displayname, author,
                description, iconpath, screenshotpath, file);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    @Override
    public PluginTypeEnum getType() {
        return PluginTypeEnum.ClothoTool;
    }

    /**
     * Loads up the class associated with this tool wrapper
     *
     * @return true if it successful, otherwise false
     */
    public boolean launchTool() {
        String step = "INITIATING";
        if(_tool == null) {
            try {
                Template<? extends ClothoTool> t = new Template<ClothoTool>( ClothoTool.class, "plugins/tools/" + _classname, null );
                step = "FOUND TEMPLATE OF: " + _classname;
                Item<? extends ClothoTool> result = Lookups.forPath( "plugins/tools" ).lookupItem( t );
                step = "FOUND ITEM OF: " + _classname;
                step = "RESULT IS NOT NULL" + result.getDisplayName() ;
                _tool = result.getInstance();
                step = "FOUND TOOL: " + _tool.toString();
            } catch ( Exception e ) {
                JOptionPane.showMessageDialog( null, "Plugin tool loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
                System.out.println("ToolWrapper loading failed on " + step);
                return false;
            }
        }

        //If it's already loaded, launch it
        if ( _tool != null ) {
            new Thread( new Runnable() {

                @Override
                public void run() {
                    _tool.launch();
                }
            } ).start();
            return true;
        }
        
        JOptionPane.showMessageDialog( null, "Plugin tool loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
        return false;

    }


    /**
     * Loads up the class associated with this as viewer
     * Sets the super classes viewer object as this tool
     * @return true if it worked, otherwise false
     */
    @Override
    public boolean launch( final ObjBase o ) {
        //If it's already loaded, launch it
        if(_tool == null) {
            try {
                Template<? extends ClothoTool> t = new Template<ClothoTool>( ClothoTool.class, "plugins/tools/" + _classname, null );
                Item<? extends ClothoTool> result = Lookups.forPath( "plugins/tools" ).lookupItem( t );
                _tool = result.getInstance();
                super._viewer = _tool;
            } catch ( Exception e ) {
                JOptionPane.showMessageDialog( null, "Plugin tool loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
                return false;
            }
        }

        new Thread( new Runnable() {

            @Override
            public void run() {
                _tool.launch( o );
            }
        } ).start();
        return true;
    }


    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////


    private ClothoTool _tool = null;
}
