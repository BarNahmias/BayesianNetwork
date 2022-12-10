import java.util.*;

public class Factor {
    public  String name;

    NodeBN myNode;
    public HashMap<ArrayList<String> ,Double> factor=new HashMap<>();
    BNGraph bnGraph;
    private  String queryString;

    private ArrayList<String> factorVars =new ArrayList<>();

    public Factor(){}

    public Factor(Factor other){

    }

    public Factor(NodeBN nodeBN){
        this.name = nodeBN.getName();
        for (ArrayList<String> row:nodeBN.getCpt().cpt.keySet()) {
            this.factor.put(row,nodeBN.getCpt().cpt.get(row));
            this.bnGraph=nodeBN.getGraph();
        }
        Iterator<ArrayList<String>> iteratorA = this.factor.keySet().iterator();
        factorVars.addAll(iteratorA.next());
        myNode=nodeBN;
    }

//    public Factor(NodeBN nodeBN,HashMap<ArrayList<String>,Double> newFactor){
//        for (ArrayList<String> row:newFactor.keySet()) {
//            nodeBN.getFactor().factor.put(row,newFactor.get(row));
//        }
//    }

    public Factor(NodeBN nodeBN,String query,BNGraph bnGraph){
        this.queryString=query;
        this.bnGraph=bnGraph;
        this.name = nodeBN.getName();
        for (ArrayList<String> row:nodeBN.getCpt().cpt.keySet()) {
            this.factor.put(row,nodeBN.getCpt().cpt.get(row));
        }
        myNode=nodeBN;

    }

    public Factor copyt (Factor other){
        Factor newFactor=new Factor(other);
        return newFactor;
    }

    public Factor copy (Factor other){
        this.queryString=other.queryString;
        this.bnGraph=other.bnGraph;
        this.name = other.getName();
        for (ArrayList<String> row:other.factor.keySet()) {
            this.factor.put(row,other.factor.get(row));
        }
        this.myNode=other.myNode;

        return this;
    }

    public Factor(NodeBN nodeBN,BNGraph bnGraph){
        this.bnGraph=bnGraph;
        this.name = nodeBN.getName();
        for (ArrayList<String> row:nodeBN.getCpt().cpt.keySet()) {
            this.factor.put(row,nodeBN.getCpt().cpt.get(row));
        }
        Iterator<ArrayList<String>> iteratorA = this.factor.keySet().iterator();
        factorVars.addAll(iteratorA.next());
        myNode=nodeBN;
    }



    public void printFactor() {
         System.out.println( this.name + " : FACTOR");
        for ( ArrayList <String> row:this.factor.keySet() )
        {
       System.out.println(row.toString()+ " : "+this.factor.get(row));

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Factor (HashMap<ArrayList<String>,Double> newFactor) {
        for (ArrayList<String> row:newFactor.keySet())
        {
            ArrayList<String> newRow= new ArrayList<>(row);
            this.factor.put(row,newFactor.get(row));
        }
        Iterator<ArrayList<String>> iteratorA = this.factor.keySet().iterator();
        factorVars.addAll(iteratorA.next());

    }


}
