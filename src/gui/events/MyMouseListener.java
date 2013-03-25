package gui.events;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import logic.iLogic;

/**
 * Implementiert den MouseListener an zentraler stelle fuer alle MouseEvents.
 *
 * @author Dimitri Balazs, Andreas Maerki
 * @version 1.00, 12 August 2012
 */
public class MyMouseListener extends MouseAdapter {

    private iLogic mLogic;

    /**
     * Setzt die Beziehung zu iLogic
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @param pLogic Objekt vom Typ iLogic
     */
    public void setRelations(iLogic pLogic) {
        mLogic = pLogic;
    }

    /**
     * Fuehrt die gewuenschte Aktion aus.
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @param me
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() instanceof JTextArea) {
            JTextArea textArea = (JTextArea) me.getSource();


            try {
                Highlighter highlighter = textArea.getHighlighter();
                int caretPos = textArea.getCaretPosition();
                if (caretPos > 6) {
                    int LineNumber = (caretPos / 4) + 1;
                    Highlighter.Highlight[] highlights = highlighter.getHighlights();
                    int remove = -1;
                    for (int i = 0; i < highlights.length; i++) {
                        if (highlights[i].getPainter() instanceof MyHighlighter) {
                            MyHighlighter tmp = (MyHighlighter) highlights[i].getPainter();
                            if (tmp.mLineNumber == LineNumber) {
                                remove = i;
                                mLogic.removeBreakPoint(textArea.getName(), LineNumber);
                            }// ende if
                        }// ende if
                    }// ende for
                    if (remove > -1) {
                        highlighter.removeHighlight(highlights[remove]);
                    } else {
                        int indexOfLineNumber = textArea.getText().indexOf(String.valueOf(LineNumber));
                        int start = textArea.getText().lastIndexOf("\n", indexOfLineNumber);
                        highlighter.addHighlight(start, start + 4, new MyHighlighter(Color.red, LineNumber));
                        mLogic.setBreakPoint(textArea.getName(), LineNumber);
                    }// ende else
                }// ende if caretPos > 6
            } catch (Exception e) {
                e.printStackTrace();
            }//  ende catch
        }// ende if JTextArea
    }// ende mouseClicked
} // ende MyMouseListener

