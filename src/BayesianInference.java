import java.text.DecimalFormat;
import java.util.*;

public class BayesianInference {

    private BNGraph bnGraph;
    private  String queryString;
    private  HashMap<String,String> evidence =new HashMap<>();
    private HashMap<String,String> query =new HashMap<>();
    private  HashMap<String,String> algoindex =new HashMap<>();
    private ArrayList<String> hidden =new ArrayList<>();
    private ArrayList<String> listOfValuesFromQuery =new ArrayList<>();

    public   HashMap<String,String> allVariableFromQuery= new  HashMap<String,String>();
    public static  int addCounter = 0;

    public static  int mullCounter = 0;

    public static double  qPro = 0;

    public String origenal = "";

    public BayesianInference(BNGraph bnGraph, String queryString) {
        this.bnGraph = bnGraph;
        this.queryString = queryString;
        this.origenal=queryString;
        setMullCounter(0);
        setAddCounter(0);

    }

    public BayesianInference(BNGraph bnGraph) {

        this.bnGraph = bnGraph;
        setMullCounter(0);
        setAddCounter(0);

    }

    public String BayesianInference(String proQuery)throws Exception{
        String q= proQuery.split("P\\(")[1].split("=")[0];
        String out = proQuery.split("P\\(")[1].split("\\|")[0];
         String var=  proQuery.split("\\|")[0].split("=")[1];
        ArrayList<String> sm =new ArrayList<String>();
        setAddCounter(0);
        setMullCounter(0);

        double numerator = simpleBayesianInference(proQuery);
        double inCpt =findQueryInCpt(proQuery);
        if(inCpt!=-1)
        {
            setAddCounter(0);
            setMullCounter(0);

            return  String.format("%.5g",inCpt);
        }
        for (String outcome : bnGraph.graph.get(q).getVariable())
        {

            if (!Objects.equals(outcome, var))
            {

            String  temp="P("+  q + "=" + outcome +"|"+ proQuery.split("\\|")[1];
            sm.add(temp);
            }
        }
        double denominator=0;
        for (String newQuery: sm)
        {
            denominator+=simpleBayesianInference(newQuery);
           addCounter++;
        }
        addCounter--;


        denominator+=numerator;

        addCounter++;

        return String.format("%.5g",(numerator/denominator));
    }


//    this function are search if the query are equal to row in cpt and return the probability value

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
    public double simpleBayesianInference(String proQuery)throws Exception{
//        System.out.println(proQuery);

        String [] buff=createSimpleQuery(proQuery);
        double result = 0;
        for (String strQuery:buff)
        {
//              System.out.println(strQuery);

              double temp = 1;
              result+= findCurrProbability(strQuery);

            DecimalFormat df = new DecimalFormat("###.#############");
//            System.out.println("queryString : " +df.format(result));
           addCounter++;
        }
        addCounter--;

        return result;
    }


    public  double findCurrProbability(String query) throws Exception {
        this.queryString=query;
        getInfoFromQuery();
        query=query.split("P\\(")[1].split("\\)")[0];
        query=query.replace('|',',');
        long count = query.chars().filter(ch -> ch == ',').count()+1;
        String[] buff= query.split(",",Math.toIntExact(count));
        double pi = 1;
        for (int i=0;i<count;i++)
        {
//            System.out.println("bu " +buff[i]+" : "+findVariableProbability(buff[i]));
            DecimalFormat df = new DecimalFormat("###.##########");
            pi*=findVariableProbability(buff[i]);
//            System.out.println("curr " +df.format(+pi));
            mullCounter++;
        }
        mullCounter--;

        DecimalFormat df = new DecimalFormat("###.###########");

//        System.out.println("pi " +df.format(pi));

        return pi;
    }



    public  double findVariableProbability(String currVariable) throws Exception {

        String variableName=currVariable.split("=")[0];
//        this.getInfoFromQuery();

        CPT cpt = bnGraph.graph.get(variableName).getCpt();

        double sum = 0.0;

        for (ArrayList<String> kay :cpt.cpt.keySet())
        {
            if(bnGraph.graph.get(variableName).getParents().isEmpty() || findMyParents(variableName).isEmpty() )
            {
                for (String str: kay)
                {
                    if(currVariable.contains(str))
                    {
                        sum=cpt.cpt.get(kay);
                    }
                }
            }
            else
            {
                String best =  '['+findMyParents(variableName).toString().split("\\[")[1].split("\\]")[0]+ ", "+ currVariable +']';

                    if (kay.toString().equals(best)) {
                        sum =cpt.cpt.get(kay);
////
//                        System.out.println("find best "+variableName+" : " + best);
//                        System.out.println("kay best "+kay.toString());
                }
            }

        }
//        System.out.println("total prob for : " +currVariable+ " : "+sum);

        qPro=sum;
        return sum;
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
        for (String str : this.getBnGraph().graph.keySet())
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



    public  String [] createSimpleQuery(String myQuery) throws Exception {
        this.queryString=myQuery;
        setQueryString(myQuery);
        getInfoFromQuery();
//        System.out.println("queryString : " +queryString);
//        System.out.println("hh : " +hidden.toString());

        int counter = 1;

        for (String hidd:hidden)
        {
            counter*=bnGraph.graph.get(hidd).getVariable().size();
        }
        String hiddenQuery= myQuery.split("\\)")[0];
        String end=myQuery.split("\\)")[1];
        String [] buffQuery = new String[counter];

        HashMap<String, Integer> modolo =culculate(this.hidden);
//        System.out.println("modolo : " +modolo.keySet().toString() +modolo.values().toString());

        for(int i=0;i<counter;i++)
        {
            String temp =hiddenQuery;
            for (String hidd:hidden)
            {

                if(i%modolo.get(hidd)==0)
                {
//                    System.out.println("modolo1 : "+ i%modolo.get(hidd) +"   " +hidd +"  " +bnGraph.graph.get(hidd).getVariable().get(bnGraph.graph.get(hidd).first));

                    if(i!=0)
                    {
                        bnGraph.graph.get(hidd).addFirst();
                    }
                    if (  bnGraph.graph.get(hidd).first>  bnGraph.graph.get(hidd).getVariable().size()-1)
                    {
                        bnGraph.graph.get(hidd).first=0;
                    }
//                    System.out.println("first true : " + hidd +" " +   bnGraph.graph.get(hidd).first);

                    temp=temp+","+hidd+"="+bnGraph.graph.get(hidd).getVariable().get(bnGraph.graph.get(hidd).first);
                }
                else
                {

//                    System.out.println("modolo : "+ i%modolo.get(hidd) +"   " +hidd+"  " +bnGraph.graph.get(hidd).getVariable().get(bnGraph.graph.get(hidd).first));
//                    System.out.println("first else : " + hidd +" " +   bnGraph.graph.get(hidd).first);

                    temp=temp+","+hidd+"="+bnGraph.graph.get(hidd).getVariable().get(bnGraph.graph.get(hidd).first);
                }

            }
            temp=temp+')'+end;

//            System.out.println("temp : " +temp);

            buffQuery[i]=temp;
        }
        return buffQuery;
    }

    HashMap<String, Integer>   culculate (List<String> hidden )
    {
        HashMap<String, Integer> modolo = new HashMap<String, Integer>();
        int outcome=1;
        for (int index = 0; index < hidden.size(); index++) {
            modolo.put(hidden.get(index), outcome );
            outcome*= bnGraph.graph.get(hidden.get(index)).getVariable().size();
        }
        return modolo;
    }








    public BNGraph getBnGraph() {
        return bnGraph;
    }

    public void setBnGraph(BNGraph bnGraph) {
        this.bnGraph = bnGraph;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }


    public HashMap<String, String> getEvidence() {
        return evidence;
    }

    public void setEvidence(HashMap<String, String> evidence) {
        this.evidence = evidence;
    }

    public HashMap<String, String> getQuery() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public HashMap<String, String> getAlgoindex() {
        return algoindex;
    }

    public void setAlgoindex(HashMap<String, String> algoindex) {
        this.algoindex = algoindex;
    }

    public ArrayList<String> getHidden() {
        return hidden;
    }

    public void setHidden(ArrayList<String> hidden) {
        this.hidden = hidden;
    }

    public ArrayList<String> getListOfValuesFromQuery() {
        return listOfValuesFromQuery;
    }

    public void setListOfValuesFromQuery(ArrayList<String> listOfValuesFromQuery) {
        this.listOfValuesFromQuery = listOfValuesFromQuery;
    }

    public static int getAddCounter() {
        return addCounter;
    }

    public static void setAddCounter(int addCounter) {
        BayesianInference.addCounter = addCounter;
    }

    public static int getMullCounter() {
        return mullCounter;
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
    public static void setMullCounter(int mullCounter) {
        BayesianInference.mullCounter = mullCounter;
    }
}
