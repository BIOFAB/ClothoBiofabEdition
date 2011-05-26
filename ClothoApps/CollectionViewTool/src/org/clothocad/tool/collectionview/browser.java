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
 * NewJFrame.java
 *
 * Created on Jul 15, 2010, 11:19:35 AM
 */

package org.clothocad.tool.collectionview;

import java.awt.Color;


/**
 * THIS IS CURRENTLY NOT BEING USED, JUST LEAVING IT HERE AS BASE CODE FOR FUTURE THINGS
 * @author J. Christopher Anderson
 */
public class browser extends javax.swing.JFrame {

    /** Creates new form NewJFrame */
    public browser() {
        initComponents();
    }

    private void initComponents() {

        getContentPane().setBackground(navyblue);

        scrollPane = new javax.swing.JScrollPane();
        listPanel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        instructions = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(listPanel);
        listPanel.setBackground(Color.WHITE);

        title.setText("Available Collections");
        title.setFont(new java.awt.Font("Tahoma", 0, 18));
        title.setForeground(Color.WHITE);

        instructions.setText("Double or right click collections");
        instructions.setFont(new java.awt.Font("Tahoma", 0, 12));
        instructions.setForeground(Color.WHITE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(instructions, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title)
                    .addComponent(instructions))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        putInData();
        pack();
        setVisible(true);
    }

    private void putInData() {
     /*   final ArrayList<objLink> collections = collector.getAllLinksOf(objType.COLLECTION);

        double numicons = collections.size();
        ImageIcon icon = imageSource.getObjectImage(objType.COLLECTION, 35);
        int numrows = (int) Math.ceil(numicons/6.0);
        System.out.println(numrows);
        listPanel.setLayout(new java.awt.GridLayout(numrows, 6));

        for( final String name: collections.keySet()) {
            final transparentButton tb = new transparentButton(icon);
            tb.setEnterAlpha(0.8f);
            tb.setExitAlpha(1.0f);
            tb.setToolTipText(name);
            final Point location = tb.getLocation();

            tb.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(e.getModifiers());
                    collection acol = collector.getCollection(collections.get(name));
                    if(acol==null) {
                        System.out.println("collection was null");
                        return;
                    }
                    if(e.getModifiers()==4) {
                        objBasePopup obp = new objBasePopup(tb, acol, tb.getLocation());
                    } else if(e.getClickCount()==2) {
                        acol.launchDefaultViewer();
                    }
                }
            });
            listPanel.add(tb);
        }
        repaint(); */
    }

/**-----------------
     variables
 -----------------*/
    // Variables declaration - do not modify
    private javax.swing.JLabel title;
    private javax.swing.JLabel instructions;
    private javax.swing.JPanel listPanel;
    private javax.swing.JScrollPane scrollPane;
    static Color navyblue = new Color(35, 48, 64);
}
