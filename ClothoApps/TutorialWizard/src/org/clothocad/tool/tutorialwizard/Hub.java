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

package org.clothocad.tool.tutorialwizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.clothocore.util.basic.ImageSource;

/**
 *
 * @author J. Christopher Anderson
 */
public class Hub extends JFrame {
    public Hub() {
        super("Available Tutorials");
        setIconImage(ImageSource.getTinyLogo());
        initcomponents();

        //Get any updates
        Updater update = new Updater();
        if(!update.checkForUpdate()) {
            return;
        }

        ArrayList<String> names = update.getNames();
        final ArrayList<String> links = update.getLinks();

        //Create the buttons
        for(int i=0; i<links.size(); i++) {
            try {
                JButton gb = new JButton(names.get(i));
                gb.setBackground(new Color(100,100,100));
                gb.setForeground(Color.WHITE);
                gb.setBorder(null);
                gb.setFont(new Font("Arial", Font.BOLD, 15));
                gb.setPreferredSize(new Dimension(400,30));
                final int index = i;
                gb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new TutorialParser(links.get(index));
                    }
                });
                box.add(gb);
                box.add(Box.createVerticalStrut(3));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        validate();
        repaint();
    }

    private void initcomponents() {
        this.setPreferredSize(new Dimension(400, 250));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(100,100,100));

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(10, 400));
        getContentPane().add(spacer, BorderLayout.WEST);

        JScrollPane noteScroller = new JScrollPane();
        ScrollablePanel CommentScrollPanel = new ScrollablePanel();
        noteScroller.setViewportView(CommentScrollPanel);
        noteScroller.setWheelScrollingEnabled(true);
        CommentScrollPanel.setLayout(new BoxLayout(CommentScrollPanel, BoxLayout.PAGE_AXIS));
        CommentScrollPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        CommentScrollPanel.setScrollableHeight(ScrollablePanel.ScrollableSizeHint.NONE);
        getContentPane().add(noteScroller, BorderLayout.CENTER);

        JLabel headline = new JLabel("Click a tutorial to launch:");
        headline.setFont(new Font("Arial", Font.BOLD, 18));
        headline.setForeground(Color.LIGHT_GRAY);
        getContentPane().add(headline, BorderLayout.NORTH);

        JTextArea baseline = new JTextArea("Check back later for more tutorials.  These load dynamically.  Contact Chris Anderson at jcanderson2167@gmail.com if you'd like to share your own tutorial.");
        baseline.setFont(new Font("Arial", Font.ITALIC, 12));
        baseline.setForeground(Color.LIGHT_GRAY);
        baseline.setOpaque(false);
        baseline.setBorder(null);
        baseline.setLineWrap(true);
        baseline.setWrapStyleWord(true);
        baseline.setEditable(false);
        getContentPane().add(baseline, BorderLayout.SOUTH);

        box = new Box(BoxLayout.Y_AXIS);
        getContentPane().add(box);

        for(String label : tutorialList.keySet()) {
            JButton gb = new JButton(label);
            gb.setBackground(new Color(100,100,100));
            gb.setForeground(Color.WHITE);
            gb.setBorder(null);
            gb.setFont(new Font("Arial", Font.BOLD, 15));
            gb.setPreferredSize(new Dimension(400,30));
            gb.addActionListener(new listener(tutorialList.get(label), numSlidesList.get(label)));
            box.add(gb);
            box.add(Box.createVerticalStrut(3));
        }

        pack();
        setVisible(true);
    }


    private class listener implements ActionListener {
        public listener(String alink, int num) {
            link = alink;
            numslides = num;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new TutorialFrame(numslides, link);
        }

        //VARIABLES//
        private String link;
        private int numslides;
    }
/*-----------------
     variables
 -----------------*/
    public static HashMap<String,String> tutorialList = new HashMap<String,String>();
    public static HashMap<String,Integer> numSlidesList = new HashMap<String,Integer>();
    private Box box;
    
    static {
        tutorialList.put("Add, edit, and browse Features", "addfeatures/");
        numSlidesList.put("Add, edit, and browse Features", 18);
        tutorialList.put("Playing with Parts", "playparts/");
        numSlidesList.put("Playing with Parts", 28);
        tutorialList.put("Referencing with Notes and Factoids", "notesandfactoids/");
        numSlidesList.put("Referencing with Notes and Factoids", 21);
        tutorialList.put("Manage your Apps", "pluginmanager/");
        numSlidesList.put("Manage your Apps", 13);
        tutorialList.put("Predicting PCR products with PCR Predictor", "pcrprediction/");
        numSlidesList.put("Predicting PCR products with PCR Predictor", 4);
    }
}
