package logic;

import java.util.LinkedList;
import java.lang.reflect.*;
import java.util.List;
import org.apache.log4j.*;

/** repräsentierendes KlassenModel inkl Werte der Members
 * 
 * 
 * @autor Christian Binder, Andreas Maerki
 * @version 1.00, 12 August 2012
 *
 *
 *Changelog:    28.05.2012  Strukturänderung, auf TreeMap wird verzichtet -> einfacherer Zugriff auf Daten
 *              15.05.2012  Grundstruktur erstellt
 *                          
 */
public class ClassModel {

    private static Logger logger = Logger.getLogger(ClassModel.class);
    private List mMembers, mMethods, mHasRelationshipTo;
    private ClassModel mExtendsFrom;
    private String mName;
    private Class[] mDeclaredClasses;
    private Class mSuperClass;
    private boolean mIsMainClass;

    /**erzeugt ein neues Klassenmodel
     * 
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pName Klassnname
     */
    public ClassModel(String pName) {
        mName = pName;
        mMembers = new LinkedList();
        mMethods = new LinkedList();
        mHasRelationshipTo = new LinkedList();
        mIsMainClass = false;
    }

    /**liefert den Klassenname
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return
     */
    public String getName() {
        return mName;
    }

    /**fügt ein neue Membervariable hinzu und erstellt ein MemberModel
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     *
     * @param pModifier
     * @param pName
     * @param pType
     * @see MemberModel
     */
    public void addMember(String pModifier, String pName, Type pType) {
        mMembers.add(new MemberModel(pModifier, pName, pType));
        logger.debug("The Member \"" + pModifier + " " + pName + " " + pType + "\" at \"" + mName + "\" added");
//        Collections.sort(mMembers); //TODO Sortierung der Member
    }

    /** liefert das gewünschte MemberModel
     *
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pName Name des gesuchten MemberModel
     * @return
     * @see MemberModel
     *
     */
    public MemberModel getMemberModel(String pName) {
        for (Object tmp : mMembers) {
            MemberModel tmpModel = (MemberModel) tmp;
            if (tmpModel.mName.equalsIgnoreCase(pName)) {
                return tmpModel;
            }
        }
        return null;
    }

    /**liefert sämtliche Membermodels
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return
     * @see MemberModel
     */
    public List<MemberModel> getMemberModels() {
        return mMembers;
    }

    /**fügt eine neue Methode hinzu und erstellt ein MethodModel
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pModifier
     * @param pName
     * @param pRetType
     * @param pParType
     * @see MethodModel
     */
    public void addMethod(String pModifier, String pName, Type pRetType, Type[] pParType) {
        mMethods.add(new MethodModel(pModifier, pName, pRetType, pParType));
        String test = "";
        int i = 0;
        for (Type p : pParType) {
            test += p;
            i++;
        }
        logger.debug("The Method \"" + pModifier + " " + pName + " " + pRetType + test + "\" at \"" + mName + "\" added");
    }//Ende addMethod(String pModifier, String pName, Type pRetType, Type[] pParType)

    /**liefert die Methoden
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return
     */
    public List<MethodModel> getMethods() {
        return mMethods;
    }//Ende getMethods()

    /**Setzt die Beziehung zu einer anderen Klasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pModel die klasse, zu der eine Beziehung besteht
     */
    public void setRelationship(ClassModel pModel) {
        mHasRelationshipTo.add(pModel);
    }//Ende setRelationship(ClassModel pModel)

    /** liefer sämtliche Beziehungen
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return
     */
    public List<ClassModel> getRelationship() {
        return mHasRelationshipTo;
    }//Ende getRelationship()

    /**Setzt die Superklasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pModel Das ClassModel der Superklasse
     */
    public void setExtendsFrom(ClassModel pModel) {
        mExtendsFrom = pModel;
    }//Ende setExtendsFrom(ClassModel pModel)

    /**liefert die Superklasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return
     */
    public ClassModel getExtendsFrom() {
        return mExtendsFrom;
    }//Ende getExtendsFrom()

    /**Setzt die Superklasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pSuperClass class repräsentation
     */
    public void setSuperClass(Class pSuperClass) {
        mSuperClass = pSuperClass;
    }//Ende setSuperClass(Class pSuperClass)

    /**liefert die Superklasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return class repräsentation
     */
    public Class getSuperClass() {
        return mSuperClass;
    }//Ende getSuperClass()

    /**kenzeichnte das Klassenmodel als Main Klasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @param pValue
     */
    public void setMainClass(boolean pValue) {
        mIsMainClass = pValue;
    }//Ende setMainClass(boolean pValue)

    /**ist Klassenmodel eine Main Klasse
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     * @return true wenn Klassenmodel eine main methode besitzt
     */
    public boolean isMainClass() {
        return mIsMainClass;
    }//Ende isMainClass()

    /** Ein Model dass eine Membervariable repraesentiert
     *
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     */
    public class MemberModel {

        public String mModifier, mName, mValue;
        public Type mType;

        /**Erstellt ein neues Membermodel
         *
         * @autor Christian Binder, Andreas Maerki
         * @version 1.00, 12 August 2012
         * @param pModifier
         * @param pName
         * @param pType
         */
        public MemberModel(String pModifier, String pName, Type pType) {
            mModifier = pModifier;
            mName = pName;
            mType = pType;
        }//Ende Konstruktor

        @Override
        public String toString() {
            String type = mType.toString();
            if (type.contains(".")) {
                type = type.substring(type.lastIndexOf("."));
            }
            String ret = mName + " : " + type;
            return ret;
        }//Ende toString()
    }//Ende MemberModel

    /**  Ein Model dass eine Methode repraesentiert
     * @autor Christian Binder, Andreas Maerki
     * @version 1.00, 12 August 2012
     */
    public class MethodModel {

        public String mModifier, mName;
        public Type mRetType;
        public Type[] mParType;

        /**Erstellt ein neues Methoden Model
         *
         * @autor Christian Binder, Andreas Maerki
         * @version 1.00, 12 August 2012
         * @param pModifier
         * @param pName
         * @param pRetType
         * @param pParType
         */
        public MethodModel(String pModifier, String pName, Type pRetType, Type[] pParType) {
            mModifier = pModifier;
            mName = pName;
            mRetType = pRetType;
            mParType = pParType;
        }//Ende Konstruktor

        @Override
        public String toString() {
            StringBuilder tmpParTypeBuilder = new StringBuilder();
            int i = 1;
            for (Type tmpPartype : mParType) {
                String tmp = tmpPartype.toString().substring(tmpPartype.toString().lastIndexOf(".") + 1);
                tmpParTypeBuilder.append(tmp).append(" value").append(i).append(", ");//ruekgabewert unklar-->TESTEN
                i++;
            }
            if (tmpParTypeBuilder.length() > 2) {
                tmpParTypeBuilder.delete(tmpParTypeBuilder.length() - 2, tmpParTypeBuilder.length());
            }
            String retType = mRetType.toString();
            if (retType.contains(".")) {
                retType = retType.substring(retType.lastIndexOf("."));
            }
            String ret = mName + "(" + tmpParTypeBuilder + ")" + " : " + retType;
            return ret;
        }//Ende toString()
    }//Ende MethodModel
}//Ende ClassModel

