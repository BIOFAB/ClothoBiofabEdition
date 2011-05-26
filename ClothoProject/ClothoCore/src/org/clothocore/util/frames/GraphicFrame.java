/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocore.util.frames;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.util.buttons.GlassButton;
import org.clothocore.util.buttons.TransparentButton;
import org.clothocore.util.misc.BareBonesBrowserLaunch;


/**
 *
 * @author jcanderson
 */
public class GraphicFrame extends JFrame  {

    public GraphicFrame(String frame_title, String background_image, String minimize_image, String close_image, String expand_image) {
        super(frame_title);
        initcomponents( background_image,  minimize_image,  close_image,  expand_image);
    }


    protected void initcomponents(String background_image, String minimize_image, String close_image, String expand_image) {
        //Load the icons
        ImageIcon minimize = new ImageIcon(minimize_image);
        ImageIcon close = new ImageIcon(close_image);
        ImageIcon expand = new ImageIcon(expand_image);
        bkgImage = new ImageIcon(background_image);
        
        int frameWidth = bkgImage.getIconWidth();
        int horizontalSpacing = 2;
        int verticalSpacing = 2;

        //Put in background image
        JLabel bkgLabel = new JLabel(bkgImage);
        bkgLabel.setBounds(0,0,frameWidth, bkgImage.getIconHeight());
        getContentPane().add(bkgLabel);

        int horizontalPosition = frameWidth-horizontalSpacing;
        System.out.println(horizontalPosition);

        //Put in a close button
        TransparentButton closeButton = new TransparentButton(close);
        horizontalPosition = horizontalPosition - close.getIconWidth();
        System.out.println(horizontalPosition);
        closeButton.setLocation(horizontalPosition,verticalSpacing);
        closeButton.setToolTipText("Close");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            closeButtonClicked(evt);}});
        getLayeredPane().add(closeButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;

        //Put in a minimize button
        TransparentButton minimizeButton = new TransparentButton(minimize);
        horizontalPosition = horizontalPosition - minimize.getIconWidth();
        System.out.println(horizontalPosition);
        minimizeButton.setLocation(horizontalPosition,verticalSpacing);
        minimizeButton.setToolTipText("Minimize");
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            minimizeButtonClicked(evt);}});
        getLayeredPane().add(minimizeButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;

        //Put in a expand button
        TransparentButton expandButton = new TransparentButton(expand);
        horizontalPosition = horizontalPosition - expand.getIconWidth();
        System.out.println(horizontalPosition);
        expandButton.setLocation(horizontalPosition,verticalSpacing);
        expandButton.setToolTipText("Expand");
        expandButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            expandButtonClicked(evt);}});
        getLayeredPane().add(expandButton, 2);

        horizontalPosition = horizontalPosition-horizontalSpacing;
        System.out.println(horizontalPosition);

        //Put in a coverButton button
        coverButton = new GlassButton("");
        coverButton.setBounds(0,0,horizontalPosition, verticalSpacing + expand.getIconHeight());
        coverButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                coverButtonDragged(evt);}
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                coverButtonMoved(evt);}});
        getLayeredPane().add(coverButton, 0);



        //Size it
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(bkgImage.getIconWidth(), bkgImage.getIconHeight());

        pack();
        setVisible(true);
    }


    protected void paintComponent(Graphics g) {
        g.drawImage(bkgImage.getImage(), 0, 0, null);
    }

    protected void coverButtonDragged(java.awt.event.MouseEvent evt) {
        Point aPoint = evt.getPoint();
        int x = getX() + aPoint.x - mousePointer.x;
        int y = getY() + aPoint.y - mousePointer.y;
        setLocation(x, y);
        Graphics graphics = getGraphics();
        paint(graphics);
    }

    public void setPopUpText(String message) {
        popUpText.setText(message);
        popUpButton.setVisible(true);
        popUpText.setVisible(true);
    }

    protected void popUpButtonClicked() {
        if(_checkingUpdates) {
            BareBonesBrowserLaunch.openURL(_updateURL);
        }

        popUpButton.setVisible(false);
        popUpText.setVisible(false);
    }

    protected void isFirstTime() {
        String isFirst = _prefs.get( "IsFirstTimeAccessed", "isFirst" );
        if(!isFirst.equals("nope")) {
            setPopUpText("Welcome to Clotho!\nHit F1 or ctrl-H for help.");
            _prefs.put( "IsFirstTimeAccessed", "nope" );
        }
    }

    protected void coverButtonMoved(java.awt.event.MouseEvent evt) {
        mousePointer = evt.getPoint();
    }

    protected void unlockButtonClicked(java.awt.event.MouseEvent evt) {
        //Put the button allowing for the window to be dragged around
        coverButton.setVisible(isLocked);

        //Toggle the icon
        if(isLocked) {
            lockButton.changeIcon(lock);
            lockButton.setToolTipText("Lock");
        } else {
            lockButton.changeIcon(unlock);
            lockButton.setToolTipText("Unlock");
        }

        //Toggle whether the winodw is locked on top or not
        setAlwaysOnTop(!isLocked);
        isLocked = !isLocked;
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



    private void setLeftAndRightButtons() {
        if(_numToolPanels==1) {
            rightButton.setVisible(false);
            leftButton.setVisible(false);
            return;
        }
        if(_currPanel == 0) {
            leftButton.setVisible(false);
        } else {
            leftButton.setVisible(true);
        }
        if(_currPanel == _numToolPanels-1) {
            rightButton.setVisible(false);
        } else {
            rightButton.setVisible(true);
        }
    }


    private void setMeThinking(boolean isit) {
        loadingAnimation.setVisible(isit);
        loadingButton.setVisible(isit);
    }

    public static void main(String[] args) {
        new GraphicFrame("Test frame", "data/icons/testy/bkg.png", "data/icons/testy/btn.png", "data/icons/testy/btn.png", "data/icons/testy/btn.png").setVisible(true);
    }


/*-----------------
     variables
 -----------------*/

private static  ImageIcon bkgImage;
private static  ImageIcon unlock;
private static  ImageIcon lock;
private static  ImageIcon connected;
private static  ImageIcon disconnected;
private static  ImageIcon popUpInfo;
private GlassButton coverButton;
private GlassButton loadingButton;
private TransparentButton lockButton;
private JLabel loadingAnimation;
private TransparentButton leftButton;
private TransparentButton rightButton;
private TransparentButton connectedButton;
private TransparentButton popUpButton;
private JTextArea popUpText;
private boolean isLocked=true;
private Point mousePointer;
private int _numToolPanels;
private int _currPanel;
private ArrayList<ToolWrapper> _wraps;
private static Preferences _prefs;

//For update checking
private static boolean _checkingUpdates=false;
private static String _updateURL="";
}
