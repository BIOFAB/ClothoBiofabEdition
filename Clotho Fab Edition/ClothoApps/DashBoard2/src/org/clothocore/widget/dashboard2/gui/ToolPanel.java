/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.clothocore.widget.dashboard2.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.clothocore.api.core.Collator;
import org.clothocore.api.core.wrapper.ToolWrapper;

/**
 *
 * @author jcanderson
 */
public class ToolPanel extends JPanel {

    public ToolPanel(Application app, int p) {
        pos = p ;
        _app = app;
        ItemPanel.setSizes();
        initcomponents();
    }

    void initcomponents() {
        this.removeAll();
        setOpaque(false);
        setLayout(null);
        setSize(210,210);

        JPanel grid = new JPanel();
        grid.setBounds(7, 50, 203, 210);
        grid.setOpaque(false);
        GridLayout gl = new GridLayout(ItemPanel.numRow,ItemPanel.numCol);
        gl.setHgap(0);
        gl.setVgap(0);
        grid.setLayout(gl);
        add(grid);

        int count = _app.buttonPanel.currPanel * ItemPanel.getNumPerToolPanel();
        ArrayList<ToolWrapper> _wraps = Collator.getAllTools();

        Loopy: for(int j=0; j<ItemPanel.numRow; j++) {
            for(int i=0; i< ItemPanel.numCol; i++) {
                if(_wraps.size()>count) {
                    ToolWrapper tw = _wraps.get(count);
                    count++;
                    ItemPanel ip = new ItemPanel(tw, i, j, this);
                    grid.add(ip);
                } else {
                    grid.add(new JLabel(""));
                }
            }
        }
    }

    void changeNumCols(int newNumCols) {
        if(newNumCols < 3 || newNumCols > 5) {
            return;
        }
        Collator.putPreference("DashBoard2numCols", Integer.toString(newNumCols));
        ItemPanel.numCol = newNumCols;
        ItemPanel.setSizes();
        _app.buttonPanel.currPanel = 0;
        _app.buttonPanel.calculateNumPanels();
        _app.buttonPanel.setLeftAndRightButtons();
        initcomponents();
        validate();
        _app.validate();
    }

/*-----------------
     variables
 -----------------*/
int pos;
Application _app;
}
