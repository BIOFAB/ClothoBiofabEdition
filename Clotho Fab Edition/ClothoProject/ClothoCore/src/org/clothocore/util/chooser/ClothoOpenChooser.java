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

/**
 *
 * @author Nade Sritanyaratana
 */
public class ClothoOpenChooser {
/** 
 * Constructor for ClothoOpenChooser.
 * @param d Description
 */
    public ClothoOpenChooser(String d) {
        //_openFrame = new ClothoOpenChooserFrame("Open Chooser Frame", this);
        _openFrame = new ClothoOpenChooserFrame(d, this);
        fileSelected = false;
        /* Matt: Added an instantiation for _file so that "getFile" won't return
         * a null pointer exception if called in the code after the user
         *  presses cancel
         */
        _file = new File("");
        //_openFrame.getChooser().setCurrentDirectory(new File("C:\\"));

        _openFrame.getChooser().setCurrentDirectory(new File( System.getProperty("user.home") ));
        
        _openFrame.getChooser().setDialogTitle("Clotho: Browse...");
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
        _openFrame.getChooser().addChoosableFileFilter(_fileFilter);
    }
    
    /** 
     * Used by OpenChooserFrame to designate the action to the cancel button
     * @param browseFileChooser
     */
    public void cancelButtonSelected(javax.swing.JFileChooser browseFileChooser) {
        _currentDirectory = browseFileChooser.getCurrentDirectory();
        fileSelected = false;
        _openFrame.dispose();
    }
    
    /** 
     * Used by OpenChooserFrame to designate the action to the approve button
     * @param browseFileChooser
     */
    public void openButtonSelected(javax.swing.JFileChooser browseFileChooser){
        _file = browseFileChooser.getSelectedFile();
        _currentDirectory = browseFileChooser.getCurrentDirectory();
        browseFileChooser.setCurrentDirectory(_file);
        fileSelected = true;
        _openFrame.dispose();
    }
    
    /** 
     * Returns the file chosen from the opendialog box
     * @return file
     * @see #open_Window()
     */
    public File getFile() {
        return _file;
    }

    /** 
     * Returns the Current Directory
     * @return filepath
     * @see #getFile()
     * @see #open_Window()
     */
    public File getCurrentDirectory() {
        return _currentDirectory;
    }

    /**
     * Returns the JFileChooser from ClothoOpenChooserFrame
     * @return javax.swing.JFileChooser The JFileChooser as mentioned above
     */
    public javax.swing.JFileChooser getFileChooser() {
        return _openFrame.getChooser();
    }
    
    /**
     * Opens the opendialog box. Preferably used as the action of an event.
     * @see #getFile()
     */
    public void open_Window() {
        _openFrame.getChooser().showOpenDialog(null);
    }

    /**
     * Sets the current FileFilter as the "Accept all File Filter. In Window's case,
     * this would mean the "All Files" filter.
     */
    public void setAsAllFileFilter() {
        _openFrame.getChooser().setFileFilter(_openFrame.getChooser().getAcceptAllFileFilter());
    }
    
    /**
     * Sets the title of the JFileChooser.
     * @param title
     */
    public void setTitle(String title) {
        _openFrame.getChooser().setDialogTitle(title);
    }
    
    /**
     * returns true if a file was selected, false otherwise.
     */
    public boolean fileSelected;
    private ClothoOpenChooserFrame _openFrame;
    private File _file;
    private File _currentDirectory;
    private javax.swing.filechooser.FileNameExtensionFilter _fileFilter;
}
