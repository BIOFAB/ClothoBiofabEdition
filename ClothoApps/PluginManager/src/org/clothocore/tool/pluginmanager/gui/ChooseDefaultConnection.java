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

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.wrapper.ConnectionWrapper;



/**
 * This is the panel with the images and the button label above them
 * @author jcanderson
 */
public class ChooseDefaultConnection extends JPanel implements FancyPanel {


    public ChooseDefaultConnection(ApplicationFrame app) {
        _app = app;

        initComponents();
    }

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

    private void initComponents() {
        setLayout(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getY()<50) {
                    _app.close();
                    return;
                }
                return;
            } });

        ArrayList<ConnectionWrapper> allcon = Collator.getAllConnections();

        String[] data = new String[allcon.size()];
        for(int i = 0; i< allcon.size(); i++) {
            data[i] = allcon.get(i).getDisplayName();
            _connections.put(data[i], allcon.get(i));
        }

        dataList = new JList(data);
        JScrollPane scrollPane = new JScrollPane(dataList);
        scrollPane.setBounds(30,30, 200, 200);
        add(scrollPane);

        JButton selectButton = new JButton();
        selectButton.setText("Set as default");
        selectButton.setBounds(250, 50, 100, 27);
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultSelected();
            }
        });
        add(selectButton);
    }
    
    private void defaultSelected() {
        String selectstring = (String) dataList.getSelectedValue();
        ConnectionWrapper cw = _connections.get(selectstring);
        Collator.setDefaultConnection(cw);
    }

    /**************
     * Variables
     **************/

    private ApplicationFrame _app;
    private JList dataList;
    Hashtable<String, ConnectionWrapper> _connections = new Hashtable<String, ConnectionWrapper>();
}