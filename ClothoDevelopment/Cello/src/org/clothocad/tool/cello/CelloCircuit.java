/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.cello;

/**
 *
 * @author rozagh
 */
public class CelloCircuit {

    public CelloCircuit() {
        this._inputGraph = new DAGraph();
        this._isOptimized = false;
        this._isTransferred = false;
        this._mappedGraph = new DAGraph();
        this._index = counter++;
    }

    public int getIndex()
    {
        return _index;
    }
    
    public void setInputGraph(DAGraph g)
    {
        _inputGraph = g;
    }

    public DAGraph getInputGraph()
    {
        return _inputGraph;

    }

    public void setMappedGraph(DAGraph g)
    {
        _mappedGraph = g;
    }

    public DAGraph getMappedGraph()
    {
        return _mappedGraph;

    }
    public void setIsOptimized(boolean b)
    {
        _isOptimized = b;
    }

    public boolean isOptimized()
    {
        return _isOptimized;

    }

    public void setIsTransferred(boolean b)
    {
        _isTransferred = b;
    }

    public boolean isTransferred()
    {
        return _isTransferred;

    }

    private int _index;
    private DAGraph _inputGraph;
    private DAGraph _mappedGraph;
    private boolean _isOptimized;
    private boolean _isTransferred;
    public static int counter;

}
