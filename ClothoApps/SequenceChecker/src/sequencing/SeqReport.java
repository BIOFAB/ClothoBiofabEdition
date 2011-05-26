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

package sequencing;

import java.awt.AlphaComposite;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.GeneralPath;
import sequencing.TraceExtract.AlignedABI;

/**
 *
 * @author Ben Bubenheim
 */
public class SeqReport extends JPanel {


    public SeqReport(TraceExtract finalTarget, String author, String sName, String resultenum) {
        auth=author;
        sampleName=sName;
        extract = finalTarget;

        numAbis = extract.getABIList().size();

        //setting up window width
        double totalbases = finalTarget.getSeq().length();
        pixPerBase=600/totalbases;

        for(AlignedABI abi : extract.getABIList()) {
            System.out.println(abi.name + " " + abi.startOnTarget + " " + abi.endOnTarget);
        }
        System.out.println("start and end of target " + finalTarget.getMergeStart() + " " + finalTarget.getMergeEnd());
        //*****
        //pixPerBase=700/(double)t;
        //*****

        seqStart=finalTarget.getMergeStart()*pixPerBase;
        seqSize=(finalTarget.getMergeEnd()-finalTarget.getMergeStart())*pixPerBase;

        nextRect=(int)seqStart;
        result=resultenum;

        //calculate window size
        y=numAbis*(abiHeight+3)+200;

        imX=x;
        imY=y;
        disp();
    }

    private void disp(){
        frame = new JFrame("Sequencing Report");
        frame.getContentPane().add(this);
        frame.setPreferredSize(new Dimension(700,500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public void showReport(){
        disp();
    }

    @Override
    public void paintComponent(Graphics g) {
        //Initiate Graphics2D, it's composite, and font context
        g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);


        FontRenderContext context = g2.getFontRenderContext();
        Composite regularOverlay = g2.getComposite();

        int targetXStart = (int) (extract.getMergeStart() * pixPerBase);
        int targetWidth = (int) ((extract.getMergeEnd() - extract.getMergeStart()) * pixPerBase);

        //Draw the green box for the comparison sequence
        g2.setColor(green);
        Rectangle2D.Double readSeq=new Rectangle2D.Double(targetXStart, numAbis*(abiHeight+8), targetWidth, 15);
        g2.fill(readSeq);

        //Paint each mutation red box over the comparison sequence box
        short[] scores = extract.getBaseScores();
        for(int i=extract.getMergeStart(); i<extract.getMergeEnd(); i++) {
            switch(scores[i]) {
                case 1:
                    break;
                //For mutations
                case 0:
                    g2.setColor(Color.RED);
                    Rectangle2D.Double red=new Rectangle2D.Double(i*pixPerBase, numAbis*(abiHeight+8), 2*pixPerBase, 15);
                    g2.fill(red);
                    break;
                //For ambiguities
                case -1:
                    g2.setColor(Color.YELLOW);
                    Rectangle2D.Double yeller=new Rectangle2D.Double(i*pixPerBase, numAbis*(abiHeight+8), 2*pixPerBase, 15);
                    g2.fill(yeller);
                    break;
                //For insertions
                case 2:
                    g2.setColor(Color.BLACK);
                    Rectangle2D.Double blacker=new Rectangle2D.Double(i*pixPerBase, numAbis*(abiHeight+8), 2*pixPerBase, 15);
                    g2.fill(blacker);
                    break;
                //For deletions
                case 3:
                    g2.setColor(Color.WHITE);
                    Rectangle2D.Double whiter=new Rectangle2D.Double(i*pixPerBase, numAbis*(abiHeight+8), 2*pixPerBase, 15);
                    g2.fill(whiter);
                    break;
            }
        }

        nextRect=(int)seqStart;

        //Add ruler underneath the comparison sequence
        int tickSize=50;
        if(extract.getSeq().length()>1000){
            tickSize=100;
        }
        double ticktemp=(extract.getMergeEnd()-extract.getMergeStart())/(double)tickSize;
        int ticks=0;
        if(ticktemp==(int)ticktemp){
            ticks=(int)ticktemp;
        }
        else{
            ticks=((int)ticktemp)+1;
        }

        int rulerL=(int)((ticks*tickSize)*pixPerBase);
        g2.setColor(Color.black);
        Line2D.Double ruler=new Line2D.Double(targetXStart, numAbis*(abiHeight+6)+25, seqStart+rulerL, numAbis*(abiHeight+6)+25);
        g2.draw(ruler);

        //Draw the ticks and labels below them
        for(int i=0; i<ticks+1; i++){
            //Put in tick lines
            Line2D.Double temp=new Line2D.Double((i*tickSize*pixPerBase)+targetXStart, numAbis*(abiHeight+6)+25, (i*tickSize*pixPerBase)+seqStart, numAbis*(abiHeight+6)+30);
            g2.draw(temp);

            //Put in base count labels
            TextLayout layout = new TextLayout(""+(i*tickSize), arial10, context);
            Double textbarwidthdiv2 = layout.getBounds().getWidth()/2;
            int xpos = (int) (((i * tickSize * pixPerBase) + seqStart) - textbarwidthdiv2 + 1);
            int ypos = numAbis*(abiHeight+6)+41;
            layout.draw(g2, xpos, ypos);
        }

        //For each ABI, put in shape, label, and ticks
        for(int i=0; i<numAbis; i++){
            AlignedABI alabi = extract.getABIList().get(i);
            boolean isRevComp = alabi.isForward;

            int xStart=(int)(alabi.startOnTarget*pixPerBase);
            int xWidth=(int)((alabi.endOnTarget-alabi.startOnTarget)*pixPerBase);
            int yStart=((numAbis-(i+1))*(abiHeight+6))+3;

            //Put in the background round rectangle for the ABI
            RoundRectangle2D.Double r=new RoundRectangle2D.Double(xStart, yStart, xWidth, abiHeight, 15, 25);
            g2.setColor(lightBlue);
            g2.fill(r);

            //Put in ambiguous base ticks
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  0.3f));
            g2.setColor(Color.WHITE);
            for(int n=0; n< alabi.trace.getSeq().length(); n++){
                if(alabi.trace.getSeq().charAt(n)=='N'){
                    int xpos = xStart+(int)(n*pixPerBase);
                    int ypos = ((numAbis-(i+1))*(abiHeight+6))+4;
                    Rectangle2D.Double yell=new Rectangle2D.Double(xpos,ypos , 2*pixPerBase, abiHeight-2);
                    g2.fill(yell);
                }
            }
            g2.setComposite(regularOverlay);

            //Draw the round rectangle surrounding each ABI shape
            g2.setColor(darkBlue);
            g2.setStroke(new BasicStroke(2));
            g2.draw(r);

            //Put in name labels of ABI's
            g2.setColor(Color.BLACK);
            String abiLabel = alabi.name;
            TextLayout layout = new TextLayout(abiLabel, arial12bold, context);

            int width = (int) (alabi.trace.getSeq().length() * pixPerBase);
            int xpos= (int) (xStart + width / 2 - layout.getBounds().getWidth() / 2);
            int ypos=yStart+16;
            layout.draw(g2, xpos, ypos);

            //Put in directionality triangles
            GeneralPath gp = new GeneralPath();
            ypos-=7;
            if(isRevComp) {
                xpos-=width/2 - layout.getBounds().getWidth()/2 - 10;
                gp.moveTo(xpos, ypos);
                gp.lineTo(xpos+5, ypos+3);
            } else {
                xpos+=width/2 + layout.getBounds().getWidth()/2 - 7;
                gp.moveTo(xpos, ypos);
                gp.lineTo(xpos-5, ypos+3);
            }
                gp.lineTo(xpos, ypos+6);
                gp.lineTo(xpos, ypos);
            g2.draw (gp);
        }

        //Put in result text
        int fat=(int)((x/2)-seqStart);
        int height=y-(y-(numAbis*(abiHeight+6)+70));
        int xpos = (int)seqStart;
        int ypos = y-height;

        TextLayout layout = new TextLayout("Sample: "+sampleName, arial12bold, context);
        layout.draw(g2, xpos, ypos);

        ypos += 15;
        layout = new TextLayout("Result: "+result, arial12bold, context);
        layout.draw(g2, xpos, ypos);

        ypos += 15;
        layout = new TextLayout("Author: "+auth, arial12bold, context);
        layout.draw(g2, xpos, ypos);

        ypos += 15;
        layout = new TextLayout(getDateTime(), arial12bold, context);
        layout.draw(g2, xpos, ypos);
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    ///////////////////////////////////////////////////////////////////
    ////                      private variables                    ////
    
    private String result, auth, sampleName;
    TraceExtract extract;
    private int numAbis;

    private ArrayList<TextLayout> toBeDeleted;
    private Graphics2D g2;
    private JFrame frame;
    private int nextRect;
    private int length, paintNum;


    private ArrayList<Shape> regions = new ArrayList<Shape>();
    private int y;
    private double pixPerBase;
    private double seqStart;
    private double seqSize;
    private int x;
    private int imX, imY;

    private final int abiHeight=23;
    private final Color green=new Color(8, 153, 72);
    private final Color lightBlue=new Color(183, 226, 224);
    private final Color darkBlue=new Color(108, 172, 183);
    private final Color yellow=new Color(231, 208, 62);
    private final Font arial10=new Font("Arial", Font.PLAIN, 10);
    private final Font arial12=new Font("Arial", Font.PLAIN, 12);
    private final Font arial14=new Font("Arial", Font.PLAIN, 14);
    private final Font arial12bold=new Font("Arial", Font.BOLD, 12);
}
