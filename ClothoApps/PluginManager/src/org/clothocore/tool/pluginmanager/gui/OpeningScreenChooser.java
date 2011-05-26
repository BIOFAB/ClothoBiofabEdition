/*
 * Copyright (c) 2007, Romain Guy
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

package org.clothocore.tool.pluginmanager.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import java.io.File;
import org.clothocore.api.core.Collator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;
import net.iharder.dnd.FileDrop;
import org.clothocore.util.misc.BareBonesBrowserLaunch;
import org.openide.util.ImageUtilities;


/**
 * This is the panel with the images and the button label above them
 * @author jcanderson
 */
public class OpeningScreenChooser extends JPanel implements FancyPanel {
    private Hashtable<Integer, String> labels = new Hashtable<Integer, String>();
    private Hashtable<Integer, String> descriptions = new Hashtable<Integer, String>();
    private RightClickPopup rcPopup;
    private static final double ANIM_SCROLL_DELAY = 450;

    private List<Image> avatars = null;

    private boolean loadingDone = false;

    private Thread picturesFinder = null;
    private Timer scrollerTimer = null;
    private Timer faderTimer = null;
    private Timer passwordTimer;

    private float veilAlphaLevel = 0.0f;
    private float alphaLevel = 0.0f;
    private float textAlphaLevel = 0.0f;

    private int avatarIndex;
    private double avatarPosition = 0.0;
    private double avatarSpacing = 0.4;
    private int avatarAmount = 5;

    private double sigma;
    private double rho;

    private double exp_multiplier;
    private double exp_member;

    private boolean damaged = true;

    private DrawableAvatar[] drawableAvatars;
    private String textAvatar;
    private String descriptionAvatar;

    private FocusGrabber focusGrabber;
    private AvatarScroller avatarScroller;
    private CursorChanger cursorChanger;
    private MouseWheelScroller wheelScroller;
    private KeyScroller keyScroller;
    private ApplicationFrame _app;

    public OpeningScreenChooser(ApplicationFrame app) {
        _app = app;
        descriptions.put(0, "View currently-installed plugins and delete ones you don't want");
        descriptions.put(1, "See Information about Clotho Objects and set preferred viewers for them");
        descriptions.put(2, "Activate and deactivate plugins launched at startup");
        descriptions.put(3, "Choose your default database connection");
        descriptions.put(4, "Click to access Plugin Manager's help page");
        descriptions.put(5, "Download more Clotho plugins");

        labels.put(0, "Remove plugins");
        labels.put(1, "Manage viewers");
        labels.put(2, "Manage widgets");
        labels.put(3, "Manage database");
        labels.put(4, "Get help");
        labels.put(5, "Go shopping");

        //Put in the plugin fileDrop
        new  FileDrop( this, new FileDrop.Listener() {
            @Override
            public void  filesDropped( java.io.File[] files ) {
                pluginDropped(files);
            }});

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        findAvatars();
        setSigma(0.5);

        addComponentListener(new DamageManager());

        initInputListeners();
        addInputListeners();
    }

    /**
     * Called when a plugin gets dropped, it calls on the plugin loader
     *
     * @param files
     */
    private void pluginDropped(final File[] files) {
        System.out.println("****You dropped " + files[0].getName());
        final ArrayList<File> fileList = Collator.unpackInstall(files);
        if(fileList != null)
        {
            new Thread() {

                    //@Override
                    public void run() {

                        List<File> installList = Collator.areInstallable(fileList);
                        if (!installList.isEmpty()) {
                            Collator.installPlugins(installList);
                        }
                    }
                }.start();
                //Collator.refreshDash();

        }
    }

    @Override
    public void setAmount(int amount) {
        if (amount > avatars.size()) {
            throw new IllegalArgumentException("Too many avatars");
        }

        this.avatarAmount = amount;
        repaint();
    }

    // XXX package access for debugging purpose only
    @Override
    public void setPosition(double position) {
        this.avatarPosition = position;
        this.damaged = true;
        repaint();
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
        this.rho = 1.0;
        computeEquationParts();
        this.rho = computeModifierUnprotected(0.0);
        computeEquationParts();
        this.damaged = true;
        repaint();
    }

    public void setSpacing(double avatarSpacing) {
        if (avatarSpacing < 0.0 || avatarSpacing > 1.0) {
            throw new IllegalArgumentException("Spacing must be < 1.0 and > 0.0");
        }

        this.avatarSpacing = avatarSpacing;
        this.damaged = true;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(128 * 3, 128 * 2);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFocusable() {
        return true;
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Composite oldComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   veilAlphaLevel));
        super.paintChildren(g);
        g2.setComposite(oldComposite);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!loadingDone && faderTimer == null) {
            return;
        }

        Insets insets = getInsets();

        int x = insets.left;
        int y = insets.top;

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Composite oldComposite = g2.getComposite();

        if (damaged) {
            drawableAvatars = sortAvatarsByDepth(x, y, width, height);
            damaged = false;
        }

        drawAvatars(g2, drawableAvatars);

        if (drawableAvatars.length > 0) {
            drawAvatarName(g2);
        }

        g2.setComposite(oldComposite);
    }

    private void drawAvatars(Graphics2D g2, DrawableAvatar[] drawableAvatars) {
        for (DrawableAvatar avatar: drawableAvatars) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
                                                                  (float) avatar.getAlpha());
            g2.setComposite(composite);
            g2.drawImage(avatars.get(avatar.getIndex()),
                         (int) avatar.getX(), (int) avatar.getY(),
                         avatar.getWidth(), avatar.getHeight(), null);
        }
    }

    private DrawableAvatar[] sortAvatarsByDepth(int x, int y,
                                                int width, int height) {
        List<DrawableAvatar> drawables = new LinkedList<DrawableAvatar>();
        for (int i = 0; i < avatars.size(); i++) {
            promoteAvatarToDrawable(drawables,
                                    x, y, width, height, i - avatarIndex);
        }

        DrawableAvatar[] drawable_avatars = new DrawableAvatar[drawables.size()];
        drawable_avatars = drawables.toArray(drawable_avatars);
        Arrays.sort(drawable_avatars);
        return drawable_avatars;
    }

    //This is the label over the images, or at least where its size is set
    private void drawAvatarName(Graphics2D g2) {
        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   textAlphaLevel));
        //This is the height and width of the little bullet
        double bulletWidth = 150.0;
        double bulletHeight = 30.0;

        double x = (getWidth() - bulletWidth) / 2.0;
        double y = (getHeight() - 154) / 2.0 - bulletHeight * 1.4;

        drawAvatarBullet(g2, x, y, bulletWidth, bulletHeight);
        drawAvatarText(g2, y, bulletHeight);
        drawDescriptionText(g2, y, bulletHeight);

        g2.setComposite(composite);
    }

    //Writes the actual text of the avatar.  textAvatar is the String it writes
    private void drawAvatarText(Graphics2D g2, double y, double bulletHeight) {
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("Dialog", Font.PLAIN, 18);
        if(textAvatar==null) {
            textAvatar="Hi there";
        }
        TextLayout layout = new TextLayout(textAvatar, font, context);
        Rectangle2D bounds = layout.getBounds();

        float text_x = (float) ((getWidth() - bounds.getWidth()) / 2.0);
        float text_y = (float) (y + (bulletHeight - layout.getAscent() - 
                                     layout.getDescent()) / 2.0) +
                                     layout.getAscent() - layout.getLeading();

        g2.setColor(Color.BLACK);
        layout.draw(g2, text_x, text_y + 1);
        g2.setColor(Color.WHITE);
        layout.draw(g2, text_x, text_y);
    }

    //Writes the Description, added by JCA
    private void drawDescriptionText(Graphics2D g2, double y, double bulletHeight) {
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("Dialog", Font.PLAIN, 15);
        if(descriptionAvatar==null) {
            descriptionAvatar = "hi there";
        }
        TextLayout layout = new TextLayout(descriptionAvatar, font, context);
        Rectangle2D bounds = layout.getBounds();

        float text_x = (float) ((getWidth() - bounds.getWidth()) / 2.0);
        float text_y = (float) (y + (bulletHeight - layout.getAscent() -
                                     layout.getDescent()) / 2.0) +
                                     layout.getAscent() - layout.getLeading() + 40;

        g2.setColor(Color.BLACK);
        layout.draw(g2, text_x, text_y + 1);
        g2.setColor(new Color(180,180,180));
        layout.draw(g2, text_x, text_y);
    }

    //JCA this is the little label over the images with the name like "part"
    private void drawAvatarBullet(Graphics2D g2,
                                  double x, double y,
                                  double bulletWidth, double bulletHeight) {
        RoundRectangle2D bullet = new RoundRectangle2D.Double(0.0, 0.0,
                                                              bulletWidth, bulletHeight,
                                                              bulletHeight, bulletHeight);
        Ellipse2D curve = new Ellipse2D.Double(-20.0, bulletHeight / 2.0,
                                               bulletWidth + 120.0, bulletHeight);

        g2.translate(x, y);

        g2.translate(-1, -2);
        g2.setColor(new Color(0, 0, 0, 170));
        g2.fill(new RoundRectangle2D.Double(0.0, 0.0,
                                            bulletWidth + 2, bulletHeight + 4,
                                            bulletHeight + 4, bulletHeight + 4));
        g2.translate(1, 2);

        Color startColor = new Color(30, 30, 30);
        Color endColor = new Color(79, 83, 77);

        Paint paint = g2.getPaint();
        g2.setPaint(new GradientPaint(0.0f, 0.0f, startColor,
                                      0.0f, (float) bulletHeight, endColor));
        g2.fill(bullet);

        startColor = new Color(50, 50, 50);
        endColor = new Color(70, 70, 70);
        g2.setPaint(new GradientPaint(0.0f, 0.0f, startColor,
                                      0.0f, (float) bulletHeight, endColor));

        Area area = new Area(bullet);
        area.intersect(new Area(curve));
        g2.fill(area);

        g2.setPaint(paint);
        g2.translate(-x, -y);
    }

    private void promoteAvatarToDrawable(List<DrawableAvatar> drawables,
                                         int x, int y, int width, int height,
                                         int offset) {
        double spacing = offset * avatarSpacing;
        double avatarPos = this.avatarPosition + spacing;

        if (avatarIndex + offset < 0 ||
            avatarIndex + offset >= avatars.size()) {
            return;
        }

        Image avatar = avatars.get(avatarIndex + offset);

        int avatarWidth = avatar.getWidth(null);
        int avatarHeight = avatar.getHeight(null);

        double result = computeModifier(avatarPos);

        int newWidth = (int) (avatarWidth * result);
        if (newWidth == 0) {
            return;
        }

        int newHeight = (int) (avatarHeight * result);
        if (newHeight == 0) {
            return;
        }

        //This is what determines the position of the avatars on the JFrame, is all relative to size of frame
        double avatar_x = x + (width - newWidth) / 2.0;
        double avatar_y = y + (height - newHeight / 2.0) / 1.7;

        double semiWidth = width / 2.0;

        avatar_x += avatarPos * semiWidth;

        if (avatar_x >= width || avatar_x < -newWidth) {
            return;
        }

        drawables.add(new DrawableAvatar(avatarIndex + offset,
                                         avatar_x, avatar_y,
                                         newWidth, newHeight,
                                         avatarPos, result));
    }

    private void startFader() {
        faderTimer = new Timer(35, new FaderAction());
        faderTimer.start();
    }
    
    private void computeEquationParts() {
        exp_multiplier = Math.sqrt(2.0 * Math.PI) / sigma / rho;
        exp_member = 4.0 * sigma * sigma;
    }

    // XXX package access for debug purpose only
    public double computeModifier(double x) {
        double result = computeModifierUnprotected(x);
        if (result > 1.0) {
            result = 1.0;
        } else if (result < -1.0) {
            result = -1.0;
        }

        return result;
    }

    private double computeModifierUnprotected(double x) {
        return exp_multiplier * Math.exp((-x * x) / exp_member);
    }

    private void addInputListeners() {
        addMouseListener(focusGrabber);
        addMouseListener(avatarScroller);
        addMouseMotionListener(cursorChanger);
        addMouseWheelListener(wheelScroller);
        addKeyListener(keyScroller);
    }

    private void initInputListeners() {
        // input listeners are all stateless
        // hence they can be instantiated once
        focusGrabber = new FocusGrabber();
        avatarScroller = new AvatarScroller();
        cursorChanger = new CursorChanger();
        wheelScroller = new MouseWheelScroller();
        keyScroller = new KeyScroller();
    }

    private void removeInputListeners() {
        removeMouseListener(focusGrabber);
        removeMouseListener(avatarScroller);
        removeMouseMotionListener(cursorChanger);
        removeMouseWheelListener(wheelScroller);
        removeKeyListener(keyScroller);
    }

    private void findAvatars() {
        avatars = new ArrayList<Image>();

        picturesFinder = new Thread(new PicturesFinderThread());
        picturesFinder.start();
    }

    private void setAvatarIndex(int index) {
        avatarIndex = index;
       //This is where the label gets set
        textAvatar = labels.get(index);
        descriptionAvatar = descriptions.get(index);
    }

    private void scrollBy(int increment) {
        if (loadingDone) {
            setAvatarIndex(avatarIndex + increment);

            if (avatarIndex < 0) {
                setAvatarIndex(0);
            } else if (avatarIndex >= avatars.size()) {
                setAvatarIndex(avatars.size() - 1);
            }

            damaged = true;
            repaint();
        }
    }
    
    private void scrollAndAnimateBy(int increment) {
        if (loadingDone && (scrollerTimer == null || !scrollerTimer.isRunning())) {
            int index = avatarIndex + increment;
            if (index < 0) {
                index = 0;
            } else if (index >= avatars.size()) {
                index = avatars.size() - 1;
            }
            
            DrawableAvatar drawable = null;

            for (DrawableAvatar avatar: drawableAvatars) {
                if (avatar.index == index) {
                    drawable = avatar;
                    break;
                }
            }

            if (drawable != null) {
                scrollAndAnimate(drawable);
            }
        }
    }

    private void scrollAndAnimate(DrawableAvatar avatar) {
        if (loadingDone) {
            scrollerTimer = new Timer(10, new AutoScroller(avatar));
            scrollerTimer.start();
        }
    }

    private BufferedImage createReflectedPicture(BufferedImage avatar) {
        int avatarWidth = avatar.getWidth();
        int avatarHeight = avatar.getHeight();

        BufferedImage alphaMask = createGradientMask(avatarWidth, avatarHeight);

        return createReflectedPicture(avatar, alphaMask);
    }

    private BufferedImage createReflectedPicture(BufferedImage avatar,
                                                 BufferedImage alphaMask) {
        int avatarWidth = avatar.getWidth();
        int avatarHeight = avatar.getHeight();

        BufferedImage buffer = createReflection(avatar,
                                                avatarWidth, avatarHeight);
        applyAlphaMask(buffer, alphaMask, avatarWidth, avatarHeight);

        return buffer;
    }

    private void applyAlphaMask(BufferedImage buffer,
                                BufferedImage alphaMask,
                                int avatarWidth, int avatarHeight) {
        Graphics2D g2 = buffer.createGraphics();
        g2.setComposite(AlphaComposite.DstOut);
        g2.drawImage(alphaMask, null, 0, avatarHeight);
        g2.dispose();
    }

    private BufferedImage createReflection(BufferedImage avatar,
                                           int avatarWidth,
                                           int avatarHeight) {
        BufferedImage buffer = new BufferedImage(avatarWidth, avatarHeight << 1,
                                                 BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = buffer.createGraphics();
        g.drawImage(avatar, null, null);
        g.translate(0, avatarHeight << 1);

        AffineTransform reflectTransform = AffineTransform.getScaleInstance(1.0, -1.0);
        g.drawImage(avatar, reflectTransform, null);
        g.translate(0, -(avatarHeight << 1));

        g.dispose();

        return buffer;
    }

    private BufferedImage createGradientMask(int avatarWidth, int avatarHeight) {
        BufferedImage gradient = new BufferedImage(avatarWidth, avatarHeight,
                                                   BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gradient.createGraphics();
        GradientPaint painter = new GradientPaint(0.0f, 0.0f,
                                                  new Color(1.0f, 1.0f, 1.0f, 0.5f),
                                                  0.0f, avatarHeight / 2.0f,
                                                  new Color(1.0f, 1.0f, 1.0f, 1.0f));
        g.setPaint(painter);
        g.fill(new Rectangle2D.Double(0, 0, avatarWidth, avatarHeight));

        g.dispose();

        return gradient;
    }

    private DrawableAvatar getHitAvatar(int x, int y) {
        for (DrawableAvatar avatar: drawableAvatars) {
            Rectangle hit = new Rectangle((int) avatar.getX(), (int) avatar.getY(),
                                          avatar.getWidth(), avatar.getHeight() / 2);
            if (hit.contains(x, y)) {
                return avatar;
            }
        }

        return null;
    }

    private class PicturesFinderThread implements Runnable {
        @Override
        public void run() {
            for(int i=0; i<6; i++ ) {
                BufferedImage image = (BufferedImage) ImageUtilities.loadImage("org/clothocore/tool/pluginmanager/images/image" + i + ".png", false);
                avatars.add(createReflectedPicture(image));
                String filename = "image" + i + ".png";

                if (i == (3) + avatarAmount / 2) {
                    setAvatarIndex(i - avatarAmount / 2);
                    startFader();
                    System.out.println(filename);
                }
            }

            loadingDone = true;
        }
    }

    private class FaderAction implements ActionListener {
        private long start = 0;

        private FaderAction() {
            alphaLevel = 0.0f;
            textAlphaLevel = 0.0f;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (start == 0) {
                start = System.currentTimeMillis();
            }

            alphaLevel = (System.currentTimeMillis() - start) / 500.0f;
            textAlphaLevel = alphaLevel;

            if (alphaLevel > 1.0f) {
                alphaLevel = 1.0f;
                textAlphaLevel = 1.0f;
                faderTimer.stop();
            }

            repaint();
        }
    }

    private class DrawableAvatar implements Comparable {
        private int index;
        private double x;
        private double y;
        private int width;
        private int height;
        private double zOrder;
        private double position;

        private DrawableAvatar(int index,
                               double x, double y, int width, int height,
                               double position, double zOrder) {
            this.index = index;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.position = position;
            this.zOrder = zOrder;
        }

        @Override
        public int compareTo(Object o) {
            double zOrder2 = ((DrawableAvatar) o).zOrder;

            if (zOrder < zOrder2) {
                return -1;
            } else if (zOrder > zOrder2) {
                return 1;
            }
            return 0;
        }
       
        public double getPosition() {
            return position;
        }

        public double getAlpha() {
            return zOrder * alphaLevel;
        }

        public int getHeight() {
            return height;
        }

        public int getIndex() {
            return index;
        }

        public int getWidth() {
            return width;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    private class MouseWheelScroller implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int increment = e.getWheelRotation();
            scrollAndAnimateBy(increment);
        }
    }

    private class KeyScroller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_UP:
                    scrollAndAnimateBy(-1);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_DOWN:
                    scrollAndAnimateBy(1);
                    break;
                case KeyEvent.VK_END:
                    scrollBy(avatars.size() - avatarIndex - 1);
                    break;
                case KeyEvent.VK_HOME:
                    scrollBy(-avatarIndex - 1);
                    break;
                case KeyEvent.VK_PAGE_UP:
                    scrollAndAnimateBy(-avatarAmount / 2);
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    scrollAndAnimateBy(avatarAmount / 2);
                    break;
            }
        }
    }

    private class FocusGrabber extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            requestFocus();
        }
    }

    private class AvatarScroller extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(rcPopup!=null) {
                rcPopup.remove();
            }
            if ((faderTimer != null && faderTimer.isRunning()) ||
                (scrollerTimer != null && scrollerTimer.isRunning()) ||
                drawableAvatars == null) {
                return;
            }

            //Scroll to that avatar
            DrawableAvatar avatar = getHitAvatar(e.getX(), e.getY());
            if (avatar != null && avatar.getIndex() != avatarIndex) {
                scrollAndAnimate(avatar);
                return;
            }

            if(e.getY()<50) {
                _app.close();
                return;
            }

            //If it's anything else, do this:
            switch(avatarIndex) {
                case 0:
                    _app.ReBuildPanel(0);
                    break;
                case 1:
                    _app.ReBuildPanel(1);
                    break;
                case 2:
                    _app.ReBuildPanel(2);
                    break;
                case 3:
                    _app.ReBuildPanel(3);
                    break;
                case 4:
                    BareBonesBrowserLaunch.openURL("http://wiki.bu.edu/ece-clotho/index.php/Plugin_Manager");
                    break;
                case 5:
                    BareBonesBrowserLaunch.openURL("http://www.bu.edu/dbin/ece-clotho/index.php/app_store");
                    break;
            }
            return;
        }
    }

    private class DamageManager extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            damaged = true;
        }
    }

    private class AutoScroller implements ActionListener {
        private double position;
        private int index;
        private long start;

        private AutoScroller(DrawableAvatar avatar) {
            this.index = avatar.getIndex();
            this.position = avatar.getPosition();
            this.start = System.currentTimeMillis();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < ANIM_SCROLL_DELAY / 2.0) {
                textAlphaLevel = (float) (1.0 - 2.0 * (elapsed / ANIM_SCROLL_DELAY));
            } else {
                textAlphaLevel = (float) (((elapsed / ANIM_SCROLL_DELAY) - 0.5) * 2.0);
                if (textAlphaLevel > 1.0f) {
                    textAlphaLevel = 1.0f;
                }
            }

            if (textAlphaLevel < 0.1f) {
                textAlphaLevel = 0.1f;
                textAvatar = " ";
            }

            double newPosition = (elapsed / ANIM_SCROLL_DELAY) * -position;

            if (elapsed >= ANIM_SCROLL_DELAY) {
                ((Timer) e.getSource()).stop();

                setAvatarIndex(index);
                setPosition(0.0);
                return;
            }

            setPosition(newPosition);
        }
    }

    private class CursorChanger extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if ((scrollerTimer != null && scrollerTimer.isRunning()) ||
                drawableAvatars == null) {
                return;
            }

            DrawableAvatar avatar = getHitAvatar(e.getX(), e.getY());
            if (avatar != null) {
                getParent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}