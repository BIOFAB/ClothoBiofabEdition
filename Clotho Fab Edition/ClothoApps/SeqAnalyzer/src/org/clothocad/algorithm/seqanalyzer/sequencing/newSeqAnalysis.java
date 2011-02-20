/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer.sequencing;

import java.util.ArrayList;

/**
 *
 * @author benjaminbubenheim
 */
public class newSeqAnalysis {
    public newSeqAnalysis(ArrayList<ABITrace> ABItraces, String sequ, String auth, String name, boolean d){
        traces=ABItraces;
        seq=sequ;
        author=auth;
        sName=name;
        dialog=d;
        analyze();
    }

    private int analyze(){


        return -1;
    }


    //Variables
    private ArrayList<ABITrace> traces;
    private String seq, author, sName;
    private boolean dialog;
}
