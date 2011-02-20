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

package org.clothocore.api.actor.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class MapToken<K, V> extends Token implements Map<K, V> {

    public MapToken(Class keyType, Class valueType) {
        super(MapToken.class);
        _data = new HashMap<K, V>();
        _keyType = keyType;
        _valueType = valueType;
    }

    protected Map<K, V> _data;
    protected Class _keyType, _valueType;

    @Override
    public Map getData() {
        return _data;
    }

    public Class getKeyType() {
        return _keyType;
    }

    public Class getValueType() {
        return _valueType;
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public boolean setData( Object data ) {
        if(!(data instanceof Map)) {
            return false;
        }

        _data = (Map) data;
        return true;
    }

    public int size() {
        return _data.size();
    }

    public boolean isEmpty() {
        return _data.isEmpty();
    }

    @SuppressWarnings("element-type-mismatch")
    public boolean containsKey( Object key ) {
        return _data.containsKey( key );
    }

    @SuppressWarnings("element-type-mismatch")
    public boolean containsValue( Object value ) {
        return _data.containsValue( value );
    }

    @SuppressWarnings("element-type-mismatch")
    public V get( Object key ) {
        return _data.get( key );
    }

    public V put( K key, V value ) {
        return _data.put( key, value );
    }

    @SuppressWarnings("element-type-mismatch")
    public V remove( Object key ) {
        return _data.remove( key );
    }

    public void putAll( Map<? extends K, ? extends V> m ) {
        _data.putAll( m );
    }

    public void clear() {
        _data.clear();
    }

    public Set<K> keySet() {
        return _data.keySet();
    }

    public Collection<V> values() {
        return _data.values();
    }

    public Set<Entry<K, V>> entrySet() {
        return _data.entrySet();
    }

}
