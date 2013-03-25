package gui;

import com.sun.org.apache.bcel.internal.classfile.LineNumber;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

/** CodeView Panel
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class CodePanel extends JTabbedPane implements DocumentListener {

    private JTextArea mTextArea;
    private JTextArea mLines;
    private UndoManager mUndoManager;
    private JScrollPane mScrollPane;
    private int mFontsize = 16;
    private String mText;
    private File mFile;

    /**
     *Konstruktor
     */
    public CodePanel() {
        super();
    }//Ende Konstruktor

    /** Überladener Konstruktor, erzeugt neues Tab und zeigt Code in Codepanel an
     * 
     * @param pText
     * @param pTitle
     * @param pFile
     * @param pMyKeyListener
     * @param pMyMouseListener
     */
    public CodePanel(String pText, String pTitle, File pFile, gui.events.MyKeyListener pMyKeyListener,
            gui.events.MyMouseListener pMyMouseListener) {

        mText = pText;
        setName(pTitle);
        mFile = pFile;

        mUndoManager = new UndoManager();
        mTextArea = new JTextArea();
        mScrollPane = new JScrollPane();
        mLines = new JTextArea();

        /*
         * Layout des Panels
         */
        setLayout(new BorderLayout());

        /*
         *JtextArea erzeugen
         */

        mTextArea.getDocument().addDocumentListener(this);
        mTextArea.getDocument().addUndoableEditListener(mUndoManager);


        mTextArea.setComponentPopupMenu(GUIHelper.getJPopupMenuInstance());

        mTextArea.setBackground(Color.white);
        mTextArea.setForeground(Color.BLACK);
        mTextArea.setFont(new Font("Monospaced", Font.PLAIN, mFontsize));
        mTextArea.setName("CodeArea");


        mTextArea.addKeyListener(pMyKeyListener);
        mLines.addMouseListener(pMyMouseListener);

        /*
         * Linien Nummern erzeugen
         */
        mLines.setBackground(Color.LIGHT_GRAY);
        mLines.setEditable(false);
        mLines.setFont(new Font("Monospaced", Font.PLAIN, mFontsize));
        mLines.setName(pTitle);

        /*
         * Scrollpanel erzeugen
         */
        mScrollPane.getViewport().add(mTextArea);
        mScrollPane.setRowHeaderView(mLines);
        mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        /*
         * Text hinzufügen
         */
        mTextArea.setText(mText);

        //ScrollPane dem Panel übergeben
        add(mScrollPane);


        /*
         * Frame sichtbar machen
         */
        setVisible(true);

    }//Ende Überladener Konstruktor

    /**
     *
     * @return gibt mLines zurück
     */
    public JTextArea getLineArea() {
        return mLines;
    }

    /**
     *
     * @return gibt mTextArea zurück
     */
    public JTextArea getTextArea() {
        return mTextArea;
    }

    /**
     * 
     * @return gibt mFile zurück
     */
    public File getFile() {
        return mFile;
    }

    /** Setzt Fontsize
     *
     * 
     * @param mFontsize
     */
    public void setFontSize(int mFontsize) {
        this.mFontsize = mFontsize;
    }

    /**
     *
     * @return gibt mUndoManager zurück
     */
    public UndoManager getUndoManager() {
        return mUndoManager;
    }

    /*
     * Document Listener und Events
     */
    public void changedUpdate(DocumentEvent de) {
        mLines.setText(getText());
    }

    public void insertUpdate(DocumentEvent de) {
        mLines.setText(getText());
    }

    public void removeUpdate(DocumentEvent de) {
        mLines.setText(getText());
    }

    private String getText() {
        int caretPosition = mTextArea.getDocument().getLength();

        Element root = mTextArea.getDocument().getDefaultRootElement();
        String text = "";
        for (int i = 1; i < root.getElementIndex(caretPosition) + 2; i++) {
            for (int x = 3; x > String.valueOf(i).length(); x--) {
                text += " ";
            }
            text += i + "\n";
        }
        return text;
    }
}//Ende CodePanel

