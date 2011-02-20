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
 * DefaultPluginManager.java
 *
 * Created on Jul 21, 2010, 8:10:31 PM
 */

package org.clothocore.util.def;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import net.iharder.dnd.FileDrop;
import org.clothocore.api.core.Collator;

/**
 *
 * @author J.Christopher Anderson
 * @author Douglas Densmore
 */
public class DefaultPluginManager extends javax.swing.JDialog {


    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** Creates new form DefaultPluginManager */
    public DefaultPluginManager(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        new  FileDrop( this, new FileDrop.Listener() {
            @Override
            public void  filesDropped( java.io.File[] files ) {
                pluginDropped(files);
            }});
        setVisible(true);
    }

    /**
     * Called when a plugin gets dropped, it calls on the plugin loader
     *
     * @param files
     */
    private void pluginDropped(final File[] files) {
        System.out.println("****You dropped " + files[0].getName());
        final ArrayList<File> fileList = Collator.unpackInstall(files);
        if(fileList != null)
        {
            new Thread() {

                    //@Override
                    public void run() {

                        List<File> installList = Collator.areInstallable(fileList);
                        if (!installList.isEmpty()) {
                            Collator.installPlugins(installList);
                        }
                    }
                }.start();
                //Collator.refreshDash();

        }
    }

    private void initComponents() {
        setTitle("Plugin Manager");
        cancelButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        Message = new javax.swing.JTextArea();
        Message.setBorder(null);
        Message.setOpaque(false);
        Message.setBackground(null);
        Message.setEditable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cancelButton.setText("cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Reset Preferences");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Message.setColumns(20);
        Message.setLineWrap(true);
        Message.setRows(5);
        Message.setText("There is currently no plugin manager tool installed.  You can drag .clo plugin files" +
                "directly onto this dialog or reset your preferences.");
        Message.setWrapStyleWord(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Message, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Message, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {

        dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        Preferences  _corePrefs = Preferences.userNodeForPackage( Collator.class );
        try {
            _corePrefs.flush();
        } catch (BackingStoreException ex) {
        }
        dispose();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultPluginManager dialog = new DefaultPluginManager(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.out.println("closing called");
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    ///////////////////////////////////////////////////////////////////
    ////                         private  variables                    ////
    private javax.swing.JTextArea Message;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButton2;

    // End of variables declaration

}
