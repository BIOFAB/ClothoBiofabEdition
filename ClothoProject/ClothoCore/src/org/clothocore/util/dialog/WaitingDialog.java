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
 * WaitingDialog.java
 *
 * Created on Jul 17, 2010, 10:24:34 AM
 */

package org.clothocore.util.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.openide.util.ImageUtilities;

/**
 *
 * @author jcanderson
 */
public class WaitingDialog extends Window {

    /** Creates new form WaitingDialog */
    public WaitingDialog(java.awt.Frame parent, final String message, boolean modal) {
        super(parent);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initComponents();
                textarea.setText(message);
                int length = message.length();
                double rows = length /57.0;
                double roundup = Math.ceil(rows);
                int irows = (int) roundup;
                int height = irows*27;
                textarea.setPreferredSize(new Dimension(387,height));
                pack();
                setVisible(true);
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JLabel label = new JLabel(bkgImage);
        System.out.println("bkgImage " + bkgImage.getIconWidth());
        add(label, BorderLayout.NORTH);

        JPanel lefty = new JPanel();
        lefty.setPreferredSize(new Dimension(20,20));
        lefty.setBackground(Color.white);
        add(lefty, BorderLayout.WEST);

        JPanel righty = new JPanel();
        righty.setPreferredSize(new Dimension(20,20));
        righty.setBackground(Color.white);
        add(righty, BorderLayout.EAST);

        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(10,10));
        bottom.setBackground(Color.white);
        add(bottom, BorderLayout.SOUTH);

        textarea = new javax.swing.JTextArea();
        textarea.setFont(new java.awt.Font("Calibri", 0, 16));
        textarea.setForeground(navyblue);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setPreferredSize(new Dimension(387,100));
        add(textarea, BorderLayout.CENTER);
    }



    private javax.swing.JTextArea textarea;
    private static final Color navyblue = new Color(35, 48, 64);
    private static final ImageIcon bkgImage = ImageUtilities.loadImageIcon( "org/clothocore/images/DialogBKG.png", false );


}
