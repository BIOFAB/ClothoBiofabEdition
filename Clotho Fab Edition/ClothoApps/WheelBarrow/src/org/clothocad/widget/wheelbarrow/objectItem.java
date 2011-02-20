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

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;
import org.clothocore.util.basic.ImageSource;

/**
 *
 * @author J. Christopher Anderson
 */
public class objectItem extends TransparentButton {

    public objectItem(ObjBase o) {
        super(chooseIcon(o));
        _myObject = o;
        this.setEnterAlpha(0.8f);
        this.setExitAlpha(1.0f);
        this.setToolTipText(o.getName());
        ObjBasePopup app = new ObjBasePopup(this, _myObject);
        _ref = new refresher();
        _myObject.isRepresentedBy(_ref, this);
    }

    private static ImageIcon chooseIcon(ObjBase o) {
        return iconSet.get(o.getType());
    }

    private class refresher implements ObjBaseObserver {
        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            if(obj==null) {
                setVisible(false);
            }
            objectItem.this.repaint();
        }
    }

/*-----------------
     variables
 -----------------*/
    ObjBase _myObject;
    private static final Map<ObjType, ImageIcon> iconSet = ImageSource.getObjectIconSet(40);
    private refresher _ref;
}
