import java.util.*;

/**
 * Created by zahar on 28/12/16.
 */
public class ex1 {


    public static void main(String[] args) {
        readFile file = new readFile("/home/zahar/IdeaProjects/AlgoDecisions/src/files/input2.txt");

        ArrayList<myQuery> queries = file.getQueries();
        HashMap<String, Var> Vars = file.getVars();

        Algorithms algorithms = new Algorithms (Vars);


        for (myQuery query : queries) {
//            System.out.println(q);
            if (query.algoNum == 1) {
                algorithms.firstAlgo(query);
            } else if (query.algoNum == 2) {
                algorithms.secondAlgo(query);
            } else {
                algorithms.thirdAlgo(query);
            }
//            System.out.println("***END of mainQuery "+q);
        }
    }
}