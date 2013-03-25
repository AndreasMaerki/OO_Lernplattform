package logic;

import java.util.LinkedList;

/**Dient als Container fuer alle werte die zu einer Vertex gehoeren, und zum layouten benoetigt werden.
 * @author Andreas Maerki
 * @version 1.00, 12 August 2012
 */
public class VertexAttributManager {

    private Object mVertex;
    private String mVertexName;
    private LinkedList mConnections;
    private String mExtendsFrom;

    /**
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pVertex Die Vertex
     * @param pVertexName Der Name der Vertex
     * @param pExtendsFrom Klasse erbt von...
     * @param pConnections linkedList mit allen Verbindungen die die Klasse hat.
     */
    public VertexAttributManager(Object pVertex, String pVertexName, String pExtendsFrom, LinkedList pConnections) {
        mVertex = pVertex;
        mVertexName = pVertexName;
        mExtendsFrom = pExtendsFrom;
        mConnections = pConnections;
    }// ende Konstruktor

    /**
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return LinkedList verbindungen
     */
    public LinkedList getmConnections() {
        return mConnections;
    }// ende getmConnections

    /**
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return String ExtendsFrom 
     */
    public String getmExtendsFrom() {
        return mExtendsFrom;
    }// ende getmExtendsFrom

    /**
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return die Vertex
     */
    public Object getmVertex() {
        return mVertex;
    }// ende getmVertex

    /**
     * @author Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return Vertexname
     */
    public String getmVertexName() {
        return mVertexName;
    }// ende getmVertexName
}// ende VertexAttributManager
