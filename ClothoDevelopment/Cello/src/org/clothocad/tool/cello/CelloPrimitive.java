/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author Douglas Densmore
 */
public class CelloPrimitive {


    public String print()
    {
        return "CelloPrimitive\n";
    }

    public String printDNA()
    {
        return _sequence;
    }

    public CelloPrimitive Copy()
    {
        return null;
    }

    protected String _sequence;
    protected int _id;
    protected CelloPrimitiveType _type;
}
