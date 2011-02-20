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

package org.clothocad.tool.formateditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Person;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.misc.BareBonesBrowserLaunch;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {
    public frame(Format format) {
        super("View format: " + format.getName());
        setIconImage(ImageSource.getTinyLogo());
        myformat = format;
        initcomponents();
        obp = new observer();
        myformat.isRepresentedBy(obp, box);
        new ObjBasePopup(box, myformat);
    }

    private void initcomponents() {
        getContentPane().setBackground(navyblue);
        getContentPane().setLayout(new BorderLayout());

        //Add all the other fields on the left
        box = new JPanel();
        BoxLayout blayout = new BoxLayout(box, BoxLayout.Y_AXIS);
        box.setLayout(blayout);
        box.setOpaque(false);

        //Do the  name
        displayName = new blueTextField("Display Name",myformat.getName()) {
            @Override
            public void loseFocus() {
                setText(oldValue);
            }
            @Override
            public void dataUpdated() {
                setText(oldValue);
            }
        };
        displayName.setGreyMode(true);
        box.add(displayName);

        //Do the description
        description = new blueTextField("Short Description", myformat.getShortDescription()) {
            @Override
            public void loseFocus() {
                setText(oldValue);
            }
            @Override
            public void dataUpdated() {
                setText(oldValue);
            }
        };
        description.setGreyMode(true);
        box.add(description);

        //Do the help link
        helplink = new blueTextField("Help Link", myformat.getPluginHelpLink()) {
            @Override
            public void loseFocus() {
                setText(oldValue);
            }
            @Override
            public void dataUpdated() {
                setText(oldValue);
            }
        };
        helplink.setGreyMode(true);
        helplink.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    URL url = new URL(myformat.getPluginHelpLink());
                    BareBonesBrowserLaunch.openURL(myformat.getPluginHelpLink());
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        box.add(helplink);


        //Do the download link
        downloadlink = new blueTextField("Download Link", myformat.getPluginDownloadLink()) {
            @Override
            public void loseFocus() {
                setText(oldValue);
            }
            @Override
            public void dataUpdated() {
                setText(oldValue);
            }
        };
        downloadlink.setGreyMode(true);
        box.add(downloadlink);

   //     box.add(Box.createVerticalStrut(10));

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
                myformat.saveDefault();
            }
        });
  //      buttonpanel.add(save);

        getContentPane().add(box, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private class observer implements ObjBaseObserver {
        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            switch(evt.getCondition()) {
                case NAME_CHANGED:
                    displayName.dataUpdated();
                    break;
                case AUTHOR_CHANGED:
                    description.dataUpdated();
                    break;
                default:
                    return;
            }
        }

    }

/*-----------------
     variables
 -----------------*/
    observer obp;
    static final Color navyblue = new Color(35, 48, 64);
    private Format myformat;
    private JPanel box;
    blueTextField displayName;
    blueTextField description;
    blueTextField helplink;
    blueTextField downloadlink;
}
