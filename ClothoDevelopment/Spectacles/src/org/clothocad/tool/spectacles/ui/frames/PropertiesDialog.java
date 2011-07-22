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
 * PropertiesDialog.java
 *
 * Created on Jul 13, 2009, 6:03:10 PM
 */

package org.clothocad.tool.spectacles.ui.frames;

import eugene.Primitive;
//import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
import javax.swing.JOptionPane;
//import org.clothocad.databaseio.Datum;
//import org.clothocad.tool.spectacles.Spectacles;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;
import org.clothocad.tool.spectacles.ui.scenes.WorkspaceScene;
//import org.clothocad.tool.spectacles.ui.scenes.SpectaclesFactory;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Part;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Collection;
//import org.openide.util.ImageUtilities;

/**
 * PropertiesDialog displays editable part properties.
 * @author Rich
 * @author Joanna
 */
public class PropertiesDialog extends javax.swing.JDialog {

    /**
     * Creates a new PropertiesDialog.
     */
    public PropertiesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        _wsFrame = (WorkspaceFrame) parent;
    }

    public String getPartObjectKeyword() {
        return _partObjectKeyword;
    }

    /**
     * Returns this dialog's parent frame.
     * @return the parent orkspaceFrame.
     */
    public WorkspaceFrame getWorkspaceFrame() {
        return _wsFrame;
    }

    /**
     * Given a wrapped part or device, shows this dialog.
     * Also sets this dialog's wrapper and WorkspaceScene.
     * @param dpw the wrapper.
     */
    public void showDialog(DevicePartWrapper dpw, WorkspaceScene wsScene) {
        _dpw = dpw;
        _wsScene = wsScene;
        setLocationRelativeTo(_wsFrame);
        setVisible(true);
    }

    /**
     * Sets this dialog's current DevicePartWrapper.
     * @param dpw the DevicePartWrapper.
     */
    public void setDevicePartWrapper(DevicePartWrapper dpw) {
        _dpw = dpw;
    }

    /**
     * Sets this dialog's WorkspaceScene.
     * @param wsScene the WorkspaceScene.
     */
    public void setWorkspaceScene(WorkspaceScene wsScene) {
        _wsScene = wsScene;
    }

    @Override
    public void setVisible(boolean visible) {
        if (!visible) {
            super.setVisible(visible);
        } else if (_wsScene != null && _dpw != null && _dpw.isPart()) {
            // load in properties here
            HashMap<String, Primitive> properties = _dpw.getPartPropertyValues();

            nameTextField.setEditable(true);
            if (properties.containsKey("Feature_Name")) {
                nameTextField.setText((String) properties.get("Feature_Name").getValue());
            }
            
            sequenceTextArea.setEditable(true);
            if (properties.containsKey("Feature_Sequence")) {
                sequenceTextArea.setText((String) properties.get("Feature_Sequence").getValue());
                //sequenceTextArea.setCaretPosition(0);
            }

            partsNameField.setEditable(true);
            //partsNameField.setText(_dpw.getName());
            if (properties.containsKey("Part_Name")){
                partsNameField.setText((String)properties.get("Part_Name").getValue());
            }

            partsDescriptionField.setEditable(true);
            if (properties.containsKey("Part_Description")){
                partsDescriptionField.setText((String)properties.get("Part_Description").getValue());
            }

            partsFormatField.setEditable(true);
            if (properties.containsKey("Part_Format")){
                partsFormatField.setText((String)properties.get("Part_Format").getValue());
            }

            partsSequenceArea.setEditable(true);
            if (properties.containsKey("Part_Sequence")){
                partsSequenceArea.setText((String)properties.get("Part_Sequence").getValue());
            }

            comppartNameField.setEditable(true);
            /*if (properties.containsKey("Composite Part Name")){
                comppartNameField.setText((String)properties.get("Composite Part Name").getValue());
            }*/
            
            comppartLefty.setEditable(true);
            /*if (properties.containsKey("Lefty Part")){
                comppartLefty.setText((String)properties.get("Lefty Part").getValue());
            }*/

            comppartRighty.setEditable(true);
            /*if (properties.containsKey("Righty Part")){
                comppartRighty.setText((String)properties.get("Righty Part").getValue());
            }
             *
             */

            nameTextField1.setEditable(true);
            sequenceTextArea1.setEditable(true);
            partsNameField1.setEditable(true);
            partsSequenceArea1.setEditable(true);

            if (partsDescriptionField.getText().equalsIgnoreCase("Composite Part") == true) {
                nameTextField.setEditable(false);
                sequenceTextArea.setEditable(false);
                nameTextField1.setEditable(false);
                sequenceTextArea1.setEditable(false);
            }

            /*if (!Spectacles.isRunningSolo()) { // and if database connection exists??
                if (_partObjectKeyword == null) { // set the default keywords if they are not set
                    _partObjectKeyword = "biobrick";
                    _secondaryObjectKeyword = "family";
                    _typeFieldKeyword = "name";
                    _displayByKeyword = "name";
                }
            }*/
            super.setVisible(visible);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        sequenceLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        sequenceScrollPane = new javax.swing.JScrollPane();
        sequenceTextArea = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        partsDescriptionField = new javax.swing.JTextField();
        partsFormatField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        partsSequenceArea = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comppartNameField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comppartLefty = new javax.swing.JTextField();
        partsNameField = new javax.swing.JTextField();
        comppartRighty = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nameLabel1 = new javax.swing.JLabel();
        sequenceLabel1 = new javax.swing.JLabel();
        nameTextField1 = new javax.swing.JTextField();
        sequenceScrollPane1 = new javax.swing.JScrollPane();
        sequenceTextArea1 = new javax.swing.JTextArea();
        searchButton1 = new javax.swing.JButton();
        assignButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        partsNameField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        partsSequenceArea1 = new javax.swing.JTextArea();
        searchButton2 = new javax.swing.JButton();
        assignButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.title")); // NOI18N
        setBounds(new java.awt.Rectangle(0, 22, 1025, 570));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("PropertiesForm"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1025, 570));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        nameLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.nameLabel.text_1")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        sequenceLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.sequenceLabel.text_1")); // NOI18N
        sequenceLabel.setName("sequenceLabel"); // NOI18N

        nameTextField.setEditable(false);
        nameTextField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.nameTextField.text")); // NOI18N
        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        sequenceScrollPane.setName("sequenceScrollPane"); // NOI18N

        sequenceTextArea.setColumns(20);
        sequenceTextArea.setEditable(false);
        sequenceTextArea.setLineWrap(true);
        sequenceTextArea.setRows(5);
        sequenceTextArea.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.sequenceTextArea.text")); // NOI18N
        sequenceTextArea.setName("sequenceTextArea"); // NOI18N
        sequenceScrollPane.setViewportView(sequenceTextArea);

        saveButton.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N
        saveButton.setVerifyInputWhenFocusTarget(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel1.text_1")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsNameLabel.text")); // NOI18N
        jLabel2.setName("partsNameLabel"); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.Label.text")); // NOI18N
        jLabel3.setName("Parts.Label"); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.Sequence.text")); // NOI18N
        jLabel6.setName("Parts.Sequence"); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        partsDescriptionField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsDescriptionField.text")); // NOI18N
        partsDescriptionField.setName("partsDescriptionField"); // NOI18N
        partsDescriptionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsDescriptionFieldActionPerformed(evt);
            }
        });

        partsFormatField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsFormatField.text")); // NOI18N
        partsFormatField.setName("partsFormatField"); // NOI18N
        partsFormatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsFormatFieldActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        partsSequenceArea.setColumns(20);
        partsSequenceArea.setRows(5);
        partsSequenceArea.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsSequenceArea.text")); // NOI18N
        partsSequenceArea.setName("partsSequenceArea"); // NOI18N
        jScrollPane1.setViewportView(partsSequenceArea);

        jLabel8.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.Type.text")); // NOI18N
        jLabel8.setName("Parts.Type"); // NOI18N

        jSeparator3.setName("jSeparator3"); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        comppartNameField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.comppartNameField.text")); // NOI18N
        comppartNameField.setName("comppartNameField"); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        comppartLefty.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.comppartLefty.text")); // NOI18N
        comppartLefty.setName("comppartLefty"); // NOI18N
        comppartLefty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comppartLeftyActionPerformed(evt);
            }
        });

        partsNameField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsNameField.text")); // NOI18N
        partsNameField.setName("partsNameField"); // NOI18N
        partsNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsNameFieldActionPerformed(evt);
            }
        });

        comppartRighty.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.comppartRighty.text")); // NOI18N
        comppartRighty.setName("comppartRighty"); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        nameLabel1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.nameLabel1.text")); // NOI18N
        nameLabel1.setName("nameLabel1"); // NOI18N

        sequenceLabel1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.sequenceLabel1.text")); // NOI18N
        sequenceLabel1.setName("sequenceLabel1"); // NOI18N

        nameTextField1.setEditable(false);
        nameTextField1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.nameTextField1.text")); // NOI18N
        nameTextField1.setName("nameTextField1"); // NOI18N
        nameTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextField1ActionPerformed(evt);
            }
        });

        sequenceScrollPane1.setName("sequenceScrollPane1"); // NOI18N

        sequenceTextArea1.setColumns(20);
        sequenceTextArea1.setEditable(false);
        sequenceTextArea1.setLineWrap(true);
        sequenceTextArea1.setRows(5);
        sequenceTextArea1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.sequenceTextArea1.text")); // NOI18N
        sequenceTextArea1.setName("sequenceTextArea1"); // NOI18N
        sequenceScrollPane1.setViewportView(sequenceTextArea1);

        searchButton1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.searchButton1.text")); // NOI18N
        searchButton1.setName("searchButton1"); // NOI18N
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });

        assignButton1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.assignButton1.text")); // NOI18N
        assignButton1.setName("assignButton1"); // NOI18N
        assignButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignButton1ActionPerformed(evt);
            }
        });

        jLabel11.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        partsNameField1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsNameField1.text")); // NOI18N
        partsNameField1.setName("partsNameField1"); // NOI18N
        partsNameField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsNameField1ActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        partsSequenceArea1.setColumns(20);
        partsSequenceArea1.setRows(5);
        partsSequenceArea1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsSequenceArea1.text")); // NOI18N
        partsSequenceArea1.setName("partsSequenceArea1"); // NOI18N
        jScrollPane2.setViewportView(partsSequenceArea1);

        searchButton2.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.searchButton2.text")); // NOI18N
        searchButton2.setName("searchButton2"); // NOI18N
        searchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton2ActionPerformed(evt);
            }
        });

        assignButton2.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.assignButton2.text")); // NOI18N
        assignButton2.setName("assignButton2"); // NOI18N
        assignButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameLabel)
                                    .addComponent(sequenceLabel))
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sequenceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel6)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comppartNameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addComponent(partsDescriptionField, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addComponent(partsFormatField, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addComponent(partsNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(comppartLefty, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                                .addGap(2, 2, 2))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(comppartRighty)
                                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1213, 1213, 1213)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(sequenceLabel1)
                                            .addComponent(nameLabel1)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel12))))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addComponent(sequenceScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(partsNameField1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(searchButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(assignButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(searchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(assignButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(709, 709, 709))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, saveButton});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nameTextField, nameTextField1, sequenceScrollPane1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sequenceScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sequenceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1))
                            .addComponent(sequenceLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(partsNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(partsDescriptionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(partsNameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sequenceScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchButton1)
                            .addComponent(assignButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(partsFormatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comppartNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comppartRighty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comppartLefty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(saveButton)
                                    .addComponent(cancelButton)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchButton2)
                            .addComponent(assignButton2))))
                .addGap(73, 73, 73))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        _dpw = null;
        _wsScene = null;

        //Set default dialogue texts as blank
        nameTextField.setEditable(false);
        nameTextField.setText("");
        sequenceTextArea.setEditable(false);
        sequenceTextArea.setText("");
        partsNameField.setEditable(false);
        partsNameField.setText("");
        partsFormatField.setEditable(false);
        partsFormatField.setText("");
        partsDescriptionField.setEditable(false);
        partsDescriptionField.setText("");
        partsSequenceArea.setEditable(false);
        partsSequenceArea.setText("");
        comppartNameField.setEditable(false);
        comppartNameField.setText("");
        comppartLefty.setEditable(false);
        comppartLefty.setText("");
        comppartRighty.setEditable(false);
        comppartRighty.setText("");
        nameTextField1.setEditable(false);
        nameTextField1.setText("");
        sequenceTextArea1.setEditable(false);
        sequenceTextArea1.setText("");
        partsNameField1.setEditable(false);
        partsNameField1.setText("");
        partsSequenceArea1.setEditable(false);
        partsSequenceArea1.setText("");
    }//GEN-LAST:event_formWindowClosed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        //Get part, feaute, composite pare names from the workspace
        String pname = partsNameField.getText();
        String fname = nameTextField.getText();
        String cname = comppartNameField.getText();
        renamePart();
        
        //Collect feature information from text fields
        String featname = nameTextField.getText();
        String featseq = sequenceTextArea.getText();

        //Collect part information from text fields
        String name = partsNameField.getText();
        String description = partsDescriptionField.getText();
        String sequence = partsSequenceArea.getText();
        Format form = Format.retrieveByName(partsFormatField.getText());

        HashMap<String, Primitive> properties = _dpw.getPartPropertyValues();

        //********* Create Feature using Clothocore method *********//

        if (featname.isEmpty() == false) {

            _feature = org.clothocore.api.data.Feature.generateFeature(featname, featseq, Collector.getCurrentUser(), false);
            //_feature.saveDefault();

            //Feature property entry fields

            //Clear properties if missing part info
            if (name.isEmpty() == true || sequence.isEmpty() == true) {
                //properties.clear();
                partsSequenceArea.setText("");
                sequence = "";
            }

            Primitive sequencePrimitive = new Primitive("Feature_Sequence", "txt");
            sequencePrimitive.txt = sequenceTextArea.getText();
            properties.put("Feature_Sequence", sequencePrimitive);

            Primitive namePrimitive = new Primitive("Feature_Name", "txt");
            namePrimitive.txt = nameTextField.getText();
            properties.put("Feature_Name", namePrimitive);

            //Add feature to collection
            Person user = Collector.getCurrentUser();
            _Collection = user.getHerCollection();
            _Collection.addObject(_feature);

            updateImageColor();
        }

        //********* Create Part using Clothocore method *********//

        if (name.isEmpty() == false) {

            _part = org.clothocore.api.data.Part.generateBasic(name, description, sequence, form, Collector.getCurrentUser());

            if (_part == null){
                JOptionPane.showMessageDialog(this,
                    "Something was wrong with your part, it was rejected.  Check that the sequence obeys the Format.",
                    "Part Failed!",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            //_part.saveDefault();

            //Part property entry fields

            //Clear properties if missing feature info
            if (featname.isEmpty() == true || featseq.isEmpty() == true) {
                //properties.clear();
            }

            Primitive partsName = new Primitive("Part_Name", "txt");
            partsName.txt = partsNameField.getText();
            properties.put("Part_Name", partsName);

            Primitive partsDesc = new Primitive("Part_Description", "txt");
            partsDesc.txt = partsDescriptionField.getText();
            properties.put("Part_Description", partsDesc);

            Primitive partsFormat = new Primitive("Part_Format", "txt");
            partsFormat.txt = _part.getFormat().toString();
            //partsFormat.txt = partsFormatField.getText();
            properties.put("Part_Format", partsFormat);

            Primitive partsSequence = new Primitive("Part_Sequence", "txt");
            partsSequence.txt = _part.getSeq().toString();
            //partsSequence.txt = partsSequenceArea.getText();
            properties.put("Part_Sequence", partsSequence);

            //Add part to collection
            Person user = Collector.getCurrentUser();
            _Collection = user.getHerCollection();
            _Collection.addObject(_part);

            updateImageColor();
        }

        //********* Create Composite Part using Clothocore method *********//

        if (cname.isEmpty() == false) {

            //Collect composite part information from text fields
            String cpartname = comppartNameField.getText();
            Part lefty = Part.retrieveByName(comppartLefty.getText());
            Part righty = Part.retrieveByName(comppartRighty.getText());
            Format cpartform = lefty.getFormat();

            //Create array list of two parts
            ArrayList<Part> composition = new ArrayList<Part>();
            composition.add(lefty);
            composition.add(righty);

            //Generate the composite part. This 'composite part' still has the same properties as 'part'
            _compPart = Part.generateComposite(composition, null, cpartform, Collector.getCurrentUser(), cpartname, "Composite Part");

            if (_compPart == null){
                JOptionPane.showMessageDialog(this,
                    "Something was wrong with your part, it was rejected.  Perhaps these parts cannot be composed together...",
                    "Composite Part Failed!",
                    JOptionPane.ERROR_MESSAGE);
            }

            //_compPart.saveDefault();

            //Adding this 'part' information to its properties
            properties.clear();

            Primitive partsName = new Primitive("Part_Name", "txt");
            partsName.txt = comppartNameField.getText();
            properties.put("Part_Name", partsName);

            Primitive partsDesc = new Primitive("Part_Description", "txt");
            partsDesc.txt = "Composite Part";
            properties.put("Part_Description", partsDesc);

            Primitive partsFormat = new Primitive("Part_Format", "txt");
            partsFormat.txt = cpartform.toString();
            properties.put("Part_Format", partsFormat);

            Primitive partsSequence = new Primitive("Part_Sequence", "txt");
            partsSequence.txt = _compPart.getSeq().toString();
            properties.put("Part_Sequence", partsSequence);

            //Composite Part property entry fields

            Primitive cpartLeftyPrimitive = new Primitive("Lefty_Part", "txt");
            cpartLeftyPrimitive.txt = comppartLefty.getText();
            properties.put("Lefty_Part", cpartLeftyPrimitive);

            Primitive cpartRightyPrimitive = new Primitive("Righty_Part", "txt");
            cpartRightyPrimitive.txt = comppartRighty.getText();
            properties.put("Righty_Part", cpartRightyPrimitive);

            //Add part to collection
            Person user = Collector.getCurrentUser();
            _Collection = user.getHerCollection();
            _Collection.addObject(_compPart);

            //Change icon image, re-name
            _wsFrame.renamePart(_dpw, comppartNameField.getText());
            _dpw.setImagePath("src/org/clothocad/tool/spectacles/partsimages/VBOL.Compositepart.png");
            _wsScene.hideContents();
            _wsScene.showContents();
        }

        //Saving operations
        _wsFrame.displayProperties();
        _wsScene.validate();
        _wsFrame.setModified(true);
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    public Part getPart() {
        return _part;
    }

    public Part getCompPart() {
        return _compPart;
    }

    public ObjBase getObjBase() {
        if (_feature != null && _part == null) {
            return _feature;
        } else if (_part != null) {
            return _part;
        } else {
            return null;
        }
    }

    public void updateImageColor() {

        //Update the color of the background image for parts, features, composites
        String featseq = sequenceTextArea.getText();
        String sequence = partsSequenceArea.getText();
        String imfirst = _dpw.getImagePath();
        int impathlength = imfirst.length();

        if (imfirst.substring(impathlength - 8).equalsIgnoreCase("part.png") == true) {
            String imtype = imfirst.substring(51, (imfirst.length() - 8));
            changeColor(featseq, sequence, imtype);
        } else if (imfirst.substring(impathlength - 8).equalsIgnoreCase("feat.png") == true) {
            String imtype = imfirst.substring(51, (imfirst.length() - 8));
            changeColor(featseq, sequence, imtype);
        } else {
            String imtype = imfirst.substring(51, (imfirst.length() - 4));
            changeColor(featseq, sequence, imtype);
        }

    }

    private void changeColor(String featseq, String sequence, String imtype) {

        //Change color of background image given image type, part seq area and feature seq area
        if (sequence.isEmpty() == false){
            //If there is a part sequence
            _wsScene.hideContents();
            _dpw.setImagePath("src/org/clothocad/tool/spectacles/partsimages/VBOL." + imtype + "part.png");
            _wsScene.showContents();
        } else if (sequence.isEmpty() == true && featseq.isEmpty() == false) {
            //If there is a feature sequences, but no part sequence
            _wsScene.hideContents();
            _dpw.setImagePath("src/org/clothocad/tool/spectacles/partsimages/VBOL." + imtype + "feat.png");
            _wsScene.showContents();
        } else {
            //If there is not part or feature sequence
            _wsScene.hideContents();
            _dpw.setImagePath("src/org/clothocad/tool/spectacles/partsimages/VBOL." + imtype + ".png");
            _wsScene.showContents();
        }

    }

    private void renamePart() {

        //Rename object based upon feautre, part, or composite part names
        String pname = partsNameField.getText();
        String fname = nameTextField.getText();
        String cname = comppartNameField.getText();

        if (pname.isEmpty() == false) {
            _wsFrame.renamePart(_dpw, partsNameField.getText());
        } else if (pname.isEmpty() == true && fname.isEmpty() == false) {
            _wsFrame.renamePart(_dpw, nameTextField.getText());
        } else {
            //_wsFrame.renamePart(_dpw, "_" + _dpw.getType() + "_");
        }

    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void partsDescriptionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsDescriptionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsDescriptionFieldActionPerformed

    private void partsNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsNameFieldActionPerformed

    private void comppartLeftyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comppartLeftyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comppartLeftyActionPerformed

    private void partsFormatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsFormatFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsFormatFieldActionPerformed

    private void nameTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextField1ActionPerformed

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed

        //Take information from search name and search database for Feature with that name
        String fsname = nameTextField1.getText();
        if (fsname.isEmpty() == false) {
            _sfeature = Feature.retrieveByName(nameTextField1.getText());
            if (_sfeature == null) {
                JOptionPane.showMessageDialog(this, "No such Feature exists!", "Try another Feature name",
                JOptionPane.ERROR_MESSAGE);
            } else {
            sequenceTextArea1.setText(_sfeature.getSeq().toString());
            }
        }

    }//GEN-LAST:event_searchButton1ActionPerformed

    private void assignButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignButton1ActionPerformed

        HashMap<String, Primitive> properties = _dpw.getPartPropertyValues();

        //Assign information from Feature search to the Feature data entry
        nameTextField.setText(nameTextField1.getText());
        nameTextField1.setText("");
        sequenceTextArea.setText(sequenceTextArea1.getText());
        sequenceTextArea1.setText("");
        renamePart();

        Primitive sequencePrimitive = new Primitive("Feature_Sequence", "txt");
        sequencePrimitive.txt = _sfeature.getSeq().toString();
        properties.put("Feature_Sequence", sequencePrimitive);

        Primitive namePrimitive = new Primitive("Feature_Name", "txt");
        namePrimitive.txt = _sfeature.getName();
        properties.put("Feature_Name", namePrimitive);

        updateImageColor();
        _wsFrame.displayProperties();
        _wsScene.validate();
        _wsFrame.setModified(true);
        dispose();

    }//GEN-LAST:event_assignButton1ActionPerformed

    private void partsNameField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsNameField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsNameField1ActionPerformed

    private void searchButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton2ActionPerformed
        
        //Take information from search name and search database for Part with that name
        String psname = partsNameField1.getText();
        if (psname.isEmpty() == false) {
            _spart = Part.retrieveByName(partsNameField1.getText());
            if (_spart == null) {
                JOptionPane.showMessageDialog(this, "No such Part exists!", "Try another Part name",
                JOptionPane.ERROR_MESSAGE);
            } else {
            partsSequenceArea1.setText(_spart.getSeq().toString());
            }
        }
        
    }//GEN-LAST:event_searchButton2ActionPerformed

    private void assignButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignButton2ActionPerformed

        HashMap<String, Primitive> properties = _dpw.getPartPropertyValues();

        //Assign information from Feature search to the Feature data entry
        partsNameField.setText(partsNameField1.getText());
        partsNameField1.setText("");
        partsSequenceArea.setText(partsSequenceArea1.getText());
        partsSequenceArea1.setText("");
        renamePart();

        //Part property entry fields
        Primitive partsName = new Primitive("Part_Name", "txt");
        partsName.txt = _spart.getName();
        properties.put("Part_Name", partsName);

        Primitive partsDesc = new Primitive("Part_Description", "txt");
        partsDesc.txt = _spart.getShortDescription();
        properties.put("Part_Description", partsDesc);

        Primitive partsFormat = new Primitive("Part_Format", "txt");
        partsFormat.txt = _spart.getFormat().toString();
        properties.put("Part_Format", partsFormat);

        Primitive partsSequence = new Primitive("Part_Sequence", "txt");
        partsSequence.txt = _spart.getSeq().toString();
        properties.put("Part_Sequence", partsSequence);

        updateImageColor();
        _wsFrame.displayProperties();
        _wsScene.validate();
        _wsFrame.setModified(true);
        dispose();

    }//GEN-LAST:event_assignButton2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PropertiesDialog dialog = new PropertiesDialog(new javax.swing.JFrame(), true);
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

    private Collection _Collection = null;
    private Feature _feature = null;
    private Feature _sfeature = null;
    private Part _part = null;
    private Part _spart = null;
    private Part _compPart = null;
    private DevicePartWrapper _dpw;
    private WorkspaceFrame _wsFrame;
    private WorkspaceScene _wsScene;

    private String _partObjectKeyword;
    //private String _secondaryObjectKeyword;
    //private String _typeFieldKeyword;
    //private String _displayByKeyword;
    //private final String _noneKeyword = "none";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton assignButton1;
    private javax.swing.JButton assignButton2;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField comppartLefty;
    private javax.swing.JTextField comppartNameField;
    private javax.swing.JTextField comppartRighty;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameLabel1;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField nameTextField1;
    private javax.swing.JTextField partsDescriptionField;
    private javax.swing.JTextField partsFormatField;
    private javax.swing.JTextField partsNameField;
    private javax.swing.JTextField partsNameField1;
    private javax.swing.JTextArea partsSequenceArea;
    private javax.swing.JTextArea partsSequenceArea1;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton searchButton1;
    private javax.swing.JButton searchButton2;
    private javax.swing.JLabel sequenceLabel;
    private javax.swing.JLabel sequenceLabel1;
    private javax.swing.JScrollPane sequenceScrollPane;
    private javax.swing.JScrollPane sequenceScrollPane1;
    private javax.swing.JTextArea sequenceTextArea;
    private javax.swing.JTextArea sequenceTextArea1;
    // End of variables declaration//GEN-END:variables
}