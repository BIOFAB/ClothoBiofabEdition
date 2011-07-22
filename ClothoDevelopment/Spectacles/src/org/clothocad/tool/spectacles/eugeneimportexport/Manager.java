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
import eugene.Rule;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Manager class is used to store information returned by Eugene.
 * @author Joanna
 */
public class Manager {

    /**
     * Creates a new manager that is empty.
     * @param fileName name (usually of a file)
     * associated with the contents of the Manager.
     */
    public Manager(String fileName) {
        _originFileName = fileName;
        //_wrappedComponents = new HashMap<String, DevicePartWrapper>();
        _wrappedDevices = new HashMap<String, DevicePartWrapper>();
        _wrappedParts = new HashMap<String, DevicePartWrapper>();
        _eugenePropertyDefs = new HashMap<String, String>();
        _eugenePropertyDefs.put("Subtype", "txt");
        _eugenePartDefs = new HashMap<String, ArrayList<ArrayList<String>>>();
        _eugenePrimitiveDecs = new HashMap<String, Primitive>();
        _eugenePartDecs = new HashMap<String, Part>();
        _eugeneDeviceDecs = new HashMap<String, Device>();
        _eugeneRuleDecs = new HashMap<String, Rule>();
        _eugeneRuleAssertions = new HashMap<String, ArrayList<Primitive>>();
        _eugeneRuleNotes = new HashMap<String, ArrayList<Primitive>>();
    }

    /**
     * Creates a new Manager that stores the Eugene data given to it.  
     * @param fileName name of the file associated with the contents of the Manager.
     * @param propertyDefs Eugene propertyDefinitions HashMap.
     * @param partDefs Eugene partDefinitions HashMap.
     * @param primitiveDecs Eugene primitiveDeclarations HashMap.
     * @param partDecs Eugene partDeclarations HashMap.
     * @param deviceDecs Eugene deviceDeclarations HashMap.
     * @param ruleDecs Eugene ruleDeclarations HashMap.
     * @param ruleAsserts Eugene ruleAssertions HashMap.
     * @param ruleNotes Eugene ruleNotes HashMap.
     */
    public Manager(String fileName,
                   HashMap<String, String> propertyDefs,
                   HashMap<String, ArrayList<ArrayList<String>>> partDefs,
                   HashMap<String, Primitive> primitiveDecs,
                   HashMap<String, Part> partDecs,
                   HashMap<String, Device> deviceDecs,
                   HashMap<String, Rule> ruleDecs,
                   HashMap<String, ArrayList<Primitive>> ruleAsserts,
                   HashMap<String, ArrayList<Primitive>> ruleNotes) {
        _originFileName = fileName;
        //_wrappedComponents = new HashMap<String, DevicePartWrapper>();
        _wrappedDevices = new HashMap<String, DevicePartWrapper>();
        _wrappedParts = new HashMap<String, DevicePartWrapper>();

        _eugenePropertyDefs = propertyDefs;
        _eugenePartDefs = partDefs;
        _eugenePrimitiveDecs = primitiveDecs;
        _eugenePartDecs = partDecs;
        _eugeneDeviceDecs = deviceDecs;
        _eugeneRuleDecs = ruleDecs;
        _eugeneRuleAssertions = ruleAsserts;
        _eugeneRuleNotes = ruleNotes;

        //_eugenePropertyDefs.put("Subtype", "txt");
        for (String key : _eugenePartDefs.keySet()) {
            ArrayList<ArrayList<String>> partInfo = _eugenePartDefs.get(key);
            ArrayList<String> propertyDecs = partInfo.get(0);
            if (!propertyDecs.contains("Feature_Sequence")) {
                propertyDecs.add("Feature_Sequence");
            }
            if (!propertyDecs.contains("Feature_Name")) {
                propertyDecs.add("Feature_Name");
            }
            if (!propertyDecs.contains("Part_Name")) {
                propertyDecs.add("Part_Name");
            }
            if (!propertyDecs.contains("Part_Description")) {
                propertyDecs.add("Part_Description");
            }
            if (!propertyDecs.contains("Part_Sequence")) {
                propertyDecs.add("Part_Sequence");
            }
            if (!propertyDecs.contains("Part_Format")) {
                propertyDecs.add("Part_Format");
            }
            if (!propertyDecs.contains("Lefty_Part")) {
                propertyDecs.add("Lefty_Part");
            }
            if (!propertyDecs.contains("Righty_Part")) {
                propertyDecs.add("Righty_Part");
            }

            //ArrayList<String> l1 = new ArrayList<String>();
            //l1.add("Feature_Sequence");
            //propertyDecs.add("Subtype");
        }
        for (String key : _eugenePartDecs.keySet()) {
            Part part = _eugenePartDecs.get(key);
            _wrappedParts.put(key, new DevicePartWrapper(part));
        }

       // _eugenePartDefs.p;

        if (!_eugenePropertyDefs.containsKey("Orientation")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Orientation", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Feature_Name")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Feature_Name", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Feature_Sequence")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Feature_Sequence", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Part_Name")) {  // if the property "Part Name" has not already been defined
            _eugenePropertyDefs.put("Part_Name", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Part_Description")) {  // if the property "Part Description" has not already been defined
            _eugenePropertyDefs.put("Part_Description", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Part_Sequence")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Part_Sequence", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Part_Format")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Part_Format", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Lefty_Part")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Lefty_Part", "txt");
        }
        if (!_eugenePropertyDefs.containsKey("Righty_Part")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Righty_Part", "txt");
        }

    }

    /**
     * Adds a Part to the HashMap storing Parts.  Also adds any necessary part
     * definitions and property definitions to their respective HashMaps in
     * order to maintain a valid Eugene data structure.
     * @param part the Part to be added.
     */
    public void addPart(Part part) {
        if (!_eugenePartDefs.containsKey(part.type)) {  // if the part type has not already been defined
            ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
            ArrayList<String> l1 = new ArrayList<String>();
            l1.add("Orientation");
            if (!_eugenePropertyDefs.containsKey("Orientation")) {  // if the property "Part Sequence" has not already been defined
            _eugenePropertyDefs.put("Orientation", "txt");
            }
            l1.add("Feature_Name");
            if (!_eugenePropertyDefs.containsKey("Feature_Name")) {  // if the property "Part Sequence" has not already been defined
                _eugenePropertyDefs.put("Feature_Name", "txt");
            }
            l1.add("Feature_Sequence");
            if (!_eugenePropertyDefs.containsKey("Feature_Sequence")) {  // if the property "Part Sequence" has not already been defined
                _eugenePropertyDefs.put("Feature_Sequence", "txt");
            }
            l1.add("Part_Name");
            if (!_eugenePropertyDefs.containsKey("Part_Name")) {  // if the property "Part Name" has not already been defined
                _eugenePropertyDefs.put("Part_Name", "txt");
            }
            l1.add("Part_Description");
            if (!_eugenePropertyDefs.containsKey("Part_Description")) {  // if the property "Part Description" has not already been defined
                _eugenePropertyDefs.put("Part_Description", "txt");
            }
            l1.add("Part_Sequence");
            if (!_eugenePropertyDefs.containsKey("Part_Sequence")) {  // if the property "Part Sequence" has not already been defined
                _eugenePropertyDefs.put("Part_Sequence", "txt");
            }
            l1.add("Part_Format");
            if (!_eugenePropertyDefs.containsKey("Part_Format")) {  // if the property "Part Sequence" has not already been defined
                _eugenePropertyDefs.put("Part_Format", "txt");
            }
            l1.add("Lefty_Part");
            if (!_eugenePropertyDefs.containsKey("Lefty_Part")) {  // if the property "Part Sequence" has not already been defined
                _eugenePropertyDefs.put("Lefty_Part", "txt");
            }
            l1.add("Righty_Part");
            if (!_eugenePropertyDefs.containsKey("Righty_Part")) {  // if the property "Part Sequence" has not already been defined
                _eugenePropertyDefs.put("Righty_Part", "txt");
            }
            //l1.add("Subtype"); // property "Subtype" is defined during initialization of this class
            ArrayList<String> l2 = new ArrayList<String>();
            l2.add("");
            list.add(l1);
            list.add(l2);
            _eugenePartDefs.put(part.type, list);
        }
        _eugenePartDecs.put(part.name, part);
        _wrappedParts.put(part.name, new DevicePartWrapper(part));
    }

    /**
     * Adds a DevicePartWrapper to the HashMap storing wrapped Devices.
     * If the wrapper does not contain a Device object, nothing is added.
     * @param wrapper the DevicePartWrapper containing the Device.
     * @return <code>true</code> if the wrapper is successfully added, 
     * otherwise returns <code>false</code>.
     */
    public boolean addWrappedDevice(DevicePartWrapper wrapper) {
        if (wrapper.isDevice()) {
            _wrappedDevices.put(wrapper.getName(), wrapper);
            return true;
        }
        return false;
    }

    /*public boolean containsWrappedComponent(String name) {
    return _wrappedComponents.containsKey(name);
    }*/

    /**
     * Returns <code>true</code> if the Manager contains a wrapped Device with
     * name <code>name</code>, otherwise returns <code>false</code>.
     * @param name the name of the Device.
     * @return <code>true</code> if the Manager contains a wrapped Device with
     * name <code>name</code>, and <code>false</code> otherwise.
     */
    public boolean containsWrappedDevice(String name) {
        return _wrappedDevices.containsKey(name);
    }

    /** Returns a hash map of DevicePartWrappers; each contains a component */
    /*public HashMap<String, DevicePartWrapper> getAllWrappedComponents() {
    return _wrappedComponents;
    }*/

    /**
     * Returns the HashMap of DevicePartWrappers, each of which wraps a device
     * @return HashMap of wrapped Devices.
     */
    public HashMap<String, DevicePartWrapper> getAllWrappedDevices() {
        return _wrappedDevices;
    }

    /**
     * Returns the HashMap of Eugene property definitions.
     * @return HashMap of Eugene property definitions.
     */
    public HashMap<String, String> getEugenePropertyDefs() {
        return _eugenePropertyDefs;
    }

    /**
     * Returns the HashMap of Eugene Part definitions.
     * @return HashMap of Eugene Part definitions.
     */
    public HashMap<String, ArrayList<ArrayList<String>>> getEugenePartDefs() {
        return _eugenePartDefs;
    }

    /**
     * Returns the HashMap of Eugene primitive declarations.
     * @return HashMap of Eugene primitive declarations.
     */
    public HashMap<String, Primitive> getEugenePrimitiveDecs() {
        return _eugenePrimitiveDecs;
    }

    /**
     * Returns the HashMap of Eugene Part declarations.
     * @return HashMap of Eugene Part declarations.
     */
    public HashMap<String, Part> getEugenePartDecs() {
        return _eugenePartDecs;
    }

    /**
     * Returns the HashMap of Eugene Device declarations.  Note that this HashMap
     * may not necessarily contain the most current set of Devices.
     * <code>getAllWrappedDevices()</code> should be used instead.
     * @return HashMap of Eugene Device declarations.
     */
    public HashMap<String, Device> getEugeneDeviceDecs() {
        return _eugeneDeviceDecs;
    }

    /**
     * Returns the HashMap of Eugene rule declarations.
     * @return HashMap of Eugene rule declarations.
     */
    public HashMap<String, Rule> getEugeneRuleDecs() {
        return _eugeneRuleDecs;
    }

    /**
     * Returns the HashMap of Eugene rule assertions.
     * @return HashMap of Eugene rule assertions.
     */
    public HashMap<String, ArrayList<Primitive>> getEugeneRuleAssertions() {
        return _eugeneRuleAssertions;
    }

    /**
     * Returns the HashMap of Eugene rule notes.
     * @return HashMap of Eugene rule notes.
     */
    public HashMap<String, ArrayList<Primitive>> getEugeneRuleNotes() {
        return _eugeneRuleNotes;
    }

    /**
     * Returns the name of the file that the contents of this Manager was imported
     * from.  If the contents was not imported from a file, returns the name
     * associated with the Manager upon creation.
     * @return name of file that the contents of the Manager originated from.
     */
    public String getOriginFileName() {
        return _originFileName;
    }

    /*public DevicePartWrapper getWrappedComponent(String name) {
    return _wrappedComponents.get(name);
    }*/

    /**
     * Returns a DevicePartWrapper containing a Device with the specified name
     * @param name the name of the Device.
     * @return the wrapped Device.
     */
    public DevicePartWrapper getWrappedDevice(String name) {
        return _wrappedDevices.get(name);
    }

    /**
     * Returns a DevicePartWrapper containing a Part with the specified name
     * @param name the name of the Part.
     * @return the wrapped Part.
     */
    public DevicePartWrapper getWrappedPart(String name) {
        return _wrappedParts.get(name);
    }

    private HashMap<String, String> _eugenePropertyDefs;
    private HashMap<String, ArrayList<ArrayList<String>>> _eugenePartDefs;
    private HashMap<String, Primitive> _eugenePrimitiveDecs;
    private HashMap<String, Part> _eugenePartDecs;
    private HashMap<String, Device> _eugeneDeviceDecs;
    private HashMap<String, Rule> _eugeneRuleDecs;
    private HashMap<String, ArrayList<Primitive>> _eugeneRuleAssertions;
    private HashMap<String, ArrayList<Primitive>> _eugeneRuleNotes;

    private DevicePartWrapper _dpw;
    private String _originFileName;
    //private HashMap<String, DevicePartWrapper> _wrappedComponents;
    private HashMap<String, DevicePartWrapper> _wrappedDevices;
    private HashMap<String, DevicePartWrapper> _wrappedParts;

}
