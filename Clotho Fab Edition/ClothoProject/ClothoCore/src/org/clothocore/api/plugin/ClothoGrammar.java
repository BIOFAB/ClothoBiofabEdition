/*
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
package org.clothocore.api.plugin;

import org.clothocore.api.data.Part;

/**
 *
 * @author J. Christopher Anderson
 * @author Douglas Densmore
 */
public interface ClothoGrammar extends ClothoPlugin {


    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /**Determine whether the grammar has defined a token as one of its lexemes
     *
     * @param token
     * @return
     */
    public boolean isValidToken( String token );

    /**
     * Looks up a human-readable description for the token and returns it as a String
     * @param token
     * @return
     */
    public String getDescriptionOf( String token );

    /**
     * Given a list of tokens corresponding to a part composition, determine whether
     * it is a grammatically-complete "sentence" or gene-like entity.
     * @param tokens
     * @return
     */
    public boolean isCompleteExpression( String[] tokens );

    /**
     * Returns all the tokens understood by this grammar.
     *
     * @return
     */
    String[] getTokens();


//THIS ONE WON'T MAKES SENSE FOR MANY INTERMEDIATES:  ONLY PUT TOKENS ON BASIC PARTS
//String getProductToken(ArrayList<part> parts);

    /**
     * Returns the list of tokens that are equivalent to the primitive token.  So, for
     * http://bioinformatics.oxfordjournals.org/cgi/content/full/23/20/2760/T2 that would be
     *
     * A call of:
     *      getDecompositionOf("M");
     *
     * Gives:
     *
     *      [M, M]
     *      [N, N]
     *      [C, E]
     *      [K, T, K]
     *
     * @param atoken
     * @return a 2D array of valid decompositions
     */
    public String[][] getDecompositionOf( String token );

    /**Tries to automatically detect the right token for the grammar plugin for this part,
     * presumably by looking through its features, then sets the token fields
     *
     * @param apart
     */
    public void autoDetectToken( Part apart );

    public enum values {

        SENTENCE, INVALID
    };
}
