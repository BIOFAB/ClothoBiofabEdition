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

package org.clothocore.api.dnd;

import java.util.EventObject;
import org.clothocore.api.data.ObjBase;

/**
 * RefreshEvents are passed during data listening events.  The event
 * refers to any change anywhere in an ObjBase, but the details of
 * what changed are contained within an enumeration (the Condition) of
 * the RefreshEvent.  Because of this, you can make more specific changes
 * in GUI than drawing everything about an ObjBase.  If this is too
 * complicated, though, you can just refresh the whole GUI and then
 * not have to worry about RefreshEvents at all.
 * 
 * @author J. Christopher Anderson
 */
public class RefreshEvent extends EventObject {

    public RefreshEvent(ObjBase obj) {
        super( obj );
    }

    public RefreshEvent(ObjBase obj, Condition cond) {
        this(obj);
        _condition = cond;
    }

    public void setEvent(Condition cond) {
        _condition = cond;
    }

    public boolean isCondition(Condition cond) {
        if(_condition.equals(cond)) {
            return true;
        }
        return false;
    }

    public Condition getCondition() {
        return _condition;
    }

/*-----------------
     variables
 -----------------*/
    public enum Condition {
        /**
         * The name was changed
         */
        NAME_CHANGED,

        /**
         * Last Modified Date was changed
         */
        LAST_DATE_CHANGED,
        /**
         * The description field was changed
         */
        DESCRIPTION_CHANGED,
        /**
         * The object was successfully saved to the database
         */
        SAVE_SUCCESSFUL,
        /**
         * The attempt to save the object failed for some reason
         */
        SAVE_FAILED,
        /**
         * The attempt to delete the object was successful (it's gone now)
         */
        DELETE_SUCCESSFUL,
        /**
         * The attempt to delete the object failed
         */
        DELETE_FAILED,
        /**
         * The NucSeq link of the object was changed.  Or if the source is a NucSeq, its sequence was changed
         */
        SEQUENCE_CHANGED,
        /**
         * The wikitext field for the target ObjBase was changed, or for a WIKITEXT,
         * the text of the WikiText was changed
         */
        WIKITEXT_CHANGED,
        /**
         * The Reference object was changed
         */
        REFERENCE_CHANGED,
        /**
         * The barcode of the ObjBase was changed
         */
        BARCODE_CHANGED,
        /**
         * An attachment was added or removed
         */
        ATTACHMENT_LINK_CHANGED,
        /**
         * A search tag was added
         */
        SEARCHTAG_ADDED,
        /**
         * The author field was changed
         */
        AUTHOR_CHANGED,
        /**
         *  The format of the part/vector/plasmid was changed
         */
        FORMAT_CHANGED,
        /**
         * Sample added to a container
         */
        SAMPLE_TO_CONTAINER,
        /**
         * A link between plasmids and strains was changed
         */
        PLASMID_TO_STRAIN,
        /**
         * A Factoid added/removed to a Note
         */
        FACTOID_TO_NOTE,
        /**
         * A Note added/removed to a Note, strain, feature, or family
         */
        NOTE_LINKED,
        /**
         * An annotation was added or removed from a nucseq
         */
        ANNOTATION_TO_NUCSEQ,
        /**
         * A container was added/removed to a plate
         */
        CONTAINER_TO_PLATE,
        /**
         * A search tag was removed
         */
        SEARCHTAG_REMOVED,


        /**
         * The email field was changed
         */
        EMAIL_CHANGED,
        /**
         * The email field was changed
         */
        GIVEN_NAME_CHANGED,
        /**
         * The surname field was changed
         */
        SURNAME_CHANGED,
        /**
         * The nick name field was changed
         */
        NICKNAME_CHANGED,
        /**
         * The registry name field was changed
         */
        REGISTRY_NAME_CHANGED,
        /**
         * The address field was changed
         */
        ADDRESS_CHANGED,
        /**
         * The lab field was changed
         */
        LAB_CHANGED,
        /**
         * The institution field was changed
         */
        INSTITUTION_CHANGED,
        /**
         * The institution field was changed
         */
        WEBSITE_CHANGED,
        /**
         * The department field was changed
         */
        DEPARTMENT_CHANGED,
        /**
         * The principal investigator field was changed
         */
        PI_CHANGED,
        /**
         * The city field was changed
         */
        CITY_CHANGED,
        /**
         * The state field was changed
         */
        STATE_CHANGED,
        /**
         * The country field was changed
         */
        COUNTRY_CHANGED,
        /**
         * Password or admin properties were altered for a user
         */
        SECURITY_CHANGED,
        /**
         * The Genbank ID field of a Feature was changed
         */
        GENBANK_CHANGED,
        /**
         * A color field has changed
         */
        COLOR_CHANGED,
        /**
         * The risk group of a Feature, Family, Strain, Part, Vector, or Plasmid was changed
         */
        RISK_GROUP_CHANGED,
        /**
         * An ObjBase was added to the Collection
         */
        COLLECTION_ADD,
        /**
         * The type of the ObjBase has changed.  This doesn't mean the ObjType, but rather
         * more specific subtype fields like the StrainType of a Strain
         */
        TYPE_CHANGED,
        /**
         * A link between Samples and SampleData was changed
         */
        DATA_TO_SAMPLE,
        /**
         * The volume of a sample was changed
         */
        VOLUME_CHANGED,
        /**
         * The Strain of a sample was changed
         */
        STRAIN_CHANGED,
        /**
         * The Plasmid link to a sample was changed
         */
        PLASMID_CHANGED,
        /**
         * The Parent type field of Strain was changed
         */
        PARENT_STRAIN_CHANGED,
        /**
         * An ObjBase was removed from the Collection
         */
        COLLECTION_REMOVE,
        /**
         * The condition hasn't been set for this event
         */
        UNKNOWN,
        /**
         * Update everything
         */
        UPDATE_ALL,
        /**
         * A family was added or removed from a Feature
         */
        FAMILY_TO_FEATURE,

    }

    private Condition _condition = Condition.UNKNOWN;
}
