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
 * BrowseNotes.java
 *
 * Created on Nov 20, 2010, 10:12:05 AM
 */

package org.clothocad.tool.grapevine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Lab;
import org.clothocore.api.data.Note;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;

/**
 *
 * @author jcanderson
 */
public class BrowseNotes extends javax.swing.JDialog {

    /** Creates new form BrowseNotes */
    public BrowseNotes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        populateData();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        allNoteList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        root = new DefaultMutableTreeNode("Labs");
        collectionTree = new javax.swing.JTree(root);
        cancelButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        allNoteList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {};
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(allNoteList);

        jScrollPane2.setViewportView(collectionTree);
        collectionTree.setVisible(false);
        collectionTree.setExpandsSelectedPaths(true);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        openButton.setText("Quick Add");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Collection acoll = Collector.getCurrentUser().getHerCollection();
                    Note anote = new Note("New Note", Collector.getCurrentUser(), null);
                    acoll.addObject(anote);
                    new NoteEditor(anote);
                    setVisible(false);
                } catch(Exception err) {

                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(openButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(openButton))
                .addContainerGap())
        );

        pack();
    }

    private void populateData() {
        new SwingWorker() {
            ArrayList<ObjLink> links;
            @Override
            protected Object doInBackground() throws Exception {
                links = Collector.getAllLinksOf(ObjType.NOTE);
                return null;
            }

            @Override
            public void done() {
                allNoteList.setModel(new javax.swing.AbstractListModel() {
                    @Override
                    public int getSize() { return links.size(); }
                    @Override
                    public Object getElementAt(int i) { return links.get(i); }
                });
                allNoteList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount()==2) {
                            System.out.println("double clicked list");
                            try {
                                ObjLink link = (ObjLink) allNoteList.getSelectedValue();
                                Note anote = Collector.getNote(link.uuid);
                                new NoteEditor(anote);
                            } catch(Exception err) {
                                err.printStackTrace();
                            }
                        }
                    }
                });
            }
        }.execute();

        new SwingWorker() {
            ArrayList<ObjLink> labs;
            @Override
            protected Object doInBackground() throws Exception {
                labs = Collector.getAllLinksOf(ObjType.LAB);
                return null;
            }

            @Override
            public void done() {
                for(ObjLink alab : labs) {
                    LabNode childLab = new LabNode(alab);
                    root.add(childLab);
                }
                collectionTree.expandRow(0);
                collectionTree.addTreeExpansionListener(new TreeExpansionListener() {

                    @Override
                    public void treeExpanded(TreeExpansionEvent event) {
                        try {
                            ObjBaseNode obn = (ObjBaseNode) event.getPath().getLastPathComponent();
                            obn.expand();
                        } catch(Exception e) {
                        }
                    }

                    @Override
                    public void treeCollapsed(TreeExpansionEvent event) {
                    }
                });
                collectionTree.addMouseListener(new TreeListener());
                collectionTree.setVisible(true);
            }
        }.execute();
    }

    abstract class ObjBaseNode extends DefaultMutableTreeNode {
        public ObjBaseNode(String name) {
            super(name);
        }

        public TreePath getTreePath() {
            TreeNode[] ptr = getPath();
            TreePath path = new TreePath(ptr);
            return path;
        }

        abstract void expand();
        //VARIABLES//
        boolean isExpanded = false;
    }

    class LabNode extends ObjBaseNode {
        public LabNode(ObjLink alab) {
            super(alab.name);
            dummynode = new DefaultMutableTreeNode("Loading...");
            DefaultTreeModel model = (DefaultTreeModel)collectionTree.getModel();
            MutableTreeNode node = (MutableTreeNode) LabNode.this;
            model.insertNodeInto(dummynode, node, 0);
            mylink = alab;
        }

        @Override
        public void expand() {
            if(isExpanded) {
                return;
            }
            new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    ClothoConnection conn = Collector.getDefaultConnection();
                    ClothoQuery mainquery = conn.createQuery( ObjType.PERSON );
                    ClothoQuery seqquery = mainquery.createAssociationQuery( Person.Fields.LAB );
                    seqquery.eq( Lab.Fields.NAME, mylink.name );
                    peoples = mainquery.getResults();
                    for(ObjBase obj: peoples) {
                        System.out.println(obj.getName());
                    }
                    return null;
                }

                @Override
                public void done() {
                    DefaultTreeModel model = (DefaultTreeModel)collectionTree.getModel();
                    MutableTreeNode node = (MutableTreeNode) LabNode.this;
                    for(ObjBase obj : peoples) {
                        Person aperson = (Person) obj;
                        CollectionNode pnode = new CollectionNode(aperson);
                        model.insertNodeInto(pnode, node, node.getChildCount());
                    }
                    model.removeNodeFromParent(dummynode);
                    collectionTree.expandPath(LabNode.this.getTreePath());
                    isExpanded = true;
                }
            }.execute();
        }

        //VARIABLES//
        ObjLink mylink;
        DefaultMutableTreeNode dummynode;
        List<ObjBase> peoples;
    }

    class NoteNode extends ObjBaseNode {
        /**
         * Constructor for an existing Note node
         * @param anote
         */
        public NoteNode(Note anote) {
            super(anote.getName());
            mynote = anote;
        }

        /**
         * Constructor for if it's a new Note node
         * @param string
         * @param mycoll
         */
        public NoteNode(String string, Collection mycoll) {
            super(string);
            newline = true;
            tocollection= mycoll;
        }

        @Override
        public void expand() {
        }

        public void launchViewer() {
            if(newline) {
                Note newnote = new Note("New Note", Collector.getCurrentUser(), null);
                tocollection.addObject(newnote);
                new NoteEditor(newnote);
            } else {
                new NoteEditor(mynote);
            }
        }

        //VARIABLES//
        Note mynote;
        Collection tocollection;
        boolean newline = false;
    }

    class CollectionNode extends ObjBaseNode {
        public CollectionNode(Person aperson) {
            super(aperson.getHerCollection().getName());
            mycoll = aperson.getHerCollection();
            newNoteNode = new NoteNode("Add new Note here", mycoll);
            add(newNoteNode);
        }

        public CollectionNode(Collection acoll) {
            super(acoll.getName());
            mycoll = acoll;
            newNoteNode = new NoteNode("Add new Note here", mycoll);
            add(newNoteNode);
        }

        @Override
        public void expand() {
            if(isExpanded) {
                return;
            }
            new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    allcolls = (ArrayList<Collection>) mycoll.getAll(ObjType.COLLECTION);
                    allnotes = (ArrayList<Note>) mycoll.getAll(ObjType.NOTE);
                    return null;
                }

                @Override
                public void done() {
                    DefaultTreeModel model = (DefaultTreeModel)collectionTree.getModel();
                    MutableTreeNode node = (MutableTreeNode) CollectionNode.this;
                    for(Collection acoll : allcolls) {
                        CollectionNode pnode = new CollectionNode(acoll);
                        model.insertNodeInto(pnode, node, node.getChildCount());
                    }
                    for(Note anote : allnotes) {
                        NoteNode pnode = new NoteNode(anote);
                        model.insertNodeInto(pnode, node, node.getChildCount());
                    }
                    validate();
                    repaint();
                    isExpanded = true;
                }
            }.execute();
        }

        //VARIABLES//
        Collection mycoll;
        NoteNode newNoteNode;
        ArrayList<Collection> allcolls;
        ArrayList<Note> allnotes;
    }

    class TreeListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()==2) {
                try {
                    TreePath path = collectionTree.getSelectionPath();
                    TreeNode node = (TreeNode) path.getLastPathComponent();
                    NoteNode cnode = (NoteNode) node;
                    cnode.launchViewer();
                } catch(Exception err) {
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

/*-----------------
     variables
 -----------------*/
    private javax.swing.JButton cancelButton;
    private javax.swing.JList allNoteList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree collectionTree;
    private javax.swing.JButton openButton;
    private DefaultMutableTreeNode root;
}
