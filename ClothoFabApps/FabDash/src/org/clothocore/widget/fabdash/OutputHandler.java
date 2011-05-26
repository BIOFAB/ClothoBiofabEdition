package org.clothocore.widget.fabdash;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.IOColorLines;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputListener;

/**
 *
 * @author Javier A. Ortiz Bultrón <javier.ortiz.78@gmail.com>
 */
public final class OutputHandler {

    private OutputHandler() {
    }
    private static ArrayList<String> outputMap = new ArrayList<String>();

    public static void output(String name, String mess, Color c) {
        output(name, mess, c, null);
    }

    public static void output(String name, String mess, OutputListener listener) {
        output(name, mess, Color.BLACK, listener);
    }

    /**
     * Print in specified color
     * @param name Tab name
     * @param mess Message to display
     * @param c Color to display the method
     * @param listener
     */
    public static void output(String name, String mess, Color c, OutputListener listener) {
        boolean select = getIO(name) || !outputMap.contains(name);
        InputOutput io = IOProvider.getDefault().getIO(name, select);
        if (select) {
            io.select();
        }
        io.setFocusTaken(false);
        if (mess == null || mess.trim().isEmpty()) {
            if (listener == null) {
                io.getOut().print(mess);
            } else {
                try {
                    io.getOut().println(mess, listener);
                } catch (IOException ex1) {
                    Logger.getLogger(OutputHandler.class.getSimpleName(), ex1.getMessage());
                    io.getOut().print(mess);
                }
            }
        } else {
            if (c != null && c != Color.BLACK) {
                try {
                    if (listener == null) {
                        IOColorLines.println(io, mess, c);
                    } else {
                        IOColorLines.println(io, mess, listener, true, c);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OutputHandler.class.getSimpleName(), ex.getMessage());
                    if (listener == null) {
                        io.getOut().print(mess);
                    } else {
                        try {
                            io.getOut().println(mess, listener);
                        } catch (IOException ex1) {
                            Logger.getLogger(OutputHandler.class.getSimpleName(), ex1.getMessage());
                            io.getOut().print(mess);
                        }
                    }
                }
            } else {
                //Just print in black as default
                if (listener == null) {
                    io.getOut().print(mess);
                } else {
                    try {
                        io.getOut().println(mess, listener);
                    } catch (IOException ex1) {
                        Logger.getLogger(OutputHandler.class.getSimpleName(), ex1.getMessage());
                        io.getOut().print(mess);
                    }
                }
            }
        }
    }

    /**
     * Print in default color black
     * @param name Tab name
     * @param mess Message to display
     */
    public static void output(String name, String mess) {
        output(name, mess, Color.BLACK, null);
    }

    /**
     * Clear a tab (close and so it'll be recreated next time)
     * @param name
     */
    public static void clear(String name) {
        if (outputMap.contains(name)) {
            try {
                //Remove it
                IOProvider.getDefault().getIO(name, false).getOut().reset();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private static boolean getIO(String name) {
        boolean create = true;
        if (outputMap.contains(name)) {
            create = false;
        } else {
            outputMap.add(name);
        }
        return create;
    }

    /**
     * Close all I/O tabs (defined)
     */
    public static void closeOutputs() {
        for (String name : outputMap) {
            IOProvider.getDefault().getIO(name, false).closeInputOutput();
        }
        outputMap.clear();
    }

    /**
     * Select an output tab (if it exists)
     * @param name
     */
    public static void select(String name) {
        if (outputMap.contains(name)) {
            IOProvider.getDefault().getIO(name, getIO(name)).select();
        }
    }

    /**
     * Update the message in the status windows of the platform
     * @param mess
     */
    public static void setStatus(String mess) {
        StatusDisplayer.getDefault().setStatusText(mess);
    }
}