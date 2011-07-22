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

/*
 * WorkspaceFrame.java
 *
 * Created on Jun 23, 2009, 11:10:09 PM
 */

package org.clothocad.tool.spectacles.ui.frames;

import eugene.Device;
import eugene.Part;
import eugene.Primitive;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
//import java.util.Map.Entry;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
//import org.clothocad.core.ClothoCore;
//import org.clothocad.databaseio.Datum;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
// import org.clothocad.tool.algorithmmanager.AlgorithmManager;
// import org.clothocad.tool.algorithmmanager.AlgorithmManagerEnums;
// import org.clothocad.tool.sequenceview.SequenceViewEnums;
// import org.clothocad.tool.sequenceview.SequenceViewManager;
import org.clothocad.tool.spectacles.Spectacles;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;
import org.clothocad.tool.spectacles.eugeneimportexport.EugeneExporter;
import org.clothocad.tool.spectacles.eugeneimportexport.EugeneImporter;
import org.clothocad.tool.spectacles.eugeneimportexport.Manager;
import org.clothocad.tool.spectacles.ui.scenes.SpectaclesFactory;
import org.clothocad.tool.spectacles.ui.scenes.ThumbnailScene;
import org.clothocad.tool.spectacles.ui.scenes.WorkspaceScene;

/**
 * WorkspaceFrame is the primary component of the Spectacles GUI. It contains
 * controls for importing and exporting Eugene files, as well as a parts
 * canvas and a palette from which parts can be dragged and dropped, and
 * a properties pane for viewing part information.
 * @author Rich
 * @author Joanna
 */
public class WorkspaceFrame extends javax.swing.JFrame {
    // TODO: Add DB highlight activate/clear to settings menu

    /**
     * Creates a new WorkspaceFrame.
     */
    public WorkspaceFrame() {
        initComponents();
        _aboutDialog = new AboutDialog(this);
        _deviceDialog = new DeviceDialog(this, true);
        _propertiesDialog = new PropertiesDialog(this, true);
        _notepad = new NotepadWindow(this);

        _preferences = Preferences.userNodeForPackage(WorkspaceFrame.class);
        _preferencesDialog = new PreferencesDialog(this, _preferences);
        _preferencesDialog.loadPreferences();

        if (Spectacles.isRunningSolo()) {
            setIconImage(ImageUtilities.loadImage(Spectacles.getIconPath()));
            _notepad.setIconImage(ImageUtilities.loadImage(Spectacles.getIconPath()));
            jButtonRefreshParts.setEnabled(false);
        } else {
            File iconFile = new File(Spectacles.getIconPath());
            try {
                setIconImage(ImageIO.read(iconFile));
                _notepad.setIconImage(ImageIO.read(iconFile));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }

            // fileMenuSendSequenceView.setEnabled(true);
            // fileMenuSendAlgorithmManager.setEnabled(true);
            // fileMenuImportAlgorithmManager.setEnabled(true);
        }

        _newDeviceCount = 0;
        _clippedWrapper = null;
        
        _wsSceneList = new ArrayList<WorkspaceScene>();
        _deviceMenuItemMap = new HashMap<DevicePartWrapper, JMenuItem>();
        _activeThumbnailScene = new ThumbnailScene(this);
        thumbnailJSP.setViewportView(_activeThumbnailScene.createView());

        setCurrentFilePath("<noFile>");
        _manager = new Manager(_currentFilePath);

        newTab();
        setModified(false);
    }

    /**
     * Clears the bottom text pane.
     */
    public void clearTextPane() {
        bottomTextPane.setText("");
    }

    /**
     * Clears all tabs and resets the file manager.
     */
    public void clearWorkspace() {
        clearTextPane();
        _activeWorkspaceScene = null;
        _activeThumbnailScene.setTargetScene(null);
        mainJTP.removeAll();
        _wsSceneList.clear();
        devicesMenu.removeAll();
        devicesMenu.add(devicesMenuManageTabs);
        devicesMenu.add(devicesMenuSeparator1);
        setCurrentFilePath("<noFile>");
        _manager = new Manager(_currentFilePath);
        _newDeviceCount = 0;
    }

    /**
     * Closes the currently selected tab.
     */
    public void closeTab() {
        if (mainJTP.getTabCount() > 1) {
            closeTab(mainJTP.getSelectedIndex());
        }
    }

    /**
     * Closes the tab with the correspondingly named device.
     * @param deviceName the device name of the tab to be closed.
     */
    public void closeTab(String deviceName) {
        //if (mainJTP.getTabCount() > 1) {
            int index = mainJTP.indexOfTab(deviceName);
            if (index != -1) {
                closeTab(index);
            }
        //}
    }

    /**
     * Closes the tab at <code>tabIndex</code>.
     * @param tabIndex the index of the tab to be closed.
     */
    public void closeTab(int tabIndex) {
        if (mainJTP.getTabCount() == 2) {
            mainJTPContextMenuCloseDevice.setEnabled(false);
            fileMenuCloseDevice.setEnabled(false);
        }
        if (mainJTP.getTabCount() > 0) {
            DevicePartWrapper device = _manager.getWrappedDevice(mainJTP.getTitleAt(tabIndex));
            devicesMenu.remove(_deviceMenuItemMap.get(device));
            _deviceMenuItemMap.remove(device);
            mainJTP.removeTabAt(tabIndex);
            _wsSceneList.remove(tabIndex).hideContents();
        }
    }

    /**
     * Displays the properties of the currently selected part or device
     * in the bottom text pane. Also updates the scene bindings.
     */
    public void displayProperties() {
        if (mainJTP.getSelectedIndex() == -1) {
            clearTextPane();
            _activeThumbnailScene.setTargetScene(null);
            return;
        }
        WorkspaceScene wsScene = _wsSceneList.get(mainJTP.getSelectedIndex());
        if (_activeWorkspaceScene != wsScene) {
            _activeWorkspaceScene.hideContents();
            _activeWorkspaceScene = wsScene;
            _activeWorkspaceScene.showContents();
            _activeWorkspaceScene.validate();
            _activeThumbnailScene.setTargetScene(_activeWorkspaceScene);
        }
        if (_activeWorkspaceScene.hasFocusedWidget()) {
            Widget widget = _activeWorkspaceScene.getFocusedWidget();
            displayProperties((DevicePartWrapper) _activeWorkspaceScene.findObject(widget));
        } else {
            clearTextPane();
        }
    }

    /**
     * Attempts to close Spectacles.
     */
    public void exit() {
        if (_notepad.closeNotepad()) {
            if (_isModified) {
                int option = promptSave();
                if (option == JOptionPane.YES_OPTION) {
                    exportEugene();
                } else if (option == JOptionPane.CANCEL_OPTION) {
                    _notepad.setVisible(true);
                    return;
                } else if (option == JOptionPane.NO_OPTION
                        && !Spectacles.isRunningSolo()) {
                    clearWorkspace();
                    newTab();
                    setModified(false);
                }
            } else {
                clearWorkspace();
                newTab();
                setModified(false);
            }
            dispose();
            if (Spectacles.isRunningSolo()) {
                System.exit(0);
            }
        }
    }

    /**
     * Returns the currently displayed WorkspaceScene.
     * @return the WorkspaceScene.
     */
    public WorkspaceScene getActiveWorkspaceScene() {
        return _activeWorkspaceScene;
    }

    /**
     * Returns the current file's path.
     * @return the current file path.
     */
    public String getCurrentFilePath() {
        return _currentFilePath;
    }

    /**
     * Returns the number of open tabs.
     * @return the number of open tabs.
     */
    public int getMainJTPTabCount() {
        return mainJTP.getTabCount();
    }

    /**
     * Returns the manager for all devices loaded in this WorkspaceFrame.
     * @return the manager.
     */
    public Manager getManager() {
        return _manager;
    }

    /**
     * Returns the combo box used to list the parts loaded in
     * the active ThumbnailScene.
     * @return the combo box that lists loaded parts.
     */
    public JComboBox getPartsComboBox() {
        return partsComboBox;
    }

    /**
     * Returns the preferences dialog for Spectacles.
     * @return PreferencesDialog.
     */
    public Preferences getPreferences() {
        return _preferences;
    }

    /**
     * Returns the NotepadWindow associated with this WorkspaceFrame.
     * @return the NotepadWindow.
     */
    public NotepadWindow getNotepad() {
        return _notepad;
    }

    /**
     * Takes a wrapped Eugene device and checks if placing it in the active
     * WorkspaceScene will cause a device cycle. Also displays an error
     * message if a cycle would occur.
     * @param wrappedDevice the Eugene device to check.
     * @return <code>true</code> if a cycle exists.
     */
    public boolean hasDeviceCycle(DevicePartWrapper wrappedDevice) {
        DevicePartWrapper activeDevice = _manager.getWrappedDevice(mainJTP.getTitleAt(mainJTP.getSelectedIndex()));
        if (checkDeviceCycle(wrappedDevice, activeDevice, null)) {
            showErrorDialog("Cannot create a device cycle");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the modification status of this WorkspaceFrame.
     * @return <code>true</code> if there are unsaved modifications,
     * and <code>false</code> otherwise.
     */
    public boolean isModified() {
        return _isModified;
    }

    /**
     * Opens a new tab and generates a new device for it.
     */
    public void newTab() {
        Device aDevice = new Device();
        aDevice.name = "NewDevice" + Integer.toString(++_newDeviceCount);
        while (_manager.containsWrappedDevice(aDevice.name)) {
            aDevice.name = "NewDevice" + Integer.toString(++_newDeviceCount);
        }
        aDevice.type = "Device";
        newTab(aDevice);
    }

    /**
     * Given a device, opens a new tab and generates a new wrapper for it.
     * @param device the device to place in the tab.
     */
    public void newTab(Device device) {
        if (_manager.getAllWrappedDevices().size() > 250) {
            showErrorDialog("Device limit reached; cannot create new devices.");
            return;
        }
        if (_activeWorkspaceScene != null) {
            _activeWorkspaceScene.hideContents();
        }
        _activeWorkspaceScene = new WorkspaceScene(this, _propertiesDialog);
        _wsSceneList.add(_activeWorkspaceScene);
        _activeThumbnailScene.setTargetScene(_activeWorkspaceScene);
        DevicePartWrapper wrapper = new DevicePartWrapper(device, _activeWorkspaceScene);
        _manager.addWrappedDevice(wrapper);
        mainJTP.addTab(wrapper.getName(), _activeWorkspaceScene.getJScrollPane());
        mainJTP.setSelectedIndex(mainJTP.getTabCount()-1);
        mainJTP.validate();
        createDeviceMenuItem(wrapper);
        setModified(true);
        if (mainJTP.getTabCount() > 1) {
            mainJTPContextMenuCloseDevice.setEnabled(true);
            fileMenuCloseDevice.setEnabled(true);
        }
    }

    /**
     * Given the name of a device that already has an associated tab,
     * opens the associated tab.
     * @param deviceName the name of the device.
     */
    public void openTab(String deviceName) {
        openTab(_manager.getWrappedDevice(deviceName));
    }

    /**
     * Given a wrapped device that already has an associated tab,
     * opens the associated tab.
     * @param wrappedDevice the wrapped device.
     */
    public void openTab(DevicePartWrapper wrappedDevice) {
        if (wrappedDevice.isPart()) {
            throw new IllegalArgumentException("Wrapper must contain a device.");
        } else if (_wsSceneList.contains(wrappedDevice.getWorkspaceScene())) {
            mainJTP.setSelectedComponent(wrappedDevice.getWorkspaceScene().getJScrollPane());
        } else {
            if (_activeWorkspaceScene != null) {
                _activeWorkspaceScene.hideContents();
            }
            _activeWorkspaceScene = wrappedDevice.getWorkspaceScene();
            _activeWorkspaceScene.showContents();
            _wsSceneList.add(_activeWorkspaceScene);
            _activeThumbnailScene.setTargetScene(_activeWorkspaceScene);
            mainJTP.addTab(wrappedDevice.getName(),
                    wrappedDevice.getWorkspaceScene().getJScrollPane());
            mainJTP.setSelectedIndex(mainJTP.getTabCount() - 1);
        }
        if (mainJTP.getTabCount() > 1) {
            mainJTPContextMenuCloseDevice.setEnabled(true);
            fileMenuCloseDevice.setEnabled(true);
        }
        createDeviceMenuItem(wrappedDevice);
    }

    /**
     * Sets the current file path.
     * @param fileName the file name.
     */
    public void setCurrentFilePath(String fileName) {
        _currentFilePath = fileName;
    }

    /**
     * Sets the current manager.
     * @param manager the manager.
     */
    public void setManager(Manager manager) {
        _manager = manager;
    }

    /**
     * Sets the modified status for this WorkspaceFrame.
     * @param modified the modification status.
     */
    public void setModified(boolean modified) {
        if (modified && !isModified() && !this.getTitle().startsWith("*")) {
            this.setTitle("*" + this.getTitle());
        } else if (!modified && isModified() && this.getTitle().startsWith("*")) {
            this.setTitle(this.getTitle().substring(1));
        }
        _isModified = modified;
    }

    /**
     * Sets the NotepadWidnow associated with this WorkspaceFrame.
     * @param notepad the NotepadWindow.
     */
    public void setNotepad(NotepadWindow notepad) {
        _notepad = notepad;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            super.setVisible(visible);
            _notepad.setLocation(getX() + getWidth(), getY());
            _notepad.setVisible(visible);
            requestFocus(); // might not need this call anymore
            requestFocusInWindow();
        } else {
            // may need to do cleanup here
            super.setVisible(false);
            _notepad.setVisible(false);
        }
    }

    /**
     * Opens a dialog for choosing devices to display.
     */
    public void showDeviceDialog() {
        _deviceDialog.chooseDevices(_manager.getEugeneDeviceDecs().keySet().toArray(), null);
    }

    /**
     * Pops up the Properties dialog for the supplied DevicePartWrapper
     * only if the preference is set in PreferencesDialog. It is assumed
     * that this method is given a wrapped Eugene part.
     * @param dpw the DevicePartWrapper.
     */
    public void showPropertiesDialog(DevicePartWrapper dpw) {
        if (_preferences.getBoolean("PropertiesDialogPopup", PreferencesDialog.defaultPropertiesDialogPopup)) {
            _propertiesDialog.showDialog(dpw, _activeWorkspaceScene);
        }
    }

    /**
     * Reloads the palette in the current ThumbnailScene.
     */
    public void reloadThumbnailScenePalette() {
        _activeThumbnailScene.loadPalette();
    }

    /**
     * Returns <code>true</code> if there is a wrapper on the clipboard,
     * and <code>false</code> otherwise.
     * @return <code>true</code> if there is something to paste.
     */
    public boolean workspaceCanPaste() {
        return _clippedWrapper != null;
    }

    /**
     * If there is a selected widget in the current WorkspaceScene,
     * removes it and places its associated wrapper on the clipboard.
     */
    public void workspaceCut() {
        if (_activeWorkspaceScene.hasFocusedWidget()) {
            workspaceCopy();
            _activeWorkspaceScene.deleteWidget(_activeWorkspaceScene.findWidget(_clippedWrapper));
            setModified(true);
        }
    }

    /**
     * If there is a selected widget in the current WorkspaceScene,
     * copies its associated wrapper to the clipboard.
     */
    public void workspaceCopy() {
        if (_activeWorkspaceScene.hasFocusedWidget()) {
            Widget widget = _activeWorkspaceScene.getFocusedWidget();
            _clippedWrapper = (DevicePartWrapper) _activeWorkspaceScene.findObject(widget);
        }
    }

    /**
     * Assuming there is a wrapper on the clipboard,
     * rewraps the part or device and adds it to the current WorkspaceScene.
     */
    public void workspacePaste() {
        DevicePartWrapper wrapper;
        if (_clippedWrapper.isDevice()) {
            wrapper = new DevicePartWrapper(_clippedWrapper.getDevice(), _activeWorkspaceScene);
        } else {
            wrapper = new DevicePartWrapper(_clippedWrapper.getPart());
        }
        _activeWorkspaceScene.insertNode(wrapper).repaint();
        setModified(true);
    }

    /**
     * Renames all instances of a part with the supplied name.
     * @param wrappedPart a wrapper containing the part to be renamed.
     * @param newName the part's new name.
     */
    protected boolean renamePart(DevicePartWrapper wrappedPart, String newName) {
        String oldName = wrappedPart.getName();
        if (!newName.equals(oldName) && validateName(newName))
        { // Check for valid name
            wrappedPart.setName(newName); // change the name of the part
            _manager.getEugenePartDecs().remove(oldName); // change name in PartDeclarations HashMap
            _manager.getEugenePartDecs().put(newName, wrappedPart.getPart());
            return true;
        }
        return false;
    }

    /**
     * Given a part or device name, propagates any name change
     * through all WorkspaceScenes containing a wrapped instance
     * of that part or device. Also updates the border for
     * the associated widgets.
     * @param name the part or device name.
     */
    protected void updateAssociatedWidgets(String name) {
        for (String aDeviceName : _manager.getAllWrappedDevices().keySet())
        {  // loop through all the Devices
            DevicePartWrapper aDeviceWrapper = _manager.getAllWrappedDevices().get(aDeviceName);
            WorkspaceScene deviceWSS = aDeviceWrapper.getWorkspaceScene();
            if (aDeviceWrapper.isDevice() && deviceWSS == _activeWorkspaceScene)
            { // getDeviceComponentsList() updates the names in the parts list
                int listSize = aDeviceWrapper.getDeviceComponentsList().size();
                for (int i=0; i < listSize; i++)
                { // go through the scene's parts and devices
                    if (aDeviceWrapper.getDeviceComponentsList().get(i).equals(name))
                    { // update the associated icon widgets
                        deviceWSS.updateWidget(
                                (IconNodeWidget) deviceWSS.getWidgetList().get(i));
                    }
                }
            }
            deviceWSS.validate();
        }
    }

    /**
     * Takes a device or part name candidate and checks if it is valid.
     * Valid names must start with a letter or an underscore, can contain only
     * letters, numbers, and underscores, and must not be the same as a name
     * already in use by another part or device.
     * This method shows a message dialog if the name is invalid.
     * @param newName the name to check.
     * @return <code>true</code> if the name is valid, <code>false</code> otherwise.
     */
    protected boolean validateName(String newName) {
        Matcher nonWordMatcher = SpectaclesFactory.patternNonWord.matcher(newName);
        Matcher digitMatcher = SpectaclesFactory.patternDigit.matcher(newName.substring(0, 1));
        if (newName.equals("")) {
            showErrorDialog("Invalid name: cannot rename to empty string.");
        } else if (nonWordMatcher.lookingAt()) {
            showErrorDialog("Invalid name: " + newName + "\nName can only contain letters, numbers, and underscores.");
        } else if (digitMatcher.matches()) {
            showErrorDialog("Invalid name: " + newName + "\nName can only start with a letter or an underscore.");
        } else if (_manager.getAllWrappedDevices().containsKey(newName) || _manager.getEugenePartDecs().containsKey(newName)) {
            showErrorDialog("Invalid name: " + newName + "\nThis name is already being used.");
        } else {  // if all name checks pass
            return true;
        }
        return false;
    }

    /**
     * Checks for a device cycle if sourceDevice is placed inside enclosingDevice.
     * @param sourceDevice the Eugene device to check.
     * @param enclosingDevice the Eugene device where sourceDevice will be placed.
     * @param subDevice a sub-device of sourceDevice; set to <code>null</code> to start the recursion.
     * @return <code>true</code> if there is a device cycle, and <code>false</code> otherwise.
     */
    private boolean checkDeviceCycle(DevicePartWrapper sourceDevice, DevicePartWrapper enclosingDevice, DevicePartWrapper subDevice) {
        if (enclosingDevice.getDevice() == sourceDevice.getDevice()) {
            return true;
        } else if (subDevice != null) {
            if (enclosingDevice.getDevice() == subDevice.getDevice()) {
                return true;
            }
            WorkspaceScene wsScene = subDevice.getWorkspaceScene();
            if (wsScene != null) {
                List<Widget> widgets = wsScene.getWidgetList();
                List<Object> wrappers = wsScene.getDPWList();
                if (widgets.size() == 0 && wrappers.size() == 0) {
                    return false;
                }
                int size;
                if (wsScene.isHidden()) {
                    size = wrappers.size();
                } else {
                    size = widgets.size();
                }
                for (int i = 0; i < size; i++) {
                    DevicePartWrapper wrapper;
                    if (wsScene.isHidden()) {
                        wrapper = (DevicePartWrapper) wrappers.get(i);
                    } else {
                        wrapper = (DevicePartWrapper) wsScene.findObject(widgets.get(i));
                    }
                    if (wrapper.isDevice() && checkDeviceCycle(sourceDevice, enclosingDevice, wrapper)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Given a wrapped device that is already associated with a tab,
     * creates a menu item for the Devices menu that opens the associated tab.
     * @param wrapper a wrapped device.
     */
    private void createDeviceMenuItem(final DevicePartWrapper wrappedDevice) {
        if (_deviceMenuItemMap.containsKey(wrappedDevice)) {
            devicesMenu.add(_deviceMenuItemMap.get(wrappedDevice));
        } else {
            JMenuItem deviceMenuItem = new JMenuItem();
            deviceMenuItem.setText(wrappedDevice.getName());
            deviceMenuItem.setName(wrappedDevice.getName());
            deviceMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int index = mainJTP.indexOfTab(wrappedDevice.getName());
                    if (index != -1) {
                        mainJTP.setSelectedIndex(index);
                    } else {
                        openTab(wrappedDevice);
                        mainJTP.validate();
                    }
                }
            });
            devicesMenu.add(deviceMenuItem);
            _deviceMenuItemMap.put(wrappedDevice, deviceMenuItem);
        }
    }

    /**
     * Given a wrapped part or device, displays its properties
     * in the bottom text pane of this WorkspaceFrame.
     * @param wrapper a wrapped part or device.
     */
    private void displayProperties(DevicePartWrapper wrapper) {
        String displayText = "Name: " + wrapper.getName() + "\n";
        displayText += "Type: " + wrapper.getType() + "\n";
        if (wrapper.isPart()) {
            displayText += "Properties:\n";
            for (String propertyName : wrapper.getPartPropertyValues().keySet()) {
                Primitive propertyContent = wrapper.getPartPropertyValues().get(propertyName);
                displayText += "  " + propertyContent.name + ": ";
                if (propertyContent.type.equals("txt")) {
                    displayText += propertyContent.txt;
                } else if (propertyContent.type.equals("num")) {
                    displayText += propertyContent.num;
                } else if (propertyContent.type.equals("txtList")) {
                    displayText += propertyContent.txtList.toString();
                } else if (propertyContent.type.equals("numList")) {
                    displayText += propertyContent.numList.toString();
                }
                displayText += "\n";
            }
        } else if (wrapper.isDevice()) {
            displayText += "Parts: ";
            for (String text : wrapper.getDeviceComponentsList()) {
                displayText += text + " ";
            }
            displayText += "\n";
        }
        bottomTextPane.setText(displayText);
        bottomTextPane.setCaretPosition(0);
    }

    /**
     * Exports the contents of this WorkspaceFrame to a Eugene file.
     */
    private void exportEugene() {
        EugeneExporter exporter = new EugeneExporter(this);
        if (exporter.runExportToFile()) {
            setModified(false);
        }
    }

    /**
     * Exports the contents of this WorkspaceFrame to
     * its associated NotepadWindow.
     */
    private void exportNotepad() {
        boolean modifiedStatus = isModified();
        EugeneExporter exporter = new EugeneExporter(this);
        String text;
        try {
            text = exporter.runExportToNotepad();
            if (text == null) {
                return;
            }
        } catch (Exception ex) {
            showErrorDialog("Problem sending to notepad: " + ex.getMessage());
            return;
        }
        setModified(modifiedStatus);
        _notepad.newNote();
        _notepad.setNotepadText(text);
        _notepad.requestFocus();
    }

    /**
     * Given a wrapped Eugene device, returns a string containing
     * the names of the device's component parts. Sub-devices are
     * preceded by newline characters. If a device is empty, it
     * is represented with an underscore, and if a part has no
     * associated sequence, it is represented with a dash.
     * @param wrappedDevice a wrapped Eugene device.
     * @return a string containing the names for all of
     * the device's sub-parts.
     */
    private String getNames(DevicePartWrapper wrappedDevice) {
        String names = "=";
        WorkspaceScene wsScene = wrappedDevice.getWorkspaceScene();
        List<Widget> widgets = null;
        List<Object> wrappers = null;
        if (wsScene != null) {
            widgets = wsScene.getWidgetList();
            wrappers = wsScene.getDPWList();
        }
        if (wsScene.isHidden() && wrappers != null) {
            names = "";
            if (wrappers.size() == 0) {
                return "_";
            }
            for (Object o : wrappers) {
                DevicePartWrapper wrapper = (DevicePartWrapper) o;
                if (wrapper.isPart()) {
                    names += "." + wrapper.getName();
                } else {
                    names += "\n" + getNames(wrapper).substring(1);
                }
            }
        } else if (widgets != null) {
            names = "";
            if (widgets.size() == 0) {
                return "_";
            }
            for (Widget w : widgets) {
                DevicePartWrapper wrapper = (DevicePartWrapper) wsScene.findObject(w);
                if (wrapper.isPart()) {
                    names += "." + wrapper.getName();
                } else {
                    names += "\n" + getNames(wrapper).substring(1);
                }
            }
        }
        return names;
    }

    /**
     * Given a wrapped Eugene device, returns a string containing
     * the sequence data of the device's component parts
     * concatenated on a single line. If a device is empty, it
     * is represented with an underscore, and if a part has no
     * associated sequence, it is represented with a dash.
     * @param wrappedDevice a wrapped Eugene device.
     * @return a string containing the sequence data for all of the
     * device's sub-parts.
     */
    private String getSequence(DevicePartWrapper wrappedDevice, String property) {
        String sequence = "";
        WorkspaceScene wsScene = wrappedDevice.getWorkspaceScene();
        List<Widget> widgets = null;
        List<Object> wrappers = null;
        if (wsScene != null) {
            widgets = wsScene.getWidgetList();
            wrappers = wsScene.getDPWList();
        }
        if (wsScene.isHidden() && wrappers != null) {
            if (wrappers.size() == 0) {
                return "_";
            }
            for (Object o : wrappers) {
                DevicePartWrapper wrapper = (DevicePartWrapper) o;
                if (wrapper.isPart()) {
                    if (wrapper.getPartPropertyValues().containsKey(property)) {
                        sequence += wrapper.getPartPropertyValues().get(property).txt;
                    } else {
                        sequence += "-";
                    }
                } else {
                    sequence += getSequence(wrapper, property);
                }
            }
        } else if (widgets != null) {
            if (widgets.size() == 0) {
                return "_";
            }
            for (Widget w : widgets) {
                DevicePartWrapper wrapper = (DevicePartWrapper) wsScene.findObject(w);
                if (wrapper.isPart()) {
                    if (wrapper.getPartPropertyValues().containsKey(property)) {
                        sequence += wrapper.getPartPropertyValues().get(property).txt;
                    } else {
                        sequence += "-";
                    }
                } else {
                    sequence += getSequence(wrapper, property);
                }
            }
        }
        return sequence;
    }

    /**
     * Imports a Eugene file into this WorkspaceFrame.
     */
    private void importEugene() {
        if (isModified()) {
            int prompt = promptSave();
            if (prompt == JOptionPane.YES_OPTION) {
                exportEugene();
            } else if (prompt == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        EugeneImporter importer = new EugeneImporter(this);
        try {
            importer.runImportFromFile();
            displayProperties();
        } catch (IOException ioe) {
            showErrorDialog(ioe.getMessage());
        } catch (NullPointerException npe) {
            showErrorDialog("Invalid input file.");
        }
    }

    /**
     * Imports a Eugene file generated from the
     * NotepadWindow associated with this WorkspaceFrame.
     */
    private void importNotepad() {
        if (isModified()) {
            int prompt = promptSave();
            if (prompt == JOptionPane.YES_OPTION) {
                exportEugene();
            } else if (prompt == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        EugeneImporter importer = new EugeneImporter(this);
        try {
            String npt = _notepad.getNotepadText();
            if (npt.equals("")) {
                showErrorDialog("No Eugene code in Notepad.");
                return;
            }
            importer.runImportFromNotepad(npt);
            displayProperties();
        } catch (IOException ioe) {
            showErrorDialog(ioe.getMessage());
        } catch (NullPointerException npe) {
            showErrorDialog("Invalid input in Notepad.");
        }
    }

    /**
     * Prompts the user with a save dialog.
     * @return the user's selction.
     */
    private int promptSave() {
        int option = JOptionPane.showConfirmDialog(
            this, "Save this device?", "Save",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        return option;
    }

    /**
     * Refreshes sequence data for parts that are linked to the database.
     * Also updates part highlighting for tool mode.
     */
    // TODO: Add DB highlight update code in this method
    private void refreshParts() {
        String partObjKywd = _propertiesDialog.getPartObjectKeyword();
        Collection<Part> allParts = _manager.getEugenePartDecs().values();
        for (Part part : allParts) {
            if (part.propertyValues.containsKey("ID") && !part.propertyValues.get("ID").txt.equals("")) {
               // XXX FIXME
               // Iterator<Datum> iter = Spectacles.getDataCore().findUsingHQL(
               //         "from " + partObjKywd + " as part where part.id = " + part.propertyValues.get("ID").txt);
               // if (iter.hasNext()) {
               //     //try {
               //         Datum partDatum = iter.next();
               //         Primitive sequencePrimitive = new Primitive("Sequence", "txt");
               //         sequencePrimitive.txt = partDatum.getFieldAsString("sequence");
               //         part.propertyValues.put("Sequence", sequencePrimitive);
               //         updateAssociatedWidgets(part.name);
               //     //} catch (DataFormatException ex) {
               //         // TODO: find how to catch the exception that needs this error message - can I even catch it?
               //         /*JOptionPane.showMessageDialog(this,
               //         "'" + part.propertyValues.get("ID").txt + "' is not a valid database ID",
               //         "Error", JOptionPane.ERROR_MESSAGE);*/
               //         //Exceptions.printStackTrace(ex);
               //     //} catch (NoSuchFieldException ex) {
               //         //Exceptions.printStackTrace(ex);
               //     //}
               // } else {
               //     showErrorDialog("The database object with ID " +  part.propertyValues.get("ID").txt + " does not exist.");
               // }
            }
        }
        displayProperties();
    }

    /**
     * Prompts the user with a dialog to rename the device
     * associated with the currently selected tab.
     */
    private void renameDevice() {
        String oldName = mainJTP.getTitleAt(mainJTP.getSelectedIndex());
        String newName = (String) JOptionPane.showInputDialog(this, "New name:",
                "Rename Device", JOptionPane.PLAIN_MESSAGE, null, null, oldName);
        //System.out.println(newName);
        if (newName == null || newName.equals(oldName) || !validateName(newName)) {
            // if the user cancels or name not actually changed
            return;
        }

        mainJTP.setTitleAt(mainJTP.getSelectedIndex(), newName);  // rename tab
        DevicePartWrapper wrappedDevice = _manager.getAllWrappedDevices().remove(oldName);  // change name in wrappedDevices HashMap
        wrappedDevice.setName(newName);  // change the name of the device
        //System.out.println(wrappedDevice.getName());
        JMenuItem deviceMenuItem = _deviceMenuItemMap.get(wrappedDevice);
        deviceMenuItem.setText(newName);
        deviceMenuItem.setName(newName);
        _manager.getAllWrappedDevices().put(newName, wrappedDevice);
        updateAssociatedWidgets(newName);
        setModified(true);
    }

    /**
     * Outputs the contents of the current WorkspaceScene
     * as one long PNG image.
     */
    private void sendPicture() {
        // open file chooser
        // grab widget list
        // stitch images together
        // output and return
        List<Widget> widgets = _activeWorkspaceScene.getWidgetList();
        if (widgets.size() == 0) {
            showErrorDialog("Sequence is empty!");
            return;
        }
        if (JFileChooser.APPROVE_OPTION == _pngFileChooser.showSaveDialog(this)) {
            int imgWidth = WorkspaceScene.iconWidth;
            int outWidth = widgets.size()*imgWidth;
            int imgHeight = WorkspaceScene.iconHeight;
            int heightOffset = 0;
            int w = 0;
            if (_preferences.getBoolean("ImageOutputBoxes", PreferencesDialog.defaultImageOutputBoxes)) {
                // adjust width/height, borders
                w += 1;
                imgWidth += 1;
                outWidth = (widgets.size()*imgWidth)+1;
                imgHeight += 2;
                heightOffset += 1;
            }
            BufferedImage out = new BufferedImage(outWidth,
                    imgHeight, BufferedImage.TYPE_3BYTE_BGR);
            Graphics g = out.getGraphics();
            for (Widget widget : widgets) {
                g.drawImage(((IconNodeWidget) widget).getImageWidget().getImage(),
                        w, heightOffset, Color.WHITE, null);
                w += imgWidth;
            }
            g.dispose();
            try {
                String outFileName = _pngFileChooser.getSelectedFile().getAbsolutePath();
                if (!outFileName.endsWith(".png")) {
                    outFileName += ".png";
                }
                ImageIO.write(out, "png", new File(outFileName));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    /**
     * Convenience method for displaying error messages.
     * @param message the error message.
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainJTPContextMenu = new javax.swing.JPopupMenu();
        mainJTPContextMenuNewDevice = new javax.swing.JMenuItem();
        mainJTPContextMenuRenameDevice = new javax.swing.JMenuItem();
        mainJTPContextMenuCloseDevice = new javax.swing.JMenuItem();
        jButtonImport = new javax.swing.JButton();
        jButtonExport = new javax.swing.JButton();
        jButtonSendNotepad = new javax.swing.JButton();
        jButtonImportNotepad = new javax.swing.JButton();
        jButtonRefreshParts = new javax.swing.JButton();
        partsComboBox = new javax.swing.JComboBox();
        thumbnailJSP = new javax.swing.JScrollPane();
        mainJTP = new javax.swing.JTabbedPane();
        bottomJSP = new javax.swing.JScrollPane();
        bottomTextPane = new javax.swing.JTextPane();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileMenuImport = new javax.swing.JMenuItem();
        fileMenuExport = new javax.swing.JMenuItem();
        fileMenuSeparator1 = new javax.swing.JSeparator();
        fileMenuImportNotepad = new javax.swing.JMenuItem();
        fileMenuSendNotepad = new javax.swing.JMenuItem();
        fileMenuSeparator2 = new javax.swing.JSeparator();
        fileMenuSendPicture = new javax.swing.JMenuItem();
        fileMenuSeparator3 = new javax.swing.JSeparator();
        fileMenuNewDevice = new javax.swing.JMenuItem();
        fileMenuCloseDevice = new javax.swing.JMenuItem();
        fileMenuSeparator4 = new javax.swing.JSeparator();
        fileMenuShowAllDevices = new javax.swing.JMenuItem();
        fileMenuClearWorkspace = new javax.swing.JMenuItem();
        fileMenuSeparator5 = new javax.swing.JSeparator();
        fileMenuExit = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editMenuCut = new javax.swing.JMenuItem();
        editMenuCopy = new javax.swing.JMenuItem();
        editMenuPaste = new javax.swing.JMenuItem();
        editMenuDelete = new javax.swing.JMenuItem();
        editMenuSeparator1 = new javax.swing.JSeparator();
        editMenuProperties = new javax.swing.JMenuItem();
        editMenuSeparator2 = new javax.swing.JSeparator();
        editMenuRenameDevice = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        settingsMenuPreferences = new javax.swing.JMenuItem();
        devicesMenu = new javax.swing.JMenu();
        devicesMenuManageTabs = new javax.swing.JMenuItem();
        devicesMenuSeparator1 = new javax.swing.JSeparator();
        helpMenu = new javax.swing.JMenu();
        helpMenuAbout = new javax.swing.JMenuItem();

        mainJTPContextMenu.setName("mainJTPContextMenu"); // NOI18N

        mainJTPContextMenuNewDevice.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.mainJTPContextMenuNewDevice.text")); // NOI18N
        mainJTPContextMenuNewDevice.setName("mainJTPContextMenuNewDevice"); // NOI18N
        mainJTPContextMenuNewDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainJTPContextMenuNewDeviceActionPerformed(evt);
            }
        });
        mainJTPContextMenu.add(mainJTPContextMenuNewDevice);

        mainJTPContextMenuRenameDevice.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.mainJTPContextMenuRenameDevice.text")); // NOI18N
        mainJTPContextMenuRenameDevice.setName("mainJTPContextMenuRenameDevice"); // NOI18N
        mainJTPContextMenuRenameDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainJTPContextMenuRenameDeviceActionPerformed(evt);
            }
        });
        mainJTPContextMenu.add(mainJTPContextMenuRenameDevice);

        mainJTPContextMenuCloseDevice.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.mainJTPContextMenuCloseDevice.text")); // NOI18N
        mainJTPContextMenuCloseDevice.setEnabled(false);
        mainJTPContextMenuCloseDevice.setName("mainJTPContextMenuCloseDevice"); // NOI18N
        mainJTPContextMenuCloseDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainJTPContextMenuCloseDeviceActionPerformed(evt);
            }
        });
        mainJTPContextMenu.add(mainJTPContextMenuCloseDevice);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.title")); // NOI18N
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(512, 415));
        setName("WorkspaceForm"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButtonImport.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonImport.text")); // NOI18N
        jButtonImport.setToolTipText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonImport.toolTipText")); // NOI18N
        jButtonImport.setName("jButtonImport"); // NOI18N
        jButtonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportActionPerformed(evt);
            }
        });

        jButtonExport.setText("Export Eugene");
        jButtonExport.setToolTipText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonExport.toolTipText")); // NOI18N
        jButtonExport.setName("jButtonExport"); // NOI18N
        jButtonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportActionPerformed(evt);
            }
        });

        jButtonSendNotepad.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonSendNotepad.text")); // NOI18N
        jButtonSendNotepad.setToolTipText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonSendNotepad.toolTipText")); // NOI18N
        jButtonSendNotepad.setName("jButtonSendNotepad"); // NOI18N
        jButtonSendNotepad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendNotepadActionPerformed(evt);
            }
        });

        jButtonImportNotepad.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonImportNotepad.text")); // NOI18N
        jButtonImportNotepad.setToolTipText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonImportNotepad.toolTipText")); // NOI18N
        jButtonImportNotepad.setName("jButtonImportNotepad"); // NOI18N
        jButtonImportNotepad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportNotepadActionPerformed(evt);
            }
        });

        jButtonRefreshParts.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonRefreshParts.text")); // NOI18N
        jButtonRefreshParts.setToolTipText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.jButtonRefreshParts.toolTipText")); // NOI18N
        jButtonRefreshParts.setName("jButtonRefreshParts"); // NOI18N
        jButtonRefreshParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshPartsActionPerformed(evt);
            }
        });

        partsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Parts..." }));
        partsComboBox.setName("partsComboBox"); // NOI18N
        partsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsComboBoxActionPerformed(evt);
            }
        });

        thumbnailJSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        thumbnailJSP.setName("thumbnailJSP"); // NOI18N

        mainJTP.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        mainJTP.setMinimumSize(new java.awt.Dimension(200, 200));
        mainJTP.setName("mainJTP"); // NOI18N
        mainJTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainJTPMouseClicked(evt);
            }
        });
        mainJTP.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mainJTPStateChanged(evt);
            }
        });

        bottomJSP.setName("bottomJSP"); // NOI18N

        bottomTextPane.setEditable(false);
        bottomTextPane.setFocusCycleRoot(false);
        bottomTextPane.setFocusable(false);
        bottomTextPane.setName("bottomTextPane"); // NOI18N
        bottomTextPane.setRequestFocusEnabled(false);
        bottomJSP.setViewportView(bottomTextPane);

        mainMenuBar.setName("mainMenuBar"); // NOI18N

        fileMenu.setMnemonic('f');
        fileMenu.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        fileMenuImport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuImport.setMnemonic('o');
        fileMenuImport.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuImport.text")); // NOI18N
        fileMenuImport.setName("fileMenuImport"); // NOI18N
        fileMenuImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuImportActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuImport);

        fileMenuExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuExport.setMnemonic('s');
        fileMenuExport.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuExport.text")); // NOI18N
        fileMenuExport.setName("fileMenuExport"); // NOI18N
        fileMenuExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuExportActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuExport);

        fileMenuSeparator1.setName("fileMenuSeparator1"); // NOI18N
        fileMenu.add(fileMenuSeparator1);

        fileMenuImportNotepad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileMenuImportNotepad.setMnemonic('i');
        fileMenuImportNotepad.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuImportNotepad.text")); // NOI18N
        fileMenuImportNotepad.setName("fileMenuImportNotepad"); // NOI18N
        fileMenuImportNotepad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuImportNotepadActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuImportNotepad);

        fileMenuSendNotepad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileMenuSendNotepad.setMnemonic('e');
        fileMenuSendNotepad.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuSendNotepad.text")); // NOI18N
        fileMenuSendNotepad.setName("fileMenuSendNotepad"); // NOI18N
        fileMenuSendNotepad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuSendNotepadActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuSendNotepad);

        fileMenuSeparator2.setName("fileMenuSeparator2"); // NOI18N
        fileMenu.add(fileMenuSeparator2);

        fileMenuSendPicture.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileMenuSendPicture.setMnemonic('p');
        fileMenuSendPicture.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuSendPicture.text")); // NOI18N
        fileMenuSendPicture.setName("fileMenuSendPicture"); // NOI18N
        fileMenuSendPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuSendPictureActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuSendPicture);

        fileMenuSeparator3.setName("fileMenuSeparator3"); // NOI18N
        fileMenu.add(fileMenuSeparator3);

        fileMenuNewDevice.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuNewDevice.setMnemonic('c');
        fileMenuNewDevice.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuNewDevice.text")); // NOI18N
        fileMenuNewDevice.setActionCommand(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuNewDevice.actionCommand")); // NOI18N
        fileMenuNewDevice.setName("fileMenuNewDevice"); // NOI18N
        fileMenuNewDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuNewDeviceActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuNewDevice);

        fileMenuCloseDevice.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuCloseDevice.setMnemonic('d');
        fileMenuCloseDevice.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuCloseDevice.text")); // NOI18N
        fileMenuCloseDevice.setEnabled(false);
        fileMenuCloseDevice.setName("fileMenuCloseDevice"); // NOI18N
        fileMenuCloseDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuCloseDeviceActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuCloseDevice);

        fileMenuSeparator4.setName("fileMenuSeparator4"); // NOI18N
        fileMenu.add(fileMenuSeparator4);

        fileMenuShowAllDevices.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileMenuShowAllDevices.setMnemonic('a');
        fileMenuShowAllDevices.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuShowAllDevices.text")); // NOI18N
        fileMenuShowAllDevices.setName("fileMenuShowAllDevices"); // NOI18N
        fileMenuShowAllDevices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuShowAllDevicesActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuShowAllDevices);

        fileMenuClearWorkspace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileMenuClearWorkspace.setMnemonic('w');
        fileMenuClearWorkspace.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuClearWorkspace.text")); // NOI18N
        fileMenuClearWorkspace.setName("fileMenuClearWorkspace"); // NOI18N
        fileMenuClearWorkspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuClearWorkspaceActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuClearWorkspace);

        fileMenuSeparator5.setName("fileMenuSeparator5"); // NOI18N
        fileMenu.add(fileMenuSeparator5);

        fileMenuExit.setMnemonic('x');
        fileMenuExit.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.fileMenuExit.text")); // NOI18N
        fileMenuExit.setName("fileMenuExit"); // NOI18N
        fileMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuExitActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuExit);

        mainMenuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N
        editMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                editMenuItemStateChanged(evt);
            }
        });

        editMenuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        editMenuCut.setMnemonic('u');
        editMenuCut.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenuCut.text")); // NOI18N
        editMenuCut.setName("editMenuCut"); // NOI18N
        editMenuCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuCutActionPerformed(evt);
            }
        });
        editMenu.add(editMenuCut);

        editMenuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        editMenuCopy.setMnemonic('c');
        editMenuCopy.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenuCopy.text")); // NOI18N
        editMenuCopy.setName("editMenuCopy"); // NOI18N
        editMenuCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuCopyActionPerformed(evt);
            }
        });
        editMenu.add(editMenuCopy);

        editMenuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        editMenuPaste.setMnemonic('p');
        editMenuPaste.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenuPaste.text")); // NOI18N
        editMenuPaste.setName("editMenuPaste"); // NOI18N
        editMenuPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuPasteActionPerformed(evt);
            }
        });
        editMenu.add(editMenuPaste);

        editMenuDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        editMenuDelete.setMnemonic('d');
        editMenuDelete.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenuDelete.text")); // NOI18N
        editMenuDelete.setName("editMenuDelete"); // NOI18N
        editMenuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuDeleteActionPerformed(evt);
            }
        });
        editMenu.add(editMenuDelete);

        editMenuSeparator1.setName("editMenuSeparator1"); // NOI18N
        editMenu.add(editMenuSeparator1);

        editMenuProperties.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        editMenuProperties.setMnemonic('t');
        editMenuProperties.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenuProperties.text")); // NOI18N
        editMenuProperties.setName("editMenuProperties"); // NOI18N
        editMenuProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuPropertiesActionPerformed(evt);
            }
        });
        editMenu.add(editMenuProperties);

        editMenuSeparator2.setName("editMenuSeparator2"); // NOI18N
        editMenu.add(editMenuSeparator2);

        editMenuRenameDevice.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        editMenuRenameDevice.setMnemonic('r');
        editMenuRenameDevice.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.editMenuRenameDevice.text")); // NOI18N
        editMenuRenameDevice.setName("editMenuRenameDevice"); // NOI18N
        editMenuRenameDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuRenameDeviceActionPerformed(evt);
            }
        });
        editMenu.add(editMenuRenameDevice);

        mainMenuBar.add(editMenu);

        settingsMenu.setMnemonic('s');
        settingsMenu.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.settingsMenu.text")); // NOI18N
        settingsMenu.setName("settingsMenu"); // NOI18N

        settingsMenuPreferences.setMnemonic('p');
        settingsMenuPreferences.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.settingsMenuPreferences.text")); // NOI18N
        settingsMenuPreferences.setName("settingsMenuPreferences"); // NOI18N
        settingsMenuPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuPreferencesActionPerformed(evt);
            }
        });
        settingsMenu.add(settingsMenuPreferences);

        mainMenuBar.add(settingsMenu);

        devicesMenu.setMnemonic('d');
        devicesMenu.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.devicesMenu.text")); // NOI18N
        devicesMenu.setName("devicesMenu"); // NOI18N

        devicesMenuManageTabs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        devicesMenuManageTabs.setMnemonic('m');
        devicesMenuManageTabs.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.devicesMenuManageTabs.text")); // NOI18N
        devicesMenuManageTabs.setName("devicesMenuManageTabs"); // NOI18N
        devicesMenuManageTabs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devicesMenuManageTabsActionPerformed(evt);
            }
        });
        devicesMenu.add(devicesMenuManageTabs);

        devicesMenuSeparator1.setName("devicesMenuSeparator1"); // NOI18N
        devicesMenu.add(devicesMenuSeparator1);

        mainMenuBar.add(devicesMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        helpMenuAbout.setMnemonic('a');
        helpMenuAbout.setText(org.openide.util.NbBundle.getMessage(WorkspaceFrame.class, "WorkspaceFrame.helpMenuAbout.text")); // NOI18N
        helpMenuAbout.setName("helpMenuAbout"); // NOI18N
        helpMenuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuAboutActionPerformed(evt);
            }
        });
        helpMenu.add(helpMenuAbout);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonImport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExport)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonImportNotepad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSendNotepad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(jButtonRefreshParts))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(thumbnailJSP, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(partsComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mainJTP, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
                            .addComponent(bottomJSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonImport)
                    .addComponent(jButtonExport)
                    .addComponent(jButtonSendNotepad)
                    .addComponent(jButtonImportNotepad)
                    .addComponent(jButtonRefreshParts))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mainJTP, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bottomJSP, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(partsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thumbnailJSP, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainJTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainJTPMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            newTab();
        } else if (evt.getButton() == MouseEvent.BUTTON2 && evt.getClickCount() == 1) {
            closeTab();
        } else if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1) {
            mainJTPContextMenu.show(mainJTP, evt.getX(), evt.getY());
        }
}//GEN-LAST:event_mainJTPMouseClicked

    private void mainJTPStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mainJTPStateChanged
        displayProperties();
}//GEN-LAST:event_mainJTPStateChanged

    private void fileMenuCloseDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuCloseDeviceActionPerformed
        closeTab();
}//GEN-LAST:event_fileMenuCloseDeviceActionPerformed

    private void jButtonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportActionPerformed
        importEugene();
    }//GEN-LAST:event_jButtonImportActionPerformed

    private void jButtonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportActionPerformed
        exportEugene();
    }//GEN-LAST:event_jButtonExportActionPerformed

    private void fileMenuNewDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuNewDeviceActionPerformed
        newTab();
}//GEN-LAST:event_fileMenuNewDeviceActionPerformed

    private void editMenuPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuPropertiesActionPerformed
        if (_activeWorkspaceScene.hasFocusedWidget()) {
            Widget w = _activeWorkspaceScene.getFocusedWidget();
            ((DevicePartWrapper)_activeWorkspaceScene.findObject(w)).openPropertiesDialog(_propertiesDialog, _activeWorkspaceScene);
        }
}//GEN-LAST:event_editMenuPropertiesActionPerformed

    private void fileMenuImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuImportActionPerformed
        importEugene();
    }//GEN-LAST:event_fileMenuImportActionPerformed

    private void fileMenuExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuExportActionPerformed
        exportEugene();
    }//GEN-LAST:event_fileMenuExportActionPerformed

    private void fileMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuExitActionPerformed
        exit();
    }//GEN-LAST:event_fileMenuExitActionPerformed

    private void jButtonImportNotepadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportNotepadActionPerformed
        importNotepad();
}//GEN-LAST:event_jButtonImportNotepadActionPerformed

    private void jButtonSendNotepadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendNotepadActionPerformed
        exportNotepad();
}//GEN-LAST:event_jButtonSendNotepadActionPerformed

    private void partsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsComboBoxActionPerformed
        if (partsComboBox.getSelectedIndex() != 0 && _activeWorkspaceScene != null) {
            String name = (String) partsComboBox.getSelectedItem();
            DevicePartWrapper tnWrapper = _activeThumbnailScene.generateWrapper(name);
            if (tnWrapper.isPart()) {
                _manager.addPart(tnWrapper.getPart());
            } else if (hasDeviceCycle(tnWrapper)) {
                return;
            }
            _activeWorkspaceScene.insertNode(tnWrapper);
            _activeWorkspaceScene.repaint();
            showPropertiesDialog(tnWrapper);
        }
}//GEN-LAST:event_partsComboBoxActionPerformed

    private void mainJTPContextMenuNewDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainJTPContextMenuNewDeviceActionPerformed
        newTab();
}//GEN-LAST:event_mainJTPContextMenuNewDeviceActionPerformed

    private void mainJTPContextMenuCloseDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainJTPContextMenuCloseDeviceActionPerformed
        closeTab();
}//GEN-LAST:event_mainJTPContextMenuCloseDeviceActionPerformed

    private void editMenuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuDeleteActionPerformed
        if (_activeWorkspaceScene != null) {
            _activeWorkspaceScene.deleteFocusedWidget();
        }
    }//GEN-LAST:event_editMenuDeleteActionPerformed

    private void editMenuRenameDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuRenameDeviceActionPerformed
        if (_activeWorkspaceScene != null) {
            renameDevice();
        }
    }//GEN-LAST:event_editMenuRenameDeviceActionPerformed

    private void mainJTPContextMenuRenameDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainJTPContextMenuRenameDeviceActionPerformed
        if (_activeWorkspaceScene != null) {
            renameDevice();
        }
}//GEN-LAST:event_mainJTPContextMenuRenameDeviceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exit();
    }//GEN-LAST:event_formWindowClosing

    private void fileMenuShowAllDevicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuShowAllDevicesActionPerformed
        HashMap<String, DevicePartWrapper> devices = _manager.getAllWrappedDevices();
        for (String deviceName : devices.keySet()) {
            if (mainJTP.indexOfTab(deviceName) == -1) {
                openTab(devices.get(deviceName));
            }
        }
        mainJTP.validate();
    }//GEN-LAST:event_fileMenuShowAllDevicesActionPerformed

    private void fileMenuClearWorkspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuClearWorkspaceActionPerformed
        if (isModified()) {
            int option = promptSave();
            if (option == JOptionPane.YES_OPTION) {
                exportEugene();
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        clearWorkspace();
        newTab();
        setModified(false);
    }//GEN-LAST:event_fileMenuClearWorkspaceActionPerformed

    private void editMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_editMenuItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (_activeWorkspaceScene == null) {
                editMenuCut.setEnabled(false);
                editMenuCopy.setEnabled(false);
                editMenuPaste.setEnabled(false);
            } else {
                if (_activeWorkspaceScene.hasFocusedWidget()) {
                    editMenuCut.setEnabled(true);
                    editMenuCopy.setEnabled(true);
                } else {
                    editMenuCut.setEnabled(false);
                    editMenuCopy.setEnabled(false);
                }
                if (workspaceCanPaste()) {
                    editMenuPaste.setEnabled(true);
                } else {
                    editMenuPaste.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_editMenuItemStateChanged

    private void editMenuCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuCutActionPerformed
        workspaceCut();
    }//GEN-LAST:event_editMenuCutActionPerformed

    private void editMenuCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuCopyActionPerformed
        workspaceCopy();
    }//GEN-LAST:event_editMenuCopyActionPerformed

    private void editMenuPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuPasteActionPerformed
        workspacePaste();
    }//GEN-LAST:event_editMenuPasteActionPerformed

    private void fileMenuSendPictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuSendPictureActionPerformed
        sendPicture();
    }//GEN-LAST:event_fileMenuSendPictureActionPerformed

    private void settingsMenuPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuPreferencesActionPerformed
        _preferencesDialog.setVisible(true);
    }//GEN-LAST:event_settingsMenuPreferencesActionPerformed

    private void helpMenuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuAboutActionPerformed
        _aboutDialog.setLocationRelativeTo(this);
        _aboutDialog.setVisible(true);
    }//GEN-LAST:event_helpMenuAboutActionPerformed

    private void jButtonRefreshPartsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshPartsActionPerformed
        refreshParts();
    }//GEN-LAST:event_jButtonRefreshPartsActionPerformed

    private void fileMenuSendNotepadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuSendNotepadActionPerformed
        exportNotepad();
    }//GEN-LAST:event_fileMenuSendNotepadActionPerformed

    private void fileMenuImportNotepadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuImportNotepadActionPerformed
        importNotepad();
    }//GEN-LAST:event_fileMenuImportNotepadActionPerformed

    private void devicesMenuManageTabsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devicesMenuManageTabsActionPerformed
        Object[] devices = _manager.getAllWrappedDevices().keySet().toArray();
        java.util.Arrays.sort(devices);
        int[] deviceIndices = new int[mainJTP.getTabCount()];
        int tabIndex = 0;
        for (int i = 0; i < mainJTP.getTabCount(); i++) {
            deviceIndices[tabIndex] = java.util.Arrays.binarySearch(devices, mainJTP.getTitleAt(i));
            tabIndex++;
        }
        _deviceDialog.chooseDevices(devices, deviceIndices);
    }//GEN-LAST:event_devicesMenuManageTabsActionPerformed

    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        System.setProperty("sun.awt.noerasebackground", "true");
        try {
            javax.swing.UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorkspaceFrame().setVisible(true);
            }
        });
    }

    public NotepadWindow _notepad;
    private PropertiesDialog _propertiesDialog;
    private PreferencesDialog _preferencesDialog;
    private AboutDialog _aboutDialog;
    private DeviceDialog _deviceDialog;

    private Preferences _preferences;
    
    private Manager _manager;
    private String _currentFilePath;

    private int _newDeviceCount;
    private boolean _isModified;
    private DevicePartWrapper _clippedWrapper;
    
    private WorkspaceScene _activeWorkspaceScene;
    private ArrayList<WorkspaceScene> _wsSceneList;
    private HashMap<DevicePartWrapper, JMenuItem> _deviceMenuItemMap;
    private ThumbnailScene _activeThumbnailScene;


    private static JFileChooser _pngFileChooser;
    {
        _pngFileChooser = new JFileChooser();
        _pngFileChooser.setAcceptAllFileFilterUsed(false);
        _pngFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) { // Handles which files are allowed by filter.
                // Since this is used during enumeration of existing file system,
                // this should not be necessary, but good practice to test for null.
                if (f != null) {
                    if (f.isDirectory()) { // Allow directories to be seen.
                        return true;
                    }
                    // Get file extension and test if should be allowed.
                    String extension = getExtension(f);
                    if (extension != null) {
                        return ((extension.equalsIgnoreCase("png")) ? true : false);
                    }
                }
                return false;
            }

            @Override
            public String getDescription() { // 'Files of Type' description
                return "*.png";
            }

            // Takes a java.io.File object, parses file extension, and returns as java.lang.String.
            String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');
                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                return ext;
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane bottomJSP;
    private javax.swing.JTextPane bottomTextPane;
    private javax.swing.JMenu devicesMenu;
    private javax.swing.JMenuItem devicesMenuManageTabs;
    private javax.swing.JSeparator devicesMenuSeparator1;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuCopy;
    private javax.swing.JMenuItem editMenuCut;
    private javax.swing.JMenuItem editMenuDelete;
    private javax.swing.JMenuItem editMenuPaste;
    private javax.swing.JMenuItem editMenuProperties;
    private javax.swing.JMenuItem editMenuRenameDevice;
    private javax.swing.JSeparator editMenuSeparator1;
    private javax.swing.JSeparator editMenuSeparator2;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMenuClearWorkspace;
    private javax.swing.JMenuItem fileMenuCloseDevice;
    private javax.swing.JMenuItem fileMenuExit;
    private javax.swing.JMenuItem fileMenuExport;
    private javax.swing.JMenuItem fileMenuImport;
    private javax.swing.JMenuItem fileMenuImportNotepad;
    private javax.swing.JMenuItem fileMenuNewDevice;
    private javax.swing.JMenuItem fileMenuSendNotepad;
    private javax.swing.JMenuItem fileMenuSendPicture;
    private javax.swing.JSeparator fileMenuSeparator1;
    private javax.swing.JSeparator fileMenuSeparator2;
    private javax.swing.JSeparator fileMenuSeparator3;
    private javax.swing.JSeparator fileMenuSeparator4;
    private javax.swing.JSeparator fileMenuSeparator5;
    private javax.swing.JMenuItem fileMenuShowAllDevices;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuAbout;
    private javax.swing.JButton jButtonExport;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JButton jButtonImportNotepad;
    private javax.swing.JButton jButtonRefreshParts;
    private javax.swing.JButton jButtonSendNotepad;
    private javax.swing.JTabbedPane mainJTP;
    private javax.swing.JPopupMenu mainJTPContextMenu;
    private javax.swing.JMenuItem mainJTPContextMenuCloseDevice;
    private javax.swing.JMenuItem mainJTPContextMenuNewDevice;
    private javax.swing.JMenuItem mainJTPContextMenuRenameDevice;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JComboBox partsComboBox;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem settingsMenuPreferences;
    private javax.swing.JScrollPane thumbnailJSP;
    // End of variables declaration//GEN-END:variables

}
