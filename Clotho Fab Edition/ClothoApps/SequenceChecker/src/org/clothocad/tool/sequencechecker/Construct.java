package org.clothocad.tool.sequencechecker;

import java.io.File;
import java.util.ArrayList;

public class Construct {

    protected String            _identifier;
    protected String            _dnaSequence;
    protected String            _status; //Statuses: "Pending", "Good", "Bad"
    protected ArrayList<Clone>  _clones;

    public Construct(String identifier)
    {
        if( identifier != null && identifier.length() > 0)
        {
            _identifier = identifier;
        }
        else
        {
            //TODO Manage null case
        }

        _clones = new ArrayList<Clone>();
        _status = "Pending";
    }

    public String getIdentifier()
    {
        return _identifier;
    }

    public String getDnaSequence()
    {
        return _dnaSequence;
    }

    public void setDnaSequence(String dnaSequence)
    {
        _dnaSequence = dnaSequence;
    }

    public String getStatus()
    {
        return _status;
    }

    public void setStatus(String status)
    {
        if(status != null && status.length() > 0)
        {
            //Should _status refer to a copy of status?
            _status = status;
        }
    }

    public void addClone(Clone clone)
    {
        if(clone != null)
        {
            _clones.add(clone);
        }
    }

    public ArrayList<Clone> getClones()
    {
        return _clones;
    }

    public String[][] generateClonesArray()
    {
        Clone clone;
        int rows = _clones.size();
        String[][] clonesArray = new String[rows][2];

        for(int i = 0; i < rows; ++i)
        {
            clone = _clones.get(i);
            clonesArray[i][0] = clone.getIdentifier();
            clonesArray[i][1] = clone.getStatus();
        }

        return clonesArray;
    }
}
