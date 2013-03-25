package data;

import java.io.*;
import java.util.ArrayList;

import java.util.*;

/** Ein Model das wichtige Projektdaten enthält
 * 
 * 
 * @autor Dimitri Balazs, Michael Moser
 * @version 1.00, 12. August 2012
 *                          
 */
public class XMLModel {

    private String mjdkpath;
    private String mlastsession;
    private String mprojectpath, mBuildDirectory;
    private LinkedHashMap<String, String> mOpenedFiles = new LinkedHashMap();
    private String[][] mGUIComponents;

    /**
     * @return gibt den mjdkpath zurück
     */
    public String getjdkpath() {
        return mjdkpath;

    }

    /**
     * @param mjdkpath setzt mjdkpath
     */
    public void setjdkpath(String mjdkpath) {
        this.mjdkpath = mjdkpath;
    }

    /**
     * @return gibt den mlastsession zurück 
     */
    public String getlastsession() {
        return mlastsession;
    }

    /**
     * @param mlastsession setzt mlastsession
     */
    public void setlastsession(String mlastsession) {
        this.mlastsession = mlastsession;
    }

    /**
     * @return gibt den mprojectpath zurück  
     */
    public File getprojectpath() {
        if (mprojectpath != null) {
            return new File(mprojectpath);
        } else {
            return null;
        }
    }

    /** 
     * @return null falls getprojectpath ungleich null ist
     */
    public File getBuildDirectory() {
        if (getprojectpath() != null) {
            return new File(getprojectpath().toString() + "/build");
        } else {
            return null;
        }
    }

    /**
     * @param mprojectpath setzt mprojectpath
     */
    public void setprojectpath(String mprojectpath) {
        this.mprojectpath = mprojectpath;
    }

    /**
     * @return gibt mOpenedFiles zurück
     */
    public LinkedHashMap<String, String> getOpenedFiles() {
        return mOpenedFiles;
    }

    /**
     * @param pOpenedFile einzelnes geöffnets File
     * @param pContent Inhalt des Files
     */
    public void setOpenedFiles(String pOpenedFile, String pContent) {
        mOpenedFiles.put(pOpenedFile, pContent);
    }

    /**
     * @return gibst mGUIComponents zurück
     */
    public String[][] getGUIComponents() {
        return mGUIComponents;
    }

    /**
     * @param mGUIComponents setzt mGUIComponents
     */
    public void setGUIComponents(String[][] mGUIComponents) {
        this.mGUIComponents = mGUIComponents;
    }
}//Ende XMLModel

