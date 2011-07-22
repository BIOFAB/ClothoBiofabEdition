/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author Douglas Densmore
 */
public class CelloRBS extends CelloPrimitive {

    public CelloRBS()
    {
        _type = CelloPrimitiveType.RBS;


    }

    public CelloRBS(String s, int i)
    {
        _id = i;
        _sequence = s;
        _type = CelloPrimitiveType.RBS;
    }

     public CelloRBS(CelloRBS crbs)
    {
        _id = crbs._id;
        _sequence = crbs._sequence;
        _type = crbs._type;
    }

     public CelloRBS Copy()
    {
         return new CelloRBS(this);
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
        return "RBS(" + _id + "," + _sequence + ")\n";
    }

    //private String _sequence;
    //private int _id;

}
