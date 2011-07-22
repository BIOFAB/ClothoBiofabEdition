package moo;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;

public class NoCopyPasteFrame {

    private JFrame myFrame;
    private JTextField myTextField;

    public void doit() {
        myFrame = new JFrame("no cut and paste");
        myFrame.setSize(400, 200);
        myFrame.setLocation(100, 100);
        myFrame.setLayout(new java.awt.FlowLayout());
        myTextField = new JTextField(20);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JTextComponent.KeyBinding[] newBindings = {
            new JTextComponent.KeyBinding(
            KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK),
            DefaultEditorKit.writableAction),
            new JTextComponent.KeyBinding(
            KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK),
            DefaultEditorKit.writableAction),
            new JTextComponent.KeyBinding(
            KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK),
            DefaultEditorKit.writableAction)
        };
        Keymap k = myTextField.getKeymap();
        JTextComponent.loadKeymap(k, newBindings, myTextField.getActions());


        myFrame.add(myTextField);
        myFrame.addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {
                myTextField.requestFocus();
            }

            public void windowActivated(WindowEvent e) {
                boolean focus = myTextField.requestFocusInWindow();
                if (focus) {
                    System.out.println("Focus successful");
                } else {
                    System.out.println("Focus unsuccessful");
                }
            }
        });
        myFrame.setVisible(true);
    }

    public static void main(String args[]) {
        new NoCopyPasteFrame().doit();
    }
}
