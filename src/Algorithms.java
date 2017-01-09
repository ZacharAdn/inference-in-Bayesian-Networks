import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zahar on 05/01/17.
 */
public class Algorithms {

    private HashMap<String, Node> Vars;
    private HashMap<String, Variable> knownVariebles;
    private int mullCount;
    private int plusCount;

    public Algorithms(HashMap<String, Node> _Vars) {
        Vars=_Vars;
    }



    public void secondAlgo(myQuery query) {

        ArrayList<String> hiddensList = getHiddens(query);
        ArrayList<Factor> factorsList = initialFactors(query);

        hiddensList.sort(String::compareTo);

//        System.out.println(factorsList + "\n");

        for (int i = 0; i < hiddensList.size(); i++) {
            ArrayList<Factor> factorsToJoin = findFacrotsToJoin(factorsList , hiddensList.get(i));

            for (int j = 0; j < factorsToJoin.size()-1; j++) {
                Factor newFactor = JoinFactors(factorsToJoin.get(j), factorsToJoin.get(j+1), hiddensList.get(i));

                factorsList.remove(factorsToJoin.get(j));
                factorsList.remove(factorsToJoin.get(j+1));

                factorsList.add(newFactor);
            }

        }

        //finaly, the query variable

    }

    private ArrayList<Factor> findFacrotsToJoin(ArrayList<Factor> factorsList, String hiddenVariable) {
        ArrayList<Factor> factorsToJoin = new ArrayList<>();
        System.out.println("\n\nhidden:" + hiddenVariable);
        for (Factor factor : factorsList) {
            if (factor.getVariables().contains(hiddenVariable)  && !factorsToJoin.contains(factor)) {
                factorsToJoin.add(factor);
            }
        }
//
//        for (int i = 0; i < factorsToJoin.size(); i++) {
//            factorsList.remove(factorsToJoin.get(i));
//        }

        factorsToJoin.sort(Factor::compareTo);

        return factorsToJoin;
    }

    private Factor JoinFactors(Factor factor1, Factor factor2, String hiddenVar) {
        Factor result = new Factor();
        System.out.println("factor1 : ");
        System.out.println(factor1);
        System.out.println("factor2 : ");
        System.out.println(factor2);

        for (int i = 0; i < factor1.getFactorTable().size(); i++) {
            for (int j = 0; j < factor1.getVarParentsNum(); j++) {
                if(factor1.getFactorTable().get(i).get(j).getVariableName().equals(hiddenVar)){
                    Variable sharedVar = factor1.getFactorTable().get(i).get(j);
                }
            }
        }

        return result;
    }

    private ArrayList<Factor> initialFactors(myQuery query) {
        ArrayList<Factor> factorsList = new ArrayList<>();

        Variable queryVariable = query.getQueryVariable();
        ArrayList<Variable> evidenceVariables = query.getEvidenceVariables();


        for (Map.Entry<String, Node> varEntry : Vars.entrySet()){
            Factor newFactor = CPTtoFactor(varEntry.getValue());

            System.out.println();
            System.out.println(newFactor);

            boolean hidden = false;
            for (int i = 0; i < evidenceVariables.size() && !hidden; i++) {
                if(evidenceVariables.get(i).getVariableName().equals(varEntry.getValue().getVarName())){
                    hidden = true;
                }
            }
//            factorsList.add(new Factor(varEntry.getValue().getVarName()
//                    ,varEntry.getValue().getParents().length ,varEntry.getValue().getCPT()));
        }

        return factorsList;
    }

    private Factor CPTtoFactor(Node var) {
        ArrayList<String> variables = new ArrayList<>();
        ArrayList<ArrayList<Variable>> factorTable = new ArrayList<>();

        int k = 0;
        for (int i = 0; i < var.getCPT().length * var.getValues().length; i++) {
            factorTable.add(new ArrayList<>());
            int j=0;
            for (j = 0; j < var.getParents().length; j++) {
                factorTable.get(i).add(var.getCPT()[i % var.getCPT().length][j]);
                if (!variables.contains(var.getCPT()[i % var.getCPT().length][j].getVariableName())) {
                    variables.add(var.getCPT()[i % var.getCPT().length][j].getVariableName());
                }
            }
            if(i % var.getCPT().length == 0 && i != 0) k++;
            String value =var.getValues()[k].substring(1);
            factorTable.get(i).add(new Variable(var.getVarName(),value));
            factorTable.get(i).add(new Variable(value , "-1"));//var.getCPT()[i % var.getCPT().length][j].getValue()));

            for (int l = 0; l < var.getValues().length-1; l++) {
                if(factorTable.get(i).get(j+1).getValue().equals("-1")) {
                    if (factorTable.get(i).get(j).getValue().equals(var.getCPT()[i % var.getCPT().length][j + l].getVariableName())) {
                        factorTable.get(i).get(j + 1).setValue(var.getCPT()[i % var.getCPT().length][j + l].getValue());
                    } else {
//                        factorTable.get(i).get(j+1).setValue();//TODO the complementary with % parents value
                    }
                }
            }
        }

//        for (int i = 0; i < factorTable.size(); i++) {
//            factorTable.get(factorTable.get(i).size()).add(
//                    new Variable(factorTable.get(i).get(factorTable.get(i).size()).getValue(), ))
//        }
//
        return new Factor(var.getVarName(), var.getParents().length, factorTable,variables);
    }

    public void firstAlgo(myQuery query) {
//        mullCount = 0;
//        plusCount = 0;
//
//        double numeratorSum = calcQuery(query);
//        double denominatorSum = calcDenominator(query);
//
//        System.out.println(numeratorSum + " , " + (denominatorSum + numeratorSum));
//
////         queryProbSum ;  //P(q|e1..en) = P(q,e1..en) / P(e1...en)
//
//        double queryProbSum = numeratorSum/(denominatorSum + numeratorSum);
//        System.out.println("\n\n RESULT: "+queryProbSum+ ", pluses:" + plusCount+", muls:" + mullCount );
    }


    private double calcQuery(myQuery query) {

        ArrayList<HashMap<String, Variable>> hidenPermutations = listPermutations( getHiddens(query) );

        return calcQueryProbability(hidenPermutations, knownVariebles);
    }

    private ArrayList<String> getHiddens(myQuery query) {
        knownVariebles = new HashMap<>();
        ArrayList<String> hiddenVariebles = new ArrayList<>();
        Variable parmToMap;


        parmToMap = query.getQueryVariable();
        knownVariebles.put(parmToMap.getVariableName(),parmToMap);// insert the query variable

        for (int i = 0; i < query.getEvidenceVariables().size(); i++) {// insert the evidence variable
            parmToMap = query.getEvidenceVariables().get(i);
            knownVariebles.put(parmToMap.getVariableName(),parmToMap);
        }


        for (Map.Entry<String, Node> varEntry : Vars.entrySet()) {// insert all the hidden variables to list
            if (!knownVariebles.containsKey(varEntry.getKey())) {
                hiddenVariebles.add(varEntry.getValue().getVarName());
            }
        }

        return hiddenVariebles;
    }

    private double calcDenominator(myQuery query) {
        double denominatorSum = 0;
        ArrayList <Double> complementaryList = new ArrayList<>();
        String  []  values = Vars.get(query.getQueryVariable().getVariableName()).getValues();

        for (int i = 0; i <values.length ; i++) {// for the denominator calculation
            if(!values[i].substring(1).equals(query.getQueryVariable().getValue())){
                System.out.println("CHECK: "+values[i] +" , " +query.getQueryVariable());
                myQuery compQuery = new myQuery(new Variable(query.getQueryVariable().getVariableName(),values[i].substring(1)) ,
                        query.getEvidenceVariables(),query.getAlgoNum());
                complementaryList.add(calcQuery(compQuery));
            }
        }
        System.out.println(complementaryList);
        for (int i = 0; i < complementaryList.size(); i++) {
            denominatorSum += complementaryList.get(i);
            plusCount++;
        }

        return denominatorSum;
    }

    private double calcQueryProbability(ArrayList<HashMap<String, Variable>> hidenPermutations, HashMap<String, Variable> nothiden) {
        double result = 0 ;
//
//        System.out.println("FOR no hidden : " + hidenPermutations.size());
//
//        if(hidenPermutations.size() == 1) {//TODO with firs query of input 2
//            System.out.println("**********************hidenPermutations.size() == 1");
//            for (Map.Entry<String, Variable> var : nothiden.entrySet()) {//TODO make arraylist
//                Node currentVar = Vars.get(var.getKey());
//                Variable[][] currentCPT = currentVar.getCPT();
//
//                double sum = 1, complementarySum = 0;
//                String[] vals = Vars.get(query.getQueryVariable().getVariableName()).getValues();
//                int valCol = findValColumn(query.getQueryVariable().getValue(), vals);
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

        for (HashMap<String, Variable> hidenMap : hidenPermutations) {// num of +
            double currentPermutationSum = 0 , varMultiplying = 1;

            System.out.println();
            System.out.println(hidenMap);

            for (Map.Entry<String, Node> varEntry : Vars.entrySet()) {// num of *
                Node currentNode = varEntry.getValue();
                Variable[][] currentCPT = currentNode.getCPT();
                String notValues [];
                boolean notVar = false;
                int cellToChange = 0, rowToChange = 0;

//                    System.out.println(currentNode.getVarName());

                boolean findTheVal = false;
                for (int i = 0; i < currentCPT.length && !findTheVal; i++) {
                    boolean correctRow = true;

                    for (int j = 0; j < currentNode.getParents().length && correctRow; j++) {
//                            System.out.println("here :"+currentNode.getVarName() + ", "+Arrays.toString(currentNode.getParents()));

                        if(!isCorrectCPTcell(currentNode,hidenMap,nothiden,i,j)){
                            correctRow = false;
                        }
                    }

                    if(correctRow){
//                            System.out.println("correct row:"+currentNode.getVarName());
                        int correctCol = 0;
                        Variable tmp = new Variable("","");
                        if(nothiden.get(currentNode.getVarName()) != null) {//the checked var is not hiden
                            correctCol = findValColumn(nothiden.get(currentNode.getVarName()).getValue(), currentNode.getValues());

                        }else{
                            correctCol = findValColumn(hidenMap.get(currentNode.getVarName()).getValue(), currentNode.getValues());
                        }

                        if(correctCol < 0){
//                                System.out.println("var: "+currentNode.getVarName()+ ", col:" + correctCol);
                            double complementary = clacComplementary(currentNode, i);
                            currentPermutationSum = 1-complementary;
                            System.out.println(currentNode.getVarName() + " mul (complementary) " +currentPermutationSum);
                        }else {
                            correctCol += currentNode.getParents().length;
//                                System.out.println("var: "+currentNode.getVarName()+ ", col:" + correctCol);
                            System.out.println(currentNode.getVarName() + " mul " +Double.parseDouble(currentCPT[i][correctCol].getValue()));
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

    private double clacComplementary(Node currentNode, int i) {
        double complementary = 0;
        for (int j = currentNode.getParents().length; j < currentNode.getCPT()[0].length; j++) {
            complementary += Double.parseDouble(currentNode.getCPT()[i][j].getValue());
        }
        return  complementary;
    }

    private String clacComplementaryFactors(String[] values, String currentValue) {
        double complementary = 0;
        for (int j = 0; j < values.length; j++) {
            if(!values[j].equals(currentValue)) {
//                complementary += Double.parseDouble(currentNode.getCPT()[i][j].getValue());
            }
        }
        return  String.valueOf(complementary);
    }

    private boolean isCorrectCPTcell(Node currentNode, HashMap<String, Variable> hidenMap, HashMap<String, Variable> nothiden, int i, int j) {
        Variable tmp;
//        System.out.println("int the func");
//        System.out.println(currentNode.getParents()[j]);
//        System.out.println(currentNode.getCPT()[i][j]);
        return ((tmp = nothiden.get(currentNode.getParents()[j])) != null &&  tmp.equals(currentNode.getCPT()[i][j]))
                || ((tmp = hidenMap.get(currentNode.getParents()[j])) != null && tmp.equals(currentNode.getCPT()[i][j]));
    }

    private int findValColumn(String value, String[] values) {
        for (int i = 0; i < values.length-1; i++) {
            if(values[i].substring(1).equals(value)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<HashMap<String, Variable>> listPermutations(ArrayList<String> list) {

        if (list.size() == 0) {
            ArrayList<HashMap<String, Variable>> result = new ArrayList<>();
            result.add(new HashMap<String, Variable>());
            return result;
        }

        ArrayList<HashMap<String, Variable>> resultLists = new ArrayList<HashMap<String, Variable>>();

        String firstElement = list.remove(0);

        ArrayList<HashMap<String, Variable>> recursiveReturn = listPermutations(list);
        for (HashMap<String, Variable> map: recursiveReturn) {

            for(String val : Vars.get(firstElement).getValues()){
                HashMap<String, Variable> tmp = new HashMap<>(map);
                tmp.put(firstElement,new Variable(firstElement ,val.substring(1)));
                resultLists.add(tmp);
            }
        }
        return resultLists;
    }

    public void thirdAlgo(myQuery query) {
    }
}
