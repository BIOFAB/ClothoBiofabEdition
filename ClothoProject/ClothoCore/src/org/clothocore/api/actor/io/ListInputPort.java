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

import java.util.List;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.ListToken;
import org.clothocore.api.actor.data.Token;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class ListInputPort<T> extends InputPort {

    @SuppressWarnings (value="unchecked")
    public ListInputPort( Actor alg, Class listType ) {
        super( alg, listType );
    }

    @Override
    public ListToken get() {
        return _data;
    }

    @SuppressWarnings (value="unchecked")
    public List<T> getList() {
        return _data;
    }

    @Override
    public boolean put( Token tok ) {
        if ( !(tok instanceof ListToken) ) {
            return false;
        }
        ListToken lt = (ListToken) tok;
        if ( Port.compatible( this.getType(), lt.getType() ) && _data == null ) {
            _data = lt;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        _data = null;
    }
    private ListToken _data;
}
