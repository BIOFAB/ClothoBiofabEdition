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

package org.clothocad.tool.sequenceview;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;

/**
 * Listener used to move a set scroll bars simultaneously
 * @author Douglas Densmore
 */
public class SequenceViewAdjustmentListener implements AdjustmentListener {
        
        public SequenceViewAdjustmentListener(JScrollPane j1, JScrollPane j2, SequenceView sv, int t)
        {
            _sequenceScroll = j1;
            _indexScroll = j2;
            _sv = sv;
            _type = t;
        }
        
    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////
        

        public void adjustmentValueChanged(AdjustmentEvent evt) {
           
            if (_type == 1)
            {
                //System.out.print("Adjustment 1\n");
               _sv.moveSequenceScrollPane();
            }
            if (_type == 2)
            {
                //System.out.print("Adjustment 2\n");
                _sv.moveIndexScrollPane();
            }
            //_connection.moveScrollPane();
            
        }
        
    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 //// 
        
        private SequenceView _sv;
        
        private JScrollPane _indexScroll;
        private JScrollPane _sequenceScroll;
        
        private int _type;
        
    }


