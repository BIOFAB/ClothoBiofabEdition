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

package org.clothocad.tool.straineditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Strain.genomeModification;
import org.clothocore.util.buttons.TransparentButton;

/**
 *
 * @author J. Christopher Anderson
 */
public class genomicField extends Box {

    public genomicField(genomeModification gm) {
        super(BoxLayout.X_AXIS);
        _gm = gm;
        Plasmid aplas = gm.getPlasmid();

        episomeButton eb = new episomeButton(aplas);
        add(eb);

        name = new whiteTextField(aplas.getName());
        add(name);

        start = new whiteTextField(Integer.toString(gm._startOnGenome));
        add(start);

        end = new whiteTextField(Integer.toString(gm.getEndOnGenome()));
        add(end);
    }

    private class whiteTextField extends JTextField {
        public whiteTextField(String str) {
            setBackground(navyblue);
            setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
            setForeground(Color.WHITE);
            setBorder(null);
            setText(str);
        }
    }

    private class episomeButton extends TransparentButton {
        public episomeButton(Plasmid plas) {
            super(frame.plasmidIcon);
            aplas = plas;
            setExitAlpha(1.0f);
            setEnterAlpha(0.8f);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseClicked(MouseEvent e) {

                }
            });
        }
        private Plasmid aplas;
    }

/*-----------------
     variables
 -----------------*/
    genomeModification _gm;
    whiteTextField name;
    whiteTextField start;
    whiteTextField end;
    private static Color navyblue = new Color(35, 48, 64);
}
