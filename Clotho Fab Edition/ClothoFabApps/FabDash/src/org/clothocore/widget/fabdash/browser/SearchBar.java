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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.ObjBase;
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

        JScrollPane scroller = new JScrollPane();
        _lister = new JList();
        scroller.setViewportView(_lister);
        add(scroller, BorderLayout.CENTER);

        _lister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    try {
                        ObjBase obj = (ObjBase) _lister.getSelectedValue();
                        obj.launchDefaultViewer();
                    } catch(Exception ex) {
                    }
                }
            }
        });
    }

    void setResults(final Collection outcoll) {
        _lister.setModel(new ListModel() {

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
        repaint();
        _obp = new ObjBasePopup(_lister, outcoll);
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    JList _lister;
    ObjBasePopup _obp;
}
