/**
 * Created by zahar on 01/01/17.
 */
public class Variable {
    private String variableName;
    private String value;

    public Variable() {
    }

    public Variable(Variable var) {
        this.variableName = var.variableName;
        this.value = var.value;
    }

    public Variable(String variableName, String value) {
        this.variableName = variableName;
        this.value = value;
    }


    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable that = (Variable) o;

        if (variableName != null ? !variableName.equals(that.variableName) : that.variableName != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public String toString() {
        return variableName + "=" + value;
    }
}
