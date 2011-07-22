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

import eugene.Primitive;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.clothocad.tool.spectacles.Spectacles;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;
import org.clothocad.tool.spectacles.ui.frames.PropertiesDialog;
import org.clothocad.tool.spectacles.ui.frames.WorkspaceFrame;

/**
 * WorkspaceScene forms the back end of the parts canvas in WorkspaceFrame.
 * Parts on the canvas are stored and tracked here. This class also contains
 * convenience methods for calculating where a part widget goes on the canvas.
 * @author Rich
 */
public class WorkspaceScene extends GraphScene  {

    /**
     * Creates a WorkspaceScene in the provided WorkspaceFrame. Part properties
     * will be displayed with the provided PropertiesDialog. A JScrollPane
     * containing the view for this scene is also generated.
     * @param wsFrame the parent WorkspaceFrame.
     * @param propertiesDialog a PropertiesDialog.
     */
    public WorkspaceScene(WorkspaceFrame wsFrame, PropertiesDialog propertiesDialog) {
        _wsFrame = wsFrame;
        _propertiesDialog = propertiesDialog;
        _mainLayer = new LayerWidget(this);
        addChild(_mainLayer);
        _widgetList = new ArrayList<Widget>();
        _dpwList = new ArrayList<Object>();
        _hidden = false;
        _jsp = new JScrollPane();
        _jsp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        _jsp.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resolveGridBounds();
            }
        });
        _jsp.setViewportView(createView());
        resolveGridBounds();
    }

    /** Pixel height of a part icon. */
    public static final int iconHeight = 56;
    /** Pixel width of a part icon. */
    public static final int iconWidth = 56;
    /** Pixel height of a grid box. */
    public static final int gridBoxHeight = iconHeight + 33;
    /** Pixel width of a grid box. */
    public static final int gridBoxWidth = iconWidth + 1;

    /**
     * Deletes the currently selected widget in this WorkspaceScene.
     */
    public void deleteFocusedWidget() {
        if (hasFocusedWidget()) {
            int option = JOptionPane.showConfirmDialog(
                    null, "Delete this part?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                deleteWidget(getFocusedWidget());
            }
        }
    }

    /**
     * Deletes the specified widget in this WorkspaceScene.
     * @param widget the widget to be deleted.
     */
    public void deleteWidget(Widget widget) {
        removeNode(findObject(widget));
        widget.removeFromParent();
        _widgetList.remove(widget);
        setFocusedWidget(null);
        _wsFrame.clearTextPane();
        resolveGridBounds();
        _wsFrame.setModified(true);
    }

    /**
     * Get the list of hidden wrappers.
     * @return the wrapper sequence.
     */
    public List<Object> getDPWList() {
        return _dpwList;
    }

    /**
     * Returns the JScrollPane containing this WorkspaceScene's view.
     * @return the JScrollPane with this WorkspaceScene's view.
     */
    public JScrollPane getJScrollPane() {
        return _jsp;
    }

    /**
     * Returns a list of widgets in this WorkspaceScene ordered
     * by their positions in the current sequence.
     * @return the list of widgets in sequence order.
     */
    public List<Widget> getWidgetList() {
        return _widgetList;
    }

    /**
     * Returns <code>true</code> if there is a currently selected widget.
     * @return <code>true</code> if there is a focused widget,
     * and <code>false</code> otherwise.
     */
    public boolean hasFocusedWidget() {
        return getFocusedWidget() != this;
    }

    /**
     * Cleans up this scene for when it is hidden.
     */
    public void hideContents() {
        boolean modified = _wsFrame.isModified();
        while (!_widgetList.isEmpty()) {
            _dpwList.add(findObject(_widgetList.get(0)));
            deleteWidget(_widgetList.get(0));
        }
        _hidden = true;
        _wsFrame.setModified(modified);
    }

    /**
     * Adds a wrapper to the hidden list; intended for imports.
     * @param node the node (a DevicePartWrapper) to be inserted.
     */
    public void insertHiddenNode(Object node) {
        if (!_hidden) {
            System.err.println("Scene is not hidden. Should be using insertNode.");
            insertNode(node);
        } else {
            _dpwList.add(node);
        }
    }

    /**
     * Adds the node and inserts the corresponding widget
     * at the end of the widget list.
     * @param node the node (a DevicePartWrapper) to be inserted.
     * @return the child widget.
     */
    public Widget insertNode(Object node) {
        return insertNode(0, node);
    }

    /**
     * Adds the node and inserts the corresponding widget into the
     * widget list at the provided index. If the index is invalid,
     * the node is inserted at the end of the sequence.
     * @param index the index at which to insert the node.
     * @param node the node (a DevicePartWrapper) to be inserted.
     * @return the child widget.
     */
    public Widget insertNode(int index, Object node) {
        if (_hidden) {
            System.err.println("Scene is hidden, should be using insertHiddenNode.");
            _dpwList.add(index, node);
            return null;
        }
        Widget child = addNode(node);
        int size = _widgetList.size();
        if (index < size && index >= 0) {
            _widgetList.remove(_widgetList.size()-1);
            if (index == size-1) {
                _widgetList.add(size-1, child);
            } else {
                _widgetList.add(index, child);
            }
        }
        // TODO: Active rule checking block goes here.
        resolveGridBounds();
        return child;
    }

    /**
     * Returns the visibility status of this scene.
     * @return the visibility status of this scene.
     */
    public boolean isHidden() {
        return _hidden;
    }

    /**
     * This method is called when this WorkspaceScene's JScrollPane is resized.
     * The scene's bounds and grid dimensions are re-calculated, and this
     * WorkspaceScene's children are rearranged to fit in the JScrollPane.
     */
    public void resolveGridBounds() {
        Rectangle bounds = _jsp.getVisibleRect();
        // adjusting insets; might remove this in the future
        bounds = new Rectangle(-2, -2, bounds.width, bounds.height);
        Dimension dim = _jsp.getSize();
        gridRows = Math.max(1, dim.height / gridBoxHeight);
        gridCols = Math.max(1, dim.width / gridBoxWidth);
        int rows = (_widgetList.size() / gridCols) + 1;
        if (rows > gridRows) {
            int newHeight = (rows+1)*gridBoxHeight;
            setMaximumBounds(new Rectangle(bounds.x, bounds.y, bounds.width, newHeight));
            setMaximumSize(new Dimension(getMaximumSize().width, newHeight));
        } else {
            setMaximumBounds(bounds);
            setMaximumSize(dim);
        }
        arrangeWidgets();
    }

    /**
     * Shows this scene's widgets.
     */
    public void showContents() {
        boolean modified = _wsFrame.isModified();
        for (Object node : _dpwList) {
            addNode(node);
        }
        _dpwList.clear();
        _hidden = false;
        _wsFrame.setModified(modified);
    }

    /**
     * Takes an IconNodeWidget and updates its label and border
     * for its associated device or part.
     * @param widget the IconNodeWidget to update.
     */
    public void updateWidget(IconNodeWidget widget) {
        DevicePartWrapper wrapper = (DevicePartWrapper) findObject(widget);
        updateWidget(widget, wrapper);
    }

    /**
     * Adds a ghost widget to this scene. There is no node associated with this widget.
     * @param widget a ghost widget.
     */
    protected void addGhostWidget(IconNodeWidget widget) {
        _mainLayer.addChild(widget);
    }

    /**
     * Rearranges this WorkspaceScene's children to fit within the grid.
     */
    protected void arrangeWidgets() {
        for (int i = 0; i < _widgetList.size(); i++) {
            _widgetList.get(i).setPreferredLocation(convertIndex2Point(i));
        }
        validate();
    }

    @Override
    protected Widget attachNodeWidget(Object node) {
        if (node instanceof DevicePartWrapper) {
            DevicePartWrapper wrapper = (DevicePartWrapper) node;
            // Resolving the part/device image
            Image image = SpectaclesFactory.resolveImage(wrapper);
            IconNodeWidget child = createChild(wrapper.getName(), image);
            if (wrapper.isPart()) {
                updateWidget(child, wrapper);
            }
            _wsFrame.setModified(true);
            return child;
        } else {
            throw new ClassCastException();
        }
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
     * Converts a Point containing a grid coordinate to an array index for the widget list.
     * There is no guarantee that the returned index is a valid array index.
     * @param gridCoord a grid coordinate.
     * @return the corresponding widget list array index.
     */
    protected int convertGrid2Index(Point gridCoord) {
        return gridCols * gridCoord.y + gridCoord.x;
    }

    /**
     * Converts a Point containing a grid coordinate to a Point containing a pixel
     * location in this WorkspaceScene.
     * @param gridCoord a grid coordinate.
     * @return the corresponding pixel point location.
     */
    protected Point convertGrid2Point(Point gridCoord) {
        return new Point(gridCoord.x * gridBoxWidth, gridCoord.y * gridBoxHeight);
    }

    /**
     * Converts a widget list array index to a Point containing a grid coordinate.
     * @param index a widget list array index.
     * @return the corresponding grid coordinate.
     */
    protected Point convertIndex2Grid(int index) {
        int row = index / gridCols;
        return new Point((index - row*gridCols) % gridCols, row);
    }

    /**
     * Converts a widget list array index to a Point containing a pixel point location.
     * @param index a widget list array index.
     * @return the corresponding pixel point location.
     */
    protected Point convertIndex2Point(int index) {
        return convertGrid2Point(convertIndex2Grid(index));
    }

    /**
     * Converts a pixel point location to a Point containing a grid coordinate.
     * @param pixelPoint a pixel point location.
     * @return the corresponding grid coordinate.
     */
    protected Point convertPoint2Grid(Point pixelPoint) {
        Point gridCoord = new Point(pixelPoint.x / gridBoxWidth, pixelPoint.y / gridBoxHeight);
        if (gridCoord.x > gridCols-1 || pixelPoint.x < 0 || pixelPoint.y < 0) {
            return new Point (gridCols, gridRows);
        }
        return gridCoord;
    }

    /**
     * Converts a pixel point location to a widget list array index.
     * There is no guarantee that the returned index is a valid array index.
     * @param pixelPoint a pixel point location.
     * @return the corresponding widget list array index.
     */
    protected int convertPoint2Index(Point pixelPoint) {
        return convertGrid2Index(convertPoint2Grid(pixelPoint));
    }

    /**
     * Returns the parent WorkspaceFrame of this WorkspaceScene.
     * @return the parent WorkspaceFrame.
     */
    protected WorkspaceFrame getWorkspaceFrame() {
        return _wsFrame;
    }

    /**
     * Given a pixel point location, determines whether or not that point
     * is in a 'snap zone' in this WorkspaceScene.
     * @param location a pixel point location.
     * @return <code>true</code> if the point is in a 'snap zone',
     * and <code>false</code> otherwise.
     */
    protected boolean isSnappable(Point location) {
        int snapSize = 35;
        location.translate(-3, -3);
        Point gridPoint = new Point (location.x - location.x % gridBoxWidth,
                location.y - location.y % gridBoxHeight);
        Rectangle r = new Rectangle(gridPoint.x, gridPoint.y, snapSize, snapSize);
        return r.contains(location);
    }

    /**
     * Removes the specified ghost widget from this WorkspaceScene.
     * @param widget the ghost widget to be removed.
     */
    protected void removeGhostWidget(IconNodeWidget widget) {
        _mainLayer.removeChild(widget);
        validate();
    }

    protected int gridRows;
    protected int gridCols;

    /**
     * Creates a child widget in this WorkspaceScene
     * and puts it at the end of the sequence.
     * Images may be shared by multiple widgets.
     * @param label the child widget's label.
     * @param image the child widget's image.
     * @return the child widget.
     */
    private IconNodeWidget createChild(String label, Image image) {
        IconNodeWidget widget = SpectaclesFactory.createIconNodeWidget(this, label, image);
        widget.getActions().addAction(ActionFactory.createPopupMenuAction(
                SpectaclesFactory.createGridWidgetContextMenuProvider(_propertiesDialog)));
        widget.getActions().addAction(SpectaclesFactory.createGridMouseHandler());
        widget.getActions().addAction(ActionFactory.createSelectAction(
                SpectaclesFactory.createGridSelectProvider(), true));
        widget.getActions().addAction(ActionFactory.createMoveAction(
                SpectaclesFactory.createGridMoveStrategy(),
                SpectaclesFactory.createGridMoveProvider()));
        widget.setPreferredLocation(convertIndex2Point(_widgetList.size()));
        _mainLayer.addChild(widget);
        _widgetList.add(widget);
        resolveGridBounds();
        return widget;
    }

    /**
     * Updates the given widget's border according to the properties
     * in the given DevicePartWrapper. The wrapper MUST be associated
     * with the widget.
     * @param widget
     * @param wrapper
     */
    // NEVER make this method public or protected
    private void updateWidget(IconNodeWidget widget, DevicePartWrapper wrapper) {
        String label = wrapper.getName();
        widget.getLabelWidget().setFont(SpectaclesFactory.labelFontPlain);
        widget.setLabel(label.substring(0, Math.min(label.length(), 10)));
        if (wrapper.isPart())
        {
            HashMap<String, Primitive> properties = wrapper.getPartPropertyValues();
            if (Spectacles.isRunningSolo() && (!properties.containsKey("Sequence")
                    || ((String) properties.get("Sequence").getValue()).equals(""))) {
                widget.setBorder(SpectaclesFactory.lineBorderYellow);
            } else if (!Spectacles.isRunningSolo())
            {
                if (!properties.containsKey("Sequence")
                    || ((String) properties.get("Sequence").getValue()).equals(""))
                {
                    if (!properties.containsKey("ID") || properties.get("ID").txt.equals(""))
                    { // set to yellow (this should always happen on part creation)
                        widget.setBorder(SpectaclesFactory.lineBorderYellow);
                    } else { // an ID has been assigned, so this shouldn't happen
                        widget.setBorder(SpectaclesFactory.lineBorderBlue);
                    }
                } else if (!properties.containsKey("ID") || properties.get("ID").txt.equals(""))
                { // sequence is set, no ID
                    if (wrapper.implementations < 1) {
                        widget.setBorder(SpectaclesFactory.lineBorderBlack);
                    } else {
                        widget.setBorder(SpectaclesFactory.lineBorderCyan);
                    }
                } else {
                    widget.setBorder(SpectaclesFactory.lineBorderBlack);
                }
            } else {
                widget.setBorder(SpectaclesFactory.lineBorderBlack);
            }
        } else {
            // Currently do nothing for devices.
        }
    }

    private PropertiesDialog _propertiesDialog;
    private WorkspaceFrame _wsFrame;
    private JScrollPane _jsp;
    private LayerWidget _mainLayer;
    private ArrayList<Widget> _widgetList;
    private ArrayList<Object> _dpwList;
    private boolean _hidden;
}
