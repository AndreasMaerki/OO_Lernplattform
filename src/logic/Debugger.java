package logic;

import data.XMLModel;
import gui.GUIConstants;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.*;
import java.io.*;
import logic.ClassModel.MemberModel;
import org.apache.log4j.*;

/** Der Debugger steuert den jdb
 * 
 * @autor Christian Binder
 * @autor Christian Binder
 * @version 1.00, 12 August 2012
 */
public class Debugger {

    private List<String> mBreakPoints;
    private String mBinary;
    private PrintWriter mWriter;
    private BufferedInputStream mStreamReader;
    private Process mProcess;
    private int mErrCount;
    private Distributor mDistributor;
    private static Logger logger = Logger.getLogger(Debugger.class);
    private XMLModel mXMLModel;
    private ClassModel mMainClassModel;

    /**Konstruiert ein neuer Debugger
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pDistributor
     * @param pXMLModel
     */
    public Debugger(Distributor pDistributor, XMLModel pXMLModel) {
        super();
        mDistributor = pDistributor;
        mXMLModel = pXMLModel;
        mBinary = "jdb";
    }//Ende Konstruktor

    /** Liefert den Zustand des Processes
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return true falls aktiv
     */
    public boolean isAlive() {
        try {
            mProcess.exitValue();
            return false;
        } catch (NullPointerException e) {
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }//Ende isAlive()

    /** setzt einen BreakPoint
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pModel Das zu übergebende Klassenmodel
     * @param pLineNr Die Liniennummer
     */
    public void addBreakPoint(ClassModel pModel, int pLineNr) {
        if (isAlive()) {
            mWriter.println("stop at " + pModel.getName() + ":" + pLineNr);
            mWriter.flush();
            readAllFromJDB();
        } else {
            mBreakPoints = new LinkedList();
            mBreakPoints.add("stop at " + pModel.getName() + ":" + pLineNr);
        }
        logger.debug("stop at " + pModel.getName() + ":" + pLineNr);
    }//Ende addBreakPoint(ClassModel pModel, int pLineNr)

    /** Entfernt einen gesetzten Breakpoint
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pModel Das zu übergebende Klassenmodel
     * @param pLineNr Die Liniennummer
     */
    public void removeBreakPoint(ClassModel pModel, int pLineNr) {
        if (isAlive()) {
            mWriter.println("clear " + pModel.getName() + ":" + pLineNr);
            mWriter.flush();
            readAllFromJDB();
        } else {
            mBreakPoints.remove("stop at " + pModel.getName() + ":" + pLineNr);
        }
        logger.debug("remove Breakpoint " + pModel.getName() + ":" + pLineNr);
    }//Ende removeBreakPoint(ClassModel pModel, int pLineNr)

    /** Startet den Prozess des Debuggers
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @throws NotFoundException Wenn "build" Ordner nicht vorhanden, oder PATH für jdb nicht gesetzt ist
     */
    public void startDebugging() throws NotFoundException {
        File directory = mXMLModel.getBuildDirectory();
        if (directory.exists()) {
            try {
                mMainClassModel = mDistributor.getMainClass();
                ProcessBuilder pb = new ProcessBuilder(mBinary, "-sourcepath", mXMLModel.getprojectpath().toString(),
                        "-classpath", mXMLModel.getBuildDirectory().toString(), mMainClassModel.getName());
                pb.directory(directory);
                logger.debug("Debug Process started");
                mProcess = pb.start();
                mStreamReader = new BufferedInputStream(mProcess.getInputStream());
                mWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mProcess.getOutputStream())));
            } catch (IOException e) {
                if (mErrCount == 0) {
                    logger.warn("jdb not Found, trying with Path variable");
                    mBinary = mXMLModel.getjdkpath() + "/jdb";
                    mErrCount++;
                    startDebugging();
                } else {
                    logger.error("javac with Path not found, PATH might not be setted correctly!");
                    throw new NotFoundException("jdb");
                }
            }
        } else {
            throw new NotFoundException("directory");
        }
    }//Ende startDebugging()

    /** übergibt dem Debugger ein Kommando
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pCommand Siehe Konstanten in iLogic
     * @see iLogic
     */
    public void run(int pCommand) {

        String instruction = "";

        switch (pCommand) {
            case iLogic.DEBUG_RUN:
                if (mBreakPoints == null) {
                    instruction = "stop in " + mMainClassModel.getName() + ".main\nrun ";// + MainClass;
                } else {
                    for (String tmp : mBreakPoints) {
                        instruction += tmp + "\n";
                    }
                    instruction += "run " + mDistributor.getMainClass().getName();
                }
                break;
            case iLogic.DEBUG_STEP:
                instruction = "step";
                break;
            case iLogic.DEBUG_STEPIN:
                instruction = "stepi";
                break;
            case iLogic.DEBUG_STEPOUT:
                instruction = "step up";
                break;
            case iLogic.DEBUG_CONTINUE:
                instruction = "cont";
                break;
            case iLogic.DEBUG_STOP:
                instruction = "quit";
                break;
            default:
                instruction = "";
        }
        logger.info("Query: " + instruction);
        mWriter.println(instruction);
        mWriter.flush();
        notifyDebugState();
    }//Ende run(int pCommand)

    /** Teilt den Observern ein DebugState-Objekt mit
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    private void notifyDebugState() {
        int LineNumber = -1;
        String className = "";
        try {
            String debugOutput = readAllFromJDB();

            if (!debugOutput.contains("The application exited") && isAlive()) {
                int lineBeginning = debugOutput.lastIndexOf("line=");
                int lineNumberEnds = debugOutput.lastIndexOf("bci");
                String stringLineNumber = debugOutput.substring(lineBeginning + 5, lineNumberEnds);
                LineNumber = Integer.valueOf(stringLineNumber.trim());
                int firstPoint = debugOutput.lastIndexOf(".", lineBeginning);
                int firstComma = debugOutput.lastIndexOf(",", firstPoint);
                className = debugOutput.substring(firstComma + 2, firstPoint);

                logger.info("I'm now at " + className + ":" + LineNumber);

                ClassModel tmp = mDistributor.getClassModel(className);

                if (tmp != null) {
                    checkFields(tmp);
                } else {
                    logger.fatal("For " + className + " no ClassModel");
                }
            } else {
                debugOutput += " -> Stopped";
            }

            Object target = gui.Gui.getInstance();
            EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
            eventqueue.postEvent(new DebugAWTEvent(target, debugOutput, LineNumber, className));

        } catch (IndexOutOfBoundsException e) {
            if (mErrCount == 0) {
                mErrCount++;
                logger.warn("Didn't catch the Prompt. Is Debugger still alive?");
                logger.warn("Trying to read again");
                notifyDebugState();
            } else {
                mErrCount = 0;
                logger.info("Debugger seems to be finished, destroyng the Process..");
                destroy();

            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//Ende notifyDebugState()

    private void checkFields(ClassModel pClassModel) {
        try {
            if (pClassModel.getMemberModels().size() > 0) {
                for (MemberModel tmpModel : pClassModel.getMemberModels()) {
                    mWriter.println("print " + tmpModel.mName);
                    mWriter.flush();
                    String jdbOut = readAllFromJDB();
                    tmpModel.mValue = findValue(jdbOut);
                    logger.debug(tmpModel.mName + " = " + tmpModel.mValue);
                }
            } else {
                logger.info("No Members listed in class " + pClassModel.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//Ende checkFields(ClassModel pClassModel)

    private String readAllFromJDB() {
        StringBuilder ret = new StringBuilder();
        try {
            int add;
            do {
                while (mStreamReader.available() > 0) {
                    add = mStreamReader.read();
                    ret.append((char) add);
                }
                //Bei mehr als 300 Zeichen ist jdb zu langsam um den Buffer zu füllen
                //deshalb eine Pause von 150ms -> recheck ob noch etwas nachgerutscht ist.
                //jdb braucht ca 1.4ms Pro Zeichen um in die Pipeline zu schicken
                //Es spielt keine grosse Rolle ob mit StringBuilder oder String += gearbeitet wird
                //String += ist tendenziell 1% langsamer als Stringbuilder
                Thread.sleep(150);
            } while (mStreamReader.available() > 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ret.toString();
        }
    }//Ende readAllFromJDB()

    private String findValue(String pText) {
        if (pText.contains(" = ")) {
            int firstIndex = pText.indexOf(" = ") + 3;
            int lasIndex = pText.indexOf("\n", firstIndex);
            return pText.substring(firstIndex, lasIndex);
        }
        return "";
    }//findValue(String pText)

    /** Terminiert den Prozess
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public void destroy() {
        mProcess.destroy();
        Object target = gui.Gui.getInstance();
        EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        eventqueue.postEvent(new DebugAWTEvent(target, "\nDebugger stopped", -1, ""));
        reset();
    }//destroy()

    /** setzt den Debugger in einen frischen Zustand
     *  Breakpoints und Main Klasse wird gelöscht
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public void reset() {
        mMainClassModel = null;
        mBreakPoints = null;
    }//reset()

    /** Enthält wichtige Information für das GUI
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public class DebugAWTEvent extends AWTEvent {

        /**Event ID des CCompileAWTEvent
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @see GUIConstants
         */
        public static final int EVENT_ID = GUIConstants.EVENT_ID_DEBUGGER;
        private String mMsg;
        private int mLineNr;
        private String mCurrentClass;

        /**
         *
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @param target Das Ziel, welches das Event soll verarbeiten
         * @param pMsg Die Nachricht des Debuggers
         * @param pLineNumber Die aktuelle Liniennummer
         * @param pClassName Der aktuelle Klassenname
         */
        public DebugAWTEvent(Object target, String pMsg, int pLineNumber, String pClassName) {
            super(target, EVENT_ID);
            mMsg = pMsg;
            if (pLineNumber > 0) {
                mLineNr = pLineNumber;
            }
            mCurrentClass = pClassName;
        }//Ende Konstruktor

        /**liefert die aktuelle Klasse
         *
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @return
         */
        public String getCurrentClass() {
            return mCurrentClass;
        }//Ende getCurrentClass()

        /**liefert die aktuelle Liniennummer
         *
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @return
         */
        public int getLineNr() {
            return mLineNr;
        }//Ende getLineNr()

        /**liefert die Nachricht
         *
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @return
         */
        public String getMsg() {
            return mMsg;
        }//Ende getMsg()
    }//Ende DebugAWTEvent
}//Ende Debugger

