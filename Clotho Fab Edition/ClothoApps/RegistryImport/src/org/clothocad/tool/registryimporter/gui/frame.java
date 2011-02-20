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

package org.clothocad.tool.registryimporter.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Part;
;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {

    public frame() {
        super("Registry Importer");
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        ImageIcon bkgImage = ImageUtilities.loadImageIcon( "org/clothocad/tool/registryimporter/gui/bkg.png", false );
        JLabel bkgLabel = new JLabel(bkgImage);
        bkgLabel.setBounds(0, 0, bkgImage.getIconWidth(), bkgImage.getIconHeight());

        JPanel contents = (JPanel) getContentPane();
        contents.add(bkgLabel);
        setPreferredSize(new Dimension(bkgImage.getIconWidth(), bkgImage.getIconHeight()));
        setResizable(false);

        ImageIcon blurImage = ImageUtilities.loadImageIcon( "org/clothocad/tool/registryimporter/gui/blur.png", false );
        JLabel blurrer = new JLabel(blurImage);
        glass = (JPanel) getGlassPane();
        glass.setLayout(new BorderLayout());
        glass.setOpaque(false);
        glass.add(blurrer, BorderLayout.CENTER);
        glass.setVisible(false);

        JLabel label = new JLabel("Registry part name to retrieve (Ex: BBa_I13521: ");
        label.setBounds(5,97,350,25);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.white);
        getLayeredPane().add(label);

        JLabel label2 = new JLabel("Include non-feature annotations?");
        label2.setBounds(5,120,220,25);
        label2.setFont(new Font("Arial", Font.PLAIN, 14));
        label2.setForeground(Color.white);
        getLayeredPane().add(label2);

        final JCheckBox jcb = new JCheckBox();
        jcb.setBounds(230,120,20,20);
        jcb.setBackground(navyblue);
        getLayeredPane().add(jcb);

        JButton browser = new JButton("Browse Parts");
        browser.setBounds(320,120,140,20);
        browser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        glass.setVisible(true);
                        ArrayList<String> pargrab = new allParts().getAllParts();
                        if(pargrab==null) {
                            return null;
                        }
                        if(pargrab.isEmpty()) {
                            return null;
                        }
                        String[] names = new String[pargrab.size()];
                        pargrab.toArray(names);

                        Object selectedValue = JOptionPane.showInputDialog( null, "Choose one", "Choose current user", JOptionPane.INFORMATION_MESSAGE, null, names, names[0] );
                        try {
                            chosen = (String) selectedValue;
                        } catch(Exception err) {
                            err.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        glass.setVisible(false);
                        if(chosen!=null) {
                            inputField.setText(chosen);
                        }
                    }

                    //VARIABLES:
                    String chosen;
                }.execute();
            }
        });
        getLayeredPane().add(browser);

        inputField = new JTextField();
        inputField.setBounds(350,97,110,20);
        //Listen for ctrl-O
        inputField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "search");
        inputField.getActionMap().put("search",
            new AbstractAction("search") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                glass.setVisible(true);
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        String textin = inputField.getText().trim();
                        if(textin.equals("") || textin==null) {
                            return null;
                        }
                        if(!textin.startsWith("BBa_")) {
                            String t = "BBa_" + textin;
                            textin = t;
                        }

                        registryPart rp = new registryPart(textin, Collector.getFormat("org-clothocad-format-rfc10-connect"), jcb.isSelected());
                        glass.setVisible(false);
                        Part newpart = rp.getPart();
                        if(newpart!=null) {
                            newpart.launchDefaultViewer();
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        glass.setVisible(false);
                    }
                }.execute();
            }});
        getLayeredPane().add(inputField);

        JMenuItem newWindow = new JMenuItem();
        newWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        newWindow.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("get help");
            }
        });
        getRootPane().add(newWindow);



        
        pack();
        setVisible(true);
    }

/*-----------------
     variables
 -----------------*/
    JTextField inputField;
    private JPanel glass;
    static Color navyblue = new Color(35, 48, 64);
}
