/*
 * 
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
package org.clothocad.library.menutasks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Container;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.OligoSample;
import org.clothocore.api.data.Plate;
import org.clothocore.api.data.PlateType;

public final class ImportIDT implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                 return f.isDirectory() || f.getName().endsWith(".csv");
            }
            @Override
            public String getDescription() {
                return "csv files (*.csv)";
            }
        });
        int returnVal = filechooser.showOpenDialog(new JLabel());
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File chosenfile = filechooser.getSelectedFile();

        try {
            runFullParse(chosenfile);
        } catch(Exception err) {
            err.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "I've had an error and need to abort.",
                "Abort", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void runFullParse(File chosenfile) {
        //Parse the file
        String text = readFile(chosenfile);
        parseFile(text);


        //Pause to save oligos or quit
        int n = JOptionPane.showConfirmDialog( new JFrame(), "I parsed " + oligoLines.size() + " oligos and created " + this.newOligoCount + " new ones.  Should I save them to the database?", "Parsing Oligos", JOptionPane.YES_NO_OPTION);
        if(n!=0) {
            for(OligoLine ol : oligoLines) {
                ol.oligo.setTransient();
            }
            return;
        }

        //Save all the oligos to the database
        for(OligoLine ol : oligoLines) {
            ol.oligo.saveDefault();
        }

        //Get a plate name or cancel
        String platename = JOptionPane.showInputDialog( "Your Oligo objects are done.  If you'd like to create samples, give me the name of the plate:" );
        if(platename==null) {
            return;
        }

        //Select the platetype
        ArrayList<ObjLink> platetypeList = Collector.getAllLinksOf(ObjType.PLATE_TYPE);
        if(platetypeList.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "There are no Plate Types in your database.  We can't proceed with making samples.",
                "No Plate Types", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[] possibilities = platetypeList.toArray();
        ObjLink link = (ObjLink) JOptionPane.showInputDialog( null, "Choose the plate type:", "Select plate type", JOptionPane.PLAIN_MESSAGE, null, possibilities, null);

        if(link==null) {
            JOptionPane.showMessageDialog(new JFrame(), "Since you chose no plate type, I'm going to quit.",
                "No Plate Types", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Create the plate
        PlateType plateType = Collector.getPlateType(link.uuid);
        Plate aplate = new Plate(platename, plateType, Collector.getCurrentUser());

        //Pause to save plate or quit
        n = JOptionPane.showConfirmDialog( new JFrame(), "I created plate " + aplate.getName() + ". Should I save it to the database then make Oligo samples?", "Save Plate", JOptionPane.YES_NO_OPTION);
        if(n!=0) {
            return;
        }

        //Save the plate
        aplate.saveDefault();

        //Get the volume
        String svol = JOptionPane.showInputDialog( "What volume in microliters (uL) do you have for each oligo?  Cancel will abort." );
        if(svol==null) {
            return;
        }

        int volume = Integer.parseInt(svol);

        if(platename==null) {
            return;
        }

        //Create OligoSamples
        for(OligoLine ol : oligoLines) {
            short row = ol.getRow();
            short col = ol.getColumn();
            Container acon = aplate.getContainerAt(row, col);
            if(acon==null) {
                acon = new Container();
                acon.putContainerToPlate(row, col, aplate);
                aplate.saveDefault();
                acon.saveDefault();
            }

            //Generate the OligoSample and save to database
            ol.osample = OligoSample.generateOligoSample(ol.oligo, acon, volume, Collector.getCurrentUser());
            if(ol.osample!=null) {
                ol.osample.saveDefault();
            }
        }
    }

    private void parseFile(String text) {
        String[] lines = text.split("\\n");
        for(String aline : lines) {
            parseOligoLine(aline);
        }
    }

    private void parseOligoLine(String aline) {
        System.out.println("parsed line " + aline);
        String[] fields = aline.split(",");
        if(fields.length<3 || fields.length>4) {
            System.out.println(aline + " didn't contain oligo info");
            return;
        }
        if(fields[0].equals("WellPosition")) {
            System.out.println(aline + " didn't contain oligo info");
            return;
        }
        if(fields[1].equals("Name")) {
            System.out.println(aline + " didn't contain oligo info");
            return;
        }

        OligoLine oline = new OligoLine();
        oline.well = fields[0];
        oline.name = fields[1];
        oline.seq = fields[2];
        if(!oline.findOrCreateOligo()) {
            newOligoCount++;
        }
        oligoLines.add(oline);
    }

    private String readFile(File file)  {
        StringBuilder sb = new StringBuilder();
        try {
        Reader in = new BufferedReader(new FileReader(file));
        char[] chars = new char[1 << 16];
        int length;
        while ((length = in.read(chars)) > 0) {
            sb.append(chars, 0, length);
        }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
      }

    private class OligoLine {
        String well;
        String name;
        String seq;
        Oligo oligo;
        OligoSample osample;

        public boolean findOrCreateOligo() {
            Oligo existing = Oligo.retrieveByName(name);
            System.out.print("***working on: " + name + "," + well + "," +getRow());
            System.out.println("," +getColumn());
            if(existing!=null) {
                System.out.println("I already had: " + name + " as " + existing.getUUID());
                oligo=existing;
                return true;
            }

            oligo = new Oligo(name, name + " autogenerated description" , Collector.getCurrentUser(), seq);
            return false;
        }

        public short getRow() {
            Character letter = well.charAt(0);
            int num =  letter - 65;
            return (short) num;
        }

        public short getColumn() {
            String nums = well.substring(1);
            int num = Integer.parseInt(nums);
            return (short) (num - 1);
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    private ArrayList<OligoLine> oligoLines = new ArrayList<OligoLine>();
    private int newOligoCount = 0;
}
