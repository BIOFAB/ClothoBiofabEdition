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
package org.clothocore.api.actor.workflow;

import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class IOLink {

    public IOLink ( InputPort in, OutputPort out ) {
        if (!in.compatible( out )) {
            throw new IllegalArgumentException( "The input and output must be compatible!" );
        }
        this.in = in;
        this.out = out;
    }

    @Override
    public boolean equals ( Object o ) {
        if (!( o instanceof IOLink )) {
            return false;
        }

        IOLink other = (IOLink) o;
        return ( in.equals( other.in ) && out.equals( other.out ) );
    }

    @Override
    public int hashCode () {
        int hash = 7;
        hash = 29 * hash + ( this.in != null ? this.in.hashCode() : 0 );
        hash = 29 * hash + ( this.out != null ? this.out.hashCode() : 0 );
        return hash;
    }

    InputPort in;
    OutputPort out;
}
