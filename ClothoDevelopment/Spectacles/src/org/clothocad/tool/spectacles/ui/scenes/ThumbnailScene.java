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

package org.clothocad.tool.spectacles.ui.scenes;

import eugene.Device;
import eugene.Part;
import eugene.Primitive;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.clothocad.tool.spectacles.Spectacles;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;
import org.clothocad.tool.spectacles.eugeneimportexport.Manager;
import org.clothocad.tool.spectacles.ui.frames.PreferencesDialog;
import org.clothocad.tool.spectacles.ui.frames.WorkspaceFrame;

/**
 * ThumbnailScene forms the back end of the palette in WorkspaceFrame.
 * Upon initialization, ThumbnailScene attempts to load part images
 * at the location specified by the user's preferences in PreferencesDialog,
 * and if this fails, a default set of parts is loaded instead.
 * @author Rich
 */
public class ThumbnailScene extends GraphScene {

    /**
     * Creates a new ThumbnailScene and loads in a default parts palette.
     */
    public ThumbnailScene(WorkspaceFrame wsFrame) {
        _wsFrame = wsFrame;
        setKeyEventProcessingType(EventProcessingType.ALL_WIDGETS);
        _mainLayer = new LayerWidget(this);
        addChild(_mainLayer);
        _newPartCount = 0;
        _rowIndexOffset = 0;
        _leftAlign = true;
        _targetWorkspaceScene = null;
        loadPalette();
    }

    /**
     * Given a name of a part or device, generates a DeviceComponentWrapper
     * containing the device or a copy of the part.
     * @param name the name of a part or device
     * @return a DeviceComponetWrapper containing either a part or device,
     * or returns null if no part or device could be found.
     */
    public DevicePartWrapper generateWrapper(String name) {
        if (!_activePaletteMap.containsKey(name)) {
            //throw new NoSuchElementException("Current palette does not contain " + name);
            return null;
        }
        DevicePartWrapper dpw = _activePaletteMap.get(name);
        if (dpw.isPart()) {
            return copyPartWrapper(dpw.getPart());
        } else {
            return copyDeviceWrapper(dpw.getDevice());
        }
    }

    /**
     * Returns the WorkspaceScene where widgets will be dropped.
     * @return the target WorkspaceScene.
     */
    public WorkspaceScene getTargetScene() {
        return _targetWorkspaceScene;
    }

    /**
     * Loads the palette for the current image location.
     * If the images are incorrectly formatted or do not exist,
     * a default palette is loaded instead.
     */
    public void loadPalette() {
        File dir = new File(Spectacles.getImageLocation());
        String[] partsImageNames = dir.list(pngFilter);
        if (partsImageNames == null) { // standalone mode
            // Pop up a warning if we're not using the default image location
            if (!Spectacles.getImageLocation().equals(PreferencesDialog.defaultImageLocation)) {
                JOptionPane.showMessageDialog(_wsFrame,
                    "Problem loading part images; reverting to default part images.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            }
            setPalette(_defaultPartsMap);
            return;
        }
        _mainPalette = new LinkedHashMap<String, DevicePartWrapper>();
        for (String imageName : partsImageNames)
        {
            if (imageName.contains("Placeholder") || imageName.contains("feat") || imageName.contains("part")) {
                continue; // skip over device and placeholder images
            }
            // 1 matches entire part name
            // 2 matches the part type
            // 3 matches the orientation, if any
            // 4 matches the part subtype
            Matcher imageMatcher = SpectaclesFactory.patternPartImageFilename.matcher(imageName);
            if (imageMatcher.matches())
            {
                // load up the palette here
                Part part = new Part();
                String type = imageMatcher.group(2); // type is the Eugene/VBOL type
                String orientation = "Bidirectional";
                if (imageMatcher.group(3) != null) {
                    if (imageMatcher.group(3).equals("+")) {
                        orientation = "Forward";
                    } else if (imageMatcher.group(3).equals("-")) {
                        orientation = "Reverse";
                    }
                }
                Primitive orientationPrimitive = new Primitive("Orientation", "txt");
                orientationPrimitive.txt = orientation;
                part.type = type;
                part.propertyValues.put("Orientation", orientationPrimitive);
                part.imagePath = new String(Spectacles.getImageLocation() + imageName);
                part.name = imageMatcher.group(1);
                String subtype = imageMatcher.group(4);
                if (subtype != null && !subtype.equals("")) {
                    Primitive subtypePrimitive = new Primitive("Subtype", "txt");
                    subtypePrimitive.txt = subtype;
                    part.propertyValues.put("Subtype", subtypePrimitive);
                }
                _mainPalette.put(part.name, new DevicePartWrapper(part));
            }
        }
        if (_mainPalette.keySet().size() > 0) {
            setPalette(_mainPalette);
        } else {
            JOptionPane.showMessageDialog(_wsFrame,
                    "Problem loading part images; reverting to default part images.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            setPalette(_defaultPartsMap);
        }
    }

    /**
     * Sets the parts palette for this ThumbnailScene.
     * @param palette a HashMap containing name-wrapper entries for parts and/or devices.
     */
    public void setPalette(LinkedHashMap<String, DevicePartWrapper> palette) {
        JComboBox partsComboBox = _wsFrame.getPartsComboBox();
        if (_activePaletteMap != null) {
            for (String name : _activePaletteMap.keySet()) {
                removeNode(_activePaletteMap.get(name));
                partsComboBox.removeItem(name);
            }
        }
        _activePaletteMap = palette;
        _mainLayer.removeChildren();
        _rowIndexOffset = 0;
        _leftAlign = true;
        for (String name : _activePaletteMap.keySet()) {
            addNode(_activePaletteMap.get(name));
            partsComboBox.addItem(name);
        }
        validate();
    }

    /**
     * Sets the WorkspaceScene where widgets will be dropped.
     * @param wsScene the target WorkspaceScene.
     */
    public void setTargetScene(WorkspaceScene wsScene) {
        _targetWorkspaceScene = wsScene;
    }

    @Override
    protected Widget attachNodeWidget(Object node) {
        DevicePartWrapper dpw = (DevicePartWrapper) node;
        return createChild(dpw.getName(), SpectaclesFactory.resolveImage(dpw));
    }

    @Override
    protected Widget attachEdgeWidget(Object edge) {
        return null;
    }

    @Override
    protected void attachEdgeSourceAnchor(Object edge, Object oldSourceNode, Object sourceNode) {
    }

    @Override
    protected void attachEdgeTargetAnchor(Object edge, Object oldTargetNode, Object targetNode) {
    }

    /**
     * Given a widget in this ThumbnailScene, generates a DeviceComponentWrapper
     * containing the device or copied part that is associated with the widget.
     * @param widget a widget in this ThumbnailScene.
     * @return a DeviceComponentWrapper.
     */
    protected DevicePartWrapper generateWrapper(Widget widget) {
        DevicePartWrapper dpw = (DevicePartWrapper) findObject(widget);
        if (dpw.isPart()) {
            return copyPartWrapper(dpw.getPart());
        } else {
            return copyDeviceWrapper(dpw.getDevice());
        }
    }

    /**
     * Makes a copy wrapper of the given Eugene device.
     * @param originalDevice a Eugene device.
     * @return a wrapped instance of the Eugene device.
     */
    private DevicePartWrapper copyDeviceWrapper(Device originalDevice) {
        return new DevicePartWrapper(originalDevice, _targetWorkspaceScene);
    }

    /**
     * Makes a copy of the given Eugene part.
     * @param originalPart a Eugene part.
     * @return a wrapped copy of the Eugene part.
     */
    private DevicePartWrapper copyPartWrapper(Part originalPart) {
        Manager manager = _targetWorkspaceScene.getWorkspaceFrame().getManager();
        Part newPart = new Part();
        newPart.imagePath = originalPart.imagePath;
        newPart.type = originalPart.type;
        if (originalPart.propertyValues.containsKey("Subtype")) {
            String subtype = originalPart.propertyValues.get("Subtype").txt;
            newPart.name = originalPart.type + "_" + subtype + _newPartCount++;
        } else {
            newPart.name = originalPart.type + _newPartCount++;
        }
        while (manager.getEugenePartDecs().containsKey(newPart.name)
                || manager.containsWrappedDevice(newPart.name)) {
            newPart.name = originalPart.type + _newPartCount++;
        }
        newPart.propertyValues.putAll(originalPart.propertyValues);
        return new DevicePartWrapper(newPart);
    }

    /**
     * Creates and places a child widget in this ThumbnailScene.
     * Child widgets are arranged in a 2-column grid.
     * Images may be shared by multiple widgets.
     * @param label the widget label.
     * @param image the widget image.
     * @return the child widget.
     */
    private Widget createChild(String label, Image image) {
        IconNodeWidget widget = SpectaclesFactory.createIconNodeWidget(this, label, image);
        if (_leftAlign) {
            widget.setPreferredLocation(
                    new Point(_leftPixelOffset, _leftPixelOffset + _verticalPixelOffset * _rowIndexOffset));
            _leftAlign = false;
        } else {
            widget.setPreferredLocation(
                    new Point(_columnPixelOffset, _leftPixelOffset + _verticalPixelOffset * _rowIndexOffset++));
            _leftAlign = true;
        }
        widget.getActions().addAction(SpectaclesFactory.createThumbnailMouseDropHandler());
        _mainLayer.addChild(widget);
        return widget;
    }

    private WorkspaceFrame _wsFrame;
    private LayerWidget _mainLayer;
    private WorkspaceScene _targetWorkspaceScene;
    private LinkedHashMap<String, DevicePartWrapper> _activePaletteMap;
    private LinkedHashMap<String, DevicePartWrapper> _mainPalette;
    private int _newPartCount;
    private int _rowIndexOffset;
    private boolean _leftAlign;

    private static final int _leftPixelOffset = 12;
    private static final int _columnPixelOffset = _leftPixelOffset + 68;
    private static final int _verticalPixelOffset = 90;

    private static final FilenameFilter pngFilter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".png");
        }
    };

    private static final String[] _defaultPartNames = {"Promoter+", "Promoter-", "RBS", "RBS+", "RBS-", "ORF+", "ORF-",
        "Terminator+", "Terminator-", "Terminator", "RestrictionSite", "PrimerSite+", "PrimerSite-"};
    private static final LinkedHashMap<String, DevicePartWrapper> _defaultPartsMap = new LinkedHashMap<String, DevicePartWrapper>();
    {
        for (String name : _defaultPartNames) {
            Part part = new Part();
            String type; // type is the Eugene/VBOL type
            String orientation;
            if (name.endsWith("+")) {
                orientation = "Forward";
                type = name.substring(0, name.length() - 1);
            } else if (name.endsWith("-")) {
                orientation = "Reverse";
                type = name.substring(0, name.length() - 1);
            } else {
                orientation = "Bidirectional";
                type = name;
            }
            Primitive orientationPrimitive = new Primitive("Orientation", "txt");
            orientationPrimitive.txt = orientation;
            part.type = type;
            part.propertyValues.put("Orientation", orientationPrimitive);
            part.imagePath = new String(PreferencesDialog.defaultImageLocation + "VBOL." + name + ".png");
            part.name = name;
            _defaultPartsMap.put(name, new DevicePartWrapper(part));
        }
    }
}
