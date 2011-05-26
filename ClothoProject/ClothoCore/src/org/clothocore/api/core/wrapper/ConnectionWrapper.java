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
import org.clothocore.api.plugin.ClothoConnection;
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
public class ConnectionWrapper extends PluginWrapper {

    public ConnectionWrapper( String uuid, String classname, String displayname, String author,
            String description, String iconpath, String screenshotpath, FileObject file ) {
        super( uuid, classname, displayname, author,
                description, iconpath, screenshotpath, file );
    }


    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////



    /* FIXME: Chris' old comments - still valid??
     * and sets it as the default in objBase
     * It does this on the main thread so that tools that connect and then
     * do something with the connection automatically don't have to be informed
     *
     * ACTUALLY--no, I'll change this back later.  it should be on a separate thread
     * and then notify the code that it connected, which is trickier for quickstart,
     * but probably is the right architecture--that way, if it fails connecting nothing
     * it won't erroneously try to call the database, and I need to do the connection
     * listening anyway to put the "is connected" sorts of notifications in things.
     */

    /**
     * Connects this connection
     *
     * @return
     */
    public boolean connect() {
        if (_connection == null) {
            try {
                Template<? extends ClothoConnection> t = new Template<ClothoConnection>(ClothoConnection.class, "plugins/connections/" + _classname, null);
                Item<? extends ClothoConnection> result = Lookups.forPath("plugins/connections").lookupItem(t);
                _connection = result.getInstance();
                return _connection.connect();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Plugin tool loading failed", "Plugin Failure", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        JOptionPane.showMessageDialog(null, "Reconnection during runtime is not allowed", "Reconnection Failure", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * Get the ClothoConnection object
     * @return ClothoConnection object for this wrapper
     */
    public ClothoConnection getConnection()
    {
        return _connection;
    }

    @Override
    public PluginTypeEnum getType() {
        return PluginTypeEnum.ClothoConnection;
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


    protected ClothoConnection _connection = null;

}
