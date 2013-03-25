package gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/** ObjectView Panel
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class ObjectView extends JPanel {

    /** Konstruktor
     * 
     */
    public ObjectView() {
        //Layout festlegen
        this.setBorder(BorderFactory.createTitledBorder("Object View"));
        this.setLayout(new BorderLayout());
    }//Ende Konstruktor
}//Ende ObjectView

