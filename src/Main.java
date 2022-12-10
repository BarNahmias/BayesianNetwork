import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws Exception {

        BNGraph bnGraph= new BNGraph();
        ArrayList<String> input =new ArrayList<String>();

        switch (1)
        {
            case 1:
                bnGraph= xmlToJava.readXml("C:\\Users\\בר נחמיאס\\IdeaProjects\\BayesianNetwork\\src\\alarm_net.xml");
                input = txtToJava.readTxt("C:\\Users\\בר נחמיאס\\IdeaProjects\\BayesianNetwork\\src\\input.txt");
                break;
            case 2:
                //        big net;

                bnGraph= xmlToJava.readXml("C:\\Users\\בר נחמיאס\\IdeaProjects\\BayesianNetwork\\src\\big_net.xml");
                input = txtToJava.readTxt("C:\\Users\\בר נחמיאס\\IdeaProjects\\BayesianNetwork\\src\\input1.txt");

        }



        for (String query:input)
        {

            int num =Character.getNumericValue(query.charAt(query.length()-1));

            switch (num)
            {
                case 1:
//                    BayesianInference query1 =new BayesianInference(bnGraph);
////                   query1.BayesianInference(query);
//                    System.out.println("this Pro  : " +  query1.BayesianInference(query));
//                    System.out.println("ADD  : " +  query1.addCounter + " MULL  : " + query1.mullCounter);

                    break;
                case 2:
                    variableElimination ve=new variableElimination(bnGraph,query);
//
                    ve.variableElimination();
//                    Factor a=new Factor();
//                    a.copy(ve.eliminateEvidence(bnGraph.graph.get("J")));

//                    ve.eliminateHidden(bnGraph.graph.get("A"));
                    System.out.println("case 2");

                    break;
                case 3:
                    System.out.println("case 3");

                    break;
            }
        }
//        Query query1 =new Query(bnGraph,"P(C3=T|B1=T),1");



//            Factor factorA =new Factor(bnGraph.graph.get("J"),"P(B=T|J=T,A=T),1",bnGraph);
//        Factor factorB =new Factor(bnGraph.graph.get("M"),"P(B=T|J=T,A=T),1",bnGraph);

//                System.out.println("A CPT " +query1.getBnGraph().graph.get("A").getCpt().cpt.toString());

//        HashMap<ArrayList<String> ,Double> nweFactor=  factorA.join("A" , factorB, factorA);
//        System.out.println();

//        for (ArrayList<String> row:nweFactor.keySet()
//             ) {
//
//            System.out.println(row + " : " + nweFactor.get(row));
//        }

//        System.out.println("D1 CPT " +bnGraph.graph.get("D1").getCpt().cpt.toString());
//        System.out.println("C1 CPT " +bnGraph.graph.get("B3").getDefinition().toString());

//        System.out.println("J CPT " +query1.getBnGraph().graph.get("J").getCpt().cpt.toString());
//        System.out.println("M CPT " +query1.getBnGraph().graph.get("M").getCpt().cpt.toString());
//        System.out.println("E CPT " +query1.getBnGraph().graph.get("E").getCpt().cpt.toString());



    }
}