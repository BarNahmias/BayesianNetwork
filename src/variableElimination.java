import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class variableElimination {

     private BNGraph bnGraph;
    private  String queryString;
    private HashMap<String,String> evidence =new HashMap<>();
    private HashMap<String,String> query =new HashMap<>();
    private  HashMap<String,String> algoindex =new HashMap<>();
    private ArrayList<String> hidden =new ArrayList<>();
    private ArrayList<String> listOfValuesFromQuery =new ArrayList<>();
    public   HashMap<String,String> allVariableFromQuery= new  HashMap<String,String>();

    public HashMap<NodeBN,Factor> allFactors=new HashMap<>();

    public   int addCounter = 0;
    public   int mullCounter = 0;

    public variableElimination(BNGraph bnGraph, String queryString, HashMap<String, String> evidence, HashMap<String, String> query, HashMap<String, String> algoindex, ArrayList<String> hidden, ArrayList<String> listOfValuesFromQuery, HashMap<String, String> allVariableFromQuery, int addCounter, int mullCounter) {
        this.bnGraph = bnGraph;
        this.queryString = queryString;
        this.evidence = evidence;
        this.query = query;
        this.algoindex = algoindex;
        this.hidden = hidden;
        this.listOfValuesFromQuery = listOfValuesFromQuery;
        this.allVariableFromQuery = allVariableFromQuery;
        this.addCounter = addCounter;
        this.mullCounter = mullCounter;
    }

    public variableElimination(){}

    public variableElimination(BNGraph bnGraph,String queryString){
        for (NodeBN node:bnGraph.graph.values())
        {
            Factor temp = new Factor(node , bnGraph);
            allFactors.put(node,temp);
        }
        this.queryString=queryString;
        this.bnGraph=bnGraph;
    }


    public  double variableElimination() throws Exception {
        getInfoFromQuery();

        HashMap<NodeBN,Factor> factorsList=new HashMap<>();
        System.out.println( "Evidence : "+evidence.keySet().toString());
        System.out.println( "Hideen : "+hidden.toString());
        System.out.println( "query : "+query.toString());
        System.out.println( "inout : "+queryString);

        for (NodeBN node:allFactors.keySet())
        {
//            allFactors.get(node).printFactor();
            eliminateEvidence(node);
//            allFactors.get(node).printFactor();

        }


        ArrayList<Factor> factors= new ArrayList<>();
        ArrayList<Factor> comHidden=new ArrayList<>();

        for (String hidd :hidden)
        {



            ArrayList<Factor> hiddenCommon=new ArrayList<>();

            for (Factor factor :allFactors.values())
            {
//                factor.printFactor();
//                System.out.println("hideen : "+hidd+ " Factor : "+factor.name+" | "+ factor.factor.keySet());
//                System.out.println(factor.factor.containsKey(hidd));
                if(factor.factor.toString().contains(hidd))
                {
                    hiddenCommon.add(factor);
                    comHidden.add(factor);
                }
            }

//            לעדכן כאן את השינוי של כל פאקטור בהיידן

            if(!hiddenCommon.isEmpty()){
            Factor nameHideen =hiddenCommon.get(hiddenCommon.size()-1);
                for (Factor temp:allFactors.values())
                {
//                    if(temp)
                }

//            for (int i = 0; i <hiddenCommon.size()-1 ; i++)
//            {
//               hiddenCommon.set(i+1,finalJoin(hiddenCommon.get(i),hiddenCommon.get(i+1)));
////                hiddenCommon.get(i).printFactor();
//            }
             eliminateHidden(bnGraph.graph.get(nameHideen.name));
//            System.out.println( "Factor : ");
//            allFactors.get(nameHideen).printFactor();
            }
        }

        for (NodeBN node:allFactors.keySet())
        {
             factors.add(allFactors.get(node));
        }

//        factors.sort( );
        for (int i = 0; i <factors.size()-1 ; i++)
        {
            factors.set(i+1,finalJoin(factors.get(i),factors.get(i+1)));
        }

//        factors.get(factors.size()-1).printFactor();

        for (NodeBN node:allFactors.keySet())
        {
//            System.out.println( "Factor : ");
//            node.getFactor().printFactor();
        }
        return 0;
    }

    //    deep copy
    public void updateFactor(Factor a,HashMap<ArrayList<String>,Double> newFactor) {
        for (ArrayList<String> row:newFactor.keySet())
        {
            ArrayList<String> newRow= new ArrayList<>(row);
           a.factor.put(row,newFactor.get(row));
        }
    }

//put hideen name
    public  Factor eliminateEvidence(NodeBN node) throws Exception {
//        getInfoFromQuery();
//        System.out.println("SS  : " +queryString);
//        System.out.println("ev  : " +evidence.keySet());
//
//        System.out.println("node.getName()  : " +node.getName());

        Factor ans = new Factor();

        if(!node.getParents().isEmpty()) {
            for (NodeBN parent : node.getParents()) {
                if (evidence.containsKey(parent.getName())) {
                    HashMap<ArrayList<String>, Double> nweFactor = new HashMap<>();
                    String curr = parent.getName() + "=" + evidence.get(parent.getName());
                    for (ArrayList<String> row : allFactors.get(node).factor.keySet()) {
                        ArrayList<String> newRow = new ArrayList<>();
                        if (row.toString().contains(curr)) {
                            newRow.addAll(row);
                            nweFactor.put(newRow, allFactors.get(node).factor.get(row));
                        }
                    }

                    Factor temp = new Factor(nweFactor);
                    temp.name = node.getName();
                    temp.myNode=node;

                    allFactors.replace(node, temp);
                    ans.copy(temp);
                }

            }

        }
        if (evidence.containsKey(node.getName()))
        {
            HashMap<ArrayList<String> ,Double> nweFac= new HashMap<>();
//            System.out.println("node.getName()  : " +node.getName());

            String curr =node.getName()+"="+ evidence.get(node.getName());
//            System.out.println("curr: " +curr);

            for (ArrayList<String> row : allFactors.get(node).factor.keySet())
            {
                ArrayList<String> newRow=new ArrayList<>();

                if (row.contains(curr))
                {
                    newRow.addAll(row);
                    nweFac.put(newRow, allFactors.get(node).factor.get(row));
//                    System.out.println("row: " +row);
                }
            }

//            print row
//            for (ArrayList<String> row : nweFac.keySet()) {
//                System.out.println("row new: " +row +" : " + nweFac.get(row));
//
//            }
            Factor temp = new Factor(nweFac);
            temp.name=node.getName();
            temp.myNode=node;

//            temp.printFactor();
            allFactors.replace(node,temp);
            ans.copy(temp);
        }

        HashMap<ArrayList<String>, Double> nweFactor = new HashMap<>();
                    for (ArrayList<String> row : allFactors.get(node).factor.keySet()) {
                        ArrayList<String> rowFactor = new ArrayList<>();
                        for (int i = 0; i < row.size(); i++) {
                            if (!evidence.containsKey(row.get(i).split("=")[0])){
                                rowFactor.add(row.get(i));
                            }

                        }
                        if (nweFactor.get(rowFactor) == null) {
                            nweFactor.put(rowFactor, allFactors.get(node).factor.get(row));
                        } else
                        {
                            nweFactor.replace(rowFactor,  allFactors.get(node).factor.get(row) + nweFactor.get(rowFactor));
                        }
                    }
        Factor temp = new Factor(nweFactor);
        temp.name=node.getName();
        temp.myNode=node;

//            temp.printFactor();
        allFactors.replace(node,temp);




//        System.out.println("ans : "+ans.factor.keySet().toString() );
        return allFactors.get(node);
    }


    public Factor eliminateHidden(NodeBN node) throws Exception {
//        getInfoFromQuery();
//        System.out.println("queryString  : " +queryString);
//        System.out.println("hidden  : " +hidden.toString());
//        System.out.println("node  : " +node.getName().toString());

        if(!node.getParents().isEmpty()){
        HashMap<ArrayList<String>, Double> nweFactor = new HashMap<>();
        for (NodeBN parent : node.getParents()) {
            if (hidden.contains(parent.getName())) {

                for (ArrayList<String> row : allFactors.get(node).factor.keySet()) {
                    ArrayList<String> rowFactor = new ArrayList<>();
                    for (int i = 0; i < row.size(); i++) {
                        if (!row.get(i).contains(parent.getName())) {
                            rowFactor.add(row.get(i));
                        }

                    }
                    if (nweFactor.get(rowFactor) == null) {
                        nweFactor.put(rowFactor, allFactors.get(node).factor.get(row));
                    } else
                    {
                        nweFactor.replace(rowFactor,  allFactors.get(node).factor.get(row) + nweFactor.get(rowFactor));
                    }
                }
            }

        }

        Factor newD=new Factor(nweFactor);
        newD.name=node.getName();
        newD.myNode=node;

//        newD.printFactor();
        allFactors.replace(node,newD);}

        HashMap<ArrayList<String>, Double> nweFact = new HashMap<>();
        if (hidden.contains(node.getName())) {

            for (ArrayList<String> row : allFactors.get(node).factor.keySet()) {
                ArrayList<String> rowFactor = new ArrayList<>();
                for (int i = 0; i < row.size(); i++) {
                    if (!row.get(i).contains(node.getName())) {
                        rowFactor.add(row.get(i));
                    }

                }
                if (nweFact.get(rowFactor) == null) {
                    nweFact.put(rowFactor, allFactors.get(node).factor.get(row));
                } else
                {
                    nweFact.replace(rowFactor,  allFactors.get(node).factor.get(row) + nweFact.get(rowFactor));
                }
            }
            Factor newF=new Factor(nweFact);
            newF.name=node.getName();
            newF.myNode=node;
//        newD.printFactor();
            allFactors.replace(node,newF);
        }
//                System.out.println("node  : " +node.getName() +" - "+ nweFactor.keySet().toString());





        return  allFactors.get(node);
    }

    public Factor finalJoin( Factor a ,Factor b) throws Exception {
        HashMap<ArrayList<String>, Double> nweFactor = new HashMap<>();
        System.out.println("A  : " );
        a.printFactor();
        System.out.println("B  : " );
        b.printFactor();

        ArrayList<String> com =CommonVar(a ,b);
        ArrayList<String> comVar =new ArrayList<>();

        if(com.isEmpty())
        {
            for (ArrayList<String> rowA : a.factor.keySet())
            {
                ArrayList<String> tempRow = new ArrayList<>();
                for (ArrayList<String> rowB : b.factor.keySet()) {
                    tempRow.clear();
                    tempRow.addAll(rowA);
                    tempRow.addAll(rowB);
                    nweFactor.put(tempRow,a.factor.get(rowA)*b.factor.get(rowB));
                }

            }
            allFactors.remove(bnGraph.graph.get(a.name));
            Factor temp = new Factor(nweFactor);
            temp.name=b.name;
            temp.myNode=b.myNode;
            allFactors.replace(b.myNode,temp);
        }
        else {
            for (ArrayList<String> rowA : a.factor.keySet()) {
                    for (ArrayList<String> rowB : b.factor.keySet()) {
                        ArrayList<String> tempRow = new ArrayList<>();
                        if (matchKey(rowA, rowB)) {
//                   System.out.println("rowA  : " +" | "+rowA+" --- " + a.factor.get(rowA) + " rowB  : " + rowB +" | "+b.factor.get(rowB)+" :: " + (a.factor.get(rowA)*b.factor.get(rowB)));

                            for (String varA : rowA) {
                                tempRow.add(varA);
                            }
                            for (String varB : rowB) {
                                if (!tempRow.contains(varB)) {
                                    tempRow.add(varB);
                                }
                            }
                            nweFactor.put(tempRow, (a.factor.get(rowA) * b.factor.get(rowB)));
                        }
                    }
            }
            allFactors.remove(bnGraph.graph.get(a.name));
            Factor temp = new Factor(nweFactor);
            temp.name = b.name;
            temp.myNode=b.myNode;
            allFactors.replace(b.myNode, temp);
            System.out.println("C  : j ");
            allFactors.get(b.myNode).printFactor();
        }

        return  allFactors.get(b.myNode);
    }

    public ArrayList<String> findCommonVar( NodeBN a ,NodeBN b){
        ArrayList<String> commons = new ArrayList<>();
        if (!a.getParents().isEmpty()){
        for (NodeBN varA:a.getParents())
        {
            if(!b.getParents().isEmpty()){
            for (NodeBN varB:b.getParents())
            {
                if(Objects.equals(varA, varB))
                {
                    commons.add(varA.getName());
                }
            }
            }
        }
        }
        return commons;
    }



    public ArrayList<String> CommonVar( Factor a ,Factor b){
        ArrayList<String> commons = new ArrayList<>();

        Iterator<ArrayList<String>> iteratorA = a.factor.keySet().iterator();
        ArrayList<String> keyA = null;
        if(iteratorA.hasNext()){
            keyA = iteratorA.next();
        }

        Iterator<ArrayList<String>> iteratorB = b.factor.keySet().iterator();
        ArrayList<String> keyB = null;
        if(iteratorB.hasNext()){
            keyB = iteratorB.next();
        }

        assert keyB != null;
        for (String var:keyB)
        {
            assert keyA != null;
            if(keyA.contains(var)){

                commons.add(var);}
        }


        return commons;
    }


    public ArrayList<String> findCommon( ArrayList<String> a ,ArrayList<String> b){
        ArrayList<String> commons = new ArrayList<>();
        for (String varA:a)
        {
            for (String varB:b)
            {
                if(Objects.equals(varA, varB))
                {
                    commons.add(varA);
                }
            }
        }
        return commons;
    }

    public boolean matchKey( ArrayList<String> a ,ArrayList<String> b){
     boolean flag=true;
        for (String varA:a)
        {
            if (!b.contains(varA)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    //    getInfoFromQuery are get query input (string)  exam :  " P(B=T|J=T,M=T),2 "
    //    Finds all variables  and assigns to the appropriate fields
    public  void getInfoFromQuery() throws Exception {
        this.query.clear();
        this.evidence.clear();
        this.hidden.clear();
        this.listOfValuesFromQuery.clear();
        String row = this.queryString;
        Character alg =row.charAt(row.length()-1);
        //        add algo number
        this.algoindex.put("algo",alg.toString()) ;
        String[] sm = row.split("\\(")[1].split("\\)")[0].split("\\|")[1].split(",");
        //        add query variable
        this.query.put(row.split("\\(")[1].split("=")[0],row.split("=")[1].split("\\|")[0]);
        this.allVariableFromQuery.put(row.split("\\(")[1].split("=")[0],row.split("=")[1].split("\\|")[0]);
        this.listOfValuesFromQuery.add(row.split("\\(")[1].split("=")[0]);

        //        add evidence variable

        if (sm.length>=1)
        {
            for (int i = 0 ;i<sm.length;i++)
            {
                evidence.put(sm[i].split("=")[0],sm[i].split("=")[1]);
                this.allVariableFromQuery.put(sm[i].split("=")[0],sm[i].split("=")[1]);
                this.listOfValuesFromQuery.add(sm[i].split("=")[0]);
            }
        }
        this.listOfValuesFromQuery.add(alg.toString());
        findHidden();
    }

    public void  findHidden(){
        for (String str : this.bnGraph.graph.keySet() )
        {
            if(!this.listOfValuesFromQuery.contains(str))
            {
                this.hidden.add(str);
            }
        }
    }

    public  ArrayList <String> findMyParents(String nodeName){
        ArrayList <String> myParents = new ArrayList<>();


        for (NodeBN node: bnGraph.graph.get(nodeName).getParents()) {
            if (!myParents.contains(node.getName())) {

                for (String variable : this.allVariableFromQuery.keySet()) {
                    if (variable.equals(node.getName()) && (!variable.equals(nodeName))) {
                        myParents.add(node.getName() + "=" + this.allVariableFromQuery.get(variable));

                    }
                }

            }
        }

//        Collections.reverse(myParents);
        return myParents;
    }




}
