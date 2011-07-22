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
 * PreferencesDialog.java
 *
 * Created on Aug 19, 2009, 2:27:51 PM
 */

package org.clothocad.tool.spectacles.ui.frames;

import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import org.clothocad.tool.spectacles.Spectacles;

/**
 * PreferencesDialog stores and accesses user preference data for Spectacles.
 * @author Rich
 */
public class PreferencesDialog extends javax.swing.JDialog {

    /** Creates new form PreferencesDialog */
    public PreferencesDialog(WorkspaceFrame wsFrame, Preferences preferences) {
        super(wsFrame, true);
        initComponents();
        _wsFrame = wsFrame;
        _preferences = preferences;
        _imageLocationModified = false;
    }

    public static final boolean defaultImageOutputBoxes = false;
    public static final boolean defaultPropertiesDialogPopup = true;
    public static final boolean defaultEugeneRuleChecking = false;
    public static final String defaultImageLocation = Spectacles.getImageLocation();
    public static final String defaultHeaderFilesLocation = Spectacles.getHeaderFilesLocation();

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            loadPreferences();
            setLocationRelativeTo(_wsFrame);
        }
        super.setVisible(visible);
    }

    /**
     * Loads default preferences for Spectacles.
     */
    protected void loadDefaults() {
        checkBoxImageOutputBoxes.setSelected(defaultImageOutputBoxes);
        checkBoxPropertiesDialogPopup.setSelected(defaultPropertiesDialogPopup);
        checkBoxEugeneRuleChecking.setSelected(defaultEugeneRuleChecking);
        imagePathTextField.setText("");
        Spectacles.setImageLocation(defaultImageLocation);
        _imageLocationModified = false;
        headerFilesPathTextField.setText("");
        Spectacles.setHeaderFilesLocation(defaultHeaderFilesLocation);
    }

    /**
     * Loads saved user preferences for Spectacles, or the defaults if none exist.
     */
    protected void loadPreferences() {
        checkBoxImageOutputBoxes.setSelected(
                _preferences.getBoolean("ImageOutputBoxes", defaultImageOutputBoxes));
        checkBoxPropertiesDialogPopup.setSelected(
                _preferences.getBoolean("PropertiesDialogPopup", defaultPropertiesDialogPopup));
        checkBoxEugeneRuleChecking.setSelected(
                _preferences.getBoolean("EugeneRuleChecking", defaultEugeneRuleChecking));
        String location = _preferences.get("ImageLocation", "");
        //System.out.println("Loading images: " + location);
        if (location.equals("")) {
            imagePathTextField.setText("");
        } else {
            imagePathTextField.setText(location);
            Spectacles.setImageLocation(location);
        }
        _imageLocationModified = false;
        String headerLocation = _preferences.get("HeaderFilesLocation", "");
        if (headerLocation.equals("")) {
            headerFilesPathTextField.setText("");
        } else {
            headerFilesPathTextField.setText(headerLocation);
            Spectacles.setHeaderFilesLocation(headerLocation);
        }
    }

    /**
     * Saves user preferences as selected on the form.
     */
    protected void savePreferences() {
        _preferences.putBoolean("ImageOutputBoxes", checkBoxImageOutputBoxes.isSelected());
        _preferences.putBoolean("PropertiesDialogPopup", checkBoxPropertiesDialogPopup.isSelected());
        _preferences.putBoolean("EugeneRuleChecking", checkBoxEugeneRuleChecking.isSelected());
        String location = imagePathTextField.getText();
        String oldLocation = _preferences.get("ImageLocation", "");
        if (!oldLocation.equals(location)) {
            _imageLocationModified = true;
        }
        if (location.equals("")) {
            Spectacles.setImageLocation(defaultImageLocation);
        } else {
            Spectacles.setImageLocation(location);
        }
        if (_imageLocationModified) {
            _wsFrame.reloadThumbnailScenePalette();
        }
        if (!location.equals("") && !location.endsWith(File.separator)) {
            location += File.separator;
        }
        _preferences.put("ImageLocation", location);
        _imageLocationModified = false;
        String headerLocation = headerFilesPathTextField.getText();
        if (headerLocation.equals("")) {
            Spectacles.setHeaderFilesLocation(defaultHeaderFilesLocation);
        } else {
            Spectacles.setHeaderFilesLocation(headerLocation);
        }
        if (!headerLocation.equals("") && !headerLocation.endsWith(File.separator)) {
            headerLocation += File.separator;
        }
        _preferences.put("HeaderFilesLocation", headerLocation);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        folderChooser = new javax.swing.JFileChooser();
        checkBoxImageOutputBoxes = new javax.swing.JCheckBox();
        checkBoxPropertiesDialogPopup = new javax.swing.JCheckBox();
        checkBoxEugeneRuleChecking = new javax.swing.JCheckBox();
        imageLocationLabel = new javax.swing.JLabel();
        imagePathTextField = new javax.swing.JTextField();
        imagePathBrowseButton = new javax.swing.JButton();
        headerFilesLocationLabel = new javax.swing.JLabel();
        headerFilesPathTextField = new javax.swing.JTextField();
        headerFilesPathBrowseButton = new javax.swing.JButton();
        defaultsButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        folderChooser.setFileFilter(null);
        folderChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.title")); // NOI18N
        setAlwaysOnTop(true);
        setResizable(false);

        checkBoxImageOutputBoxes.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.checkBoxImageOutputBoxes.text")); // NOI18N
        checkBoxImageOutputBoxes.setToolTipText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.checkBoxImageOutputBoxes.toolTipText")); // NOI18N

        checkBoxPropertiesDialogPopup.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.checkBoxPropertiesDialogPopup.text")); // NOI18N
        checkBoxPropertiesDialogPopup.setToolTipText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.checkBoxPropertiesDialogPopup.toolTipText")); // NOI18N

        checkBoxEugeneRuleChecking.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.checkBoxEugeneRuleChecking.text")); // NOI18N
        checkBoxEugeneRuleChecking.setToolTipText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.checkBoxEugeneRuleChecking.toolTipText")); // NOI18N

        imageLocationLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.imageLocationLabel.text")); // NOI18N

        imagePathTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.imagePathTextField.text")); // NOI18N

        imagePathBrowseButton.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.imagePathBrowseButton.text")); // NOI18N
        imagePathBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagePathBrowseButtonActionPerformed(evt);
            }
        });

        headerFilesLocationLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.headerFilesLocationLabel.text")); // NOI18N

        headerFilesPathTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.headerFilesPathTextField.text")); // NOI18N

        headerFilesPathBrowseButton.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.headerFilesPathBrowseButton.text")); // NOI18N
        headerFilesPathBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerFilesPathBrowseButtonActionPerformed(evt);
            }
        });

        defaultsButton.setMnemonic('d');
        defaultsButton.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.defaultsButton.text")); // NOI18N
        defaultsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultsButtonActionPerformed(evt);
            }
        });

        saveButton.setMnemonic('s');
        saveButton.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.saveButton.text")); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setMnemonic('c');
        cancelButton.setText(org.openide.util.NbBundle.getMessage(PreferencesDialog.class, "PreferencesDialog.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(checkBoxImageOutputBoxes)
            .addComponent(checkBoxPropertiesDialogPopup)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(defaultsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(checkBoxEugeneRuleChecking)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageLocationLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(headerFilesPathTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(imagePathTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imagePathBrowseButton)
                            .addComponent(headerFilesPathBrowseButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerFilesLocationLabel)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBoxImageOutputBoxes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxPropertiesDialogPopup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxEugeneRuleChecking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageLocationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imagePathBrowseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerFilesLocationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(headerFilesPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headerFilesPathBrowseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(saveButton)
                    .addComponent(defaultsButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        savePreferences();
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void defaultsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultsButtonActionPerformed
        loadDefaults();
    }//GEN-LAST:event_defaultsButtonActionPerformed

    private void imagePathBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagePathBrowseButtonActionPerformed
        if (folderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            _imageLocationModified = true;
            imagePathTextField.setText(folderChooser.getSelectedFile().getAbsolutePath() + File.separator);
        }
    }//GEN-LAST:event_imagePathBrowseButtonActionPerformed

    private void headerFilesPathBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerFilesPathBrowseButtonActionPerformed
        if (folderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            headerFilesPathTextField.setText(folderChooser.getSelectedFile().getAbsolutePath() + File.separator);
        }
    }//GEN-LAST:event_headerFilesPathBrowseButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PreferencesDialog dialog = new PreferencesDialog(null, null);
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

    private boolean _imageLocationModified;
    private Preferences _preferences;
    private WorkspaceFrame _wsFrame;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox checkBoxEugeneRuleChecking;
    private javax.swing.JCheckBox checkBoxImageOutputBoxes;
    private javax.swing.JCheckBox checkBoxPropertiesDialogPopup;
    private javax.swing.JButton defaultsButton;
    private javax.swing.JFileChooser folderChooser;
    private javax.swing.JLabel headerFilesLocationLabel;
    private javax.swing.JButton headerFilesPathBrowseButton;
    private javax.swing.JTextField headerFilesPathTextField;
    private javax.swing.JLabel imageLocationLabel;
    private javax.swing.JButton imagePathBrowseButton;
    private javax.swing.JTextField imagePathTextField;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

}
