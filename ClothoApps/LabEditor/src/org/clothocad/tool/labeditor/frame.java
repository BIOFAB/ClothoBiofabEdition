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

package org.clothocad.tool.labeditor;

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
import org.clothocore.api.data.Lab;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Person;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {
    public frame(Lab lab) {
        super("Edit lab: " + lab.getName());
        mylab = lab;
        initcomponents();
        obp = new observer();
        mylab.isRepresentedBy(obp, box);
        new ObjBasePopup(box, mylab);
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
        displayName = new blueTextField("Display Name",mylab.getName()) {
            @Override
            public void loseFocus() {
                mylab.changeName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mylab.getName());
            }
        };
        box.add(displayName);

        //Do the department
        department = new blueTextField("Department name",mylab.getDepartment()) {
            @Override
            public void loseFocus() {
                mylab.changeDepartment(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mylab.getDepartment());
            }
        };
        box.add(department);

        //Do the website
        website = new blueTextField("Website",mylab.getWebsite()) {
            @Override
            public void loseFocus() {
                mylab.changeWebsite(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mylab.getWebsite());
            }
        };
        box.add(website);

        //Do the principal investigator
        String pistring = "";
        Person piperson = mylab.getPI();
        if(piperson!=null) {
            pistring = piperson.getName();
        }
        piname = new blueTextField("Principal Investigator", pistring) {
            @Override
            public void loseFocus() {
                Person princinv = Person.retrieveByName(getText());
                if(princinv!=null) {
                    mylab.changePI(princinv);
                } else {
                    dataUpdated();
                }
            }
            @Override
            public void dataUpdated() {
                String pistring = "";
                Person piperson = mylab.getPI();
                if(piperson!=null) {
                    pistring = piperson.getName();
                }
                setText(pistring);
            }
        };
        box.add(piname);

        //Do the Institution
        instname = new blueTextField("Institution Name",mylab.getInstitution().getName()) {
            @Override
            public void loseFocus() {
                Institution inst = Institution.retrieveByName(getText());
                if(inst!=null) {
                    mylab.changeInstitition(inst);
                } else {
                    dataUpdated();
                }
            }
            @Override
            public void dataUpdated() {
                setText(mylab.getInstitution().getName());
            }
        };
        box.add(instname);

        //Do the snail mail
        snailmail = new blueTextArea("Mailing Address", mylab.getAddress(), 100) {
            @Override
            public void loseFocus() {
                mylab.changeAddress(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mylab.getAddress());
            }
        };
        box.add(snailmail);

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
                mylab.saveDefault();
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
                case ADDRESS_CHANGED:
                    snailmail.dataUpdated();
                    break;
                case WEBSITE_CHANGED:
                    website.dataUpdated();
                    break;
                case DEPARTMENT_CHANGED:
                    department.dataUpdated();
                    break;
                case PI_CHANGED:
                    piname.dataUpdated();
                    break;
                case INSTITUTION_CHANGED:
                    instname.dataUpdated();
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
    private Lab mylab;
    private JPanel box;
    blueTextField displayName;
    blueTextField website;
    blueTextField department;
    blueTextField instname;
    blueTextField piname;
    blueTextArea snailmail;



}
