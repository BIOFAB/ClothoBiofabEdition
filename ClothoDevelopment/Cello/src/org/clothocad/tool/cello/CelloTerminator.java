/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author Douglas Densmore
 */
public class CelloTerminator extends CelloPrimitive {


    public CelloTerminator()
    {
        _type = CelloPrimitiveType.Terminator;
    }

    public CelloTerminator(String s, int i)
    {
        _id = i;
        _sequence = s;
        _type = CelloPrimitiveType.Terminator;

    }

    public CelloTerminator(CelloTerminator ct)
    {
        _id = ct._id;
        _sequence = ct._sequence;
        _type = ct._type;

    }

    public CelloTerminator Copy()
    {
        return new CelloTerminator(this);
    }

    public String getSequence(){
            return _sequence;
    }

    public int getId(){
        return _id;
    }

    public void setId(int i)
    {
        _id = i;
    }

    @Override
    public String print()
    {
        return "Terminator(" + _id + "," + _sequence + ")\n";
    }

    //private String _sequence;
    //private int _id;
}
