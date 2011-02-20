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

package org.clothocore.widget.fabdash;

import java.util.ArrayList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.ObjBase;

/**
 *
 * @author jcanderson_Home
 */
public class CollectionBrowser implements TreeModel {

        public static CollectionBrowser generate(Collection acoll) {
            return new CollectionBrowser(acoll);
        }

        private CollectionBrowser(Collection acoll) {
            personalCollection = acoll;
        }

        @Override
        public Object getRoot() {
            return personalCollection;
        }

        @Override
        public Object getChild(Object parent, int index) {
            try {
                Collection c = (Collection) parent;
                ArrayList<ObjBase> listy = c.getAll();
                return listy.get(index);
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public int getChildCount(Object parent) {
            try {
                Collection c = (Collection) parent;
                ArrayList<ObjBase> listy = c.getAll();
                return listy.size();
            } catch(Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public boolean isLeaf(Object node) {
            try {
                Collection acoll = (Collection) node;
                return false;
            } catch(Exception e) {
                return true;
            }
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {

        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            try {
                Collection c = (Collection) parent;
                ArrayList<ObjBase> listy = c.getAll();
                return listy.indexOf(child);
            } catch(Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {

        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {

        }



    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
        Collection personalCollection;
}
