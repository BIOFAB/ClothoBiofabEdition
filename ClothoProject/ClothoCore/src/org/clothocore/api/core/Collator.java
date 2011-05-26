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
package org.clothocore.api.core;

import org.clothocore.api.core.wrapper.AlgorithmWrapper;
import org.clothocore.api.core.wrapper.ConnectionWrapper;
import org.clothocore.api.core.wrapper.PluginWrapper;
import org.clothocore.api.core.wrapper.ToolWrapper;
import org.clothocore.api.core.wrapper.ViewerWrapper;
import org.clothocore.api.core.wrapper.WidgetWrapper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.clothocore.api.data.ObjType;
import org.clothocore.util.dialog.ClothoDialogBox;
import org.clothocore.util.def.DefaultPluginManager;
import org.netbeans.api.autoupdate.InstallSupport;
import org.netbeans.api.autoupdate.OperationContainer;
import org.netbeans.api.autoupdate.OperationException;
import org.netbeans.api.autoupdate.OperationSupport;
import org.netbeans.api.autoupdate.OperationSupport.Restarter;
import org.netbeans.api.autoupdate.UpdateElement;
import org.netbeans.api.autoupdate.UpdateManager;
import org.netbeans.api.autoupdate.UpdateManager.TYPE;
import org.netbeans.api.autoupdate.UpdateUnit;
import org.netbeans.api.autoupdate.UpdateUnitProvider;
import org.netbeans.api.autoupdate.UpdateUnitProviderFactory;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.util.Exceptions;

/**
 * Collator is for installing and registering Clotho plugins.  You call Collator to install
 * a .clo file (collection of .nbm files which together represent a plugin), to uninstall a plugin from its pluginwrapper, or to get a plugin wrapper
 * by name or uuid so that it can be explicitly launched.  You can also get lists of plugins
 * of a particular type, or particular viewers of a given ObjType.
 *
 * @author Bing Xia
 * @author Douglas Densmore
 * @author J. Christopher Anderson
 */
public class Collator {

    /** Private to conform to the Singleton design pattern.
     *  To get the one and only Collator use the getCore() function.
     */
    private Collator() {
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////
    /**
     * Access the single instance of the class, create the single instance if not created yet
     * @return Collator instance
     */
    public static Collator getCore() {
        if ( _core == null ) {
            _core = new Collator();
        }

        return _core;
    }

    /**
     * Attempts to install the given nbmFile. Returns true on success, false
     * on failure.
     * @param nbmFile
     * @return
     */
    public static boolean installPlugin( File nbmFile ) {
        UpdateUnitProvider create = UpdateUnitProviderFactory.getDefault().create( nbmFile.getName(), new File[]{ nbmFile } );
        List<UpdateUnit> updateUnits = create.getUpdateUnits( TYPE.MODULE );
        if ( updateUnits == null || updateUnits.size() != 1 ) {
            return false;
        }

        UpdateUnit install = updateUnits.get( 0 );
        List<UpdateElement> availableUpdates = install.getAvailableUpdates();
        if ( availableUpdates == null || availableUpdates.size() != 1 ) {
            return false;
        }

        UpdateElement upEle = availableUpdates.get( 0 );

        OperationContainer<InstallSupport> createForInstall = OperationContainer.createForInstall();
        createForInstall.add( upEle );

        if ( !createForInstall.listAll().get( 0 ).getBrokenDependencies().isEmpty() ) {
            return false;
        }

        try {
            InstallSupport.Validator v = createForInstall.getSupport().doDownload( null, true );
            InstallSupport.Installer i = createForInstall.getSupport().doValidate( v, null );
            Restarter restarter = createForInstall.getSupport().doInstall( i, null );
            if ( restarter != null ) {
                //createForInstall.getSupport().doRestartLater( restarter );
                createForInstall.getSupport().doRestart(restarter, null);
            }
        } catch ( OperationException ex ) {
            return false;
        }

        return true;
    }

    /**
     * Install a single plugin using a progress handler
     * @param nbmFile the file to install
     * @param handle the progress handler
     * @return
     */
    public static boolean installPlugin( File nbmFile, ProgressHandle handle ) {
        UpdateUnitProvider create = UpdateUnitProviderFactory.getDefault().create( nbmFile.getName(), new File[]{ nbmFile } );
        List<UpdateUnit> updateUnits = create.getUpdateUnits( TYPE.MODULE );
        if ( updateUnits == null || updateUnits.size() != 1 ) {
            return false;
        }

        UpdateUnit install = updateUnits.get( 0 );
        List<UpdateElement> availableUpdates = install.getAvailableUpdates();
        if ( availableUpdates == null || availableUpdates.size() != 1 ) {
            return false;
        }

        UpdateElement upEle = availableUpdates.get( 0 );

        OperationContainer<InstallSupport> createForInstall = OperationContainer.createForInstall();
        createForInstall.add( upEle );

        if ( !createForInstall.listAll().get( 0 ).getBrokenDependencies().isEmpty() ) {
            return false;
        }

        try {
            InstallSupport.Validator v = createForInstall.getSupport().doDownload( handle, true );
            InstallSupport.Installer i = createForInstall.getSupport().doValidate( v, handle );
            Restarter restarter = createForInstall.getSupport().doInstall( i, handle );
            if ( restarter != null ) {
                //createForInstall.getSupport().doRestartLater( restarter );
                createForInstall.getSupport().doRestart(restarter, handle);
            }
        } catch ( OperationException ex ) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the given nbmFile can be installed.
     * @param nbmFIle the file to install
     * @return true if it is installable
     */
    public static boolean isInstallable( File nbmFile ) {
        UpdateUnitProvider create = UpdateUnitProviderFactory.getDefault().create( nbmFile.getName(), new File[]{ nbmFile } );
        List<UpdateUnit> updateUnits = create.getUpdateUnits( TYPE.MODULE );
        if ( updateUnits == null || updateUnits.size() != 1 ) {
            return false;
        }

        UpdateUnit install = updateUnits.get( 0 );
        List<UpdateElement> availableUpdates = install.getAvailableUpdates();
        if ( availableUpdates == null || availableUpdates.size() != 1 ) {
            return false;
        }

        UpdateElement upEle = availableUpdates.get( 0 );

        OperationContainer<InstallSupport> createForInstall = OperationContainer.createForInstall();
        createForInstall.add( upEle );

        return createForInstall.listAll().get( 0 ).getBrokenDependencies().isEmpty();
    }

    /**
     * Attempts to install the given nbmfiles together.  This is the installer you would use
     * for a file drop, or for installing a list of nbms all needed for one plugin.
     * @param nbmFiles a group of nbm files
     * @return true if they were installed
     */
    public static boolean installPlugins( List<File> nbmFiles ) {
        
        File[] nbmFileArray = new File[nbmFiles.size()];
        int index=0;
        for (File afile: nbmFiles)
        {   
            nbmFileArray[index] = afile;
            index++;
            System.out.print("Attempting to install " + afile.getName() + "\n");
        }

        UpdateUnitProvider create = UpdateUnitProviderFactory.getDefault().create( "local install set", nbmFileArray );
        List<UpdateUnit> updateUnits = create.getUpdateUnits( TYPE.MODULE );
        if ( updateUnits == null || updateUnits.isEmpty() ) {
            return false;
        }

        OperationContainer<InstallSupport> createForInstall = OperationContainer.createForInstall();
        for ( UpdateUnit upUnit : updateUnits ) {
            System.out.println( upUnit.getCodeName() );
            List<UpdateElement> availableUpdates = upUnit.getAvailableUpdates();
            UpdateElement upEle = availableUpdates.get( 0 );
            createForInstall.add( upEle );
        }

        for ( OperationContainer.OperationInfo info : createForInstall.listAll() ) {
            if ( !info.getBrokenDependencies().isEmpty() ) {
                return false;
            }
        }

        try {
            InstallSupport.Validator v = createForInstall.getSupport().doDownload( null, true );
            InstallSupport.Installer i = createForInstall.getSupport().doValidate( v, null );
            Restarter restarter = createForInstall.getSupport().doInstall( i, null );
            if ( restarter != null ) {
                //createForInstall.getSupport().doRestartLater( restarter );
                createForInstall.getSupport().doRestart(restarter, null);
            }
        } catch ( OperationException ex ) {
            return false;
        }

        return true;
    }

    /**
     * Attempts to install the given nbmfiles together; with a progress handle
     * @param nbmFiles
     * @return
     */
    public static boolean installPlugins( List<File> nbmFiles, ProgressHandle handle ) {
        
        File[] nbmFileArray = new File[nbmFiles.size()];
        int index=0;
        for (File afile: nbmFiles)
        {   
            nbmFileArray[index] = afile;
            index++;
        }
           
        UpdateUnitProvider create = UpdateUnitProviderFactory.getDefault().create( "local install set", nbmFileArray );
        List<UpdateUnit> updateUnits = create.getUpdateUnits( TYPE.MODULE );
        if ( updateUnits == null || updateUnits.isEmpty() ) {
            return false;
        }

        OperationContainer<InstallSupport> createForInstall = OperationContainer.createForInstall();
        for ( UpdateUnit upUnit : updateUnits ) {
            System.out.println( upUnit.getCodeName() );
            List<UpdateElement> availableUpdates = upUnit.getAvailableUpdates();
            UpdateElement upEle = availableUpdates.get( 0 );
            createForInstall.add( upEle );
        }

        for ( OperationContainer.OperationInfo info : createForInstall.listAll() ) {
            if ( !info.getBrokenDependencies().isEmpty() ) {
                return false;
            }
        }

        try {
            InstallSupport.Validator v = createForInstall.getSupport().doDownload( handle, true );
            InstallSupport.Installer i = createForInstall.getSupport().doValidate( v, handle );
            Restarter restarter = createForInstall.getSupport().doInstall( i, handle );
            if ( restarter != null ) {
                //createForInstall.getSupport().doRestartLater( restarter );
                createForInstall.getSupport().doRestart(restarter, handle);
            }
        } catch ( OperationException ex ) {
            return false;
        }

        return true;
    }

    /**
     * Unpacks a file for installation
     * @param files
     * @return
     */
    public static ArrayList<File> unpackInstall(final File[] files)
    {
        ClothoDialogBox dialog = new ClothoDialogBox("Plugin Install", "Are you sure you want to install " + files[0].getName() + "?\nThis may require a restart of Clotho!");
        int optionChoice = dialog.show_optionDialog(1, 2);

        //If the user wants to proceed with the install
        if (optionChoice == 0) {
            System.out.print("Installing....\n");

            //check if the dropped file is a valid format
            boolean validFile = Collator.isValidCloFile(files);

            if (validFile) {
                final ArrayList<File> fileList = new ArrayList<File>();

                try {
                    //unzip the .clo file
                    ZipFile zipfile = new ZipFile(files[0]);
                    ZipEntry entry;

                    byte[] readData = new byte[1024];
                    File destinationFile;
                    FileOutputStream fos;
                    BufferedInputStream fis;
                    Enumeration e = zipfile.entries();

                    while (e.hasMoreElements()) {
                        entry = (ZipEntry) e.nextElement();
                        //Puts the files in a temp directory
                        destinationFile = File.createTempFile("temp", entry.getName());
                        destinationFile.deleteOnExit();
                        fis = new BufferedInputStream(zipfile.getInputStream(entry));

                        fos = new FileOutputStream(destinationFile);
                        System.out.println("Extracting: " + entry);
                        int count;
                        while ((count = fis.read(readData, 0, 1024))
                                != -1) {
                            fos.write(readData, 0, count);
                        }

                        fis.close();
                        fos.close();

                        fileList.add(destinationFile);
                        //System.out.println( "****.clo file contains " + destinationFile.getName() );
                    }

                } catch (ZipException ex) {
                    Exceptions.printStackTrace(ex);
                    
                    return null;
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                    
                    return null;
                }
                
                return fileList;
            }
            else {
                ClothoDialogBox dialog2 = new ClothoDialogBox("Plugin Install Error", "Plugin installation requires a valid .clo file!");
                dialog2.show_Dialog(0);
                return null;
            }
        }
        else
            return null;
    }

    /**
     * Returns true if the given set of nbm files are installable.
     * @param nbmFiles
     * @return
     */
    public static List<File> areInstallable( List<File> nbmFiles ) {
        //System.out.print("Checking installable for " + nbmFiles.toString() + " \n");
        boolean out = true;
        String appList = "";
        List<File> returnList = new ArrayList<File>();
        for(File afile : nbmFiles) {
            //System.out.print("File check for " + afile.getName() + "\n");
            if(!isInstallable(afile)) {
                out = false;
                System.out.print("Couldn't install " + afile.getName() + "\n");
                appList = appList + "\n" + afile.getName();
                /*
                ClothoDialogBox dialog2 = new ClothoDialogBox("Plugin Install Warning", afile.getName() + " is not installable!\n"
                        + "Make sure that the app is not already installed!\n"
                        + "For more see: " + helpURLBase + deployURL );
                dialog2.show_Dialog(0);
                 */
            }
            else
                returnList.add(afile);
        }

        if(returnList.isEmpty())
        {
            ClothoDialogBox dialog2 = new ClothoDialogBox("Plugin Install Warning", appList + " are not installable!\n"
                        + "Make sure that the app is not already installed!\n"
                        + "For more see: " + helpURLBase + deployURL );
                        dialog2.show_Dialog(0);
        }

        return returnList;
    }


     /**
     * Determines if the file is valid .clo file
     * @param files
     * @return
     */
    public static boolean isValidCloFile(File[] files)
    {

      //This naturally has some potential loopholes but it is intended as a sanity check, not a security catch all
      //Make sure the file has .clo in it; yes we could enforce .clo at the end but this will suffice currently
        if (files[0].getName().contains(".clo")) {
            try {
                //Check if the file is a valid zip file
                ZipFile zipfile = new ZipFile(files[0]);
            } catch (ZipException ex) {
                return false;
            } catch (IOException ex) {
                return false;
            }
            return true;
        } 
        else {
            return false;
        }
    }


    /**
     * Uninstall a plugin from its UUID.  You get the UUID from the plugin wrapper by
     * calling getUUID().
     * @param pluginUUID the UUID from a PluginWrapper
     * @return true if it uninstalled
     */
    public static boolean uninstall( PluginWrapper pw ) {
        boolean lastUninstall = false;
        String pluginUUID = pw.getUUID();
        System.out.println("Collator uninstall going to remove pluginUUID " + pluginUUID);
        if(pluginUUID == null || pluginUUID.equals( "")) {
            System.out.println("Collator uninstall had null or blank pluginID");
            return false;
        }
        List<UpdateUnit> installedPlugins = UpdateManager.getDefault().getUpdateUnits( TYPE.MODULE );

        //Locate the update unit for this pluginUUID
        UpdateUnit amodule = null;
        for(UpdateUnit unit : installedPlugins) {
            //System.out.print(unit.getCodeName() + "\n");
            if(unit.getCodeName().equals( pluginUUID)) {
                amodule = unit;
                break;
            }
        }

        if(amodule == null) {
            System.out.println("Uninstaller failed to identify the module");
            return false;
        }

        else
            System.out.println("Uninstaller identified " + amodule.getInstalled().getCodeName());

        //This will determine if anything is dependent on the app being removed
        boolean dependent = false;
        ArrayList<String> dependentList = new ArrayList<String>();
        if(!dependentList.isEmpty())
        {
            //Check the dependencies
            for(PluginWrapper wrapper : AllClothoPlugins)
            {
                //System.out.print("Checking dep " + wrapper.getClothoDependencyObj().getDependencies() + " " + pluginUUID + "\n");
                if(wrapper.getClothoDependencyObj().isDependent(pluginUUID))
                {
                    dependentList.add(wrapper.getDisplayName());
                    dependent = true;
                }
            }
        }

        
        //This check is if anything is dependent on what you are uninstalling
        //If so, do not prevent the uninstall
        if(dependent)
        {
        //System.out.print(wrapper.getDisplayName() + " is dependent on " + pluginUUID + "\n");
                ClothoDialogBox dialog2 = new ClothoDialogBox("Uninstall Dependency",
                        dependentList.toString() + " are dependent on " + pw.getDisplayName() + "!\nCan't uninstall!");//\nContinuing will uninstall the dependent tools as well!");
                //int optionChoice = dialog2.show_optionDialog(2, 2);
                dialog2.show_Dialog(0);
                return false;
        }

        //Another check
        //This list will hold apps that can be removed since nothing depends on them
        ArrayList<PluginWrapper> uniqueDepList = new ArrayList<PluginWrapper>();
        //Get the list of the dependencies for the app to be removed
        ArrayList<String> depList = pw.getClothoDependencyObj().getDependencies();
        if (depList != null && !depList.isEmpty()) {
            /*Run through the dependencies and see if anything else is going to be dependent on these
            if not, prompt to remove them
             */
            //Go through all the dependencies
            for (String dep : depList) {
                boolean unique = true;
                //Go through all the apps
                for (PluginWrapper wrapper : AllClothoPlugins) {
                    //can ignore this app
                    if (wrapper != pw) {
                        ArrayList<String> otherDepList = wrapper.getClothoDependencyObj().getDependencies();
                        for (String otherdep : otherDepList) {
                            if (dep.equals(otherdep)) {
                                unique = false;
                            }
                        }

                    }

                }
                if (unique) {
                    //Add the wrapper to the list of things that are not needed by anything else
                    uniqueDepList.add(pluginUUIDHash.get(dep));
                }

            }
        }

        OperationContainer<OperationSupport> uninstaller = OperationContainer.createForDirectUninstall();
        uninstaller.add(amodule, amodule.getInstalled());
        OperationSupport support = uninstaller.getSupport();


        //OLD DEPENDENCY PIECE
        /*THE ABOVE WAS BEGINNING TO DEAL WITH REMOVING DEPENDENCIES
        ALSO NEED TO CHECK FOR DEPENDCIES AT START OF ALL THIS
         */
        
        /*
        //Get the ModuleInfo for the chosen module
        ModuleInfo itsInfo=null;
        java.util.Collection<ModuleInfo> modules = (java.util.Collection<ModuleInfo>) Lookup.getDefault().lookupAll(ModuleInfo.class);
        for(ModuleInfo info : modules) {
            if(info.getCodeNameBase().equals(amodule.getInstalled().getCodeName())) {
                itsInfo = info;
            }
        }

        if(itsInfo==null) {
            return false;
        }

        //Determine the depencies from itsInfo
        Set<Dependency> depencies = itsInfo.getDependencies();
        for(Dependency d : depencies ) {
            String depUUID = d.getName();
            System.out.println("Depencdent on: " + depUUID);
        }
        */


        try {
            support.doOperation(null);

            //Remove plugin from hashes
            pluginUUIDHash.remove(pw.getUUID());
            pluginNameHash.remove(pw.getDisplayName());
            PluginTypeEnum type = pw.getType();
            if(type.equals(PluginTypeEnum.ClothoTool)) {
                AllClothoTools.remove((ToolWrapper) pw);
            } else if(type.equals(PluginTypeEnum.ClothoViewer)) {
                AllClothoViewers.remove((ViewerWrapper) pw);
            } else if(type.equals(PluginTypeEnum.ClothoWidget)) {
                AllClothoWidgets.remove((WidgetWrapper) pw);
            } else if(type.equals(PluginTypeEnum.ClothoConnection)) {
                AllClothoConnections.remove((ConnectionWrapper) pw);
            } else if(type.equals(PluginTypeEnum.ClothoAlgorithm)) {
                AllClothoAlgorithms.remove((AlgorithmWrapper) pw);
            }

            AllClothoPlugins.remove(pw);
       
            Collector._rebootMode = true;
            //refreshDash();
            System.out.print("Actually uninstalled " + pw.getDisplayName() + "\n");
        } catch (OperationException ex) {
            System.out.println("Uninstaller failed to uninstall");
            return false;
        }

        //Now uninstall the items that where only depended on by the now uninstall module (if desired)
        if (!uniqueDepList.isEmpty()) {
            ClothoDialogBox dialog2 = new ClothoDialogBox("Uninstall Dependency",
                    pw.getDisplayName() + " was the only thing dependent on " + uniqueDepList.toString() + "!\nDo you wish to remove these apps as well?");
            int optionChoice = dialog2.show_optionDialog(1, 2);

            if(optionChoice == 0)
            {
                for (PluginWrapper uniquePW: uniqueDepList)
                    uninstall(uniquePW);
            }
        }

        return true;
    }

    /**
     * Way to "refresh" dash. Used after installing or uninstalling plugins
     */
    public static void refreshDash()
    {
         WidgetWrapper dash = (WidgetWrapper) getPluginByUUID(_dashBoardUUID);
         //Collector._rebootMode = true;
         dash.close();
         dash.launch();
    }

    /**
     * "_corePrefs" as the universal preference storage spot
     * This method is so that other methods can add and get these preferences Strings
     *
     * @param key the key for the preference string
     * @return String of the value, or "" if it didn't find it
     */
    public static String getPreference( String key ) {
        return _corePrefs.get( key, "" );
    }

    /**
     * Add preferences from external methods
     *
     * @param key for the preference
     * @param value the String value of the preference
     */
    public static void putPreference( String key, String value ) {
        _corePrefs.put( key, value );
    }

    /**
     *
     * @param cw
     */
    public static void setDefaultConnection(ConnectionWrapper cw) {
        if(cw!=null) {
            Collator.putPreference("DefaultConnectionID", cw.getUUID());
        }
    }

    /**
     *
     * @param uuid
     */
    public static void setDashUUID( String uuid ) {
        _dashBoardUUID = uuid;
    }

    /**
     *
     * @param uuid
     */
    public static void setPluginManagerUUID( String uuid ) {
        _pluginManagerUUID = uuid;
    }

    /**Called during boot to register viewers on object types
     *
     * @param atool the tool being registered
     * @param itstype the ObjType as a String
     */
    public static void registerViewer( ViewerWrapper atool, List<String> types ) {
        for ( String t : types ) {
            getCore()._viewerRegistry.addViewer( atool, t );
            atool.putViewerType( t );
        }
        registerPlugin( atool );
    }

    public static void registerTool( ToolWrapper atool, List<String> types ) {
        for ( String t : types ) {
            getCore()._viewerRegistry.addViewer( atool, t );
            atool.putViewerType( t );
        }
        registerPlugin( atool );
        AllClothoTools.add( atool );
    }

    public static void registerPlugin( PluginWrapper awrap ) {
        pluginUUIDHash.put( awrap.getUUID(), awrap );
        pluginNameHash.put( awrap.getDisplayName(), awrap );
        AllClothoPlugins.add(awrap);
    }

    public static void registerConnection( ConnectionWrapper wrap ) {
        registerPlugin( wrap );
        AllClothoConnections.add( wrap );
    }

    public static void registerAlgorithm( AlgorithmWrapper wrap ) {
        registerPlugin( wrap );
        AllClothoAlgorithms.add( wrap );
    }

    public static void registerWidget( WidgetWrapper wrap ) {
        registerPlugin( wrap );
        AllClothoWidgets.add( wrap );
    }

    /**
     * Get all available viewers for a given ObjType
     * @param type of ObjBase
     * @return the ViewerWrappers that can launch on the ObjBase
     */
    public static ArrayList<ViewerWrapper> getAvailableViewers( ObjType type ) {
        return getCore()._viewerRegistry.getViewers( type );
    }

    /**
     * Retrieve the ViewerWrapper that the user has set as the default viewer
     * for a given type of ObjBase
     * @param type the ObjType of the ObjBase in question
     * @return the singular preferred viewer for that type of ObjBase or null if none is set
     */
    public static ViewerWrapper getPreferredViewer( ObjType type ) {
        String uuid = getPreference( "preferredViewerOf" + type.toString() );
        if ( uuid == null || uuid.equals( "" ) ) {
            return null;
        }

        return (ViewerWrapper) pluginUUIDHash.get( uuid );
    }

    /**
     * Set a Viewer plugin (or Tool plugin) as the default viewer of a given object type.
     * This is usually preceded by a call to getAvailableViewers(objtype), then the user
     * chooses the one they want.
     * @param type the ObjType in question
     * @param wrapper the wrapper for the plugin chosen as viewer for that ObjType
     */
    public static void putPreferredViewer( ObjType type, ViewerWrapper wrapper ) {
        putPreference( "preferredViewerOf" + type.toString(), wrapper.getUUID() );
    }

    /**
     * Set that a particular Widget plugin should not be launched at startup
     * @param ww
     */
    public static void hideWidget( WidgetWrapper ww ) {
        _corePrefs.putBoolean( "show" + ww.getUUID(), false );
    }

    /**
     * Set that a particular Widget plugin should be launched at startup
     * @param ww
     */
    public static void showWidget( WidgetWrapper ww ) {
        _corePrefs.putBoolean( "show" + ww.getUUID(), true );
    }

    /**
     * Launch a plugin manager tool.  One plugin manager will always be available.  One is
     * encoded in Clotho core, but additional ones can be added as plugins.  Whichever one
     * is set as the default plugin manager will get launched here.
     */
    public static void launchPluginManager() {
        if ( _pluginManagerUUID != null ) {
            PluginWrapper pw = getPluginByUUID( _pluginManagerUUID );
            try {
                ToolWrapper tw = (ToolWrapper) pw;
                tw.launchTool();
            } catch ( java.lang.ClassCastException e ) {
                DefaultPluginManager dialog = new DefaultPluginManager( new javax.swing.JFrame(), true );
            }
        } else {
            DefaultPluginManager dialog = new DefaultPluginManager( new javax.swing.JFrame(), true );
        }
    }


    /**
     * Determine whether a Widget plugin should be launched at startup
     * @param ww
     * @return
     */
    public static boolean isWidgetDesired( WidgetWrapper ww ) {
        return _corePrefs.getBoolean( "show" + ww.getUUID(), true );
    }

    /**
     *
     */
    private class ViewerWrapHasher {

        private Map<String, ArrayList<ViewerWrapper>> hash;

        public ViewerWrapHasher() {
            hash = new HashMap<String, ArrayList<ViewerWrapper>>();
            for ( ObjType ot : ObjType.values() ) {
                ArrayList<ViewerWrapper> atw = new ArrayList<ViewerWrapper>();
                hash.put( ot.toString(), atw );
            }
        }

        public void addViewer( ViewerWrapper tw, String objtype ) {
            ArrayList<ViewerWrapper> atw = hash.get( objtype );
            atw.add( tw );
            hash.put( objtype, atw );
            if ( !AllClothoViewers.contains( tw ) ) {
                AllClothoViewers.add( tw );
            }
        }

        public ArrayList<ViewerWrapper> getViewers( ObjType type ) {
            return hash.get( type.toString() );
        }
    }

    /**
     * Get all Tool plugins currently available via their wrappers
     * @return
     */
    public static ArrayList<ToolWrapper> getAllTools() {
        return AllClothoTools;
    }

    /**
     * Get all Widget plugins currently available via their wrappers
     * @return
     */
    public static ArrayList<WidgetWrapper> getAllWidgets() {
        return AllClothoWidgets;
    }

    /**
     * Get all Connection plugins currently available via their wrappers
     * @return
     */
    public static ArrayList<ConnectionWrapper> getAllConnections() {
        return AllClothoConnections;
    }

    /**
     * Get all Viewer plugins currently available via their wrappers
     * @return
     */
    public static ArrayList<ViewerWrapper> getAllViewers() {
        return AllClothoViewers;
    }

    /**
     * Get all Algorithm plugins currently available via their wrappers
     * @return
     */
    public static ArrayList<AlgorithmWrapper> getAllAlgorithms() {
        return AllClothoAlgorithms;
    }

    /**
     * Get all the plugins available via their wrappers
     * @return
     */
    public static ArrayList<PluginWrapper> getAllPlugins(){
        return AllClothoPlugins;
    }


    /**
     * Retrieve a plugin by its display name
     * @param name
     * @return
     */
    public static PluginWrapper getPluginByName( String name ) {
        return pluginNameHash.get( name );
    }

    /**
     * Retrieve a plugin by its uuid
     * @param uuid
     * @return
     */
    public static PluginWrapper getPluginByUUID( String uuid ) {
        return pluginUUIDHash.get( uuid );
    }

    /**
     * Get the Dashboard UUID
     * @return UUID for the current Dashboard
     */
    public static String getDashBoardUUID(){
        return _dashBoardUUID;
    }


    /**
     * 
     */
    public static enum LogLevel {

        MESSAGE, WARNING, ERROR;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////

    private static Map<String, PluginWrapper> pluginUUIDHash;
    private static Map<String, PluginWrapper> pluginNameHash;

    private static Collator _core = null;
    private static Preferences _corePrefs;

    //PlugIn information
    private ViewerWrapHasher _viewerRegistry = new ViewerWrapHasher();
    private static ArrayList<ToolWrapper> AllClothoTools;
    private static ArrayList<AlgorithmWrapper> AllClothoAlgorithms;
    private static ArrayList<WidgetWrapper> AllClothoWidgets;
    private static ArrayList<ViewerWrapper> AllClothoViewers;
    private static ArrayList<ConnectionWrapper> AllClothoConnections;

    private static ArrayList<PluginWrapper> AllClothoPlugins;

    private static String _pluginManagerUUID;
    private static String _dashBoardUUID;



    public static final String helpURLBase = "http://wiki.bu.edu/ece-clotho/index.php";

    public static final String deployURL = "/Deploying_App";

    static {
        AllClothoPlugins = new ArrayList<PluginWrapper>();
        AllClothoTools = new ArrayList<ToolWrapper>();
        AllClothoAlgorithms = new ArrayList<AlgorithmWrapper>();
        AllClothoWidgets = new ArrayList<WidgetWrapper>();
        AllClothoViewers = new ArrayList<ViewerWrapper>();
        AllClothoConnections = new ArrayList<ConnectionWrapper>();
        pluginUUIDHash = new HashMap<String, PluginWrapper>();
        pluginNameHash = new HashMap<String, PluginWrapper>();
        _corePrefs = Preferences.userNodeForPackage( Collator.class );
    }
}
