import java.util.*;

/**
 * Created by zahar on 28/12/16.
 */
public class ex1 {


    public static void main(String[] args) {
        String fileName = "input.txt";
        IOfile file = new IOfile(fileName);
        file.readFile();


        ArrayList<myQuery> queries = file.getQueries();
        HashMap<String, Node> Vars = file.getVars();

        Algorithms algorithms = new Algorithms (Vars , file);


        for (myQuery query : queries) {
            if (query.getAlgoNum() == 1) {
                algorithms.firstAlgo(query);
            } else if (query.getAlgoNum() == 2) {
                algorithms.secondAlgo(query);
            } else {
                algorithms.thirdAlgo(query);
            }
        }

        file.closeWriter();
    }
}
