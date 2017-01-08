/**
 * Created by zahar on 01/01/17.
 */
public class myParameter {
    String queryName;
    String value;

    public myParameter(String queryName, String value) {
        this.queryName = queryName;
        this.value = value;
    }


    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
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

        myParameter that = (myParameter) o;

        if (queryName != null ? !queryName.equals(that.queryName) : that.queryName != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public String toString() {
        return queryName + "=" + value;
    }
}
