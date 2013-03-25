package gui.events;

import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import logic.iLogic;
import gui.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFileChooser;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * Implementiert den ActionListener an zentraler stelle fuer etliche
 * ActionEvents.
 *
 * @author Dimitri Balazs
 * @version 1.00, 12 August 2012
 */
public class MyActionListener implements ActionListener {

    private Gui mGui;
    private iLogic mLogic;

    /**
     * Setzt die Beziehung zu iLogic
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @param pLogic Objekt vom Typ iLogic
     */
    public void setRelations(iLogic pLogic) {
        mGui = Gui.getInstance();
        mLogic = pLogic;
    }

    /**
     * Fuehrt die gewuenschte Aktion aus.
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @param e uebergebener ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            switch (button.getName()) {
                case GUIConstants.START:
                    mLogic.useDebugger(iLogic.DEBUG_RUN);
                    break;
                case GUIConstants.CONTINUE:
                    mLogic.useDebugger(iLogic.DEBUG_CONTINUE);
                    break;
                case GUIConstants.STEP:
                    mLogic.useDebugger(iLogic.DEBUG_STEP);
                    break;
                case GUIConstants.STEPIN:
                    mLogic.useDebugger(iLogic.DEBUG_STEPIN);
                    break;
                case GUIConstants.STEPOUT:
                    mLogic.useDebugger(iLogic.DEBUG_STEPOUT);
                    break;
                case GUIConstants.STOP:
                    mLogic.useDebugger(iLogic.DEBUG_STOP);
                    break;
                default:
            }// ende switch


        } else if (e.getSource() instanceof JMenuItem) {
            JMenuItem menuitem = (JMenuItem) e.getSource();

            switch (menuitem.getName()) {
                case GUIConstants.NEWFILE:
                    GUIHelper.getFileChooser(GUIConstants.NEWFILE);
                    break;
                case GUIConstants.OPENFILE:

                    GUIHelper.getFileChooser(GUIConstants.OPENFILE);
                    break;
                case GUIConstants.SAVE:
                    mLogic.saveFiles();
                    break;
                case GUIConstants.CLOSE:
                    WindowEvent we = new WindowEvent(mGui, WindowEvent.WINDOW_CLOSING);
                    for (WindowListener listener : mGui.getWindowListeners()) {
                        listener.windowClosing(we);
                    }
                    break;
                case GUIConstants.REDO:
                    try {
                        mGui.getCodeView().getSelectedCodePanel().getUndoManager().redo();
                    } catch (CannotRedoException ex) {
                        //Ignore
                    }
                    break;
                case GUIConstants.UNDO:
                    try {
                        mGui.getCodeView().getSelectedCodePanel().getUndoManager().undo();
                    } catch (CannotUndoException ex) {
                        //Ignore
                    }
                    break;
                default:
            }// ende swich
        }// ende else if
    }// ende actionPerformed
}// ende MyActionListener

