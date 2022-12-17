import java.util.*;

public class Factor implements  Comparable<Factor>{
    public  String name;

    NodeBN myNode = new NodeBN();
    public HashMap<ArrayList<String> ,Double> factor=new HashMap<>();

    public int size;
    public String mySize;

    BNGraph bnGraph =new BNGraph();
    public  String queryString = new String();

    public ArrayList<String> factorVars =new ArrayList<>();

    public Factor(){
    }


    public Factor(NodeBN nodeBN){
        this.name = nodeBN.getName();
        for (ArrayList<String> row:nodeBN.getCpt().cpt.keySet()) {
            this.factor.put(row,nodeBN.getCpt().cpt.get(row));
            this.bnGraph=nodeBN.getGraph();
        }
        Iterator<ArrayList<String>> iteratorA = this.factor.keySet().iterator();
        factorVars.addAll(iteratorA.next());
        this.size=this.factor.size()*factorVars.size();
        this.mySize=String.valueOf(size);

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
        this.size=this.factor.size()*factorVars.size();
        this.mySize=String.valueOf(size);

    }



    public Factor copy (Factor other){
        this.queryString=other.queryString;
        this.bnGraph=other.bnGraph;
        this.name = other.getName();
        this.myNode=other.myNode;
        this.factor.clear();
        this.factorVars=other.factorVars;
        for (ArrayList<String> row:other.factor.keySet()) {
            this.factor.put(row,other.factor.get(row));
        }
        this.size=this.factor.size()*factorVars.size();
        this.mySize=String.valueOf(size);
        this.myNode.ancestors=other.myNode.ancestors;
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
        this.size=this.factor.size()*factorVars.size();
        this.mySize=String.valueOf(size);

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
        this.size=this.factor.size();
        this.mySize=String.valueOf(size);

    }







    public ArrayList<String> getFactorVars() {
        return factorVars;
    }

    public void setFactorVars(ArrayList<String> factorVars) {
        this.factorVars = factorVars;
    }

    @Override
    public int compareTo(Factor f2) {
        if (this.size > f2.size)
            return 1;
        else if (this.size < f2.size)
            return -1;
        else
            return this.getName().compareTo(f2.getName());
    }




}
