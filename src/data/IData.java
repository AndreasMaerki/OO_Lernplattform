package data;

import java.util.ArrayList;
import java.io.*;

/** Klasse Data Interface
 * 
 * 
 * @autor Christian Binder
 * @version 1.00, 12 August 2012
 * 
 */
public interface IData {

    /** Fragt offene Files an
     *
     * @param pFile
     * @return Neues projekt oder altes falls kein neues Vorhanden
     */
    public boolean openFileRequest(File pFile);

    /** Überladenes Filerequest mit KlassenName als übergabewert
     *
     * @param pClassName
     */
    public void openFileRequest(String pClassName);

    /** Methode zum schliessen des Filerequests
     *
     * @param pFile
     */
    public void closeFileRequest(File pFile);

    /** Fragt die letzte BuildDirectory ab
     *
     * @return mBuildDirecorty
     */
    public File getBuildDirectory();

    /** Setzt Pfad des Files
     *
     * @param pFile
     */
    public void setPath(File pFile);

    /** Holt sich den Pfad eiens Files
     *
     * @return pPath
     */
    public File getPath();

    /** Holt sich Identifiers (Noch nicht implementiert)
     *
     * @return
     */
    public ArrayList getIdentifiers();

    /** Schreibt text in File
     *
     */
    public void writeToFile();

    /** Schreibt Inhalt von XML in XML
     *
     */
    public void writeToXML();

    /** Prüft ob angegebener Pfad existiert
     *
     * @return true / false
     */
    public boolean isValidDirectory();

    /** Holt XML Model
     *
     * @return pXMLModel
     */
    public XMLModel getXMLModel();

    /** Setzt Flag falls sich ein File geändert hat
     *
     * @param pHasChanged
     */
    public void setHasChanged(boolean pHasChanged);

    /** Prüft ob sich ein File geändert hat
     *
     * @return true / false
     */
    public boolean hasChanged();
    /**Konstante des XML Pfades
     *
     */
    public String XML_PATH = "./lib/SysProp.xml";
}//Ende IData
