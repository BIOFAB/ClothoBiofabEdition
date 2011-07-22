/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocad.tool.primerdesigner;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Annotation;
import org.clothocore.api.data.Collection;
import org.clothocore.api.data.Feature;
import org.clothocore.api.data.NucSeq;
import org.clothocore.api.data.Oligo;
import org.clothocore.util.chooser.ClothoOpenChooser;
import org.openide.windows.TopComponent;

/**
 *
 * @author Jenhan Tao
 * Performs primer design calculations
 */
public class PrimerDesignController {

    /**
     * constructor links controller to the view
     * @param s
     */
    public PrimerDesignController(DesignFrame d) {
        isTC = true;
        _frameView = d;
        _textField = d.getSequenceTextField();
        _ns = new NucSeq("");
        final JComponent guiContentPane = (JComponent) _frameView.getContentPane();
//            JRootPane guiRootPane = _frameView.getRootPane();
        final JMenuBar menu = _frameView.getJMenuBar();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                _tcView = new TopComponent();
                _tcView.setLayout(new BorderLayout());
                JScrollPane sp = new JScrollPane(guiContentPane);
                _tcView.add(menu, BorderLayout.NORTH);
                _tcView.add(sp, BorderLayout.CENTER);
                _tcView.setName("Primer Designer");
                _tcView.open();
                _tcView.requestActive();

            }
        });
    }

    public String getSequence() {
        return _sequence;
    }

    public void setSequence(String s) {
        _sequence = s;
        _textField.setText(s);
        ((DesignFrame) _frameView).updateLabels();

    }

    /**
     * 
     * Primers of length +/-3 of target length are generated
     * @param seq
     * @param tm
     * @param length
     * @param insert1
     * @param insert2
     * @param spacer1
     * @param spacer2
     */
    public void generatePrimers(String seq, Double tm, int length, String insert1, String insert2, String spacer1, String spacer2) {
//        System.out.println("Sequence: " + seq);
//        System.out.println("Target tm: " + tm);
//        System.out.println("Target length: " + length);
//        System.out.println("Inserts: " + insert1 + ", " + insert2);
//        System.out.println("Spacers: " + spacer1 + ", " + spacer2);
        ArrayList<String> fwdSequences = new ArrayList<String>();
        ArrayList<String> revSequences = new ArrayList<String>();
        ArrayList<Double> fwdG = new ArrayList<Double>();
        ArrayList<Double> revG = new ArrayList<Double>();
        if (spacer1 == null) {
            spacer1 = "";
        }
        if (spacer2 == null) {
            spacer2 = "";
        }
        if (seq.length() < 60) {
            String[] yesNoOpt = {"Yes", "No"};
            if (javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Template is less than 60 basepairs in length.\nProceed anyways?", "Primer Designer: Warning", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, yesNoOpt, yesNoOpt[1]) == 1) {
                return;
            }
            if (seq.length() < 20) {
                JOptionPane.showMessageDialog(new JFrame(), "Sequence is too short to generate primers", "Primer Designer: Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (length < 15) {
            String[] yesNoOpt = {"Yes", "No"};
            if (javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Preferred primer length may be too short to allow for specific annealing.\nProceed anyways?", "Primer Designer: Warning", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, yesNoOpt, yesNoOpt[1]) == 1) {
                return;
            }
            if (length < 4) {
                length = 4; //min target length allowed
            }
        }
        if (spacer1.length() > 5 || spacer2.length() > 5) {
            String[] yesNoOpt = {"Yes", "No"};
            if (javax.swing.JOptionPane.showOptionDialog(new JFrame(), "Spacers seem to be large.\nProceed anyways?", "Primer Designer: Warning", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, yesNoOpt, yesNoOpt[1]) == 1) {
                return;
            }
        }
        String revPrimer = "";
        String fwdPrimer = "";
        //generates primer sequences with length +/-3 of target length excluding the insert length
        for (int i = length - 3; i <= length + 3; i++) {
            fwdPrimer = this.complementSequence(seq.substring(0, i));
            fwdG.add(calcDeltaG(fwdPrimer));
            if (!insert1.equalsIgnoreCase("none")) {
                Feature afeat = Feature.retrieveByName(insert1);
                fwdPrimer = this.complementSequence(afeat.getSeq().toString()) + fwdPrimer;
            }
            fwdPrimer = spacer1 + fwdPrimer;
            fwdSequences.add(fwdPrimer.toUpperCase());
            revPrimer = this.flip(seq.substring(seq.length() - i));
            revG.add(calcDeltaG(revPrimer));
            if (!insert2.equalsIgnoreCase("none")) {
                Feature afeat = Feature.retrieveByName(insert2);
                revPrimer = this.flip(afeat.getSeq().toString()) + revPrimer;
            }
            revPrimer = spacer2 + revPrimer;
            revSequences.add(revPrimer.toUpperCase());
        }
        PrimerResultFrame prf = new PrimerResultFrame(this, fwdSequences, revSequences, fwdG, revG);
        prf.pack();
        prf.setVisible(true);
    }

    /**
     * Saves two arraylists of sequences as Clotho oligos uses nameRoot as the base of the name
     * @param sequences
     * @param nameRoot
     */
    public void saveAll(ArrayList<String> fwdseq, ArrayList<String> revseq, String nameRoot, String collectionName) {
        Collection saveTo = Collection.retrieveByName(collectionName);
        if (saveTo != null) {
            for (int i = 0; i < fwdseq.size(); i++) {
                Oligo ol = new Oligo(nameRoot + "F" + i, "primer", Collector.getCurrentUser(), fwdseq.get(i));
                saveTo.addObject(ol);
            }
            for (int i = 0; i < revseq.size(); i++) {
                Oligo ol = new Oligo(nameRoot + "R" + i, "primer", Collector.getCurrentUser(), revseq.get(i));
                saveTo.addObject(ol);
                ol.saveDefault();

            }
            saveTo.saveDefault();
        }
    }

    /**
     * Complents input string
     * @param s
     * @return
     */
    public String complementSequence(String s) {
        if (s != null) {
            _ns.changeSeq(s);
            String seq = _ns.revComp();
            String temp = "";
            for (int j = seq.length() - 1; j > -1; j--) {
                temp = temp + seq.charAt(j);
            }
            return temp;
        }
        return null;
    }

    /**
     * flips input string
     * @param s
     * @return
     */
    public String flip(String s) {
        if (s != null) {
            String temp = "";
            for (int j = s.length() - 1; j > -1; j--) {
                temp = temp + s.charAt(j);
            }
            return temp;
        }
        return null;
    }

    /**
     * Checks sequence for restriction sites.
     * @param seq sequences being scanned
     * @return a hashmap containing restriction sites indices, key is start, value is end
     */
    public HashMap<Integer, Integer> checkForRESites() {
        if (_sequence == null) {
            return null;
        }
        _ns.changeSeq(_sequence);
        HashMap<Integer, Integer> toReturn = new HashMap<Integer, Integer>();
        HashSet<Annotation> hs = _ns.getAnnotations();

        for (Annotation an : hs) {
            if (an.getFeature().getSearchTags().contains("restriction enzyme")) {
                toReturn.put(an.getStart(), an.getEnd());
            }
        }
        if (toReturn.isEmpty()) {
            toReturn = null;
        }
        return toReturn;
    }

    public Double calcMeltingTemp(String seq) {
        _ns.changeSeq(seq);
        Double toReturn = _ns.meltingTemp();
        return toReturn;
    }

    public Double calcGCContent(String seq) {
        _ns.changeSeq(seq);
        Double toReturn = _ns.gcContent()[1];
        return toReturn;
    }

    /**
     * Calculates delta g value for input string, representing a dna sequence, using nearest neighbor method
     * Delta g calculations taken from "A unified view of polymer, dumbbell, and oligonucleotide DNA nearest-neighbor "
     * @param s
     * @return
     */
    public static Double calcDeltaG(String s) {
        Double toReturn = 0.0;
        if (s != null) {
            s = s.toUpperCase();
            if (s.startsWith("C") || s.startsWith("G")) {
                toReturn = toReturn + 0.98;
            } else {
                toReturn = toReturn + 1.03;
            }
            if (s.endsWith("C") || s.endsWith("G")) {
                toReturn = toReturn + 0.98;
            } else {
                toReturn = toReturn + 1.03;
            }
            for (int i = 0; i < s.length() - 1; i++) {
                String token = s.substring(i, i + 2);
                if (token.equals("AA") || token.equals("TT")) {
                    toReturn = toReturn - 1.00;
                } else if (token.equals("AT")) {
                    toReturn = toReturn - 0.88;
                } else if (token.equals("TA")) {
                    toReturn = toReturn - -0.58;
                } else if (token.equals("CA") || token.equals("AC")) {
                    toReturn = toReturn - 1.45;
                } else if (token.equals("GT") || token.equals("TG")) {
                    toReturn = toReturn - 1.44;
                } else if (token.equals("CT") || token.equals("TC")) {
                    toReturn = toReturn - 1.28;
                } else if (token.equals("GA") || token.equals("AG")) {
                    toReturn = toReturn - 1.30;
                } else if (token.equals("CG")) {
                    toReturn = toReturn - 2.17;
                } else if (token.equals("GC")) {
                    toReturn = toReturn - 2.24;
                } else if (token.equals("GG") || token.equals("CC")) {
                    toReturn = toReturn - 1.84;
                }
            }
            return toReturn;
        }

        return 0.00;
    }

    public String checkForDimers(String fwdSeq, String revSeq, Double fwdG, Double revG) {
        String result = "";
        String alignString = "";
        Pattern p;
        Matcher m;
        Double dG = 0.0;
        //check for self dimers
        result = result + "SELF DIMERS\nfwd primer:";
        alignString = Aligner.align(fwdSeq, fwdSeq);
        p = Pattern.compile("[|]+");
        m = p.matcher(dimerCheckHelper(alignString));
        while (m.find()) {
            dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
        }
        result = result + "\nDelta G: " + dG;

        if (dG.intValue() <= fwdG.intValue()) {
            result = result + "\nWarning: self dimer is likely in forward primer";
        }

        result = result + "\n" + alignString;
        result = result + "\nDelta G: " + dG;
        result = result + "\nrev primer:";
        dG = 0.0;
        alignString = Aligner.align(revSeq, revSeq);
        p = Pattern.compile("[|]+");
        m = p.matcher(dimerCheckHelper(alignString));
        while (m.find()) {
            dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
        }

        if (dG.intValue() <= revG.intValue()) {
            result = result + "\nWarning: self dimer is likely in reverse primer";
        }

        result = result + "\n" + alignString;
        result = result + "\nDelta G: " + dG;

        //check for dimer with other primer
        result = result + "\nrev primer:";
        dG = 0.0;
        alignString = Aligner.align(fwdSeq, revSeq);

        p = Pattern.compile("[|]+");
        m = p.matcher(dimerCheckHelper(alignString));
        while (m.find()) {
            dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
        }

        if ((dG.intValue() <= fwdG.intValue() || dG.intValue() < revG.intValue())) {
            result = result + "\nWarning: Dimerization is likely to occur between forward and reverse primer";
        }

        result = result + "\n" + alignString;
        result = result + "\nDelta G: " + dG;

        //Check for hairpins
        result = result + "\nHairpins:\nfwd primer:";

        for (int i = 0; i < fwdSeq.length(); i++) {
            dG = 0.0;
            alignString = Aligner.align(fwdSeq.substring(0, fwdSeq.length() - i), fwdSeq.substring(i));
            p = Pattern.compile("[|]+");
            m = p.matcher(dimerCheckHelper(alignString));
            while (m.find()) {
                dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
            }

            if (dG.intValue() <= fwdG.intValue()) {
                result = result + "\nWarning: Forward primer is likely to form a hairpin";
                result = result + "\n" + alignString;
                result = result + "\nDelta G: " + dG;

            }

        }
        result = result + "\nrev primer:";

        for (int i = 0; i < revSeq.length(); i++) {
            dG = 0.0;
            alignString = Aligner.align(revSeq.substring(0, revSeq.length() - i), revSeq.substring(i));
            p = Pattern.compile("[|]+");
            m = p.matcher(dimerCheckHelper(alignString));
            while (m.find()) {
                dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
            }
            if (dG.intValue() <= revG.intValue()) {
                result = result + "\nWarning: Reverse primer is likely to form a hairpin";
                result = result + "\n" + alignString;
                result = result + "\nDelta G: " + dG;
            }


        }
        //check against template for portential match
        result = result + "\nMISMATCH AGAINST TEMPLATE:\nfwd primer:";
        dG = 0.0;
        alignString = Aligner.align(fwdSeq, _sequence);
        p = Pattern.compile("[|]+");
        m = p.matcher(dimerCheckHelper(alignString));
        while (m.find()) {
            dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
        }
        if (dG.intValue() <= fwdG.intValue()) {
            result = result + "\nWarning: Forward primer is likely to mismatch against the template";
        }

        result = result + "\n" + alignString;
        result = result + "\nDelta G: " + dG;
        result = result + "\nrev primer:";
        dG = 0.0;
        alignString = Aligner.align(revSeq, _sequence);
        p = Pattern.compile("[|]+");
        m = p.matcher(dimerCheckHelper(alignString));
        while (m.find()) {
            dG = dG + calcDeltaG(alignString.substring(m.start() - 1, m.end() - 1));
        }
        if (dG.intValue() <= revG.intValue()) {
            result = result + "\nWarning: Reverse primer is likely to mismatch against the template";
        }

        result = result + "\n" + alignString;
        result = result + "\nDelta G: " + dG;

        return result;
    }

    private static String dimerCheckHelper(String s) {
        int first = 0;
        int second = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i, i + 1).matches("\\n")) {
                if (first == 0) {
                    first = i;
                } else {
                    second = i;
                }

            }
        }
        return (s.substring(first, second));
    }

    public void switchViews() {
        if (isTC) {
            Component[] components = _tcView.getComponents();
//            for (int i = 0; i < components.length; i++) {
//                System.out.println(components[i]);
//            }
            _frameView = new JFrame();
            _frameView.setContentPane((Container) components[1]);
            _frameView.setJMenuBar((JMenuBar) components[0]);
            _frameView.pack();
            _frameView.setVisible(true);
            isTC = false;
            _tcView.close();
        } else {
            final JComponent guiContentPane = (JComponent) _frameView.getContentPane();
//            JRootPane guiRootPane = _frameView.getRootPane();
            final JMenuBar menu = _frameView.getJMenuBar();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    _tcView = new TopComponent();
                    _tcView.setLayout(new BorderLayout());
                    JScrollPane sp = new JScrollPane(guiContentPane);
                    _tcView.add(menu, BorderLayout.NORTH);
                    _tcView.add(sp, BorderLayout.CENTER);
                    _tcView.setName("Primer Designer");
                    _tcView.open();
                    _tcView.requestActive();

                }
            });
            _frameView.setVisible(false);
            isTC = true;
        }

    }

    public void close() {
        if (isTC) {
            _tcView.close();
        } else {
            _frameView.dispose();
        }
    }

    public void validateKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        return;
    }

    public void validateKeyTyped(KeyEvent evt) {
        if (evt.getKeyChar() != 'a'
                && evt.getKeyChar() != 'g'
                && evt.getKeyChar() != 't'
                && evt.getKeyChar() != 'c'
                && evt.getKeyChar() != 'A'
                && evt.getKeyChar() != 'G'
                && evt.getKeyChar() != 'T'
                && evt.getKeyChar() != 'C') {
            evt.consume();
        }
    }

    public void validateNumTyped(KeyEvent evt) {
        if (evt.getKeyChar() != '1'
                && evt.getKeyChar() != '2'
                && evt.getKeyChar() != '3'
                && evt.getKeyChar() != '4'
                && evt.getKeyChar() != '5'
                && evt.getKeyChar() != '6'
                && evt.getKeyChar() != '7'
                && evt.getKeyChar() != '8'
                && evt.getKeyChar() != '9'
                && evt.getKeyChar() != '0') {
            evt.consume();
        }

    }

    public void loadSequence() {
        FileNameExtensionFilter fastaFilter = new FileNameExtensionFilter("FASTA File", "fa", "mpfa", "fna", "fsa", "fas", "fasta");
        FileNameExtensionFilter genbankFilter = new FileNameExtensionFilter("GenBank File", "gen", "gb", "gbank", "genbank");
        if (!_fileOpenerInstantiated) {
            _fileOpener = new ClothoOpenChooser("Load Sequence");
            _fileOpener.getFileChooser().addChoosableFileFilter(fastaFilter);
            _fileOpener.getFileChooser().addChoosableFileFilter(genbankFilter);
            _fileOpener.getFileChooser().setFileFilter(_fileOpener.getFileChooser().getAcceptAllFileFilter());
            _fileOpener.setTitle("Open a Sequence...");
            _fileOpenerInstantiated = true;
        }
        _fileOpener.open_Window();

        if (_fileOpener.fileSelected) {
            loadSequence(_fileOpener.getFile());
        }
    }

    public void loadSequence(File toLoad) {
        String toSeqView = "";
        if (toLoad.exists()) {
            try {
                java.io.BufferedReader inFile = new java.io.BufferedReader(new java.io.FileReader(toLoad));
                String line = inFile.readLine();

                // Reads in a FASTA format file
                if (line.startsWith(">")) {
                    line = line.substring(1, line.length());
                    line = inFile.readLine();
                    while (line != null) {
                        toSeqView = toSeqView + line.trim();
                        line = inFile.readLine();
                    }
                    setSequence(toSeqView);

                } // Read in a Genbank format file
                else if (line.startsWith("LOCUS")) {
                    while (line != null) {
                        if (line.startsWith("ORIGIN")) {
                            line = inFile.readLine().trim();
                            while (!(line.startsWith("//"))) {
                                ArrayList<String> seq = new ArrayList(Arrays.asList(line.split(" ")));
                                for (int i = 1; i < seq.size(); i++) {
                                    toSeqView = toSeqView + seq.get(i);
                                }
                                line = inFile.readLine().trim();
                            }
                        }

                        line = inFile.readLine();
                    }
                    setSequence(toSeqView);

                } else {
                    String[] yesNoOpt = {"Yes", "No"};
                    if (javax.swing.JOptionPane.showOptionDialog(new JFrame(), "This does not appear to be a Genbank or FASTA formated file.\n Do you want to proceed?", "Clotho: Sequnce View", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, yesNoOpt, yesNoOpt[1]) == javax.swing.JOptionPane.YES_OPTION) {
                        while (line != null) {
                            toSeqView = toSeqView + line.trim();
                            line = inFile.readLine();
                        }

                        setSequence(toSeqView);

                        System.out.println("WARNING: User decided to open a file that is not in FASTA or Genbank format");
                    } else {
                        System.out.println("ERROR: User decided to cancel operation since the file is not in FASTA or Genbank format");
                    }
                }

                inFile.close();
            } catch (java.io.IOException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }

            // Sets the openChooser to open up at the location of the last
            // opened file
        } else {
            System.out.println("File does not exist!");
        }

    }

    public void updateSequence(String s) {
        _sequence = s;
    }
    private JTextField _textField;
    private String _sequence;
    private JFrame _frameView;
    private boolean isTC;
    private TopComponent _tcView;
    private NucSeq _ns;
    private boolean _fileOpenerInstantiated;
    private ClothoOpenChooser _fileOpener;
}
