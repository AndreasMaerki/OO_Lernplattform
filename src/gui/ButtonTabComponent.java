package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import gui.events.*;

/** Button Komponent um Tabs schliessen zu können
 * 
 * 
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class ButtonTabComponent extends JPanel {

    private final JTabbedPane pane;
    private CodeView mView;
    private MyActionListener mMyActionListener;
    private MyMouseListener mMyMouseListener;

    /**Konstruktor
     *
     * @param pane
     * @param pView
     */
    public ButtonTabComponent(final JTabbedPane pane, CodeView pView) {

        //Layout festlegen und überschreiben
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));


        mView = pView;
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);

        //macht das JLabel die Title von JTabbedPane liest
        JLabel label = new JLabel() {

            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };


        add(label);
        //Abstand zwischen Buttons und Tabs setzen
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //Tab button erzeugen
        JButton button = new TabButton();
        add(button);
        //Oberhalb des Komponenten platz schaffen
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

    }//Ende Konstruktor

    /**Eventlisteners setzen
     *
     * @param pMyActionListener
     * @param pMyMouseListener
     */
    public void setEventListener(gui.events.MyActionListener pMyActionListener, gui.events.MyMouseListener pMyMouseListener) {
        mMyActionListener = pMyActionListener;
        mMyMouseListener = pMyMouseListener;

    }

    /** TabButton Klasse
     *
     *
     * @autor Michael Moser
     * @version 1.00, 12. August 2012
     *
     */
    private class TabButton extends JButton implements ActionListener {
        //TabButton Konstruktor

        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }//Ende Konstruktor TabButton

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
                mView.removeTab(i);
            }

        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //Zeichnet das X des Buttons
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //Ändert das Bild des gedrückten Knopfes
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
    private final static MouseListener buttonMouseListener = new MouseAdapter() {

        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}//Ende ButtonTabComponent
