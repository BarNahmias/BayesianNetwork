package Ex1;

import java.util.ArrayList;

public class NodeBN {

    String name;
    VARIABLE variable;
    DEFINITION definition;

    private ArrayList<NodeBN> parents;

    private BNGraph graph;




    public NodeBN(String name, VARIABLE variable, DEFINITION definition, ArrayList<NodeBN> arrayListr) {
        this.name = name;
        this.variable = variable;
        this.definition = definition;
        this.parents=arrayListr ;
    }
    public NodeBN(){
        this.parents= new ArrayList<NodeBN>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VARIABLE getVariable() {
        return variable;
    }

    public void setVariable(VARIABLE variable) {
        this.variable = variable;
    }

    public DEFINITION getDefinition() {
        return definition;
    }

    public void setDefinition(DEFINITION definition) {
        this.definition = definition;
    }

    public void addParents(NodeBN nodeBN){
        this.parents.add(nodeBN);
    }

    public BNGraph getGraph() {
        return graph;
    }

    public void setGraph(BNGraph graph) {
        this.graph = graph;
    }

    public ArrayList<NodeBN> getParents() {
        return parents;
    }

    public void setParents(ArrayList<NodeBN> parents) {
        this.parents = parents;}


        @Override
    public String toString() {
        return "NodeBN{" +
                "name='" + name + '\'' +
                ", variable=" + variable +
                ", definition=" + definition +
                ", parents=" + parents +
                '}';
    }


    public String toStringPa() {
        String name = "";
        for (NodeBN no :parents) {
            name=name+ " , " + no.name;
        }
        return "NodeBN{" +
                "parents=" + name +
                '}';
    }
}
