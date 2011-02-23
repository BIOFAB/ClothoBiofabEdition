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
 * spreaditSequencing.java
 *
 * Created on Aug 4, 2010, 9:03:45 AM
 */

package org.clothocad.tool.spreaditsequencing;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Container;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.PlasmidSample;
import org.clothocore.api.data.Sample;
import org.clothocore.api.data.Sample.sampleType;
import org.clothocore.api.data.SampleData;
import org.clothocore.api.data.SampleData.dataType;
import org.clothocore.api.data.SequenceRead;
import org.clothocore.api.data.Strain;
import org.clothocore.api.data.Vector;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.util.basic.ObjBasePopup;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author J. Christopher Anderson
 */
public class spreaditSequencing extends javax.swing.JFrame implements ObjBaseObserver {

    /** Creates new changeFormat spreaditSequencing */
    public spreaditSequencing(Collection coll) {
        super("Spreadit Sequencing");
        _prefs = Preferences.userNodeForPackage(spreaditSequencing.class);
        _Collection = coll;
        _Collection.isRepresentedBy(this, this);
        new ObjBasePopup(getContentPane(), _Collection);
        _title = new String[] { "Run name", "Sub. Date", "Plasmid", "Sample", "Oligo", "Result"};
        initComponents();
        setSize(600,650);
        setVisible(true);
        ExcelAdapter myAd = new ExcelAdapter(table);
        new Thread() {
            @Override
            public void run() {
                parseSequencing();
            }
        }.start();
    }

    @Override
    public void update(ObjBase obj, RefreshEvent evt) {
        if(obj==null) {
            dispose();
        }
        new Thread() {
            @Override
            public void run() {
                parseSequencing();
            }
        }.start();
    }

    private void parseSequencing() {
        ArrayList<SampleData> listy = (ArrayList<SampleData>) _Collection.getAll(ObjType.SAMPLE_DATA);
        allSequencing.clear();
        listeners.clear();
        for(SampleData sd : listy) {
            if(sd.getDatatype().equals(dataType.SEQUENCE_READ)) {
                allSequencing.add((SequenceRead) sd);
            }
        }
        
        _data = new String [allSequencing.size()][6];

        final DefaultTableModel tableModel = new DefaultTableModel(_data, _title);
        table.setModel(tableModel);

        System.out.println(allSequencing.size());
        for(int i=0; i<allSequencing.size(); i++ ) {
            final SequenceRead currread = allSequencing.get(i);
            if(currread==null) {
                continue;
            }
            final int index = i;
            ObjBaseObserver obr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                    @Override
                        public void run() {
                            String samplename="";
                            String plasmidname="";
                            String oligoname="";
                            String subdate = "";
                            try {
                                HashSet<Sample> allsam = currread.getSamples();
                                Sample asam = allsam.iterator().next();
                                samplename = asam.getName();
                                if(asam.getSampleType().equals(sampleType.PLASMID_SAMPLE)) {
                                    plasmidname = ((PlasmidSample) asam).getPlasmid().getName();
                                }
                            } catch(Exception e) {
                            }
                            try {
                                oligoname = currread.getOligo().getName();
                            } catch(Exception e) {
                            }
                            try {
                                _sdf.format(currread.getDateSubmitted());
                            } catch(Exception e) {
                            }

                            table.setValueAt(currread.getName(), index, 0);
                            table.setValueAt(subdate, index, 1);
                            table.setValueAt(samplename, index, 2);
                            table.setValueAt(plasmidname, index, 3);
                            table.setValueAt(oligoname, index, 4);
                        }
                    });
                }
            };

            ObjBaseObserver wikiobr = new ObjBaseObserver() {
                @Override
                public void update(ObjBase obj, RefreshEvent evt) {
                    if(obj==null) {
                        return;
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                    @Override
                        public void run() {
                            String wiki = "";
                            try {
                                wiki = currread.getResultWiki().getAsWikiText();
                            } catch(Exception e) {
                            }
                            table.setValueAt(wiki, index, 5);
                        }
                    });
                }
            };

            //Install and fire the listeners
            currread.isObservedBy(obr);
            currread.getResultWiki().isObservedBy(wikiobr);
            listeners.add(obr);
            listeners.add(wikiobr);
            obr.update(currread, null);
            wikiobr.update(currread.getResultWiki(), null);
        }

        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                table.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        SequenceRead changedVector = allSequencing.get(e.getFirstRow());
                        processInputChange(changedVector, e.getFirstRow(), e.getColumn(), (String) tableModel.getValueAt(e.getFirstRow(), e.getColumn()));
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
                            final SequenceRead read = allSequencing.get(index);
                            ObjBasePopup obp = new ObjBasePopup(table, read, e.getPoint());
                            obp.addMenuItem("Remove from Collection", new java.awt.event.ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    _Collection.removeItem(read);
                                }
                            });
                        }
                     }
                });
            }
        });
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
                    newcoll.changeDescription("Temporary collection of sequencing");
                    new spreaditSequencing(newcoll);
                }
            });
            fileMenu.add(newWindow);

            JMenuItem addBasic = new JMenuItem();
            addBasic.setText("Add read");
            addBasic.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
            addBasic.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    readEnterer dialog = new readEnterer(myself, true);
                    Vector aVector = dialog.getVector();
                    if(aVector!=null) {
                        _Collection.addObject(aVector);
                        System.out.println("read added");
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
                        SequenceRead selectVector = allSequencing.get(i);
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
                new spreaditSequencing(outcoll);
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
                new spreaditSequencing(outcoll);
            } catch( Exception e) {
            }
        }

        private void processInputChange(SequenceRead read, int row, int column, String val) {
            try {
                switch(column) {
                    case 0:
                        if(read.getName().equals(val)) {
                            return;
                        }
                        read.changeName(val);
                        table.setValueAt(read.getName(), row, column);
                        return;
                    case 1:
                        String currdate = _sdf.format(read.getDateSubmitted());
                        if(currdate.equals(val)) {
                            return;
                        }
                        Date adate = _sdf.parse(val);
                        read.changeDateSubmitted(adate);
                        currdate = _sdf.format(read.getDateSubmitted());
                        table.setValueAt(currdate, row, column);
                        return;
                    case 2:
                        //See if a sample is already put in (if so, cancel this)
                        try {
                            HashSet<Sample> allsam = read.getSamples();
                            Sample asam = allsam.iterator().next();
                            String plasmidname = ((PlasmidSample) asam).getPlasmid().getName();
                            table.setValueAt(plasmidname, row, column);
                            return;
                        } catch(Exception e) {
                        }
                        
                        //See if they put in any text
                        if(val.equals("")) {
                            return;
                        }

                        //Try to identify the plasmid
                        Plasmid newplas = Plasmid.retrieveByName(val);
                        if(newplas==null) {
                            try {
                                String[] partandvect = val.split("-");
                                Part apart = Part.retrieveByName(partandvect[1]);
                                Vector avect = Vector.retrieveByName(partandvect[0]);
                                newplas = Plasmid.generatePlasmid(apart, avect, Collector.getCurrentUser(), apart.getFormat());
                                newplas.setTransient();
                            } catch(Exception e) {
                            }
                        }
                        if(newplas==null) {
                            table.setValueAt("", row, column);
                            return;
                        }

                        //Choose the strain
                        Strain astrain = null;
                        ArrayList<ObjLink> allstrain = Collector.getAllLinksOf(ObjType.STRAIN);
                        Object[] allNames = allstrain.toArray();
                        ObjLink link = (ObjLink) JOptionPane.showInputDialog(null, "What strain is this from?", "Choose Strain",
                            JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
                        if(link!=null) {
                            astrain = Collector.getStrain(link.uuid);
                        }
                        if(astrain==null) {
                            table.setValueAt("", row, column);
                            return;
                        }

                        //Create the plasmidSample
                        PlasmidSample plasam = PlasmidSample.generatePlasmidSample(newplas, astrain, new Container(), 50.0, Collector.getCurrentUser());
                        if(plasam==null) {
                            table.setValueAt("", row, column);
                            return;
                        }

                        //Add the read to the plasmid sample
                        plasam.addObject(read);
                        return;
                    case 3:
                        //See if a sample is already put in (otherwise cancel)
                        Sample asam = null;
                        try {
                            HashSet<Sample> allsam = read.getSamples();
                            asam = allsam.iterator().next();
                            asam.getName();
                        } catch(Exception e) {
                            table.setValueAt("", row, column);
                            return;
                        }

                        //See if they put in any text
                        if(val.equals("")) {
                            return;
                        }

                        //Change the naem of the sample, usually "clone 1"
                        asam.changeName("val");

                        return;
                    case 4:
                        //If unchanged, return
                        if(read.getOligo()!=null) {
                            if(read.getOligo().getName().equals(val)) {
                                return;
                            }
                        }

                        //See if put in a valid oligo, if so change the oligo
                        Oligo newoligo = Oligo.retrieveByName(val);
                        if(newoligo!=null) {
                            read.changeOligo(newoligo);
                        }
                        return;
                    case 5:
                        //If unchanged, return
                        if(read.getResultWiki().getAsWikiText().equals(val)) {
                            table.setValueAt("", row, column);
                            return;
                        }
                        read.getResultWiki().changeTextAsWiki(val);
                        return;
                }
            } catch(Exception e) {
                table.setValueAt("-error-", row, column);
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
    private ArrayList<SequenceRead> allSequencing = new ArrayList<SequenceRead>();
    static Preferences _prefs;   //prefs
    private static SimpleDateFormat _sdf = new SimpleDateFormat( "dd-MMM-yyyy", Locale.US );
    private HashSet<ObjBaseObserver> listeners = new HashSet<ObjBaseObserver>();
}
