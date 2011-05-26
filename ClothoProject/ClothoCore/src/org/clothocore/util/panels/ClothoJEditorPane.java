/*
 Copyright (c) 2008 The Regents of the University of California.
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

package org.clothocore.util.panels;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.util.ArrayList;

/**
 *
 * @author Nade Sritanyaratana
 */
public class ClothoJEditorPane {

/**
 * Constructor for ClothoJEditorPane.
 */
    public ClothoJEditorPane(){
    }
    
    /**
     * Returns the string "as displayed" on the JEditorPane
     * @param tp
     * @return
     */
    public String getDisplayed(JScrollPane sp, JEditorPane tp) {
        String text = tp.getText();
        if (tp.getContentType().equalsIgnoreCase("text/plain")) {
            text = tp.getText();
        }
        else if (tp.getContentType().equalsIgnoreCase("text/html")) {
            try {
            text = tp.getDocument().getText(0, tp.getDocument().getLength());
            //System.out.println(text);
            }
            catch(javax.swing.text.BadLocationException e) { }
        }
        
        
        int w = sp.getViewportBorderBounds().width;
        //more compensation
        if (w>20) {
        w -= 20;
        }
        
        String newtext = "";
        int linewidth = 0;
        
        String[] words = text.split("[ \n]");
        int currentwordindex = 0;
        int currentwordwidth = 0;
        
        for (int i=0; i<text.length(); i++) {
            int cwidth = tp.getFontMetrics(tp.getFont()).charWidth(text.charAt(i));
            currentwordwidth += cwidth;
            //stringwidth += cwidth;
            
            if (text.substring(i, i+1).matches(" ")){
                linewidth += currentwordwidth;
                    if (linewidth>w) {
                        newtext = newtext.concat("\n" + words[currentwordindex] + text.substring(i, i+1));
                        linewidth = currentwordwidth;
                    }
                    else {
                        newtext = newtext.concat(words[currentwordindex] + text.substring(i, i+1));
                    }
                currentwordindex++;
                currentwordwidth = 0;
            }
            if (text.substring(i, i+1).matches("\n")) {
                newtext = newtext.concat(words[currentwordindex] + "\n");
                linewidth=0;
                currentwordindex++;
                currentwordwidth = 0;
            }
        }
        return newtext;
    }
    
}
