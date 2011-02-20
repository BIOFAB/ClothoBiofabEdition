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
package org.clothocore.api.actor.io;

import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.Token;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class OutputPort<TT extends Token> extends Port {

    /**
     * sampleData must not be null
     * @param sampleData
     */
    public OutputPort ( Actor alg, Class<TT> type ) {
        if (type == null) {
            throw new IllegalArgumentException( "Sample Data not allowed to be null" );
        }

        _algorithm = alg;
        _type = type;
        _id = "" + nextID++;
        alg.registerOutput( this );
    }

    public OutputPort ( Actor alg, Class<TT> type, String id ) {
        if (type == null || id == null) {
            throw new IllegalArgumentException( "Sample data and id not allowed to be null" );
        }

        _algorithm = alg;
        _type = type;
        _id = id;
        alg.registerOutput( this );
    }

    public TT get () {
        return _data;
    }

    public void clear () {
        _data = null;
    }

    public void put ( TT data ) {
        _data = data;
    }

    public Class getType () {
        return _type;
    }

    public boolean hasData () {
        return _data != null;
    }

    public String getID () {
        return _id;
    }

    public Actor getAlgorithm () {
        return _algorithm;
    }

    @Override
    public boolean equals ( Object o ) {
        if (!( o instanceof OutputPort )) {
            return false;
        }

        OutputPort other = (OutputPort) o;
        return other._id.equals( _id );
    }

    @Override
    public int hashCode () {
        int hash = 7;
        hash = 71 * hash + ( this._id != null ? this._id.hashCode() : 0 );
        return hash;
    }

    /******* Private Variables *******/
    private TT _data;
    private Class _type;
    private String _id;
    private Actor _algorithm;
    /******* Static Variables *******/
    private static long nextID = 0;
}
