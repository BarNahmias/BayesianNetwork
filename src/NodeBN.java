import java.util.ArrayList;

public class NodeBN {

    private  String name;
    private  ArrayList<String> variable;
    private ArrayList<String> definition;
    private ArrayList<NodeBN> parents = new ArrayList<NodeBN>();

    private String parentsNames;
    private CPT cpt;


     boolean empty = true;
    private int index = 0;
    private BNGraph graph;

    public int first= 0;

    public NodeBN(String name, ArrayList<String> variable, ArrayList<String> definition, ArrayList<NodeBN> parents, BNGraph graph) {
        this.name = name;
        this.variable = variable;
        this.definition = definition;
        this.parents = parents;
        this.graph = graph;

    }



    public NodeBN() {
        this.variable=new ArrayList<>();
        this.definition=new ArrayList<>();
        this.parents = new ArrayList<NodeBN>();
    }

    public String getParentsNames() {
        String name = "";
        for (NodeBN nodeBN:parents) {
            name = name +" "+ nodeBN.getName();
        }
        this.parentsNames = name;
        return parentsNames;
    }

    public void setParentsNames(String parentsNames) {
        this.parentsNames = parentsNames;
    }

    public BNGraph getGraph() {
        return graph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getVariable() {
        return variable;
    }

    public void setVariable(ArrayList<String> variable) {
        this.variable = variable;
    }

    public ArrayList<String> getDefinition() {
        return definition;
    }

    public void setDefinition(ArrayList<String> definition) {
        this.definition = definition;
    }

    public ArrayList<NodeBN> getParents() {
        return parents;
    }

    public boolean ifParentsIsEmpty() {
        return empty;
    }
    public void setParents(ArrayList<NodeBN> parents) {
        this.parents = parents;
    }

    public void setGraph(BNGraph graph) {
        this.graph = graph;
    }

    public int getIndex() {
        return index;
    }
    public void addIndex() {
        index++;
    }
    public void zeroIndex() {
        index=0;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public CPT getCpt() {
        CPT cpt1 =new CPT(this);
        return cpt1;
    }

    public void addFirst(){
        this.first++;
    }
    public void setCpt(CPT cpt) {
        this.cpt = cpt;
    }


}