package gui.events;

import java.awt.Color;
import javax.swing.text.DefaultHighlighter;

/**
 * Default Highlighter mit der Information der Liniennummer
 *
 * @author Dimitri Balazs
 * @version 0.50 12 August 2012
 *
 */
class MyHighlighter extends DefaultHighlighter.DefaultHighlightPainter {

    int mLineNumber;

    public MyHighlighter(Color c, int LineNumber) {
        super(c);
        mLineNumber = LineNumber;
    }// ende MyHighlighter
}// ende MyHighlighter
