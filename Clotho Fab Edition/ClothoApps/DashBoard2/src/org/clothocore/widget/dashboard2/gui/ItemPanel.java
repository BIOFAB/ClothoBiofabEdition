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
ENHANCEMENTS, OR MODIFICATIONS.
 */
package org.clothocore.widget.dashboard2.gui;

import java.awt.BorderLayout;
import org.clothocore.util.buttons.TransparentButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.clothocore.api.core.wrapper.ToolWrapper;

/**
 *
 * @author jcanderson
 */
public class ItemPanel extends JPanel {

    public ItemPanel( ToolWrapper tw, int x, int y, ToolPanel pan ) {
        xpos = x;
        ypos = y;
        MyPanel = pan;
        _tw = tw;

        String name = tw.getDisplayName();

        if ( name.length() > 20 ) {
            toolName = name.substring( 0, 20 );
        } else {
            toolName = name;
        }
        originalName = toolName;

        //Figure out how to split up the string into 2 lines, centered
        String[] lines = toolName.split("\\s+");
        toolName="";
        for(int lineIndex=0; lineIndex<lines.length; lineIndex++) {
            double dlen = Math.floor( ( 10 - lines[lineIndex].length() )/2 );
            int len = (int) dlen;
            for(int i=0; i<len; i++) {
                toolName+="  ";
            }
            if(lines[lineIndex].length()>10) {
                toolName += lines[lineIndex].substring(0,10);
            } else {
                toolName += lines[lineIndex];
            }
            toolName+="\n";
        }

        initcomponents();
    }


    static void setSizes() {
        //Put in the image size
        if(numCol==3) {
            size = 50;
            fontsize = 10;
            numRow = 2;
        } else if(numCol==4) {
            size = 38;
            fontsize = 8;
            numRow = 3;
        } else if(numCol==5) {
            size = 35;
            numRow = 4;
        }
    }

    private void initcomponents() {
        setOpaque( false );
        setLayout( new BorderLayout() );

        //Put in the image
        ImageIcon imh = _tw.getIcon( size);

        imgLauncher = new TransparentButton( imh );
        imgLauncher.setExitAlpha( 1.0f );
        imgLauncher.setEnterAlpha( 0.7f );
        imgLauncher.addMouseListener( new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked( java.awt.event.MouseEvent evt ) {
                launchButtonClicked( evt );
            }
        } );
        add( imgLauncher, BorderLayout.CENTER );

        //Add tool tip with label
        imgLauncher.setToolTipText( originalName );

        if(numCol<5) {
            //Put in the label
            JTextArea lab = new JTextArea( toolName );
            lab.setFont( new Font( "Arial", Font.BOLD, fontsize ) );
            lab.setForeground( textColor );
            lab.setBackground( null );
            lab.setOpaque( false );
            lab.setEditable( false );
            add( lab, BorderLayout.SOUTH );
        }

        //Put in the wheel listener
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                if(rotation<0) {
                    MyPanel._app.buttonPanel.leftButtonClicked(null);
                } else {
                    MyPanel._app.buttonPanel.rightButtonClicked(null);
                }
            }
        });
    }

    private void launchButtonClicked( java.awt.event.MouseEvent evt ) {
        //If it was a right click, select it for moving
        if ( evt.getModifiers() == 4 ) {
            //If it's the first item selected, highlight it and store it
            if ( firstSelected == null ) {
                firstSelected = this;
                firstSelected.imgLauncher.setEnterAlpha( 0.2f );
                firstSelected.imgLauncher.setExitAlpha( 0.2f );
            } else {
                firstSelected.imgLauncher.setExitAlpha( 1.0f );
                firstSelected.imgLauncher.setEnterAlpha( 0.7f );
                firstSelected.imgLauncher.reset();
                secondSelected = this;
             //   dash.reorder();
                secondSelected = null;
                firstSelected = null;
            }
        }

        //do a launch if left click
        if ( evt.getClickCount() > 1 ) {
            java.awt.EventQueue.invokeLater( new Runnable() {

                @Override
                public void run() {
                    _tw.launchTool();
                }
            } );

        } else {
            //select it so can move around
        }
    }

    static int getNumPerToolPanel() {
        return numCol * numRow;
    }
    
    /*-----------------
    variables
    -----------------*/
    private TransparentButton imgLauncher;
    private String toolName = "";
    private String originalName = "";
    private static Color textColor = new Color( 180, 182, 176 );
    static ItemPanel firstSelected;
    static ItemPanel secondSelected;
    ToolWrapper _tw;
    int xpos;
    int ypos;
    ToolPanel MyPanel;

    private static int size;
    private static int fontsize;

    static int numCol = 4;  //5
    static int numRow = numCol - 1;

}
