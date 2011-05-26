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

package org.clothocore.util.def;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.TransparentButton;

/**
 *
 * @author J. Christopher Anderson
 */
public class DefaultViewer extends JFrame {
    public DefaultViewer(ObjBase obj) {
        _obj = obj;
        if(_obj!=null) {
            initComponents();
        }
    }

    private void initComponents() {
        this.setTitle(_obj.getName() + " - " + _obj.getUUID());
        ImageIcon img = ImageSource.getObjectImage(_obj.getType(),100);
        objButton tb = new objButton(img);
        getContentPane().setBackground(Color.black);
        getContentPane().add(tb);
        _obp = new ObjBasePopup(tb, _obj);
        _obj.isRepresentedBy(tb, tb);
        setResizable(false);
        pack();
        setVisible(true);
    }

    private class objButton extends TransparentButton implements ObjBaseObserver {
        public objButton(ImageIcon img) {
            super(img);
            setEnterAlpha(0.8f);
            setExitAlpha(1.0f);
            setBorder(null);
        }

        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            if(obj==null) {
                setExitAlpha(0.1f);
                setEnterAlpha(0.1f);
                _obp.disable();
            } else {
                setEnterAlpha(0.8f);
                setExitAlpha(1.0f);
                _obp.enable();
            }
        }

    }

/*-----------------
     variables
 -----------------*/
    private ObjBase _obj;
    private ObjBasePopup _obp;
}
