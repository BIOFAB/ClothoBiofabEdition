/*
 * 
Copyright (c) 2010 The Regents of the University of California.
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

package sequencing;

import jaligner.Alignment;
import jaligner.Sequence;
import jaligner.SmithWatermanGotoh;
import jaligner.matrix.MatrixLoader;
import java.util.ArrayList;
import javax.swing.JPanel;
import sequencing.TraceExtract.AlignedABI;
import sequencing.TraceExtract.ResultType;

/**
 *
 * @author jcanderson_Home
 */
public class Analyzer {

    public Analyzer(ArrayList<ABITrace> abis, String sequ) {
        //Initiate data
        String oldsequ = sequ.toUpperCase();
        sequ = oldsequ;
        traces= new ArrayList<TraceExtract>();
        contigs = new ArrayList<TraceExtract>();
        _abis = abis;
        for(ABITrace abi : abis) {
            TraceExtract te = new TraceExtract(abi);
            traces.add(te);
            contigs.add(te);
        }
        int[] partQualities = new int[sequ.length()];
        short[] partScores = new short[sequ.length()];
        finalTarget = new TraceExtract(sequ, partQualities, partScores);
       }

    public ResultType go() {
        //Do the analysis:
            //Take all the traces and let them assemble without bias of the target sequence
            assemblyContig();
            //Remove all the contigs that don't align to the target sequence and reorient the good ones
            pruneOutDuds();
            //Call each base as correct, wrong, or ambiguous (data stored in the baseScores array as 1, 0, or -1
            alignToTarget();
            //Realign all the abis
            for(AlignedABI abi: finalTarget.getABIList()) {
                abi.realign(finalTarget.getSeq());
            }
        return finalTarget.getResult();
    }

    public void launchReport() {
            //Instantiate the gui on the final target
            new InteractiveReport(finalTarget, "Chris Anderson", "Best sample ever", finalTarget.getResult().toString()).showReport();

    }

    //returns the JPanel instead of displaying it immediately, which is done in launchReport()
    public JPanel returnReport() {
        return new InteractiveReport(finalTarget, "Chris Anderson", "Best sample ever", finalTarget.getResult().toString()).getPanel();

    }

    /**
     * The contigs going into this are already confirmed to be good matches
     * to the target and have been oriented in the right direction.  This
     * transfers the best quality score to the quality array.
     */
    private void alignToTarget() {
        for(TraceExtract atrace : contigs) {
            Alignment Falign = swAlign(atrace.getSeq(), finalTarget.getSeq());

            //Modify the target sequence and qualities to include any gaps
            char[] seqy = Falign.getSequence2();
            for(int i=0; i<seqy.length; i++) {
                if(seqy[i]!='-') {
                    continue;
                }
                finalTarget.addGap(Falign.getStart2()+i, 10);
                atrace.noteDeletion(Falign.getStart1() + i);
            }

            //Modify the target sequence and qualities to include any deletions
            seqy = Falign.getSequence1();
            for(int i=0; i<seqy.length; i++) {
                if(seqy[i]!='-') {
                    continue;
                }
                finalTarget.noteDeletion(Falign.getStart2() + i);
                atrace.addGap(Falign.getStart1() + i, 10);
            }

            //With all the gaps fixed, merge them


            Falign = swAlign(atrace.getSeq(), finalTarget.getSeq());
            int start = Falign.getStart1();
            int end = Falign.getLength() + start;
            int partStart = Falign.getStart2();
            int partOffset = start - partStart;

            TraceExtract result = TraceExtract.merge(atrace, finalTarget, start, partStart, true);
            finalTarget = result;

//            System.out.println("\n\nSequence: ");
//            System.out.println(result.getSeq());
//            int[] quals = result.getQualities();
//
//            System.out.println("\n\nQualities: ");
//            for(int i=0; i < quals.length; i++) {
//                System.out.print(quals[i]);
//            }
            System.out.println("\n\nBaseCalls: ");
            short[] calls = result.getBaseScores();
            for(int i=0; i < calls.length; i++) {
                System.out.print(calls[i]);
            }
            System.out.println("\n\nI have x many abis: " + result.getABIList().size());

        }

//        System.out.println("finalTarget seq:  " + finalTarget.getSeq());
    }

    /**
     * Takes all the qualified Strings and gets them to assemble into one contig (if possible)
     * @param traces
     */
    private void assemblyContig() {
        //Create a second copy of the array to avoid concurrency errors
        ArrayList<TraceExtract> backupArray = new ArrayList<TraceExtract>();
        for(TraceExtract str : contigs) {
            backupArray.add(str);
        }

        //Iterate through all the contigs and merge them into one contig (if possible)
        while(alignAndMerge(contigs.get(0), TraceExtract.revcomp(contigs.get(0))));

    }

    /**
     * Aligns a TraceExtract contig to the target sequence
     * @param atrace
     */
    private void pruneOutDuds() {
        //Copy the ArrayList
        ArrayList<TraceExtract> listy = new ArrayList<TraceExtract>();
        
        for(TraceExtract atrace : contigs) {
            TraceExtract rctrace = TraceExtract.revcomp(atrace);
            Alignment Falign = swAlign(atrace.getSeq(), finalTarget.getSeq());
            Alignment Ralign = swAlign(rctrace.getSeq(), finalTarget.getSeq());

            float fscore = Falign.getScoreWithNoTerminalGaps();
            float rscore = Ralign.getScoreWithNoTerminalGaps();
            
            if(Math.max(fscore, rscore) > 200) {
                if(fscore>rscore) {
                    listy.add(atrace);
                } else {
                    listy.add(rctrace);
                }
            }
        }
        contigs = listy;
    }

    /**
     * Align two TraceExtracts and merge them into a contig.  This is called iteratively
     * to force all the abis to assemble into contigs (ideally 1, but will make more if there
     * isn't homology between them).
     * @param first
     * @param firstrc
     * @return
     */
    private boolean alignAndMerge(TraceExtract first, TraceExtract firstrc) {
            if(contigs.size()<2) {
                return false;
            }

            boolean bestRC=false;  //if the best Match for or rev
            int bestMatch=0;   //Index in contigs of best match
            float bestScore=0; //Score of the best match
            Alignment bestAlign = null;  //The alignment for the best match

            //For each string after the first see which is the best
            for(int j=1; j<contigs.size(); j++) {
                String second = contigs.get(j).getSeq();
                Alignment Falign = swAlign(first.getSeq(), second);
                Alignment Ralign = swAlign(firstrc.getSeq(), second);
                Alignment betterAlign = Falign;
                boolean isRC = false;

                //If the alignment is better as rev comp, set that parameter
                float fscore = Falign.getScoreWithNoTerminalGaps();
                float rscore = Ralign.getScoreWithNoTerminalGaps();
                if(fscore<rscore){
                    isRC=true;
                    betterAlign = Ralign;
                }

                float myscore = Math.max(fscore, rscore);

//                System.out.println(isRC + "  " + fscore + "  " +rscore + "  " +myscore);

                //If this is the best alignment of the first datapoint to the others in the array, set that
                if(myscore>bestScore) {
                    bestScore = myscore;
                    bestRC = isRC;
                    bestMatch = j;
                    bestAlign = betterAlign;

                }
            }

            if(bestScore<200) {
                return false;
            }

            //Based on best choice, reorient first seq to be in right order
            TraceExtract reorient = first;
            if(bestRC) {
                reorient = firstrc;
            }

            //Do the merge, remove the two that were aligned and put in the new merge
            TraceExtract newone = TraceExtract.merge(reorient, contigs.get(bestMatch), bestAlign.getStart1(), bestAlign.getStart2(), false);
//            System.out.println(newone.getSeq());
            contigs.remove(bestMatch);
            contigs.remove(0);
            contigs.add(newone);
            return true;
    }

    public static Alignment swAlign(String first, String test){
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



    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    public static final double MIN_QUALITY=6;
    public static final int MIN_AVERAGE_QUALITY=25;

    private ArrayList<TraceExtract> traces;
    private ArrayList<TraceExtract> contigs;
    private ArrayList<ABITrace> _abis;

    private TraceExtract finalTarget;
    private Result _result;

    public static enum Result {
        PERFECT,
        PERFECT_PARTIAL,
        MUTATIONS
    }
}
