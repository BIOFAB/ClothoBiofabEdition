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
public class CelloNOR extends CelloGates {


    public CelloNOR()
    {
        _ipromoter1 = new CelloIPromoter();
        _ipromoter2 = new CelloIPromoter();
        _rbs1 = new CelloRBS();
        _gene1 = new CelloGene();
        _t1 = new CelloTerminator();
        _rpromoter = new CelloRPromoter();
        _rbs2 = new CelloRBS();
        _gene2 = new CelloGene();
        _t2 = new CelloTerminator();

        _nor = new ArrayList<CelloPrimitive>();

        _nor.add(0, _ipromoter1);
        _nor.add(1, _ipromoter2);
        _nor.add(2, _rbs1);
        _nor.add(3, _gene1);
        _nor.add(4, _t1);
        _nor.add(5, _rpromoter);
        _nor.add(6, _rbs2);
        _nor.add(7, _gene2);
        _nor.add(8, _t2);

        _name = "NOR2";
        this._graph = new DAGraph();
        this.makeGraph();

        this._equiGraph = new DAGraph();
        this.makeEquiGraph();


    }



    public CelloIPromoter getIPromoter1()
    {
        return this._ipromoter1; //(CelloIPromoter) _nor.get(0);
    }

    public CelloIPromoter getIPromoter2()
    {
        return this._ipromoter2;// (CelloIPromoter) _nor.get(1);
    }

    public CelloRBS getRBS1()
    {
        return this._rbs1;// (CelloRBS) _nor.get(2);
    }

    public CelloRBS getRBS2()
    {
        return this._rbs2;// (CelloRBS) _nor.get(6);
    }

    public CelloGene getGene1()
    {
        return this._gene1;// (CelloGene) _nor.get(3);
    }

    public CelloGene getGene2()
    {
        return this._gene2;// (CelloGene) _nor.get(7);
    }

    public CelloRPromoter getRPromoter()
    {
        return this._rpromoter;// (CelloRPromoter) _nor.get(5);
    }

    public CelloTerminator getTerminator1()
    {
        return this._t1;// (CelloTerminator) _nor.get(4);
    }
    
    public CelloTerminator getTerminator2()
    {
        return this._t2;// (CelloTerminator) _nor.get(8);
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
    public void setIPromoter1(CelloIPromoter ip1)
    {
        _nor.set(0, ip1);
        this._ipromoter1 = ip1;
        this._graph.Vertices.get(0).Feature = ip1;

    }

    public void setIPromoter2(CelloIPromoter ip2)
    {
        _nor.set(1, ip2);
        this._ipromoter2 = new CelloIPromoter(ip2);
         this._graph.Vertices.get(1).Feature = ip2;
    }

    public void setRBS1(CelloRBS rbs1)
    {
        _nor.set(2, rbs1);
        this._rbs1  = new CelloRBS(rbs1);
        this._graph.Vertices.get(2).Feature = rbs1;
    }

    public void setRBS2(CelloRBS rbs2)
    {
        _nor.set(6, rbs2);
        this._rbs2  = new CelloRBS(rbs2);
         this._graph.Vertices.get(6).Feature = rbs2;
    }

    public void setGene1(CelloGene cg1)
    {
        _nor.set(3, cg1);
        this._gene1 = new CelloGene(cg1);
         this._graph.Vertices.get(3).Feature = cg1;

    }

    public void setGene2(CelloGene cg2)
    {
        _nor.set(7, cg2);
        this._gene2 = new CelloGene(cg2);
        this._graph.Vertices.get(7).Feature = cg2;

    }

    public void setRPromoter(CelloRPromoter rp1)
    {
        _nor.set(5, rp1);
        this._rpromoter  = new CelloRPromoter(rp1);
        this._graph.Vertices.get(5).Feature = rp1;

    }

     public void setTerminator1 (CelloTerminator ct1)
    {
        _nor.set(4, ct1);
        this._t1  = new CelloTerminator(ct1);
         this._graph.Vertices.get(4).Feature = ct1;

    }
     
      public void setTerminator2 (CelloTerminator ct2)
    {
        _nor.set(8, ct2);
        this._t2  = new CelloTerminator(ct2);
         this._graph.Vertices.get(8).Feature = ct2;

    }
      
    public void setName(String n)
    {
        this._name = n;
    }


    public String print()
    {
        String returnString = "";
        for(int i = 0; i<_nor.size(); i++)
            returnString = returnString + _nor.get(i).print();

        return returnString;
    }

    public String printDNA()
    {
        String returnString = "";
                for(int i = 0; i<_nor.size(); i++)
            returnString = returnString + _nor.get(i).printDNA();

        return returnString;


    }

    private void makeGraph ()
    {
        DAGVertex vipromoter1 = new DAGVertex("ipromoter1", _ipromoter1.getClass().toString(), null, _ipromoter1);
        DAGVertex vipromoter2 = new DAGVertex("ipromoter2", _ipromoter2.getClass().toString(), null, _ipromoter2);
        DAGVertex vrbs1 = new DAGVertex("rbs1", _rbs1.getClass().toString(), null, _rbs1);
        DAGVertex vgene1 = new DAGVertex("gene1", _gene1.getClass().toString(), null, _gene1);
        DAGVertex vt1 = new DAGVertex("terminator1", _t1.getClass().toString(), null, _t1);
        DAGVertex vrprompoter = new DAGVertex("rpromoter", _rpromoter.getClass().toString(), null, _rpromoter);
        DAGVertex vrbs2 = new DAGVertex("rbs2", _rbs2.getClass().toString(), null, _rbs2);
        DAGVertex vgene2 = new DAGVertex("gene2", _gene2.getClass().toString(), null, _gene2);
        DAGVertex vt2 = new DAGVertex("terminator2", _t2.getClass().toString(), null, _t2);

        DAGEdge eip1torbs1 = new DAGEdge( vrbs1, vipromoter1,null);
        DAGEdge eip2torbs1 = new DAGEdge( vrbs1,vipromoter2, null);
        DAGEdge erbs1togene1 = new DAGEdge( vgene1,vrbs1, null);
        DAGEdge egene1tot1 = new DAGEdge(vt1,vgene1,  null);
        DAGEdge et1torp = new DAGEdge(vrprompoter,vt1,  null);
        DAGEdge erptorbs2 = new DAGEdge( vrbs2,vrprompoter, null);
        DAGEdge erbs2togene2 = new DAGEdge( vgene2,vrbs2, null);
        DAGEdge egene2tot2 = new DAGEdge( vt2,vgene2, null);

        this._graph.Vertices.add(vipromoter1);
        this._graph.Vertices.add(vipromoter2);
        this._graph.Vertices.add(vrbs1);
        this._graph.Vertices.add(vgene1);
        this._graph.Vertices.add(vt1);
        this._graph.Vertices.add(vrprompoter);
        this._graph.Vertices.add(vrbs2);
        this._graph.Vertices.add(vgene2);
        this._graph.Vertices.add(vt2);


        this._graph.Edges.add(eip1torbs1);
        this._graph.Edges.add(eip2torbs1);
        this._graph.Edges.add(erbs1togene1);
        this._graph.Edges.add(egene1tot1);
        this._graph.Edges.add(et1torp);
        this._graph.Edges.add(erptorbs2);
        this._graph.Edges.add(erbs2togene2);
        this._graph.Edges.add(egene2tot2);

        this._graph.setParent(vt2);


    }

    private void makeEquiGraph ()
    {
        DAGVertex v1 = new DAGVertex("~", "31", null);
        DAGVertex v2 = new DAGVertex("|", "34", null);

        DAGEdge e1 = new DAGEdge(v1, v2, null);

        this._equiGraph.Vertices.add(v1);
        this._equiGraph.Vertices.add(v2);
        this._equiGraph.Edges.add(e1);

        this._equiGraph.setParent(v1);
    }

    //Array list of all the primitives for a NOR
    private ArrayList<CelloPrimitive> _nor;

    //Primitives for a NOR gate
    private CelloIPromoter _ipromoter1;
    private CelloIPromoter _ipromoter2;
    private CelloRBS _rbs1;
    private CelloGene _gene1;
    private CelloTerminator _t1;
    private CelloRPromoter _rpromoter;
    private CelloRBS _rbs2;
    private CelloGene _gene2;
    private CelloTerminator _t2;
    private DAGraph _graph;
    private DAGraph _equiGraph;
    private String _name;
}
