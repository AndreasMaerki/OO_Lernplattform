package logic;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse enthaelt verschiedene Styles fuer alle Objekte die die bei der
 * Erzeugung eines UML Diagramms benoetigt werden. Alle Methoden geben eine
 * HashMap zurueck, welche die Parameter fuer den gewuenschten Style enthaelt.
 *
 * @author Andreas Maerki
 * @version 1.00, 12 August 2012
 *
 */
public class DesignPatternPool {

    Color vertexColor = new Color(214, 214, 214);

    /**
     *
     */
    /**
     * Returniert eine HashMap welche die Konfiguration fuer einen gestrichelten
     * UML Pfeil mit nicht ausgefuelltem Ende enthaelt. Diese muss einem
     * mxStylesheet hinzugefuegt werden. BSP:
     * mStylesheet.putCellStyle("UMLPfeil", setDashedUMLEdgeStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer einen gestrichelten UML Pfeil.
     * @see mxStylesheet
     */
    public Map setDashedUMLEdgeStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_ORTHOGONAL, true);
        style.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK);
        style.put(mxConstants.STYLE_STROKEWIDTH, 1);
        style.put(mxConstants.STYLE_ENDFILL, 0);// end of edge is not filled
        style.put(mxConstants.STYLE_DASHED, true);// gestrichelt
        style.put(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND);
        style.put(mxConstants.STYLE_STARTSIZE, 6);
        style.put(mxConstants.STYLE_ENDSIZE, 8);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // default is #6482B9
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        style.put(mxConstants.STYLE_MOVABLE, false);
        mxStylesheet edgeStyle = new mxStylesheet();
        return style;
    }// ende setDashedUMLEdgeStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer einen gestrichelten
     * UML Pfeil mit nicht ausgefuelltem Ende enthaelt. Diese muss einem
     * mxStylesheet hinzugefuegt werden. BSP:
     * mStylesheet.putCellStyle("UMLPfeil", setDashedUMLEdgeStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer einen gestrichelten UML Pfeil.
     * @see mxStylesheet
     */
    public Map setExtensionUMLEdgeStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_ORTHOGONAL, true);
        style.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK);
        style.put(mxConstants.STYLE_STROKEWIDTH, 1);
        style.put(mxConstants.STYLE_ENDFILL, 0);// end of edge is not filled
        style.put(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND);
        style.put(mxConstants.STYLE_STARTSIZE, 6);
        style.put(mxConstants.STYLE_ENDSIZE, 16);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // default is #6482B9
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        style.put(mxConstants.STYLE_MOVABLE, false);
        mxStylesheet edgeStyle = new mxStylesheet();
        return style;
    }// ende setExtensionUMLEdgeStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer einen
     * durchgezogenen UML Pfeil mit ausgefuelltem Ende enthaelt. Diese muss
     * einem mxStylesheet hinzugefuegt werden. BSP:
     * mStylesheet.putCellStyle("UMLPfeil", setUMLEdgeStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer einen UML Pfeil.
     * @see mxStylesheet
     */
    public Map setUMLEdgeStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_ORTHOGONAL, true);
        style.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        style.put(mxConstants.STYLE_BENDABLE, 1);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK);
        style.put(mxConstants.STYLE_STROKEWIDTH, 1);
        style.put(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND);
        style.put(mxConstants.STYLE_STARTSIZE, 6);
        style.put(mxConstants.STYLE_ENDSIZE, 8);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // default is #6482B9
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        style.put(mxConstants.STYLE_MOVABLE, false);
        mxStylesheet edgeStyle = new mxStylesheet();
        return style;
    }// ende setUMLEdgeStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer ObjectVertex mit
     * abgerundeten Ecken enthaelt. Diese muss einem mxStylesheet hinzugefuegt
     * werden. BSP: mStylesheet.putCellStyle("Vertex",
     * setRoundetRectangleStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer eine ObjectVertex.
     * @see mxStylesheet
     */
    public Map<String, Object> setRoundetRectangleStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_GLASS, 1);
        style.put(mxConstants.STYLE_SHADOW, true);
        style.put(mxConstants.STYLE_STROKEWIDTH, 2);
        style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_TOPTOBOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_AUTOSIZE, true);
        style.put(mxConstants.STYLE_RESIZABLE, false);
        return style;
    }// ende setRoundetRectangleStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer ClassVertex mit
     * spitzen Ecken enthaelt. Diese muss einem mxStylesheet hinzugefuegt
     * werden. BSP: mStylesheet.putCellStyle("Vertex", setRectangleStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer eine ClassVertex.
     * @see mxStylesheet
     */
    public Map<String, Object> setRectangleStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_STROKEWIDTH, 2);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_TOP);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        style.put(mxConstants.STYLE_ROUNDED, false);
        style.put(mxConstants.STYLE_RESIZABLE, false);
        style.put(mxConstants.STYLE_AUTOSIZE, true);
        return style;
    }// ende setRectangleStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer ChildVertexes
     * enthaelt. Sie werden innerhalb einer MutterVertex erzeugt. Umrandung und
     * fill haben die selbe Farbe. Die Map muss einem mxStylesheet hinzugefuegt
     * werden. BSP: mStylesheet.putCellStyle("ChildVertex",
     * setClassChildrenStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer eine ChildVertex.
     * @see mxStylesheet
     */
    public Map<String, Object> setClassChildrenStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_FONTCOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
        style.put(mxConstants.STYLE_STROKEWIDTH, 0.5);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        style.put(mxConstants.STYLE_MOVABLE, false);
        style.put(mxConstants.STYLE_ROUNDED, false);
        style.put(mxConstants.STYLE_RESIZABLE, false);
        style.put(mxConstants.STYLE_AUTOSIZE, true);
        return style;
    }// ende setClassChildrenStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer
     * setItalicClassChildrenVertexes enthaelt. Sie werden innerhalb einer
     * MutterVertex erzeugt. Umrandung und fill haben die selbe Farbe. Die Map
     * muss einem mxStylesheet hinzugefuegt werden. BSP:
     * mStylesheet.putCellStyle("ChildVertex", setClassChildrenStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer eine ChildVertex.
     * @see mxStylesheet
     */
    public Map<String, Object> setItalicClassChildrenStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_FONTCOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_ITALIC);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
        style.put(mxConstants.STYLE_STROKEWIDTH, 0.5);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        style.put(mxConstants.STYLE_MOVABLE, false);
        style.put(mxConstants.STYLE_ROUNDED, false);
        style.put(mxConstants.STYLE_RESIZABLE, false);
        style.put(mxConstants.STYLE_AUTOSIZE, true);
        return style;
    }// ende setItalicClassChildrenStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer ClassNameVertexes
     * enthaelt. Sie werden innerhalb einer MutterVertex erzeugt. Umrandung und
     * fill haben die selbe Farbe. Die Map muss einem mxStylesheet hinzugefuegt
     * werden. BSP: mStylesheet.putCellStyle("ChildVertex",
     * setClassChildrenStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer eine ChildVertex.
     * @see mxStylesheet
     */
    public Map<String, Object> setClassNameStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(vertexColor));
        style.put(mxConstants.STYLE_FONTCOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_ITALIC);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
        style.put(mxConstants.STYLE_STROKEWIDTH, 0.5);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        style.put(mxConstants.STYLE_MOVABLE, false);
        style.put(mxConstants.STYLE_ROUNDED, false);
        style.put(mxConstants.STYLE_RESIZABLE, false);
        style.put(mxConstants.STYLE_AUTOSIZE, true);
        return style;
    }// ende setClassNameStyle

    /**
     * Returniert eine HashMap welche die Konfiguration fuer linienVertexes
     * enthaelt. Diese werden innerhalb einer MutterVertex erzeugt. Umrandung
     * und fill haben die selbe Farbe. Die Map muss einem mxStylesheet
     * hinzugefuegt werden. BSP: mStylesheet.putCellStyle("ChildVertex",
     * setClassChildrenStyle());
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return HashMap fuer eine ChildVertex.
     * @see mxStylesheet
     */
    public Map<String, Object> setLineStyle() {
        Map<String, Object> style = new HashMap();
        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
        style.put(mxConstants.STYLE_STROKEWIDTH, 0.5);
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_RECTANGLE);
        style.put(mxConstants.STYLE_ROUNDED, false);
        style.put(mxConstants.STYLE_MOVABLE, false);
        return style;
    }// ende setLineStyle
} // ende DesignPatternPool

