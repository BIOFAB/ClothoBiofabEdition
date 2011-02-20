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

/*
 * factoidEditor.java
 *
 * Created on Aug 23, 2010, 5:52:25 PM
 */

package org.clothocad.tool.grapevine;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.clothocore.api.data.Factoid;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.Person;
import org.clothocore.api.data.Reference;
import org.clothocore.api.dnd.ObjBaseObserver;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.dnd.RefreshEvent.Condition;
import org.clothocore.util.basic.ObjBasePopup;
import org.clothocad.wikieditorpanel.WikiEditorPanel;
import org.clothocore.util.basic.ImageSource;

/**
 *
 * @author J. Christopher Anderson
 */
public class factoidEditor extends javax.swing.JFrame {

    /** Creates new form factoidEditor */
    public factoidEditor(Factoid fact) {
        super("Factoid Editor");
        setIconImage(ImageSource.getTinyLogo());
        _fact = fact;
        initComponents();
        obo = new ObjBaseObserver() {
            @Override
            public void update(ObjBase obj, RefreshEvent evt) {
                Condition cond = evt.getCondition();
                switch(cond) {
                    case AUTHOR_CHANGED:
                        authorField.dataUpdated();
                        break;
                    case DELETE_SUCCESSFUL:
                        dispose();
                        break;
                    case NAME_CHANGED:
                        titleField.dataUpdated();
                        break;
                    case REFERENCE_CHANGED:
                        pmidAbstractArea.dataUpdated();
                        pmidTitleArea.dataUpdated();
                        break;
                    case UPDATE_ALL:
                        authorField.dataUpdated();
                        titleField.dataUpdated();
                        pmidAbstractArea.dataUpdated();
                        pmidTitleArea.dataUpdated();
                        break;
                }
            }
        };
        _fact.isRepresentedBy(obo, getContentPane());
        new ObjBasePopup(getContentPane(), _fact);
        setSize(600,600);
        setVisible(true);
    }

    private void initComponents() {

        wikiPanel = new WikiEditorPanel(this, _fact.getWikiText());
        titleLabel = new javax.swing.JLabel();
        titleLabel.setForeground(Color.lightGray);
        authorLabel = new javax.swing.JLabel();
        authorLabel.setForeground(Color.lightGray);
        reflabel = new javax.swing.JLabel();
        reflabel.setForeground(Color.lightGray);
        authorexlabel = new javax.swing.JLabel();
        authorexlabel.setForeground(Color.lightGray);

        jScrollPane1 = new javax.swing.JScrollPane();
        pmidAbstractArea = new blueTextArea(10) {
            @Override
            public void dataUpdated() {
                Reference ref = _fact.getReference();
                if(ref!=null) {
                    this.setText(ref.getAbstract());
                }
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        pmidTitleArea = new blueTextArea(15) {
            @Override
            public void dataUpdated() {
                Reference ref = _fact.getReference();
                if(ref!=null) {
                    this.setText(ref.getTitle());
                }
            }
        };

        getContentPane().setBackground(navyblue);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        refField = new blueTextField(12) {
            @Override
            public void gainFocus() {
            }
            @Override
            public void loseFocus() {
                if(oldValue.equals(this.getText())) {
                    return;
                }
                oldValue = this.getText();
                _fact.changeReference(oldValue);
            }
            @Override
            public void dataUpdated() {
                if(_fact.getReference()!=null) {
                    this.setText(_fact.getReference().getID());
                }
            }
        };
        authorField = new blueTextField(12) {
            @Override
            public void gainFocus() {
            }
            @Override
            public void loseFocus() {
                if(oldValue.equals(this.getText())) {
                    return;
                }
                oldValue = this.getText();
                Person aperson = Person.retrieveByName(oldValue.trim());
                _fact.changeAuthor(aperson);
            }
            @Override
            public void dataUpdated() {
                this.setText(_fact.getAuthor().getName());
            }
        };
        titleField = new blueTextField(16) {
            @Override
            public void gainFocus() {
            }
            @Override
            public void loseFocus() {
                if(oldValue.equals(this.getText())) {
                    return;
                }
                oldValue = this.getText();
                _fact.changeName(oldValue);
            }
            @Override
            public void dataUpdated() {
                this.setText(_fact.getName());
            }
        };

        titleLabel.setText("Factoid title:");
        authorLabel.setText("Author:");
        reflabel.setText("Reference Link");
        authorexlabel.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        authorexlabel.setText("(Ex: \"PMID:1347277\" )");

        pmidAbstractArea.setColumns(20);
        pmidAbstractArea.setRows(5);
        jScrollPane1.setViewportView(pmidAbstractArea);
        pmidTitleArea.setColumns(20);
        pmidTitleArea.setRows(2);
        jScrollPane2.setViewportView(pmidTitleArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(wikiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(titleField, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(titleLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reflabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(authorexlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(refField, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(authorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(authorField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(authorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorLabel)
                    .addComponent(reflabel)
                    .addComponent(refField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorexlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(wikiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }




    private abstract class blueTextArea extends javax.swing.JTextArea {
        //VARIABLES:
        int _size;
        String oldValue="";

        public blueTextArea(int size) {
            _size = size;
            this.setBackground(navyblue);
            this.setFont(new java.awt.Font("Arial", 0, _size));
            this.setForeground(Color.WHITE);
            this.setBorder(null);
            this.setLineWrap(true);
            this.setWrapStyleWord(true);
            this.setEditable(false);
            dataUpdated();
        }
        public abstract void dataUpdated();
    }
/**-----------------
     variables
 -----------------*/
    private blueTextField authorField;
    private blueTextField titleField;
    private blueTextField refField;

    private blueTextArea pmidAbstractArea;
    private blueTextArea pmidTitleArea;

    private javax.swing.JLabel authorLabel;
    private javax.swing.JLabel authorexlabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;

    private javax.swing.JLabel reflabel;

    private javax.swing.JLabel titleLabel;
    private WikiEditorPanel wikiPanel;
    private Factoid _fact;
    static Color navyblue = new Color(35, 48, 64);
    private static final Border blackline = BorderFactory.createLineBorder(Color.black);

    private ObjBaseObserver obo;
}
