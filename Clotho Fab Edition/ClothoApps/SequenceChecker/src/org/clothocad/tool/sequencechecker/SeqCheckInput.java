package org.clothocad.tool.sequencechecker;

import java.io.File;
import java.util.ArrayList;

public class SeqCheckInput {

    private Object[][] table; //table form of the data
    private int numberOfElements; //stores the number of trace files held in table
    private ArrayList<File> files; //holds all the file objects

    public SeqCheckInput(ArrayList<File> fileList) {
        table = new Object[fileList.size()][4];
        numberOfElements = fileList.size();
        files = fileList;
        int i = 0;
        for (File file : fileList) {
            table[i][0] = file.getName();
            String[] tokens = file.getName().split("[\\p{Punct}]");
            table[i][1] = tokens[0]; //construct name should appear before first underscore or period
            table[i][3] = tokens[tokens.length - 2];
            try {
                Integer.parseInt(tokens[1]);
                table[i][2] = tokens[1]; //second token should contain the clone number
            } catch (NumberFormatException e) {
                table[i][2] = null;
            }
            i++;
        }
    }
    //Iterates through contents of object, printing a 1 line summary for each trace file

    public void print() {
        for (int i = 0; i < table.length; i++) {
            System.out.println("trace: " + table[i][0] + " construct: " + table[i][1] + " cloneNumber: " + table[i][2] + " wellNumber: " + table[i][3]);
        }
    }
    //returns the number of files in the selected directory

    public int getNumberOfElements() {
        return numberOfElements;
    }
    //returns the trace array

    public String[] getTraces() {
        String[] toReturn = new String[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            toReturn[i] = (String) table[i][0];

        }
        return toReturn;
    }
    //returns the constructName array

    public String[] getConstructNames() {
        String[] toReturn = new String[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            toReturn[i] = (String) table[i][1];

        }
        return toReturn;
    }
    //returns the cloneNumber array

    public String[] getCloneNumbers() {
        String[] toReturn = new String[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            toReturn[i] = (String) table[i][2];

        }
        return toReturn;
    }
    //returns the wellNumber array

    public String[] getWellNumbers() {
        String[] toReturn = new String[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            toReturn[i] = (String) table[i][3];

        }
        return toReturn;
    }
    //Returns the file paths of each trace file in table
    public String[] getfilePaths() {
        String[] toReturn = new String[numberOfElements];
        int i = 0;
        for (File file : files) {
            toReturn[i] = file.getPath();
            i++;
        }
        return toReturn;
    }

    //returns all File objects represented in the table
    public ArrayList<File> getFiles() {
        return files;
    }

    public Object[][] getTable() {

        return table;
    }
}
