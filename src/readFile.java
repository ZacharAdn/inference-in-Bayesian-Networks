import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zahar on 01/01/17.
 */
public class readFile {

    private ArrayList<myQuery> Queries;
    private HashMap<String, Node> Vars;

    public readFile(String fileName) {
        Vars = new HashMap<>();
        int rows,columns,numOfParentsValues;
        String line;
        Variable[][] CPT;
        String[] names,cptLine;

        try {
            FileReader inputFile= new FileReader(fileName);
            BufferedReader bufferReader = new BufferedReader(inputFile);


            //resad all the vars from the file.
            if(bufferReader.readLine().contains("Network")) {
                line = bufferReader.readLine();
                bufferReader.readLine();
                line = line.substring(11,line.length());
                names = line.split(",");

                for (int i = 0; i < names.length; i++) {

                    line = bufferReader.readLine();

                    if (line.contains("Var")) {
                        Node currentNode = new Node();

                        currentNode.setVarName(line.substring(4));
                        line = bufferReader.readLine().substring(7);
                        currentNode.setValues(line.split(","));

                        line = bufferReader.readLine();
                        numOfParentsValues =1;

                        if (!line.contains("none")) {
                            line= line.substring(9);
                            currentNode.setParents(line.split(","));

                            for (String parent : currentNode.getParents()) {
                                    if (Vars.containsKey(parent)) {
                                        Node node = Vars.get(parent);
                                        if (node.getValues() != null) {
                                            numOfParentsValues *= node.getValues().length;
                                        }
                                }
                            }
                        }else {
                            currentNode.setParents(new String[0]);
                        }

                        rows = numOfParentsValues;

                        int queryPart = currentNode.getParents().length;
                        int evidancePart =  (currentNode.getValues().length - 1);

                        columns = queryPart + evidancePart;

                        CPT = new Variable[rows][columns];
                        bufferReader.readLine();

                        for (int j = 0; j < CPT.length; j++) {
                            line = bufferReader.readLine();
                            cptLine = line.split(",");
                            int wordsCount = 0;

                            for (int k = 0; k < CPT[0].length; k++) {
                                if(cptLine[wordsCount].contains("=")){
                                    CPT[j][k] = new Variable(cptLine[wordsCount++].substring(1),cptLine[wordsCount++]);
                                }else {
                                    CPT[j][k] = new Variable(currentNode.getParents()[k], cptLine[wordsCount++]);
                                }
                            }

                        }
                        currentNode.setCPT(CPT);
                        Vars.put(currentNode.getVarName(), currentNode);
                        bufferReader.readLine();

                    }
                }
            }

//
//            for (Map.Entry<String , Node> varEntry : Vars.entrySet()){
//                System.out.println(varEntry.getValue());
//            }

            // read all the queries from the file
            if(bufferReader.readLine().contains("Queries")){
                Queries  = new ArrayList<>();
                while((line = bufferReader.readLine()) != null){
                    ArrayList<Variable> evidenceList = new ArrayList<>();
                    String [] queryPart = line.substring(line.indexOf("(")+1,line.indexOf("|")).split("=");
                    String [] evidencePart = line.substring(line.indexOf("|")+1,line.indexOf(")")).split(",");
                    int algoNum = Integer.parseInt(line.substring(line.indexOf(")")+2));


                    for (String evidence : evidencePart) {
                        String [] split = evidence.split("=");
                        evidenceList.add(new Variable(split[0],split[1]));
                    }

                    Queries.add(new myQuery(new Variable(queryPart[0],queryPart[1]), evidenceList,algoNum));
                }
            }

//            for (myQuery query : Queries){
//                System.out.println(query);
//            }

            bufferReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<myQuery> getQueries() {
        return Queries;
    }

    public void setQueries(ArrayList<myQuery> queries) {
        Queries = queries;
    }


    public HashMap<String, Node> getVars() {
        return Vars;
    }

    public void setVars(HashMap<String, Node> vars) {
        Vars = vars;
    }
}
