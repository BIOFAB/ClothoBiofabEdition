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
    protected String                        _identifier;
    protected String                        _status;
    protected ArrayList<SequencingResult>   _seqResults;


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

        _seqResults = new ArrayList<SequencingResult>();
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

    public void addSequenceResult(SequencingResult seqResult)
    {
        if(seqResult != null)
        {
            _seqResults.add(seqResult);
        }
    }

    public ArrayList<SequencingResult> getSequencingResults()
    {
        return _seqResults;
    }

    public String[][] generateSequencingResultsArray()
    {
        SequencingResult seqResult;
        int rows = _seqResults.size();
        String[][] seqResultsArray = new String[rows][3];

        for(int i = 0; i < rows; ++i)
        {
            seqResult = _seqResults.get(i);
            seqResultsArray[i][0] = seqResult.getPrimer();
            seqResultsArray[i][1] = seqResult.getStatus();
            seqResultsArray[i][2] = seqResult.getTraceFile().getName();
        }

        return seqResultsArray;
    }
}


