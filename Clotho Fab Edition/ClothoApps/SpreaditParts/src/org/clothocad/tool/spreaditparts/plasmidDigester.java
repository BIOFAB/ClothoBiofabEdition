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
 * plasmidDigester.java
 *
 * Created on Aug 7, 2010, 12:05:03 PM
 */

package org.clothocad.tool.spreaditparts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import org.clothocore.api.data.Plasmid;


/**
 *
 * @author J. Christopher Anderson
 */
public class plasmidDigester extends javax.swing.JFrame {

    /** Creates new form plasmidDigester */
    public plasmidDigester() {
        super("Digest Report");
        initComponents();
        reportArea.setFont(reportFont);
        setVisible(true);
    }

    public void runDigest(final digItem item) {
        String rawseq = item.plasmid.getSeq().getSeq();
        String first8 = rawseq.substring(0,8);
        String seq = rawseq + first8;

        String enz1seq = enzyToSeq.get(item.enzyme1);
        String enz2seq = enzyToSeq.get(item.enzyme2);
        if(enz1seq==null || enz2seq==null || seq==null) {
            return;
        }

        //Figure out where the cuts are
        errorMsg="";

        //Run the first enzyme
        int firstIndex = findPattern(item.enzyme1, enz1seq, seq, rawseq);
        System.out.println("plasmidDigester:  first run " + item.enzyme1 + " firstIndex is " + firstIndex);

        //Run the second enzyme
        int secondIndex = findPattern(item.enzyme2, enz2seq, seq, rawseq);
        System.out.println("plasmidDigester:  first run " + item.enzyme2 + " second Index is " + secondIndex);

        if(!errorMsg.equals("")) {
            //Put a line in the report
            final String err = errorMsg;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    reportArea.append(item.plasmid.getName());
                    reportArea.append("\t" + item.enzyme1);
                    reportArea.append("/" + item.enzyme2);
                    reportArea.append("\t" + err);
                    reportArea.append("\n" );
                }
            });
            return;
        }

        firstIndex += enzyToOffset.get(item.enzyme1);
        secondIndex += enzyToOffset.get(item.enzyme2);

        System.out.println("plasmidDigester:  with offset " + item.enzyme1 + " first Index is " + firstIndex);
        System.out.println("plasmidDigester:  with offset " + item.enzyme2 + " second Index is " + secondIndex);

        final int frag1 = Math.abs(secondIndex - firstIndex);
        System.out.println(frag1);

        final int frag2 = rawseq.length() - frag1;
        System.out.println(frag2);

        //Put a line in the report
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                reportArea.append(item.plasmid.getName());
                reportArea.append("\t" + item.enzyme1);
                reportArea.append("/" + item.enzyme2);
                reportArea.append("\t" + frag1);
                reportArea.append("+" + frag2);
                reportArea.append("\tKeep:");
                reportArea.append("\t" + frag2);
                reportArea.append("\n" );
            }
        });
    }

    private int findPattern(String enzyme1, String enzseq, String seq, String rawseq) {
        Pattern p = Pattern.compile(enzseq);
        String[] result = p.split(seq);

        int firstIndex = result[0].length();
        System.out.println("plasmidDigester:  run " + enzyme1 + " firstIndex is " + firstIndex);

        if(result.length>2) {
            errorMsg +=  enzyme1 + " has multiple cuts\t";
        }
        if(firstIndex > rawseq.length()) {
            errorMsg +=  enzyme1 + " doesn't cut\t";
        }
        return firstIndex;
    }

    public class digItem {
        public digItem(Plasmid p, String e1, String e2) {
            plasmid = p;
            enzyme1 = e1;
            enzyme2 = e2;
        }
        public Plasmid plasmid;
        public String enzyme1;
        public String enzyme2;
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        reportArea = new javax.swing.JTextArea();
        setPreferredSize(new Dimension(480,150));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jScrollPane1.setViewportView(reportArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jScrollPane1, BorderLayout.CENTER);

        pack();
    }


/**-----------------
     variables
 -----------------*/
    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea reportArea;
    // End of variables declaration
    private static Font reportFont = new java.awt.Font("Courier New", Font.PLAIN, 12);
    private static HashMap<String, String> enzyToSeq = new HashMap<String, String>();
    private static HashMap<String, Integer> enzyToOffset = new HashMap<String, Integer>();
    private String errorMsg;
    static {
        enzyToSeq.put("BamHI", "GGATCC");
        enzyToSeq.put("BglII", "AGATCT");
        enzyToSeq.put("EcoRI", "GAATTC");
        enzyToSeq.put("XhoI", "CTCGAG");
        enzyToSeq.put("AlwNI", "CAG...CTG");
        enzyToSeq.put("BglI", "GCC.....GGC");
        enzyToSeq.put("PstI", "CTGCAG");
        enzyToSeq.put("XbaI", "TCTAGA");
        enzyToSeq.put("SpeI", "ACTAGT");
        enzyToSeq.put("BsaI", "GGTCTC");

        enzyToOffset.put("BamHI", 1);
        enzyToOffset.put("BglII", 1);
        enzyToOffset.put("EcoRI", 1);
        enzyToOffset.put("XhoI", 1);
        enzyToOffset.put("AlwNI", 6);
        enzyToOffset.put("BglI", 7);
        enzyToOffset.put("PstI", 1);
        enzyToOffset.put("XbaI", 1);
        enzyToOffset.put("SpeI", 1);
        enzyToOffset.put("BsaI", 0);
    }
}
//"AlwNI", "BglI"}