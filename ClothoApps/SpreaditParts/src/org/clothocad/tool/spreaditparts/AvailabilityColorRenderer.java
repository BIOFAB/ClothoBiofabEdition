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

package org.clothocad.tool.spreaditparts;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class AvailabilityColorRenderer extends JLabel
    implements TableCellRenderer {

    public AvailabilityColorRenderer( ) {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {

        Color fg = null;
        Color bg = null;

        setBorder(null);

        if (isSelected) {
            super.setForeground(fg == null ? table.getSelectionForeground()
                                           : fg);
            super.setBackground(bg == null ? table.getSelectionBackground()
                                           : bg);
	} else {
            Color currcol = Color.WHITE;
            try {
                String val = (String) table.getValueAt(row, 6);
                if(val!=null) {
                    if(val.equals("works")) {
                        currcol = worksColor;
                    } else if(val.equals("fails")) {
                        currcol = failsColor;
                    }
                }
            } catch(java.lang.ClassCastException e) {
            }
            setBackground(currcol);
            setForeground(Color.BLACK);
	}

	setFont(table.getFont());
        super.setText((String) value);

	return this;

    }

    @Override
    public boolean isOpaque() {
        return true;
    }

/**-----------------
     variables
 -----------------*/
    private static final Color failsColor = new Color(238,221,215);
    private static final Color worksColor = new Color(222,239,187);
}