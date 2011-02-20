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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashSet;
import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.ObjType;
import org.clothocad.tool.spreaditfeatures.spreaditFeatures;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;

/**
 *
 * @author J. Christopher Anderson
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    public ButtonEditor(spreaditFeatures frame) {
        _frame = frame;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Box out = new Box(BoxLayout.X_AXIS);
        out.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                stopCellEditing();
                cancelCellEditing();
            }
        });
        out.setBackground(Color.red);
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

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }


/**-----------------
     variables
 -----------------*/

    private spreaditFeatures _frame;
    private static final ImageIcon noteIcon = ImageSource.getObjectImage(ObjType.NOTE, 20);
}
