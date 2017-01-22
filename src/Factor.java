import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by zahar on 08/01/17.
 */
public class Factor implements Comparable<Factor>{

    private ArrayList<ArrayList<Variable>> factorTable;
    private String varName;
    private int varParentsNum;
    private HashMap<String, Integer> variables;

    public Factor() {
        factorTable = new ArrayList<>();
        variables = new HashMap<>();//map of the var name and the column in the factor table
    }

    public Factor(String _varName, int _varParentsNum, ArrayList<ArrayList<Variable>> _factor ) {
        varName = _varName;
        varParentsNum = _varParentsNum;
        factorTable = _factor;
        variables = new HashMap<>();//map of the var name and the column in the factor table
    }

    public ArrayList<ArrayList<Variable>> getFactorTable() {
        return factorTable;
    }

    public void setFactorTable(ArrayList<ArrayList<Variable>> factorTable) {
        this.factorTable = factorTable;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public HashMap<String, Integer> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, Integer> variables) {
        this.variables = variables;
    }

    public int getVarParentsNum() {
        return varParentsNum;
    }

    public void setVarParentsNum(int varParentsNum) {
        this.varParentsNum = varParentsNum;
    }

    @Override
    public String toString() {
        String factorPrint = "";
        for (ArrayList<Variable> row: factorTable) {
            factorPrint += row + "\n";
        }

        return varName +" - variables: "+ variables  + " factorTable : \n" + factorPrint;
    }


    @Override
    public int compareTo(Factor factor) {
        if(factor.getFactorTable().get(0).size() > this.getFactorTable().get(0).size()){
            return -1;
        }else if(factor.getFactorTable().get(0).size() < this.getFactorTable().get(0).size()){
            return 1;
        }else{
            return 0;
        }
    }
}
