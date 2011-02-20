/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocad.hibernate;

import java.util.List;
import org.clothocore.api.data.*;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;
import org.clothocore.api.plugin.ClothoConnection.ClothoCriterion;

/**
 *
 * @author Bing Xia <bxia@bxia.net>
 */
public class ExampleQueries {
/*
In groovy:
 *
import org.clothocore.api.plugin.ClothoConnection.ClothoCriterion;
import org.clothocore.api.plugin.ClothoConnection.ClothoQuery;

c = Collector.getDefaultConnection();
mainQuery = c.createQuery( ObjType.PART );
seqQuery = mainQuery.createAssociationQuery( Part.Fields.SEQUENCE );
seqQuery.contains( NucSeq.Fields.SEQUENCE, "AAAA", false );
results = mainQuery.getResults();

for(obj in results) {
    println(obj.getName())
}
 */
    public static void exampleQueries() {
        HibernateConnection c = new HibernateConnection();

        // To find all parts whose author has a certain name
        // Create the main query
        ClothoQuery mainQuery = c.createQuery( ObjType.PART );
        // Create a query on an associated table
        ClothoQuery personQuery = mainQuery.createAssociationQuery( Part.Fields.AUTHOR );
        // Set a criteria on the associated table
        personQuery.eq( Person.Fields.SURNAME, "<Someone's first name>" );
        // Get the results and use them
        List<ObjBase> results = mainQuery.getResults();

        // To find all parts which have a certain format and a certain author
        mainQuery = c.createQuery( ObjType.PART );
        personQuery = mainQuery.createAssociationQuery( Part.Fields.AUTHOR );
        ClothoQuery formatQuery = mainQuery.createAssociationQuery( Part.Fields.FORMAT );
        personQuery.eq( Person.Fields.SURNAME, "AUTHOR_NAME" );
        formatQuery.eq( Format.Fields.NAME, "FORMAT_NAME" );
        results = mainQuery.getResults();

        // To find a part who's sequence contains ATGATGATG
        mainQuery = c.createQuery( ObjType.PART );
        ClothoQuery seqQuery = mainQuery.createAssociationQuery( Part.Fields.SEQUENCE );
        seqQuery.contains( NucSeq.Fields.SEQUENCE, "ATGATGATG", false );
        results = mainQuery.getResults();

        // To find a part who's author is either Bing or Chris
        mainQuery = c.createQuery( ObjType.PART );
        personQuery = mainQuery.createAssociationQuery( Part.Fields.AUTHOR );
        ClothoCriterion crit1 = personQuery.getEqualCrit( Person.Fields.SURNAME, "Bing" );
        ClothoCriterion crit2 = personQuery.getEqualCrit( Person.Fields.SURNAME, "Chris" );
        ClothoCriterion or_crit1_crit2 = personQuery.or( crit2, crit2 );
        personQuery.add( or_crit1_crit2 );
        results = mainQuery.getResults();

        
    }
}
