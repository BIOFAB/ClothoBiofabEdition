/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer.sequencing;

/**
 *
 * @author benjaminbubenheim
 */
public class AlignedAbi {
    private ABITrace abi;
    private boolean revComp;
    private int startInSeq, startInTrace, originalIndex;
    public AlignedAbi(ABITrace a, int sT, int sS){
        abi=a;
        startInSeq=sS;
        startInTrace=sT;
        if(startInTrace<0){
            startInTrace*=-1;
            revComp=true;
        }
        else{
            revComp=false;
        }
    }
    public void setOriginalIndex(int i){
        originalIndex=i;
    }
    public ABITrace getAbi(){
        return abi;
    }
    public boolean isRevComp(){
        return revComp;
    }
    public int getStartInSeq(){
        return startInSeq;
    }
    public int getStartInTrace(){
        return startInTrace;
    }
    public int getOriginalIndex(){
        return originalIndex;
    }
    public String toString(){
        return ""+originalIndex+", "+startInTrace+", "+startInSeq+", "+revComp;
    }
}
