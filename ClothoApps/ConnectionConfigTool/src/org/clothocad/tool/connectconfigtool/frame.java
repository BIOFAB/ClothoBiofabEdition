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

package org.clothocad.tool.connectconfigtool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.xml.XMLParser;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {

    public frame() {
        super("Configure Connection");
        setIconImage(ImageSource.getTinyLogo());
        InputStream stream = null;
        try {
            configFile = FileUtil.getConfigFile("data/private/org.clothocad.connection.configurableconnection/").getFileObject("hibernate.cfg.xml");
            stream = configFile.getInputStream();
            XMLParser parser = new XMLParser(stream, "hibernate-configuration");
            List<Element> elements = parser.getNodesOfTag("session-factory");
            Element root = elements.get(0);
            NodeList listy = root.getChildNodes();

            server = listy.item(5).getTextContent();
            database = listy.item(11).getTextContent();
            login = listy.item(7).getTextContent();
            password = listy.item(9).getTextContent();

            int end = server.indexOf(":3306");
            String tempy = server.substring(13,end);
            server = tempy;

            for(int i=0; i< listy.getLength(); i++) {
                Node anode = listy.item(i);
                System.out.println("e.getNodeName():" + i + " is " + anode.getTextContent());
                //5 is jdbc:mysql://andersonlab.qb3.berkeley.edu:3306/WillsPlayground\
                //7 is pobol
                //9 is biobrick
                //11 is WillsPlayground
            }
            initcomponents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(stream!=null) {
            try {
                stream.close();
            } catch (IOException ex1) {
            }
        }
    }

    private void initcomponents() {

        getContentPane().setBackground(navyblue);
        getContentPane().setLayout(new BorderLayout());

        //Add all the other fields on the left
        box = new JPanel();
        BoxLayout blayout = new BoxLayout(box, BoxLayout.Y_AXIS);
        box.setLayout(blayout);
        box.setOpaque(false);

        //Do the server field
        serverfield = new blueTextField("Server", server) {
            @Override
            public void loseFocus() {
                server = getText();
            }
            @Override
            public void dataUpdated() {
                setText(server);
            }
        };
        box.add(serverfield);

        //Do the database field
        databasefield = new blueTextField("Database", database) {
            @Override
            public void loseFocus() {
                database = getText();
            }
            @Override
            public void dataUpdated() {
                setText(database);
            }
        };
        box.add(databasefield);


        //Do the login field
        loginfield = new blueTextField("Login", login) {
            @Override
            public void loseFocus() {
                login = getText();
            }
            @Override
            public void dataUpdated() {
                setText(login);
            }
        };
        box.add(loginfield);

        //Do the password field
        passwordfield = new blueTextField("Password", password) {
            @Override
            public void loseFocus() {
                password = getText();
            }
            @Override
            public void dataUpdated() {
                setText(password);
            }
        };
        box.add(passwordfield);

        box.add(Box.createVerticalStrut(10));

        //Add the buttons
        JPanel buttonpanel = new JPanel();
        box.add(buttonpanel);
        buttonpanel.setOpaque(false);

        //Save changes button
        JButton save = new JButton("Save Changes");
        save.setMaximumSize(new Dimension(150,23));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        buttonpanel.add(save);

        getContentPane().add(box, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void saveChanges() {
        //Make sure things have all updated fields before running save
        databasefield.requestFocusInWindow();
        loginfield.requestFocusInWindow();

        //Write the document to the file
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!DOCTYPE hibernate-configuration PUBLIC \"-//Hibernate/Hibernate Configuration DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd\">");
        sb.append("<hibernate-configuration>\n");
        sb.append("  <session-factory>\n");
        sb.append("    <property name=\"hibernate.dialect\">org.hibernate.dialect.MySQLDialect</property>\n");
        sb.append("    <property name=\"hibernate.connection.driver_class\">com.mysql.jdbc.Driver</property>\n");
        sb.append("    <property name=\"hibernate.connection.url\">jdbc:mysql://");
        sb.append(server);
        sb.append(":3306/");
        sb.append(database);
        sb.append("</property>\n");
        sb.append("    <property name=\"hibernate.connection.username\">");
        sb.append(login);
        sb.append("</property>\n");
        sb.append("    <property name=\"hibernate.connection.password\">");
        sb.append(password);
        sb.append("</property>\n");
        sb.append("    <property name=\"hibernate.default_catalog\">");
        sb.append(database);
        sb.append("</property>\n");
        sb.append("  </session-factory>");
        sb.append("</hibernate-configuration>");

        //System.out.println(sb.toString());

        OutputStream ostream = null;
        try {
            ostream = configFile.getOutputStream();
            ostream.write(sb.toString().getBytes());
            dispose();
        } catch(Exception e) {
            System.out.println("Error saving changes to database configuration");
            e.printStackTrace();
        }
        if(ostream!=null) {
            try {
                ostream.close();
            } catch (IOException ex) {
            }
        }
    }
/*-----------------
     variables
 -----------------*/
        FileObject configFile;
        static final Color navyblue = new Color(35, 48, 64);
        private JPanel box;

        blueTextField serverfield;
        blueTextField loginfield;
        blueTextField passwordfield;
        blueTextField databasefield;

        private String server;
        private String database;
        private String login;
        private String password;
}
