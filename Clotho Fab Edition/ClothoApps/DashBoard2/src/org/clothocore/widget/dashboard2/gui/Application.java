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


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.util.basic.ImageSource;


/**
 *
 * @author J. Christopher Anderson
 */


public class Application extends JFrame {

    public Application() throws HeadlessException {
        super("Clotho Dashboard");
        setIconImage(ImageSource.getTinyLogo());
        _app = this;
        
        //Set the preferred dimensions of the dashboard
        String snumcols = Collator.getPreference("DashBoard2numCols");
        try {
            int numCols = Integer.parseInt(snumcols);
            if(numCols > 2 || numCols <6) {
                ItemPanel.numCol = numCols;
            }
        } catch(java.lang.NumberFormatException e) {
        }

        buildContentPane();
        startAnimation();
        setSize(214, 306);
        setResizable(false);
        setUndecorated(true);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Position it in the lower right corner
        setLocationByPlatform(true);
        setAlwaysOnTop(true);
        Toolkit toolkit =  Toolkit.getDefaultToolkit ();
        Dimension dim = toolkit.getScreenSize();
        setLocation(dim.width-214, dim.height-30-306);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
                buttonPanel.searchbar.jtf.requestFocusInWindow();
            }
        });
    }

    static Application getApp() {
        return _app;
    }

    private void startAnimation() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curves.animate();
                curves.repaint();
            }
        });
        timer.start();
    }

    private void buildContentPane() {
        stackingPane = new JPanel();
        stackingPane.setLayout(new StackLayout());

        BackgroundPanel gradient = new BackgroundPanel();
        curves = new CurvesPanel();
        ArrayList<ToolWrapper> wraps = Collator.getAllTools();
        buttonPanel = new ButtonPanel(this);
        toolpanel = new ToolPanel(this, 5);
        blurrer = new BlurPanel();
        blurrer.setVisible(false);

        stackingPane.add(gradient, StackLayout.TOP);
        stackingPane.add(buttonPanel, StackLayout.TOP);
        stackingPane.add(curves, StackLayout.TOP);
        stackingPane.add(toolpanel, StackLayout.TOP);
        stackingPane.add(blurrer, StackLayout.TOP);

        add(stackingPane);
    }

    protected void draggerHeaderDragged( java.awt.event.MouseEvent evt ) {
        Point aPoint = evt.getPoint();
        int x = getX() + aPoint.x - mousePointer.x;
        int y = getY() + aPoint.y - mousePointer.y;
        setLocation( x, y );
        Graphics graphics = getGraphics();
        paint( graphics );
    }

    protected void draggerHeaderMoved( java.awt.event.MouseEvent evt ) {
        mousePointer = evt.getPoint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _app = new Application();
            }
        });
    }

/*-----------------
     variables
 -----------------*/
    private Timer timer;
    private JPanel stackingPane;
    CurvesPanel curves;

    ToolPanel toolpanel;
    ButtonPanel buttonPanel;
    BlurPanel blurrer;
    private static Application _app;
    private Point mousePointer;

}