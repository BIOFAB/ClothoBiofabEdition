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
import java.util.ArrayList;

/**
 *
 * @author jcanderson_Home
 */
public class TraceExtract {

    public TraceExtract(ABITrace abi) {
        _trace = abi;
        int[] quals = abi.getQualities();
        StringBuffer ret= new StringBuffer();
        for(int i=0; i<quals.length; i++){
            if(quals[i]<Analyzer.MIN_QUALITY){
                ret.append("N");
            }
            else{
                ret.append(abi.getSequence().charAt(i));
            }
        }
        _qualities = new ArrayList<Integer>();
        _scores = new ArrayList<Short>();
        for(int i=0; i<quals.length; i++) {
            _qualities.add(quals[i]);
            _scores.add((short) -1);
        }
        _seq = ret.toString();
        _abiList.add(new AlignedABI(abi));
        mergeStart = 0;
        mergeEnd = _seq.length();
    }

    public ABITrace getTrace() {
        return _trace;
    }

    public TraceExtract(String qseq, int[] qualities, short[] scores) {
        _qualities = new ArrayList<Integer>();
        _scores = new ArrayList<Short>();
        for(int i=0; i<qualities.length; i++) {
            _qualities.add(qualities[i]);
            if(scores==null) {
                _scores.add((short) -1);
            } else {
                _scores.add(scores[i]);
            }
        }
        _seq = qseq;
        mergeStart = 0;
        mergeEnd = _seq.length();
    }

    /**
     * Takes in two TraceExtract and the positions of their aligned regions and from this calculates
     * a new TraceExtract that has the best newscores for the two
     * @param firstContig
     * @param secondTarget
     * @param firstIndex
     * @param secondIndex
     * @param length
     * @return
     */
    public static TraceExtract merge(TraceExtract firstContig, TraceExtract secondTarget, int firstIndex, int secondIndex, boolean isfinal) {
//        System.out.println("***********starting merge " + firstContig._abiList.size() + "  " + secondTarget._abiList.size() );

        if(firstIndex < secondIndex) {
            TraceExtract temp = firstContig;
            firstContig = secondTarget;
            secondTarget = temp;
            int tempi = firstIndex;
            firstIndex = secondIndex;
            secondIndex = tempi;
        }

        int[] firstquals = firstContig.getQualities();
        int[] secondquals = secondTarget.getQualities();
        short[] firstscores = firstContig.getBaseScores();
        short[] secondscores = secondTarget.getBaseScores();

        int secondoffset = firstIndex - secondIndex;

        int totalLength = Math.max((secondTarget.getSeq().length() + secondoffset), firstContig._seq.length());
//        System.out.println("totallength is " + totalLength + " from " + secondTarget.getSeq().length() + " offset " +secondoffset);
        int[] newqual = new int[totalLength];
        short[] newscores = new short[totalLength];
        StringBuffer newseq = new StringBuffer();

        //Scan through the sequences and put in the best base at each position
        for(int i=0; i<totalLength; i++) {
            char firstval = 'x';
            char secondval = 'x';
            int firstqual = -1;
            int secondqual= -1;
            short firstscore = -1;
            short secondscore = -1;

            if(firstContig._seq.length()> i) {
                firstval = firstContig._seq.charAt( i);
                firstqual = firstquals[ i];
                firstscore = firstscores[i];
            }

            if(secondTarget._seq.length()>i - secondoffset && i >= secondoffset) {
                secondval = secondTarget._seq.charAt(i - secondoffset);
                secondqual = secondquals[i - secondoffset];
                secondscore = secondscores[i - secondoffset];
            }

            char chosenval = firstval;
            int chosenqual = firstqual;
            if(firstqual<secondqual) {
                chosenval = secondval;
                chosenqual = secondqual;
            }

            //Put in the character to build the sequence
            if(isfinal) {
                newseq.append(firstval);
            } else {
                newseq.append(chosenval);
            }
            newqual[i] = chosenqual;

            //The rest of this method only pertains to the final contig-to-target merge
            if(!isfinal) {
                continue;
            }

//            System.out.print(i+ " {}{}{}{}Merging scores: firstval:" + firstval + "  firstqual: "+firstqual + "  secondval:" + secondval  + " firstscore:" + firstscore + "  ");

            //If a gap was put in, see if it's good quality, score and continue
            if(firstscore==2) {
//                System.out.print("2");
                if(firstqual>Analyzer.MIN_AVERAGE_QUALITY) {
                    newscores[i] = 2;
                } else {
                    newscores[i] = -1;
                }
//                System.out.println("  newscores:" +newscores[i]);
                continue;
            } else if(firstscore==3) {
//                System.out.print("3");
                if(firstqual>Analyzer.MIN_AVERAGE_QUALITY) {
                    newscores[i] = 3;
                } else {
                    newscores[i] = -1;
                }
//                System.out.println("  newscores:" +newscores[i]);
                continue;
            }

            //If it's not gapped/deletion, see if it's in a range that was aligned
            if(secondval=='x') {
//                System.out.print("4");
                newscores[i] = 4;
                continue;
            } else if(firstval=='x') {
//                System.out.print("-1");
                newscores[i] = -1;
                continue;
            }

            //If got this far, the position is unscored but data is good quality, so score it
            if(firstval=='N') {
//                System.out.print("-1");
                newscores[i] = -1;
            } else if(firstval == secondval) {
                if(firstqual < Analyzer.MIN_QUALITY) {
                    newscores[i] = -1;
                } else {
                    newscores[i] = 1;
                }
            } else {
                if(firstqual < Analyzer.MIN_AVERAGE_QUALITY) {
                    newscores[i] = -1;
                } else {
                    newscores[i] = 0;
                }
            }

//            System.out.println("  newscores:" +newscores[i]);
                
        }

        TraceExtract out = new TraceExtract(newseq.toString(), newqual, newscores);

        for(AlignedABI abi : firstContig._abiList) {
            out._abiList.add(abi);
        }
        for(AlignedABI abi : secondTarget._abiList) {
            out._abiList.add(abi);
        }

        out.mergeStart = firstIndex - secondIndex;
        out.mergeEnd = out.mergeStart + secondTarget.getSeq().length();

//        System.out.println("***********ending merge " + out._abiList.size()  );
        return out;
    }


    public static TraceExtract revcomp(TraceExtract first) {
//        System.out.println("############starting revcomp " + first._abiList.size()  );
        int[] oldquals = first.getQualities();
        short[] oldscores = first.getBaseScores();
        String newseq = first.revcomp();
        int[] newqual = new int[oldquals.length];
        short[] newscores = new short[oldquals.length];
        int counter=0;
        for(int i=oldquals.length-1; i>=0; i--) {
            newqual[counter] = oldquals[i];
            newscores[counter] = oldscores[i];
            counter++;
        }

        TraceExtract out = new TraceExtract(newseq, newqual, newscores);
        for(AlignedABI abi : first.getABIList()) {
            out._abiList.add(abi);
        }

//        System.out.println("############ending revcomp " + out._abiList.size()  );
        return out;
    }

    public String revcomp(){
        String in = _seq;
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

    public int[] getQualities() {
        int[] out = new int[_qualities.size()];
        for(int i=0; i<_qualities.size(); i++) {
            out[i] = _qualities.get(i);
        }
        return out;
    }

    public short[] getBaseScores() {
        short[] out = new short[_scores.size()];
        for(int i=0; i<_scores.size(); i++) {
            out[i] = _scores.get(i);
        }
        return out;
    }

    public ArrayList<AlignedABI> getABIList() {
        return _abiList;
    }

    /**
     * Create a gap in the sequence, qualities, and base newscores
     * after a specified position in the array
     * @param position
     */
    public void addGap(int position, int quality) {
        _qualities.add(position, quality);
        _scores.add( position, (short) 2);
        String newseq = _seq.substring(0,position);
        newseq += "-";
        newseq += _seq.substring(position);
        _seq = newseq;
    }

    public void noteDeletion(int position) {
        _scores.remove(position);
        _scores.add(position, (short) 3);
    }

    public String getSeq() {
        return _seq;
    }

    public int getMergeStart() {
        return mergeStart;
    }

    public int getMergeEnd() {
        return mergeEnd;
    }

    public ResultType getResult() {
        boolean ambiguousfound = false;
        boolean mutationfound = false;

        for(int i=mergeStart; i< mergeEnd; i++) {
            int current = _scores.get(i);
            switch(current) {
                case -1:
                    ambiguousfound = true;
                    break;
                case 0:
                    mutationfound = true;
                    break;
                case 1:
                    continue;
                case 2:
                    return ResultType.INSERT_DELETE;
                case 3:
                    return ResultType.INSERT_DELETE;
            }
        }
        if(mutationfound) {
            return ResultType.POINT_MUTATIONS;
        }
        if(ambiguousfound) {
            return ResultType.PERFECT_PARTIAL;
        }

        return ResultType.PERFECT;
    }

    public class AlignedABI {
        String name;
        TraceExtract trace;
        int startOnTarget;
        int endOnTarget;
        boolean isForward;

        private AlignedABI(ABITrace abi) {
            trace = TraceExtract.this;
            name = abi.getFileName();
            startOnTarget = 0;
            endOnTarget = _qualities.size();
            isForward = true;
//            System.out.println("&&%^^&%$&*& Created new Aligned abi from .abi " + startOnTarget + " " + endOnTarget);
        }

        public void realign(String target) {
            TraceExtract tracerc = TraceExtract.revcomp(trace);
//            System.out.println("@##$%$^#$% target: " +target);
//            System.out.println("@##$%$^#$% trace: " +trace.getSeq());
//            System.out.println("@##$%$^#$% tracerc: " +tracerc.getSeq());

            Alignment Falign = Analyzer.swAlign(trace.getSeq(), target);
            Alignment Ralign = Analyzer.swAlign(tracerc.getSeq(), target);

            float fscore = Falign.getScoreWithNoTerminalGaps();
            float rscore = Ralign.getScoreWithNoTerminalGaps();
            boolean isrc;

            //If it's reverse complemented
            if(fscore<rscore) {
                trace = tracerc;
                isForward = false;
                startOnTarget = Ralign.getStart2()-Ralign.getStart1();
                endOnTarget = Ralign.getStart2()+trace.getSeq().length()-Ralign.getStart1();
            //If it's forward oriented
            } else {
                isForward = true;
                startOnTarget = Falign.getStart2()-Falign.getStart1();
                endOnTarget = Falign.getStart2()+trace.getSeq().length()-Falign.getStart1();
            }

        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    private ArrayList<Integer> _qualities;
    private ArrayList<Short> _scores; //unknown = -1, bad = 0, good = 1, gap = 2, del = 3, outside target = 4
    private String _seq;
    private ArrayList<AlignedABI> _abiList = new ArrayList<AlignedABI>();
    private int mergeStart;
    private int mergeEnd;
    private ABITrace _trace;

    public enum ResultType {
        PERFECT,
        PERFECT_PARTIAL,
        INSERT_DELETE,
        POINT_MUTATIONS
    }
}
