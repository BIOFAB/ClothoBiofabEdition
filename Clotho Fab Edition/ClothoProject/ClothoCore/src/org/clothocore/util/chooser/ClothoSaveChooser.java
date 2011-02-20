/* This is the connector for the FeaturesLibrary function.
 * 
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


package org.clothocore.util.chooser;

import java.io.*;
import org.clothocore.util.dialog.ClothoDialogBox;
//import org.jdesktop.swingbinding.SwingBindings;

/**
 *
 * @author Nade Sritanyaratana
 */
public class ClothoSaveChooser {
/**
 * Constructor for Save Chooser Connection. Instantiates a Save Chooser Frame
 * simultaneously, but will not invoke openWindow until prompted by programmer
 * (see openWindow).
 * @param d
 * @see #setString
 * @see #openWindow
 * @see #fileSelected
 * @see #overwriteSaveFile(java.lang.String)
 */
    public ClothoSaveChooser(String d) {
        _saveFrame = new ClothoSaveChooserFrame(d, this);
        _stringToFile = "";
        fileSelected = false;
        _saveFrame.getChooser().setCurrentDirectory(new File("."));
        _saveFrame.getChooser().setDialogTitle("Clotho: Save..");
    }
    
    /**
     * Adds a FileFilter to the GUI. This also hides all other files not matching
     * the specified extension.
     * @param description
     * @param extension
     */
    public void addFilter(String description, String... extension) {
        _fileFilter = new javax.swing.filechooser.FileNameExtensionFilter(
                description, extension);
        _saveFrame.getChooser().addChoosableFileFilter(_fileFilter);
    }
    
    /**
     * used with SaveChooserFrame to respond to the cancel button.
     * @param browseFileChooser
     */
    public void cancelButtonSelected(javax.swing.JFileChooser browseFileChooser) {
        fileSelected = false;
        _saveFrame.dispose();
    }
    
    /**
     * Returns the JFileChooser from ClothoSaveChooserFrame
     * @return javax.swing.JFileChooser The JFileChooser as mentioned above
     */
    public javax.swing.JFileChooser getFileChooser() {
        return _saveFrame.getChooser();
    }
    
    /**
     * Returns the selected FileFilter after the user selects and clicks save.
     * @return fileFilter
     */
    public javax.swing.filechooser.FileFilter getFileFilter() {
        return _saveFrame.getChooser().getFileFilter();
    }
    
    public File getSaveFile(){
        return _saveToFile;
    }
    
    /**
     * Opens the savedialog box. To be used after setString unless programmer would
     * like to set an empty string into a given file. Returns the int that is normally
     * returned from javax.swing.JFileChooser.showSaveDialog. Three possible ints
     * are returned, one if accept button is chosen, another is cancel is chosen,
     * and another if the window is disposed (called javax.swing.JFileChooser.ERROR_OPTION)
     * @see #setString
     * @see javax.swing.JFileChooser#showSaveDialog
     */
    public int openWindow() {
        return _saveFrame.getChooser().showSaveDialog(null);
    }
    
    /**
     * After openWindow(). Used to overwrite the file that was set from setString().
     * Meant for use in the case where user could select different filefilters
     * and thus save in different formats.
     */
    public void overwriteSaveFile(String stringToSave) {
        _saveToFile.delete();
        try {
            _saveToFile.createNewFile();
            _out = new BufferedWriter(new FileWriter(_saveToFile));
            _out.write(stringToSave);
            _out.close();}
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    /**
     * Renames the file to be saved to.
     * (Note: must be called *before* overwriteSaveFile in order to work
     * appropriately)
     * 
     * @param newName The new name for the file
     */
    
    public void renameSaveFile(String newName) {
        //File newFile = new File(_saveToFile.getParent() + "\\" + newName);
        File newFile = new File(_saveToFile.getParent() + "/" + newName);
        _saveToFile.delete();
        _saveToFile = newFile;
    }
    
     /**
     * Sets the current FileFilter as the "Accept all File Filter. In Window's case,
     * this would mean the "All Files" filter.
     */
    public void setAsAllFileFilter() {
        _saveFrame.getChooser().setFileFilter(_saveFrame.getChooser().getAcceptAllFileFilter());
    }
    
    /**
     * used with SaveChooserFrame to respond to the approve button.
     * @param browseFileChooser
     */
    public void saveButtonSelected(javax.swing.JFileChooser browseFileChooser){
        _saveToFile = browseFileChooser.getSelectedFile();
        try {
            _unoccupied = _saveToFile.createNewFile();
        }
        catch(IOException e) {
            _dialogBox = new ClothoDialogBox("SaveChooser Error", "Cannot " +
                    "create file in selected directory.");
            _dialogBox.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!_unoccupied) {
            _dialogBox = new ClothoDialogBox("Existing File", "File currently " +
                    "exists. Overwrite?");
            if (_dialogBox.show_optionDialog(javax.swing.JOptionPane.
                    YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) ==
                    javax.swing.JOptionPane.YES_OPTION) {
                _saveToFile.delete();
                try{_saveToFile.createNewFile();}
                catch(IOException e) {}
            }
            else
                return;
        }
        try {
            _out = new BufferedWriter(new FileWriter(_saveToFile));
            _out.write(_stringToFile);
            _out.close();}
        catch (IOException e) { }
        fileSelected = true;
        _saveFrame.getChooser().setCurrentDirectory(_saveToFile);
        _saveFrame.dispose();
    }

    /**
     * To be used BEFORE openWindow. This sets the specified string to be set 
     * into the filepath given by the savedialog box. If setString is not invoked
     * before openWindow, an empty string will be saved into the file.
     * @param string the string to write into the file
     * @see #openWindow
     */
    public void setString(String string) {
        _stringToFile = string;
        _stringToFile = _stringToFile.replaceAll("\n",System.
                getProperty("line.separator"));
    }
    
    /**
     * Sets the title of the JFileChooser.
     * @param title
     */
    public void setTitle(String title) {
        _saveFrame.getChooser().setDialogTitle(title);
    }
    
    /**
     * returns true if a file was selected; returns false if not.
     */
    public boolean fileSelected;
    
    private boolean _unoccupied;
    private BufferedWriter _out;
    private ClothoDialogBox _dialogBox;
    private ClothoSaveChooserFrame _saveFrame;
    private File _saveToFile;
    private String _stringToFile;
    private javax.swing.filechooser.FileNameExtensionFilter _fileFilter;
}