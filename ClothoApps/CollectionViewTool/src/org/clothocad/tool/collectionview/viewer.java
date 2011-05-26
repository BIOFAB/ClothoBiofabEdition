package org.clothocad.tool.collectionview;
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

/*
 * NewJFrame.java
 *
 * Created on May 21, 2010, 6:04:40 PM
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.misc.BareBonesBrowserLaunch;

/**
 *
 * @author J. Christopher Anderson
 */
public class viewer extends javax.swing.JFrame  {

    /** Creates new form NewJFrame */
    public viewer(Collection coll) {
        super("Collection View:" + coll.getName());
        setIconImage(ImageSource.getTinyLogo());
        _myCollection = coll;
        _ref = new refresher();
        _myCollection.isRepresentedBy(_ref, getRootPane());
        obp = new ObjBasePopup(getRootPane(), coll);
        initComponents();
        createStartupMessage();
        setVisible(true);
        refreshGUI();
    }

    private void initComponents() {
        //Create the menu bar
        JMenuBar menu = new JMenuBar();
        getRootPane().setJMenuBar(menu);
        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);
            JMenuItem newhere = new JMenuItem("Insert new Collection");
            newhere.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            fileMenu.add(newhere);
            newhere.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog( "What do you want to name the new collection?" );
                    if(name!=null) {
                        String description = JOptionPane.showInputDialog( "Give me a description of the new collection." );
                        if(description!=null) {
                            Collection acoll = new Collection(name, description, Collector.getCurrentUser());
                            _myCollection.addObject(acoll);
                        }
                    }
                }
            });

            JMenuItem newout = new JMenuItem("Popout new Collection");
            newout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK | java.awt.event.InputEvent.ALT_MASK));
            fileMenu.add(newout);
            newout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Collection acoll = new Collection("New Collection", "Description of new Collection", Collector.getCurrentUser());
                    new viewer(acoll);
                }
            });

        JMenu helpMenu = new JMenu("Help");
        menu.add(helpMenu);
            JMenuItem launchhelp = new JMenuItem("Visit help page");
            launchhelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
            helpMenu.add(launchhelp);
            launchhelp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BareBonesBrowserLaunch.openURL( Collator.helpURLBase + "/Collection_View");
                }
            });

        //Create the contents
        getContentPane().setBackground(navyblue);
        jPanel1 = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        collectionNameField = new javax.swing.JTextField();
        collectionDescriptionField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        objectListTable = new javax.swing.JTable();
        newCollectionButton = new JButton("New");
        newCollectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog( "What do you want to name the new collection?" );
                if(name!=null) {
                    String description = JOptionPane.showInputDialog( "Give me a description of the new collection." );
                    if(description!=null) {
                        Collection acoll = new Collection(name, description, Collector.getCurrentUser());
                        _myCollection.addObject(acoll);
                        new viewer(acoll);
                    }
                }
            }
        });
        helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BareBonesBrowserLaunch.openURL( Collator.helpURLBase + "/Collection_View");
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(navyblue);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ObjBasePopup pop = new ObjBasePopup(jPanel1, _myCollection, evt.getPoint());
            }
        });

        searchTextField.setText("Search...");

        searchButton.setText("search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSearch();
            }
        });

        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(searchTextField.getText().equals("Search...")) {
                    searchTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(searchTextField.getText().equals("")) {
                    searchTextField.setText("Search...");
                }
            }
        });

        collectionNameField.setBackground(navyblue);
        collectionNameField.setFont(new java.awt.Font("Tahoma", 0, 18));
        collectionNameField.setForeground(new java.awt.Color(255, 255, 255));
        collectionNameField.setBorder(null);
        collectionNameField.setText(_myCollection.getName());
        collectionNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                collectionNameField.setBackground(Color.WHITE);
                collectionNameField.setFont(new java.awt.Font("Tahoma", 0, 18));
                collectionNameField.setForeground(Color.BLACK);
                collectionNameField.setBorder(blackline);
            }

            @Override
            public void focusLost(FocusEvent e) {
                collectionNameField.setBackground(navyblue);
                collectionNameField.setFont(new java.awt.Font("Tahoma", 0, 18));
                collectionNameField.setForeground(new java.awt.Color(255, 255, 255));
                collectionNameField.setBorder(null);
                if(!collectionNameField.getText().equals(_myCollection.getName())) {
                    _myCollection.changeName(collectionNameField.getText());
                }
            }

        });

        collectionDescriptionField.setBackground(navyblue);
        collectionDescriptionField.setFont(new java.awt.Font("Tahoma", 0, 12));
        collectionDescriptionField.setForeground(new java.awt.Color(255, 255, 255));
        collectionDescriptionField.setBorder(null);
        collectionDescriptionField.setText("Loading the collection, please wait...");
        collectionDescriptionField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                collectionDescriptionField.setBackground(Color.WHITE);
                collectionDescriptionField.setFont(new java.awt.Font("Tahoma", 0, 12));
                collectionDescriptionField.setForeground(Color.BLACK);
                collectionDescriptionField.setBorder(blackline);
            }

            @Override
            public void focusLost(FocusEvent e) {
                collectionDescriptionField.setBackground(navyblue);
                collectionDescriptionField.setFont(new java.awt.Font("Tahoma", 0, 12));
                collectionDescriptionField.setForeground(new java.awt.Color(255, 255, 255));
                collectionDescriptionField.setBorder(null);
                if(!collectionDescriptionField.getText().equals(_myCollection.getDescription())) {
                    _myCollection.changeDescription(collectionDescriptionField.getText());
                }
            }
        });

        saveButton.setText("Save changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        _myCollection.saveDefault();

                        if(!_myCollection.isChanged()) {
                            GlassMessage msgbox = new GlassMessage("Collection saved.", null, 122, 50, glassPanel, Color.WHITE, null);
                            msgbox.setBounds(290,28,122,50);
                            glassPanel.add(msgbox);
                            validate();
                            repaint();
                        } else {
                            GlassMessage msgbox = new GlassMessage("Collection save failed!", null, 145, 50, glassPanel, Color.PINK, null);
                            msgbox.setBounds(290,28,145,50);
                            glassPanel.add(msgbox);
                            validate();
                            repaint();
                        }
                        return null;
                    }
                }.execute();
            }
        });


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(newCollectionButton)
                        .addGap(16, 16, 16)
                        .addComponent(saveButton)
                        .addGap(16, 16, 16)
                        .addComponent(helpButton)
                        .addGap(16, 16, 16)
                        .addComponent(searchTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(collectionNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(collectionDescriptionField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(collectionNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(collectionDescriptionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(newCollectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton)
                    .addComponent(helpButton)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        model = new collectionTableModel();
        objectListTable.setModel(model);
        objectListTable.setShowHorizontalLines(false);
        objectListTable.setShowVerticalLines(false);
        jScrollPane1.setViewportView(objectListTable);
        objectListTable.getColumnModel().getColumn(0).setResizable(false);
        objectListTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        objectListTable.getTableHeader().addMouseListener(new headerListener());
        //Disable sorting on all columns


        objectListTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            tableClicked(evt);
                        }   });


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );

        //Listen for ctrl-C for copy
        objectListTable.getInputMap().put( KeyStroke.getKeyStroke( "control C" ), "copyAction" );
        objectListTable.getActionMap().put( "copyAction", new AbstractAction( "copyAction" ) {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                copyActionPerformed();
            }
        } );

        //Listen for ctrl-X for copy
        objectListTable.getInputMap().put( KeyStroke.getKeyStroke( "control X" ), "cutAction" );
        objectListTable.getActionMap().put( "cutAction", new AbstractAction( "cutAction" ) {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                cutActionPerformed();
            }
        } );

        //Listen for ctrl-V for copy
        objectListTable.getInputMap().put( KeyStroke.getKeyStroke( "control V" ), "pasteAction" );
        objectListTable.getActionMap().put( "pasteAction", new AbstractAction( "pasteAction" ) {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                pasteActionPerformed();
            }
        } );

        //Listen for DELETE for delete
        objectListTable.getInputMap().put( KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delAction" );
        objectListTable.getActionMap().put( "delAction", new AbstractAction( "delAction" ) {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                deleteActionPerformed();
            }
        } );

        //Put in the glassPanel
        glassPanel = new JPanel();
        glassPanel.setOpaque(false);
        setGlassPane(glassPanel);

        mousesilencer = new MouseAdapter() { };
        glassPanel.addMouseListener(mousesilencer);
        motionsilencer = new MouseMotionAdapter() { };
        glassPanel.addMouseMotionListener(motionsilencer);
        keysilencer = new KeyAdapter() { };
        glassPanel.addKeyListener(keysilencer);
        getGlassPane().setVisible(true);
        glassPanel.setLayout(null);

        pack();
        objectListTable.requestFocusInWindow();

    }

    private class headerListener extends MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            JTableHeader h = (JTableHeader) e.getSource();
            TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = h.columnAtPoint(e.getPoint());
            int column = columnModel.getColumn(viewColumn).getModelIndex();
            if (column != -1) {
                sortBy sortmode;
                switch(column) {
                    case 0:
                        sortmode = sortBy.ICON;
                        break;
                    case 1:
                        sortmode = sortBy.NAME;
                        break;
                    case 2:
                        sortmode = sortBy.DATECREATED;
                        break;
                    case 3:
                        sortmode = sortBy.LASTMODIFIED;
                        break;
                    default:
                        return;
                }
                if(sortmode.equals(sortByChoice)) {
                    reverseit = !reverseit;
                } else {
                    reverseit = false;
                }
                setSortMode(sortmode);
            }
        }
    }

    public void setSortMode(sortBy choice) {
        sortByChoice = choice;
        refreshGUI();
    }

    private ArrayList<ObjBase> sortList(ArrayList<ObjBase> inlist) {
        ArrayList<ObjBase> out = new ArrayList<ObjBase>();

        Comparator comparator = new Comparator() {

            @Override
            public int compare( Object o1, Object o2 ) {
                ObjBase part1 = (ObjBase) o1;
                ObjBase part2 = (ObjBase) o2;

                switch(sortByChoice) {
                    case ICON:
                        String ti1 = part1.getType().toString();
                        String ti2 = part2.getType().toString();
                        if ( reverseit ) {
                            return ti2.compareTo( ti1 );
                        } else {
                            return ti1.compareTo( ti2 );
                        }
                    case NAME:
                        String i1 = part1.getName();
                        String i2 = part2.getName();
                        if ( reverseit ) {
                            return i2.compareTo( i1 );
                        } else {
                            return i1.compareTo( i2 );
                        }
                    case DATECREATED:
                        String d1 = part1.getDateCreated().toString();
                        String d2 = part2.getDateCreated().toString();
                        if ( reverseit ) {
                            return d2.compareTo( d1 );
                        } else {
                            return d1.compareTo( d2 );
                        }
                    case LASTMODIFIED:
                        String L1 = part1.getDateCreated().toString();
                        String L2 = part2.getDateCreated().toString();
                        if ( reverseit ) {
                            return L2.compareTo( L1 );
                        } else {
                            return L1.compareTo( L2 );
                        }
                    default:
                        return 1;
                }
            }
        };
        java.util.Collections.sort( inlist, comparator );

        for ( ObjBase p : inlist ) {
            out.add( p );
        }
        return out;
    }

    private void cutActionPerformed() {
        copyActionPerformed();
        isCutMode = true;
    }

    private void deleteActionPerformed() {
        int[] rows = objectListTable.getSelectedRows();
        int n = JOptionPane.showConfirmDialog( null, "Are you sure you want to remove these " + rows.length +  " items ?",
                "Confirm delete", JOptionPane.YES_NO_OPTION );
        if ( n == 0 ) {
            for(int row: rows) {
                ObjBase o = getObjectAt(row);
                _myCollection.removeItem(o);
            }
        }
    }

    private void copyActionPerformed() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                sourceCollection = _myCollection;
                copiedObjects.clear();
                int[] rows = objectListTable.getSelectedRows();
                for(int row: rows) {
                    ObjBase o = getObjectAt(row);
                    viewer.copiedObjects.add(o);
                    System.out.println("copying " + o.getName());
                }
                isCutMode = false;
                return null;
            }
        }.execute();
    }

    private void pasteActionPerformed() {
        if(sourceCollection==null) {
            return;
        }
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //If it's cut mode, remove all copied objects from the source collection
                if(isCutMode) {
                    for(ObjBase obj : copiedObjects) {
                        sourceCollection.removeItem(obj);
                    }
                }
                //Put all the objects into this guys collection
                for(ObjBase obj : copiedObjects) {
                    _myCollection.addObject(obj);
                }
                isCutMode = false;
                sourceCollection = null;
                return null;
            }
        }.execute();
    }

    private void processSearch() {
        JOptionPane.showMessageDialog( null, "Search is not yet implemented", "Not implemented", JOptionPane.ERROR_MESSAGE );
    }

    public void tableClicked(final java.awt.event.MouseEvent evt) {
        //If it was a right click
        if(evt.getModifiers()==4) {
            int numSelected = objectListTable.getSelectedRows().length;

            //If there are multiple items selected, enable copy
            if(numSelected > 1) {
                for(int i: objectListTable.getSelectedRows()) {
                    ObjBase o = getObjectAt(i);
                    System.out.println(o.getName());

                    //CREATE A TRANSIENT COLLECTION, SAVE THAT TO CLIPBOARD?
                    //HAVE A SECOND CLIPBOARD THAT IS ArrayList<objbase> ?
                }
                return;
            }

            //If it was a single item, show a right-click popup
            if(numSelected == 1) {
                final ObjBase selObj = getObjectAt(objectListTable.getSelectedRow());
                final ObjBasePopup pop = new ObjBasePopup(viewer.this, selObj, evt.getPoint());
                final ActionListener al = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        _myCollection.removeItem(selObj);
                    }
                };
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        pop.addMenuItem("Remove from collection", al);
                    }
                });
                return;
            }
        }

        //If it was a double click
        if(evt.getClickCount()==2) {
            System.out.println("double clicked");
            ObjBase selObj = getObjectAt(objectListTable.getSelectedRow());
            System.out.println(selObj.getName());
            selObj.launchDefaultViewer();
        }
        return;
    }

    private ObjBase getObjectAt(int selectedIndex) {
        ObjBase selObject = allContents.get(selectedIndex);
        return selObject;
    }

    private void createStartupMessage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startupMessage = new StartupMessage("Hold on while your contents load...", null, 210, 50, glassPanel, Color.WHITE, null);
                startupMessage.setBounds(getWidth()/2-105, getHeight()/2, 210, 50);
                glassPanel.add(startupMessage);
            }
        });
    }

    private class refresher implements ObjBaseObserver {
        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            if(obj==null) {
                dispose();
            }
            refreshGUI();
        }
    }

    private void refreshGUI() {
       new SwingWorker() {
            /////VARIABLES:
            Object[][] datas;

            @Override
            protected Object doInBackground() throws Exception {
                ArrayList<ObjBase> everything = _myCollection.getAll();
                allContents = sortList(everything);
                datas = new Object[allContents.size()][5];
                int count=0;
                for(ObjBase o: allContents) {
                    if(o==null) {
                        continue;
                    }
                    //Put in the little icons based on type
                    datas[count][0] = iconSet.get(o.getType());
                    datas[count][1] =  o.getName();
                    datas[count][2] =  o.getType().toString();
                    datas[count][3] =  o.getDateCreated().toString();
                    datas[count][4] =  o.getLastModified().toString();
                    count++;
                }
                return null;
            }

            @Override
            protected void done() {
                model.setData(datas);
                collectionDescriptionField.setText(_myCollection.getDescription());
                collectionNameField.setText(_myCollection.getName());
                objectListTable.setModel(model);
                if(startupMessage!=null) {
                    startupMessage.dispose();
                    glassPanel.removeMouseListener(mousesilencer);
                    glassPanel.removeKeyListener(keysilencer);
                    glassPanel.removeMouseMotionListener(motionsilencer);

                    startupMessage=null;
                }
                validate();
                repaint();
            }
        }.execute();
    }


    class collectionTableModel extends AbstractTableModel {
        private String[] columnNames = new String [] {
                    "", "Name", "Type", "Date Created", "Last Modified"  };
        private Object[][] data = new Object [80][6];

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        public void setData(Object[][] datas) {
            data = datas;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

            @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

            @Override
        public Class getColumnClass(int c) {
            if(c==0) {
                return ImageIcon.class;
            }
            return String.class;
        }
    }

/**-----------------
     variables
 -----------------*/
    private javax.swing.JTextField collectionNameField;
    private javax.swing.JTextField collectionDescriptionField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable objectListTable;
    private collectionTableModel model;
    private JButton saveButton;
    private JButton searchButton;
    private JButton newCollectionButton;
    private JButton helpButton;
    private javax.swing.JTextField searchTextField;
    private ArrayList<ObjLink> _allAuthors;
    private Collection _myCollection;

    private static final Border blackline = BorderFactory.createLineBorder(Color.black);
    private static final Map<ObjType, ImageIcon> iconSet = ImageSource.getObjectIconSet(14);
    static Color navyblue = new Color(35, 48, 64);

    private static boolean isCutMode = false;
    private static Collection sourceCollection;
    private static HashSet<ObjBase> copiedObjects = new HashSet<ObjBase>();
    private refresher _ref;
    private ObjBasePopup obp;
    private JPanel glassPanel;

    private StartupMessage startupMessage;


    public enum sortBy {ICON, NAME, DATECREATED, LASTMODIFIED};
    private sortBy sortByChoice = sortBy.ICON;
    private boolean reverseit = false;

    private ArrayList<ObjBase> allContents;
    private MouseAdapter mousesilencer;
    private MouseMotionAdapter motionsilencer;
    private KeyAdapter keysilencer;
}
