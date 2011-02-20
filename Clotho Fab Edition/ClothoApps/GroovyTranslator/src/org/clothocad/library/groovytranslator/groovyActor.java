/*
 Copyright (c) 2009 The Regents of the University of California.
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

package org.clothocad.library.groovytranslator;

import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.wrapper.AlgorithmWrapper;

/**
 *
 * @author J. Christopher Anderson
 */
public class groovyActor {


    public static InputPort getInput( String id ) {

        Actor actor;
        AlgorithmWrapper ca;
        ca = (AlgorithmWrapper) Collator.getPluginByUUID("org.clothocad.algorithm.stringappend");
        ca.getDescription();
        actor = ca.getAlgorithm().createAlgorithm();

        return Actor.getInput( id );
    }

    public static OutputPort getOutput( String id ) {
        return Actor.getOutput( id );
    }

/*-----------------
     variables
 -----------------*/
}
