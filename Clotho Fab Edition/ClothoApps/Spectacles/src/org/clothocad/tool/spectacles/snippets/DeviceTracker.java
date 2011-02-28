/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.spectacles.snippets;

import java.util.HashMap;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;

/**
 *
 * @author Joanna
 */
public class DeviceTracker {

    public DeviceTracker() {
        _devices = new HashMap<String, DevicePartWrapper>();
        // store FridFrame too??
    }

    /** Adds a DevicePartWrapper to the hash map storing devices.
     * If the wrapper does not contain a Device object, nothing is added.
     *
     * @return true if a DevicePartWrapper is successfully added,
     * or false if nothing is added.
     */
    public boolean addDevice(DevicePartWrapper wrapper) {
        if (wrapper.isDevice()) {
            _devices.put(wrapper.getName(), wrapper);
            return true;
        }
        return false;
    }

    public boolean containsDevice(String name) {
        return _devices.containsKey(name);
    }

    /** Returns a hash map of DevicePartWrappers; each contains a device */
    public HashMap<String, DevicePartWrapper> getAllDevices() {
        return _devices;
    }

    public DevicePartWrapper getWrappedDevice(String name) {
        return _devices.get(name);
    }

    private HashMap<String, DevicePartWrapper> _devices;

}
