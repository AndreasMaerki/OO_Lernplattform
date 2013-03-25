package logic;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxStylesheet;
import gui.GUIConstants;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

/**
 *
 * @author Andreas Maerki
 * @version 1.0
 *
 *
 */
public class ClassDesign extends ModelToView {

    /**
     * Der Superklasse wird die Styleinformation uebergeben sowie der Boolean
     * isObject. Dem Stylesheet werden die noetigen Styles die zur darstellund
     * des Klassendiagramms benoetigt werden hinzugefuegt.
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     *
     * @see mxStylesheet
     *
     */
    public ClassDesign() {
        super("ClassStyle", false);
        mStylesheet.putCellStyle("ClassStyle", setRectangleStyle());
        mStylesheet.putCellStyle("UMLEdge", setUMLEdgeStyle());
    }// ende Konstruktor

    /**
     * Der Graphcomponent wird ueber einen AWT event upgedated
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @see AWTEvent
     */
    public void updateGui() {
        Object target = gui.Gui.getInstance();
        EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        eventqueue.postEvent(new ClassAWTEvent(target, mGraphComponent));
    }// ende updateGui

    /**
     * Kommunikation laeuft ueber AWT event, da threadSafe
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     *
     */
    public class ClassAWTEvent extends AWTEvent {

        public static final int EVENT_ID = GUIConstants.EVENT_ID_CLASSDESIGNER;
        private mxGraphComponent mGraphComponent;

        /**
         * @author Andreas Maerki
         * @version 1.00, 12 August 2012
         * @param target Das Ziel, welches das Event soll verarbeiten
         * @param pGraph Der Graph auf dem das Event ausgefuehrt werden soll
         */
        public ClassAWTEvent(Object target, mxGraphComponent pGraph) {
            super(target, EVENT_ID);
            mGraphComponent = pGraph;
        }// ende ClassAWTEvent

        /**
         * @author Andreas Maerki
         * @version 1.00, 12 August 2012
         * @return mGraphComponent
         * @see mxGraphComponent
         */
        public mxGraphComponent getGraphComponent() {
            return mGraphComponent;
        }// ende getGraphComponent
    }// ende AWT
} //ende ClassDesign

