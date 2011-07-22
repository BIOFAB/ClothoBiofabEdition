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

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.clothocore.util.dialog.ClothoDialogBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.text.*;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.api.data.NucSeq;
import org.clothocore.util.basic.ImageSource;

/**
 * Sequence view GUI
 * @author  Douglas Densmore
 */
public class SequenceViewGUI extends javax.swing.JFrame {

    /** Creates new form SequenceView */
    public SequenceViewGUI() {
        initComponents();
    }

    public SequenceViewGUI(SequenceView sv) {
        guiContentPane = null; //assign value later when needed, saves memory
        guiRootPane = null;
        _sv = sv;
        _firstResize = 0;
        _minWidth = 850;
        _minHeight = 400;
        initComponents();

        SequenceViewAdjustmentListener listener1 = new SequenceViewAdjustmentListener(jScrollPane1, jScrollPane2, _sv, 1);
        jScrollPane2.getVerticalScrollBar().addAdjustmentListener(listener1);
        SequenceViewAdjustmentListener listener2 = new SequenceViewAdjustmentListener(jScrollPane1, jScrollPane2, _sv, 2);
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(listener2);
        SequenceViewDocumentListener listener3 = new SequenceViewDocumentListener(SequenceTextPane, _sv);
        SequenceTextPane.getDocument().addDocumentListener(listener3);
        SequenceTextPane.setSelectedTextColor(Color.WHITE);
        SequenceTextPane.setSelectionColor(Color.GRAY);
        jLabel30.setVisible(false);

        setIconImage(ImageSource.getTinyLogo());
        // Checks to see if data is saved before closing the window
        _view = this;
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                if (!(_sv.getSaved())) {
                    ClothoDialogBox db = new ClothoDialogBox("Warning!", "Sequence View contains unsaved data!  Close without saving?");
                    String[] options = {"Yes Close", "No", "Save", "Hide"};
                    int chosen = db.show_optionDialog(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, options, options[2]);
                    if (chosen == javax.swing.JOptionPane.YES_OPTION) {
                        // No longer clears data on close, since windows can be
                        // re-opened after they are closed.
                        // _connection.updateClearedSequence();
                        SequenceTextPane.setText("");
                        setTitle("Clotho: Sequence View (Address: " + _sv.getIndex() + ") New Sequence");
                        CommentTextArea.setText("");
                        OutputTextArea.setText("");
                        _sv.updateWindowMenus();
                        //FIXME _sv.loadPart(new clothodata.ClothoPartsData());
                        _view.dispose();
                    } else if (chosen == javax.swing.JOptionPane.NO_OPTION) {
                        _view.requestFocus();
                    } else if (chosen == 2) {
                        _view.requestFocus();
                        _sv.saveSequence();
                    } else if (chosen == 3) {
                        _view.dispose();
                    }

                } else {
                    _view.dispose();
                }
            }
        });

        // Sets a minimum size to avoid strange resizing errors
        java.awt.Dimension minDimensions = new java.awt.Dimension(_minWidth, _minHeight);
        this.setMinimumSize(minDimensions);

        // Enables WordWrap in the two text areas
        CommentTextArea.setLineWrap(true);
        CommentTextArea.setWrapStyleWord(true);
        OutputTextArea.setLineWrap(true);
        OutputTextArea.setWrapStyleWord(true);

        // Fixed width font for output
        OutputTextArea.setFont(new java.awt.Font("Courier New", 0, 11));

        // Sets colors in windows to avoid contrasts with the Look & Feel
        CommentTextArea.setBackground(java.awt.Color.WHITE);
        CommentTextArea.setForeground(java.awt.Color.BLACK);
        CommentTextArea.setCaretColor(java.awt.Color.BLACK);
        OutputTextArea.setBackground(java.awt.Color.WHITE);
        OutputTextArea.setForeground(java.awt.Color.BLACK);
        SequenceTextPane.setBackground(java.awt.Color.WHITE);
        SequenceTextPane.setForeground(java.awt.Color.BLACK);
        SequenceTextPane.setCaretColor(java.awt.Color.BLACK);



        // Adds the SequenceView data transfer handler, to handle special drag and drop actions
        //commented
//        SequenceTextPane.setTransferHandler(new SequenceViewTransferHandler(_sv.getManager()));

        // Adds a FocusListener to makes sure the row and column numbering on
        // the SequenceView is correct whenever a sequence is loaded
        addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                _sv.configureBasePairBoth(columnCountJLabel);
            }
        });
    }

    public javax.swing.JCheckBox getCircularBox() {
        return CircularCheckBox;
    }

    public javax.swing.JLabel getColLabel() {
        return columnCountJLabel;
    }

    /**
     * Return the CommentTextArea
     * @return CommentTextArea
     */
    public javax.swing.JTextArea getCommentTextArea() {
        return CommentTextArea;
    }

    public javax.swing.JCheckBox getDegeneracyBox() {
        return degeneracyCheckBox;
    }

    public javax.swing.JScrollPane getIndexScroll() {
        return jScrollPane2;
    }

    public javax.swing.JCheckBox getLockedBox() {
        return this.LockCheckBox;
    }

    public javax.swing.JCheckBox getMethylationBox() {
        return MethCheckBox;
    }

    /**
     * Return the OutputTextArea
     * @return OutputTextArea
     */
    public javax.swing.JTextArea getOutputTextArea() {
        return OutputTextArea;
    }

    public javax.swing.JScrollPane getSeqScroll() {
        return jScrollPane1;
    }

    public javax.swing.JLabel getSeqCountLabel() {
        return SequenceLengthJLabel;
    }

    /**
     * Reteruns the WindowMenu
     * @return WindowMenu
     */
    public javax.swing.JMenu getWindowMenu() {
        return WindowMenu;
    }

    public javax.swing.JTextPane get_TextArea() {
        return SequenceTextPane;
    }

    public void setIndexTextArea(String s) {
        indexTextArea.setText(s);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainToolBar = new javax.swing.JToolBar();
        revCompButton = new javax.swing.JButton();
        transButton = new javax.swing.JButton();
        revTransButton = new javax.swing.JButton();
        uppperCaseButton = new javax.swing.JButton();
        switchButton = new javax.swing.JButton();
        lowerCaseButton = new javax.swing.JButton();
        packageButton = new javax.swing.JButton();
        featuresButton = new javax.swing.JButton();
        resSiteButton = new javax.swing.JButton();
        highlightButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        columnCountJLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        SequenceLengthJLabel = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        caretLocationJLabel = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        mouseLocationJLabel = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        featureNameJLabel = new javax.swing.JLabel();
        degeneracyCheckBox = new javax.swing.JCheckBox();
        CircularCheckBox = new javax.swing.JCheckBox();
        MethCheckBox = new javax.swing.JCheckBox();
        LockCheckBox = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SequenceTextPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        indexTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        CommentTextArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        OutputTextArea = new javax.swing.JTextArea();
        jLabel33 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        SequenceViewMenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        OpenNewMenuItem = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        LoadMenuItem = new javax.swing.JMenuItem();
        SaveMenuItem = new javax.swing.JMenuItem();
        createPartMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        ExitMenuItem = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JSeparator();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        revCutMenuItem = new javax.swing.JMenuItem();
        revCopyMenuItem = new javax.swing.JMenuItem();
        revPasteMenuItem = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        findMenuItem = new javax.swing.JMenuItem();
        moveOriginMenuItem = new javax.swing.JMenuItem();
        ToolMenu = new javax.swing.JMenu();
        ToolsMenuItem = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JSeparator();
        revCompMenuItem = new javax.swing.JMenuItem();
        TranslateMenuItem = new javax.swing.JMenuItem();
        revTranslateMenuItem = new javax.swing.JMenuItem();
        primerMenuItem = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JSeparator();
        ORFMenu = new javax.swing.JMenu();
        NextORFMenuItem = new javax.swing.JMenuItem();
        PrevORFMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        RevNextORFMenuItem = new javax.swing.JMenuItem();
        RevPrevORFMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        MapORFMenuItem = new javax.swing.JMenuItem();
        HighlightMenu = new javax.swing.JMenu();
        highlightSelectedMenuItem = new javax.swing.JMenuItem();
        highlightFeaturesMenuItem = new javax.swing.JMenuItem();
        highlightEnzMenuItem = new javax.swing.JMenuItem();
        highlightFeaturesEnzymesMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        removeAllHighlightsMenuItem = new javax.swing.JMenuItem();
        removeFeatureEnzymeHighlightMenuItem = new javax.swing.JMenuItem();
        removeUserSelectedHighlightMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        editFeatureLibraryMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        importGenbankMenuItem = new javax.swing.JMenuItem();
        importApEMenuItem = new javax.swing.JMenuItem();
        preferenceMenuItem = new javax.swing.JMenuItem();
        WindowMenu = new javax.swing.JMenu();
        switchViewMenuItem = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Clotho: Sequence View"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                frameResized(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        mainToolBar.setBorder(javax.swing.BorderFactory.createTitledBorder("Tools"));
        mainToolBar.setFloatable(false);

        revCompButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/ReverseComplementIcon.png"))); // NOI18N
        revCompButton.setMnemonic('R');
        revCompButton.setToolTipText("Display Reverse Complement"); // NOI18N
        revCompButton.setFocusable(false);
        revCompButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        revCompButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        revCompButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revCompButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(revCompButton);

        transButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/Translate.png"))); // NOI18N
        transButton.setMnemonic('T');
        transButton.setToolTipText("Translate"); // NOI18N
        transButton.setFocusable(false);
        transButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        transButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        transButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(transButton);

        revTransButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/TranslateReverse.png"))); // NOI18N
        revTransButton.setToolTipText("Reverse-Complement Translate"); // NOI18N
        revTransButton.setFocusable(false);
        revTransButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        revTransButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        revTransButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revTransButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(revTransButton);

        uppperCaseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/ToUpperCase.png"))); // NOI18N
        uppperCaseButton.setMnemonic('U');
        uppperCaseButton.setToolTipText("UPPER CASE"); // NOI18N
        uppperCaseButton.setFocusable(false);
        uppperCaseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uppperCaseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        uppperCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uppperCaseButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(uppperCaseButton);

        switchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/ChangeCase.png"))); // NOI18N
        switchButton.setMnemonic('S');
        switchButton.setToolTipText("Switch Case"); // NOI18N
        switchButton.setFocusable(false);
        switchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        switchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        switchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(switchButton);

        lowerCaseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/ToLowerCase.png"))); // NOI18N
        lowerCaseButton.setMnemonic('l');
        lowerCaseButton.setToolTipText("lower case"); // NOI18N
        lowerCaseButton.setFocusable(false);
        lowerCaseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lowerCaseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lowerCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowerCaseButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(lowerCaseButton);

        packageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/packagePartIcon16.png"))); // NOI18N
        packageButton.setMnemonic('P');
        packageButton.setToolTipText("Package as Clotho object: Associates it with a connection"); // NOI18N
        packageButton.setFocusable(false);
        packageButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        packageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packageButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(packageButton);

        featuresButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/HighlightFeatures.png"))); // NOI18N
        featuresButton.setMnemonic('F');
        featuresButton.setToolTipText("Highlight Features"); // NOI18N
        featuresButton.setFocusable(false);
        featuresButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        featuresButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        featuresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                featuresButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(featuresButton);

        resSiteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/clothocad/tool/sequenceview/images/icons/HighlightRestrictionSites.png"))); // NOI18N
        resSiteButton.setMnemonic('H');
        resSiteButton.setToolTipText("Highlight Restriction Sites"); // NOI18N
        resSiteButton.setFocusable(false);
        resSiteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resSiteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        resSiteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resSiteButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(resSiteButton);

        highlightButton.setText("Highlight");
        highlightButton.setToolTipText("Highlight selected sequence");
        highlightButton.setFocusable(false);
        highlightButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        highlightButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        highlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightButtonActionPerformed(evt);
            }
        });
        mainToolBar.add(highlightButton);

        jButton1.setText("Clear All Highlighting");
        jButton1.setToolTipText("Clear all highlighting");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        mainToolBar.add(jButton1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Location"); // NOI18N

        columnCountJLabel.setText("ColLabel"); // NOI18N

        jLabel3.setText("0"); // NOI18N

        jLabel4.setText("0"); // NOI18N

        jLabel5.setText("0"); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Start"); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Length"); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("End"); // NOI18N

        jLabel10.setText("<"); // NOI18N

        jLabel11.setText("0"); // NOI18N

        jLabel12.setText(">"); // NOI18N

        jLabel13.setText("<"); // NOI18N

        jLabel14.setText("0"); // NOI18N

        jLabel15.setText(">"); // NOI18N

        jLabel16.setText("<"); // NOI18N

        jLabel17.setText("0"); // NOI18N

        jLabel18.setText(">"); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel19.setText("Sequence"); // NOI18N

        SequenceLengthJLabel.setText("0"); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel21.setText("insert@"); // NOI18N

        caretLocationJLabel.setText("0"); // NOI18N

        jLabel23.setText("<"); // NOI18N

        jLabel24.setText("0"); // NOI18N

        jLabel25.setText(">"); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("%GC"); // NOI18N
        jLabel9.setToolTipText("GC Content - Minimum (Maximum)"); // NOI18N

        jLabel26.setText("0 (0)"); // NOI18N
        jLabel26.setToolTipText("GC Content - Minimum (Maximum)"); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel27.setText("Tm"); // NOI18N
        jLabel27.setToolTipText("Oligo Annealing Temperature"); // NOI18N

        jLabel28.setText("<0"); // NOI18N
        jLabel28.setToolTipText("Oligo Annealing Temperature"); // NOI18N

        mouseLocationJLabel.setText("0"); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel30.setText("Feature"); // NOI18N

        featureNameJLabel.setText(" "); // NOI18N
        featureNameJLabel.setToolTipText(featureNameJLabel.getText());

        degeneracyCheckBox.setText("Allow Degeneracy"); // NOI18N
        degeneracyCheckBox.setToolTipText("Enables or disables the use of degenerate IUPAC codes"); // NOI18N
        degeneracyCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                degeneracyCheckBoxItemStateChanged(evt);
            }
        });

        CircularCheckBox.setText("Circular"); // NOI18N
        CircularCheckBox.setToolTipText("If selected, the sequence will be treated like a plasmid"); // NOI18N
        CircularCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CircularStateChanged(evt);
            }
        });
        CircularCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CircularCheckBoxActionPerformed(evt);
            }
        });

        MethCheckBox.setText("Dam/Dcm"); // NOI18N
        MethCheckBox.setToolTipText("Enables or disables methylation of the sequence"); // NOI18N
        MethCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MethCheckBoxItemStateChanged(evt);
            }
        });

        LockCheckBox.setText("Locked"); // NOI18N
        LockCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LockCheckBoxItemStateChanged(evt);
            }
        });
        LockCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LockCheckBoxActionPerformed(evt);
            }
        });

        jLabel20.setText("Columns: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(1336, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(64, 64, 64))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(LockCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(SequenceLengthJLabel)
                        .addComponent(jLabel19))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(caretLocationJLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(mouseLocationJLabel)
                        .addComponent(jLabel1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(6, 6, 6)
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel12))
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel15))
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel18)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel28)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 441, Short.MAX_VALUE)
                            .addComponent(degeneracyCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(MethCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(CircularCheckBox))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(featureNameJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(columnCountJLabel)))
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(22, 22, 22))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LockCheckBox)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel21)
                                            .addGap(4, 4, 4)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(caretLocationJLabel)
                                                .addComponent(jLabel23)
                                                .addComponent(jLabel24)
                                                .addComponent(jLabel25)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(4, 4, 4)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel12)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel10)
                                                    .addComponent(jLabel3)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(4, 4, 4)
                                                .addComponent(mouseLocationJLabel))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(4, 4, 4)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel15)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jLabel4)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(4, 4, 4)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel16)
                                                    .addComponent(jLabel17)
                                                    .addComponent(jLabel18)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel27)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel30))
                                                .addGap(4, 4, 4)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel28)
                                                    .addComponent(featureNameJLabel)
                                                    .addComponent(jLabel26)))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addGap(4, 4, 4)
                                        .addComponent(SequenceLengthJLabel)))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CircularCheckBox)
                                .addComponent(MethCheckBox)
                                .addComponent(degeneracyCheckBox))
                            .addGap(4, 4, 4)
                            .addComponent(columnCountJLabel)))
                    .addContainerGap(28, Short.MAX_VALUE)))
        );

        SequenceTextPane.setFont(new java.awt.Font("Courier New", 0, 11));
        SequenceTextPane.setDragEnabled(true);
        SequenceTextPane.setMargin(new java.awt.Insets(0, 0, 0, 0));
        SequenceTextPane.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SequenceViewGUI.this.caretUpdate(evt);
            }
        });
        SequenceTextPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                SequenceViewGUI.this.mouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                mouseMovedinTextPane(evt);
            }
        });
        SequenceTextPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textPaneFocusGained(evt);
            }
        });
        SequenceTextPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SequenceTextPaneKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedJTextPane(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SequenceViewGUI.this.keyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(SequenceTextPane);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setOpaque(false);

        indexTextArea.setBackground(new java.awt.Color(236, 233, 216));
        indexTextArea.setColumns(20);
        indexTextArea.setEditable(false);
        indexTextArea.setFont(new java.awt.Font("Courier New", 0, 11));
        indexTextArea.setRows(5);
        indexTextArea.setToolTipText("Base Pair Line Index"); // NOI18N
        indexTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        indexTextArea.setOpaque(false);
        indexTextArea.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(indexTextArea);
        indexTextArea.getAccessibleContext().setAccessibleParent(jScrollPane2);

        CommentTextArea.setColumns(20);
        CommentTextArea.setRows(5);
        jScrollPane3.setViewportView(CommentTextArea);

        OutputTextArea.setColumns(20);
        OutputTextArea.setEditable(false);
        OutputTextArea.setRows(5);
        jScrollPane4.setViewportView(OutputTextArea);

        jLabel33.setText("Output Data"); // NOI18N

        jLabel32.setText("Sequence Comments"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1326, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                                .addGap(83, 83, 83))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mainToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1447, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addContainerGap())
        );

        FileMenu.setText("File"); // NOI18N

        OpenNewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        OpenNewMenuItem.setText("New Sequence View Window"); // NOI18N
        OpenNewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenNewMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(OpenNewMenuItem);
        FileMenu.add(jSeparator13);

        LoadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        LoadMenuItem.setText("Load Sequence File"); // NOI18N
        LoadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(LoadMenuItem);

        SaveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveMenuItem.setText("Save Sequence File ");
        SaveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(SaveMenuItem);

        createPartMenuItem.setText("Create New Clotho Object"); // NOI18N
        createPartMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPartMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(createPartMenuItem);
        FileMenu.add(jSeparator1);

        ExitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        ExitMenuItem.setText("Close Window");
        ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed1(evt);
            }
        });
        FileMenu.add(ExitMenuItem);

        SequenceViewMenuBar.add(FileMenu);

        EditMenu.setText("Edit"); // NOI18N
        EditMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditMenuActionPerformed(evt);
            }
        });

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.setText("Undo");
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(undoMenuItem);

        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.setText("Redo");
        redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(redoMenuItem);
        EditMenu.add(jSeparator11);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setText("Cut"); // NOI18N
        cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setText("Copy"); // NOI18N
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setText("Paste"); // NOI18N
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(pasteMenuItem);
        EditMenu.add(jSeparator10);

        revCutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        revCutMenuItem.setText("Cut (Reverse Complement)"); // NOI18N
        revCutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revCutMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(revCutMenuItem);

        revCopyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        revCopyMenuItem.setText("Copy (Reverse Complement)"); // NOI18N
        revCopyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revCopyMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(revCopyMenuItem);

        revPasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        revPasteMenuItem.setText("Paste (Reverse Complement)"); // NOI18N
        revPasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revPasteMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(revPasteMenuItem);
        EditMenu.add(jSeparator12);

        findMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        findMenuItem.setText("Find"); // NOI18N
        findMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(findMenuItem);

        moveOriginMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        moveOriginMenuItem.setText("Move Origin to Cursor"); // NOI18N
        moveOriginMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveOriginMenuItemActionPerformed(evt);
            }
        });
        EditMenu.add(moveOriginMenuItem);

        SequenceViewMenuBar.add(EditMenu);

        ToolMenu.setText("Tools"); // NOI18N

        ToolsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        ToolsMenuItem.setText("Search Tools"); // NOI18N
        ToolsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToolsMenuItemActionPerformed(evt);
            }
        });
        ToolMenu.add(ToolsMenuItem);
        ToolMenu.add(jSeparator17);

        revCompMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SLASH, java.awt.event.InputEvent.CTRL_MASK));
        revCompMenuItem.setText("Reverse Complement"); // NOI18N
        revCompMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revCompMenuItemActionPerformed1(evt);
            }
        });
        ToolMenu.add(revCompMenuItem);

        TranslateMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        TranslateMenuItem.setText("Translate"); // NOI18N
        TranslateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TranslateMenuItemActionPerformed(evt);
            }
        });
        ToolMenu.add(TranslateMenuItem);

        revTranslateMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        revTranslateMenuItem.setText("Translate Reverse"); // NOI18N
        revTranslateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revTranslateMenuItemActionPerformed(evt);
            }
        });
        ToolMenu.add(revTranslateMenuItem);

        primerMenuItem.setText("Create Primers");
        primerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primerMenuItemActionPerformed(evt);
            }
        });
        ToolMenu.add(primerMenuItem);
        ToolMenu.add(jSeparator14);

        SequenceViewMenuBar.add(ToolMenu);

        ORFMenu.setText("ORFs"); // NOI18N

        NextORFMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PERIOD, java.awt.event.InputEvent.CTRL_MASK));
        NextORFMenuItem.setText("Find Next ORF"); // NOI18N
        NextORFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextORFMenuItemActionPerformed(evt);
            }
        });
        ORFMenu.add(NextORFMenuItem);

        PrevORFMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.CTRL_MASK));
        PrevORFMenuItem.setText("Find Previous ORF"); // NOI18N
        PrevORFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevORFMenuItemActionPerformed(evt);
            }
        });
        ORFMenu.add(PrevORFMenuItem);
        ORFMenu.add(jSeparator2);

        RevNextORFMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PERIOD, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        RevNextORFMenuItem.setText("Find Next Reverse ORF"); // NOI18N
        RevNextORFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RevNextORFMenuItemActionPerformed(evt);
            }
        });
        ORFMenu.add(RevNextORFMenuItem);

        RevPrevORFMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        RevPrevORFMenuItem.setText("Find Previous Reverse ORF"); // NOI18N
        RevPrevORFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RevPrevORFMenuItemActionPerformed(evt);
            }
        });
        ORFMenu.add(RevPrevORFMenuItem);
        ORFMenu.add(jSeparator3);

        MapORFMenuItem.setText("Display ORF Map"); // NOI18N
        MapORFMenuItem.setEnabled(false);
        MapORFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MapORFMenuItemActionPerformed(evt);
            }
        });
        ORFMenu.add(MapORFMenuItem);

        SequenceViewMenuBar.add(ORFMenu);

        HighlightMenu.setText("Highlighting");
        HighlightMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HighlightMenuActionPerformed(evt);
            }
        });

        highlightSelectedMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        highlightSelectedMenuItem.setText("Highlight Selected");
        highlightSelectedMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightSelectedMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(highlightSelectedMenuItem);

        highlightFeaturesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        highlightFeaturesMenuItem.setText("Highlight Features"); // NOI18N
        highlightFeaturesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightFeaturesMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(highlightFeaturesMenuItem);

        highlightEnzMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        highlightEnzMenuItem.setText("Highlight Restriction Sites"); // NOI18N
        highlightEnzMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightEnzMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(highlightEnzMenuItem);

        highlightFeaturesEnzymesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        highlightFeaturesEnzymesMenuItem.setText("Highlight Features/Enzymes"); // NOI18N
        highlightFeaturesEnzymesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightFeaturesEnzymesMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(highlightFeaturesEnzymesMenuItem);
        HighlightMenu.add(jSeparator6);

        removeAllHighlightsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        removeAllHighlightsMenuItem.setText("Remove All Highlights"); // NOI18N
        removeAllHighlightsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllHighlightsMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(removeAllHighlightsMenuItem);

        removeFeatureEnzymeHighlightMenuItem.setText("Remove Features/Enzymes Highlights"); // NOI18N
        removeFeatureEnzymeHighlightMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFeatureEnzymeHighlightMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(removeFeatureEnzymeHighlightMenuItem);

        removeUserSelectedHighlightMenuItem.setText("Remove User-Selected Highlights"); // NOI18N
        removeUserSelectedHighlightMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeUserSelectedHighlightMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(removeUserSelectedHighlightMenuItem);
        HighlightMenu.add(jSeparator4);

        editFeatureLibraryMenuItem.setText("Open Feature/Enzyme Library");
        editFeatureLibraryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editFeatureLibraryMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(editFeatureLibraryMenuItem);

        jMenu1.setText("Import Features");

        importGenbankMenuItem.setText("Import Genbank Features");
        importGenbankMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importGenbankMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(importGenbankMenuItem);

        importApEMenuItem.setText("Import ApE Features");
        importApEMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importApEMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(importApEMenuItem);

        HighlightMenu.add(jMenu1);

        preferenceMenuItem.setText("Preferences"); // NOI18N
        preferenceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferenceMenuItemActionPerformed(evt);
            }
        });
        HighlightMenu.add(preferenceMenuItem);

        SequenceViewMenuBar.add(HighlightMenu);

        WindowMenu.setText("Windows"); // NOI18N
        WindowMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WindowMenuActionPerformed(evt);
            }
        });

        switchViewMenuItem.setText("Switch View");
        switchViewMenuItem.setToolTipText("Switch between top component view and frame view");
        switchViewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchViewMenuItemActionPerformed(evt);
            }
        });
        WindowMenu.add(switchViewMenuItem);

        SequenceViewMenuBar.add(WindowMenu);

        HelpMenu.setText("Help");

        helpMenuItem.setText("Help"); // NOI18N
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        HelpMenu.add(helpMenuItem);

        SequenceViewMenuBar.add(HelpMenu);

        setJMenuBar(SequenceViewMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void CircularStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CircularStateChanged
    _sv.update_circular();
}//GEN-LAST:event_CircularStateChanged

private void keyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTyped
    _sv.validateKeyTyped(evt);
}//GEN-LAST:event_keyTyped

private void frameResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_frameResized
    if (_firstResize >= 2) {
        _sv.configureBasePairBoth(columnCountJLabel);
    } else {
        _firstResize++;
    }

}//GEN-LAST:event_frameResized

private void caretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_caretUpdate
    _sv.processCaret(evt,
            jLabel6, jLabel3, jLabel11, jLabel10, jLabel12,
            jLabel7, jLabel4, jLabel14, jLabel13, jLabel15,
            jLabel8, jLabel5, jLabel17, jLabel16, jLabel18,
            jLabel21, caretLocationJLabel, jLabel24, jLabel23, jLabel25,
            jLabel9, jLabel26, jLabel27, jLabel28);
}//GEN-LAST:event_caretUpdate

private void mouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseDragged
    _sv.processMouseHighlight(evt, SequenceTextPane,
            jLabel6, jLabel3, jLabel11, jLabel10, jLabel12,
            jLabel7, jLabel4, jLabel14, jLabel13, jLabel15,
            jLabel8, jLabel5, jLabel17, jLabel16, jLabel18,
            jLabel21, caretLocationJLabel, jLabel24, jLabel23, jLabel25,
            jLabel9, jLabel26, jLabel27, jLabel28);
}//GEN-LAST:event_mouseDragged

private void revCompButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revCompButtonActionPerformed
    _sv.processSearchToolAction(evt, "Reverse Complement");
}//GEN-LAST:event_revCompButtonActionPerformed

private void transButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transButtonActionPerformed
    _sv.processSearchToolAction(evt, "Translation");
}//GEN-LAST:event_transButtonActionPerformed

private void switchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchButtonActionPerformed
    _sv.processSearchToolAction(evt, "Switch Case");
}//GEN-LAST:event_switchButtonActionPerformed

private void resSiteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resSiteButtonActionPerformed
    _sv.highlightRestrictionSites();
}//GEN-LAST:event_resSiteButtonActionPerformed

private void keyReleasedJTextPane(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyReleasedJTextPane
    //this function is here as well as keyTyped since key typed seems to count string length 
    //in textPane before the key is release i.e. it does not count the latest addition
    _sv.validateKeyReleased(evt);
    _sv.updateSequenceCount(SequenceLengthJLabel);
}//GEN-LAST:event_keyReleasedJTextPane

private void LoadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadMenuItemActionPerformed
    _sv.loadSequence();
}//GEN-LAST:event_LoadMenuItemActionPerformed

private void SaveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveMenuItemActionPerformed
    _sv.saveSequence();
}//GEN-LAST:event_SaveMenuItemActionPerformed

private void ExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuItemActionPerformed
    if (!(_sv.getSaved())) {
//        ClothoDialogBox db = new ClothoDialogBox("Warning!", "Sequence View contains unsaved data!  Close without saving?");
        String[] options = {"Yes Close", "No", "Save", "Hide"};
//        int chosen = db.show_optionDialog(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, options, options[2]);
        int chosen = javax.swing.JOptionPane.showOptionDialog(
                null,
                "Sequence View contains unsaved data!  Close without saving?",
                "Warning!",
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);//FIXME replace with ClothoDialogBox

        if (chosen == javax.swing.JOptionPane.YES_OPTION) {
            // No longer clears data on close, since windows can be
            // re-opened after they are closed.
            // _connection.updateClearedSequence();
            SequenceTextPane.setText("");
            setTitle("Clotho: Sequence View (Address: " + _sv.getIndex() + ") New Sequence");
            CommentTextArea.setText("");
            OutputTextArea.setText("");
            _sv.updateWindowMenus();

            //FIXME _sv.loadPart(new clothodata.ClothoPartsData());
            if (_sv.getIsTC()) {
                _sv.getTCView().close();
            }
            this.dispose();
        } else if (chosen == javax.swing.JOptionPane.NO_OPTION) {
            this.requestFocus();
        } else if (chosen == 2) {
            this.requestFocus();
            _sv.saveSequence();
        } else if (chosen == 3) {
            if (_sv.getIsTC()) {
                _sv.getTCView().close();
            }
            this.dispose();
        }
    } else {
        if (_sv.getIsTC()) {
                _sv.getTCView().close();
            }
        this.dispose();
    }
}//GEN-LAST:event_ExitMenuItemActionPerformed

private void OpenNewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenNewMenuItemActionPerformed
//    _sv.createNewWindow();
    _sv.createNewTab();
}//GEN-LAST:event_OpenNewMenuItemActionPerformed

private void ToolsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToolsMenuItemActionPerformed
    _sv.show_tools();
}//GEN-LAST:event_ToolsMenuItemActionPerformed

private void TranslateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TranslateMenuItemActionPerformed
    _sv.processSearchToolAction(evt, "Translation");

}//GEN-LAST:event_TranslateMenuItemActionPerformed

private void NextORFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextORFMenuItemActionPerformed
    _sv.findNextORF();
}//GEN-LAST:event_NextORFMenuItemActionPerformed

private void PrevORFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevORFMenuItemActionPerformed
    _sv.findPrevORF();
}//GEN-LAST:event_PrevORFMenuItemActionPerformed

private void MapORFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MapORFMenuItemActionPerformed
    _sv.displayORFs();
}//GEN-LAST:event_MapORFMenuItemActionPerformed

private void mouseMovedinTextPane(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseMovedinTextPane
    _sv.processMouseMoved(evt, mouseLocationJLabel, featureNameJLabel, jLabel30);
}//GEN-LAST:event_mouseMovedinTextPane

private void packageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packageButtonActionPerformed
    //_connection.packagePartOpen();
    if (SequenceTextPane.getText().isEmpty()) {
        ClothoDialogBox db = new ClothoDialogBox("Error", "No sequence data to package");
        db.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
    } else {
        _sv.createPart();
    }
}//GEN-LAST:event_packageButtonActionPerformed

private void RevNextORFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RevNextORFMenuItemActionPerformed
    _sv.findNextRevORF();
}//GEN-LAST:event_RevNextORFMenuItemActionPerformed

private void RevPrevORFMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RevPrevORFMenuItemActionPerformed
    _sv.findPrevRevORF();
}//GEN-LAST:event_RevPrevORFMenuItemActionPerformed

private void uppperCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uppperCaseButtonActionPerformed
    _sv.changeCase(true);
}//GEN-LAST:event_uppperCaseButtonActionPerformed

private void lowerCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowerCaseButtonActionPerformed
    _sv.changeCase(false);
}//GEN-LAST:event_lowerCaseButtonActionPerformed

private void LockCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LockCheckBoxItemStateChanged
    _sv.update_lock(evt);
}//GEN-LAST:event_LockCheckBoxItemStateChanged

private void highlightFeaturesEnzymesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightFeaturesEnzymesMenuItemActionPerformed
    _sv.resetHighlight(SequenceTextPane);
    _sv.highlightRestrictionSites();
    _sv.highlightFeatures();
}//GEN-LAST:event_highlightFeaturesEnzymesMenuItemActionPerformed

private void highlightFeaturesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightFeaturesMenuItemActionPerformed
    _sv.highlightFeatures();
}//GEN-LAST:event_highlightFeaturesMenuItemActionPerformed

private void highlightEnzMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightEnzMenuItemActionPerformed
    _sv.highlightRestrictionSites();
}//GEN-LAST:event_highlightEnzMenuItemActionPerformed

private void MethCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MethCheckBoxItemStateChanged
    _sv.update_methylated(evt);
}//GEN-LAST:event_MethCheckBoxItemStateChanged

private void SequenceTextPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SequenceTextPaneKeyPressed
    _sv.validateKeyPressed(evt);
}//GEN-LAST:event_SequenceTextPaneKeyPressed

private void degeneracyCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_degeneracyCheckBoxItemStateChanged
    _sv.update_AllowDegeneracy(evt);
}//GEN-LAST:event_degeneracyCheckBoxItemStateChanged

private void revTransButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revTransButtonActionPerformed
    _sv.processSearchToolAction(evt, "Reverse Translation");
}//GEN-LAST:event_revTransButtonActionPerformed

private void removeAllHighlightsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllHighlightsMenuItemActionPerformed
    _sv.resetHighlight(SequenceTextPane);
//    _sv.get_highlightData().clear();
}//GEN-LAST:event_removeAllHighlightsMenuItemActionPerformed

private void findMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findMenuItemActionPerformed
    _sv.show_tools();
}//GEN-LAST:event_findMenuItemActionPerformed

private void moveOriginMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveOriginMenuItemActionPerformed
    _sv.moveOrigin();
}//GEN-LAST:event_moveOriginMenuItemActionPerformed

private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
    _sv.setToMainSeqView();
}//GEN-LAST:event_formFocusGained

private void textPaneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPaneFocusGained
    _sv.setToMainSeqView();
}//GEN-LAST:event_textPaneFocusGained

private void removeFeatureEnzymeHighlightMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFeatureEnzymeHighlightMenuItemActionPerformed
    _sv.removeFeatureEnzymeHighlights();
//    _sv.get_highlightData().clear();
}//GEN-LAST:event_removeFeatureEnzymeHighlightMenuItemActionPerformed

private void removeUserSelectedHighlightMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeUserSelectedHighlightMenuItemActionPerformed
    _sv.removeUserSelectedHighlights();
}//GEN-LAST:event_removeUserSelectedHighlightMenuItemActionPerformed

private void revTranslateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revTranslateMenuItemActionPerformed
    _sv.processSearchToolAction(evt, "Reverse Translation");
}//GEN-LAST:event_revTranslateMenuItemActionPerformed

private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItemActionPerformed
//    SequenceTextPane.cut();
    StringSelection ss = new StringSelection(SequenceTextPane.getSelectedText());
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    SequenceTextPane.replaceSelection("");
}//GEN-LAST:event_cutMenuItemActionPerformed

private void revCompMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revCompMenuItemActionPerformed
    _sv.processSearchToolAction(evt, "Reverse Complement");
}//GEN-LAST:event_revCompMenuItemActionPerformed

private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
//    SequenceTextPane.copy();
    StringSelection ss = new StringSelection(SequenceTextPane.getSelectedText());
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
}//GEN-LAST:event_copyMenuItemActionPerformed

private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuItemActionPerformed
//    SequenceTextPane.paste();
    String newSeq = null;
    java.awt.datatransfer.Transferable t = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    try {
        if (t != null && t.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {
            newSeq = (String) t.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
            SequenceTextPane.replaceSelection(newSeq);
        }
    } catch (java.awt.datatransfer.UnsupportedFlavorException e) {
    } catch (java.io.IOException e) {
    }

    if ((newSeq == null) || (newSeq.equalsIgnoreCase(""))) {
        return;
    }
    if (!_sv.checkValidSequence(newSeq, _sv.getDegeneracy())) {
        //commented
        //               ClothoCore.getCore().log("Sequence View: Cannot paste sequence with non nucleotide characters", LogLevel.MESSAGE);
        return;
    }
}//GEN-LAST:event_pasteMenuItemActionPerformed

private void revCutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revCutMenuItemActionPerformed
    _sv.revCompFunctions("CUT", SequenceTextPane.getSelectedText());
    SequenceTextPane.replaceSelection("");
}//GEN-LAST:event_revCutMenuItemActionPerformed

private void revCopyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revCopyMenuItemActionPerformed
    _sv.revCompFunctions("COPY", SequenceTextPane.getSelectedText());
}//GEN-LAST:event_revCopyMenuItemActionPerformed

private void revPasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revPasteMenuItemActionPerformed
    if (_sv.revCompFunctions("PASTE", null).length() > 0) {
        SequenceTextPane.replaceSelection(_sv.revCompFunctions("PASTE", null));
    }
}//GEN-LAST:event_revPasteMenuItemActionPerformed

private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
    _sv.undoActionPerformed(evt);
}//GEN-LAST:event_undoMenuItemActionPerformed

private void redoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoMenuItemActionPerformed
    _sv.redoActionPerformed(evt);
}//GEN-LAST:event_redoMenuItemActionPerformed

private void featuresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_featuresButtonActionPerformed
    _sv.highlightFeatures();
}//GEN-LAST:event_featuresButtonActionPerformed

private void createPartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPartMenuItemActionPerformed
    _sv.createPart();
}//GEN-LAST:event_createPartMenuItemActionPerformed

private void LockCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LockCheckBoxActionPerformed
    _sv.configureBasePairBoth(columnCountJLabel);
}//GEN-LAST:event_LockCheckBoxActionPerformed

private void CircularCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CircularCheckBoxActionPerformed
    //_sv.sequenceChanged();
    _sv.update_circular();
}//GEN-LAST:event_CircularCheckBoxActionPerformed

private void preferenceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferenceMenuItemActionPerformed
    _sv.openPreferences();
}//GEN-LAST:event_preferenceMenuItemActionPerformed

private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
    _sv.openHelp();
}//GEN-LAST:event_helpMenuItemActionPerformed

private void undoMenuItemActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed1
    // TODO add your handling code here:
}//GEN-LAST:event_undoMenuItemActionPerformed1

private void revCompMenuItemActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revCompMenuItemActionPerformed1
    // TODO add your handling code here:
    if (SequenceTextPane.getSelectedText() != null) {
        NucSeq ns = new NucSeq(SequenceTextPane.getSelectedText());

        OutputTextArea.setText(ns.revComp());
    }

}//GEN-LAST:event_revCompMenuItemActionPerformed1

private void editFeatureLibraryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFeatureLibraryMenuItemActionPerformed
    for (ToolWrapper tw : Collator.getAllTools()) {
        if (tw.getUUID().equals("org.clothocad.tool.spreaditfeatures")) {
            tw.launchTool();
            break;
        }
    }
}//GEN-LAST:event_editFeatureLibraryMenuItemActionPerformed

private void WindowMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WindowMenuActionPerformed
}//GEN-LAST:event_WindowMenuActionPerformed

private void EditMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditMenuActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_EditMenuActionPerformed

private void HighlightMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HighlightMenuActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_HighlightMenuActionPerformed

private void highlightSelectedMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightSelectedMenuItemActionPerformed
    _sv.highlightUserSelected();
}//GEN-LAST:event_highlightSelectedMenuItemActionPerformed

private void highlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightButtonActionPerformed
    _sv.highlightUserSelected();
}//GEN-LAST:event_highlightButtonActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    _sv.removeFeatureEnzymeHighlights();
    _sv.removeUserSelectedHighlights();
}//GEN-LAST:event_jButton1ActionPerformed

private void importGenbankMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importGenbankMenuItemActionPerformed
    _sv.importGenbankFeatures();
}//GEN-LAST:event_importGenbankMenuItemActionPerformed

private void importApEMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importApEMenuItemActionPerformed
    _sv.importApEFeatures();
}//GEN-LAST:event_importApEMenuItemActionPerformed

private void switchViewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchViewMenuItemActionPerformed
_sv.switchView();
}//GEN-LAST:event_switchViewMenuItemActionPerformed

private void ExitMenuItemActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuItemActionPerformed1
if (!(_sv.getSaved())) {
//        ClothoDialogBox db = new ClothoDialogBox("Warning!", "Sequence View contains unsaved data!  Close without saving?");
        String[] options = {"Yes Close", "No", "Save", "Hide"};
//        int chosen = db.show_optionDialog(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, options, options[2]);
        int chosen = javax.swing.JOptionPane.showOptionDialog(
                null,
                "Sequence View contains unsaved data!  Close without saving?",
                "Warning!",
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);//FIXME replace with ClothoDialogBox

        if (chosen == javax.swing.JOptionPane.YES_OPTION) {
            // No longer clears data on close, since windows can be
            // re-opened after they are closed.
            // _connection.updateClearedSequence();
            SequenceTextPane.setText("");
            setTitle("Clotho: Sequence View (Address: " + _sv.getIndex() + ") New Sequence");
            CommentTextArea.setText("");
            OutputTextArea.setText("");
            _sv.updateWindowMenus();

            //FIXME _sv.loadPart(new clothodata.ClothoPartsData());
            if (_sv.getIsTC()) {
                _sv.getTCView().close();
            }
            this.dispose();
        } else if (chosen == javax.swing.JOptionPane.NO_OPTION) {
            this.requestFocus();
        } else if (chosen == 2) {
            this.requestFocus();
            _sv.saveSequence();
        } else if (chosen == 3) {
            if (_sv.getIsTC()) {
                _sv.getTCView().close();
            }
            this.dispose();
        }
    } else {
        if (_sv.getIsTC()) {
                _sv.getTCView().close();
            }
        this.dispose();
    }
}//GEN-LAST:event_ExitMenuItemActionPerformed1

private void primerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primerMenuItemActionPerformed
String sequence = SequenceTextPane.getSelectedText();
if (sequence==null) {
    sequence=SequenceTextPane.getText();
}
    _sv.createPrimers(sequence);
}//GEN-LAST:event_primerMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SequenceViewGUI().setVisible(true);


            }
        });
    }
    private JComponent guiContentPane;
    private JRootPane guiRootPane;
    private SequenceView _sv;
    private int _firstResize;
    private int _minWidth;
    private int _minHeight;
    private SequenceViewGUI _view;
    private AbstractDocument doc;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CircularCheckBox;
    private javax.swing.JTextArea CommentTextArea;
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenuItem ExitMenuItem;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenu HighlightMenu;
    private javax.swing.JMenuItem LoadMenuItem;
    private javax.swing.JCheckBox LockCheckBox;
    private javax.swing.JMenuItem MapORFMenuItem;
    private javax.swing.JCheckBox MethCheckBox;
    private javax.swing.JMenuItem NextORFMenuItem;
    private javax.swing.JMenu ORFMenu;
    private javax.swing.JMenuItem OpenNewMenuItem;
    private javax.swing.JTextArea OutputTextArea;
    private javax.swing.JMenuItem PrevORFMenuItem;
    private javax.swing.JMenuItem RevNextORFMenuItem;
    private javax.swing.JMenuItem RevPrevORFMenuItem;
    private javax.swing.JMenuItem SaveMenuItem;
    private javax.swing.JLabel SequenceLengthJLabel;
    private javax.swing.JTextPane SequenceTextPane;
    private javax.swing.JMenuBar SequenceViewMenuBar;
    private javax.swing.JMenu ToolMenu;
    private javax.swing.JMenuItem ToolsMenuItem;
    private javax.swing.JMenuItem TranslateMenuItem;
    private javax.swing.JMenu WindowMenu;
    private javax.swing.JLabel caretLocationJLabel;
    private javax.swing.JLabel columnCountJLabel;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem createPartMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JCheckBox degeneracyCheckBox;
    private javax.swing.JMenuItem editFeatureLibraryMenuItem;
    private javax.swing.JLabel featureNameJLabel;
    private javax.swing.JButton featuresButton;
    private javax.swing.JMenuItem findMenuItem;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JButton highlightButton;
    private javax.swing.JMenuItem highlightEnzMenuItem;
    private javax.swing.JMenuItem highlightFeaturesEnzymesMenuItem;
    private javax.swing.JMenuItem highlightFeaturesMenuItem;
    private javax.swing.JMenuItem highlightSelectedMenuItem;
    private javax.swing.JMenuItem importApEMenuItem;
    private javax.swing.JMenuItem importGenbankMenuItem;
    private javax.swing.JTextArea indexTextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JButton lowerCaseButton;
    private javax.swing.JToolBar mainToolBar;
    private javax.swing.JLabel mouseLocationJLabel;
    private javax.swing.JMenuItem moveOriginMenuItem;
    private javax.swing.JButton packageButton;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem preferenceMenuItem;
    private javax.swing.JMenuItem primerMenuItem;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JMenuItem removeAllHighlightsMenuItem;
    private javax.swing.JMenuItem removeFeatureEnzymeHighlightMenuItem;
    private javax.swing.JMenuItem removeUserSelectedHighlightMenuItem;
    private javax.swing.JButton resSiteButton;
    private javax.swing.JButton revCompButton;
    private javax.swing.JMenuItem revCompMenuItem;
    private javax.swing.JMenuItem revCopyMenuItem;
    private javax.swing.JMenuItem revCutMenuItem;
    private javax.swing.JMenuItem revPasteMenuItem;
    private javax.swing.JButton revTransButton;
    private javax.swing.JMenuItem revTranslateMenuItem;
    private javax.swing.JButton switchButton;
    private javax.swing.JMenuItem switchViewMenuItem;
    private javax.swing.JButton transButton;
    private javax.swing.JMenuItem undoMenuItem;
    private javax.swing.JButton uppperCaseButton;
    // End of variables declaration//GEN-END:variables
}
