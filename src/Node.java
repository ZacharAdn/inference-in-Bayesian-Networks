import java.util.Arrays;

/**
 * Created by zahar on 28/12/16.
 */
public class Node {

    private String varName;
    private String [] values;
    private String [] parents;
    private Variable[][] CPT;

    /**
     * Node - Var that was created during reading the input file
     */
    public Node() {
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String[] getParents() {
        return parents;
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }

    public Variable[][] getCPT() {
        return CPT;
    }

    public void setCPT(Variable[][] CPT) {
        this.CPT = CPT;
    }

    @Override
    public String toString() {
        String cpt="";


        for (Variable[] row: CPT) {
            cpt += Arrays.toString(row) + "\n";
        }

        return "Var " + varName +
                ", values=" + Arrays.toString(values) +
                ", parents=" + Arrays.toString(parents) +
                ", CPT=\n" + cpt + "\n";
    }
}

