import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by zahar on 28/12/16.
 */
public class Var {

    String varName;
    String [] values;
    String [] parents;
    myParameter [][] CPT;

    public Var() {
    }

    public Var(String varName, String[] values, String[] parents, myParameter [][] CPT) {
        this.varName = varName;
        this.values = values;
        this.parents = parents;
        this.CPT = CPT;
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

    public myParameter[][] getCPT() {
        return CPT;
    }

    public void setCPT(myParameter[][] CPT) {
        this.CPT = CPT;
    }

    @Override
    public String toString() {
        String cpt="";


        for (myParameter[] row: CPT) {
            cpt += Arrays.toString(row) + "\n";
        }

        return "Var " + varName +
                ", values=" + Arrays.toString(values) +
                ", parents=" + Arrays.toString(parents) +
                ", CPT=\n" + cpt + "\n";
    }
}

