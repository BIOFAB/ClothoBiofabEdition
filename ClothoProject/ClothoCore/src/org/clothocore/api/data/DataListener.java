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

 package org.clothocore.api.data;

import java.util.EventListener;
import org.clothocore.api.dnd.RefreshEvent;


/**
 * DataListener is used to make GUI fields update themselves whenever an ObjBase item
 * is modified.  To use it, you put the following in your GUI, where the ObjBase item you
 * want to watch is _obj:
        _obj.addDataListener(new DataListener() {
            @Override
            public void objectChanged(DataEvent evt) {
                updateField();
            } });
 * updateField() or whatever else you put in there is called in the GUI when the object
 * is modified, so you put any redraw/repaint/re-retrieve commands in there
 * 
 * @author jcanderson
 */
public interface DataListener extends EventListener {
    void objectChanged(RefreshEvent evt);
}
