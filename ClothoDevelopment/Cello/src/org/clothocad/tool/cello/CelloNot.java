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
public class CelloNot extends CelloGates {


    public CelloNot()
    {
        _rpromoter = new CelloRPromoter();
        _rbs = new CelloRBS();
        _gene = new CelloGene();
        _t = new CelloTerminator();


        _not = new ArrayList<CelloPrimitive>();

        _not.add(0, _rpromoter);
        _not.add(1, _rbs);
        _not.add(2, _gene);
        _not.add(3, _t);

        _name = "NOT";
        this._graph = new DAGraph();
        this.makeGraph();

        this._equiGraph = new DAGraph();
        this.makeEquiGraph();


    }




    public CelloRBS getRBS()
    {
        return this._rbs;// (CelloRBS) _nor.get(2);
    }



    public CelloGene getGene()
    {
        return this._gene;// (CelloGene) _nor.get(3);
    }

    public CelloRPromoter getRPromoter()
    {
        return this._rpromoter;// (CelloRPromoter) _nor.get(5);
    }

    public CelloTerminator getTerminator()
    {
        return this._t;// (CelloTerminator) _nor.get(4);
    }


     public DAGraph getEquiGraphe ()
    {
        return this._equiGraph;
    }

    public DAGraph getGraphe ()
    {
        return this._graph;
    }

    public String getName()
    {
        return this._name;
    }


    public void setRPromoter(CelloRPromoter rp1)
    {
        _not.set(0, rp1);
        this._rpromoter  = new CelloRPromoter(rp1);
        this._graph.Vertices.get(0).Feature = rp1;

    }

    public void setRBS(CelloRBS rbs)
    {
        _not.set(1, rbs);
        this._rbs  = new CelloRBS(rbs);
        this._graph.Vertices.get(1).Feature = rbs;
    }


    public void setGene(CelloGene cg)
    {
        _not.set(2, cg);
        this._gene = new CelloGene(cg);
         this._graph.Vertices.get(2).Feature = cg;

    }

     public void setTerminator (CelloTerminator ct)
    {
        _not.set(4, ct);
        this._t  = new CelloTerminator(ct);
         this._graph.Vertices.get(3).Feature = ct;

    }


    public void setName(String n)
    {
        this._name = n;
    }


    public String print()
    {
        String returnString = "";
        for(int i = 0; i<_not.size(); i++)
            returnString = returnString + _not.get(i).print();

        return returnString;
    }

    public String printDNA()
    {
        String returnString = "";
                for(int i = 0; i<_not.size(); i++)
            returnString = returnString + _not.get(i).printDNA();

        return returnString;


    }

    private void makeGraph ()
    {
        DAGVertex vrprompoter = new DAGVertex("rpromoter", _rpromoter.getClass().toString(), null, _rpromoter);
        DAGVertex vrbs = new DAGVertex("rbs", _rbs.getClass().toString(), null, _rbs);
        DAGVertex vgene = new DAGVertex("gene", _gene.getClass().toString(), null, _gene);
        DAGVertex vt = new DAGVertex("terminator", _t.getClass().toString(), null, _t);


        DAGEdge eip1torbs1 = new DAGEdge( vrbs, vrprompoter,null);
        DAGEdge erbs1togene1 = new DAGEdge( vgene,vrbs, null);
        DAGEdge egene1tot1 = new DAGEdge(vt,vgene,  null);

        this._graph.Vertices.add(vrprompoter);
        this._graph.Vertices.add(vrbs);
        this._graph.Vertices.add(vgene);
        this._graph.Vertices.add(vt);



        this._graph.Edges.add(eip1torbs1);
        this._graph.Edges.add(erbs1togene1);
        this._graph.Edges.add(egene1tot1);

        this._graph.setParent(vt);


    }

    private void makeEquiGraph ()
    {
        DAGVertex v1 = new DAGVertex("~", "31", null);

        this._equiGraph.Vertices.add(v1);

        this._equiGraph.setParent(v1);
    }

    //Array list of all the primitives for a NOR
    private ArrayList<CelloPrimitive> _not;

    //Primitives for a NOR gate
    private CelloRPromoter _rpromoter;
    private CelloRBS _rbs;
    private CelloGene _gene;
    private CelloTerminator _t;

    private DAGraph _graph;
    private DAGraph _equiGraph;
    private String _name;
}
