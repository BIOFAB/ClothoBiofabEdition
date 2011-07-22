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
package org.clothocad.tool.spectacles;

import javax.swing.JDialog;
import javax.swing.JFrame;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.plugin.ClothoTool;
// import org.clothocad.databaseio.DataCore;
// import org.clothocad.util.UnknownKeywordException;
import org.clothocore.util.basic.UnknownKeywordException;
// import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.clothocad.tool.spectacles.ui.frames.WorkspaceFrame;

/**
 * This is the main class for Spectacles.
 * @author Rich
 */
public class Spectacles implements ClothoTool {

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        System.setProperty("sun.awt.noerasebackground", "true");
        /*
        try {
            javax.swing.UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
        } catch (Exception e) {
            System.out.println(e);
        }
        */
        new WorkspaceFrame().setVisible(true);
    }

    /*
    public static DataCore getDataCore() {
        return _dataCore;
    }

    */

    public static String getHeaderFilesLocation() {
        return _headerFilesLocation;
    }

    public static String getIconPath() {
        return _iconPath;
    }

    public static String getImageLocation() {
        return _imageLocation;
    }

    public static boolean isRunningSolo() {
        return _runningSolo;
    }

    public static void setHeaderFilesLocation(String location) {
        _headerFilesLocation = location;
    }
    
    public static void setImageLocation(String location) {
        _imageLocation = location;
    }

    @Override
    public void init() {
        /*
        _runningSolo = false;
        _iconPath = "data/icons/tools/spectacles_icon.png";
        _imageLocation = "data/tools/spectacles/partsimages/";
        _headerFilesLocation = "data/tools/spectacles/include/";
        _workspaceFrame = new WorkspaceFrame();
        _dataCore = DataCore.getDataCore();
*/
         }

    @Override
    public void launch() {
/*        _workspaceFrame.setVisible(true); */

        _runningSolo = false;
       //_iconPath = "data/icons/tools/spectacles_icon.png";
        _iconPath = "src/org/clothocad/tool/spectacles/spectacles_icon.png";
        _imageLocation = "src/org/clothocad/tool/spectacles/partsimages/";
        //_headerFilesLocation = "data/tools/spectacles/include/";
        _workspaceFrame = new WorkspaceFrame();
       //  _dataCore = DataCore.getDataCore();

        _workspaceFrame.setVisible(true);
        System.out.println("LAUNCHED");
    }

/*
    public Object getData(String object, String field) throws UnknownKeywordException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendData(Object data, ClothoTool sender) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendData(Object data, ClothoTool sender, int opcode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
*/

    private WorkspaceFrame _workspaceFrame;
    // private static DataCore _dataCore = null;
    // use a private static method to resolve the image path
    // all path stuff should go here for easy access
    private static String _iconPath = "tool/spectacles/spectacles_icon.png";
    private static String _imageLocation  = "src/org/clothocad/tool/spectacles/partsimages/";
    private static String _headerFilesLocation = "include/";
    private static boolean _runningSolo = true;

    @Override
    public void launch(ObjBase o) {
        _runningSolo = false;
        _iconPath = "data/icons/tools/spectacles_icon.png";
        _imageLocation = "src/org/clothocad/tool/spectacles/partsimages/";
        _headerFilesLocation = "data/tools/spectacles/include/";
        _workspaceFrame = new WorkspaceFrame();
       //  _dataCore = DataCore.getDataCore();

        _workspaceFrame.setVisible(true);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
