package gui.events;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraphView;
import gui.CodePanel;
import gui.GUIConstants;
import gui.GUIHelper;
import gui.Gui;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JTextArea;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import logic.iLogic;

/**
 * Implementirt den KeyListener fuer saemtliche Tastaturkommandos
 *
 * @author Dimitri Balazs
 * @version 1.00, 12 August 2012
 */
public class MyKeyListener extends KeyAdapter {

    private int mAutoSaveCount;
    private iLogic mLogic;
    private Gui mGui;

    /**
     * Setzt die beziehung zu iLogic und der Gui
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @param pLogic iLogic referenz
     */
    public void setRelations(iLogic pLogic) {

        mLogic = pLogic;
        mGui = gui.Gui.getInstance();
    }// ende setRelations

    /**
     * Wertet die KeyEvents aus
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     *
     * @param event
     */
    public void keyPressed(KeyEvent event) {

        int key = event.getKeyCode();

        //Wird überprüft, da ModelToView keine Instanz von mLogic besitzt
        if (mLogic != null) {
            mLogic.setEditFlag();
        }

        if (event.isControlDown() | event.isMetaDown()) {

            //Zoomfunktion der CodePanels;
            if (event.getSource() instanceof JTextArea) {
                JTextArea textArea = (JTextArea) event.getSource();

                Object supposedCodePanel = textArea.getParent().getParent().getParent();
                if (supposedCodePanel instanceof CodePanel) {
                    CodePanel codePanel = (CodePanel) supposedCodePanel;
                    try {
                        if (key == KeyEvent.VK_Z) {
                            codePanel.getUndoManager().undo();
                        } else if (key == KeyEvent.VK_Y) {
                            codePanel.getUndoManager().redo();
                        }// ende else if
                    } catch (CannotRedoException e) {
                        //Ignore
                    } catch (CannotUndoException e) {
                        //Ignore
                    }// end catch
                    Font tmpFont = textArea.getFont();
                    int size = tmpFont.getSize();
                    if (key == 107) {
                        size++;
                    } else if (key == 109) {
                        size--;
                    }// ende else if

                    tmpFont = new Font(tmpFont.getName(), tmpFont.getStyle(), size);

                    codePanel.getLineArea().setFont(tmpFont);

                    textArea.setFont(tmpFont);
                }// ende if CodePanel

                //Zoomfunktion der Graphen
            } else if (event.getSource() instanceof mxGraphComponent) {
                mxGraphComponent graphComponent = (mxGraphComponent) event.getSource();
                mxGraphView view = graphComponent.getGraph().getView();
                double scale = view.getScale();
                if (key == 107) {
                    scale += 0.1;
                } else if (key == 109) {
                    scale -= 0.1;
                }// ende else if
                view.setScale(scale);
            }// ende else if

            //Dateien speichern
            if (key == KeyEvent.VK_S) {
                mLogic.saveFiles();
            } else if (key == KeyEvent.VK_N || key == KeyEvent.VK_T) {//Neue Datei erstellen oder neues Tab öffnen
                GUIHelper.getFileChooser(GUIConstants.NEWFILE);
            } else if (key == KeyEvent.VK_Q) {//Programm beenden
                WindowEvent we = new WindowEvent(mGui, WindowEvent.WINDOW_CLOSING);
                for (WindowListener listener : mGui.getWindowListeners()) {
                    listener.windowClosing(we);
                }// ende for
            } else if (key == KeyEvent.VK_O) {//Änderungen rückgangig
                GUIHelper.getFileChooser(GUIConstants.OPENFILE);
            }// ende else if
        }// end if event.isControlDown() | event.isMetaDown()
    }// ende keyPressed
}// ende MyKeyListener

