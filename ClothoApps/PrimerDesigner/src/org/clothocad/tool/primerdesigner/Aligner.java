/*
 * 
 * 
 */
package org.clothocad.tool.primerdesigner;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.bio.alignment.NeedlemanWunsch;
import org.biojava.bio.alignment.SequenceAlignment;
import org.biojava.bio.alignment.SubstitutionMatrix;
import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.FiniteAlphabet;
import org.biojava.bio.symbol.Alignment;
import org.biojava.bio.symbol.IllegalAlphabetException;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojavax.bio.seq.RichSequence;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.symbol.SymbolList;
import org.biojava.bio.seq.StrandedFeature;
import org.biojavax.SimpleNamespace;
import org.biojavax.bio.seq.SimpleRichSequence;

public class Aligner {
    /**
     * Aligns two DNA sequences using Needleman Wunsch Algorithm
     * @param fwdSequence
     * @param revSequence
     * @return
     */
    public static String align(String fwdSequence, String revSequence) {
        SymbolList revSeq3to5Prime = null;
        String annealingString = null;
        SimpleRichSequence fwdSeq;
        SimpleRichSequence revSeq;

        try {
            revSeq = new SimpleRichSequence(new SimpleNamespace("org"), "REVERSE", "REVERSE", 1, DNATools.createDNA(revSequence), 1.0);
            fwdSeq = new SimpleRichSequence(new SimpleNamespace("org"), "FORWARD", "FORWARD", 1, DNATools.createDNA(fwdSequence), 1.0);
            revSeq3to5Prime = DNATools.complement(DNATools.flip(revSeq, StrandedFeature.NEGATIVE));

        } catch (IllegalSymbolException ex) {
            return (ex.getMessage());
        } catch (IllegalAlphabetException ex) {
            return (ex.getMessage());
        }
        if (fwdSeq != null && revSeq != null) {
            try {
                FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("DNA");
                SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet, "#\n# Lowest score = -4, Highest score = 5\n#\n    A   T   G   C\nA  -4  4  -4  -4\nT   4  -4  -4  -4\nG  -4  -4  -4   5\nC  -4  -4   5  -4", "AnnealingMatrix");
                NeedlemanWunsch seqAlignment = new NeedlemanWunsch(
                        (short) 3, // match
                        (short) 3, // replace
                        (short) 2, // insert
                        (short) 2, // delete
                        (short) 1, // gapExtend
                        matrix // SubstitutionMatrix
                        );
//                SequenceAlignment alignment = new NeedlemanWunsch(
//                        (short) 3,      // match
//                        (short) 3,      // replace
//                        (short) 2,      // insert
//                        (short) 2,      // delete
//                        (short) 1,      // gapExtend
//                        matrix  // SubstitutionMatrix
//                );
                seqAlignment.pairwiseAlignment(fwdSeq, revSeq3to5Prime);
                Alignment alignment = seqAlignment.getAlignment(fwdSeq, revSeq3to5Prime);
                annealingString = generateAlignmentString(alignment);
                return annealingString;
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Invalid parameters were submitted.";


        }
    }

    /**
     * Formats the result text of an Alignment object
     * @param alignment
     * @return
     */
    private static String generateAlignmentString(Alignment alignment) {
        String annealingString = null;
        try {
            SymbolList forwardSymbolList = alignment.symbolListForLabel(alignment.getLabels().get(0));
            SymbolList reverseSymbolList = alignment.symbolListForLabel(alignment.getLabels().get(1));
            String matchCharString = "";
            //Will contain all of the | characters
            if (forwardSymbolList.length() == reverseSymbolList.length()) {
                for (int n = 1; n
                        <= forwardSymbolList.length(); n++) {
                    if (!forwardSymbolList.symbolAt(n).getName().equals("gap") && !reverseSymbolList.symbolAt(n).getName().equals("gap") && !forwardSymbolList.symbolAt(n).getName().equals("[]") && !reverseSymbolList.symbolAt(n).getName().equals("[]")) {
                        //Checks to make sure that there is not a gap symbol at point of comparison
                        if (forwardSymbolList.symbolAt(n).equals(DNATools.complement(reverseSymbolList.symbolAt(n)))) {
                            matchCharString = matchCharString + "|";
                            //If there is a match, insert a | character
                        } else {
                            matchCharString = matchCharString + " ";
                            //Adds a blank space if there is no match
                        }
                    } else {
                        matchCharString = matchCharString + " ";
                        //Adds a blank space if there is no match
                    }
                }
                annealingString = alignment.symbolListForLabel(alignment.getLabels().get(0)).seqString() + "\n" + matchCharString + "\n" + alignment.symbolListForLabel(alignment.getLabels().get(1)).seqString();
            } else {
                return "the SymbolLists generated by the alignment have unequal lengths.  Severe error in alignment is likely.";
            }
        } catch (Exception ex) {
            Logger.getLogger(Aligner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annealingString;

    }
}
