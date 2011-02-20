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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.clothocore.api.core.Collator;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.dnd.CopyAndPaste;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.GlassButton;
import org.clothocore.util.buttons.TransparentButton;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.openide.util.ImageUtilities;

/**
 *
 * @author jcanderson
 */
public class wheelBarrowGui extends JFrame  {

    public wheelBarrowGui() {
        super("Wheelbarrow");
        setIconImage(ImageSource.getTinyLogo());
        initcomponents( "org/clothocad/widget/wheelbarrow/images/wheelBarrow.png",  "org/clothocad/widget/wheelbarrow/images/minimize.png",  "org/clothocad/widget/wheelbarrow/images/close.png",  "org/clothocad/widget/wheelbarrow/images/obutton.png", "org/clothocad/widget/wheelbarrow/images/lock.png");
        _ref = new refresher();
        _myCollection.isRepresentedBy(_ref, this);
        new CopyAndPaste(this.getRootPane(), _myCollection, CopyAndPaste.enable.PASTE);

        ObjBasePopup obp = new ObjBasePopup(getContentPane(), _myCollection);

        //Set the screen position from last time
        _prefs = Preferences.userNodeForPackage( wheelBarrowGui.class );
        int posX = _prefs.getInt("posX", 0);
        int posY = _prefs.getInt("posY", 0);
        isLocked = !_prefs.getBoolean("isLocked", false);
        lockButtonClicked(null);
        setLocation(posX, posY);
    }

    private class refresher implements ObjBaseObserver {
        @Override
        public void update(ObjBase passed, RefreshEvent evt) {
            for(objectItem oi: _objItemList) {
                getLayeredPane().remove(oi);
            }
            _posIndex= 0;
            _objItemList = new ArrayList<objectItem>();
            ArrayList<ObjBase> all = _myCollection.getAll();

            for(ObjBase obj: all) {
                final objectItem o2 = new objectItem(obj);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        o2.setLocation(getNextAvailablePosition());
                        getLayeredPane().add(o2, 2);
                        repaint();
                    }
                });
                
                _objItemList.add(o2);
            }
        }
    }

    protected void initcomponents(String background_image, String minimize_image, String close_image, String expand_image, String lock_image) {
        //Load the icons
        ImageIcon minimize = ImageUtilities.loadImageIcon(minimize_image, false);
        ImageIcon close = ImageUtilities.loadImageIcon(close_image, false);
        ImageIcon expand = ImageUtilities.loadImageIcon(expand_image, false);
        lock = ImageUtilities.loadImageIcon(lock_image, false);
        bkgImage = ImageUtilities.loadImageIcon(background_image, false);

        int frameWidth = bkgImage.getIconWidth();
        int horizontalSpacing = 2;
        int verticalSpacing = 2;

        //Paint the background below everything white
        getContentPane().setBackground(Color.white);

        //Put in background image
        JLabel bkgLabel = new JLabel(bkgImage);
        bkgLabel.setBounds(0,0,frameWidth, bkgImage.getIconHeight());
        getContentPane().add(bkgLabel);

        int horizontalPosition = frameWidth-horizontalSpacing;

        //Put in a close button
        TransparentButton closeButton = new TransparentButton(close);
        horizontalPosition = horizontalPosition - close.getIconWidth();
        closeButton.setLocation(horizontalPosition,verticalSpacing);
        closeButton.setToolTipText("Close");
        closeButton.setExitAlpha(0.3f);
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            closeButtonClicked(evt);}});
        getLayeredPane().add(closeButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;

        //Put in a minimize button
        TransparentButton minimizeButton = new TransparentButton(minimize);
        horizontalPosition = horizontalPosition - minimize.getIconWidth();
        minimizeButton.setLocation(horizontalPosition,verticalSpacing);
        minimizeButton.setToolTipText("Minimize");
        minimizeButton.setExitAlpha(0.3f);
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            minimizeButtonClicked(evt);}});
        getLayeredPane().add(minimizeButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;

        //Put in a expand button
        TransparentButton expandButton = new TransparentButton(expand);
        horizontalPosition = horizontalPosition - expand.getIconWidth();
        expandButton.setLocation(horizontalPosition,verticalSpacing);
        expandButton.setToolTipText("Expand");
        expandButton.setExitAlpha(0.3f);
        expandButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            expandButtonClicked(evt);}});
        getLayeredPane().add(expandButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;

        //Put in a lock button
        lockButton = new TransparentButton(lock);
        horizontalPosition = horizontalPosition - lock.getIconWidth();
        lockButton.setLocation(horizontalPosition,verticalSpacing);
        lockButton.setToolTipText("Lock");
        lockButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            lockButtonClicked(evt);}});
        getLayeredPane().add(lockButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;

        //Put in a draggerButton button
        draggerButton = new GlassButton("");
        draggerButton.setBounds(0,0,horizontalPosition, verticalSpacing + expand.getIconHeight());
        draggerButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                draggerButtonDragged(evt);}
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                draggerButtonMoved(evt);}});
        getLayeredPane().add(draggerButton, 0);

        //Put in a garbage bin
        garbageBin gb = new garbageBin(_myCollection);
        gb.setLocation(150,135);
        getLayeredPane().add(gb, 2);

        JMenuItem listener = new JMenuItem();
        listener.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        listener.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed();
            }
        });
        getRootPane().add(listener);

        listener = new JMenuItem();
        listener.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        listener.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed();
            }
        });
        getRootPane().add(listener);

        //reset focus whenever something changes it
        getRootPane().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getRootPane().requestFocusInWindow();
            }
        });

        //Size it
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(bkgImage.getIconWidth(), bkgImage.getIconHeight());

        pack();
        setVisible(true);
    }

    private void helpActionPerformed() {
        String url = Collator.helpURLBase + "/Wheelbarrow";
        BareBonesBrowserLaunch.openURL(url);
    }

    private Point getNextAvailablePosition() {
        return _locations[_posIndex++];
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(bkgImage.getImage(), 0, 0, null);
    }

    protected void draggerButtonDragged(java.awt.event.MouseEvent evt) {
        Point aPoint = evt.getPoint();
        int x = getX() + aPoint.x - mousePointer.x;
        int y = getY() + aPoint.y - mousePointer.y;
        setLocation(x, y);
        Graphics graphics = getGraphics();
        paint(graphics);
        _prefs.putInt("posX", x);
        _prefs.putInt("posY", y);
    }


    protected void draggerButtonMoved(java.awt.event.MouseEvent evt) {
        mousePointer = evt.getPoint();
    }

    protected void lockButtonClicked(java.awt.event.MouseEvent evt) {
        //Put the button allowing for the window to be dragged around
        draggerButton.setVisible(isLocked);

        //Toggle the icon
        if(isLocked) {
            lockButton.setExitAlpha(0.3f);
            lockButton.setToolTipText("Lock");
        } else {
            lockButton.setExitAlpha(0.1f);
            lockButton.setToolTipText("Unlock");
        }

        //Toggle whether the winodw is locked on top or not
        setAlwaysOnTop(!isLocked);
        isLocked = !isLocked;
        _prefs.putBoolean("isLocked", isLocked);
    }

    protected void expandButtonClicked(java.awt.event.MouseEvent evt) {
        //THIS IS RESERVED FOR MAKING THE DASHBOARD LARGER OR SOMETHING LIKE THAT
    }

    protected void minimizeButtonClicked(java.awt.event.MouseEvent evt) {
        setState(JFrame.ICONIFIED);
    }

    protected void closeButtonClicked(java.awt.event.MouseEvent evt) {
        dispose();
    }

    public static void main(String[] args) {
        new wheelBarrowGui();
    }



/*-----------------
     variables
 -----------------*/

private Point mousePointer;
private Collection _myCollection = new Collection();
private ArrayList<objectItem> _objItemList = new ArrayList<objectItem>();
private static  ImageIcon bkgImage;
private static  ImageIcon unlock;
private static  ImageIcon lock;
private GlassButton draggerButton;
private TransparentButton lockButton;
private boolean isLocked=false;

private static Point[] _locations = new Point[] {
    new Point(140,72),
    new Point(80, 60),
    new Point(112,70),
    new Point(176,73),
    new Point(140, 40),
    new Point(176,83),
    new Point(90,44),
    new Point(206,73),
    new Point(203,47),
    new Point(115,42),
    new Point(177,29),
    new Point(106,21),
    new Point(142,16),
    new Point(52,52),
    new Point(71,26),
    new Point(80,2),
    new Point(210,15),
    new Point(14,133),
    new Point(54,122),
    new Point(11,97),
    new Point(49,170),
    new Point(137,171),
    };
private int _posIndex=0;
private static Preferences _prefs;
private refresher _ref;
}
