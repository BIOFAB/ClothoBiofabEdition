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
package org.clothocore.util.basic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.clothocore.api.data.ObjType;
import org.openide.util.ImageUtilities;

/**
 * imageSource loads the images for all the various icons that represent objBase items and the icons
 * and screenshots for plugins.
 *
 * You can retrieve the _objectImages as a HashMap of either ImageIcons or BufferedImages.
 * Either way, you request them of a specific height up to 100px tall and they come back
 * of a HashMap.  You can store the HashMap in your plugin, then request the icon for a particular
 * type such as:
 *
HashMap<ObjType, ImageIcon> iconset = imageSource.getObjectIconSet(28);
ImageIcon partIcon = iconset.get(ObjType.PART);
 *
 * That would return an ImageIcon for PART that is 28 px tall.
 *
 * @author J. Christopher Anderson
 */
public class ImageSource {

    /**
     * Get an icon set in the form of ImageIcons
     * @param height  height in pixels of the desired icons
     * @return the HashMap
     */
    public static Map<ObjType, ImageIcon> getObjectIconSet( int height ) {
        initiateImages();
        Map<ObjType, ImageIcon> out = new EnumMap<ObjType, ImageIcon>( ObjType.class );

        for ( ObjType type : ObjType.values() ) {
            BufferedImage img = _objectImages.get( type );
            out.put( type, new ImageIcon( scaleBF( img, height ) ) );
        }
        return out;
    }

    public static ImageIcon getObjectImage( ObjType type, int height ) {
        initiateImages();
        BufferedImage img = _objectImages.get( type );
        return new ImageIcon( scaleBF( img, height ) );
    }

    public static ImageIcon getDefaultIcon(int height) {
        initiateImages();
        BufferedImage img = _defaultImg;
        return new ImageIcon( scaleBF( img, height ) );
    }

    public static BufferedImage getDefaultImage(int height) {
        initiateImages();
        BufferedImage img = _defaultImg;
        return scaleBF( img, height );
    }

    public static ImageIcon getClothoLogoIcon(int height, boolean isWhite) {
        BufferedImage img = (BufferedImage) ImageUtilities.loadImage("org/clothocore/images/ClothoLogoWhite.png", false);
        return new ImageIcon( scaleBF( img, height ) );
    }

    /**
     * Get an icon set in the form of BufferedImages
     * @param height height in pixels of the desired icons
     * @return the HashMap
     */
    public static HashMap<ObjType, BufferedImage> getObjectImageSet( int height ) {
        initiateImages();
        HashMap<ObjType, BufferedImage> out = new HashMap<ObjType, BufferedImage>();

        for ( ObjType type : ObjType.values() ) {
            BufferedImage img = _objectImages.get( type );
            out.put( type, scaleBF( img, height ) );
        }
        return out;
    }

    public static void initiateImages() {
        if ( _isInitiated ) {
            return;
        }
        _isInitiated = false;
        _objectImages = new HashMap<ObjType, BufferedImage>();
        for ( ObjType type : ObjType.values() ) {
            BufferedImage img = (BufferedImage) ImageUtilities.loadImage("org/clothocore/images/" + type.toString() + ".png", false);
            _objectImages.put( type, img );
        }
    }

    public static void initiateDefault() {
        _defaultImg = (BufferedImage) ImageUtilities.loadImage("org/clothocore/images/Default.png", false);
    }

    public static void addPluginScreenshot( String uuid, File file ) {
        try {
            BufferedImage img = ImageIO.read( file );
            _pluginScreenshotImages.put( uuid, img );
        } catch ( IOException ex ) {
        }
    }

    public static void addPluginIcon( String uuid, File file ) {
        try {
            BufferedImage img = ImageIO.read( file );
            _pluginIconImages.put( uuid, img );
        } catch ( IOException ex ) {
        }
    }

    /**
     * Get a screenshot for a plugin in the form of ImageIcons
     * @param height  height in pixels of the desired icons
     * @return an ImageIcon
     */
    public static ImageIcon getPluginScreenshot( String pluginuuid, int height ) {
        BufferedImage img = _pluginScreenshotImages.get( pluginuuid );
        return new ImageIcon( scaleBF( img, height ) );
    }

    /**
     * Get a screenshot for a plugin in the form of Buffered Image
     * @param height  height in pixels of the desired icons
     * @return a BufferedImage
     */
    public static BufferedImage getPluginScreenshotAsImage( String pluginuuid, int height ) {
        BufferedImage img = _pluginScreenshotImages.get( pluginuuid );
        return scaleBF( img, height );
    }

    /**
     * Get a laucnh icon (a logo-like graphic) for a plugin in the form of ImageIcons
     * @param height  height in pixels of the desired icons
     * @return an ImageIcon
     */
    public static ImageIcon getPluginIcon( String pluginuuid, int height ) {
        BufferedImage img = _pluginIconImages.get( pluginuuid );
        return new ImageIcon( scaleBF( img, height ) );
    }

    /**
     * Get a laucnh icon (a logo-like graphic) for a plugin in the form of BufferedImage
     * @param height  height in pixels of the desired icons
     * @return a BufferedImage
     */
    public static BufferedImage getPluginIconAsImage( String pluginuuid, int height ) {
        BufferedImage img = _pluginIconImages.get( pluginuuid );
        return scaleBF( img, height );
    }

    public static BufferedImage scaleBF( BufferedImage img, int height ) {
        if ( img == null ) {
            if ( _defaultImg == null ) {
                initiateDefault();
            }
            img = _defaultImg;
        }
        double wide = img.getWidth();
        double high = img.getHeight();

        double aspectratio = wide / high;
        int newwidth = (int) Math.floor( height * aspectratio );

        BufferedImage b = new BufferedImage( newwidth, height, BufferedImage.TYPE_INT_ARGB );
        Graphics g = b.getGraphics();
        g.drawImage( img.getScaledInstance( newwidth, height, Image.SCALE_SMOOTH ), 0, 0, null );

        return b;
    }
    
    public static BufferedImage getTinyLogo() {
        return _tinyClotho;
    }
    /*-----------------
    variables
    -----------------*/
    private static HashMap<ObjType, BufferedImage> _objectImages;
    private static HashMap<String, BufferedImage> _pluginIconImages = new HashMap<String, BufferedImage>();
    private static HashMap<String, BufferedImage> _pluginScreenshotImages = new HashMap<String, BufferedImage>();  //UUID to image map
    private static BufferedImage _defaultImg;
    private static BufferedImage _tinyClotho = (BufferedImage) ImageUtilities.loadImage("org/clothocore/images/ClothoLogoTiny.png", false);
    private static boolean _isInitiated = false;
}