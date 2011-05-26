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

import org.clothocore.api.data.ObjBase;

/**
 *
 * @author J.Christopher Anderson
 * @author Douglas Densmore
 */
public interface ObjBaseObserver  {
    /**
     * Called from the ObjBase whenever its data changes.  This method will
     * be called on the Event Dispatch Thread (EDT).  If you implement anything
     * heavy in this method, you should use SwingWorker or a new Thread or it will
     * tie up all of Clotho's GUIs.
     * @param obj the ObjBase calling the method, will be null if the object was "deleted"
     * @param evt currently null, later will incorporate details of what was updated
     */
    void update(ObjBase obj, RefreshEvent evt);
}
