import java.text.DecimalFormat;
import java.util.*;

public class variableElimination {

     private BNGraph bnGraph;
    private  String queryString;
    private HashMap<String,String> evidence =new HashMap<>();
    private HashMap<String,String> query =new HashMap<>();
    private  HashMap<String,String> algoindex =new HashMap<>();
    private ArrayList<String> hidden =new ArrayList<>();
    private ArrayList<String> listOfValuesFromQuery =new ArrayList<>();

    private ArrayList<Factor> listOfFactor =new ArrayList<>();

    public   HashMap<String,String> allVariableFromQuery= new  HashMap<String,String>();

    public HashMap<NodeBN,Factor> allFactors=new HashMap<>();

    public Set<Factor> hash_Set = new HashSet<Factor>();



    public   int addCounter = 0;
    public   int mullCounter = 0;
    public variableElimination(){}

    public variableElimination(BNGraph bnGraph,String queryString) throws Exception {

        for (NodeBN node:bnGraph.graph.values())
        {
            Factor temp = new Factor(node , bnGraph);
                allFactors.put(node,temp);
        }
        this.queryString=queryString;
        this.bnGraph=bnGraph;
    }


    public  String variableElimination() throws Exception {
        getInfoFromQuery();
//        System.out.println( "inout : "+queryString);
//        System.out.println( "Evidence : "+evidence.keySet().toString());
//        System.out.println( "Hideen : "+hidden.toString());
//        System.out.println( "query : "+query.toString());

        double inCpt =findQueryInCpt(queryString);
        if(inCpt!=-1)
        {
            return String.format("%.5g", inCpt);

        }

        listOfFactor.addAll(allFactors.values());
        hidden.sort(String::compareTo);
        for (Factor factor:listOfFactor)
        {
            eliminateEvidence(allFactors.get(factor.myNode));

        }


        ArrayList<String> relevanHiddens = new ArrayList<>();
        for (String hidd :hidden)
        {
            if(!relevantHidden(hidd))
            {
                relevanHiddens.add(hidd);
            }
        }

        for (String badHidden:relevanHiddens)
        {
            removeHidden(badHidden);
        }

        for (Factor factor:hash_Set)
        {
                allFactors.remove(factor.myNode,allFactors.get(factor.myNode));
        }


        relevanHiddens.clear();

        for (String hidden :hidden)
        {
            if(relevantHidden(hidden))
            {
                relevanHiddens.add(hidden);
            }
        }

        relevanHiddens.sort(String::compareTo);

//        hidden.sort(String::compareTo);
        for (String hidd :relevanHiddens) {
            Factor temp = new Factor();
            temp.name="temp";

            PriorityQueue<Factor> queue = new PriorityQueue<Factor>(Factor::compareTo);

            for (Factor currFactor:allFactors.values())
            {
                if (currFactor.factor.size() > 1)
                {
                    if (currFactor.getFactorVars().toString().contains(hidd))
                    {
                        queue.add(currFactor);
                    }
                }
            }

            while (queue.size()>1)
            {

//                System.out.println( "----first factor--- ");

                Factor first = new Factor();
                first.copy(Objects.requireNonNull(queue.poll()));
//                first.printFactor();

//                System.out.println( "----second factor--- ");
                Factor second = new Factor();
                second.copy(Objects.requireNonNull(queue.poll()));
//                second.printFactor();

                allFactors.remove(first.myNode,first);
                allFactors.remove(second.myNode,second);

                temp.copy(Join(first,second));
                queue.add(temp);

//                System.out.println( "----new factor--- ");
                assert queue.peek() != null;
//                queue.peek().printFactor();
//                System.out.println("add : "+ addCounter+" mull   : "+mullCounter );

            }
//            System.out.println( "new factor after all join and elimation" );
//            allFactors.get(queue.peek().myNode).printFactor();

            assert queue.peek() != null;
            allFactors.put(queue.peek().myNode, eliminateHidden(queue.peek(),hidd));
            assert queue.peek() != null;

        }


        ArrayList<Factor> listOfallFactors =new ArrayList<>();
        Factor temp = new Factor();
        for (Factor currFactor:allFactors.values())
        {
            if (currFactor.factor.size() > 1) {
            listOfallFactors.add(currFactor);}
        }

        listOfallFactors.sort(Factor::compareTo);


        for (int i = 0; i < listOfallFactors.size(); i++)
        {

//            System.out.println( "final" );
//            listOfallFactors.get(i).printFactor();

                temp.copy(Join(temp, listOfallFactors.get(i)));
                allFactors.remove(listOfallFactors.get(i).myNode,listOfallFactors.get(i));
        }

        allFactors.put(temp.myNode, temp);

        double numerator =0.0;
        double denominator = 0.0;

        for (ArrayList<String> row : temp.factor.keySet())
        {
            if(row.contains(query.toString().split("\\{")[1].split("\\}")[0])){
                numerator=numerator+temp.factor.get(row);
            }
            denominator=denominator+temp.factor.get(row);
            addCounter++;
        }
        addCounter--;


        return String.format("%.5g",(numerator/denominator));


    }

//    public  String variable() throws Exception {
//        getInfoFromQuery();
////        System.out.println( "inout : "+queryString);
////        System.out.println( "Evidence : "+evidence.keySet().toString());
////        System.out.println( "Hideen : "+hidden.toString());
////        System.out.println( "query : "+query.toString());
//
//        double inCpt =findQueryInCpt(queryString);
//        if(inCpt!=-1)
//        {
//            return String.format("%.5g", inCpt);
//
//        }
//
//        listOfFactor.addAll(allFactors.values());
//        hidden.sort(String::compareTo);
//        for (Factor factor:listOfFactor)
//        {
//            eliminateEvidence(allFactors.get(factor.myNode));
//
//        }
//
//
//        ArrayList<String> relevanHiddens = new ArrayList<>();
//        for (String hidd :hidden)
//        {
//            if(!relevantHidden(hidd))
//            {
//                relevanHiddens.add(hidd);
//            }
//        }
//
//        for (String badHidden:relevanHiddens)
//        {
//            removeHidden(badHidden);
//        }
//
//        for (Factor factor:hash_Set)
//        {
//            allFactors.remove(factor.myNode,allFactors.get(factor.myNode));
//        }
//
//
//        relevanHiddens.clear();
//
//        for (String hidden :hidden)
//        {
//            if(relevantHidden(hidden))
//            {
//                relevanHiddens.add(hidden);
//            }
//        }
//
//        relevanHiddens.sort(String::compareTo);
//
////        hidden.sort(String::compareTo);
//        for (String hidd :relevanHiddens) {
//            Factor temp = new Factor();
//            temp.name="temp";
//
//           ArrayList<Factor> queue = new ArrayList<Factor>();
//
//            for (Factor currFactor:allFactors.values())
//            {
//                if (currFactor.factor.size() > 1)
//                {
//                    if (currFactor.getFactorVars().toString().contains(hidd))
//                    {
//                        queue.add(currFactor);
//                    }
//                }
//            }
//
//
//
//            while (queue.size()>1)
//            {
//                System.out.println( "----queue.size()--- " +queue.size());
//                System.out.println( "----queue--- " +queue.get(0).getName() +" " +queue.get(1).getName());
//
//                Factor first = new Factor();
//                ArrayList<NodeBN> towFactorToJoin =new ArrayList<>(findMaxCommonVar(queue));
//                System.out.println( "----towFactorToJoin--- " +towFactorToJoin.get(0).getName() +" " +towFactorToJoin.get(1).getName());
//
//                first.copy(allFactors.get(towFactorToJoin.get(0)));
//                Factor second = new Factor();
//                second.copy(allFactors.get(towFactorToJoin.get(1)));
//
//                System.out.println( "----first factor--- ");
//
//                first.printFactor();
//
//                System.out.println( "----second factor--- ");
//                second.printFactor();
//
//                allFactors.remove(first.myNode,first);
//                allFactors.remove(second.myNode,second);
////remove object from q, not from max comm
//                queue.remove(allFactors.get(towFactorToJoin.get(1)));
//                queue.remove(allFactors.get(towFactorToJoin.get(0)));
//                temp.copy(Join(first,second));
//                queue.add(temp);
////                allFactors.put(temp.myNode,temp);
//
//                System.out.println( "----new factor--- ");
//                temp.printFactor();
////                queue.peek().printFactor();
////                System.out.println("add : "+ addCounter+" mull   : "+mullCounter );
//
//            }
////            System.out.println( "new factor after all join and elimation" );
////            allFactors.get(queue.peek().myNode).printFactor();
//
//            allFactors.put(temp.myNode, eliminateHidden(temp,hidd));
//
//        }

//
//        ArrayList<Factor> listOfallFactors =new ArrayList<>();
//        Factor temp = new Factor();
//        for (Factor currFactor:allFactors.values())
//        {
//            if (currFactor.factor.size() > 1) {
//                listOfallFactors.add(currFactor);}
//        }
//
//        listOfallFactors.sort(Factor::compareTo);
//
//
//        for (int i = 0; i < listOfallFactors.size(); i++)
//        {
//
////            System.out.println( "final" );
////            listOfallFactors.get(i).printFactor();
//
//            temp.copy(Join(temp, listOfallFactors.get(i)));
//            allFactors.remove(listOfallFactors.get(i).myNode,listOfallFactors.get(i));
//        }
//
//        allFactors.put(temp.myNode, temp);
//
//        double numerator =0.0;
//        double denominator = 0.0;
//
//        for (ArrayList<String> row : temp.factor.keySet())
//        {
//            if(row.contains(query.toString().split("\\{")[1].split("\\}")[0])){
//                numerator=numerator+temp.factor.get(row);
//            }
//            denominator=denominator+temp.factor.get(row);
//            addCounter++;
//        }
//        addCounter--;
//
//
//        return String.format("%.5g",(numerator/denominator));
//
//
//    }
    public boolean relevantHidden(String hidden){

        for (Factor factor:allFactors.values())
        {
//            System.out.println( "factor " + factor.name + " ancestors "   + factor.myNode.ancestors );

            if (factor.myNode.ancestors.contains(hidden) &&
                    ((evidence.keySet().toString().contains(factor.getName()) || query.containsKey(factor.getName())))) {

                return true;
            }
        }
        return false;
    }

    public void removeHidden(String hidden)
    {
        for (Factor factor:allFactors.values())
        {
            if(factor.myNode.getParentsNames().contains(hidden)||factor.name.equals(hidden))
            {
                hash_Set.add(factor);
            }
        }
    }



//    public ArrayList<NodeBN> findMaxCommonVar(ArrayList<Factor> factors){
//        ArrayList<NodeBN> maxCommonVar =new ArrayList<>();
//        int max= 0;
//
////
//                for (int i = 0; i <factors.size() ; i++) {
//                    for (int j = i+1; j <factors.size() ; j++) {
//
//                    int curr=findCommon(factors.get(i).factorVars,factors.get(j).factorVars).size();
//                    if (curr>max){
//                        max=curr;
//                        maxCommonVar.clear();
//                        maxCommonVar.add(factors.get(i).myNode);
//                        maxCommonVar.add(factors.get(j).myNode);
//                    }
//                }
//
//            }
//
////                    System.out.println( "coom " + maxCommonVar.get(0).name + "  " +maxCommonVar.get(1).name );
//
//        return maxCommonVar;
//    }



    public  Factor eliminateEvidence(Factor factor) throws Exception {

        ArrayList<String> ev = new ArrayList<>(evidence.keySet());
        ArrayList<String> comev = new ArrayList<>(findCommon(factorVar(factor) ,ev));
        ev.sort(String::compareTo);
//        System.out.println("ev  : " +ev);
//        System.out.println("comev  : " +comev + "factor name : " +factor.name);

        ArrayList<String> allvar = new ArrayList<>();

        for (String comEvi:comev)
        {
            String evi = comEvi + "=" + this.evidence.get(comEvi);
            allvar.add(evi);
        }

        if(!comev.isEmpty()) {
            HashMap<ArrayList<String>, Double> newFactor = new HashMap<>();
            for (ArrayList<String> row : factor.factor.keySet()) {
                ArrayList<String> newRow = new ArrayList<>();
                boolean flag = true;
                for (String com : allvar) {
                    if (!row.toString().contains(com)) {
                        flag = false;
//                                System.out.println("flag  : " + flag);

                    }
                }

                if (flag) {
                    newRow.clear();
//                    System.out.println("row  : " + row);
                    for (String var : row) {
                        if (!allvar.contains(var)) {
                            newRow.add(var);
                        }
                    }

                    if (newFactor.get(newRow) == null) {
                        newFactor.put(newRow, factor.factor.get(row));

                    } else {
                        newFactor.replace(newRow, factor.factor.get(row) + newFactor.get(newRow));
                        addCounter++;
                    }
                }

            }
            Factor temp = new Factor(newFactor);
            temp.name = factor.getName();
            temp.myNode = factor.myNode;
            temp.setFactorVars(factorVar(factor));
            allFactors.remove(factor.myNode, factor);
            allFactors.put(temp.myNode, temp);
        }

        else
        {
            return  allFactors.get(factor.myNode);
        }

        return allFactors.get(factor.myNode);

    }
    public Factor eliminateHidden(Factor factor ,String hidden) throws Exception {
//        getInfoFromQuery();
//        System.out.println("queryString  : " +queryString);
//        System.out.println("hidden  : " +hidden.toString());
//        System.out.println("node  : " +node.getName().toString());

        ArrayList<String> factorVar=new ArrayList<>(factorVar(factor));

        HashMap<ArrayList<String>, Double> nweFact = new HashMap<>();
//
//                System.out.println("factor  : " +factor.getName() +" - "+ factorVar + " : " +hidden);
//        System.out.println(factorVar.contains(hidden));

        if(factorVar.contains(hidden)){

            for (ArrayList<String> row : allFactors.get(factor.myNode).factor.keySet()) {
                ArrayList<String> rowFactor = new ArrayList<>();
                for (int i = 0; i < row.size(); i++) {
                    if (!row.get(i).contains(hidden)) {
                        rowFactor.add(row.get(i));
                    }

                }
                if (nweFact.get(rowFactor) == null) {
                    nweFact.put(rowFactor, allFactors.get(factor.myNode).factor.get(row));
                } else
                {
                    nweFact.replace(rowFactor,  allFactors.get(factor.myNode).factor.get(row) + nweFact.get(rowFactor));
                    addCounter++;
                }
            }

            Factor newF=new Factor(nweFact);
            newF.setFactorVars(factor.myNode.getFactor().getFactorVars());
            newF.setFactorVars(CommonHash(newF));

            newF.name=factor.myNode.getName();
            newF.myNode=factor.myNode;
//        newD.printFactor();
            allFactors.replace(factor.myNode,newF);
        }
//                System.out.println("node  : " +node.getName() +" - "+ nweFactor.keySet().toString());



        return  allFactors.get(factor.myNode);
    }

    public Factor Join( Factor a ,Factor b) throws Exception {

        if(a.factor.isEmpty()){
            return b;
        }
        HashMap<ArrayList<String>, Double> nweFactor = new HashMap<>();//

        ArrayList<String> com =CommonVar(a ,b);

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
                    mullCounter++;
                }

            }
            allFactors.remove(bnGraph.graph.get(a.name));
            Factor temp = new Factor(nweFactor);
            temp.name = b.name;
            temp.myNode=b.myNode;
            temp.bnGraph=b.bnGraph;
            temp.queryString=b.queryString;
            temp.setFactorVars(CommonHash(temp));
            allFactors.remove(b.myNode, b);
            allFactors.put(temp.myNode,temp);
        }
        else {
            for (ArrayList<String> rowA : a.factor.keySet()) {
                    for (ArrayList<String> rowB : b.factor.keySet()) {
                        ArrayList<String> tempRow = new ArrayList<>();
                        if (matchKey(com,rowA, rowB)) {

                            for (String varA : rowA) {
                                tempRow.add(varA);
                            }
                            for (String varB : rowB) {
                                if (!tempRow.contains(varB)) {
                                    tempRow.add(varB);
                                }
                            }
                            nweFactor.put(tempRow, (a.factor.get(rowA) * b.factor.get(rowB)));
                            mullCounter++;

                        }
                    }

            }

            allFactors.remove(bnGraph.graph.get(a.name));
            Factor temp = new Factor(nweFactor);
            temp.name = b.name;
            temp.myNode=b.myNode;
            temp.bnGraph=b.bnGraph;
            temp.queryString=b.queryString;
            temp.setFactorVars(CommonHash(temp));
            allFactors.remove(b.myNode, b);
            allFactors.put(temp.myNode,temp);

        }

        return  allFactors.get(b.myNode);

    }



    public ArrayList<String> CommonHash( Factor a ){
        ArrayList<String> commons = new ArrayList<>();

        Iterator<ArrayList<String>> iteratorA = a.factor.keySet().iterator();
        ArrayList<String> keyA = null;
        if(iteratorA.hasNext()){
            keyA = iteratorA.next();
        }


        for (String var:keyA)
        {
            assert keyA != null;
            if(keyA.contains(var)){
                commons.add(var);}
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

            if(keyA.toString().contains(var.split("=")[0])){
                commons.add(var.split("=")[0]);}
        }


        return commons;
    }


    public ArrayList<String> factorVar( Factor a ){
        ArrayList<String> commons = new ArrayList<>();

        Iterator<ArrayList<String>> iteratorA = a.factor.keySet().iterator();
        ArrayList<String> keyA = null;
        if(iteratorA.hasNext()){
            keyA = iteratorA.next();
        }

        for (String var:keyA)
        {

                commons.add(var.split("=")[0]);
        }


        return commons;
    }


    public ArrayList<String> NodeVar( NodeBN a ){
        ArrayList<String> commons = new ArrayList<>();

        Iterator<ArrayList<String>> iteratorA = a.getCpt().cpt.keySet().iterator();
        ArrayList<String> keyA = null;
        if(iteratorA.hasNext()){
            keyA = iteratorA.next();
        }

        for (String var:keyA)
        {

            commons.add(var.split("=")[0]);
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

    public boolean matchKey( ArrayList<String> com , ArrayList<String> a ,ArrayList<String> b){
     boolean flag=true;
        for (String varA:a)
        {
            if (com.contains(varA.split("=")[0])&&!b.contains(varA)) {
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



    public double findQueryInCpt(String proQuery){

        String var= proQuery.split("\\|")[0].split("=")[0].split("P\\(")[1];
        String qu =proQuery.split("\\|")[1].split("\\)")[0] + "," + proQuery.split("\\|")[0].split("P\\(")[1];

        String [] buff  =  qu.split(",",listOfValuesFromQuery.size()-1);

        ArrayList<String> myVar = new ArrayList<>(NodeVar(bnGraph.getGraph().get(var)));

        boolean fl =true;
        for (String var2:myVar)
        {
            if (!Arrays.toString(buff).contains(var2))
            {
                fl = false;
                break;
            }
        }

        for (ArrayList<String> row :bnGraph.getGraph().get(var).getCpt().cpt.keySet())
        {
            boolean flag = true;
            for (String vari : buff ){
                    if((!row.contains(vari)))
                    {
                        flag=false;
                    }

            }
            if((flag )&&(fl))
            {
                return bnGraph.getGraph().get(var).getCpt().cpt.get(row);
            }
        }
        return  -1;

    }


}
