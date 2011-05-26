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

package org.clothocore.widget.dashboard2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import org.clothocore.api.actor.Actor;
import org.clothocore.api.actor.data.ObjBaseToken;
import org.clothocore.api.actor.data.StringToken;
import org.clothocore.api.core.wrapper.AlgorithmWrapper;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.Collector;
import org.clothocore.api.data.Collection;
import org.clothocore.util.buttons.TransparentButton;
import org.openide.util.ImageUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class SuperSearch extends JPanel {

    public SuperSearch(final Application app) {
        _app = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        this.setBorder(blackline);
        jtf = new JTextField();
        ImageIcon searchImg = ImageUtilities.loadImageIcon( "org/clothocore/widget/dashboard2/images/search.png", false );
        searchTB = new TransparentButton(searchImg);
        searchTB.setExitAlpha(1.0f);
        searchTB.setEnterAlpha(0.8f);
        searchTB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startSearch();
            }
        });
        this.add(jtf, BorderLayout.CENTER);
        this.add(searchTB, BorderLayout.EAST);

        //Listen for ctrl-O
        jtf.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "search");
        jtf.getActionMap().put("search",
            new AbstractAction("search") {
            @Override
                public void actionPerformed(ActionEvent evt) {
                    startSearch();
                }});

/*
        //Make this always in focus
        jtf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(_app.blurrer.hasFocus()) {
                    return;
                }
                if(searchTB.hasFocus()) {
                    return;
                }
                jtf.requestFocusInWindow();
            }
        });

        searchTB.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                jtf.requestFocusInWindow();
            }
        });
 */
    }

    /* SETTERS
     * */

    private void startSearch() {
        if(!Collector.isConnected()) {
            JOptionPane.showMessageDialog( null, "Database connection required to run search, connect to the database first!",
                                           "Not connected", JOptionPane.ERROR_MESSAGE );
            jtf.requestFocusInWindow();
            return;
        }
        _app.blurrer.setBlurring(true, "Searching...");
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    runSearch();
                } catch(Exception err) {
                    err.printStackTrace();
                }
                _app.blurrer.setBlurring(false, "Searching...");
                jtf.requestFocusInWindow();
                return null;
            }
        }.execute();
    }

    public void runSearch() {
        //Get the Actors from the plugin
        AlgorithmWrapper saa = (AlgorithmWrapper) Collator.getPluginByUUID("org.clothocad.algorithm.searchalgorithm");
        if(saa==null) {
            return;
        }
        System.out.println("Searcher: " + saa.getDescription());
        Actor searchActor = saa.getAlgorithm().createAlgorithm();

        //Create a Stringtoken input to the actor and put it into Inport
        String query = jtf.getText();
        System.out.println("Searching query: " + query);
        System.out.println("SearchActor: " + searchActor);

        StringToken s1 = new StringToken(query);
        System.out.println("Token: " + s1.getData());
        searchActor.getInputs().get(0).put( s1 );

        //Run it
        System.out.println( searchActor.run() );
        ObjBaseToken out = (ObjBaseToken) searchActor.getOutputs().get(0).get();
        Collection outcoll = (Collection) out.getData();
        outcoll.launchDefaultViewer();
    }

/*-----------------
     variables
 -----------------*/
    JTextField jtf;
    Application _app;
    TransparentButton searchTB;
    private static final Border blackline = BorderFactory.createLineBorder(Color.BLACK);
}
