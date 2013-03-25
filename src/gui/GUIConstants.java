package gui;

import java.awt.AWTEvent;

/**Saemtliche mit der GUI in verbindung stehende Konstanten sowie diejenigen fuer die Keycommands und AWT events.
 *
 * @author Christian
 * @version 1.00, 12 August 2012
 */
public class GUIConstants {

    /**Titel der Objektorientierten Lenrplatform.
     *
     */
    public static final String APPTITLE = "Objektorientierte Lenrplatform";
    //ToolBar Button-Text
    /**ID fuer den button "Start"
     *
     */
    public static final String START = "Start";
    /**
     *ID fuer den button "Continue"
     * 
     */
    public static final String CONTINUE = "Continue";
    /**
     *ID fuer den button "Step"
     */
    public static final String STEP = "Step";
    /**
     *ID fuer den button "Step in"
     */
    public static final String STEPIN = "Step in";
    /**
     *ID fuer den button "Step out"
     */
    public static final String STEPOUT = "Step out";
    /**
     *ID fuer den button "Stop"
     */
    public static final String STOP = "Stop";
    //MenuBar Item-Text
    /**
     * ID fuer Menueeintrag "Datei"
     */
    public static final String FILE = "Datei";
    /**
     *ID fuer Menueeintrag "Neu"
     */
    public static final String NEWFILE = "Neu             (Ctrl+N)";
    /**
     *ID fuer Menueeintrag "Öffnen"
     */
    public static final String OPENFILE = "Öffnen         (Ctrl+O)";
    /**
     *ID fuer Menueeintrag "Speichern"
     */
    public static final String SAVE = "Speichern  (Ctrl+S)";
    /**
     *ID fuer Menueeintrag "Beenden"
     */
    public static final String CLOSE = "Beenden    (Ctrl+Q)";
    /**
     *ID fuer Menueeintrag "Bearbeiten"
     */
    public static final String EDIT = "Bearbeiten";
    /**
     *ID fuer Menueeintrag "Wiederholen"
     */
    public static final String REDO = "Wiederholen   (Ctrl+Y)";
    /**
     *ID fuer Menueeintrag "Rückgängig"
     */
    public static final String UNDO = "Rückgängig    (Ctrl+Z)";
    //User Information
    /**
     *Text der bei einer schliessen anfrage des Programms angezeigt wird
     */
    public static final String CLOSEAPP = "Wollen Sie die objektorientierte Lernplattform wirklich beenden?";
    /**
     *Der pfad zum JDK ist nicht bekannt
     */
    public static final String JDKMISSING = "JDK Pfad ist nicht in der Systemvariable PATH gesetzt!\n"
            + "Bitte wählen sie das \"bin\" Verzeichniss des JDKs";
    /**
     * Sollen aenderungen gespeichert werden?
     */
    public static final String SAVEFILES = "Änderungen speichern?";
    //Custom AWT Events ID
    /**
     * AWT Event ID fuer den Debugger
     */
    public static final int EVENT_ID_DEBUGGER = AWTEvent.RESERVED_ID_MAX + 1;
    /**
     *  AWT Event ID fuer Compiler
     */
    public static final int EVENT_ID_COMPILER = AWTEvent.RESERVED_ID_MAX + 2;
    /**
     *  AWT Event ID fuer den ClassDesigner
     */
    public static final int EVENT_ID_CLASSDESIGNER = AWTEvent.RESERVED_ID_MAX + 3;
    /**
     *  AWT Event ID fuer den ObjectDesigner
     */
    public static final int EVENT_ID_OBJECTDESIGNER = AWTEvent.RESERVED_ID_MAX + 4;
    /**
     *  AWT Event ID fuer den FileHandler
     */
    public static final int EVENT_ID_FILEHANDLER = AWTEvent.RESERVED_ID_MAX + 5;
}
