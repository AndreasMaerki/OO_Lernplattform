package gui;

import com.mxgraph.swing.mxGraphComponent;
import data.FileHandler.FileAWTEvent;
import logic.ClassDesign.ClassAWTEvent;
import logic.ObjectDesign.ObjectAWTEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import java.util.logging.*;
import javax.swing.filechooser.FileFilter;
import logic.Debugger.DebugAWTEvent;
import logic.Compiler.CompileAWTEvent;
import logic.iLogic;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import gui.events.*;
import logic.Distributor;

/**
 * Gui Klasse
 *
 *
 * @autor Michael Moser
 * @version 1.00, 12. August 2012
 *
 */
public class Gui extends JFrame {

    //Objekte Registrieren
    //JSplitPanes
    private JSplitPane mSplitHorizontal;
    private JSplitPane mSplitTop;
    private JSplitPane mSplitBottom;
    //Panels
    private ClassView mClassView;
    private ObjectView mObjectView;
    private CodeView mCodeView;
    private OutputView mOutputView;
    private ToolBar mToolBar;
    //Icons
    private ImageIcon mIcon;
    //Menu
    private JMenuBar mMenuBar;
    private JMenu mMenuFile;
    private JMenu mMenuEdit;
    private JMenuItem mMenuNew;
    private JMenuItem mMenuOpen;
    private JMenuItem mMenuSave;
    private JMenuItem mMenuQuit;
    private JMenuItem mMenuUndo;
    private JMenuItem mMenuRedo;
    //Layout
    private GroupLayout mLayout;
    private LayoutStyle mLayoutStyle;
    private static Gui instance = null;
    private iLogic mLogic;
    //gui.event.Classes
    private MyActionListener mMyActionListener;
    private MyKeyListener mMyKeyListener;
    private MyMouseListener mMyMouseListener;
    private MyWindowListener mMyWindowListener;

    /**
     * Konstruktor
     */
    private Gui() {
        super(GUIConstants.APPTITLE);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Membervariabeln deklarieren  

        mSplitHorizontal = new JSplitPane();
        mSplitTop = new JSplitPane();
        mSplitBottom = new JSplitPane();

        mClassView = new ClassView();
        mObjectView = new ObjectView();
        mCodeView = new CodeView();
        mOutputView = new OutputView();
        mToolBar = new ToolBar();

        mIcon = new ImageIcon("src/icon.png");

        mMenuBar = new JMenuBar();
        mMenuFile = new JMenu(GUIConstants.FILE);
        mMenuFile.setName(GUIConstants.FILE);

        mMenuNew = new JMenuItem(GUIConstants.NEWFILE);
        mMenuNew.setName(GUIConstants.NEWFILE);
        mMenuOpen = new JMenuItem(GUIConstants.OPENFILE);
        mMenuOpen.setName(GUIConstants.OPENFILE);
        mMenuEdit = new JMenu(GUIConstants.EDIT);
        mMenuEdit.setName(GUIConstants.EDIT);
        mMenuRedo = new JMenuItem(GUIConstants.REDO);
        mMenuRedo.setName(GUIConstants.REDO);
        mMenuUndo = new JMenuItem(GUIConstants.UNDO);
        mMenuUndo.setName(GUIConstants.UNDO);
        mMenuSave = new JMenuItem(GUIConstants.SAVE);
        mMenuSave.setName(GUIConstants.SAVE);
        mMenuQuit = new JMenuItem(GUIConstants.CLOSE);
        mMenuQuit.setName(GUIConstants.CLOSE);

        mLayout = new GroupLayout(getContentPane());
        mLayoutStyle = new LayoutStyle();

        mMyActionListener = new MyActionListener();
        mMyKeyListener = new MyKeyListener();
        mMyMouseListener = new MyMouseListener();
        mMyWindowListener = new MyWindowListener();

        //Look and Feel festlegen

        //Layout Hauptfenster
        setLayout(new GridLayout(5, 5));

        //Inhalt Hauptfenster
        //Splitpanel Ausrichtung  

        mSplitTop.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mSplitBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);

        //Splitpanes Layouthandling
        getContentPane().setLayout(mLayout);

        //Layout Horizontal
        mLayout.setHorizontalGroup(
                mLayout.createParallelGroup(mLayout.LEADING).add(mLayout.createSequentialGroup().add(mSplitHorizontal,
                mLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE).addPreferredGap(mLayoutStyle.RELATED)
                .add(mToolBar, mLayout.PREFERRED_SIZE, mLayout.DEFAULT_SIZE, mLayout.PREFERRED_SIZE).addContainerGap()));

        //Layout Vertical
        mLayout.setVerticalGroup(
                mLayout.createParallelGroup(mLayout.LEADING).add(mLayout.createSequentialGroup()
                .add(mLayout.createParallelGroup(mLayout.LEADING).add(mLayout.TRAILING, mLayout.createSequentialGroup()
                .addContainerGap().add(mToolBar, mLayout.DEFAULT_SIZE, mLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(mSplitHorizontal)).addContainerGap()));

        //Panels den Splitpanes hinzufügen
        mSplitTop.setLeftComponent(mClassView);
        mSplitTop.setRightComponent(mObjectView);
        mSplitBottom.setLeftComponent(mCodeView);
        mSplitBottom.setRightComponent(mOutputView);
        mSplitHorizontal.setLeftComponent(mSplitTop);
        mSplitHorizontal.setRightComponent(mSplitBottom);


        //Menu
        setJMenuBar(mMenuBar);
        mMenuBar.add(mMenuFile);
        mMenuFile.add(mMenuNew);
        mMenuFile.add(mMenuOpen);
        mMenuFile.add(mMenuSave);
        mMenuFile.add(new JSeparator());
        mMenuFile.add(mMenuQuit);
        mMenuBar.add(mMenuEdit);
        mMenuEdit.add(mMenuRedo);
        mMenuEdit.add(mMenuUndo);


        //Listeners den Objekten übergeben
        addWindowListener(mMyWindowListener);
        mMenuOpen.addActionListener(mMyActionListener);
        mMenuUndo.addActionListener(mMyActionListener);
        mMenuRedo.addActionListener(mMyActionListener);
        mMenuNew.addActionListener(mMyActionListener);
        mMenuSave.addActionListener(mMyActionListener);
        mMenuQuit.addActionListener(mMyActionListener);

        mCodeView.setEventListener(mMyKeyListener, mMyMouseListener);
        mToolBar.setEventListener(mMyActionListener);


        //Vergösserbarkeit, Sichtbarkeit, Position, Icon des Hauptpanels festlegen / packen
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        setResizable(true);
        setVisible(true);

        pack();
        setIconImage(mIcon.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        enableEvents(DebugAWTEvent.EVENT_ID);
        enableEvents(CompileAWTEvent.EVENT_ID);
        enableEvents(ClassAWTEvent.EVENT_ID);
        enableEvents(ObjectAWTEvent.EVENT_ID);
        enableEvents(FileAWTEvent.EVENT_ID);

    }// Ende Konstruktor

    /**Gibt Gui Instanz zurück
     *
     * @return Instanz retournieren
     */
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    /**Beziehung Logik setzten
     *
     * @param pLogic
     */
    public void setLogic(iLogic pLogic) {
        mLogic = pLogic;
        mCodeView.setLogic(pLogic);
        mMyActionListener.setRelations(pLogic);
        mMyKeyListener.setRelations(pLogic);
        mMyMouseListener.setRelations(pLogic);
        mMyWindowListener.setRelations(pLogic);
        new GUIHelper().setRelation(mMyActionListener);

    }

    /** Gibt ClassView zurück
     *
     * @return gibt mClassView zurück
     */
    public JPanel getClassView() {
        return mClassView;
    }

    /** Gibt ObjectView zurück
     *
     * @return gibt mObjectView zurück
     */
    public JPanel getObjectView() {
        return mObjectView;
    }

    /** Gibt CodeView zurück
     *
     * @return gibt mCodeView zurück
     */
    public CodeView getCodeView() {
        return mCodeView;
    }

    /** Gibt Logic zurück
     *
     * @return gibt mLogic zurück
     */
    public iLogic getLogic() {
        return mLogic;
    }

    /** Gibt neue Instanz von JFileChooser zurück
     *
     * @return gibt eine neue Instanz von JFileChooser zurück
     */
    public static JFileChooser getFileChooser() {

        return new JFileChooser();
    }

    @Override
    protected void processEvent(AWTEvent e) {

        if (e instanceof DebugAWTEvent) {
            DebugAWTEvent debugEvent = (DebugAWTEvent) e;
            mOutputView.addText(debugEvent.getMsg());
            mCodeView.highliteDebugPosition(debugEvent.getLineNr(), debugEvent.getCurrentClass());
        } else if (e instanceof CompileAWTEvent) {
            CompileAWTEvent compileEvent = (CompileAWTEvent) e;
            JOptionPane.showMessageDialog(this, compileEvent.getMsg());
        } else if (e instanceof ClassAWTEvent) {
            if (mClassView.getComponentCount() > 0) {
                mClassView.removeAll();
            }
            ClassAWTEvent classEvent = (ClassAWTEvent) e;
            mxGraphComponent tmp = classEvent.getGraphComponent();
            tmp.addMouseListener(mMyMouseListener);
            mClassView.add(tmp);
            revalidate();
        } else if (e instanceof ObjectAWTEvent) {
            if (mObjectView.getComponentCount() > 0) {
                mObjectView.removeAll();
            }
            ObjectAWTEvent objectEvent = (ObjectAWTEvent) e;
            mObjectView.add(objectEvent.getGraphComponent());
            revalidate();
        } else if (e instanceof FileAWTEvent) {
            FileAWTEvent fileevent = (FileAWTEvent) e;
            Map map = fileevent.getFiles();
            Iterator iterator = map.entrySet().iterator();
            int tabCount = mCodeView.getTabCount();
            if (tabCount > map.size()) {
                mCodeView.removeAllTabs();
            }
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                mCodeView.setTab(new File(mapEntry.getKey().toString()), mapEntry.getValue().toString());
            }
            mCodeView.focusTabByName(fileevent.getFocusedFile());
        } else {
            super.processEvent(e);
        }
    }
}// Klassen Ende

