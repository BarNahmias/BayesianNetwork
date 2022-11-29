import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CPT {
    String name;
    HashMap<ArrayList<String>, Double> cpt  = new HashMap<>();

        public CPT(HashMap<ArrayList<String>, Double> cpt, String name) {
            this.cpt = cpt;
            this.name = name;
        }
        public CPT() {}
        public CPT(NodeBN nodeBN) {
            this.name = nodeBN.getName();

            HashMap<String, Integer> modolo;

            modolo = culculateModolo(nodeBN);

            for (int index = 0; index < nodeBN.getDefinition().size(); index++) {
                ArrayList<String> row = new ArrayList<>();

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
//                System.out.println(row.toString());

                this.cpt.put(row, Double.valueOf(nodeBN.getDefinition().get(index)));
            }
        }

//            public HashMap<ArrayList<String>, Double> crateCpt (NodeBN nodeBN){
//                HashMap<ArrayList<String>, Double> cpt = new HashMap<>();
//                HashMap<String, Integer> modolo;
//                modolo = culculateModolo(nodeBN);
//                for (int index = 0; index < nodeBN.getDefinition().size(); index++) {
//                    ArrayList<String> row = new ArrayList<>();
//
//                    if (index % modolo.get(nodeBN.getName()) == 0) {
//                        if (index != 0) {
//                            nodeBN.addIndex();
//                        }
//                        if (nodeBN.getIndex() >= nodeBN.getVariable().size()) {
//                            nodeBN.zeroIndex();
//                        }
//                        String temp = nodeBN.getName() + " = " + nodeBN.getVariable().get(nodeBN.getIndex());
//                        row.add(temp);
//                    } else {
//                        String temp = nodeBN.getName() + " = " + nodeBN.getVariable().get(nodeBN.getIndex());
//                        row.add(temp);
//                    }
//
//                    if (!nodeBN.getParents().isEmpty()) {
//
//                        for (int i = nodeBN.getParents().size() - 1; i >= 0; i--) {
//
//                            if (index % modolo.get(nodeBN.getParents().get(i).getName()) == 0) {
//                                if (index != 0) {
//                                    nodeBN.getParents().get(i).addIndex();
//                                }
//
//                                if (nodeBN.getParents().get(i).getIndex() == nodeBN.getParents().get(i).getVariable().size()) {
//                                    nodeBN.getParents().get(i).zeroIndex();
//                                }
//                                String par = nodeBN.getParents().get(i).getName() + " = " + nodeBN.getParents().get(i).getVariable().get(nodeBN.getParents().get(i).getIndex());
//                                row.add(par);
//                            } else {
//                                String par = nodeBN.getParents().get(i).getName() + " = " + nodeBN.getParents().get(i).getVariable().get(nodeBN.getParents().get(i).getIndex());
//                                row.add(par);
//                            }
//                        }
//                    }
////                System.out.println(row.toString());
//
//                    cpt.put(row, Double.valueOf(nodeBN.getDefinition().get(index)));
//                }
//                return cpt;
//            }

//       this function are  adding  arr a into arr  b;
            public static void addArray (ArrayList < NodeBN > a, ArrayList < NodeBN > b){
                for (NodeBN no : a) {
                    b.add(no);
                }
            }

//       this function are calculate the modolo off eich node in the true table;
         HashMap<String, Integer>   culculateModolo (NodeBN node)
            {
                HashMap<String, Integer> modolo = new HashMap<String, Integer>();
                modolo.put(node.getName(), (int) Math.pow(node.getVariable().size(), node.getParents().size()));

                for (int index = 0; index < node.getParents().size(); index++) {
                    modolo.put(node.getParents().get(index).getName(), (int) Math.pow(node.getParents().get(index).getVariable().size(), index));
                }
                return modolo;
            }


            @Override
            public String toString () {
                return "cpt{" +
                        "cpt=" + cpt +
                        ", name='" + name + '\'' +
                        '}';
            }


        }

