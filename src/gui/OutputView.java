package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** OutputView Panel
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class OutputView extends JPanel {

    private JTextArea mTextArea;
    private JScrollPane mScrollPane;

    /** Konstruktor
     *
     */
    public OutputView() {
        //Layout festlegen
        this.setBorder(BorderFactory.createTitledBorder("Output View"));
        setLayout(new BorderLayout());
        mTextArea = new JTextArea();
        mScrollPane = new JScrollPane(mTextArea);

        add(mScrollPane);
        setVisible(true);
        mTextArea.setBackground(Color.WHITE);
        mTextArea.setForeground(Color.BLACK);
        mTextArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
        mTextArea.setEditable(false);

    }//Ende Konstruktor

    /**Übergibt Text der TextArea
     * 
     * @param pText
     */
    public void addText(String pText) {
        mTextArea.setText(mTextArea.getText() + pText);
    }
}//Ende OutputView

