package logic;

import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.reflect.*;
import org.apache.log4j.*;
import data.XMLModel;
import logic.ClassModel.MemberModel;
import logic.ClassModel.MethodModel;

/** Erstellt die Klassenmodels aus den .class Dateien im build Ordner
 * 
 * 
 * @autor Christian Binder
 * @version 1.00, 12 August 2012
 *
 */
public class ModelAnalyzer {

    private LinkedList<ClassModel> mClassModels;
    private XMLModel mXMLModel;
    private static final Logger logger = Logger.getLogger(ModelAnalyzer.class);
    private final String[] KNOWNTYPES = {"boolean", "String", "int", "double", "long"};
    private ClassLoader mClassLoader;

    /** Erstellt einen neuen Modelanalyzer
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pXMLModel
     */
    public ModelAnalyzer(XMLModel pXMLModel) {
        mXMLModel = pXMLModel;
        mClassModels = new LinkedList();
    }//Ende Konstruktor

    /**liefert das Klassenmodel, welches der Main Klasse entspricht
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return Das Klassenmodel
     * @throws NotFoundException wenn keine Main Klasse gefunden wurde
     */
    public ClassModel getMainClass() throws NotFoundException {
        for (ClassModel tmpModel : mClassModels) {
            for (MethodModel tmpMethodModel : tmpModel.getMethods()) {
                if (tmpMethodModel.mModifier.contains("static") && tmpMethodModel.mName.equalsIgnoreCase("main")) {
                    return tmpModel;
                }
            }
        }
        throw new NotFoundException("main Class");
    }//Ende getMainClass()

    /** liefert die Klassenmodelle in einer Liste
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return
     */
    public LinkedList<ClassModel> getClassModels() {
        if (mClassModels.size() == 0) {
            createClassModels();
        }
        logger.info("ClassModels just given, not created");
        return mClassModels;
    }//Ende getClassModels

    /** liefert das Klassenmodel einer Klasse
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pName der Name der gesuchten Klasse
     * @return
     */
    public ClassModel getClassModel(String pName) {
        if (pName.endsWith(".java")) {
            pName = pName.substring(0, pName.lastIndexOf("."));
        }

        for (ClassModel tmpModel : mClassModels) {
            if (tmpModel.getName().contains(pName)) {
                return tmpModel;
            }
        }
        return null;
    }//Ende getClassModel

    /** markiert die Schlüsselwörter in einer Datei -> Noch nicht implementiert
     *
     * @autor Christian Binder
     * @version 0.10, 12 August 2012
     */
    public void highlightIdentifiers() {
        throw new UnsupportedOperationException("Not supported yet");
    }//Ende highlightIdentifiers()

    private void createClassModels(File dir) {
        try {
            LinkedList<File> mFiles = new LinkedList();
            for (File subFile : dir.listFiles()) {

                if (subFile.isDirectory()) {
                    for (File sub2File : subFile.listFiles()) {
                        if (sub2File.isDirectory()) {
                            for (File sub3File : subFile.listFiles()) {
                                if (sub3File.isDirectory()) {
                                    //irgendwo hat es auch Grenzen..
                                } else {
                                    mFiles.add(sub3File);
                                }
                            }
                        } else {
                            mFiles.add(sub2File);
                        }
                    }
                } else {
                    mFiles.add(subFile);
                }
            }

            for (File tmpFile : mFiles) {
                StringBuilder tmpBuilder = new StringBuilder(tmpFile.getAbsolutePath());
                tmpBuilder.delete(0, dir.toString().length());
                tmpBuilder.delete(tmpBuilder.lastIndexOf("."), tmpBuilder.length());
                if (tmpBuilder.toString().startsWith(File.separator)) {
                    tmpBuilder.deleteCharAt(0);
                }

                String className = tmpBuilder.toString().replace(File.separator, ".");

                logger.info(className + " in reflections machinery loaded");

                Class c = mClassLoader.loadClass(className);
                ClassModel model = new ClassModel(className);

                //Methoden auslesen
                Method[] methods = c.getDeclaredMethods();
                for (int x = 0; x < methods.length; x++) {
                    if (!methods[x].isAccessible()) {
                        methods[x].setAccessible(true);
                    }
                    String modifier = Modifier.toString(methods[x].getModifiers());
                    String name = methods[x].getName();
                    Type retType = methods[x].getGenericReturnType();
                    Type[] parType = methods[x].getGenericParameterTypes();
                    model.addMethod(modifier, name, retType, parType);
                }

                //Member Variabeln auslesen
                Field[] fields = c.getDeclaredFields();
                for (int x = 0; x < fields.length; x++) {
                    if (!fields[x].isAccessible()) {
                        fields[x].setAccessible(true);
                    }
                    String modifier = Modifier.toString(fields[x].getModifiers());
                    String name = fields[x].getName();
                    Type type = fields[x].getGenericType();
                    model.addMember(modifier, name, type);
                }

                //SuperKlasse auslesen
                model.setSuperClass(c.getSuperclass());

                mClassModels.add(model);
            }

            //Beziehungen und Superklasse feststellen Variante
            for (ClassModel tmp : mClassModels) {
                for (int x = 0; x < mClassModels.size(); x++) {
                    for (Object tmpModel : tmp.getMemberModels()) {
                        Type type = ((MemberModel) tmpModel).mType;
                        if (!checkIfKnownType(type)) {
                            int y = 0;
                            if (type.toString().contains(mClassModels.get(x).getName())) {
                                tmp.setRelationship(mClassModels.get(x));
                                logger.debug(tmp.getName() + " has Relation to " + ((ClassModel) tmp.getRelationship().get(y)).getName());
                                y++;
                            }
                        }
                    }

                    if (tmp.getSuperClass() != null && tmp.getSuperClass().getName().contains(mClassModels.get(x).getName())) {
                        tmp.setExtendsFrom(mClassModels.get(x));
                        logger.debug(tmp.getName() + " extends from " + ((ClassModel) tmp.getExtendsFrom()).getName());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//Ende createClassModels (File dir)

    /** erstellt aus einer .class Datei ein repräsentierendes KlassenModel
     *  Bedingungen: mData != null &&  valid WorkingDirectory
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @return 
     *
     */
    public LinkedList createClassModels() {
        try {
            mClassModels.clear();
            if (mClassLoader != null) {
                mClassLoader.clearAssertionStatus();
            }
            logger.info("ClassModels newly created");

            File buildFolder = mXMLModel.getBuildDirectory();

            URL url[] = new URL[1];
            url[0] = new URL("file", "", buildFolder.getPath() + "/");
            mClassLoader = new URLClassLoader(url);
            createClassModels(buildFolder);

        } catch (Exception e) {
            logger.error(e);
        }
        return mClassModels;
    }//Ende createClassModels()

    private boolean checkIfKnownType(Type pType) {
        boolean ret = false;
        for (String tmp : KNOWNTYPES) {
            if (pType.toString().contains(tmp)) {
                ret = true;
            }
        }
        return ret;
    }//Ende checkIfKnownType(Type pType)
}//Ende ModelAnalyzer

