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

package org.clothocad.library.sequencelight;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.event.FocusEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.misc.BareBonesBrowserLaunch;

/**
 *
 * @author J. Christopher Anderson
 */
public class seqPanel extends JPanel {
    public seqPanel(nucSeqEditor window) {
        super(new BorderLayout());
        nse = window;
        initComponents();

        _obo = new SeqObserver();
        window._seq.isObservedBy(_obo);

        _obo.update(nse._seq, new RefreshEvent(nse._seq, RefreshEvent.Condition.UNKNOWN));
    }

    private class SeqObserver implements ObjBaseObserver {
        @Override
        public void update(ObjBase obj, RefreshEvent evt) {
            if(evt.isCondition(RefreshEvent.Condition.SEQUENCE_CHANGED )
                    || evt.isCondition(RefreshEvent.Condition.ANNOTATION_TO_NUCSEQ )
                    || evt.isCondition(RefreshEvent.Condition.UNKNOWN )) {
                initFeatures();
            } else if(evt.isCondition(RefreshEvent.Condition.DELETE_SUCCESSFUL)) {
                setVisible(false);
            }
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JScrollPane scrollpane = new JScrollPane();

        ScrollablePanel seqAreaCon = new ScrollablePanel();
        seqAreaCon.setBackground(Color.WHITE);

        scrollpane.setViewportView(seqAreaCon);
        scrollpane.setWheelScrollingEnabled(true);
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollpane, BorderLayout.CENTER);

        seqarea = new JTextArea();
        seqAreaCon.setLayout(new BorderLayout());
        seqAreaCon.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        seqAreaCon.setScrollableHeight(ScrollablePanel.ScrollableSizeHint.NONE);
        seqAreaCon.add(seqarea, BorderLayout.CENTER);

        //Format the sequence text area
        seqarea.setText(sequence);
        seqarea.setFont(new Font("Courier New", Font.PLAIN, 14));
        seqarea.setLineWrap(true);
        seqarea.setKeymap(new dnaKeymap());

        //Add a colorlistener, but hide it
         colorpop = new colorPopup(nse.frame, false, this);

        //Add a Find window, but hide it
         findpop = new findReplace(nse.frame, false, this);

        //make the tab and enter keys xfer focus, not insert a tab character
        Set forwardTraversalKeys = new HashSet();
        forwardTraversalKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
        forwardTraversalKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        seqarea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,forwardTraversalKeys);
        Set backwardTraversalKeys = new HashSet();
        backwardTraversalKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK));
        backwardTraversalKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK));
        seqarea.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardTraversalKeys);

        //Listen for F1 and ctrl-H to get launch of Help via web
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("F1"), "HhelpAction");
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control H"), "HhelpAction");
        seqarea.getActionMap().put("HhelpAction",
            new AbstractAction("HhelpAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("help requested");
                    String url = Collator.helpURLBase + "/SequenceLight";
                    BareBonesBrowserLaunch.openURL(url);
                }});

        //Autoannotate for user's collection
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control K"), "collectionAutoannotateAction");
        seqarea.getActionMap().put("collectionAutoannotateAction",
            new AbstractAction("collectionAutoannotateAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    new SwingWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            Person user = Collector.getCurrentUser();
                            nse._seq.autoAnnotate(user.getHerCollection().recursiveGetAllLinksOf(ObjType.FEATURE), user, true);
                            return null;
                        }
                    }.execute();
                }});

        //Autoannotate for database collection
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control alt K"), "databaseAutoannotateAction");
        seqarea.getActionMap().put("databaseAutoannotateAction",
            new AbstractAction("databaseAutoannotateAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    new SwingWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            nse._seq.autoAnnotate(Collector.getCurrentUser());
                            return null;
                        }
                    }.execute();
                }});

        //Clear highlighting
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control shift K"), "clearAutoannotateAction");
        seqarea.getActionMap().put("clearAutoannotateAction",
            new AbstractAction("clearAutoannotateAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    searchHighlights = new HashSet<Highlight>();
                    nse._seq.removeAnnotations();
                }});

        //Clear search highlighting
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control shift F"), "clearSearchHighlights");
        seqarea.getActionMap().put("clearSearchHighlights",
            new AbstractAction("clearSearchHighlights") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    for(Highlight h: searchHighlights) {
                        seqarea.getHighlighter().removeHighlight(h);
                    }
                    searchHighlights = new HashSet<Highlight>();
                }});

        //Find/Replace
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control F"), "findAction");
        seqarea.getActionMap().put("findAction",
            new AbstractAction("findAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    findpop.setVisible(true);
                }});

        //Reverse complement the nucseq
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("control R"), "reverseComplementAction");
        seqarea.getActionMap().put("reverseComplementAction",
            new AbstractAction("reverseComplementAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("reverse complementing and redrawing");
                    nse._seq.revCompThis();
                }});

        //Paste sequence
        seqarea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK), "mypasteAction");
        seqarea.getActionMap().put("mypasteAction",
            new AbstractAction("mypasteAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("pasteAction requested");
                    String clipseq = util.getClipboardSequence();
                    seqarea.replaceSelection(clipseq);
                    String newseq = seqarea.getText();
                    nse._seq.changeSeq(newseq);
                }});

        seqarea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("focus lost");
                String newseq = seqarea.getText();
                nse._seq.changeSeq(newseq);
            }
        });

        //Auto color selected feature
        seqarea.getInputMap().put(KeyStroke.getKeyStroke("alt shift C"), "autoColorAction");
        seqarea.getActionMap().put("autoColorAction",
            new AbstractAction("autoColorAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("autoColorAction requested");
                    Color color = colorpop.getRandomColor();
                    System.out.println(color.getRGB());
                    receiveColor(color);
                }});

        seqarea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickResponder(evt);
            } });
        }

    /**
     * Puts the sequence of the NucSeq into the JTextArea and also
     * calls drawing of the features.  This is always called from the
     * OBO, so is on EDT
     */
    void initFeatures() {
        seqarea.getHighlighter().removeAllHighlights();
        searchHighlights.clear();
        sequence = nse._seq.toString();
        System.out.println("initfeatures1 seq: " + sequence.substring(0, Math.min(20, sequence.length())));

        seqarea.setText(sequence);

        HashSet<Annotation> annotations = nse._seq.getAnnotations();
        if(annotations.size()==0) {
            return;
        }
        for(final Annotation an: annotations) {
            try {
                System.out.println("Drawing annotation " + an.getName());
                highlightText(an.getStart(), an.getEnd(), an.getColor());
            } catch(Exception e) {
                System.out.println("Putting in annotations got an error");
            }
        }
    }

    public Highlight[] getHighlights() {
        return seqarea.getHighlighter().getHighlights();
    }


    private void mouseClickResponder(java.awt.event.MouseEvent evt) {
        if(colorpop.isVisible()) {
            colorpop.setVisible(false);
        }

        //If it was a right click
        if(evt.getModifiers()==4) {
            if(seqarea.getSelectedText()!=null) {
                Point position = evt.getLocationOnScreen();
                start = seqarea.getSelectionStart();
                end = seqarea.getSelectionEnd();
                colorpop.show(position);
                return;
            }
        }
                
        //If it was a control click, launch preferred feature viewer
        if(evt.getModifiers()==18) {
            for(Highlight h: seqarea.getHighlighter().getHighlights()) {
                alphaHighlighter ah = (alphaHighlighter) h.getPainter();
                Rectangle bounds = new Rectangle(ah.startX, ah.startY, ah.endX-ah.startX, ah.endY-ah.startY);
                if(bounds.contains(evt.getPoint())) {
                    System.out.println("sequencelight control click on highlighter " + ah.toString());
                }
            }
            return;
        }
    }

    public void receiveColor(Color color) {
        System.out.println("receiveColor receiving " + color.getRGB());
        try {
            if(start!=-1) {
                String name = JOptionPane.showInputDialog("How should I label the annotation?");
                new Annotation(name, nse._seq, color, color, start, end, Collector.getCurrentUser(), true, null);
                System.out.println("receiveColor made annotation " + color.getRGB() + " " + start + " " + end);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveFind(String searchstr, boolean matchcase, boolean alsorevcomp, boolean highlightmode) {
        start=0;
        end=1;
        while(true) {
            int newstart = sequence.indexOf(searchstr, start);
            if(start<0) {
                return;
            }
            if(start==newstart) {
                return;
            }
            start = newstart;
            end = start+searchstr.length();
            if(!highlightmode) {
                seqarea.select(start, end);
            }
        }
    }

    private void highlightText(final int start, final int end, final Color color) {
        final Highlighter lighter = seqarea.getHighlighter();
        final alphaHighlighter painter = new alphaHighlighter(color)  ;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                  lighter.addHighlight(start, end, painter);
                } catch (BadLocationException ble) {
                }

                if(color==searchColor) {
                    Highlight[] high = lighter.getHighlights();
                    searchHighlights.add(high[high.length-1]);
                    
                }
                if(iconpanel!=null) {
                    iconpanel.putInIcons();
                }
            }
        });
    }

    public Point getStartPoint(Highlight h) {
        alphaHighlighter ah = (alphaHighlighter) h.getPainter();
        return ah.getStart();
    }

    public Point getEndPoint(Highlight h) {
        alphaHighlighter ah = (alphaHighlighter) h.getPainter();
        return ah.getEnd();
    }

    private class alphaHighlighter extends DefaultHighlighter.DefaultHighlightPainter {
        private float transparency = 0.5f;
        private boolean initiated=false;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public alphaHighlighter(Color color) {
            super(color);
        }

        public Point getStart() {
            return new Point(startX, startY);
        }

        public Point getEnd() {
            return new Point(endX, endY);
        }

        @Override
        public Shape paintLayer(Graphics oldgraphics, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
            
            Color color = getColor();
            if(color==searchColor) {
                transparency = 1.0f;
            }
            Graphics2D g2 = (Graphics2D) oldgraphics.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, transparency));
            if (color == null) {
                g2.setColor(c.getSelectionColor());
            }
            else {
                g2.setColor(color);
            }
            if (offs0 == view.getStartOffset() &&
                offs1 == view.getEndOffset()) {
                // Contained in view, can just use bounds.
                Rectangle alloc;
                if (bounds instanceof Rectangle) {
                    alloc = (Rectangle)bounds;
                }
                else {
                    alloc = bounds.getBounds();
                }

                g2.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);

                //Fill in dimension information
                if(!initiated) {
                    startX = alloc.x;
                    startY = alloc.y;
                    initiated=true;
                }
                endX = alloc.x + alloc.width;
                endY = alloc.y + alloc.height;

                return alloc;
            }
            else {
                // Should only render part of View.
                try {
                    // --- determine locations ---
                    Shape shape = view.modelToView(offs0, Position.Bias.Forward,
                                                   offs1,Position.Bias.Backward,
                                                   bounds);
                    Rectangle r = (shape instanceof Rectangle) ?
                                  (Rectangle)shape : shape.getBounds();
                    g2.fillRect(r.x, r.y, r.width, r.height);

                    //Fill in dimension information
                    if(!initiated) {
                        startX = r.x;
                        startY = r.y;
                        initiated=true;
                    }
                    endX = r.x + r.width;
                    endY = r.y + r.height;

                    return r;
                } catch (BadLocationException e) {
                    // can't render
                }
            }
            // Only if exception
            g2.dispose();
            return null;
        }
        }


    /* SETTERS
     * */

    void setIconPanel(iconPanel ip) {
        iconpanel = ip;
    }

    /* PUTTERS
     * */

    /* GETTERS
     * */


/*-----------------
     variables
 -----------------*/
    private Timer timer;
    private Point lastPoint=new Point(0,0);
    private Boolean showingFeature=false;
    private ObjBaseObserver _obo;

    private int start = 0;
    private int end = 0;

    private JTextArea seqarea;
    private String sequence;
    private nucSeqEditor nse;
    private colorPopup colorpop;
    private findReplace findpop;
    private HashSet<Highlight> searchHighlights = new HashSet<Highlight>();
    public static final Color searchColor = new Color(70,255,70);
    private iconPanel iconpanel;
}
