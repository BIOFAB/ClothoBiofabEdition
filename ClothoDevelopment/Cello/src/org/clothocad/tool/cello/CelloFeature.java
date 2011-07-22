/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author rozaghamari
 */
public class CelloFeature {

    public CelloFeature ()
    {

    }

    public CelloFeature(String s, int i, CelloFeatureType t )
    {
        _id = i;
        _sequence = s;
        _type = t;
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

    public String print()
    {
        return "RBS(" + _id + "," + _sequence + ")\n";
    }

    private String _sequence;
    private int _id;
    private CelloFeatureType _type;

}

