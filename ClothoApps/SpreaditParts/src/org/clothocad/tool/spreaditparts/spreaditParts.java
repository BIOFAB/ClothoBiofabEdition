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
 * spreaditParts.java
 *
 * Created on Aug 4, 2010, 9:03:45 AM
 */

package org.clothocad.tool.spreaditparts;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Format;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;
import org.jdesktop.swingx.JXTable;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
@SuppressWarnings (value="unchecked")
public class spreaditParts extends javax.swing.JFrame  {

    /** Creates new changeFormat spreaditParts */
    public spreaditParts(Collection coll) {
        super("Spreadit Parts: " + getStringName(coll));
        setIconImage(ImageSource.getTinyLogo());
        _prefs = Preferences.userNodeForPackage(spreaditParts.class);
        _Collection = coll;
        refreshOBO = new refresher();
        _Collection.isRepresentedBy(refreshOBO, this);
        new ObjBasePopup(getContentPane(), _Collection);

        _title = new String[] { "", "Nickname", "Short Description", "Sequence",  "Format", "Author", "Status", "RG"};
        initComponents();

        //prefs
        int wide = _prefs.getInt("spreaditPartsWidth", 600);
        int high = _prefs.getInt("spreaditPartsHeight", 650);

        setSize(wide,high);
        setVisible(true);
        ExcelAdapter myAd = new ExcelAdapter(table);
        new Thread() {
            @Override
            public void run() {
                parseParts();

                //prefs
                setWidths();
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

    //prefs
    private void setWidths() {
        final List<TableColumn> cols = table.getColumns();
        for(int i=0; i<cols.size(); i++) {
            final int wide = _prefs.getInt("spreaditPartsColumn" + i, cols.get(i).getWidth());
            final TableColumn tc = cols.get(i);
            columnWidths[i] = wide;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Setting " + tc + " to " + wide);
                    tc.setPreferredWidth(wide);
                }
            });
        }
    }

    private class refresher implements ObjBaseObserver {
        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            if(obj==null) {
                dispose();
            }
            new Thread() {
                @Override
                public void run() {
                    parseParts();
                }
            }.start();
        }
    }

    private void parseParts() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                ArrayList<Part> inlist = (ArrayList<Part>) _Collection.getAll(ObjType.PART);
                allParts = sortList(inlist);

                _data = new String [allParts.size()][8];
                return null;
            }
            
            @Override
            public void done() {
                edtRelay();
            }
        }.execute();
    }

    private void edtRelay() {
        final DefaultTableModel tableModel = new DefaultTableModel(_data, _title) { };
        table.setModel(tableModel);
        table.setDefaultRenderer(java.lang.Object.class, new AvailabilityColorRenderer());
        
        System.out.println(allParts.size());
        obrlist.clear();
        for(int i=0; i<allParts.size(); i++ ) {
            final Part currPart = allParts.get(i);
            if(currPart==null) {
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
                            refreshValues((Part) obj);
                        }
                    });
                }
            };
            obrlist.add(obr);
            currPart.isObservedBy(obr);
            obr.update(currPart, null);
            validate();
            repaint();
        }

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Part changedPart = allParts.get(e.getFirstRow());
                processPartChange(changedPart, e.getFirstRow(), e.getColumn(), (String) tableModel.getValueAt(e.getFirstRow(), e.getColumn()));
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
                    final Part aPart = allParts.get(index);
                    ObjBasePopup obp = new ObjBasePopup(table, aPart, e.getPoint());
                    obp.addMenuItem("Remove from Collection", new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            _Collection.removeItem(aPart);
                        }
                    });
                }
             }
        });
        isInitiated = true;
    }

    private void refreshValues(Part currPart) {
        int index = allParts.indexOf(currPart);
        String isbasicstring = "c";
        if(currPart.getPartType().equals(Part.partType.Basic)) {
            isbasicstring = "b";
        }
        table.setValueAt(isbasicstring, index, 0);
        table.setValueAt(currPart.getName(), index, 1);
        table.setValueAt(currPart.getShortDescription(), index, 2);
        table.setValueAt(currPart.getSeq().toString(), index, 3);
        table.setValueAt(currPart.getFormat().getName(), index, 4);
        table.setValueAt(currPart.getAuthor().getName(), index, 5);
        table.setValueAt(findStatus(currPart), index, 6);
        table.setValueAt(((Short) currPart.getRiskGroup()).toString(), index, 7);
    }

    private class headerListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTableHeader h = (JTableHeader) e.getSource();
            TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = h.columnAtPoint(e.getPoint());
            int column = columnModel.getColumn(viewColumn).getModelIndex();
            if (column != -1) {
                sortBy sortmode;
                switch(column) {
                    case 0:
                        sortmode = sortBy.ISBASIC;
                        break;
                    case 1:
                        sortmode = sortBy.NAME;
                        break;
                    case 2:
                        sortmode = sortBy.DESCRIPTION;
                        break;
                    case 3:
                        sortmode = sortBy.SEQUENCE;
                        break;
                    case 4:
                        sortmode = sortBy.FORMAT;
                        break;
                    case 5:
                        sortmode = sortBy.AUTHOR;
                        break;
                    case 7:
                        sortmode = sortBy.RG;
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
        parseParts();
    }

    private ArrayList<Part> sortList(ArrayList<Part> inlist) {
        ArrayList<Part> out = new ArrayList<Part>();

        Comparator comparator = new Comparator() {

            @Override
            public int compare( Object o1, Object o2 ) {
                Part part1 = (Part) o1;
                Part part2 = (Part) o2;

                switch(sortByChoice) {
                    case ISBASIC:
                        String ti1 = part1.getPartType().toString();
                        String ti2 = part2.getPartType().toString();
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
                    case DESCRIPTION:
                        String d1 = part1.getShortDescription();
                        String d2 = part2.getShortDescription();
                        if ( reverseit ) {
                            return d2.compareTo( d1 );
                        } else {
                            return d1.compareTo( d2 );
                        }
                    case SEQUENCE:
                        String s1 = part1.getSeq().getSeq();
                        String s2 = part2.getSeq().getSeq();
                        if ( reverseit ) {
                            return s2.compareTo( s1 );
                        } else {
                            return s1.compareTo( s2 );
                        }
                    case FORMAT:
                        String f1 = part1.getFormat().getName();
                        String f2 = part2.getFormat().getName();
                        if ( reverseit ) {
                            return f2.compareTo( f1 );
                        } else {
                            return f1.compareTo( f2 );
                        }
                    case AUTHOR:
                        String a1 = part1.getAuthor().getName();
                        String a2 = part2.getAuthor().getName();
                        if ( reverseit ) {
                            return a2.compareTo( a1 );
                        } else {
                            return a1.compareTo( a2 );
                        }
                    case RG:
                        String r1 = ((Short) part1.getRiskGroup()).toString();
                        String r2 = ((Short) part2.getRiskGroup()).toString();
                        if ( reverseit ) {
                            return r2.compareTo( r1 );
                        } else {
                            return r1.compareTo( r2 );
                        }
                    default:
                        return 1;
                }
            }
        };
        java.util.Collections.sort( inlist, comparator );

        for ( Part p : inlist ) {
            out.add( p );
        }
        return out;
    }


    private String findStatus(Part apart) {
        ArrayList<String> tags = apart.getSearchTags();
        if(tags.contains("works")) {
            return "works";
        } else if(tags.contains("fails")) {
            return "fails";
        } else {
            return "";
        }
    }
    private void initComponents() {
        scroller = new javax.swing.JScrollPane();
        table = new JXTable();

        //prefs
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //table.setSearchable(new Searchable());
        table.getTableHeader().addMouseListener(new headerListener());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                _prefs.putInt("spreaditPartsWidth", getWidth());
                _prefs.putInt("spreaditPartsHeight", getHeight());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowclosing");

                List<TableColumn> cols = table.getColumns();
                for(int i=0; i<cols.size(); i++) {
                    _prefs.putInt("spreaditPartsColumn" + i, columnWidths[i]);
                }
            }
        });

        //prefs
        table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

            public void columnAdded(TableColumnModelEvent e) {
            }

            public void columnRemoved(TableColumnModelEvent e) {
            }
            public void columnMoved(TableColumnModelEvent e) {
            }
            public void columnMarginChanged(ChangeEvent e) {
                List<TableColumn> cols = table.getColumns();
                for(int i=0; i<cols.size(); i++) {
                    columnWidths[i] = cols.get(i).getWidth();
                }
            }
            public void columnSelectionChanged(ListSelectionEvent e) {
            }
        });
        
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
                    newcoll.changeName("Spreadit part collection");
                    newcoll.changeDescription("Temporary collection of parts");
                    new spreaditParts(newcoll);
                }
            });
            fileMenu.add(newWindow);

            JMenuItem addBasic = new JMenuItem();
            addBasic.setText("Add basic Part");
            addBasic.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            addBasic.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    basicEnterer dialog = new basicEnterer(myself, true);
                    Part aPart = dialog.getPart();
                    if(dialog.cancelled) {
                        return;
                    }
                    if(aPart!=null) {
                        _Collection.addObject(aPart);
                        System.out.println("composite Part added");
                    } else {
                        JOptionPane.showMessageDialog(spreaditParts.this,
                            "Something was wrong with your part, it was rejected.  Check that the sequence obeys the Format.",
                            "Basic part failed",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            fileMenu.add(addBasic);

            JMenuItem addComposite = new JMenuItem();
            addComposite.setText("Add composite Part");
            addComposite.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
            addComposite.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    compositeEnterer dialog = new compositeEnterer(myself, true);
                    if(dialog.cancelled) {
                        return;
                    }
                    Part aPart = dialog.getPart();
                    if(aPart!=null) {
                        _Collection.addObject(aPart);
                        System.out.println("composite Part added");
                    } else {
                        JOptionPane.showMessageDialog(spreaditParts.this,
                            "Something was wrong with your part, it was rejected.  Perhaps these parts cannot be composed together",
                            "Composite part failed",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            fileMenu.add(addComposite);

            JMenuItem changeColl = new JMenuItem();
            changeColl.setText("Change Default Collection");
            changeColl.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //Throw up a dialog and get user to select the collection stored as 'chosen'
                    ArrayList<ObjLink> allColl = Collector.getAllLinksOf(ObjType.COLLECTION);
                    if(allColl.isEmpty()) {
                        return;
                    }
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

            JMenuItem changeIcon = new JMenuItem();
            changeIcon.setText("Change icon");
            changeIcon.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //DO A FILE CHOOSER AND CHANGE LOGO.PNG TO THE FILE
                }
            });
            fileMenu.add(changeIcon);
            
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
                        Part selectPart = allParts.get(i);
                        selectPart.saveDefault();
                    }
                }
            });
            selectMenu.add(save);
/*
            JMenuItem copyall = new JMenuItem();
            copyall.setText("Copy selected");
            copyall.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int[] selected = table.getSelectedRows();
                    copyList.clear();
                    cutmode = true;
                    copiedWindow = spreaditParts.this;
                    for(int i: selected) {
                        Part selectPart = allParts.get(i);
                        copyList.add(selectPart);
                    }
                }
            });
            //selectMenu.add(copyall);

            JMenuItem cutall = new JMenuItem();
            cutall.setText("Cut selected");
            cutall.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int[] selected = table.getSelectedRows();
                    copyList.clear();
                    cutmode = false;
                    copiedWindow = spreaditParts.this;
                    for(int i: selected) {
                        Part selectPart = allParts.get(i);
                        copyList.add(selectPart);
                    }
                }
            });
            //selectMenu.add(cutall);

            JMenuItem pasteselected = new JMenuItem();
            pasteselected.setText("Paste selected");
            pasteselected.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    for(Part apart: copyList) {
                        _Collection.addObject(apart);
                    }
                    if(copiedWindow!=null) {
                        if(cutmode) {
                            for(Part apart: copyList) {
                                copiedWindow._Collection.removeItem(apart);
                            }
                        }
                    }
                    cutmode = false;
                }
            });
            //selectMenu.add(pasteselected);
*/


        JPanel plasmidCalculator = new JPanel();
        plasmidCalculator.setLayout(null);
        plasmidCalculator.setBackground(null);
        plasmidCalculator.setBorder(null);
        plasmidCalculator.setOpaque(false);
        menuBar.add(plasmidCalculator);

            JLabel vectorLabel = new JLabel("Vector:");
            vectorLabel.setForeground(Color.WHITE);
            vectorLabel.setBounds(25, 1, 50, 21);
            vectorLabel.setFont(labelText);
            plasmidCalculator.add(vectorLabel);

            vectorOfPlasmidCalc = new JTextField();
            vectorOfPlasmidCalc.setText("");
            vectorOfPlasmidCalc.setBounds(80, 1, 75, 21);
            vectorOfPlasmidCalc.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            final String vectorname = vectorOfPlasmidCalc.getText();
                            if(vectorname.equals("")) {
                                return;
                            }
                            new Thread() {
                            @Override
                                public void run() {
                                    chosenVector = Vector.retrieveByName(vectorname);
                                    if(chosenVector==null) {
                                        Part apart = Part.retrieveByName(vectorname);
                                        if(apart!=null) {
                                            JOptionPane.showMessageDialog(spreaditParts.this,
                                                "It looks like you put a part into the vector field.",
                                                "Data is swapped",
                                                JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(spreaditParts.this,
                                                "I couldn't find that vector.",
                                                "Vector unavailable",
                                                JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    runPlasmidCalculator();
                                }
                            }.start();
                        }
                    });
                }
            });
            plasmidCalculator.add(vectorOfPlasmidCalc);

            JLabel partLabel = new JLabel("Part:");
            partLabel.setForeground(Color.WHITE);
            partLabel.setBounds(175, 1, 40, 21);
            partLabel.setFont(labelText);
            plasmidCalculator.add(partLabel);

            partOfPlasmidCalc = new JTextField();
            partOfPlasmidCalc.setText("");
            partOfPlasmidCalc.setBounds(220, 1, 75, 21);
            partOfPlasmidCalc.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            final String partname = partOfPlasmidCalc.getText();
                            if(partname.equals("")) {
                                return;
                            }
                            new Thread() {
                            @Override
                                public void run() {
                                    chosenPart = Part.retrieveByName(partname);
                                    if(chosenPart==null) {
                                        Vector avect = Vector.retrieveByName(partname);
                                        if(avect!=null) {
                                            JOptionPane.showMessageDialog(spreaditParts.this,
                                                "It looks like you put a vector into the part field.",
                                                "Data is swapped",
                                                JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(spreaditParts.this,
                                                "I couldn't find that part.",
                                                "Part unavailable",
                                                JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    runPlasmidCalculator();
                                }
                            }.start();
                        }
                    });
                }
            });
            plasmidCalculator.add(partOfPlasmidCalc);

            plasmidIcon = new TransparentButton(ImageSource.getObjectImage(ObjType.PLASMID, 20));
            plasmidIcon.setEnterAlpha(0.8f);
            plasmidIcon.setExitAlpha(1.0f);
            plasmidIcon.setLocation(310,1);
            plasmidCalculator.add(plasmidIcon);
            plasmidIcon.setVisible(false);

            digestIcon = new TransparentButton(ImageUtilities.loadImageIcon("org/clothocad/tool/spreaditparts/scissors.png", false));
            digestIcon.setEnterAlpha(0.8f);
            digestIcon.setExitAlpha(1.0f);
            digestIcon.setLocation(332,1);
            digestIcon.setToolTipText("Calculate plasmid digest");
            plasmidCalculator.add(digestIcon);
            digestIcon.addMouseListener(new MousePopupListener());
            createPopupMenu();
            digestIcon.setVisible(false);

        setJMenuBar(menuBar);
    }
    
        private void createPopupMenu() {
            popup = new JPopupMenu();

            //Listener for the second enzyme
            final ActionListener secondEnzListener = new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                    JMenuItem chosen = (JMenuItem) e.getSource();
                    secondEnz = chosen.getText();
                    System.out.println(firstEnz + " " + secondEnz);
                    plasmidDigester pd = new plasmidDigester();
                    pd.runDigest(pd.new digItem(chosenPlasmid, firstEnz, secondEnz));
              }
            };

            //Listener for the first enzyme
            MenuAdapter menuListener = new MenuAdapter() {
                @Override
                public void menuSelected(MenuEvent e) {
                    JMenu chosen = (JMenu) e.getSource();
                    firstEnz = chosen.getText();
                    String[] enzlist2 = new String[] {"EcoRI","XhoI","AlwNI", "BglI", "PstI"};
                    chosen.removeAll();
                    for(int i=0; i<enzlist2.length; i++) {
                        JMenuItem item = new JMenuItem(enzlist2[i]);
                        item.addActionListener(secondEnzListener);
                        chosen.add(item);
                    }
                }
            };

            String[] enzlist1 = new String[] {"EcoRI","BamHI","BglII","XhoI","XbaI", "SpeI", "PstI"};

            for(int i=0; i<enzlist1.length; i++) {
                JMenu item = new JMenu(enzlist1[i]);
                item.addMenuListener(menuListener);
                popup.add(item);
            }
        }

        class MenuAdapter implements MenuListener {
            @Override
            public void menuSelected(MenuEvent e) {
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        }

        //Popup listening for digestIcon
        private class MousePopupListener extends MouseAdapter {
            @Override
            public void mousePressed(MouseEvent e) {
              checkPopup(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
              checkPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
              checkPopup(e);
            }

            private void checkPopup(MouseEvent e) {
              if (e.isPopupTrigger()) {
                popup.show(digestIcon, e.getX(), e.getY());
              }
            }
        }

        private void runPlasmidCalculator() {
            if(!isInitiated) {
                System.out.println("runPlasmidCalculator:  not initiated");
                return;
            }
            //If something is null, clear everything
            if(chosenPart==null || chosenVector==null) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        plasmidIcon.setVisible(false);
                        digestIcon.setVisible(false);
                    }
                });
                chosenPlasmid=null;
                System.out.println("spreaditParts runPlasmidCalculator:  something is null");
                return;
            }

            //Try to get or create the plasmid
            chosenPlasmid = Plasmid.retrieveByName(chosenVector.getName() + "-" + chosenPart.getName());
            if(chosenPlasmid==null) {
                System.out.println("runPlasmidCalculator:  plasmid by name retrieval failed");
                chosenPlasmid = Plasmid.generatePlasmid(chosenPart, chosenVector, Collector.getCurrentUser(), chosenPart.getFormat());
                chosenPlasmid.setTransient();
            }

            //Quit if that didn't produce a plasmid
            if(chosenPlasmid==null) {
                System.out.println("runPlasmidCalculator:  plasmid was null");
                return;
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    plasmidIcon.setVisible(true);
                    digestIcon.setVisible(true);
                }
            });
            
            ObjBaseObserver obr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, RefreshEvent evt) {
                }
            };
            chosenPlasmid.isRepresentedBy(obr, plasmidIcon);
            popper = new ObjBasePopup(plasmidIcon,chosenPlasmid);
        }

        private void searchSeq(String tags) {
            try {
                //Query the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.PART );
                ClothoQuery seqQuery = mainQuery.createAssociationQuery( Part.Fields.SEQUENCE );
                seqQuery.contains( NucSeq.Fields.SEQUENCE, tags, false );
                List<ObjBase> results = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Parts for " + tags);
                for(ObjBase apart : results) {
                    outcoll.addObject(apart);
                }
                new spreaditParts(outcoll);
            } catch( Exception e) {
                e.printStackTrace();
                System.out.println("Sequence find Encountered an error");
            }
        }

        private void searchString(String tags) {
            try {
                //Query the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.PART );
                mainQuery.contains( Part.Fields.DESCRIPTION, tags, false );
                List<ObjBase> descresults = mainQuery.getResults();

                c = Collector.getDefaultConnection();
                mainQuery = c.createQuery( ObjType.PART );
                mainQuery.contains( Part.Fields.NAME, tags, false );
                List<ObjBase> nameresults = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Parts for " + tags);
                for(ObjBase apart : descresults) {
                    outcoll.addObject(apart);
                }
                for(ObjBase apart : nameresults) {
                    outcoll.addObject(apart);
                }
                new spreaditParts(outcoll);
            } catch( Exception e) {
                e.printStackTrace();
            }
        }

        private void processPartChange(Part aPart, int row, int column, String val) {
            System.out.println(aPart.getName());
            System.out.println(column);
            switch(column) {
                case 0:
                    String isbasicstring = "c";
                    if(aPart.getPartType().equals(Part.partType.Basic)) {
                        isbasicstring = "b";
                    }
                    if(isbasicstring.equals(val)) {
                        return;
                    }
                    table.setValueAt(isbasicstring, row, column);
                    break;
                case 1:
                    if(aPart.getName().equals(val)) {
                        return;
                    }
                    aPart.changeName(val);
                    table.setValueAt(aPart.getName(), row, column);
                    break;
                case 2:
                    if(aPart.getShortDescription().equals(val)) {
                        return;
                    }
                    aPart.changeShortDescription(val);
                    table.setValueAt(aPart.getShortDescription(), row, column);
                    break;
                case 3:
                    if(aPart.getSeq().toString().equals(val)) {
                        return;
                    }
                    aPart.changeSequence(val);
                    table.setValueAt(aPart.getSeq().toString(), row, column);
                    break;
                case 4:
                    if(aPart.getFormat().getName().equals(val)) {
                        return;
                    }
                    Format form = Format.retrieveByName(val);
                    aPart.changeFormat(form);
                    table.setValueAt(aPart.getFormat().getName(), row, column);
                    break;
                case 5:
                    if(aPart.getAuthor().getName().equals(val)) {
                        return;
                    }
                    Person aPerson = Person.retrieveByName(val);
                    aPart.changeAuthor(aPerson);
                    table.setValueAt(aPart.getAuthor().getName(), row, column);
                    break;
                case 6:
                    System.out.println("changed the find status");
                    if(findStatus(aPart).equals(val)) {
                        return;
                    }
                    if(val.equals("works")) {
                        aPart.addSearchTag("works");
                        aPart.removeSearchTag("fails");
                    } else if(val.equals("fails")) {
                        aPart.removeSearchTag("works");
                        aPart.addSearchTag("fails");
                    }
                    table.setValueAt(findStatus(aPart), row, column);
                    break;
                case 7:
                    if(aPart.getRiskGroup() == Short.parseShort(val)) {
                        return;
                    }
                    short rg = Short.parseShort(val);
                    System.out.println("risk group new is " + rg);
                    aPart.changeRiskGroup(rg);
                    table.setValueAt(((Short) aPart.getRiskGroup()).toString(), row, column);
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
    private ArrayList<Part> allParts;

    static Preferences _prefs;   //prefs
    private int[] columnWidths = new int[8];
    List<ObjBaseObserver> obrlist = new ArrayList<ObjBaseObserver>();
    refresher refreshOBO;

    //Plasmid calculator fields
    private Vector chosenVector;
    private Part chosenPart;
    private Plasmid chosenPlasmid;
    private ObjBasePopup popper;
    private JTextField vectorOfPlasmidCalc;
    private JTextField partOfPlasmidCalc;
    private TransparentButton plasmidIcon;
    private static Font labelText = new java.awt.Font("Arial", Font.ITALIC, 10);
    private boolean isInitiated = false;

    //Digest calculator fields
    private TransparentButton digestIcon;
    private JPopupMenu popup;
    private String firstEnz;
    private String secondEnz;

    public enum sortBy {ISBASIC, NAME, DESCRIPTION, SEQUENCE, FORMAT, AUTHOR, RG};
    private sortBy sortByChoice = sortBy.NAME;
    private boolean reverseit = false;
}
