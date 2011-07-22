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
package org.clothocad.tool.spectacles.eugeneimportexport;

import eugene.Device;
import eugene.Part;
import eugene.Primitive;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import org.netbeans.api.visual.widget.Widget;
import org.clothocad.tool.spectacles.ui.frames.PropertiesDialog;
import org.clothocad.tool.spectacles.ui.scenes.WorkspaceScene;

/**
 * A DevicePartWrapper contains either a Eugene device or a Eugene part,
 * but not both at the same time. This class allows both parts and devices
 * to be stored in the same sequence in Spectacles.
 * @author Joanna
 */
public class DevicePartWrapper {

    /**
     * Creates a new DevicePartWrapper, which stores the Part given to it.
     * @param part the Part to be stored.
     */
    public DevicePartWrapper(Part part) {
        implementations = -1;
        _part = part;
        _device = null;
        _wsScene = null;
    }

    /**
     * Creates a new DevicePartWrapper, which stores the Device given to it
     * and the WorkspaceScene associated with the Device.
     * @param dev the Device to be stored.
     * @param wsScene the WorkspaceScene associated with the device.
     */
    public DevicePartWrapper(Device dev, WorkspaceScene wsScene) {
        implementations = -1;
        _part = null;
        _device = dev;
        _wsScene = wsScene;
    }

    /**
     * If this wrapper contains a part,
     * opens the properties window for that part.
     * @param dialog the PropertiesDialog to display.
     * @param wsScene the WorkspaceScene that the Part is located in.
     */
    public void openPropertiesDialog(PropertiesDialog dialog, WorkspaceScene wsScene) {
        if (isPart()) {
            /* XXX TODO 
             dialog.showDialog(this, wsScene); */
            dialog.showDialog(this, wsScene);
        } else {
            JOptionPane.showMessageDialog(null, "No device properties available.\n" +
                    "To rename this device, go to the device's tab.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns the Device contained in this DevicePartWrapper if it exists,
     * otherwise returns null.
     * <p>It is not recommended to call this method unless <code>isDevice()</code>
     * returns <code>true</code>.</p>
     * @return the Device contained in this DevicePartWrapper.
     */
    public Device getDevice() {
        updatePartsList();
        return _device;
    }

    /**
     * Returns an ArrayList of Strings listing, in order, the Parts of the Device
     * contained in this DevicePartWrapper.  If the contained object is not a Device,
     * returns null.
     * <p>It is not recommended to call this method unless <code>isDevice()</code>
     * returns <code>true</code>.</p>
     * @return an ArrayList of Strings listing the Parts of the wrapped Device in order.
     */
    public ArrayList<String> getDeviceComponentsList() {
        if (isDevice()) {
            updatePartsList();
            return _device.components;
        } else {
            return null;
        }
    }

    /**
     * Returns the image path associated with the wrapped Device or Part.
     * @return the image path.
     */
    public String getImagePath() {
        if (isPart()) {
            return _part.imagePath;
        } else if (isDevice()) {
            return _device.imagePath;
        } else {
            throw new RuntimeException("no object in this wrapper");
        }
    }

    /**
     * Returns the name of the wrapped Device or Part.
     * @return the name of the wrapped Device or Part.
     */
    public String getName() {
        if (isPart()) {
            return _part.name;
        } else if (isDevice()) {
            return _device.name;
        } else {
            throw new RuntimeException("no object in this wrapper");
        }
    }

    /** 
     * Returns the Part contained in this DevicePartWrapper if it exists,
     * otherwise returns null.
     * <p>It is not recommended to call this method unless <code>isPart()</code>
     * returns <code>true</code>.</p>
     * @return the Part contained in this DevicePartWrapper.
     */
    public Part getPart() {
        return _part;
    }

    /** 
     * Returns a HashMap of Strings to Primitives, representing the properties of
     * the Part contained in this DevicePartWrapper.  If the contained object is
     * not a Part, returns null.
     * <p>It is not recommended to call this method unless <code>isPart()</code>
     * returns <code>true</code>.</p>
     * @return a HashMap that stores the properties for the wrapped Part.
     */
    public HashMap<String, Primitive> getPartPropertyValues() {
        if (isPart()) {
            return _part.propertyValues;
        } else {
            return null;
        }
    }

    /**
     * Returns the type of the wrapped Device or Part.
     * @return the type of the wrapped Device or Part.
     */
    public String getType() {
        if (isPart()) {
            return _part.type;
        } else if (isDevice()) {
            return _device.type;
        } else {
            throw new RuntimeException("no object in this wrapper");
        }
    }

    /** 
     * Returns the WorkspaceScene that displays the Parts of the wrapped Device.
     * If the contained object is not a Device, returns null.
     * <p>It is not recommended to call this method unless <code>isDevice()</code>
     * returns <code>true</code>.</p>
     * @return the WorkspaceScene associated with the wrapped Device.
     */
    public WorkspaceScene getWorkspaceScene() {
        if (isDevice()) {
            return _wsScene;
        } else {
            return null;
        }
    }

    /**
     * Returns <code>true</code> if the wrapped object is a Device,
     * and <code>false</code> otherwise.
     * @return <code>true</code> if the wrapped object is a Device,
     * and <code>false</code> otherwise.
     */
    public boolean isDevice() {
        if (_device != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the wrapped object is a Part,
     * and <code>false</code> otherwise.
     * @return <code>true</code> if the wrapped object is a Part,
     * and <code>false</code> otherwise.
     */
    public boolean isPart() {
        if (_part != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the image path for the wrapped Part.
     * @param path
     */
    public void setImagePath(String path) {
        if (isPart()) {
            _part.imagePath = path;
        } else {
            _device.imagePath = path;
        }
    }

    /**
     * Sets the name of the wrapped Device or Part.
     * @param name the name of the Device or Part.
     */
    public void setName(String name) {
        if (isPart()) {
            _part.name = name;
        } else if (isDevice()) {
            _device.name = name;
        }
    }

    // Attempt to add setSequence method to class

    //public void setSequence(String seq, Primitive prim) {
    //    if (isPart()) {
    //        _part.propertyValues = <seq, prim>;
    //    }
    //}

    /**
     * Sets the type of the wrapped Part.
     * @param type the type of the Part.
     */
    public void setType(String type) {
        if (isPart()) {
            _part.type = type;
        }
    }

    /**
     * Updates the list of Parts stored in the wrapped Device to reflect the icons
     * visible in the WorkspaceScene associated with the Device.  
     */
    private void updatePartsList() {
        _device.components.clear();
        boolean hidden = _wsScene.isHidden();
        if (hidden) {
            _wsScene.showContents();
        }
        for (Widget widget : _wsScene.getWidgetList()) {
            DevicePartWrapper wrapper = (DevicePartWrapper) _wsScene.findObject(widget);
            _device.components.add(wrapper.getName());
        }
        if (hidden) {
            _wsScene.hideContents();
        }
    }

    public int implementations;
    private Part _part;
    private Device _device;
    private WorkspaceScene _wsScene;
}
