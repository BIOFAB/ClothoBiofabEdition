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

package org.clothocad.wikieditorpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import net.iharder.dnd.FileDrop;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Attachment;
import org.clothocore.api.data.Attachment.AttachmentType;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.WikiText;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.clothocore.util.panels.CardPanel;
import org.openide.util.ImageUtilities;

/**
 * A wikiEditor Panel is a Swing container that is used to view and edit WikiText.
 * It will obey whatever postioning and location settings are imposed by the parent
 * container.  Just treat it like a JPanel, but you have to give it the parent Window
 * (most likely a JFrame or JWindow) and the WikiText object in its constructor.
 *
 * To change the colors, borders, or other visual aspects of it, you can extend
 * WikiEditorPanel and change those variable directly (they are protected access).
 *
 * @author J. Christopher Anderson
 */
public class WikiEditorPanel extends CardPanel {

    public WikiEditorPanel(final Window parent, final WikiText wiki_text) {
        _wt = wiki_text;
        _parent = parent;
        initComponents();
        createEmptyHTMLPage();
        switchModes();
        obo = new ObjBaseObserver() {
            @Override
            public void update(ObjBase obj, RefreshEvent evt) {
                if(obj==null) {
                    return;
                }
                if(evt.isCondition(RefreshEvent.Condition.WIKITEXT_CHANGED)) {
                    WikiText wt = (WikiText) obj;

                    //Refresh the wiki page
                    String currwiki = wikiArea.getText();
                    String realwiki = wt.getAsWikiText();
                    if(!realwiki.equals(currwiki)) {
                        wikiArea.setText(realwiki);
                    }

                    //Refresh the html page
                    String texty = wikiArea.getText();

                    if(!texty.equals(_wt.getAsWikiText())) {
                        _wt.changeTextAsWiki(texty);
                    }
                    if(texty.equals("")) {
                        htmlArea.setDocument(emptyHTMLPage);
                        htmlArea.setBackground(htmlEmpty);
                        htmlArea.setForeground(Color.WHITE);
                    } else {
                        htmlArea.setDocument(fullHTMLPage);
                        htmlArea.setText(_wt.getAsHTML());
                        htmlArea.setForeground(Color.BLACK);
                        htmlArea.setBackground(htmlBack);
                    }

                    //Determine if isChanged or not, change background color
                    if(!texty.equals(_wt.getAsWikiText())) {
                        if(!texty.equals("")) {
                            htmlArea.setBackground(htmlUnsaved);
                        }
                    }
                }
            }
        };
        _wt.isObservedBy(obo);
    }

    private void initComponents() {
        htmlPanel = new javax.swing.JPanel();
        htmlPanel.setLayout(new BorderLayout());
        htmlArea = new javax.swing.JEditorPane("text/html", _wt.getAsHTML());
        htmlScrollPane = new javax.swing.JScrollPane();
        htmlScrollPane.setViewportView(htmlArea);
        htmlScrollPane.setBorder(loweredbevel);
        htmlArea.setBackground(htmlBack);
        htmlArea.setEditable(false);
        htmlArea.setToolTipText("Type ctrl-E or double-click to edit, or ctrl-H for help");
        htmlArea.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        htmlArea.setFont(new Font("Arial", Font.PLAIN, 12));
        fullHTMLPage = (HTMLDocument) htmlArea.getDocument();
        htmlArea.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent event) {
                if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    //If it's a Media link (a link to a file)
                    if(event.getDescription().indexOf("Media:")>0) {
                        String url = event.getDescription();
                        int start = url.indexOf("Media:") + 6;
                        final String fileName = url.substring(start);
                        new Thread() {
                            @Override
                            public void run() {
                                launchFile(fileName);
                            }
                        }.start();

                    //If it's an ObjBase link
                    } else if(event.getDescription().indexOf("Link:")>0) {
                        try {
                            String url = event.getDescription();
                            String[] words = url.split(":");
                            String stype = words[1];
                            ObjType type = ObjType.valueOf(stype);
                            String uuid = words[2];
                            ObjBase obj = Collector.get(type, uuid);
                            obj.launchDefaultViewer();
                        } catch(Exception e) {
                        }

                    //If it's not media or link, it's probably a URL, launch it in browser
                    } else {
                        try {
                            URL url = new URL(event.getURL().toString());
                            BareBonesBrowserLaunch.openURL(event.getURL().toString());
                        } catch (Exception e) {
                        }
                    }
                }
            }});

        //Listen for mouse double clicks
        htmlArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //If it was a double-click
                if(evt.getClickCount()==2) {
                    switchModes();
                }
            }});

        //Listen for ctrl-E to switch to wiki mode
        htmlArea.getInputMap().put(KeyStroke.getKeyStroke("control E"), "convertToWikiAction");
        htmlArea.getActionMap().put("convertToWikiAction",
            new AbstractAction("convertToWikiAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    switchModes();
                }});

        //Listen for ctrl-S from either card and save the edited wikitext in response
        htmlArea.getInputMap().put(KeyStroke.getKeyStroke("control S"), "saveWikiAction");
        htmlArea.getActionMap().put("saveWikiAction",
            new AbstractAction("saveWikiAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    saveWikiText();
                }});

        //Listen for ctrl-H from wiki card and launch help
        htmlArea.getInputMap().put(KeyStroke.getKeyStroke("control H"), "getHelpAction");
        htmlArea.getInputMap().put(KeyStroke.getKeyStroke("F1"), "getHelpAction");
        htmlArea.getActionMap().put("getHelpAction",
            new AbstractAction("getHelpAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    String url = Collator.helpURLBase + "/WikiText_Editor";
                    BareBonesBrowserLaunch.openURL(url);
                }});

        htmlPanel.add(htmlScrollPane, BorderLayout.CENTER);

        wikiPanel = new javax.swing.JPanel();
        wikiPanel.setLayout(new BorderLayout());
        wikiScrollPane = new javax.swing.JScrollPane();
        wikiArea = new javax.swing.JTextArea();
        wikiArea.setLineWrap(true);
        wikiArea.setWrapStyleWord(true);
        wikiArea.setText(_wt.getAsWikiText());
        wikiScrollPane.setViewportView(wikiArea);
        wikiScrollPane.setBorder(loweredbevel);
        wikiArea.setBackground(wikiBack);
        wikiArea.setForeground(wikiTextColor);
        wikiArea.setEditable(true);
        wikiArea.setToolTipText("Type ctrl-P to return to html view or ctrl-H for help");
        wikiArea.setFont(new Font("Monospaced", Font.BOLD, 13));

        //When focus is lost update the wikiText object
        wikiArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(_wt==null) {
                    return;
                }
                String oldtext = _wt.getAsWikiText();
                String newText = wikiArea.getText();
                if(!newText.equals(oldtext)) {
                    _wt.changeTextAsWiki(newText);
                }
            }
        } );

        //Listen for ctrl-P to switch to html mode
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control P"), "convertToHtmlAction");
        wikiArea.getActionMap().put("convertToHtmlAction",
            new AbstractAction("convertToHtmlAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    switchModes();
                }});

        //Listen for ctrl-S from either card and save the edited wikitext in response
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control S"), "saveWikiAction");
        wikiArea.getActionMap().put("saveWikiAction",
            new AbstractAction("saveWikiAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    _wt.saveDefault();
                    htmlArea.setBackground(htmlBack);
                }});

        //Listen for ctrl-H from wiki card and launch help
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control H"), "getHelpAction");
        wikiArea.getActionMap().put("getHelpAction",
            new AbstractAction("getHelpAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    String url = Collator.helpURLBase + "/WikiText_Editor";
                    BareBonesBrowserLaunch.openURL(url);
                }});

        //Add a file dropped-into-window listener
        new  FileDrop( wikiArea, new FileDrop.Listener()
        {   @Override
        public void  filesDropped( java.io.File[] files ) {
              for(int i=0; i< files.length; i++) {
                inputImage(files[i]);
              }
          }
        });

        //Listen for ctrl-Shift-v from wiki card and paste image
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "pasteImageAction");
        wikiArea.getActionMap().put("pasteImageAction",
            new AbstractAction("pasteImageAction") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                getImageFromClipboard();
            }});

        //Listen for ctrl-c from wiki card and copy selected text
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control C"), "copyTextAction");
        wikiArea.getActionMap().put("copyTextAction",
            new AbstractAction("copyTextAction") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    String selected = wikiArea.getSelectedText();
                    StringSelection ss = new StringSelection(selected);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
                }});

        //Listen for ctrl-x from wiki card and cut selected text
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control X"), "cutTextAction");
        wikiArea.getActionMap().put("cutTextAction",
            new AbstractAction("cutTextAction") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new SwingWorker() {
                        //VARIABLES:
                        String out;
                        @Override
                        protected Object doInBackground() throws Exception {
                            String selected = wikiArea.getSelectedText();
                            String original = wikiArea.getText();
                            int start = original.indexOf(selected);
                            out = original.substring(0,start) + original.substring(start+selected.length());
                            StringSelection ss = new StringSelection(selected);
                            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
                            return null;
                        }

                        @Override
                        protected void done() {
                             _wt.changeTextAsWiki(out);
                        }
                    }.execute();
                }});

        //Listen for ctrl-v from wiki card and paste text
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control V"), "pasteTextAction");
        wikiArea.getActionMap().put("pasteTextAction",
            new AbstractAction("pasteTextAction") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new SwingWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            String original = wikiArea.getText();
                            int start = wikiArea.getCaretPosition();
                            String out = original.substring(0,start) + getClipboardString() + original.substring(start);
                            _wt.changeTextAsWiki(out);
                            return null;
                        }
                    }.execute();
                }});

        //<<<<<<<<<  Section on Undo/Redo for WikiArea >>>>>>>>>
        final UndoManager undo = new UndoManager();
        Document doc = wikiArea.getDocument();

        // Listen for undo and redo events
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent evt) {
                undo.addEdit(evt.getEdit());
            }
        });

        // Create an undo action and add it to the text component
        wikiArea.getActionMap().put("Undo",
            new AbstractAction("Undo") {
                    @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (undo.canUndo()) {
                            undo.undo();
                        }
                    } catch (CannotUndoException e) {
                    }
                }
           });

        // Bind the undo action to ctl-Z
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

        // Create a redo action and add it to the text component
        wikiArea.getActionMap().put("Redo",
            new AbstractAction("Redo") {
                    @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        if (undo.canRedo()) {
                            undo.redo();
                        }
                    } catch (CannotRedoException e) {
                    }
                }
            });

        // Bind the redo action to ctl-Y
        wikiArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
        //<<<<<<<<<  END UNDO/REDO >>>>>>>>>

        wikiPanel.add(wikiScrollPane, BorderLayout.CENTER);

         
         add(htmlPanel);
         add(wikiPanel);
    }

    public void saveWikiText() {
        obo.update(_wt, new RefreshEvent(_wt, RefreshEvent.Condition.WIKITEXT_CHANGED));
        _wt.saveDefault();
        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                htmlArea.setBackground(htmlBack);
            }
        });
        setHTMLMode();
    }

    public void clear() {
        _wt = new WikiText("");
        _wt.setTransient();
        setHTMLMode();
    }

    public WikiText getWikiText() {
        return _wt;
    }

    private void createEmptyHTMLPage() {

        //File file = new File(Attachment.cacheDir.getAbsolutePath() + "\\" + "pencilandpaper.png");
        File file = new File(Attachment.cacheDir.getAbsolutePath() + File.separator + "pencilandpaper.png");
        
        if(!file.exists()) {
            BufferedImage img = (BufferedImage) ImageUtilities.loadImage("org/clothocad/wikieditorpanel/pencilandpaper.png", false);
            if(img!=null) {
                try {
                    ImageIO.write(img, "png", file);
                } catch (IOException ex) {
                }
            }
        }
        String imagelink = file.getAbsolutePath();

        JEditorPane pane = new JEditorPane("text/html", "<html>\n<body>\n<p align=\"center\">&nbsp;</p>\n<p align=\"center\">&nbsp;</p>\n<p align=\"center\"><img src=\"file:\\"  + File.separator + imagelink + "\" width=\"110\" height=\"87\" alt=\"doubleClick\" /></p>\n<p align=\"center\">&nbsp;</p>\n<p align=\"center\" class=\"style1\">Double click to add text.</p>\n</body>\n</html>");

        pane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        pane.setFont(new Font("Arial", Font.PLAIN, 12));
        pane.setBackground(htmlEmpty);
        pane.setForeground(new Color(255,255,255));

        emptyHTMLPage = (HTMLDocument) pane.getDocument();
    }

    public void setHTMLMode() {
        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                showCard(htmlPanel);
                htmlArea.requestFocus();
                _editMode = false;
            }
        });
    }

    public void setEditMode() {
        SwingUtilities.invokeLater(new Runnable() {
        @Override
            public void run() {
                showCard(wikiPanel);
                wikiArea.requestFocus();
                wikiArea.setCaretPosition(wikiArea.getText().length());
                _editMode = true;
            }
        });
    }
    
    private void switchModes() {
        if(_editMode) {
            setHTMLMode();
        } else {
            setEditMode();
        }
        _editMode = !_editMode;
    }

    /**
     * Download and launch the file associated with the WikiText
     * that is named filename
     *
     * @param filename String of the file Name in the WikiText
     */
    public static void launchFile(String filename) {
        File afile = new File("cache\\" + filename);
        String end = filename.split("\\.")[1];

        if(!afile.exists()) {
            System.out.println("File " + afile.getAbsolutePath() +" not there");
            return;
        }
        try {
            String[] commands = {"cmd", "/c", "start", "\"DummyTitle\"", afile.getAbsolutePath()};
            Runtime.getRuntime().exec(commands);
        } catch (IOException ex) {
        }
    }

    /**
     * Starting with a File, turn it into a saved file and WikiText flag
     * @param file
     */
    private void inputImage(File file) {
        String filepath = file.getName();
        if(filepath.endsWith(".png") ||  filepath.endsWith(".PNG") ) {
            Attachment att = new Attachment(file, file.getName(), AttachmentType.PNG);
            _wt.addObject(att);
            _wt.appendTextAsWiki("[[Image:" + filepath + "]]");

            return;
        }
        if(filepath.endsWith(".jpg") ||  filepath.endsWith(".JPG") ||  filepath.endsWith(".gif") ||  filepath.endsWith(".GIF") ) {
            //CONVERT THEM TO PNG THEN MAKE ATTACHMENT AND ADD IT
            return;
        }
        if(filepath.endsWith(".xls") ||  filepath.endsWith(".xlsx") ||  filepath.endsWith(".csv") ) {
            Attachment att = new Attachment(file, file.getName(), AttachmentType.EXCEL);
            _wt.addObject(att);
            _wt.appendTextAsWiki("[[Media:" + filepath + "]]");
            return;
        }
        if(filepath.endsWith(".gb") ) {
            Attachment att = new Attachment(file, file.getName(), AttachmentType.GB);
            _wt.addObject(att);
            _wt.appendTextAsWiki("[[Media:" + filepath + "]]");
            return;
        }
        if(filepath.endsWith(".pdf") ) {
            Attachment att = new Attachment(file, file.getName(), AttachmentType.PDF);
            _wt.addObject(att);
            _wt.appendTextAsWiki("[[Media:" + filepath + "]]");
            return;
        }
        if(filepath.endsWith(".ab1") ) {
            Attachment att = new Attachment(file, file.getName(), AttachmentType.ABI);
            _wt.addObject(att);
            _wt.appendTextAsWiki("[[Media:" + filepath + "]]");
            return;
        }

        Attachment att = new Attachment(file, file.getName(), AttachmentType.OTHER);
        _wt.addObject(att);
        _wt.appendTextAsWiki("[[Media:" + filepath + "]]");
        return;
    }

    /**
     * Get an image off the system clipboard.
     * @return returns the image if successful, otherwise null
     */
    private boolean getImageFromClipboard() {
        //Request filename from user
        String pngname = JOptionPane.showInputDialog("What do you want to call this image?");
        if(pngname==null) {
            return false;
        }
        String filename = Attachment.cacheDir.getAbsolutePath() + "\\" + pngname + ".png";
        File localfile = new File(filename);
        File tempfile = new File(Attachment.cacheDir.getAbsolutePath() + "\\temporary11111.png");

        //Pull image off the clipboard and save to file
        Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if(content==null){
            System.err.println("error: nothing found in clipboard");
            return false;
        }
        if(!content.isDataFlavorSupported(DataFlavor.imageFlavor)){
            System.err.println("error: no image found in clipbaord");
            return false;
        }
        try {
            BufferedImage img = (BufferedImage) content.getTransferData(DataFlavor.imageFlavor);
            ImageIO.write(img,"png",tempfile);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("WEP image copied to: " + tempfile.getAbsolutePath());

        //Make the Attachment and put in wikitext
        Attachment att = new Attachment(tempfile, localfile.getName(), AttachmentType.PNG);
        _wt.addObject(att);
        _wt.appendTextAsWiki("[[Image:" + localfile.getName() + "]]");
        tempfile.delete();
        return true;
    }

    /**
     * Pull a String from the system clipboard
     * @return The String form the clipboard
     */
    private String getClipboardString() {
        String result = "";
        Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        boolean hasTransferableText =
          (contents != null) &&
          contents.isDataFlavorSupported(DataFlavor.stringFlavor)
        ;
        if ( hasTransferableText ) {
          try {
            result = (String)contents.getTransferData(DataFlavor.stringFlavor);
          }
          catch (UnsupportedFlavorException ex){
            System.out.println(ex);
          }
          catch (IOException ex) {
            System.out.println(ex);
          }
      }
        return result;
      }

    public void removeHtmlBorders() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                htmlArea.setBorder(null);
                htmlScrollPane.setBorder(null);
                htmlPanel.setBorder(null);
            }
        });
    }

    public void removeWikiBorders() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                wikiArea.setBorder(null);
                wikiScrollPane.setBorder(null);
                wikiPanel.setBorder(null);
            }
        });
    }

    public JEditorPane getTopContainer() {
        return htmlArea;
    }
/*-----------------
     variables
 -----------------*/
    protected WikiText _wt;
    protected ObjBaseObserver obo;
    private boolean _editMode = false;
    private HTMLDocument emptyHTMLPage;
    private HTMLDocument fullHTMLPage;

    protected Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    protected javax.swing.JComponent _myContainer;
    protected javax.swing.JTextArea wikiArea;
    protected javax.swing.JEditorPane htmlArea;
    protected javax.swing.JScrollPane htmlScrollPane;
    protected javax.swing.JScrollPane wikiScrollPane;
    protected javax.swing.JPanel wikiPanel;
    protected javax.swing.JPanel htmlPanel;
    private Window _parent;

    protected Color wikiBack = new Color(224, 223, 211);
    protected Color htmlBack = new Color(255, 255, 255);
    protected Color htmlEmpty = new Color(35, 48, 64);
    protected Color wikiTextColor = new Color(32, 123, 214);
    protected Color htmlUnsaved = new Color(242, 230, 208);

}
