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

package org.clothocad.tool.personeditor;

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
import org.clothocore.api.data.Lab;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Person;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocad.wikieditorpanel.WikiEditorPanel;
import org.clothocore.util.basic.ImageSource;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {
    public frame(Person person) {
        super("Edit person: " + person.getName());
        setIconImage(ImageSource.getTinyLogo());
        myperson = person;
        initcomponents();
        obp = new observer();
        person.isRepresentedBy(obp, box);
        new ObjBasePopup(box, person);
    }

    private void initcomponents() {
        getContentPane().setBackground(navyblue);
        getContentPane().setLayout(new BorderLayout());

        //Add a wiki editor for the biography on the right
        WikiEditorPanel wep = new WikiEditorPanel(this, myperson.getBiography());
        wep.setPreferredSize(new Dimension(400,400));
        wep.setHTMLMode();
        getContentPane().add(wep, BorderLayout.CENTER);

        //Add all the other fields on the left
        box = new JPanel();
        BoxLayout blayout = new BoxLayout(box, BoxLayout.Y_AXIS);
        box.setLayout(blayout);
        box.setOpaque(false);

        //Do the display name
        displayName = new blueTextField("Display Name",myperson.getName()) {
            @Override
            public void loseFocus() {
                myperson.changeName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getName());
            }
        };
        box.add(displayName);

        //Do the email
        email = new blueTextField("Email Address",myperson.getEmailAddress()) {
            @Override
            public void loseFocus() {
                myperson.changeEmailAddress(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getEmailAddress());
            }
        };
        box.add(email);

        //Do the given name
        given = new blueTextField("Given Name",myperson.getGivenName()) {
            @Override
            public void loseFocus() {
                myperson.changeGivenName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getGivenName());
            }
        };
        box.add(given);

        //Do the surname
        surname = new blueTextField("Last Name",myperson.getSurName()) {
            @Override
            public void loseFocus() {
                myperson.changeSurName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getSurName());
            }
        };
        box.add(surname);

        //Do the nickname
        nickname = new blueTextField("Nick Name",myperson.getNickName()) {
            @Override
            public void loseFocus() {
                myperson.changeNickName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getNickName());
            }
        };
        box.add(nickname);

        //Do the Registry name
        regname = new blueTextField("Registry Name",myperson.getRegistryName()) {
            @Override
            public void loseFocus() {
                myperson.changeRegistryName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getRegistryName());
            }
        };
        box.add(regname);

        //Do the Registry name
        labname = new blueTextField("Lab Name",myperson.getLab().getName()) {
            @Override
            public void loseFocus() {
                Lab alab = Lab.retrieveByName(getText());
                if(alab!=null) {
                    myperson.changeLab(alab);
                } else {
                    dataUpdated();
                }
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getLab().getName());
            }
        };
        box.add(labname);

        //Do the is administrator
        admin = new blueTextField("Is Administrator", Boolean.toString(myperson.isAdmin())) {
            @Override
            public void loseFocus() {
                try {
                    boolean isadmin = Boolean.parseBoolean(getText());
                    myperson.setAsAdministrator(isadmin);
                } catch(Exception e) {
                    setText(oldValue);
                }
            }
            @Override
            public void dataUpdated() {
                setText(Boolean.toString(myperson.isAdmin()));
            }
        };
        box.add(admin);

        //Do the snail mail
        snailmail = new blueTextArea("Mailing Address", myperson.getSnailMailAddress(), 100) {
            @Override
            public void loseFocus() {
                myperson.changeSnailMailAddress(getText());
            }
            @Override
            public void dataUpdated() {
                setText(myperson.getSnailMailAddress());
            }
        };
        box.add(snailmail);

        box.add(Box.createVerticalStrut(10));

        //Add the buttons
        JPanel buttonpanel = new JPanel();
        box.add(buttonpanel);
        buttonpanel.setOpaque(false);

        //Change password button
        JButton pass = new JButton("Modify Password");
        pass.setMaximumSize(new Dimension(150,23));
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  myperson.clearPassword();
            }
        });
        buttonpanel.add(pass);

        //Save changes button
        JButton save = new JButton("Save Changes");
        save.setMaximumSize(new Dimension(150,23));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myperson.getLab().saveDefault();
                myperson.saveDefault();

                if(!myperson.isChanged()) {
                    GlassMessage msgbox = new GlassMessage("Person saved.", null, 110, 50, glassPanel, Color.WHITE, null);
                    msgbox.setBounds(180,300,110,50);
                    glassPanel.add(msgbox);
                    validate();
                    repaint();
                } else {
                    GlassMessage msgbox = new GlassMessage("Person save failed!", null, 140, 50, glassPanel, Color.PINK, null);
                    msgbox.setBounds(180,300,140,50);
                    glassPanel.add(msgbox);
                    validate();
                    repaint();
                }
            }
        });
        buttonpanel.add(save);

        getContentPane().add(box, BorderLayout.WEST);
        wep.requestFocusInWindow();

        //Put in the glassPanel
        glassPanel = new JPanel();
        glassPanel.setOpaque(false);
        setGlassPane(glassPanel);
        getGlassPane().setVisible(true);
        glassPanel.setLayout(null);

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
                case EMAIL_CHANGED:
                    email.dataUpdated();
                    break;
                case GIVEN_NAME_CHANGED:
                    given.dataUpdated();
                    break;
                case SURNAME_CHANGED:
                    surname.dataUpdated();
                    break;
                case NICKNAME_CHANGED:
                    nickname.dataUpdated();
                    break;
                case REGISTRY_NAME_CHANGED:
                    regname.dataUpdated();
                    break;
                case SECURITY_CHANGED:
                    admin.dataUpdated();
                    break;
                case ADDRESS_CHANGED:
                    snailmail.dataUpdated();
                    break;
                case LAB_CHANGED:
                    labname.dataUpdated();
                    break;
                default:
                    return;
            }
            if(myperson.isChanged()) {
                GlassMessage msgbox = new GlassMessage("Person modified.", null, 125, 50, glassPanel, Color.WHITE, null);
                msgbox.setBounds(10,300,125,50);
                glassPanel.add(msgbox);
                validate();
                repaint();
            }
        }

    }

/*-----------------
     variables
 -----------------*/
    observer obp;
    static final Color navyblue = new Color(35, 48, 64);
    private Person myperson;
    private JPanel box;
    private JPanel glassPanel;
    blueTextField displayName;
    blueTextField email;
    blueTextField given;
    blueTextField surname;
    blueTextField nickname;
    blueTextArea snailmail;
    blueTextField labname;
    blueTextField regname;
    blueTextField admin;
}
