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

package org.clothocad.tool.groovyscripter;

/**
 *
 * @author J. Christopher Anderson
 */

import groovy.lang.GroovyShell;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import jsyntaxpane.DefaultSyntaxKit;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.misc.CommandHelp;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;


public class gframe extends JFrame  {
    public gframe() {
        super("Groovy Scripter");
        setIconImage(ImageSource.getTinyLogo());
        initComponents();
    }
    public static void main(String[] args) throws Exception {
        new gframe();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        DefaultSyntaxKit.initKit();

        initMenu();

        JScrollPane scrPane = new JScrollPane(codeEditor);
        getContentPane().add(scrPane, BorderLayout.CENTER);
        getContentPane().doLayout();
        codeEditor.setContentType("text/groovy");

        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initMenu() {
        menuBar = new javax.swing.JMenuBar();
        setJMenuBar(menuBar);

        //Put in file menu items
        fileMenu = new javax.swing.JMenu();
        fileMenu.setText("File");
        menuBar.add(fileMenu);

            JMenuItem saveItem = new JMenuItem();
            saveItem.setText("Save script");
            saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
            saveItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    save(false);
                }
            });
            fileMenu.add(saveItem);

            JMenuItem saveasItem = new JMenuItem();
            saveasItem.setText("Save as...");
            saveasItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    save(true);
                }
            });
            fileMenu.add(saveasItem);

            JMenuItem openItem = new JMenuItem();
            openItem.setText("Open script");
            openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
            openItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.addChoosableFileFilter(new MyFilter());

                    // Open file dialog.
                    fileChooser.showOpenDialog(gframe.this);
                    saveFile = fileChooser.getSelectedFile();
                    System.out.println(saveFile.getAbsolutePath());

                    //Read the file
                    StringBuilder text = new StringBuilder();
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(saveFile, "UTF-8");
                        while (scanner.hasNextLine()){
                            text.append(scanner.nextLine());
                            text.append("\n");
                        }
                        scanner.close();
                        codeEditor.setText(text.toString());
                    } catch(Exception e) {
                        e.printStackTrace();
                        scanner.close();
                    }
            }});


            fileMenu.add(openItem);

            JMenuItem closeItem = new JMenuItem();
            closeItem.setText("Close window");
            closeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
            closeItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dispose();
                }
            });
            fileMenu.add(closeItem);

        //Put in edit menu items
        runMenu = new javax.swing.JMenu();
        runMenu.setText("Run");
        menuBar.add(runMenu);
            JMenuItem runscriptItem = new JMenuItem();
            runscriptItem.setText("Run groovy script");
            runscriptItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
            runscriptItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    runGroovy();
                }
            });
            runMenu.add(runscriptItem);

        //Put in help menu items
        helpMenu = new javax.swing.JMenu();
        helpMenu.setText("Help");
        menuBar.add(helpMenu);
            JMenuItem commandsItem = new JMenuItem();
            commandsItem.setText("Command List");
            commandsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
            commandsItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    FileObject fo = FileUtil.getConfigFile("org/clothocad/tool/groovyscripter/commands.csv");
                    new CommandHelp(fo, "Groovy Scripter","", "AndersonClothoBugs@gmail.com", "");
                }
            });
            helpMenu.add(commandsItem);

            JMenu exampleItem = new JMenu();
            exampleItem.setText("Examples");
            helpMenu.add(exampleItem);

                JMenuItem findPartItem = new JMenuItem();
                findPartItem.setText("Find a part");
                findPartItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        codeEditor.setText(
                            "apart = Part.retrieveByName(\"sbb29\")\n"
                          + "println(apart.getName());\n"
                          + "//semicolons and parenthesis aren't required \n"
                          + "println apart.getName()\n\n"
                          + "println(apart.getUUID());"
                          + "println(apart.getSeq().toString());");
                    }
                });
                exampleItem.add(findPartItem);

                JMenuItem listcollItem = new JMenuItem();
                listcollItem.setText("List a collection");
                listcollItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        codeEditor.setText(
                            "acoll = Collector.getCollection(\"2965433626953184012695320aa20001\")\n"
                          + "println(acoll.getName());\n"
                          + "//Loops in groovy look slightly different:\n"
                          + "for(apart in acoll.getAll(ObjType.PART)) {\n"
                          + "    println(apart.getName());\n"
                          + "}");
                    }
                });
                exampleItem.add(listcollItem);

                JMenuItem seqQueryItem = new JMenuItem();
                seqQueryItem.setText("Query parts with sequence");
                seqQueryItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        codeEditor.setText(
                            "c = Collector.getDefaultConnection();\n"
                          + "mainQuery = c.createQuery( ObjType.PART );\n"
                          + "seqQuery = mainQuery.createAssociationQuery( Part.Fields.SEQUENCE );\n"
                          + "seqQuery.contains( NucSeq.Fields.SEQUENCE, \"TAAAATTCG\", false );\n"
                          + "results = mainQuery.getResults();\n"
                          + "\n"
                          + "for(obj in results) {\n"
                          + "    println(obj.getName())\n"
                          + "}");
                    }
                });
                exampleItem.add(seqQueryItem);

                JMenuItem actorExItem = new JMenuItem();
                actorExItem.setText("Run Algorithm Workflow");
                actorExItem.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        codeEditor.setText(
                            "//Get the Actors from a plugin\n"
                          + "saa = Collator.getPluginByUUID(\"org.clothocad.algorithm.stringappend\");\n"
                          + "println saa.getDescription();\n"
                          + "stringAlg = saa.getAlgorithm().createAlgorithm();\n"
                          + "stringAlg2 = saa.getAlgorithm().createAlgorithm();\n"
                          + "\n"
                          + "//Create the workflow and add the actors\n"
                          + "workflow = new CompositeActor();\n"
                          + "workflow.addAlgorithm( stringAlg );\n"
                          + "workflow.addAlgorithm( stringAlg2 );\n"
                          + "\n"
                          + "//Create some Stringtoken inputs to the actor and put them into Inports\n"
                          + "s1 = new StringToken( \"hello \" );\n"
                          + "s2 = new StringToken( \"world, \" );\n"
                          + "stringAlg.getInputs().get( 0 ).put( s1 );\n"
                          + "stringAlg.getInputs().get( 1 ).put( s2 );\n"
                          + "\n"
                          + "s3 = new StringToken( \"What's up?\" );\n"
                          + "stringAlg2.getInputs().get( 1 ).put( s3 );\n"
                          + "\n"
                          + "//Link the two append algorithms together\n"
                          + "workflow.link( stringAlg.getOutputs().get( 0 ),stringAlg2.getInputs().get( 0 ) );\n"
                          + "\n"
                          + "//Run it\n"
                          + "println( workflow.run() );\n"
                          + "\n"
                          + "//Print out results\n"
                          + "println( stringAlg2.getOutputs().get( 0 ).get() );");
                    }
                });
                exampleItem.add(actorExItem);
    }

    private void save(boolean usefile) {
        if(usefile || saveFile==null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new MyFilter());

            // Open file dialog.
            fileChooser.showSaveDialog(gframe.this);
            saveFile = fileChooser.getSelectedFile();
            if(!saveFile.getAbsolutePath().endsWith(".groovy")) {
                String newfilename = saveFile.getAbsolutePath() + ".groovy";
                saveFile = new File(newfilename);
            }
            System.out.println(saveFile.getAbsolutePath());
        }

        // Save the file
        String text = codeEditor.getText();
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(saveFile), "UTF-8");
            out.write(text);
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private class MyFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(".groovy");
        }
        @Override
        public String getDescription() {
            return "*.groovy";
        }
    }

    private void runGroovy() {
        String runcode = codeEditor.getText();
        final StringBuffer sb = new StringBuffer();
        sb.append("import org.clothocore.api.actor.*\n");
        sb.append("import org.clothocore.api.actor.data.*\n");
        sb.append("import org.clothocore.api.actor.io.*\n");
        sb.append("import org.clothocore.api.actor.workflow.*\n");
        sb.append("import org.clothocore.api.core.*\n");
        sb.append("import org.clothocore.api.core.wrapper.*\n");
        sb.append("import org.clothocore.api.core.wrapper.support.*\n");
        sb.append("import org.clothocore.api.data.*\n");
        sb.append("import org.clothocore.api.dnd.*\n");
        sb.append("import org.clothocore.core.*\n");
        sb.append("import org.clothocore.images.*\n");
        sb.append("import org.clothocore.api.plugin.*\n");
        sb.append("import org.clothocore.util.basic.*\n");
        sb.append("import org.clothocore.util.blast.*\n");
        sb.append("import org.clothocore.util.buttons.*\n");
        sb.append("import org.clothocore.util.chooser.*\n");
        sb.append("import org.clothocore.util.def.*\n");
        sb.append("import org.clothocore.util.dialog.*\n");
        sb.append("import org.clothocore.util.elements.*\n");
        sb.append("import org.clothocore.util.frames.*\n");
        sb.append("import org.clothocore.util.misc.*\n");
        sb.append("import org.clothocore.util.panels.*\n");
        sb.append("import org.clothocore.util.xml.*\n");

        sb.append("import org.clothocad.library.groovytranslator.*\n");
        
        sb.append("java.util.ArrayList\n");
        sb.append("java.util.HashMap\n");
        sb.append("java.util.HashSet\n");
        sb.append("java.util.Set\n");
        sb.append("java.util.Hashtable\n");
        sb.append("java.util.List\n");

        /*
         * These are all for rerouting static methods to instances.
         * Though they are uncapped class names, for whatever reason
         * Groovy doesn't mind the capitalization
         */
        sb.append("objType = new groovyObjType()\n");
        sb.append("collector = new groovyCollector()\n");
        sb.append("collator = new groovyCollator().getCollator()\n");
        sb.append("objBase = new groovyObjBase()\n");
        sb.append("part = new groovyPart()\n");
        sb.append("format = new groovyFormat()\n");
        sb.append("vector = new groovyVector()\n");
        sb.append("strain = new groovyStrain()\n");
        sb.append("plasmid = new groovyPlasmid()\n");
        sb.append("person = new groovyPerson()\n");
        sb.append("port = new groovyPort()\n");
        sb.append("lab = new groovyLab()\n");
        sb.append("institution = new groovyInstitution()\n");
        sb.append("collection = new groovyCollection()\n");
        sb.append("feature = new groovyFeature()\n");
        sb.append("actor = new groovyActor()\n");
        sb.append("println(\"Groovy ready for code!\")\n");

        sb.append(runcode);
        final GroovyShell g = new GroovyShell();

        new Thread() {
            @Override
            public void run() {
                try {
                    g.evaluate(sb.toString());
                    System.out.println("gframe Done with Groovy script");
                } catch(Exception e) {
                    System.out.println("Error occurred executing Groovy script");
                    e.printStackTrace();
                }
            }
        }.start();

    }
    /*
inst = new institution("hi", "hi", "hi", "hi");
println(inst.getName());
     */

/*-----------------
     variables
 -----------------*/
    final JEditorPane codeEditor = new JEditorPane();

    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu runMenu;
    private javax.swing.JMenu helpMenu;
    private File saveFile;
    private javax.swing.JMenuBar menuBar;
}