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
 ENHANCEMENTS, OR MODIFICATIONS..
 */

package org.clothocad.tool.registryimporter.gui;


import java.util.ArrayList;
import org.clothocore.util.xml.XMLParser;


/**
 *
 * @author J. Christopher Anderson
 */
public class allParts {

    public allParts() {
    }

    public ArrayList<String> getAllParts() {
        XMLParser myParser =  parserGrabber.get("http://partsregistry.org/das/parts/entry_points" , "DASEP");
        
        if(myParser!=null) {
            System.out.println("trying3");
            _allParts = myParser.getMultipleTagValues("SEGMENT");
        }
        return _allParts;
}


    public static void main(String[] args) {
        new allParts();
    }

/*-----------------
     variables
 -----------------*/
     private ArrayList<String> _allParts = new ArrayList<String>();
}
