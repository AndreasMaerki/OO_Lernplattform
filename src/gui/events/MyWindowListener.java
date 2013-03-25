package gui.events;

import gui.GUIConstants;
import gui.Gui;
import java.awt.event.*;
import javax.swing.JOptionPane;
import logic.iLogic;

/**
 * Implementiert den WindowListener an zentraler stelle fuer alle WindowEvents.
 *
 * @author Dimitri Balazs, Andreas Maerki
 * @version 1.00, 12 August 2012
 */
public class MyWindowListener extends WindowAdapter {

    // members
    private Gui mGui;
    private iLogic mLogic;

    /**
     * Setzt die Beziehung zu iLogic
     *
     * @author Dimitri Balazs, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pLogic Objekt vom Typ iLogic
     */
    public void setRelations(iLogic pLogic) {
        mGui = Gui.getInstance();
        mLogic = pLogic;
    }// ende setRelations

    /**
     * Fuehrt die gewuenschte Aktion aus.
     *
     * @author Dimitri Balazs, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param e 
     */
    @Override
    public void windowClosing(WindowEvent e) {
        int abfrage = JOptionPane.showConfirmDialog(
                mGui,
                GUIConstants.CLOSEAPP, GUIConstants.CLOSE,
                JOptionPane.YES_NO_OPTION);

        if (abfrage == JOptionPane.YES_OPTION) {
            if (mLogic.isEdited()) {
                abfrage = JOptionPane.showConfirmDialog(
                        mGui,
                        GUIConstants.SAVEFILES, GUIConstants.SAVE,
                        JOptionPane.YES_NO_OPTION);

                if (abfrage == JOptionPane.YES_OPTION) {
                    mLogic.saveFiles();
                } else {
                    //Do Nothing
                }
                mGui.setVisible(false);
                mGui.dispose();
                mLogic.exit();
            } else {
                mGui.setVisible(false);
                mGui.dispose();
                mLogic.exit();
            }
        } else {
            //Do Nothing
        }// ende else
    }// ende windowClosing
}// ende MyWindowListener

