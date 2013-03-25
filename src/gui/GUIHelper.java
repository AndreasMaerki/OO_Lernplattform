package gui;

import gui.events.MyActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import logic.iLogic;

/**
 *
 * @author Dimitri Balazs
 * @version 1.00, 12 August 2012
 */
public class GUIHelper {

    private static JFileChooser mFileChooser;
    private static JPopupMenu mPopupMenu;
    private static MyActionListener mMyActionListener;

    /**
     * ActionListener wird in Beziehung gesetzt
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     *
     * @param pMyActionListener Der uebergebene ActionListener
     *
     */
    public void setRelation(MyActionListener pMyActionListener) {
        mMyActionListener = pMyActionListener;
    }// ende setRelation

    /**
     * Statische Methode die den FileChooser zurueckgiebt, nachdem er durch
     * einen switchCase durchlaufen hat, und entsprechend gesetzt wurde
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @param pFCType ein String der GUIConstants:NEWFILE, OPENFILE, JDKMISSING
     * @return beladener fileChooser
     */
    public static JFileChooser getFileChooser(String pFCType) {
        if (mFileChooser == null) {
            mFileChooser = new JFileChooser();
        }
        iLogic mLogic = gui.Gui.getInstance().getLogic();
        switch (pFCType) {

            case GUIConstants.NEWFILE:
                mFileChooser.setCurrentDirectory(mLogic.getXMLModel().getprojectpath());
                mFileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File pFile) {
                        return pFile.getName().toLowerCase().endsWith(".java")
                                || pFile.isDirectory();
                    }// ende accept

                    @Override
                    public String getDescription() {
                        return "Java-Applikationen(*.java)";
                    }// ende getDescription
                });
                int save = mFileChooser.showSaveDialog(gui.Gui.getInstance());
                if (save == JFileChooser.APPROVE_OPTION) {
                    mLogic.openFileRequest(new File(mFileChooser.getSelectedFile().getAbsolutePath()));
                }// ende if
                break;

            case GUIConstants.OPENFILE:
                mFileChooser.setCurrentDirectory(mLogic.getXMLModel().getprojectpath());
                mFileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File pFile) {
                        return pFile.getName().toLowerCase().endsWith(".java")
                                || pFile.isDirectory();
                    }// ende accept

                    @Override
                    public String getDescription() {
                        return "Java-Applikationen(*.java)";
                    }// ende getDescription
                });
                int choose = mFileChooser.showOpenDialog(gui.Gui.getInstance());
                if (choose == JFileChooser.APPROVE_OPTION) {
                    mLogic.openFileRequest(mFileChooser.getSelectedFile());
                }// ende if
                break;
            //jdk nicht gefunden
            case GUIConstants.JDKMISSING:
                mFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                mFileChooser.showOpenDialog(gui.Gui.getInstance());
        }// ende swith
        return mFileChooser;
    }// ende getFileChooser

    /**
     * Returniert eine Instanz des Popup-menues und uebergiebt ihr einen
     * ActionListener.
     *
     * @author Dimitri Balazs
     * @version 1.00, 12 August 2012
     * @return Das PopupMenue
     */
    public static JPopupMenu getJPopupMenuInstance() {
        if (mPopupMenu == null) {
            mPopupMenu = new JPopupMenu();
            JMenuItem mMenuNew = new JMenuItem(GUIConstants.NEWFILE);
            mMenuNew.setName(GUIConstants.NEWFILE);
            mMenuNew.addActionListener(mMyActionListener);
            mPopupMenu.add(mMenuNew);
            mPopupMenu.addSeparator();
            JMenuItem mMenuOpen = new JMenuItem(GUIConstants.OPENFILE);
            mMenuOpen.setName(GUIConstants.OPENFILE);
            mMenuOpen.addActionListener(mMyActionListener);
            mPopupMenu.add(mMenuOpen);
        }// ende getJPopupMenuInstance
        return mPopupMenu;
    }// ende getJPopupMenuInstance
} // ende GUIHelper

