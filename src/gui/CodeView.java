package gui;

import data.XMLModel;
import gui.events.MyLineHighlighter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.Highlighter;
import logic.iLogic;

/** CodeView CodeView
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class CodeView extends JPanel {

    private CodePanel mPane;
    private iLogic mLogic;
    private gui.events.MyKeyListener mMyKeyListener;
    private gui.events.MyMouseListener mMyMouseListener;

    /** Konstruktor
     * 
     * 
     */
    public CodeView() {
        mPane = new CodePanel();
        //Layout festlegen
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Code View"));
        this.setPreferredSize(new Dimension(500, 450));
        initMenu();
        add(mPane);
    }//Ende Konstruktor

    private void initMenu() {
        for (int i = 0; i < mPane.getTabCount(); i++) {
            initTabComponent(i);
        }
    }//Ende initMenu()

    private void initTabComponent(int pIndex) {
        pIndex--;
        mPane.setTabComponentAt(pIndex, new ButtonTabComponent(mPane, this));
    }//Ende initTabComponent(int pIndex)

    /** setzt Tabs
     *
     * @param pFile
     * @param pText
     */
    public void setTab(File pFile, String pText) {
        boolean loaded = false;

        for (int i = 0; i < mPane.getTabCount(); i++) {
            if (mPane.getTitleAt(i).equals(pFile.getName())) {
                loaded = true;
            }
        }

        if (!loaded) {
            CodePanel codePanel = new CodePanel(pText, pFile.getName(), pFile, mMyKeyListener, mMyMouseListener);
            mPane.add(pFile.getName(), codePanel);
            initTabComponent(getTabCount());
        } else {
            //Do Nothing
        }
    }//Ende setTab(File pFile, String pText)

    /** Tab entfernen
     * 
     * @param pIndex
     */
    public void removeTab(int pIndex) {
        mLogic.closeFileRequest(((CodePanel) mPane.getComponentAt(pIndex)).getFile());
        mPane.remove(pIndex);
    }//removeTab(int pIndex)

    /** Alle tTabs entfernen
     * 
     */
    public void removeAllTabs() {
        mPane.removeAll();
    }//removeAllTabs

    /** Fokusiert Tab nach Name
     * 
     * @param pFile
     */
    public void focusTabByName(File pFile) {
        for (int i = 0; i < mPane.getTabCount(); i++) {
            if (mPane.getTitleAt(i).equals(pFile.getName())) {
                mPane.getModel().setSelectedIndex(i);
            }
        }
    }//Ende focusTabByName(File pFile)

    /** Gibt Anzahl Tabs zurück
     * 
     * @return 
     */
    public int getTabCount() {
        return mPane.getTabCount();
    }//Ende getTabCount()

    /** Setzt Logic
     * 
     * @param pLogic 
     */
    public void setLogic(iLogic pLogic) {
        mLogic = pLogic;
    }//Ende setLogic(iLogic pLogic)

    /** Gibt ausgewähltes CodePanel zurück
     *
     * @return ausgewähltes CodePanel
     */
    public CodePanel getSelectedCodePanel() {
        return (CodePanel) mPane.getSelectedComponent();
    }//Ende getSelectedCodePanel()

    /** Schreibt text in XML 
     *
     */
    public void setTextXML() {
        XMLModel xmlModel = mLogic.getXMLModel();
        Map map = xmlModel.getOpenedFiles();

        Iterator iterator = map.entrySet().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            CodePanel codePanel = (CodePanel) mPane.getComponentAt(i);
            mapEntry.setValue(codePanel.getTextArea().getText());
        }
    }//Ende setTextXML()

    /** Highlighted Position von Debugger
     *
     * @param pLineNumber
     * @param pclassName 
     */
    public void highliteDebugPosition(int pLineNumber, String pclassName) {


        //alte Highliter entfernen
        CodePanel codePanel = (CodePanel) mPane.getSelectedComponent();
        JTextArea textArea = codePanel.getTextArea();
        textArea.getHighlighter().removeAllHighlights();

        int selection = -1;
        String className = pclassName;
        if (!pclassName.equals("")) {
            if (pclassName.contains(".")) {
                className = pclassName.substring(pclassName.indexOf(".") + 1);
            }
            for (int i = 0; i < mPane.getTabCount(); i++) {
                String title = mPane.getTitleAt(i);
                int indexOfPoint = title.lastIndexOf(".");
                title = title.substring(0, indexOfPoint);
                if (title.equalsIgnoreCase(className)) {
                    mPane.getModel().setSelectedIndex(i);
                    selection = i;
                }
            }

            try {
                if (selection == -1) {
                    mLogic.openFileRequest(pclassName);
                } else {
                    //Do Nothing
                }
                codePanel = (CodePanel) mPane.getSelectedComponent();
                textArea = codePanel.getTextArea();
                Highlighter highliter = textArea.getHighlighter();
                highliter.addHighlight(0, 0, new MyLineHighlighter(Color.green, pLineNumber));
                //Liniennummer  * 15, da durschnittliche ZeichenZahl pro Linie ungefähr 15 beträgt
                textArea.setCaretPosition(pLineNumber * 15);
                textArea.grabFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Do Nothing
        }
    }//Ende highliteDebugPosition(int pLineNumber, String pclassName)

    /** EventListener setzen
     *
     * @param pMyKeyListener
     * @param pMyMouseListener
     */
    public void setEventListener(gui.events.MyKeyListener pMyKeyListener, gui.events.MyMouseListener pMyMouseListener) {
        mMyKeyListener = pMyKeyListener;
        mMyMouseListener = pMyMouseListener;

    }//Ende setEventlistener(gui.events.MyKeyListener pMyKeyListener, gui.events.MyMouseListener pMyMouseListener)
}//Ende CodeView

