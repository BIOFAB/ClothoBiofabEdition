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
package org.clothocore.api.actor.io;

import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.Token;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class InputPort<TT extends Token> extends Port {

    /**
     * Provide an instance of the type of data this InputPort should receive
     * @param sampleData not null
     */
    public InputPort ( Actor alg, Class<? super TT> type ) {
        if (alg == null || type == null) {
            throw new IllegalArgumentException( "Algorithm and type not allowed to be null" );
        }

        init( alg, type, "" + nextID++ );
    }

    /**
     * Provide an instance of the type of data this InputPort should receive, as
     * well as an ID for this InputPort
     * @param sampleData not null
     * @param id not null
     */
    public InputPort ( Actor alg, Class<? super TT> type, String id ) {
        if (alg == null || type == null || id == null || Actor.getInput( id ) != null) {
            throw new IllegalArgumentException( "Algorithm, type and id not allowed to be null, and id must be unique." );
        }

        init( alg, type, id );
    }

    private void init ( Actor alg, Class type, String id ) {
        _algorithm = alg;
        _type = type;
        _id = id;

        alg.registerInput( this );
    }

    /**
     * Returns if the given OutputPort is compatible with this InputPort. This
     * default behavior looks at the type of both ports, and returns true
     * if the two types exactly match.
     * @param op
     * @return
     */
    public boolean compatible ( OutputPort op ) {
        Class outType = op.getType();
        return Port.compatible( _type, outType);
    }

    /**
     *
     * @return
     */
    public String getID () {
        return _id;
    }

    /**
     * 
     * @return
     */
    public Actor getAlgorithm () {
        return _algorithm;
    }

    /**
     * 
     * @return
     */
    public Class getType () {
        return _type;
    }

    /**
     * Puts data in this InputPort, so the ClothoAlgorithm can take it out later.
     * Pre-condition: this.isClear() returns true
     * @param data
     */
    public boolean put ( TT data ) {
        if (_data != null) {
            return false;
        }
        _data = data;
        return true;
    }

    /**
     * Gets the data from this input port. Pre-condition: this.hasData() should
     * have returned true.
     * @return
     */
    public TT get () {
        return _data;
    }

    /**
     * After grabbing the data with clear, the 
     */
    public void clear () {
        _data = null;
    }

    /**
     *
     * @return true if this InputPort has data
     */
    public boolean hasData () {
        return _data != null;
    }

    /**
     *
     * @return true if this InputPort is clear
     */
    public boolean isClear () {
        return _data == null;
    }

    @Override
    public boolean equals ( Object o ) {
        if (!( o instanceof InputPort )) {
            return false;
        }

        InputPort other = (InputPort) o;
        return other._id.equals( _id );
    }

    @Override
    public int hashCode () {
        int hash = 3;
        hash = 67 * hash + ( this._id != null ? this._id.hashCode() : 0 );
        return hash;
    }

    /******* Private Variables *******/
    private TT _data;
    private Actor _algorithm;
    private Class _type;
    private String _id;
    /******* Statc Variables *******/
    private static long nextID;
}
