package Ex1;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) throws Exception {

        BNGraph bnGraph= new BNGraph();
        bnGraph= xmlToJava.readXml("C:\\Users\\בר נחמיאס\\eclipse-workspace\\untitled2\\src\\Ex1\\alarm_net.xml");
//        cpt c = new cpt(bnGraph.graph.get("A"));
        System.out.println("graph :" +  bnGraph.graph.toString());

        ArrayList<String>  S =new ArrayList<String>();
               S = txtToJava.readTxt("C:\\Users\\בר נחמיאס\\eclipse-workspace\\untitled2\\src\\Ex1\\input.txt");
//        System.out.println(S.toString());
//        System.out.println("var  :" +  bnGraph.toStringVariabls());
        System.out.println("par :" +  bnGraph.graph.get("A").getDefinition().getTABLE().get(1));
//        System.out.println( c.toString());
    }

}
