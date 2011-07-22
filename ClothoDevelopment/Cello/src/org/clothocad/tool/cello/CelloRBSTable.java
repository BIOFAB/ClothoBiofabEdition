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
public class CelloRBSTable {


    public CelloRBSTable()
    {
        _table = new ArrayList<CelloRBS>();
    }


    public void add(CelloRBS cr)
    {
        int index = _table.size();
        cr.setId(index);
        _table.add(index, cr);
    }

    public int Size ()
    {
        return _table.size();
    }

    public CelloRBS get(int i)
    {
        return _table.get(i);
    }

    private ArrayList<CelloRBS> _table;
}
