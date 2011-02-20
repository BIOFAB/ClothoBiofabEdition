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

package org.clothocad.widget.wheelbarrow;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class garbageBin extends TransparentButton implements ObjBaseObserver{

    public garbageBin(Collection wheelColl) {
        super(ImageUtilities.loadImageIcon("org/clothocad/widget/wheelbarrow/images/garbagebin.png", false));
        this.setEnterAlpha(0.8f);
        this.setExitAlpha(1.0f);
        _wheelColl = wheelColl;
        ObjBasePopup app = new ObjBasePopup(this, _myCollection);
        _myCollection.isRepresentedBy(this, this);
    }

    @Override
    public void update(ObjBase obj, RefreshEvent evt) {
        ArrayList<ObjBase> all = _myCollection.getAll();
        System.out.println(all.size());
        for(ObjBase o: all) {
            System.out.println(o.getName());
            _wheelColl.removeItem(o);
        }
        _myCollection.removeAll();
        repaint();
    }
/*-----------------
     variables
 -----------------*/
    private Collection _myCollection = new Collection();
    private Collection _wheelColl;
}
