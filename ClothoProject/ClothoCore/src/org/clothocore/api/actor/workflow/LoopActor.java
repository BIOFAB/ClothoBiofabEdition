/*
 *  Copyright (c) 2009 The Regents of the University of California.
 *  All rights reserved.
 *  Permission is hereby granted, without written agreement and without
 *  license or royalty fees, to use, copy, modify, and distribute this
 *  software and its documentation for any purpose, provided that the above
 *  copyright notice and the following two paragraphs appear in all copies
 *  of this software.
 * 
 *  IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 *  FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 *  ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 *  THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 * 
 *  THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 *  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 *  PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 *  CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 *  ENHANCEMENTS, OR MODIFICATIONS.
 */
package org.clothocore.api.actor.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.ListToken;
import org.clothocore.api.actor.data.Token;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.ListInputPort;
import org.clothocore.api.actor.io.ListOutputPort;
import org.clothocore.api.actor.io.OutputPort;
import org.clothocore.api.actor.RunStatus;
import org.jgrapht.alg.CycleDetector;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class LoopActor extends CompositeActor {

    public LoopActor() {
        _lastInputs = new HashMap<ListInputPort, Token>();
        _multToSingleInputs = new HashMap<ListInputPort, InputPort>();
        _singleToMultInputs = new HashMap<InputPort, ListInputPort>();
        _multToSingleOutputs = new HashMap<ListOutputPort, OutputPort>();
        _singleToMultOutputs = new HashMap<OutputPort, ListOutputPort>();
    }

    public LoopActor( CompositeActor alg ) {
    }

    @Override
    public boolean isReady() {
        if ( _inputData == null ) {
            return false;
        }

        for ( ListInputPort inp : _multToSingleInputs.keySet() ) {
            if ( !_inputData.containsKey( inp ) ) {
                return false;
            } else {
                List data = _inputData.get( inp );
                if ( data.size() != 1 && data.size() != _iterations ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * inputData should be a map from InputPort to a list of Tokens
     * which should be used as sequential input to the given inputPorts.
     * The length of each list should either be the number of desired iterations
     * or one. For example, we could send in 3 lists of length 3, 3, and 1, but
     * we can't send in 3 lists of length 3, 2, and 1.
     * @param inputData
     */
    public void setInputs( Map<ListInputPort, ListToken> inputData ) {
        _inputData = inputData;
        for ( ListInputPort inp : _inputData.keySet() ) {
            if ( _inputData.get( inp ).size() != 1 ) {
                _iterations = _inputData.get( inp ).size();
                return;
            }
        }
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public boolean addAlgorithm( Actor alg ) {
        if ( _workflow.addVertex( alg ) ) {
            for ( InputPort inp : alg.getInputs() ) {
                ListInputPort multInp;
                if ( inp instanceof ListInputPort ) {
                    multInp = new ListInputPort( this, ListToken.class );
                } else {
                    multInp = new ListInputPort( this, inp.getType() );
                }
                _multToSingleInputs.put( multInp, inp );
                _singleToMultInputs.put( inp, multInp );
            }

            for ( OutputPort out : alg.getOutputs() ) {
                ListOutputPort multOut;
                if ( out instanceof ListOutputPort ) {
                    multOut = new ListOutputPort( this, ListToken.class );
                } else {
                    multOut = new ListOutputPort( this, out.getType() );
                }
                _multToSingleOutputs.put( multOut, out );
                _singleToMultOutputs.put( out, multOut );
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public boolean removeAlgorithm( Actor alg, boolean breakLinks ) {
        if ( !_workflow.containsVertex( alg ) ) {
            return false;
        }

        Set<IOLink> edgesOf = _workflow.edgesOf( alg );

        if ( !breakLinks && !edgesOf.isEmpty() ) {
            return false;
        }

        if ( breakLinks ) {
            for ( IOLink inLink : _workflow.incomingEdgesOf( alg ) ) {
                ListOutputPort multOut;
                if ( inLink.out instanceof ListOutputPort ) {
                    multOut = new ListOutputPort( this, ListToken.class );
                } else {
                    multOut = new ListOutputPort( this, inLink.out.getType() );
                }

                _multToSingleOutputs.put( multOut, inLink.out );
                _singleToMultOutputs.put( inLink.out, multOut );
            }

            for ( IOLink outLink : _workflow.outgoingEdgesOf( alg ) ) {
                ListInputPort multIn;
                if ( outLink.in instanceof ListInputPort ) {
                    multIn = new ListInputPort( this, ListToken.class );
                } else {
                    multIn = new ListInputPort( this, outLink.out.getType() );
                }

                _multToSingleInputs.put( multIn, outLink.in );
                _singleToMultInputs.put( outLink.in, multIn );
            }

            _workflow.removeAllEdges( edgesOf );
        }

        return true;
    }

    @Override
    public boolean link( OutputPort out, InputPort in ) {
        Actor start =
                  out.getAlgorithm();
        Actor end = in.getAlgorithm();

        if ( !_workflow.containsVertex( start ) || !_workflow.containsVertex( end ) ) {
            return false;
        }


        try {
            IOLink link = new IOLink( in, out );
            if ( !_workflow.addEdge( start, end, link ) ) {
                return false;
            } else {
                CycleDetector<Actor, IOLink> cd = new CycleDetector<Actor, IOLink>( _workflow );
                if ( cd.detectCycles() ) {
                    _workflow.removeEdge( link );
                    return false;
                } else {
                    ListInputPort multInp = _singleToMultInputs.get( in );
                    ListOutputPort multOut = _singleToMultOutputs.get( out );
                    _inputs.remove( multInp );
                    _outputs.remove( multOut );
                    _singleToMultInputs.remove( in );
                    _singleToMultOutputs.remove( out );
                    _multToSingleInputs.remove( multInp );
                    _multToSingleOutputs.remove( multOut );
                    return true;
                }
            }
        } catch ( IllegalArgumentException iae ) {
            return false;
        }
    }

    @Override
    public boolean unlink( OutputPort out, InputPort in ) {
        return false;
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public RunStatus run() {
        if ( !isReady() ) {
            return RunStatus.NOT_READY_ERROR;
        }

        _lastInputs.clear();
        for ( ListOutputPort multOut : _multToSingleOutputs.keySet() ) {
            multOut.put( new ListToken( multOut.getType() ) );
        }

        for ( int i = 0; i < _iterations; i++ ) {
            insertNextInputs();

            if ( !runTopological() ) {
                return RunStatus.RUN_ERROR;
            }

            for ( ListOutputPort multOut : _multToSingleOutputs.keySet() ) {
                OutputPort out = _multToSingleOutputs.get( multOut );
                Token data = out.get();
                multOut.get().add( data );
            }
        }

        return RunStatus.COMPLETE;
    }

    @SuppressWarnings (value="unchecked")
    private void insertNextInputs() {
        for ( ListInputPort multInp : _inputData.keySet() ) {
            List<Token> data = _inputData.get( multInp );
            Token tok;
            if ( data.isEmpty() ) {
                tok = _lastInputs.get( multInp );
            } else {
                tok = data.remove( 0 );
                _lastInputs.put( multInp, tok );
            }
            InputPort inp = _multToSingleInputs.get( multInp );
            inp.put( tok );
        }
    }
    /******* Protected Variables *******/
    protected Map<ListInputPort, ListToken> _inputData;
    protected int _iterations;
    protected Map<ListInputPort, Token> _lastInputs;
    protected Map<ListInputPort, InputPort> _multToSingleInputs;
    protected Map<InputPort, ListInputPort> _singleToMultInputs;
    protected Map<ListOutputPort, OutputPort> _multToSingleOutputs;
    protected Map<OutputPort, ListOutputPort> _singleToMultOutputs;
}
