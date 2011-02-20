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
package org.clothocore.api.actor.data;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class BooleanToken extends Token {

    public BooleanToken( boolean data ) {
        super( Boolean.class );
        _data = data;
    }

    @Override
    public Boolean getData() {
        return _data;
    }

    public boolean setData(Object data) {
        if(!(data instanceof Boolean)) {
            return false;
        }
        _data = (Boolean) data;
        return true;
    }

    public boolean setData(Boolean data) {
        _data = data;
        return true;
    }

    public boolean setData(boolean data) {
        _data = data;
        return true;
    }

    @Override
    public String toString() {
        if ( _data != null ) {
            return _data.toString();
        }
        return null;
    }
    public Boolean _data;
}
