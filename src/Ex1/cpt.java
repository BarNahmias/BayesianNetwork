package Ex1;

import java.util.ArrayList;
import java.util.HashMap;

public class cpt {

    HashMap<ArrayList<String>, Double> cpt;
    String name;

    public cpt(HashMap<ArrayList<String>, Double> cpt, String name) {
        this.cpt = cpt;
        this.name = name;
    }

    public cpt(NodeBN nodeBN) {
        this.name = nodeBN.getName() + "CPT";
        this.cpt = cratecpt(nodeBN);
    }


    public HashMap<ArrayList<String>, Double>  cratecpt(NodeBN nodeBN) {
        HashMap<ArrayList<String>, Double> cpt = new HashMap<>();

        for(int index = 0 ;index<nodeBN.getDefinition().getTABLE().size();index++) {
            ArrayList<String> row = new ArrayList<>();
            int dominsize = nodeBN.variable.getOUTCOME().size();
                String temp = nodeBN.variable.getNAME() +" = "  + nodeBN.variable.getOUTCOME().get(index % dominsize);


            for (int i = nodeBN.getParents().size()-1; i>=0 ;i--) {
                String par =  nodeBN.getParents().get(i).variable.getNAME() + " = " +  nodeBN.variable.getOUTCOME().get(((index+1)/(dominsize+1)) %
                        nodeBN.getParents().get(i).getVariable().getOUTCOME().size() )+ " " ;
                dominsize *=  nodeBN.getParents().get(i).getParents().size();
                row.add(temp +" | "+ par);
            }
            cpt.put(row , Double.valueOf(nodeBN.getDefinition().getTABLE().get(index)));
        }return cpt;
    }

    @Override
    public String toString() {
        return "cpt{" +
                "cpt=" + cpt +
                ", name='" + name + '\'' +
                '}';
    }
}

