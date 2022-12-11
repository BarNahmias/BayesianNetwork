package Ex1;

import java.util.HashMap;

public class BNGraph {
    HashMap<String,NodeBN> graph;

    public BNGraph() {
        this.graph=new HashMap<String,NodeBN>();
    }
    public BNGraph(HashMap<String, NodeBN> graph) {
        this.graph = graph;
    }

    public  void insertNodeBN(String kay , NodeBN value){
        this.graph.put(kay,value);
    }


    @Override
    public String toString() {
        return "BNGraph{" +
                "graph=" + graph +
                '}';
    }

    public String toStringVariabls() {
        String var="";
        for (NodeBN nodeBN: graph.values())
        {
            var=var + " "+ nodeBN.getVariable().getNAME().toString();
        }
        return "BNGraphVariabls{" +
                "Variabls=" + var +
                '}';
    }

}
