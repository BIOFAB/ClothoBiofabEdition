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

package org.clothocore.util.blast;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.UUID;
import org.clothocore.api.data.NucSeq;
import org.clothocore.util.basic.FileUtils;
import org.clothocore.util.basic.ReaderInputStream;
import org.clothocore.util.xml.XMLParser;
import org.concordia.cs.common.ncbi.qblast.GetCommand;
import org.concordia.cs.common.ncbi.qblast.PutCommand;
import org.concordia.cs.common.ncbi.qblast.Runner;

/**
 *
 * @author J. Christopher Anderson
 */
public class BlastNCBIQuery {
    /**
     * Initiate a protein-input blast to get best match
     * 
     * @param protSeq the protein seq to query (in form of MSGQRIV...)
     * @param DB the database(s) to query
     */
    public BlastNCBIQuery(String protSeq, database DB, BlastRequester myRequester) {
        _myQueriedDatabase = DB;
        _myRequester = myRequester;
        querySeq = protSeq;
        queryType = "blastp";
    }

    /**
     * Initiate a nucleotide-input blast to get best match
     *
     * @param seq the NucSeq to query (in form of ATGACGATGC)
     * @param DB the database(s) to query
     */
    public BlastNCBIQuery(NucSeq seq, database DB, BlastRequester myRequester) {
        _myQueriedDatabase = DB;
        _myRequester = myRequester;
        querySeq = seq.toString();
        switch(_myQueriedDatabase) {
            case PDB:
                queryType = "blastx";
                break;
            case SWISSPROT:
                queryType = "blastx";
                break;
            case NR:
                queryType = "blastx";
                break;
            case NRNT:
                queryType = "blastn";
                break;
            default:
                queryType = "blastn";
                break;
        }
    }

    public void go() {
        //Put in the put command for blast
        PutCommand put_command = new PutCommand();
        put_command.setQuery(querySeq);
        put_command.setProgram(queryType);
        put_command.setIdentityPrecision(90);
        switch(_myQueriedDatabase) {
            case PDB:
                put_command.setDatabase("pdb");
                break;
            case NR:
                put_command.setDatabase("nr");
                break;
            case SWISSPROT:
                put_command.setDatabase("swissprot");
                break;
            case NRNT:
                System.out.println("nrnt working on " + querySeq);
                put_command.setDatabase("nr");
                break;
            default:
                break;
        }
        put_command.setHitListSize(1);


        //formulate the Get returner of data for Blast
        GetCommand get_command = new GetCommand() {
            @Override
            public void parseResults(Reader reader) throws Exception {
                returnBlast(reader, queryID);
            }
        };
        get_command.setFormatType("XML");

        //Submit the query
        Runner runner = new Runner(put_command, get_command);
        runner.start();
    }

    @SuppressWarnings("deprecation")
    private void returnBlast(Reader reader, String queryID) {

        if(reader==null) {
            System.out.println("Error occurred");
            return;
        }

        XMLParser myParser=null;
        StringBuffer tempXMLFileStr= new StringBuffer();
        ReaderInputStream RIS = new ReaderInputStream(reader);
            DataInputStream dis = new DataInputStream(RIS);
            String inputLine;

            try {
            inputLine = dis.readLine();
            while (inputLine != null) {
                if(inputLine.equals("")) {
                    break;
                }
                if(inputLine.startsWith("<?xml")) {
                    inputLine = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
                }
                if(inputLine.startsWith("<!DOCTYPE")) {
                    inputLine = "";
                }
                tempXMLFileStr.append(inputLine);
                tempXMLFileStr.append("\n");
            }
            dis.close();
        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }

        String filename = queryID + ".xml";
        FileUtils.writeFile(tempXMLFileStr.toString(), filename);

        //Read the file and access it in an xmlParser, then close it
        File xmlfile = new File(filename);
        myParser = new XMLParser(xmlfile, "BlastOutput" );

        String scoreString = myParser.getFirstTag("Hsp_score");
        if(scoreString.equals("")) {
            _wasSuccessful = false;
            _myRequester.receiveBlastResults((this));
            xmlfile.delete();
            return;
        }
        scoreInt = Integer.parseInt(scoreString);

        //From here down is specific to the particular database
        switch(_myQueriedDatabase) {
            case PDB:
                String hitid = myParser.getFirstTag("Hit_id");
                String[] pbdArray = hitid.split("\\|");
                bestHitID = pbdArray[3];
                break;
            default:
                bestHitID= myParser.getFirstTag("Hit_accession");
                break;
        }

        //If it was a blastx, get the frame etc.
        if(queryType.equals("blastx")) {
            String frame = myParser.getFirstTag("Hsp_query-frame");
            hitFrame = Integer.parseInt(frame);
        }

        String start = myParser.getFirstTag("Hsp_query-from");
        hitStartOnQuery = Integer.parseInt(start)-1;
        String end = myParser.getFirstTag("Hsp_query-to");
        hitEndOnQuery = Integer.parseInt(end);
        start = myParser.getFirstTag("Hsp_hit-from");
        hitStartOnHit = Integer.parseInt(start);
        end = myParser.getFirstTag("Hsp_hit-to");
        hitEndOnHit = Integer.parseInt(end);

        System.out.println("Score: " + scoreInt + "  Code:  " + bestHitID);
        myParser = null;
        xmlfile.delete();
        _wasSuccessful = true;
        _myRequester.receiveBlastResults((this));
    }

    public String getBestHitID() {
        return bestHitID;
    }

    public int getBestScore() {
        return scoreInt;
    }

    public String getQueryUUID() {
        return queryID;
    }

    public  database getDatabaseQueried() {
        return _myQueriedDatabase;
    }

    public boolean wasSuccessful() {
        return _wasSuccessful;
    }

    public int getFrame() {
        return hitFrame;
    }

    public int getStartOnQuery() {
        return hitStartOnQuery;
    }

    public int getEndOnQuery() {
        return hitEndOnQuery;
    }

    public int getStartOnHit() {
        return hitStartOnHit;
    }

    public int getEndOnHit() {
        return hitEndOnHit;
    }

/*-----------------
     variables
 -----------------*/
     final String queryID = UUID.randomUUID().toString();
     public enum database {NRNT, SWISSPROT, PDB, NR};
     private String querySeq="";
     private String queryType="";
     private String bestHitID="";
     
     private int scoreInt=0;
     private int hitFrame;
     private int hitStartOnQuery;
     private int hitEndOnQuery;
     private int hitStartOnHit;
     private int hitEndOnHit;

     private database _myQueriedDatabase;
     private BlastRequester _myRequester;
     private boolean _wasSuccessful;
}
