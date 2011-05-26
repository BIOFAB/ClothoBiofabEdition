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

import org.clothocore.api.core.Collector;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.core.Hub;
import org.openide.util.Exceptions;

/**
 *
 * @author J. Christopher Anderson
 */
public class Person extends ObjBase {

    /**
     * Constructor for ClothoConnections only
     * @param d
     */
    public Person( PersonDatum d ) {
        super( d );
        _personDatum = d;
    }

    /**Constructor from raw data
     *
     * @param displayname = String of author such as "JCAnderson"
     * @param affiliation = String of affiliation such as "UC Berkeley"
     */
    public Person( String displayname, Lab alab, String rawPassword ) {
        super();
        _personDatum = new PersonDatum();
        _datum = _personDatum;
        _datum.uuid = _uuid;
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();

        Person prexistingSeq = retrieveByName( displayname );
        String newname = displayname;
        while ( prexistingSeq != null ) {
            displayname = JOptionPane.showInputDialog( "A Person named " + displayname + " already exists, please give me a new name." );
            if(displayname==null) {
                newname += "_copy";
            } else {
                newname = displayname;
            }
            prexistingSeq = retrieveByName( displayname );
        }
        _datum.name = newname;
        
        if ( alab != null ) {
            _personDatum._labUUID = alab.getUUID();
        }
        changePassword( rawPassword );
        Collection herCollection = new Collection( this.getDisplayName() + "'s collection", "User " + this.getDisplayName() + "'s personal collection of objects", this );
        _personDatum._myCollectionID = herCollection.getUUID();
        _isBrandNew = true;
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting person save" );

        if ( Collector.isLocal( this._personDatum._labUUID ) ) {
            Lab alab = getLab();
            if ( alab != null ) {
                if ( !alab.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( Collector.isLocal( this.getBiography().getUUID() ) ) {
            WikiText wt = getBiography();
            if ( wt != null ) {
                if ( !wt.save( conn ) ) {
                    return false;
                }
            }
        }

        if ( this.isInDatabase() ) {
            if ( Collector.isLocal( this._personDatum._myCollectionID ) ) {
                Collection coll = getHerCollection();
                if ( coll != null ) {
                    if ( !coll.save( conn ) ) {
                        return false;
                    }
                }
            }

            //For a new Person, have a chicken-before-the-egg problem, this resolves that
        } else {
            String tempcollID = _personDatum._myCollectionID;
            _personDatum._myCollectionID = null;
            super.save( conn );
            _personDatum._myCollectionID = tempcollID;
            _datum._isChanged = true;
            if ( Collector.isLocal( this._personDatum._myCollectionID ) ) {
                Collection coll = getHerCollection();
                if ( coll != null ) {
                    if ( !coll.save( conn ) ) {
                        return false;
                    }
                }
            }
        }

        return super.save( conn );
    }

    /* SETTERS
     * */

    /**
     * Public accessible method for setting a Person as administrator.
     *
     * An administrator must be the current user for this to do anything.
     * -OR-
     * If the database has only 3 people in it the change is also allowed
     *
     * Admins have the ability to clear a person's password and possibly other
     * sensitive things.
     *
     * @param isit
     */
    public final void setAsAdministrator( boolean isit ) {
        Person curruser = Collector.getCurrentUser();
        if(curruser!=null) {
            if ( curruser._personDatum._isAdministrator ) {
                addUndo( "_isAdministrator", _personDatum._isAdministrator, isit );
                _personDatum._isAdministrator = isit;
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.SECURITY_CHANGED);
                return;
            }
        }
        ArrayList<ObjLink> allPersons = Collector.getAllLinksOf( ObjType.PERSON );
        if ( allPersons.size() < 3 ) {
            JOptionPane.showMessageDialog( null, "Since you appear to be a new lab, I'll allow this, but once you exceed 3 people only an administrator can make this change.", "Error", JOptionPane.OK_OPTION );
            addUndo( "_isAdministrator", _personDatum._isAdministrator, isit );
            _personDatum._isAdministrator = isit;
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.SECURITY_CHANGED);
            return;
        }
        JOptionPane.showMessageDialog( null, "I'm sorry, an administrator must be logged in to set this person as an administrator.  I'm canceling the change.", "Error", JOptionPane.ERROR_MESSAGE );
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.SECURITY_CHANGED);
    }

    /* SETTERS
     * */

    @Override
    public boolean addObject( ObjBase dropObject ) {
        switch ( dropObject.getType() ) {
            case PART:
                return false;
            default:
                return false;
        }
    }

    /**
     * Method for clearing the password so that a new one can be entered
     * An administrator must be logged in to use this
     */
    public final boolean clearPassword() {
        Person user = Collector.getCurrentUser();
        if(user==null) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SECURITY_CHANGED));
            return false;
        }
        if(!user._personDatum._isAdministrator) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SECURITY_CHANGED));
            return false;
        }
        this._personDatum._password = null;
        setChanged(RefreshEvent.Condition.SECURITY_CHANGED);
        return true;
    }

    /**
     * Plugin-accessible method for changing the Person's password
     * @param raw
     */
    public final void changePassword( String raw ) {
        String newpass = SHA1( raw );

        if(_personDatum._password==null || _personDatum._password.equals("")) {
        } else {
            if(!checkPassword()) {
                JOptionPane.showMessageDialog( null, "I'm sorry, your password didn't match.  I'm canceling the change.", "Error", JOptionPane.ERROR_MESSAGE );
                fireData(new RefreshEvent(this, RefreshEvent.Condition.SECURITY_CHANGED));
                return;
            }
        }

        final JPasswordField jpf = new JPasswordField();
        JOptionPane jop = new JOptionPane(jpf,
        JOptionPane.QUESTION_MESSAGE,
        JOptionPane.OK_OPTION);

        JDialog dialog = jop.createDialog("Please retype your password:");
        dialog.setVisible(true);
        jpf.requestFocusInWindow();
        int result = (Integer)jop.getValue();
        dialog.dispose();

        char[] chars = jpf.getPassword();
        String rawValue = "";
        for(char c : chars) {
            rawValue+=c;
        }
        String inputValue = SHA1( rawValue );

        if ( newpass.equals( inputValue ) ) {
            addUndo( "_password", _personDatum._password, inputValue );
            _personDatum._password = inputValue;
            JOptionPane.showMessageDialog( null, "Your password has been changed.", "Confirmed", JOptionPane.OK_OPTION );
            setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.SECURITY_CHANGED);
        } else {
            JOptionPane.showMessageDialog( null, "I'm sorry, your password didn't match.  I'm canceling the change.", "Error", JOptionPane.ERROR_MESSAGE );
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SECURITY_CHANGED));
        }
    }

    /**
     * Check the Person's password
     * @return true if the user successfully provided the correct password
     */
    public final boolean checkPassword() {
        final JPasswordField jpf = new JPasswordField();
        JOptionPane jop = new JOptionPane(jpf,
        JOptionPane.QUESTION_MESSAGE,
        JOptionPane.OK_OPTION);

        JDialog dialog = jop.createDialog("Please enter your password:");
        dialog.setVisible(true);
        jpf.requestFocusInWindow();
        int result = (Integer)jop.getValue();
        dialog.dispose();

        char[] chars = jpf.getPassword();
        String rawValue = "";
        for(char c : chars) {
            rawValue+=c;
        }
   //     System.out.println("check password rawValue " + rawValue);
        String inputValue = SHA1( rawValue );
        return checkPassword(inputValue);
    }

    /**
     * Login this Person.  This involves validating that the password has been confirmed.
     */
    public final void login() {
        _isLoggedIn = true;
        if (checkPassword()) {
            _isLoggedIn = true;
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SECURITY_CHANGED));
            return;
        } else {
            JOptionPane.showMessageDialog( null, "I'm sorry, your password didn't match.  You aren't logged in.", "Error", JOptionPane.ERROR_MESSAGE );
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SECURITY_CHANGED));
            return;
        }
    }

    /**
     * Plugin-accessible call to determine if the Person is logged in
     * @return true if this Person is logged in
     */
    public final boolean isLoggedIn() {
        return _isLoggedIn;
    }

    /**
     * Change the User's first name
     * @param str a String
     */
    public final void changeGivenName( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.GIVEN_NAME_CHANGED));
            return;
        }
        addUndo( "_given_name", _personDatum._given_name, str );
        _personDatum._given_name = str;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.GIVEN_NAME_CHANGED);
    }

    /**
     * Change the user's last name (surname)
     * @param str a String
     */
    public final void changeSurName( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.SURNAME_CHANGED));
            return;
        }
        addUndo( "_surname", _personDatum._surname, str );
        _personDatum._surname = str;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.SURNAME_CHANGED);
    }

    /**
     * Change the name a user wishes to be called
     * @param str a String
     */
    public final void changeNickName( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.NICKNAME_CHANGED));
            return;
        }
        addUndo( "_nick_name", _personDatum._nick_name, str );
        _personDatum._nick_name = str;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NICKNAME_CHANGED);
    }

    /**
     * Change the user's display name
     * @param str a String
     */
    @Override
    public final void changeName( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.NAME_CHANGED));
            return;
        }
        super.changeName(str);
    }

    /**
     * Change the user's display name
     * @param str a String
     */
    public final void changeDisplayName( String str ) {
        changeName(str);
    }

    /**
     * Change the User's Registry login name
     * @param str a String
     */
    public final void changeRegistryName( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.REGISTRY_NAME_CHANGED));
            return;
        }
        addUndo( "_registry_name", _personDatum._registry_name, str );
        _personDatum._registry_name = str;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.REGISTRY_NAME_CHANGED);
    }

    /**
     * Change the Person's Lab to a different Lab
     * @param alab a Lab ObjBase
     */
    public final void changeLab( Lab alab ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.LAB_CHANGED));
            return;
        }
        if ( alab == null ) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.LAB_CHANGED));
            return;
        }
        addUndo( "_labUUID", _personDatum._labUUID, alab.getUUID() );
        _personDatum._labUUID = alab.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.LAB_CHANGED);
    }

    /**
     * Change the paper envelope mailing address
     * @param str the new address
     */
    public final void changeSnailMailAddress( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.ADDRESS_CHANGED));
            return;
        }
        addUndo( "_snailmail_address", _personDatum._snailmail_address, str );
        _personDatum._snailmail_address = str;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.ADDRESS_CHANGED);
    }

    /**
     * Change the Person's Email address.  The argument must be a properly formatted
     * email address such as "bob@bob.com"
     * @param str the new email address
     */
    public final void changeEmailAddress( String str ) {
        if(!hasChangeClearance()) {
            fireData(new RefreshEvent(this, RefreshEvent.Condition.EMAIL_CHANGED));
            return;
        }
        while( !validateEmailString( str ) ) {
            String inputValue = JOptionPane.showInputDialog( "I'm sorry, that is not a valid email address.  Please retype it:" );
            if(inputValue==null) {
                str = _personDatum._email_address;
                break;
            } else {
                str = inputValue;
            }
        }
        addUndo( "_email_address", _personDatum._email_address, str );
        _personDatum._email_address = str;
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.EMAIL_CHANGED);
    }

    /**
     * Validate hex with regular expression
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public static boolean validateEmailString( final String hex ) {
        matcher = pattern.matcher( hex );
        return matcher.matches();
    }

    /* GETTERS
     * */

    /**
     * Retrieve a Person ObjBase from the database using their display name.
     * This will only query Persons saved to the database, not local ones.
     * @param name
     * @return
     */
    public static Person retrieveByName( String name ) {
        if ( name.length() == 0 ) {
            return null;
        }
        ClothoQuery cq = Hub.defaultConnection.createQuery( ObjType.PERSON );
        cq.eq( Person.Fields.DISPLAY_NAME, name );
        List l = cq.getResults();
        if ( l.isEmpty() ) {
            return null;
        }
        Person p = (Person) l.get( 0 );
        return p;
    }

    /**
     * It checks a password to see if it matches the user's password and returns
     * true if they match, otherwise returns false.
     * It only stores the encrypted version of the password using SHA-1 hashing.
     *
     * @param raw the raw password supplied by user
     * @return true if it's a match
     */
    public final boolean checkPassword( String raw ) {
     //   System.out.println("check password rawValue " + raw);
     //   System.out.println("check password _personDatum._password " + _personDatum._password);
        return raw.equals( _personDatum._password );
    }

    public static String SHA1( String text ) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance( "SHA-1" );
            byte[] sha1hash = new byte[ 40 ];
            md.update( text.getBytes( "iso-8859-1" ), 0, text.length() );
            sha1hash = md.digest();
            return convertToHex( sha1hash );
        } catch ( UnsupportedEncodingException ex ) {
            Exceptions.printStackTrace( ex );
            return "";
        } catch ( NoSuchAlgorithmException ex ) {
            Exceptions.printStackTrace( ex );
            return "";
        }
    }

    private static String convertToHex( byte[] data ) {
        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < data.length; i++ ) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ( (0 <= halfbyte) && (halfbyte <= 9) ) {
                    buf.append( (char) ('0' + halfbyte) );
                } else {
                    buf.append( (char) ('a' + (halfbyte - 10)) );
                }
                halfbyte = data[i] & 0x0F;
            } while ( two_halfs++ < 1 );
        }
        return buf.toString();
    }

    private boolean hasChangeClearance() {
        //If it's a new Person, changes are OK
        if(_isBrandNew) {
            return true;
        }
        Person user = Collector.getCurrentUser();
        //If nobody's logged in, they aren't allowed to change things
        if(user==null) {
            JOptionPane.showMessageDialog( null, "You aren't logged in.  You aren't allowed to change this data.", "Forbidden data change", JOptionPane.OK_OPTION );
            return false;
        }
        //If a person is editing their own fields, that's ok
        if(user.getUUID().equals(this.getUUID())) {
            return true;
        }
        //If the logged in user is an admin, they can edit fields
        if(user._personDatum._isAdministrator) {
            return true;
        }
        JOptionPane.showMessageDialog( null, "Only the user herself or admins can change this data.", "Forbidden data change", JOptionPane.OK_OPTION );
        return false;
    }
    /**
     * Get the Person's Lab ObjBase
     * @return a Lab ObjBase
     */
    public Lab getLab() {
        return Collector.getLab( _personDatum._labUUID );
    }

    /**
     * Get the person's given name
     * @return a String
     */
    public String getGivenName() {
        return _personDatum._given_name;
    }

    /**
     * Get the person's last name (surname)
     * @return a String
     */
    public String getSurName() {
        return _personDatum._surname;
    }

    /**
     * Get the name a person wishes to be called
     * @return a String
     */
    public String getNickName() {
        if ( _personDatum._nick_name==null ) {
            return "";
        } else {
            return _personDatum._nick_name;
        }
    }

    /**
     * Is the person an administrator?
     * @return true if they are
     */
    public boolean isAdmin() {
        return _personDatum._isAdministrator;
    }

    /**
     * Get the person's login name
     * @return a String
     */
    public String getDisplayName() {
        return getName();
    }

    /**
     * Get the email address of the person
     * @return a String for the email address.  This field is validated, so it will be a properly-formatted email address
     * of the "bob@bob.com" type
     */
    public String getEmailAddress() {
        return _personDatum._email_address;
    }

    /**
     * Get the paper mailing address (usually a lab address)
     * @return a longer String in several lines
     */
    public String getSnailMailAddress() {
        return _personDatum._snailmail_address;
    }

    /**
     * Get the Person's Registry login name
     * @return
     */
    public String getRegistryName() {
        return _personDatum._registry_name;
    }

    /**
     * Get the Person's lab as a UUID link
     * @return a String UUID
     */
    public String getLabUUID() {
        return _personDatum._labUUID;
    }

    /**
     * Get the WikiText ObjBase that holds the Biography of the Person
     * @return a WikiText ObjBase
     */
    public WikiText getBiography() {
        if ( _personDatum._biographyUUID != null ) {
            return Collector.getWikiText( _personDatum._biographyUUID );
        }
        System.out.println( "I'm returning a new biography" );
        WikiText biography = new WikiText( "" );
        _personDatum._biographyUUID = biography.getUUID();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.NAME_CHANGED);
        return biography;
    }

    /**
     * Get the personal Collection of this object
     * @return a Collection ObjBase
     */
    public Collection getHerCollection() {
        return Collector.getCollection( _personDatum._myCollectionID );
    }

    /**
     * Get the personal collection for this Person as a UUID link
     * @return a String UUID for the Collection ObjBase
     */
    public String getCollectionUUID() {
        return _personDatum._myCollectionID;
    }

    /**
     * This method will not exist in future versions of Clotho.  It is needed right now
     * for ClothoConnections, but don't use it in Apps.
     * @deprecated
     * @return
     */
    @Deprecated
    public String getPassword() {
        return _personDatum._password;
    }

    @Override
    public String toString() {
        return _personDatum.name;
    }

    @Override
    public ObjType getType() {
        return ObjType.PERSON;
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        String displayName = (String) objHash.get( "name" );
        String givenName = (String) objHash.get( "_given_name" );
        String surName = (String) objHash.get( "_surname" );
        String nickName = (String) objHash.get( "_nick_name" );
        String email = (String) objHash.get( "_email_address" );
        String snailmail = (String) objHash.get( "_snailmail_address" );
        String registryName = (String) objHash.get( "_registry_name" );
        String labUUID = (String) objHash.get( "_labUUID" );
        String biographyUUID = (String) objHash.get( "_biographyUUID" );
        String myCollectionID = (String) objHash.get( "_myCollectionID" );

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        PersonDatum d = new PersonDatum();

        d.uuid = uuid;
        d.name = displayName;
        d._given_name = givenName;
        d._surname = surName;
        d._nick_name = nickName;
        d._email_address = email;
        d._snailmail_address = snailmail;
        d._registry_name = registryName;
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d._labUUID = labUUID;
        d._myCollectionID = myCollectionID;
        d._biographyUUID = biographyUUID;

        Person aperson = new Person( d );


        Collection herCollection = new Collection( displayName + "'s collection", "User " + displayName + "'s personal collection of objects", aperson );
        aperson._personDatum._myCollectionID = herCollection.getUUID();

        return aperson;
    }

    @Override
    protected HashMap<String, HashMap<String, Object>> generateXml( HashMap<String, HashMap<String, Object>> allObjects ) {
        //If the hash already has the object, skip adding anything
        if ( allObjects.containsKey( getUUID() ) ) {
            return allObjects;
        }

        //Fill in the individual fields

        //NOTE: Collection intentionally omitted, it will be made fresh when imported
        HashMap<String, Object> datahash = new HashMap<String, Object>();
        datahash.put( "objType", getType().toString() );
        datahash.put( "uuid", _personDatum.uuid );
        datahash.put( "name", _personDatum.name );


        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "_given_name", _personDatum._given_name );
        datahash.put( "_surname", _personDatum._surname );
        datahash.put( "_nick_name", _personDatum._nick_name );
        datahash.put( "_email_address", _personDatum._email_address );

        datahash.put( "_snailmail_address", _personDatum._snailmail_address );
        datahash.put( "_registry_name", _personDatum._registry_name );
        datahash.put( "_labUUID", _personDatum._labUUID );

        WikiText wt = this.getBiography();
        if ( wt != null ) {
            datahash.put( "_biographyUUID", _personDatum._biographyUUID );
            allObjects = getBiography().generateXml( allObjects );
        }

        datahash.put( "_myCollectionID", _personDatum._myCollectionID );

        allObjects = getLab().generateXml( allObjects );

        //Add the HashMap to the list and return
        allObjects.put( getUUID(), datahash );

        return allObjects;
    }


    /*-----------------
    variables
    -----------------*/
    private PersonDatum _personDatum;

//Database bound fields
    public static class PersonDatum extends ObjBaseDatum {
        public String _labUUID;
        public String _given_name = "";
        public String _surname = "";
        public String _nick_name = "";
        public String _email_address = "";
        public String _snailmail_address = "";
        public String _registry_name = "";
        public String _password;
        public String _biographyUUID;
        public String _myCollectionID;
        public boolean _isAdministrator = false;

        @Override
        public ObjType getType() {
            return ObjType.PERSON;
        }
    }
//transient fields
    transient private boolean _isBrandNew = false;
    transient private boolean _isLoggedIn = false;
    transient private static Matcher matcher;
    transient private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    transient private static Pattern pattern = Pattern.compile( EMAIL_PATTERN );

    /******* FIELDS *******/
    public static enum Fields {

        DATE_CREATED,
        LAST_MODIFIED,
        NICKNAME,
        GIVEN_NAME,
        SURNAME,
        PASSWORD,
        DISPLAY_NAME,
        EMAIL_ADDRESS,
        SNAILMAIL_ADDRESS,
        REGISTRY_NAME,
        COLLECTION,
        LAB,
        BIOGRAPHY,
        ADMIN 
    }
}
