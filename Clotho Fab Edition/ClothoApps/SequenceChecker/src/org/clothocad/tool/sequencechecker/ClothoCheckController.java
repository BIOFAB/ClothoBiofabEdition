package org.clothocad.tool.sequencechecker;
//Will contain code that will call the Sequence Analyzer of the Anderson lab

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import sequencing.ABITrace;
import sequencing.Analyzer;
import sequencing.TraceExtract.ResultType;

public class ClothoCheckController {

    String target;
    File trace;

    public ClothoCheckController(File f, String s) {
        target = s;
        trace = f;
    }

    public void performCheck() {
        ABITrace traceABI = null;
        try {
            traceABI = new ABITrace(trace);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ABITrace> abis = new ArrayList<ABITrace>(); //The analyzer seems to be able to handle multiple traces, i'm not sure what this does
        abis.add(traceABI);
        Analyzer analysis = new Analyzer(abis, target);
        ResultType rep = analysis.go(); //runs the analysis and stores the result, i'm not sure wat ResultType is used for
        analysis.launchReport(); //Opens a graphical result window

    }
}
