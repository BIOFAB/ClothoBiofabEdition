/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

import java.util.ArrayList;

/**
 *
 * @author Douglas Densmore
 */
public class CelloRPromoterTable {


    public CelloRPromoterTable()
    {
        _table = new ArrayList<CelloRPromoter>();
    }

    public void add(CelloRPromoter crp)
    {
        int index = _table.size();
        crp.setId(index);
        _table.add(index, crp);
    }

    public int Size ()
    {
        return _table.size();
    }
    
    public CelloRPromoter get(int i)
    {
        return _table.get(i);
    }
    

    private ArrayList<CelloRPromoter> _table;
}
