/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author Douglas Densmore
 */
public class CelloRPromoter extends CelloPrimitive{

    public CelloRPromoter()
    {
        _type = CelloPrimitiveType.RPromoter;
    }


    public CelloRPromoter(String s, int i)
    {
        _id = i;
        _sequence = s;
        _type = CelloPrimitiveType.RPromoter;

    }

    public CelloRPromoter(CelloRPromoter cpr)
    {
        _id = cpr._id;
        _sequence = cpr._sequence;
        _type = cpr._type;

    }

    public CelloRPromoter Copy()
    {
        return new CelloRPromoter(this);
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
        return "RPomoter(" + _id + "," + _sequence + ")\n";
    }

    //private String _sequence;
    //private int _id;
}
