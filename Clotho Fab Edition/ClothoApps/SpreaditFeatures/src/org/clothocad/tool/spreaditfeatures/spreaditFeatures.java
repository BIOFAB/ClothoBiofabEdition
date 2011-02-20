/*
 Copyright (c) 2009 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY FEATUREY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A FEATUREICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS..
 */

/*
 * spreaditFeatures.java
 *
 * Created on Aug 4, 2010, 9:03:45 AM
 */

package org.clothocad.tool.spreaditfeatures;

import java.awt.Rectangle;
import org.clothocad.tool.spreaditfeatures.helpers.HTMLColors;
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
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.dnd.RefreshEvent.Condition;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocad.tool.spreaditfeatures.colors.ColorPicker;
import org.clothocad.tool.spreaditfeatures.helpers.ButtonEditor;
import org.clothocad.tool.spreaditfeatures.helpers.ColorRenderer;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author J. Christopher Anderson
 */
public class spreaditFeatures extends javax.swing.JFrame  {

    /** Creates new changeFormat spreaditFeatures */
    public spreaditFeatures(Collection coll) {
        super("Spreadit Features: " + getStringName(coll));
        setIconImage(ImageSource.getTinyLogo());
        _prefs = Preferences.userNodeForPackage(spreaditFeatures.class);
        _Collection = coll;
        refreshOBO = new refresher();
        _Collection.isRepresentedBy(refreshOBO, this);
        new ObjBasePopup(getContentPane(), _Collection);

        _title = new String[] { "Name", "Sequence", "RG", "Author", "For Color", "Rev Color", "Families", "Notes"};
        initComponents();

        //prefs
        int wide = _prefs.getInt("spreaditFeaturesWidth", 600);
        int high = _prefs.getInt("spreaditFeaturesHeight", 650);

        setSize(wide,high);
        setVisible(true);
        ExcelAdapter myAd = new ExcelAdapter(table);
        new Thread() {
            @Override
            public void run() {
                parseFeatures();

                //prefs
                setWidths();
            }
        }.start();

        cpx = new ColorPicker();
        cpx.setVisible(false);
        getLayeredPane().add(cpx,2);
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
            final int wide = _prefs.getInt("spreaditFeaturesColumn" + i, cols.get(i).getWidth());
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
            if(evt.isCondition(Condition.COLLECTION_ADD) || evt.isCondition(Condition.COLLECTION_REMOVE)  ) {
                new Thread() {
                    @Override
                    public void run() {
                        parseFeatures();
                    }
                }.start();
            }
        }
    }

    private void parseFeatures() {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                ArrayList<Feature> inlist = (ArrayList<Feature>) _Collection.getAll(ObjType.FEATURE);
                allFeatures = sortList(inlist);
                _data = new String [allFeatures.size()][8];
                return null;
            }

            @Override
            public void done() {
                edtRelay();
            }
        }.execute();
    }

    private void edtRelay() {
        //Set the table model
        finalTableModel = new DefaultTableModel(_data, _title);
        table.setModel(finalTableModel);

        //Put in special renderers for various columns
        table.setDefaultRenderer(java.lang.Object.class, new ColorRenderer(this));

        //Put in icon/note chooser for Button column
        TableColumn column = table.getColumnModel().getColumn(7);
        column.setCellEditor( new ButtonEditor(this));

        //Put in the data and listeners
        System.out.println(allFeatures.size());
        obrlist.clear();
        for(int i=0; i<allFeatures.size(); i++ ) {
            final Feature currFeat = allFeatures.get(i);
            if(currFeat==null) {
                continue;
            }
            final int index = i;
            ObjBaseObserver obr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, final RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    refreshValues((Feature) obj, evt);
                }
            };
            obrlist.add(obr);
            currFeat.isObservedBy(obr);
            obr.update(currFeat, new RefreshEvent(currFeat, Condition.UNKNOWN));

            ObjBaseObserver nucobr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, final RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    if(evt.isCondition(Condition.SEQUENCE_CHANGED)) {
                        table.setValueAt(((NucSeq) obj).toString(), index, 1);
                    }
                }
            };
            obrlist.add(nucobr);
            currFeat.getSeq().isObservedBy(nucobr);
            nucobr.update(currFeat.getSeq(), new RefreshEvent(currFeat.getSeq(), Condition.SEQUENCE_CHANGED));

            isInitiated = true;
        }

        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                table.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        Feature changedFeature = allFeatures.get(e.getFirstRow());
                        Object ovalue = finalTableModel.getValueAt(e.getFirstRow(), e.getColumn());
                        String value = null;
                        try {
                            value = (String) ovalue;
                        } catch(Exception er) {
                        }
                        if(value==null) {
                            try {
                                value = Integer.toString((Integer) ovalue);
                            } catch(Exception er) {
                            }
                        }
                        if(value==null) {
                            try {
                                value = Short.toString((Short) ovalue);
                            } catch(Exception er) {
                            }
                        }
                        processFeatureChange(changedFeature, e.getFirstRow(), e.getColumn(), value);
                    }
                });
                table.addMouseListener(new MouseAdapter() {
                     @Override
                     public void mousePressed( MouseEvent e ) {
                        if(e.getModifiers()==4) {
                            //Start editing mode on all the buttons so that they are right-clickable
                            for(int row = 0; row< table.getRowCount(); row++) {
                                table.editCellAt(row, 7);
                            }
                            
                            if(table.getSelectedRows().length>1) {
                                return;
                            }
                            int index = table.getSelectedRow();
                            Feature feat = null;
                            try {
                                feat = allFeatures.get(index);
                            } catch (Exception err) {
                                return;
                            }
                            if(feat==null) {
                                return;
                            }
                            final Feature aFeature = feat;

                            boolean pointup = false;
                            int rowcount = table.getRowCount();
                            if(index > rowcount/2) {
                                pointup = true;
                            }
                            Rectangle over4 = table.getCellRect(index, 4, true);
                            if(over4.contains(e.getPoint())) {
                                int posx = over4.x+10;
                                if(pointup) {
                                    int posy = over4.y + 40 - ColorPicker.getOffset();
                                    cpx.setLocation(posx, posy);
                                } else {
                                    int posy = over4.y + 53;
                                    cpx.setLocation(posx, posy);
                                }
                                cpx.setVisible(true);
                                cpx.feature = aFeature;
                                cpx.isForward = true;
                                return;
                            }

                            Rectangle over5 = table.getCellRect(index, 5, true);
                            if(over5.contains(e.getPoint())) {
                                int posx = over5.x+10;
                                if(pointup) {
                                    int posy = over5.y + 40 - ColorPicker.getOffset();
                                    cpx.setLocation(posx, posy);
                                } else {
                                    int posy = over5.y + 53;
                                    cpx.setLocation(posx, posy);
                                }
                                cpx.setVisible(true);
                                cpx.feature = aFeature;
                                cpx.isForward = false;
                                return;
                            }
                            ObjBasePopup obp = new ObjBasePopup(table, aFeature, e.getPoint());
                            obp.addMenuItem("Remove from Collection", new java.awt.event.ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    _Collection.removeItem(aFeature);
                                }
                            });
                            cpx.setVisible(false);
                        } else {
                            cpx.setVisible(false);
                        }
                     }
                });
            }
        });
    }

    private void refreshValues(Feature currFeat, RefreshEvent evt) {
        int index = allFeatures.indexOf(currFeat);
        String isbasicstring = "c";
        if(evt.isCondition(Condition.UNKNOWN)
                || evt.isCondition(Condition.NAME_CHANGED)) {
            table.setValueAt(currFeat.getName(), index, 0);
        }
        if(evt.isCondition(Condition.UNKNOWN)
                || evt.isCondition(Condition.RISK_GROUP_CHANGED)) {
            table.setValueAt(currFeat.getRiskGroup(), index, 2);
        }
        if(evt.isCondition(Condition.UNKNOWN)
                || evt.isCondition(Condition.AUTHOR_CHANGED)) {
            table.setValueAt(currFeat.getAuthor().getName(), index, 3);
        }
        if(evt.isCondition(Condition.UNKNOWN)
                || evt.isCondition(Condition.COLOR_CHANGED)) {
            table.setValueAt(Integer.toString(currFeat.getForwardColor().getRGB()), index, 4);
            table.setValueAt(Integer.toString(currFeat.getReverseColor().getRGB()), index, 5);
        }
        if(evt.isCondition(Condition.UNKNOWN)
                || evt.isCondition(Condition.SEARCHTAG_ADDED)
                || evt.isCondition(Condition.SEARCHTAG_REMOVED)) {
            table.setValueAt(writeKeywords(currFeat), index, 6);
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
                        sortmode = sortBy.SEQUENCE;
                        break;
                    case 2:
                        sortmode = sortBy.RISK_GROUP;
                        break;
                    case 3:
                        sortmode = sortBy.AUTHOR;
                        break;
                    case 4:
                        sortmode = sortBy.FOR_COLOR;
                        break;
                    case 5:
                        sortmode = sortBy.REV_COLOR;
                        break;
                    case 6:
                        sortmode = sortBy.FAMILY;
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
        parseFeatures();
    }

    private ArrayList<Feature> sortList(ArrayList<Feature> inlist) {
        ArrayList<Feature> out = new ArrayList<Feature>();
        try {
            Comparator comparator = new Comparator() {

                @Override
                public int compare( Object o1, Object o2 ) {
                    Feature feature1 = (Feature) o1;
                    Feature feature2 = (Feature) o2;

                    switch(sortByChoice) {
                        case NAME:
                            String i1 = feature1.getName();
                            String i2 = feature2.getName();
                            if ( reverseit ) {
                                return i2.compareTo( i1 );
                            } else {
                                return i1.compareTo( i2 );
                            }
                        case FOR_COLOR:
                            Integer fi1 = feature1.getForwardColor().getRGB();
                            Integer fi2 = feature2.getForwardColor().getRGB();
                            if ( reverseit ) {
                                return fi2.compareTo( fi1 );
                            } else {
                                return fi1.compareTo( fi2 );
                            }
                        case REV_COLOR:
                            Integer ri1 = feature1.getReverseColor().getRGB();
                            Integer ri2 = feature2.getReverseColor().getRGB();
                            if ( reverseit ) {
                                return ri2.compareTo( ri1 );
                            } else {
                                return ri1.compareTo( ri2 );
                            }
                        case SEQUENCE:
                            String os1 = feature1.getSeq().getSeq();
                            String os2 = feature2.getSeq().getSeq();
                            if ( reverseit ) {
                                return os2.compareTo( os1 );
                            } else {
                                return os1.compareTo( os2 );
                            }
                        case AUTHOR:
                            String a1 = feature1.getAuthor().getName();
                            String a2 = feature2.getAuthor().getName();
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

            for ( Feature p : inlist ) {
                out.add( p );
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return out;
    }


    private String writeKeywords(Feature afeat) {
        ArrayList<String> tags = afeat.getSearchTags();
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
                _prefs.putInt("spreaditFeaturesWidth", getWidth());
                _prefs.putInt("spreaditFeaturesHeight", getHeight());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowclosing");

                List<TableColumn> cols = table.getColumns();
                for(int i=0; i<cols.size(); i++) {
                    _prefs.putInt("spreaditFeaturesColumn" + i, columnWidths[i]);
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
                    newcoll.changeName("Spreadit Feature Collection");
                    newcoll.changeDescription("Temporary Collection of Features");
                    new spreaditFeatures(newcoll);
                }
            });
            fileMenu.add(newWindow);

            JMenuItem addFeature = new JMenuItem();
            addFeature.setText("Add Feature");
            addFeature.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            addFeature.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    featureEnterer dialog = new featureEnterer(myself, true);
                    Feature afeat = dialog.getFeature();
                    if(afeat!=null) {
                        _Collection.addObject(afeat);
                        System.out.println("basic Feature added");
                    }
                }
            });
            fileMenu.add(addFeature);

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
            find.setText("Search name");
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
                        Feature selectFeat = allFeatures.get(i);
                        selectFeat.saveDefault();
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
                ClothoQuery mainQuery = c.createQuery( ObjType.FEATURE );
                ClothoQuery seqQuery = mainQuery.createAssociationQuery( Feature.Fields.SEQUENCE );
                seqQuery.contains( NucSeq.Fields.SEQUENCE, tags, false );
                List<ObjBase> results = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Features for " + tags);
                for(ObjBase aFeature : results) {
                    outcoll.addObject(aFeature);
                }
                new spreaditFeatures(outcoll);
            } catch( Exception e) {
                System.out.println("Sequence find Encountered an error");
            }
        }

        private void searchString(String tags) {
            try {
                //Query the database
                ClothoConnection c = Collector.getDefaultConnection();
                ClothoQuery mainQuery = c.createQuery( ObjType.FEATURE );
                mainQuery.contains( Feature.Fields.NAME, tags, false );
                List<ObjBase> nameresults = mainQuery.getResults();

                //Wrap the results in a transient collection and launch a viewer
                Collection outcoll = new Collection();
                outcoll.changeName("Find sequence results");
                outcoll.changeDescription("Find output from Spreadit Features for " + tags);
                for(ObjBase aFeature : nameresults) {
                    outcoll.addObject(aFeature);
                }
                new spreaditFeatures(outcoll);
            } catch( Exception e) {
            }
        }

        private void processFeatureChange(Feature aFeature, int row, int column, String val) {
            switch(column) {
                case 0:
                    if(aFeature.getName().equals(val)) {
                        return;
                    }
                    aFeature.changeName(val);
                    table.setValueAt(aFeature.getName(), row, column);
                    break;
                case 1:
                    if(aFeature.getSeq().toString().equals(val)) {
                        return;
                    }
                    aFeature.changeSequence(val);
                    table.setValueAt(aFeature.getSeq().toString(), row, column);
                    break;
                case 2:
                    String srg = Short.toString(aFeature.getRiskGroup());
                    if(srg.equals(val)) {
                        return;
                    }
                    try {
                        aFeature.changeRiskGroup(Short.parseShort(val));
                    } catch(Exception err) {
                        table.setValueAt(srg, row, column);
                    }
                    break;
                case 3:
                    if(aFeature.getAuthor().getName().equals(val)) {
                        return;
                    }
                    Person aPerson = Person.retrieveByName(val);
                    if(aPerson!=null) {
                        aFeature.changeAuthor(aPerson);
                    } else {
                        table.setValueAt(aFeature.getAuthor().getName(), row, column);
                    }
                    break;
                case 4:
                    if(Integer.toString(aFeature.getForwardColor().getRGB()).equals(val)) {
                        System.out.println("Value is unchanged");
                        return;
                    }
                    //If the user typed in a non-color, revert it
                    Color incolor = null;
                    try {
                        incolor = HTMLColors.getColor(val);
                    } catch(Exception e) {
                    }
                    if(incolor==null) {
                        try {
                            int icol = Integer.parseInt(val);
                            incolor = new Color(icol);
                        } catch(Exception e) {
                        }
                    }
                    if(incolor==null) {
                        System.out.println("Spreadit Features:  That was an invalid forward color");
                        table.setValueAt(Integer.toString(aFeature.getForwardColor().getRGB()), row, column);
                        return;
                    }
                    
                    //If it's unchanged, return
                    if(aFeature.getForwardColor().getRGB() == incolor.getRGB()) {
                        System.out.println("Spreadit Features:  forward color isn't really changed");
                        table.setValueAt(Integer.toString(aFeature.getForwardColor().getRGB()), row, column);
                        return;
                    }

                    //Otherwise, they entered a new valid color, so change it
                    System.out.println("Spreadit Features:  Changeing the forward color");
                    aFeature.changeForwardColor(incolor);
                    cpx.modColors(incolor);
                    break;
                case 5:
                    if(Integer.toString(aFeature.getReverseColor().getRGB()).equals(val)) {
                        System.out.println("Value is unchanged");
                        return;
                    }
                    //If the user typed in a non-color, revert it
                    Color in2color = null;
                    try {
                        in2color = HTMLColors.getColor(val);
                    } catch(Exception e) {
                    }
                    if(in2color==null) {
                        try {
                            int icol = Integer.parseInt(val);
                            in2color = new Color(icol);
                        } catch(Exception e) {
                        }
                    }
                    if(in2color==null) {
                        System.out.println("Spreadit Features:  That was an invalid reverse color");
                        table.setValueAt(Integer.toString(aFeature.getReverseColor().getRGB()), row, column);
                        return;
                    }

                    //If it's unchanged, return
                    if(aFeature.getReverseColor().getRGB() == in2color.getRGB()) {
                        System.out.println("Spreadit Features:  reverse color isn't really changed");
                        table.setValueAt(Integer.toString(aFeature.getReverseColor().getRGB()), row, column);
                        return;
                    }

                    //Otherwise, they entered a new valid color, so change it
                    System.out.println("Spreadit Features:  Changeing the forward color");
                    aFeature.changeReverseColor(in2color);
                    cpx.modColors(in2color);
                    break;
                case 6:
                    if(val.equals("")) {
                        return;
                    }
                    table.setValueAt("", row, column);
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

        public Feature getFeatureAt(int row) {
            return allFeatures.get(row);
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
    private ArrayList<Feature> allFeatures;
    private DefaultTableModel finalTableModel;

    static Preferences _prefs;   //prefs
    private int[] columnWidths = new int[8];
    List<ObjBaseObserver> obrlist = new ArrayList<ObjBaseObserver>();
    refresher refreshOBO;
    
    //Plasmid calculator fields
    private Vector chosenVector;
    private Feature chosenFeature;
    private Plasmid chosenPlasmid;
    private ObjBasePopup popper;
    private JTextField vectorOfPlasmidCalc;
    private JTextField featureOfPlasmidCalc;
    private TransparentButton plasmidIcon;
    private static Font labelText = new java.awt.Font("Arial", Font.ITALIC, 10);
    private boolean isInitiated = false;

    //Digest calculator fields
    private TransparentButton digestIcon;
    private JPopupMenu popup;
    private String firstEnz;
    private String secondEnz;

    public enum sortBy {NAME, SEQUENCE, AUTHOR, RISK_GROUP, FOR_COLOR, REV_COLOR, FAMILY};
    private sortBy sortByChoice = sortBy.NAME;
    private boolean reverseit = false;

    ColorPicker cpx;
}
