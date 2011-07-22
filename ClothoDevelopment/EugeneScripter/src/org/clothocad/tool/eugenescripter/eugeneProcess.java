/*
 Copyright (c) 2010 The Regents of the University of California.
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

package org.clothocad.tool.eugenescripter;

import eugene.Device;
import eugene.Part;
import eugene.Primitive;
import eugene.eugeneLexer;
import eugene.eugeneParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JEditorPane;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Format;

/**
 *
 * @author Douglas Densmore
 */
public class eugeneProcess {


    public static boolean runEugene(String code, JEditorPane outputPane) throws IOException{

        outputPane.setText(outputPane.getText() + "\nRunning Eugene\n");

        if(!code.equals("") && code != null)
        {

            //Write code to a file
            String newFileName = System.getProperty("java.io.tmpdir") + "/tmpIn.eug";
            File eugFile = new File(newFileName);
            Writer output = new BufferedWriter(new FileWriter(eugFile));
            output.write(code);
            output.close();

            //create lexer and parser
            eugeneLexer lex = new eugeneLexer(new ANTLRFileStream(eugFile.getAbsolutePath()));
            CommonTokenStream tokens = new CommonTokenStream(lex);
            eugeneParser _parser = new eugeneParser(tokens);

            try {
                _parser.prog();
            } catch (IllegalArgumentException iae) {
                if (!_parser.ruleAssertionViolations.isEmpty()) {
                    String assertionMessage = "One or more rule assertions have been violated:";
                    for (String deviceName : _parser.ruleAssertionViolations.keySet()) {
                        assertionMessage += "\nIn device " + deviceName + ":";
                        List<String> rules = _parser.ruleAssertionViolations.get(deviceName);
                        for (String ruleName : rules) {
                            assertionMessage += " " + ruleName;
                        }
                    }

                    outputPane.setText(outputPane.getText() + assertionMessage);
                    /*JOptionPane.showMessageDialog(_wsFrame, assertionMessage,
                    "Warning - Assertions", JOptionPane.WARNING_MESSAGE);*/

                } else {

                    outputPane.setText(outputPane.getText() + "Eugene parser error: " + iae.getMessage());
                    /*JOptionPane.showMessageDialog(_wsFrame, "Eugene parser error: " + iae.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);*/
                }
                return false;
            } catch (RecognitionException ex) {
                outputPane.setText(outputPane.getText() + "Eugene parser error: " + ex.getMessage());

                /*JOptionPane.showMessageDialog(_wsFrame, "Eugene parser error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);*/

                return false;
            } finally {
                if (!_parser.ruleNoteViolations.isEmpty()) {
                    String noteMessage = "One or more rule notes have been violated:";
                    for (String deviceName : _parser.ruleNoteViolations.keySet()) {
                        noteMessage += "\nIn device " + deviceName + ":";
                        List<String> rules = _parser.ruleNoteViolations.get(deviceName);
                        for (String ruleName : rules) {
                            noteMessage += " " + ruleName;
                        }
                    }

                    outputPane.setText(outputPane.getText() + noteMessage);
                    /*JOptionPane.showMessageDialog(_wsFrame, noteMessage,
                    "Warning - Notes", JOptionPane.WARNING_MESSAGE);*/
                }
            }
        decomposeEugene(_parser, outputPane);
        }

        return true;

    }

    private static void decomposeEugene(eugeneParser parser, JEditorPane outputPane)
    {

        //create a collection
        Collection coll = new Collection();
        
        //List of the devices
        ArrayList<org.clothocore.api.data.Part> partList = new ArrayList<org.clothocore.api.data.Part>();

        //go through all the devices and determine parts and properties
        for(String deviceKey : parser.deviceDeclarations.keySet())
        {
            Device currentDevice = parser.deviceDeclarations.get(deviceKey);
            outputPane.setText(outputPane.getText() + currentDevice.name + "\n");

            for (String currentComponentName : currentDevice.components) {
                if (parser.partDeclarations.containsKey(currentComponentName)) {
                    Part currentPart = parser.partDeclarations.get(currentComponentName);
                    outputPane.setText(outputPane.getText() + currentPart.name + "\n");

                     //default values for parts
                    String nameDefault = "DefaultName";
                    String sdDefault = "DefaultShortDescription";
                    String seqDefault = "GATCTAAAAAAG";

                    for (String currentPropertyName : currentPart.propertyValues.keySet())
                    {
                        if (currentPart.propertyValues.containsKey(currentPropertyName)) {
                            Primitive currentProperty = currentPart.propertyValues.get(currentPropertyName);
                            outputPane.setText(outputPane.getText() + currentProperty.name + "=" + currentProperty.num + "=" + currentProperty.txt + "\n");

                            if(currentPropertyName.equals("name"))
                                nameDefault = currentProperty.txt;
                            if(currentPropertyName.equals("shortDescription"))
                                sdDefault = currentProperty.txt;
                            if(currentPropertyName.equals("sequence"))
                                seqDefault = currentProperty.txt;
                        }

                    }
                    
                    //Create a new part
                    org.clothocore.api.data.Part partInstance = org.clothocore.api.data.Part.generateBasic(nameDefault, sdDefault, seqDefault, Format.retrieveByName("BglBricks"), Collector.getCurrentUser());
                    
                    //add the part to the list of parts for the composite
                    partList.add(partInstance);

                    //add the part to the collection
                    coll.addObject(partInstance);
                }
            }

            //create a composite part with all the individual parts
            org.clothocore.api.data.Part compPart = org.clothocore.api.data.Part.generateComposite(partList, null, Format.retrieveByName("BglBricks"), Collector.getCurrentUser(), currentDevice.name, "DefaultDescription");
            coll.addObject(compPart);
        }

        coll.launchDefaultViewer();
    }



}
