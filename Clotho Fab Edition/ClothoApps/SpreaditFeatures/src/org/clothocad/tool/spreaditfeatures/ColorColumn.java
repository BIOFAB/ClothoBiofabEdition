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

package org.clothocad.tool.spreaditfeatures;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import org.clothocad.tool.spreaditfeatures.helpers.HTMLColors;

/**
 *
 * @author J. Christopher Anderson
 */
public class ColorColumn extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
        Color fg = null;
        Color bg = null;

        if (isSelected) {
            //popup the color window
	} 

	setFont(table.getFont());

	if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = table.getBorder();
            }
            setBorder(border);

            /*
	    if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = DefaultLookup.getColor(this, ui, "Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = DefaultLookup.getColor(this, ui, "Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
	    }

             */
	} else {
            setBorder(table.getBorder());
	}

        value = table.getValueAt(row, column);
        //Convert whatever's there into a String
        String svalue = null;
        try {
            svalue = (String) value;
        } catch(Exception e) {
        }
        if(svalue==null) {
            try {
                svalue = Short.toString((Short) value);
            } catch(Exception e) {
            }
        }
        if(svalue==null) {
            try {
                svalue = Integer.toString((Integer) value);
            } catch(Exception e) {
            }
        }

        Color incolor = null;
        try {
             incolor = HTMLColors.getColor((String) value);
        } catch(Exception e) {
        }
        if(incolor==null) {
            try {
                int icol = Integer.parseInt((String) value);
                incolor = new Color(icol);
            } catch(Exception e) {
            }
        }
        if(incolor!=null) {
            setBackground(incolor);
        } else {
            setBackground(Color.white);
        }
    return this;
    }
}