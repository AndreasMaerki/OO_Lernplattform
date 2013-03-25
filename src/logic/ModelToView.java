package logic;

import com.mxgraph.swing.handler.mxGraphHandler;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import javax.swing.SwingConstants;
import logic.ClassModel.MemberModel;
import logic.ClassModel.MethodModel;

/**
 * Diese Klasse enthaelt die Logik fuer die Darstellung der Klassen und
 * Objektdiagramme.
 *
 * @author Andreas Maerki
 * @version 1.00, 12 August 2012
 *
 * Vertexes passen ihre Groesse dem Textinhalt an. zoom-in/out Methoden
 * hinzugefuegt. Saemtliche Graphelemente werden neu skaliert. testUpdateViews()
 * geloescht.
 *
 *
 * 0.02 16.07.2012 Diverse Kommentare hinzugefuegt 0.01 1. Juli 2012
 *
 * todoo: sortieren der klassen.
 */
abstract public class ModelToView extends DesignPatternPool {

    //members
    private LinkedList<ClassModel> mClassModels;
    private mxGraph mGraph;
    protected mxGraphComponent mGraphComponent;
    protected mxStylesheet mStylesheet;
    private int mVerticalSpacing;
    private int mVerticalOfset;
    private int mHorizontalCellSize;
    private int mRootPointX;
    private int mRootPointY;
    private boolean mIsObject;
    private Object mParent;
    private String mParentStyle;
    private mxHierarchicalLayout hierarchicalLayout;
    private ArrayList<VertexAttributManager> mVertexAttributManager;
    private LinkedList<Object> ToLayout;

    /**
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pParentStyle Style fuer darstellund
     * @param pIsObject soll ein Objekt dargestellt werden?
     */
    public ModelToView(String pParentStyle, boolean pIsObject) {

        mGraph = new mxGraph();
        mClassModels = new LinkedList<>();
        mIsObject = pIsObject;

        mParentStyle = pParentStyle;
        configureGraph(mGraph);
        mParent = mGraph.getDefaultParent();
        hierarchicalLayout = new mxHierarchicalLayout(mGraph);
        ToLayout = new LinkedList<>();

        mVertexAttributManager = new ArrayList();

        // initialisiere werte zur Kontrolle der Raumaufteilung innerhalb der MutterVertex
        mVerticalSpacing = 20;
        mVerticalOfset = 19;
        mHorizontalCellSize = 100;
        mRootPointY = 20;
        mRootPointX = 15;

        // Styles zuweisen
        mStylesheet = mGraph.getStylesheet();
        mStylesheet.putCellStyle("child", setClassChildrenStyle());
        mStylesheet.putCellStyle("blackLine", setLineStyle());
        mStylesheet.putCellStyle("ClassName", setClassNameStyle());
        mStylesheet.putCellStyle("dashedLineUMLEdge", setDashedUMLEdgeStyle());
        mStylesheet.putCellStyle("extensionStyle", setExtensionUMLEdgeStyle());
        mStylesheet.putCellStyle("ItalicChild", setItalicClassChildrenStyle());
        mGraphComponent = new mxGraphComponent(mGraph) {

            // GraphHandler erzeugen
            @Override
            public mxGraphHandler createGraphHandler() {
                return new mxGraphHandler(this) {

                    @Override
                    protected boolean shouldRemoveCellFromParent(Object parent, Object[] cells, MouseEvent e) {
                        return false;
                    }//ende shouldRemoveCellFromParent
                };// ende mxGraphHandler
            }// ende createGraphHandler
        };// ende mxgraphComponent;

        // keyListener fuer Zoomfunktion
        mGraphComponent.addKeyListener(new gui.events.MyKeyListener());
        configureGraphComponent(mGraphComponent);

        // LayoutTuning 
        hierarchicalLayout.setOrientation(SwingConstants.WEST);
        hierarchicalLayout.setDisableEdgeStyle(true);
        hierarchicalLayout.setParallelEdgeSpacing(10);
    }// Konstruktor ende

    /**
     * Itteriert durch die uebergebenen classModels sowie deren member und
     * Methoden. Jede Klasse erhaelt eine MutterVertex, zu welcher dann die
     * member und Methoden hinzugefuegt weden. Die Methode ist fuer Objekt-, und
     * Klassen-darstellung geeignet. Bei der Instanzierung der Klasse wird ueber
     * Boolean isObject entschieden wie sie sich zur Laufzeit verhaelt.
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pClassModels
     */
    public void updateViews(LinkedList<ClassModel> pClassModels) {
        mClassModels = pClassModels;

        String classExtendsFrom = null;

        mGraph.getModel().beginUpdate();
        try {

            for (ClassModel tmpModel : mClassModels) {// iteriert durch die models
                Object ClassVertex;
                Boolean underlineMembers = true;
                Boolean underLineTitle = true;
                mVerticalOfset = 19;

                ClassVertex = mGraph.insertVertex(mParent, tmpModel.getName(), null, mRootPointX, mRootPointY,
                        mHorizontalCellSize + 5, 20, mParentStyle);
                int i = 0;  // vertical spacing ofset

                Object TitleVertex = mGraph.insertVertex(ClassVertex, null, tmpModel.getName(), 15, 2,
                        mHorizontalCellSize, mVerticalSpacing, "ClassName");
                mGraph.cellSizeUpdated(TitleVertex, false);

                // iteriert durch die members des jeweiligen models
                for (Iterator<MemberModel> it = tmpModel.getMemberModels().iterator(); it.hasNext();) {
                    MemberModel tmpMemberModel = it.next();
                    if (underLineTitle) {
                        mGraph.insertVertex(ClassVertex, null, null, 0, mVerticalOfset,
                                mHorizontalCellSize + 70, 0.5, "blackLine");//erzeugt eine schwarze linie
                        underLineTitle = false;
                        mVerticalOfset += 6;
                    }// end if
                    Object MemberVertex;

                    if (mIsObject && tmpMemberModel.mValue != null) {
                        MemberVertex = mGraph.insertVertex(ClassVertex, null, tmpMemberModel.toString() +
                                "  " + tmpMemberModel.mValue.toString(), 2, mVerticalOfset + i * mVerticalSpacing,
                                mHorizontalCellSize, mVerticalSpacing, "child");
                    } else {
                        MemberVertex = mGraph.insertVertex(ClassVertex, null, tmpMemberModel.toString(), 2,
                                mVerticalOfset + i * mVerticalSpacing, mHorizontalCellSize, mVerticalSpacing, "child");
                    }// ende else
                    mGraph.cellSizeUpdated(MemberVertex, false);
                    i++;
                }// ende members for

                // iteriert durch die methoden des jeweiligen models
                for (Iterator<MethodModel> it = tmpModel.getMethods().iterator(); it.hasNext();) {
                    MethodModel tmpMethodModel = it.next();
                    if (underlineMembers) {// zeichnet eine Linie
                        mGraph.insertVertex(ClassVertex, null, null, 0, mVerticalOfset + i * mVerticalSpacing,
                                mHorizontalCellSize + 70, 0.5, "blackLine");//erzeugt eine schwarze linie
                        underlineMembers = false;
                        mVerticalOfset += 8;
                    }// ende if

                    String prefix;
                    if (tmpMethodModel.mModifier.contains("public")) {
                        prefix = "+ ";
                    } else if (tmpMethodModel.mModifier.contains("private")) {
                        prefix = "- ";
                    } else {
                        prefix = "# ";
                    }// ende else
                    Object MethodVertex;
                    if (tmpMethodModel.mModifier.contains("static")) {
                        MethodVertex = mGraph.insertVertex(ClassVertex, null, prefix + tmpMethodModel.toString(), 2,
                                mVerticalOfset + i * mVerticalSpacing, mHorizontalCellSize,
                                mVerticalSpacing, "ItalicChild");
                    } else {
                        MethodVertex = mGraph.insertVertex(ClassVertex, null, prefix + tmpMethodModel.toString(), 2,
                                mVerticalOfset + i * mVerticalSpacing, mHorizontalCellSize, mVerticalSpacing, "child");
                    }// ende else
                    mGraph.cellSizeUpdated(MethodVertex, false);
                    i++;

                }// ende for methoden

                if (mIsObject) {// erzeuge eine dummie-zelle wenn das darzustellende Diagramm ein Objektdiagramm ist
                    mGraph.insertVertex(ClassVertex, null, null, mHorizontalCellSize / 2,
                            mVerticalOfset / 2 + i * mVerticalSpacing, 0, mVerticalOfset, "child");
                } else {
                    mGraph.insertVertex(ClassVertex, null, null, mHorizontalCellSize / 2,
                            mVerticalOfset / 2 + i * mVerticalSpacing, 0, mVerticalOfset / 1.5, "child");
                }// ende else

                // default abstand der Vertexes vor anwendung des Layouts
                mRootPointX += (mHorizontalCellSize + 120);
                ToLayout.add(ClassVertex);

                // Werte fuer Beziehungen auslesen und der relationshipClassNameList hinzufügen
                LinkedList<String> relationshipClassNameList = new LinkedList();
                List<ClassModel> relationship = tmpModel.getRelationship();
                for (ClassModel tmpRlationship : relationship) {
                    relationshipClassNameList.add(tmpRlationship.getName());
                }// ende for
                if (tmpModel.getExtendsFrom() != null) {
                    classExtendsFrom = tmpModel.getExtendsFrom().getName();
                } else {
                    classExtendsFrom = "empty";
                }// end else
                mVertexAttributManager.add(new VertexAttributManager(ClassVertex, tmpModel.getName(),
                        classExtendsFrom, relationshipClassNameList));
            }// ende for classModels 

            updateEdges();

            mGraph.updateGroupBounds();// classVertex soll ihre grenzen um die tochterzellen legen.
            hierarchicalLayout.execute(mParent, ToLayout);
            mGraph.refresh();

        } finally {
            mGraph.getModel().endUpdate();
            updateGui();
        }// ende finally
    }// ende update method

    /**
     * Iteriert durch die vorhandenen Vertexes und zeichnet Beziehungspfeile
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     */
    public void updateEdges() {
        String parentStyle;
        if (mIsObject) {
            parentStyle = "dashedLineUMLEdge";
        } else {
            parentStyle = "UMLEdge";
        }
        for (VertexAttributManager TargetVertex : mVertexAttributManager) {
            for (VertexAttributManager SourceVertex : mVertexAttributManager) {

                LinkedList<String> list = SourceVertex.getmConnections();
                if (SourceVertex.getmExtendsFrom() != null
                        && SourceVertex.getmExtendsFrom().equals(TargetVertex.getmVertexName()) && !mIsObject) {
                    mGraph.insertEdge(mParent, null, null, SourceVertex.getmVertex(), TargetVertex.getmVertex(), "extensionStyle");
                }
                for (String tmpConnection : list) {
                    if (TargetVertex.getmVertexName().equals(tmpConnection)) {
                        mGraph.insertEdge(mParent, null, null, SourceVertex.getmVertex(), TargetVertex.getmVertex(), parentStyle);
                    }// ende if
                }// end for tmpConnection
            }// ende for list
        }// ende for VertexAttributManager
    }// ende updateEdges

    /**
     * Konfiguriert das Handling des Graphen. Welche Operationen duerfen
     * ausgefuehrt werden, und welche nicht. Bezieht sich ausschliesslich auf
     * Aktionen auf der Graphischen Oberflaeche.
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param graph der zu kofigurierende Graph
     *
     * @see mxGraph
     */
    private void configureGraph(mxGraph graph) {
        graph.setEnabled(true);
        graph.setCellsResizable(false);
        graph.setConstrainChildren(true);// kind darf granzen des parents nicht verlassen
        graph.setExtendParents(true);
        graph.setCellsEditable(false);
        graph.setKeepEdgesInBackground(true);
        graph.setGridEnabled(true);
        graph.setGridSize(20);
        graph.setExtendParentsOnAdd(true);
        graph.setDefaultOverlap(0);
        graph.setAutoSizeCells(true);
        graph.getView().setScale(1);
    }// ende configureGraph

    /**
     * konfiguriert den graphComponenten
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param graphComponent der zu kofigurierende Graphkomponent
     */
    private void configureGraphComponent(mxGraphComponent graphComponent) {
        graphComponent.getViewport().setOpaque(true);// überdeckte stellen werden alle übermalen. nichts scheint durch
        graphComponent.getViewport().setBackground(new Color(0x8f8f8f));// hintergrundfarbe
        graphComponent.setConnectable(false);// es dürfen keine neuen pfeile erzeugt werden
    }// ende configureGraphComponent

    /**
     * Der Body der Methode wird in ClassDesign und ObjectDesign auscodiert, da
     * die beiden Klassen unterschiedliche AWT-Events werfen. Da im Gui jedesmal
     * ein revalidate ausgeführt wird, soll und muss diese Methode bei jedem
     * Update aufgerufen werden
     *
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     */
    abstract public void updateGui();
}// ende ModelToView

