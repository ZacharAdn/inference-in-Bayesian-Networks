import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zahar on 05/01/17.
 */
public class Algorithms {

    private HashMap<String, Var> Vars;
    private int mullCount;
    private int plusCount;

    public Algorithms(HashMap<String, Var> _Vars) {
        Vars=_Vars;
    }


    public void firstAlgo(myQuery query) {
        mullCount = 0;
        plusCount = 0;


        double numeratorSum = calcQuery(query);
        double denominatorSum  = 0;

        String  []  vals = Vars.get(query.getMainQuery().getQueryName()).getValues();
        ArrayList <Double> complementaries = new ArrayList<>();

        for (int i = 0; i <vals.length ; i++) {
            if(!vals[i].substring(1).equals(query.getMainQuery().getValue())){
                System.out.println("CHECK: "+vals[i] +" , " +query.getMainQuery());
                myQuery compQuery = new myQuery(new myParameter(query.getMainQuery().getQueryName(),vals[i].substring(1)) ,
                        query.getEvidences(),query.getAlgoNum());
                complementaries.add(calcQuery(compQuery));
            }
        }


        System.out.println(complementaries);
        for (int i = 0; i < complementaries.size(); i++) {
            denominatorSum += complementaries.get(i);
            plusCount++;
        }


        System.out.println(numeratorSum + " , " + (denominatorSum + numeratorSum));

//         queryProbSum ;  //P(q|e1..en) = P(q,e1..en) / P(e1...en)

        double queryProbSum = numeratorSum/(denominatorSum + numeratorSum);
        System.out.println("\n\n RESULT: "+queryProbSum+ ", pluses:" + plusCount+", muls:" + mullCount );

    }

    private double calcQuery(myQuery query) {

        HashMap<String, myParameter> nothiden = new HashMap<>();
        ArrayList<String> hiden = new ArrayList<>();


        myParameter parmToMap;

        parmToMap = query.getMainQuery();

        System.out.println("main "+query+":" + parmToMap);
        nothiden.put(parmToMap.getQueryName(),parmToMap);

        for (int i = 0; i < query.getEvidences().size(); i++) {
            parmToMap = query.getEvidences().get(i);
            nothiden.put(parmToMap.getQueryName(),parmToMap);
        }


        System.out.println(nothiden);
        for (Map.Entry<String, Var> varEntry : Vars.entrySet()) {
            if (!nothiden.containsKey(varEntry.getKey()) && !nothiden.containsKey("~"+varEntry.getKey())) {
                hiden.add(varEntry.getValue().getVarName());//the hiden var if val = true
            }
        }
        System.out.println(hiden);


        ArrayList<HashMap<String, myParameter>> hidenPermutations = listPermutations(hiden);

        return calcQueryProbability(query,hidenPermutations, nothiden);
    }

    private double calcQueryProbability(myQuery query, ArrayList<HashMap<String, myParameter>> hidenPermutations, HashMap<String, myParameter> nothiden) {
        double result = 0 ;
//
//        System.out.println("FOR no hidden : " + hidenPermutations.size());
//
//        if(hidenPermutations.size() == 1) {//TODO with firs query of input 2
//            System.out.println("**********************hidenPermutations.size() == 1");
//            for (Map.Entry<String, myParameter> var : nothiden.entrySet()) {//TODO make arraylist
//                Var currentVar = Vars.get(var.getKey());
//                myParameter[][] currentCPT = currentVar.getCPT();
//
//                double sum = 1, complementarySum = 0;
//                String[] vals = Vars.get(query.getMainQuery().getQueryName()).getValues();
//                int valCol = findValColumn(query.getMainQuery().getValue(), vals);
//
//
//                for (int i = 0; i < currentCPT.length; i++) {
//                    for (int j = 0; j < currentVar.getParents().length; j++) {
//
//                        if (nothiden.get(currentVar.getParents()[j]).equals(currentCPT[i][j])) {
//                            System.out.println("valcol " + valCol);
//                            if (valCol == vals.length - 1) {
//                                for (int k = currentVar.getParents().length; k < currentCPT[0].length; k++) {
//                                    complementarySum += Double.parseDouble(currentCPT[i][k].getValue());
//                                }
//                                sum *= (1 - complementarySum);
//                            } else {
//                                sum *= Double.parseDouble(currentCPT[i][valCol].getValue());
//                            }
//                            System.out.println("*********" + sum);
//
//                            break;
//                        }
//                    }
//                }
//            }
//        }

        for (HashMap<String, myParameter> hidenMap : hidenPermutations) {// num of +
            double currentPermutationSum = 0 , varMultiplying = 1;

            System.out.println();
            System.out.println(hidenMap);

            for (Map.Entry<String, Var> varEntry : Vars.entrySet()) {// num of *
                Var currentVar = varEntry.getValue();
                myParameter[][] currentCPT = currentVar.getCPT();
                String notValues [];
                boolean notVar = false;
                int cellToChange = 0, rowToChange = 0;

//                    System.out.println(currentVar.getVarName());

                boolean findTheVal = false;
                for (int i = 0; i < currentCPT.length && !findTheVal; i++) {
                    boolean correctRow = true;

                    for (int j = 0; j < currentVar.getParents().length && correctRow; j++) {
//                            System.out.println("here :"+currentVar.getVarName() + ", "+Arrays.toString(currentVar.getParents()));

                        if(!isCorrectCPTcell(currentVar,hidenMap,nothiden,i,j)){
                            correctRow = false;
                        }
                    }

                    if(correctRow){
//                            System.out.println("correct row:"+currentVar.getVarName());
                        int correctCol = 0;
                        myParameter tmp = new myParameter("","");
                        if(nothiden.get(currentVar.getVarName()) != null) {//the checked var is not hiden
                            correctCol = findValColumn(nothiden.get(currentVar.getVarName()).getValue(), currentVar.getValues());

                        }else{
                            correctCol = findValColumn(hidenMap.get(currentVar.getVarName()).getValue(), currentVar.getValues());
                        }

                        if(correctCol < 0){
//                                System.out.println("var: "+currentVar.getVarName()+ ", col:" + correctCol);
                            double complementary = clacComplementary(currentVar, i);
                            currentPermutationSum = 1-complementary;
                            System.out.println(currentVar.getVarName() + " mul (complementary) " +currentPermutationSum);
                        }else {
                            correctCol += currentVar.getParents().length;
//                                System.out.println("var: "+currentVar.getVarName()+ ", col:" + correctCol);
                            System.out.println(currentVar.getVarName() + " mul " +Double.parseDouble(currentCPT[i][correctCol].getValue()));
                            currentPermutationSum = Double.parseDouble(currentCPT[i][correctCol].getValue());
                        }

                        if(varMultiplying!=1){
                            mullCount++;
                        }
                        varMultiplying *= currentPermutationSum;
                        findTheVal = true;
                    }
                }
            }
//                mullCount--;
            System.out.println("mul sum "+ varMultiplying);
            if(result != 0){
                plusCount++;
            }
            result += varMultiplying;
        }
        System.out.println("result " + result+"\n");
        return result ;
    }

    private double clacComplementary(Var currentVar, int i) {
        double complementary = 0;
        for (int j = currentVar.getParents().length ; j < currentVar.getCPT()[0].length; j++) {
            complementary += Double.parseDouble(currentVar.getCPT()[i][j].getValue());
        }
        return  complementary;
    }

    private boolean isCorrectCPTcell(Var currentVar, HashMap<String, myParameter> hidenMap, HashMap<String, myParameter> nothiden, int i, int j) {
        myParameter tmp;
//        System.out.println("int the func");
//        System.out.println(currentVar.getParents()[j]);
//        System.out.println(currentVar.getCPT()[i][j]);
        return ((tmp = nothiden.get(currentVar.getParents()[j])) != null &&  tmp.equals(currentVar.getCPT()[i][j]))
                || ((tmp = hidenMap.get(currentVar.getParents()[j])) != null && tmp.equals(currentVar.getCPT()[i][j]));
    }

    private int findValColumn(String value, String[] values) {
        for (int i = 0; i < values.length-1; i++) {
            if(values[i].substring(1).equals(value)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<HashMap<String,myParameter>> listPermutations(ArrayList<String> list) {

        if (list.size() == 0) {
            ArrayList<HashMap<String,myParameter>> result = new ArrayList<>();
            result.add(new HashMap<String,myParameter>());
            return result;
        }

        ArrayList<HashMap<String,myParameter>> resultLists = new ArrayList<HashMap<String,myParameter>>();

        String firstElement = list.remove(0);

        ArrayList<HashMap<String,myParameter>> recursiveReturn = listPermutations(list);
        for (HashMap<String,myParameter> map: recursiveReturn) {

            for(String val : Vars.get(firstElement).getValues()){
                HashMap<String,myParameter> tmp = new HashMap<>(map);
                tmp.put(firstElement,new myParameter(firstElement ,val.substring(1)));
                resultLists.add(tmp);
            }
        }
        return resultLists;
    }

    public void secondAlgo(myQuery query) {


    }

    public void thirdAlgo(myQuery query) {
    }
}
