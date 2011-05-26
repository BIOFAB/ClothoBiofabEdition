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

package org.clothocad.tool.pcrpredict;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocad.library.sequencelight.nucSeqEditor;
import org.clothocore.util.basic.ImageSource;

/**
 *
 * @author J. Christopher Anderson
 */
public class Calculator extends JFrame {

    public Calculator() {
        super("PCR Predictor");
        setIconImage(ImageSource.getTinyLogo());
        templateSeq = new NucSeq("");
        templateSeq.setTransient();
        oligo1Seq = new NucSeq("");
        oligo1Seq.setTransient();
        oligo2Seq = new NucSeq("");
        oligo2Seq.setTransient();
        productSeq = new NucSeq("");
        productSeq.setTransient();
        initComponents();
    }

    public Calculator(NucSeq seq) {
        super("PCR Predictor");
        templateSeq = seq;
        oligo1Seq = new NucSeq("");
        oligo1Seq.setTransient();
        oligo2Seq = new NucSeq("");
        oligo2Seq.setTransient();
        productSeq = new NucSeq("");
        productSeq.setTransient();
        initComponents();
    }

    private void initComponents() {
        getContentPane().setBackground(navyblue);
        setLayout(new BorderLayout());
        Box headerPanel = new Box(BoxLayout.X_AXIS);
        headerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(headerPanel, BorderLayout.NORTH);

            whiteLabel o1label = new whiteLabel(oligoImg);
            o1label.setText("For:");
            headerPanel.add(o1label);
            headerPanel.add(Box.createHorizontalStrut(2));

            final JTextField o1field = new JTextField();
            o1field.setPreferredSize(new Dimension(75,22));
            o1field.addFocusListener(new FocusAdapter(){
                @Override
                public void focusLost(FocusEvent e) {
                    if(!Collector.isConnected()) {
                        return;
                    }
                    String text = o1field.getText().trim();
                    if(text==null || text.equals("")) {
                        return;
                    }
                    Oligo out = Oligo.retrieveByName(text);
                    if(out==null) {
                        return;
                    }
                    oligo1Seq.changeSeq(out.getSeq().getSeq());
                }
            });
            headerPanel.add(o1field);
            headerPanel.add(Box.createHorizontalStrut(2));

            whiteLabel o2label = new whiteLabel(oligoImg);
            o2label.setText("Rev:");
            headerPanel.add(o2label);
            headerPanel.add(Box.createHorizontalStrut(2));

            final JTextField o2field = new JTextField();
            o2field.setPreferredSize(new Dimension(75,22));
            o2field.addFocusListener(new FocusAdapter(){
                @Override
                public void focusLost(FocusEvent e) {
                    if(!Collector.isConnected()) {
                        return;
                    }
                    String text = o2field.getText().trim();
                    if(text==null || text.equals("")) {
                        return;
                    }
                    Oligo out = Oligo.retrieveByName(text);
                    if(out==null) {
                        return;
                    }
                    oligo2Seq.changeSeq(out.getSeq().getSeq());
                }
            });
            headerPanel.add(o2field);
            headerPanel.add(Box.createHorizontalStrut(2));

            whiteLabel partlabel = new whiteLabel(partImg);
            headerPanel.add(partlabel);

            whiteLabel vectorlabel = new whiteLabel(vectorImg);
            headerPanel.add(vectorlabel);

            whiteLabel featurelabel = new whiteLabel(featureImg);
            headerPanel.add(featurelabel);

            whiteLabel tmplabel = new whiteLabel(plasmidImg);
            tmplabel.setText("Template:");
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

        JPanel dominantPanel = new JPanel();
        dominantPanel.setOpaque(false);
        GridLayout gl = new GridLayout(1,2);
        gl.setHgap(5);
        dominantPanel.setLayout(gl);
        add(dominantPanel, BorderLayout.CENTER);
        
        Box leftpanel = new Box(BoxLayout.Y_AXIS);
        leftpanel.setBackground(Color.BLUE);
        dominantPanel.add(leftpanel);

            whiteLabel oligo1label = new whiteLabel("Forward Oligo");
            leftpanel.add(oligo1label);

            oligo1field = new nucSeqEditor(oligo1Seq, this);
            oligo1field.setSize(250,22);
            leftpanel.add(oligo1field);

            whiteLabel oligo2label = new whiteLabel("Reverse Oligo");
            leftpanel.add(oligo2label);

            oligo2field = new nucSeqEditor(oligo2Seq, this);
            oligo2field.setSize(250,22);
            leftpanel.add(oligo2field);

            whiteLabel templateLabel = new whiteLabel("Template");
            leftpanel.add(templateLabel);

            templateArea = new nucSeqEditor(templateSeq, this);
            templateArea.setPreferredSize(new Dimension(250,180));
            leftpanel.add(templateArea);

            JButton goButton = new JButton("Calculate");
            goButton.setPreferredSize(new Dimension(100,23));
            goButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runCalc();
            } });
            leftpanel.add(goButton);

        Box rightpanel = new Box(BoxLayout.Y_AXIS);
        rightpanel.setBackground(Color.RED);
        dominantPanel.add(rightpanel);

            whiteLabel productLabel = new whiteLabel("Product");
            rightpanel.add(productLabel);

            prodArea = new nucSeqEditor(productSeq, this);
            prodArea.setPreferredSize(new Dimension(250,400));
            rightpanel.add(prodArea);
            
        pack();
        setVisible(true);
    }

    private void runCalc() {
        predictor p = new predictor(oligo1Seq, oligo2Seq, templateSeq);
        productSeq.changeSeq(p.getResult());
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

    nucSeqEditor oligo1field;
    nucSeqEditor oligo2field;
    nucSeqEditor templateArea;
    nucSeqEditor prodArea;

    private static final ImageIcon oligoImg = ImageSource.getObjectImage(ObjType.OLIGO, 17);
    private static final ImageIcon partImg = ImageSource.getObjectImage(ObjType.PART, 17);
    private static final ImageIcon vectorImg = ImageSource.getObjectImage(ObjType.VECTOR, 17);
    private static final ImageIcon featureImg = ImageSource.getObjectImage(ObjType.FEATURE, 17);
    private static final ImageIcon plasmidImg = ImageSource.getObjectImage(ObjType.PLASMID, 17);

    NucSeq oligo1Seq;
    NucSeq oligo2Seq;
    NucSeq productSeq;
    NucSeq templateSeq;
}
