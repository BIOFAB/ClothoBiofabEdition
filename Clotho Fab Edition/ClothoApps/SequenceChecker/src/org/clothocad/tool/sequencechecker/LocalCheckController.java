package org.clothocad.tool.sequencechecker;
//Will contain code that will call the Sequence Analyzer of the Anderson lab

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import sequencing.ABITrace;
import sequencing.Analyzer;
import sequencing.TraceExtract.ResultType;

public class LocalCheckController
{

    String  _target;
    File    _trace;

    public LocalCheckController()
    {
        
    }

    public void performCheck(File traceFile, String refSequence)
    {
        ABITrace traceABI = null;
        
        try
        {
            traceABI = new ABITrace(traceFile);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        ArrayList<ABITrace> abis = new ArrayList<ABITrace>(); //The analyzer seems to be able to handle multiple traces, i'm not sure what this does
        abis.add(traceABI);
        Analyzer analysis = new Analyzer(abis, refSequence);
        ResultType rep = analysis.go(); //runs the analysis and stores the result, i'm not sure wat ResultType is used for
        analysis.launchReport(); //Opens a graphical result window

    }
        //method is the same as perormCheck except that the Jpanel that is displayed is returned instead of displayed immediately
        public JPanel getCheckPanel(File traceFile, String refSequence) {
        ABITrace traceABI = null;

        try
        {
            traceABI = new ABITrace(traceFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<ABITrace> abis = new ArrayList<ABITrace>(); //The analyzer seems to be able to handle multiple traces, i'm not sure what this does
        abis.add(traceABI);
        Analyzer analysis = new Analyzer(abis, refSequence);
        ResultType rep = analysis.go(); //runs the analysis and stores the result, i'm not sure wat ResultType is used for
        System.out.println("returning panel");
        return analysis.returnReport(); //Opens a graphical result window

    }
}
