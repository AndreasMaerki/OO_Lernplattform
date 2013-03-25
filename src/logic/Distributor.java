package logic;

import java.io.File;
import java.util.LinkedList;
import data.IData;
import data.XMLModel;
import gui.GUIConstants;
import gui.GUIHelper;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.log4j.*;

/** Der Distributor ist für den Arbeitsablauf zuständig
 * Er ist ist die Schnittstelle für Gui Logik und Data
 * 
 * 
 * @autor Christian Binder
 * @version 1.00, 12 August 2012
 * @see iLogic
 *                          
 */
public class Distributor implements iLogic {

    private Debugger mDebugger;
    private Compiler mCompiler;
    private ModelAnalyzer mModelAnalyzer;
    private IData mIData;
    private ClassDesign mClassDesign;
    private ObjectDesign mObjectDesign;
    private static final Logger logger = Logger.getLogger(Distributor.class);

    /** Konstruiert einen neuen Distributor
     * 
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pData Das Interface zu IData
     * @see IData
     */
    public Distributor(IData pData) {
        gui.Gui.getInstance().setLogic(this);
        mIData = pData;
        mModelAnalyzer = new ModelAnalyzer(getXMLModel());
        mCompiler = new Compiler(getXMLModel());
        mDebugger = new Debugger(this, getXMLModel());

        try {
            if (!getXMLModel().getOpenedFiles().isEmpty()) {
                compile();
            }
        } catch (NotFoundException e) {
            logger.warn("NotFound Catched, shoud we inform the User??");
        } catch (Error e) {
            logger.info("Compile Errors");
            //Keine besondere Exception Behandlung, Projekt darf beim öffnen Fehler enthalten
        }
    }//Ende Konstruktor

    /** liefert das KlassenModel
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return das ClassenModel
     * @see ClassModel
     */
    public ClassModel getMainClass() {
        try {
            return mModelAnalyzer.getMainClass();
        } catch (NotFoundException e) {
            JFileChooser fileChooser = GUIHelper.getFileChooser(GUIConstants.OPENFILE);
            String nameOfFile = fileChooser.getSelectedFile().getName();
            return mModelAnalyzer.getClassModel(nameOfFile);
        }
    }//Ende getMainClass()

    public XMLModel getXMLModel() {
        return mIData.getXMLModel();
    }//Ende getXMLModel()

    public void setBreakPoint(String pClassName, Integer pLineNumber) {
        mDebugger.addBreakPoint(mModelAnalyzer.getClassModel(pClassName), pLineNumber);
    }//Ende setBreakPoint(String pClassName, Integer pLineNumber)

    public void removeBreakPoint(String pClassName, Integer pLineNumber) {
        mDebugger.removeBreakPoint(mModelAnalyzer.getClassModel(pClassName), pLineNumber);
    }//Ende removeBreakPoint(String pClassName, Integer pLineNumber)

    public void setEditFlag() {
        mIData.setHasChanged(true);
    }//Ende setEditFlag()

    public boolean isEdited() {
        return mIData.hasChanged();
    }//Ende isEdited()

    public void openFileRequest(File pFile) {
        try {

            //Kompiliert falls ein neues Projekt geöffnet wurde
            if (!mIData.openFileRequest(pFile)) {
                mDebugger.reset();
                compile();
            } else {
                //Do Nothing
            }

        } catch (NotFoundException e) {
            if (e.getMessage().contains("javac")) {
                JOptionPane.showMessageDialog(gui.Gui.getInstance(), GUIConstants.JDKMISSING);
                JFileChooser tmp = GUIHelper.getFileChooser(GUIConstants.JDKMISSING);
                getXMLModel().setjdkpath("C:");
            }
        } catch (Error e) {
            logger.info("Compile Errors");
            //Keine besondere Exception Behandlung, Projekt darf beim öffnen Fehler enthalten
        }
    }//Ende openFileRequest(File pFile)

    public void openFileRequest(String pClassName) {
        mIData.openFileRequest(pClassName);
    }//Ende openFileRequest(String pClassName)

    public void closeFileRequest(File pFile) {
        mIData.closeFileRequest(pFile);
    }//Ende closeFileRequest(File pFile)

    public void saveFiles() {
        gui.Gui.getInstance().getCodeView().setTextXML();
        mIData.writeToFile();
    }//Ende saveFiles()

    private void compile() throws Error, NotFoundException {
        mCompiler.compile();
        updateClassGraph(mModelAnalyzer.createClassModels());
    }//Ende compile()

    public void useDebugger(Integer pCommand) {
        try {
            if (mDebugger.isAlive()) {

                mDebugger.run(pCommand);

            } else {
                compile();
                mDebugger.startDebugging();
                if (pCommand != DEBUG_RUN) {
                    mDebugger.run(DEBUG_RUN);
                }
                mDebugger.run(pCommand);
            }
            updateObjectGraph(mModelAnalyzer.getClassModels());

        } catch (Error e) {
            logger.info("Compile Errors");
        } catch (NotFoundException e) {
            if (e.getMessage().contains("javac") || e.getMessage().contains("jdb")) {
                JOptionPane.showMessageDialog(gui.Gui.getInstance(), GUIConstants.JDKMISSING);
                JFileChooser fileChooser = GUIHelper.getFileChooser(GUIConstants.JDKMISSING);
                getXMLModel().setjdkpath("C:");
                //refresht das XMLModel des Compilers
                mCompiler = new Compiler(getXMLModel());

            }
        }
    }//Ende useDebugger(int pCommand)

    /** liefert das Klassenmodel anhand des Klassennamens
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pName Name der gewünschten Klasse
     * @return null wenn keine Klasse gefunden wurde
     */
    public ClassModel getClassModel(String pName) {
        return mModelAnalyzer.getClassModel(pName);
    }//Ende  getClassModel(String pName)

    /** liefert die Klassenmodelle in einer Liste
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return
     */
    public LinkedList<ClassModel> getClassModels() {
        return mModelAnalyzer.getClassModels();
    }//Ende getClassModels()

    /**
     * @autor Andreas Märki
     * @version 1.00, 12 August 2012
     * updated die Grafische Ansicht des Klassenmodells.
     * @param pClassModels KlassenModellArrayList
     */
    public void updateClassGraph(LinkedList<ClassModel> pClassModels) {
        mClassDesign = new ClassDesign();
        mClassDesign.updateViews(pClassModels);
    }//Ende updateClassGraph(LinkedList<ClassModel> pClassModels)

    /** updated die Grafische Ansicht des Klassenmodells.
     *
     * @autor Andreas Märki
     * @version 1.00, 12 August 2012
     * @param pClassModels KlassenModellArrayList
     */
    public void updateObjectGraph(LinkedList<ClassModel> pClassModels) {
        mObjectDesign = new ObjectDesign();
        mObjectDesign.updateViews(pClassModels);
    }//Ende updateObjectGraph(LinkedList<ClassModel> pClassModels)

    public void exit() {
        mIData.writeToXML();
        System.exit(0);
    }//Ende exit()
}//Ende Distributor

