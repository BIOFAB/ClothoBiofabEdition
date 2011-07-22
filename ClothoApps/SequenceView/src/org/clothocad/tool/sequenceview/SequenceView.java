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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.text.Caret;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.Position;
import javax.swing.undo.*;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import org.clothocore.api.data.NucSeq;
import org.clothocore.util.dialog.ClothoDialogBox;
import org.clothocore.util.chooser.ClothoOpenChooser;
import org.clothocore.util.chooser.ClothoSaveChooser;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.Person;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.clothocad.tool.primerdesigner.DesignFrame;
/**
 * The sequence view of the design. An editable view for raw DNA data.
 * @author Douglas Densmore
 * @author Jenhan Tao
 * @author Roza
 * @author Nade Sritanyaratana
 * @author Matthew Johnson
 * @author Thien Nguyen
 */
public class SequenceView {

    private static int numOfSeqViews = 0;

    public SequenceView(String n, String d, SequenceViewManager m, int index) {
        _isTC = false; //sequence view launches into frame by default
        _manager = m;
        _myIndex = index;
        _annotationsOn = false;
        _REOn = false;
        _featuresOn = false;
        _sequenceview = new SequenceViewGUI(this);
        _sequence = new NucSeq(_sequenceview.get_TextArea().getText());
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                Person user = Collector.getCurrentUser();
                _sequence.autoAnnotate(user);
                return null;
            }
        }.execute();
        magicHighlight = false;
        _sequenceviewtools = new SequenceViewSearchTools(this);
        _duplicates = new HashSet();
        _sequenceview.get_TextArea().setCaretPosition(0);
        int charwidth = _sequenceview.get_TextArea().getFontMetrics(_sequenceview.get_TextArea().getFont()).charWidth('A');
        int width_offset = _sequenceview.get_TextArea().getWidth();
        _logicalCol = width_offset / charwidth;
        _logicalLineCnt = 1;
        _selectedHit = -1;
        ORFColor = Color.YELLOW;
        userSelectColor = Color.GRAY;
        userHighlightColor = Color.ORANGE;
        _backspaceKeyPressed = false;
        _deleteKeyPressed = false;
        _dnaType = true;
        _fileOpenerInstantiated = false;
        _fileSaverInstantiated = false;
//        _highlightDataMade = false;
        _insertIsInsideAHighlight = false;
        _keyTypedAtLeastOnce = false;
        _locked = false;
        _longTimeHighlightWarned = false;
        _ORFsCalculated = false;
        _removalIsAffectingAHighlight = false;
        _revORFsCalculated = false;
        _saved = true;
        _caseSensitiveOption = "";
        _seqType = "DNA";
        numOfSeqViews++;
        _seqViewNumber = numOfSeqViews;
        //FIXME
        //_partData = new ClothoPartsData();
        //mechanisms for undo
        _undo = new UndoManager();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        Document doc = _sequenceview.get_TextArea().getDocument();
        doc.addUndoableEditListener(new SequenceUndoableEditListener());



        JTextComponent.KeyBinding[] newBindings = {
            new JTextComponent.KeyBinding(
            KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK),
            DefaultEditorKit.writableAction),
            new JTextComponent.KeyBinding(
            KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK),
            DefaultEditorKit.writableAction),
            new JTextComponent.KeyBinding(
            KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK),
            DefaultEditorKit.writableAction)
        };

        Keymap k = _sequenceview.get_TextArea().getKeymap();
        JTextComponent.loadKeymap(k, newBindings, _sequenceview.get_TextArea().getActions());


    }

    public void switchView() {
        if (_isTC) {
            Component[] components = _tcView.getComponents();
            _sequenceview.setContentPane((Container) components[1]);
            _sequenceview.setJMenuBar((JMenuBar) components[0]);
            _sequenceview.pack();
            _sequenceview.setVisible(true);
            _isTC = false;
//FIXME fix top component persistence
            _tcView.close();

        } else {
            final JComponent guiContentPane = (JComponent) _sequenceview.getContentPane();
//            JRootPane guiRootPane = _frameView.getRootPane();
            final JMenuBar menu = _sequenceview.getJMenuBar();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    _tcView = new TopComponent();
                    _tcView.setLayout(new BorderLayout());
                    JScrollPane sp = new JScrollPane(guiContentPane);
                    _tcView.add(menu, BorderLayout.NORTH);
                    _tcView.add(sp, BorderLayout.CENTER);
                    _tcView.setName("Sequence View");
                    _tcView.open();
                    _tcView.requestActive();

                }
            });
            _sequenceview.setVisible(false);
            _isTC = true;
        }
    }

    public void createPrimers(String sequence) {
        if (!Collector.isConnected()) {
            JOptionPane.showMessageDialog(null, "Database connection required to launch this tool",
                    "Not connected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DesignFrame someFrame = new DesignFrame(sequence);    }

    class SequenceUndoableEditListener
            implements UndoableEditListener {

        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            //Remember the edit and update the menus.
            _undo.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        }
    }

    class UndoAction extends AbstractAction {

        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                _undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            updateUndoState();
            redoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (_undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, _undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    class RedoAction extends AbstractAction {

        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                _undo.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            updateRedoState();
            undoAction.updateUndoState();
        }

        protected void updateRedoState() {
            if (_undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, _undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }
    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    //highlights all nonrestriction related features
    public static int getNumberOfSequenceViews() {
        return numOfSeqViews;
    }

    /**
     * Changes the case (UPPER <-> lower) of any text selected in the Sequence
     * View according to the given parameters
     * 
     * @param toUpper True if the text should be capitalized, false if it is
     *                being sent to lower case
     */
    public void changeCase(boolean toUpper) {
        String s = _sequenceview.get_TextArea().getSelectedText();
        if (s != null) {
            if (toUpper) {
                s = s.toUpperCase();
            } else {
                s = s.toLowerCase();
            }
            replaceTextAtSelection(s);
        } else {
            askHighlight("change case");
        }
        refreshHighlights();
    }

    /**
     * Checks to see if the ORFs have been calculated for the sequence, and if
     * they haven't, calculates them.
     */
    public void checkORFs() {
        if (!(_ORFsCalculated) && _sequenceview.get_TextArea().getText() != null) {
//comment            _ORFs = (HashMap) _sequenceUtils.findORFs(_sequenceview.get_TextArea().getText(), _circular, _allowDegeneracy, true, _multipleStartCodons, _dnaType);
            NucSeq ns = new NucSeq(_sequenceview.get_TextArea().getText());
            try {
                _ORFs = ns.findORFs(true, _multipleStartCodons);
            } catch (Exception e) {
                e.printStackTrace();
            }

            _ORFsCalculated = true;

        }
        if (_ORFs.size() == 0) {
            System.out.println("No ORFs in this sequence");
        }
    }

    /**
     * Checks to see if the Reverse ORFs have been calculated for the sequence,
     * and if they haven't, calculates them.
     */
    public void checkRevORFs() {
        if (!(_revORFsCalculated)) {
            _revORFs = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
            String seq = _sequenceview.get_TextArea().getText();
            NucSeq ns = new NucSeq(new NucSeq(seq).revComp());
            temp = ns.findORFs(true, _multipleStartCodons);
            for (Integer i : temp.keySet()) {
                _revORFs.put(seq.length() - i, seq.length() - temp.get(i));//key=orf end, value=orf start
            }
            _revORFsCalculated = true;
        }
        if (_revORFs.size() == 0) {
            System.out.println("No ORFs in this sequence");
        }
    }

    /**
     * Clears the sequence view
     */
    public void clearOutputWindow() {
        _sequenceview.getOutputTextArea().setText("");
    }

    public void configureBasePairRow() {
        int len_offset = _sequenceview.get_TextArea().getDocument().getLength();
        Rectangle r = new Rectangle();
        int lineHeight = _sequenceview.get_TextArea().getFontMetrics(_sequenceview.get_TextArea().getFont()).getHeight();
        try {
            if (len_offset > 0) {
                len_offset--;
            }
            r = _sequenceview.get_TextArea().modelToView(len_offset);//-1 fixes indexing error when inserting a new character
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

        int logicalLines = r.y / lineHeight;

        if (logicalLines + 1 != _logicalLineCnt) {
            _logicalLineCnt = logicalLines + 1;

        }
        int rowValues = 0;
        String indices = new String("");
        for (int i = 0; i < _logicalLineCnt; i++) {
            rowValues = _logicalCol * i + 1;
            //System.out.print(rowValues + "\n");
            indices = indices + rowValues + "\n";
        }
        _sequenceview.setIndexTextArea(indices);

    }

    public void configureBasePairBoth(JLabel columnLabel) {
        //int width_offset = _sequenceview.get_TextArea().getWidth();
        //int width_offset = _sequenceview.getSeqScroll().getWidth();
        //small adjustment to make it match with the way letters wrap around the
        //textpane
        int width_offset = _sequenceview.getSeqScroll().getWidth() + 4;

        //requires fixed width characters
        int charwidth = _sequenceview.get_TextArea().getFontMetrics(_sequenceview.get_TextArea().getFont()).charWidth('A');

        //double d = new Double(width_offset) / new Double(charwidth);
        _logicalCol = width_offset / charwidth;

        //small adjustment to make it true to the viewer
        // Checks to see if the visible height is smaller than the total height
        // of the TextArea, to take the appearance of the scroll bar into account
        if (_sequenceview.get_TextArea().getHeight() > _sequenceview.get_TextArea().getVisibleRect().getHeight()) {
            _logicalCol = _logicalCol - 4;
        } else {
            _logicalCol = _logicalCol - 2;
        }

        // adjusts for the viewer being locked, which decreases lines size by
        // one character
        if (_locked) {
            _logicalCol = _logicalCol - 1;
        }

        Integer col = new Integer(_logicalCol);
        columnLabel.setText(col.toString());

        configureBasePairRow();
    }

    /**
     * Highlights an Open Reading Frame in the SequenceView
     * 
     * @param start Int that specifies
     * @param forward True if it's a forward ORF, false for a reverse ORF
     */
    @Deprecated
    public void createORFHighlight(int start, boolean forward) {
        int end;
        if ((forward && _ORFs.get(start) != null) || (!(forward) && _revORFs.get(start) != null)) {
            String name;
            String seq = _sequenceview.get_TextArea().getText();
            int len = seq.length();
            if (forward) {
                end = _ORFs.get(start);
                name = "ORF";
            } else {
                end = start;
                start = _revORFs.get(end);
                name = "Reverse ORF";
            }
            //FIXME
            /*
            ClothoData highlightData = new ClothoData();
             */
            //commented
            //ClothoHighlightData[] highlighterArray;
            int[][] orfIndex = new int[2][1];
            java.awt.Color col = java.awt.Color.BLACK;

//comment            _startCodons = _sequenceUtils.getStartCodons(forward, _dnaType);

            // seq.concat(seq) is used so that if the start codon is split
            // between the end and start of a circular sequence, it won't
            // return an error
            String startCodon;
            if (forward) {
                startCodon = seq.concat(seq).substring(start, start + 3);
                for (int i = 0; i < _startCodons.size(); i++) {
                    if (startCodon.equalsIgnoreCase(_startCodons.get(0))) {
                        col = new java.awt.Color(0, 255, 0);
                    } else if (startCodon.equalsIgnoreCase(_startCodons.get(1))) {
                        col = new java.awt.Color(255, 255, 0);
                    } else {
                        col = new java.awt.Color(255, 0, 0);
                    }
                }
            } else {
                startCodon = seq.concat(seq).concat(seq).substring(end + len - 3, end + len);
                for (int i = 0; i < _startCodons.size(); i++) {
                    if (startCodon.equalsIgnoreCase(_startCodons.get(0))) {
                        col = new java.awt.Color(0, 128, 0);
                    } else if (startCodon.equalsIgnoreCase(_startCodons.get(1))) {
                        col = new java.awt.Color(128, 128, 0);
                    } else {
                        col = new java.awt.Color(128, 0, 0);
                    }
                }
            }


            if (end > start) {
                //commented
                /*
                ClothoHighlightData highlighter;
                
                orfIndex[0][0] = start;
                orfIndex[1][0] = end;
                highlighter = new ClothoHighlightData(name + " " + start + " to " + end);
                highlighter.setHighlightData(orfIndex, col, name, seq.substring(start, end));
                
                highlighterArray = new ClothoHighlightData[1];
                highlighterArray[0] = highlighter;
                 *
                 */
            } else {
                //commented
                /*
                int [][] orfIndex2 = new int[2][1];
                String orfSeq = seq.substring(start, len) + seq.substring(0, end);
                ClothoHighlightData highlighterEnd;
                ClothoHighlightData highlighterStart;
                
                orfIndex[0][0] = start;
                orfIndex[1][0] = len;
                highlighterEnd = new ClothoHighlightData(name + " " + start + " to " + end);
                highlighterEnd.setHighlightData(orfIndex, col, name, orfSeq);
                
                orfIndex2[0][0] = 0;
                orfIndex2[1][0] = end;
                highlighterStart = new ClothoHighlightData(name + " " + start + " to " + end);
                highlighterStart.setHighlightData(orfIndex2, col, name, orfSeq);
                
                highlighterArray = new ClothoHighlightData[2];
                highlighterArray[0] = highlighterEnd;
                highlighterArray[1] = highlighterStart;
                 *
                 */
            }

            _h = _sequenceview.get_TextArea().getHighlighter();
            javax.swing.text.Highlighter.Highlight[] highlights = _h.getHighlights();
            for (int i = 0; i < highlights.length; i++) {
                javax.swing.text.Highlighter.Highlight u = highlights[i];
                //commented
                /*
                if (u.getPainter() instanceof ClothoHighlightPainter_Underline) {
                _h.removeHighlight(u);
                }
                 *
                 */
            }

            //commented
            /*
            for (int i = 0; i < highlighterArray.length; i++) {
            newUnderliner(_sequenceview.get_TextArea(), highlighterArray[i].color);
            underlineText(highlighterArray[i].search);
            } 
             *
             */

        } else {
            System.out.println("Specified Open Reading Frame does not exist");
        }
    }

    /**
     * Creates a new Sequence View window, returning the core address of the
     * newly created SequenceViewConnection
     */
    public Integer createNewWindow() {
        SequenceView seqView = new SequenceView("SequenceView", "SequenceView", _manager, _manager.getSequenceViewArray().size());
        //seqView.activate();
        _manager.add(seqView);
        _manager.setMainSequenceView(seqView);
        seqView.run();
        seqView.openWindow();
        _saved = true; //empty windows do not need to be saved
        return seqView.getIndex();
    }
        /**
     * Creates a new Sequence View tab, returning the core address of the
     * newly created SequenceViewConnection
     */
    public Integer createNewTab() {
        SequenceView seqView = new SequenceView("SequenceView", "SequenceView", _manager, _manager.getSequenceViewArray().size());
        //seqView.activate();
        _manager.add(seqView);
        _manager.setMainSequenceView(seqView);
        seqView.run();
        seqView.openTab();
        _saved = true; //empty windows do not need to be saved
        return seqView.getIndex();
    }

    /**
     * Opens the SequenceViewPartPackager window
     */
    public void createPart() {
        new SequenceViewPartExport(this, _sequenceview.get_TextArea());
    }

    /**
     * Displays all the Open Reading Frames in the sequence
     */
    public void displayORFs() {
        checkORFs();
        //todo, consider adding evan's visualizer
    }

    /**
     * Export the sequence information as determined by the tool menu settings
     */
    @Deprecated
    public void exportSeq() {
        String sequence = "";

        if (_sequenceviewtools.wholeSeqSel()) {
            sequence = _sequenceview.get_TextArea().getText();
        } else if (_sequenceviewtools.highlightSeqSel()) {
            sequence = _sequenceview.get_TextArea().getSelectedText();
        } else if (_sequenceviewtools.intervalSeqSel()) {
            int beginIndex, endIndex;
            try {
                beginIndex = Integer.parseInt(_sequenceviewtools.getBeginIndex());
            } catch (NumberFormatException e) {
                ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence Export", "Begin interval must be an integer!");
                db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                endIndex = Integer.parseInt(_sequenceviewtools.getEndIndex());
            } catch (NumberFormatException e) {
                ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence Export", "End interval must be an integer!");
                db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Integer beginIndex = new Integer (svpe.getBeginIndex());
            //Integer endIndex =  new Integer (svpe.getEndIndex());
            if ((endIndex <= beginIndex) || endIndex < 0 || beginIndex < 0 || endIndex > _sequenceview.get_TextArea().getText().length() || beginIndex > _sequenceview.get_TextArea().getText().length()) {
                ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence Export", "Sequence interval is incorrect!\nCheck the ordering, length, and make sure it is positive.");
                db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                sequence = _sequenceview.get_TextArea().getText().substring(beginIndex, endIndex);
            }
        } else {
            ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence Export", "Select what aspect of the sequence should be exported!");
            db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        //System.out.print("Sequence = " + sequence + "\n");

        //now take the sequence and export it
        if (_sequenceviewtools.get_connectionsComboBox().getSelectedIndex() != 0) {
            boolean duplicates = false;
            int intAddr = 0;
            if (_duplicates.contains((String) _sequenceviewtools.get_connectionsComboBox().getSelectedItem())) {
                duplicates = true;
                ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence Export", "This is a description shared by multiple connections.\nUsing the core address provided.");
                db.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);

                String addr = _sequenceviewtools.get_AddressField().getText();
                addr.trim();
                if (addr.equals("")) {
                    ClothoDialogBox db2 = new ClothoDialogBox("Clotho: Sequence Export", "No core address provided!");
                    db2.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //System.out.print("Int: " + addr + "\n");
                try {
                    Integer intObj = Integer.parseInt(addr);
                    intAddr = intObj.intValue();
                } catch (NumberFormatException e) {
                    ClothoDialogBox db3 = new ClothoDialogBox("Clotho: Sequence Export", "Core address must be an integer!");
                    db3.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (intAddr < 0) {
                ClothoDialogBox db4 = new ClothoDialogBox("Clotho: Sequence Export", "Core address must be positive!");
                db4.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            /* FIXME
            ClothomySQLDataObject dataObj = new ClothomySQLDataObject();
            ClothoData d = new ClothoData();
            d.set_operation(ClothoOperationEnum.operationExportData);
            d.set_use_code(ClothoDataUseEnum.exportData);
            d.set_payload(sequence);
            d.set_sender(this.get_description());
            d.set_recipient((String) _sequenceviewtools.get_connectionsComboBox().getSelectedItem());
            dataObj.setPrimaryID((String)_sequenceviewtools.get_connectionsComboBox().getSelectedItem());
            //dataObj.setStatementKey(pi.getmySQLDataObject().getStatementKey());
            d.set_payloadInfo(dataObj);
            if(!duplicates)
            get_hub().get_core().process_data_in_connection(d.get_recipient(),  d);
            else
            get_hub().get_core().process_data_in_connection(d.get_recipient(), intAddr, d);
             * */
        } else {
            ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence Export", "Need to select a view for export!");
            db.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);
            //commented
//               ClothoCore.getCore().log("Need to select a view for export!\n", LogLevel.WARNING);
        }
    }

    /**
     * Finds the next Open Reading Frame in the SequenceView
     */
    public void findNextORF() {
        _painter = new ORFPainter(ORFColor);
        JTextComponent textArea = _sequenceview.get_TextArea();
        _h = textArea.getHighlighter();
        HashMap<Integer, Integer> hm;
        checkORFs();
        _sequenceview.getOutputTextArea().setText("Finding orf");
        hm = _ORFs;
//        if (textArea.getText().length()>10) {
//            try {
//                _h.addHighlight(0, 9, _painter);
//            } catch (BadLocationException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        }
        if (hm != null && !(hm.isEmpty())) {
            ArrayList<Integer> startPositions = new ArrayList();
            if (textArea.getSelectedText() != null && currentORFStart == 0) {
                currentORFStart = textArea.getSelectionStart();
            } else {
                currentORFStart = textArea.getCaretPosition();
            }
            startPositions.addAll(hm.keySet());
            java.util.Collections.sort(startPositions);
            if (textArea.getCaretPosition() > startPositions.get(startPositions.size() - 1)) {
                textArea.setCaretPosition(startPositions.get(0) - 1);
                currentORFStart = startPositions.get(0) - 1;
            }
            if (startPositions.get(0) != 0) {
                currentORFStart++;
            }
            for (int i = 0; i < startPositions.size(); i++) {
                int num = startPositions.get(i);
                if (currentORFStart <= num) {
                    currentORFStart = num;
                    break;
                }
            }
            if (currentORFStart > startPositions.get(startPositions.size() - 1)) {
                currentORFStart = startPositions.get(0);
            }
            if (hm.containsKey(currentORFStart) && hm.get(currentORFStart) <= textArea.getText().length()) {

                try {

                    if (startPositions.indexOf(currentORFStart) == startPositions.size() - 1) {
                        if (_circular && hm.get(startPositions.get(startPositions.size() - 1)) == textArea.getText().length()) {
                            this.removeORFHighlights();
                            String seq = textArea.getText();
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }
                            if (lastORFHighlightTag != null) {
                                _h.removeHighlight(lastORFHighlightTag);
                            }
                            _sequenceview.getOutputTextArea().setText("Found circular orf");
                            _h.addHighlight(currentORFStart, seq.length(), _painter);
                            _h.addHighlight(0, Math.min(seq.toLowerCase().indexOf("tag"), Math.min(seq.toLowerCase().indexOf("taa"), seq.toLowerCase().indexOf("tga"))) + 3, _painter);
                            currentORFStart = startPositions.get(0); //reset start to first ORF start to allow looping
                            textArea.setCaretPosition(0);
                            currentORFStart++;
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }
                        } else {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }
                            this.removeORFHighlights();
                            _sequenceview.getOutputTextArea().setText("Found last orf");
                            lastORFHighlightTag = _h.addHighlight(currentORFStart, hm.get(currentORFStart), _painter);
                            textArea.setCaretPosition(currentORFStart);
                            currentORFStart++;
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }
                        }
                    } else {
                        //default case
                        Highlight[] highlights = _h.getHighlights();
                        ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                        for (int i = 0; i < highlights.length; i++) {
                            if (highlights[i].getPainter() instanceof FeaturePainter) {
                                shuffledHighlights.add(highlights[i]);
                                _h.removeHighlight(highlights[i]);
                            }
                        }
                        this.removeORFHighlights();
                        _sequenceview.getOutputTextArea().setText("Found orf");
                        lastORFHighlightTag = _h.addHighlight(currentORFStart, hm.get(currentORFStart), _painter);
                        textArea.setCaretPosition(currentORFStart);
                        currentORFStart++;
                        for (Highlight h : shuffledHighlights) {
                            _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                        }
                    }

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Finds the previous Open Reading Frame in the SequenceView
     */
    public void findPrevORF() {
        _sequenceview.getOutputTextArea().setText("Finding orf");
        _painter = new ORFPainter(ORFColor);
        JTextComponent textArea = _sequenceview.get_TextArea();
        if (textArea.getCaretPosition() == 0) {
            textArea.setCaretPosition(textArea.getText().length());
        }
        _h = textArea.getHighlighter();
        HashMap<Integer, Integer> hm;
        checkORFs();
        hm = _ORFs;

        if (hm != null && !(hm.isEmpty())) {
            ArrayList<Integer> startPositions = new ArrayList();
            if (textArea.getSelectedText() != null && currentORFStart == 0) {
                currentORFStart = textArea.getSelectionStart();
            } else {
                currentORFStart = textArea.getCaretPosition();
            }
            startPositions.addAll(hm.keySet());
            java.util.Collections.sort(startPositions);
            if (startPositions.get(startPositions.size() - 1) != textArea.getText().length()) {
                currentORFStart--;
            }
            for (int i = 0; i < startPositions.size(); i++) {
                int num = startPositions.get(startPositions.size() - 1 - i);
                if (currentORFStart >= num) {
                    currentORFStart = num;
                    break;
                }
            }
            if (currentORFStart < startPositions.get(0)) {
                currentORFStart = startPositions.get(startPositions.size() - 1);
            }
            if (hm.containsKey(currentORFStart) && hm.get(currentORFStart) <= textArea.getText().length()) {
                try {
                    if (startPositions.indexOf(currentORFStart) == 0) {
                        if (_circular && hm.get(startPositions.get(startPositions.size() - 1)) == textArea.getText().length()) {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }

                            this.removeORFHighlights();
                            String seq = textArea.getText();
                            _sequenceview.getOutputTextArea().setText("Fournd circular orf");
                            _h.addHighlight(startPositions.get(startPositions.size() - 1), seq.length(), _painter);
                            _h.addHighlight(0, Math.min(seq.toLowerCase().indexOf("tag"), Math.min(seq.toLowerCase().indexOf("taa"), seq.toLowerCase().indexOf("tga"))) + 3, _painter);
                            currentORFStart = startPositions.get(startPositions.size() - 1); //reset start to first ORF start to allow looping
                            textArea.setCaretPosition(currentORFStart);
                            currentORFStart--;
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }

                        } else {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }

                            this.removeORFHighlights();
                            _sequenceview.getOutputTextArea().setText("Found last orf");
                            lastORFHighlightTag = _h.addHighlight(currentORFStart, hm.get(currentORFStart), _painter);
                            textArea.setCaretPosition(currentORFStart);
                            currentORFStart--; //reset start to first ORF start to allow looping
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }

                        }
                    } else {
                        Highlight[] highlights = _h.getHighlights();
                        ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                        for (int i = 0; i < highlights.length; i++) {
                            if (highlights[i].getPainter() instanceof FeaturePainter) {
                                shuffledHighlights.add(highlights[i]);
                                _h.removeHighlight(highlights[i]);
                            }
                        }
                        this.removeORFHighlights();
                        _sequenceview.getOutputTextArea().setText("Found orf");
                        lastORFHighlightTag = _h.addHighlight(currentORFStart, hm.get(currentORFStart), _painter);
                        textArea.setCaretPosition(currentORFStart);
                        currentORFStart--;
                        for (Highlight h : shuffledHighlights) {
                            _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                        }

                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Finds the next Open Reading Frame in the reverse of the SequenceView
     */
    public void findNextRevORF() {
        _sequenceview.getOutputTextArea().setText("Finding reverse orf");

        _painter = new ORFPainter(ORFColor);
        JTextComponent textArea = _sequenceview.get_TextArea();
        _h = textArea.getHighlighter();
        HashMap<Integer, Integer> hm;
        checkRevORFs();
        hm = _revORFs;
        if (hm != null && !(hm.isEmpty())) {
            ArrayList<Integer> endPositions = new ArrayList();

            endPositions.addAll(hm.keySet());
            ArrayList<Integer> startPositions = new ArrayList();
            for (Integer i : hm.keySet()) {
                startPositions.add(hm.get(i));
            }
            java.util.Collections.sort(endPositions);
            if (!startPositions.contains(textArea.getCaretPosition())) {
                currentORFStart = textArea.getCaretPosition();
            }

            for (int i = 0; i < endPositions.size(); i++) {
                int num = endPositions.get(i);
                if (currentORFStart <= num) {
                    currentORFStart = num;
                    break;
                }
            }
            if (currentORFStart > endPositions.get(endPositions.size() - 1)) {
                currentORFStart = endPositions.get(0);
            }
            if (hm.containsKey(currentORFStart) && hm.get(currentORFStart) <= textArea.getText().length()) {
                try {

                    if (endPositions.indexOf(currentORFStart) == endPositions.size() - 1) {
                        if (_circular && currentORFStart == textArea.getText().length()) {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }

                            this.removeORFHighlights();
                            String seq = textArea.getText();
                            _sequenceview.getOutputTextArea().setText("Found circular reverse orf");
                            _h.addHighlight(currentORFStart, seq.length(), _painter);
                            _h.addHighlight(0, Math.min(seq.toLowerCase().indexOf("cta"), Math.min(seq.toLowerCase().indexOf("tta"), seq.toLowerCase().indexOf("tca"))) + 3, _painter);
                            currentORFStart = endPositions.get(0); //reset start to first ORF start to allow looping
                            textArea.setCaretPosition(hm.get(currentORFStart));
                            currentORFStart++;
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }

                        } else {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }

                            this.removeORFHighlights();
                            _sequenceview.getOutputTextArea().setText("Found last reverse orf");
                            lastORFHighlightTag = _h.addHighlight(hm.get(currentORFStart), currentORFStart, _painter);
                            textArea.setCaretPosition(hm.get(currentORFStart));
                            currentORFStart++;
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }

                        }
                    } else {
                        Highlight[] highlights = _h.getHighlights();
                        ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                        for (int i = 0; i < highlights.length; i++) {
                            if (highlights[i].getPainter() instanceof FeaturePainter) {
                                shuffledHighlights.add(highlights[i]);
                                _h.removeHighlight(highlights[i]);
                            }
                        }

                        this.removeORFHighlights();
                        _sequenceview.getOutputTextArea().setText("Found reverse orf");
                        lastORFHighlightTag = _h.addHighlight(hm.get(currentORFStart), currentORFStart, _painter);
                        textArea.setCaretPosition(hm.get(currentORFStart));
                        currentORFStart++;
                        for (Highlight h : shuffledHighlights) {
                            _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                        }

                    }

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Finds the previous Open Reading Frame in the reverse of the SequenceView
     */
    public void findPrevRevORF() {
        _sequenceview.getOutputTextArea().setText("Finding reverse orf");
        _painter = new ORFPainter(ORFColor);
        JTextComponent textArea = _sequenceview.get_TextArea();
        if (textArea.getCaretPosition() == 0) {
            textArea.setCaretPosition(textArea.getText().length());
        }
        _h = textArea.getHighlighter();
        HashMap<Integer, Integer> hm;
        checkRevORFs();
        hm = _revORFs;

        if (hm != null && !(hm.isEmpty())) {
            ArrayList<Integer> endPositions = new ArrayList();
            endPositions.addAll(hm.keySet());
            java.util.Collections.sort(endPositions);
            ArrayList<Integer> startPositions = new ArrayList();
            for (Integer i : hm.keySet()) {
                startPositions.add(hm.get(i));
            }
            java.util.Collections.sort(endPositions);
            if (!startPositions.contains(textArea.getCaretPosition())) {
                currentORFStart = textArea.getCaretPosition();
            }
            for (int i = 0; i < endPositions.size(); i++) {
                int num = endPositions.get(endPositions.size() - 1 - i);
                if (currentORFStart >= num) {
                    currentORFStart = num;
                    break;
                }
            }
            if (currentORFStart < endPositions.get(0)) {
                currentORFStart = endPositions.get(endPositions.size() - 1);
            }
            if (hm.containsKey(currentORFStart) && hm.get(currentORFStart) <= textArea.getText().length()) {
                try {
                    if (endPositions.indexOf(currentORFStart) == 0) {
                        if (_circular && currentORFStart == textArea.getText().length()) {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }

                            this.removeORFHighlights();
                            String seq = textArea.getText();
                            _h.addHighlight(endPositions.get(endPositions.size() - 1), seq.length(), _painter);
                            _h.addHighlight(0, Math.min(seq.toLowerCase().indexOf("cta"), Math.min(seq.toLowerCase().indexOf("tta"), seq.toLowerCase().indexOf("tca"))) + 3, _painter);
                            _sequenceview.getOutputTextArea().setText("Found circular reverse orf");
                            currentORFStart = endPositions.get(endPositions.size() - 1); //reset start to first ORF start to allow looping
                            textArea.setCaretPosition(hm.get(currentORFStart));
                            currentORFStart--;
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }

                        } else {
                            Highlight[] highlights = _h.getHighlights();
                            ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                            for (int i = 0; i < highlights.length; i++) {
                                if (highlights[i].getPainter() instanceof FeaturePainter) {
                                    shuffledHighlights.add(highlights[i]);
                                    _h.removeHighlight(highlights[i]);
                                }
                            }

                            this.removeORFHighlights();
                            lastORFHighlightTag = _h.addHighlight(hm.get(currentORFStart), currentORFStart, _painter);
                            _sequenceview.getOutputTextArea().setText("Found last reverse orf");
                            textArea.setCaretPosition(hm.get(currentORFStart));
                            currentORFStart--; //reset start to first ORF start to allow looping
                            for (Highlight h : shuffledHighlights) {
                                _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                            }

                        }
                    } else {
                        Highlight[] highlights = _h.getHighlights();
                        ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                        for (int i = 0; i < highlights.length; i++) {
                            if (highlights[i].getPainter() instanceof FeaturePainter) {
                                shuffledHighlights.add(highlights[i]);
                                _h.removeHighlight(highlights[i]);
                            }
                        }

                        this.removeORFHighlights();
                        _sequenceview.getOutputTextArea().setText("Found reverse orf");
                        lastORFHighlightTag = _h.addHighlight(hm.get(currentORFStart), currentORFStart, _painter);
                        textArea.setCaretPosition(hm.get(currentORFStart));
                        currentORFStart--;
                        for (Highlight h : shuffledHighlights) {
                            _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                        }

                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gives focus to the sequence view window specified by the address
     * @param addr The integer address of the Sequence View to be focused on
     */
    public void focusOnSeqViewWindow(int addr) {
        SequenceView c = _manager.getSpecificSV(addr);
        if (c != null) {
            if (c.getIsTC()) {
                c.getTCView().requestActive();
            } else {
                c.getSequenceView().setVisible(true);
                c.getSequenceView().requestFocus();
            }
            _manager.setMainSequenceView(c);
        } else {
            System.out.println("No Sequence View found at address " + addr);
        }
    }

    /**
     * Returns _backspaceKeyPressed.
     * @return _backspaceKeyPressed  
     */
    public boolean get_backspaceKeyPressed() {
        return _backspaceKeyPressed;
    }

    /**
     * Returns true if the sequence is checked as circular
     * @return
     */
    public boolean getCircular() {
        return _circular;
    }

    /**
     * Returns true is the sequence view allows degenerate codes
     * @return
     */
    public boolean getDegeneracy() {
        return _allowDegeneracy;
    }

    /**
     * Returns _deleteKeyPressed.
     * @return _deleteKeyPressed
     */
    public boolean get_deleteKeyPressed() {
        return _deleteKeyPressed;
    }

    /**
     * returns _highlightData which is the data structure for highlights
     * @return _highlightData
     */
//    public ArrayList<SingleHighlight> get_highlightData() {
//        return _highlightData;
//    }
//
//    /**
//     * Returns _highlightDataMade.
//     * @return _highlightDataMade
//     */
//    public boolean get_highlightDataMade() {
//        return _highlightDataMade;
//    }
    /**
     * Returns true if the sequence is set to be methylated
     * @return
     */
    public boolean getMethylated() {
        return _methylated;
    }

    /**
     * Calls the similiarly named method in ClothoDNAConnection, reverse
     * complementing the contents of the clipboard
     */
    public void revCompClipboard() {
        //comment   _sequenceUtils.revCompClipboard(_dnaType);
    }

    /**
     * Return the index for this sequence view referring to its location in the Sequence
     * View Manager arraylist
     * @return
     */
    public int getIndex() {
        return _myIndex;
    }

    /**
     * Return the preferences frame for this sequence view
     * @return
     */
//    public SequenceViewPreferences getPreferences() {
//        return _preferencesFrame;
//    }
    public SequenceViewManager getManager() {
        return _manager;
    }

    /**
     * Returns a boolean stating whether or not the sequence has been saved in
     * its current state.
     */
    public boolean getSaved() {
        return _saved;
    }

    public String getSequence() {
        return _sequence.toString();

    }

    /**
     * Returns the SequenceView window associated with this connection
     * @return _sequenceview
     */
    public SequenceViewGUI getSequenceView() {
        return _sequenceview;
    }

    /**
     * Returns the number of the Sequence View, determined by the order it was
     * created in.
     * @return _seqViewNumber
     */
    public int getSequenceViewNumber() {
        return _seqViewNumber;
    }

    /**
     * Invokes DNA Functions Connection to create a Regular Expression out of the
     * input sequence
     * @param sequence
     * @return _regExp - a regular expression
     */
    public String grab_RegExp(String sequence) {
//comment        _regExp = (String) _sequenceUtils.seqToRegExp(_sequenceview.get_TextArea().getText(), _allowDegeneracy);
        return _regExp;
    }

    /**
     * Adds the highlights for user selected areas.
     */
    public void highlightUserSelected() {
        JTextPane textArea = _sequenceview.get_TextArea();
        _h = textArea.getHighlighter();
        int start = textArea.getSelectionStart();
        int end = textArea.getSelectionEnd();

        if (textArea.getSelectedText() != null) {
            textArea.select(0, 0);
            try {
                Highlight[] highlights = _h.getHighlights();
                ArrayList<Highlight> shuffledHighlights = new ArrayList(); //stores highlights that will be temporarily removed
                for (int i = 0; i < highlights.length; i++) {
                    if (highlights[i].getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
                        shuffledHighlights.add(highlights[i]);
                        _h.removeHighlight(highlights[i]);
                    }
                }
                if (magicHighlight && lastUserHighlightTag != null) {
                    if (userHighlightColor.equals(Color.CYAN)) {
                        this.changeUserHighlightColor(Color.GRAY);
                    } else if (userHighlightColor.equals(Color.GRAY)) {
                        this.changeUserHighlightColor(Color.GREEN);
                    } else if (userHighlightColor.equals(Color.GREEN)) {
                        this.changeUserHighlightColor(Color.PINK);
                    } else if (userHighlightColor.equals(Color.PINK)) {
                        this.changeUserHighlightColor(Color.ORANGE);

                    } else if (userHighlightColor.equals(Color.ORANGE)) {
                        this.changeUserHighlightColor(Color.YELLOW);

                    } else if (userHighlightColor.equals(Color.YELLOW)) {
                        this.changeUserHighlightColor(Color.CYAN);
                    } else {
                        this.changeUserHighlightColor(Color.CYAN);
                    }
                }
                lastUserHighlightTag = _h.addHighlight(start, end, new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(userHighlightColor));

                for (Highlight h : shuffledHighlights) {
                    _h.addHighlight(h.getStartOffset(), h.getEndOffset(), h.getPainter());
                }
                textArea.grabFocus();
                textArea.select(start, end);
            } catch (javax.swing.text.BadLocationException ble) {
                ble.printStackTrace();
            }
        }
    }

    /**
     * Loads a sequence from a specified Genbank or FASTA file
     * Genbank features can be imported
     * @param toLoad File to be loaded
     */
    public void loadSequence(File toLoad) {
        String toSeqView = "";
        if (toLoad.exists()) {
            try {
                java.io.BufferedReader inFile = new java.io.BufferedReader(new java.io.FileReader(toLoad));
                String line = inFile.readLine();

                // Reads in a FASTA format file
                if (line.startsWith(">")) {
                    setTitle("Clotho: Sequence View (Address: " + _myIndex + ") " + toLoad.getName());
                    _sequenceview.getOutputTextArea().setText(toLoad.getName() + " loaded");
                    line = line.substring(1, line.length());
                    line = inFile.readLine();
                    while (line != null) {
                        toSeqView = toSeqView + line.trim();
                        line = inFile.readLine();
                    }

//                    refreshHighlightData(3);
                    sequenceChanged();

                    _sequenceview.get_TextArea().setText(toSeqView);
                    _sequenceview.get_TextArea().setCaretPosition(0);
                    _saved = true;
                    _sequence = new NucSeq(_sequenceview.get_TextArea().getText());
                    new SwingWorker() {

                        @Override
                        protected Object doInBackground() throws Exception {
                            Person user = Collector.getCurrentUser();
                            _sequence.removeAnnotations();
                            _sequence.autoAnnotate(user);
                            return null;
                        }
                    }.execute();
                    updateWindowMenus();
                } // Read in a Genbank format file
                else if (line.startsWith("LOCUS")) {
                    ArrayList<String> featureLines = new ArrayList();//holds lines that contain feature information; used to generate new features after sequence is parsed
                    String area = "";
                    String toComments = "";
                    setTitle("Clotho: Sequence View (Address: " + _myIndex + ") " + toLoad.getName());
                    _sequenceview.getOutputTextArea().setText(toLoad.getName() + " loaded");
                    while (line != null) {
                        if (line.startsWith("   ")) {
                            if (area.equals("COMMENT")) {
                                toComments = toComments + line.substring(12, line.length());
                            }
                        }

                        if (line.startsWith("COMMENT")) {
                            String comment = line.substring(12, line.length());
                            area = "COMMENT";
                            // FIXME
                            // Do something with ApE methylation data if present
                            if (comment.startsWith("ApEinfo:methylated")) {
                                String meth = (line.substring(comment.length() - 1, comment.length()));
                            } else {
                                toComments = toComments + comment;
                            }
                        }
                        if (line.startsWith("FEATURES")) {
                            area = "FEATURES";
                            line = inFile.readLine().trim();
                            while (!(line.startsWith("ORIGIN"))) {
                                if (!(line.startsWith("//")) && !(line.startsWith("SOURCE"))) {
                                    featureLines.add(line);
                                }
                                line = inFile.readLine().trim();
                            }

                        }

                        if (line.startsWith("ORIGIN")) {
                            line = inFile.readLine().trim();
                            while (!(line.startsWith("//"))) {
                                ArrayList<String> seq = new ArrayList(Arrays.asList(line.split(" ")));
                                for (int i = 1; i < seq.size(); i++) {
                                    toSeqView = toSeqView + seq.get(i);
                                }
                                line = inFile.readLine().trim();
                            }
                        }

                        line = inFile.readLine();
                    }

//                    refreshHighlightData(3);
                    sequenceChanged();

                    _sequenceview.get_TextArea().setText(toSeqView);
                    _sequenceview.get_TextArea().setCaretPosition(0);
                    _sequenceview.getCommentTextArea().setText(toComments);
                    _sequenceview.getCommentTextArea().setCaretPosition(0);
                    _saved = true;
                    _sequence = new NucSeq(_sequenceview.get_TextArea().getText());
                    new SwingWorker() {

                        @Override
                        protected Object doInBackground() throws Exception {
                            Person user = Collector.getCurrentUser();
                            _sequence.removeAnnotations();
                            _sequence.autoAnnotate(user);
                            return null;
                        }
                    }.execute();
                    int importOption = javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Import features from genbank file?", "Sequence View Tool Message", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (importOption == 0) {
                        new ImportFeaturesWindow("genbank", _sequence.toString(), this, featureLines);
                    }
                    updateWindowMenus();

                } else {
//                    ClothoDialogBox db = new ClothoDialogBox("Clotho: Sequence View", "This does not appear to be a Genbank or FASTA formated file.\n Do you want to proceed?");
//                    if (db.show_optionDialog(javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                    String[] yesNoOpt = {"Yes", "No"};
                    if (javax.swing.JOptionPane.showOptionDialog(new JFrame(), "This does not appear to be a Genbank or FASTA formated file.\n Do you want to proceed?", "Clotho: Sequnce View", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, yesNoOpt, yesNoOpt[1]) == javax.swing.JOptionPane.YES_OPTION) {
                        setTitle("Clotho: Sequence View (Address: " + _myIndex + ") " + toLoad.getName());
                        _sequenceview.getOutputTextArea().setText(toLoad.getName() + " loaded");

                        while (line != null) {
                            toSeqView = toSeqView + line.trim();
                            line = inFile.readLine();
                        }

//                        refreshHighlightData(3);
                        sequenceChanged();

                        _sequenceview.get_TextArea().setText(toSeqView);
                        _sequenceview.get_TextArea().setCaretPosition(0);
                        _saved = true;
                        updateWindowMenus();

                        System.out.println("WARNING: User decided to open a file that is not in FASTA or Genbank format");
                        //commented
                        //ClothoCore.getCore().log("File is not in FASTA or Genbank Format\n", LogLevel.WARNING);
                    } else {
                        System.out.println("ERROR: User decided to cancel operation since the file is not in FASTA or Genbank format");
                        //commented
                        // ClothoCore.getCore().log("File is not in FASTA or Genbank Format\n", LogLevel.ERROR);
                    }
                }

                inFile.close();
            } catch (java.io.IOException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }

            // Sets the openChooser to open up at the location of the last
            // opened file
            _filePath = _fileOpener.getFileChooser().getCurrentDirectory();
        } else {
            System.out.println("File does not exist!");
        }

        sequenceChanged();
        _sequenceview.requestFocus();
    }

    /**
     * generates appropriate windows to import Genbank features
     * @return
     */
    public void importGenbankFeatures() {
        NucSeq temp = _sequence;
        try {
            java.io.BufferedReader inFile = null;
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter genbankFilter = new FileNameExtensionFilter("GenBank File", "gen", "gb", "gbank", "genbank");
            chooser.addChoosableFileFilter(genbankFilter);
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                inFile = new java.io.BufferedReader(new java.io.FileReader(chooser.getSelectedFile()));
                String line = inFile.readLine();
                if (line.startsWith("LOCUS")) {
                    ArrayList<String> featureLines = new ArrayList(); //holds lines that contain feature information; used to generate new features after sequence is parsed
                    String area = "";
                    String toComments = "";
                    String sequence = "";
                    while (line != null) {
                        if (line.startsWith("   ")) {
                            if (area.equals("COMMENT")) {
                                toComments = toComments + line.substring(12, line.length());
                            }
                        }
                        if (line.startsWith("COMMENT")) {
                            String comment = line.substring(12, line.length());
                            area = "COMMENT";
                            // FIXME
                            // Do something with ApE methylation data if present
                            if (comment.startsWith("ApEinfo:methylated")) {
                                String meth = line.substring(comment.length() - 1, comment.length());
                            } else {
                                toComments = toComments + comment;
                            }
                        }
                        if (line.startsWith("FEATURES")) {
                            area = "FEATURES";
                            line = inFile.readLine().trim();
                            while (!(line.startsWith("ORIGIN"))) {
                                if (!(line.startsWith("//")) && !(line.startsWith("SOURCE"))) {
                                    featureLines.add(line);
                                }
                                line = inFile.readLine().trim();
                            }
                        }
                        if (line.startsWith("ORIGIN")) {
                            line = inFile.readLine().trim();
                            while (!(line.startsWith("//"))) {
                                ArrayList<String> seq = new ArrayList(Arrays.asList(line.split(" ")));
                                for (int i = 1; i < seq.size(); i++) {
                                    sequence = sequence + seq.get(i);
                                }
                                line = inFile.readLine().trim();
                            }
                        }
                        line = inFile.readLine();
                    }
                    new ImportFeaturesWindow("genbank", sequence, this, featureLines);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Selected file does not appear to be a genbank file", "Sequence View Import", JOptionPane.ERROR_MESSAGE);
                }
                inFile.close();
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void importApEFeatures() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        if (chooser.getSelectedFile() != null) {
            try {
                java.io.BufferedReader inputFile = new java.io.BufferedReader(new java.io.FileReader(chooser.getSelectedFile()));
                String line = inputFile.readLine();
                ArrayList<String> featureLines = new ArrayList();
                while (line != null) {
                    featureLines.add(line);
                    line = inputFile.readLine();
                }
                if (featureLines.size() > 0) {
                    new ImportFeaturesWindow("ApE", "", this, featureLines);
                } else {
                    JOptionPane.showMessageDialog(null, "File does not appear to be a valid ApE feature file", "Sequence View Import", JOptionPane.ERROR_MESSAGE);
                }
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
        }


    }

    /**
     * Parses an ArrayList of strings for Genbank features
     * @param toParse- an ArrayList of strings, each representing one line extracted from a Genbank file
     * @param collectionLink an ObjLink that represents the collection that the new features will be saved to
     */
    public boolean parseForGenbankFeatures(String sequence, ObjLink collectionLink, ArrayList<String> inputLines) throws Exception {
        Collection coll = Collector.getCollection(collectionLink.uuid);
        int size = inputLines.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (inputLines.get(i).substring(inputLines.get(i).length() - 1).matches("\\d") && !inputLines.get(i).startsWith("/")) {
                    String[] tokens = inputLines.get(i).split("[\\s[\\p{Punct}]]+");
                    String seq = sequence.substring(Integer.parseInt(tokens[tokens.length - 2]) - 1, Integer.parseInt(tokens[tokens.length - 1]) - 1);
                    if (!tokens[0].equalsIgnoreCase("source")) {//source is a feature that is the entire sequence. useless to retrieve this
                        i++;
                        while (!inputLines.get(i).contains("\"")) {
                            i++;
                        }
                        String name = inputLines.get(i);
                        name = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\""));
                        Feature newFeature = Feature.generateFeature(name, seq, Collector.getCurrentUser(), false);
                        if (newFeature != null) {
                            Collector.add(newFeature);
                            coll.addObject(newFeature);
                            newFeature.saveDefault();
                            coll.saveDefault();

                        }
                    }
                }
            }

        }
        javax.swing.JOptionPane.showMessageDialog(null, "Finished importing features!", "Sequence View Import", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public boolean parseForApEFeatures(ObjLink collectionLink, ArrayList<String> inputLines) throws Exception {
        Collection coll = Collector.getCollection(collectionLink.uuid);
        int size = inputLines.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String[] tokens = inputLines.get(i).split("\\t");
                Feature newFeature = Feature.generateFeature(tokens[0], tokens[1], Collector.getCurrentUser(), false);
                if (newFeature != null) {
                    Collector.add(newFeature);
                    coll.addObject(newFeature);
                    newFeature.saveDefault();
                    coll.saveDefault();
                }
            }
        }
        javax.swing.JOptionPane.showMessageDialog(null, "Finished importing features!", "Sequence View Import", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    /**
     * Loads a sequence from a Genbank or FASTA file, using a "Load File"
     * dialogue box
     */
    public void loadSequence() {
        FileNameExtensionFilter fastaFilter = new FileNameExtensionFilter("FASTA File", "fa", "mpfa", "fna", "fsa", "fas", "fasta");
        FileNameExtensionFilter genbankFilter = new FileNameExtensionFilter("GenBank File", "gen", "gb", "gbank", "genbank");
        if (!_fileOpenerInstantiated) {
            _fileOpener = new ClothoOpenChooser("Load Sequence");
            _fileOpener.getFileChooser().addChoosableFileFilter(fastaFilter);
            _fileOpener.getFileChooser().addChoosableFileFilter(genbankFilter);
            _fileOpener.getFileChooser().setFileFilter(_fileOpener.getFileChooser().getAcceptAllFileFilter());
            _fileOpener.getFileChooser().setCurrentDirectory(_filePath);
            _fileOpener.setTitle("Open a Sequence...");
            _fileOpenerInstantiated = true;
        }
        _fileOpener.open_Window();

        if (_fileOpener.fileSelected) {
            loadSequence(_fileOpener.getFile());
            _saved = true; //newly loaded sequences have no changes and thus, do not need to be saved
        }
    }

    /**
     * Moves the origin of a circular sequence
     */
    public void moveOrigin() {
        if (_circular) {
            int newOrigin;
            String sequence = _sequenceview.get_TextArea().getText();
            if (_sequenceview.get_TextArea().getSelectedText() != null) {
                newOrigin = _sequenceview.get_TextArea().getSelectionStart();
            } else {
                newOrigin = _sequenceview.get_TextArea().getCaretPosition();
            }

            sequence = sequence.substring(newOrigin, sequence.length()) + sequence.substring(0, newOrigin);
            Boolean refresh = _annotationsOn;
            this.removeFeatureEnzymeHighlights();
            this.removeUserSelectedHighlights();
            _sequenceview.get_TextArea().setText(sequence);
            if (refresh) {
                this.highlightFeatures();
            }
        } else {
//            ClothoDialogBox db = new ClothoDialogBox("Error", "Sequence is not circular!");
//            db.show_Dialog(javax.swing.JOptionPane.OK_OPTION);
            javax.swing.JOptionPane.showMessageDialog(new JFrame(), "Sequence is not circular", "error", javax.swing.JOptionPane.OK_OPTION);
        }
    }

    public void moveSequenceScrollPane() {
        JScrollPane is = _sequenceview.getIndexScroll();
        JScrollPane ss = _sequenceview.getSeqScroll();

        //ss.getVerticalScrollBar().setValue(is.getVerticalScrollBar().getValue());
        is.getVerticalScrollBar().setValue(ss.getVerticalScrollBar().getValue());
    }

    public void moveIndexScrollPane() {
        JScrollPane is = _sequenceview.getIndexScroll();
        JScrollPane ss = _sequenceview.getSeqScroll();

        is.getVerticalScrollBar().setValue(ss.getVerticalScrollBar().getValue());
    }

    public void moveScrollPane() {
        JScrollPane is = _sequenceview.getIndexScroll();
        JScrollPane ss = _sequenceview.getSeqScroll();
        ss.getVerticalScrollBar().setValue(is.getVerticalScrollBar().getValue());
        is.getVerticalScrollBar().setValue(ss.getVerticalScrollBar().getValue());
    }

    public void newUnderliner(javax.swing.JTextPane sequenceview, Color color) {
        _h = sequenceview.getHighlighter();
        //commented
        // _underlinePainter = new ClothoHighlightPainter_Underline(color);
    }

    /**
     * Opens the Enzyme Library window from within the SequenceView
     */
    public void openEnzymeLibrary() {
        //commented
        // EnzymeLibraryTool elt = (EnzymeLibraryTool) ClothoCore.getCore().getTool(EnzymeLibraryTool.class);
        // elt.display();
    }

    /**
     * Open the tools menu and set the tab to the export pane
     */
    public void openExport() {
        _sequenceviewtools.setVisible(true);
        _sequenceviewtools.get_toolsTabbedPane().setSelectedIndex(3);
    }

    /**
     * Open help
     */
    public void openHelp() {
        BareBonesBrowserLaunch.openURL(Collator.helpURLBase + "/Sequence_View");
        //  _manager.openHelp();
    }

    /**
     * Opens the Features Library window from within the SequenceView
     */
    public void openFeatureLibrary() {
        //commented
        // FeatureLibraryTool flt = (FeatureLibraryTool) ClothoCore.getCore().getTool(FeatureLibraryTool.class);
        // flt.display();
    }

    public void editFeatureLibrary() {
        for (ToolWrapper tw : Collator.getAllTools()) {
            if (tw.getUUID().equals("org.clothocad.tool.spreaditfeatures")) {
                tw.launchTool();
                System.out.println("launching: " + tw.getDisplayName());
                break;

            }
        }
    }

    /**
     * Open the preferences window
     */
    public void openPreferences() {
        BasicPreferences basicPreferencesFrame = new BasicPreferences(this);
        basicPreferencesFrame.setVisible(true);
        //TODO: implement more sophistcated preferences
//        if (_preferencesFrame == null) {
//            _preferencesFrame = new SequenceViewPreferences(this);
//        }
//        _preferencesFrame.setDNA(_dnaType);
//        _preferencesFrame.setMethylated(_methylated);
//        _preferencesFrame.setCircular(_circular);
//        _preferencesFrame.setDegen(_allowDegeneracy);
//        _preferencesFrame.setFilePath(_filePath.toString());
//        _preferencesFrame.setMultiCodons(_multipleStartCodons);
//        _preferencesFrame.setThreeLetterCode(_threeLetterCode);
//        _preferencesFrame.setVisible(true);
    }

    /**
     * Opens the SequenceView frame associated with this connection
     */
    public void openWindow() {
        _sequenceview.setVisible(true);
        _sequenceview.requestFocus();
    }
    /**
     * opens the Sequence View tab associated with this connection
     */
    public void openTab() {
            final JComponent guiContentPane = (JComponent) _sequenceview.getContentPane();
//            JRootPane guiRootPane = _frameView.getRootPane();
            final JMenuBar menu = _sequenceview.getJMenuBar();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    _tcView = new TopComponent();
                    _tcView.setLayout(new BorderLayout());
                    JScrollPane sp = new JScrollPane(guiContentPane);
                    _tcView.add(menu, BorderLayout.NORTH);
                    _tcView.add(sp, BorderLayout.CENTER);
                    _tcView.setName("Sequence View");
                    _tcView.open();
                    _tcView.requestActive();

                }
            });
            _sequenceview.setVisible(false);
            _isTC = true;
            
    }

    //FIXME
    public void packagePartOpen() {
        /*
        SequenceViewPartExport svpe = new SequenceViewPartExport(this);
        
        svpe.getLibraries().removeAllItems();
        svpe.getFields().removeAllItems();
        
        //set the connections to the currently available connections
        ClothoData d = new ClothoData();
        d.set_sender(this.get_description());
        d.set_recipient("Connect to mySQL");
        d.set_operation(ClothoOperationEnum.operationGetConnections);
        d.set_use_code(ClothoDataUseEnum.communicationData);
        
        ClothomySQLDataObject dataObj = new ClothomySQLDataObject();
        dataObj.setMisc(svpe);
        d.set_payloadInfo(dataObj);
        this.get_hub().get_core().process_data_in_connection(d.get_recipient(), d);
        
        //svpe.getLibraries().setSelectedIndex(0);
        
        
        
        //svpe.setVisible(true);
         */
    }

    //FIXME
    public void packageData(SequenceViewPartExport svpe) {
        /*
        if(svpe.getLibraries().getSelectedIndex() != 0)
        {
        ClothoData d = new ClothoData();
        d.set_operation(ClothoOperationEnum.operationPackagePart);
        d.set_use_code(ClothoDataUseEnum.communicationData);
        d.set_sender(this.get_description());
        d.set_recipient("Parts Navigator");
        
        
        
        ClothomySQLDataObject info = new ClothomySQLDataObject();
        info.setName("packagedPart"); //was mySQLConnection
        
        //database name
        info.setDb(_database);
        //table name
        info.setTable(_table);
        
        //the hash key
        info.setStatementKey(svpe.getLibraries().getSelectedItem().toString());
        
        d.set_payloadInfo(info);
        
        ArrayList payload_arrays = new ArrayList(0);
        
        //need the table field names
        ArrayList fieldnames = new ArrayList(0);
        for(int i = 0; i<svpe.getFields().getItemCount(); i++){
        fieldnames.add(svpe.getFields().getItemAt(i).toString());
        //System.out.print("Adding field " + svpe.getFields().getItemAt(i).toString() + "\n");
        }
        payload_arrays.add(fieldnames);
        
        //need the data to add to the table
        //System.out.print("Number of fields " + svpe.getFields().getItemCount() + "\n");
        ArrayList payload_array_data = new ArrayList();
        for (int i = 0; i<fieldnames.size(); i++)
        {
        //this is where you should insert data
        if (i == svpe.getFields().getSelectedIndex()){
        payload_array_data.add(svpe.getDataPane().getText());
        //System.out.print(svpe.getDataPane().getText());
        }
        else{
        payload_array_data.add(" ");
        //System.out.print("nothing");
        }
        }
        payload_arrays.add(payload_array_data);
        
        d.set_payload(payload_arrays);
        this.get_hub().get_core().process_data_in_connection(d.get_recipient(), d);
        svpe.dispose();
        }
        
        else
        {
        ClothoDialogBox db = new ClothoDialogBox("Clotho: Packager", "Select a connection!");
        db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
        }
         */
    }

    public boolean populate_menu() {
        return true;
    }

    /**
     * Uses caret information to display the current location of insertion into
     * the sequence view. It will also set a number of JLabels to not be visible.
     * @param evt
     * @param startText
     * @param startValue
     * @param startIndex
     * @param startLParen
     * @param startRParen
     * @param lengthText
     * @param lengthValue
     * @param lengthIndex
     * @param lengthLParen
     * @param lengthRParen
     * @param endText
     * @param endValue
     * @param endIndex
     * @param endLParen
     * @param endRParen
     * @param insertText
     * @param insertValue
     * @param insertIndex
     * @param insertLParen
     * @param insertRParen
     */
    public void processCaret(CaretEvent evt,
            JLabel startText, JLabel startValue, JLabel startIndex, JLabel startLParen, JLabel startRParen,
            JLabel lengthText, JLabel lengthValue, JLabel lengthIndex, JLabel lengthLParen, JLabel lengthRParen,
            JLabel endText, JLabel endValue, JLabel endIndex, JLabel endLParen, JLabel endRParen,
            JLabel insertText, JLabel insertValue, JLabel insertIndex, JLabel insertLParen, JLabel insertRParen,
            JLabel gcText, JLabel gcValue, JLabel tmText, JLabel tmValue) {


        Integer dot = evt.getDot();
        Integer mark = evt.getMark();
        //System.out.print("Caret update1 " + dot + " " + mark + "\n");
        Integer insert_index;

        /* This means that nothing is highlighted. Highlighted text is
         * handled by a mouse listener.
         */
        //this.get_hub().get_core().send_to_stdout_connection(dot + " " + mark + "\n", ClothoDataUseEnum.stringMessageData);
        if (mark.equals(dot)) {
            insertText.setVisible(true);
            insertValue.setVisible(true);
            insertIndex.setVisible(true);
            insertLParen.setVisible(true);
            insertRParen.setVisible(true);

            startText.setVisible(false);
            startValue.setVisible(false);
            startIndex.setVisible(false);
            startLParen.setVisible(false);
            startRParen.setVisible(false);

            lengthText.setVisible(false);
            lengthValue.setVisible(false);
            lengthIndex.setVisible(false);
            lengthLParen.setVisible(false);
            lengthRParen.setVisible(false);

            endText.setVisible(false);
            endValue.setVisible(false);
            endIndex.setVisible(false);
            endLParen.setVisible(false);
            endRParen.setVisible(false);

            gcText.setVisible(false);
            gcValue.setVisible(false);
            tmText.setVisible(false);
            tmValue.setVisible(false);

            Integer insert_start = new Integer(0);
            insert_start = dot + 1;

            insert_index = (insert_start - 1) % 3;
            insertIndex.setText(insert_index.toString());

            insertValue.setText(insert_start.toString());
            //System.out.print("Caret update2 " + dot + " " + mark + " " + insert_index + "\n");    
            endValue.setText("0");
            startValue.setText("0");
            lengthValue.setText("0");
        }

    }

    /**
     * Provides information about highlighted sequence data in the sequence view. 
     * This also causes various JLabels to become visible or invisible.
     * @param evt
     * @param pane
     * @param startText
     * @param startValue
     * @param startIndex
     * @param startLParen
     * @param startRParen
     * @param lengthText
     * @param lengthValue
     * @param lengthIndex
     * @param lengthLParen
     * @param lengthRParen
     * @param endText
     * @param endValue
     * @param endIndex
     * @param endLParen
     * @param endRParen
     * @param insertText
     * @param insertValue
     * @param insertIndex
     * @param insertLParen
     * @param insertRParen
     */
    public void processMouseHighlight(MouseEvent evt, JTextPane pane,
            JLabel startText, JLabel startValue, JLabel startIndex, JLabel startLParen, JLabel startRParen,
            JLabel lengthText, JLabel lengthValue, JLabel lengthIndex, JLabel lengthLParen, JLabel lengthRParen,
            JLabel endText, JLabel endValue, JLabel endIndex, JLabel endLParen, JLabel endRParen,
            JLabel insertText, JLabel insertValue, JLabel insertIndex, JLabel insertLParen, JLabel insertRParen,
            JLabel gcText, JLabel gcValue, JLabel tmText, JLabel tmValue) {

        Caret caret = pane.getCaret();
        Integer dot = caret.getDot();
        Integer mark = caret.getMark();
        Integer len = new Integer(0);
        Integer sequence_start = new Integer(0);
        Integer sequence_end = new Integer(0);
        Integer start_index;
        Integer end_index;
        Integer length_index;

        //I.e. something is highlighted
        if (!dot.equals(mark)) {
            insertText.setVisible(false);
            insertValue.setVisible(false);
            insertIndex.setVisible(false);
            insertLParen.setVisible(false);
            insertRParen.setVisible(false);

            startText.setVisible(true);
            startValue.setVisible(true);
            startIndex.setVisible(true);
            startLParen.setVisible(true);
            startRParen.setVisible(true);

            lengthText.setVisible(true);
            lengthValue.setVisible(true);
            lengthIndex.setVisible(true);
            lengthLParen.setVisible(true);
            lengthRParen.setVisible(true);

            endText.setVisible(true);
            endValue.setVisible(true);
            endIndex.setVisible(true);
            endLParen.setVisible(true);
            endRParen.setVisible(true);

            gcText.setVisible(true);
            gcValue.setVisible(true);
            tmText.setVisible(true);
            tmValue.setVisible(true);

            if (dot.intValue() > mark.intValue()) {
                sequence_start = mark + 1;
                sequence_end = dot;
                endValue.setText(sequence_end.toString());
                startValue.setText(sequence_start.toString());
                len = dot - mark;
            } else {
                sequence_start = dot + 1;
                sequence_end = mark;
                endValue.setText(sequence_end.toString());
                startValue.setText(sequence_start.toString());
                len = mark - dot;
            }

            length_index = len % 3;
            lengthIndex.setText(length_index.toString());

            start_index = (sequence_start - 1) % 3;
            startIndex.setText(start_index.toString());

            end_index = (sequence_end - 1) % 3;
            endIndex.setText(end_index.toString());

            lengthValue.setText(len.toString());

            String selectedText = _sequenceview.get_TextArea().getSelectedText().toUpperCase();
            int atCount = 0;
            Double gcCount = 0.0;
            for (int i = 0; i < selectedText.length(); i++) {
                if (selectedText.charAt(i) == 'G' || selectedText.charAt(i) == 'C') {
                    gcCount++;
                }
                if (selectedText.charAt(i) == 'A' || selectedText.charAt(i) == 'T') {
                    atCount++;
                }
            }
            Double tm = 0.0;

            if (selectedText.length() > 0) {
                if (selectedText.length() < 15) {
                    tm = 4 * gcCount + 2 * atCount;
                } else {
                    tm = 64.9 + 41 * (gcCount - 16.4) / selectedText.length();
                }
            }
            tm = Math.floor(tm);
            if (tm < 0) {
                _tmString = "< 0 C";
            } else {
                _tmString = tm.toString() + " C";
            }
            gcCount = Math.floor(gcCount / selectedText.length() * 100);
            _gcString = gcCount + " %";
            gcValue.setText(_gcString);

            //comment(Double) _sequenceUtils.meltingTemp(selectedText, _dnaType);


            tmValue.setText(_tmString);
        }

    }

    public void processMouseMoved(java.awt.event.MouseEvent evt, JLabel loc,
            javax.swing.JLabel sequenceName, javax.swing.JLabel sequenceLabel) {
        JTextPane src = (JTextPane) evt.getComponent();
        Position.Bias[] biasRet = new Position.Bias[1];
        int pos = src.getUI().viewToModel(src, evt.getPoint(), biasRet);
//        System.out.println("Annotations: "+_annotationsOn+" RE: "+_REOn+" features: "+_featuresOn);
        //System.out.print("Mouse: " +  point.getX() + " " + point.getY() + "\n");

        Integer location = new Integer(pos) + 1;//location indexing starts at 1, not 0, this is the fix
        //if _mouseoverData is already made, then the mousemoved event should
        // set the loc field as the name of the feature/enzyme.
        if (_annotationsOn == true) {
            _mouseoverString = "";
            if (_annotations.size() > 0) {
                _annotationsArray = _sequence.getAnnotations().toArray();
//            _hasAtLeastOneHighlight = false;
                for (int i = 0; i < _annotationsArray.length; i++) {
                    //if location is between the start/end indices of the current
                    // _mouseoverDataIndividual, then append _mouseoverString
                    //if _REOn is false, then we don't want to display the name of the feature, same for features, which are distinguished by the lack of a "restriction enzyme search tag"
                    if ((location > (Integer) ((Annotation) _annotationsArray[i]).getStart()) && (location < (Integer) ((Annotation) _annotationsArray[i]).getEnd())) {
                        //Searcher is used to prevent identical concatenations of mouseovers
                        // (i.e., so we don't see something like "EcoR1, EcoR1, EcoR1, BamHI")
                        //uncommented
//                        _searcher = new ClothoSearchUtil((String) _highlightData.get(i).get(0), _mouseoverString);
                        _searcher = new ClothoSearchUtil((String) ((Annotation) _annotationsArray[i]).getName(), _mouseoverString);

                        //uncomment
                        if (_REOn && ((Annotation) _annotationsArray[i]).getFeature().getSearchTags().contains("restriction enzyme")) {
                            _mouseoverString = _mouseoverString.concat(((Annotation) _annotationsArray[i]).getName());

                        } else if (_featuresOn && !((Annotation) _annotationsArray[i]).getFeature().getSearchTags().contains("restriction enzyme")) {
                            _mouseoverString = _mouseoverString.concat(((Annotation) _annotationsArray[i]).getName());

                        }

                        if (_searcher.getHitCount() == 0) {
                            if (_annotationsArray.length > 0) {
                                _mouseoverString = _mouseoverString.concat(", ");
                            }

                        }

                    }
                }
                if (_annotationsArray.length > 0) {
                    sequenceLabel.setVisible(true);
                    sequenceName.setText(_mouseoverString);
                } else {
                    sequenceLabel.setVisible(false);
                    sequenceName.setText(" ");
                }
            }

        } else {
            sequenceLabel.setVisible(false);
            sequenceName.setText(" ");
        }
        loc.setText(location.toString());
    }

    /**
     * Connection to the core and consequently to the other connectors.
     * @param d
     */
    //FIXME
    //public void process_data(ClothoData d) {
       /*
    if(d.get_use_code() == ClothoDataUseEnum.triggerData){
    
    // Creates a new Sequence View and returns its address to the
    // connection it was called from
    if (d.get_op() == ClothoOperationEnum.operationNewWindow) {
    int address = createNewWindow();
    
    ClothoData pm = new ClothoData();
    pm.set_payload(address);
    pm.set_sender(get_description());
    pm.set_recipient(d.get_sender());
    pm.set_use_code(ClothoDataUseEnum.communicationData);
    pm.set_operation(ClothoOperationEnum.operationPopulateView);
    
    this.get_hub().get_core().process_data_in_connection(pm.get_recipient(), pm);
    
    }
    
    else {
    openWindow();
    
    //populate the export connections drop down
    setup_export_connections();
    }
    
    }
    
    if(d.get_use_code() == ClothoDataUseEnum.exportData)
    {
    String importdata = (String) d.get_payload();
    ClothoDialogBox db = new ClothoDialogBox("Clotho Part Export:", "Where do you want to place imported data in the Sequence View?\nNOTE: This may affect the current highlighting.");
    String[] options = {"At cursor", "Beginning of sequence", "End of sequence", "Replace selected data", "Replace all data", "Cancel"};
    int chosen = db.show_optionDialog(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, options, options[5]);
    if (chosen == 0) {
    //StringBuffer sb = new StringBuffer(_sequenceview.get_TextArea().getText());
    //sb.insert(_sequenceview.get_TextArea().getCaretPosition(),importdata);
    _sequenceview.get_TextArea().setCaretPosition(_sequenceview.
    get_TextArea().getSelectionStart());
    _sequenceview.get_TextArea().replaceSelection(importdata);
    refreshHighlightData(0);
    refreshHighlightData(1);
    
    }
    else if (chosen == 1) {
    _sequenceview.get_TextArea().setCaretPosition(0);
    _sequenceview.get_TextArea().replaceSelection(importdata);
    refreshHighlightData(0);
    refreshHighlightData(1);
    }
    else if (chosen == 2) {
    _sequenceview.get_TextArea().setCaretPosition(_sequenceview.
    get_TextArea().getText().length());
    _sequenceview.get_TextArea().replaceSelection(importdata);
    refreshHighlightData(0);
    refreshHighlightData(1);
    }
    else if (chosen == 3) {
    _sequenceview.get_TextArea().replaceSelection(importdata);
    refreshHighlightData(0);
    refreshHighlightData(1);
    }
    else if (chosen == 4) {
    refreshHighlightData(2);
    _sequenceview.get_TextArea().setText(importdata);
    }
    
    }
    
    else if (d.get_op() == ClothoOperationEnum.operationClearHighlight) {
    resetHighlight(_sequenceview.get_TextArea());
    _highlightData = new ArrayList();
    }
    
    
    else if(d.get_op() == ClothoOperationEnum.operationGetConnections)
    {
    ClothomySQLDataObject dataObj = (ClothomySQLDataObject)d.get_payloadInfo();
    SequenceViewPartExport svpe = (SequenceViewPartExport) dataObj.getMisc();
    svpe.getLibraries().removeAllItems();
    //svpe.getLibraries().addItem("Select Library Connection...");
    ComboBoxModel connection = (ComboBoxModel) d.get_payload();
    for (int i = 0; i < connection.getSize(); i++)
    {
    svpe.getLibraries().addItem((String) connection.getElementAt(i));
    }
    if(connection.getSize() > 1)
    
    {
    svpe.setVisibleFlag(true);
    svpe.setVisible(true);
    }
    else
    {
    ClothoDialogBox db = new ClothoDialogBox("Clotho: Packager", "Need to have at least one created library connection!");
    db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }
    
    else if(d.get_op() == ClothoOperationEnum.operationGetFields)
    {
    ClothomySQLDataObject info = (ClothomySQLDataObject) d.get_payloadInfo();
    SequenceViewPartExport svpe = (SequenceViewPartExport) info.getMisc();
    _database = info.getDb();
    _table = info.getTable();
    svpe.getFields().removeAllItems();
    
    
    
    ArrayList fields = (ArrayList) d.get_payload();
    for (int i = 0; i < fields.size(); i++)
    {
    svpe.getFields().addItem((String) fields.get(i));
    }
    }
    
    else if (d.get_op() == ClothoOperationEnum.operationGetHashRef) {
    _PoBoLHashRef = (java.util.Hashtable) d.get_payload();
    }
    
    else if (d.get_op() == ClothoOperationEnum.operationDispose) {
    _sequenceview.dispose();
    }
    }
    
    if (d.get_use_code() == ClothoDataUseEnum.stringSeqViewData) {
    String outputText = (String) d.get_payload();
    clearOutputWindow();
    writeToOutput(outputText);
    }
    
    }
     */
    @Deprecated
    /*
     * no plugin support implemented
     */
    public void callPlugIns() {
//        if (_plugIns == null) {
//            JOptionPane.showMessageDialog(null, "PlugIns null!!", "PlugIn error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (_plugIns.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "No plugIns found.", "No plugIns", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//        String analyzerName = (String) JOptionPane.showInputDialog(null,
//                "Please choose a plugIn from the list below...",
//                "Choose a plugIn",
//                JOptionPane.PLAIN_MESSAGE,
//                null,
//                _plugIns.keySet().toArray(),
//                _plugIns.keySet().toArray()[0]);
//        if (analyzerName != null) {
//            SequenceViewPlugInInterface analyzerObj = _plugIns.get(analyzerName);
//            //Provides analyzer with this connection
//            analyzerObj.setSequenceViewConnection(this);
//
//            //Analyzes Sequence
//            String sequence;
//            if (_sequenceview.get_TextArea().getSelectedText() != null) {
//                sequence = _sequenceview.get_TextArea().getSelectedText();
//            } else {
//                sequence = _sequenceview.get_TextArea().getText();
//            }
//            String result = analyzerObj.analyzeSequence(sequence);
//            //Outputs analysis
//            this._sequenceview.getOutputTextArea().append(result + "\n");
//        }
    }

    /**
     * Process actions from the sequence tool window
     * @param evt
     * @param s
     */
    void processSearchToolAction(ActionEvent evt, String s) {


        String selectedText = _sequenceview.get_TextArea().getSelectedText();


        //SET ClothoOperationEnum here for each case....
        if (s.equalsIgnoreCase("findNextButton")) {
            String findFieldText = _findField.getText();
            //If rev-comp checked, send rev-comp of ReplaceFindField to 
            // SearchHelper.
            if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
                _revCompSequence = revComp_String(findFieldText);

                findFieldText = _revCompSequence;
            }

            //If "find both" is checked, send both rev-comp and orig to SearchHelper.
            if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
                _revCompSequence = revComp_String(findFieldText);

                findFieldText = findFieldText.concat("|" + _revCompSequence);

            }

            //if the sequence contains degeneracies or the sequenceview contains 
            //  degeneracies then turn findText into a regular expression
            if (_sequenceview.get_TextArea().getText().matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")
                    || findFieldText.matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")) {
                findFieldText = grab_RegExp(findFieldText);
            }

            // it is possible to make this more efficient by instantiating 
            // ClothoSearchHelper only when the findField or sequenceview are 
            // changed. For later.
            //uncommented
            _searcher = new ClothoSearchUtil(findFieldText,
                    _sequenceview.get_TextArea().getText(), _caseSensitiveOption);
            _search = _searcher.getSearch();
            _hitCount = _searcher.getHitCount();

            if (_hitCount < 1) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Message", "No hits!");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.INFORMATION_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "No hits!", "Sequence View: Tools Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                _sequenceview.get_TextArea().setCaretPosition(0);
                _selectedHit = 0;
                return;
            }

            _selectedHit += 1;
            if (_selectedHit > _hitCount - 1) {
                _selectedHit = 0;
            }
            selectHit(_sequenceview.get_TextArea());
        } else if (s.equalsIgnoreCase("findPrevButton")) {
            String findFieldText = _findField.getText();
            //If rev-comp checked, send rev-comp of ReplaceFindField to 
            // SearchHelper.
            if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
                //comment   _revCompSequence = revComp_String(findFieldText, _dnaType);
                NucSeq ns = new NucSeq(findFieldText);
                ns = new NucSeq(ns.revComp());
                _revCompSequence = ns.toString();
                findFieldText = _revCompSequence;
            }

            //If both checked, send both rev-comp and orig to SearchHelper.
            if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
                _revCompSequence = revComp_String(findFieldText);
                findFieldText = findFieldText.concat("|" + _revCompSequence);
            }

            //if the sequence contains degeneracies or the sequenceview contains 
            //  degeneracies then turn findText into a regular expression
            if (_sequenceview.get_TextArea().getText().matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")
                    || findFieldText.matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")) {
                findFieldText = grab_RegExp(findFieldText);
            }

            // it is possible to make this more efficient by instantiating 
            // ClothoSearchHelper only when the findField or sequenceview are 
            // changed. For later.
            //uncommented
            _searcher = new ClothoSearchUtil(findFieldText,
                    _sequenceview.get_TextArea().getText(), _caseSensitiveOption);
            _search = _searcher.getSearch();
            _hitCount = _searcher.getHitCount();

            if (_hitCount < 1) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Message", "No hits!");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.INFORMATION_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "No hits!", "Sequence View: Tools Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                _sequenceview.get_TextArea().setCaretPosition(0);
                _selectedHit = 0;
                return;
            }

            _selectedHit -= 1;
            if (_selectedHit < 0) {
                _selectedHit = _hitCount - 1;
            }
            selectHit(_sequenceview.get_TextArea());
        } else if (s.equalsIgnoreCase("Go")) {
            //The following block tests if both fields are integers.
            try {
                Integer.parseInt(_goField.getText());
            } catch (NumberFormatException e) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Error", "Field is not an integer.");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "Field is not an integer.", "Sequence View Tools Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                _sequenceviewtools.toFront();
                return;
            }
            //The following block tests for out-of-bounds errors.
            if (_sequenceview.get_TextArea().getText().length() < Integer.parseInt(_goField.getText()) | Integer.parseInt(_goField.getText()) < 0) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Error", "A number greater than sequence length "
//                        + "or a negative\nnumber was entered into Go field.");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "A number greater than the sequence length or a negative\nnumber was intered into Go field", "Sequence View Tools Error", javax.swing.JOptionPane.ERROR_MESSAGE);

                _sequenceviewtools.toFront();
                return;
            }
            _sequenceview.toFront();
            _sequenceview.get_TextArea().setCaretPosition(Integer.parseInt(
                    _goField.getText()));
        } else if (s.equalsIgnoreCase("Select")) {
            //The following block tests if both fields are integers.
            try {
                Integer.parseInt(_selectFromField.getText());
                Integer.parseInt(_selectToField.getText());
            } catch (NumberFormatException e) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Error", "One of the fields is not an integer.");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "One of the fields is not an integer", "Sequence View Tools Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                _sequenceviewtools.toFront();
                return;
            }
            if ( //any of the fields are out of bounds:
                    Integer.parseInt(_selectFromField.getText()) < 0
                    | Integer.parseInt(_selectFromField.getText())
                    > _sequenceview.get_TextArea().getText().length()
                    | Integer.parseInt(_selectFromField.getText())
                    > Integer.parseInt(_selectToField.getText())
                    | Integer.parseInt(_selectToField.getText()) < 0
                    | Integer.parseInt(_selectToField.getText())
                    > _sequenceview.get_TextArea().getText().length()) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Error", "Cannot select because one of the "
//                        + "following happened:\n"
//                        + "1. From and/or To fields are negative,\n"
//                        + "2. From and/or To fields are over the sequence length,\n"
//                        + "3. From Field is greater than To Field.");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "Cannot select because one of the "
                        + "following happened:\n"
                        + "1. From and/or To fields are negative,\n"
                        + "2. From and/or To fields are over the sequence length,\n"
                        + "3. From Field is greater than To Field.", "Sequence View Tools Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            _sequenceview.get_TextArea().grabFocus();
            _sequenceview.get_TextArea().select(Integer.parseInt(_selectFromField.getText()), Integer.parseInt(_selectToField.getText()));
            _sequenceviewtools.toFront();
        } else if (s.equalsIgnoreCase("SelectAll")) {
            _sequenceview.get_TextArea().grabFocus();
            _sequenceview.get_TextArea().selectAll();
            _sequenceviewtools.toFront();
        } else if (s.equalsIgnoreCase("replaceNextButton")) {
            String replaceFindFieldText = _replaceFindField.getText();
            //If rev-comp checked, send rev-comp of ReplaceFindField to 
            // SearchHelper.
            if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
                //comment   _revCompSequence = revComp_String(replaceFindFieldText, _dnaType);
                NucSeq ns = new NucSeq(replaceFindFieldText);
                ns = new NucSeq(ns.revComp());
                _revCompSequence = ns.toString();
                replaceFindFieldText = _revCompSequence;
            }

            //If both checked, send both rev-comp and orig to SearchHelper.
            if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
                _revCompSequence = revComp_String(replaceFindFieldText);

                replaceFindFieldText = replaceFindFieldText.concat("|" + _revCompSequence);
            }

            //if the sequence contains degeneracies or the sequenceview contains 
            //  degeneracies then turn findText into a regular expression
            if (_sequenceview.get_TextArea().getText().matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")
                    || replaceFindFieldText.matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")) {
                replaceFindFieldText = grab_RegExp(replaceFindFieldText);
            }

            // it is possible to make this more efficient by instantiating 
            // ClothoSearchHelper only when the findField or sequenceview are 
            // changed. For later.
            //uncommented
            _searcher = new ClothoSearchUtil(replaceFindFieldText,
                    _sequenceview.get_TextArea().getText(), _caseSensitiveOption);
            _search = _searcher.getSearch();
            _hitCount = _searcher.getHitCount();

            _selectedHit += 1;
            if (_selectedHit > _hitCount - 1) {
                _selectedHit = 0;
            }
            selectHit(_sequenceview.get_TextArea());
        } else if (s.equalsIgnoreCase("replacePrevButton")) {
            String replaceFindFieldText = _replaceFindField.getText();
            //If rev-comp checked, send rev-comp of ReplaceFindField to 
            // SearchHelper.
            if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
                _revCompSequence = revComp_String(replaceFindFieldText);

                replaceFindFieldText = _revCompSequence;
            }

            //If both checked, send both rev-comp and orig to SearchHelper.
            if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
                //comment     _revCompSequence = revComp_String(replaceFindFieldText, _dnaType);
                NucSeq ns = new NucSeq(replaceFindFieldText);
                ns = new NucSeq(ns.revComp());
                _revCompSequence = ns.toString();
                replaceFindFieldText = replaceFindFieldText.concat("|" + _revCompSequence);
            }

            //if the sequence contains degeneracies or the sequenceview contains 
            //  degeneracies then turn findText into a regular expression
            if (_sequenceview.get_TextArea().getText().matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")
                    || replaceFindFieldText.matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")) {
                replaceFindFieldText = grab_RegExp(replaceFindFieldText);
            }

            // it is possible to make this more efficient by instantiating 
            // ClothoSearchHelper only when the findField or sequenceview are 
            // changed. For later.
            //uncommented
            _searcher = new ClothoSearchUtil(replaceFindFieldText,
                    _sequenceview.get_TextArea().getText(), _caseSensitiveOption);
            _search = _searcher.getSearch();
            _hitCount = _searcher.getHitCount();
            if (_hitCount < 1) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Message", "No hits!");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.INFORMATION_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "No hits!", "Sequence View: Tools Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                _selectedHit = 0;
                return;
            }
            _selectedHit -= 1;
            if (_selectedHit < 0) {
                _selectedHit = _hitCount - 1;
            }
            selectHit(_sequenceview.get_TextArea());
        } else if (s.equalsIgnoreCase("replaceButton")) {
            String replaceFindFieldText = _sequenceviewtools.get_ReplaceFindField().getText();

            //DUPLICATED with setSearch
            //If rev-comp checked, send rev-comp of ReplaceFindField to 
            // SearchHelper.
            if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
                _revCompSequence = revComp_String(replaceFindFieldText);

                replaceFindFieldText = _revCompSequence;
            }

            //If both checked, send both rev-comp and orig to SearchHelper.
            if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
                _revCompSequence = revComp_String(replaceFindFieldText);

                replaceFindFieldText = replaceFindFieldText.concat("|" + _revCompSequence);
            }

            //if the sequence contains degeneracies or the sequenceview contains 
            //  degeneracies then turn findText into a regular expression
            if (_sequenceview.get_TextArea().getText().matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")
                    || replaceFindFieldText.matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")) {
                replaceFindFieldText = grab_RegExp(replaceFindFieldText);
            }

            //run a new search, saved in _searcher
            //uncommented
            _searcher = new ClothoSearchUtil(replaceFindFieldText,
                    _sequenceview.get_TextArea().getText(), _caseSensitiveOption);

            //the _search & _hitcount is kept for later calls to this function, if not go into these special cases
            //however this is dangerous because after the replacement the indexes inside _search must be changed
            //current +(different in length)
            //only create new _search array in completely first time search
            if (_replaceFindField == null | _replaceField == null) {
                //commented  //****************what to do with log?? ********************
                //  ClothoCore.getCore().log("1st search! \n", LogLevel.MESSAGE);
                //uncomment
                _search = _searcher.getSearch();
                //uncomment
                _hitCount = _searcher.getHitCount();
                _selectedHit = 0;
                selectHit(_sequenceview.get_TextArea());
                _sequenceviewtools.store_ToolsFields();
                return;
            }

            //new search, there is new string in either of the search box
            //Also create new _search & hitCount
            if ((!_replaceFindField.getText().matches(_sequenceviewtools.get_ReplaceFindField().getText()))
                    | (!_replaceField.getText().matches(_sequenceviewtools.get_ReplaceField().getText()))
                    | (_search == null)) {
                //commented
                //               ClothoCore.getCore().log("New search!\n", LogLevel.MESSAGE);
                //uncomment
                _search = _searcher.getSearch();
                //uncomment
                _hitCount = _searcher.getHitCount();
                _selectedHit = 0;
                selectHit(_sequenceview.get_TextArea());
                _sequenceviewtools.store_ToolsFields();
                return;
            }
            //end of hits, trigger condition for new search since since the _search & _hitCount is not valid anymore
            if (_selectedHit > _hitCount - 1) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Message", "No more hits!");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.INFORMATION_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "No more hits!", "Sequence View: Tools Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);


                _search = null;
                _sequenceview.get_TextArea().setCaretPosition(0);
                return;
            }

            //for the case that the search had resulted in no hits
            //uncomment

            if (_searcher.getHitCount() < 1) {
//                ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                        + "Tools Message", "No Hits!");
//                toolsDialog.show_Dialog(javax.swing.JOptionPane.INFORMATION_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "No hits!", "Sequence View: Tools Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                _sequenceviewtools.toFront();
                return;
            }

            //*/
            String replaceFieldText = _sequenceviewtools.get_ReplaceField().getText();

            if (_sequenceviewtools.get_PasteRevCompCheckbox().isSelected()) {
                _revCompSequence = revComp_String(replaceFieldText);

                replaceFieldText = _revCompSequence;
            }
            //"select" on the text area, the old text segment, with the position from the _search at index _selectedHit++
            selectHit(_sequenceview.get_TextArea());
            //replace the pre selected text with the new text,
            _sequenceview.get_TextArea().replaceSelection(replaceFieldText);
            //move the all the position up because of the change in length
            //seems that only search one time & use this for further replace
            int _slack = _sequenceviewtools.get_ReplaceField().getText().length()
                    - _sequenceviewtools.get_ReplaceFindField().getText().length();

            for (int j = _selectedHit + 1; j < _search[0].length; j++) {  //DANGEROUS: the new length only affect the indexes of the matches after the replacement
                _search[0][j] += _slack;
                _search[1][j] += _slack;
            }
            _selectedHit++;

            //for the case where the selection has past the number of hits
            if (_selectedHit > _hitCount - 1) {
                return;
            }
            selectHit(_sequenceview.get_TextArea());
        } else if (s.equalsIgnoreCase("replaceAllButton")) {
            String replaceFindFieldText = _sequenceviewtools.get_ReplaceFindField().getText();
            //If rev-comp checked, send rev-comp of ReplaceFindField to 
            // SearchHelper.
            if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
                _revCompSequence = revComp_String(replaceFindFieldText);

                replaceFindFieldText = _revCompSequence;
            }

            //If both checked, send both rev-comp and orig to SearchHelper.
            if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
                _revCompSequence = revComp_String(replaceFindFieldText);
                replaceFindFieldText = replaceFindFieldText.concat("|" + _revCompSequence);
            }

            if (replaceFindFieldText.matches("[a-zA-Z]*?[BDHKMNRSVWY][a-zA-Z]*?")) {
                replaceFindFieldText = grab_RegExp(replaceFindFieldText);
            }
            //uncommented
            _searcher = new ClothoSearchUtil(replaceFindFieldText,
                    _sequenceview.get_TextArea().getText(),
                    _caseSensitiveOption);
            _search = _searcher.getSearch();
            _hitCount = _searcher.getHitCount();


            _selectedHit = 0;

            int _slack = _sequenceviewtools.get_ReplaceField().getText().length()
                    - _sequenceviewtools.get_ReplaceFindField().getText().length();

            for (int i = 0; i < _hitCount; i++) {
                selectHit(_sequenceview.get_TextArea());
                _replaceField = _sequenceviewtools.get_ReplaceField();
                _sequenceview.get_TextArea().replaceSelection(_replaceField.getText());
                for (int j = _selectedHit + 1; j < _search[0].length; j++) {
                    _search[0][j] += _slack;
                    _search[1][j] += _slack;
                }
                _selectedHit++;
            }
            _replaceField.setCaretPosition(0);
        } else if (s.equalsIgnoreCase("Reverse Complement")) {
            if (selectedText != null) {
                String _revCompS = revComp_String(selectedText);
                _sequenceview.getOutputTextArea().setText(_revCompS);
            } else {
                this.askHighlight("Reverse Complement");
            }
        } else if (s.equalsIgnoreCase("Complement")) {
            if (selectedText != null) {
                String _revCompS = revComp_String(selectedText);
                _sequenceview.getOutputTextArea().setText(_revCompS);
            } else {
                this.askHighlight("Complement");
            }
        } else if (s.equalsIgnoreCase("Switch Case")) {
            if (selectedText != null) {
                String sc = "";
                char[] char_selected = selectedText.toCharArray();
                for (int i = 0; i < char_selected.length; i++) {
                    char c = Character.toUpperCase(char_selected[i]);
                    if (c == char_selected[i]) {
                        c = Character.toLowerCase(char_selected[i]);
                    }
                    sc += c;
                }
                //comment_sequenceUtils.switchCase(selectedText);
                replaceTextAtSelection(sc);
            } else {
                this.askHighlight("Switch Case");
            }

        } else if (s.equalsIgnoreCase("Translation")) {
            if (selectedText != null) {
                //comment String trans = "";
                //comment sequenceUtils.translateSeq(selectedText, _threeLetterCode);
                //(trans);
                NucSeq ns = new NucSeq(selectedText);
                _sequenceview.getOutputTextArea().setText(ns.translate(0));
            } else {
                this.askHighlight("Translation");
            }

        } else if (s.equalsIgnoreCase("Reverse Translation")) {
            if (selectedText != null) {
                //String trans = "";
                //comment _sequenceUtils.translateSeq(_sequenceUtils.revComp(selectedText, _dnaType), _threeLetterCode);
                //reverseTranslate(trans);
                NucSeq ns = new NucSeq(selectedText);
                ns = new NucSeq(ns.revComp());
                _sequenceview.getOutputTextArea().setText(ns.translate(0));
            } else {
                this.askHighlight("Reverse Translation");
            }

        }

        //ClothoCore.getCore().log("Processing Tool Action...\n", LogLevel.MESSAGE);
    }

    /**
     * Action performed event for redo
     * @param evt
     */
    public void redoActionPerformed(ActionEvent evt) {
//comment        _redoAction.actionPerformed(evt);
        if (_undo.canRedo()) {
            _undo.redo();
        }
    }

    /**
     * Refeshes highlights according to highlightData. NOTE: this method re-renders
     * the highlighting graphics in the background, so it is possible that YOU MAY
     * LOSE JTextPane INFORMATION SUCH AS CARET POSITION.
     */
    public void refreshHighlights() {
        Boolean re = _REOn;
        Boolean fe = _featuresOn;
        removeFeatureEnzymeHighlights();
        if (fe) {
            highlightFeatures();
        }
        if (re) {
            highlightRestrictionSites();
        }
    }

    /**
     * Refreshes _highlightData, for use when _sequenceview.get_TextArea().
     * getText().setText("X") is invoked.
     * @param refreshType There are two different stages for refresh. Use 0 to "clear" _highlightData, 1 to "revive" _highlightData, and 2 to completely erase _highlightData
     */
//    public void refreshHighlightData(int refreshType) {
//        if (refreshType == 0) {
//            //The following block clears highlightData to prevent any nullPointerExceptions
//            // from DocumentListener
//            _highlightDataClone = (ArrayList) _highlightData.clone();
//            _highlightData.clear();
//            _highlightDataMade = false;
//        }
//        if (refreshType == 1) {
//            //The following block refreshes the highlight now that the text is replaced.
//            _highlightData = (ArrayList) _highlightDataClone.clone();
//            _highlightDataMade = true;
//            if (_needsToRefreshHighlight) {
//                refreshHighlights();
//                _needsToRefreshHighlight = false;
//            }
//            if (_insertIsInsideAHighlight) {
//                ClothoDialogBox dialogBox = new ClothoDialogBox("Insertion "
//                        + "Warning", "You are inserting text inside a feature.\n "
//                        + "Highlights will be retained until cleared.");
//                dialogBox.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);
//                _sequenceview.toFront();
//                _insertIsInsideAHighlight = false;
//            }
//            if (_removalIsAffectingAHighlight) {
//                ClothoDialogBox dialogBox = new ClothoDialogBox("Removal "
//                        + "Warning", "You are removing text from a feature.\n "
//                        + "Highlights will be retained until cleared.");
//                dialogBox.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);
//                _sequenceview.toFront();
//                _removalIsAffectingAHighlight = false;
//            }
//        }
//        if (refreshType == 2) {
//            _highlightData.clear();
//            _highlightDataMade = false;
//            refreshHighlights();
//        }
//    }
    /**
     * Removes only Feature/Enzyme Highlights from the current sequenceview. Used
     * in conjunction with the Remove Features/Enzymes Highlights menu item, under
     * highlighting.
     */
    public void removeFeatureEnzymeHighlights() {
        // Remove any existing underlines.
        _annotationsOn = false;
        _REOn = false;
        _featuresOn = false;
        _h = _sequenceview.get_TextArea().getHighlighter();
        javax.swing.text.Highlighter.Highlight[] highlights = _h.getHighlights();
        for (int i = 0; i < highlights.length; i++) {
            javax.swing.text.Highlighter.Highlight h = highlights[i];
            if (h.getPainter() instanceof FeaturePainter) {
                _h.removeHighlight(h);
            }
        }
        NucSeq.refreshFeatureTable();
    }

    /**
     * Removes only User-Selected Highlights from the current sequenceview. Used
     * in conjunction with the Remove User-Selected Highlights menu item, under
     * highlighting.
     */
    public void removeUserSelectedHighlights() {
        if (_h != null) {
            javax.swing.text.Highlighter.Highlight[] highlights = _h.getHighlights();
            for (int i = 0; i < highlights.length; i++) {
                javax.swing.text.Highlighter.Highlight h = highlights[i];
                if (h.getPainter() instanceof javax.swing.text.DefaultHighlighter.DefaultHighlightPainter && !(h.getPainter() instanceof FeaturePainter)) {
                    _h.removeHighlight(h);
                }
            }
        }
    }

    public void removeORFHighlights() {
        if (_h != null) {
            javax.swing.text.Highlighter.Highlight[] highlights = _h.getHighlights();
            for (int i = 0; i < highlights.length; i++) {
                javax.swing.text.Highlighter.Highlight h = highlights[i];
                if (h.getPainter() instanceof ORFPainter) {
                    _h.removeHighlight(h);
                }
            }
        }
    }

    /**
     * Replaces the text selected in the SequenceView
     * 
     * @param replacement String that replaces the selected text
     */
    public void replaceTextAtSelection(String replacement) {
//        refreshHighlightData(0);
        Boolean refresh = _annotationsOn;
        this.removeFeatureEnzymeHighlights();
        this.removeUserSelectedHighlights();
        String text = _sequenceview.get_TextArea().getText();
        int start = _sequenceview.get_TextArea().getSelectionStart();
        int end = _sequenceview.get_TextArea().getSelectionEnd();
        text = text.substring(0, start) + replacement + text.substring(end, text.length());
        _sequenceview.get_TextArea().setText(text);
        if (refresh) {
            this.highlightFeatures();
        }
        _sequenceview.get_TextArea().setSelectionStart(start);
        _sequenceview.get_TextArea().setSelectionEnd(end);
        //_sequenceview.get_TextArea().setCaretPosition(end);
    }

    /**
     * Resets the SequenceView highlighter, which [WILL LATER BE] used for
     * Features highlighting.
     * @param sequenceview
     */
    public void resetHighlight(javax.swing.JTextPane sequenceview) {
        _annotationsOn = false;
        _REOn = false;
        _featuresOn = false;
        _h = sequenceview.getHighlighter();
        _h.removeAllHighlights();
        //commented
        //  _highlightPainter = new ClothoHighlightPainter(Color.yellow);
    }

    public void reverseTranslate(String t) {
        String revTrans = t;
        String alignedSeq = "";
        String alignedTrans = "";
        String positions = "";
        int start;
        int end;

        SequenceViewOutputWindow transWindow = new SequenceViewOutputWindow(this);
        transWindow.getText().setFont(new java.awt.Font("Courier New", java.awt.Font.BOLD, 20));
        transWindow.getText().setEditable(false);
        transWindow.getText().setLineWrap(false);
        // Finds the corresponding sequence in the sequenceview and
        // reverse complements it
        String sequence = _sequenceview.get_TextArea().getSelectedText();
        if (sequence == null) {
            start = 1;
            end = _sequenceview.get_TextArea().getText().length();
            sequence = _sequenceview.get_TextArea().getText();
        } else {
            start = _sequenceview.get_TextArea().getSelectionStart() + 1;
            end = _sequenceview.get_TextArea().getSelectionEnd();
        }
        transWindow.setTitle(start + " - " + end);

        _revCompSequence = revComp_String(sequence);
        sequence = _revCompSequence;

        // Spaces the sequence out by codons
        while (sequence.length() % 3 != 0) {
            sequence = sequence + " ";
        }
        int rem = sequence.length() % 3;
        sequence = sequence.substring(rem, sequence.length());
        alignedSeq = "";
        for (int i = 0; i + 2 < sequence.length(); i = i + 3) {
            alignedSeq = alignedSeq + sequence.substring(i, i + 3) + " ";
        }
        alignedSeq = alignedSeq.substring(0, alignedSeq.length() - 1);

        // Spaces the translation appropriately
        if (_threeLetterCode) {
            alignedTrans = revTrans;
        } else {
            for (int i = 0; i < revTrans.length(); i++) {
                alignedTrans = alignedTrans + " " + revTrans.substring(i, i + 1) + " -";
            }
            alignedTrans = alignedTrans.substring(0, alignedTrans.length() - 1);
        }

        transWindow.getText().setText(alignedTrans + "\n" + alignedSeq);
        transWindow.setVisible(true);

        // Old method printed sequence to output text area
        _sequenceview.getOutputTextArea().setText(revTrans);

        //this.get_hub().get_core().send_to_stdout_connection(revTrans + "\n", ClothoDataUseEnum.stringSeqViewData);

    }

    public void run() {
        setTitle("Clotho: Sequence View (Address: " + _myIndex + ") New Sequence");
        configureBasePairBoth(_sequenceview.getColLabel());
        updateWindowMenus();
    }

    /**
     * Save the preferences
     */
    public void savePrefernces() {
        //TODO: implement saving preferences
//        _preferences.putBoolean("dnaType", _preferencesFrame.getDNA());
//        _preferences.putBoolean("methylated", _preferencesFrame.getMethylated());
//        _preferences.putBoolean("circular", _preferencesFrame.getCircular());
//        _preferences.putBoolean("degeneracy", _preferencesFrame.getDegen());
//        _preferences.put("filePath", _preferencesFrame.getFilePath());
//        _preferences.putBoolean("multipleStartCodons", _preferencesFrame.getMultiCodons());
//        _preferences.putBoolean("threeLetterCode", _preferencesFrame.getThreeLetterCode());
//
//        load_preferences();
    }

    /**
     * Saves a sequence to a Genbank or FASTA file
     */
    public void saveSequence() {
        ArrayList<String> extensions;
        String toFile = "";
        String sequence = _sequenceview.get_TextArea().getText();
        int charsPerLine;
        int length = sequence.length();

        FileNameExtensionFilter fastaFilter = new FileNameExtensionFilter("FASTA File", "fa", "mpfa", "fna", "fsa", "fas", "fasta");
        FileNameExtensionFilter genbankFilter = new FileNameExtensionFilter("GenBank File", "gen", "gb", "gbank", "genbank");
        File saveFile;

        if (!_fileSaverInstantiated) {
            _fileSaver = new ClothoSaveChooser("Save Sequence");
            _fileSaver.setTitle("Save Sequence As...");
            _fileSaver.getFileChooser().addChoosableFileFilter(fastaFilter);
            _fileSaver.getFileChooser().setFileFilter(genbankFilter);
            _fileSaver.getFileChooser().removeChoosableFileFilter(_fileSaver.getFileChooser().getAcceptAllFileFilter());
            _fileSaverInstantiated = true;
        }
        _fileSaver.getFileChooser().setCurrentDirectory(_filePath);
        _fileSaver.openWindow();

        if (_fileSaver.fileSelected == true) {

            // FASTA File
            if (_fileSaver.getFileFilter() == fastaFilter) {
                charsPerLine = 70;
                toFile = toFile + ">" + _fileSaver.getSaveFile().getName() + "\n";

                for (int i = 0; i < length; i = i + charsPerLine) {
                    if (i + charsPerLine >= length) {
                        toFile = toFile + sequence.substring(i, length);
                    } else {
                        toFile = toFile + sequence.substring(i, i + charsPerLine) + "\n";
                    }
                }

                extensions = new ArrayList(Arrays.asList(fastaFilter.getExtensions()));
                for (int i = 0; i < extensions.size(); i++) {
                    if (!(_fileSaver.getSaveFile().getName().endsWith("." + extensions.get(i)))) {
                        if (i == extensions.size() - 1) {
                            _fileSaver.renameSaveFile(_fileSaver.getSaveFile().getName() + ".fasta");
                        }
                    } else {
                        break;
                    }
                }

                _fileSaver.overwriteSaveFile(toFile);
                _saved = true;
            } // GenBank File
            else if (_fileSaver.getFileFilter() == genbankFilter) {
                charsPerLine = 79;

                // LOCUS Line
                // FIXME
                // Do something with Locus Name instead of leaving it blank?
                String locusName = "ENTER_LOCNAME   ";
                toFile = toFile + "LOCUS       " + locusName + " ";

                for (int i = 0; i < (11 - Integer.valueOf(length).toString().length()); i++) {
                    toFile = toFile + " ";
                }

                toFile = toFile + length + " bp    " + _seqType + "     ";

                if (_circular) {
                    toFile = toFile + "circular ";
                } else {
                    toFile = toFile + "linear   ";
                }
                toFile = toFile + "UNA ";
                java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy");
                java.util.Date date = new java.util.Date();
                toFile = toFile + dateFormat.format(date) + "\n";

                // DEFINITION Line
                toFile = toFile + "DEFINITION  " + "." + "\n";

                // ACCESSION Line
                toFile = toFile + "ACCESSION   " + "" + "\n";

                // VERSION Line
                toFile = toFile + "VERSION     " + "" + "\n";

                // SOURCE Line
                toFile = toFile + "SOURCE      " + "." + "\n";

                // ORGANISM Sub-Line
                toFile = toFile + "  ORGANISM  " + "." + "\n";

                // FIXME
                // Add Comment data appropriately

                // COMMENT Lines
                toFile = toFile + "COMMENT     ";
                // Gets comments from comment box and parses them for storage
                ArrayList<String> comments = new ArrayList(Arrays.asList(_sequenceview.getCommentTextArea().getText().split(" |\\n")));
                int charsLeft;
                int charsPerCommentLine = 67;
                String word;
                charsLeft = charsPerCommentLine;

                for (int i = 0; i < comments.size(); i++) {
                    word = comments.get(i);
                    if ((word.length() + 1) <= charsLeft) {
                        toFile = toFile + word + " ";
                        charsLeft = charsLeft - word.length() - 1;
                    } else if (word.length() > charsPerCommentLine) {
                        toFile = toFile + word.substring(0, charsLeft) + "\n            ";
                        comments.remove(i);
                        comments.add(i, word.substring(charsLeft, word.length()));
                        charsLeft = charsPerCommentLine;
                        i--;
                    } else {
                        toFile = toFile + "\n            " + word + " ";
                        charsLeft = charsPerCommentLine - word.length() - 1;
                    }
                }
                if (!(toFile.endsWith("\n"))) {
                    toFile = toFile + "\n";
                }
                toFile = toFile + "COMMENT     " + "." + "\n";

                // ORIGIN Lines and Sequence Data
                toFile = toFile + "ORIGIN     \n";

                for (int i = 0; i < length; i = i + 60) {
                    for (int j = 0; j < 9 - Integer.valueOf(i + 1).toString().length(); j++) {
                        toFile = toFile + " ";
                    }

                    if ((i + 60) <= length) {
                        toFile = toFile + Integer.toString(i + 1) + " "
                                + sequence.substring(i, i + 10) + " "
                                + sequence.substring(i + 10, i + 20) + " "
                                + sequence.substring(i + 20, i + 30) + " "
                                + sequence.substring(i + 30, i + 40) + " "
                                + sequence.substring(i + 40, i + 50) + " "
                                + sequence.substring(i + 50, i + 60) + "\n";
                    } else {
                        toFile = toFile + Integer.toString(i + 1);
                        int remainder = length - i;
                        for (int j = 0; j < remainder; j++) {
                            if ((j % 10) == 0) {
                                toFile = toFile + " ";
                            }
                            toFile = toFile + sequence.substring(i + j, i + j + 1);
                        }
                        toFile = toFile + "\n";
                    }
                }
                toFile = toFile + "//\n";

                extensions = new ArrayList(Arrays.asList(genbankFilter.getExtensions()));
                for (int i = 0; i < extensions.size(); i++) {
                    if (!(_fileSaver.getSaveFile().getName().endsWith("." + extensions.get(i)))) {
                        if (i == extensions.size() - 1) {
                            _fileSaver.renameSaveFile(_fileSaver.getSaveFile().getName() + ".gb");
                        }
                    } else {
                        break;
                    }
                }

                _fileSaver.overwriteSaveFile(toFile);
                _saved = true;
                setTitle("Clotho: Sequence View (Address: " + _myIndex + ") " + _fileSaver.getSaveFile().getName());
            } else {
                System.out.println("Error Choosing File Type to Save As");
            }

            // Sets the saveChooser to open up at the location of the last
            // saved file
            //Nade to Matt: you had it as _fileOpener.getFileChooser.getCurrentDirectory
            // as opposed to _fileSaver. =P
            _filePath = _fileSaver.getFileChooser().getCurrentDirectory();
        }
        _sequenceview.requestFocus();
    }

    /**
     * Notifies relevant processes that the contents of the sequence view
     * have changed.
     */
    public void sequenceChanged() {
        _ORFsCalculated = false;
        _revORFsCalculated = false;
        _saved = false;

        updateSequenceCount(_sequenceview.getSeqCountLabel());
        configureBasePairBoth(_sequenceview.getColLabel());
        _sequence.changeSeq(_sequenceview.get_TextArea().getText());
    }

    /**
     * Allows search and replace from SequenceViewSearchTools to select a given match.
     * This given match is from the _search array at the position _selectedHit
     * @param sequenceview
     */
    public void selectHit(javax.swing.JTextPane sequenceview) {
        if (_hitCount < 1) {
            //System.out.println("no hits!");
            return;
        }
        sequenceview.requestFocusInWindow();
        sequenceview.select(_search[0][_selectedHit], _search[1][_selectedHit]);
    }

    /**
     * sets _backSpaceKeyPressed.
     * @param backspaceKeyPressed
     */
    public void set_backspaceKeyPressed(boolean backspaceKeyPressed) {
        _backspaceKeyPressed = backspaceKeyPressed;
    }

    /**
     * sets _deleteKeyPressed.
     * @param deleteKeyPressed
     */
    public void set_deleteKeyPressedPressed(boolean deleteKeyPressed) {
        _deleteKeyPressed = deleteKeyPressed;
    }

    /**
     * Sets boolean _insertIsInsideAHighlight used for DocListener
     * @param insertIsInsideAHighlight
     */
    public void set_insertIsInsideAHighlight(boolean insertIsInsideAHighlight) {
        _insertIsInsideAHighlight = insertIsInsideAHighlight;
    }

    /**
     * Sets boolean _removalIsAffectingAHighlight used for DocListener
     * @param removalIsAffectingAHighlight
     */
    public void set_removalIsAffectingAHighlight(boolean removalIsAffectingAHighlight) {
        _removalIsAffectingAHighlight = removalIsAffectingAHighlight;
    }

    /**
     * Determines the initial search indices for the replace function.
     * @param findField
     */
    public void setSearch(javax.swing.JTextField findField) {
        String replaceFindFieldText = _sequenceviewtools.get_ReplaceFindField().getText();
        //If rev-comp checked, send rev-comp of ReplaceFindField to
        // SearchHelper.
        if (_sequenceviewtools.get_RevCompCheckBox().isSelected()) {
            _revCompSequence = revComp_String(replaceFindFieldText);
            replaceFindFieldText = _revCompSequence;

        }

        //If both checked, send both rev-comp and orig to SearchHelper.
        if (_sequenceviewtools.get_BothCheckBox().isSelected()) {
            _revCompSequence = revComp_String(replaceFindFieldText);
            replaceFindFieldText = replaceFindFieldText.concat("|" + _revCompSequence);
        }
        //Search in text area from the BEGINNING, not from previous position
//uncommented
        _searcher = new ClothoSearchUtil(replaceFindFieldText,
                _sequenceview.get_TextArea().getText(),
                _caseSensitiveOption);
        _search = _searcher.getSearch();
        _hitCount = _searcher.getHitCount();
        _selectedHit = 0;
    }

    /**
     *
     * Set the main sequence area to the give string
     * @param newSequence
     */
    public void setSequence(String newSequence) {
        int actionChoice;
        String currentText = _sequenceview.get_TextArea().getText();
        if ((currentText == null) || (currentText.equalsIgnoreCase(""))) { //if currently empty, don't need to ask for replace/append
            actionChoice = 0; //Replace All
        } else {
//            ClothoDialogBox db = new ClothoDialogBox("Sequence View Import", "Incoming sequence information. What should be done?");
            String[] options = {"Replace All", "Replace Selected", "Append", "Cancel"};
//            actionChoice = db.show_optionDialog(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, options, options[0]);
            actionChoice = javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Incoming sequence information. What should be done?", "Sequence View Import", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }
        //check for valid characters
        if (!this.checkValidSequence(newSequence, this.getDegeneracy())) {
//            ClothoDialogBox dbox = new ClothoDialogBox("Sequence View Import", "Imported sequence contains non-standard characters. Continue?");
            String[] choices = {"Continue", "Cancel"};
//            int getChoice = dbox.show_optionDialog(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, choices, choices[1]);
            int getChoice = javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Imported sequence contains non-standard characters. Continue?", "Sequence View Import", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
            if (getChoice == 1) {
                return;
            }
        }

        if (actionChoice == 0) {
            _sequenceview.get_TextArea().setText(newSequence);
        } else if (actionChoice == 1) {
            _sequenceview.get_TextArea().replaceSelection(newSequence);
        } else if (actionChoice == 2) {
            _sequenceview.get_TextArea().setText(_sequenceview.get_TextArea().getText() + newSequence);
        } else if (actionChoice == 3) {
            return;
        }
        _sequenceview.setVisible(true);
    }

    /**
     * Sets this Sequence View to be the main Sequence View.
     */
    public void setToMainSeqView() {
        //System.out.print("Sequence view focus " + get_core_address() + "\n");
        _manager.setMainSequenceView(this);
    }

    /**
     * Used with SequenceViewSearchTools.java JFrame. Collects component data from the
     * JFrame.
     * @param findField
     * @param goField
     * @param selectFromField
     * @param selectToField
     * @param replaceField
     * @param replaceFindField
     */
    public void setToolsFields(JTextField findField, JTextField goField,
            JTextField selectFromField, JTextField selectToField, JTextField replaceField, JTextField replaceFindField) {
        _findField = new JTextField();
        _findField.setText(findField.getText());
        _goField = new JTextField();
        _goField.setText(goField.getText());
        _selectFromField = new JTextField();
        _selectFromField.setText(selectFromField.getText());
        _selectToField = new JTextField();
        _selectToField.setText(selectToField.getText());
        _replaceField = new JTextField();
        _replaceField.setText(replaceField.getText());
        _replaceFindField = new JTextField();
        _replaceFindField.setText(replaceFindField.getText());

    }

    /**
     * Retitles the SequenceView window
     * @param title String containing new tile for the window
     */
    public void setTitle(String title) {
        _sequenceview.setTitle(title);
    }

    /**
     * Make the tools visible.
     */
    public void show_tools() {
        _sequenceviewtools.setVisible(true);
    }

    public boolean support_stdout() {
        return true;
    }

    public void translate(String t) {
        String trans = t;
        String alignedSeq = "";
        String alignedTrans = "";
        int start;
        int end;

        SequenceViewOutputWindow transWindow = new SequenceViewOutputWindow(this);
        transWindow.getText().setFont(new java.awt.Font("Courier New", java.awt.Font.BOLD, 20));
        transWindow.getText().setEditable(false);
        transWindow.getText().setLineWrap(false);
        // Finds the corresponding sequence in the sequenceview
        String sequence = _sequenceview.get_TextArea().getSelectedText();
        if (sequence == null) {
            sequence = _sequenceview.get_TextArea().getText();
            start = 1;
            end = _sequenceview.get_TextArea().getText().length();
        } else {
            start = _sequenceview.get_TextArea().getSelectionStart() + 1;
            end = _sequenceview.get_TextArea().getSelectionEnd();
        }
        transWindow.setTitle("Translation: " + start + " - " + end);

        // Spaces the sequence out by codons
        while (sequence.length() % 3 != 0) {
            sequence = sequence + " ";
        }
        for (int i = 0; i + 2 < sequence.length(); i = i + 3) {
            alignedSeq = alignedSeq + sequence.substring(i, i + 3) + " ";
        }
        alignedSeq = alignedSeq.substring(0, alignedSeq.length() - 1);

        // Spaces the translation appropriately
        if (_threeLetterCode) {
            alignedTrans = trans;
        } else {
            for (int i = 0; i < trans.length(); i++) {
                alignedTrans = alignedTrans + " " + trans.substring(i, i + 1) + " -";
            }
            alignedTrans = alignedTrans.substring(0, alignedTrans.length() - 1);
        }
        transWindow.getText().setText(alignedTrans + "\n" + alignedSeq);
        transWindow.setVisible(true);

        // Old method printed sequence to output text area
        _sequenceview.getOutputTextArea().setText(trans);
        //this.get_hub().get_core().send_to_stdout_connection(trans + "\n", ClothoDataUseEnum.stringSeqViewData);
    }

    public void underlineText(int[][] search) {
        for (int i = 0; i < search[0].length; i++) {
            //commented
/*
            try{
            _h.addHighlight(search[0][i], search[1][i], _underlinePainter);
            }
            
            catch (javax.swing.text.BadLocationException ble) {
            ClothoCore.getCore().log(ble.toString(), LogLevel.ERROR);
            }
             *
             */
        }
    }

    /**
     * Action performed event for undo
     * @param evt
     */
    public void undoActionPerformed(ActionEvent evt) {
//        _undoAction.actionPerformed(evt);
        if (_undo.canUndo()) {
            _undo.undo();
        }
    }

    /**
     * Toggles case sensitive option for search.
     * @param evt
     */
    public void update_caseSensitive(ItemEvent evt) {

        if (evt.getStateChange() == ItemEvent.DESELECTED) {
            //commented
            // ClothoCore.getCore().log("Case Sensitive Deselected\n", LogLevel.MESSAGE);
            _caseSensitiveOption = "";
        } else {
            //commented
            // ClothoCore.getCore().log("Case Sensitive Selected\n", LogLevel.MESSAGE);
            _caseSensitiveOption = "case";
        }
    }

    /**
     * Function to update the state of the circular variable
     */
    public void update_circular() {
        JCheckBox cb = _sequenceview.getCircularBox();
        if (cb.isSelected()) {
            _circular = true;
            _sequence = new NucSeq(_sequence.getSeq(), false, true);
            sequenceChanged();
        } else {
            _circular = false;
        }
        /*if (evt.getStateChange() == ItemEvent.DESELECTED) {
        //commented
        
        //ClothoCore.getCore().log("Circular Deselected\n", LogLevel.MESSAGE);
        _circular = false;
        } else {
        //commented
        // ClothoCore.getCore().log("Circular Selected\n", LogLevel.MESSAGE);
        _circular = true;
        }*/

    }

    /**
     * Function to update the state of the allowDegeneracy variable
     */
    public void update_AllowDegeneracy(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.DESELECTED) {
            //commented
            // ClothoCore.getCore().log("Allow Degeneracy Deselected\n", LogLevel.MESSAGE);
            _allowDegeneracy = false;
        } else {
            //commented
            // ClothoCore.getCore().log("Allow Degeneracy Selected\n", LogLevel.MESSAGE);
            _allowDegeneracy = true;
            if (!_longTimeHighlightWarned) {
//                ClothoDialogBox dialogBox = new ClothoDialogBox("Highlighting Warning",
//                        "While checked, highlighting may take an extended \n"
//                        + "period of time to search for enzymes/features. \n"
//                        + "Uncheck Allow Degeneracy if this is not desired.");
//                dialogBox.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);
                javax.swing.JOptionPane.showMessageDialog(new JFrame(), "While checked, highlighting may take an extended \n"
                        + "period of time to search for enzymes/features. \n"
                        + "Uncheck Allow Degeneracy if this is not desired.", "Highlighting Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
                _longTimeHighlightWarned = true;
            }
        }
    }

    /**
     * Function to update the state of the locked variable
     */
    public void update_lock(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            _locked = true;
        } else {
            _locked = false;
        }
        _sequenceview.get_TextArea().setEditable(!(_locked));
    }

    /**
     * Function to update the state of the methylated variable
     */
    public void update_methylated(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            _methylated = true;
        } else {
            _methylated = false;
        }
    }

    /**
     * Set of actions before sequence view is somehow cleared; if for instance
     * loading a sequence or closing the window.
     */
//    public void updateClearedSequence() {
//        _highlightData.clear();
//        _highlightDataMade = false;
//    }
//    public void updateFields(SequenceViewPartExport svpe) {
    //System.out.print("Update Fields\n");
//        if (svpe.visible()) {
    //System.out.print("Update Fields Visible\n");
    //set the connections to the currently available connections
        /* FIXME ClothoData d = new ClothoData();
    d.set_sender(this.get_description());
    d.set_recipient("Connect to mySQL");
    d.set_operation(ClothoOperationEnum.operationGetFields);
    d.set_use_code(ClothoDataUseEnum.communicationData);
    
    ClothomySQLDataObject dataObj = new ClothomySQLDataObject();
    dataObj.setMisc(svpe);
    
    
    
    ArrayList payload_array = new ArrayList(0);
    //sets the string to use in a hashable
    if(svpe.getLibraries().getItemCount() > 0)
    {
    payload_array.add(svpe.getLibraries().getSelectedItem().toString());
    d.set_payload(payload_array);
    dataObj.setStatementKey(svpe.getLibraries().getSelectedItem().toString());
    d.set_payloadInfo(dataObj);
    
    this.get_hub().get_core().process_data_in_connection(d.get_recipient(), d);
     * */
    //}
//        }
//    }
    /**
     * Updates the JLabel with the number of characters in the sequence view.
     * @param sequenceValue
     */
    public void updateSequenceCount(JLabel sequenceValue) {
        Integer len = new Integer(_sequenceview.get_TextArea().getText().length());
        sequenceValue.setText(len.toString());
    }

    /**
     * Updates the Window Menu in the Sequence View with the appropriate windows
     */
    public void updateWindowMenus() {
        ArrayList<String> windows = new ArrayList();
        for (int i = 0; i < _manager.getSequenceViewArray().size(); i++) {
            SequenceView seq = _manager.getSpecificSV(i);
            windows.add(seq.getSequenceView().getTitle());
        }
        for (int i = 0; i < _manager.getSequenceViewArray().size(); i++) {
            SequenceView seq = _manager.getSpecificSV(i);
            SequenceViewGUI seqView = seq.getSequenceView();
            JMenuItem switchItem = seqView.getWindowMenu().getItem(0); //don't want to clear this item which allows view switching
            seqView.getWindowMenu().removeAll();
            seqView.getWindowMenu().add(switchItem);
            for (int j = 0; j < windows.size(); j++) {
                String name = windows.get(j);
                seqView.getWindowMenu().add(new javax.swing.JMenuItem(name.substring(32, name.length())));
                seqView.getWindowMenu().getItem(j).addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        // Parses the window selected from the name of the menu item selected
                        try {
                            int addrSelected = Integer.parseInt(evt.getActionCommand().split("\\)")[0]);
                            focusOnSeqViewWindow(addrSelected);
                        } catch (NumberFormatException e) {
                            //first item selected, expected exception
                        }
                    }
                });

            }
        }

    }

    /**
     * Consumes enter and tab.
     * @param evt
     */
    public void validateKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER
                || evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            _backspaceKeyPressed = true;
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            _deleteKeyPressed = true;
        }
        //pasting action occurs here
        if ((evt.getKeyCode() == KeyEvent.VK_V) && (evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)) {
            String newSeq = null;
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            try {
                if (t != null && t.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {
                    newSeq = (String) t.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
                    _sequenceview.get_TextArea().replaceSelection(newSeq);
                }
            } catch (java.awt.datatransfer.UnsupportedFlavorException e) {
            } catch (java.io.IOException e) {
            }

            if ((newSeq == null) || (newSeq.equalsIgnoreCase(""))) {
                return;
            }
            if (!this.checkValidSequence(newSeq, this.getDegeneracy())) {
                evt.consume();
            }
            return;
        }
        //copying action occurs here
        if ((evt.getKeyCode() == KeyEvent.VK_C) && (evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)) {
            StringSelection ss = new StringSelection(_sequenceview.get_TextArea().getSelectedText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        }
        //cutting action occurs here
        if ((evt.getKeyCode() == KeyEvent.VK_X) && (evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)) {
            JTextPane jtp = _sequenceview.get_TextArea();
            StringSelection ss = new StringSelection(jtp.getSelectedText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            jtp.replaceSelection("");
        }
    }

    public void validateKeyReleased(KeyEvent evt) {
        if (_needsToRefreshHighlight) {
            refreshHighlights();
            _needsToRefreshHighlight = false;
        }
        if (_insertIsInsideAHighlight) {
//            ClothoDialogBox dialogBox = new ClothoDialogBox("Insertion "
//                    + "Warning", "You are inserting text inside a feature.\n "
//                    + "Highlights will be retained until cleared.");
//            dialogBox.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);
//            _sequenceview.toFront();
            _insertIsInsideAHighlight = false;
        }

        if (_removalIsAffectingAHighlight) {
//            ClothoDialogBox dialogBox = new ClothoDialogBox("Removal "
//                    + "Warning", "You are removing text from a feature.\n "
//                    + "Highlights will be retained until cleared.");
//            dialogBox.show_Dialog(javax.swing.JOptionPane.WARNING_MESSAGE);
//            _sequenceview.toFront();
            _removalIsAffectingAHighlight = false;
        }
    }

    /**
     * Used to validate key strokes. If key is invalid (i.e. not a 
     * A, G, C, T/U, etc.) then the event will be consumed
     * @param evt KeyEvent
     */
    public void validateKeyTyped(KeyEvent evt) {
        char lo;
        char hi;
        if (_dnaType) {
            lo = 't';
            hi = 'T';
        } else {
            lo = 'u';
            hi = 'U';
        }
        if (_allowDegeneracy == false
                && (evt.getKeyChar() != 'a'
                && evt.getKeyChar() != 'A'
                && evt.getKeyChar() != 'g'
                && evt.getKeyChar() != 'G'
                && evt.getKeyChar() != lo
                && evt.getKeyChar() != hi
                && evt.getKeyChar() != 'c'
                && evt.getKeyChar() != 'C')) {
            evt.consume();
        }
        if (_allowDegeneracy == true
                && (evt.getKeyChar() != 'a' && evt.getKeyChar() != 'A'
                && evt.getKeyChar() != 'g' && evt.getKeyChar() != 'G'
                && evt.getKeyChar() != lo && evt.getKeyChar() != hi
                && evt.getKeyChar() != 'c' && evt.getKeyChar() != 'C'
                && evt.getKeyChar() != 'r' && evt.getKeyChar() != 'R'
                && evt.getKeyChar() != 'y' && evt.getKeyChar() != 'Y'
                && evt.getKeyChar() != 'm' && evt.getKeyChar() != 'M'
                && evt.getKeyChar() != 'k' && evt.getKeyChar() != 'K'
                && evt.getKeyChar() != 'w' && evt.getKeyChar() != 'W'
                && evt.getKeyChar() != 's' && evt.getKeyChar() != 'S'
                && evt.getKeyChar() != 'b' && evt.getKeyChar() != 'B'
                && evt.getKeyChar() != 'd' && evt.getKeyChar() != 'D'
                && evt.getKeyChar() != 'h' && evt.getKeyChar() != 'H'
                && evt.getKeyChar() != 'v' && evt.getKeyChar() != 'V'
                && evt.getKeyChar() != 'n' && evt.getKeyChar() != 'N')) {
            evt.consume();
        }

        if (!_keyTypedAtLeastOnce) {
            _keyTypedAtLeastOnce = true;
        }
    }

    public void writeToOutput(String s) {
        _sequenceview.getOutputTextArea().append(s);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private/protected helper methods            ////
    /**
     * Helper for displaying a dialog box asking to highlighting sequence before running a utility
     * @param funcName - the function name in the dialog box
     */
    private void askHighlight(String funcName) {
//        ClothoDialogBox toolsDialog = new ClothoDialogBox("SequenceView "
//                + "Tools Message", "Need to highlight the sequence for " + funcName);
//        toolsDialog.show_Dialog(javax.swing.JOptionPane.INFORMATION_MESSAGE);
        javax.swing.JOptionPane.showMessageDialog(new JFrame(), "Need to highlight the sequence for " + funcName, "Sequence View Tools Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    protected boolean checkValidSequence(String newS, boolean degenerated) {
        String checkRegex;
        if (!this.getDegeneracy()) {
            checkRegex = "[AaCcGgTtUu \n\t]*";
        } else {
            checkRegex = "[aAgGtTuUcCrRyYmMkKwWsSbBdDhHvVnN \n\t]*";
        }
        return newS.matches(checkRegex);

    }

    private void revCompClipBoard_Add(String s) {
        _revComp = new NucSeq(s);
        _revComp = new NucSeq(_revComp.revComp());

    }

    public String revCompFunctions(String Func, String SelectText) {
        if (Func.equals("CUT")) {
            String text = _sequenceview.get_TextArea().getSelectedText();
            text = revComp_String(text);
            StringSelection ss = new StringSelection(text);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            return "";

        }
        if (Func.equalsIgnoreCase("COPY")) {
            String text = _sequenceview.get_TextArea().getSelectedText();
            text = revComp_String(text);
            StringSelection ss = new StringSelection(text);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            return "";
        }
        if (Func.equalsIgnoreCase("PASTE")) {
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            try {
                if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    String text = (String) t.getTransferData(DataFlavor.stringFlavor);
                    text = revComp_String(text);
                    return text;
                }
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
                return "";

            } catch (IOException e) {
                e.printStackTrace();
                return "";

            }
            return "";
        }
        return "";


    }

    private String revComp_String(String s) {
        NucSeq ns = new NucSeq(s);
//        ns = new NucSeq(ns.revComp());
        return (ns.revComp().toString());
    }

    public void highlightRestrictionSites() {
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                Person user = Collector.getCurrentUser();
                _sequence.removeAnnotations();
                _sequence.autoAnnotate(user);
                return null;
            }
        }.execute();
        _annotations = _sequence.getAnnotations();
        _annotationsOn = true;
        _REOn = true;
        _h = _sequenceview.get_TextArea().getHighlighter();

        for (Annotation an : _annotations) {
            if (an.getFeature().getSearchTags().contains("restriction enzyme")) {

                try {
                    _h.addHighlight(an.getStart(), an.getEnd(), new FeaturePainter(an.getColor()));
                } catch (BadLocationException ex) {
                    System.out.println("error highlighting features");
                    Exceptions.printStackTrace(ex);

                }
            }
        }
        NucSeq.refreshFeatureTable();
    }

    public void highlightFeatures() {
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                Person user = Collector.getCurrentUser();
                _sequence.removeAnnotations();
                _sequence.autoAnnotate(user);
                return null;
            }
        }.execute();
        _annotations = _sequence.getAnnotations();
//        this.removeFeatureEnzymeHighlights();
//        this.removeUserSelectedHighlights();
        _annotationsOn = true;
        _featuresOn = true;
        _h = _sequenceview.get_TextArea().getHighlighter();

        for (Annotation an : _annotations) {
            if (!an.getFeature().getSearchTags().contains("restriction enzyme")) {
                try {
                    _h.addHighlight(an.getStart(), an.getEnd(), new FeaturePainter(an.getColor()));
                } catch (BadLocationException ex) {
                    System.out.println("error highlighting features");
                    Exceptions.printStackTrace(ex);

                }
            }
        }
        NucSeq.refreshFeatureTable();

    }

    private class FeaturePainter extends DefaultHighlighter.DefaultHighlightPainter {

        FeaturePainter(Color c) {
            super(c);
        }
    }

    private class ORFPainter extends DefaultHighlighter.DefaultHighlightPainter {

        ORFPainter(Color c) {
            super(c);
        }
    }

    public Color getORFColor() {
        return ORFColor;
    }

    public void changeORFColor(Color c) {
        ORFColor = c;
    }

    public Color getUserSelectColor() {
        return userSelectColor;
    }

    public void changeUserSelectColor(Color c) {
        userSelectColor = c;
        _sequenceview.get_TextArea().setSelectionColor(userSelectColor);
    }

    public Color getUserHighlightColor() {
        return userHighlightColor;
    }

    public void changeUserHighlightColor(Color c) {
        userHighlightColor = c;
    }

    public boolean getMagicHighlight() {
        return magicHighlight;
    }

    public void setMagicHighlight(Boolean b) {
        magicHighlight = b;
    }

    public boolean getIsTC() {
        return _isTC;
    }

    public TopComponent getTCView() {
        return _tcView;
    }
    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////
    private boolean _isTC;
    private TopComponent _tcView;
    private NucSeq _sequence;
    private HashSet<Annotation> _annotations;
    private Object[] _annotationsArray;
    private Object lastORFHighlightTag;
    private int currentORFStart;
    NucSeq _revComp = new NucSeq("");
//    private ArrayList<SingleHighlight> _highlightData;
//    private ArrayList<SingleHighlight> _highlightDataClone;
    private ArrayList<String> _startCodons;
    private boolean _annotationsOn;
    private boolean _REOn;
    private boolean _featuresOn;
    private boolean _allowDegeneracy;
    private boolean _backspaceKeyPressed;
    private boolean _circular;
    private boolean _deleteKeyPressed;
    private boolean _dnaType;
    private boolean _fileOpenerInstantiated;
    private boolean _fileSaverInstantiated;
    private boolean _insertIsInsideAHighlight;
    private boolean _locked;
    private boolean _longTimeHighlightWarned;
    private boolean _methylated;
    private boolean _multipleStartCodons;
    private boolean _needsToRefreshHighlight;
    private boolean _ORFsCalculated;
    private boolean _removalIsAffectingAHighlight;
    private boolean _revORFsCalculated;
    private boolean _saved;
    private boolean _threeLetterCode;
    private ClothoOpenChooser _fileOpener;
    private ClothoSaveChooser _fileSaver;
    private ClothoSearchUtil _searcher;
    private File _filePath;
    private HashMap<Integer, Integer> _ORFs;
    private HashMap<Integer, Integer> _revORFs;
    private HashSet _duplicates;
    private int _hitCount;
    private int[][] _search;
    private int _selectedHit;
    private int _seqViewNumber;
    private javax.swing.text.Highlighter _h;
    private JTextField _findField;
    private JTextField _goField;
    private JTextField _selectFromField;
    private JTextField _selectToField;
    private JTextField _replaceField;
    private JTextField _replaceFindField;
    private SequenceViewGUI _sequenceview;
    private SequenceViewManager _manager;
    private SequenceViewSearchTools _sequenceviewtools;
    private String _caseSensitiveOption;
    private String _gcString;
    private String _mouseoverString;
    private String _regExp;
    private String _revCompSequence;
    private String _seqType;
    private String _tmString;
    private int _logicalLineCnt;
    private int _logicalCol;
    private int _myIndex;
    private boolean _keyTypedAtLeastOnce;
    private HighlightPainter _painter;
    private UndoManager _undo;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private ArrayList<Feature> allFeatures;
    private Color userSelectColor;
    private Color ORFColor;
    private Color userHighlightColor;
    private boolean magicHighlight;
    private Object lastUserHighlightTag;
}
