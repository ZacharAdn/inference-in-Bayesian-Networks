/**
 * Created by zahar on 01/01/17.
 */
public class Variable {
    private String variableName;
    private String value;


    /**
     * Cell in the CPT or Factor
     *
     */
    public Variable(String variableName, String value) {
        this.variableName = variableName;
        this.value = value;
    }

    public Variable(Variable var) {
        this.variableName = var.variableName;
        this.value = var.value;
    }


    public String getVariableName() {
        return variableName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object variable) {
        if (this == variable) return true;
        if (variable == null || getClass() != variable.getClass()) return false;

        Variable that = (Variable) variable;

        if (variableName != null ? !variableName.equals(that.variableName) : that.variableName != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public String toString() {
        return variableName + "=" + value;
    }
}
