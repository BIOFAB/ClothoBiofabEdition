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
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileFilter;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.MoveStrategy;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.WidgetKeyEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.ImageUtilities;
import org.clothocad.tool.spectacles.Spectacles;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;
import org.clothocad.tool.spectacles.ui.frames.PreferencesDialog;
import org.clothocad.tool.spectacles.ui.frames.PropertiesDialog;
import org.clothocad.tool.spectacles.ui.frames.WorkspaceFrame;

/**
 * SpectaclesFactory contains static convenience methods for setting up
 * widgets used by WorkspaceScene and ThumbnailScene. Any miscellaneous
 * fields used by classes in the ui package are also found here.
 * @author Rich
 */
public class SpectaclesFactory {

    /** A 1-pixel black line border for widgets. */
    public static final Border lineBorderBlack = BorderFactory.createLineBorder(1, Color.BLACK);
    /** A 1-pixel blue line border for widgets. */
    public static final Border lineBorderBlue = BorderFactory.createLineBorder(1, Color.BLUE);
    /** A 1-pixel cyan line border for widgets. */
    public static final Border lineBorderCyan = BorderFactory.createLineBorder(1, Color.CYAN);
    /** A 1-pixel gray line border for widgets. */
    public static final Border lineBorderGray = BorderFactory.createLineBorder(1, Color.GRAY);
    /** A 1-pixel green line border for widgets. */
    public static final Border lineBorderGreen = BorderFactory.createLineBorder(1, Color.GREEN);
    /** A 1-pixel orange line border for widgets. */
    public static final Border lineBorderOrange = BorderFactory.createLineBorder(1, Color.ORANGE);
    /** A 1-pixel red line border for widgets. */
    public static final Border lineBorderRed = BorderFactory.createLineBorder(1, Color.RED);
    /** A 1-pixel yellow line border for widgets. */
    public static final Border lineBorderYellow = BorderFactory.createLineBorder(1, Color.YELLOW);

    /** A plain mono-spaced 8 point font. */
    public static final Font labelFontPlain = new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 8);
    /** An italic mono-spaced 8 point font. */
    public static final Font labelFontItalic = new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.ITALIC, 8);
    /** A bold mono-spaced 8 point font. */
    public static final Font labelFontBold = new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 8);

    /** A regex pattern that matches against non-word characters. */
    public static final Pattern patternNonWord = Pattern.compile(".*?\\W");
    /** A regex pattern that matches against digit characters. */
    public static final Pattern patternDigit = Pattern.compile("\\d");
    /** A regex pattern that matches against part image filenames. */
    // 1 matches entire part name
    // 2 matches the part type
    // 3 matches the orientation, if any
    // 4 matches the part subtype
    // IMPORTANT: Remember to check for conflicts with SpectaclesFactory.resolveImage(DevicePartWrapper)
    // when this regex is modified, or image resolving will be broken!
    public static final Pattern patternPartImageFilename =
            Pattern.compile("(?:VBOL|SBOLv|SBOLV|Spectacles)\\.(([A-Za-z_]\\w*?)(\\+|-)?(?:_(\\w+))?)\\.(?:png|PNG)");

    /**
     * Creates a WidgetAction that handles miscellaneous mouse events.
     * @return a GridMouseHandler.
     */
    public static WidgetAction createGridMouseHandler() {
        return new GridMouseHandler();
    }

    /**
     * Creates a MoveProvider that is used with GridMoveStrategy in a WorkspaceScene.
     * This helps handle widget insertion in a WorkspaceScene.
     * @return a GridMoveProvider.
     */
    public static MoveProvider createGridMoveProvider() {
        return new GridMoveProvider();
    }

    /**
     * Creates a MoveStrategy used in a WorkspaceScene. The seleced widget follows the
     * mouse unless it is within a 'snap zone' of the grid defined in WorkspaceScene.
     * @return a GridMoveStrategy.
     */
    public static MoveStrategy createGridMoveStrategy() {
        return new GridMoveStrategy();
    }

    /**
     * Creates a SelectProvider used in a WorkspaceScene. This determines the
     * behavior when a widget with this SelectProvider is clicked by the user.
     * @return a GridSelectProvider.
     */
    public static SelectProvider createGridSelectProvider() {
        return new GridSelectProvider();
    }

    /**
     * Given a PropertiesDialog, creates a PopupMenuProvider used in a WorkspaceScene.
     * This pops up a menu when a widget with this PopupMenuProvider is right-clicked by the user.
     * @param propertiesDialog the PropertiesDialog that is shown when the 'Properties'
     * option is selected in the context menu.
     * @return a GridWidgetContextMenuProvider.
     */
    public static PopupMenuProvider createGridWidgetContextMenuProvider(PropertiesDialog propertiesDialog) {
        return new GridWidgetContextMenuProvider(propertiesDialog);
    }

    /**
     * Generates an IconNodeWidget for a scene from a label string and an image.
     * The label set to a monospaced 8pt font with a maximum length of 10 characters.
     * The IconNodeWidget is also given a 1-pixel black line border.
     * @param scene the scene that will contain the IconNodeWidget.
     * @param label the label for the IconNodeWidget.
     * @param image the image for the IconNodeWidget.
     * @return an IconNodeWidget with the given label text and image.
     */
    public static IconNodeWidget createIconNodeWidget(Scene scene, String label, Image image) {
        IconNodeWidget widget = new IconNodeWidget(scene);
        widget.setImage(image);
        widget.getLabelWidget().setFont(labelFontPlain);
        widget.setLabel(label.substring(0, Math.min(10, label.length())));
        widget.setBorder (SpectaclesFactory.lineBorderBlack);
        return widget;
    }

    /**
     * Creates a WidgetAction to handle drag-n-drop between a ThumbnailScene and
     * a WorkspaceScene. This generates a ghost widget with a gray border that
     * follows the mouse in the target WorkspaceScene. The drop action may be
     * cancelled by right-clicking before releasing the left mouse button.
     * @return a ThumbnailMouseDropHandler.
     */
    public static WidgetAction createThumbnailMouseDropHandler() {
        return new ThumbnailMouseDropHandler();
    }

    /**
     * Given a Eugene device, attempts to resolve the image specified in the
     * device's image path field.
     * @param device a Eugene device.
     * @return the specified image if the path is valid,
     * and a default device image otherwise.
     * image otherwise.
     */
    public static Image resolveImage(Device device) {
        return resolveImage(new DevicePartWrapper(device, null));
    }

    /**
     * Given a Eugene part, attempts to resolve the image specified in the
     * part's image path field. If the path is not valid, a substitute
     * part image will be returned based on the part's type and orientation.
     * If a part image cannot be matched to the part, then a 'No Image'
     * placeholder will be returned.
     * @param part a Eugene part.
     * @return the specified image if the path is valid,
     * and a default part image otherwise.
     */
    public static Image resolveImage(Part part) {
        return resolveImage(new DevicePartWrapper(part));
    }

    /**
     * Given a DeviceComponentWrapper containing a Eugene part or device,
     * attempts to resolve the image specified in the image path field.
     * @param wrapper a DeviceComponentWrapper.
     * @return the specified image if the path is valid,
     * and a default image otherwise.
     */
    public static Image resolveImage(DevicePartWrapper wrapper) {
        String prefixVBOL = Spectacles.getImageLocation() + "VBOL.";
        String prefixSBOLv = Spectacles.getImageLocation() + "SBOLv.";
        String prefixSBOLV = Spectacles.getImageLocation() + "SBOLV.";
        String prefixSpectacles = Spectacles.getImageLocation() + "Spectacles.";
        String[] prefixArray = new String[] {prefixVBOL, prefixSBOLv, prefixSBOLV, prefixSpectacles};

        String suffixLC = ".png";
        String suffixUC = ".PNG";
        String[] suffixArray = new String[] {suffixLC, suffixUC};

        String placeholder = PreferencesDialog.defaultImageLocation + "VBOL.Placeholder" + suffixLC;
        String customPath = wrapper.getImagePath();
        Image image = loadImage(customPath);
        
        if (image == null)
        { // Fix the image path
            if (wrapper.isDevice())
            { // Device image handling
                for (String prefix : prefixArray)
                {
                    for (String suffix : suffixArray)
                    {
                        image = loadImage(prefix + "Device" + suffix);
                        if (image != null) {
                            break;
                        }
                    }
                    if (image != null) {
                        break;
                    }
                }
                if (image == null) { // custom path not working?
                    image = loadImage(PreferencesDialog.defaultImageLocation + "VBOL.Device" + suffixLC);
                }
            } else
            { // Part image handling
                if (!wrapper.getPartPropertyValues().containsKey("Orientation")) {
                    image = loadImage(placeholder);
                } else
                {
                    ArrayList<String> pathList = new ArrayList<String>();
                    String type = wrapper.getType();
                    String subtype = "";
                    String orientation = (String) wrapper.getPartPropertyValues().get("Orientation").getValue();
                    if (orientation.equals("Forward")) {
                        type += "+";
                    } else if (orientation.equals("Reverse")) {
                        type += "-";
                    }
                    if (wrapper.getPartPropertyValues().containsKey("Subtype"))
                    {
                        subtype = "_" + (String) wrapper.getPartPropertyValues().get("Subtype").getValue();
                        if (!subtype.equals("_")) {
                            for (String prefix : prefixArray) {
                                for (String suffix : suffixArray) {
                                    pathList.add(prefix + type + subtype + suffix);
                                }
                            }
                        }
                    }
                    for (String prefix : prefixArray) {
                        for (String suffix : suffixArray) {
                            pathList.add(prefix + type + suffix);
                        }
                    }
                    for (String path : pathList) {
                        image = loadImage(path);
                        if (image != null) {
                            break;
                        }
                    }
                }
                if (image == null) { // custom path not working
                    image = loadImage(placeholder);
                }
            }
        }
        return image;
    }

    /**
     * Given a widget in a WorkspaceScene, retrieves the WorkspaceScene.
     * @param widget a widget in a WorkspaceScene.
     * @return the WorkspaceScene containing the widget.
     */
    private static WorkspaceScene getWorkspaceScene(Widget widget) {
        return (WorkspaceScene) widget.getParentWidget().getParentWidget();
    }

    /**
     * If Spectacles is running in standalone mode, loadImage
     * will interpret the resource string as a relative path
     * from its working directory. If Spectacles is running
     * from Clotho, loadImage will interpret the resource
     * string as a relative path from Clotho's working directory.
     * @param path
     * @return
     */
    private static Image loadImage(String path) {
        Image image = null;
        if (Spectacles.isRunningSolo()) {
            image = ImageUtilities.loadImage(path);
        }
        if (image == null) {
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException ex) {
                return null;
            }
        }
        return image;
    }

    /**
     * A WidgetAction that handles miscellaneous mouse events.
     * In particular, this class currently handles double-click events.
     */
    private static class GridMouseHandler extends WidgetAction.Adapter {
        @Override
        public State mouseClicked(Widget widget, WidgetMouseEvent event) {
            if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
                // check for device and then open the tab
                WorkspaceScene wsScene = getWorkspaceScene(widget);
                DevicePartWrapper dpw = (DevicePartWrapper) wsScene.findObject(widget);
                if (dpw.isDevice()) {
                    WorkspaceFrame wsFrame = wsScene.getWorkspaceFrame();
                    wsFrame.openTab(dpw);
                }
                return State.CONSUMED;
            }
            return State.REJECTED;
        }
    }

    /**
     * A MoveProvider that is used with a GridMoveStrategy in a WorkspaceScene.
     * This handles widget placement after the widget is released.
     */
    private static class GridMoveProvider implements MoveProvider {

        @Override
        public void movementStarted(Widget widget) {
            widget.bringToFront();
            originalLocation = widget.getPreferredLocation();
        }

        @Override
        public void movementFinished(Widget widget) {
            WorkspaceScene wsScene = getWorkspaceScene(widget);
            List<Widget> widgets = wsScene.getWidgetList();
            int size = widgets.size();
            if (wsScene.isSnappable(widget.getPreferredLocation())) {
                widgets.remove(widget);
                int index = wsScene.convertPoint2Index(widget.getPreferredLocation());
                if (index > size - 1 || index < 0) {
                    // Column coordinate offscreen
                    // or index is out of bounds; just move to end of list
                    widgets.add(widget);
                } else if (index == size - 1) {
                    // Insert behind last element (don't forget, this widget was just removed from the list)
                    widgets.add(size - 1, widget);
                } else {
                    widgets.add(index, widget);
                }
            }
            wsScene.arrangeWidgets();
            if (!originalLocation.equals(widget.getPreferredLocation())) {
                wsScene.getWorkspaceFrame().setModified(true);
                // TODO: add active rule checking block/method here
            }
        }

        @Override
        public Point getOriginalLocation(Widget widget) {
            return widget.getPreferredLocation();
        }

        @Override
        public void setNewLocation(Widget widget, Point location) {
            widget.setPreferredLocation(location);
        }

        private Point originalLocation;
    }

    /**
     * A MoveStrategy used in a WorkspaceScene. The selected widget follows
     * the mouse until it is inside a 'snap zone' specified in the widget's
     * WorkspaceScene, at which point the widget will 'snap' to the zone's
     * grid point until the mouse moves outside the 'snap zone'.
     */
    private static class GridMoveStrategy implements MoveStrategy {

        @Override
        public Point locationSuggested(Widget widget, Point originalLocation, Point suggestedLocation) {
            if (getWorkspaceScene(widget).isSnappable(suggestedLocation)) {
                return new Point(suggestedLocation.x + 3 - suggestedLocation.x % WorkspaceScene.gridBoxWidth,
                        suggestedLocation.y + 3 - suggestedLocation.y % WorkspaceScene.gridBoxHeight);
            } else {
                return suggestedLocation;
            }
        }
    }

    /**
     * A SelectProvider used in a WorkspaceScene. Sets the widget as the focused
     * widget for the widget's WorkspaceScene and updates the corresponding
     * WorkspaceFrame's properties text box.
     */
    private static class GridSelectProvider implements SelectProvider {

        @Override
        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        @Override
        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        @Override
        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            WorkspaceScene wsScene = getWorkspaceScene(widget);
            wsScene.setFocusedWidget(widget);
            wsScene.getWorkspaceFrame().displayProperties();
        }
    }

    /**
     * A PopupMenuProvider used in a WorkspaceScene. Pops up a right-click menu
     * for widgets in a WorkspaceScene.
     */
    private static class GridWidgetContextMenuProvider implements PopupMenuProvider {

        public GridWidgetContextMenuProvider(PropertiesDialog propertiesDialog) {
            GridWidgetContextMenuProvider._propertiesDialog = propertiesDialog;
        }

        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            widget.bringToFront();
            _childWidget = widget;
            return _jpm;
        }
        
        private static PropertiesDialog _propertiesDialog;
        private static Widget _childWidget;
        private static JPopupMenu _jpm;
        {
            final JMenuItem cut = new JMenuItem();
            cut.setText("Cut");
            cut.setName("cut");
            cut.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    _propertiesDialog.getWorkspaceFrame().workspaceCut();
                }
            });

            final JMenuItem copy = new JMenuItem();
            copy.setText("Copy");
            copy.setName("copy");
            copy.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    _propertiesDialog.getWorkspaceFrame().workspaceCopy();
                }
            });
            
            final JMenuItem paste = new JMenuItem();
            paste.setText("Paste");
            paste.setName("paste");
            paste.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    _propertiesDialog.getWorkspaceFrame().workspacePaste();
                }
            });

            JMenuItem delete = new JMenuItem();
            delete.setText("Delete");
            delete.setName("delete");
            delete.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getWorkspaceScene(_childWidget).deleteFocusedWidget();
                    _childWidget = null;
                }
            });

            JMenuItem properties = new JMenuItem();
            properties.setText("Properties");
            properties.setName("properties");
            properties.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    WorkspaceScene wsScene = getWorkspaceScene(_childWidget);
                    DevicePartWrapper dpw = (DevicePartWrapper) wsScene.findObject(_childWidget);
                    dpw.openPropertiesDialog(_propertiesDialog, wsScene);
                }
            });

            final JMenuItem changeImage = new JMenuItem();
            changeImage.setText("Change Image");
            changeImage.setName("changeImage");
            changeImage.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    WorkspaceScene wsScene = getWorkspaceScene(_childWidget);
                    DevicePartWrapper dpw = (DevicePartWrapper) wsScene.findObject(_childWidget);
                    if (_pngChooser.showOpenDialog(wsScene.getWorkspaceFrame()) == JFileChooser.APPROVE_OPTION) {
                        File f = _pngChooser.getSelectedFile();
                        dpw.setImagePath(f.getAbsolutePath());
                        wsScene.hideContents();
                        wsScene.showContents();
                    }
                }
            });

            final JMenuItem resetImage = new JMenuItem();
            resetImage.setText("Reset Image");
            resetImage.setName("resetImage");
            resetImage.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    WorkspaceScene wsScene = getWorkspaceScene(_childWidget);
                    DevicePartWrapper dpw = (DevicePartWrapper) wsScene.findObject(_childWidget);
                    int reset = JOptionPane.showConfirmDialog(
                            wsScene.getWorkspaceFrame(), "Reset part image?",
                            "Confirm part image reset", JOptionPane.YES_NO_OPTION);
                    if (reset == JOptionPane.OK_OPTION) {
                        dpw.setImagePath("");
                        wsScene.hideContents();
                        wsScene.showContents();
                    }
                }
            });
            
            _jpm = new JPopupMenu();
            _jpm.addPopupMenuListener(new PopupMenuListener() {
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    /*if (_propertiesDialog.getWorkspaceFrame().getActiveWorkspaceScene().hasFocusedWidget()) {
                        cut.setEnabled(true);
                        copy.setEnabled(true);
                    } else {
                        cut.setEnabled(false);
                        copy.setEnabled(false);
                    }
                    if (((DevicePartWrapper) _propertiesDialog.getWorkspaceFrame().getActiveWorkspaceScene().findObject(_childWidget)).isPart()) {
                        changeImage.setEnabled(true);
                        resetImage.setEnabled(true);
                    } else {
                        changeImage.setEnabled(false);
                        resetImage.setEnabled(false);
                    }
                    if (((DevicePartWrapper) _propertiesDialog.getWorkspaceFrame().getActiveWorkspaceScene().findObject(_childWidget)).isPart()) {
                        changeImage.setEnabled(true);
                        resetImage.setEnabled(true);
                    } else {
                        changeImage.setEnabled(false);
                        resetImage.setEnabled(false);
                    }*/
                    if (_propertiesDialog.getWorkspaceFrame().workspaceCanPaste()) {
                        paste.setEnabled(true);
                    } else {
                        paste.setEnabled(false);
                    }
                }
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                }
                public void popupMenuCanceled(PopupMenuEvent e) {
                }
            });
            _jpm.add(cut);
            _jpm.add(copy);
            _jpm.add(paste);
            _jpm.add(new JSeparator());
            _jpm.add(delete);
            _jpm.add(new JSeparator());
            _jpm.add(changeImage);
            _jpm.add(resetImage);
            _jpm.add(new JSeparator());
            _jpm.add(properties);
        }
        private static JFileChooser _pngChooser;
        {
            _pngChooser = new JFileChooser();
            _pngChooser.setAcceptAllFileFilterUsed(false);
            _pngChooser.setFileFilter(new FileFilter() {
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
    }

    /**
     * A WidgetAction that handles drag-n-drop between a ThumbnailScene and a
     * WorkspaceScene. Generates a ghost widget with a gray line border that
     * follows the mouse in the WorkspaceScene. The drop action may be cancelled
     * by right-clicking before the left mouse button is released.
     */
    private static class ThumbnailMouseDropHandler extends WidgetAction.LockedAdapter {

        @Override
        public State keyTyped(Widget widget, WidgetKeyEvent event) {
            // For some reason the focus system is semi-broken for Visual Library
            // components, so this bit of code probably never gets called since
            // the ThumbnailScene's keyboard listener doesn't get anything.
            if (widget == _lockedWidget && event.getKeyChar() == KeyEvent.VK_ESCAPE) {
                releaseWidget();
                return State.CONSUMED;
            } else {
                return State.REJECTED;
            }
        }

        @Override
        public State mousePressed(Widget widget, WidgetMouseEvent event) {
            if (isLocked()) {
                if (event.getButton() == MouseEvent.BUTTON3 && event.getClickCount() == 1) {
                    releaseWidget();
                    return State.CONSUMED;
                }
                return State.createLocked(widget, this);
            }
            if (event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 1) {
                _mouseOffset = event.getPoint();
                _lockedWidget = widget;
                _tnScene = (ThumbnailScene) widget.getParentWidget().getParentWidget();
                _wsScene = _tnScene.getTargetScene();
                if (_wsScene == null) {
                    return State.REJECTED;
                }
                // should create just one of each part in tnScene and grab from there
                _visibleWidget = new IconNodeWidget(_wsScene);
                _visibleWidget.getLabelWidget().setFont(labelFontPlain);
                _visibleWidget.setImage(((IconNodeWidget) _lockedWidget).getImageWidget().getImage());
                _visibleWidget.setLabel(((IconNodeWidget) _lockedWidget).getLabelWidget().getLabel());
                _visibleWidget.setBorder(lineBorderGray);
                _wsScene.addGhostWidget(_visibleWidget);
                _visibleWidget.setPreferredLocation(getWidgetSceneLocation());
                return State.createLocked(widget, this);
            }
            return State.REJECTED;
        }

        @Override
        public State mouseReleased(Widget widget, WidgetMouseEvent event) {
            if (widget == _lockedWidget) {
                if (event.getButton() != MouseEvent.BUTTON1) {
                    return State.createLocked(widget, this);
                }
                WorkspaceFrame wsFrame = _wsScene.getWorkspaceFrame();
                DevicePartWrapper tnWrapper = _tnScene.generateWrapper(widget);
                if (tnWrapper.isDevice() && wsFrame.hasDeviceCycle(tnWrapper)) {
                    releaseWidget();
                    return State.CONSUMED;
                }
                if (tnWrapper.isPart()) {
                    wsFrame.getManager().addPart(tnWrapper.getPart());
                }
                // grab mouse info and insert the widget to wsScene
                _wsScene.insertNode(
                        _wsScene.convertPoint2Index(getWidgetSceneLocation()),
                        tnWrapper).repaint();
                releaseWidget();
                wsFrame.showPropertiesDialog(tnWrapper);
                return State.CONSUMED;
            } else {
                return State.REJECTED;
            }
        }

        @Override
        public State mouseDragged(Widget widget, WidgetMouseEvent event) {
            if (widget == _lockedWidget) {
                Point wsLocation = getWidgetSceneLocation();
                if (_wsScene.isSnappable(wsLocation)) {
                    _visibleWidget.setPreferredLocation(
                            new Point(wsLocation.x + 3 - wsLocation.x % WorkspaceScene.gridBoxWidth,
                            wsLocation.y + 3 - wsLocation.y % WorkspaceScene.gridBoxHeight));
                } else {
                    _visibleWidget.setPreferredLocation(wsLocation);
                }
                _wsScene.validate();
                return State.createLocked(widget, this);
            } else {
                return State.REJECTED;
            }
        }

        @Override
        protected boolean isLocked() {
            return _lockedWidget != null;
        }

        /**
         * Cleans up and releases any resources used during the drag-n-drop operation.
         */
        private void releaseWidget() {
            _wsScene.removeGhostWidget(_visibleWidget);
            _mouseOffset = null;
            _lockedWidget = null;
            _visibleWidget = null;
            _tnScene = null;
            _wsScene = null;
        }

        /**
         * Returns the ghost widget's location in the WorkspaceScene.
         * @return a Point with the ghost widget's location in the WorkspaceScene,
         * and <code>null</code> if there is no designated WorkspaceScene.
         */
        private Point getWidgetSceneLocation() {
            if (_wsScene != null) {
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                Point wssLocation = _wsScene.getView().getLocationOnScreen();
                return new Point(mouseLocation.x - wssLocation.x - _mouseOffset.x,
                        mouseLocation.y - wssLocation.y - _mouseOffset.y);
            } else {
                return null;
            }
        }
        
        private Widget _lockedWidget = null; // a widget in ThumbnailScene
        private IconNodeWidget _visibleWidget = null; // a widget in WorkspaceScene
        private ThumbnailScene _tnScene = null;
        private WorkspaceScene _wsScene = null;
        private Point _mouseOffset = null;
    }
}
