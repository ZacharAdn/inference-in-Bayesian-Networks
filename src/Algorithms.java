import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zahar on 05/01/17.
 */
public class Algorithms {

    private DecimalFormat decimalFormat;
    private HashMap<String, Node> Vars;
    private HashMap<String, Variable> knownVariebles;
    private int mullCount;
    private int plusCount;


    public Algorithms(HashMap<String, Node> _Vars) {
        Vars=_Vars;
        decimalFormat = new DecimalFormat("#.#####");
    }



    public void secondAlgo(myQuery query) {
        System.out.println("\n\nNEW QUERY:" +query +"\n\n");

        ArrayList<String> hiddensList = getHiddens(query);
        ArrayList<Factor> factorsList = initialFactors(query,hiddensList);

        hiddensList.sort(String::compareTo);// sort the hidden by the alphabet (ABC)

        //TODO while query not empty
        for (int i = 0; i < hiddensList.size(); i++) {
            ArrayList<Factor> factorsToJoin = findFacrotsToJoin(factorsList, hiddensList.get(i));

            for (int j = 0; j < factorsToJoin.size() - 1; j++) {
//                Factor newFactor = JoinFactors(factorsToJoin.get(j), factorsToJoin.get(j + 1), hiddensList.get(i));


//                factorsList.remove(factorsToJoin.get(j));
//                factorsList.remove(factorsToJoin.get(j+1));
//
//                factorsList.add(newFactor);
            }

        }
        //finally, the query variable - join of all the factors

    }

    private boolean isAncestor(Node var, Variable queryVariable, HashMap<String, Variable> evidence) {
        boolean ans;
        if (queryVariable.getVariableName().equals(var.getVarName()) || evidence.containsKey(var.getVarName())) {//TODO if its query its no need?
            return true;
        }

        ans = isAncestorRecursive(var, Vars.get(queryVariable.getVariableName()).getParents());

        if (!ans) {
            for (Map.Entry<String, Variable> evidenceEntry : evidence.entrySet()) {
                ans = isAncestorRecursive(var, Vars.get(evidenceEntry.getValue().getVariableName()).getParents());
            }
        }

        return ans;
    }

    private boolean isAncestorRecursive(Node var , String[] parents){
        if(parents.length == 0){
            return false;
        }

        for (String parent : parents) {
            if (var.getVarName().equals(parent)) {
                return true;
            }else{
                boolean ans = isAncestorRecursive(var,Vars.get(parent).getParents());
                if(ans){
                    return ans;
                }
            }
        }

        return false;
    }

    private ArrayList<Factor> findFacrotsToJoin(ArrayList<Factor> factorsList, String hiddenVariable) {
        ArrayList<Factor> factorsToJoin = new ArrayList<>();
        for (Factor factor : factorsList) {
            if (factor.getVariables().containsKey(hiddenVariable)  && !factorsToJoin.contains(factor)) {
                factorsToJoin.add(factor);
            }
        }

        factorsToJoin.sort(Factor::compareTo);

//        System.out.println("factorsToJoin : " +factorsToJoin + " \n\n\n");

        return factorsToJoin;
    }

    private Factor JoinFactors(Factor factor1, Factor factor2, String hiddenVar) {
        Factor result = new Factor();
        ArrayList<ArrayList<Variable>> ansFactorTable = new ArrayList<>();

//        System.out.println(factor1);


        ArrayList<String> shared = sharedVarsInFactors(factor1.getVariables(), factor2.getVariables());


        for (int k = 0; k < factor1.getFactorTable().size(); k++) {
            //ArrayList<Variable> rowFactor1 : factor1.getFactorTable()) {

            ArrayList<Variable> rowFactor1 = factor1.getFactorTable().get(k);
            ArrayList<Variable> ansRow = new ArrayList<>();

            double mulAns;
//            for (ArrayList<Variable> rowFactor2 : factor2.getFactorTable()) {
            for (int j = 0; j < factor1.getFactorTable().size(); j++) {
                //ArrayList<Variable> rowFactor1 : factor1.getFactorTable()) {

                ArrayList<Variable> rowFactor2 = factor2.getFactorTable().get(j);

                boolean canMerge = true;
                mulAns = 0;

                for (String sharedVar : shared) {

                    if(factor1.getVariables().get(sharedVar) != null && factor2.getVariables().get(sharedVar) != null){
                        int column1 = factor1.getVariables().get(sharedVar);//the column of the mutual var in factor 1
                        int column2 = factor2.getVariables().get(sharedVar);//the column of the mutual var in factor 2

//                        System.out.println("*************factor1:\n" + factor1 + "\n****************");
//                        System.out.println("*************factor2:\n" + factor2 + "\n****************");
//
//                        System.out.println("shVar 1:" + factor1.getVariables().get(sharedVar));
//                        System.out.println("shVar :" + factor1.getVariables());
//                        System.out.println("shVar 2:" + factor2.getVariables().get(sharedVar));
//                        System.out.println("shVar :" + factor2.getVariables());
//                        System.out.println("column1:" + column1 + ", column:" + column2);
//                        System.out.println("rowFactor1.get(column1).getValue()"  + rowFactor1+", indexI : "+ k + ", indexJ" + j);

                        if (!rowFactor1.get(column1).getValue().equals(rowFactor2.get(column2).getValue())) {// if the mutual var has the same val
                            canMerge = false;
                        }
                    }
                }
                if(canMerge){


//                    System.out.println("val : " + rowFactor1.get(rowFactor1.size()-1).getValue());
//                    mulAns = Double.parseDouble(rowFactor1.get(rowFactor1.size()-1).getValue()) *
//                            Double.parseDouble(rowFactor2.get(rowFactor2.size()-1).getValue());


                    rowFactor1.remove(rowFactor1.size()-1);
                    ansRow.addAll(rowFactor1);

                    int iterateAnsList = factor2.getVariables().size()-1 - shared.size();
                    for (int i = 0; i < iterateAnsList; i++) {
                        if(!shared.contains(rowFactor2.get(i).getVariableName())){
                            ansRow.add(rowFactor1.get(i));
                        }
                    }

                    ansRow.add(new Variable(rowFactor2.get(rowFactor2.size()-1).getVariableName(), String.valueOf(decimalFormat.format(mulAns))));
                }
//                System.out.println(ansRow);
                ansFactorTable.add(ansRow);
            }
//            System.out.println("\n");

        }

        return result;
    }

    private ArrayList<String> sharedVarsInFactors(HashMap<String, Integer> vars1, HashMap<String, Integer> vars2) {

        ArrayList<String> varsList2= new ArrayList<>(vars2.keySet());
        ArrayList<String> result = new ArrayList<>(vars1.keySet());

        varsList2.removeAll(vars1.keySet());
        result.addAll(varsList2);

        return result;
    }

    private ArrayList<Factor> initialFactors(myQuery query, ArrayList<String> hiddensList) {
        ArrayList<Factor> factorsList = new ArrayList<>();
        HashMap<String,Variable> evidenceMap =  new HashMap<>();

        ArrayList<Variable> evidenceVariables = query.getEvidenceVariables(); //Variable queryVariable = query.getQueryVariable();
        for(Variable evidence : evidenceVariables ){
            evidenceMap.put(evidence.getVariableName(), evidence);
        }

        for (Map.Entry<String, Node> varEntry : Vars.entrySet()) {// factor for eche variable
            if (isAncestor(varEntry.getValue(),query.getQueryVariable(),evidenceMap)) {// if the variable not evidence or ancestor - no need factor
                factorsList.add(CPTtoFactor(varEntry.getValue(), evidenceMap));//convert cpt to factor and add to factors list
            }
        }

        System.out.println(factorsList);

        return factorsList;
    }

    private Factor CPTtoFactor(Node var, HashMap<String, Variable> evidenceMap) {
        Factor factor = new Factor();

        factor.setVarName(var.getVarName());
        factor.setVarParentsNum(var.getParents().length);

            System.out.println("var.getVarName() : " +var.getVarName());

            for (int i = 0; i < var.getCPT().length; i++) {
                ArrayList<Variable> row = new ArrayList<>(Arrays.asList(var.getCPT()[i]));
                boolean neededRow = true;

                for(Map.Entry<String ,Variable> evi : evidenceMap.entrySet()) {
                    if (neededRow) {
                        Variable evidence = evi.getValue();
                        if (!needToAddRow(evidence, row)) neededRow = false;
                        if (row.contains(evidence)) row.remove(evidence);// remove all the evidance vars
                    }
                }

                boolean findProbRow = false;
                double complementary = 0;

                //if the val in the cpt is the needed val
                for (int j = var.getParents().length; j < row.size(); j++) {
                    String checkedVar = row.get(j).getVariableName();
                    if (checkedVar.equals(evidenceMap.get(var.getVarName()).getValue())) {
                        findProbRow = true;
                    } else {
                        complementary += Double.parseDouble(row.get(j).getValue());
                        row.remove(j--);
                    }
                }

                if(!findProbRow){
                    complementary = 1 - complementary;
                    row.add(new Variable(evidenceMap.get(var.getVarName()).getValue(), String.valueOf(decimalFormat.format(complementary))));
                }

                if(neededRow){
                    factor.getFactorTable().add(row);
                }
            }

        if(!evidenceMap.containsKey(var.getVarName())){ // if the variable is evidence , it has the same value as the query
            int k = 0;
            for (int i = 0; i < var.getCPT().length * var.getValues().length; i++) {// all the options for the var  - hes option in the cpt * hes values
                factor.getFactorTable().add(new ArrayList<>());

//                int j = 0;
//                for (j = 0; j < var.getParents().length; j++) { //iterate on
//
////                    Variable current = var.getCPT()[i % var.getCPT().length][j];
////                    String [] currValues = Vars.get(current.getVariableName()).getValues();
////                    for (int l = 0; l < currValues.length ; l++) {
////                        if(!current.getValue().equals(currValues[i])){
////                            current.setValue();
////                        }
////                    }
//
//                    if(!evidenceMap.containsKey(var.getCPT()[i % var.getCPT().length][j].getVariableName())) {
//                        factor.getFactorTable().get(i).add(var.getCPT()[i % var.getCPT().length][j]);
//                    }
//                }
                if (i % var.getCPT().length == 0 && i != 0) k++; //the next value of the variable
                String value = var.getValues()[k].substring(1);
                factor.getFactorTable().get(i).add(new Variable(var.getVarName(), value));
                factor.getFactorTable().get(i).add(new Variable(value, "-1"));

                for (int l = 0; l < var.getValues().length - 1; l++) {//calculate the values
                    int col =factor.getFactorTable().get(i).size()-1;
                    if (factor.getFactorTable().get(i).get(col).getValue().equals("-1")) {
                        if (factor.getFactorTable().get(i).get(col-1).getValue().equals(var.getCPT()[i % var.getCPT().length][j + l].getVariableName())) {
                            factor.getFactorTable().get(i).get(col).setValue(var.getCPT()[i % var.getCPT().length][j + l].getValue());
                        }
                    }
                }
            }
            int numOfparentsValue = calcParentsValue(var);

            int index =  numOfparentsValue * (var.getValues().length - 1);
            int lastCol = factor.getFactorTable().get(0).size() - 1;


            for (int i = index; i < factor.getFactorTable().size(); i++) {
                index = i;
                if (factor.getFactorTable().get(i).get(lastCol).getValue().equals("-1")) {//calculate the complementary value
                    double complementary = 0;
                    for (int m = 0; m < var.getValues().length - 1; m++) {
                        index -= numOfparentsValue;
                        complementary += Double.parseDouble(factor.getFactorTable().get(index).get(lastCol).getValue());
                    }
                    factor.getFactorTable().get(i).get(lastCol).setValue(String.valueOf(decimalFormat.format(1 - complementary)));//TODO the complementary with % num of parents value
                }
            }
        }



        for (ArrayList<Variable> row : factor.getFactorTable()) {// generate the variables map of the current factor
            factor.getVariables().put(var.getVarName(), -1);
            for (int j = 0; j < factor.getVarParentsNum(); j++) {
                String varName = row.get(j).getVariableName();
                if (!factor.getVariables().containsKey(varName) && !evidenceMap.containsKey(varName) && Vars.containsKey(varName)) {
                    factor.getVariables().put(varName,j);// insert the var and the column of the var
                }
            }
        }
//        System.out.println("variables: " + factor.getVariables());

//        System.out.println("factor " + factor.getVarName()  +": "+ factor.getFactorTable() + "\nvars: "+ factor.getVariables() + " \n\n");

        return factor;
    }

    private boolean needToAddRow(Variable evidence, ArrayList<Variable> row) {

        for (String value : Vars.get(evidence.getVariableName()).getValues()){// remove all the rows that has wrong val of evidence var
            if(!evidence.getValue().equals(value)){
                evidence.setValue(value);

                if(row.contains(evidence)){
                    return false;
                }
            }
        }
        return true;
    }

    private int calcParentsValue(Node var) {

        int numOfparentsValue = 1;
        for (int m = 0; m < var.getParents().length; m++) {
            numOfparentsValue *= Vars.get(var.getParents()[m]).getValues().length;
        }

        return numOfparentsValue;
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
                System.out.println("var : \n"+ currentNode+"\n");
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
