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

package org.clothocad.tool.tutorialwizard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.wrapper.ConnectionWrapper;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.core.wrapper.WidgetWrapper;
import org.clothocore.api.data.ObjType;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class ImportedTutorialFrame extends JFrame {

    public ImportedTutorialFrame(ArrayList<File> files) {
        super("Tutorial Wizard");
        if(files.size()==0) {
            return;
        }
        _files = files;
        numslide = files.size();
        this.setIconImage(ImageSource.getTinyLogo());
        initcomponents();
    }

    private void initcomponents() {
        setSize(new Dimension(677,505));
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        ImageIcon firstimage = new ImageIcon(_files.get(0).getAbsolutePath());
        imagelabel = new JLabel(firstimage);
        imagelabel.setBounds(0,0,672,480);
        getContentPane().add(imagelabel);

        //Put in the previous button
        previousButton = new TransparentButton(leftarrow);
        previousButton.setLocation(40,410);
        previousButton.setEnterAlpha(0.8f);
        previousButton.setExitAlpha(1.0f);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goPrevious();
            }
        });
        getLayeredPane().add(previousButton,2);
        previousButton.setVisible(false);

        //Put in the next button
        nextButton = new TransparentButton(rightarrow);
        nextButton.setLocation(580,410);
        nextButton.setEnterAlpha(0.8f);
        nextButton.setExitAlpha(1.0f);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goNext();
            }
        });
        getLayeredPane().add(nextButton,2);

        setVisible(true);
    }

    private void goNext() {
        if(currentIndex==numslide-1) {
            dispose();
        }
        currentIndex++;
        drawScreen(isPrev(), isNext());
    }


    private void goPrevious() {
        currentIndex--;
        drawScreen(isPrev(), isNext());
    }

    private void drawScreen(boolean prev, boolean next) {
        //Remove any buttons added during the previous step
        for(JComponent gb : otherButtons) {
            getLayeredPane().remove(gb);
        }
        otherButtons.clear();

        //Do the background image
        if(currentIndex >= this.numslide) {
            return;
        }
        ImageIcon firstimage = new ImageIcon(_files.get(currentIndex).getAbsolutePath());
        imagelabel.setIcon(firstimage);
        imagelabel.setBounds(0,0,672,480);
        getContentPane().add(imagelabel);

        //Put in the previous button
        if(prev) {
            previousButton.setVisible(true);
        } else {
            previousButton.setVisible(false);
        }

        //Put in the next button
        if(next) {
            nextButton.changeIcon(rightarrow);
        } else {
            nextButton.changeIcon(endbutton);
        }

        runSpecialInstructions();
        validate();
        getContentPane().repaint();
        getLayeredPane().repaint();
    }

    private void runSpecialInstructions() {
        switch(currentIndex) {
            case 0:
                break;
            default:
                break;
        }
    }

    private boolean isPrev() {
        if(currentIndex==0) {
            return false;
        }
        return true;
    }

    private boolean isNext() {
        if(currentIndex==numslide-1) {
            return false;
        }
        return true;
    }

    private void endItAll() {
        WidgetWrapper ww = (WidgetWrapper) Collator.getPluginByUUID("org.clothocad.widget.onstartup");
        if(ww!=null) {
            Collator.uninstall(ww);
        }
    }
/*-----------------
     variables
 -----------------*/
    private int currentIndex=0;

    private ArrayList<File> _files;
    int numslide = 0;

    private JLabel imagelabel;
    private TransparentButton nextButton;
    private TransparentButton previousButton;

    private ImageIcon leftarrow = ImageUtilities.loadImageIcon("org/clothocad/tool/tutorialwizard/buttons/leftarrow.png", false);
    private ImageIcon rightarrow = ImageUtilities.loadImageIcon("org/clothocad/tool/tutorialwizard/buttons/rightarrow.png", false);
    private ImageIcon endbutton = ImageUtilities.loadImageIcon("org/clothocad/tool/tutorialwizard/buttons/endbutton.png", false);
    ArrayList<JComponent> otherButtons = new ArrayList<JComponent>();
}
