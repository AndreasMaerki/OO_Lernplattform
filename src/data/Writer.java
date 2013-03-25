package data;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Writer für File und XML
 *
 *
 * @autor Michael Moser,Dimitri Balazs
 *
 * @version 1.00, 12 August 2012
 *
 */
public class Writer {

    /**
     * Schreibt pCode nach pFile
     *
     *
     * @autor Dimitri Balazs
     *
     * @version 1.00, 12 August 2012
     * @param pFile String Repräsentation einer Methode
     * @param pCode Code welcher in das File geschrieben wird
     * @return false
     *
     */
    public Boolean writeToFile(File pFile, String pCode) {
        try {
            FileWriter fw = new FileWriter(pFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(pCode);
            bw.close();
            return true;
        } catch (IOException e) {
            System.out.println("WriteToFile: " + e);
            return false;
        }
    }//Ende writeToFile(File pFile, String pCode)

    /**
     * Schreibt Inhalt von XMLModel in ein XML File.
     *
     *
     * @autor Michael Moser
     *
     * @version 1.00, 12. August 2012
     * @param pXMLPath Pfad wo zu schreibendes XML liegt
     * @param pXMLModel Das XML Model von welchem der Inhalt in ein XML
     * geschrieben werden muss.
     * @throws ParserConfigurationException
     * @throws IOException
     *
     */
    public void writeToXML(String pXMLPath, XMLModel pXMLModel) throws ParserConfigurationException, IOException {


        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // Root element festlegen
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("SysProp");
        doc.appendChild(rootElement);

        // Element "Userprop" erzeugen
        Element userprop = doc.createElement("userprop");
        rootElement.appendChild(userprop);

        // Element "idkpath" erzeugen
        Element jdkpath = doc.createElement("jdkpath");
        jdkpath.appendChild(doc.createTextNode(pXMLModel.getjdkpath()));
        userprop.appendChild(jdkpath);

        // Element "session" erzeugen
        Element session = doc.createElement("session");
        userprop.appendChild(session);

        // Attribut "name" dem Element Session hinzufügen und den Wert "lastSession" zuweisen.
        Attr attr = doc.createAttribute("name");
        attr.setValue("lastSession");
        session.setAttributeNode(attr);

        // Element "projectpath" erzeugen
        Element projectpath = doc.createElement("projectpath");
        projectpath.appendChild(doc.createTextNode(pXMLModel.getprojectpath().toString()));
        session.appendChild(projectpath);

        // Element "OpenedFiles" erzeugen und loopen
        for (Entry<String, String> entry : pXMLModel.getOpenedFiles().entrySet()) {
            String key = entry.getKey();
            Element OpenedFile = doc.createElement("OpenedFile");
            OpenedFile.appendChild(doc.createTextNode(key));
            session.appendChild(OpenedFile);


        }

        File xml = new File(pXMLPath);
        FileWriter fw = new FileWriter(xml);
        BufferedWriter bw = new BufferedWriter(fw);
        XMLSerializer s = new XMLSerializer(bw, new OutputFormat(doc, "UTF-8", true));
        s.serialize(doc);


    }//Ende writeToXML(String pXMLPath, XMLModel pXMLModel)
}//Ende Writer

