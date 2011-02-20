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

package org.clothocad.tool.sequenceanalysistool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import net.iharder.dnd.FileDrop;
import org.clothocad.library.sequencelight.nucSeqEditor;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.BooleanToken;
import org.clothocore.api.actor.data.ListToken;
import org.clothocore.api.actor.data.ObjBaseToken;
import org.clothocore.api.actor.data.StringToken;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.core.wrapper.AlgorithmWrapper;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class SeqFrame extends JFrame {
    public SeqFrame() {
        super("Sequence Checker");
        setIconImage(ImageSource.getTinyLogo());
        templateSeq = new NucSeq("");
        templateSeq.setTransient();
        initComponents();
    }

    public SeqFrame(ObjBase obj) {
        super("PCR Predictor");
        if(obj==null) {
            return;
        }
        switch(obj.getType()) {
            case NUCSEQ:
                templateSeq = (NucSeq) obj;
                break;
            case PART:
                TemplateObj = obj;
                templateSeq = ((Part) obj).getSeq();
                break;
            case PLASMID:
                TemplateObj = obj;
                templateSeq = ((Plasmid) obj).getSeq();
                break;
            case VECTOR:
                TemplateObj = obj;
                templateSeq = ((Vector) obj).getSeq();
                break;
            case OLIGO:
                TemplateObj = obj;
                templateSeq = ((Oligo) obj).getSeq();
                break;
            case FEATURE:
                TemplateObj = obj;
                templateSeq = ((Feature) obj).getSeq();
                break;
        }


        initComponents();
    }

    private void initComponents() {
        getContentPane().setBackground(navyblue);
        setLayout(new BorderLayout());
        Box headerPanel = new Box(BoxLayout.X_AXIS);
        headerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(headerPanel, BorderLayout.NORTH);
            headerPanel.add(Box.createHorizontalStrut(2));

            whiteLabel partlabel = new whiteLabel(partImg);
            headerPanel.add(partlabel);

            whiteLabel vectorlabel = new whiteLabel(vectorImg);
            headerPanel.add(vectorlabel);

            whiteLabel featurelabel = new whiteLabel(featureImg);
            headerPanel.add(featurelabel);

            whiteLabel tmplabel = new whiteLabel(plasmidImg);
            tmplabel.setText("Retrieve Sequence:");
            headerPanel.add(tmplabel);
            headerPanel.add(Box.createHorizontalStrut(2));

            final JTextField tmpfield = new JTextField();
            tmpfield.setPreferredSize(new Dimension(100,22));
            tmpfield.addFocusListener(new FocusAdapter(){
                @Override
                public void focusLost(FocusEvent e) {
                    if(!Collector.isConnected()) {
                        return;
                    }
                    String text = tmpfield.getText().trim();
                    if(text==null || text.equals("")) {
                        return;
                    }
                    NucSeq out = null;
                    while(true) {
                        Part outpart = Part.retrieveByName(text);
                        if(outpart!=null) {
                            out = outpart.getSeq();
                            break;
                        }
                        Vector outvect = Vector.retrieveByName(text);
                        if(outvect!=null) {
                            out = outvect.getSeq();
                            break;
                        }
                        Feature outfeat = Feature.retrieveByName(text);
                        if(outfeat!=null) {
                            out = outfeat.getSeq();
                            break;
                        }
                        Plasmid outplas = Plasmid.retrieveByName(text);
                        if(outplas!=null) {
                            out = outplas.getSeq();
                            break;
                        }
                        break;
                    }

                    if(out==null) {
                        return;
                    }
                    templateSeq.changeSeq(out.getSeq());
                }
            });
            headerPanel.add(tmpfield);

        Box leftpanel = new Box(BoxLayout.Y_AXIS);
        leftpanel.setBackground(Color.BLUE);
        add(leftpanel);
            templateArea = new nucSeqEditor(templateSeq, this);
            templateArea.setPreferredSize(new Dimension(450,380));
            leftpanel.add(templateArea);

            JPanel lowerpanel = new JPanel();
            lowerpanel.setOpaque(false);
            leftpanel.add(lowerpanel);
            lowerpanel.setLayout(new BorderLayout());
            lowerpanel.setMaximumSize(new Dimension(10000,26));

            JButton goButton = new JButton("Run");
            goButton.setPreferredSize(new Dimension(100,23));
            goButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runCalc();
            } });
            lowerpanel.add(goButton, BorderLayout.WEST);

            buttonPanel = new Box(BoxLayout.X_AXIS);
            lowerpanel.add(buttonPanel, BorderLayout.CENTER);

        //Put in the plugin fileDrop
        new FileDrop( getContentPane(), new FileDrop.Listener() {
            @Override
            public void filesDropped( java.io.File[] files ) {
                pluginDropped( files );
            }
        } );

        pack();
        setVisible(true);
    }

    /**
     * This is the main thing for running the algorithm
     */
    private void runCalc() {
        //Get the Actors from a plugin
        Actor seqActor = null;
        try {
            AlgorithmWrapper saa = (AlgorithmWrapper) Collator.getPluginByUUID("org.clothocad.algorithm.seqanalyzer");
            System.out.println(saa.getUUID());
            seqActor = saa.getAlgorithm().createAlgorithm();
        } catch(Exception err) {
            JOptionPane.showMessageDialog(this,
                "It appears that the Sequencing algorithm has been uninstalled.",
                "Algorithm Missing",
                JOptionPane.ERROR_MESSAGE);
        }
        if(seqActor==null) {
            return;
        }

        //Create the tokens for inputs
        String name = "";
        if(TemplateObj!=null) {
            name = TemplateObj.getName();
        }
        ObjBaseToken seqtoken = new ObjBaseToken(templateSeq);
        StringToken seqName = new StringToken( name );
        ObjBaseToken author = new ObjBaseToken(Collector.getCurrentUser());
        ListToken filelist = new ListToken(File.class);
        BooleanToken throwresult = new BooleanToken(true);

        //Poupulate the file list
        ArrayList<File> files = new ArrayList<File>();
        for(String filepath : fileNames) {
            File afile = new File(filepath);
            if(afile.exists()) {
                files.add(afile);
            }
        }
        filelist.setData(files);

        //Install the input ports
        seqActor.getInputs().get( 0 ).put( seqtoken );
        seqActor.getInputs().get( 1 ).put( seqName );
        seqActor.getInputs().get( 2 ).put( author );
        seqActor.getInputs().get( 3 ).put( filelist );
        seqActor.getInputs().get( 4 ).put( throwresult );

        //Run it
        System.out.println( seqActor.run() );
    }

    private void pluginDropped(File[] files) {
        for(File afile : files) {
            if(!afile.exists()) {
                continue;
            }
            System.out.println(afile.getName());
            fileNames.add(afile.getAbsolutePath());
        }
        putInFiles();
    }

    private void putInFiles() {
        System.out.println("fileimage width : " + fileImage.getIconWidth());
        buttonPanel.removeAll();
        buttonPanel.add(Box.createVerticalStrut(15));
        for(String str : fileNames) {
            File afile = new File(str);
            final String name = afile.getName();

            final fileButton tb = new fileButton(afile.getAbsolutePath(), fileImage);
            buttonPanel.add(tb);
            tb.setToolTipText(name);
            tb.setEnterAlpha(0.8f);
            tb.setExitAlpha(1.0f);
            tb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fileNames.remove(tb.filepath);
                    putInFiles();
                }
            });
        }
        validate();
        repaint();
    }

    private class fileButton extends TransparentButton {
        public fileButton(String filep, ImageIcon icon) {
            super(icon);
            filepath = filep;
        }

        //VARIABLES//
        public String filepath;
    }

    private class whiteLabel extends JLabel {
        public whiteLabel(String name) {
            super(name);
            init();
        }
        public whiteLabel(ImageIcon icon) {
            super(icon);
            init();
        }
        private void init() {
            setForeground(Color.WHITE);
            setFont(arialItal12);
        }
    }

/*-----------------
     variables
 -----------------*/
    private static final Color navyblue = new Color(35, 48, 64);
    private static final Font arialItal12 = new Font("Arial", Font.ITALIC, 12);

    nucSeqEditor templateArea;
    nucSeqEditor prodArea;

    private Box buttonPanel;

    private HashSet<String> fileNames = new HashSet<String>();

    private static final ImageIcon oligoImg = ImageSource.getObjectImage(ObjType.OLIGO, 17);
    private static final ImageIcon partImg = ImageSource.getObjectImage(ObjType.PART, 17);
    private static final ImageIcon vectorImg = ImageSource.getObjectImage(ObjType.VECTOR, 17);
    private static final ImageIcon featureImg = ImageSource.getObjectImage(ObjType.FEATURE, 17);
    private static final ImageIcon plasmidImg = ImageSource.getObjectImage(ObjType.PLASMID, 17);
    private static final ImageIcon fileImage = ImageUtilities.loadImageIcon( "org/clothocad/tool/sequenceanalysistool/gui/abifile.png", false );

    NucSeq templateSeq;

    private ObjBase TemplateObj;
}
