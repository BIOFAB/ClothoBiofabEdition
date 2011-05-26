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

package org.clothocad.algorithm.searchalgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.RunStatus;
import org.clothocore.api.actor.data.ObjBaseToken;
import org.clothocore.api.actor.data.StringToken;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.*;
import org.clothocore.api.plugin.ClothoConnection;
import org.clothocore.api.plugin.ClothoConnection.ClothoCriterion;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;

/**
 *
 * @author J. Christopher Anderson
 */
public class searchActor extends Actor {

    public searchActor() {
        _inp = new InputPort<StringToken>( this, StringToken.class );
        _out = new OutputPort<ObjBaseToken>( this, ObjBaseToken.class );
    }

    @Override
    public RunStatus run() {
        if ( !isReady() ) {
            return RunStatus.NOT_READY_ERROR;
        }

        StringToken tok1 = _inp.get();
        _inp.clear();

        String query = tok1.getData();

        //RUN QUERY AND POPULATE OUTCOLL
        try {
        ObjBaseToken out = new ObjBaseToken(runSearch(query) );
        _out.put( out );
        return RunStatus.COMPLETE;
        } catch(Exception e) {
            e.printStackTrace();
            return RunStatus.RUN_ERROR;
        }
    }

    public Collection runSearch(String searchText) {
        //Try doing a BY search
        String ins = searchText.toUpperCase();
        if(ins.indexOf(" BY ")>-1) {
            String[] parsed = ins.split("\\s+");
            for(String str: parsed) {
                System.out.println("parsed str for BY " + str);
            }
            if(parsed.length==3) {
                Collection out = runByQuery(parsed[0], parsed[2]);
                if(out!=null) {
                    return out;
                }
            }
        }

        //Try doing a SEQ search
        if(ins.indexOf(" SEQ ")>-1) {
            String[] parsed = ins.split("\\s+");
            for(String str: parsed) {
                System.out.println("parsed str for SEQ " + str);
            }
            if(parsed.length==3) {
                String stype = parsed[0];
                if(stype.equals("PART") || stype.equals("FEATURE") ||  stype.equals("OLIGO") || stype.equals("VECTOR")) {
                    Collection out = runSeqQuery(parsed[0], parsed[2]);
                    if(out!=null) {
                        return out;
                    }
                }
            }
        }

        HashSet<String> finalterms = new HashSet<String>();
        HashSet<String> reparseterms = new HashSet<String>();

        //Deal with any quotes
        boolean endsQuote = false;
        if(searchText.endsWith("\"")) {
            endsQuote = true;
        }
        boolean startsQuote = false;
        if(searchText.startsWith("\"")) {
            startsQuote = true;
        }

        String[] parseQuotes = searchText.split("\"");

        if(!startsQuote) {
            reparseterms.add(parseQuotes[0].trim());
        } else {
            finalterms.add(parseQuotes[0].trim());
        }
        if(!endsQuote) {
            reparseterms.add(parseQuotes[parseQuotes.length-1].trim());
        } else {
            finalterms.add(parseQuotes[parseQuotes.length-1].trim());
        }

        for(int i=1; i<parseQuotes.length-1; i++) {
            if(i % 2 == 1) {
                finalterms.add(parseQuotes[i].trim());
            } else {
                reparseterms.add(parseQuotes[i].trim());
            }
        }

        //Deal with spaces in non-quoted text
        for(String s: reparseterms) {
            System.out.println("parsing: " + s);
            String[] parsed = s.split("\\s+");
            for(String str: parsed) {
                finalterms.add(str.trim());
            }
        }

        for(String s: finalterms) {
            System.out.println("final: " + s);
        }

        return runWordsQuery(finalterms);
    }
    //one two "a phrase" three  four "yes so" five six

    private Collection runSeqQuery(String stype, String seq) {
        System.out.println("Running a SEQ query");
        List results = new ArrayList();
        HashSet finallist;
        Collection out = new Collection();

         ObjType type;
        try {
            type = ObjType.valueOf(stype);
        } catch(Exception e) {
            System.out.println("SEQ search enum cast error");
            return null;
        }

        finallist = seqQuery(type, seq);

        //*******Finish it up********
        if(finallist.isEmpty()) {
            System.out.println("SEQ search finallist was empty");
            return null;
        }

        for(Object obj : finallist) {
            ObjBase o = (ObjBase) obj;
            out.addObject(o);
        }
        return out;
    }

    private HashSet seqQuery(ObjType type, String seq) {
        System.out.println("SEQ search starting actual SEQQuery");
        if ( seq.charAt( 0 ) != '%' ) {
            seq = "%" + seq;
        }
        if ( seq.charAt( seq.length() - 1 ) != '%' ) {
            seq = seq + "%";
        }
        ClothoConnection c = Collector.getDefaultConnection();
        ClothoQuery mainQuery = c.createQuery( type );
        HashSet finallist = new HashSet();
        ClothoQuery personQuery = null;;
        switch(type) {
            case PART:
                personQuery = mainQuery.createAssociationQuery( Part.Fields.SEQUENCE );
                break;
            case VECTOR:
                personQuery = mainQuery.createAssociationQuery( Vector.Fields.SEQUENCE );
                break;
            case FEATURE:
                personQuery = mainQuery.createAssociationQuery( Feature.Fields.SEQUENCE );
                break;
            case OLIGO:
                personQuery = mainQuery.createAssociationQuery( Oligo.Fields.SEQUENCE );
                break;
        }
        ClothoCriterion crit1 = personQuery.getMatchesCrit( NucSeq.Fields.SEQUENCE, seq );
        personQuery.add( crit1 );

        List results = mainQuery.getResults();
        for(Object obj: results) {
            finallist.add(obj);
        }
        return finallist;
    }

    private Collection runByQuery(String stype, String author) {
        System.out.println("Running a by query");
        List results = new ArrayList();
        HashSet finallist;
        Collection out = new Collection();

         ObjType type;
        try {
            type = ObjType.valueOf(stype);
        } catch(Exception e) {
            System.out.println("BY search enum cast error");
            return null;
        }

        finallist = byQuery(type, author);

        //*******Finish it up********
        if(finallist.isEmpty()) {
            System.out.println("BY search finallist was empty");
            return null;
        }

        for(Object obj : finallist) {
            ObjBase o = (ObjBase) obj;
            out.addObject(o);
        }
        return out;
    }

    private HashSet byQuery(ObjType type, String name) {
        System.out.println("BY search starting actual byQuery");
        if ( name.charAt( 0 ) != '%' ) {
            name = "%" + name;
        }
        if ( name.charAt( name.length() - 1 ) != '%' ) {
            name = name + "%";
        }
        ClothoConnection c = Collector.getDefaultConnection();
        ClothoQuery mainQuery = c.createQuery( type );
        HashSet finallist = new HashSet();
        ClothoQuery personQuery = null;;
        switch(type) {
            case NOTE:
                personQuery = mainQuery.createAssociationQuery( Note.Fields.AUTHOR );
                break;
            case FACTOID:
                personQuery = mainQuery.createAssociationQuery( Factoid.Fields.AUTHOR );
                break;
            case PART:
                personQuery = mainQuery.createAssociationQuery( Part.Fields.AUTHOR );
                break;
            case VECTOR:
                personQuery = mainQuery.createAssociationQuery( Vector.Fields.AUTHOR );
                break;
            case COLLECTION:
                personQuery = mainQuery.createAssociationQuery( Collection.Fields.AUTHOR );
                break;
            case PLASMID:
                personQuery = mainQuery.createAssociationQuery( Plasmid.Fields.AUTHOR );
                break;
            case STRAIN:
                personQuery = mainQuery.createAssociationQuery( Strain.Fields.AUTHOR );
                break;
            case FEATURE:
                personQuery = mainQuery.createAssociationQuery( Feature.Fields.AUTHOR );
                break;
            case OLIGO:
                personQuery = mainQuery.createAssociationQuery( Oligo.Fields.AUTHOR );
                break;
            case PLATE:
                personQuery = mainQuery.createAssociationQuery( Plate.Fields.AUTHOR );
                break;
        }
        ClothoCriterion crit1 = personQuery.getMatchesCrit( Person.Fields.GIVEN_NAME, name );
        ClothoCriterion crit2 = personQuery.getMatchesCrit( Person.Fields.SURNAME, name );
        ClothoCriterion crit3 = personQuery.getMatchesCrit( Person.Fields.DISPLAY_NAME, name );
        ClothoCriterion crit4 = personQuery.getMatchesCrit( Person.Fields.NICKNAME, name );
        ClothoCriterion or_crit1_crit2 = personQuery.or( crit1, crit2 );
        ClothoCriterion or_crit3_crit4 = personQuery.or( crit3, crit4 );
        ClothoCriterion allofem = personQuery.or( or_crit1_crit2, or_crit3_crit4 );
        personQuery.add( allofem );

        List results = mainQuery.getResults();
        for(Object obj: results) {
            finallist.add(obj);
        }
        return finallist;
    }

    private Collection runWordsQuery(HashSet<String> terms) {
        List results = new ArrayList();
        HashSet finallist = new HashSet();
        Collection out = new Collection();

        finallist = indivQuery(ObjType.PART, Part.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.PART, Part.Fields.DESCRIPTION, terms, finallist);
        finallist = indivQuery(ObjType.VECTOR, Vector.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.VECTOR, Vector.Fields.DESCRIPTION, terms, finallist);
        finallist = indivQuery(ObjType.PLASMID, Plasmid.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.NOTE, Note.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.FACTOID, Factoid.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.PERSON, Person.Fields.DISPLAY_NAME, terms, finallist);
        finallist = indivQuery(ObjType.PERSON, Person.Fields.GIVEN_NAME, terms, finallist);
        finallist = indivQuery(ObjType.PERSON, Person.Fields.SURNAME, terms, finallist);
        finallist = indivQuery(ObjType.PERSON, Person.Fields.EMAIL_ADDRESS, terms, finallist);
        finallist = indivQuery(ObjType.PERSON, Person.Fields.REGISTRY_NAME, terms, finallist);
        finallist = indivQuery(ObjType.LAB, Lab.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.INSTITUTION, Institution.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.ATTACHMENT, Attachment.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.COLLECTION, Collection.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.FEATURE, Feature.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.FAMILY, Family.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.FORMAT, Format.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.GRAMMAR, Grammar.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.OLIGO, Oligo.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.PLATE_TYPE, PlateType.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.STRAIN, Strain.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.WIKITEXT, WikiText.Fields.NAME, terms, finallist);
        finallist = indivQuery(ObjType.PLATE, Plate.Fields.NAME, terms, finallist);

/*
        //Do note wikitext
        mainQuery = c.createQuery( ObjType.NOTE );
        assocQuery = mainQuery.createAssociationQuery( Note.Fields.WIKITEXT );
        for(String str : terms) {
            mainQuery.contains( WikiText.Fields.WIKI_TEXT, str, false );
        }
        results = mainQuery.getResults();
        for(Object obj: results) {
            finallist.add(obj);
        }
*/

/*
        //Do factoid wikitext
        mainQuery = c.createQuery( ObjType.FACTOID );
        assocQuery = mainQuery.createAssociationQuery( Factoid.Fields.WIKITEXT );
        for(String str : terms) {
            mainQuery.contains( WikiText.Fields.WIKI_TEXT, str, false );
        }
        results = mainQuery.getResults();
        for(Object obj: results) {
            finallist.add(obj);
        }

*/



        //*******Finish it up********
        if(finallist.isEmpty()) {
            return out;
        }

        for(Object obj : finallist) {
            ObjBase o = (ObjBase) obj;
            out.addObject(o);
        }
        return out;
    }

    private HashSet indivQuery(ObjType type, Enum stringfield, HashSet<String> terms, HashSet finallist) {
        ClothoConnection c = Collector.getDefaultConnection();
        ClothoQuery mainQuery = c.createQuery( type );
        for(String str : terms) {
            mainQuery.contains( stringfield, str, false );
        }
        List results = mainQuery.getResults();
        for(Object obj: results) {
            finallist.add(obj);
        }
        return finallist;
    }

    @Override
    public String getName() {
        return "Search Algorithm";
    }

/*-----------------
     variables
 -----------------*/
    private InputPort<StringToken> _inp;
    private OutputPort<ObjBaseToken> _out;
}
