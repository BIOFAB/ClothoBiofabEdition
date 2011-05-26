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
 * spreadsheet.java
 *
 * Created on Apr 2, 2010, 8:19:03 PM
 */

package org.clothocad.tool.bulltrowell.view;

import java.awt.Color;
import org.clothocad.tool.bulltrowell.interpreters.interpreter;
import org.clothocore.util.basic.ImageSource;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author J. Christopher Anderson
 */
public class spreadsheet extends javax.swing.JFrame {

    /** Creates new form spreadsheet */
    public spreadsheet(Object[][] data, String[] title, interpreter interpret) {
        super("BullTrowell Importer");
        setIconImage(ImageSource.getTinyLogo());
        _data = data;
        _title = title;
        _interpreter = interpret;
        initComponents();
        setSize(800,650);
        clearAll();
        setVisible(true);
        ExcelAdapter myAd = new ExcelAdapter(theTable);
    }

    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        tableScroll = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        theTable = new JXTable();
        jComboBox1 = new javax.swing.JComboBox();
        submitButton = new javax.swing.JButton();
        comboLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        comboLabel2 = new javax.swing.JLabel();
        comboLabel3 = new javax.swing.JLabel();
        comboLabel4 = new javax.swing.JLabel();
        comboLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        titleArea = new javax.swing.JEditorPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();

        theTable.setSortable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cancelButton.setText("cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelPerformed();
            }});

        clearButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int numcols = theTable.getColumnCount();
                int numrow = theTable.getRowCount();
                for(int i=0; i<numrow; i++) {
                    for(int j=0; j<numcols; j++) {
                        theTable.setValueAt("",i, j);
                    }
                }
            }});

        theTable.setModel(new javax.swing.table.DefaultTableModel(_data, _title));

        jScrollPane1.setViewportView(theTable);

        tableScroll.setViewportView(jScrollPane1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        submitButton.setText("submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            returnData();}});

        jButton1.setText("jButton1");
        jButton2.setText("jButton2");
        jButton3.setText("jButton3");
        clearButton.setText("clear");
        jScrollPane3.setViewportView(titleArea);
        jScrollPane4.setViewportView(logArea);
        jButton4.setText("jButton4");

        titleArea.setOpaque(false);
        titleArea.setBorder(null);
        titleArea.setEditable(false);
        titleArea.setContentType("text/html; charset=EUC-JP");
        jScrollPane3.setOpaque(false);
        jScrollPane3.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboLabel1))
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboLabel2))
                                .addGap(63, 63, 63)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboLabel3))
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboLabel4))
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboLabel5)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)
                        .addGap(102, 102, 102))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboLabel1)
                    .addComponent(comboLabel2)
                    .addComponent(comboLabel3)
                    .addComponent(comboLabel4)
                    .addComponent(comboLabel5))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(cancelButton)
                    .addComponent(submitButton)
                    .addComponent(clearButton)))
        );

        pack();
    }// </editor-fold>

    private void clearAll() {
        jComboBox1.setVisible(false);
        jComboBox2.setVisible(false);
        jComboBox3.setVisible(false);
        jComboBox4.setVisible(false);
        jComboBox5.setVisible(false);
        comboLabel1.setVisible(false);
        comboLabel2.setVisible(false);
        comboLabel3.setVisible(false);
        comboLabel4.setVisible(false);
        comboLabel5.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton4.setVisible(false);
    }

    //Not sure how to implement this, and it might not work with substance
    public void highLightColumn(int columnIndex, Color theColor) {

    }

    public void putComboField1(String label, Object[] list, Object selected) {
        comboLabel1.setText(label);
        comboLabel1.setVisible(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(list));
        jComboBox1.setVisible(true);
        jComboBox1.setSelectedItem(selected);
    }

    public Object getComboField1() {
        return jComboBox1.getSelectedItem();
    }

    public void putComboField2(String label, Object[] list, Object selected) {
        comboLabel2.setText(label);
        comboLabel2.setVisible(true);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(list));
        jComboBox2.setVisible(true);
        jComboBox2.setSelectedItem(selected);
    }

    public Object getComboField2() {
        return jComboBox2.getSelectedItem();
    }

    public void putComboField3(String label, Object[] list, Object selected) {
        comboLabel3.setText(label);
        comboLabel3.setVisible(true);
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(list));
        jComboBox3.setVisible(true);
        jComboBox3.setSelectedItem(selected);
    }

    public Object getComboField3() {
        return jComboBox3.getSelectedItem();
    }

    public void putComboField4(String label, Object[] list, Object selected) {
        comboLabel4.setText(label);
        comboLabel4.setVisible(true);
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(list));
        jComboBox4.setVisible(true);
        jComboBox4.setSelectedItem(selected);
    }

    public Object getComboField4() {
        return jComboBox4.getSelectedItem();
    }

    public void putComboField5(String label, Object[] list, Object selected) {
        comboLabel5.setText(label);
        comboLabel5.setVisible(true);
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(list));
        jComboBox5.setVisible(true);
        jComboBox5.setSelectedItem(selected);
    }

    public Object getComboField5() {
        return jComboBox5.getSelectedItem();
    }

    public void appendLogMessage(String str) {
        String current = logArea.getText();
        logArea.setText(current.concat(str));
    }

    public void setTitleArea(String text) {
        titleArea.setText(text);
    }

    public void putHoverText(int row, int col, String message) {

    }

    private void returnData() {
        for(int i=0; i< _data.length; i++) {
            for(int j=0; j<_data[0].length; j++) {
                String cellValue = theTable.getModel().getValueAt(i, j).toString().trim();
                _data[i][j] = cellValue;
            }
        }
        _interpreter.receiveData(_data);
    }

    private void cancelPerformed() {
        _interpreter = null;
        System.out.println("Cancell button pressed");
        this.dispose();
    }

    public void refreshData(Object[][] data) {
        _data = data;
        theTable.setModel(new javax.swing.table.DefaultTableModel(_data, _title));
        theTable.repaint();
    }




    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        String[][] data = new String [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            };
        String[] titles = { "Nickname", "Short Description", "Sequence", "Family", "Format", "Author" };
        new spreadsheet(data, titles, null);
    }

/**-----------------
     variables
 -----------------*/
    // Variables declaration - do not modify
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel comboLabel1;
    private javax.swing.JLabel comboLabel2;
    private javax.swing.JLabel comboLabel3;
    private javax.swing.JLabel comboLabel4;
    private javax.swing.JLabel comboLabel5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton submitButton;
    private javax.swing.JScrollPane tableScroll;
    private JXTable theTable;
    private javax.swing.JEditorPane titleArea;
    private Object[][] _data;
    private String [] _title;
    private interpreter _interpreter;
    // End of variables declaration

}
