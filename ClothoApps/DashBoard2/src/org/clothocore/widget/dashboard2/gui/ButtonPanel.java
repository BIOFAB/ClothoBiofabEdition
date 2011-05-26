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

package org.clothocore.widget.dashboard2.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.iharder.dnd.FileDrop;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.clothocore.util.buttons.GlassButton;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class ButtonPanel extends JPanel {

    public ButtonPanel(Application app) {
        _app = app;

        //Get the number of panels
        calculateNumPanels();

        initcomponents();
        connectListen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setConnected();
            }
        };
        Collector.logConnectionListener(connectListen);
    }

    private void initcomponents() {
        setLayout(null);

        JMenuItem listener = new JMenuItem();
        listener.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        listener.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(null);
            }
        });
        _app.getRootPane().add(listener);

        listener = new JMenuItem();
        listener.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        listener.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Collector.selectCurrentUser();
            }
        });
        _app.getRootPane().add(listener);

        listener = new JMenuItem();
        listener.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        listener.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed();
            }
        });
        _app.getRootPane().add(listener);

        listener = new JMenuItem();
        listener.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        listener.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed();
            }
        });
        _app.getRootPane().add(listener);

        //Add shutdown listener
        _app.addWindowListener( new WindowAdapter() {

            @Override
            public void windowClosed( WindowEvent e ) {
                Collector.shutDown();
            }
        } );
        
        //Load left and right arrows
        ImageIcon leftArrow = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/leftArrow.png", false );
        ImageIcon rightArrow = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/rightArrow.png", false );

        //Load the buttons
        lock = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/lock.png", false );
        unlock = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/unlock.png", false );
        ImageIcon minimize = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/minimize.png", false );
        ImageIcon close = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/close.png", false );
        ImageIcon expand = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/Obutton.png", false );
        popUpInfo = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/infoPop.png", false );

        //Load the loading button
        ImageIcon loadingImg = new ImageIcon("plugins/Widgets/dashboard2/images/loading.gif");

        //Load the blinking green connected button
        connected = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/dot.gif", false );
        disconnected = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/reddot.png", false );

        //Put in a draggerHeader button
        draggerHeader = new GlassButton("");
        draggerHeader.setBounds(0,0,115, 13);
        draggerHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                _app.draggerHeaderDragged(evt);
            }
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                _app.draggerHeaderMoved(evt);
            }
        });
        add(draggerHeader);
        draggerHeader.setVisible(false);

        //Put in a lock/unlock button
        lockButton = new TransparentButton(unlock);
        lockButton.setLocation(114,1);
        lockButton.setToolTipText("Unlock");
        lockButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            unlockButtonClicked(evt);}});
        add(lockButton);

        //Put in a expand button
        TransparentButton expandButton = new TransparentButton(expand);
        expandButton.setLocation(139,1);
        expandButton.setToolTipText("Enlarge/Shrink Buttons");
        expandButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            expandButtonClicked(evt);}});
        add(expandButton);

        //Put in a minimize button
        TransparentButton minimizeButton = new TransparentButton(minimize);
        minimizeButton.setLocation(162,1);
        minimizeButton.setToolTipText("Minimize");
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            minimizeButtonClicked(evt);}});
        add(minimizeButton);

        //Put in a close button
        TransparentButton closeButton = new TransparentButton(close);
        closeButton.setLocation(185,1);
        closeButton.setToolTipText("Close");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            closeButtonClicked(evt);}});
        add(closeButton);

        //Put in a clotho button
        TransparentButton clothoButton = new TransparentButton(ImageSource.getClothoLogoIcon(38, true));
        clothoButton.setExitAlpha(0.28f);
        clothoButton.setEnterAlpha(0.4f);
        clothoButton.setLocation(13,8);
        clothoButton.setToolTipText("Connect to database");
        clothoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            connectActionPerformed(evt);}});
        add(clothoButton);

        //Put in a manage button
        GlassButton manageButton = new GlassButton("");
        manageButton.setBounds(118,21,90, 18);
        manageButton.setToolTipText("Manage plugins");
        manageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            manageButtonClicked(evt);}});
        add(manageButton);

        //Put in a left arrow button
        leftButton = new TransparentButton(leftArrow);
        leftButton.setLocation(22,255);
        leftButton.setExitAlpha(0.7f);
        leftButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            leftButtonClicked(evt);}});
        add(leftButton);

        //Put in a right arrow button
        rightButton = new TransparentButton(rightArrow);
        rightButton.setLocation(150,255);
        rightButton.setExitAlpha(0.7f);
        rightButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rightButtonClicked(evt);}});
        add(rightButton);
        setLeftAndRightButtons();

        //Listen for leftward and rightward swipes
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            private long[] times = new long[8];
            private Point startPos;
            private long lastTimeSwiped = System.currentTimeMillis();

            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                long startTime = System.currentTimeMillis();
                if(times[0] == 0) {
                    times[0] = startTime;
                    startPos = evt.getPoint();
                } else if(times[1] == 0) {
                    times[1] = startTime;
                } else if(times[2] == 0) {
                    times[2] = startTime;
                } else if(times[3] == 0) {
                    times[3] = startTime;
                } else if(times[4] == 0) {
                    times[4] = startTime;
                 }else if(times[5] == 0) {
                    times[5] = startTime;
                } else if(times[6] == 0) {
                    times[6] = startTime;
                } else if(times[7] == 0) {
                    times[7] = startTime;
                } else {
                    //If it's been a long time since a contiguous dragging event, quit
                    //Sees if time elapsed during the 8 swipes is too slow
                    if(startTime - times[0] > 800) {
                    }
                    //Otherwise, see if it was a leftward or rightward swipe
                    else {
                        Point endPos = evt.getPoint();
                        int xdeflection = endPos.x - startPos.x;
                        int ydeflection = endPos.y - startPos.y;

                        //If it was a leftward or rightward swipe, keep going
                        if(Math.abs(ydeflection) < Math.abs(xdeflection)) {
                            if(startTime - lastTimeSwiped > 900) {
                                //Determine if leftward or rightward, call left and right events
                                if(xdeflection < Math.abs(xdeflection) ) {
                                    System.out.println("leftward swipe done");
                                    if(leftButton.isVisible()) {
                                        leftButtonClicked(null);
                                    }
                                } else {
                                    System.out.println("rightward swipe done");
                                    if(rightButton.isVisible()) {
                                        rightButtonClicked(null);
                                    }
                                lastTimeSwiped = System.currentTimeMillis();
                                }
                            }
                        }
                    }
                    //Clear the times array
                    for(int i=0; i<8; i++) {
                        times[i] = 0;
                    }
                }
            }
        });

        //Put in the connected button
        connectedButton = new TransparentButton(disconnected);
        connectedButton.setLocation(100,255);
        connectedButton.setExitAlpha(0.3f);
        connectedButton.setEnterAlpha(0.3f);
        connectedButton.setToolTipText("F1 for Help");
        add(connectedButton);

        //Put in the plugin fileDrop
        new FileDrop( this, new FileDrop.Listener() {

            @Override
            public void filesDropped( java.io.File[] files ) {
                pluginDropped( files );
            }
        } );

        //Put in the wheel listener
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                if(rotation<0) {
                    leftButtonClicked(null);
                } else {
                    rightButtonClicked(null);
                }
            }
        });


        //Put in searchbar
        searchbar = new SuperSearch(_app);
        searchbar.setBounds(5,282,205,21);
        add(searchbar);
    }

    private void connectActionPerformed(java.awt.event.MouseEvent evt) {
        _app.blurrer.setBlurring(true,"Connecting...");
        new Thread( new Runnable() {
            @Override
            public void run() {
                Collector.connectToDefault();
                _app.blurrer.setBlurring(false,"Connecting...");
            }
        } ).start();
    }

    /**
     * Called when a plugin gets dropped, it calls on the plugin loader
     *
     * @param files
     */
    private void pluginDropped( final File[] files )
    {
        final ArrayList<File> fileList = Collator.unpackInstall(files);
        if(fileList != null)
        {
            System.out.println( "****You dropped " + files[0].getName() );
            _app.blurrer.setBlurring(true, "Installing plugin...");
            
                new Thread() {

                    //@Override
                    public void run() {
                       
                        List<File> installList = Collator.areInstallable(fileList);
                        if (!installList.isEmpty()) {
                            Collator.installPlugins(installList);
                        }
                        _app.blurrer.setBlurring(false, "Installing plugin...");
                       //Collator.refreshDash();
                    }
                }.start();
                //Collator.refreshDash();
            
        }
    }

    protected void unlockButtonClicked(java.awt.event.MouseEvent evt) {
        //Put the button allowing for the window to be dragged around
        draggerHeader.setVisible(isLocked);

        //Toggle the icon
        if(isLocked) {
            lockButton.changeIcon(lock);
            lockButton.setToolTipText("Lock");
        } else {
            lockButton.changeIcon(unlock);
            lockButton.setToolTipText("Unlock");
        }

        //Toggle whether the window is locked on top or not
        _app.setAlwaysOnTop(!isLocked);
        isLocked = !isLocked;
    }

    protected void expandButtonClicked(java.awt.event.MouseEvent evt) {
        int numcol = ItemPanel.numCol;
        int newcol = 3;
        if(numcol==3) {
             newcol = 4;
        } else if(numcol==4) {
             newcol = 5;
        } else if(numcol==5) {
             newcol = 3;
        }
        calculateNumPanels();
        _app.toolpanel.changeNumCols(newcol);
    }

    protected void minimizeButtonClicked(java.awt.event.MouseEvent evt) {
        _app.setState(JFrame.ICONIFIED);
    }

    protected void closeButtonClicked(java.awt.event.MouseEvent evt) {
        _app.dispose();
    }

    protected void manageButtonClicked(java.awt.event.MouseEvent evt) {
        //If plugin manager is installed, launch it
        try {
            Collator.launchPluginManager();
        } catch(java.lang.NullPointerException e) {
            System.out.println("plugin manager was not available");
        }
    }

    void rightButtonClicked(java.awt.event.MouseEvent evt) {
        if(_app.buttonPanel.currPanel < numToolPanels - 1) {
            _app.buttonPanel.currPanel ++;
            _app.buttonPanel.setLeftAndRightButtons();
            _app.toolpanel.initcomponents();
            _app.toolpanel.validate();
            _app.validate();
        }
    }

    void leftButtonClicked(java.awt.event.MouseEvent evt) {
        if(_app.buttonPanel.currPanel > 0) {
            _app.buttonPanel.currPanel --;
            _app.buttonPanel.setLeftAndRightButtons();
            _app.toolpanel.initcomponents();
            _app.toolpanel.validate();
            _app.validate();
        }
    }

    void setLeftAndRightButtons() {
        if ( numToolPanels == 1 ) {
            rightButton.setVisible( false );
            leftButton.setVisible( false );
            return;
        }
        if ( currPanel == 0 ) {
            leftButton.setVisible( false );
        } else {
            leftButton.setVisible( true );
        }
        if ( currPanel == numToolPanels - 1 ) {
            rightButton.setVisible( false );
        } else {
            rightButton.setVisible( true );
        }
    }

    private void helpActionPerformed() {
        String url = "http://wiki.bu.edu/ece-clotho/index.php/Dashboard";
        BareBonesBrowserLaunch.openURL(url);
    }

    public void setConnected() {
        if ( Collector.isConnected() ) {
            connectedButton.changeIcon( connected );
        } else {
            connectedButton.changeIcon( disconnected );
        }
    }

    void calculateNumPanels() {
        ArrayList<ToolWrapper> _wraps = Collator.getAllTools();
        numWraps = (double) _wraps.size();
        double dcount = Math.ceil( numWraps / ItemPanel.getNumPerToolPanel() );
        numToolPanels = (int) dcount;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

/*-----------------
     variables
 -----------------*/
private Application _app;
private double numWraps;
private static  ImageIcon bkgImage;
private static  ImageIcon unlock;
private static  ImageIcon lock;
private static  ImageIcon connected;
private static  ImageIcon disconnected;
private static  ImageIcon popUpInfo;
private GlassButton draggerHeader;

private TransparentButton lockButton;

private TransparentButton leftButton;
private TransparentButton rightButton;
private TransparentButton connectedButton;
private TransparentButton popUpButton;
private boolean isLocked=true;
private Point mousePointer;
int numToolPanels;
int currPanel = 0;
private ActionListener connectListen;
//For update checking
private static boolean _checkingUpdates=false;
private static String _updateURL="";
SuperSearch searchbar ;
}
