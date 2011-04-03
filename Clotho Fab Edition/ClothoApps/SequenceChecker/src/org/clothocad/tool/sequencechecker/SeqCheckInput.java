package org.clothocad.tool.sequencechecker;

import java.io.File;
import java.util.ArrayList;

public class SeqCheckInput {

    private String[] trace;
    private String[] constructName;
    private String[] cloneNumber;
    private String[] wellNumber;

    public SeqCheckInput(ArrayList<File> fileList) {
        trace = new String[fileList.size()];
        constructName = new String[fileList.size()];
        cloneNumber = new String[fileList.size()];
        wellNumber = new String[fileList.size()];
        int i = 0;
        for (File file : fileList) {
            trace[i] = file.getName();
            String[] tokens = file.getName().split("[\\p{Punct}]");
            constructName[i] = tokens[0]; //construct name should appear before first underscore or period
            wellNumber[i] = tokens[tokens.length - 2];
            try {
                Integer.parseInt(tokens[1]);
                cloneNumber[i] = tokens[1]; //second token should contain the clone number
            } catch (NumberFormatException e) {
                cloneNumber[i] = null;
            }
            i++;
        }
    }
    //Iterates through contents of object, printing a 1 line summary for each trace file
    public void print() {
        for (int i = 0; i < trace.length; i++) {
            System.out.println("trace: " + trace[i] + " construct: " + constructName[i] + " cloneNumber: " + cloneNumber[i] + " wellNumber: " + wellNumber[i]);
        }
    }
    //returns the number of files in the selected directory
    public int numberOfElements() {
        return trace.length;
    }
}
