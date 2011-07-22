/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moo;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author Henry
 */
public class Main {

    public static void split(String s) {
//        String[] tokens = s.split("[\\s]+");
        String[] tokens = s.split("[\\s[\\p{Punct}]]+");
        System.out.println(s);
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);//first is name, second is start offset, last is end offset
        }
        System.out.println("");
    }

    public static void split2(String s) {
//        String[] tokens = s.split("[\\s]+");
        String[] tokens = s.split("[\\p{Punct}]+");
        System.out.println(s);
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
        System.out.println("");
    }

    public static void detectLocation(String s) {
        if (s.substring(s.length() - 1).matches("\\d")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void ask() {
        int n = javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Import features from genbank file?", "Sequence View Tool Message", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, null, null);
        System.out.println(n);
    }

    public static void window() {
        JFrame windowFrame = new JFrame();
        windowFrame.setLayout(new BorderLayout());
        JComboBox cb = new JComboBox();
        cb.addItem("one");
        cb.addItem("two");
        cb.addItem("three");
        cb.setSelectedIndex(-1);
        windowFrame.add(cb, BorderLayout.NORTH);
        JButton yesButton = new JButton("yes");
        JButton noButton = new JButton("no");
        windowFrame.add(yesButton, BorderLayout.WEST);
        windowFrame.add(noButton, BorderLayout.EAST);
        windowFrame.pack();
        windowFrame.setVisible(true);

    }

    public static void main(String[] args) {
//        JFileChooser chooser = new JFileChooser();
//        chooser.showOpenDialog(null);
//        chooser.getSelectedFile();
//        try {
//            java.io.BufferedReader inputFile = new java.io.BufferedReader(new java.io.FileReader(chooser.getSelectedFile()));
//            String line = inputFile.readLine();
//            while (line != null) {
//                String[] tokens = line.split("\\t");
//                System.out.print("Name: " + tokens[0]);
//                System.out.println(" Sequence: " + tokens[1]);
//                line = inputFile.readLine();
//            }
//
//        } catch (java.io.IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

//TestFrame tf = new TestFrame();
//tf.setVisible(true);
NoCopyPasteFrame nocp = new NoCopyPasteFrame();
nocp.doit();



    }
}
