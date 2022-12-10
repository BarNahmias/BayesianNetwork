import java.util.ArrayList;
import java.util.HashMap;

public class NodeBN {
    private  String name;
    private  ArrayList<String> variable;
    private ArrayList<String> definition;
    private ArrayList<NodeBN> parents = new ArrayList<NodeBN>();

    private String parentsNames;
    private CPT cpt;
    public   Factor factor;
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

    public void setFactor(Factor other) {
        this.factor=new Factor();
        for (ArrayList<String> row:other.factor.keySet())
        {
            ArrayList<String> temp = new ArrayList<>();
            for (String var:row)
            {
                temp.add(var);
            }
            this.factor.factor.put(temp,other.factor.get(row));
        }
    }

    public void setDeepFactor(HashMap<ArrayList<String>,Double> other) {
        for (ArrayList<String> row:other.keySet())
        {
            ArrayList<String> temp = new ArrayList<>();
            for (String var:row)
            {
                temp.add(var);
            }
            this.factor.factor.put(temp,other.get(row));
        }
    }

    public  void eliminateEvidenceNode(HashMap<String,String> evidence) throws Exception {
//        getInfoFromQuery();
        Factor ans = new Factor();

        for (NodeBN parent:this.getParents())
        {
            Factor temp = new Factor();
            if (evidence.containsKey(parent.getName()))
            {
                HashMap<ArrayList<String> ,Double> nweFactor= new HashMap<>();

                String curr =parent.getName()+"="+ evidence.get(parent.getName());
                for (ArrayList<String> row : this.getFactor().factor.keySet())
                {
                    ArrayList<String> newRow=new ArrayList<>();
                    if (row.toString().contains(curr))
                    {
                        newRow.addAll(row);
                        temp.factor.put(newRow, this.getFactor().factor.get(row));
                    }
                }
                this.setFactor(temp);
                this.getFactor().name=this.getName();
            }

        }


        if (evidence.containsKey(this.getName()))
        {
            HashMap<ArrayList<String> ,Double> nweFac= new HashMap<>();
            System.out.println("node.getName()  : " +this.getName());

            String curr =this.getName()+"="+ evidence.get(this.getName());
            System.out.println("curr: " +curr);

            Factor temp = new Factor();

            for (ArrayList<String> row : this.getFactor().factor.keySet())
            {
                ArrayList<String> newRow=new ArrayList<>();

                if (row.contains(curr))
                {
                    newRow.addAll(row);
                    temp.factor.put(newRow, this.getFactor().factor.get(row));
//                    System.out.println("row: " +row);
                }
            }

//            print row
            for (ArrayList<String> row : nweFac.keySet()) {
                System.out.println("row new: " +row +" : " + nweFac.get(row));

            }


            System.out.println("temp : " );
            temp.name=this.getName();
            this.setFactor(temp);
//            node.setDeepFactor(nweFac);
            this.getFactor().printFactor();

        }

//        return this.getFactor();
    }


//    public boolean comper(NodeBN outher){
//
//
//
//        return this.name.>outher.name;
//    }


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

    public void resetFactor() {
        this.factor.factor.clear();
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

    public Factor getFactor() {
        Factor factor1 =new Factor(this);
        return factor1;
    }

    public void addFirst(){
        this.first++;
    }
    public void setCpt(CPT cpt) {
        this.cpt = cpt;
    }


}