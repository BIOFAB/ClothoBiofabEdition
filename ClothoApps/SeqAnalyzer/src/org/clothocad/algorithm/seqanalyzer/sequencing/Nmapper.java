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
public class Nmapper {
    private AlignedAbi abi;
    private ArrayList<Integer> NsInSeq, NsInAbi, correctedNs;
    public Nmapper(AlignedAbi a, ArrayList<Integer> n){
        abi=a;
        NsInSeq=n;
        NsInAbi=mapToAbi();
        correctedNs=fix();
    }
    public AlignedAbi getAbi(){
        return abi;
    }
    public ArrayList<Integer> getNsInSeq(){
        return NsInSeq;
    }
    public ArrayList<Integer> getNsInAbi(){
        return NsInAbi;
    }
    public ArrayList<Integer> mapToAbi(){
        ArrayList<Integer> ret=new ArrayList<Integer>();
        for(int i=0; i<NsInSeq.size(); i++){
            ret.add(new Integer(NsInSeq.get(i)+(abi.getStartInTrace()-abi.getStartInSeq())));
        }
        return ret;
    }
    public ArrayList<Integer> getCorrectedNs(){
        return correctedNs;
    }
    public ArrayList<Integer> fix(){
        ArrayList<Integer> c=new ArrayList<Integer>();
        if(abi.isRevComp()){
            for(int i=0; i<NsInAbi.size(); i++){
                System.out.println("it was "+NsInAbi.get(i));
                c.add(0, Math.abs((NsInAbi.get(i))-(abi.getAbi().getSequenceLength()-1)));
                System.out.println("now its "+c.get(0));
            }
        return c;
        }
        else{
            return NsInAbi;
        }
    }
    public int getQuality(int ind){
        int ret=0;
        ret=abi.getAbi().getQualities()[correctedNs.get(ind)];
        return ret;
    }
    public void remove(int i){
        NsInSeq.remove(i);
        NsInAbi.remove(i);
        correctedNs.remove(i);
    }
    public String toString(){

        return""+NsInAbi;
    }
    public static void main(String[] args){
        ArrayList<Integer> c=new ArrayList<Integer>();
        ArrayList<Integer> cs=new ArrayList<Integer>();
        cs.add(0);
        cs.add(2);
        cs.add(4);
        cs.add(9);
            for(int i=0; i<cs.size(); i++){
                c.add(Math.abs(cs.get(i)-9));
            }

            System.out.println(c);

    }
    
}
