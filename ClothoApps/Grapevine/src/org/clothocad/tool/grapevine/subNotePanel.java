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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class subNotePanel extends JPanel {
    public subNotePanel(NoteEditor parent, Note anote) {
        _note = anote;
        parentFrame = parent;
        initComponents();

        factoidListener = new ObjBaseObserver() {
            @Override
            public void update(ObjBase obj, RefreshEvent evt) {
                if(evt.isCondition(RefreshEvent.Condition.NAME_CHANGED)
                        || evt.isCondition(RefreshEvent.Condition.UPDATE_ALL)) {
                    titleField.setText(_note.getName());
                }
            }
        };
        _note.isRepresentedBy(factoidListener, this);
        new ObjBasePopup(this, _note);
        new ObjBasePopup(titleField, _note);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(navyblue);
        setBorder(blackline);
        setPreferredSize(new Dimension(500,27));

        titleField = new JLabel(_note.getName()) {
            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        titleField.setForeground(Color.WHITE);

        Box controlBox = new Box(BoxLayout.X_AXIS);
        add(new JLabel("   "), BorderLayout.WEST);
        add(titleField, BorderLayout.CENTER);
        add(controlBox, BorderLayout.EAST);

        //Put in control icons all on right side
        TransparentButton tb = new TransparentButton(saveIcon);
        tb.setEnterAlpha(1.0f);
        tb.setExitAlpha(0.4f);
        tb.setToolTipText("Save to database");
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _note.saveDefault();
            }
        });
        controlBox.add(tb);
        controlBox.add(Box.createHorizontalStrut(2));

        tb = new TransparentButton(removeIcon);
        tb.setEnterAlpha(1.0f);
        tb.setExitAlpha(0.4f);
        tb.setToolTipText("Remove from note");
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame._note.removeChildNote(_note);
            }
        });
        controlBox.add(tb);
        controlBox.add(Box.createHorizontalStrut(4));

        tb = new TransparentButton(popoutIcon);
        tb.setEnterAlpha(1.0f);
        tb.setExitAlpha(0.4f);
        tb.setToolTipText("Popout to edit");
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NoteEditor(_note);
            }
        });
        controlBox.add(tb);
        controlBox.add(Box.createHorizontalGlue());

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    _note.launchDefaultViewer();
                }
            }
        };
        titleField.addMouseListener(listener);
        addMouseListener(listener);
    }

/*-----------------
     variables
 -----------------*/
    private Note _note;
    private NoteEditor parentFrame;
    private ObjBaseObserver factoidListener;
    private static Color navyblue = new Color(35, 48, 64);
    private static final Border blackline = BorderFactory.createLineBorder(Color.black);
    private JLabel titleField;

    private static ImageIcon saveIcon = ImageUtilities.loadImageIcon("org/clothocad/tool/grapevine/isave.png", false);
    private static ImageIcon removeIcon = ImageUtilities.loadImageIcon("org/clothocad/tool/grapevine/itrash.png", false);
    private static ImageIcon popoutIcon = ImageUtilities.loadImageIcon("org/clothocad/tool/grapevine/ipopout.png", false);

}
