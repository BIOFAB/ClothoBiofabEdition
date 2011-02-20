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

package org.clothocad.tool.straineditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.Strain;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Strain.genomeModification;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;

/**
 *
 * @author J. Christopher Anderson
 */
public class frame extends JFrame {
    public frame(Strain strain) {
        super("Edit strain: " + strain.getName());
        setIconImage(ImageSource.getTinyLogo());
        mystrain = strain;
        initcomponents();
        obp = new observer();
        mystrain.isRepresentedBy(obp, box);
        new ObjBasePopup(box, mystrain);
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
        displayName = new blueTextField("Display Name",mystrain.getName()) {
            @Override
            public void loseFocus() {
                mystrain.changeName(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mystrain.getName());
            }
        };
        box.add(displayName);


        //Do the Strain type
        strainTypeField = new blueTextField("Strain Type", mystrain.getStrainType().toString()) {
            @Override
            public void loseFocus() {
                String input = getText();
                try {
                    Strain.strainType stype = Strain.strainType.valueOf(input);
                    mystrain.changeStrainType(stype);
                } catch(Exception err) {
                    setText(oldValue);
                }
            }
            @Override
            public void dataUpdated() {
                setText(mystrain.getStrainType().toString());
            }
        };
        if(!mystrain.isBasic()) {
            strainTypeField.setGreyMode(true);
        }
        box.add(strainTypeField);

        //Do the description
        descriptionField = new blueTextArea("Genotype", mystrain.getDescription(), 70) {
            @Override
            public void loseFocus() {
                mystrain.changeDescription(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mystrain.getDescription());
            }
        };
        box.add(descriptionField);

        //Do the author
        author = new blueTextField("Author name", mystrain.getAuthor().getDisplayName()) {
            @Override
            public void loseFocus() {
                Person aperson = Person.retrieveByName(getText());
                if(aperson==null) {
                    setText(oldValue);
                }
                mystrain.changeAuthor(aperson);
            }
            @Override
            public void dataUpdated() {
                setText(mystrain.getAuthor().getDisplayName());
            }
        };
        box.add(author);

        //Do the parent Strain
        String parentlabel = "(Basic Strain)";
        if(!mystrain.isBasic()) {
            parentlabel = mystrain.getParentStrain().getName();
        }
        parentstrain = new blueTextField("Parent Strain", parentlabel) {
            @Override
            public void loseFocus() {
                Strain astrain = Strain.retrieveByName(getText());
                if(astrain==null) {
                    setText(oldValue);
                }
                mystrain.changeParentStrain(astrain);
            }
            @Override
            public void dataUpdated() {
                setText(mystrain.getAuthor().getDisplayName());
            }
        };
        if(mystrain.isBasic()) {
            parentstrain.setGreyMode(true);
        }
        box.add(parentstrain);

        //Do the risk group
        riskGroupField = new blueTextField("Risk Group", Short.toString(mystrain.getRiskGroup())) {
            @Override
            public void loseFocus() {
                try {
                    Short newvalue = Short.parseShort(getText());
                    mystrain.changeRiskGroup(newvalue);
                } catch(Exception err) {
                    setText(Short.toString(mystrain.getRiskGroup()));
                }
            }
            @Override
            public void dataUpdated() {
                setText(Short.toString(mystrain.getRiskGroup()));
            }
        };
        box.add(riskGroupField);

        //Do the Genome id
        genomeIdField = new blueTextField("Genbank Id", mystrain.getGenomeID()) {
            @Override
            public void loseFocus() {
                mystrain.changeGenomeID(getText());
            }
            @Override
            public void dataUpdated() {
                setText(mystrain.getGenomeID());
            }
        };
        if(!mystrain.isBasic()) {
            genomeIdField.setGreyMode(true);
        }
        box.add(genomeIdField);
/*
        public boolean _isBasic = true;  //Default is a basic Strain  NOTE INCLUDING THIS
        public String _genbankFileUUID;           //Only populated for basic

        public HashSet<String> _sampleLinks = new HashSet<String>();
 */

        noteListBox = new Box(BoxLayout.X_AXIS);
        box.add(noteListBox);
        putInNotes();

        box.add(Box.createVerticalStrut(10));

        episomeListBox = new Box(BoxLayout.X_AXIS);
        box.add(episomeListBox);
        putInEpisomes();

        putInGenomicPlasmids();

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
                mystrain.saveDefault();
            }
        });
        buttonpanel.add(save);

        getContentPane().add(box, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void putInNotes() {
        HashSet<String> allnotelinks = mystrain.getNoteLinks();
        noteListBox.removeAll();
        System.out.println("Putting in the notes, list is x long: " + allnotelinks.size());

        //Create the label
        JLabel noteLabel = new JLabel("Notes linked:");
        noteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        noteLabel.setForeground(Color.WHITE);
        noteListBox.add(noteLabel);

        noteListBox.add(Box.createHorizontalStrut(5));

        for(String uuid : allnotelinks) {
            System.out.println("putting in a note");
            noteButton nb = new noteButton(uuid);
            noteListBox.add(nb);
        }
        noteListBox.add(Box.createVerticalStrut(2));
        validate();
        repaint();
    }

    private class noteButton extends TransparentButton {
        public noteButton(String auuid) {
            super(noteIcon);
            setExitAlpha(1.0f);
            setEnterAlpha(0.8f);
            uuid = auuid;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if(anote==null) {
                        anote = Collector.getNote(uuid);
                        setToolTipText(uuid);
                    }
                    if(anote!=null) {
                        setToolTipText(anote.getName());
                    }
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(anote==null) {
                        anote = Collector.getNote(uuid);
                    }
                    if(anote!=null) {
                        mystrain.removeNote(anote);
                    }
                }
            });
        }

        private String uuid;
        private Note anote;
    }

    private void putInEpisomes() {
        HashSet<String> allepis = mystrain.getEpisomalPlasmidsLinks();
        episomeListBox.removeAll();
        System.out.println("Putting in the notes, list is x long: " + allepis.size());

        //Create the label
        JLabel epiLabel = new JLabel("Episomal Plasmids:");
        epiLabel.setFont(new Font("Arial", Font.BOLD, 14));
        epiLabel.setForeground(Color.WHITE);
        episomeListBox.add(epiLabel);

        episomeListBox.add(Box.createHorizontalStrut(5));

        for(String uuid : allepis) {
            System.out.println("putting in a note");
            episomeButton nb = new episomeButton(uuid);
            episomeListBox.add(nb);
        }
        episomeListBox.add(Box.createVerticalStrut(2));
        validate();
        repaint();
    }

    private class episomeButton extends TransparentButton {
        public episomeButton(String auuid) {
            super(plasmidIcon);
            setExitAlpha(1.0f);
            setEnterAlpha(0.8f);
            uuid = auuid;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if(aplas==null) {
                        aplas = Collector.getPlasmid(uuid);
                        setToolTipText(uuid);
                    }
                    if(aplas!=null) {
                        setToolTipText(aplas.getName());
                    }
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(aplas==null) {
                        aplas = Collector.getPlasmid(uuid);
                    }
                    if(aplas!=null) {
                        mystrain.removePlasmid(aplas);
                    }
                }
            });
        }

        private String uuid;
        private Plasmid aplas;
    }

    private void putInGenomicPlasmids() {
        System.out.println("Putting in genome modifications");
        HashSet<genomeModification> gms = mystrain.getGenomeModifications();
        for(genomeModification gm : gms) {
            System.out.println("Strain editor adding a genome modification of " + gm._plasmidLink);
            box.add(new genomicField(gm));
        }
        validate();
        repaint();
    }

    private class observer implements ObjBaseObserver {
        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            switch(evt.getCondition()) {
                case NAME_CHANGED:
                    displayName.dataUpdated();
                    break;
                case AUTHOR_CHANGED:
                    author.dataUpdated();
                    break;
                case DESCRIPTION_CHANGED:
                    descriptionField .dataUpdated();
                    break;
                case TYPE_CHANGED:
                    strainTypeField  .dataUpdated();
                    break;
                case PARENT_STRAIN_CHANGED:
                    parentstrain.dataUpdated();
                    break;
                case RISK_GROUP_CHANGED:
                    riskGroupField .dataUpdated();
                    break;
                case GENBANK_CHANGED:
                    genomeIdField.dataUpdated();
                    break;
                case NOTE_LINKED:
                    putInNotes();
                    break;
                case PLASMID_TO_STRAIN:
                    putInEpisomes();
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
    private Strain mystrain;
    private JPanel box;
    blueTextField displayName;
    blueTextField strainTypeField;
    blueTextArea descriptionField;
    blueTextField author;
    blueTextField riskGroupField;
    blueTextField parentstrain;
    blueTextField genomeIdField;

    static final ImageIcon noteIcon = ImageSource.getObjectImage(ObjType.NOTE, 21);
    static final ImageIcon plasmidIcon = ImageSource.getObjectImage(ObjType.PLASMID, 21);

    Box noteListBox;
    Box episomeListBox;
}


//need to relay remove note into note
