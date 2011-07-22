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

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import org.openide.util.Exceptions;
//import org.clothocad.core.ClothoCore;

/**
 * Listener used to monitor changes in the text of the SequenceView
 * @author Matthew Johnson
 */
public class SequenceViewDocumentListener implements DocumentListener {

    public SequenceViewDocumentListener(JTextPane tp, SequenceView c) {
        _textPane = tp;
        _connection = c;
        _clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        _insertIsInsideAHighlight = false;
        _insertIsInsideAHighlightWarnedAlready = false;
        _removalIsAffectingAHighlight = false;
        _removalIsAffectingAHighlightWarnedAlready = false;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////
    @Override
    public void insertUpdate(DocumentEvent evt) {
//        updateHighlightData_Insert(evt);
        if (!_insertIsInsideAHighlightWarnedAlready && _insertIsInsideAHighlight) {
            _connection.set_insertIsInsideAHighlight(_insertIsInsideAHighlight);
            _insertIsInsideAHighlight = false;
            _insertIsInsideAHighlightWarnedAlready = true;
        }
        //Quick fix for ORF issue: underlining ORF then type after that, it keep underlining new character

        javax.swing.text.Highlighter hl = _connection.getSequenceView().get_TextArea().getHighlighter();
        javax.swing.text.Highlighter.Highlight[] highlights = hl.getHighlights();
        for (int i = 0; i < highlights.length; i++) {
            javax.swing.text.Highlighter.Highlight u = highlights[i];
            //u.getEndOffset(); // --> new extended offset including new characters
            //evt.getOffset();  // original, where the cursor before insert
            //modified only when the evt is add at the end of the old ORF hightlight, thus end of evt is end of u
            if ((u.getStartOffset() < evt.getOffset())
                    && (u.getEndOffset() == evt.getOffset() + evt.getLength()) //&& (u.getPainter() instanceof org.clothocad.util.highlight.ClothoHighlightPainter_Underline) //commented
                    ) {
                try {
                    hl.changeHighlight(u, u.getStartOffset(), u.getEndOffset() - evt.getLength());
                } catch (javax.swing.text.BadLocationException ble) {
                    //commented
                    // ClothoCore.getCore().log(ble.toString(), org.clothocad.core.ClothoCore.LogLevel.ERROR);
                }
            } //if add in the middle of the ORF hightlight, delete the hightlight
            else if ((u.getStartOffset() < evt.getOffset())
                    && (u.getEndOffset() > evt.getOffset() + evt.getLength()) //commented
                    //&& (u.getPainter() instanceof org.clothocad.util.highlight.ClothoHighlightPainter_Underline)
                    ) {
                hl.removeHighlight(u);
            }
            _connection.sequenceChanged();
        }


        // Checks to see if any text over one character is added, and makes it
        // the current selection
        //BUG: this makes after copy/paste the selection includes old characters
        /*
        if (evt.getLength() > 1) {
        JTextPane s = _connection.getSequenceView().get_TextArea();
        //s.setSelectionStart(s.getCaretPosition());
        s.setSelectionStart(s.getCaretPosition() - evt.getLength());
        s.setSelectionEnd(s.getCaretPosition() );
        }
         */
    }

    @Override
    public void removeUpdate(DocumentEvent evt) {
//        updateHighlightData_Remove(evt);
        if (!_removalIsAffectingAHighlightWarnedAlready && _removalIsAffectingAHighlight) {
            _connection.set_removalIsAffectingAHighlight(_removalIsAffectingAHighlight);
            _removalIsAffectingAHighlight = false;
            _removalIsAffectingAHighlightWarnedAlready = true;
        }
        javax.swing.text.Highlighter hl = _connection.getSequenceView().get_TextArea().getHighlighter();
        javax.swing.text.Highlighter.Highlight[] highlights = hl.getHighlights();
        for (int i = 0; i < highlights.length; i++) {
            javax.swing.text.Highlighter.Highlight u = highlights[i];
            //deleting before a higlight
            if (evt.getOffset() < u.getStartOffset()) {
                try {
                    hl.changeHighlight(u, u.getStartOffset() - evt.getLength() + 1, u.getEndOffset() - evt.getLength() + 1);
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
                //deleting from the middle
            } else if ((u.getStartOffset() <= evt.getOffset()) && (u.getEndOffset() >= evt.getOffset() + evt.getLength())) {
                hl.removeHighlight(u);
                //deleting at the end of a highlight
            } else if ((u.getEndOffset() == evt.getOffset())) {
                hl.removeHighlight(u);
            }

        }
        _connection.sequenceChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent evt) {
//        javax.swing.text.Highlighter hl = _connection.getSequenceView().get_TextArea().getHighlighter();
//        javax.swing.text.Highlighter.Highlight[] highlights = hl.getHighlights();
//        for (int i = 0; i < highlights.length; i++) {
//            javax.swing.text.Highlighter.Highlight u = highlights[i];
//            if ((u.getStartOffset() < evt.getOffset()) && (u.getEndOffset() > evt.getOffset() + evt.getLength())) {
//                hl.removeHighlight(u);
//            }
//
//        }
        _connection.sequenceChanged();
    }
    //DEBUG HERE: Revise Sequence upon breaking a highlight.
//    public void updateHighlightData_Insert(DocumentEvent evt) {
//        if (!_connection.get_highlightDataMade()) {
//            return;
//        }
//        for (int i=0; i<_connection.get_highlightData().size(); i++) {
//            //The following block stores highlight index information
//            Integer startIndexInteger = (Integer)_connection.get_highlightData().get(i).get(1);
//            Integer endIndexInteger = (Integer)_connection.get_highlightData().get(i).get(2);
//            int startIndex = startIndexInteger.intValue();
//            int endIndex = endIndexInteger.intValue();
//            //all highlights above the caret position must be moved up. So,
//            // The following block moves up all affected highlights.
//            if (evt.getOffset() <= startIndex) {
//                _connection.get_highlightData().get(i).set(1, startIndex+evt.getLength());
//                _connection.get_highlightData().get(i).set(2, endIndex+evt.getLength());
//            }
//            //The following block handles insertions inbetween highlights;
//            // highlightData will be split but only temporarily; everything
//            // resets if user invokes clearHighlight
//            if (evt.getOffset() < endIndex && evt.getOffset() > startIndex) {
//                //The following line sets a boolean that will be used to later 
//                // warn of the impending insertion.
//                _insertIsInsideAHighlight = true;
//                //The following block will split the highlight.
//                //Revise sequences as well too, later (set 4)
//                _connection.get_highlightData().add(i+1,(SingleHighlight)_connection.
//                        get_highlightData().get(i).clone());
//                _connection.get_highlightData().get(i+1).set(1, evt.getOffset());
//                _connection.get_highlightData().get(i+1).set(2, endIndex);
//                _connection.get_highlightData().get(i).set(2, evt.getOffset());
//            }
//        }
//    }
//    
//    public void updateHighlightData_Remove(DocumentEvent evt) {
//        if (!_connection.get_highlightDataMade()) {
//            return;
//        }
//        
//        if (evt.getLength() == 1) {
//            for (int i=0; i<_connection.get_highlightData().size(); i++) {
//                //In the case that backspace was pressed...
//                if (_connection.get_backspaceKeyPressed()) {
//                    //The following block stores highlight index information
//                    Integer startIndexInteger = (Integer)_connection.get_highlightData().get(i).get(1);
//                    Integer endIndexInteger = (Integer)_connection.get_highlightData().get(i).get(2);
//                    int startIndex = startIndexInteger.intValue();
//                    int endIndex = endIndexInteger.intValue();
//                    //If the removal is BEFORE the highlight...
//                    if (evt.getOffset() < startIndex &&
//                            evt.getOffset() < endIndex) {
//                        //The following block shifts the affected highlights down.
//                        _connection.get_highlightData().get(i).set(1, startIndex-1);
//                        _connection.get_highlightData().get(i).set(2, endIndex-1);
//                    }
//                    //If the removal is INSIDE the highlight...
//                    else if (evt.getOffset() >= startIndex && 
//                            evt.getOffset() < endIndex) {
//                        //System.out.println("Inside Highlight");
//                        //_connection.get_highlightData().get(i).set(1, startIndex);
//                        _connection.get_highlightData().get(i).set(2, endIndex-1);
//                        _removalIsAffectingAHighlight = true;
//                    }
//                }
//                //In the case that delete was pressed...
//                if (_connection.get_deleteKeyPressed()) {
//                    //The following block stores highlight index information
//                    Integer startIndexInteger = (Integer)_connection.get_highlightData().get(i).get(1);
//                    Integer endIndexInteger = (Integer)_connection.get_highlightData().get(i).get(2);
//                    int startIndex = startIndexInteger.intValue();
//                    int endIndex = endIndexInteger.intValue();
//                    //If the removal is BEFORE the highlight...
//                    if (evt.getOffset() < startIndex && evt.getOffset() < endIndex) {
//                        //The following block shifts the affected highlights down.
//                        _connection.get_highlightData().get(i).set(1, startIndex-1);
//                        _connection.get_highlightData().get(i).set(2, endIndex-1);
//                    }
//                    //If the removal is INSIDE the highlight...
//                    else if (evt.getOffset() >= startIndex && evt.getOffset() < endIndex) {
//                        //System.out.println("event offset: " + evt.getOffset());
//                        //System.out.println("event length: " + evt.getLength());
//                        //System.out.println(_connection.get_highlightData().get(i));
//                        _connection.get_highlightData().get(i).set(2, endIndex - 1);
//                        _removalIsAffectingAHighlight = true;
//                    }
//                }
//            }
//            _connection.set_backspaceKeyPressed(false);
//            _connection.set_deleteKeyPressedPressed(false);
//        }
//        //There are five kinds of selection removals:
//        // Case 1. Highlight isn't affected, but is shifted.
//        // Case 2. The whole highlight is removed.
//        // Case 3. The starting portion of the highlight is removed.
//        // Case 4. The ending portion of the highlight is removed.
//        // Case 5. The inside of the highlight is removed.
//        if (evt.getLength()>1) {
//            if (_textPane.getText().length() == 0) {
//                _connection.get_highlightData().clear();
//            }
//            for (int i=0; i<_connection.get_highlightData().size(); i++) {
//                //Case 1
//                if (evt.getOffset()+evt.getLength() <= (Integer)_connection.get_highlightData().get(i).get(1) &&
//                        evt.getOffset()+evt.getLength() <= (Integer)_connection.get_highlightData().get(i).get(2)) {
//                    //The following block stores highlight index information
//                    Integer startIndexInteger = (Integer)_connection.get_highlightData().get(i).get(1);
//                    Integer endIndexInteger = (Integer)_connection.get_highlightData().get(i).get(2);
//                    int startIndex = startIndexInteger.intValue();
//                    int endIndex = endIndexInteger.intValue();
//                    //The following block sets the shifted values to _highlightData
//                    _connection.get_highlightData().get(i).set(1, startIndex-evt.getLength());
//                    _connection.get_highlightData().get(i).set(2, endIndex-evt.getLength());
//                }
//                //Case 2
//                else if (evt.getOffset() <= (Integer)_connection.get_highlightData().get(i).get(1) && 
//                        evt.getOffset()+evt.getLength() >= (Integer)_connection.get_highlightData().get(i).get(2)) {
//                    _connection.get_highlightData().remove(i);
//                    _removalIsAffectingAHighlight = true;
//                }
//                //Case 3
//                else if (evt.getOffset()+evt.getLength() < (Integer)_connection.get_highlightData().get(i).get(2) &&
//                        evt.getOffset()+evt.getLength() >= (Integer)_connection.get_highlightData().get(i).get(1) &&
//                        evt.getOffset() <= (Integer)_connection.get_highlightData().get(i).get(1)) {
//                    //System.out.println("event offset: " + evt.getOffset());
//                    //System.out.println("event length: " + evt.getLength());
//                    //System.out.println(_connection.get_highlightData().get(i));
//                    //System.out.println("Case 3");
//                    
//                    //The following block stores highlight index information
//                    Integer endIndexInteger = (Integer)_connection.get_highlightData().get(i).get(2);
//                    int endIndex = endIndexInteger.intValue();
//                    _connection.get_highlightData().get(i).set(1, evt.getOffset());
//                    _connection.get_highlightData().get(i).set(2, endIndex - evt.getLength());
//                    _removalIsAffectingAHighlight = true;
//                }
//                //Case 4
//                else if (evt.getOffset()+evt.getLength() >= (Integer)_connection.get_highlightData().get(i).get(2) &&
//                        evt.getOffset() < (Integer)_connection.get_highlightData().get(i).get(2)) {
//                    _connection.get_highlightData().get(i).set(2, evt.getOffset());
//                    _removalIsAffectingAHighlight = true;
//                }
//                //Case 5
//                else if (evt.getOffset() > (Integer)_connection.get_highlightData().get(i).get(1) &&
//                        evt.getOffset()+evt.getLength() < (Integer)_connection.get_highlightData().get(i).get(2)) {
//                    //The following block stores highlight index information
//                    Integer endIndexInteger = (Integer)_connection.get_highlightData().get(i).get(2);
//                    int endIndex = endIndexInteger.intValue();
//                    _connection.get_highlightData().get(i).set(2, endIndex - evt.getLength());
//                    _removalIsAffectingAHighlight = true;
//                }
//            }
//
//        }
//    }
    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 //// 
    java.awt.datatransfer.Clipboard _clipboard;
    private boolean _insertIsInsideAHighlight;
    private boolean _insertIsInsideAHighlightWarnedAlready;
    private boolean _removalIsAffectingAHighlight;
    private boolean _removalIsAffectingAHighlightWarnedAlready;
    private SequenceView _connection;
    private JTextPane _textPane;
}
