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

import java.util.List;
import java.util.Set;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.RunStatus;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class CompositeActor extends Actor {

    public CompositeActor() {
        _workflow = new DirectedMultigraph<Actor, IOLink>( IOLink.class );
        _running = false;
    }

    public String getName() {
        return "Composite Algorithm";
    }

    @Override
    public List<InputPort> getInputs() {
        return _inputs;
    }

    @Override
    public List<OutputPort> getOutputs() {
        return _outputs;
    }

    /**
     * Adds the given ClothoAlgorithm to the workflow.
     * @param alg
     * @return
     */
    public boolean addAlgorithm( Actor alg ) {
        if ( _running ) {
            return false;
        }

        _inputs.addAll( alg.getInputs() );
        _outputs.addAll( alg.getOutputs() );
        return _workflow.addVertex( alg );
    }

    public boolean removeAlgorithm( Actor alg, boolean breakLinks ) {
        if ( _running ) {
            return false;
        }

        if ( !_workflow.containsVertex( alg ) ) {
            return false;
        }

        Set<IOLink> edgesOf = _workflow.edgesOf( alg );

        if ( !breakLinks && !edgesOf.isEmpty() ) {
            return false;
        }

        if ( breakLinks ) {
            for ( IOLink inLink : _workflow.incomingEdgesOf( alg ) ) {
                _outputs.add( inLink.out );
            }

            for ( IOLink outLink : _workflow.outgoingEdgesOf( alg ) ) {
                _inputs.add( outLink.in );
            }
            _workflow.removeAllEdges( edgesOf );
        }

        return true;
    }

    /**
     * Links the given InputPort to the given OutputPort. The ports must be
     * compatible (in.compatible(out) should return true), and the link must
     * not create any cycles in the workflow.
     * @param in
     * @param out
     * @return
     */
    public boolean link( OutputPort out, InputPort in ) {
        // TODO: think about returning what type of error occurred rather than
        // just true or false
        if ( _running ) {
            return false;
        }

        Actor start = out.getAlgorithm();
        Actor end = in.getAlgorithm();

        if ( (!_workflow.containsVertex( start ) || !_workflow.containsVertex( end )) ) {
            return false;
        }

        for ( IOLink link : _workflow.getAllEdges( start, end ) ) {
            if ( link.in.equals( in ) && link.out.equals( out ) ) {
                return false;
            }
        }

        for ( IOLink link : _workflow.incomingEdgesOf( end ) ) {
            if ( link.in.equals( in ) ) {
                return false;
            }
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
                    _inputs.remove( in );
                    _outputs.remove( out );
                    return true;
                }
            }
        } catch ( IllegalArgumentException iae ) {
            return false;
        }
    }

    /**
     * Unlinks the given two ports.
     * @param out
     * @param in
     * @return
     */
    public boolean unlink( OutputPort out, InputPort in ) {
        if ( _running ) {
            return false;
        }

        Actor start = out.getAlgorithm();
        Actor end = in.getAlgorithm();

        if ( !_workflow.containsVertex( start ) || !_workflow.containsVertex( end ) ) {
            return false;
        }

        if ( _workflow.removeEdge( start, end ) == null ) {
            return false;
        }

        _inputs.add( in );
        _outputs.add( out );

        return true;
    }

    /**
     * Removes the given IOLink from the CompositeAlgorithm
     * @param link
     * @return
     */
    public boolean unlink( IOLink link ) {
        return unlink( link.out, link.in );
    }

    /**
     * Gets the set of all algorithms in the workflow.
     * @return
     */
    public Set<Actor> getAlgorithms() {
        return _workflow.vertexSet();
    }

    /**
     * Gets all the links in the workflow.
     * @return
     */
    public Set<IOLink> getAllLinks() {
        return _workflow.edgeSet();
    }

    /**
     * Returns all the incoming links to the given algorithm. The links returned
     * will have their InputPorts belong to the given algorithm.
     * @param alg
     * @return
     */
    public Set<IOLink> getIncomingLInks( Actor alg ) {
        if ( !_workflow.containsVertex( alg ) ) {
            return null;
        }

        return _workflow.incomingEdgesOf( alg );
    }

    /**
     * Returns all the outgoing links of the given algorithm. The links
     * returned will have the OutputPorts belong to the given algorithm.
     * @param alg
     * @return
     */
    public Set<IOLink> getOutgoingLinks( Actor alg ) {
        if ( !_workflow.containsVertex( alg ) ) {
            return null;
        }

        return _workflow.outgoingEdgesOf( alg );
    }

    /**
     * Runs the workflow. Since a workflow is defined to not have any cycles,
     * it is a directed acyclic graph (DAG), and there exists a partial
     * ordering on the workflow. The algorithms in the workflow are run in
     * a topological order, which should ensure that the workflow runs to
     * completion, assuming that all the inputs have been linked to some output.
     * @return
     */
    @Override
    public RunStatus run() {
        _running = true;
        if ( !isReady() ) {
            return RunStatus.NOT_READY_ERROR;
        }

        boolean success = runTopological();
        _running = false;
        if ( success ) {
            return RunStatus.COMPLETE;
        } else {
            return RunStatus.RUN_ERROR;
        }
    }

    public RunStatus runOneStep() {
        if ( !_running ) {
            _running = true;
            _workflowIterator = new TopologicalOrderIterator<Actor, IOLink>( _workflow );
        }

        if ( !_workflowIterator.hasNext() ) {
            _running = false;
            _workflowIterator = null;
            return RunStatus.COMPLETE;
        }

        _currentAlgorithm = _workflowIterator.next();
        if ( !_currentAlgorithm.isReady() ) {
            return RunStatus.NOT_READY_ERROR;
        }

        RunStatus result = _currentAlgorithm.run();

        if ( result == RunStatus.COMPLETE ) {
            return RunStatus.ONE_STEP_SUCCESS;
        } else {
            return RunStatus.RUN_ERROR;
        }
    }

    @SuppressWarnings (value="unchecked")
    protected boolean runTopological() {
        TopologicalOrderIterator<Actor, IOLink> iter = new TopologicalOrderIterator<Actor, IOLink>( _workflow );
        while ( iter.hasNext() ) {
            _currentAlgorithm = iter.next();

            if ( !_currentAlgorithm.isReady() ) {
                // TODO: signal error
                return false;
            }

            if ( _currentAlgorithm.run() != RunStatus.COMPLETE ) {
                // TODO: signal that alg failed at running
                return false;
            }

            for ( IOLink link : getOutgoingLinks( _currentAlgorithm ) ) {
                link.in.put( link.out.get() );
            }
        }
        return true;
    }
    /******* Private Variables *******/
    protected DirectedGraph<Actor, IOLink> _workflow;
    protected boolean _running;
    protected TopologicalOrderIterator<Actor, IOLink> _workflowIterator;
    protected Actor _currentAlgorithm;
}
