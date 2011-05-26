/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocore.api.data;

import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.dnd.RefreshEvent;
import org.clothocore.api.dnd.RefreshEvent.Condition;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.core.Hub;
import org.clothocore.util.dialog.WaitingDialog;
import org.clothocore.util.xml.XMLParser;
import org.openide.util.ImageUtilities;

public class NucSeq extends ObjBase implements Serializable {

    /**
     * Constructor from database
     * @param uuid
     * @param sequence
     * @param dateCreated
     * @param lastmodified
     * @param annots
     */
    public NucSeq( NucSeqDatum d ) {
        super( d );
        _seqDatum = d;

        if ( !initiateNucSeq( _seqDatum.theSequence ) ) {
            return;
        }
    }

    /**
     * Constructor for a NucSeq (dna sequence) object.  The sequence ends up in the
     * private variable 'theSequence'.  In constructing it, it checks whether it is
     * a legitimate RNA or DNA molecule and whether it contains degenerate codons,
     * which is stored as a boolean 'isDegenerate'.
     *
     * @param inputSeq
     * @param strandedness
     * @param circularity
     */
    public NucSeq( String inputSeq, boolean strandedness, boolean circularity ) {
        super();
        _seqDatum = new NucSeqDatum();
        _datum = _seqDatum;
        _datum.name = "nucseq";
        _datum.dateCreated = new Date();
        _datum.lastModified = new Date();
        _seqDatum.uuid = _uuid;

        _seqDatum.isSingleStranded = strandedness;
        _seqDatum.isCircular = circularity;
        lowerArray = new boolean[ inputSeq.length() ];
        if ( !initiateNucSeq( inputSeq ) ) {
            return;
        }

        createStartStopCodons();

        //NEED TO USE A DIFFERENT OBJBASE CONSTRUCTOR TO SET HASH AS UUID
        //_myUUID = generateUUIDAsHash(getSeq());
        if ( translation == null ) {
            makeTranslationHash();
        }
    }

    //alternate constuctor if circularity and strandedness isn't specified
    public NucSeq( String inputSeq ) {
        this( inputSeq, false, false );
    }

    @Override
    public ObjType getType() {
        return ObjType.NUCSEQ;
    }

    /*
     * Initialize values for start and stop codons
     */
    private void createStartStopCodons()
    {
        _startCodons = new ArrayList<String>();
        _startCodons.add("ATG");
        _startCodons.add("GTG");
        _startCodons.add("TGG");
        _startCodons.add("RTG");

        _stopCodons = new ArrayList<String>();
        _stopCodons.add("TAA");
        _stopCodons.add("TAG");
        _stopCodons.add("TGA");
        _stopCodons.add("TRA");
        _stopCodons.add("TAR");
    }

    protected static ObjBase importFromHashMap( String uuid, HashMap<String, Object> objHash ) {
        System.out.println( "working on " + uuid );
        String name = (String) objHash.get( "name" );
        String sequence = (String) objHash.get( "sequence" );
        boolean locked = Boolean.parseBoolean( (String) objHash.get( "isLocked" ) );

        //Pull all the annotations and add them to the list
        @SuppressWarnings (value="unchecked")
        ArrayList<String> arrannot = (ArrayList<String>) objHash.get( "annotations" );
        HashSet<String> annots = new HashSet<String>();
        if ( arrannot != null ) {
            for ( String s : arrannot ) {
                annots.add( s );
            }
        }

        Date dateCreated = getDateFromString( (String) objHash.get( "_dateCreated" ) );
        Date lastModified = getDateFromString( (String) objHash.get( "_lastModified" ) );

        NucSeqDatum d = new NucSeqDatum();

        d.uuid = uuid;
        d.name = "sequence";
        d.dateCreated = dateCreated;
        d.lastModified = lastModified;
        d.isLocked =  locked;
        d.annotations = annots;


        return new NucSeq( d );
    }

    @Override
    protected HashMap<String, HashMap<String, Object>> generateXml( HashMap<String, HashMap<String, Object>> allObjects ) {

        //If the hash already has the object, skip adding anything
        if ( allObjects.containsKey( getUUID() ) ) {
            return allObjects;
        }

        //Fill in the individual fields
        HashMap<String, Object> datahash = new HashMap<String, Object>();
        datahash.put( "objType", getType().toString() );
        datahash.put( "uuid", _seqDatum.uuid );
        datahash.put( "name", _seqDatum.name );
        datahash.put( "_dateCreated", getDateCreatedAsString() );
        datahash.put( "_lastModified", getLastModifiedAsString() );

        datahash.put( "sequence", _seqDatum.theSequence );

        datahash.put( "annotations", _seqDatum.annotations );

        for ( String s : _seqDatum.annotations ) {
            allObjects = Collector.getAnnotation( s ).generateXml( allObjects );
        }

        //Add the HashMap to the list and return
        allObjects.put( getUUID(), datahash );

        return allObjects;
    }

    public boolean initiateNucSeq( String inputSeq ) {
        if(inputSeq == null) {
            _seqDatum.theSequence = "!";
            setTransient();
            return false;
        }

        char currentchar;
        StringBuffer seq = new StringBuffer();
        lowerArray = new boolean[ inputSeq.length() ];

        //Check whether this is an RNA, DNA, or a bad seq, and if is degenerate:
        loopy:
        for ( int i = 0; i < inputSeq.length(); i++ ) {
            currentchar = inputSeq.charAt( i );
            char upperChar = Character.toUpperCase( currentchar );

            //Put the case in a format array
            if ( currentchar == upperChar ) {
                lowerArray[i] = false;
            } else {
                lowerArray[i] = true;
            }

            //Build up the new format-free sequence
            seq.append( upperChar );

            switcheroo:
            switch ( upperChar ) {
                case '.':
                    _seqDatum.isLinear = true;
                    break;
                case 'A':
                    break;
                case 'C':
                    break;
                case 'G':
                    break;
                case 'T':
                    break;
                case 'B':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'D':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'H':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'K':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'M':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'N':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'R':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'S':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'V':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'W':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'Y':
                    _seqDatum.isDegenerate = true;
                    break;
                case 'U':
                    _seqDatum.isRNA = true;
                    break;
                default:
                    System.out.println( "Nucseq had to break on " + upperChar );
                    _seqDatum.theSequence = "!";
                    setTransient();
                    return false;
            }
        }
        _seqDatum.theSequence = seq.toString();
        return true;
    }


     /**
     * Finds indices of Open Reading Frames in a given nucleotide sequence and
     * returns them as HashMap with start indices for keys and end indices for
     * values.
     *
     * @param s String to check for ORFs
     * @param forward Boolean, set to 'true' for finding forward reading frames
     *          or to 'false' to find ORFs in the reverse complement
     */
    @SuppressWarnings (value="unchecked")
    public HashMap findORFs(boolean forward, boolean multipleStartCodons) {
        String seq = _seqDatum.theSequence;
        int len = seqLength();
        HashMap orfs = new HashMap();
        if (isCircular()) {
            seq = seq.concat(seq);
        }

        Pattern pattern = Pattern.compile(makeORFRegExp(multipleStartCodons, isDegenerate()), Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        if (forward) {
            matcher = pattern.matcher(seq);
        }
        else {
            matcher = pattern.matcher(revComp());
        }

        int end;
        int start;
        int pos = 0;
        while (matcher.find(pos)) {
            start = matcher.start();
            end = matcher.end();

            if (end > len) {
                end = end - len;
            }

            if (!(start >= len || matcher.group().length() > len)) {
                if (forward) {
                    orfs.put(start, end);
                }
                else {
                    orfs.put(len - start, len - end);
                }
            }

            pos = matcher.start() + 3;
        }

        return orfs;
    }

        /**
     * Returns a regular expression for finding Open Reading Frames using
     * data from the codon table
     */
    private String makeORFRegExp(boolean msc, boolean allowDegen) {
        String regexp = "(";
        if (msc) {
            for (int i = 0; i < _startCodons.size(); i++) {
                if (i + 1 < _startCodons.size())
                    regexp = regexp + seqToRegExp(_startCodons.get(i), allowDegen) + "|";
                else
                    regexp = regexp + seqToRegExp(_startCodons.get(i), allowDegen) + ")";
            }
        }
        else {
            regexp = regexp + seqToRegExp(_startCodons.get(0), allowDegen) + ")";
        }
        regexp = regexp + "(...)*?(";
        for (int i = 0; i < _stopCodons.size(); i++) {
            if (i + 1 < _stopCodons.size())
                regexp = regexp + seqToRegExp(_stopCodons.get(i), allowDegen) + "|";
            else
                regexp = regexp + seqToRegExp(_stopCodons.get(i), allowDegen) + ")";
        }
        return regexp;
    }

    /**
     * Takes a sequence and transforms it into a regular expression for
     * searches.
     *
     * @param s String containing the sequence
     */
    private String seqToRegExp(String seq, boolean degen) {
        String regexp = "";
        String c;
        String prefix = "";
        String suffix = "";
        int rootstart = 0;
        int rootend = seq.length();
        if (seq.indexOf("<") != -1   ||   seq.indexOf(">") != -1) {
            for (int i = 0; i < seq.length(); i++) {
                c = seq.substring(i,i+1);
                if (c.equalsIgnoreCase("<")) {
                    Pattern pattern = Pattern.compile(".", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(regexp);
                    while (matcher.find()) {
                        prefix = "(" + prefix + matcher.group() + ")?";
                    }
                    rootstart = i+1;
                }
                else if (c.equalsIgnoreCase(">")) {
                    Pattern pattern = Pattern.compile(".", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(seq.replaceFirst(regexp,""));
                    int counter = 0;
                    matcher.find();
                    while (matcher.find()) {
                        suffix = suffix + "(" + matcher.group();
                        counter++;
                    }
                    for (int j=0; j<counter; j++) {
                        suffix = suffix + ")?";
                    }
                    rootend = i;
                }
                else {
                    regexp = regexp + c;
                }
            }
        }

        //System.out.println("Is");

        regexp = prefix + seq.substring(rootstart, rootend) + suffix;

        if (degen) {
            //The following block strings the regex and protects with @ symbol
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[aA][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[aA]", "[A@D@H@M@N@R@V@W]");
            //System.out.println("here?C " + regexp.length());
            // (regexp.matches("[a-z[A-Z]\\[\\]]*\\]?(?<!@)[cC]\\[?[a-z[A-Z]\\[\\]]*"))
                regexp = regexp.replaceAll("(?<!@)[cC]", "[@BC@H@M@N@S@V@Y]");
            //System.out.println("here?G " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]\\]]*(?<!@)[gG][a-z[A-Z]\\[]*"))
                regexp = regexp.replaceAll("(?<!@)[gG]", "[@B@DG@K@N@R@S@V]");
            //System.out.println("here?T " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]\\]]*(?<!@)[tT][a-z[A-Z]\\[]*"))
                regexp = regexp.replaceAll("(?<!@)[tT]", "[@B@D@H@K@NT@W@Y]");
            //System.out.println("here?U " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[uU][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[uU]", "[@B@D@H@K@NU@W@Y]");
            //System.out.println("here?B " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[bB][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[bB]", "[BC@DG@H@K@M@N@R@STU@V@W@Y]");
            //System.out.println("here?D " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[dD][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[dD]", "[ABDG@H@K@M@N@R@STU@V@W@Y]");
            //System.out.println("here?H " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[hH][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[hH]", "[ABCDH@K@M@N@R@STU@V@W@Y]");
            //System.out.println("here?K " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[kK][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[kK]", "[BDGHK@N@R@STU@V@W@Y]");
            //System.out.println("here?M " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[mM][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[mM]", "[ABCDHM@N@R@S@V@W@Y]");
            //System.out.println("here?N " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[nN][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[nN]", "[ABCDGHKMN@R@STU@V@W@Y]");
            //System.out.println("here?R " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[rR][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[rR]", "[ABDGHKMNR@S@V@W]");
            //System.out.println("here?S " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[sS][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[sS]", "[BCDGHKMNRS@V@Y]");
            //System.out.println("here?V " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[vV][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[vV]", "[ABCDGHKMNRSV@W@Y]");
            //System.out.println("here?W " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[wW][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[wW]", "[ABDHKMNRTUVW@Y]");
            //System.out.println("here?Y " + regexp.length());
            //if (regexp.matches("[a-z[A-Z]]*(?<!@)[yY][a-z[A-Z]]*"))
                regexp = regexp.replaceAll("(?<!@)[yY]", "[BCDHKMNSTUVWY]");
        }
        else {
            regexp = regexp.replaceAll("(?<!@)[bB]", "[CGTU]");
            regexp = regexp.replaceAll("(?<!@)[dD]", "[AGTU]");
            regexp = regexp.replaceAll("(?<!@)[hH]", "[ACTU]");
            regexp = regexp.replaceAll("(?<!@)[kK]", "[GTU]");
            regexp = regexp.replaceAll("(?<!@)[mM]", "[AC]");
            regexp = regexp.replaceAll("(?<!@)[nN]", "[ACGTU]");
            regexp = regexp.replaceAll("(?<!@)[rR]", "[AG]");
            regexp = regexp.replaceAll("(?<!@)[sS]", "[CG]");
            regexp = regexp.replaceAll("(?<!@)[vV]", "[ACG]");
            regexp = regexp.replaceAll("(?<!@)[wW]", "[ATU]");
            regexp = regexp.replaceAll("(?<!@)[yY]", "[CTU]");
            degen = true;
        }

        //System.out.println("here?# " + regexp.length());
        if (regexp.indexOf("#") != -1)
            regexp = regexp.replaceAll("#", "[ABCDGHKMNRSTUVWY]*?");

        //Unprotect
        regexp = regexp.replaceAll("@", "");

        //System.out.println("This Heap Space?");

        return regexp;
    }


     /**
     * Determines the GC content of a string of nucleotides, returns a double
     * in the range from 0 to 1
     *
     */
    public double[] gcContent() {
        String seq = _seqDatum.theSequence;
        seq = seq.toUpperCase();
        int len = seqLength();
        double gcMin = 0;
        double gcMax = 0;
        double[] gc = new double[2];
        for (int i = 0; i < len; i++) {
            String n = seq.substring(i, i + 1);
            if (n.matches("[CGS]")) {
                gcMin++;
                gcMax++;
            }
            else if (n.matches("[RYMKBDHVN]")) {
                gcMax++;
            }
        }
        gcMin = gcMin / len;
        gcMax = gcMax / len;

        gc[0] = gcMin;
        gc[1] = gcMax;
        return gc;
    }


    /**
     * Determines the approximate melting point (Celsius) of a sequence of DNA
     * using a Nearest-Neighbor method, assuming 1.0 M [NaCl] and
     * 50 nM [primer].
     *
     */
    public double meltingTemp () {
        
        /* Resources:
         * http://en.wikipedia.org/wiki/DNA_melting#Nearest-neighbor_method
         * http://www.basic.northwestern.edu/biotools/oligocalc.html
         * http://dna.bio.puc.cl/cardex/servers/dnaMATE/tm-pred.html
         */

        String seq = _seqDatum.theSequence;
        int len = seqLength();
        double concP = 50 * java.lang.Math.pow(10, -9);
        double dH = 0;
        double dS = 0;
        double logCt = 0;
        double R = 1.987;
        double temp;
        String pair;
        seq = seq.toUpperCase();

        // Checks terminal base pairs
        char init = seq.charAt(0);
        if (init == 'G' || init == 'C') {
            dH += 0.1;
            dS += -2.8;
        }
        else if (init == 'A' || init == 'T') {
            dH += 2.3;
            dS += 4.1;
        }
        init = seq.charAt(len - 1);
        if (init == 'G' || init == 'C') {
            dH += 0.1;
            dS += -2.8;
        }
        else if (init == 'A' || init == 'T') {
            dH += 2.3;
            dS += 4.1;
        }

        // Checks nearest neighbor pairs
        for (int i = 0; i < len - 1; i++) {
            pair = seq.substring(i,i+2);
            if (pair.equals("AA") || pair.equals("TT")) {
                dH += -7.9;
                dS += -22.2;
            }
            else if (pair.equals("AG") || pair.equals("CT")) {
                dH += -7.8;
                dS += -21.0;
            }
            else if (pair.equals("AT")) {
                dH += -7.2;
                dS += -20.4;
            }
            else if (pair.equals("AC") || pair.equals("GT") ) {
                dH += -8.4;
                dS += -22.4;
            }
            else if (pair.equals("GA") || pair.equals("TC")) {
                dH += -8.2;
                dS += -22.2;
            }
            else if (pair.equals("GG") || pair.equals("CC")) {
                dH += -8.0;
                dS += -19.9;
            }
            else if (pair.equals("GC")) {
                dH += -9.8;
                dS += -24.4;
            }
            else if (pair.equals("TA")) {
                dH += -7.2;
                dS += -21.3;
            }
            else if (pair.equals("TG") || pair.equals("CA")) {
                dH += -8.5;
                dS += -22.7;
            }
            else if (pair.equals("CG") ) {
                dH += -10.6;
                dS += -27.2;
            }
        }

        // Checks for self-complementarity
        int mid;
        if (len % 2 == 0) {
            mid = len / 2;
            if (seq.substring(0, mid).equals(new NucSeq(seq.substring(mid,len)).revComp())) {
                dS += -1.4;
            }
        }
        else {
            mid = (len - 1) / 2;
            if (seq.substring(0, mid).equals(new NucSeq(seq.substring(mid + 1,len)).revComp())) {
                dS += -1.4;
            }
        }

        // dH is in kCal, dS is in Cal - equilibrating units
        dH = dH * 1000;

        // logCt = java.lang.Math.log(1 / concP);
        logCt = java.lang.Math.log(concP);

        temp = (dH / (dS + (R * logCt))) - 273.15;

        //return temp;
        return temp;
    }


    /**
     * Reverse complement this NucSeq.  The case will be saved with this
     * operation, and the annotations will be repositioned.
     */
    public void revCompThis() {
        char currentchar;
        StringBuffer seq = new StringBuffer();
        boolean[] newLower = new boolean[ _seqDatum.theSequence.length() ];

        for ( int x = (_seqDatum.theSequence.length() - 1); x >= 0; x-- ) {
            currentchar = _seqDatum.theSequence.charAt( x );
            char appendChar = ' ';
            switch ( currentchar ) {   // (Assume N is an integer variable.)
                case 'A':
                    if ( _seqDatum.isRNA ) {
                        appendChar = 'U';
                    } else {
                        appendChar = 'T';
                    }
                    break;
                case 'T':
                    appendChar = 'A';
                    break;
                case 'C':
                    appendChar = 'G';
                    break;
                case 'G':
                    appendChar = 'C';
                    break;
                case '&':
                    appendChar = '&';
                    break;
                case 'R':
                    appendChar = 'Y';
                    break;
                case 'Y':
                    appendChar = 'R';
                    break;
                case 'M':
                    appendChar = 'K';
                    break;
                case 'K':
                    appendChar = 'M';
                    break;
                case 'W':
                    appendChar = 'W';
                    break;
                case 'S':
                    appendChar = 'S';
                    break;
                case 'B':
                    appendChar = 'V';
                    break;
                case 'D':
                    appendChar = 'H';
                    break;
                case 'H':
                    appendChar = 'D';
                    break;
                case 'V':
                    appendChar = 'B';
                    break;
                case 'N':
                    appendChar = 'N';
                    break;
                case 'U':
                    appendChar = 'A';
                    break;
                default:
                    break;
            }
            seq.append( appendChar );
            if ( lowerArray[x] ) {
                appendChar = Character.toLowerCase( appendChar );
                newLower[_seqDatum.theSequence.length() - x - 1] = true;
            } else {
                newLower[_seqDatum.theSequence.length() - x - 1] = false;
            }
        }

        //update the sequence
        lowerArray = newLower;
        if(changeSeq(seq.toString())) {
            //Invert all annotations
            for ( String s : _seqDatum.annotations ) {
                Annotation an = Collector.getAnnotation( s );
                an.invert( seq.length() );
            }
        }

    }

    /**
     * Subroutine revComp returns the reverse complement of theSequence,
     * in all uppercase as a String.  To actually reverse complement this
     * NucSeq, and also invert its annotations, use revCompThis instead.
     *
     * @return a String that is the reverse complement
     */
    public String revComp() {
        StringBuffer seq = new StringBuffer();
        char currentchar;

        for ( int x = (_seqDatum.theSequence.length() - 1); x >= 0; x-- ) {
            currentchar = _seqDatum.theSequence.charAt( x );
            char outchar = ' ';
            switch ( currentchar ) {   // (Assume N is an integer variable.)
                case 'A':
                    if ( _seqDatum.isRNA ) {
                        outchar = 'U';
                    } else {
                        outchar = 'T';
                    }
                    break;
                case 'T':
                    outchar = 'A';
                    break;
                case 'C':
                    outchar = 'G';
                    break;
                case 'G':
                    outchar = 'C';
                    break;
                case '&':
                    outchar = '&';
                    break;
                case 'R':
                    outchar = 'Y';
                    break;
                case 'Y':
                    outchar = 'R';
                    break;
                case 'M':
                    outchar = 'K';
                    break;
                case 'K':
                    outchar = 'M';
                    break;
                case 'W':
                    outchar = 'W';
                    break;
                case 'S':
                    outchar = 'S';
                    break;
                case 'B':
                    outchar = 'V';
                    break;
                case 'D':
                    outchar = 'H';
                    break;
                case 'H':
                    outchar = 'D';
                    break;
                case 'V':
                    outchar = 'B';
                    break;
                case 'N':
                    outchar = 'N';
                    break;
                case 'U':
                    outchar = 'A';
                    break;
                default:
                    break;
            }
            seq.append( outchar );
        }  // end for loop
        return seq.toString();
    }

    /**
     * Get the NucSeq in Genbank format from the user currently logged in
     * @return a String in Genbank format
     */
    public String getGenbank() {
        return getGenbank( new Person[]{ Collector.getCurrentUser() } );
    }

    /**
     * Get the NucSeq in Genbank format with annotations from a specific
     * list of users
     * @param users
     * @return a String in Genbank format
     */
    public String getGenbank( Person[] users ) {
        return "not yet implemented";
    }

    /**
     * Annotate the NucSeq from the complete database worth of features
     * If Person is null, uses the current user from Collector
     *
     * The Person is used as the author of the Annotation
     */
    public void autoAnnotate( Person user) {
        System.out.println( "I'm autoannotating your nucSeq from all database features" );
        autoAnnotate( null, user, false );
    }

    /**
     * Annotate the NucSeq from the features in a particular Collection
     * If Person is null, uses the current user from Collector
     *
     * The Person is used as the author of the Annotation
     * @param col
     */
    public void autoAnnotate( Collection col, Person user ) {
        HashSet<String> allfeatures = col.getAllLinksOf( ObjType.FEATURE );
        System.out.println( "Autoannotating with all features from a particular collection:" );
        for ( String s : allfeatures ) {
            System.out.println( "autoannotate with " + s );
        }
        autoAnnotate( allfeatures, user, true );
    }

    /**
     * Relay for other two autoAnnotate calls, but can also use this in Apps directly.
     * For using all features in the database, set constrainTo == false.
     *
     * To search a particularly Collection,
     * call autoAnnotate ( Collection col, Person user ).  To search all collections of
     * a particular user call autoAnnotate ( Person user )
     *
     * The Person is used as the author of the Annotation
     *
     * @param onlyFeatures the list of Feature UUIDs requested for autoannotation
     * @param user  the Person to be set as author of the Annotation
     * @param constrainTo true if should constrain annotations to supplied list, otherwise false
     */
    public void autoAnnotate( HashSet<String> onlyFeatures, Person user, Boolean constrainTo ) {
        if ( !featuresInitiated ) {
            initiateFeatureTable();
        }

        String revcomp = this.revComp();

        for ( int i = 0; i < featureTable.length; i++ ) {
            if(constrainTo) {
                if ( onlyFeatures != null ) {
                    if ( !onlyFeatures.contains( featureTable[i][0] ) ) {
                        System.out.println( featureTable[i][0] + " is not requested" );
                        continue;
                    }
                }
            }

            try {
                testFeature(featureTable[i][2], featureTable[i][0], user, revcomp );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Iterated method that compares one feature sequence to the target
     *
     * @param teststring
     * @param uuid
     * @param user
     * @param revcomp
     */
    private void testFeature(String teststring, String uuid, Person user, String revcomp ) {
        Pattern p = Pattern.compile(teststring);

        //Check Feature exact matches in forward orientation
        String[] text = {_seqDatum.theSequence};
        for (int i = 0; i < text.length; i++) {
            Matcher matcher = p.matcher(text[i]);
            while (matcher.find()) {
                System.out.println( "start=" + matcher.start() + " end = " + matcher.end());
                Feature f = Collector.getFeature( uuid );
                if(f==null || f.isDeleted()) {
                    return;
                }
                int start = matcher.start();
                int end = matcher.end();
                if(f.isCDS()) {
                    try {
                        //For CDS features, if the 5' sequences is a start codon, include that in annotation
                        String fiveprime = text[i].substring(start-3, start);
                        System.out.println("fiveprime is " + fiveprime);
                        if(fiveprime.equals("ATG") || fiveprime.equals("TTG") || fiveprime.equals("GTG")) {
                            start = start-3;
                        }
                    } catch(Exception e) {
                    }
                    try {
                        //For CDS features, if the 3' sequences is a stop codon, include that in annotation
                        String threeprime = text[i].substring(end, end+3);
                        System.out.println("threeprime is " + threeprime);
                        if(threeprime.equals("TAA") || threeprime.equals("TGA") || threeprime.equals("TAG")) {
                            end = end+3;
                        }
                    } catch(Exception e) {
                    }
                }
                Annotation annot = new Annotation( f, this, null, null, start, end, user, true, null );
                System.out.println( "I found a forward annotation at " + start );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.ANNOTATION_TO_NUCSEQ);
            }

        }

        //Check it as reverse complement
        String[] text2 = {revcomp};
        for (int i = 0; i < text.length; i++) {
            Matcher matcher = p.matcher(text2[i]);
            while (matcher.find()) {
                System.out.println( "start=" + matcher.start() + " end = " + matcher.end());
                Feature f = Collector.getFeature( uuid );
                if(f==null || f.isDeleted()) {
                    return;
                }
                int index = _seqDatum.theSequence.length() - matcher.start();
                int start = index - teststring.length();
                int end = index;
                if(f.isCDS()) {
                    try {
                        //For CDS features, if the 5' sequences is a an RC stop codon, include it
                        String fiveprime = text[i].substring(start-3, start);
                        System.out.println("fiveprime is " + fiveprime);
                        if(fiveprime.equals("TTA") || fiveprime.equals("TCA") || fiveprime.equals("CTA")) {
                            start = start-3;
                        }
                    } catch(Exception e) {
                    }
                    try {
                        //For CDS features, if the 3' sequences is a an RC start codon, include that in annotation
                        String threeprime = text[i].substring(end, end+3);
                        System.out.println("threeprime is " + threeprime);
                        if(threeprime.equals("CAT") || threeprime.equals("CAA") || threeprime.equals("CAC")) {
                            end = end+3;
                        }
                    } catch(Exception e) {
                    }
                }
                Annotation annot = new Annotation( f, this, null, null, start, end, user, false, null );
                System.out.println( "I found a reverse annotation at " + start );
                setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.ANNOTATION_TO_NUCSEQ);
            }
        }
    }

    public void removeAnnotations() {
        for ( String s : _seqDatum.annotations ) {
            _seqDatum.removeAnnotations.add( s );
        }
        _seqDatum.annotations = new HashSet<String>();
        setChanged(org.clothocore.api.dnd.RefreshEvent.Condition.ANNOTATION_TO_NUCSEQ);
    }

    /**
     * Recursively save all child elements and then call ObjBase to save itself.
     */
    @Override
    public synchronized boolean save( ClothoConnection conn ) {
        System.out.println( "============ Starting nucseq save of " + getUUID() + " size " + _seqDatum.annotations.size() );
        if ( !isChanged() ) {
            System.out.println( "nucseq didn't require saving" );
            return true;
        }
        
        //Temporarily hide the annotations
        HashSet<String> tempannots = _seqDatum.annotations;
        _seqDatum.annotations = new HashSet<String>();

        //Save the sequences without the annotations
        if(!super.save( conn )) {
            return false;
        }

        //Re-add the annotations and redo the saves
        _seqDatum.annotations = tempannots;

        for ( String s : _seqDatum.annotations ) {
            Annotation att = Collector.getAnnotation( s );
            System.out.println( "nucseq save saving annotation " + att.getUUID() );
            if ( att == null ) {
                return false;
            }
            if ( !att.save( conn ) ) {
                return false;
            }
        }

        for ( String s : _seqDatum.removeAnnotations ) {
            Annotation att = Collector.getAnnotation( s );
            System.out.println( "nucseq save deleting annotation " + att.getUUID() );
            if ( att != null ) {
                if ( !att.deleteFromDatabase() ) {
                    return false;
                }
            }

            _seqDatum.removeAnnotations = new HashSet<String>();
        }
        return super.save( conn );
    }

    @Override
    public boolean addObject( ObjBase dropObject ) {
        return false;
    }

    void setLocked( boolean isit ) {
        _seqDatum.isLocked = isit;
    }

    /**
     * Add a user-defined non-Feature Annotation
     * @param uuid
     */
    void addAnnotationLink( String uuid ) {
        _seqDatum.annotations.add( uuid );
        System.out.println("NucSeq " + this.getUUID() + " added: " + uuid);
        setChanged(Condition.ANNOTATION_TO_NUCSEQ);
    }

    public ArrayList<Integer> find( NucSeq seq ) {
        ArrayList<Integer> out = new ArrayList<Integer>();
        String testSeq = seq._seqDatum.theSequence;

        int start = 0;
        searchforward:
        while ( true ) {
            String test = _seqDatum.theSequence.substring( start );
            System.out.println( test.substring( 0, 100 ) );
            int b = test.indexOf( testSeq );
            if ( b > 0 ) {
                out.add( b + start );
                System.out.println( "for adding: " + b );
                start += b;
                start += 1;
                System.out.println( "New start: " + start );
            } else {
                break searchforward;
            }
        }

        testSeq = seq.revComp();
        start = 0;
        searchreverse:
        while ( true ) {
            String test = _seqDatum.theSequence.substring( start );
            System.out.println( test.substring( 0, 100 ) );
            int b = test.indexOf( testSeq );
            if ( b > 0 ) {
                out.add( b + start );
                System.out.println( "for adding: " + b );
                start += b;
                start += 1;
                System.out.println( "New start: " + start );
            } else {
                break searchreverse;
            }
        }
        if ( out.size() == 0 ) {
            System.out.println( "I didn't find any" );
        }
        return out;
    }

    public String translate( int frame ) {
        return translate( frame, _seqDatum.theSequence.length() );
    }

    public String translate( int start, int end ) {
        int extra = (end - start) % 3;
        if ( extra % 3 > 0 ) {
            System.out.println( "You gave me an invalid translation query: " + _seqDatum.theSequence.substring( start, end ) );
            return "*";
        }
        String seq = _seqDatum.theSequence.substring( start, end );

        int value;
        int i = 0;
        String acodon = "";
        String outSeq = "";
        while ( i < seq.length() ) {
            acodon = seq.substring( i, i + 3 );
            if ( translation.containsKey( acodon ) ) {
                int anum = translation.get( acodon );
                outSeq += (char) anum;
            } else {
                outSeq += "?";
            }
            i = i + 3;
        }

        return outSeq;
    }

    public char getCharAt( int i ) {
        return _seqDatum.theSequence.charAt( i );
    }

    public int seqLength() {
        return _seqDatum.theSequence.length();
    }

    public boolean isDegenerate() {
        return _seqDatum.isDegenerate;
    }

    public boolean isRNA() {
        return _seqDatum.isRNA;
    }

    public boolean isLocked() {
        return _seqDatum.isLocked;
    }

    public boolean isCircular() {
        return _seqDatum.isCircular;
    }

    public boolean isSingleStranded() {
        return _seqDatum.isSingleStranded;
    }

    public HashSet<Annotation> getAnnotations() {
        HashSet<Annotation> out = new HashSet<Annotation>();
        for ( String s : _seqDatum.annotations ) {
            System.out.println("retrieving an annoation link: " + s);
            out.add( Collector.getAnnotation( s ) );
        }
        return out;
    }

    public HashSet<String> getAnnotationLinks() {
        return this._seqDatum.annotations;
    }

    /**
     * Returns the user-formatted version of the String
     * @return
     */
    @Override
    public String toString() {
        StringBuffer seq = new StringBuffer();
        for ( int i = 0; i < _seqDatum.theSequence.length(); i++ ) {
            char letter = _seqDatum.theSequence.charAt( i );
            if ( lowerArray[i] ) {
                letter = Character.toLowerCase( letter );
            }
            seq.append( letter );
        }
        return seq.toString();
    }

    /**
     * Returns the unformatted all-caps string, used
     * for bioinformaticcy treatment
     */
    public String getSeq() {
        return _seqDatum.theSequence;
    }

    /**
     * Returns the unformatted all-caps string with all
     * degeneracy positions replaced by regex
     * @return
     */
    public String getMatcher() {
        String out = _seqDatum.theSequence;
        out.replaceAll("N", ".");
        out.replaceAll("R", ".");
        out.replaceAll("K", ".");
        out.replaceAll("S", ".");
        return out;
    }

    /**
     * Change the sequence of this NucSeq.  Parts, Vectors,
     * features, and oligos "lock" their NucSeq...you must
     * call changeSeq from the part, vector, Feature, or oligo
     * to change their sequence.
     *
     * @param newseq
     */
    public boolean changeSeq( String newseq ) {
        if ( _seqDatum.isLocked ) {
            return false;
        }
        //ADD UNDO HERE FOR THESEQUENCE AND ANNOTATIONS, THEN CLEAR ANNOTATIONS
        return APIchangeSeq( newseq );
    }

    boolean APIchangeSeq( String newseq ) {
        if(initiateNucSeq( newseq )) {
            setChanged(Condition.SEQUENCE_CHANGED);
            return true;
        } else {
            fireData(new RefreshEvent(this, Condition.SEQUENCE_CHANGED));
            return false;
        }
        
    }

    static {
        makeTranslationHash();
    }

    private static void makeTranslationHash() {
        translation = new Hashtable<String, Integer>();
        translation.put( "TTT", new Integer( 70 ) );
        translation.put( "TTC", new Integer( 70 ) );
        translation.put( "TTA", new Integer( 76 ) );
        translation.put( "TTG", new Integer( 76 ) );
        translation.put( "CTT", new Integer( 76 ) );
        translation.put( "CTC", new Integer( 76 ) );
        translation.put( "CTA", new Integer( 76 ) );
        translation.put( "CTG", new Integer( 76 ) );
        translation.put( "ATT", new Integer( 73 ) );
        translation.put( "ATC", new Integer( 73 ) );
        translation.put( "ATA", new Integer( 73 ) );
        translation.put( "ATG", new Integer( 77 ) );
        translation.put( "GTT", new Integer( 86 ) );
        translation.put( "GTC", new Integer( 86 ) );
        translation.put( "GTA", new Integer( 86 ) );
        translation.put( "GTG", new Integer( 86 ) );
        translation.put( "TCT", new Integer( 83 ) );
        translation.put( "TCC", new Integer( 83 ) );
        translation.put( "TCA", new Integer( 83 ) );
        translation.put( "TCG", new Integer( 83 ) );
        translation.put( "CCT", new Integer( 80 ) );
        translation.put( "CCC", new Integer( 80 ) );
        translation.put( "CCA", new Integer( 80 ) );
        translation.put( "CCG", new Integer( 80 ) );
        translation.put( "ACT", new Integer( 84 ) );
        translation.put( "ACC", new Integer( 84 ) );
        translation.put( "ACA", new Integer( 84 ) );
        translation.put( "ACG", new Integer( 84 ) );
        translation.put( "GCT", new Integer( 65 ) );
        translation.put( "GCC", new Integer( 65 ) );
        translation.put( "GCA", new Integer( 65 ) );
        translation.put( "GCG", new Integer( 65 ) );
        translation.put( "TAT", new Integer( 89 ) );
        translation.put( "TAC", new Integer( 89 ) );
        translation.put( "TAA", new Integer( 42 ) );
        translation.put( "TAG", new Integer( 42 ) );
        translation.put( "CAT", new Integer( 72 ) );
        translation.put( "CAC", new Integer( 72 ) );
        translation.put( "CAA", new Integer( 81 ) );
        translation.put( "CAG", new Integer( 81 ) );
        translation.put( "AAT", new Integer( 78 ) );
        translation.put( "AAC", new Integer( 78 ) );
        translation.put( "AAA", new Integer( 75 ) );
        translation.put( "AAG", new Integer( 75 ) );
        translation.put( "GAT", new Integer( 68 ) );
        translation.put( "GAC", new Integer( 68 ) );
        translation.put( "GAA", new Integer( 69 ) );
        translation.put( "GAG", new Integer( 69 ) );
        translation.put( "TGT", new Integer( 67 ) );
        translation.put( "TGC", new Integer( 67 ) );
        translation.put( "TGA", new Integer( 42 ) );
        translation.put( "TGG", new Integer( 87 ) );
        translation.put( "CGT", new Integer( 82 ) );
        translation.put( "CGC", new Integer( 82 ) );
        translation.put( "CGA", new Integer( 82 ) );
        translation.put( "CGG", new Integer( 82 ) );
        translation.put( "AGT", new Integer( 83 ) );
        translation.put( "AGC", new Integer( 83 ) );
        translation.put( "AGA", new Integer( 82 ) );
        translation.put( "AGG", new Integer( 82 ) );
        translation.put( "GGT", new Integer( 71 ) );
        translation.put( "GGC", new Integer( 71 ) );
        translation.put( "GGA", new Integer( 71 ) );
        translation.put( "GGG", new Integer( 71 ) );
    }

    public static void refreshFeatureTable() {
        generateFeatureTable( false, true );
    }

    public static void initiateFeatureTable() {
        generateFeatureTable( true, false );
    }

    private static void generateFeatureTable( boolean init, boolean backgroundMode ) {
        if ( initiating ) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog = new WaitingDialog( new javax.swing.JFrame(), "Clotho is pulling down all features in preparation for autoannotation, this will take a moment.  Hold on.", true );
                }
            });
        }
        initiating = true;
        if ( init ) {
            if ( featuresInitiated ) {
                return;
            }
        }

        if ( !backgroundMode ) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog = new WaitingDialog( new javax.swing.JFrame(), "Clotho is pulling down all features in preparation for autoannotation, this will take a moment.  Hold on.", true );
                }
            });
        }
        featureTable = Hub.defaultConnection.getTableAsArray( ObjType.FEATURE );
        for ( int i = 0; i < featureTable.length; i++ ) {
            try {
            String astring = featureTable[i][2];
            String string2 = astring.toUpperCase();
            featureTable[i][2] = string2.replaceAll("N", ".");
            } catch(Exception e) {
                featureTable[i][2] = "XXXXXXXXXXXXXXXXXX";
            }
        }
        featuresInitiated = true;
        if ( dialog != null ) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog.dispose();
                }
            });
        }
        initiating = false;
    }

    /**
     * Relayed from feature constructors to add the local memory feature to the table of autoannotations
     * @param afeature
     */
    static void addFeatureToTable(Feature afeature) {
        if(afeature==null) {
            return;
        }

        //Transfer old data into new table with one more row
        int newsize = featureTable.length +1;
        String[][] newtable = new String[newsize][5];
        for ( int i = 0; i < featureTable.length; i++ ) {
            newtable[i][2] = featureTable[i][2];
        }

        //Add the data to the new row
        String seq = afeature.getSeq().getSeq();
        featureTable[featureTable.length][2] = seq.replaceAll("N", ".");
    }

    /**
     * This is the general method called to perform all biofafety checks
     * on a DNA sequence.  Called from part, vector, and Feature factory
     * methods.
     *
     * @return the biosafety level of this NucSeq
     */
    Short performBiosafetyCheck() {
       System.out.println( "performBiosafetyCheck triggered" );
       short rg = -1;
       rg = getBSLfromServer();
       //If it's RG3+, show a special message
       if ( rg == 4 ) {
           ImageIcon bslicon = ImageUtilities.loadImageIcon( "org/clothocore/images/BIOHAZARD.png", false );
           JOptionPane.showMessageDialog( null,
                                          "You have executed a risk group check on a sequence that came back\n"
                   + "as Risk Group 4.\nSuch materials could be highly dangerous!\n"
                   + "You should examine your design closer before proceeding.",
                                          "Risk Group 4 material!",
                                          JOptionPane.INFORMATION_MESSAGE,
                                          bslicon );
       }
       if ( rg == 5 ) {
           ImageIcon bslicon = ImageUtilities.loadImageIcon( "org/clothocore/images/BIOHAZARD.png", false );
           JOptionPane.showMessageDialog( null,
                                          "You have executed a risk group check on a sequence that came back\n"
                   + "as being highly similar to a select agent.\nSuch materials could be highly dangerous and potential illegal!\n"
                   + "You should examine your design closer before proceeding.",
                                          "Select Agent detected!",
                                          JOptionPane.INFORMATION_MESSAGE,
                                          bslicon );
       }
       return rg;
   }

   /**
    * This queries the bsl server and parses the xml to
    * get the biosafety level of the NucSeq
    *
    * @return the biosafety level of this NucSeq
    */
   private short getBSLfromServer(){
        //If it's already failed 3 times, then don't bother anymore
        if(failCount>3) {
            return -1;
        }

        //Form the URL query
        String seq = getSeq();
        URL urlRobot;
	try {
            String urlstr = _BSLServerURL + "\"" + seq + "\"";
	    urlRobot = new URL(urlstr);
	} catch (Exception e) {
	    e.printStackTrace();
            return -1;
	}

        XMLParser myParser = null;
	try {
            //Starts reading the URL
	    InputStream urlRobotStream = urlRobot.openStream();

            //Read the file and access it in an xmlParser, then close it
            try {
                myParser = new XMLParser(urlRobotStream, "output" );
            } catch(Exception e) {
                System.out.println("Biosafety server data could not be parsed");
                failCount++;
                updateBSLServer(_updateBSLURL1);
                return -1;
            }
	    urlRobotStream.close();
	} catch (java.net.ConnectException e) {
	    System.out.println("Biosafety server not available");
            //Keeps count of failures.  Once pass 3 give up.
            failCount++;
            updateBSLServer(_updateBSLURL1);
            return -1;
	} catch (java.io.IOException ex) {
            System.out.println("Biosafety server not available");
            failCount++;
            updateBSLServer(_updateBSLURL1);
            return -1;
        }

        if(myParser==null) {
            System.out.println("Biosafety server information could not be parsed");
            failCount++;
            updateBSLServer(_updateBSLURL1);
            return-1;
        }

        try {
            String bslvalue = myParser.getFirstTag("bsl");
            short bsl = Short.parseShort(bslvalue);
            System.out.println("Biosafety server returning risk group " + bsl);
            if(bsl == (short) 0) {
                bsl=1;
            }
            return bsl;
        } catch(Exception e) {
            failCount++;
            updateBSLServer(_updateBSLURL1);
           return -1;
        }
   }

   /**
    * The first time this class is called, set the biosafety BLAST
    * server from preferences.  If no preference is set, try retrieving
    * it from an XML file online
    */
   static {
       String tempurl = Collator.getPreference("NucSeqBSLServerAddress");
       try {
           URL url = new URL(tempurl);
       } catch (Exception ex) {
           updateBSLServer("http://www.bu.edu/ece-clotho/xmlfes/updatebsl.xml");
       }
   }

   /**
    * Request that NucSeq update its biosafety server.
    */
    public static void updateBSLServer(String url) {
	//Form URL of the file
	URL urlRobot = null;
	try {
	    urlRobot = new URL(url);
	} catch (Exception e) {
	    e.printStackTrace();
            if(!url.equals(_updateBSLURL2)) {
                updateBSLServer(_updateBSLURL2);
                return;
            }
	}

        XMLParser myParser = null;
	try {
            //Starts reading the URL
	    InputStream urlRobotStream = urlRobot.openStream();

            //Read the file and access it in an xmlParser, then close it
            myParser = new XMLParser(urlRobotStream, "update" );
	    urlRobotStream.close();
	} catch (Exception e) {
	    e.printStackTrace();
            if(!url.equals(_updateBSLURL2)) {
                updateBSLServer(_updateBSLURL2);
                return;
            }
	}
        if(myParser==null) {
            return;
        }

        String newurl = myParser.getFirstTag("url");
        try {
            URL urly = new URL(newurl);
        } catch (MalformedURLException ex) {
            return;
        }

        Collator.putPreference("NucSeqBSLServerAddress", newurl);
        _BSLServerURL = newurl;
        System.out.println("The new BSL server from " + urlRobot.getPath() + " is " + newurl);
    }

/*-----------------
    variables
-----------------*/
    private static final String _updateBSLURL1 = "http://www.bu.edu/ece-clotho/xmlfiles/updatebsl.xml";
    private static final String _updateBSLURL2 = "http://andersonlab.qb3.berkeley.edu/Software/updatebsl.xml";
    private static short failCount = 0;
    private static String _BSLServerURL = "http://cidar1.bu.edu/cgi-bin/tst.pl?";

    private static Hashtable<String, Integer> translation;
    protected boolean[] lowerArray;
    private NucSeqDatum _seqDatum;

    public static class NucSeqDatum extends ObjBaseDatum {

        public boolean isDegenerate = false;   // if it has N's or R's and so on
        public boolean isRNA = false;          // if it has U's
        public boolean isLinear = false;          // if it '.''s
        public boolean isSingleStranded = false;   // if its an oligo
        public boolean isCircular = false;     // if its a plasmid
        public String theSequence;
        public HashSet<String> annotations = new HashSet<String>();  //The list of annoations
        public HashSet<String> removeAnnotations = new HashSet<String>(); // ? What is is this?
        public boolean isLocked = false;

        @Override
        public ObjType getType() {
            return ObjType.NUCSEQ;
        }
    }
    private static boolean featuresInitiated = false;
    private static boolean initiating = false;
    private static String[][] featureTable;
    private static WaitingDialog dialog;

    private ArrayList<String> _stopCodons;
    private ArrayList<String> _startCodons;

    /******* FIELDS *******/
    public static enum Fields {

        NAME,
        DATE_CREATED,
        LAST_MODIFIED,
        SEQUENCE,
        VECTORS,
        PARTS,
        ANNOTATIONS,
        FEATURES,
        OLIGOS
    }
}
