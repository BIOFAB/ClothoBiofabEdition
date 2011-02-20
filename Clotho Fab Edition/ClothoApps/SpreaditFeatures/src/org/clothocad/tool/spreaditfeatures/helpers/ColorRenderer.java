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

package org.clothocad.tool.spreaditfeatures.helpers;


import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.ObjType;
import org.clothocad.tool.spreaditfeatures.spreaditFeatures;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;

public class ColorRenderer extends JLabel
    implements TableCellRenderer {

    public ColorRenderer(spreaditFeatures frame) {
        _frame = frame;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {

        Color fg = null;
        Color bg = null;

        setBorder(null);

        Color currcol = Color.WHITE;
        if (isSelected) {
            super.setForeground(fg == null ? table.getSelectionForeground()
                                           : fg);
            super.setBackground(bg == null ? table.getSelectionBackground()
                                           : bg);
	} else {
            setBackground(currcol);
            setForeground(Color.BLACK);
	}

        if(column == 4) {
            currcol = getColor(table, row, 4);
            setBackground(currcol);
        } else if(column == 5) {
            currcol = getColor(table, row, 5);
            setBackground(currcol);
        } else if(column == 7) {
            return drawNotes(row);
        }

	setFont(table.getFont());


        String texty = null;
        try {
            texty = (String) value;
        } catch(Exception e) {
        }
        if(texty==null) {
            try {
                texty = Integer.toString((Integer) value);
            } catch(Exception e) {
            }
        }
        if(texty==null) {
            try {
                texty = Short.toString((Short) value);
            } catch(Exception e) {
            }
        }
        if(texty==null) {
            try {
                texty = value.toString();
            } catch(Exception e) {
            }
        }
        super.setText(texty);

	return this;

    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    private Color getColor(JTable table, int row, int col) {
        Object value = table.getValueAt(row, col);
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

        if(incolor==null) {
            return Color.WHITE;
        }
        return incolor;
    }

    private Component drawNotes(int row) {
        Box out = new Box(BoxLayout.X_AXIS);
        Feature currFeat = _frame.getFeatureAt(row);
        HashSet<Note> notes = currFeat.getNotes();
        for(Note anote : notes) {
            TransparentButton tb = new TransparentButton(noteIcon);
            tb.setEnterAlpha(0.8f);
            tb.setExitAlpha(1.0f);
            ObjBasePopup obp = new ObjBasePopup(tb, anote);
            tb.setToolTipText(anote.getName());
            out.add(tb);
        }
        return out;
    }

/**-----------------
     variables
 -----------------*/
    
    private spreaditFeatures _frame;
    private static final ImageIcon noteIcon = ImageSource.getObjectImage(ObjType.NOTE, 20);
}