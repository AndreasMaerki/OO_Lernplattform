package logic;

import com.mxgraph.swing.mxGraphComponent;
import gui.GUIConstants;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

/**
 *
 * @author Andreas Maerki
 * @version 1.00, 12 August 2012
 * 
 */
public class ObjectDesign extends ModelToView {

    /**
     * Der Superklasse wird die Styleinformation uebergeben sowie der Boolean
     * isObject. Dem Stylesheet werden die noetigen Styles die zur darstellund
     * des Objektdiagramms benoetigt werden hinzugefuegt.
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @see mxStylesheet
     *
     */
    public ObjectDesign() {
        super("ObjectStyle", true);
        mStylesheet.putCellStyle("ObjectStyle", setRoundetRectangleStyle());
        mStylesheet.putCellStyle("dashedLineUMLEdge", setDashedUMLEdgeStyle());
    }// ende Konstruktor

    public void updateGui() {
        Object target = gui.Gui.getInstance();
        EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        eventqueue.postEvent(new ObjectAWTEvent(target, mGraphComponent));
    }

    /**
     * Kommunikation laeuft ueber AWT event, da threadSafe
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     */
    public class ObjectAWTEvent extends AWTEvent {

        public static final int EVENT_ID = GUIConstants.EVENT_ID_OBJECTDESIGNER;
        private mxGraphComponent mGraphComponent;

        /**
         * @author Andreas Maerki
         * @version 1.00, 12 August 2012
         * @param target Das Ziel, welches das Event soll verarbeiten
         * @param pGraph Der Graph auf dem das Event ausgefuehrt werden soll
         */
        public ObjectAWTEvent(Object target, mxGraphComponent pGraph) {
            super(target, EVENT_ID);
            mGraphComponent = pGraph;
        }// ende ObjectAWTEvent

        /**
         * @author Andreas Maerki
         * @version 1.00, 12 August 2012
         * @return
         */
        public mxGraphComponent getGraphComponent() {
            return mGraphComponent;
        }// ende getGraphComponent
    }// end AWT
}// ObjectDesign
