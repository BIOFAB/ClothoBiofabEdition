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

package org.clothocad.tool.straineditor;

import java.awt.Window;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Attachment;
import org.clothocore.api.data.Institution;
import org.clothocore.api.data.Strain;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.plugin.ClothoTool;

/**
 *
 * @author J. Christopher Anderson
 */
public class connect implements ClothoTool {

    @Override
    public void launch() {
        if(!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch Strain Editor!",
                                           "Not connected", JOptionPane.ERROR_MESSAGE );
            return;
        }

        //Throw up a dialog and get user to select the collection stored as 'chosen'
        ArrayList<ObjLink> alltypes = Collector.getAllLinksOf(ObjType.STRAIN);
        Object[] allNames = alltypes.toArray();
        Object[] param = new Object[allNames.length+2];
        param[0] = "New Basic Strain";
        param[1] = "New Composite Strain";
        for(int i=0; i<allNames.length; i++) {
            param[i+2] = allNames[i];
        }
        Object val = JOptionPane.showInputDialog(null, "Choose one", "Collection",
            JOptionPane.INFORMATION_MESSAGE, null, param, param[0]);

        if(val==null) {
            return;
        }
        
        //If new Basic Strain chosen, create a new basic strain
        if(val.equals(param[0])) {
            //Get the name for the new strain
            String strainname = JOptionPane.showInputDialog( "What is the name of the new strain?" );
            if(strainname==null || strainname.equals("")) {
                return;
            }

            //Get the Strain type
            Object[] types = Strain.strainType.values();
            Strain.strainType stype = (Strain.strainType) JOptionPane.showInputDialog(null, "What type of organism is this?", "Choose Strain type",
                JOptionPane.INFORMATION_MESSAGE, null, types, types[0]);
            if(stype==null) {
                return;
            }
            
            //Get the Genbank file
            //default icon, custom title
            int n = JOptionPane.showConfirmDialog(
                null,
                "Do you have a genbank file for the genome sequence of this Strain.  If yes, you can upload it next.?",
                "Genbank file available?",
                JOptionPane.YES_NO_OPTION);
            File genfile = null;
            if(n==0) {
                JFileChooser chooser = new JFileChooser(); 
                int returnVal = chooser.showOpenDialog(null); 
                final File file ; 
                if(returnVal == JFileChooser.APPROVE_OPTION) { 
                    file = chooser.getSelectedFile();
                    if(file.exists()) {
                        genfile = file;
                    }
                }
            }
            
            //Maybe create an attachment object
            Attachment genatt = null;
            if(genfile!=null) {
                genatt = new Attachment(genfile, genfile.getName(), Attachment.AttachmentType.GB);
            }

            //Create the new strain
            Strain chosen = new Strain( strainname,  Collector.getCurrentUser(), genatt, stype);
            if(chosen!=null) {
                launch(chosen);
            }
            return;
        }

        //If new Composite Strain chosen, create a new Composte strain
        if(val.equals(param[1])) {
            //Get the name for the new strain
            String strainname = JOptionPane.showInputDialog( "What is the name of the new strain?" );
            if(strainname==null) {
                return;
            }

            //Get the Strain type
            Object[] types = Strain.strainType.values();
            Strain.strainType stype = (Strain.strainType) JOptionPane.showInputDialog(null, "What type of organism is this?", "Choose Strain type",
                JOptionPane.INFORMATION_MESSAGE, null, types, types[0]);
            if(stype==null) {
                return;
            }

            //Choose the parent Strain
            Object[] strainlist = new Object[allNames.length];
            for(int i=0; i<allNames.length; i++) {
                strainlist[i] = allNames[i];
            }
            Object strval = JOptionPane.showInputDialog(null, "Choose one", "Collection",
                JOptionPane.INFORMATION_MESSAGE, null, strainlist, strainlist[0]);

            if(strval==null) {
                return;
            }

            ObjLink strlink = (ObjLink) strval;
            if(strlink==null) {
                return;
            }
            Strain parentstrain = Collector.getStrain(strlink.uuid);

            //Create the new composite strain
            Strain chosen = new Strain( strainname, Collector.getCurrentUser(), parentstrain);
            if(chosen!=null) {
                launch(chosen);
            }
            return;
        }

        ObjLink link = (ObjLink) val;
        if(link!=null) {
            Strain chosen = Collector.getStrain(link.uuid);
            launch(chosen);
        }
    }

    @Override
    public void launch(ObjBase o) {
        if(!o.getType().equals(ObjType.STRAIN)) {
            return;
        }
        if(!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to launch this tool",
                                           "Not connected", JOptionPane.ERROR_MESSAGE );
            return;
        }
        pig.add(new WeakReference<Window>(new frame((Strain) o)));
    }


    @Override
    public void close() {
        for(WeakReference<Window> wf: pig) {
            Window gui =  wf.get();
            if(gui!=null) {
                gui.dispose();
            }
        }
    }

    @Override
    public void init() {
    }

/*-----------------
     variables
 -----------------*/
        private ArrayList<WeakReference<Window>> pig= new ArrayList<WeakReference<Window>>();

}
