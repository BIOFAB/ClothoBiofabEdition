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
public class CelloTerminatorTable {


    public CelloTerminatorTable()
    {
        _table = new ArrayList<CelloTerminator>();
    }

    public void add(CelloTerminator ct)
    {
        int index = _table.size();
        ct.setId(index);
        _table.add(index, ct);
    }

    public int Size ()
    {
        return _table.size();
    }

    public CelloTerminator get(int i)
    {
        return _table.get(i);
    }


    private ArrayList<CelloTerminator> _table;
}
