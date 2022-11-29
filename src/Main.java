import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws Exception {
        BNGraph bnGraph= new BNGraph();
        bnGraph= xmlToJava.readXml("C:\\Users\\בר נחמיאס\\IdeaProjects\\BayesianNetwork\\src\\alarm_net.xml");


        ArrayList<String> query =new ArrayList<String>();
        query = txtToJava.readTxt("C:\\Users\\בר נחמיאס\\IdeaProjects\\BayesianNetwork\\src\\input.txt");

        Query query1 =new Query(bnGraph,"P(B=F|E=T,A=T,J=F,M=T),1");
        System.out.println("query" + query.get(0));
        System.out.println("this VAR  :" +  query1.getBnGraph().graph.keySet().toString());


        System.out.println("query pro "+ query1.findQueryProbability());
        System.out.println("B CPT " +query1.getBnGraph().graph.get("B").getCpt().cpt.toString());
        System.out.println("J CPT " +query1.getBnGraph().graph.get("J").getCpt().cpt.toString());
        System.out.println("M CPT " +query1.getBnGraph().graph.get("M").getCpt().cpt.toString());
        System.out.println("A CPT " +query1.getBnGraph().graph.get("A").getCpt().cpt.toString());
        System.out.println("E CPT " +query1.getBnGraph().graph.get("E").getCpt().cpt.toString());

        System.out.println("probab " +query1.findCurrProbability("P(B=F|E=T,A=T,J=F,M=T)"));
        System.out.println("this kayset  :" +  query1.allVariableFromQuery.keySet().toString());

//        System.out.println("total pro "+ query1.simpleBayesianInference());
        System.out.println("beast   :" +  query1.getBestKay());

//        System.out.println("con : " + query1.getBestKay().contains("B=T"));
        System.out.println("add : " + query1.addCounter + "  mull : " + query1.mullCounter);


//        System.out.println("this VAR  :" +  query1.getBnGraph().graph.keySet().toString());
//        System.out.println("hidden pro "+ query1.findHiddenProbability());
//        System.out.println("query" + query.toString());
//        System.out.println("this query string  :" +  query1.getQueryString());
//        System.out.println("this query var  :" +  query1.getQuery().toString());
//
//        System.out.println("this evidence  :" +  query1.getEvidence().toString());
//        query1.findHidden();
//        System.out.println("this Hidden  :" +  query1.getHidden().toString());
//
//        System.out.println("this list  :" +  query1.getListOfValuesFromQuery().toString());
//
//        System.out.println("beast   :" +  query1.getBestKay());
//
//        System.out.println("keys   :" +  query1.getBnGraph().getGraph().get("J").getCpt().toString());
//
//        System.out.println("findEvidenceProb   :" + query1.findEvidenceProbability());
//
//                System.out.println("pro  :" +  bnGraph.graph.get("A").getDefinition());
//        System.out.println("var  :" +  bnGraph.graph.get("A").getVariable());






    }
}