/*
 * Copyright (c) 2007, Romain Guy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.clothocore.tool.pluginmanager.gui;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.clothocore.util.basic.ImageSource;

/**
 * @author Romain Guy
 */
public class ApplicationFrame extends JFrame {
    
    private FancyPanel chooser;
    private CurvesPanel curves;
    private Timer timer;
    private JPanel stackingPane;
    private ApplicationFrame originalFrame=null;

    public ApplicationFrame() throws HeadlessException {
        super("Plugin Manager");
        setIconImage(ImageSource.getTinyLogo());

        buildContentPane();
        
        startAnimation();
        
        setSize(640, 340);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private ApplicationFrame(int type, ApplicationFrame old) throws HeadlessException {
        super("Plugin Manager");
        originalFrame = old;
        setIconImage(ImageSource.getTinyLogo());

        //build content pane
        stackingPane = new JPanel();
        stackingPane.setLayout(new StackLayout());

        GradientPanel gradient = new GradientPanel();
        curves = new CurvesPanel();

        switch(type) {
            case 0:
                //Remove plugins
                chooser = new UninstallPlugins(this);
                break;
            case 1:
                //Manage viewers
                chooser = new ChoosePreferredViewers(this);
                break;
            case 2:
                //Manage widgets
                chooser = new ShowAndHideWidgets(this);
                break;
            case 3:
                //Manage connections
                chooser = new ChooseDefaultConnection(this);
                break;
            case 5:
                //Launch store
                break;
            default:
                return;
        }

        stackingPane.add(gradient, StackLayout.TOP);
        stackingPane.add((Component) chooser, StackLayout.TOP);
        stackingPane.add(curves, StackLayout.TOP);

        add(stackingPane);

        startAnimation();

        setSize(640, 340);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void close() {
        timer.stop();
        chooser = null;
        curves = null;
        if(originalFrame!=null) {
            originalFrame.setVisible(true);
        }
        dispose();
    }

    private void startAnimation() {
        timer = new Timer(50, new ActionListener() {
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
        
        GradientPanel gradient = new GradientPanel();
        curves = new CurvesPanel();
        chooser = new OpeningScreenChooser(this);
        
        stackingPane.add(gradient, StackLayout.TOP);
        stackingPane.add((Component) chooser, StackLayout.TOP);
        stackingPane.add(curves, StackLayout.TOP);
        
        add(stackingPane);
    }

    /**
     * Called after selecting the secondary manager, builds a new frame
     * and hides the old one.
     * @param type
     */
    void ReBuildPanel( final int type) {
        final ApplicationFrame old = this;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationFrame AF = new ApplicationFrame(type, old);
                old.setVisible(false);
                AF.setVisible(true);
            }
        });
    }


    public static void main(String[] args) {     
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationFrame tester = new ApplicationFrame();
                tester.setVisible(true);
            }
        });
    }
}