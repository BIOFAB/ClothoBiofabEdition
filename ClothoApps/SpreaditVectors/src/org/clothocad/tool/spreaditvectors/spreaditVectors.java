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
 * spreaditVectors.java
 *
 * Created on Aug 4, 2010, 9:03:45 AM
 */

package org.clothocad.tool.spreaditvectors;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author J. Christopher Anderson
 */
public class spreaditVectors extends javax.swing.JFrame implements ObjBaseObserver {

    /** Creates new changeFormat spreaditVectors */
    public spreaditVectors(Collection coll) {
        super("Spreadit Vectors: " + getStringName(coll));
        setIconImage(ImageSource.getTinyLogo());
        _prefs = Preferences.userNodeForPackage(spreaditVectors.class);
        _Collection = coll;
        _Collection.isRepresentedBy(this, this);
        new ObjBasePopup(getContentPane(), _Collection);
        _title = new String[] { "Nickname", "Short Description", "Sequence",  "Format", "Author"};
        initComponents();
        setSize(600,650);
        setVisible(true);
        ExcelAdapter myAd = new ExcelAdapter(table);
        new Thread() {
            @Override
            public void run() {
                parseVectors();
            }
        }.start();
    }

    private static String getStringName(Collection c) {
        if(c!=null) {
            return c.getName();
        } else {
            return "Error";
        }
    }

    @Override
    public void update(ObjBase obj, RefreshEvent evt) {
        if(obj==null) {
            dispose();
        }
        new Thread() {
            @Override
            public void run() {
                parseVectors();
            }
        }.start();
    }

    private void parseVectors() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                allVectors = (ArrayList<Vector>) _Collection.getAll(ObjType.VECTOR);
                _data = new String [allVectors.size()][5];
                return null;
            }

            @Override
            public void done() {
                edtRelay();
            }
        }.execute();
    }

    private void edtRelay() {
        final DefaultTableModel tableModel = new DefaultTableModel(_data, _title);
        table.setModel(tableModel);

        System.out.println(allVectors.size());
        for(int i=0; i<allVectors.size(); i++ ) {
            final Vector currVector = allVectors.get(i);
            if(currVector==null) {
                continue;
            }
            final int index = i;
            ObjBaseObserver obr = new ObjBaseObserver() {
                @Override
                public void update(final ObjBase obj, RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                    @Override
                        public void run() {
                            refreshValues((Vector) obj);
                        }
                    });
                }
            };
            currVector.isObservedBy(obr);
            obr.update(currVector, null);
        }

        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                table.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        Vector changedVector = allVectors.get(e.getFirstRow());
                        processVectorChange(changedVector, e.getFirstRow(), e.getColumn(), (String) tableModel.getValueAt(e.getFirstRow(), e.getColumn()));
                    }
                });
                table.addMouseListener(new MouseAdapter() {
                    @Override
                     public void mousePressed( MouseEvent e ) {
                        if(e.getModifiers()==4) {
                            if(table.getSelectedRows().length>1) {
                                return;
                            }
                            int index = table.getSelectedRow();
                            final Vector aVector = allVectors.get(index);
                            ObjBasePopup obp = new ObjBasePopup(table, aVector, e.getPoint());
                            obp.addMenuItem("Remove from Collection", new java.awt.event.ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    _Collection.removeItem(aVector);
                                }
                            });
                        }
                     }
                });
            }
        });
    }

    private void refreshValues(Vector currVector) {
        int index = allVectors.indexOf(currVector);
        table.setValueAt(currVector.getName(), index, 0);
        table.setValueAt(currVector.getShortDescription(), index, 1);
        table.setValueAt(currVector.getSeq().toString(), index, 2);
        table.setValueAt(currVector.getFormat().getName(), index, 3);
        table.setValueAt(currVector.getAuthor().getName(), index, 4);
    }

    private void initComponents() {
        scroller = new javax.swing.JScrollPane();
        table = new JXTable();
        
        getContentPane().setBackground(navyblue);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        String[][] data = new String [][] {
                {"Data loading..."}
            };
        _data = data;
        DefaultTableModel tableModel = new DefaultTableModel(_data, new String[] {""});
        table.setModel(tableModel);
        table.setSortable(false);
        table.getTableHeader().setReorderingAllowed(false);

        scroller.setViewportView(table);

        putInMenuBar();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroller, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroller, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void putInMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(navyblue);

        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");
        fileMenu.setForeground(Color.WHITE);
        menuBar.add(fileMenu);
            JMenuItem newWindow = new JMenuItem();
            newWindow.setText("New window");
            final javax.swing.JFrame myself = this;
            newWindow.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Collection newcoll = new Collection();
                    newcoll.changeName("Spreadit vector collection");
                    newcoll.changeDescription("Temporary collection of vectors");
                    new spreaditVectors(newcoll);
                }
            });
            fileMenu.add(newWindow);

            JMenuItem addBasic = new JMenuItem();
            addBasic.setText("Add Vector");
            addBasic.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            addBasic.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    vectorEnterer dialog = new vectorEnterer(myself, true);
                    Vector aVector = dialog.getVector();
                    if(aVector!=null) {
                        _Collection.addObject(aVector);
                        System.out.println("composite Vector added");
                    } else {
                        JOptionPane.showMessageDialog(spreaditVectors.this,
                            "Something was wrong with your Vector, it was rejected.  Check that the sequence obeys the Format.",
                            "Add Vector failed",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            fileMenu.add(addBasic);

            JMenuItem changeColl = new JMenuItem();
            changeColl.setText("Change Default Collection");
            changeColl.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //Throw up a dialog and get user to select the collection stored as 'chosen'
                    ArrayList<ObjLink> allColl = Collector.getAllLinksOf(ObjType.COLLECTION);
                    Object[] allNames = allColl.toArray();
                    ObjLink link = (ObjLink) JOptionPane.showInputDialog(null, "Choose one", "Collection",
                        JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
                    if(link!=null) {
                        Collection chosen = Collector.getCollection(link.uuid);
                        _prefs.put("launchCollection", chosen.getUUID());
                    }
                }
            });

            fileMenu.add(changeColl);
            JMenuItem saveCollection = new JMenuItem();
            saveCollection.setText("Save Collection");
            saveCollection.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    _Collection.saveDefault();
                }
            });
            fileMenu.add(saveCollection);

            JMenuItem closeWindow = new JMenuItem();
            closeWindow.setText("Close window");
            closeWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
            closeWindow.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    myself.dispose();
                }
            });
            fileMenu.add(closeWindow);

        JMenu editMenu = new JMenu();
        editMenu.setText("Edit");
        editMenu.setForeground(Color.WHITE);
        menuBar.add(editMenu);

            JMenuItem selectAll = new JMenuItem();
            selectAll.setText("Select all");
            selectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
            selectAll.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    table.selectAll();
                }
            });
            editMenu.add(selectAll);

            JMenuItem find = new JMenuItem();
            find.setText("Search name/description");
            find.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
            find.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String query = JOptionPane.showInputDialog( "What do you want to search for?" );
                    if(query==null) {
                        return;
                    }
                    searchString(query);
                }
            });
            editMenu.add(find);

            JMenuItem findSeq = new JMenuItem();
            findSeq.setText("Search sequence");
            findSeq.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
            findSeq.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String query = JOptionPane.showInputDialog( "What sequence do you want to search for?" );
                    if(query==null) {
                        return;
                    }
                    searchSeq(query);
                }
            });
            editMenu.add(findSeq);

        JMenu selectMenu = new JMenu();
        selectMenu.setText("Selection");
        selectMenu.setForeground(Color.WHITE);
        menuBar.add(selectMenu);

            JMenuItem save = new JMenuItem();
            save.setText("Save selected");
            save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
            save.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int[] selected = table.getSelectedRows();
                    for(int i: selected) {
                        Vector selectVector = allVectors.get(i);
                        selectVector.saveDefault();
                    }
                }
            });
            selectMenu.add(save);

        setJMenuBar(menuBar);
    }

        private void searchSeq(String tags) {
            try {
                //Query the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.VECTOR );
                ClothoQuery seqQuery = mainQuery.createAssociationQuery( Vector.Fields.SEQUENCE );
                seqQuery.contains( NucSeq.Fields.SEQUENCE, tags, false );
                List<ObjBase> results = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Oligos for " + tags);
                for(ObjBase avect : results) {
                    outcoll.addObject(avect);
                }
                new spreaditVectors(outcoll);
            } catch( Exception e) {
                System.out.println("Sequence find Encountered an error");
            }
        }

        private void searchString(String tags) {
            try {
                //Query the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.VECTOR );
                mainQuery.contains( Vector.Fields.DESCRIPTION, tags, false );
                List<ObjBase> descresults = mainQuery.getResults();

                c = Collector.getDefaultConnection();
                mainQuery = c.createQuery( ObjType.VECTOR );
                mainQuery.contains( Vector.Fields.NAME, tags, false );
                List<ObjBase> nameresults = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Oligos for " + tags);
                for(ObjBase avect : descresults) {
                    outcoll.addObject(avect);
                }
                for(ObjBase avect : nameresults) {
                    outcoll.addObject(avect);
                }
                new spreaditVectors(outcoll);
            } catch( Exception e) {
            }
        }

        private void processVectorChange(Vector aVector, int row, int column, String val) {
            System.out.println(aVector.getName());
            System.out.println(column);
            switch(column) {
                case 0:
                    if(aVector.getName().equals(val)) {
                        return;
                    }
                    aVector.changeName(val);
                    table.setValueAt(aVector.getName(), row, column);
                    break;
                case 1:
                    if(aVector.getShortDescription().equals(val)) {
                        return;
                    }
                    aVector.changeShortDescription(val);
                    table.setValueAt(aVector.getShortDescription(), row, column);
                    break;
                case 2:
                    if(aVector.getSeq().toString().equals(val)) {
                        return;
                    }
                    aVector.changeSequence(val);
                    table.setValueAt(aVector.getSeq().toString(), row, column);
                    break;
                case 3:
                    if(aVector.getFormat().getName().equals(val)) {
                        return;
                    }
                    Format form = Format.retrieveByName(val);
                    aVector.changeFormat(form);
                    table.setValueAt(aVector.getFormat().getName(), row, column);
                    break;
                case 4:
                    if(aVector.getAuthor().getName().equals(val)) {
                        return;
                    }
                    Person aPerson = Person.retrieveByName(val);
                    aVector.changeAuthor(aPerson);
                    table.setValueAt(aVector.getAuthor().getName(), row, column);
                    break;
            }
        }

        private void launchPopup(MouseEvent e) {
            if(table.getSelectedRows().length>1) {
                System.out.println("Multiple rows selected");
                return;
            }
            int selection = table.getSelectedRow();
            System.out.println("Right click popup on " + selection);
        }

/**-----------------
     variables
 -----------------*/
    private javax.swing.JScrollPane scroller;
    private JXTable table;
    static Color navyblue = new Color(35, 48, 64);

    private Object[][] _data;
    private String [] _title;

    private Collection _Collection;
    private ArrayList<Vector> allVectors;
    static Preferences _prefs;   //prefs
}
