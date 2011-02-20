/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer;

import org.clothocore.api.actor.Actor;
import org.clothocore.api.plugin.ClothoAlgorithm;

/**
 *
 * @author benjaminbubenheim
 */
public class SequenceChecker implements ClothoAlgorithm {

    @Override
    public Actor createAlgorithm() {
        return new SequenceCheckerActor();
    }

    @Override
    public void init() {
        
    }

}
