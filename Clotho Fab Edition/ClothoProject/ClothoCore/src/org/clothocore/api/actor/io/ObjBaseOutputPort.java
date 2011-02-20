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

import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.ObjBaseToken;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class ObjBaseOutputPort<T extends ObjBase> extends OutputPort<ObjBaseToken> {

    public ObjBaseOutputPort(Actor alg, ObjType type) {
        super(alg, ObjBaseToken.class);
        _objBaseType = type;
    }

    public ObjType getObjType() {
        return _objBaseType;
    }

    @Override
    public void put(ObjBaseToken data) {
        _data = data;
    }

    @Override
    public ObjBaseToken get() {
        return _data;
    }

    protected ObjType _objBaseType;
    protected ObjBaseToken _data;
}
