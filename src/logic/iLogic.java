package logic;

import java.io.*;
import data.XMLModel;

/** Schnitstelle zwischen Logik und Gui
 * 
 * 
 * @autor Christian Binder
 * @version 1.00, 12 August 2012
 *
 *
 *Changelog:    15.05.2012  Grundstruktur erstellt
 *                          
 */
public interface iLogic {

    /** Debuggerbefehl "run"
     *
     */
    int DEBUG_RUN = 1;
    /** Debuggerbefehl "step"
     *
     */
    int DEBUG_STEP = 2;
    /** Debuggerbefehl "step in"
     *
     */
    int DEBUG_STEPIN = 3;
    /** Debuggerbefehl "step out"
     *
     */
    int DEBUG_STEPOUT = 4;
    /** Debuggerbefehl "continue"
     *
     */
    int DEBUG_CONTINUE = 5;
    /**Debuggerbefehl "stop"
     *
     */
    int DEBUG_STOP = 6;

    /**Setzt einen Breakpoint
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pClassName Der Name der Klasse
     * @param pLineNumber Die Liniennummer
     */
    public void setBreakPoint(String pClassName, Integer pLineNumber);

    /**Entfernt einen BreakPoint
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pClassName Der Name der Klasse
     * @param pLineNumber Die Liniennummer
     */
    public void removeBreakPoint(String pClassName, Integer pLineNumber);

    /** öffnet eine Datei, oder fokusiert diese, falls schon geöffnet
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pFile Die Datei
     */
    public void openFileRequest(File pFile);

    /** öffnet eine Datei anhand des Klassennames, oder fokusiert diese, falls schon geöffnet
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pClassName Der Klassenname
     */
    public void openFileRequest(String pClassName);

    /** schliest die Datei
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pFile Die Datei
     */
    public void closeFileRequest(File pFile);

    /** liefert das XMLModel
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return
     */
    public XMLModel getXMLModel();

    /** Steuert den Debugger
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pCommand
     */
    public void useDebugger(Integer pCommand);

    /** Muss aufgerufen werden, falls etwas geändert wurde
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public void setEditFlag();

    /**
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return true wenn etwas geändert wurde
     */
    public boolean isEdited();

    /** Speichert alle geöffneten Dateien
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public void saveFiles();

    /** Schliesst die Appikation
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public void exit();
}
