/**
 * Created by zahar on 01/01/17.
 */
public class myParameter {
    String queryName;
    String bool;

    public myParameter(String queryName, String bool) {
        this.queryName = queryName;
        this.bool = bool;
    }


    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getBool() {
        return bool;
    }

    public void setBool(String bool) {
        this.bool = bool;
    }

    @Override
    public String toString() {
        return queryName + "=" + bool;
    }
}
