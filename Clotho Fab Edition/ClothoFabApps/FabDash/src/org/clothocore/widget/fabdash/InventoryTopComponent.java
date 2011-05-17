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
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Plasmid;
import org.clothocore.widget.fabdash.browser.ObjTypeChooser;
import org.clothocore.widget.fabdash.browser.SearchBar;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.clothocore.widget.fabdash//Inventory//EN",
autostore = false)
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

    private void fetchInventoryInformation() {
//        //Switches Clotho to a local database
        //String selectstring = "org.clothocad.connection.localconnection";
        //ConnectionWrapper cw = (ConnectionWrapper) Collator.getPluginByUUID(selectstring);
        //Collator.setDefaultConnection(cw);
        new SwingWorker() {

            Collection personalCollection = null;

            @Override
            protected Object doInBackground() throws Exception {
                System.out.println("################################ FabDash connecting");
                if (!Collector.isConnected()) {
                    return null;
                }

                try {
                    personalCollection = Collector.getCurrentUser().getHerCollection();
                    ArrayList<ObjLink> allPlasmids = Collector.getAllLinksOf(ObjType.PLASMID);
                    InventoryTopComponent itc = InventoryTopComponent.getDefault(); //finds instance of inventory top component
                    System.out.println(((JTabbedPane)itc.getComponent(0)).getComponent(0));

                    JTable itcTable = (JTable) ((JViewport) ((JScrollPane) ((JTabbedPane) itc.getComponent(0)).getComponent(0)).getComponent(0)).getComponent(0); //retrieves a reference to the plasmidTable JTable in the InventoryTopComponent class

                    Object[][] itcTableModel = new Object[allPlasmids.size()][2];

                    for (int i = 0; i < allPlasmids.size(); i++) {
                        itcTableModel[i][0] = allPlasmids.get(i).name;
                        Plasmid aplas = Collector.getPlasmid(allPlasmids.get(i).uuid);
                        Format aform = aplas.getFormat(); //get the Format of aplas
                        itcTableModel[i][1] = aform.generateSequencingRegion(aplas).getSeq(); //based on the Format, the sequence of the region of interest is retreieved and used to populate the table

                    }

                    itcTable.setModel(new javax.swing.table.DefaultTableModel(itcTableModel, new String[]{"Plasmid Name", "Sequence"}));


                } catch (Exception e) {
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        plasmidTable = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        plasmidTable.setModel(new javax.swing.table.DefaultTableModel(
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
        plasmidTable.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(plasmidTable);
        plasmidTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(InventoryTopComponent.class, "InventoryTopComponent.plasmidTable.columnModel.title0")); // NOI18N
        plasmidTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(InventoryTopComponent.class, "InventoryTopComponent.plasmidTable.columnModel.title1")); // NOI18N

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(InventoryTopComponent.class, "InventoryTopComponent.jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable plasmidTable;
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
