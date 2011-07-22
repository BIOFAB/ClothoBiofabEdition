/*
Copyright (c) 2009 The Regents of the University of California.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS..
 */
package org.clothocad.tool.sequenceview;

import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.ObjBase;
import org.clothocore.api.data.ObjType;
import org.clothocore.api.data.Oligo;
import org.clothocore.api.data.Part;
import org.clothocore.api.data.Plasmid;
import org.clothocore.api.data.Vector;
import org.clothocore.api.plugin.ClothoTool;
import org.clothocore.util.basic.UnknownKeywordException;

/**
 *
 * @author Douglas Densmore
 * @author Roza Ghamari
 * @author Jenhan Tao
 */
public class SequenceViewManager implements ClothoTool {

    public SequenceViewManager() {
        /*_sequenceViewArray = new ArrayList<SequenceView>();
        _sequenceViewArray.add(new SequenceView("SequenceView", "SequenceView", this, 0, _plugIns));
        _currentSequenceViewIndex = 0;
        _sequenceViewArray.get(_currentSequenceViewIndex).setTitle("Clotho: Sequence View (Address: " + _currentSequenceViewIndex + ") New Sequence");
        _help = new ClothoHelp();*/
    }

    public static void main(String[] args) {
        SequenceViewManager svm = new SequenceViewManager();
        svm.init();
        svm.launch();
    }

    public void init() {
        _sequenceViewArray = new ArrayList<SequenceView>();
        _sequenceViewArray.add(new SequenceView("SequenceView", "SequenceView", this, 0));
        _currentSequenceViewIndex = 0;
        _sequenceViewArray.get(_currentSequenceViewIndex).setTitle("Clotho: Sequence View (Address: " + _currentSequenceViewIndex + ") New Sequence");

    }

    public void launch() {
        if (!Collector.isConnected()) {
            JOptionPane.showMessageDialog(null, "Database connection required to launch this tool",
                    "Not connected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        _sequenceViewArray = new ArrayList<SequenceView>();
        _sequenceViewArray.add(new SequenceView("SequenceView", "SequenceView", this, 0));
        _currentSequenceViewIndex = 0;
        _sequenceViewArray.get(_currentSequenceViewIndex).setTitle("Clotho: Sequence View (Address: " + _currentSequenceViewIndex + ") New Sequence");
//        _sequenceViewArray.get(_currentSequenceViewIndex).getSequenceView().setVisible(true);
        _sequenceViewArray.get(_currentSequenceViewIndex).openTab();
        _sequenceViewArray.get(_currentSequenceViewIndex).getSequenceView().requestFocus();
    }

    public Object getData(String object, String field) throws UnknownKeywordException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendData(Object data, ClothoTool sender) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(SequenceView sv) {
        _sequenceViewArray.add(sv);
    }

    public boolean getCircular() {
        return _sequenceViewArray.get(_currentSequenceViewIndex).getCircular();

    }

    public SequenceView getCurrentSV() {
        return _sequenceViewArray.get(_currentSequenceViewIndex);
    }

    public boolean getDegeneracy() {
        return _sequenceViewArray.get(_currentSequenceViewIndex).getDegeneracy();
    }

    public int getIndex() {
        return _currentSequenceViewIndex;
    }

    public boolean getMethylated() {
        return _sequenceViewArray.get(_currentSequenceViewIndex).getMethylated();
    }

    public String getSequence() {
        return _sequenceViewArray.get(_currentSequenceViewIndex).getSequence();

    }

    public ArrayList<SequenceView> getSequenceViewArray() {
        return _sequenceViewArray;
    }

    public SequenceView getSpecificSV(int i) {
        return _sequenceViewArray.get(i);
    }

    public void setMainSequenceView(SequenceView sv) {
        _currentSequenceViewIndex = sv.getIndex();
    }
    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////
    private ArrayList<SequenceView> _sequenceViewArray;
//    private ClothoHelp _help;
    private int _currentSequenceViewIndex;
//    private HashMap<String, SequenceViewPlugInInterface> _plugIns;

    @Override
    public void launch(ObjBase o) {
        System.out.println("name: " + o.getName());
        System.out.println("type: " + o.getType());
        Boolean toLaunch = false;
        String sequence = null;
        if (o.getType().equals(ObjType.OLIGO)) {
            toLaunch = true;
            sequence = ((Oligo) Collector.get(ObjType.OLIGO, o.getUUID())).getSeq().toString();
        }
        if (o.getType().equals(ObjType.VECTOR)) {
            toLaunch = true;
            sequence = ((Vector) Collector.get(ObjType.VECTOR, o.getUUID())).getSeq().toString();

        }
        if (o.getType().equals(ObjType.PART)) {
            toLaunch = true;
            sequence = ((Part) Collector.get(ObjType.PART, o.getUUID())).getSeq().toString();

        }
        if (o.getType().equals(ObjType.PLASMID)) {
            toLaunch = true;
            sequence = ((Plasmid) Collector.get(ObjType.PLASMID, o.getUUID())).getSeq().toString();

        }
        if (o.getType().equals(ObjType.FEATURE)) {
            toLaunch = true;
            sequence = ((Feature) Collector.get(ObjType.FEATURE, o.getUUID())).getSeq().toString();

        }
        if (o.getType().equals(ObjType.NUCSEQ)) {
            toLaunch = true;
            sequence = ((NucSeq) Collector.get(ObjType.NUCSEQ, o.getUUID())).getSeq().toString();

        }
        if (toLaunch) {
//            if (_currentSequenceViewIndex<0) {
//                _currentSequenceViewIndex=0;
//            }
            _sequenceViewArray = new ArrayList<SequenceView>();
            _sequenceViewArray.add(new SequenceView("SequenceView", "SequenceView", this, _currentSequenceViewIndex));

            _sequenceViewArray.get(_currentSequenceViewIndex).setTitle("Clotho: Sequence View (Address: " + _currentSequenceViewIndex + ") " + o.getName());
            _sequenceViewArray.get(_currentSequenceViewIndex).setSequence(sequence);
            _sequenceViewArray.get(_currentSequenceViewIndex).getSequenceView().setVisible(true);
            _sequenceViewArray.get(_currentSequenceViewIndex).getSequenceView().requestFocus();
            _sequenceViewArray.get(_currentSequenceViewIndex).getSequenceView().getOutputTextArea().setText("Loaded Clotho object: " + o.getName());
            _currentSequenceViewIndex++;

        }
    }

    @Override
    public void close() {
        for (WeakReference<Window> wf : pig) {
            Window gui = (Window) wf.get();
            if (gui != null) {
                gui.dispose();
            }
        }
    }
    private HashSet<WeakReference<Window>> pig = new HashSet<WeakReference<Window>>();
}
