/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author rozagh
 */
public class DAGVertex {

    protected  int Index;

    public String Name;

    public String Type;

    public DAGEdge Outgoing;

    public CelloPrimitive Feature;

    public int Cover  = -1;

    public int subCover  = -1;

    public static int numberofvertex;
   // public int id;

    public DAGVertex ()
    {
        this.Index = ++numberofvertex;//++;

        this.Name = "";
        this.Type = "";
       this.Outgoing = null;
       this.Feature = null;

      // this.id = ++numberofvertex;
    }

    public DAGVertex(String Name, String Type, DAGEdge Outgoing)
    {
        this.Index= ++numberofvertex;//++;
        this.Name = Name;
        this.Type = Type;
        this.Outgoing = Outgoing;
    }

    public DAGVertex(String Name, String Type, DAGEdge Outgoing, CelloPrimitive Feature)
    {
        this.Index= ++numberofvertex;//++;
        this.Name = Name;
        this.Type = Type;
        this.Outgoing = Outgoing;
        this.Feature =  Feature;
    }
public DAGVertex (DAGVertex v)
    {
        this.Index = v.Index;
        this.Name = v.Name;
        //if (v.Outgoing != null)
        //    this.Outgoing = v.Outgoing.Copy();
        this.Type = v.Type;
        this.Cover = v.Cover;
        this.subCover = v.subCover;

        if (v.Feature != null)
         this.Feature = v.Feature.Copy();

    }

    public DAGVertex Copy ()
    {
        DAGVertex v = new DAGVertex(this);
        return v;
    }

    public void AddEdge(DAGEdge Edge)
    {
        if (this.Outgoing == null)
            this.Outgoing = Edge;
        else
        {
            DAGEdge e = this.Outgoing.Next;
            while (e != null)
                e = e.Next;
            e =  Edge;
        }
    }



}
