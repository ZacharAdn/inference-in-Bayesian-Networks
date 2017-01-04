import java.util.ArrayList;

/**
 * Created by zahar on 01/01/17.
 */
public class myQuery {

    myParameter query;
    ArrayList<myParameter> evidences;
    int algoNum;

    public myQuery(myParameter query, ArrayList<myParameter> evidences, int algoNum) {
        this.query = query;
        this.evidences = evidences;
        this.algoNum = algoNum;
    }

    public int getAlgoNum() {
        return algoNum;
    }

    public void setAlgoNum(int algoNum) {
        this.algoNum = algoNum;
    }

    public myParameter getQuery() {
        return query;
    }

    public void setQuery(myParameter query) {
        this.query = query;
    }

    public ArrayList<myParameter> getEvidences() {
        return evidences;
    }

    public void setEvidences(ArrayList<myParameter> evidences) {
        this.evidences = evidences;
    }

    @Override
    public String toString() {
        String evidence = "";
        for (myParameter evi : evidences){
            evidence += evi + ",";
        }
        return "P(" + query + " | " + evidences + ") , " + algoNum;
    }
}
