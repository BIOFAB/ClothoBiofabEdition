/*
 Copyright (c) 2009 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY OLIGOY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A OLIGOICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS..
 */

/*
 * spreaditOligos.java
 *
 * Created on Aug 4, 2010, 9:03:45 AM
 */

package org.clothocad.tool.spreaditoligos;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
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
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.dnd.RefreshEvent.Condition;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author J. Christopher Anderson
 */
public class spreaditOligos extends javax.swing.JFrame  {

    /** Creates new changeFormat spreaditOligos */
    public spreaditOligos(Collection coll) {
        super("Spreadit Oligos: " + getStringName(coll));
        setIconImage(ImageSource.getTinyLogo());
        _prefs = Preferences.userNodeForPackage(spreaditOligos.class);
        _Collection = coll;
        refreshOBO = new refresher();
        _Collection.isRepresentedBy(refreshOBO, this);
        new ObjBasePopup(getContentPane(), _Collection);

        _title = new String[] { "Nickname", "Short Description", "Sequence", "Author", "Keywords"};
        initComponents();

        //prefs
        int wide = _prefs.getInt("spreaditOligosWidth", 600);
        int high = _prefs.getInt("spreaditOligosHeight", 650);

        setSize(wide,high);
        setVisible(true);
        ExcelAdapter myAd = new ExcelAdapter(table);
        new Thread() {
            @Override
            public void run() {
                parseOligos();

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
            final int wide = _prefs.getInt("spreaditOligosColumn" + i, cols.get(i).getWidth());
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
                    parseOligos();
                }
            }.start();
        }
    }

    private void parseOligos() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                ArrayList<Oligo> inlist = (ArrayList<Oligo>) _Collection.getAll(ObjType.OLIGO);
                allOligos = sortList(inlist);
                _data = new String [allOligos.size()][5];
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
        
        System.out.println(allOligos.size());
        obrlist.clear();
        for(int i=0; i<allOligos.size(); i++ ) {
            final Oligo currOligo = allOligos.get(i);
            if(currOligo==null) {
                continue;
            }
            final int index = i;
            ObjBaseObserver obr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, final RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    refreshValues((Oligo) obj, evt);
                }
            };
            obrlist.add(obr);
            currOligo.isObservedBy(obr);
            obr.update(currOligo, new RefreshEvent(currOligo, Condition.UNKNOWN));

            ObjBaseObserver nucobr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, final RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    if(evt.isCondition(Condition.SEQUENCE_CHANGED)) {
                    table.setValueAt(((NucSeq) obj).toString(), index, 2);
                    }
                }
            };
            obrlist.add(nucobr);
            currOligo.getSeq().isObservedBy(nucobr);
            nucobr.update(currOligo.getSeq(), new RefreshEvent(currOligo.getSeq(), Condition.SEQUENCE_CHANGED));

            isInitiated = true;
        }

        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                table.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        Oligo changedOligo = allOligos.get(e.getFirstRow());
                        processOligoChange(changedOligo, e.getFirstRow(), e.getColumn(), (String) tableModel.getValueAt(e.getFirstRow(), e.getColumn()));
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
                            final Oligo aOligo = allOligos.get(index);
                            ObjBasePopup obp = new ObjBasePopup(table, aOligo, e.getPoint());
                            obp.addMenuItem("Remove from Collection", new java.awt.event.ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    _Collection.removeItem(aOligo);
                                }
                            });
                        }
                     }
                });
            }
        });
    }

    private void refreshValues(Oligo currOligo, RefreshEvent evt) {
        int index = allOligos.indexOf(currOligo);
        table.setValueAt(currOligo.getName(), index, 0);
        table.setValueAt(currOligo.getDescription(), index, 1);
        table.setValueAt(currOligo.getAuthor().getName(), index, 3);
        if(evt.isCondition(Condition.UNKNOWN)
                || evt.isCondition(Condition.SEARCHTAG_ADDED)
                || evt.isCondition(Condition.SEARCHTAG_REMOVED)) {
            table.setValueAt(writeKeywords(currOligo), index, 4);
        }
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
                        sortmode = sortBy.NAME;
                        break;
                    case 1:
                        sortmode = sortBy.DESCRIPTION;
                        break;
                    case 2:
                        sortmode = sortBy.SEQUENCE;
                        break;
                    case 3:
                        sortmode = sortBy.AUTHOR;
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
        parseOligos();
    }

    private ArrayList<Oligo> sortList(ArrayList<Oligo> inlist) {
        ArrayList<Oligo> out = new ArrayList<Oligo>();

        Comparator comparator = new Comparator() {

            @Override
            public int compare( Object o1, Object o2 ) {
                Oligo oligo1 = (Oligo) o1;
                Oligo oligo2 = (Oligo) o2;

                switch(sortByChoice) {
                    case NAME:
                        String i1 = oligo1.getName();
                        String i2 = oligo2.getName();
                        if ( reverseit ) {
                            return i2.compareTo( i1 );
                        } else {
                            return i1.compareTo( i2 );
                        }
                    case DESCRIPTION:
                        String s1 = oligo1.getDescription();
                        String s2 = oligo2.getDescription();
                        if ( reverseit ) {
                            return s2.compareTo( s1 );
                        } else {
                            return s1.compareTo( s2 );
                        }
                    case SEQUENCE:
                        String os1 = oligo1.getSeq().getSeq();
                        String os2 = oligo2.getSeq().getSeq();
                        if ( reverseit ) {
                            return os2.compareTo( os1 );
                        } else {
                            return os1.compareTo( os2 );
                        }
                    case AUTHOR:
                        String a1 = oligo1.getAuthor().getName();
                        String a2 = oligo2.getAuthor().getName();
                        if ( reverseit ) {
                            return a2.compareTo( a1 );
                        } else {
                            return a1.compareTo( a2 );
                        }
                    default:
                        return 1;
                }
            }
        };
        java.util.Collections.sort( inlist, comparator );

        for ( Oligo p : inlist ) {
            out.add( p );
        }
        return out;
    }


    private String writeKeywords(Oligo aoligo) {
        ArrayList<String> tags = aoligo.getSearchTags();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<tags.size(); i++) {
            sb.append(tags.get(i));
            if(i<tags.size()-1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    private void initComponents() {
        scroller = new javax.swing.JScrollPane();
        table = new JXTable();

        //prefs
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getTableHeader().addMouseListener(new headerListener());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                _prefs.putInt("spreaditOligosWidth", getWidth());
                _prefs.putInt("spreaditOligosHeight", getHeight());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowclosing");

                List<TableColumn> cols = table.getColumns();
                for(int i=0; i<cols.size(); i++) {
                    _prefs.putInt("spreaditOligosColumn" + i, columnWidths[i]);
                }
            }
        });

        //prefs
        table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

            @Override
            public void columnAdded(TableColumnModelEvent e) {
            }

            @Override
            public void columnRemoved(TableColumnModelEvent e) {
            }
            @Override
            public void columnMoved(TableColumnModelEvent e) {
            }
            @Override
            public void columnMarginChanged(ChangeEvent e) {
                List<TableColumn> cols = table.getColumns();
                for(int i=0; i<cols.size(); i++) {
                    columnWidths[i] = cols.get(i).getWidth();
                }
            }
            @Override
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
                    newcoll.changeName("Spreadit oligo collection");
                    newcoll.changeDescription("Temporary collection of oligos");
                    new spreaditOligos(newcoll);
                }
            });
            fileMenu.add(newWindow);

            JMenuItem addOligo = new JMenuItem();
            addOligo.setText("Add Oligo");
            addOligo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            addOligo.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    oligoEnterer dialog = new oligoEnterer(myself, true);
                    Oligo aOligo = dialog.getOligo();
                    if(aOligo!=null) {
                        _Collection.addObject(aOligo);
                        System.out.println("basic Oligo added");
                    }
                }
            });
            fileMenu.add(addOligo);

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
                        Oligo selectOligo = allOligos.get(i);
                        selectOligo.saveDefault();
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
                ClothoQuery mainQuery = c.createQuery( ObjType.OLIGO );
                ClothoQuery seqQuery = mainQuery.createAssociationQuery( Oligo.Fields.SEQUENCE );
                seqQuery.contains( NucSeq.Fields.SEQUENCE, tags, false );
                List<ObjBase> results = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Oligos for " + tags);
                for(ObjBase aOligo : results) {
                    outcoll.addObject(aOligo);
                }
                new spreaditOligos(outcoll);
            } catch( Exception e) {
                System.out.println("Sequence find Encountered an error");
            }
        }

        private void searchString(String tags) {
            try {
                //Query the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.OLIGO );
                mainQuery.contains( Oligo.Fields.DESCRIPTION, tags, false );
                List<ObjBase> descresults = mainQuery.getResults();

                c = Collector.getDefaultConnection();
                mainQuery = c.createQuery( ObjType.OLIGO );
                mainQuery.contains( Oligo.Fields.NAME, tags, false );
                List<ObjBase> nameresults = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Oligos for " + tags);
                for(ObjBase aOligo : descresults) {
                    outcoll.addObject(aOligo);
                }
                for(ObjBase aOligo : nameresults) {
                    outcoll.addObject(aOligo);
                }
                new spreaditOligos(outcoll);
            } catch( Exception e) {
            }
        }

        private void processOligoChange(Oligo aOligo, int row, int column, String val) {
            switch(column) {
                case 0:
                    if(aOligo.getName().equals(val)) {
                        return;
                    }
                    aOligo.changeName(val);
                    table.setValueAt(aOligo.getName(), row, column);
                    break;
                case 1:
                    if(aOligo.getDescription().equals(val)) {
                        return;
                    }
                    aOligo.changeDescription(val);
                    table.setValueAt(aOligo.getDescription(), row, column);
                    break;
                case 2:
                    if(aOligo.getSeq().toString().equals(val)) {
                        return;
                    }
                    aOligo.changeSequence(val);
                    table.setValueAt(aOligo.getSeq().toString(), row, column);
                    break;
                case 3:
                    if(aOligo.getAuthor().getName().equals(val)) {
                        return;
                    }
                    Person aPerson = Person.retrieveByName(val);
                    aOligo.changeAuthor(aPerson);
                    table.setValueAt(aOligo.getAuthor().getName(), row, column);
                    break;
                case 4:
                    if(writeKeywords(aOligo).equals(val)) {
                        return;
                    }
                    ArrayList<String> oldtags = aOligo.getSearchTags();
                    ArrayList<String> newtags = new ArrayList<String>();
                    ArrayList<String> addlist = new ArrayList<String>();
                    ArrayList<String> remlist = new ArrayList<String>();

                    //Parse out what they typed in
                    String[] tags = val.split("[\\,.]+");
                    for(String tag : tags) {
                        String cleaned = tag.trim();
                        newtags.add(cleaned);
                        System.out.println(tags);
                    }
                    
                    //Decide what goes and what stays
                    for(String tag : newtags) {
                        if(!oldtags.contains(tag)) {
                            addlist.add(tag);
                        }
                    }
                    
                    for(String tag : oldtags) {
                        if(!newtags.contains(tag)) {
                            remlist.add(tag);
                        }
                    }
                    
                    //Make changes to the searchtags
                    for(String tag : addlist) {
                        aOligo.addSearchTag(tag);
                    }
                    
                    for(String tag : remlist) {
                        aOligo.removeSearchTag(tag);
                    }
                    return;
            }
        }
/*
anoligo = Oligo.retrieveByName("ca998")
acoll = new Collection()
acoll.addObject(anoligo)
acoll.launchDefaultViewer()
 */
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
    private ArrayList<Oligo> allOligos;

    static Preferences _prefs;   //prefs
    private int[] columnWidths = new int[5];
    List<ObjBaseObserver> obrlist = new ArrayList<ObjBaseObserver>();
    refresher refreshOBO;
    
    //Plasmid calculator fields
    private Vector chosenVector;
    private Oligo chosenOligo;
    private Plasmid chosenPlasmid;
    private ObjBasePopup popper;
    private JTextField vectorOfPlasmidCalc;
    private JTextField oligoOfPlasmidCalc;
    private TransparentButton plasmidIcon;
    private static Font labelText = new java.awt.Font("Arial", Font.ITALIC, 10);
    private boolean isInitiated = false;

    //Digest calculator fields
    private TransparentButton digestIcon;
    private JPopupMenu popup;
    private String firstEnz;
    private String secondEnz;

    public enum sortBy {NAME, DESCRIPTION, SEQUENCE, AUTHOR};
    private sortBy sortByChoice = sortBy.NAME;
    private boolean reverseit = false;
}
