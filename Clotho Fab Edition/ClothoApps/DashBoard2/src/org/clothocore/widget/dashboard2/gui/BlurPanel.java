/*
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

package org.clothocore.widget.dashboard2.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.clothocore.widget.dashboard2.util.ColorTintFilter;
import org.clothocore.widget.dashboard2.util.GaussianBlurFilter;

/**
 *
 * @author J. Christopher Anderson
 */
public class BlurPanel extends JPanel {

    public BlurPanel() {
        super(new BorderLayout());
        _bp = this;

        //Add a mouse listener to silence any mouse events
        addMouseListener(new java.awt.event.MouseAdapter() {
        } );
    }

    public static BlurPanel getBlurPanel() {
        return _bp;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

 @Override
    protected void paintComponent(Graphics g) {
        // enables anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        //Draw the blurry image
        Application mainFrame = Application.getApp();
        int width = mainFrame.getRootPane().getWidth();
        int height = mainFrame.getRootPane().getHeight();
        g2.drawImage(backDrop, 0, 0, width, height, null);

        //Sets a 75% translucent composite
        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.75f);
        Composite composite = g2.getComposite();
        g2.setComposite(alpha);

        //Put in the rounded black box
        g2.setColor(TEXT_BOX_COLOR);
        RoundRectangle2D.Double rr = new RoundRectangle2D.Double(45, 200, 125, 25,  9, 9);
        g2.fill(rr);
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(TEXT_COLOR);
        g2.draw(rr);
        g2.setComposite(composite);

        // Draws the text
        g2.setColor(TEXT_COLOR);
        g2.drawString(_label, 60, 217);
        g2.setComposite(composite);
    }

    public void setBlurring(final boolean isit, final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _label = text;
                Application.getApp().buttonPanel.searchbar.jtf.getCaret().setVisible(!isit);
                setVisible(isit);
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            Application mainFrame = Application.getApp();
            System.out.println(mainFrame);
            backDrop = GraphicsUtilities.createCompatibleImage(mainFrame.getRootPane().getWidth(),
                   mainFrame.getRootPane().getHeight());
            Graphics2D g2 = backDrop.createGraphics();
            mainFrame.getRootPane().paint(g2);
            g2.dispose();

            backDrop = GraphicsUtilities.createThumbnail(backDrop,
                   mainFrame.getRootPane().getWidth() / 2);
            backDrop = new ColorTintFilter(Color.BLACK, 0.10f).filter(backDrop, null);
            backDrop = new GaussianBlurFilter(5).filter(backDrop, null);
        } else {
            if (backDrop != null) {
                backDrop.flush();
            }
            backDrop = null;
        }
        super.setVisible(visible);
    }

/*-----------------
     variables
 -----------------*/
    private Rectangle _screenRect;
    private BufferedImage backDrop = null;
    String _label="empty";
    
    private static final Color TEXT_COLOR = new Color(245,245,245);
    private static final Color TEXT_BOX_COLOR = new Color(20,20,20);
    private static final Color GRADIENT_COLOR2 = Color.WHITE;
    private static final Color GRADIENT_COLOR1 = Color.GRAY;
    private static BlurPanel _bp;
}
