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

import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import org.clothocore.api.core.PluginTypeEnum;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.plugin.ClothoViewer;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.openide.util.Lookup.Item;
import org.openide.util.Lookup.Template;
import org.openide.util.lookup.Lookups;
import org.openide.filesystems.FileObject;

/**
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public class ViewerWrapper extends PluginWrapper {

    public ViewerWrapper(String uuid, String classname, String displayname, String author,
            String description, String iconpath, String screenshotpath, FileObject file) {
        super(uuid, classname, displayname, author,
                description, iconpath, screenshotpath, file);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    @Override
    public PluginTypeEnum getType() {
        return PluginTypeEnum.ClothoViewer;
    }

    /**
     * Provide a list of the clothocore.api.data objects which are viewable (which is used to associate viewers with)
     * @return a set of ObjTypes
     */
    public Set<ObjType> getViewableTypes() {
        return _viewableSet;
    }

    /**
     * Loads up the class associated with this viewer
     *
     * @return true if it worked, otherwise false
     */
    public boolean launch( final ObjBase o ) {
        //If it's already loaded, launch it
        if ( _viewer == null ) {
            try {
                Template<? extends ClothoViewer> t = new Template<ClothoViewer>( ClothoViewer.class, "plugins/viewers/" + _classname, null );
                Item<? extends ClothoViewer> result = Lookups.forPath( "plugins/viewers" ).lookupItem( t );
                _viewer = result.getInstance();
            } catch ( Exception e ) {
                JOptionPane.showMessageDialog( null, "Plugin viewer loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE );
                return false;
            }
        }

        new Thread( new Runnable() {

            @Override
            public void run() {
                _viewer.launch( o );
            }
        } ).start();
        return true;
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

    /**
     * A to the viewable set list the string provided
     * If the string cannot be converted to a ObjTypem, this will fail
     * @param stype
     */
    public void putViewerType( String stype ) {
        try {
            ObjType type = ObjType.valueOf( stype );
            _viewableSet.add( type );
        } catch ( java.lang.IllegalArgumentException evt ) {
            System.out.println( "One of the plugins has an invalid viewer tag!" );
            return;
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         protected variables               ////

    protected ClothoViewer _viewer = null;
    protected Set<ObjType> _viewableSet = new HashSet<ObjType>();
}
