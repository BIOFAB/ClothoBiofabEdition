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

package org.clothocore.util.misc;
import java.io.*;
import org.clothocore.util.dialog.ClothoDialogBox;


/**
 *
 * @author Nade Sritanyaratana
 */
public class ClothoTxtUtil {
    
     /**
     * Constructor for ClothoTxtUtil. Requires the file param in helper methods.
     */
    public ClothoTxtUtil() {
    }
    
    /**
     * Constructor for ClothoTxtUtil. Use this constructor if you will be using
     * ClothoTxtUtil just for (File TextFile).
     * @param TextFile
     */
    public ClothoTxtUtil(File TextFile) {
        _textFile = TextFile;
        getString();
    }
    
    /**
     * writes the inputString into the file specified under pathname.
     * @param inputString String to append
     * @param txtFile File to be appended
     * @return revisedTxtFile
     */
    public File appendToString(String inputString, File txtFile) {
    try {
        inputString = inputString.replaceAll("\n",System.
                getProperty("line.separator"));
        _out = new BufferedWriter(new FileWriter(txtFile, true));
        _out.write(inputString);
        _out.close();
    } catch (IOException e) {
    }
    return txtFile;
    }

    
    /**
     * writes the inputInt into the file specified under pathname.
     * @param inputInt integer to append
     * @param txtFile File to be appended
     * @return revisedTxtFile
     */
    public File appendToString(int inputInt, File txtFile) {
    try {
        _out = new BufferedWriter(new FileWriter(txtFile, true));
        _out.write(inputInt);
        _out.close();
    } catch (IOException e) {
    }
    return txtFile;
    }
    
    /**
     * Returns file into a string.
     * @param TextFile
     * @return stringFromFile
     */
    public String getString(File TextFile)
    {
        StringBuffer contents = new StringBuffer();
            try {
      //use buffering, reading one line at a time
      //FileReader always assumes default encoding is OK!

                BufferedReader input =  new BufferedReader(new FileReader(TextFile));
            try {
                String line = null;
               
              //  readLine is a bit quirky :
              //  it returns the content of a line MINUS the newline.
              //  it returns null only for the END of the stream.
              //  it returns an empty String if two newlines appear in a row.
              
              while (( line = input.readLine()) != null){
               contents.append(line);
               contents.append("\n");
             }
            }
            finally {
              input.close();
            }
            }
           catch (IOException ex){
             ex.printStackTrace();
           }
            
          return _string = contents.toString();
        }
    
    /**
     * Turns file to a string. Required the instantiation of the 
     * ClothoTxtUtil(TextFile) constructor.
     * @return stringFromFile
     */
    public String getString()
    {
        StringBuffer contents = new StringBuffer();
            try {
      //use buffering, reading one line at a time
      //FileReader always assumes default encoding is OK!

                BufferedReader input =  new BufferedReader(new FileReader(_textFile));
            try {
                String line = null;
               
              //  readLine is a bit quirky :
              //  it returns the content of a line MINUS the newline.
              //  it returns null only for the END of the stream.
              //  it returns an empty String if two newlines appear in a row.
              
              while (( line = input.readLine()) != null){
               contents.append(line);
               contents.append("\n");
             }
            }
            finally {
              input.close();
            }
            }
           catch (IOException ex){
             ex.printStackTrace();
           }
            
          return _string = contents.toString();
        }
    
    /**
     * returns all strings separated by single tabs, into a String[].
     * @param TextFile
     * @return wordsByTab
     */
    public String[] getWordsByTab(File TextFile) {
         _string = getString(TextFile);
         _words = new String[_string.split("\t").length];
         _words = _string.split ("\t");
         return _words;
    }
    
    /**
     * returns all strings separated by single tabs, into a String[]. Requires
     * ClothoTxtUtil(File TextFile) instantiation.
     * @return wordsByTab
     */
    public String[] getWordsByTab() {
         _words = new String[_string.split("\t").length];
         _words = _string.split ("\t");
         return _words;
    }
        
    /**
     * returns all strings separated by single spaces, into a String[].
     * @param TextFile
     * @return wordsBySpace
     */
     public String[] getWordsBySpace(File TextFile) {
         _string = getString(TextFile);
         _words = new String[_string.split(" ").length];
         _words = _string.split (" ");
         return _words;
    }
     
    /**
     * returns all strings separated by single spaces, into a String[]. Requires
     * ClothoTxtUtil(File TextFile) instantiation.
     * @return wordsBySpace
     */
     public String[] getWordsBySpace() {
         _words = new String[_string.split(" ").length];
         _words = _string.split (" ");
         return _words;
    }
     
     /**
      * returns all strings separated by \n, into a String[].
      * @param TextFile
      * @return wordsBySlash_n
      */
     public String[] getWordsBySlash_n(File TextFile) {
         _string = getString(TextFile);
         _words = new String[_string.split("\n").length];
         _words = _string.split ("\n");
         return _words;
    }
     
     /**
      * returns all strings separated by \n, into a String[]. Requires
      * ClothoTxtUtil(File TextFile) instantiation.
      * @return wordsBySlash
      */
     public String[] getWordsBySlash_n() {
         _words = new String[_string.split("\n").length];
         _words = _string.split ("\n");
         return _words;
    }
     
     
     /**
     * overwrites the file specified under pathname with the inputString.
     * @param inputString String to replace
     * @param txtFile File being overwritten
     * @return revisedTextFile
     */
    public File overwriteFile(String inputString, File txtFile) {
    try {
        if (txtFile.delete())
            txtFile.createNewFile();
        else {
            _dialogBox = new ClothoDialogBox("txtHelper Error", "Could not " +
                    "delete file for overwrite.");
            _dialogBox.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } 
    catch (IOException e) {
        _dialogBox = new ClothoDialogBox("txtHelper Error", "Could not " +
                    "create file for overwrite.");
        _dialogBox.show_Dialog(javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    _textFile = appendToString(inputString, txtFile);
    return _textFile;
    }
     
    private BufferedWriter _out;
    private ClothoDialogBox _dialogBox;
    private File _textFile;
    private String _string;
    private String[] _words;
}
