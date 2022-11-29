import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class xmlToJava {

//        this function get xml file name, read xml file and return graph

    public static BNGraph readXml(String filename){
//            xml
        final String FILENAME = filename;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

//            create new graph
        BNGraph bnGraph =new BNGraph();

        try
        {

            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

//              create nodelist of  VARIABLE and DEFINITION  from xml file;

            NodeList variableList = doc.getElementsByTagName("VARIABLE");
            NodeList definitionList = doc.getElementsByTagName("DEFINITION");



            for (int variable = 0; variable < variableList.getLength(); variable++)
            {
                NodeBN nodeBNtemp = new NodeBN();
                nodeBNtemp.setGraph(bnGraph);
                Node node = variableList.item(variable);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    String NAME = element.getElementsByTagName("NAME").item(0).getTextContent();
                    nodeBNtemp.setName(NAME);

                    for(int outcome = 0; outcome <element.getElementsByTagName("OUTCOME").getLength();outcome++)
                    {
                      nodeBNtemp.getVariable().add(element.getElementsByTagName("OUTCOME").item(outcome).getTextContent());
                    }
                }

                bnGraph.insertNodeBN(nodeBNtemp.getName(),nodeBNtemp);

                Node node1 = definitionList.item(variable);
                if (node1.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node1;
                    String FOR = element.getElementsByTagName("FOR").item(0).getTextContent();

                    for (int giv = 0; giv < element.getElementsByTagName("GIVEN").getLength(); giv++)
                    {
                        bnGraph.graph.get(FOR).getParents().add(bnGraph.graph.get(element.getElementsByTagName("GIVEN").item(giv).getTextContent()));
                        bnGraph.graph.get(FOR).empty=false;

                    }
                    String table = "";
                    for (int tab = 0; tab < element.getElementsByTagName("TABLE").getLength(); tab++)
                    {
                        table=element.getElementsByTagName("TABLE").item(tab).getTextContent();

                    }
//                    bnGraph.graph.get(FOR).getDefinition().add("0.0");
                    split( bnGraph.graph.get(FOR).getDefinition(),table);

                }

            }

        }
        catch(ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }

        return  bnGraph;
    }

    public static  void split(ArrayList<String> arrayList ,String str){
    //using String split function
    String[] words = str.split(" ");
    //using java.util.regex Pattern
    Pattern pattern = Pattern.compile(" ");
    words = pattern.split(str);
        for (String s:words) {
            arrayList.add(s);
        }
    }

}