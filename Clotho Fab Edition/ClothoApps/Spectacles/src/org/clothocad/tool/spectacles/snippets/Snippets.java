/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.spectacles.snippets;

/**
 *
 * @author Rich
 */
public class Snippets {
    
    void deviceDeletionSnippet() {
        //String deviceName = mainJTP.getTitleAt(tabIndex);
        //DevicePartWrapper wrapper = _manager.getWrappedDevice(deviceName);
        //devicesMenu.remove(_deviceMenuItemMap.get(wrapper));
    }

    void exporterCode() {
        //String[] originNames = _wsFrame.getManagerTracker().keySet().toArray(new String[0]);
        //String originFileName = _wsFrame.getCurrentFilePath(); //originNames[0];   // should only be one Manager in the ManagerTracker
        /*managerForExport.getEugeneComponentDecs().clear();
        HashMap<String, DevicePartWrapper> wrappedComponents = managerForExport.getAllWrappedComponents();
        for (String aComponentName : wrappedComponents.keySet()) {
        if (wrappedComponents.get(aComponentName).isPart()) {
        managerForExport.getEugeneComponentDecs().put(aComponentName, wrappedComponents.get(aComponentName).getPart());
        } else {
        JOptionPane.showMessageDialog(null, "Problem exporting: bad data structure",
        "Error", JOptionPane.ERROR_MESSAGE);
        return;
        }
        }*/
    }

    void importerCode() {
        /*System.out.println("PropertyDefs: " + _parser.propertyDefinitions.keySet());
        System.out.println("PartDefs: " + _parser.partDefinitions.keySet());
        System.out.println("PrimitiveDecs: " + _parser.primitiveDeclarations.keySet());
        System.out.println("PartDecs: " + _parser.partDeclarations.keySet());
        System.out.println("DeviceDecs: " + _parser.deviceDeclarations.keySet());
        System.out.println("RuleDecs: " + _parser.ruleDeclarations.keySet());
        System.out.println("RuleAsserts: " + _parser.ruleAssertions.keySet());
        System.out.println("RuleNotes: " + _parser.ruleNotes.keySet());*/

        // Import information into Manager
        /* keep this for later - allows for import of multiple files
        _gridFrame.getManagerTracker().put(fileName, new Manager(fileName));
        for (String aDeviceName : _parser.deviceDeclarations.keySet()) {
        importAndWrapDevice(aDeviceName, fileName);
        _gridFrame.getCurrentGGScene().validate();
        }*/
    }

    void gridGraphSceneInitCode() {
        //setKeyEventProcessingType(EventProcessingType.ALL_WIDGETS);
        //mainLayer = new LayerWidget(this);
        //mainLayer.getActions().addAction(GridFactory.createGridKBHandler(this));
        //mainLayer.setLayout(new PartsGridLayout());
        //addChild(mainLayer);
    }

    void widgetHandlers() {
        //w.addDependency(GridFactory.createWidgetListener(this, w));
    /*private static class GridKBHandler extends WidgetAction.Adapter {
        public GridKBHandler (GridGraphScene ggScene) {
            this.ggScene = ggScene;
        }

        @Override
        public State keyTyped(Widget widget, WidgetKeyEvent event) {
            if (widget instanceof LayerWidget) {
                if (event.getKeyChar() == KeyEvent.VK_SPACE) {
                    //System.out.println("space");
                    return State.CONSUMED;
                }
                if (event.getKeyChar() == KeyEvent.VK_DELETE) {
                    //System.out.println("delete");
                    ggScene.deleteSelectedWidget();
                    return State.CONSUMED;
                }
            }
            return State.REJECTED;
        }

        private GridGraphScene ggScene;

    }

    private static class GridMouseHandler extends WidgetAction.Adapter {
        @Override
        public State mouseClicked(Widget widget, WidgetMouseEvent event) {
            if (event.getClickCount() == 1) {
                switch(event.getButton()) {
                    case(MouseEvent.BUTTON1):
                        break;
                    case(MouseEvent.BUTTON3):
                        // get wrapper and call editProperties();
                        break;
                }
            }
            return State.REJECTED;
        }

        @Override
        public State focusGained(Widget widget, WidgetAction.WidgetFocusEvent event) {
            widget.setBorder(GridGraphScene.blueLineBorder);
            //System.out.println("focus gained");
            return State.REJECTED;
        }

        @Override
        public State focusLost(Widget widget, WidgetAction.WidgetFocusEvent event) {
            widget.setBorder(GridGraphScene.blackLineBorder);
            //System.out.println("focus lost");
            return State.REJECTED;
        }
    }

    private static class WidgetListener implements Widget.Dependency {

        public WidgetListener(GridGraphScene ggScene, Widget widget) {
            this.ggScene = ggScene;
            layer = ggScene.getMainLayer();
            targetWidget = widget;
            origPos = targetWidget.getLocation();
        }

        @Override
        public void revalidateDependency() {
            if (!targetWidget.getLocation().equals(origPos)) {
                System.out.println("beep beep beep");
                origPos = targetWidget.getLocation();
            }
        }

        private GridGraphScene ggScene;
        private LayerWidget layer;
        private Widget targetWidget;
        private Point origPos;
    }*/
    }

    void managerAddWrappedPartCode() {
        /** Adds a DevicePartWrapper to the hash map storing parts.
         * If the wrapper does not contain a Component object, nothing is added.
         *
         * @return true if a DevicePartWrapper is successfully added,
         * or false if nothing is added.
         */
        /*public boolean addWrappedComponent(DevicePartWrapper wrapper) {
        if (wrapper.isPart()) {
        _wrappedComponents.put(wrapper.getName(), wrapper);
        return true;
        }
        return false;
        }*/
    }
}
