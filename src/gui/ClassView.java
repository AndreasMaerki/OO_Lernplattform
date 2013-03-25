package gui;

import java.awt.*;
import javax.swing.*;

/** ClassView Panel
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class ClassView extends JPanel {

    /** Konstruktor */
    public ClassView() {
        //Layout festlegen
        setBorder(BorderFactory.createTitledBorder("Class View"));
        setPreferredSize(new Dimension(500, 300));
        setLayout(new BorderLayout());
        addKeyListener(new gui.events.MyKeyListener());
    }//Ende Konstruktor
}//Ende ClassView

