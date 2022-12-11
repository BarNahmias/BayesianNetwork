package Ex1;

import java.util.ArrayList;

public class VARIABLE {
   private String NAME;
    private ArrayList<String> OUTCOME;

   public VARIABLE(){
       this.OUTCOME= new ArrayList<String>();
   }


    public VARIABLE(String NAME, ArrayList<String> OUTCOME) {
        this.NAME = NAME;
        this.OUTCOME = OUTCOME;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void insertToOutcome(String out) {
        this.OUTCOME.add(out) ;
    }

    public ArrayList<String> getOUTCOME() {
        return OUTCOME;
    }

    public void setOUTCOME(ArrayList<String> OUTCOME) {
        this.OUTCOME = OUTCOME;
    }

    @Override
    public String toString() {
        return "VARIABLE{" +
                "NAME='" + NAME + '\'' +
                ", OUTCOME=" + OUTCOME +
                '}';
    }
}
