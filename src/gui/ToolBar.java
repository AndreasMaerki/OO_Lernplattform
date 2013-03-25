package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import gui.events.*;

/** ToolBar Klasse für Bedienung der Software
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class ToolBar extends JPanel {
    //Komponente registrieren

    private JButton mStart;
    private JButton mContinue;
    private JButton mStep;
    private JButton mStepIn;
    private JButton mStepOut;
    private JButton mStop;
    private MyActionListener mMyActionListener;
    private Gui mGui;

    /** Konstruktor
     *
     */
    public ToolBar() {
        //mMyActionListener = new MyActionListener();
        mStart = new JButton(GUIConstants.START);
        mStart.setName(GUIConstants.START);
        mStart.setForeground(new Color(0, 153, 51));
        mStart.setFont(new Font("Lucida Grande", 1, 13));

        mContinue = new JButton(GUIConstants.CONTINUE);
        mContinue.setName(GUIConstants.CONTINUE);

        mStep = new JButton(GUIConstants.STEP);
        mStep.setName(GUIConstants.STEP);

        mStepIn = new JButton(GUIConstants.STEPIN);
        mStepIn.setName(GUIConstants.STEPIN);

        mStepOut = new JButton(GUIConstants.STEPOUT);
        mStepOut.setName(GUIConstants.STEPOUT);

        mStop = new JButton(GUIConstants.STOP);
        mStop.setName(GUIConstants.STOP);
        mStop.setForeground(new Color(255, 0, 0));
        mStop.setFont(new Font("Lucida Grande", 1, 13));

        //Layout festlegen
        setLayout(new GridLayout(6, 1));

        //Buttons der Toolbar hinzufügen
        add(mStart);
        add(mContinue);
        add(mStep);
        add(mStepIn);
        add(mStepOut);
        add(mStop);
    }// Ende Konstruktor

    /** Eventlisteners setzen
     *
     * @param pMyActionListener
     */
    public void setEventListener(gui.events.MyActionListener pMyActionListener) {
        mMyActionListener = pMyActionListener;
        mStart.addActionListener(pMyActionListener);
        mContinue.addActionListener(pMyActionListener);
        mStep.addActionListener(pMyActionListener);
        mStepIn.addActionListener(pMyActionListener);
        mStepOut.addActionListener(pMyActionListener);
        mStop.addActionListener(pMyActionListener);

    }
}//Ende ToolBar

