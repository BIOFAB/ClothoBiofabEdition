/*
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


package org.clothocore.api.core;

import java.io.File;
import java.util.ArrayList;
import org.clothocore.util.basic.FileUtils;
import org.clothocore.util.xml.XMLParser;

/**
 *
 * @author J.Christopher Anderson
 * @author Douglas Densmore
 */
public class PluginManager extends javax.swing.JDialog {


    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////


    /** 
     * Creates new form pluginManager
     */
    public PluginManager(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        loadDefaultTools();
        initComponents(getDisplayNames());
    }

    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PluginManager dialog = new PluginManager(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    private void initComponents(final String[] names) {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        uninstallButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Select items you wish to uninstall:");

        jList1.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() { return names.length; }
            @Override
            public Object getElementAt(int i) { return names[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        uninstallButton.setText("Uninstall Selected"); // NOI18N
        uninstallButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uninstallButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uninstallButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uninstallButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }


    /**
     * Goes through the dist\tools\xml directory and grabs all the plugin xml files in there
     */
    private static void loadDefaultTools() {
        
        _availablePlugins = new ArrayList<XMLParser>();
        //FIXME: Don't like the hard coded path
        File xmlLocations = new File("plugins/xml/");
        for (File toolXML : xmlLocations.listFiles()) {
            if (FileUtils.isXMLFile( toolXML.getName() )) {
                parseXMLFile( toolXML );
            }
        }
    }

    /**
     * Parse the XML files and make wrappers
     *
     * @param toolXML an XML file in the right directory
     */
    private static void parseXMLFile(File toolXML) {
        XMLParser parser = new XMLParser(toolXML, "plugin");
        _availablePlugins.add(parser);
    }

    /**
     * Create a list of the plugin display names
     * @return String[] of display names
     */
    private static String[] getDisplayNames() {
        String[] out = new String[_availablePlugins.size()];
        for(int i=0; i<out.length; i++) {
            out[i] = _availablePlugins.get(i).getFirstTag("displayname");
        }
        return out;
    }

    private void uninstallButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: replace all the ClothoBoot.boot() with the appropriate calls

        int[] uninstallIndices = jList1.getSelectedIndices();
        for(int i: uninstallIndices) {
            XMLParser xp = _availablePlugins.get(i);
            String type = xp.getFirstTag("type");
            if(type.equals("ClothoTool")) {
                String directory = "plugins/Tools/" + xp.getFirstTag("directory");
                File dir = new File(directory);
                recursiveDelete(dir);
                //ClothoBoot.boot();
            } else if(type.equals("ClothoFormat")) {
                //FIXME: DO A DATABASE CHECK FOR DEPENDENCIES, THROW MESSAGE TO WARN ABOUT IT
                //DELETE THE JAR
            } else if(type.equals("ClothoAlgorithm")) {
                String directory = "plugins/Algorithms/" + xp.getFirstTag("directory");
                File dir = new File(directory);
                recursiveDelete(dir);
                //ClothoBoot.boot();
            } else if(type.equals("ClothoViewer")) {
                String directory = "plugins/Viewers/" + xp.getFirstTag("directory");
                File dir = new File(directory);
                recursiveDelete(dir);
                //ClothoBoot.boot();
            } else if(type.equals("ClothoConnection")) {
                String directory = "plugins/Connections/" + xp.getFirstTag("directory");
                File dir = new File(directory);
                recursiveDelete(dir);
                //ClothoBoot.boot();
            }

            File xml = xp.getFile();
            xp = null;
            xml.delete();
        }
        _availablePlugins= null;
        dispose();
    }

    /**
     * Traverse a file and delete all the directories
     * @param dir
     */
    private void recursiveDelete(File dir) {
        for(File f: dir.listFiles()) {
            System.out.println(f.getAbsolutePath());
            if(f.isDirectory()) {
                recursiveDelete(f);
            } else {
                f.delete();
            }
        }
        dir.delete();
    }

    /**
     * Clear the available plug in list and dispose of the frame
     * @param evt
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        _availablePlugins= null;
        dispose();
    }


    ///////////////////////////////////////////////////////////////////
    ////                         private variables                ////

    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton uninstallButton;
    private static ArrayList<XMLParser> _availablePlugins;
}