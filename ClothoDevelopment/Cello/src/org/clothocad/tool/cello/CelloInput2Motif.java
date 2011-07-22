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
public class CelloInput2Motif {

    public CelloInput2Motif()
    {
        _rpromoter1 = new CelloRPromoter();
        _rpromoter2 = new CelloRPromoter();
        _rbs1 = new CelloRBS();
        _gene1 = new CelloGene();

        _motif2.add(0, _rpromoter1);
        _motif2.add(1, _rpromoter2);
        _motif2.add(2, _rbs1);
        _motif2.add(3, _gene1);

    }

    public CelloRPromoter getRPromoter1()
    {
        return (CelloRPromoter) _motif2.get(0);
    }

    public CelloRPromoter getRPromoter2()
    {
        return (CelloRPromoter) _motif2.get(1);
    }

    public CelloRBS getRBS1()
    {
        return (CelloRBS) _motif2.get(2);
    }

    public CelloGene getGene1()
    {
        return (CelloGene) _motif2.get(3);
    }


    public void setRPromoter1(CelloRPromoter ip1)
    {
        _motif2.set(0, ip1);
    }

    public void setRPromoter2(CelloRPromoter ip2)
    {
        _motif2.set(1, ip2);
    }

    public void setRBS1(CelloRBS rbs1)
    {
        _motif2.set(2, rbs1);
    }

    public void setGene1(CelloGene cg1)
    {
        _motif2.set(3, cg1);
    }

    //Array list of all the primitives for a NOR
    private ArrayList<CelloPrimitive> _motif2;

    //Primitives for a NOR gate
    private CelloRPromoter _rpromoter1;
    private CelloRPromoter _rpromoter2;
    private CelloRBS _rbs1;
    private CelloGene _gene1;
}
