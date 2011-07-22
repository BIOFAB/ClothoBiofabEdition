/*
 * 
Copyright (c) 2010 The Regents of the University of California.
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

package org.clothocore.widget.fabdash.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.util.basic.ObjBasePopup;

/**
 *
 * @author jcanderson_Home
 */
public class SearchBar extends JPanel {
    public SearchBar() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,200));
        add(new SuperSearch(this), BorderLayout.NORTH);

        mainPane = new JTabbedPane();               // create the tabbed pane
        add(mainPane, BorderLayout.CENTER);         // add it to the center of our JPanel
        _lister = new JList();                      // create the JList to view the results

       _lister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getClickCount()==2) {
                    try {
                        System.out.println(mainPane.getComponent(0));
                        ObjBase obj = (ObjBase) _lister.getSelectedValue();
                        obj.launchDefaultViewer();
                    } catch(Exception ex) {
                    }
                }
            }
        });
    }

    // updates the results of the search, doesn't do any filtering
    public void setResults(final Collection outcoll) {

        mainPane.removeAll();                       // first remove all panes
        JScrollPane scroller = new JScrollPane();   // create a scrollpane
        scroller.setViewportView(_lister);          // set the lister to the scroller

        if (!outcoll.getAll().isEmpty()){
            // if the collection has some objects

            _lister.setModel(new ListModel() {
                // create a list model and populate

                @Override
                public int getSize() {
                    return outcoll.getAll().size();
                }

                @Override
                public Object getElementAt(int index) {
                    return outcoll.getAll().get(index);
                }

                @Override
                public void addListDataListener(ListDataListener l) {
                }

                @Override
                public void removeListDataListener(ListDataListener l) {
                }

        });

        mainPane.addTab("All",scroller);             // create the All tab
    }

        else{
            // if the collection is empty

            DefaultListModel listModel = new DefaultListModel();    // create a default list model
            listModel.addElement("Returned No Results");            // add no results string
            _lister.setModel(listModel);                            // use the model
            mainPane.addTab("No Results",scroller);                  // create a tab for the list

         }

        repaint();
        _obp = new ObjBasePopup(_lister, outcoll);
    }


    // takes ObjType and filters the search using that type
    public void setResults(final Collection outcoll, final ObjType type, final JList list) {
        list.setModel(new ListModel() {

            @Override
            public int getSize() {
                return outcoll.getAll(type).size();//.size();
            }

            @Override
            public Object getElementAt(int index) {
                return outcoll.getAll(type).get(index);
            }

            @Override
            public void addListDataListener(ListDataListener l) {
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
            }
        });
        repaint();
        _obp = new ObjBasePopup(list, outcoll);
    }

    public void createTabs(final Collection outcoll){
        // takes in a collection of objects
        // determines what types of objects are in the collection
        // generates tabs with lists of each objects in the JPanel

       for(ObjType myObj : ObjType.values()) { // iterate through all ObjTypes

            ArrayList result = outcoll.getAll(myObj);
            if (!result.isEmpty()){

                // if some objects exist of that type
                JScrollPane dynScroller = new JScrollPane();    // create new scroller
                JList dynLister = new JList();                  // create new jList
                dynScroller.setViewportView(dynLister);         // put the list in the scroller
                setResults(outcoll,myObj,dynLister);          // set the results for lister
                mainPane.addTab(myObj.toString(), dynScroller);    // add a pane for the user
//                _obp = new ObjBasePopup(dynLister,(ObjBase) result.get(0));

            }

        }

    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    JTabbedPane mainPane;   // main pane for the searchBar
    JList _lister;          // JList for entire returned collection
    ObjBasePopup _obp;
}
