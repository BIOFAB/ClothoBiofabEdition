/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer.sequencing;

import jaligner.Alignment;
import jaligner.Sequence;
import jaligner.SmithWatermanGotoh;
import jaligner.matrix.MatrixLoader;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;



/**
 *
 * @author bbubenheim
 */
public class seqAnalysis {
    //for test
    public seqAnalysis(ArrayList<ABITrace> ABItraces, String sequ, String author, String sName, boolean dialog){
        traces=ABItraces;
        partSeq=sequ;

        System.out.println("\n\n\n\n***********  Initiating Analysis ************");
        System.out.println("** Target is " + partSeq.substring(0,Math.min(20, partSeq.length())) + "...");
        System.out.println("** I have " + traces.size() + " ab1 files to compare");
        System.out.println("** Author is " + author);
        System.out.println("** Template name is " + sName);
        System.out.println("** Throw report is " + dialog);
        System.out.println("**********************************************\n\n\n\n");

        if(traces.isEmpty()) {
            return;
        }
        analyze(dialog, author, sName);
    }
    

    /**
     * Algorithm for comparing an ABI file to a theoretical sequence, returns false unless it is perfect, throws
     * dialogs in the process.
     *
     * @param traces a list of abi files converted to parsers
     * @param dropSample the sample being compared, null if directly handing it a sequence
     * @param dropSeq the sequence being compared, null if getting it from a sample
     * @param throwDialog if it should throw dialogs of results or not
     */
    private boolean analyze(boolean throwDialog, String author, String sName) {
        qualities=new int[traces.size()][];   //holds qualities taken from the abi parser
        qStrings=new ArrayList<String>(); //holds the "qualified" sequences, after being screened for quality
        findStrings=new ArrayList<String>();
        traceMap=new ArrayList<ArrayList<Integer>>();
        baseToAbi=new ArrayList<ArrayList<Integer>>();
        traceNames=new ArrayList<String>();
        alignedAbis=new ArrayList<AlignedAbi>();

        //for all of the (assumed to be unordered and possibly reverse complemented) traces, extract quality for each call
        for(int i=0; i<traces.size(); i++){
            traceNames.add(traces.get(i).getFileName());
            qualities[i]=traces.get(i).getQualities();
            qStrings.add(qualifySeq(traces.get(i), qualities[i]));
            findStrings.add(qualifySeq(traces.get(i), qualities[i]));
            System.out.println("qString added: "+qStrings.get(i));
        }
        
        //take the list of qualified strings, which is assumed to be unordered, and patch it together
        comp=mergeSeq(qStrings, partSeq);

        //fixing revcomps for report generation
        for(int i=0; i<traceMap.size(); i++){
            System.out.println("index is... "+traceMap.get(i).get(1).intValue());
            if(traceMap.get(i).get(1).intValue()<0){
                System.out.println("BOO");
                findStrings.set(traceMap.get(i).get(0).intValue(), revcomp(findStrings.get(traceMap.get(i).get(0).intValue())));
                //traceMap.get(i).set(1, new Integer(traceMap.get(i).get(1).intValue()*-1));
            }
        }

        System.out.println("comp is: ");
        System.out.println(comp);
        System.out.println("target is: ");
        System.out.println(partSeq);
        
        
        String read=comp;
        
        //not perfect, do some diagnostics and cleanup
        //see where the part sequence matches the read, comp will not need to be reverse complemented
        //as mergeSeq adds the first read in the correct direction
        align=swAlign(comp, partSeq);

        int readIndex=align.getStart1();
        int partIndex=align.getStart2();
        System.out.println("readIndex= "+readIndex);
        System.out.println("partIndex= "+partIndex);
        System.out.println("traceMap: "+traceMap);
        for(int i=0; i<traceMap.size(); i++){
            //traceMap.get(i).set(2, new Integer(traceMap.get(i).get(2)-readIndex));
            alignedAbis.add(new AlignedAbi(traces.get(traceMap.get(i).get(0)), traceMap.get(i).get(1).intValue(), traceMap.get(i).get(2).intValue()));
            alignedAbis.get(i).setOriginalIndex(traceMap.get(i).get(0));
        }
        System.out.println(alignedAbis);
        String alignedRead=String.valueOf(align.getSequence1());
        String alignedSeq=String.valueOf(align.getSequence2());

        System.out.println("aligned comp:");
        System.out.println(alignedRead);
        System.out.println("aligned seq:");
        System.out.println(alignedSeq);

        //fill baseToAbi
        for(int i=0; i<alignedRead.length(); i++){
            ArrayList<Integer> temp=new ArrayList<Integer>();
            for(int j=0; j<traceMap.size(); j++){
                if(i>=traceMap.get(j).get(2).intValue() && i<traces.get(traceMap.get(j).get(0)).getSequenceLength()+traceMap.get(j).get(2).intValue()){
                    temp.add(new Integer(j));
                }
            }
            baseToAbi.add(temp);
        }
        
        //check for gaps (shouldn't happen unless nonsense)
        if(alignedRead.contains("-") || alignedSeq.contains("-")){
            System.out.println("gaps???");
            //gaphandler()
        }
        
            //check for and record confident mutations and Ns
            //boolean mutation=false;
           //ArrayList<Integer> muts=new ArrayList<Integer>();

            int a=0;
            ArrayList<Nmapper> Ns=new ArrayList<Nmapper>();
            if(alignedAbis.size()==1){
                ArrayList<Integer> N=new ArrayList<Integer>();
                for(int i=0; i<alignedRead.length(); i++){
                    if(alignedRead.charAt(i)=='N'){
                        N.add(new Integer(i+partIndex));
                    }
                }
                Nmapper temp=new Nmapper(alignedAbis.get(0), N);
                System.out.println("1new ambiguitites at "+temp);
                Ns.add(temp);
            }
            else{
                ArrayList<Integer> N=new ArrayList<Integer>();
                for(int i=0; i<alignedRead.length(); i++){
                    if(a<(alignedAbis.size()-1)){
                        if(i+partIndex>alignedAbis.get(a+1).getStartInSeq()){
                            Nmapper temp=new Nmapper(alignedAbis.get(a), N);
                            Ns.add(temp);
                            System.out.println("2new ambiguitites at "+temp);
                            N=new ArrayList<Integer>();
                            a++;
                        }
                    }
                    if(alignedRead.charAt(i)=='N'){
                        N.add(new Integer(i+partIndex));
                    }
                }
                Nmapper temp=new Nmapper(alignedAbis.get(a), N);
                Ns.add(temp);
                System.out.println("3new ambiguitites at "+temp);
            }
            for(int i=0; i<Ns.size(); i++){
                System.out.println(Ns.get(i));
                ArrayList<Integer> toKeep=new ArrayList<Integer>();
                for(int j=0; j<Ns.get(i).getCorrectedNs().size(); j++){
                    System.out.println(""+j+", "+Ns.get(i).getQuality(j));
                    if(Ns.get(i).getQuality(j)>=MIN_POST_QUALITY){
                        char[] temp=alignedRead.toCharArray();
                        int nIndex=Ns.get(i).getNsInSeq().get(j)-partIndex;
                        System.out.println("removing ns");
                        //System.out.println(alignedRead);
                        //System.out.println(partSeq);
                        System.out.println(""+nIndex);
                        System.out.println(""+temp[nIndex]);
                        temp[nIndex]=Ns.get(i).getAbi().getAbi().getSequence().charAt(Ns.get(i).getNsInAbi().get(j));//alignedSeq.charAt(Ns.get(i).getNsInSeq().get(j));
                        System.out.println(""+temp[nIndex]);
                        alignedRead=String.valueOf(temp);
                    }
                    else{
                        toKeep.add(new Integer(Ns.get(i).getNsInSeq().get(j)));
                    }
                }
                Nmapper t=new Nmapper(Ns.get(i).getAbi(), toKeep);
                Ns.set(i, t);
                System.out.println(Ns);
            }
            System.out.println(alignedRead);
            //imperfect full read
            if(alignedRead.length()==partSeq.length()){
                alignedRead=ambiguous(Ns, alignedRead);
                   
            }
            //Case partial
            else{
                if(Ns.isEmpty()){
                //perfect partial
                System.out.println("read is a perfect partial");
                }
                else{
                    //ambiguous partial
                    System.out.println("read is an ambiguous partial");
                    alignedRead= ambiguous(Ns, alignedRead);
                }
            }
        
        //find mutations
        ArrayList<Integer> mutations=new ArrayList<Integer>();
        
        for(int i=0; i<alignedRead.length(); i++){
            if(alignedRead.charAt(i)!=alignedSeq.charAt(i) && alignedRead.charAt(i)!='N' && alignedSeq.charAt(i)!='N'){
                mutations.add(new Integer(i));
            }
        }
        a=0;
        ArrayList<Nmapper> MsLeft=new ArrayList<Nmapper>();
        if(alignedAbis.size()==1){
            ArrayList<Integer> Ni=new ArrayList<Integer>();
            for(int i=0; i<alignedRead.length(); i++){
                if(alignedRead.charAt(i)!=alignedSeq.charAt(i) && alignedRead.charAt(i)!='N' && alignedSeq.charAt(i)!='N'){
                    Ni.add(new Integer(i+partIndex));
                }
            }
            Nmapper temp=new Nmapper(alignedAbis.get(0), Ni);
            System.out.println("1new mutations at "+temp);
            MsLeft.add(temp);
        }
        else{
            ArrayList<Integer> Ni=new ArrayList<Integer>();
            for(int i=0; i<alignedRead.length(); i++){
                if(a<(alignedAbis.size()-1)){
                    if(i+partIndex>alignedAbis.get(a+1).getStartInSeq()){
                        Nmapper temp=new Nmapper(alignedAbis.get(a), Ni);
                        MsLeft.add(temp);
                        System.out.println("2new mutations at "+temp);
                        Ni=new ArrayList<Integer>();
                        a++;
                    }
                }
                if(alignedRead.charAt(i)!=alignedSeq.charAt(i) && alignedRead.charAt(i)!='N' && alignedSeq.charAt(i)!='N'){
                    Ni.add(new Integer(i+partIndex));
                    System.out.println(""+i);
                }
            }
            Nmapper temp=new Nmapper(alignedAbis.get(a), Ni);
            System.out.println("3new mutations at "+temp);
            MsLeft.add(temp);
        }

        boolean mutation=false, N=false, fullRead=false;
        if(!mutations.isEmpty()){
            mutation=true;
        }
        if(alignedRead.contains("N")){
            N=true;
        }
        if(alignedRead.length()==partSeq.length()){
            fullRead=true;
        }
        seqResult="";
        if(mutation){
            seqResult+="Mutated ";
        }
        if(N){
            seqResult+="Ambiguous ";
        }
        if(fullRead && !N && !mutation){
            seqResult+="Perfect Full Read";
        }
        else if(fullRead){
            seqResult+="Full Read";
        }
        else if(!N && !mutation){
            seqResult+="Perfect Partial Read";
        }
        else{
            seqResult+="Partial Read";
        }

        a=0;
        ArrayList<Nmapper> NsLeft=new ArrayList<Nmapper>();
        if(alignedAbis.size()==1){
            ArrayList<Integer> Ni=new ArrayList<Integer>();
            for(int i=0; i<alignedRead.length(); i++){
                if(alignedRead.charAt(i)=='N'){
                    Ni.add(new Integer(i+partIndex));
                }
            }
            Nmapper temp=new Nmapper(alignedAbis.get(0), Ni);
            System.out.println("4new ambiguitites at "+temp);
            NsLeft.add(temp);
        }
        else{
            ArrayList<Integer> Ni=new ArrayList<Integer>();
            for(int i=0; i<alignedRead.length(); i++){
                if(a<(alignedAbis.size()-1)){
                    if(i+partIndex>alignedAbis.get(a+1).getStartInSeq()){
                        Nmapper temp=new Nmapper(alignedAbis.get(a), Ni);
                        NsLeft.add(temp);
                        System.out.println("5new ambiguitites at "+temp);
                        Ni=new ArrayList<Integer>();
                        a++;
                    }
                }
                if(alignedRead.charAt(i)=='N'){
                    Ni.add(new Integer(i+partIndex));
                }
            }
            Nmapper temp=new Nmapper(alignedAbis.get(a), Ni);
            System.out.println("6new ambiguitites at "+temp);
            NsLeft.add(temp);
        }
    
        System.out.println(MsLeft);
        //generate report
        
        //sr=new seqReport(splits, weird, findStrings, traceNames, traceMap, seqResult, partSeq, new ArrayList<ABITrace>());
        sr=new seqReport(partSeq, alignedRead, seqResult, author, sName, MsLeft, NsLeft, traces, findStrings, traceMap, alignedAbis, baseToAbi, new ArrayList<ABITrace>(), throwDialog);
        return false;
        
    }

    private ArrayList<String> showPartialWithErrorsDialog(int[] Npositions, AlignedAbi abi, String read) {
        // create and configure a text area - fill it with exception text.
        final sequencingPanel apanel = new sequencingPanel(Npositions, abi, read);

        // stuff it in a scrollpane with a controlled size.
        JScrollPane scrollPane = new JScrollPane(apanel);
        scrollPane.setPreferredSize(new Dimension(350, 170));

        // pass the scrollpane to the joptionpane.
        JOptionPane.showMessageDialog(null, scrollPane, "There are possible bad calls", JOptionPane.INFORMATION_MESSAGE);
        boolean done=false;
        return apanel.getFixes();
    }
private String ambiguous(ArrayList<Nmapper> nm, String read){
    System.out.println("in ambiguous()");
    ArrayList<String> fixes=new ArrayList<String>();
    for(int n=0; n<nm.size(); n++){
        if(nm.get(n).getNsInSeq().isEmpty()){
            continue;
        }
        ArrayList<Integer> ns=nm.get(n).getCorrectedNs();
        System.out.println("ns is "+ns);
        int[] nPos=new int[ns.size()];
        for(int i=0; i<ns.size(); i++){
            nPos[i]=ns.get(i);//getAbiPos(ns.get(i).intValue(), read);
        }
        for(int i=0; i<nPos.length; i++){
                System.out.println(""+nPos[i]);
        }
        ArrayList<String> temp=showPartialWithErrorsDialog(nPos, nm.get(n).getAbi(), read);
        for(int i=0; i<temp.size(); i++){
            fixes.add(temp.get(i));
        }
        System.out.println(fixes);
        for(int i=0; i<read.length(); i++){
            if(!fixes.isEmpty()){
            if(read.charAt(i)=='N'){
                read=read.substring(0, i)+fixes.remove(0)+read.substring(i+1);
            }
            }
        }   
    }
    
    return read;
}

private int[] getAbiPos(int pos, String read){
    int[] ret=new int[3];
    ret[0]=pos;
    ret[1]=baseToAbi.get(pos).get(0);
    ret[2]=traceMap.get(ret[1]).get(2)+pos;
    return ret;
    
}

private ArrayList<String> split(ArrayList<String> fill, String seq){
    String temp=seq;
    ArrayList<String> weird=new ArrayList<String>();
    if(traces.size()==1){
        fill.add(temp);
        return weird;
    }
    else if(!seq.contains("NN")){
        fill.add(temp);
        return weird;
    }
    else{
        for(int i=0; i<traces.size(); i++){
            if(i==0){
                fill.add(temp.substring(0, temp.indexOf("NN")));
                temp=temp.substring(temp.indexOf("NN"));
            }
            else if(i==traces.size()-1){
                fill.add(temp.substring(temp.lastIndexOf("NN")+2));
                temp=temp.substring(0, temp.lastIndexOf("NN")+2);
                weird.add(temp);
            }
            else{
                fill.add(temp.substring(temp.lastIndexOf("NN", seq.length()/(2*(traces.size()-i)))+2, temp.indexOf("NN", seq.length()/(2*(traces.size()-i)))));
                weird.add(temp.substring(0, temp.lastIndexOf("NN", seq.length()/(2*(traces.size()-i)))+2));
                temp=temp.substring(temp.lastIndexOf("NN", seq.length()/(2*(traces.size()-i))));
            }
        }
    }
    return weird;
}
/*
 * reconstructs the sequence, but adds "N" for each call that is less than the determined quality cutoff
 */
private String qualifySeq(ABITrace abi, int[] q){
    String ret="";
    for(int i=0; i<q.length; i++){
        if(q[i]<MIN_QUALITY){
            ret+="N";
        }
        else{
            ret+=""+abi.getSequence().charAt(i);
        }
    }
    return ret;
}


private String revcomp(String in){
    String ret="";
    for(int i=in.length()-1; i>=0; i--){
        char nuc=in.charAt(i);
        switch(nuc){
            case 'A': ret+='T'; break;
            case 'G': ret+='C'; break;
            case 'C': ret+='G'; break;
            case 'T': ret+='A'; break;
            case 'N': ret+='N'; break;
        }
    }
    return ret;
}

private String mergeSeq(ArrayList<String> list, String actual){
    String start="";
    int[] startsTrace=new int[list.size()];
    int[] startsSeq=new int[list.size()];
    int startCount=0;
    int lSize=list.size();
    int[] order=new int[list.size()];
    int orderCount=0;
    double startPer=0.0;
    int startIndex=Integer.MAX_VALUE;
    int startReadIndex=0;
    int readPos=0;
    int firstString=0;
    boolean firstRev=false;
    //find first abi
    for(int i=0; i<list.size(); i++){
        boolean revtemp=false;
        Alignment[] temp=new Alignment[2];
        temp[0]=swAlign(list.get(i), actual.substring(0, actual.length()/list.size()));
        temp[1]=swAlign(revcomp(list.get(i)), actual.substring(0, actual.length()/list.size()));
        System.out.println("mergeSeq() score: "+temp[0].getScore());
        System.out.println("mergeSeq() revscore: "+temp[1].getScore());
        System.out.println("mergeSeq() nogaps score: "+temp[0].getScoreWithNoTerminalGaps());
        System.out.println("mergeSeq() rev nogaps score: "+temp[1].getScoreWithNoTerminalGaps());
        if(temp[0].getScoreWithNoTerminalGaps()<temp[1].getScoreWithNoTerminalGaps()){
            revtemp=true;
        }
        if(revtemp==false){
            if(temp[0].getStart2()<startIndex){
                startIndex=temp[0].getStart2();
                startReadIndex=temp[0].getStart1();
                firstString=i;
            }
        }
        else{
            if(temp[1].getStart2()<startIndex){
                startIndex=temp[1].getStart2();
                startReadIndex=-temp[1].getStart1();
                firstString=i*-1;
                if(firstString==0){
                    firstRev=true;
                }
            }
        }
    }
    if(firstString<0 || firstRev){
        System.out.println("reverse");
        start+=revcomp(list.get(firstString*-1));
    }
    else{
        start+=list.get(firstString);
    }
    int startShift=startReadIndex-startIndex;
    System.out.println("startShift is "+startShift);
    //startsTrace[startCount]=startIndex;
    //startsSeq[startCount]=0;
    System.out.println("first string aligns to "+startIndex+" in the seq");
    System.out.println("first string aligns to "+startReadIndex+" in the read");
    startsTrace[startCount]=startReadIndex;
    startsSeq[startCount]=startIndex;
    startCount++;
    order[orderCount]=firstString;
    orderCount++;
    list.remove(firstString);
    System.out.println("mergeSeq(): first abi is: "+firstString);
    System.out.println("mergeSeq(): start is now: "+start);
    while(!list.isEmpty()){
        double max=0.0;
        int listIndex=0, readIndex=0, next=0, listIndexUS=0, readIndexUS=0;
        //attempt to align each element left in the list, pick the one that aligns the best
        for(int i=0; i<list.size(); i++){
            Alignment result=swAlign(start, list.get(i));
            Alignment rev=swAlign(start, revcomp(list.get(i)));
            System.out.println("mergeSeq(): result seq1");
            System.out.println(String.valueOf(result.getSequence1()));
            System.out.println("mergeSeq(): result seq2");
            System.out.println(String.valueOf(result.getSequence2()));
            System.out.println("mergeSeq(): rev seq1");
            System.out.println(String.valueOf(rev.getSequence1()));
            System.out.println("mergeSeq(): rev seq2");
            System.out.println(String.valueOf(rev.getSequence2()));
            double tempPer=result.getScoreWithNoTerminalGaps();
            double revPer=rev.getScoreWithNoTerminalGaps();
            System.out.println("mergeSeq(): tempPer= "+tempPer);
            System.out.println("mergeSeq(): revPer= "+revPer);
            double correct=tempPer;
            if(revPer>tempPer){
                correct=revPer;
            }
            if(correct>max){
                max=correct;
                next=i;
                int revGap1=0, revGap2=0, resGap1=0, resGap2=0;
                for(int g=0; g<result.getSequence1().length; g++){
                    if(result.getSequence1()[g]=='-'){
                        resGap1++;
                    }
                    if(result.getSequence2()[g]=='-'){
                        resGap2++;
                    }
                }
                for(int g=0; g<rev.getSequence1().length; g++){
                    if(rev.getSequence1()[g]=='-'){
                        revGap1++;
                    }
                    if(rev.getSequence2()[g]=='-'){
                        revGap2++;
                    }
                }
                if(correct==tempPer){
                    int shift1=0, shift2=0;
                    if(resGap1>resGap2){
                        shift2=resGap1-resGap2;
                    }
                    if(resGap1<resGap2){
                        shift1=resGap2-resGap1;
                    }
                    if(String.valueOf(result.getSequence1()).contains("-") || String.valueOf(result.getSequence2()).contains("-")){
                        readIndex=result.getStart1()+Math.max(String.valueOf(result.getSequence1()).lastIndexOf("-"), String.valueOf(result.getSequence2()).lastIndexOf("-"))+shift1;
                        listIndex=result.getStart2()+Math.max(String.valueOf(result.getSequence1()).lastIndexOf("-"), String.valueOf(result.getSequence2()).lastIndexOf("-"))+shift2;
                    }
                    else{
                        readIndex=result.getStart1();
                        listIndex=result.getStart2();
                    }
                    readIndexUS=result.getStart1();
                    listIndexUS=result.getStart2();
                }
                else{
                    int shift1=0, shift2=0;
                    if(resGap1>resGap2){
                        shift2=resGap1-resGap2;
                    }
                    if(resGap1<resGap2){
                        shift1=resGap2-resGap1;
                    }
                    if(String.valueOf(rev.getSequence1()).contains("-") || String.valueOf(rev.getSequence2()).contains("-")){
                        readIndex=rev.getStart1()+Math.max(String.valueOf(rev.getSequence1()).lastIndexOf("-"), String.valueOf(rev.getSequence2()).lastIndexOf("-")+shift1);
                        listIndex=-(rev.getStart2()+Math.max(String.valueOf(rev.getSequence1()).lastIndexOf("-"), String.valueOf(rev.getSequence2()).lastIndexOf("-"))+shift2);
                    }
                    else{
                        readIndex=rev.getStart1();
                        listIndex=-rev.getStart2();
                    }
                    readIndexUS=rev.getStart1();
                    listIndexUS=-rev.getStart2();
                }
            }
            System.out.println("mergeSeq(): shifted start");
            System.out.println(start.substring(readIndex));
            System.out.println("mergeSeq(): shifted next");
            //System.out.println(revcomp(list.get(i)).substring(-listIndex));
        }
        //fix up start and append the chosen string
        System.out.println(""+readIndex+" vs "+readIndexUS);
        System.out.println(""+listIndex+" vs "+listIndexUS);
        startsTrace[startCount]=listIndexUS;
        startsSeq[startCount]=readIndexUS-startShift;
        startCount++;
        if(listIndex<0){
            listIndex*=-1;
            list.set(next, revcomp(list.get(next)));
        }
        System.out.println("mergeSeq(): shifted start");
        System.out.println(start.substring(readIndex));
        System.out.println("mergeSeq(): shifted next");
        System.out.println(list.get(next).substring(listIndex));
        String startTemp=start;
        for(int i=readIndex; i<start.length(); i++){
            System.out.println(""+i+", "+start.length()+", "+(listIndex+(i-readIndex))+", "+list.get(next).length());
            //System.out.println(""+start.charAt(i)+"-"+list.get(next).charAt(listIndex+(i-readIndex)));
            if((listIndex+(i-readIndex))>=list.get(next).length()){
                continue;
            }
            else if(start.charAt(i)=='N'){
                System.out.println("found an N: "+i);
                startTemp=startTemp.substring(0, i)+list.get(next).charAt(listIndex+(i-readIndex));
                if(i!=start.length()-1){
                    startTemp+=start.substring(i+1);
                }
            }
            else if((start.charAt(i)!=list.get(next).charAt(listIndex+(i-readIndex))) && (list.get(next).charAt(listIndex+(i-readIndex))!='N')){
                System.out.println("found a discrepancy: "+i);
                char comparison=partSeq.charAt(i-startShift);//'a';
                System.out.println(start.charAt(i)+", "+list.get(next).charAt(listIndex+(i-readIndex))+", "+comparison+", "+(i-startShift));
                //System.out.println(start.substring(i));
                //System.out.println(list.get(next).substring(listIndex+(i-readIndex)));
                //System.out.println(partSeq.substring(i-startShift));
                startTemp=startTemp.substring(0, i)+list.get(next).charAt(listIndex+(i-readIndex));
                if(i!=start.length()-1){
                    startTemp+=start.substring(i+1);
                }
            }
            
        }
        System.out.println(start);
        System.out.println(startTemp);
        start=startTemp;
        start+=list.get(next).substring(listIndex+(start.length()-readIndex));
        order[orderCount]=next;
        orderCount++;
        list.remove(next);
        
    }
    //System.out.println("order is "+order[0]+", "+order[1]);
    ArrayList<Integer> map=new ArrayList<Integer>();
    for(int i=0; i<traces.size(); i++){
        map.add(new Integer(i));
    }
    int[] newOrder=new int[order.length];
    System.out.println("new order:");
    for(int i=0; i<order.length; i++){
        newOrder[i]=(map.remove(order[i])).intValue();
        System.out.print(""+newOrder[i]);
    }
    System.out.println();
    for(int i=0; i<traces.size(); i++){
        ArrayList<Integer> temp=new ArrayList<Integer>();
        temp.add(new Integer(newOrder[i]));
        temp.add(new Integer(startsTrace[i]));
        temp.add(new Integer(startsSeq[i]));
        System.out.println("added "+temp);
        traceMap.add(temp);
        System.out.println("traceMap "+traceMap);
    }
    return start;
}

private Alignment swAlign(String first, String test){
    Sequence s1=new Sequence(first);
    Sequence s2=new Sequence(test);
    try{
    Alignment alignment = SmithWatermanGotoh.align(s1, s2, MatrixLoader.load("IDENTITY_1"), 100f, 50f);
    //System.out.println ( alignment.getSummary() );
    //System.out.println ( new Pair().format(alignment) );
    alignment.getScoreWithNoTerminalGaps();
    return alignment;
    } catch (Exception e) {
        	System.out.println("Failed running example: " + e.getMessage());
        }
    return null;

}

public String getResult(){
    return seqResult;
}

    private void file(String datafile, String filename)  {
        try {
            Writer output = null;
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file));
            output.write(datafile);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(seqAnalysis.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("*****IOException in file writer");
        }
    }
//VARIABLES
private static final double MIN_QUALITY=20;
private static final double MIN_POST_QUALITY=10;
private ArrayList<ABITrace> traces;
private String partSeq;
private int[][] qualities;
private ArrayList<String> qStrings, findStrings;
private String comp;
private Alignment align;
private ArrayList<ArrayList<Integer>> traceMap;
private ArrayList<ArrayList<Integer>> baseToAbi;
private ArrayList<String> traceNames;
private seqReport sr;
private String seqResult;
private ArrayList<AlignedAbi> alignedAbis;
}
