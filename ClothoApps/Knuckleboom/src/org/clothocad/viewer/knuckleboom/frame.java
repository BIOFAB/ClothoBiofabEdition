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

package org.clothocad.viewer.knuckleboom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.clothocad.wikieditorpanel.WikiEditorPanel;
import org.clothocore.api.data.WikiText;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {

public frame(WikiText wt) {
    super(wt.getName());
    _wt = wt;

    getContentPane().setBackground(navyblue);
    getContentPane().setLayout(new BorderLayout());
    WikiEditorPanel wep = new WikiEditorPanel(this, _wt);
    getContentPane().add(wep, BorderLayout.CENTER);

    setPreferredSize(new Dimension(400, 600));
    pack();
    wep.setHTMLMode();
    setVisible(true);

}

/*
wt = Collector.getWikiText("00cac28fe7274ca790b47b40dbcf0ac8")
wt.launchDefaultViewer()
 */
/*-----------------
     variables
 -----------------*/
private WikiText _wt;
private static Color navyblue = new Color(35, 48, 64);
}
