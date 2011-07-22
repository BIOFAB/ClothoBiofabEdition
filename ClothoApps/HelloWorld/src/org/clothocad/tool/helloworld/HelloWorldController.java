/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clothocad.tool.helloworld;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.openide.windows.TopComponent;

/**
 *
 * @author jenhan
 */
public class HelloWorldController {

    public HelloWorldController(JFrame frame) {
        _frameView = frame;
        _isTC = false;
    }
    private TopComponent _tcView;
    private JFrame _frameView;
    private boolean _isTC;

    public void switchViews() {
        if (_isTC) {
            Component[] components = _tcView.getComponents();
            _frameView = new JFrame();
            _frameView.setContentPane((Container) components[1]);
            _frameView.setJMenuBar((JMenuBar) components[0]);
            _frameView.pack();
            _frameView.setVisible(true);
            _isTC = false;
            _tcView.close();
        } else {
            final JComponent guiContentPane = (JComponent) _frameView.getContentPane();
            final JMenuBar menu = _frameView.getJMenuBar();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    _tcView = new TopComponent();
                    _tcView.setLayout(new BorderLayout());
                    JScrollPane sp = new JScrollPane(guiContentPane);
                    _tcView.add(menu, BorderLayout.NORTH);
                    _tcView.add(sp, BorderLayout.CENTER);
                    _tcView.setName("Hello World");
                    _tcView.open();
                    _tcView.requestActive();

                }
            });
            _frameView.dispose();
            _isTC = true;
        }
    }
}
