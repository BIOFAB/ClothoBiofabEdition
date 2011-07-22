/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

import java.util.HashMap;

/**
 *
 * @author rozaghamari
 */
public class CelloRGenePromoterTable {

    public CelloRGenePromoterTable()
    {
        _table = new HashMap<Integer, Integer>(); //key = Rpromoter id and Value = Gene id
    }

    public void add( CelloRPromoter crpK, CelloGene cgV)
    {
        _table.put( crpK.getId(), cgV.getId());
    }

    public Integer getGeneId(CelloRPromoter crpK)
    {
        return  _table.get(crpK.getId());
    }

    private HashMap<Integer, Integer> _table;

}
