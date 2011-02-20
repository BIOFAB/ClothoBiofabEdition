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
ENHANCEMENTS, OR MODIFICATIONS.
 */
package org.clothocore.boot;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.clothocore.api.core.wrapper.AlgorithmWrapper;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.ConnectionWrapper;
import org.clothocore.api.core.wrapper.PluginWrapper;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.core.wrapper.WidgetWrapper;
import org.clothocore.util.xml.XMLParser;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

/**
 * Manages a module's life cycle.
 * This class boots up the core.
 * This file needs to be specified in the core
 * "Module Manifest file under "OpenIDE-Module-Install"
 *
 * @author Douglas Densmore
 * @author Bing Xia
 */
public class Installer extends ModuleInstall {

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    @Override
    public void restored() {
        try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            System.out.println("failed");
        }
        catch (ClassNotFoundException e) {
            System.out.println("failed");
        }
        catch (InstantiationException e) {
            System.out.println("failed");
        }
        catch (IllegalAccessException e) {
            System.out.println("failed");
        }

        FileObject pluginXMLs = FileUtil.getConfigFile( "plugins/xml" );

        for ( FileObject pluginXML : pluginXMLs.getChildren() ) {
            if ( pluginXML.getExt().equals( "xml" ) ) {
                parseXMLFile( pluginXML );
            }
        }

        //Get the WidgetWrapper for the Dashboard
        String uuid = Collator.getDashBoardUUID();
        WidgetWrapper dashboard = (WidgetWrapper) Collator.getPluginByUUID(uuid);

        //If there is no dashboard, throw a bare-bones tool launcher
        if(dashboard==null) {
            Object[] allNames = new Object[Collator.getAllTools().size()];
            int i=0;
            for(ToolWrapper tw: Collator.getAllTools()) {
                allNames[i] = tw.getDisplayName();
                i++;
            }

            String selectedValue = (String) JOptionPane.showInputDialog(null, "Choose tool to launch", "Tool Chooser",
                JOptionPane.INFORMATION_MESSAGE, null, allNames, allNames[0]);
            if(selectedValue!=null) {
                PluginWrapper pw = Collator.getPluginByName(selectedValue);
                if(pw!=null) {
                    ToolWrapper tw= (ToolWrapper) pw;
                    tw.launchTool();
                }
            }
        }

        //This section will effectively launch all the widgets
        for ( final WidgetWrapper ww : Collator.getAllWidgets() ) {
            //If it's the Dashboard, show it regardless of preferences
            if(!dashboard.getUUID().equals(ww.getUUID())) {
                //For all other widgets, show them only if they are desired
                if ( !Collator.isWidgetDesired( ww ) ) {
                    continue;
                }
            }

            //FIXME: Determine the actual threading process to use in Clotho
            new Thread() {

                @Override
                public void run() {
                    ww.launch();
                }
            }.start();

            //Could run quickstart here....
            org.clothocore.api.core.QuickStart.run();
        }
    }

    @Override
    public boolean closing() {
        Collector.shutDown(this);
        return true;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                    ////

    private static boolean parseXMLFile( FileObject file ) {
        XMLParser parser;
        try {
            parser = new XMLParser( file.getInputStream(), "plugin" );
        } catch ( FileNotFoundException ex ) {
            Exceptions.printStackTrace( ex );
            return false;
        }
        String type = parser.getFirstTag( "type" );

        if ( type.equals( "ClothoTool" ) ) {
            return parseClothoTool( parser, file, false );
        } else if ( type.equals( "ClothoPluginManager" ) ) {
            return parseClothoTool( parser, file, true );
        } else if ( type.equals( "ClothoWidget" ) ) {
            return parseClothoWidget( parser, file, false );
        } else if ( type.equals( "ClothoDashboard" ) ) {
            return parseClothoWidget( parser, file, true );
        } else if ( type.equals( "ClothoAlgorithm" ) ) {
            return parseClothoAlgorithm( parser, file );
        } else if ( type.equals( "ClothoFormat" ) ) {
        } else if ( type.equals( "ClothoGrammar" ) ) {
        } else if ( type.equals( "ClothoViewer" ) ) {
            return parseClothoViewer( parser, file );
        } else if ( type.equals( "ClothoConnection" ) ) {
            return parseClothoConnection( parser, file );
        }

        return false;
    }

    private static boolean parseClothoWidget( XMLParser parser, FileObject file, boolean isDashboard) {
        String classname = parser.getFirstTag( "classname" );
        String uuid = parser.getFirstTag( "uuid" );
        String description = parser.getFirstTag( "description" );
        String displayname = parser.getFirstTag( "displayname" );
        String iconpath = parser.getFirstTag( "iconpath" );
        String screenshotpath = parser.getFirstTag( "screenshotpath" );
        String author = parser.getFirstTag( "author" );

        WidgetWrapper wrap = new WidgetWrapper( uuid, classname, displayname, author,
                                                description, iconpath, screenshotpath, file );
        Collator.registerWidget( wrap );

        if ( isDashboard ) {
            // TODO: actually save the dashboard
            Collator.setDashUUID( uuid );
            System.out.println( "_dashBoardUUID is " + uuid );
        }
        System.out.print("Registered " + wrap.getDisplayName() + "\n");

        return true;
    }

    private static boolean parseClothoTool( XMLParser parser, FileObject file, boolean isPluginManager ) {
        String classname = parser.getFirstTag( "classname" );
        String uuid = parser.getFirstTag( "uuid" );
        String description = parser.getFirstTag( "description" );
        String displayname = parser.getFirstTag( "displayname" );
        String iconpath = parser.getFirstTag( "iconpath" );
        String screenshotpath = parser.getFirstTag( "screenshotpath" );
        String author = parser.getFirstTag( "author" );
        ArrayList<String> viewTypes = parser.getMultipleTagValues( "viewer" );
     

        ToolWrapper wrap = new ToolWrapper( uuid, classname, displayname, author,
                                            description, iconpath, screenshotpath, file );
        Collator.registerTool( wrap, viewTypes );

        if ( isPluginManager ) {
            Collator.setPluginManagerUUID( wrap.getUUID() );
            System.out.println("_pluginManagerUUID is " + wrap.getUUID());
        }
        System.out.print("Registered " + wrap.getDisplayName() + "\n");

        return true;
    }

    private static boolean parseClothoAlgorithm( XMLParser parser, FileObject file ) {
        String classname = parser.getFirstTag( "classname" );
        String uuid = parser.getFirstTag( "uuid" );
        String description = parser.getFirstTag( "description" );
        String displayname = parser.getFirstTag( "displayname" );
        String iconpath = parser.getFirstTag( "iconpath" );
        String author = parser.getFirstTag( "author" );
        String screenshotpath = parser.getFirstTag( "screenshotpath" );

        AlgorithmWrapper wrap = new AlgorithmWrapper( uuid, classname, displayname, author,
                                                      description, iconpath, screenshotpath, file );
        Collator.registerAlgorithm( wrap );
        System.out.print("Registered " + wrap.getDisplayName() + "\n");

        return true;
    }

    private static boolean parseClothoViewer( XMLParser parser, FileObject file ) {
        String classname = parser.getFirstTag( "classname" );
        String uuid = parser.getFirstTag( "uuid" );
        String description = parser.getFirstTag( "description" );
        String displayname = parser.getFirstTag( "displayname" );
        String iconpath = parser.getFirstTag( "iconpath" );
        String screenshotpath = parser.getFirstTag( "screenshotpath" );
        ArrayList<String> viewTypes = parser.getMultipleTagValues( "viewer" );
        String author = parser.getFirstTag( "author" );

        ViewerWrapper wrap = new ViewerWrapper( uuid, classname, displayname, author,
                                                description, iconpath, screenshotpath, file );

        Collator.registerViewer( wrap, viewTypes );
        System.out.print("Registered " + wrap.getDisplayName() + "\n");

        return true;
    }

    private static boolean parseClothoConnection( XMLParser parser, FileObject file ) {

        String classname = parser.getFirstTag( "classname" );
        String uuid = parser.getFirstTag( "uuid" );
        String description = parser.getFirstTag( "description" );
        String displayname = parser.getFirstTag( "displayname" );
        String iconpath = parser.getFirstTag( "iconpath" );
        String screenshotpath = parser.getFirstTag( "screenshotpath" );
        String author = parser.getFirstTag( "author" );
        String shd = parser.getFirstTag ("helpdescription");

        ConnectionWrapper wrap = new ConnectionWrapper( uuid, classname, displayname, author,
                                                        description, iconpath, screenshotpath, file );
        Collator.registerConnection( wrap );
        System.out.print("Registered " + wrap.getDisplayName() + "\n");

        return true;
    }
}
