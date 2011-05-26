/*
Copyright (c) 2010 The Regents of the University of California.
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
ENHANCEMENTS, OR MODIFICATIONS..
 */

package org.clothocore.api.core.wrapper.support;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.clothocore.util.xml.XMLParser;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author Douglas Densmore
 */
public class ClothoDependency {


    public ClothoDependency(FileObject file) {
        _dependencyList = new ArrayList<String>();
        createList(file);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /**
     * Return the whole dependency list
     * @return ArrayList<String> for the dependencies
     */
    public ArrayList<String> getDependencies() {
        return _dependencyList;
    }

    /**
     * Check if the provided uuid is is the dependency list
     * @param uuid - the id to check if is in the list
     * @return true if the provided string is in the dependency list
     */
    public boolean isDependent(String uuid) {
        for (String dependency : _dependencyList) {
            if (dependency.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    /**
     * Method which creates the list of dependencies
     * @param xmlFile
     */
    private void createList(FileObject xmlFile) {

     XMLParser parser;
        try {
            parser = new XMLParser( xmlFile.getInputStream(), "plugin" );
        } catch ( FileNotFoundException ex ) {
            Exceptions.printStackTrace( ex );
            return;
        }

        _dependencyList = parser.getMultipleTagValues( "dependency" );

    }

    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////
    private ArrayList<String> _dependencyList;

}
