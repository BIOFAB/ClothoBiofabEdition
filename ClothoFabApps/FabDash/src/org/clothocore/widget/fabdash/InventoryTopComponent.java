/*
 * 
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
package org.clothocore.widget.fabdash;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingWorker;

import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.widget.fabdash.browser.ObjTypeChooser;
import org.clothocore.widget.fabdash.browser.SearchBar;

import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import org.netbeans.api.settings.ConvertAsProperties;

@ConvertAsProperties(dtd = "-//org.clothocore.widget.fabdash//Inventory//EN", autostore = false)
public final class InventoryTopComponent extends TopComponent {

    private static InventoryTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "InventoryTopComponent";

    public InventoryTopComponent() {


        initComponents();
        add(new ObjTypeChooser(this), java.awt.BorderLayout.NORTH);
        add(new SearchBar(), java.awt.BorderLayout.SOUTH);


        setName(NbBundle.getMessage(InventoryTopComponent.class, "CTL_InventoryTopComponent"));
        setToolTipText(NbBundle.getMessage(InventoryTopComponent.class, "HINT_InventoryTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);

    }

    public void changeObjType(ObjType type) {
        _chosenType = type;
        System.out.println(_chosenType);
    }

    private void fetchInventoryInformation()
    {
        new SwingWorker() {

            Collection personalCollection = null;

            @Override
            protected Object doInBackground() throws Exception {
                System.out.println("################################ FabDash connecting");
                
                if (!Collector.isConnected())
                {
                    return null;
                }

                try
                {
                    personalCollection = Collector.getCurrentUser().getHerCollection();
//                    InventoryTopComponent itc = InventoryTopComponent.getDefault(); //finds instance of InventoryTopComponent
//                    //Code for populating the Plasmid tab is below
//                    ArrayList<ObjLink> allPlasmids = Collector.getAllLinksOf(ObjType.PLASMID);
//                    JTable itcPlasmidTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(0)).getComponent(0)).getComponent(0); //retrieves a reference to the plasmidTable JTable in the InventoryTopComponent class
//                    Object[][] plasmidTableModel = new Object[allPlasmids.size()][2];
//
//                    for (int i = 0; i < allPlasmids.size(); i++)
//                    {
//                        plasmidTableModel[i][0] = allPlasmids.get(i).name;
//                        Plasmid aplas = Collector.getPlasmid(allPlasmids.get(i).uuid);
//                        Format aform = aplas.getFormat(); //get the Format of aplas
//                        plasmidTableModel[i][1] = aform.generateSequencingRegion(aplas).getSeq(); //based on the Format, the sequence of the region of interest is retreieved and used to populate the table
//                    }
//
//                    itcPlasmidTable.setModel(new javax.swing.table.DefaultTableModel(plasmidTableModel, new String[]{"Plasmid Name", "Sequence"}));


                    ArrayList<ObjLink> allOligos = Collector.getAllLinksOf(ObjType.OLIGO);
                    //JTable itcOligoTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(1)).getComponent(0)).getComponent(0);
                    Object[][] oligoTableModel = new Object[allOligos.size()][2];
                    
                    for (int i = 0; i < allOligos.size(); i++)
                    {
                        oligoTableModel[i][1] = allOligos.get(i).name;
                        Oligo aolig = Collector.getOligo(allOligos.get(i).uuid);
                        //oligoTableModel[i][1] = aolig.getSeq();
                    }
                    
                    oligosTable.setModel(new javax.swing.table.DefaultTableModel(oligoTableModel, new String[]{"Identifier", "Description"}));

                    //Code for populating the Vectors tab is below
//                    ArrayList<ObjLink> allVectors = Collector.getAllLinksOf(ObjType.VECTOR);
//                    JTable itcVectorTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(2)).getComponent(0)).getComponent(0);
//                    Object[][] vectorTableModel = new Object[allVectors.size()][2];
//
//                    for (int i = 0; i < allVectors.size(); i++) {
//                        vectorTableModel[i][0] = allVectors.get(i).name;
//                        Vector avec = Collector.getVector(allVectors.get(i).uuid);
//                        vectorTableModel[i][1] = avec.getSeq(); //based on the Format, the sequence of the region of interest is retreieved and used to populate the table
//                    }
//                    itcVectorTable.setModel(new javax.swing.table.DefaultTableModel(vectorTableModel, new String[]{"Vector Name", "Sequence"}));

                    //Code for populating the Features tab is below
//                    ArrayList<ObjLink> allFeatures = Collector.getAllLinksOf(ObjType.FEATURE);
//                    JTable itcFeatureTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(3)).getComponent(0)).getComponent(0);
//                    Object[][] featureTableModel = new Object[allFeatures.size()][2];
//                    for (int i = 0; i < allFeatures.size(); i++) {
//                        featureTableModel[i][0] = allFeatures.get(i).name;
//                        Feature afeat = Collector.getFeature(allFeatures.get(i).uuid);
//                        featureTableModel[i][1] = afeat.getSeq(); //based on the Format, the sequence of the region of interest is retreieved and used to populate the table
//                    }
//                    itcFeatureTable.setModel(new javax.swing.table.DefaultTableModel(featureTableModel, new String[]{"Feature Name", "Sequence"}));

                    //Code for populating the Parts tab is below
//                    ArrayList<ObjLink> allParts = Collector.getAllLinksOf(ObjType.PART);
//                    JTable itcPartTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(4)).getComponent(0)).getComponent(0);
//                    Object[][] partTableModel = new Object[allParts.size()][1];
//                    for (int i = 0; i < allParts.size(); i++) {
//                        partTableModel[i][0] = allParts.get(i).name;
//                    }
//                    itcPartTable.setModel(new javax.swing.table.DefaultTableModel(partTableModel, new String[]{"Part Name"}));



                } 
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void done() {
//                browseTree.setModel( CollectionBrowser.generate(personalCollection));
//                browseTree.validate();
                repaint();
            }
        }.execute();


    }
    //populates the Parts Tab
//    public static void refreshPartsTab()
//    {
//        InventoryTopComponent itc = InventoryTopComponent.getDefault(); //finds instance of InventoryTopComponent
//        ArrayList<ObjLink> allParts = Collector.getAllLinksOf(ObjType.PART);
//        JTable itcPartTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(4)).getComponent(0)).getComponent(0);
//        Object[][] partTableModel = new Object[allParts.size()][1];
//
//        for (int i = 0; i < allParts.size(); i++)
//        {
//            partTableModel[i][0] = allParts.get(i).name;
//        }
//
//        itcPartTable.setModel(new javax.swing.table.DefaultTableModel(partTableModel, new String[]{"Part Name"}));
//        itcPartTable.repaint();
//    }
    //refreshes and populates Plasmid Tab
    public static void refreshPlasmidTab() {

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inventoryTabbedPane = new javax.swing.JTabbedPane();
        oligosScrollPane = new javax.swing.JScrollPane();
        oligosTable = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        oligosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identifier", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        oligosScrollPane.setViewportView(oligosTable);

        inventoryTabbedPane.addTab(org.openide.util.NbBundle.getMessage(InventoryTopComponent.class, "InventoryTopComponent.oligosScrollPane.TabConstraints.tabTitle_1"), oligosScrollPane); // NOI18N

        add(inventoryTabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTabbedPane inventoryTabbedPane;
    protected javax.swing.JScrollPane oligosScrollPane;
    protected javax.swing.JTable oligosTable;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized InventoryTopComponent getDefault() {
        if (instance == null) {
            instance = new InventoryTopComponent();
        }

        return instance;
    }

    /**
     * Obtain the InventoryTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized InventoryTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(InventoryTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof InventoryTopComponent) {
            return (InventoryTopComponent) win;
        }
        Logger.getLogger(InventoryTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    //Called to populate the tree
    public void componentOpened() {
        fetchInventoryInformation();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }

        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    private ObjType _chosenType = ObjType.PART;
}
