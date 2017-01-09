import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zahar on 08/01/17.
 */
public class Factor implements Comparable<Factor>{

    private ArrayList<ArrayList<Variable>> factorTable;
    private String varName;
    private int varParentsNum;
    private ArrayList<String> variables;

    public Factor() {
        factorTable = new ArrayList<>();
        variables = new ArrayList<>();
    }

    public Factor(String _varName, int _varParentsNum, ArrayList<ArrayList<Variable>> _factor , ArrayList<String> _variables) {
        varName = _varName;
        varParentsNum = _varParentsNum;
        factorTable = _factor;
        variables = _variables;

//        findVariables();
    }

//    private void findVariables() {
//        variables.add(varName);
//        for (int i = 0; i < factorTable.size(); i++) {
//            for (int j = 0; j < varParentsNum; j++) {
//                if(!variables.contains(factorTable.get(i).get(j).getVariableName())){
//                    variables.add(factorTable.get(i).get(j).getVariableName());
//                }
//            }
//        }
//    }

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

    public ArrayList<String> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<String> variables) {
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

        return varName +" - factorTable table: \n" + factorPrint;
    }


    @Override
    public int compareTo(Factor factor) {
        if(factor.getVarParentsNum() > this.getVarParentsNum()){
            return -1;
        }else if(factor.getVarParentsNum() < this.getVarParentsNum()){
            return 1;
        }else{
            return 0;
        }
    }
}
