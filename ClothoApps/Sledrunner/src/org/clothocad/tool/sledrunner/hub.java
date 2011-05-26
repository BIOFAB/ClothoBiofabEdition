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

package org.clothocad.tool.sledrunner;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjLink;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocad.library.sequencelight.nucSeqEditor;
import org.clothocore.api.data.Annotation;
import org.clothocore.util.basic.ImageSource;
import org.clothocore.util.basic.ObjBasePopup;


/**
 *
 * @author J. Christopher Anderson
 */
public class hub extends JFrame {

    public hub(ObjBase obj, String titl, ObjType type) {
        super("Sledrunner viewing: " + titl);
        _obj = obj;
        setIconImage(ImageSource.getTinyLogo());
        switch(obj.getType()) {
            case NUCSEQ:
                _seq = (NucSeq) obj;
                break;
            case PART:
                Part p = (Part) obj;
                _seq = p.getSeq();
                break;
            case VECTOR:
                Vector vect = (Vector) obj;
                _seq = vect.getSeq();
                break;
            case OLIGO:
                Oligo anoligo = (Oligo) obj;
                _seq = anoligo.getSeq();
                break;
            case FEATURE:
                Feature feat = (Feature) obj;
                _seq = feat.getSeq();
                break;
            case PLASMID:
                Plasmid plas = (Plasmid) obj;
                _seq = plas.getSeq();
                break;
            default:
                return;
        }

        new ObjBasePopup(getContentPane(), obj);
        title = type.toString().toLowerCase() + ": " + titl;
        initComponents();
        if(type.equals(ObjType.PLASMID)) {
            saveButton.setVisible(false);
        }
        setVisible(true);
        putInAuthors();
    }

    private void initComponents() {
        getContentPane().setBackground(navyblue);
        jPanel1 = new javax.swing.JPanel();
        titleField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        seqPanel = new nucSeqEditor(_seq, this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(navyblue);

        titleField.setBackground(navyblue);
        titleField.setFont(new java.awt.Font("Tahoma", 0, 18));
        titleField.setForeground(new java.awt.Color(255, 255, 255));
        titleField.setText(title);
        titleField.setBorder(null);

        saveButton.setText("Save Everything");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _obj.saveDefault();
                _seq.saveDefault();
                for(Annotation n : _seq.getAnnotations()) {
                    n.saveDefault();
                }
            }
        });


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addGap(16, 16, 16))
                    .addComponent(titleField, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton))
                .addContainerGap())
        );



        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(seqPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seqPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void putInAuthors() {
        if(!Collector.isConnected()) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                _allAuthors = Collector.getAllLinksOf(ObjType.PERSON);
                final ArrayList<String> listy = new ArrayList<String>();
                for(ObjLink link: _allAuthors) {
                    listy.add(link.name);
                }
            }
        }.start();


    }
/*-----------------
     variables
 -----------------*/
    private NucSeq _seq;
    private ObjBase _obj;
    private javax.swing.JTextField titleField;
    private javax.swing.JPanel jPanel1;
    private nucSeqEditor seqPanel;
    private javax.swing.JButton saveButton;
    private ArrayList<ObjLink> _allAuthors;
    private String title = "Nucleic Acid Sequence";

    static Color navyblue = new Color(35, 48, 64);
}
