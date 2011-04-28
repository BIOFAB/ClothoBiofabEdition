/*
 * 
Copyright (c) 2010 The Regents of the University of California.
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

package org.clothocore.widget.fabdash.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.clothocore.api.data.ObjType;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.buttons.TransparentButton;
import org.clothocore.widget.fabdash.InventoryTopComponent;

/**
 *
 * @author jcanderson_Home
 */
public class ObjTypeChooser extends JPanel {

    public ObjTypeChooser(InventoryTopComponent tc) {
        _browser = tc;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,62));

        JScrollPane noteScroller = new JScrollPane();
        ScrollablePanel CommentScrollPanel = new ScrollablePanel();
        noteScroller.setViewportView(CommentScrollPanel);
        noteScroller.setWheelScrollingEnabled(true);
        CommentScrollPanel.setScrollableHeight(ScrollablePanel.ScrollableSizeHint.FIT);
        CommentScrollPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.NONE);
        add(noteScroller, BorderLayout.CENTER);


        Map<ObjType, ImageIcon> icons = ImageSource.getObjectIconSet(30);
        for(ObjType atype : ObjType.values()) {
            if(atype.equals(ObjType.GRAMMAR) || atype.equals(ObjType.FLEX_FIELD) || atype.equals(ObjType.NUCSEQ) || atype.equals(ObjType.WIKITEXT)) {
                continue;
            }
            ImageIcon icon = icons.get(atype);
            typeButton tb = new typeButton(icon, atype);

            if(atype.equals(ObjType.PART)) {
                chosenButton = tb;
                chosenButton.setExitAlpha(1.0f);
            }
            CommentScrollPanel.add(tb);
        }
    }

    private class typeButton extends TransparentButton  {
        ///VARIABLES//
        private ObjType type;

        public typeButton(ImageIcon icon, ObjType atype) {
            super(icon);
            type = atype;
            setEnterAlpha(0.6f);
            setExitAlpha(0.2f);
            setToolTipText(atype.toString().toLowerCase());
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chosenButton.setExitAlpha(0.2f);
                    chosenButton.repaint();
                    setExitAlpha(1.0f);
                    chosenButton = typeButton.this;
                    _browser.changeObjType(type);
                }
            });
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    private typeButton chosenButton;
    private InventoryTopComponent _browser;
}
