package org.clothocad.tool.sequencechecker;
//Will contain code that will call the Sequence Analyzer of the Anderson lab

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import javax.swing.JPanel;

//import org.python.core.PyException;
//import org.python.core.PyInteger;
//import org.python.core.PyObject;
//import org.python.util.PythonInterpreter;

import org.openide.util.Exceptions;

import sequencing.ABITrace;
import sequencing.Analyzer;
import sequencing.TraceExtract.ResultType;

public class SeqCheckController
{
    String  _webServiceBaseUrl;

    public SeqCheckController()
    {
        //_webServiceBaseUrl = "http://localhost:8080/ElectronicDatasheets/constructs";
        _webServiceBaseUrl = "http://biofab.jbei.org/services/data/constructs";

    }

    public JPanel check(Construct construct)
    {
        //File                        traceFile = null;
        ABITrace                    traceABI = null;
        Analyzer                    analyzer = null;
        JPanel                      panel = null;
        ArrayList<SequencingResult> seqResults;
        ArrayList<ABITrace>         traces;
        //Clone                       clone;
        String                      refSequence;

        refSequence = this.fetchConstructSequence(construct.getIdentifier());

        if(refSequence != null && refSequence.length() > 0)
        {
            construct.setDnaSequence(refSequence);
            seqResults = construct.getClones().get(0).getSequencingResults();
            traces = new ArrayList<ABITrace>();
            
            for(SequencingResult seqResult:seqResults)
            {
                try
                {
                    traceABI = new ABITrace(seqResult.getTraceFile());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                
                traces.add(traceABI);
            }
            
            analyzer = new Analyzer(traces, refSequence);
            ResultType rep = analyzer.go(); //runs the analysis and stores the result, i'm not sure wat ResultType is used fo
            panel = analyzer.returnReport();
        }
        
        return panel;
    }
        
    protected String fetchConstructSequence(String constructID)
    {
        InputStream         inputStream = null;
        URL                 url = null;
        HttpURLConnection   connection = null;
        String              seq = "";
        
        if(constructID != null && constructID.length() > 0)
        {
            try
            {
                url = new URL(_webServiceBaseUrl + "?id=" + constructID + "&format=seq");
                inputStream = url.openStream();
                seq = new Scanner(inputStream).useDelimiter("\\A").next();
            }
            catch (MalformedURLException ex)
            {
                Exceptions.printStackTrace(ex);
            }
            catch (IOException ex)
            {
                Exceptions.printStackTrace(ex);
            }
            finally
            {
                try
                {
                    if(inputStream != null)
                    {
                        inputStream.close();
                    }
                }
                catch (IOException ex)
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        return seq;
    }

//    public void testPython()
//    {
//        // Create an instance of the PythonInterpreter
//        PythonInterpreter interp = new PythonInterpreter();
//
//        // The exec() method executes strings of code
//        interp.exec("import sys");
//        interp.exec("print sys");
//
//        // Set variable values within the PythonInterpreter instance
//        interp.set("a", new PyInteger(42));
//        interp.exec("print a");
//        interp.exec("x = 2+2");
//
//        // Obtain the value of an object from the PythonInterpreter and store it
//        // into a PyObject.
//        PyObject x = interp.get("x");
//        System.out.println("x: " + x);
//    }
}
