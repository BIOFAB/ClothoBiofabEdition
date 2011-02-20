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
public class ClothoHelp {

    public ClothoHelp(FileObject file)
    {
       _helpURL = new String();
       _helpDescription = new String();
       _helpCommands = new ArrayList<String>();
       createHelp(file);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////


    /**
     * Getter for help url string
     * @return String for help url
     */
    public String getHelpUrl(){
        return _helpURL;
    }

    /**
     * Getter for the help description
     * @return String for the help description
     */
    public String getHelpDescription(){
        return _helpDescription;
    }

    /**
     * Getter for the help command list
     * @return ArrayList<String> for the commands
     */
    public ArrayList<String> getCommands(){
        return _helpCommands;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    /**
     * Initialize the help variables based on the FileObject
     * @param xmlFile
     */
    private void createHelp(FileObject xmlFile)
    {
        XMLParser parser;
        try {
            parser = new XMLParser( xmlFile.getInputStream(), "help" );
            _helpCommands = parser.getMultipleTagValues( "helpcommand" );
            _helpURL = parser.getFirstTag("helpurl");
            _helpDescription = parser.getFirstTag("helpdescription");
        } catch ( Exception ex ) {
            Exceptions.printStackTrace( ex );
            return;
        }
    }


    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////

    private ArrayList<String> _helpCommands;
    private String _helpDescription;
    private String _helpURL;

    

}
