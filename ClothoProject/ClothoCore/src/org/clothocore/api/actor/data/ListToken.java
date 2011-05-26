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
package org.clothocore.api.actor.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class ListToken<ListType> extends Token implements List<ListType> {

    public ListToken( Class<ListType> type ) {
        super( type );
        _data = new ArrayList<ListType>();
    }

    protected ListToken( Class type, List<ListType> data ) {
        super( type );
        _data = data;
    }

    /**
     * Sets the list to be data, if the data is a List. Does not error check
     * to make sure all elements of the list match the type of this ListToken
     * @param data
     * @return
     */
    @Override
    @SuppressWarnings (value="unchecked")
    public boolean setData( Object data ) {
        if ( !(data instanceof List) ) {
            return false;
        }

        _data = (List<ListType>) data;
        return true;
    }

    @Override
    public List<ListType> getData() {
        return _data;
    }
    private List<ListType> _data;

    public int size() {
        return _data.size();
    }

    public boolean isEmpty() {
        return _data.isEmpty();
    }

    @SuppressWarnings("element-type-mismatch")
    public boolean contains( Object o ) {
        return _data.contains( o );
    }

    public Iterator<ListType> iterator() {
        return _data.iterator();
    }

    public Object[] toArray() {
        return _data.toArray();
    }

    public <T> T[] toArray( T[] a ) {
        return _data.toArray( a );
    }

    public boolean add( ListType e ) {
        return _data.add( e );
    }

    @SuppressWarnings("element-type-mismatch")
    public boolean remove( Object o ) {
        return _data.remove( o );
    }

    public boolean containsAll( Collection<?> c ) {
        return _data.containsAll( c );
    }

    public boolean addAll( Collection<? extends ListType> c ) {
        return _data.addAll( c );
    }

    public boolean addAll( int index, Collection<? extends ListType> c ) {
        return _data.addAll( index, c );
    }

    public boolean removeAll( Collection<?> c ) {
        return _data.removeAll( c );
    }

    public boolean retainAll( Collection<?> c ) {
        return _data.retainAll( c );
    }

    public void clear() {
        _data.clear();
    }

    public ListType get( int index ) {
        return _data.get( index );
    }

    public ListType set( int index, ListType element ) {
        return _data.set( index, element );
    }

    public void add( int index, ListType element ) {
        _data.add( index, element );
    }

    public ListType remove( int index ) {
        return _data.remove( index );
    }

    public int indexOf( Object o ) {
        return _data.indexOf( o );
    }

    public int lastIndexOf( Object o ) {
        return lastIndexOf( o );
    }

    public ListIterator<ListType> listIterator() {
        return _data.listIterator();
    }

    public ListIterator<ListType> listIterator( int index ) {
        return _data.listIterator( index );
    }

    public List<ListType> subList( int fromIndex, int toIndex ) {
        return new ListToken<ListType>( _type, _data.subList( fromIndex, toIndex ) );
    }
}
