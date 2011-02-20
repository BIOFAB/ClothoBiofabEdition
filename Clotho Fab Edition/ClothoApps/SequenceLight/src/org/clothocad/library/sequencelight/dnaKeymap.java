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

package org.clothocad.library.sequencelight;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

/**
 *
 * @author J. Christopher Anderson
 */
public class dnaKeymap implements Keymap {
    private String name;
    private Keymap parent;
    private HashMap<KeyStroke,Action> map;
    private Action defaultAction;
    private boolean degeneracy = false;
    private boolean basemods = false;
    private boolean rna = false;
    public static final int ALLOW_DEGENERACY = 2;
    public static final int ALLOW_BASEMODS = 3;
    public static final int ALLOW_URACIL = 5;

    /**
     * The blank constructor will enable the degeneracy code letters only
     */
    public dnaKeymap() {
        this(2);
    }
    /**
     * If you want to enable all the degeneracy codes, can put in
     * @param deg
     */
    public dnaKeymap(int mods) {
        if(mods%2==0) {
            degeneracy = true;
        }
        if(mods%5==0) {
            rna = true;
        }
        if(mods%3==0) {
            basemods = true;
        }
        removeBindings();
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Action getDefaultAction() {
        return null;
    }

    public void setDefaultAction(Action a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Action getAction(KeyStroke key) {
       if (map.containsKey(key)) {
           return map.get(key);
       }
       else {
           return null;
       }
    }

    public KeyStroke[] getBoundKeyStrokes() {
        KeyStroke[] strokes = new KeyStroke[map.size()];
        int count=0;
        for(KeyStroke k : strokes) {
            strokes[count] = k;
            count++;
        }
        return strokes;
    }

    public Action[] getBoundActions() {
      return null;
    }

    public boolean isLocallyDefined(KeyStroke key) {
        return map.containsKey(key);
    }

    public void addActionForKeyStroke(KeyStroke key, Action a) {
        map.put(key,a);
    }

    public void removeKeyStrokeBinding(KeyStroke keys) {
        map.remove(keys);
    }

    public void removeBindings() {
        map = new HashMap<KeyStroke,Action>();
        JTextArea jta = new JTextArea();
        parent = jta.getKeymap();
        defaultAction= parent.getDefaultAction();

        map.put(KeyStroke.getKeyStroke('a'), defaultAction);
        map.put(KeyStroke.getKeyStroke('A'), defaultAction);
        map.put(KeyStroke.getKeyStroke('t'), defaultAction);
        map.put(KeyStroke.getKeyStroke('T'), defaultAction);
        map.put(KeyStroke.getKeyStroke('c'), defaultAction);
        map.put(KeyStroke.getKeyStroke('C'), defaultAction);
        map.put(KeyStroke.getKeyStroke('g'), defaultAction);
        map.put(KeyStroke.getKeyStroke('G'), defaultAction);
        if(degeneracy) {
            map.put(KeyStroke.getKeyStroke('n'), defaultAction);
            map.put(KeyStroke.getKeyStroke('N'), defaultAction);
            map.put(KeyStroke.getKeyStroke('r'), defaultAction);
            map.put(KeyStroke.getKeyStroke('R'), defaultAction);
            map.put(KeyStroke.getKeyStroke('y'), defaultAction);
            map.put(KeyStroke.getKeyStroke('Y'), defaultAction);
            map.put(KeyStroke.getKeyStroke('m'), defaultAction);
            map.put(KeyStroke.getKeyStroke('M'), defaultAction);
            map.put(KeyStroke.getKeyStroke('k'), defaultAction);
            map.put(KeyStroke.getKeyStroke('K'), defaultAction);
            map.put(KeyStroke.getKeyStroke('s'), defaultAction);
            map.put(KeyStroke.getKeyStroke('S'), defaultAction);
            map.put(KeyStroke.getKeyStroke('w'), defaultAction);
            map.put(KeyStroke.getKeyStroke('W'), defaultAction);
            map.put(KeyStroke.getKeyStroke('h'), defaultAction);
            map.put(KeyStroke.getKeyStroke('H'), defaultAction);
            map.put(KeyStroke.getKeyStroke('b'), defaultAction);
            map.put(KeyStroke.getKeyStroke('B'), defaultAction);
            map.put(KeyStroke.getKeyStroke('v'), defaultAction);
            map.put(KeyStroke.getKeyStroke('V'), defaultAction);
            map.put(KeyStroke.getKeyStroke('d'), defaultAction);
            map.put(KeyStroke.getKeyStroke('D'), defaultAction);
        }
        if(basemods) {
            //For biotinylation
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_MASK), defaultAction);
            //For phosphthiorylation
            map.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK), defaultAction);
        }
        if(rna) {
            map.put(KeyStroke.getKeyStroke('u'), defaultAction);
            map.put(KeyStroke.getKeyStroke('U'), defaultAction);
        }
    }

    public Keymap getResolveParent() {
        return null;
    }

    public void setResolveParent(Keymap p) {
    }

    public KeyStroke[] getKeyStrokesForAction(Action a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /* SETTERS
     * */

    /* PUTTERS
     * */

    /* GETTERS
     * */

/*-----------------
     variables
 -----------------*/
}
