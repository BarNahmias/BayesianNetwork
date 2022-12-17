import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CPT {
    String name;
    HashMap<ArrayList<String>, Double> cpt  = new HashMap<>();

//    NodeBN node;
    public CPT(HashMap<ArrayList<String>, Double> cpt, String name) {
        this.cpt = cpt;
        this.name = name;
    }

//    copy constructor

    public CPT(CPT other){
        this.name = other.name;
        for (ArrayList<String> row:other.cpt.keySet()) {
            this.cpt.put(row,other.cpt.get(row));
        }
    }
//    constructor
    public CPT() {}

    public CPT(NodeBN nodeBN) {
        this.name = nodeBN.getName();

        HashMap<String, Integer> modolo;

        modolo = culculateModolo(nodeBN);

        for (int index = 0; index < nodeBN.getDefinition().size(); index++) {
            ArrayList<String> row = new ArrayList<>();


            if (!nodeBN.getParents().isEmpty()) {

                for (int i = nodeBN.getParents().size() - 1; i >= 0; i--) {

                    if (index % modolo.get(nodeBN.getParents().get(i).getName()) == 0) {
                        if (index != 0) {
                            nodeBN.getParents().get(i).addIndex();
                        }

                        if (nodeBN.getParents().get(i).getIndex() == nodeBN.getParents().get(i).getVariable().size()) {
                            nodeBN.getParents().get(i).zeroIndex();
                        }
                        String par = nodeBN.getParents().get(i).getName() + "=" + nodeBN.getParents().get(i).getVariable().get(nodeBN.getParents().get(i).getIndex());
                        row.add(par);
                    } else {
                        String par = nodeBN.getParents().get(i).getName() + "=" + nodeBN.getParents().get(i).getVariable().get(nodeBN.getParents().get(i).getIndex());
                        row.add(par);
                    }
                }
            }
            Collections.reverse(row);

            if (index % modolo.get(nodeBN.getName()) == 0) {
                if (index != 0) {
                    nodeBN.addIndex();
                }
                if (nodeBN.getIndex() >= nodeBN.getVariable().size()) {
                    nodeBN.zeroIndex();
                }
                String temp = nodeBN.getName() + "=" + nodeBN.getVariable().get(nodeBN.getIndex());
                row.add(temp);
            } else {
                String temp = nodeBN.getName() + "=" + nodeBN.getVariable().get(nodeBN.getIndex());
                row.add(temp);
            }


//            System.out.println(row.toString() + " : " + nodeBN.getDefinition().get(index));

            this.cpt.put(row, Double.valueOf(nodeBN.getDefinition().get(index)));
        }
        for (String node: nodeBN.getGraph().graph.keySet()
        ) {
            nodeBN.getGraph().graph.get(node).zeroIndex();
        }

    }



    //       this function are calculate the modolo off eich node in the true table;
    HashMap<String, Integer>   culculateModolo (NodeBN node)
    {
        HashMap<String, Integer> modolo = new HashMap<String, Integer>();
            int outcome =node.getVariable().size();
        for (int index = node.getParents().size()-1; index >= 0; index--) {
            modolo.put(node.getParents().get(index).getName(),outcome);
//            System.out.println("parent size :" +node.getParents().get(index).getName() +" mod "+ outcome);

            outcome*=node.getParents().get(index).getVariable().size();


        }
        modolo.put(node.getName(), (int) Math.pow(node.getVariable().size(), 0));

        return modolo;
    }

    public void printCpt() {
        System.out.println( this.name + " : FACTOR");
        for ( ArrayList <String> row:this.cpt.keySet() )
        {
            System.out.println(row.toString()+ " : "+this.cpt.get(row));

        }
    }

    @Override
    public String toString () {
        return "cpt{" +
                "cpt=" + cpt +
                ", name='" + name + '\'' +'}';
    }


}