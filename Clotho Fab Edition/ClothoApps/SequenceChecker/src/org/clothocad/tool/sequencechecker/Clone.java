/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.sequencechecker;

/**
 *
 * @author cesarr
 */

import java.util.ArrayList;

public class Clone
{
    protected String                _identifier;
    protected String                _status;
    protected ArrayList<Primer>     _primers;


    public Clone(String identifier)
    {
        if(identifier != null && identifier.length() > 0)
        {
            this._identifier = identifier;
        }
        else
        {
            //TODO Manage null case
        }

        _primers = new ArrayList<Primer>();
        _status = "Pending";
    }

    /**
     * @return the _identifier
     */
    public String getIdentifier()
    {
        return _identifier;
    }

    /**
     * @return the _status
     */
    public String getStatus()
    {
        return _status;
    }

    /**
     * @param status the _status to set
     */
    public void setStatus(String status)
    {
        this._status = status;
    }

    public void addPrimer(Primer primer)
    {
        if(primer != null)
        {
            _primers.add(primer);
        }
    }

    public ArrayList<Primer> getPrimers()
    {
        return _primers;
    }

    public String[][] generatePrimersArray()
    {
        Primer primer;
        int rows = _primers.size();
        String[][] primersArray = new String[rows][3];

        for(int i = 0; i < rows; ++i)
        {
            primer = _primers.get(i);
            primersArray[i][0] = primer.getIdentifier();
            primersArray[i][1] = primer.getStatus();
            primersArray[i][2] = primer.getTraceFile().getName();
        }

        return primersArray;
    }
}


