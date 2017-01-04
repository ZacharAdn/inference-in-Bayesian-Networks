import java.util.*;

/**
 * Created by zahar on 28/12/16.
 */
public class ex1 {


    public static void main(String[] args) {
        readFile file = new readFile("/home/zahar/IdeaProjects/AlgoDecisions/src/input.txt");

        ArrayList<myQuery> queries = file.getQueries();
        HashMap<String, Var> Vars = file.getVars();

        for (myQuery q : queries) {
//            System.out.println(q);
            if (q.algoNum == 1) {
                firstAlgo(q, Vars);
            } else if (q.algoNum == 2) {
                secondAlgo(q, Vars);
            } else {
                thirdAlgo(q,Vars);
            }

//            System.out.println("***END of query "+q);
        }
    }

    private static void firstAlgo(myQuery query, HashMap<String, Var> Vars) {

        HashMap<String, Var> nothiden = new HashMap<>();
        HashMap<String, Var> hiden = new HashMap<>();

        double notHidden = 0;
        String queryName;

        queryName = query.getQuery().getQueryName();
        nothiden.put(queryName, Vars.get(queryName));

//        notHidden += Vars.get(query.getQuery().getQueryName()).

        for (int i = 0; i < query.getEvidences().size(); i++) {
            queryName = query.getEvidences().get(i).getQueryName();
            nothiden.put(queryName, Vars.get(queryName));
        }


        for (Map.Entry<String, Var> varEntry : Vars.entrySet()) {
            if (!nothiden.containsKey(varEntry.getKey())) {
                hiden.put(varEntry.getValue().getVarName(), varEntry.getValue());
            }
        }

        allHiddenPermutation(0, new HashMap<>(), hiden, new ArrayList<>());

    }

    private static <String, Var> void allHiddenPermutation(int index, HashMap<String, Var> current,
                                                           HashMap<String, Var> map,
                                                           ArrayList<HashMap<String, Var>> list) {

        if (index == map.size()) {
            HashMap<String, Var> newMap = new HashMap<>();
//            System.out.println(current);
            for (String varName : current.keySet()) {          // copy contents to new map.
                newMap.put(varName, current.get(varName));
            }
            list.add(newMap); // add to result.
        } else {
            Object currentName = map.keySet().toArray()[index]; // take the current key


            for (Map.Entry<String,Var> currentVar : map.entrySet()) {

                current.put(currentVar.getKey(), currentVar.getValue()); // put each value into the temporary map
                allHiddenPermutation(index + 1, current, map, list); // recursive call
                current.remove(currentVar); // discard and try a new value
            }
//
//            System.out.println("\n\nlist:");
//            System.out.println(list);
        }
    }

    private void GeneratePermutations(ArrayList<ArrayList<String>> combinations, ArrayList<String> resultCombination, int depth, String current) {
        if (depth == combinations.size()) {
            resultCombination.add(current);
            return;
        }
        for (int i = 0; i < combinations.get(depth).size(); ++i) {
            GeneratePermutations(combinations, resultCombination, depth + 1, current + combinations.get(depth).get(i) + ",");
        }
    }



    private static void secondAlgo(myQuery q, HashMap<String, Var> vars) {
    }

    private static void thirdAlgo(myQuery q, HashMap<String, Var> vars) {

    }
}