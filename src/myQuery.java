import java.util.ArrayList;

/**
 * Created by zahar on 01/01/17.
 */
public class myQuery {

    private Variable queryVariable;
    private ArrayList<Variable> evidenceVariables;
    private int algoNum;

    public myQuery(Variable query, ArrayList<Variable> evidenceVariables, int algoNum) {
        this.queryVariable = query;
        this.evidenceVariables = evidenceVariables;
        this.algoNum = algoNum;
    }

    public int getAlgoNum() {
        return algoNum;
    }

    public void setAlgoNum(int algoNum) {
        this.algoNum = algoNum;
    }

    public Variable getQueryVariable() {
        return queryVariable;
    }

    public void setQueryVariable(Variable queryVariable) {
        this.queryVariable = queryVariable;
    }

    public ArrayList<Variable> getEvidenceVariables() {
        return evidenceVariables;
    }

    public void setEvidenceVariables(ArrayList<Variable> evidenceVariables) {
        this.evidenceVariables = evidenceVariables;
    }

    @Override
    public String toString() {
        String evidence = "";
        for (Variable evi : evidenceVariables){
            evidence += evi + ",";
        }
        return "P(" + queryVariable + " | " + evidenceVariables + ") , " + algoNum;
    }
}
