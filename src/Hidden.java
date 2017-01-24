/**
 * Created by zahar on 22/01/17.
 */
public class Hidden implements Comparable<Hidden>{

    private String name;
    private int rowsAppearanseCount;

    /**
     * class for the 3th algorithm - use to sort the hidden variables
     * @param name
     */
    public Hidden(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowsAppearanseCount() {
        return rowsAppearanseCount;
    }

    public void setRowsAppearanseCount(int rowsAppearanseCount) {
        this.rowsAppearanseCount = rowsAppearanseCount;
    }

    @Override
    public int compareTo(Hidden hidden) {
        if(this.getRowsAppearanseCount() < hidden.getRowsAppearanseCount()){
            return 1;
        }else if(this.getRowsAppearanseCount() > hidden.getRowsAppearanseCount()){
            return -1;
        }else{
            return 0;
        }
    }
}
