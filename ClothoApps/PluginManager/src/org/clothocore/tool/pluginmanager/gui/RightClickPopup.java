/*
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

package org.clothocore.tool.pluginmanager.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.clothocore.api.core.Collator;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.data.ObjType;



public class RightClickPopup {

     public RightClickPopup(int x, int y, String type) {
         myType = ObjType.valueOf(type.toUpperCase());
         
         popupMenu = new JPopupMenu();
         ItemHandler handler = new ItemHandler();

         //Add a "launch" label:
         JLabel launchlabel = new JLabel();
         launchlabel.setText("Set Preferred Viewer:");
         launchlabel.setFont(new Font("Arial", Font.ITALIC, 10));
         popupMenu.add( launchlabel );

         //Add a separator
         JSeparator jsep = new JSeparator();
         popupMenu.add( jsep );


         if(_connected) {
             ArrayList<ViewerWrapper> wraps = Collator.getAvailableViewers(myType);
             Object[] _viewers = Collator.getAvailableViewers(myType).toArray();
            // construct each menu item and enable click
             for ( int i = 0; i < _viewers.length; i++ )
                 {
                 JMenuItem amenu = new JMenuItem( _viewers[i].toString() );
                 menuOptions.add(amenu);
                 popupMenu.add( amenu );
                 amenu.addActionListener( handler );
                 viewerHash.put(_viewers[i].toString(), (ViewerWrapper) _viewers[i]);
             }
         }

         popupMenu.show( null, x, y );
     }

     public void remove() {
         popupMenu.setVisible(false);
     }

     private class ItemHandler implements ActionListener {
        @Override
         public void actionPerformed( ActionEvent e ) {
             // determine which menu item was selected
            JMenuItem selectitem = (JMenuItem) e.getSource();
             String selected = selectitem.getText();

             //Set the preferred viewer
             if(viewerHash.containsKey(selected)) {
                     ViewerWrapper vw = viewerHash.get(selected);
                     Collator.putPreferredViewer(myType, vw);
                     System.out.println("I set preferred " + myType + " " + vw.getDisplayName());
                 }
             popupMenu.setVisible(false);
             return;
            }
     }

/*-----------------
     variables
 -----------------*/
     private ArrayList<JMenuItem> menuOptions = new ArrayList<JMenuItem>();
     private Hashtable<String, ViewerWrapper> viewerHash = new Hashtable<String, ViewerWrapper>();
     private ObjType myType;
     private JPopupMenu popupMenu = null;
     private boolean _connected = true;
}