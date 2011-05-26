package org.clothocad.tool.grapevine;

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



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.clothocore.api.data.Factoid;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocad.wikieditorpanel.WikiEditorPanel;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class factoidPanel extends JPanel {
    public factoidPanel(NoteEditor parent, Factoid afact) {
        _fact = afact;
        parentFrame = parent;
        initComponents(parent);

        factoidListener = new ObjBaseObserver() {
            @Override
            public void update(ObjBase obj, RefreshEvent evt) {
                if(evt.isCondition(RefreshEvent.Condition.NAME_CHANGED)
                        || evt.isCondition(RefreshEvent.Condition.UPDATE_ALL)) {
                    titleField.setText(_fact.getName());
                } else if(evt.isCondition(RefreshEvent.Condition.REFERENCE_CHANGED)
                        || evt.isCondition(RefreshEvent.Condition.UPDATE_ALL)) {
                    if(_fact.getReference()!=null) {
                        pmidField.setText(_fact.getReference().getID());
                    }
                }
            }
        };
        _fact.isRepresentedBy(factoidListener, buttonPanel);

    }

    private void initComponents(Window parent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(new JLabel("  "), BorderLayout.EAST);
        add(new JLabel("  "), BorderLayout.WEST);

        headerPanel = new JPanel();
        headerPanel.setBackground(palePink);
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);

        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(500,2));
        spacerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.add(spacerPanel, BorderLayout.NORTH);

        buttonPanel = new Box(BoxLayout.X_AXIS);
        buttonPanel.setPreferredSize(new Dimension(500,23));
        buttonPanel.setBackground(verylightGray);
        headerPanel.add(buttonPanel, BorderLayout.CENTER);

        //Put in name
        pmidField = new JLabel();
        pmidField.setOpaque(false);
        pmidField.setBorder(null);
        pmidField.setBackground(null);
        pmidField.setFont(arial12bold);
        pmidField.setForeground(Color.LIGHT_GRAY);
        pmidField.setToolTipText("Double click for PDF");
        if(_fact.getReference()!=null) {
            pmidField.setText(_fact.getReference().getID());
        }
        MouseAdapter pubgetClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    if(_fact.getReference()!=null) {
                        _fact.getReference().launchWebLink();
                    }
                }
            }
        };
        pmidField.addMouseListener(pubgetClick);
        buttonPanel.add(Box.createHorizontalStrut(4));
        buttonPanel.add(pmidField);

        buttonPanel.add(Box.createHorizontalStrut(10));

        //Put in factoid article title
        titleField = new JTextField();
        titleField.setOpaque(false);
        titleField.setBorder(null);
        titleField.setBackground(null);
        titleField.setEditable(false);
        titleField.setFont(arial12plain);
        titleField.setForeground(Color.BLACK);
        titleField.setText(_fact.getName());
        buttonPanel.add(titleField);

        //Put in control icons all on right side
        TransparentButton tb = new TransparentButton(saveIcon);
        tb.setEnterAlpha(1.0f);
        tb.setExitAlpha(0.4f);
        tb.setToolTipText("Save to database");
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _fact.saveDefault();
            }
        });
        buttonPanel.add(tb);
        buttonPanel.add(Box.createHorizontalGlue());

        buttonPanel.add(Box.createHorizontalStrut(2));

        tb = new TransparentButton(removeIcon);
        tb.setEnterAlpha(1.0f);
        tb.setExitAlpha(0.4f);
        tb.setToolTipText("Remove from note");
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame._note.removeFactoid(_fact);
            }
        });
        buttonPanel.add(tb);
        buttonPanel.add(Box.createHorizontalGlue());

        buttonPanel.add(Box.createHorizontalStrut(4));

        tb = new TransparentButton(popoutIcon);
        tb.setEnterAlpha(1.0f);
        tb.setExitAlpha(0.4f);
        tb.setToolTipText("Popout to edit");
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new factoidEditor(_fact);
            }
        });
        buttonPanel.add(tb);
        buttonPanel.add(Box.createHorizontalGlue());

        MouseAdapter doubleClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    new factoidEditor(_fact);
                }
            }
        };
        titleField.addMouseListener( doubleClick );
        new ObjBasePopup(titleField, _fact);
        new ObjBasePopup(headerPanel, _fact);

        //Put in wiki editor
        wep = new WikiEditorPanel(parent, _fact.getWikiText());
        wep.removeHtmlBorders();
        wep.setHTMLMode();
        add(wep, BorderLayout.CENTER);

        JPanel spacerPanel2 = new JPanel();
        spacerPanel2.setPreferredSize(new Dimension(500,10));
        spacerPanel2.setBackground(Color.WHITE);
        add(spacerPanel2, BorderLayout.SOUTH);
    }

/*-----------------
     variables
 -----------------*/
    private Factoid _fact;
    private WikiEditorPanel wep;
    private JPanel headerPanel ;
    private JLabel pmidField;
    private JTextField titleField;
    private Box buttonPanel;
    private static final Color verylightGray = new Color(240,240,240);
    private static final Color palePink = new Color(248,248,248);
    private final Font arial12bold = new Font("Arial", Font.BOLD, 12);
    private final Font arial12plain = new Font("Arial", Font.PLAIN, 12);
    private static ImageIcon saveIcon = ImageUtilities.loadImageIcon("org/clothocad/tool/grapevine/save.png", false);
    private static ImageIcon removeIcon = ImageUtilities.loadImageIcon("org/clothocad/tool/grapevine/trash.png", false);
    private static ImageIcon popoutIcon = ImageUtilities.loadImageIcon("org/clothocad/tool/grapevine/popout.png", false);

    NoteEditor parentFrame;
    ObjBaseObserver factoidListener;
}
