package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Reader von Files und XML-Files
 *
 *
 * @autor Michael Moser, Dimitri Balazs
 *
 * @version 1.00, 12. August 2012
 *
 */
public class Reader {

    FileReader mFileReader;
    LineNumberReader mLineNumberReader;
    BufferedReader mBufferedReader;
    StringBuilder mStringBuilder;

    /**
     * liefert den Inhalt vom File mit Namen "pFile"
     *
     *
     * @autor Dimitri Balazs
     *
     * @version 1.00, 12 August 2012
     * @param pFile Name des auszulesenden Files
     * @return StringBuilder mit Code als Inhalt
     *
     */
    public StringBuilder readFromFile(File pFile) {
        try {
            mFileReader = new FileReader(pFile);
            mBufferedReader = new BufferedReader(mFileReader);
        } catch (Exception e) {
            System.out.println("abcdefg" + e);
        }

        mStringBuilder = new StringBuilder();
        try {
            String tmp;
            while ((tmp = mBufferedReader.readLine()) != null) {
                mStringBuilder.append(tmp);
                mStringBuilder.append("\n");
            }
            mBufferedReader.close();
            mFileReader.close();
        } catch (Exception e) {
            //System.out.println("File nicht gefunden " + e);
        }
        return mStringBuilder;
    }

    /**
     * liefert das gewünschte XML-Model
     *
     * @autor Michael Moser
     *
     * @version 1.00, 12 August 2012
     * @param pFile Name des auszulesenden XML's
     * @return xmlModel mit Inhalt vom XML File
     *
     */
    public XMLModel readFromXML(File pFile) {
        XMLModel xmlModel = new XMLModel();
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(pFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("session");
            NodeList jdk = doc.getElementsByTagName("jdkpath");
            NodeList childNodes = nList.item(0).getChildNodes();
            NodeList childNodess = jdk.item(0).getChildNodes();

            //JDK Path auslesen und XML Model übergeben  
            xmlModel.setjdkpath(childNodess.item(0).getTextContent().trim());

            //Projectpath auslesen und XML Model übergeben
            xmlModel.setprojectpath(childNodes.item(1).getTextContent().trim());
            //OpenedFiles auslesen und XML Model übergeben
            for (int t = 3; t < childNodes.getLength(); t += 2) {
                System.out.println("Lengt: " + childNodes.getLength() + childNodes.item(t).getTextContent().trim());
                xmlModel.setOpenedFiles(new File(childNodes.item(t).getTextContent().trim()).toString(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlModel;
    }
}//Ende Reader

