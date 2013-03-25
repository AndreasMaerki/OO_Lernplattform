package logic;

import data.XMLModel;
import gui.GUIConstants;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.*;

/** Compiliert sämtliche java Files im workingDirectory und generiert .class Files im OutputDirectory
 *
 * 
 * @author Christian Binder
 * @version 1.00, 12 August 2012
 *
 *                          
 */
public class Compiler {

    private int mErrCount;
    private String mBinary;
    private static final Logger logger = Logger.getLogger(Compiler.class);
    private XMLModel mXMLModel;

    /** Konstruiert einen neuen Compiler
     *
     *
     * @param pXMLModel
     * @author Christian Binder
     * @version 1.00, 12 August 2012
     *
     */
    public Compiler(XMLModel pXMLModel) {
        super();
        mBinary = "javac";
        mXMLModel = pXMLModel;
    }//Konstruktor ende

    /** compiliert
     *
     *
     * @author Christian Binder
     * @version 1.00, 12 August 2012
     * @throws Error wenn Fehler im Source Code gefunden wurden
     * @throws NotFoundException  Wenn Projektordner nicht vorhanden, oder PATH für jdb nicht gesetzt ist
     *
     */
    public void compile() throws Error, NotFoundException {
        if (!(mXMLModel.getprojectpath() == null || mXMLModel.getBuildDirectory() == null)) {
            File directory = mXMLModel.getprojectpath();
            File output = mXMLModel.getBuildDirectory();

            if (output.exists() && directory.exists()) {
                Map map = mXMLModel.getOpenedFiles();

                Iterator iterator = map.entrySet().iterator();
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                try {
                    ProcessBuilder pb = new ProcessBuilder(mBinary, "-d", output.toString(), "-sourcepath",
                            directory.toString(), mapEntry.getKey().toString());

                    logger.debug("Trying to compile..");
                    Process p = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String add = "";
                    StringBuilder sb = new StringBuilder();
                    while (add != null) {
                        add = reader.readLine();
                        if (add != null) {
                            sb.append(add);
                            sb.append("\n");
                        }
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.lastIndexOf("\n"));

                        Object target = gui.Gui.getInstance();
                        EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
                        eventqueue.postEvent(new CompileAWTEvent(target, sb.toString()));

                        throw new Error("Errors found during compiling");
                    }
                    logger.debug("No Errors Found");
                    p.destroy();
                } catch (IOException e) {
                    if (mErrCount == 0) {
                        logger.warn("javac not Found, trying with Path variable");
                        mBinary = mXMLModel.getjdkpath() + File.separator + "javac";
                        mErrCount++;
                        compile();
                    } else {
                        logger.error("javac with Path not found, PATH might not be setted correctly!");
                        throw new NotFoundException("javac");
                    }
                }
            } else {
                throw new NotFoundException("Directory");
            }
        } else {
            throw new NotFoundException("Directory");
        }
    }//compile() Ende

    /** Enthält wichtige Information für das GUI
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     */
    public class CompileAWTEvent extends AWTEvent {

        /**Event ID des CCompileAWTEvent
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @see GUIConstants
         */
        public static final int EVENT_ID = GUIConstants.EVENT_ID_COMPILER;
        private String mMsg;

        /**
         * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @param target Das Ziel, welches das Event soll verarbeiten
         * @param pMsg Die Nachricht (compilier Fehler)
         */
        public CompileAWTEvent(Object target, String pMsg) {
            super(target, EVENT_ID);
            mMsg = pMsg;

        }//Konstruktor Ende

        /** liefert die Nachricht
         *
         *  * @autor Christian Binder
         * @version 1.00, 12 August 2012
         * @return
         */
        public String getMsg() {
            return mMsg;
        }//getMsg() Ende
    }//Ende CompileAWTEvent
}//Ende Compiler

