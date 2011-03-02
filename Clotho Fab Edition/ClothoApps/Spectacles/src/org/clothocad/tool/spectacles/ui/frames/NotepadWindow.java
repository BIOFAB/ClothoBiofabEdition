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

/*
 * NotepadWindow.java
 *
 * Created on Feb 24, 2009, 2:26:03 AM
 */

package org.clothocad.tool.spectacles.ui.frames;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

/**
 * NotepadWindow is the Eugene file editor for Spectacles.
 * WorkspaceFrame can import Eugene code that is
 * present in the editor.
 * @author brian
 * @author Rich
 */
public class NotepadWindow extends javax.swing.JFrame {

    /**
     * Creates a new NotepadWindow.
     */
    public NotepadWindow(WorkspaceFrame wsFrame) {
        initComponents();
        _wsFrame = wsFrame;
        _docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            public void changedUpdate(DocumentEvent e) {
            }
        };
        newNote();
        _isModified = false;
    }

    /**
     * Tries to close this notepad. Returns <code>true</code> if successful,
     * and <code>false</code> if the user cancels on a save prompt.
     * @return <code>true</code> if closing is successful, <code>false</code> if closing is cancelled.
     */
    public boolean closeNotepad() {
        if (_isModified) {
            int option = promptSave();
            if (option == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }
        dispose();
        return true;
    }

    /**
     * Opens a new note, and brings up a prompt if there
     * is an unsaved file open.
     */
    public void newNote() {
        if (_isModified) {
            int prompt = promptSave();
            if (prompt == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (prompt == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        noteText.getDocument().removeDocumentListener(_docListener);
        setModified(false);
        _fileName = "";
        noteText.setText("");
        this.setTitle("Unsaved Eugene File");
        noteText.getDocument().addDocumentListener(_docListener);
    }

    /**
     * Returns the current file's text as a string.
     * @return the current file's text.
     */
    public String getNotepadText() {
        return noteText.getText();
    }

    /**
     * Loads the text into the current file.
     * Overwrites any pre-existing text.
     * @param text the text to be loaded.
     */
    public void setNotepadText(String text) {
        noteText.setText(text);
        noteText.setCaretPosition(0);
        setModified(true);
    }

    /**
     * Returns the current file's modification status.
     * @return <code>true</code> if the current file has been modified,
     * and <code>false</code> otherwise.
     */
    public boolean isModified() {
        return _isModified;
    }

    /**
     * Set the current file's modified status. This also affects the window title;
     * an asterisk will appear if there are unsaved changes.
     * @param modified <code>true</code> to indicate the current file is modified,
     * <code>false</code> otherwise.
     */
    public void setModified(boolean modified) {
        if (modified && !isModified() && !this.getTitle().startsWith("*")) {
            this.setTitle("*" + this.getTitle());
        } else if (!modified && isModified() && this.getTitle().startsWith("*")) {
            this.setTitle(this.getTitle().substring(1));
        }
        _isModified = modified;
    }

    /**
     * Brings up a file chooser and opens the selected file.
     */
    protected void openFile() {
        if (isModified()) {
            int prompt = promptSave();
            if (prompt == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (prompt == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        if (JFileChooser.APPROVE_OPTION == _eugFileChooser.showOpenDialog(this)) {
            File f = _eugFileChooser.getSelectedFile();
            try {
                String newText = "";
                BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                String line;
                while ((line = reader.readLine()) != null) {
                    newText += line + "\n";
                }
                noteText.setText(newText);
                noteText.setCaretPosition(0);
                this.setTitle(f.getName());
                _fileName = f.getAbsolutePath();

                reader.close();
                setModified(false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NotepadWindow.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.toString());
            } catch (IOException ex) {
                Logger.getLogger(NotepadWindow.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * Saves the current file. If the file is new, this method
     * brings up a file chooser and saves to the specified file.
     */
    protected void saveFile() {
        if (_fileName.equals("")) {
            if (JFileChooser.APPROVE_OPTION == _eugFileChooser.showSaveDialog(this)) {
                File f = _eugFileChooser.getSelectedFile();
                _fileName = f.getAbsolutePath();
                if (!_fileName.endsWith(".eug")) {
                    _fileName += ".eug";
                }
                this.setTitle(f.getName());
                try {
                    FileWriter fw = new FileWriter(_fileName);
                    fw.write(noteText.getText());
                    fw.close();
                    setModified(false);
                } catch (IOException ex) {
                    Logger.getLogger(NotepadWindow.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.toString());
                    _fileName = "";
                }
            }
        } else {
           try {
                FileWriter fw = new FileWriter(_fileName);
                fw.write(noteText.getText());
                fw.close();
                setModified(false);
            } catch (IOException ex) {
                Logger.getLogger(NotepadWindow.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.toString());
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contextMenu = new javax.swing.JPopupMenu();
        contextMenuCut = new javax.swing.JMenuItem();
        contextMenuCopy = new javax.swing.JMenuItem();
        contextMenuPaste = new javax.swing.JMenuItem();
        contextMenuSelectAll = new javax.swing.JMenuItem();
        noteTextJSP = new javax.swing.JScrollPane();
        noteText = new javax.swing.JTextArea();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileMenuNew = new javax.swing.JMenuItem();
        fileMenuOpen = new javax.swing.JMenuItem();
        fileMenuSave = new javax.swing.JMenuItem();
        fileMenuSaveAs = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editMenuCut = new javax.swing.JMenuItem();
        editMenuCopy = new javax.swing.JMenuItem();
        editMenuPaste = new javax.swing.JMenuItem();
        editMenuSelectAll = new javax.swing.JMenuItem();

        contextMenuCut.setMnemonic('u');
        contextMenuCut.setText("Cut");
        contextMenuCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contextMenuCutActionPerformed(evt);
            }
        });
        contextMenu.add(contextMenuCut);

        contextMenuCopy.setMnemonic('c');
        contextMenuCopy.setText("Copy");
        contextMenuCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contextMenuCopyActionPerformed(evt);
            }
        });
        contextMenu.add(contextMenuCopy);

        contextMenuPaste.setMnemonic('p');
        contextMenuPaste.setText("Paste");
        contextMenuPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contextMenuPasteActionPerformed(evt);
            }
        });
        contextMenu.add(contextMenuPaste);

        contextMenuSelectAll.setMnemonic('a');
        contextMenuSelectAll.setText("Select All");
        contextMenuSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contextMenuSelectAllActionPerformed(evt);
            }
        });
        contextMenu.add(contextMenuSelectAll);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Notepad");
        setMinimumSize(new java.awt.Dimension(250, 160));
        setName("NotepadFrame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        noteText.setColumns(20);
        noteText.setRows(5);
        noteText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noteTextMouseClicked(evt);
            }
        });
        noteTextJSP.setViewportView(noteText);

        fileMenu.setText("File");

        fileMenuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuNew.setMnemonic('n');
        fileMenuNew.setText("New");
        fileMenuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuNewActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuNew);

        fileMenuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuOpen.setMnemonic('o');
        fileMenuOpen.setText("Open");
        fileMenuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuOpenActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuOpen);

        fileMenuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileMenuSave.setMnemonic('s');
        fileMenuSave.setText("Save");
        fileMenuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuSaveActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuSave);

        fileMenuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileMenuSaveAs.setMnemonic('a');
        fileMenuSaveAs.setText("Save As");
        fileMenuSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuSaveAsActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuSaveAs);

        mainMenuBar.add(fileMenu);

        editMenu.setText("Edit");

        editMenuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        editMenuCut.setMnemonic('u');
        editMenuCut.setText("Cut");
        editMenuCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuCutActionPerformed(evt);
            }
        });
        editMenu.add(editMenuCut);

        editMenuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        editMenuCopy.setMnemonic('c');
        editMenuCopy.setText("Copy");
        editMenuCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuCopyActionPerformed(evt);
            }
        });
        editMenu.add(editMenuCopy);

        editMenuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        editMenuPaste.setMnemonic('p');
        editMenuPaste.setText("Paste");
        editMenuPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuPasteActionPerformed(evt);
            }
        });
        editMenu.add(editMenuPaste);

        editMenuSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        editMenuSelectAll.setMnemonic('a');
        editMenuSelectAll.setText("Select All");
        editMenuSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuSelectAllActionPerformed(evt);
            }
        });
        editMenu.add(editMenuSelectAll);

        mainMenuBar.add(editMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(noteTextJSP, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(noteTextJSP, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileMenuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuNewActionPerformed
        newNote();
}//GEN-LAST:event_fileMenuNewActionPerformed

    private void fileMenuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuOpenActionPerformed
        openFile();
}//GEN-LAST:event_fileMenuOpenActionPerformed

    private void fileMenuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuSaveActionPerformed
        saveFile();
}//GEN-LAST:event_fileMenuSaveActionPerformed

    private void editMenuCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuCutActionPerformed
        noteText.cut();
    }//GEN-LAST:event_editMenuCutActionPerformed

    private void editMenuCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuCopyActionPerformed
        noteText.copy();
    }//GEN-LAST:event_editMenuCopyActionPerformed

    private void editMenuPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuPasteActionPerformed
        noteText.paste();
    }//GEN-LAST:event_editMenuPasteActionPerformed

    private void noteTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noteTextMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1) {
            contextMenu.show(noteText, evt.getX(), evt.getY());
        }
}//GEN-LAST:event_noteTextMouseClicked

    private void contextMenuCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contextMenuCutActionPerformed
        noteText.cut();
    }//GEN-LAST:event_contextMenuCutActionPerformed

    private void contextMenuCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contextMenuCopyActionPerformed
        noteText.copy();
    }//GEN-LAST:event_contextMenuCopyActionPerformed

    private void contextMenuPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contextMenuPasteActionPerformed
        noteText.paste();
    }//GEN-LAST:event_contextMenuPasteActionPerformed

    private void fileMenuSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuSaveAsActionPerformed
        String tempFileName = _fileName;
        _fileName = "";
        saveFile();
        if (_fileName.equals("")) {
            _fileName = tempFileName;
        }
    }//GEN-LAST:event_fileMenuSaveAsActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (_wsFrame == null) {
            dispose();
            System.exit(0);
        } else {
            _wsFrame.exit();
        }
    }//GEN-LAST:event_formWindowClosing

    private void editMenuSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuSelectAllActionPerformed
        noteText.selectAll();
    }//GEN-LAST:event_editMenuSelectAllActionPerformed

    private void contextMenuSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contextMenuSelectAllActionPerformed
        noteText.selectAll();
    }//GEN-LAST:event_contextMenuSelectAllActionPerformed



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotepadWindow(null).setVisible(true);
            }
        });
    }

    private int promptSave() {
        int option = JOptionPane.showConfirmDialog(
            this, "Save this file?", "Save",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        return option;
    }

    private boolean _isModified;
    private DocumentListener _docListener;
    private String _fileName;
    private WorkspaceFrame _wsFrame;
    private static JFileChooser _eugFileChooser;
    {
        _eugFileChooser = new JFileChooser();
        _eugFileChooser.setAcceptAllFileFilterUsed(false);
        _eugFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) { // Handles which files are allowed by filter.
                // Since this is used during enumeration of existing file system,
                // this should not be necessary, but good practice to test for null.
                if (f != null) {
                    if (f.isDirectory()) { // Allow directories to be seen.
                        return true;
                    }
                    // Get file extension and test if should be allowed.
                    String extension = getExtension(f);
                    if (extension != null) {
                        return ((extension.equalsIgnoreCase("eug")) ? true : false);
                    }
                }
                return false;
            }

            @Override
            public String getDescription() { // 'Files of Type' description
                return "*.eug";
            }

            // Takes a java.io.File object, parses file extension, and returns as java.lang.String.
            String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');
                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                return ext;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu contextMenu;
    private javax.swing.JMenuItem contextMenuCopy;
    private javax.swing.JMenuItem contextMenuCut;
    private javax.swing.JMenuItem contextMenuPaste;
    private javax.swing.JMenuItem contextMenuSelectAll;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuCopy;
    private javax.swing.JMenuItem editMenuCut;
    private javax.swing.JMenuItem editMenuPaste;
    private javax.swing.JMenuItem editMenuSelectAll;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMenuNew;
    private javax.swing.JMenuItem fileMenuOpen;
    private javax.swing.JMenuItem fileMenuSave;
    private javax.swing.JMenuItem fileMenuSaveAs;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JTextArea noteText;
    private javax.swing.JScrollPane noteTextJSP;
    // End of variables declaration//GEN-END:variables
}
