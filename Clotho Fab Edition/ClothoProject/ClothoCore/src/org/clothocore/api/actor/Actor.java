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
ENHANCEMENTS, OR MODIFICATIONS.
 */
package org.clothocore.api.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public abstract class Actor {

    public Actor() {
        _inputs = new ArrayList<InputPort>();
        _outputs = new ArrayList<OutputPort>();
    }

    public List<InputPort> getInputs() {
        return _inputs;
    }

    public List<OutputPort> getOutputs() {
        return _outputs;
    }

    public InputPort getInputPort( String id ) {
        return Actor.getInput( id );
    }

    public OutputPort getOutputPort( String id ) {
        return Actor.getOutput( id );
    }

    /**
     * Returns whether this algorithm is ready to run. By default,
     * this method just checks each InputPort in _inputs, and returns
     * false if any of them don't have data, and true otherwise. Users should
     * override this method if they need to do special checks to determine
     * if the algorithm is ready to run.
     * @return
     */
    public boolean isReady() {
        for(InputPort inp : _inputs) {
            if(!inp.hasData()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Each user-generated algorithm should override this method to contain
     * all the logic that should be executed for this algorithm to run.
     * @return
     */
    public abstract RunStatus run();

    /**
     * Returns whether this algorithm is finished running. By default, returns
     * whether all of the OutputPorts have data in them. 
     * @return
     */
    public boolean isFinished() {
        for(OutputPort out : _outputs) {
            if(!out.hasData()) {
                return false;
            }
        }
        return false;
    }

    public boolean registerInput( InputPort inp ) {
        if ( _allInputs.containsKey( inp.getID() ) ) {
            return false;
        }
        _inputs.add( inp );
        _allInputs.put( inp.getID(), inp );
        return true;
    }

    public boolean registerOutput( OutputPort out ) {
        if ( _allOutputs.containsKey( out.getID() ) ) {
            return false;
        }
        _outputs.add( out );
        _allOutputs.put( out.getID(), out );
        return true;
    }

    public static InputPort getInput( String id ) {
        return _allInputs.get( id );
    }

    public static OutputPort getOutput( String id ) {
        return _allOutputs.get( id );
    }
    protected List<InputPort> _inputs;
    protected List<OutputPort> _outputs;
    protected static Map<String, InputPort> _allInputs;
    protected static Map<String, OutputPort> _allOutputs;

    static {
        _allInputs = new HashMap<String, InputPort>();
        _allOutputs = new HashMap<String, OutputPort>();
    }

    public abstract String getName();
}
