/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.tool.sequencechecker;

import java.io.File;

/**
 *
 * @author cesarr
 */
public class Primer
{
    protected String    _identifier;
    protected String    _status;
    protected File      _traceFile;

    
    public Primer(String identifier, File traceFile)
    {
        _identifier = identifier;
        _traceFile = traceFile;
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
        _status = status;
    }

    /**
     * @return the _traceFile
     */
    public File getTraceFile()
    {
        return _traceFile;
    }
}
