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

package org.clothocore.util.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.openide.filesystems.FileObject;


/**
 *
 * @author J. Christopher Anderson
 */
public class CommandHelp extends JFrame {

    /**
     * Launch command help.
     * @param file the file object of a csv with the list of commands
     * @param toolname the name of the plugin such as "Bull Trowell"
     * @param url a website with more help information on the tool (can be null or empty)
     * @param bugaddress an email address where bugs can be reported (can be null or empty)
     * @param bugzillalink a bugzilla server where bug reports can be sent (can be null or empty)
     */
    public CommandHelp(FileObject file, String toolname, String url, String bugaddress, String bugzillalink) {
        super(toolname + " Command Help");

        try {
            _url = new URL(url);
        } catch (MalformedURLException ex) {
        }

        _toolname = toolname;
        _bugaddress = bugaddress;
        _bugzillaLink = bugzillalink;
        if(parseFile(file)) {
            initComponents();
        }
    }

    private boolean parseFile(FileObject file) {
        try {
            InputStream is = file.getInputStream();
            if (is != null) {
                String line;

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        process(line);
                    }
                } finally {
                    is.close();
                }
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private void parseFile(File file) {
        String line;
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            if (!in.ready())
                throw new IOException();
            while ((line = in.readLine()) != null)
                process(line);
            in.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return;
        }
    }

    private void process(String line) {
        System.out.println(line);
        //If the line is a divider, change the current arraylist
        if(line.startsWith("***")) {
            if(line.startsWith("***KeyBindings")) {
                currentLines = keybindings;
            } else if(line.startsWith("***JComponents")) {
                currentLines = jcomponents;
            } else if(line.startsWith("***MouseEvents")) {
                currentLines = mouseevents;
            } else if(line.startsWith("***DragEvents")) {
                currentLines = dragevents;
            } else if(line.startsWith("***OtherEvents")) {
                currentLines = otherevents;
                System.out.println("!!!!!!!!!!!!!!!!added an other events");
            } else {
                return;
            }
            return;
        }

        //Otherwise, try parsing it as a command line
        String[] splitArray = line.split("\\,");
        if(splitArray.length==4) {
            currentLines.add(new helpLine(splitArray));
        }
    }

    private void initComponents() {
        getContentPane().setBackground(new Color(255,246,192));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new BorderLayout());
        buttonpanel.setOpaque(false);
        JLabel namelabel = new JLabel(_toolname);
        namelabel.setFont(new Font("Arial", Font.PLAIN, 22));
        buttonpanel.add(namelabel, BorderLayout.WEST);

        JTextArea bugrep = new JTextArea();
        if(_bugaddress==null || _bugaddress.equals("")) {
            bugrep.setText("No Bug reporting address has been provided for this plugin (sorry).");
        } else {
            bugrep.setText("Send an email to " + _bugaddress + " to report bugs.  Be sure to describe" +
                    " the error, which plugin caused it, and ideally the error report as well.");
        }

        bugrep.setBackground(null);
        bugrep.setBorder(null);
        bugrep.setLineWrap(true);
        bugrep.setOpaque(false);
        bugrep.setWrapStyleWord(true);
        buttonpanel.add(bugrep, BorderLayout.SOUTH);

        if(_url!=null) {
            JButton urlbutton = new JButton();
            urlbutton.setText("Launch help site");
            urlbutton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    BareBonesBrowserLaunch.openURL(_url.toString());
                }
            });
            buttonpanel.add(new JLabel(), BorderLayout.CENTER);
            buttonpanel.add(urlbutton, BorderLayout.EAST);
        }
        getContentPane().add(buttonpanel);

        if(keybindings.size()>0) {
            getContentPane().add(new headerLabel("Key Commands"));
            for(helpLine hl: keybindings) {
                getContentPane().add(new guiLine(hl));
            }
        }

        if(jcomponents.size()>0) {
            getContentPane().add(new headerLabel("Buttons and other visible components"));
            for(helpLine hl: jcomponents) {
                getContentPane().add(new guiLine(hl));
            }
        }

        if(mouseevents.size()>0) {
            getContentPane().add(new headerLabel("Mouse actions"));
            for(helpLine hl: mouseevents) {
                getContentPane().add(new guiLine(hl));
            }
        }

        if(dragevents.size()>0) {
            getContentPane().add(new headerLabel("Drag and drop behavior"));
            for(helpLine hl: dragevents) {
                getContentPane().add(new guiLine(hl));
            }
        }

        if(otherevents.size()>0) {
            getContentPane().add(new headerLabel("Other operations"));
            for(helpLine hl: otherevents) {
                getContentPane().add(new guiLine(hl));
            }
        }

        pack();
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class guiLine extends JPanel {
        public guiLine(helpLine line) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            textGUI sourcelabel = new textGUI(line.source, 100);
            
            textGUI operationlabel = new textGUI(line.operation, 100);
            operationlabel.setFont(new Font("Arial", Font.BOLD, 12));
            textGUI shortDlabel = new textGUI(line.shortD, 100);
            textGUI longDlabel = new textGUI(line.longD, 250);


            add(sourcelabel);
            add(operationlabel);
            add(shortDlabel);
            add(longDlabel);
        }
        
        @Override
        public boolean isOpaque() {
            return false;
        }
    }

    private class textGUI extends JTextArea {
        public textGUI(String line, int preferredWidth) {
            super(line);
            setOpaque(false);
            setLineWrap(true);
            setEditable(false);
            setWrapStyleWord(true);
            setFont(new Font("Arial", Font.PLAIN, 12));
            this.setBounds(0,0,preferredWidth, 200);
        }
    }

    private class headerLabel extends JTextField {
        public headerLabel(String line) {
            setText(line);
            setEditable(false);
            setPreferredSize(new Dimension(550, 30));
            setBackground(new Color(255, 247, 200));
            //255,246,192
            setBorder(null);
            setFont(new Font("Arial", Font.PLAIN, 18));
        }
    }

    private class helpLine {
        public helpLine(String[] list) {
            source = list[0];
            operation = list[1];
            shortD = list[2];
            longD = list[3];
        }
        public String source;
        public String operation;
        public String shortD;
        public String longD;
    }

/*-----------------
     variables
 -----------------*/
    private ArrayList<helpLine> keybindings = new ArrayList<helpLine>();
    private ArrayList<helpLine> jcomponents = new ArrayList<helpLine>();
    private ArrayList<helpLine> mouseevents = new ArrayList<helpLine>();
    private ArrayList<helpLine> dragevents = new ArrayList<helpLine>();
    private ArrayList<helpLine> otherevents = new ArrayList<helpLine>();
    private URL _url;
    private String _toolname;
    private String _bugaddress;
    private String _bugzillaLink;
    private ArrayList<helpLine> currentLines;
}
