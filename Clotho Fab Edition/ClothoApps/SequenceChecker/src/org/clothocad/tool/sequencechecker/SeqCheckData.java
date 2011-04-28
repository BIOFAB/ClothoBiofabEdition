package org.clothocad.tool.sequencechecker;

import java.io.File;
import java.util.ArrayList;

public class SeqCheckData {

    protected Object[][]        table; //table form of the data
    protected int               numberOfElements; //stores the number of trace files held in table
    protected ArrayList<File>   files; //holds all the file objects

    public SeqCheckData(ArrayList<File> fileList)
    {
        table = new Object[fileList.size()][4];
        numberOfElements = fileList.size();
        files = fileList;
        int i = 0;

        for (File file : fileList)
        {
            table[i][3] = file.getName(); //Trace file names
            String[] tokens = file.getName().split("[_.]");
            table[i][2]=tokens[2];
            for (String token : tokens) {
                /*if (token.toLowerCase().matches("[a-zA-Z]{1}\\d{1,}")) {
                    table[i][2] = token; //Primer name
                }*/
                if (token.toLowerCase().matches("[a-zA-Z]*?\\d{2,}?") && table[i][1] == null && token != table[i][3]) {
                    table[i][0] = token; //Construct
                }
                if (token.toLowerCase().matches("\\d+")) {
                    table[i][1] = token; //Clone number
                }
            }

            i++;
        }
    }
    //Iterates through contents of object, printing a 1 line summary for each trace file

    public void print()
    {
        for (int i = 0; i < table.length; i++)
        {
            System.out.println("trace: " + table[i][0] + " construct: " + table[i][1] + " cloneNumber: " + table[i][2] + " wellNumber: " + table[i][3]);
        }
    }
    //returns the number of files in the selected directory

    public int getNumberOfElements()
    {
        return numberOfElements;
    }
    //returns the trace array

    public String[] getTraces()
    {
        String[] toReturn = new String[numberOfElements];

        for (int i = 0; i < numberOfElements; i++)
        {
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
