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

package org.clothocad.library.sequencelight;

import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.Highlighter.Highlight;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.Feature;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;

/**
 *
 * @author J. Christopher Anderson
 */
public class iconPanel extends JPanel {
    public iconPanel(seqPanel p, Frame f) {
        frame = f;
        nsp = p;
        nsp.setIconPanel(this);
        setLayout(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                putInIcons();
            }
        });
    }
    void putInIcons() {
        
    }
    /*
    void putInIcons() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("putinicons called");
                    //Clear old icons
                    for(TransparentButton oldt: alliconbuttons) {
                        remove(oldt);
                    }
                    alliconbuttons.clear();

                    //Draw new icons
                    Highlight[] highs = nsp.getHighlights();
                    for(Highlight h: highs) {
                        TransparentButton tb = new TransparentButton(arrowIcon);

                        Annotation an = nsp.highlightHash.get(h);
                        tb.setToolTipText(an.getName());

                        Feature itsfeature = an.getFeature();
                        if(itsfeature!=null) {
                            ObjBasePopup app = new ObjBasePopup(tb, itsfeature);
                        }

                        Point p = nsp.getEndPoint(h);
                        tb.setLocation(p.x-8,p.y-25);
                        add(tb);
                        alliconbuttons.add(tb);
                    }
                    repaint();
                } catch(Exception e) {

                }
            }
        });
    }

    */

    @Override
    public boolean isOpaque() {
        return false;
    }

/*-----------------
     variables
 -----------------*/
    private ArrayList<TransparentButton> alliconbuttons = new ArrayList<TransparentButton>();
    private seqPanel nsp;
    private Frame frame;
    private ImageIcon arrowIcon = new ImageIcon("plugins/Tools/flatData/Images/arrow.png");
}
