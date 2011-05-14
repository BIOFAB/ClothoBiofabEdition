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
public class SequencingResult
{
    protected String    _primer;
    protected String    _status;
    protected File      _traceFile;

    
    public SequencingResult(String primer, File traceFile)
    {
        _primer = primer;
        _traceFile = traceFile;
        _status = "Pending";
    }

    /**
     * @return the _primerName
     */
    public String getPrimer()
    {
        return _primer;
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
