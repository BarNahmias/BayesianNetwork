import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class BNGraph {
    HashMap<String, NodeBN> graph;


    public BNGraph() {
        this.graph = new HashMap<String, NodeBN>();
    }

    public BNGraph(HashMap<String, NodeBN> graph) {
        this.graph = graph;
    }

    public void insertNodeBN(String kay, NodeBN value) {
        this.graph.put(kay, value);
    }


    public HashMap<String, NodeBN> getGraph() {
        return graph;
    }

    public void setGraph(HashMap<String, NodeBN> graph) {
        this.graph = graph;
    }
}
