import java.io.*;

import java.util.ArrayList;


public class Ex1 {

    public static void main(String[] args) throws Exception {


        String path = args[0];

        ArrayList<String>  input = txtToJava.readTxt(path);
        BNGraph bnGraph = xmlToJava.readXml(input.get(0));
//        BNGraph bnGraph = xmlToJava.readXml(net);


        try {

            FileWriter writer = new FileWriter("output.txt");

        for (String query : input) {

            int num = Character.getNumericValue(query.charAt(query.length() - 1));

            if (num==1)
                {
                    BayesianInference bi = new BayesianInference(bnGraph);
                    writer.write(bi.BayesianInference(query) + "," + bi.addCounter + "," + bi.mullCounter+"\n");
                }
            if (num==2)
                {
                    variableElimination ve1 = new variableElimination(bnGraph, query);
                    writer.write(ve1.variableElimination() + "," + ve1.addCounter + "," + ve1.mullCounter+"\n");
                }
            if (num==3)
                {
                    variableElimination ve = new variableElimination(bnGraph, query);
                    writer.write(ve.variableElimination() + "," + ve.addCounter + "," + ve.mullCounter+"\n");
                }

            }
            writer.close();

                    } catch (IOException ignored) {
                    }



    }

}





