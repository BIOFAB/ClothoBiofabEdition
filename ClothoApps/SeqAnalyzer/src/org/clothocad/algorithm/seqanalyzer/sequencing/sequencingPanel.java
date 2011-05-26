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

package org.clothocad.algorithm.seqanalyzer.sequencing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.BorderLayout;

/**
 *
 * @author J. Christopher Anderson
 */
public class sequencingPanel extends JPanel implements ActionListener {
private ArrayList<String> fixes;
private char[] preFixes;
//Npositions is the positions in the abi
public sequencingPanel(int[] Npositions, AlignedAbi abi, String read) {
    System.out.println("in constructor");
    setLayout(null);
    _abi = abi.getAbi();
    fixes=new ArrayList<String>();
    setPreferredSize(new Dimension(150*Npositions.length+20,150));
    setBackground(Color.gray);

    preFixes=new char[Npositions.length];
    for(int i=0; i<preFixes.length; i++){
        preFixes[i]='N';
    }

    System.out.println("entering loop");
    for(int n=0; n<Npositions.length; n++) {
        JLabel seqLabel = new JLabel();
        seqLabel.setBounds(150*n+10, 5, 110, 26);
        add(seqLabel);
        putInSequence(seqLabel, Npositions[n]);
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(150*n+10, 30, 120, 90);
        add(imageLabel);
        drawAnIcon(imageLabel, Npositions[n]);

        JLabel sugg=new JLabel(""+_abi.getSequence().charAt(Npositions[n])+"?");
        switch(sugg.getText().charAt(0)){
            case 'A':sugg.setForeground(Color.green); break;
            case 'C':sugg.setForeground(Color.blue); break;
            case 'G':sugg.setForeground(Color.black); break;
            case 'T':sugg.setForeground(Color.red); break;
        }
        sugg.setBounds(150*n+85, 10, 80, 20);
        sugg.setBackground(Color.white);
        add(sugg);

        LinkedButton a=new LinkedButton("A", n);
        a.setActionCommand("a");
        LinkedButton c=new LinkedButton("C", n);
        c.setActionCommand("c");
        LinkedButton g=new LinkedButton("G", n);
        g.setActionCommand("g");
        LinkedButton t=new LinkedButton("T", n);
        t.setActionCommand("t");
        a.addLink(c);
        a.addLink(g);
        a.addLink(t);
        c.addLink(a);
        c.addLink(g);
        c.addLink(t);
        g.addLink(a);
        g.addLink(c);
        g.addLink(t);
        t.addLink(a);
        t.addLink(c);
        t.addLink(g);

        a.setBounds(150*n+10, 120, 30, 20);
        a.addActionListener(this);
        a.setForeground(Color.green);
        add(a);
        c.setBounds(150*n+40, 120, 30, 20);
        c.addActionListener(this);
        c.setForeground(Color.blue);
        add(c);
        g.setBounds(150*n+70, 120, 30, 20);
        g.addActionListener(this);
        g.setForeground(Color.black);
        add(g);
        t.setBounds(150*n+100, 120, 30, 20);
        t.addActionListener(this);
        t.setForeground(Color.red);
        add(t);

    }
}

    public void drawAnIcon(JLabel alabel, int n) {
        BufferedImage traceImage = _abi.getImage(90,1,n);
        ImageIcon traceIcon = new ImageIcon(traceImage);
        alabel.setIcon(traceIcon);
    }

    public void putInSequence(JLabel alabel, int n) {
        String text = _abi.getSequence().substring(Math.max(n-3, 0), Math.min(n+3, _abi.getSequence().length()-1));
        alabel.setText(text);
    }

    public void actionPerformed(ActionEvent e){
        String base="";
        if(e.getActionCommand().equals("a")){
            base="A";
        }
        if(e.getActionCommand().equals("c")){
            base="C";
        }
        if(e.getActionCommand().equals("g")){
            base="G";
        }
        if(e.getActionCommand().equals("t")){
            base="T";
        }
        //fixes.add(base);
        preFixes[((LinkedButton)e.getSource()).getIndex()]=base.charAt(0);
        System.out.println(fixes);
        ((LinkedButton)e.getSource()).setEnabled(false);
        ArrayList<LinkedButton> links=((LinkedButton)e.getSource()).getLinks();
        while(!links.isEmpty()){
            links.remove(0).setEnabled(false);
        }
    }

    public ArrayList<String> getFixes(){
        for(int i=0; i<preFixes.length; i++){
            fixes.add(""+preFixes[i]);
        }
        return fixes;
    }

    
    /* SETTERS
     * */

    /* PUTTERS
     * */

    /* GETTERS
     * */

/*-----------------
     variables
 -----------------*/
    protected ABITrace _abi;
    protected ArrayList<ABITrace> _abis;
}
