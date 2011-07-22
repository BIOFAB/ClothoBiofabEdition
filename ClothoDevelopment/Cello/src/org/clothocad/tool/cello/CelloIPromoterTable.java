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
public class CelloIPromoterTable {


    public CelloIPromoterTable()
    {
        _table = new ArrayList<CelloIPromoter>();
    }


    public void add(CelloIPromoter cip)
    {
        int index = _table.size();
        cip.setId(index);
        _table.add(index, cip);
    }

    public int Size ()
    {
        return _table.size();
    }

    public CelloIPromoter get(int i)
    {
        return _table.get(i);
    }

    private ArrayList<CelloIPromoter> _table;
}
