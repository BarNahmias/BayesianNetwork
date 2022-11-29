import java.util.ArrayList;
import java.util.HashMap;

public class Query {

    private BNGraph bnGraph;
    private  String queryString;
    private  HashMap<String, HashMap<String,String>> simp = new HashMap<>();
    private  HashMap<String,String> evidence =new HashMap<>();
    private HashMap<String,String> query =new HashMap<>();
    private  HashMap<String,String> algoindex =new HashMap<>();
    private ArrayList<String> hidden =new ArrayList<>();
    private ArrayList<String> listOfValuesFromQuery =new ArrayList<>();

    public   HashMap<String,String> allVariableFromQuery= new  HashMap<String,String>();
    public static  int addCounter = 0;

    public static  int mullCounter = 0;

    public static double  qPro = 0;

    public static double  ePro = 0;

    public Query(BNGraph bnGraph, String queryString) {
        this.bnGraph = bnGraph;
        this.queryString = queryString;
    }



    public  double findCurrProbability(String query) throws Exception {
        query=query.split("P\\(")[1].split("\\)")[0];
        query=query.replace('|',',');
        long count = query.chars().filter(ch -> ch == ',').count()+1;
        String[] buff= query.split(",",Math.toIntExact(count));
        double pi = 1;
        for (int i=0;i<count;i++){
            System.out.println("bu " +buff[i]+" : "+findVariableProbability(buff[i]));
            pi*=findVariableProbability(buff[i]);
            mullCounter++;
        }
        return pi;
    }



    public  double findVariableProbability(String currVariable) throws Exception {

        String variableName=currVariable.split("=")[0];
        this.getInfoFromQuery();

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
                        sum=sum+ cpt.cpt.get(kay);
                        addCounter ++;
                    }
                }
            }
            else
            {
                String best = '['+currVariable + ", "+ findMyParents(variableName).toString().split("\\[")[1].split("\\]")[0]+']';
                System.out.println("query best "+variableName+" : " + best);
                    System.out.println("str best "+kay.toString());
                System.out.println("bool " +kay.toString().equals(best));
                    if (kay.toString().equals(best)) {
                        sum = sum + cpt.cpt.get(kay);
                        addCounter++;

                }
            }

        }
        qPro=sum;
        return sum;
    }





//        findProbability are calculate probability of query variable when given are parents
//    from cpt table

    public  double findQueryProbability() throws Exception {
        String query=this.queryString;
        this.getInfoFromQuery();
        CPT cpt = bnGraph.graph.get(this.listOfValuesFromQuery.get(0).toString()).getCpt();
        double sum = 0.0;

        for (ArrayList<String> kay :cpt.cpt.keySet())
        {
            if(bnGraph.graph.get(cpt.name).getParents().isEmpty() || findMyParents(cpt.name).isEmpty() )
            {
                for (String str: kay)
                {
                    if(this.query.toString().contains(str))
                    {
                        sum=sum+ cpt.cpt.get(kay);
                        addCounter ++;
                    }
                }
            }
            else
            {
                String best =  findMyParents(this.listOfValuesFromQuery.get(0)).toString().split("\\[")[1].split("\\]")[0];
                System.out.println("query best" + best);
                for (String str : kay) {
                        if (best.contains(str)&&(query.contains(str))) {
                            sum = sum + cpt.cpt.get(kay);
                            addCounter++;
                        }
                    }
                }

        }
        qPro=sum;
        return sum;
    }

    public  double findEvidenceProbability() throws Exception {

        String query=this.queryString;
//        getInfoFromQuery();

        double eviPro = 1;
        for (String evidence :this.evidence.keySet())
        {
            CPT cpt = new CPT(bnGraph.graph.get(evidence));
            double sum = 0;
            if (bnGraph.graph.get(evidence).getParents().isEmpty() || findMyParents(evidence).isEmpty() ) {
                for (ArrayList<String> kay : cpt.cpt.keySet()) {
                        if (kay.contains(evidence.toString())) {
                            sum = sum + cpt.cpt.get(kay);
                            addCounter++;
                        }
                }
            }
            else {
                String best = findMyParents(evidence).toString().split("\\[")[1].split("\\]")[0];
                for (ArrayList<String> kay : cpt.cpt.keySet()) {
                        if (kay.contains(best)) {
                            sum = sum + cpt.cpt.get(kay);
                            sum *=qPro;
                            addCounter++;
                            mullCounter++;                        }
                    }

            }eviPro*=sum;
        }
        return eviPro;
    }



    public  double findHiddenProbability() throws Exception {

        String query=this.queryString;
        double hiddPro = 1;

        for (String hidden :this.hidden)
        {
            CPT cpt = new CPT(bnGraph.graph.get(hidden));
            double sum = 0;
            if (bnGraph.graph.get(hidden).getParents().isEmpty()|| findMyParents(hidden).isEmpty()) {
                System.out.println("best : "+hidden+ ": , " + " fin : "+findMyParents(hidden).toString());

                for (ArrayList<String> kay : cpt.cpt.keySet()) {
                        if (kay.toString().contains(hidden)) {
                            sum = sum + cpt.cpt.get(kay);
                            addCounter++;
                        }
                }
            }
            else {
                String a  = findMyParents(hidden).toString().split("\\[")[1].split("\\]")[0];
//                String newBest [] = best.split(",",findMyParents(hidden).size()-1);
                System.out.println("best : "+hidden+ ": , " + " fin : "+findMyParents(hidden).toString());

                for (ArrayList<String> kay : cpt.cpt.keySet()) {
                        if (kay.contains(a)&&(kay.toString().contains(hidden))) {
                            sum = sum + cpt.cpt.get(kay);
                            addCounter++;
                            System.out.println("sum : "+hidden+ ": , " + sum);
                        }
                }

            }
            if (sum!=0){
            hiddPro*=sum;
            mullCounter++;}
        }
        return hiddPro;
    }


    public double simpleBayesianInference()  throws Exception{
        double queryVariable = findQueryProbability();
        double evidenceVariable=findEvidenceProbability();
        double  hiddenVariable=findHiddenProbability();
        System.out.println("queryVariable "+ queryVariable );
        System.out.println("evidenceVariable "+ evidenceVariable );
        System.out.println("hiddenVariable "+ hiddenVariable );

        mullCounter+=3;
        return queryVariable*evidenceVariable*hiddenVariable;
    }


    public double getFinalPro(String ask){



        return 0;
    }




        //    getInfoFromQuery are get query input (string)  exam :  " P(B=T|J=T,M=T),2 "
    //    Finds all variables  and assigns to the appropriate fields
    public  void getInfoFromQuery() throws Exception {
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
        this.simp.put("query",query);
        this.simp.put("evidence",evidence);
        this.simp.put("algoindex",algoindex);
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
        return myParents;
    }

    public String getBestKay(){
        String key = this.query.toString();
        key = key.split("\\{")[1].split("\\}")[0];
        key = key +", ";
        for (String str: this.evidence.keySet())
        {
            key=key + str +"="+  this.evidence.get(str) +", ";
        }
        key=key.substring(0,key.length()-2);
        return key;
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

    public HashMap<String, HashMap<String, String>> getSimp() {
        return simp;
    }

    public void setSimp(HashMap<String, HashMap<String, String>> simp) {
        this.simp = simp;
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
        Query.addCounter = addCounter;
    }

    public static int getMullCounter() {
        return mullCounter;
    }

    public static void setMullCounter(int mullCounter) {
        Query.mullCounter = mullCounter;
    }
}
