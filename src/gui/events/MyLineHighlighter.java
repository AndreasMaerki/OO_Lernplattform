package gui.events;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/** CodeView MyHiglighter
 *
 *
 * @autor Christian Binder
 * @version 1.00, 12. August 2012
 *
 */
public class MyLineHighlighter implements Highlighter.HighlightPainter {

    int mLineNumber;
    Color mColor;

    /**
     * Konstruktor von MyHiglighter
     */
    public MyLineHighlighter(Color c, int LineNumber) {
        mColor = c;
        mLineNumber = LineNumber;
    }//Ende Konstruktor von MyHiglighter

    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {

        int height = c.getFontMetrics(c.getFont()).getHeight();
        int width = c.getWidth();
        g.setColor(mColor);
        //Offset von 6, da die 1. Linie ebenfalls mit einem Offset geschrieben wird
        int y = height * (mLineNumber - 1) + 6;
        g.fillRect(0, y, width, height);

    }
}//Ende MyLineHighlighter
