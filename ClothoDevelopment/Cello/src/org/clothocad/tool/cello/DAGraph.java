/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import sun.security.provider.certpath.Vertex;

/**
 *
 * @author rozagh
 */
public class DAGraph {

    public ArrayList<DAGVertex> Vertices;

    public ArrayList<DAGEdge> Edges;

    private DAGVertex _parent;

    public DAGraph ()
    {
        this.Edges = new ArrayList<DAGEdge>();
        this.Vertices = new ArrayList<DAGVertex>();
    }

    public DAGraph(List<DAGVertex> Vertices, List<DAGEdge> Edges )
    {
        this.Edges = new ArrayList<DAGEdge>(Edges);
        this.Vertices = new ArrayList<DAGVertex>(Vertices);
    }

    public DAGraph( DAGraph g)
    {
        /*this.Edges = new ArrayList<DAGEdge>();
        for (int i = 0; i< g.Edges.size(); i++)
            this.Edges.add(g.Edges.get(i).Copy());

        this.Vertices = new ArrayList<DAGVertex>();
        for (int i=0; i<g.Vertices.size(); i++)
            this.Vertices.add(g.Vertices.get(i).Copy());
         *
         */

        if (g.Vertices != null)
            {
                this.Vertices = new ArrayList<DAGVertex>();
                for (int i=0; i<g.Vertices.size(); i++)
                    this.Vertices.add(g.Vertices.get(i).Copy());
            }

            if (g.Edges != null)
            {
                this.Edges = new ArrayList<DAGEdge>();
                for (int i = 0; i< g.Edges.size(); i++)
                    this.Edges.add(g.Edges.get(i).Copy());
            }


            for (int i = 0; i < this.Vertices.size(); i++)
            {
                if (g.Vertices.get(i).Outgoing != null)
                {
                    int i2 = this.findEdge(g.Vertices.get(i).Outgoing.Index);
                    if (-1 < i2 && i2< this.Edges.size())
                        this.Vertices.get(i).Outgoing = this.Edges.get(i2);
                }
            }

            for (int i = 0; i < this.Edges.size(); i++)
            {
                if (g.Edges.get(i).From != null)
                {
                    int i2 = this.findVertex(g.Edges.get(i).From.Index);
                    if (-1 < i2 && i2 < this.Vertices.size())
                        this.Edges.get(i).From = this.Vertices.get(i2);
                }

                if (g.Edges.get(i).To != null)
                {
                    int i2 = this.findVertex(g.Edges.get(i).To.Index);
                    if (-1 < i2 && i2< this.Vertices.size())
                        this.Edges.get(i).To = this.Vertices.get(i2);
                }

                if (g.Edges.get(i).Next != null)
                {
                    int i2 = this.findEdge(g.Edges.get(i).Next.Index);
                    if (-1 < i2 && i2 < this.Edges.size())
                        this.Edges.get(i).Next = this.Edges.get(i2);
                }
            }


    }

    public DAGraph Copy ()
    {
        DAGraph g = new DAGraph(this);
        return g;
    }


    

   /* public DAGraph CopyByParent ()
    {
        ArrayList<DAGVertex> parents =  this.findParent();
        DAGraph result = new DAGraph();

        for (int i =0; i< parents.size(); i++)
        {


        }

    }
    * 
    */

    public void setParent (DAGVertex v)
    {
        this._parent = v;
    }

    public DAGVertex getParent ()
    {
        return this._parent;
    }
    
    public int VertexCount(){
        return this.Vertices.size();
    }

    public void AddVertex ( DAGVertex v ){
        this.Vertices.add(v);
    }

    public void AddChild (DAGVertex Parent, DAGVertex Child )
    {
        this.Vertices.add(Child);
        DAGEdge e = new DAGEdge(Parent, Child, null);
        //Parent.AddEdge(e);
        this.Edges.add(e);

    }

    public String PrintGraph ()
    {
        String result = "";
        for (int i=0; i< this.Vertices.size(); i++)
        {
            result += "\n\n Vertex is --> "+ this.Vertices.get(i).Name +"\t" + String.valueOf( this.Vertices.get(i).Index);
            
            result += "\tCover Part : " + String.valueOf( this.Vertices.get(i).Cover);
            result += "\tCover Part Number : " + String.valueOf( this.Vertices.get(i).subCover);
            if (this.Vertices.get(i).Feature != null)
                result += "\tPrimitive Sequence : " + this.Vertices.get(i).Feature.print();
            result +="\nChilds:  " ;

            DAGEdge e= this.Vertices.get(i).Outgoing;
            while (e!= null )
            {
                if (e.To != null)
                 result += " --> "+e.To.Name +" -- " + String.valueOf( e.To.Index) + "\t";
                e = e.Next;
            }

        }
        return result;
    }

    public void ChangeChild(DAGVertex oldV, DAGVertex newV )
    {
        for(int i = 0; i< this.Edges.size(); i++)
        {
            if (this.Edges.get(i).To == oldV)
            {
                this.Edges.get(i).To = new DAGVertex();
                this.Edges.get(i).To = newV;
            }
        }
    }

    public void ReplaceVertex (DAGVertex oldV, DAGVertex newV)
    {
        //change all edges from oldv to from newv
        for(int i = 0; i< this.Edges.size(); i++)
        {
            if (this.Edges.get(i).From == oldV)
            {
                this.Edges.get(i).From = new DAGVertex();
                this.Edges.get(i).From = newV;
            }
        }

         //change all edges to  oldv to from newv
        for(int i = 0; i< this.Edges.size(); i++)
        {
            if (this.Edges.get(i).To == oldV)
            {
                this.Edges.get(i).To = new DAGVertex();
                this.Edges.get(i).To = newV;
            }
        }

    }

    public ArrayList<DAGVertex> findRoots ()
    {
        ArrayList<DAGVertex> parents = new ArrayList<DAGVertex>();

        for (int i=0; i< this.Vertices.size(); i++)
        {
            DAGVertex curv = this.Vertices.get(i);
            boolean isparent = true;

            for (int j=0; j< this.Edges.size(); j++)
            {
                DAGEdge cure = this.Edges.get(j);
                if (cure.To == curv)
                {
                    isparent=false;
                    break;
                }
            }

            if (isparent)
                parents.add(curv);
        }
        return parents;
    }

    public ArrayList<DAGVertex> findLeaf ()
    {
        ArrayList<DAGVertex> result = new ArrayList<DAGVertex>();
        for (int i=0; i< this.Vertices.size(); i++)
            if (this.Vertices.get(i).Outgoing == null)
                result.add(this.Vertices.get(i));

        return result;
    }

    public int findEdge (int index)
    {
        for (int i=0; i< this.Edges.size(); i++)
        {
            DAGEdge cure = this.Edges.get(i);
            if (cure.Index == index)
            {
                return i;
            }
        }

        return -1;
    }

    public int findVertex (int index)
    {
        for (int i=0; i< this.Vertices.size(); i++)
        {
            DAGVertex curv = this.Vertices.get(i);
            if (curv.Index == index)
            {
                return i;
            }
        }

        return -1;
    }

    public String printSequence ()
    {
        ArrayList<DAGVertex> roots = this.findRoots();

        String result = "";

        for (int i=0; i< roots.size(); i++)
        {
            result += "\n" + this.makeSequenceArray(roots.get(i));
        }

        return result;

    }


    public String makeSequenceArray (DAGVertex v)
    {
        String Result = "";
         HashMap<String, ArrayList<String>> sequenceArray = new HashMap<String, ArrayList<String>>();


        DFSSearch(v, sequenceArray);

        Iterator iterator = sequenceArray.keySet().iterator();

        while(iterator.hasNext()){

            ArrayList<String> temp = sequenceArray.get((String) iterator.next());
            Result+="\n";
            for (int i=0; i< temp.size(); i++)
                Result+= " " + temp.get(i);
        }

        return Result;
    }


    public void DFSSearch (DAGVertex v, HashMap<String, ArrayList<String>> sequenceArray)
    {
        DAGEdge e = v.Outgoing;

        while (e!= null)
        {
            DFSSearch(e.To, sequenceArray);
            e = e.Next;

        }

        if (v.Feature !=null)
        {

            String Key = String.valueOf( v.Cover)+"_"+String.valueOf( v.subCover);
            if (!sequenceArray.containsKey(Key))
                sequenceArray.put(Key, new ArrayList<String>());
            if (v.Feature._sequence != null)
            {
                //Result += "("+v.Feature._sequence+")";
               sequenceArray.get(Key).add(v.Feature._sequence);
            }else
            {
                 sequenceArray.get(Key).add("(N/A)");
                //Result += "(N/A)";
            }
        }

    }


}
