package data;

import gui.GUIConstants;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Handelt den Umgang mit den Dateien
 *
 *
 * @author Christian Binder
 * @version 1.00, 12 August 2012
 *
 */
public class FileHandler implements IData {

    private Reader mReader;
    private Writer mWriter;
    private ArrayList<StringBuilder> mFileList;
    private File mWorkingDirectory, mBuildDirectory, mPath;
    private XMLModel mXMLModel;
    private static final Logger logger = Logger.getLogger(FileHandler.class);
    private boolean mHasChanged;

    /** Konstruktor
     *
     */
    public FileHandler() {
        mReader = new Reader();
        mWriter = new Writer();
    }//Ende Konstruktor

    private void createDirectory(File pDirectory) {
        if (!pDirectory.exists()) {
            pDirectory.mkdir();
            logger.info("build Folder created");
        } else {
            pDirectory.delete();
            pDirectory.mkdir();
            logger.info("build Folder already exists -> deleted");
        }
        mBuildDirectory = pDirectory;
    }

    public ArrayList getIdentifiers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPath(File pFile) {
        mPath = pFile;
    }

    public File getPath() {
        return mPath;
    }

    public boolean isValidDirectory() {
        if (mXMLModel.getprojectpath() == null) {
            return false;
        }
        File tmp = mXMLModel.getprojectpath();
        if (tmp.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean openFileRequest(File pFile) {

        boolean sameProject = false;

        if (!pFile.exists()) {
            String FileName = pFile.getName();
            if (!FileName.endsWith(".java")) {
                pFile = new File(pFile.getAbsolutePath() + ".java");
            } else {
                //Do Nothing
            }

            if (!(pFile.getName().equalsIgnoreCase("main.java"))) {
                mWriter.writeToFile(pFile, "public class " + FileName + " {\n\n}");
            } else {
                mWriter.writeToFile(pFile, "public class " + FileName + " {\n\n\tpublic static void main(String[] args) {\n\n\t}\n}");
            }
        }
        if (mXMLModel.getprojectpath() != null) {
            if (mXMLModel.getprojectpath().equals(pFile.getParentFile())) {
                mXMLModel.setOpenedFiles(pFile.toString(), mReader.readFromFile(pFile).toString());
                sameProject = true;
            } else {

                String tmp = mReader.readFromFile(pFile).toString();

                if (tmp.contains("package")) {
                    String parent = pFile.getParent();
                    String Secondparent = new File(parent).getParent();
                    if (mXMLModel.getprojectpath().toString().equals(Secondparent)) {
                        sameProject = true;
                        mXMLModel.setOpenedFiles(pFile.toString(), tmp);
                    } else {
                        sameProject = false;
                        mXMLModel.setprojectpath(new File(parent).getParent());
                        mXMLModel.getOpenedFiles().clear();
                        updateGui(pFile);
                    }

                } else {
                    sameProject = false;
                    mXMLModel.getOpenedFiles().clear();
                    updateGui(pFile);
                    mXMLModel.setprojectpath(pFile.getParent());
                }
                mXMLModel.setOpenedFiles(pFile.toString(), tmp);
                logger.info(mXMLModel.getprojectpath() + " as WorkingDirectory setted");
            }
        } else {
            sameProject = false;
            mXMLModel.setprojectpath(pFile.getParent());
            mXMLModel.setOpenedFiles(pFile.toString(), mReader.readFromFile(pFile).toString());
            logger.info(mXMLModel.getprojectpath() + " as WorkingDirectory setted");
        }
        createDirectory(new File(mXMLModel.getprojectpath() + "/build"));
        updateGui(pFile);
        return sameProject;
    }

    public void openFileRequest(String className) {
        className = className.replace(".", File.separator);
        openFileRequest(new File(mXMLModel.getprojectpath() + File.separator + className + ".java"));
    }

    public void closeFileRequest(File pFile) {
        if (mXMLModel.getOpenedFiles().containsKey(pFile.toString())) {
            mXMLModel.getOpenedFiles().remove(pFile.toString());
        } else {
            logger.error("Try to close a File, but this was theoretical never opened..");
        }
    }

    public File getBuildDirectory() {
        return mBuildDirectory;
    }

    public void writeToFile() {
        Map map = mXMLModel.getOpenedFiles();

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            mWriter.writeToFile(new File(mapEntry.getKey().toString()), mapEntry.getValue().toString());
        }
    }

    public void writeToXML() {
        try {
            mWriter.writeToXML(XML_PATH, mXMLModel);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public XMLModel getXMLModel() {
        if (mXMLModel == null) {
            setXMLModel();
        } else {
            //Do Nothing
        }
        return mXMLModel;
    }

    private void setXMLModel() {
        if (new File(XML_PATH).exists()) {
            logger.debug("XML found");
            mXMLModel = mReader.readFromXML(new File(XML_PATH));
        } else {
            logger.warn("No XML found, empty XMLModel created");
            mXMLModel = new XMLModel();
        }

        if (isValidDirectory()) {
            if (!mXMLModel.getOpenedFiles().isEmpty()) {
                Map map = mXMLModel.getOpenedFiles();
                Iterator iterator = map.entrySet().iterator();
                File tmpFile = new File("C:");
                while (iterator.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) iterator.next();
                    tmpFile = new File(mapEntry.getKey().toString());
                    mapEntry.setValue(mReader.readFromFile(tmpFile));
                }
                logger.debug("OldProjectFiles loaded");
                updateGui(tmpFile);
            } else {
                //Do Nothing
            }
        } else {
            //TODO User Inormieren??
            mXMLModel.setprojectpath(null);
            mXMLModel.getOpenedFiles().clear();
            logger.warn("Old project folder not found");
        }
    }

    private void updateGui(File pFocusedFile) {
        Object target = gui.Gui.getInstance();
        EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        eventqueue.postEvent(new FileAWTEvent(target, mXMLModel.getOpenedFiles(), pFocusedFile));
    }

    public void setHasChanged(boolean pHasChanged) {
        mHasChanged = pHasChanged;
    }

    public boolean hasChanged() {
        return mHasChanged;
    }

    /** Klasse zu AWT Eventahndling
     *
     */
    public class FileAWTEvent extends AWTEvent {

        /** Deklarieren der Event ID's, mFiles und mFocusedFiles
         * 
         */
        public static final int EVENT_ID = GUIConstants.EVENT_ID_FILEHANDLER;
        private LinkedHashMap<String, String> mFiles;
        private File mFocusedFile;

        /**Handelt AWT Events
         *
         * @param target
         * @param pFiles
         * @param pFocusedFile
         */
        public FileAWTEvent(Object target, LinkedHashMap<String, String> pFiles, File pFocusedFile) {
            super(target, EVENT_ID);
            mFiles = pFiles;
            mFocusedFile = pFocusedFile;
        }

        /** Gibt eine HashMap mit Files zurück
         *
         * @return mFiles
         */
        public LinkedHashMap<String, String> getFiles() {
            return mFiles;
        }

        /** Gibt das aktuelle File zurück 
         *
         * @return mFocusedFile
         */
        public File getFocusedFile() {
            return mFocusedFile;
        }
    }
}//Ende FileHandler

