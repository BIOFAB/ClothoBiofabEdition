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

package org.clothocad.algorithm.seqanalyzer.sequencing;

import java.awt.AlphaComposite;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Ben Bubenheim
 */
public class seqReport extends JPanel implements MouseListener {
    private boolean display;
    private ArrayList<ABITrace> abis, duds;
    private ArrayList<String> traces, traceNames;
    private ArrayList<ArrayList<Integer>> traceMap, abiMap;
    private ArrayList<AlignedAbi> alignedAbis;
    private ArrayList<TextLayout> toBeDeleted;
    private Graphics2D g2;
    private JFrame frame;
    private int nextRect;
    private int length, paintNum;
    private String result, seq, fullRead, auth, sampleName;
    private ArrayList<Shape> regions = new ArrayList<Shape>();
    //private ArrayList<AnomalyRe> anomalies=new ArrayList<AnomalyButton>();
    private int y;
    private double pixPerBase;
    private double seqStart;
    private double seqSize;
    private int x;
    private int imX, imY;
    private ArrayList<Nmapper> nPos, mutations;
    private final int abiHeight=23;
    private final Color green=new Color(8, 153, 72);
    private final Color lightBlue=new Color(183, 226, 224);
    private final Color darkBlue=new Color(108, 172, 183);
    private final Color yellow=new Color(231, 208, 62);
    private final Font arial10=new Font("Arial", Font.PLAIN, 10);
    private final Font arial12=new Font("Arial", Font.PLAIN, 12);
    private final Font arial14=new Font("Arial", Font.PLAIN, 14);
    private final Font arial12bold=new Font("Arial", Font.BOLD, 12);
    //public seqReport(ArrayList<String> split, ArrayList<String> weird, ArrayList<String> qStrings, ArrayList<ABITrace> abi, ArrayList<String> abiNames, ArrayList<ArrayList<Integer>> map, String res, String target, ArrayList<ABITrace> dud) {

    public seqReport(String target, String full, String res, String author, String sName, ArrayList<Nmapper> muts, ArrayList<Nmapper> nMap, ArrayList<ABITrace> abi, ArrayList<String> qStrings, ArrayList<ArrayList<Integer>> map, ArrayList<AlignedAbi> ali, ArrayList<ArrayList<Integer>> baseToAbi, ArrayList<ABITrace> dud, boolean d){
        addMouseListener(this);
        display=d;
        auth=author;
        sampleName=sName;
        alignedAbis=ali;
        mutations=muts;
        abiMap=baseToAbi;
        nPos=nMap;
        abis=abi;
        fullRead=full;
        paintNum=0;
        traces=qStrings;
        toBeDeleted=new ArrayList<TextLayout>();
        traceNames=new ArrayList<String>();
        for(int i=0; i<abis.size(); i++){
            traceNames.add(abis.get(i).getFileName());
        }
        duds=dud;
        traceMap=map;
        for(int i=0; i<map.size(); i++){
            System.out.println(map.get(i));
        }
        seq=target;
        //setting up window width
        pixPerBase=700/(double)seq.length();
        int t=traceMap.get(0).get(1).intValue()+traceMap.get(traceMap.size()-1).get(2).intValue()+traces.get(traceMap.get(traceMap.size()-1).get(0)).length()-Math.abs(traceMap.get(traceMap.size()-1).get(1).intValue());
        t*=1.1;
        int f=Math.max(traceMap.get(0).get(2)*-1, 0)+traceMap.get(traceMap.size()-1).get(2)+traces.get(traceMap.get(traceMap.size()-1).get(0)).length();
        int sl=seq.length();
        if(f>sl){
            t=f;
        }
        else{
            t=(int)(sl*1.1);
        }
        System.out.println("length should be "+t+"?");
        //*****
        //pixPerBase=700/(double)t;
        //*****
        x=(int)(t*pixPerBase);
        seqStart=(x*.05);
        seqSize=seq.length()*pixPerBase;
        nextRect=(int)seqStart;
        result=res;
        
        //calculate window size
        int numAbis=traces.size()+Math.max(duds.size()-3, 0);
        y=numAbis*(abiHeight+3)+200;

        imX=x;
        imY=y;
        //System.out.println("abi2's index is "+traceMap.get(1).get(1).intValue());
        //make new report
        if(display){
            disp();
        }
        
    }
    private void disp(){
        frame = new JFrame("Sequencing Report");
        frame.getContentPane().add(this);
        frame.setPreferredSize(new Dimension(x,y));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
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
        

        //Draw the box for the comparison sequence
        g2.setColor(yellow);
        Rectangle2D.Double fullSeq=new Rectangle2D.Double(seqStart, traces.size()*(abiHeight+8), seqSize, 15);
        g2.fill(fullSeq);

        g2.setColor(green);
        double fullStart=Math.max(alignedAbis.get(0).getStartInSeq(), 0);
        Rectangle2D.Double readSeq=new Rectangle2D.Double(seqStart+(fullStart*pixPerBase), traces.size()*(abiHeight+8), fullRead.length()*pixPerBase, 15);
        g2.fill(readSeq);

        //Paint each mutation red box over the comparison sequence box
        int mth=0;
        for(int n=0; n<mutations.size(); n++){
            for(int i=0; i<mutations.get(n).getNsInSeq().size(); i++){
                g2.setColor(Color.RED);
                Rectangle2D.Double red=new Rectangle2D.Double(nextRect+((mutations.get(n).getNsInSeq().get(i).intValue()*pixPerBase)), traces.size()*(abiHeight+8), 2*pixPerBase, 15);
                g2.fill(red);
                AnomalyRect re=new AnomalyRect(true, mth, mutations.get(n), nextRect+(int)(mutations.get(n).getNsInSeq().get(i).intValue()*pixPerBase)-(4*pixPerBase), traces.size()*(abiHeight+8), 8*pixPerBase, 15);
            
                mth++;
            }
            mth=0;
        }
        //Paint each ambiguous yellow box over the comparison sequence box
        int nth=0;
            for(int n=0; n<nPos.size(); n++){
                for(int i=0; i<nPos.get(n).getNsInSeq().size(); i++){
                    g2.setColor(yellow);
                    Rectangle2D.Double yell=new Rectangle2D.Double(nextRect+(int)(nPos.get(n).getNsInSeq().get(i).intValue()*pixPerBase), traces.size()*(abiHeight+8), 2*pixPerBase, 15);
                    g2.fill(yell);
                    AnomalyRect re=new AnomalyRect(false, nth, nPos.get(n), nextRect+(int)(nPos.get(n).getNsInSeq().get(i).intValue()*pixPerBase)-(4*pixPerBase), traces.size()*(abiHeight+8), 8*pixPerBase, 15);
                    nth++;
                }
                nth=0;
            }
            //nextRect+=(int)(good.get(i).length()*pixPerBase);
            
        
        nextRect=(int)seqStart;

        //Add ruler underneath the comparison sequence
        int tickSize=50;
        if(seq.length()>1000){
            tickSize=100;
        }
        double ticktemp=(seq.length()/(double)tickSize);
        int ticks=0;
        if(ticktemp==(int)ticktemp){
            ticks=(int)ticktemp;
        }
        else{
            ticks=((int)ticktemp)+1;
        }

        int rulerL=(int)((ticks*tickSize)*pixPerBase);
        g2.setColor(Color.black);
        Line2D.Double ruler=new Line2D.Double(seqStart, traces.size()*(abiHeight+6)+25, seqStart+rulerL, traces.size()*(abiHeight+6)+25);
        g2.draw(ruler);

        //Draw the ticks and labels below them
        for(int i=0; i<ticks+1; i++){
            //Put in tick lines
            Line2D.Double temp=new Line2D.Double((i*tickSize*pixPerBase)+seqStart, traces.size()*(abiHeight+6)+25, (i*tickSize*pixPerBase)+seqStart, traces.size()*(abiHeight+6)+30);
            g2.draw(temp);

            //Put in base count labels
            TextLayout layout = new TextLayout(""+(i*tickSize), arial10, context);
            Double textbarwidthdiv2 = layout.getBounds().getWidth()/2;
            int xpos = (int) (((i * tickSize * pixPerBase) + seqStart) - textbarwidthdiv2 + 1);
            int ypos = traces.size()*(abiHeight+6)+41;
            layout.draw(g2, xpos, ypos);
        }
        
        //For each ABI, put in shape, label, and ticks
        for(int i=0; i<alignedAbis.size(); i++){
            boolean isRevComp = false;  //NEED TO PASS WHETHER THE ORIENTATION IS REV COMP OR NOT TO THIS
            String trace=traces.get(alignedAbis.get(i).getOriginalIndex());
            //System.out.println("trace is "+trace);

            int seqAlign=alignedAbis.get(i).getStartInSeq();//Math.abs((int)(traceMap.get(i).get(2).intValue()));
            int traceAlign=alignedAbis.get(i).getStartInTrace();//Math.abs((int)(traceMap.get(i).get(1).intValue()));
            int xStart=(int)(seqStart+((seqAlign-traceAlign)*pixPerBase));
            int yStart=((traces.size()-(i+1))*(abiHeight+6))+3;
            System.out.println("seqAlign is "+seqAlign);
            System.out.println("traceAlign is "+traceAlign);
            System.out.println(""+(traceAlign-(-seqAlign)));
            if(alignedAbis.get(i).isRevComp()){//traceAlign<0){
                isRevComp=true;
                //traceAlign*=-1;
            }

            //Put in the background round rectangle for the ABI
            RoundRectangle2D.Double r=new RoundRectangle2D.Double(xStart, yStart, traces.get(traceMap.get(i).get(0)).length()*pixPerBase, abiHeight, 15, 25);
            g2.setColor(lightBlue);
            g2.fill(r);

            //Put in ambiguous base ticks
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  0.3f));
            g2.setColor(Color.WHITE);
            for(int n=0; n<trace.length(); n++){
                if(trace.charAt(n)=='N'){
                    int xpos = xStart+(int)(n*pixPerBase);
                    int ypos = ((traces.size()-(i+1))*(abiHeight+6))+4;
                    Rectangle2D.Double yell=new Rectangle2D.Double(xpos,ypos , 2*pixPerBase, abiHeight-2);
                    g2.fill(yell);
                }
            }
            g2.setComposite(regularOverlay);

            //Put in error base ticks
            //PUT IN ERROR TICKS HERE, add to regions, maybe add also to a hashtable
            //that returns abi and another that returns the base position so on
            //mouse click can grab the abi and position and use that to calculate bufferedimage

            //Draw the round rectangle surrounding each ABI shape
            int xpos = xStart;
            int ypos =  yStart;
            int width = (int) (traces.get(traceMap.get(i).get(0)).length() * pixPerBase);
            int height = abiHeight;
            RoundRectangle2D.Double r1=new RoundRectangle2D.Double(xpos, ypos, width, height, 15, 25);
            g2.setColor(darkBlue);
            g2.setStroke(new BasicStroke(2));
            g2.draw(r1);

            //Put in name labels of ABI's
            g2.setColor(Color.BLACK);
            String abiLabel = traceNames.get(traceMap.get(i).get(0).intValue());
            TextLayout layout = new TextLayout(abiLabel, arial12bold, context);
            xpos+= width/2 - layout.getBounds().getWidth()/2;
            ypos+=16;
            layout.draw(g2, xpos, ypos);

            //Put in directionality triangles
            
            GeneralPath gp = new GeneralPath();
            ypos-=7;
            if(!isRevComp) {
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
        int height=y-(y-(traces.size()*(abiHeight+6)+70));
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

    public void mouseClicked(MouseEvent e) {
        System.out.println("You clicked the mouse");
        Point p = e.getPoint();
        //for(Shape s: regions) {
            //if(s.contains(p)) {
                //System.out.println("You clicked within" + s.toString());
                //((AnomalyRect)s).mouseClicked(e);
            //}
        //}
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        Point p = e.getPoint();
        for(Shape s: regions) {
            if(s.contains(p)) {
                System.out.println("You entered " + s.toString());
                //((AnomalyRect)s).mouseEntered(e);
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        Point p = e.getPoint();
        for(Shape s: regions) {
            if(s.contains(p)) {
                System.out.println("You exited " + s.toString());
                //((AnomalyRect)s).mouseExited(e);
            }
        }
    }
    
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private class AnomalyRect extends Rectangle2D.Double implements MouseListener{
        private double xpos, ypos, w, h;
        private boolean mutation;
        private Rectangle2D.Double rect;
        private Nmapper nMap;
        private int index;
        

        public AnomalyRect(boolean mut, int num, Nmapper n, double xposs, double yposs, double width, double height){
            super(xposs, yposs, width, height);
            addMouseListener(this);
            index=num;
            nMap=n;
            xpos=xposs;
            ypos=yposs;
            w=width;
            h=height;
            mutation=mut;
            //setBounds(xpos, ypos, w, h);
            
            setVisible(true);

            setOpaque(false);
            regions.add(this);
        }

        public void mouseClicked(MouseEvent e) {
            //System.out.println("You clicked me!");
            Point p=e.getPoint();
            if(contains(p)){
                System.out.println("CONTAINED");
                throwPopUp();
            }

        }

        public void mousePressed(MouseEvent e) {
            //System.out.println("pressed");
        }

        public void mouseReleased(MouseEvent e) {
            //System.out.println("released");
        }

        public void mouseEntered(MouseEvent e) {
            //System.out.println("in here!!!!");
        }

        public void mouseExited(MouseEvent e) {
            //System.out.println("exited!");
        }

        public void throwPopUp(){
            if(!mutation){
                int posInRead=nMap.getNsInSeq().get(index).intValue();
                int posInAbi=nMap.getCorrectedNs().get(index).intValue();
                int actualPosInAbi=nMap.getNsInAbi().get(index).intValue();
                int trace=nMap.getAbi().getOriginalIndex();
                BufferedImage traceImage =abis.get(trace).getImage(90,1,posInAbi);
                ImageIcon traceIcon = new ImageIcon(traceImage);
                FontRenderContext context = g2.getFontRenderContext();
                TextLayout info = new TextLayout("Ambiguous Base at "+posInRead+" in the sequence, "+posInAbi+" in the trace: ", arial12, context);
                g2.setColor(frame.getBackground());
                if(!toBeDeleted.isEmpty()){
                    //g2.setColor(Color.red);
                    g2.fill(new Rectangle2D.Double((int)(imX*.35)-20, traces.size()*(abiHeight+6)+45, (int)(imX*.3)+50, 40));
                    //toBeDeleted.remove(0).draw(g2, (int)(imX*.35)-20, traces.size()*(abiHeight+6)+55);
                    toBeDeleted.remove(0);

                }
                g2.setColor(Color.black);
                info.draw(g2, (int)(imX*.35)-20, traces.size()*(abiHeight+6)+55);
                g2.drawImage(traceImage, (int)(imX*.42), traces.size()*(abiHeight+6)+60, (int)(imX*.16), (int)(imY*.4), frame);
                g2.setColor(frame.getBackground());
                if(!toBeDeleted.isEmpty()){
                    //g2.setColor(Color.red);
                    g2.fill(new Rectangle2D.Double((int)(imX*.35)-20, traces.size()*(abiHeight+6)+60+(int)(imY*.4), (int)(imX*.3)+100, 40));
                    //g2.fill(toBeDeleted.remove(0).getBounds());
                    //toBeDeleted.remove(0).draw(g2, (int)(imX*.35), traces.size()*(abiHeight+6)+70+(int)(imY*.4));
                    toBeDeleted.remove(0);
                }
                TextLayout suggestion = new TextLayout(""+abis.get(trace).getSequence().charAt(nMap.getNsInAbi().get(index))+" was called at this position with quality "+nMap.getAbi().getAbi().getQualities()[nMap.getNsInAbi().get(index)], arial12, context);
                g2.setColor(Color.black);
                suggestion.draw(g2, (int)(imX*.35), traces.size()*(abiHeight+6)+70+(int)(imY*.4));
                toBeDeleted.add(info);
                toBeDeleted.add(suggestion);
            }
            else{
                int posInRead=nMap.getNsInSeq().get(index).intValue();
                int posInAbi=nMap.getCorrectedNs().get(index).intValue();
                int actualPosInAbi=nMap.getNsInAbi().get(index).intValue();
                int trace=nMap.getAbi().getOriginalIndex();
                BufferedImage traceImage = abis.get(trace).getImage(90,1,posInAbi);
                FontRenderContext context = g2.getFontRenderContext();
                TextLayout info = new TextLayout("Mutated Base at "+posInRead+" in the sequence, "+posInAbi+" in the trace", arial12, context);
                g2.setColor(frame.getBackground());
                if(!toBeDeleted.isEmpty()){
                    //g2.setColor(Color.red);
                    g2.fill(new Rectangle2D.Double((int)(imX*.35)-20, traces.size()*(abiHeight+6)+45, (int)(imX*.3)+50, 40));
                    //toBeDeleted.remove(0).draw(g2, (int)(imX*.35)-20, traces.size()*(abiHeight+6)+55);
                    toBeDeleted.remove(0);

                }
                g2.setColor(Color.black);
                info.draw(g2, (int)(imX*.35)-20, traces.size()*(abiHeight+6)+55);
                g2.drawImage(traceImage, (int)(imX*.42), traces.size()*(abiHeight+6)+60, (int)(imX*.16), (int)(imY*.4), frame);
                g2.setColor(frame.getBackground());
                if(!toBeDeleted.isEmpty()){
                    //g2.setColor(Color.red);
                    g2.fill(new Rectangle2D.Double((int)(imX*.35)-20, traces.size()*(abiHeight+6)+60+(int)(imY*.4), (int)(imX*.3)+100, 40));
                    //g2.fill(toBeDeleted.remove(0).getBounds());
                    //toBeDeleted.remove(0).draw(g2, (int)(imX*.35), traces.size()*(abiHeight+6)+70+(int)(imY*.4));
                    toBeDeleted.remove(0);
                }
                TextLayout suggestion = new TextLayout(""+abis.get(trace).getSequence().charAt(posInAbi)+" was called at this position with quality "+abis.get(trace).getQualities()[posInAbi]+"; "+seq.charAt(posInRead)+" was expected", arial12, context);
                g2.setColor(Color.black);
                suggestion.draw(g2, (int)(imX*.35)-20, traces.size()*(abiHeight+6)+70+(int)(imY*.4));
                toBeDeleted.add(info);
                toBeDeleted.add(suggestion);
            }
        }
        
    }
    


}
