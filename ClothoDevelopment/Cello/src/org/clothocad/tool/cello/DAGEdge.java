/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author rozagh
 */
public class DAGEdge {

    protected  int Index;

    public DAGVertex From;

    public DAGVertex To;

    public DAGEdge Next;

    public static int numberofedges;

    public DAGEdge()
    {
        this.Index = ++numberofedges;
        this.From = new DAGVertex();
        this.To = new DAGVertex();
        this.Next = null;
    }

    public DAGEdge(DAGVertex From, DAGVertex To, DAGEdge Next)
    {
        this.Index = ++numberofedges;
        this.From = From;
        this.To = To;
        this.Next = Next;

        if (From.Outgoing == null)
            From.Outgoing = this;
        else if (From.Outgoing.From == null)
            From.Outgoing = this;
        else
        {
            DAGEdge E = From.Outgoing;
            while (E.Next != null)
                E = E.Next;
            E.Next = this;
        }

    }


    public DAGEdge (DAGEdge e)
    {
       // this.From = e.From.Copy();
       // this.To = e.To.Copy();
       // if (e.Next != null)
       //     this.Next = e.Next.Copy();
        this.Index = e.Index;
    }

    public DAGEdge Copy ()
    {
        DAGEdge e = new DAGEdge(this);
        return e;
    }

}
