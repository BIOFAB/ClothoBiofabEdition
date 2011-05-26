/*
 *  Copyright (c) 2010 The Regents of the University of California.
 * 
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
import org.clothocore.api.actor.data.MapToken;
import org.clothocore.api.actor.data.Token;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class MapInputPort<K, V> extends InputPort {

    @SuppressWarnings (value="unchecked")
    public MapInputPort( Actor alg, Class keyType, Class valueType ) {
        super( alg, MapToken.class );
        _keyType = keyType;
        _valueType = valueType;
    }

    @Override
    public boolean compatible( OutputPort out ) {
        if ( !(out instanceof MapOutputPort) ) {
            return false;
        }

        MapOutputPort mapOut = (MapOutputPort) out;

        if ( Port.compatible( mapOut.getKeyType(), _keyType )
                && Port.compatible( mapOut.getValueType(), _valueType ) ) {
            return true;
        }

        return false;
    }

    @Override
    public MapToken<K, V> get() {
        return _data;
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public boolean put( Token data ) {
        if ( !(data instanceof MapToken) ) {
            return false;
        }

        MapToken mapData = (MapToken) data;
        if(Port.compatible( _keyType, mapData.getKeyType()) &&
                Port.compatible( _valueType, mapData.getValueType())) {
            _data = mapData;
            return true;
        }
        
        return false;
    }

    public boolean put( MapToken<K, V> data ) {
        _data = data;
        return true;
    }
    
    private MapToken<K, V> _data;
    private Class _keyType, _valueType;
}
