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

package org.clothocad.tool.spectacles.eugeneimportexport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;
//import tool.spectacles.Spectacles; //jcDelLater
import org.clothocad.tool.spectacles.ui.frames.PreferencesDialog;
import org.clothocad.tool.spectacles.ui.frames.WorkspaceFrame;

/**
 * EugeneExporter exports Eugene files from Spectacles.
 * @author Joanna
 * @author Richard
 */
public class EugeneExporter {

    /**
     * Creates a new EugeneExporter.
     * @param frame the parent WorkspaceFrame.
     */
    public EugeneExporter(WorkspaceFrame frame) {
        _wsFrame = frame;

    }

    /**
     * Exports Eugene code to a file that the user selects from a file dialog.
     * @return <code>true</code> if the export was successful,
     * and <code>false</code> otherwise.
     */
    public boolean runExportToFile() {
        // File chooser
        File outputFile;
        JFileChooser chooser = new JFileChooser();
        if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(_wsFrame)) {
            outputFile = chooser.getSelectedFile();
        } else {
            return false;
        }
        return runExport(outputFile);
    }

    /**
     * Exports Eugene code to the notepad window.
     * @return the String containing the text to be displayed in the notepad window.
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public String runExportToNotepad() throws  IOException, FileNotFoundException {
        // Export to temp file
        String newFileName = System.getProperty("java.io.tmpdir") + "/tmpOut.eug";
        File tmpFile = new File(newFileName);
        if (!runExport(tmpFile)) {
            return null;
        }

        // Return text from temp file to display in notepad
        BufferedReader fileReader = new BufferedReader(new FileReader(tmpFile));
        String text = "";
        String line = fileReader.readLine();
        while (line != null) {
            text += line + "\n";
            line = fileReader.readLine();
        }
        fileReader.close();
        tmpFile.delete();
        return text;
    }

    /**
     * Exports Eugene code to the file given.  This method is called by both
     * <code>runExportToFile()</code> and <code>runExportToNotepad()</code>.
     * @param outputFile the file to write the exported Eugene code to
     * @return <code>true</code> if the export was successful,
     * and <code>false</code> otherwise.
     */
    public boolean runExport(File outputFile) {
        // Get Manager to export
        Manager managerForExport = _wsFrame.getManager();
        
        // Update Component declarations and Device declarations
        managerForExport.getEugeneDeviceDecs().clear();
        HashMap<String, DevicePartWrapper> wrappedDevices = managerForExport.getAllWrappedDevices();
        for (String aDeviceName : wrappedDevices.keySet()) {
            if (wrappedDevices.get(aDeviceName).isDevice()) {
                managerForExport.getEugeneDeviceDecs().put(aDeviceName, wrappedDevices.get(aDeviceName).getDevice());
            } else {
            JOptionPane.showMessageDialog(_wsFrame,
                    "Problem exporting: bad data structure",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Run Eugene's export method
        eugene.tools.EugeneExporter exporter =
                new eugene.tools.EugeneExporter(
                        managerForExport.getEugenePropertyDefs(),
                        managerForExport.getEugenePartDefs(),
                        managerForExport.getEugenePartDecs(),
                        managerForExport.getEugeneDeviceDecs(),
                        managerForExport.getEugenePrimitiveDecs(),
                        managerForExport.getEugeneRuleDecs(),
                        managerForExport.getEugeneRuleAssertions(),
                        managerForExport.getEugeneRuleNotes());
        try {
            exporter.exportEugene(outputFile);
            if (!_wsFrame.getPreferences().getBoolean("EugeneRuleChecking", PreferencesDialog.defaultEugeneRuleChecking)) {
                EugeneImporter importer = new EugeneImporter(_wsFrame);
                try {
                    return importer.runImport(outputFile, true);
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(_wsFrame, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return true;
    }

    private WorkspaceFrame _wsFrame;
}
