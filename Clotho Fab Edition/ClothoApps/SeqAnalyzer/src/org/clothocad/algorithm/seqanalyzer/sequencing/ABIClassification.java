/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer.sequencing;

import java.awt.Color;
import java.util.Hashtable;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
//import sequencing.ABITrace;
//import sequencing.seqAnalysis;
/**
 *
 * @author benjaminbubenheim
 */
public class ABIClassification extends JPanel{
    ArrayList<ABITrace> abis;
    int score;
    public ABIClassification(int s){
        setBackground(Color.gray);
        score=s;
        abis=new ArrayList<ABITrace>();
        Hashtable hash=new Hashtable(200);
        File xls = new File("//Users//benjaminbubenheim//Downloads//sequencingmatching.csv");
        try{
        BufferedReader in=new BufferedReader(new FileReader(xls));
        String str="";
        while((str=in.readLine())!=null){
            //System.out.println(str);
            String temp1=str.substring(0, str.indexOf(","));
            String temp2=str.substring(str.indexOf(",")+1);
            hash.put(temp1, temp2);
        }
        }
        catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        File folder = new File("//Users//benjaminbubenheim//Downloads//sequencing//");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&listOfFiles[i].getName().contains(".ab1")) {
                System.out.println("File " + listOfFiles[i].getName());
                try{
                    abis.add(new ABITrace(listOfFiles[i]));
                }
                catch(IOException e){
                    System.out.println("OOPS");
                }
            } 
        }
        try{
            abis.add(new ABITrace(listOfFiles[0]));
        }
        catch(IOException e){
            System.out.println("OOPS");
        }
        JLabel seqLabel = new JLabel(""+score);
        seqLabel.setBounds(10, 5, 110, 26);
        add(seqLabel);
        for(int i=0; i<abis.size(); i++) {
            for(int j=0; j<abis.get(i).getQualities().length; j++){
                if(abis.get(i).getQualities()[j]==score){
                    System.out.println("j is "+j);
                    JLabel imageLabel = new JLabel();
                    imageLabel.setBounds(150*i+10, 30, 120, 90);
                    add(imageLabel);
                    drawAnIcon(imageLabel, new int[]{i, j});
                }
            }
        }
    }
public void drawAnIcon(JLabel alabel, int[] n) {
        BufferedImage traceImage = abis.get(n[0]).getImage(90,1,Math.max(0, n[1]));
        ImageIcon traceIcon = new ImageIcon(traceImage);
        alabel.setIcon(traceIcon);
    }
public static void main(String[] args){
    ABIClassification a=new ABIClassification(12);
    JScrollPane scrollPane = new JScrollPane(a);
    scrollPane.setPreferredSize(new Dimension(350, 170));
    JOptionPane.showMessageDialog(null, scrollPane, ""+a.score, JOptionPane.INFORMATION_MESSAGE);
    /*
    ABIClassification b=new ABIClassification(18);
    ABIClassification c=new ABIClassification(17);
    ABIClassification d=new ABIClassification(16);
    ABIClassification e=new ABIClassification(15);
    ABIClassification f=new ABIClassification(14);
    ABIClassification g=new ABIClassification(13);
    ABIClassification h=new ABIClassification(12);
    ABIClassification i=new ABIClassification(11);
    ABIClassification j=new ABIClassification(10);
    ABIClassification k=new ABIClassification(9);
    ABIClassification l=new ABIClassification(8);
    ABIClassification m=new ABIClassification(7);
    ABIClassification n=new ABIClassification(6);
    ABIClassification o=new ABIClassification(5);
    ABIClassification p=new ABIClassification(4);
    ABIClassification q=new ABIClassification(3);
    ABIClassification r=new ABIClassification(2);
    ABIClassification s=new ABIClassification(1);
    ABIClassification t=new ABIClassification(0);
    */
}
}