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
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
// import org.clothocad.databaseio.Datum;
import org.clothocad.tool.spectacles.Spectacles;
import org.clothocad.tool.spectacles.eugeneimportexport.DevicePartWrapper;
import org.clothocad.tool.spectacles.ui.scenes.WorkspaceScene;

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
            nameTextField.setText(_dpw.getName());
            typeTextField.setText(_dpw.getType());
            
            

            if (properties.containsKey("Orientation")) {
                //orientationTextField.setText((String) properties.get("Orientation").getValue());
                orientationComboBox.setSelectedItem(properties.get("Orientation").getValue());
            }
            // idTextField.setEditable(true);  // no longer allow editing of ID field as info comes from database
            // may need to move these checks out to dpw methods
            if (properties.containsKey("ID")) {
                idTextField.setText((String) properties.get("ID").getValue());
            }
            sequenceTextArea.setEditable(true);
            if (properties.containsKey("Sequence")) {
                sequenceTextArea.setText((String) properties.get("Sequence").getValue());
                sequenceTextArea.setCaretPosition(0);
            }

            if (properties.containsKey("partsName")){
                partsNameField.setText((String)properties.get("partsName").getValue());
            }

            if (properties.containsKey("partsType")){
                partsTypeField.setText((String)properties.get("partsType").getValue());
            }

            if (properties.containsKey("partsLabel")){
                partsLabelField.setText((String)properties.get("partsType").getValue());
            }

            if (properties.containsKey("partsID")){
                partsIDField.setText((String)properties.get("partsID").getValue());
            }

            if (properties.containsKey("partsSequence")){
                partsSequenceArea.setText((String)properties.get("partsSequence").getValue());
            }

            
            if (!Spectacles.isRunningSolo()) { // and if database connection exists??
                if (_partObjectKeyword == null) { // set the default keywords if they are not set
                    _partObjectKeyword = "biobrick";
                    _secondaryObjectKeyword = "family";
                    _typeFieldKeyword = "name";
                    _displayByKeyword = "name";
                }
                partObjectKeywordComboBox.setEnabled(true);
                populatePartObjectKeywordComboBox();
                // the rest of the combo boxes will populate in response to listeners on the partObjectKeywordComboBox
                termToMatchTextField.setEditable(true);
                termToMatchTextField.setText(_dpw.getType());
            }
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
        typeLabel = new javax.swing.JLabel();
        orientationLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        sequenceLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        headerLabel = new javax.swing.JLabel();
        partObjectKeywordLabel = new javax.swing.JLabel();
        secondaryObjectKeywordLabel = new javax.swing.JLabel();
        typeFieldKeywordLabel = new javax.swing.JLabel();
        termToMatchLabel = new javax.swing.JLabel();
        displayByKeywordLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        implementationLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        typeTextField = new javax.swing.JTextField();
        orientationComboBox = new javax.swing.JComboBox();
        idTextField = new javax.swing.JTextField();
        sequenceScrollPane = new javax.swing.JScrollPane();
        sequenceTextArea = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        partObjectKeywordComboBox = new javax.swing.JComboBox();
        secondaryObjectKeywordComboBox = new javax.swing.JComboBox();
        typeFieldKeywordComboBox = new javax.swing.JComboBox();
        termToMatchTextField = new javax.swing.JTextField();
        displayByKeywordComboBox = new javax.swing.JComboBox();
        getImplementationsButton = new javax.swing.JButton();
        implementationComboBox = new javax.swing.JComboBox();
        getSequenceButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        partsTypeField = new javax.swing.JTextField();
        partsLabelField = new javax.swing.JTextField();
        partsIDField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        partsSequenceArea = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        partsNameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.title")); // NOI18N
        setAlwaysOnTop(true);
        setName("PropertiesForm"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        nameLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        typeLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.typeLabel.text")); // NOI18N
        typeLabel.setName("typeLabel"); // NOI18N

        orientationLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.orientationLabel.text")); // NOI18N
        orientationLabel.setName("orientationLabel"); // NOI18N

        idLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.idLabel.text_1")); // NOI18N
        idLabel.setName("idLabel"); // NOI18N

        sequenceLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.sequenceLabel.text")); // NOI18N
        sequenceLabel.setName("sequenceLabel"); // NOI18N

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setName("jSeparator1"); // NOI18N

        headerLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.headerLabel.text")); // NOI18N
        headerLabel.setName("headerLabel"); // NOI18N

        partObjectKeywordLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partObjectKeywordLabel.text")); // NOI18N
        partObjectKeywordLabel.setName("partObjectKeywordLabel"); // NOI18N

        secondaryObjectKeywordLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.secondaryObjectKeywordLabel.text")); // NOI18N
        secondaryObjectKeywordLabel.setName("secondaryObjectKeywordLabel"); // NOI18N

        typeFieldKeywordLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.typeFieldKeywordLabel.text")); // NOI18N
        typeFieldKeywordLabel.setName("typeFieldKeywordLabel"); // NOI18N

        termToMatchLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.termToMatchLabel.text")); // NOI18N
        termToMatchLabel.setName("termToMatchLabel"); // NOI18N

        displayByKeywordLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.displayByKeywordLabel.text")); // NOI18N
        displayByKeywordLabel.setName("displayByKeywordLabel"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        implementationLabel.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.implementationLabel.text")); // NOI18N
        implementationLabel.setName("implementationLabel"); // NOI18N

        nameTextField.setEditable(false);
        nameTextField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.nameTextField.text")); // NOI18N
        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        typeTextField.setEditable(false);
        typeTextField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.typeTextField.text")); // NOI18N
        typeTextField.setName("typeTextField"); // NOI18N
        typeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeTextFieldActionPerformed(evt);
            }
        });

        orientationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Forward", "Reverse", "Bidirectional" }));
        orientationComboBox.setEnabled(false);
        orientationComboBox.setName("orientationComboBox"); // NOI18N

        idTextField.setEditable(false);
        idTextField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.idTextField.text")); // NOI18N
        idTextField.setName("idTextField"); // NOI18N

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

        partObjectKeywordComboBox.setEnabled(false);
        partObjectKeywordComboBox.setName("partObjectKeywordComboBox"); // NOI18N
        partObjectKeywordComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                partObjectKeywordComboBoxItemStateChanged(evt);
            }
        });

        secondaryObjectKeywordComboBox.setEnabled(false);
        secondaryObjectKeywordComboBox.setName("secondaryObjectKeywordComboBox"); // NOI18N
        secondaryObjectKeywordComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                secondaryObjectKeywordComboBoxItemStateChanged(evt);
            }
        });

        typeFieldKeywordComboBox.setEnabled(false);
        typeFieldKeywordComboBox.setName("typeFieldKeywordComboBox"); // NOI18N
        typeFieldKeywordComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeFieldKeywordComboBoxItemStateChanged(evt);
            }
        });

        termToMatchTextField.setEditable(false);
        termToMatchTextField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.termToMatchTextField.text")); // NOI18N
        termToMatchTextField.setName("termToMatchTextField"); // NOI18N
        termToMatchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                termToMatchTextFieldActionPerformed(evt);
            }
        });

        displayByKeywordComboBox.setEnabled(false);
        displayByKeywordComboBox.setName("displayByKeywordComboBox"); // NOI18N
        displayByKeywordComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                displayByKeywordComboBoxItemStateChanged(evt);
            }
        });

        getImplementationsButton.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.getImplementationsButton.text")); // NOI18N
        getImplementationsButton.setEnabled(false);
        getImplementationsButton.setName("getImplementationsButton"); // NOI18N
        getImplementationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getImplementationsButtonActionPerformed(evt);
            }
        });

        implementationComboBox.setEnabled(false);
        implementationComboBox.setName("implementationComboBox"); // NOI18N

        getSequenceButton.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.getSequenceButton.text")); // NOI18N
        getSequenceButton.setEnabled(false);
        getSequenceButton.setName("getSequenceButton"); // NOI18N
        getSequenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSequenceButtonActionPerformed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel1.text_1")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsNameLabel.text")); // NOI18N
        jLabel2.setName("partsNameLabel"); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.Label.text")); // NOI18N
        jLabel3.setName("Parts.Label"); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.DatabaseID.text")); // NOI18N
        jLabel5.setName("Parts.DatabaseID"); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.Sequence.text")); // NOI18N
        jLabel6.setName("Parts.Sequence"); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        partsTypeField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsTypeField.text")); // NOI18N
        partsTypeField.setName("partsTypeField"); // NOI18N
        partsTypeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsTypeFieldActionPerformed(evt);
            }
        });

        partsLabelField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsLabelField.text")); // NOI18N
        partsLabelField.setName("partsLabelField"); // NOI18N

        partsIDField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsIDField.text")); // NOI18N
        partsIDField.setName("partsIDField"); // NOI18N
        partsIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsIDFieldActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        partsSequenceArea.setColumns(20);
        partsSequenceArea.setRows(5);
        partsSequenceArea.setName("partsSequenceArea"); // NOI18N
        jScrollPane1.setViewportView(partsSequenceArea);

        jLabel8.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.Parts.Type.text")); // NOI18N
        jLabel8.setName("Parts.Type"); // NOI18N

        partsNameField.setText(org.openide.util.NbBundle.getMessage(PropertiesDialog.class, "PropertiesDialog.partsNameField.text")); // NOI18N
        partsNameField.setName("partsNameField"); // NOI18N
        partsNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partsNameFieldActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(typeLabel)
                            .addComponent(orientationLabel)
                            .addComponent(idLabel)
                            .addComponent(sequenceLabel)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(partsIDField, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                            .addComponent(sequenceScrollPane, 0, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(idTextField)
                                    .addComponent(orientationComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(typeTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(partsLabelField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                    .addComponent(saveButton, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(partsTypeField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(partsNameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(implementationLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                                .addComponent(implementationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(partObjectKeywordLabel)
                                    .addComponent(secondaryObjectKeywordLabel)
                                    .addComponent(typeFieldKeywordLabel)
                                    .addComponent(termToMatchLabel)
                                    .addComponent(displayByKeywordLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(partObjectKeywordComboBox, 0, 393, Short.MAX_VALUE)
                                    .addComponent(typeFieldKeywordComboBox, 0, 393, Short.MAX_VALUE)
                                    .addComponent(termToMatchTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                                    .addComponent(displayByKeywordComboBox, 0, 393, Short.MAX_VALUE)
                                    .addComponent(getImplementationsButton)
                                    .addComponent(secondaryObjectKeywordComboBox, 0, 393, Short.MAX_VALUE)))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)))
                    .addComponent(getSequenceButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orientationLabel)
                    .addComponent(orientationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sequenceScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sequenceLabel))
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(partsNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(partsTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(partsLabelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(partsIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addGap(42, 42, 42))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(partObjectKeywordComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(partObjectKeywordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondaryObjectKeywordComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secondaryObjectKeywordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeFieldKeywordComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeFieldKeywordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(termToMatchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(termToMatchLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayByKeywordComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(displayByKeywordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getImplementationsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(implementationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(implementationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getSequenceButton)
                .addContainerGap(356, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        _dpw = null;
        _wsScene = null;
        nameTextField.setEditable(false);
        nameTextField.setText("");
        typeTextField.setText("");
        //orientationTextField.setText("");
        idTextField.setEditable(false);
        idTextField.setText("");
        sequenceTextArea.setEditable(false);
        sequenceTextArea.setText("");
        partObjectKeywordComboBox.setEnabled(false);
        partObjectKeywordComboBox.removeAllItems();
        secondaryObjectKeywordComboBox.setEnabled(false);
        secondaryObjectKeywordComboBox.removeAllItems();
        typeFieldKeywordComboBox.setEnabled(false);
        typeFieldKeywordComboBox.removeAllItems();
        termToMatchTextField.setEditable(false);
        termToMatchTextField.setText("");
        displayByKeywordComboBox.setEnabled(false);
        displayByKeywordComboBox.removeAllItems();
        getImplementationsButton.setEnabled(false);
        implementationComboBox.setEnabled(false);
        implementationComboBox.removeAllItems();
        getSequenceButton.setEnabled(false);
    }//GEN-LAST:event_formWindowClosed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        _wsFrame.renamePart(_dpw, nameTextField.getText());
        HashMap<String, Primitive> properties = _dpw.getPartPropertyValues();
        Primitive idPrimitive = new Primitive("ID", "txt");
        idPrimitive.txt = idTextField.getText();
        properties.put("ID", idPrimitive);
        Primitive sequencePrimitive = new Primitive("Sequence", "txt");
        sequencePrimitive.txt = sequenceTextArea.getText();
        properties.put("Sequence", sequencePrimitive);

        //Parts
        Primitive partsName = new Primitive("partsName", "txt");
        partsName.txt = partsNameField.getText();
        properties.put("partsName", partsName);

        Primitive partsType = new Primitive("partsType", "txt");
        partsType.txt = partsTypeField.getText();
        properties.put("partsType", partsType);

        Primitive partsLabel = new Primitive("partsLabel", "txt");
        partsLabel.txt = partsLabelField.getText();
        properties.put("partsLabel", partsType);

        Primitive partsID = new Primitive("partsID", "txt");
        partsID.txt = partsIDField.getText();
        properties.put("partsID", partsID);

        Primitive partsSequence = new Primitive("partsSequence", "txt");
        partsSequence.txt = partsSequenceArea.getText();
        properties.put("partsSequence", partsSequence);
        
        _wsFrame.updateAssociatedWidgets(_dpw.getName());
        // store keywords for next setVisible()
        _partObjectKeyword = (String) partObjectKeywordComboBox.getSelectedItem();
        _secondaryObjectKeyword = (String) secondaryObjectKeywordComboBox.getSelectedItem();
        _typeFieldKeyword = (String) typeFieldKeywordComboBox.getSelectedItem();
        _displayByKeyword = (String) displayByKeywordComboBox.getSelectedItem();
        // assuming that only part properties will be displayed right now
        _wsFrame.displayProperties();
        _wsScene.validate();
        _wsFrame.setModified(true);
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void getImplementationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getImplementationsButtonActionPerformed
        getImplementations();
}//GEN-LAST:event_getImplementationsButtonActionPerformed

    private void getSequenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getSequenceButtonActionPerformed
        //String partObjKywd = (String) partObjectKeywordComboBox.getSelectedItem();
        //String implementation = (String) implementationComboBox.getSelectedItem();
        //String dispByKywd = (String) displayByKeywordComboBox.getSelectedItem();
        //System.out.println("from " + partObjKywd + " where " + dispByKywd + " = '" + implementation + "'");
        //Iterator<Datum> iter = Spectacles.getDataCore().findByFieldEquality(partObjKywd, dispByKywd, matchTerm);
        //Iterator<Datum> iter = Spectacles.getDataCore().findUsingHQL("from " + partObjKywd + " where " + dispByKywd + " = '" + implementation + "'");
        //if (iter.hasNext()) {
            //try {
        //        Datum partDatum = iter.next();
        //        sequenceTextArea.setText(partDatum.getFieldAsString("sequence"));
        //        sequenceTextArea.setCaretPosition(0);
        //        idTextField.setText(partDatum.getFieldAsInt("id") + ""); // use database's unique id as ID
            /*} catch (DataFormatException ex) {
                Exceptions.printStackTrace(ex);
            } catch (NoSuchFieldException ex) {
                Exceptions.printStackTrace(ex);
            }*/
        //}
}//GEN-LAST:event_getSequenceButtonActionPerformed

    private void partObjectKeywordComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_partObjectKeywordComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            secondaryObjectKeywordComboBox.setEnabled(true);
            secondaryObjectKeywordComboBox.removeAllItems();
            populateSecondaryObjectKeywordComboBox();
            displayByKeywordComboBox.setEnabled(true);
            displayByKeywordComboBox.removeAllItems();
            populateDisplayByKeywordComboBox();
        } else if (partObjectKeywordComboBox.getSelectedIndex() == -1) {
            secondaryObjectKeywordComboBox.setEnabled(false);
            typeFieldKeywordComboBox.setEnabled(false);
            displayByKeywordComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_partObjectKeywordComboBoxItemStateChanged

    private void secondaryObjectKeywordComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_secondaryObjectKeywordComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            typeFieldKeywordComboBox.setEnabled(true);
            typeFieldKeywordComboBox.removeAllItems();
            String secondaryKeyword = (String) secondaryObjectKeywordComboBox.getSelectedItem();
            if (secondaryKeyword.equals(_noneKeyword)) {
                populateTypeFieldKeywordComboBox((String) partObjectKeywordComboBox.getSelectedItem()); //fix!!!!!!!!!
            } else {
                populateTypeFieldKeywordComboBox(secondaryKeyword);
            }
        } else if (secondaryObjectKeywordComboBox.getSelectedIndex() == -1) {
            typeFieldKeywordComboBox.setEnabled(false);
            
        }
    }//GEN-LAST:event_secondaryObjectKeywordComboBoxItemStateChanged

    private void typeFieldKeywordComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeFieldKeywordComboBoxItemStateChanged
        if (typeFieldKeywordComboBox.getSelectedIndex() > -1
                && displayByKeywordComboBox.getSelectedIndex() > -1) {
            getImplementationsButton.setEnabled(true);
        } else {
            getImplementationsButton.setEnabled(false);
        }
    }//GEN-LAST:event_typeFieldKeywordComboBoxItemStateChanged

    private void displayByKeywordComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_displayByKeywordComboBoxItemStateChanged
        if (typeFieldKeywordComboBox.getSelectedIndex() > -1
                && displayByKeywordComboBox.getSelectedIndex() > -1) {
            getImplementationsButton.setEnabled(true);
        } else {
            getImplementationsButton.setEnabled(false);
        }
    }//GEN-LAST:event_displayByKeywordComboBoxItemStateChanged

    private void termToMatchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_termToMatchTextFieldActionPerformed
        getImplementations();
    }//GEN-LAST:event_termToMatchTextFieldActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void partsTypeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsTypeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsTypeFieldActionPerformed

    private void partsIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsIDFieldActionPerformed

    private void partsNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partsNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partsNameFieldActionPerformed

    private void typeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeTextFieldActionPerformed

    private void getImplementations() {
        implementationComboBox.setEnabled(true);
        implementationComboBox.removeAllItems();
        populateImplementationComboBox();
        getSequenceButton.setEnabled(true);
    }
    private void populatePartObjectKeywordComboBox() {
        //List<String> objNames = Spectacles.getDataCore().getBoundObjectNames();
        //int index = -1;
        //for (String kywd : objNames) {
            //System.out.println("__" + kywd + "__");
         //   partObjectKeywordComboBox.addItem(kywd);
         //   if (kywd.equals(_partObjectKeyword)) {
         //       index = partObjectKeywordComboBox.getItemCount() - 1;
                //System.out.println("part object keyword matched, index " + index);
         //   }
        //}
        //partObjectKeywordComboBox.setSelectedItem(_partObjectKeyword);  // set default keyword
        //partObjectKeywordComboBox.setSelectedIndex(index);
    }

    private void populateSecondaryObjectKeywordComboBox() {
        secondaryObjectKeywordComboBox.addItem(_noneKeyword);
        int index = -1;
        if (_noneKeyword.equals(_secondaryObjectKeyword)) {
            index = 0;
        }
        /*
        String[] objNames = Spectacles.getDataCore().getAdjacentDatumNames((String) partObjectKeywordComboBox.getSelectedItem());
        for (String kywd : objNames) {
            secondaryObjectKeywordComboBox.addItem(kywd);
            if (kywd.equals(_secondaryObjectKeyword)) {
                index = secondaryObjectKeywordComboBox.getItemCount() - 1;
            }
        }
         * */
         
        //secondaryObjectKeywordComboBox.setSelectedItem(_noneKeyword);  // set default keyword
        secondaryObjectKeywordComboBox.setSelectedIndex(index);
    }

    private void populateTypeFieldKeywordComboBox(String objKeyword) {
        /*
        String[] fieldNames = Spectacles.getDataCore().getLocalFields(objKeyword);
        int index = -1;
        for (String kywd : fieldNames) {
            //System.out.println("__" + kywd + "__");
            typeFieldKeywordComboBox.addItem(kywd);
            if (kywd.equals(_typeFieldKeyword)) {
                index = typeFieldKeywordComboBox.getItemCount() - 1;
                //System.out.println("type field keyword matched, index " + index);
            }
        }
         *
         */
        //typeFieldKeywordComboBox.setSelectedItem(_typeFieldKeyword);  // set default keyword
        //typeFieldKeywordComboBox.setSelectedIndex(index);
    }

    private void populateDisplayByKeywordComboBox() {
        /*
        String objKeyword = (String) partObjectKeywordComboBox.getSelectedItem();
        String[] fieldNames = Spectacles.getDataCore().getLocalFields(objKeyword);
        int index = -1;
        for (String kywd : fieldNames) {
            displayByKeywordComboBox.addItem(kywd);
            if (kywd.equals(_displayByKeyword)) {
                index = displayByKeywordComboBox.getItemCount() - 1;
                //System.out.println("display by keyword matched, index " + index);
            }
        }
         *
         */
        //displayByKeywordComboBox.setSelectedItem(_displayByKeyword);  // set default keyword*/
        //displayByKeywordComboBox.setSelectedIndex(index);

    }

    private void populateImplementationComboBox() {
        DevicePartWrapper mainWrapper = _wsFrame.getManager().getWrappedPart(_dpw.getName());
        mainWrapper.implementations = 0;
        String partObjKywd = (String) partObjectKeywordComboBox.getSelectedItem();
        String secondaryObjKywd = (String) secondaryObjectKeywordComboBox.getSelectedItem();
        String typeFieldKywd = (String) typeFieldKeywordComboBox.getSelectedItem();
        String matchTerm = termToMatchTextField.getText();
        String dispByKywd = (String) displayByKeywordComboBox.getSelectedItem();
        String queryStr;
        if (secondaryObjKywd.equals(_noneKeyword)) {
            queryStr = "from " + partObjKywd + " where " + typeFieldKywd + " like '%" + matchTerm + "%'";
        } else {
            queryStr = "from " + partObjKywd + " where " + secondaryObjKywd + "." + typeFieldKywd + " like '%" + matchTerm + "%'";
        }
        //Iterator<Datum> iter = Spectacles.getDataCore().findUsingHQL(queryStr);
        //while (iter.hasNext()) {
            //try {
         //       implementationComboBox.addItem(iter.next().getFieldAsString(dispByKywd));
          //      mainWrapper.implementations += 1;
            /*} catch (DataFormatException ex) {
                Exceptions.printStackTrace(ex);
            } catch (NoSuchFieldException ex) {
                Exceptions.printStackTrace(ex);
            }*/
        //}
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
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


    private DevicePartWrapper _dpw;
    private WorkspaceFrame _wsFrame;
    private WorkspaceScene _wsScene;

    private String _partObjectKeyword;
    private String _secondaryObjectKeyword;
    private String _typeFieldKeyword;
    private String _displayByKeyword;

    private final String _noneKeyword = "none";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox displayByKeywordComboBox;
    private javax.swing.JLabel displayByKeywordLabel;
    private javax.swing.JButton getImplementationsButton;
    private javax.swing.JButton getSequenceButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idTextField;
    private javax.swing.JComboBox implementationComboBox;
    private javax.swing.JLabel implementationLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JComboBox orientationComboBox;
    private javax.swing.JLabel orientationLabel;
    private javax.swing.JComboBox partObjectKeywordComboBox;
    private javax.swing.JLabel partObjectKeywordLabel;
    private javax.swing.JTextField partsIDField;
    private javax.swing.JTextField partsLabelField;
    private javax.swing.JTextField partsNameField;
    private javax.swing.JTextArea partsSequenceArea;
    private javax.swing.JTextField partsTypeField;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox secondaryObjectKeywordComboBox;
    private javax.swing.JLabel secondaryObjectKeywordLabel;
    private javax.swing.JLabel sequenceLabel;
    private javax.swing.JScrollPane sequenceScrollPane;
    private javax.swing.JTextArea sequenceTextArea;
    private javax.swing.JLabel termToMatchLabel;
    private javax.swing.JTextField termToMatchTextField;
    private javax.swing.JComboBox typeFieldKeywordComboBox;
    private javax.swing.JLabel typeFieldKeywordLabel;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JTextField typeTextField;
    // End of variables declaration//GEN-END:variables

}
