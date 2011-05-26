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

import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import org.clothocore.api.data.ObjBase;
/**
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public class ObjBaseDropTargetListener extends DropTargetAdapter {

    public ObjBaseDropTargetListener(Component guiComponent, ObjBase target) {
        _myObject = target;
        dropTarget = new DropTarget(guiComponent, DnDConstants.ACTION_COPY,
                this, true, null);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    @Override
    public void drop(DropTargetDropEvent event) {
        try {

            Transferable tr = event.getTransferable();
            ObjBase obj = (ObjBase) tr.getTransferData(TransferableObject.objBaseFlavor);

            if (event.isDataFlavorSupported(TransferableObject.objBaseFlavor)) {

                event.acceptDrop(DnDConstants.ACTION_COPY);
                _myObject.addObject(obj);

                event.dropComplete(true);
                return;
            }
            event.rejectDrop();
        } catch (Exception e) {
            System.out.println("I'm rejecting your drop for some reason");
            e.printStackTrace();
            event.rejectDrop();
        }
    }
    
    ///////////////////////////////////////////////////////////////////
    ////                         private variables                ////

    private DropTarget dropTarget;
    private ObjBaseObserver receivingpanel;
    private final ObjBase _myObject;

    }