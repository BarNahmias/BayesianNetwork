
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class txtToJava {

    //    this function get txt file and return arraylist of string
    public static ArrayList<String> readTxt(String filename) throws Exception
    {

        File file = new File(filename);

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> ans =new  ArrayList<String>();
        // Declaring a string variable
        String st  = " ";
//        br.readLine();
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null)

            // insert the row to arraylist
            ans.add(st);
        return ans ;
    }

}
