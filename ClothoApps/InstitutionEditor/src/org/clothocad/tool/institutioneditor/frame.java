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

package org.clothocad.tool.institutioneditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.clothocore.api.data.Institution;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {
    public frame(Institution inst) {
        super("Edit institution: " + inst.getName());
        setIconImage(ImageSource.getTinyLogo());
        myinst = inst;
        initcomponents();
        obp = new observer();
        myinst.isRepresentedBy(obp, box);
        new ObjBasePopup(box, myinst);
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
        displayName = new blueTextField("Display Name",myinst.getName()) {
            @Override
            public void loseFocus() {
                myinst.changeName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myinst.getName());
            }
        };
        box.add(displayName);

        //Do the city
        city = new blueTextField("City",myinst.getCity()) {
            @Override
            public void loseFocus() {
                myinst.changeCity(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myinst.getCity());
            }
        };
        box.add(city);

        //Do the state
        state = new blueTextField("State",myinst.getState()) {
            @Override
            public void loseFocus() {
                myinst.changeState(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myinst.getState());
            }
        };
        box.add(state);

        //Do the country
        country = new blueTextField("Country",myinst.getCountry()) {
            @Override
            public void loseFocus() {
                myinst.changeCountry(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myinst.getCountry());
            }
        };
        box.add(country);

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
                myinst.saveDefault();
            }
        });
        buttonpanel.add(save);

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
                case CITY_CHANGED:
                    city.dataUpdated();
                    break;
                case STATE_CHANGED:
                    state.dataUpdated();
                    break;
                case COUNTRY_CHANGED:
                    country.dataUpdated();
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
    private Institution myinst;
    private JPanel box;
    blueTextField displayName;
    blueTextField city;
    blueTextField state;
    blueTextField country;



}
