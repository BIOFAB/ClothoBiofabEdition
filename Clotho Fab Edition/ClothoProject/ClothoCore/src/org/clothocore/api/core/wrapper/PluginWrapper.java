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

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.clothocore.api.core.PluginTypeEnum;
import org.clothocore.api.core.wrapper.support.ClothoDependency;
import org.clothocore.api.core.wrapper.support.ClothoHelp;
import org.clothocore.util.basic.ImageSource;
import org.openide.util.ImageUtilities;
import org.openide.filesystems.FileObject;

/**
 * Generic base for various plugin wrappers
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public abstract class PluginWrapper {

    protected PluginWrapper( String uuid, String classname, String displayName, String author,
                             String description, String iconpath, String screenshotpath, FileObject file ) {

        _UUID = uuid;
        _classname = classname;
        _displayName = displayName;
        _author = author;
        _description = description;
        _iconPath = iconpath;
        _screenshotPath = screenshotpath;
        _xmlFile = file;
        _helpLink = "http://www.clothocad.org";

        _dependencyObj = null;
        _helpObj = null;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                   ////


    /**
     * Returns the description string
     * @return a String description
     */
    public String getDescription() {
        return _description;
    }

    /**
     * Returns the display name
     * @return a String name for display
     */
    public String getDisplayName() {
        return _displayName;
    }

    /**
     * Provides the image for the icon for this plugin
     * @param height that you want the image at
     * @return ImageIcon
     */
    public ImageIcon getIcon(int height) {
        if (_iconPath == null) {
            return ImageSource.getDefaultIcon(height);
        }
        BufferedImage img = (BufferedImage) ImageUtilities.loadImage(_iconPath, false);

        if (img == null) {
            return ImageSource.getDefaultIcon(height);
        }

        return new ImageIcon(ImageSource.scaleBF(img, height));
    }


    /**
     * Provides the image for the icon for this plugin
     * @param height that you want the image at
     * @return BufferedImage for this icon
     */
    public BufferedImage getIconImage(int height) {
        if (_screenshotPath == null) {
            return ImageSource.getDefaultImage(height);
        }
        BufferedImage img = (BufferedImage) ImageUtilities.loadImage(_iconPath, false);

        if (img == null) {
            return ImageSource.getDefaultImage(height);
        }

        return ImageSource.scaleBF(img, height);
    }

    /**
     * Provides a screenshot of the plugin at the height you request
     * @param height for the screenshot
     * @return ImageIcon for the screenshot
     */
    public ImageIcon getScreenshot(int height) {
        if (_screenshotPath == null) {
            return ImageSource.getDefaultIcon(height);
        }
        BufferedImage img = (BufferedImage) ImageUtilities.loadImage(_screenshotPath, false);

        if (img == null) {
            return ImageSource.getDefaultIcon(height);
        }

        return new ImageIcon(ImageSource.scaleBF(img, height));
    }

    /**
     * Provides a screenshot of the plugin at the height you request
     * @param height for the screenshot
     * @return BufferedImage for the screenshot
     */
    public BufferedImage getScreenshotImage(int height) {
        if (_screenshotPath == null) {
            return ImageSource.getDefaultImage(height);
        }
        BufferedImage img = (BufferedImage) ImageUtilities.loadImage(_screenshotPath, false);

        if (img == null) {
            return ImageSource.getDefaultImage(height);
        }

        return ImageSource.scaleBF(img, height);
    }

    /**
     * An abstract method for getting the type of the plugin
     * @return a PlugInTypeEnum object
     */
    public abstract PluginTypeEnum getType();

    /**
     * Get the uuid for the plugin
     * @return a String for UUID
     */
    public String getUUID() {
        return _UUID;
    }

    /**
     * Get the file object for the xml file that is passed
     * to the wrapper when loaded. This is the file that is associated
     * with an app
     * @return
     */
    public FileObject getXmlFile(){
        return _xmlFile;
    }


    /**
     * Create an object containing information about help
     * @return ClothoHelp object
     */
    public ClothoHelp getClothoHelpObj()
    {
        if(_helpObj == null)
        {
            _helpObj = new ClothoHelp(_xmlFile);
            return _helpObj;
        }
        else
            return _helpObj;
    }

    /**
     * Create an object containing information about dependencies
     * @return
     */
    public ClothoDependency getClothoDependencyObj()
    {
        if(_dependencyObj == null)
        {
            _dependencyObj = new ClothoDependency(_xmlFile);
            return _dependencyObj;
        }
        else
            return _dependencyObj;
    }


    /**
     *
     */
    public abstract void launchHelp();

    @Override
    public String toString() {
        return _displayName;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         protected variables               ////

    protected String _UUID;
    protected String _classname;
    protected String _displayName;
    protected String _author;
    protected String _description;
    protected String _iconPath;
    protected String _screenshotPath;
    protected String _helpLink;
    protected FileObject _xmlFile;

    protected ClothoHelp _helpObj;
    protected ClothoDependency _dependencyObj;
}
