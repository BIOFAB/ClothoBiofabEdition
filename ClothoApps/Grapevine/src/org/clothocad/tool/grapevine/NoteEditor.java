package org.clothocad.tool.grapevine;

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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Attachment;
import org.clothocore.api.data.Factoid;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocad.wikieditorpanel.WikiEditorPanel;
import org.clothocore.api.data.Family;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Strain;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.buttons.TransparentButton;
import org.clothocore.util.misc.CommandHelp;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author J. Christopher Anderson
 */
public class NoteEditor extends JFrame {
    public NoteEditor(Note note) {
        super("Grapevine Note Editor");
        _note = note;
        setIconImage(ImageSource.getTinyLogo());
        initComponents();
        putInFactoids();
        noteListener = new ObjBaseObserver() {
            @Override
            public void update(ObjBase obj, RefreshEvent evt) {
                if(evt.isCondition(RefreshEvent.Condition.FACTOID_TO_NOTE)
                        || evt.isCondition(RefreshEvent.Condition.NOTE_LINKED)
                        || evt.isCondition(RefreshEvent.Condition.UPDATE_ALL)) {
                    putInFactoids();
                }
                if(evt.isCondition(RefreshEvent.Condition.NAME_CHANGED)
                        || evt.isCondition(RefreshEvent.Condition.UPDATE_ALL)) {
                    noteTitleField.setText(_note.getName());
                }
                if(evt.isCondition(RefreshEvent.Condition.NOTE_LINKED)) {
                    putLinksToBiologicals();
                }
            }
        };
        _note.isRepresentedBy(noteListener, headerPanel);
    }

    public void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );

        createHeader();
        createMenuBar();
        topPanel.add(headerPanel, BorderLayout.NORTH);

        notesPanel = new JPanel();
        notesPanel.setLayout(new BorderLayout());
        JScrollPane noteScroller = new JScrollPane();
        CommentScrollPanel = new ScrollablePanel();
        noteScroller.setViewportView(CommentScrollPanel);
        noteScroller.setWheelScrollingEnabled(true);
        CommentScrollPanel.setLayout(new BoxLayout(CommentScrollPanel, BoxLayout.PAGE_AXIS));
        CommentScrollPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        CommentScrollPanel.setScrollableHeight(ScrollablePanel.ScrollableSizeHint.NONE);
        notesPanel.add(noteScroller);

        topPanel.add(notesPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(550, 600));
        pack();
        addFactoid.requestFocus();
        setVisible( true );
    }

    private void createHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout( new BorderLayout() );
        headerPanel.setBackground(navyblue);

        noteTitleField = new blueTextField(18) {
            @Override
            public void gainFocus() {
            }

            @Override
            public void loseFocus() {
                if(oldValue.equals(this.getText())) {
                    return;
                }
                oldValue = this.getText();
                _note.changeName(oldValue);
            }

            @Override
            public void dataUpdated() {
                this.setText(_note.getName());
            }
        };
        noteTitleField.setText(_note.getName());
        headerPanel.add(noteTitleField, BorderLayout.NORTH);

        noteWTEd = new WikiEditorPanel(this, _note.getWikiText());
        noteWTEd.setHTMLMode();
        headerPanel.add(noteWTEd, BorderLayout.CENTER);

        Box newNoteFactBox = new Box(BoxLayout.X_AXIS);
        headerPanel.add( newNoteFactBox, BorderLayout.SOUTH );

        addFactoid = new JButton("Add new Factoid...");
        addFactoid.setIcon(factoidIcon);
        addFactoid.setBackground(navyblue);
        addFactoid.setForeground(Color.WHITE);
        addFactoid.setBorder(null);
        addFactoid.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _note.addObject(new Factoid("New factoid", Collector.getCurrentUser(), "", null));
            }
        });
        new ObjBasePopup(addFactoid, _note);
        newNoteFactBox.add(addFactoid);
        newNoteFactBox.add(Box.createHorizontalStrut(13));

        addSubNote = new JButton("Add new sub-note...");
        addSubNote.setIcon(noteIcon);
        addSubNote.setBackground(navyblue);
        addSubNote.setForeground(Color.WHITE);
        addSubNote.setBorder(null);
        addSubNote.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _note.addObject(new Note("New note", Collector.getCurrentUser(), null));
            }
        });
        new ObjBasePopup(addSubNote, _note);
        newNoteFactBox.add(addSubNote);
        newNoteFactBox.add(Box.createHorizontalStrut(13));

        addFeature = new JButton("Link to:");
        addFeature.setBackground(navyblue);
        addFeature.setForeground(Color.WHITE);
        addFeature.setBorder(null);
        addFeature.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Create new one");
            }
        });
        new ObjBasePopup(addFeature, _note);
        newNoteFactBox.add(addFeature);

        newNoteFactBox.add(Box.createHorizontalStrut(5));
        featureTextBox = new JTextField("Type help for details");
        featureTextBox.setFont(italFont);
        featureTextBox.setToolTipText("Type feature, strain, or family name");
        featureTextBox.setMaximumSize(new Dimension(90, 18));
        featureTextBox.setBorder(blackline);
        featureTextBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(featureTextBox.getText().equals("Type help for details")) {
                    featureTextBox.setText("");
                    featureTextBox.setFont(regFont);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(featureTextBox.getText().equals("")) {
                    featureTextBox.setText("Type help for details");
                    featureTextBox.setFont(italFont);
                    return;
                } else if(featureTextBox.getText().trim().equals("help")) {
                    JOptionPane.showMessageDialog(NoteEditor.this,
                        "Type the name of the Feature, Family, or Strain you wish to link.",
                        "Type in name",
                        JOptionPane.DEFAULT_OPTION);
                    featureTextBox.setText("Type help for details");
                    featureTextBox.setFont(italFont);
                    return;
                }
                processFeatureTextBox();
            }
        });
        newNoteFactBox.add(featureTextBox);
        newNoteFactBox.add(Box.createHorizontalStrut(5));

        BioLinksBox = new Box(BoxLayout.X_AXIS);
        newNoteFactBox.add(BioLinksBox);

        new ObjBasePopup(newNoteFactBox, _note);
        new ObjBasePopup(noteTitleField, _note);
    }

    private void processFeatureTextBox() {
        String entry = featureTextBox.getText();
        System.out.println("Processing " + entry);
        Feature afeat = Feature.retrieveByName(entry);
        if(afeat!=null) {
            System.out.println("adding note to feature " + afeat.getName());
            afeat.addObject(_note);
            thingsToSave.add(afeat);
            featureTextBox.setText("Type help for details");
            featureTextBox.setFont(italFont);
            return;
        }
        Strain astrain = Strain.retrieveByName(entry);
        if(astrain!=null) {
            System.out.println("adding note to strain " + astrain.getName());
            astrain.addObject(_note);
            thingsToSave.add(astrain);
            featureTextBox.setText("Type help for details");
            featureTextBox.setFont(italFont);
            return;
        }
        Family afam = Family.retrieveByName(entry);
        if(afam!=null) {
            System.out.println("adding note to family " + afam.getName());
            afam.addObject(_note);
            thingsToSave.add(afam);
            featureTextBox.setText("Type help for details");
            featureTextBox.setFont(italFont);
            return;
        }
    }

    private void createMenuBar() {
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
        menuBar.setBackground(navyblue);

        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");
        fileMenu.setForeground(Color.WHITE);
        menuBar.add(fileMenu);
            JMenuItem newWindow = new JMenuItem();
            newWindow.setText("New Note");
            newWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            newWindow.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    new NoteEditor(new Note("New note", Collector.getCurrentUser(), null));
                }
            });
            fileMenu.add(newWindow);

            JMenuItem openItem = new JMenuItem();
            openItem.setText("Open My Notes");
            openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
            openItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    launchListOfNotes(false);
                }
            });
            fileMenu.add(openItem);

            JMenuItem openAllItem = new JMenuItem();
            openAllItem.setText("Browse All Notes");
            openAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK | java.awt.event.InputEvent.ALT_MASK));
            openAllItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    new BrowseNotes(NoteEditor.this, false).setVisible(true);
                }
            });
            fileMenu.add(openAllItem);

            JMenuItem saveAll = new JMenuItem();
            saveAll.setText("Save everything");
            saveAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
            saveAll.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    HashSet<Factoid> facts = _note.getFactoids();
                    for(Factoid afact : facts) {
                        try {
                            afact.getWikiText().saveDefault();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            afact.saveDefault();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        HashSet<Attachment> atts = afact.getWikiText().getAttachments();
                        for(Attachment att : atts) {
                            try {
                                att.saveDefault();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        _note.saveDefault();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        _note.getWikiText().saveDefault();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for(ObjBase obj : thingsToSave) {
                        try {
                            obj.saveDefault();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            fileMenu.add(saveAll);

            JMenuItem closeWindow = new JMenuItem();
            closeWindow.setText("Close window");
            closeWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
            closeWindow.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    NoteEditor.this.dispose();
                }
            });
            fileMenu.add(closeWindow);

        JMenu helpMenu = new JMenu();
        helpMenu.setText("Help");
        helpMenu.setForeground(Color.WHITE);
        menuBar.add(helpMenu);

            JMenuItem commandList = new JMenuItem();
            commandList.setText("Command help");
            commandList.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
            commandList.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    FileObject fo = FileUtil.getConfigFile("org/clothocad/tool/grapevine/commands.csv");
                    new CommandHelp(fo, "Grapevine Note Editor", "http://wiki.bu.edu/ece-clotho/index.php/Grapevine", "AndersonClothoBugs@gmail.com", "");
                }
            });
            helpMenu.add(commandList);
    }

        public static void launchListOfNotes(boolean newoption) {
                launchAllNotes(newoption);
        }

    public static void launchPartialList(boolean newoption) {
        //Put all the links that exist and are in the user's list; add to chosenuuids
            HashMap<String, String> chosenuuids = new HashMap<String, String>();

            //Retrieve all links from current user and add to list
            ClothoConnection c = Collector.getDefaultConnection();
            ClothoQuery mainQuery = c.createQuery( ObjType.NOTE );
            ClothoQuery personQuery = mainQuery.createAssociationQuery( Note.Fields.AUTHOR );
            personQuery.eq( Person.Fields.DISPLAY_NAME, Collector.getCurrentUser().getDisplayName() );
            List<ObjBase> results = mainQuery.getResults();
            for(ObjBase obj : results) {
                Note anote = (Note) obj;
                chosenuuids.put(anote.getUUID(), anote.getName());
                System.out.println(anote.getName() + anote.getUUID()+ " was a query hit");
            }

            //Retrieve all links from "Devices2010" and add to list
            mainQuery = c.createQuery( ObjType.NOTE );
            personQuery = mainQuery.createAssociationQuery( Note.Fields.AUTHOR );
            personQuery.eq( Person.Fields.DISPLAY_NAME, "Devices2010" );
            results = mainQuery.getResults();
            for(ObjBase obj : results) {
                Note anote = (Note) obj;
                chosenuuids.put(anote.getUUID(), anote.getName());
                System.out.println(anote.getName() + anote.getUUID()+ " was a query hit");
            }

            //Make a list of ObjLinks
            ArrayList<ObjLink> objlinks = new ArrayList<ObjLink>();
            objlinks.add(new ObjLink("newnote", ObjType.NOTE, "Create a new note"));
            for(String uuid: chosenuuids.keySet()) {
                ObjLink ol = new ObjLink(uuid, ObjType.NOTE, chosenuuids.get(uuid));
                objlinks.add(ol);
            }

            //Throw up a chooser
            Object[] allNames = objlinks.toArray();
            ObjLink link = (ObjLink) JOptionPane.showInputDialog(null, "Choose one", "Note",
                JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
            if(link!=null) {
                Note chosen = null;
                if(link.uuid.equals("newnote")) {
                    chosen = new Note("New note", Collector.getCurrentUser(), null);
                } else {
                    chosen = Collector.getNote(link.uuid);
                }
                if(chosen!=null) {
                    new NoteEditor(chosen);
                }
            }
    }

    /**
     * Puts a chooser with all notes written by the current user
     * @param newoption
     */
    public static void launchAllNotes(boolean newoption) {
            //Make a list of Notes
            ClothoConnection c = Collector.getDefaultConnection();
            ClothoQuery mainQuery = c.createQuery( ObjType.NOTE );
            ClothoQuery seqQuery = mainQuery.createAssociationQuery( Note.Fields.AUTHOR );
            String name = Collector.getCurrentUser().getDisplayName();
            seqQuery.contains( Person.Fields.DISPLAY_NAME, name, false );
            List<ObjBase> results = mainQuery.getResults();

            //Create a list of links starting with a new note
            ArrayList<ObjLink> objlinks = new ArrayList<ObjLink>();
            objlinks.add(new ObjLink("newnote", ObjType.NOTE, "Create a new note"));
            
            //Put whatever objLinks were in the collection into the final list
            for(ObjBase obj : results) {
                objlinks.add(new ObjLink(obj.getUUID(), ObjType.NOTE, obj.getName()));
            }

            //Throw up a chooser
            Object[] allNames = objlinks.toArray();
            ObjLink link = (ObjLink) JOptionPane.showInputDialog(null, "Choose one", "Note",
                JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
            if(link!=null) {
                Note chosen = null;
                if(link.uuid.equals("newnote")) {
                    chosen = new Note("New note", Collector.getCurrentUser(), null);
                } else {
                    chosen = Collector.getNote(link.uuid);
                }
                if(chosen!=null) {
                    new NoteEditor(chosen);
                }
            }
    }

    private void putInFactoids() {
        new SwingWorker() {
            //VARIABLES:
            ArrayList<Factoid> listy;
            ArrayList<Note> notelisty;

            @Override
            protected Object doInBackground() throws Exception {
                HashSet<Factoid> setty = _note.getFactoids();
                wikiListeners.clear();
                listy = new ArrayList<Factoid>();
                for(Factoid afact : setty) {
                    listy.add(afact);
                        ObjBaseObserver wikiListener = new ObjBaseObserver() {
                        @Override
                        public void update(ObjBase obj, RefreshEvent evt) {
                            if(evt.isCondition(RefreshEvent.Condition.WIKITEXT_CHANGED)) {
                                validate();
                            }
                        }
                    };
                    afact.getWikiText().isObservedBy(wikiListener);
                    wikiListeners.add(wikiListener);
                }

                sortFactoids(listy);
                
                HashSet<Note> notesy = _note.getChildNotes();
                notelisty = new ArrayList<Note>();
                for(Note anote : notesy) {
                    notelisty.add(anote);
                }
                return null;
            }

            @Override
            protected void done() {
                CommentScrollPanel.removeAll();
                for(final Note currnote: notelisty) {
                    subNotePanel pl = new subNotePanel(NoteEditor.this, currnote);
                    CommentScrollPanel.add(pl);
                }

                for(final Factoid currfact: listy) {
                    factoidPanel pl = new factoidPanel(NoteEditor.this, currfact);
                    CommentScrollPanel.add(pl);
                }
                validate();
            }
        }.execute();
        putLinksToBiologicals();
    }

    private void putLinksToBiologicals() {
        new SwingWorker() {
            //VARIABLES:
            ArrayList<TransparentButton> listy = new ArrayList<TransparentButton>();

            @Override
            protected Object doInBackground() throws Exception {
                HashSet<String> featLinks = _note.getFeatureLinks();
                HashSet<String> famLinks = _note.getFamilyLinks();
                HashSet<String> strainLinks = _note.getStrainLinks();

                for(String str : featLinks) {
                    listy.add(createTB(str, ObjType.FEATURE, featureIcon));
                }

                for(String str : famLinks) {
                    listy.add(createTB(str, ObjType.FAMILY, familyIcon));
                }

                for(String str : strainLinks) {
                    listy.add(createTB(str, ObjType.STRAIN, strainIcon));
                }
                return null;
            }

            @Override
            protected void done() {
                BioLinksBox.removeAll();
                for(TransparentButton tb : listy) {
                    BioLinksBox.add(tb);
                }
                validate();
                repaint();
            }
        }.execute();
    }
    
    private TransparentButton createTB(final String uuid, final ObjType type, ImageIcon icon) {
        final linkOutButton tb = new linkOutButton(icon);
        tb.setEnterAlpha(0.8f);
        tb.setExitAlpha(1.0f);
        tb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(tb.isInitiated) {
                    return;
                }
                ObjBase obj = Collector.get(type, uuid);
                if(obj==null) {
                    return;
                }
                thingsToSave.add(obj);
                tb.setToolTipText(obj.getName());
                new ObjBasePopup(tb, obj);
                obj.isDragListenedBy(tb);
                tb.isInitiated = true;
            }
        });
        tb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObjBase obj = Collector.get(type, uuid);
                if(obj==null) {
                    return;
                }
                thingsToSave.add(obj);
                int n = JOptionPane.showConfirmDialog(
                    NoteEditor.this,
                    "Are you sure you want to remove this note from " + obj.getName() + "?",
                    "Confirm unlink",
                    JOptionPane.YES_NO_OPTION);
                if(n==0) {
                    switch(type) {
                        case STRAIN:
                            Strain astrain = (Strain) obj;
                            astrain.removeNote(_note);
                            break;
                        case FAMILY:
                            Family afam = (Family) obj;
                            afam.removeNote(_note);
                            break;
                        case FEATURE:
                            Feature feat = (Feature) obj;
                            feat.removeNote(_note);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        return tb;
    }

    private class linkOutButton extends TransparentButton {
        public linkOutButton(ImageIcon img) {
            super(img);
        }
        //VARIABLES//
        private boolean isInitiated = false;
    }

    private static void sortFactoids(ArrayList<Factoid> factList) {
        Comparator comparator = new Comparator() {
            @Override
            public int compare ( Object o1, Object o2 ) {
                Factoid part1 = (Factoid) o1;
                Factoid part2 = (Factoid) o2;
                Date i1 = part1.getDateCreated();
                Date i2 = part2.getDateCreated();
                return i2.compareTo( i1 );
            }
        };
        Collections.sort( factList, comparator );
    }

/*-----------------
     variables
 -----------------*/
    Note _note;
    private JPanel headerPanel;
    private JButton addFactoid;
    private JButton addSubNote;
    private JButton addFeature;
    private JTextField featureTextBox;
    private Box BioLinksBox;
    private static FileObject _noteList = FileUtil.getConfigFile("org/clothocad/tool/grapevine/existingNotes.txt");

    private ScrollablePanel CommentScrollPanel;
    private JPanel notesPanel;
    private static final ImageIcon factoidIcon = ImageSource.getObjectImage(ObjType.FACTOID, 25);
    private static final ImageIcon noteIcon = ImageSource.getObjectImage(ObjType.NOTE, 25);
    private static final ImageIcon featureIcon = ImageSource.getObjectImage(ObjType.FEATURE, 20);
    private static final ImageIcon strainIcon = ImageSource.getObjectImage(ObjType.STRAIN, 20);
    private static final ImageIcon familyIcon = ImageSource.getObjectImage(ObjType.STRAIN, 20);
    private ObjBaseObserver noteListener;

    private WikiEditorPanel noteWTEd;
    private HashSet<ObjBase> thingsToSave = new HashSet<ObjBase>();
    private static Color navyblue = new Color(35, 48, 64);
    private blueTextField noteTitleField;
    private HashSet<ObjBaseObserver> wikiListeners = new HashSet<ObjBaseObserver>();
    private static final Border blackline = BorderFactory.createLineBorder(Color.black);
    private Font italFont = new Font("Arial", Font.ITALIC, 9);
    private Font regFont = new Font("Arial", Font.PLAIN, 11);
}
