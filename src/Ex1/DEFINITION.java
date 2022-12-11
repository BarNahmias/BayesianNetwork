package Ex1;

import java.util.ArrayList;

public class DEFINITION {
    private String FOR;
    public ArrayList<String> GIVEN;
    public ArrayList<String> TABLE;

    public DEFINITION(){
        this.TABLE=new ArrayList<String>();
        this.GIVEN=new ArrayList<String>();
    }
    public DEFINITION(String FOR,ArrayList<String> GIVEN,ArrayList<String> TABLE){
        this.FOR=FOR;
        this.GIVEN=GIVEN;
        this.TABLE=TABLE;
    }

    public String getFOR() {
        return FOR;
    }

    public void setFOR(String FOR) {
        this.FOR = FOR;
    }

    public ArrayList<String> getGIVEN() {
        return GIVEN;
    }

    public void setGIVEN(ArrayList<String> GIVEN) {
        this.GIVEN = GIVEN;
    }

    public ArrayList<String> getTABLE() {
        return TABLE;
    }

    public void insertToTable(String input) {
        this.TABLE.add(input) ;
    }

    public boolean isGivenEmpty() {
        return this.GIVEN.isEmpty() ;
    }
    public void insertToGiven(String out) {
        this.GIVEN.add(out) ;
    }


    public void setTABLE(ArrayList<String> TABLE) {
        this.TABLE = TABLE;
    }

    @Override
    public String toString() {
        return "DEFINITION{" +
                "FOR='" + FOR + '\'' +
                ", GIVEN=" + GIVEN +
                ", TABLE=" + TABLE +
                '}';
    }
}

