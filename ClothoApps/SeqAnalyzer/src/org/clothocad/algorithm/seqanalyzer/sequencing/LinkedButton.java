/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer.sequencing;

import javax.swing.JButton;
import java.util.ArrayList;
/**
 *
 * @author benjaminbubenheim
 */
public class LinkedButton extends JButton {
    private ArrayList<LinkedButton> links;
    private int index;
    public LinkedButton(String name, int ind){
        super(name);
        index=ind;
        links=new ArrayList<LinkedButton>();
    }
    public void addLink(LinkedButton l){
        links.add(l);
    }
    public ArrayList<LinkedButton> getLinks(){
        return links;
    }
    public int getIndex(){
        return index;
    }
}
