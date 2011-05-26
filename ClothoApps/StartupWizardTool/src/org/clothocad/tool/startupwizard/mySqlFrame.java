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
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.ConnectionWrapper;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.core.wrapper.WidgetWrapper;
import org.clothocore.api.data.Institution;
import org.clothocore.api.data.ObjType;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocore.util.buttons.GlassButton;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class mySqlFrame extends JFrame {

    public mySqlFrame() {
        super("Startup Wizard");
        setIconImage(ImageSource.getTinyLogo());
        try {
            setPreferences();
        } catch(Exception e) {
            e.printStackTrace();
        }
        initcomponents();
    }

    private void initcomponents() {
        setSize(new Dimension(677,505));
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        ImageIcon firstimage = ImageUtilities.loadImageIcon( imagebase + 0 + ".PNG", false );
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

        runSpecialInstructions();
        setVisible(true);
    }

    private void goNext() {
        currentIndex++;
        drawScreen(isPrev(), isNext());
    }


    private void goPrevious() {
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
        ImageIcon firstimage = ImageUtilities.loadImageIcon( imagebase + currentIndex + ".PNG", false );
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
            case 0:
                GlassButton firsturl = new GlassButton("");
                firsturl.setBounds(90,320,490,25);
                firsturl.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BareBonesBrowserLaunch.openURL("http://www.bu.edu/ece-clotho/dbfiles/ClothoDBSetup.sql");
                    }
                });
                getLayeredPane().add(firsturl);
                otherButtons.add(firsturl);

                GlassButton secondurl = new GlassButton("");
                secondurl.setBounds(90,390,490,25);
                secondurl.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BareBonesBrowserLaunch.openURL("http://wiki.bu.edu/ece-clotho/index.php/Database_Setup");
                    }
                });
                getLayeredPane().add(secondurl);
                otherButtons.add(secondurl);

            case 1:
                break;
            case 2:
                //Launch the database configuration tool
                if(true) {
                    ToolWrapper tw = (ToolWrapper) Collator.getPluginByUUID("org.clothocad.tool.connectconfigtool-tool");
                    tw.launchTool();
                }

                break;
            case 3:
                //Connect to the database, quit if failed
                if(!Collector.isConnected()) {
                    Collector.connectToDefault();
                }
                if(!Collector.isConnected()) {
                    Collector.shutDown();
                }

                //Has Person, Lab, and Institution tool launches
                GlassButton instbutt = new GlassButton("");
                instbutt.setBounds(10,10,120,120);
                instbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ToolWrapper tw = (ToolWrapper) Collator.getPluginByUUID("org.clothocad.tool.institutioneditor");
                        tw.launchTool();
                    }
                });
                getLayeredPane().add(instbutt);
                otherButtons.add(instbutt);

                GlassButton labbutt = new GlassButton("");
                labbutt.setBounds(10,170,120,120);
                labbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ToolWrapper tw = (ToolWrapper) Collator.getPluginByUUID("org.clothocad.tool.labeditor");
                        tw.launchTool();
                    }
                });
                getLayeredPane().add(labbutt);
                otherButtons.add(labbutt);

                GlassButton personbutt = new GlassButton("");
                personbutt.setBounds(10,320,120,120);
                personbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ToolWrapper tw = (ToolWrapper) Collator.getPluginByUUID("org.clothocad.tool.personeditor");
                        tw.launchTool();
                    }
                });
                getLayeredPane().add(personbutt);
                otherButtons.add(personbutt);
                break;
            case 5:
                Collector.selectCurrentUser();

                //Stop button
                GlassButton stopbutt = new GlassButton("");
                stopbutt.setBounds(40,80,90,70);
                stopbutt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        endItAll();
                        mySqlFrame.this.dispose();
                    }
                });
                getLayeredPane().add(stopbutt);
                otherButtons.add(stopbutt);

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
                        mySqlFrame.this.dispose();
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
        if(currentIndex==0) {
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

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.straineditor");
        Collator.putPreferredViewer(ObjType.STRAIN, vw);

        vw = (ViewerWrapper) Collator.getPluginByUUID("org.clothocad.tool.institutioneditor");
        Collator.putPreferredViewer(ObjType.INSTITUTION, vw);

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

        ConnectionWrapper cw = (ConnectionWrapper) Collator.getPluginByUUID("org.clothocad.connection.configurableconnection-ClothoConnection");
        Collator.setDefaultConnection(cw);
    }

    private void endItAll() {
        WidgetWrapper ww = (WidgetWrapper) Collator.getPluginByUUID("org.clothocad.widget.onstartup");
        if(ww!=null) {
            if(Collator.uninstall(ww)) {
                Collator.refreshDash();
                return;
            }
        }
        return;
    }
/*-----------------
     variables
 -----------------*/
    private int currentIndex=0;
    String imagebase = "org/clothocad/tool/startupwizard/images/Slide";
    private JLabel imagelabel;
    private GlassButton nextButton;
    private GlassButton previousButton;

    ArrayList<JComponent> otherButtons = new ArrayList<JComponent>();
}
