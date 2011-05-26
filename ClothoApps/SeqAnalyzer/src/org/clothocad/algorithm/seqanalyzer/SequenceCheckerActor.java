/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocad.algorithm.seqanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.RunStatus;
import org.clothocore.api.actor.data.StringToken;
import org.clothocore.api.actor.data.ListToken;
import org.clothocore.api.actor.io.InputPort;
import org.clothocore.api.actor.io.OutputPort;
import org.clothocad.algorithm.seqanalyzer.sequencing.seqAnalysis;
import org.clothocad.algorithm.seqanalyzer.sequencing.ABITrace;
import org.clothocore.api.actor.data.BooleanToken;
import org.clothocore.api.actor.data.ObjBaseToken;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Person;

/**
 *
 * @author benjaminbubenheim
 */
public class SequenceCheckerActor extends Actor {

    public SequenceCheckerActor(){
        _seq = new InputPort<ObjBaseToken>(this, ObjBaseToken.class);      //Make that a NucSeq
        _seqName = new InputPort<StringToken>(this, StringToken.class);  //Make that an ObjBase
        _author = new InputPort<ObjBaseToken>(this, ObjBaseToken.class);   //Make that a Person
        _abis = new InputPort<ListToken>(this, ListToken.class);  //Make that a List of files
        _throwResult = new InputPort<BooleanToken>(this, BooleanToken.class); //Whether or not to throw result gui

        _result = new OutputPort<StringToken>(this, StringToken.class);
    }

    /**
     * Does validation of the ObjBases put into each port
     * @return
     */
    @Override
    public boolean isReady() {
        for(InputPort inp : _inputs) {
            if(!inp.hasData()) {
                return false;
            }
        }
        try {
            if(!_seq.get().getData().getType().equals(ObjType.NUCSEQ)) {
                return false;
            }
            if(!_author.get().getData().getType().equals(ObjType.PERSON)) {
                return false;
            }

            List files = _abis.get().getData();
            for(Object obj : files) {
                File afile = (File) obj;
                if(!afile.exists()) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public RunStatus run() {
        if ( !isReady() ) {
            return RunStatus.NOT_READY_ERROR;
        }

        NucSeq nseq = (NucSeq) _seq.get().getData();
        String seq = nseq.getSeq();
        Person author = (Person) _author.get().getData();
        String authorname = author.getDisplayName();
        StringToken seqName = _seqName.get();

        ListToken<File> abis = _abis.get();
        ArrayList<ABITrace> traces = new ArrayList<ABITrace>();
        for(File afile : abis) {
            try {
                ABITrace atrace = new ABITrace(afile);
                traces.add(atrace);
                System.out.print("Added a trace for " + afile.getName());
                System.out.println(" with length " + atrace.getTraceLength());
            } catch (IOException ex) {
                System.out.println();
            }
        }

        _seq.clear();
        _author.clear();
        _seqName.clear();

        seqAnalysis s = new seqAnalysis(traces, seq, authorname, seqName.getData(), _throwResult.get()._data);

        _result.put(new StringToken(s.getResult()));

        return RunStatus.COMPLETE;
    }

    @Override
    public String getName() {
        return "Sequence Checker";
    }
    //Variables
    private InputPort<ObjBaseToken> _seq, _author;
    private InputPort<StringToken> _seqName;
    private InputPort<ListToken> _abis;
    private InputPort<BooleanToken> _throwResult;
    private OutputPort<StringToken> _result;
}
