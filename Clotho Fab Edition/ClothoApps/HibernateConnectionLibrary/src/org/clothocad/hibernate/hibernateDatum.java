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

package org.clothocad.hibernate;

import java.util.Date;
import org.clothocore.api.data.ObjBase;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public interface hibernateDatum {

    /**
     * Construct an ObjBase object using data from this SavedObject
     * @return
     */
    public ObjBase getObject();

    /**
     * Update the given datum using this hibernate object.
     * @param datum
     */
    public ObjBase.ObjBaseDatum getObjBaseDatum();

    /**
     * Get the name field for the object
     * @return
     */
    public String getName();

    /**
     * Get the uuid field for the object
     * @return
     */
    public String getUUID();

    /**
     * Returns the date lastModified fields of datums that have it
     * or the current time if the field is unavailable
     * @return
     */
    Date getLastModified();

    /**
     * Return whether the table needs secondary processing for saving Xrefs
     * or not
     * @return
     */
    boolean needsSecondaryProcessing();

    /**
     * Run any needed secondary processing of Xrefs (for xref saving
     * when it's a new table entry)
     */
    void runSecondaryProcessing(ObjBase obj);
}
