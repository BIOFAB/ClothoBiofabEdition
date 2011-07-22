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
public class CelloInput1Motif {

    public CelloInput1Motif()
    {
        _ipromoter1 = new CelloIPromoter();
        _ipromoter2 = new CelloIPromoter();
        _rbs1 = new CelloRBS();
        _gene1 = new CelloGene();

        _motif1.add(0, _ipromoter1);
        _motif1.add(1, _ipromoter2);
        _motif1.add(2, _rbs1);
        _motif1.add(3, _gene1);

    }

   public CelloIPromoter getIPromoter1()
    {
        return (CelloIPromoter) _motif1.get(0);
    }

    public CelloIPromoter getIPromoter2()
    {
        return (CelloIPromoter) _motif1.get(1);
    }

    public CelloRBS getRBS1()
    {
        return (CelloRBS) _motif1.get(2);
    }

    public CelloGene getGene1()
    {
        return (CelloGene) _motif1.get(3);
    }



    public void setIPromoter1(CelloIPromoter ip1)
    {
        _motif1.set(0, ip1);
    }

    public void setIPromoter2(CelloIPromoter ip2)
    {
        _motif1.set(1, ip2);
    }

    public void setRBS1(CelloRBS rbs1)
    {
        _motif1.set(2, rbs1);
    }

    public void setGene1(CelloGene cg1)
    {
        _motif1.set(3, cg1);
    }



    //Array list of all the primitives for a NOR
    private ArrayList<CelloPrimitive> _motif1;

    //Primitives for a NOR gate
    private CelloIPromoter _ipromoter1;
    private CelloIPromoter _ipromoter2;
    private CelloRBS _rbs1;
    private CelloGene _gene1;
}
