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

package org.clothocore.util.dialog;

import javax.swing.Icon;
import org.clothocore.util.frames.OptionFrame;

/**
 * Basic class which will be used to create dialog boxes in Clotho.
 * This also supports option dialogs
 * @author Douglas Densmore
 */
public class ClothoDialogBox{

    public ClothoDialogBox(String title, String message)
    {
        _message = message;
        _title = title;
        _of = new OptionFrame();
        _df = new DialogFrame();
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////
    
    
    public void show_Dialog(int message_type)
    {   
        javax.swing.JOptionPane.showMessageDialog(
			    _of,
			    _message,
                            _title,
                            message_type);    
    }
    
    public void show_Dialog(int message_type, Icon i)
    {   
        javax.swing.JOptionPane.showMessageDialog(
			    _of,
			    _message,
                            _title,
                            message_type,
                            i);    
    }
    
    
    public int show_optionDialog(int option_type, int message_type)
    {
        String[] yesNoOpt = {"Yes", "No"};
        int n =  javax.swing.JOptionPane.showOptionDialog(
			    _of,
			    _message,
                            _title,
			    option_type,
                            message_type,
                            null,
                            yesNoOpt,
                            yesNoOpt[1]);
        
        return n;
    }
    
    public int show_optionDialog(int option_type, int message_type, Icon i)
    {
        String[] yesNoOpt = {"Yes", "No"};
        
        int n =  javax.swing.JOptionPane.showOptionDialog(
			    _of,
			    _message,
                            _title,
			    option_type,
                            message_type,
                            i,
                            yesNoOpt,
                            yesNoOpt[1]);
        
        return n;
    }
    
    public int show_optionDialog(int option_type, int message_type, Object[] options, Object initialValue)
    {
        
        int n =  javax.swing.JOptionPane.showOptionDialog(
			    _of,
			    _message,
                            _title,
			    option_type,
                            message_type,
                            null,
                            options,
                            initialValue);
        
        return n;
    }
    
    public int show_optionDialog(int option_type, int message_type, Icon i, Object[] options, Object initialValue)
    {
        
        int n =  javax.swing.JOptionPane.showOptionDialog(
			    _of,
			    _message,
                            _title,
			    option_type,
                            message_type,
                            i,
                            options,
                            initialValue);
        
        return n;
    }
    


    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////       
    
    
    private String _message;
    private String _title;
    
    private OptionFrame _of;
    private DialogFrame _df;
    
}
