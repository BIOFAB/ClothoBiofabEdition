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

package org.clothocad.tool.startupwizard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.clothocad.tool.spreaditfeatures.spreaditFeatures;
import org.clothocad.tool.spreaditoligos.spreaditOligos;
import org.clothocad.tool.spreaditparts.spreaditParts;
import org.clothocad.tool.spreaditvectors.spreaditVectors;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Institution;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;
import org.clothocore.api.core.wrapper.ConnectionWrapper;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.core.wrapper.WidgetWrapper;
import org.clothocore.api.data.Collection;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.GlassButton;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class localFrame extends JFrame {

    public localFrame() {
        super("Startup Wizard");
        setIconImage(ImageSource.getTinyLogo());
        initcomponents();
        nextButton.setVisible(false);

        saveLocalDatabaseToFile();

        try {
            setPreferences();
        } catch(Exception e) {
            e.printStackTrace();
        }

        runSpecialInstructions();
        nextButton.setVisible(true);
    }

    private void initcomponents() {
        setSize(new Dimension(677,505));
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        ImageIcon firstimage = ImageUtilities.loadImageIcon( "org/clothocad/tool/startupwizard/images/local1.png", false );
        imagelabel = new JLabel(firstimage);
        imagelabel.setBounds(0,0,672,480);
        getContentPane().add(imagelabel);

        //Put in the previous button
        previousButton = new GlassButton("");
        previousButton.setBounds(510,410,60,60);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goPrevious();
            }
        });
        getLayeredPane().add(previousButton,2);
        previousButton.setVisible(false);

        //Put in the next button
        nextButton = new GlassButton("");
        nextButton.setBounds(600,410,60,60);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goNext();
            }
        });
        getLayeredPane().add(nextButton,2);

        setVisible(true);
    }

    private void goNext() {
        if(currentIndex == 1) {
            currentIndex = 5;
            drawScreen(isPrev(), isNext());
            return;
        }
        currentIndex++;
        drawScreen(isPrev(), isNext());
    }


    private void goPrevious() {
        if(currentIndex == 5) {
            currentIndex = 1;
            drawScreen(isPrev(), isNext());
            return;
        }
        currentIndex--;
        drawScreen(isPrev(), isNext());
    }

    private void drawScreen(boolean prev, boolean next) {
        //Remove any buttons added during the previous step
        for(JComponent gb : otherButtons) {
            getLayeredPane().remove(gb);
        }
        otherButtons.clear();

        //Do the background image
        ImageIcon firstimage = null;
        if(currentIndex==1) {
            firstimage = ImageUtilities.loadImageIcon( "org/clothocad/tool/startupwizard/images/local1.png", false );
        } else if(currentIndex==5) {
            firstimage = ImageUtilities.loadImageIcon( "org/clothocad/tool/startupwizard/images/local2.png", false );
        }else {
            firstimage = ImageUtilities.loadImageIcon( imagebase + currentIndex + ".PNG", false );
        }
        imagelabel.setIcon(firstimage);
        imagelabel.setBounds(0,0,672,480);
        getContentPane().add(imagelabel);

        //Put in the previous button
        if(prev) {
            previousButton.setVisible(true);
        } else {
            previousButton.setVisible(false);
        }
        //Put in the next button
        if(next) {
            nextButton.setVisible(true);
        } else {
            nextButton.setVisible(false);
        }

        runSpecialInstructions();
        validate();
        getContentPane().repaint();
        getLayeredPane().repaint();
    }

    private void runSpecialInstructions() {
        switch(currentIndex) {
            case 1:
                //Connect to the database, quit if failed
                if(!Collector.isConnected()) {
                    Collector.connectToDefault();
                }
                setMorePreferences();
                try {
                Person jenny = Person.retrieveByName("jennyj");
                Collator.putPreference( "CurrentUserUUID", jenny.getUUID() );
                } catch(Exception e) {
                    return;
                }
                break;
            case 5:
                //Tutorial button
                GlassButton tutorialbutt = new GlassButton("");
                tutorialbutt.setBounds(520,150,120,120);
                tutorialbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("called tutorials");
                        ToolWrapper tw = (ToolWrapper) Collator.getPluginByUUID("org.clothocad.tool.tutorialwizard");
                        if(tw!=null) {
                            tw.launchTool();
                        }
                    }
                });
                getLayeredPane().add(tutorialbutt);
                otherButtons.add(tutorialbutt);
                break;
            case 7:
                //Hide error reporter
                GlassButton errorbutt = new GlassButton("");
                errorbutt.setBounds(30,90,250,320);
                errorbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        WidgetWrapper ww = (WidgetWrapper) Collator.getPluginByUUID("org.clothocad.widget.errorreporter");
                        ww.close();
                        Collator.hideWidget(ww);
                    }
                });
                getLayeredPane().add(errorbutt);
                otherButtons.add(errorbutt);
                break;
            case 8:
                //Hide wheelbarrow
                GlassButton wheelbutt = new GlassButton("");
                wheelbutt.setBounds(40,160,300,250);
                wheelbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        WidgetWrapper ww = (WidgetWrapper) Collator.getPluginByUUID("org.clothocad.widget.wheelbarrow");
                        ww.close();
                        Collator.hideWidget(ww);
                    }
                });
                getLayeredPane().add(wheelbutt);
                otherButtons.add(wheelbutt);
                break;
            case 9:
                //Institution right click popuper
                GlassButton instrightbutt = new GlassButton("");
                instrightbutt.setBounds(350,370,100,100);
                Institution myinst = new Institution("Jacobson College", "Baltimore", "MD", "USA");
                if(myinst!=null) {
                    myinst.setTransient();
                    new ObjBasePopup(instrightbutt, myinst);
                }
                getLayeredPane().add(instrightbutt);
                otherButtons.add(instrightbutt);
                break;
            case 19:
                //Stop button
                GlassButton stopbutt2 = new GlassButton("");
                stopbutt2.setBounds(590,400,70,70);
                stopbutt2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        endItAll();
                        localFrame.this.dispose();
                    }
                });
                getLayeredPane().add(stopbutt2);
                otherButtons.add(stopbutt2);
                break;
            default:
                break;
        }
    }

    private boolean isPrev() {
        if(currentIndex==1) {
            return false;
        }
        return true;
    }

    private boolean isNext() {
        if(currentIndex==19) {
            return false;
        }
        return true;
    }

    private void setPreferences() {
        ViewerWrapper vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.grapevine");
        Collator.putPreferredViewer(ObjType.NOTE, vw);
        Collator.putPreferredViewer(ObjType.FACTOID, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.collectionview");
        Collator.putPreferredViewer(ObjType.COLLECTION, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.institutioneditor");
        Collator.putPreferredViewer(ObjType.INSTITUTION, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.straineditor");
        Collator.putPreferredViewer(ObjType.STRAIN, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.labeditor");
        Collator.putPreferredViewer(ObjType.LAB, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.personeditor");
        Collator.putPreferredViewer(ObjType.PERSON, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.sledrunner");
        Collator.putPreferredViewer(ObjType.PART, vw);
        Collator.putPreferredViewer(ObjType.VECTOR, vw);
        Collator.putPreferredViewer(ObjType.OLIGO, vw);
        Collator.putPreferredViewer(ObjType.FEATURE, vw);
        Collator.putPreferredViewer(ObjType.PLASMID, vw);

        ConnectionWrapper cw = (ConnectionWrapper) Collator.getPluginByUUID("org.clothocad.connection.localconnection");
        Collator.setDefaultConnection(cw);
    }

    private void setMorePreferences() {
        if(!Collector.isConnected()) {
            return;
        }

        Person jjenn = Person.retrieveByName("jjenn");
        if(jjenn!=null) {
            Collator.putPreference( "CurrentUserUUID", jjenn.getUUID() );
        }

        associate(spreaditParts.class, "jjenn Parts");
        associate(spreaditOligos.class, "jjenn Oligos");
        associate(spreaditVectors.class, "jjenn Vectors");
        associate(spreaditFeatures.class, "jjenn Features");
    }

    private void associate(Class aclass, String collname) {
        try {
            Preferences _prefs = Preferences.userNodeForPackage(aclass);
            Collection acoll = Collection.retrieveByName(collname);
            String uuid = acoll.getUUID();
            _prefs.put("launchCollection", uuid);
        } catch(Exception e) {
        }
    }

    private void endItAll() {
        WidgetWrapper ww = (WidgetWrapper) Collator.getPluginByUUID("org.clothocad.widget.onstartup");
        if(ww!=null) {
            Collator.uninstall(ww);
        }
    }


    private void saveLocalDatabaseToFile() {
        File testfile = new File("maindb.script");
        if(testfile.exists()) {
            int n = JOptionPane.showConfirmDialog(
                this,
                "I notice that you've run this before.  Is it ok for me to \n"
                + "erase your database and replace it with the original version?",
                "OK to erase database",
                JOptionPane.YES_NO_OPTION);
            if(n!=0) {
                return;
            }
            n = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to erase the database?",
                "Sure about it?",
                JOptionPane.YES_NO_OPTION);
            if(n!=0) {
                return;
            }
        }

        
        //Write the document to the file
        copy("maindb.lck");
        copy("maindb.log");
        copy("maindb.properties");
        copy("maindb.script");

    }

    /**
     * copier
     *
     *
     * @param databasefile
     * @param filename
     */
    private static void copy( String filename ) {
        FileObject databasefile = FileUtil.getConfigFile("org/clothocad/tool/startupwizard/" + filename);

        System.out.println(databasefile.getName());

        System.out.println(databasefile.getName());
        FileOutputStream to = null;
        try {
            byte[] bytes = databasefile.asBytes();

            to = new FileOutputStream( new File(filename) );

            to.write( bytes); // write
        } catch ( IOException ex ) {
            Exceptions.printStackTrace( ex );
        } finally {
            if ( to != null ) {
                try {
                    to.close();
                } catch ( IOException e ) {
                }
            }
        }
    }
/*-----------------
     variables
 -----------------*/
    private int currentIndex=1;
    String imagebase = "org/clothocad/tool/startupwizard/images/Slide";
    private JLabel imagelabel;
    private GlassButton nextButton;
    private GlassButton previousButton;

    ArrayList<JComponent> otherButtons = new ArrayList<JComponent>();

}
