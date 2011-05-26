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

package org.clothocad.tool.spreaditfeatures.colors;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.clothocore.api.core.Collator;
import org.clothocore.api.data.Feature;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class ColorPicker extends JPanel {

    public ColorPicker() {
        setLayout(null);
        setOpaque(false);
        _corePrefs = Preferences.userNodeForPackage( Collator.class );
        ImageIcon bkgImage = ImageUtilities.loadImageIcon( "org/clothocad/tool/spreaditfeatures/colors/colorbox2.png", false );
        img = (BufferedImage) ImageUtilities.loadImage( "org/clothocad/tool/spreaditfeatures/colors/colorbox2.png", false );
        JLabel bkgLabel = new JLabel(bkgImage);
        bkgLabel.setBounds(0,topspacing, bkgImage.getIconWidth(), bkgImage.getIconHeight());
        add(bkgLabel);
        imageheight = bkgImage.getIconHeight();
        setSize(bkgImage.getIconWidth(),imageheight + topspacing + bottomspacing);

        for(int row=0; row<2; row++) {
            for(int col=0; col<5; col++ ) {
                JPanel current = new JPanel();
                int index = 5*row + col;
                oldColors[index] = current;
                current.setBounds(20*col, 10+ 15*row, 20,15);
                add(current);
            }
        }
        updateOldColors();
        addMouseListener(new Mouser());
    }

    public static int getOffset() {
        return imageheight + topspacing ;
    }

    private void updateOldColors() {
        for(int i=0; i<10; i++) {
            int colorint = _corePrefs.getInt("JCAColorPicker" + i, -76081);
            oldColors[i].setBackground(new Color(colorint));
        }
    }

    public void modColors(Color chosen) {
        if(chosen==null) {
            return;
        }
        for(int i=9; i>0; i--) {
            int colorint = _corePrefs.getInt("JCAColorPicker" + (i-1), -2834);
            _corePrefs.putInt("JCAColorPicker" + i, colorint);
        }
        _corePrefs.putInt("JCAColorPicker" + 0, chosen.getRGB());
        updateOldColors();
    }

    private class Mouser implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Clicked "+ e.getPoint());
            int posx = e.getPoint().x;
            int posy = e.getPoint().y;
            if(posy<=topspacing) {
                int index =0;
                if(posy>15) {
                    index = 5;
                }
                index = index + posx/20;
                try {
                    Color chosen = oldColors[index].getBackground();
                    if(isForward) {
                        feature.changeForwardColor(chosen);
                    } else {
                        feature.changeReverseColor(chosen);
                    }
                } catch(Exception err) {
                }
                setVisible(false);
                return;
            }
            posy = e.getPoint().y - topspacing;

            try {
                Color chosen = new Color(img.getRGB(posx, posy));
                if(isForward) {
                    feature.changeForwardColor(chosen);
                } else {
                    feature.changeReverseColor(chosen);
                }
                modColors(chosen);
            } catch(Exception err) {
            }
            setVisible(false);
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
           setVisible(false);
        }


    }

/*-----------------
     variables
 -----------------*/
    private static BufferedImage img;
    private static final int topspacing = 40;
    private static final int bottomspacing = 20;
    private static int imageheight;
    private JPanel[] oldColors = new JPanel[10];

    public Feature feature;

    public boolean isForward;
    private static Preferences _corePrefs;
}
