/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocad.algorithm.stringappend;

import org.clothocore.api.actor.Actor;
import org.clothocore.api.plugin.ClothoAlgorithm;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class StringAppendAlgorithmFactory implements ClothoAlgorithm {

    @Override
    public Actor createAlgorithm() {
        return new StringAppendAlgorithm();
    }

    @Override
    public void init() {
    }
}
