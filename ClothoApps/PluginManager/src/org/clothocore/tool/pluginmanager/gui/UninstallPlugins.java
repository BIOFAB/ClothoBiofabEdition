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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.PluginWrapper;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.Grammar;
import org.clothocore.api.data.ObjType;



/**
 * This is the panel with the images and the button label above them
 * @author jcanderson
 */
public class UninstallPlugins extends JPanel implements FancyPanel {

    public UninstallPlugins(ApplicationFrame app) {
        _app = app;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        //Listen for closing of window
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getY()<50) {
                    _app.close();
                    return;
                }
                return;
            } });

        String[] data = new String[] {"Choose plugin type..."};

        dataList = new JList(data);
        dataList.setSelectionBackground(Color.lightGray);
        dataList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                putDescriptionInfo();
            }
        });
        JScrollPane scrollPane = new JScrollPane(dataList);
        scrollPane.setBounds(155,50, 200, 200);
        add(scrollPane);

        descField = new JTextArea();
        descField.setBounds(375, 50, 200, 200);
        descField.setBorder(null);
        descField.setOpaque(false);
        descField.setFont(new Font("Dialog", Font.PLAIN, 16));
        descField.setForeground(Color.WHITE);
        descField.setEditable(false);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        descField.setBackground(null);
        add(descField);

        prettyButton uninstallButton = new prettyButton("Uninstall");
        uninstallButton.setBounds(160, 260, 100, 26);
        uninstallButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uninstallSelected();
            }
        });
        add(uninstallButton);

        prettyButton toolButton = new prettyButton("Tools");
        toolButton.setBounds(30, 50, 100, 26);
        toolButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList alltools = Collator.getAllTools();
                putListOfPlugins(alltools);
            }
        });
        add(toolButton);

        prettyButton viewerButton = new prettyButton("Viewers");
        viewerButton.setBounds(30, 85, 100, 26);
        viewerButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList alltools = Collator.getAllViewers();
                putListOfPlugins(alltools);
            }
        });
        add(viewerButton);

        prettyButton widgetButton = new prettyButton("Widgets");
        widgetButton.setBounds(30, 120, 100, 26);
        widgetButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList alltools = Collator.getAllWidgets();
                putListOfPlugins(alltools);
            }
        });
        add(widgetButton);

        prettyButton algorithmButton = new prettyButton("Algorithms");
        algorithmButton.setBounds(30, 155, 100, 26);
        algorithmButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList alltools = Collator.getAllAlgorithms();
                putListOfPlugins(alltools);
            }
        });
        add(algorithmButton);

        prettyButton connectionButton = new prettyButton("Connections");
        connectionButton.setBounds(30, 190, 100, 26);
        connectionButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList alltools = Collator.getAllConnections();
                putListOfPlugins(alltools);
            }
        });
        add(connectionButton);

        prettyButton grammarButton = new prettyButton("Grammars");
        grammarButton.setBounds(30, 225, 100, 26);
        grammarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!Collector.isConnected()) {
                    return;
                }
                 @SuppressWarnings (value="unchecked")
                ArrayList<Grammar> grams = Collector.getAll(ObjType.GRAMMAR);
                putListOfPlugins(grams);
            }
        });
        add(grammarButton);

        prettyButton formatButton = new prettyButton("Formats");
        formatButton.setBounds(30, 260, 100, 26);
        formatButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!Collector.isConnected()) {
                    return;
                }
                 @SuppressWarnings (value="unchecked")
                ArrayList<Format> forms = Collector.getAll(ObjType.FORMAT);
                putListOfPlugins(forms);
            }
        });
        add(formatButton);
    }

    private void putListOfPlugins(final ArrayList listy) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dataList.setListData(listy.toArray());
            }
        });
    }

    private void putTextForDesc(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                descField.setText(text);
            }
        });
    }

    private void putDescriptionInfo() {
        System.out.println(dataList.getSelectedValue());
        if(dataList.getSelectedValue()==null) {
            putTextForDesc("");
            return;
        }
        try {
            PluginWrapper cw = (PluginWrapper) dataList.getSelectedValue();
            String desc = cw.getDescription();
            putTextForDesc(desc);
            return;
        } catch(java.lang.ClassCastException e) {
        }

        try {
            Format obj = (Format) dataList.getSelectedValue();
            String desc = obj.getShortDescription();
            putTextForDesc(desc);
            return;
        } catch(java.lang.ClassCastException e) {
        }

        try {
            Grammar obj = (Grammar) dataList.getSelectedValue();
            String desc = obj.getShortDescription();
            putTextForDesc(desc);
            return;
        } catch(java.lang.ClassCastException e) {
        }
    }

    private void uninstallSelected() {
        ArrayList<String> listy = new ArrayList<String>();
        listy.add("Choose plugin type...");
        try {
            PluginWrapper cw = (PluginWrapper) dataList.getSelectedValue();
            if(doTheUninstall(cw)) {
                putListOfPlugins(listy);
            }
            return;
        } catch(java.lang.ClassCastException e) {
        }

        try {
            Format obj = (Format) dataList.getSelectedValue();
            System.out.println(obj.getUUID());
            //NEED TO SEE IF ANY PARTS, VECTORS, PLASMIDS ARE THIS FORMAT VIA A QUERY
            //NEED TO QUERY IF ANY GRAMMARS POINT TO THIS FORMAT
            putListOfPlugins(listy);
            return;
        } catch(java.lang.ClassCastException e) {
        }

        try {
            Grammar obj = (Grammar) dataList.getSelectedValue();
            System.out.println(obj.getUUID());
            //NEED TO SEE IF ANY PARTS ARE THIS GRAMMAR
            putListOfPlugins(listy);
            return;
        } catch(java.lang.ClassCastException e) {
        }
    }

    private boolean doTheUninstall(PluginWrapper cw) {
        if(cw==null) {
            System.out.println("Plugin manager uninstallPlugins has no pluginWrapper");
            return false;
        }
        System.out.println("Plugin manager uninstallPlugins has " + cw.getUUID());
        int n = JOptionPane.showConfirmDialog( null, "Are you sure you want to uninstall " + cw.getDisplayName() + "? This cannot be undone.", "Uninstall", JOptionPane.YES_NO_OPTION );
        if ( n == 0 ) {
            if(Collator.uninstall(cw)) {
                Collator.refreshDash();
                return true;
            }
        }
        return false;
    }

    private class prettyButton extends JButton {
        private String _title;

        public prettyButton(String title) {
            _title = title;
            setBorder(null);
        }

        @Override
        public boolean isOpaque() {
            return false;
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //This is the height and width of the little bullet
            double bulletWidth = 100.0;
            double bulletHeight = 26.0;

            drawAvatarBullet(g2,0, 0, bulletWidth, bulletHeight);
            drawAvatarText(g2, 0, bulletHeight);
        }

        private void drawAvatarBullet(Graphics2D g2,
                                      double x, double y,
                                      double bulletWidth, double bulletHeight) {
            RoundRectangle2D bullet = new RoundRectangle2D.Double(0.0, 0.0,
                                                                  bulletWidth, bulletHeight,
                                                                  bulletHeight, bulletHeight);
            Ellipse2D curve = new Ellipse2D.Double(-20.0, bulletHeight / 2.0,
                                                   bulletWidth + 120.0, bulletHeight);

            g2.translate(x, y);

            g2.translate(-1, -2);
            g2.setColor(new Color(0, 0, 0, 170));
            g2.fill(new RoundRectangle2D.Double(0.0, 0.0,
                                                bulletWidth + 2, bulletHeight + 4,
                                                bulletHeight + 4, bulletHeight + 4));
            g2.translate(1, 2);

            Color startColor = new Color(30, 30, 30);
            Color endColor = new Color(79, 83, 77);

            Paint paint = g2.getPaint();
            g2.setPaint(new GradientPaint(0.0f, 0.0f, startColor,
                                          0.0f, (float) bulletHeight, endColor));
            g2.fill(bullet);

            startColor = new Color(50, 50, 50);
            endColor = new Color(70, 70, 70);
            g2.setPaint(new GradientPaint(0.0f, 0.0f, startColor,
                                          0.0f, (float) bulletHeight, endColor));

            Area area = new Area(bullet);
            area.intersect(new Area(curve));
            g2.fill(area);

            g2.setPaint(paint);
            g2.translate(-x, -y);
        }

        private void drawAvatarText(Graphics2D g2, double y, double bulletHeight) {
            FontRenderContext context = g2.getFontRenderContext();
            Font font = new Font("Dialog", Font.PLAIN, 16);
            TextLayout layout = new TextLayout(_title, font, context);
            Rectangle2D bounds = layout.getBounds();

            float text_x = (float) ((getWidth() - bounds.getWidth()) / 2.0);
            float text_y = (float) (y + (bulletHeight - layout.getAscent() -
                                         layout.getDescent()) / 2.0) +
                                         layout.getAscent() - layout.getLeading();

            g2.setColor(Color.BLACK);
            layout.draw(g2, text_x, text_y + 1);
            g2.setColor(Color.WHITE);
            layout.draw(g2, text_x, text_y);
        }
    }



    /**************
     * Variables
     **************/

    private ApplicationFrame _app;
    private JList dataList;
    private JTextArea descField;



    /**************
     * The rest here is meaningless
     **************/


    @Override
    public void setAmount(int amount) {
    }

    // XXX package access for debugging purpose only
    @Override
    public void setPosition(double position) {
    }

    @Override
    public void setSigma(double sigma) {
    }

    @Override
    public void setSpacing(double avatarSpacing) {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(128 * 3, 128 * 2);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFocusable() {
        return true;
    }

    @Override
    public double computeModifier(double d) {
        return 0.1;
    }

}