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

package org.clothocad.tool.toolappbrowser.tc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author jcanderson_Home
 */
public class BrowsePanel extends JScrollPane {

    public BrowsePanel() {
        ScrollablePanel scrollingpanel = new ScrollablePanel();
        setViewportView(scrollingpanel);
        setWheelScrollingEnabled(true);
        scrollingpanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        scrollingpanel.setScrollableHeight(ScrollablePanel.ScrollableSizeHint.STRETCH);
        scrollingpanel.setAutoscrolls(true);

        GridLayout lay = new GridLayout(5,6,10,10);

        scrollingpanel.setLayout(lay);

        scrollingpanel.setBackground(Color.WHITE);

        ArrayList<ToolWrapper> listy = Collator.getAllTools();
        for(ToolWrapper tw: listy) {
            ToolComponent button = new ToolComponent(tw);
            scrollingpanel.add(button);
        }
    }

    private class ToolComponent extends JComponent {

        public ToolComponent(final ToolWrapper tw) {
            setPreferredSize(new Dimension(200,150));
            ImageIcon img = tw.getIcon(120);
            TransparentButton button = new TransparentButton(img);
            button.setExitAlpha(1.0f);
            button.setEnterAlpha(0.7f);
            int xpos = (190-img.getIconWidth())/2;
            button.setBounds(xpos+10,7,img.getIconWidth(),img.getIconHeight());
            add(button);

            TransparentButton deleteMe = new TransparentButton(deletebtn);
            deleteMe.setExitAlpha(0.5f);
            deleteMe.setEnterAlpha(1.0f);
            deleteMe.setLocation(177,5);
            deleteMe.setToolTipText("Click to uninstall");
            deleteMe.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Uninstall requested for " + tw.getDisplayName());
                    //THROW A WARNING DIALOG
                    //CALL TW.UNINSTALL();
                }
            });
            add(deleteMe);

            JLabel label = new JLabel(tw.getDisplayName());
            label.setBounds(15,120,190,25);
            add(label);



            this.setToolTipText(tw.getDescription());


            button.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==2) {
                        tw.launchTool();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            int fwidth = 190;
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(offwhite);
            g.fillRoundRect(10, 0, fwidth, 145, 20, 20);
            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(10, 0, fwidth, 145, 20, 20);
            super.paintComponent(g);
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    private static Color offwhite = new Color(240,240,218);
    private static ImageIcon deletebtn = ImageUtilities.loadImageIcon("org/clothocad/tool/toolappbrowser/tc/deletebtn.png", false);
}
