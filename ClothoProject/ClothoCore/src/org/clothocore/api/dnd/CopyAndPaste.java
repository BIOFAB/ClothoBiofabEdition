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

package org.clothocore.api.dnd;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Collection;
import org.clothocore.api.core.Collector;

/**
 * To add CopyAndPaste functionality, add:
 *
 *      new CopyAndPaste(theGUIRepresenter, theObject);
 *
 * To only enable copy or paste, include the enum in the statement.
 *
 * To also enable cut operations, you have to tell it where to cut
 * from.  Add a line in addition to the above with:
 *
 *      new CopyAndPaste(theGUIRepresenter, theObject).enableCut(acollection);
 * 
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public class CopyAndPaste {

    public CopyAndPaste(JComponent c, ObjBase o, enable e) {
        _myObject = o;
        _myComponent = c;
        _myEnablement = e;

        switch(_myEnablement) {
            case COPY:
                enableCopy();
                break;
            case PASTE:
                enablePaste();
                break;
            case COPY_AND_PASTE:
                enableCopy();
                enablePaste();
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /**
     *
     * @param c
     * @param o
     */
    public CopyAndPaste(JComponent c, ObjBase o) {
        this(c, o, enable.COPY_AND_PASTE);
    }

    /**
     *
     * @param ContainingCollection
     */
    public void enableCut(final Collection ContainingCollection) {
        _myContainingCollection = ContainingCollection;
        _myComponent.getInputMap().put(KeyStroke.getKeyStroke("control X"), "CutObjectAction");
        _myComponent.getActionMap().put("CutObjectAction",
                new AbstractAction("CutObjectAction") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("control x typed");

                        //Put the object on the clipboard (the "copy" part of cut)
                        Collector.copyToClipboard(_myObject);

                        //Remove the object from the containing collection (the "remove" portion of cut)
                        ContainingCollection.removeItem(_myObject);
                    }
                });
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public variables                 ////

    public static enum enable {COPY, PASTE, COPY_AND_PASTE };

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    /**
     *
     */
    private void enableCopy() {
        _myComponent.getInputMap().put(KeyStroke.getKeyStroke("control C"), "CopyObjectAction");
        _myComponent.getActionMap().put("CopyObjectAction",
            new AbstractAction("CopyObjectAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("control c typed");

                    //Put the object on the clipboard
                    Collector.copyToClipboard(_myObject);
                }});
    }

    /**
     * 
     */
    private void enablePaste() {
        _myComponent.getInputMap().put(KeyStroke.getKeyStroke("control V"), "PasteObjectAction");
        _myComponent.getActionMap().put("PasteObjectAction",
            new AbstractAction("PasteObjectAction") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("control v typed");
                ObjBase pasteObject = Collector.getFromClipboard();
                if(pasteObject!=null) {
                    _myObject.addObject(pasteObject);
                }
            }});
    }



    ///////////////////////////////////////////////////////////////////
    ////                         private variables                ////

    private JComponent _myComponent;
    private ObjBase _myObject;
    private enable _myEnablement;
    private Collection _myContainingCollection;
}
