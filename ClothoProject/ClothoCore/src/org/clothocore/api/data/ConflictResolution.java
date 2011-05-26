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

package org.clothocore.api.data;

import javax.swing.JOptionPane;

/**
 *
 * @author J. Christopher Anderson
 */
public class ConflictResolution {

    /**
     * Method for resolving conflicts between Strings
     *
     * @param oldstr
     * @param newstr
     * @param objName
     * @param fieldName
     * @return
     */
    public static boolean resolve(String oldstr, String newstr, String objName, String fieldName) {
        //If the field hasn't change, don't bother updating the field
        if(newstr.equals(oldstr)) {
            System.out.println("No conflict here, the " + fieldName + " isn't changed");
            return false;
        }

        //If the old field was null, update to the database value
        if(oldstr==null) {
            System.out.println("No conflict, have null original for " + fieldName +" so will update");
            return true;
        }

        //Otherwise ask the user to resolve it
        int n = JOptionPane.showConfirmDialog(
            null,
            "The " + fieldName + " field of\n\t" + objName + "\nis in conflict.  Currently you have\n" + oldstr +"\nDo you want to change the " + fieldName + " to\n" + newstr + "?",
            "Data update conflict",
            JOptionPane.YES_NO_OPTION);
        if(n==0) {
            return true;
        }

        return false;
    }

    /**
     * Method for resolving a link that seems to have been removed in database copy
     * @param missinglink
     * @param fieldName
     * @return
     */
    public static boolean resolve(String missinglink, String objName, String fieldName) {
        //Otherwise ask the user to resolve it
        int n = JOptionPane.showConfirmDialog(
            null,
            "The link " + missinglink + "\nfrom the " + fieldName + " field of\n" + objName + "\nhas been deleted in the database copy.\nDo you want continue with the deletion?",
            "Data update conflict",
            JOptionPane.YES_NO_OPTION);
        if(n==0) {
            return true;
        }

        return false;
    }
/*-----------------
     variables
 -----------------*/
}
