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

package org.clothocad.tool.grapevine;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author J. Christopher Anderson
 */

public abstract class blueTextField extends javax.swing.JTextField implements FocusListener  {

    public blueTextField(int size) {
        _size = size;
        this.setBackground(navyblue);
        this.setFont(new java.awt.Font("Arial", 0, _size));
        this.setForeground(Color.WHITE);
        this.setBorder(null);
        dataUpdated();
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        setBackground(Color.WHITE);
        setFont(new java.awt.Font("Arial", 0, _size));
        setForeground(Color.BLACK);
        setBorder(blackline);
        gainFocus();
    }

    @Override
    public void focusLost(FocusEvent e) {
        setBackground(navyblue);
        setFont(new java.awt.Font("Arial", 0, _size));
        setForeground(Color.WHITE);
        setBorder(null);
        loseFocus();
    }
    public abstract void gainFocus();
    public abstract void loseFocus();
    public abstract void dataUpdated();

/**-----------------
     variables
 -----------------*/
    public int _size;
    public String oldValue="";

    private static Color navyblue = new Color(35, 48, 64);
    private static final Border blackline = BorderFactory.createLineBorder(Color.black);
    }