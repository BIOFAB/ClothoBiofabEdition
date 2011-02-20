package org.clothocad.tool.personeditor;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/*
 * GlassMessage.java
 *
 * Created on May 2, 2007, 4:49 PM
 *
 * Copyright (c) 2007, Sun Microsystems, Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *
 * @author Chet
 */
public class GlassMessage extends JComponent implements ActionListener {

    /** Creates a new instance of SmoothAnimation */
    public GlassMessage(String msg, String secondmsg, int wide, int high, Container cont, Color bkg, ActionListener al) {
        imageW = wide;
        imageH = high;
        container = cont;
        message = msg;
        bkgColor = bkg;
        actionOnClick = al;
        message2 = secondmsg;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(closeCircle==null) {
                    return;
                }
                if(closeCircle.getBounds().contains(e.getPoint())) {
                    dumpit();
                } else {
                    if(actionOnClick!=null) {
                        actionOnClick.actionPerformed(null);
                    }
                }
            }
        });

        startTimer(currentResolution);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
    
    /**
     * Create the image that will be animated. This image may be an actual
     * image (duke.gif), or some graphics (a variation on a black filled
     * rectangle) that are rendered into an image. The contents
     * of this image are dependent upon the runtime toggles that have been
     * set when this method is called.
     */
    void createAnimationImage() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        image = gc.createCompatibleImage(imageW, imageH, Transparency.TRANSLUCENT);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D.Double roundrect = new RoundRectangle2D.Double(5, 5, imageW-10, imageH-10, 10, 10);

        for(int i=10; i>0; i--) {
            g2d.setStroke(new BasicStroke(i));
            double falpha = 60*(11-i)/10 + 5;
            int alpha =(int) Math.floor(falpha);
            System.out.println(falpha);
            g2d.setColor(new Color( 80,80,80, alpha));
            g2d.draw(roundrect);
        }

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(bkgColor);
        g2d.fill(roundrect);
        g2d.setColor(Color.BLACK);
        g2d.draw(roundrect);

        if(message==null) {
            message = "";
        }
        int divisor = 2;
        boolean dotwolines = false;

        if(message2!=null ) {
            if(!message2.equals("")) {
                divisor = 3;
                dotwolines = true;
            }
        }
        //Put in the text
        g2d.drawString(message, 12, imageH/divisor + 5);
        if(dotwolines) {
            g2d.drawString(message2, 12, (divisor-1)*imageH/divisor+5);
        }
        
        //Draw the close it button
        g2d.setColor(Color.DARK_GRAY);
        closeCircle = new Ellipse2D.Double(imageW-20, 8, 12, 12);
        g2d.fill(closeCircle);

        //Put white X on it
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(imageW-18, 10, imageW-11, 17);
        g2d.drawLine(imageW-11, 10, imageW-18, 17);

        g2d.dispose();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (image == null) {
            createAnimationImage();
        }
        
        // Erase the background
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw the fading image
        Graphics2D gFade = (Graphics2D)g.create();
        gFade.setComposite(AlphaComposite.SrcOver.derive(opacity));
        gFade.drawImage(image, fadeX, fadeY, null);
        gFade.dispose();
    }
    
    /**
     * This method handles the events from the Swing Timer
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(timeElapsed<START_TIME) {
            float fraction = (float) timeElapsed / START_TIME;
            animate(fraction);
            timeElapsed+=currentResolution;
            return;
        } else if(timeElapsed> (START_TIME + LULL_TIME + END_TIME) ) {
            dumpit();
            return;
        } else if(timeElapsed> (START_TIME + LULL_TIME) ) {
            long timeleft = START_TIME + LULL_TIME + END_TIME - timeElapsed;
            float fraction = (float) timeleft / END_TIME;
            animate(fraction);
            timeElapsed+=currentResolution;
            return;
        } else {
            timeElapsed+=currentResolution;
            return;
        }
    }

    /**
     * Animate the opacity and location factors, according to the current
     * fraction.
     */
    public void animate(float fraction) {
        float animationFactor;
        animationFactor = (float)Math.sin(fraction * (float)Math.PI/2);
        animationFactor = Math.min(animationFactor, 1.0f);
        animationFactor = Math.max(animationFactor, 0.0f);
        opacity = animationFactor;
        repaint();
    }
    
    /**
     * Starts the animation
     */
    private void startTimer(int resolution) {
        if (timer != null) {
            timer.stop();
            timer.setDelay(resolution);
        } else {
            timer = new Timer(resolution, this);
        }
        timer.start();
    }

    private void dumpit() {
        timer.stop();
        animate(0.0f);
        container.remove(this);
        container.validate();
        container.repaint();
    }
    
    private static void createAndShowGUI() {
	JFrame f = new JFrame("Smooth Moves");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(500,500);
	GlassMessage component = new GlassMessage("Failed to load part.", null, 200, 50, f, Color.WHITE, null);
	f.add(component);
	f.setVisible(true);
    }

    public static void main(String[] args) {
	Runnable doCreateAndShowGUI = new Runnable() {
            @Override
	    public void run() {
		createAndShowGUI();
	    }
	};
	SwingUtilities.invokeLater(doCreateAndShowGUI);
    }

/*-----------------
     variables
 -----------------*/
    //For the close early button
    Shape closeCircle;
    Container container;
    String message;
    String message2;

    /** image holds the graphics we render for each animating object */
    BufferedImage image = null;
    private int imageW;
    private int imageH;

    /** Location of fading animation */
    int fadeX = 0;
    int fadeY = 0;


    /** Current opacity of fading animation */
    float opacity = 0.0f;

    /** Toggles for various demo options (key to toggle in parentheses) */
    boolean useImage = false;   // (i) image instead of rectangle
    boolean useAA = false;      // (a) anti-aliased edges (rectangle only)
    boolean motionBlur = false; // (b) ghost images behind moving animation
    boolean alterColor = false; // (c) light-gray instead of black rectangle
    boolean linear = true;      // (l) linear vs. non-linear motion


    /** Basic Timer animation info */
    final static int CYCLE_TIME = 2000;     // One cycle takes 2 seconds

    final static int END_TIME = 1000;     //Time for fading at the end
    final static int START_TIME = 2000;     //Time for fading at the start
    final static int LULL_TIME = 5000;      //Time for sitting around

    int currentResolution = 25;             // current Timer resolution
    Timer timer = null;                     // animation Timer
    long timeElapsed = 0;                        // track start time for each cycle

    //Formatting preferences
    private Color bkgColor;
    private ActionListener actionOnClick;
}
