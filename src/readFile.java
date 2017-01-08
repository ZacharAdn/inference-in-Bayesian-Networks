import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by zahar on 01/01/17.
 */
public class readFile {

    private ArrayList<myQuery> Queries;
    private HashMap<String, Var> Vars;

    public readFile(String fileName) {
        Vars = new HashMap<>();
        int rows,columns,numOfParentsValues;
        String line;
        myParameter[][] CPT;
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
                        Var currentVar = new Var();

                        currentVar.setVarName(line.substring(4));
                        line = bufferReader.readLine().substring(7);
                        currentVar.setValues(line.split(","));

                        line = bufferReader.readLine();
                        numOfParentsValues =1;

                        if (!line.contains("none")) {
                            line= line.substring(9);
                            currentVar.setParents(line.split(","));

                            for (String parent : currentVar.getParents()) {
                                    if (Vars.containsKey(parent)) {
                                        Var var = Vars.get(parent);
                                        if (var.getValues() != null) {
                                            numOfParentsValues *= var.getValues().length;
                                        }
                                }
                            }
                        }else {
                            currentVar.setParents(new String[0]);
                        }

                        rows = numOfParentsValues;

                        int queryPart = currentVar.getParents().length;
                        int evidancePart =  (currentVar.getValues().length - 1);

                        columns = queryPart + evidancePart;

                        CPT = new myParameter[rows][columns];
                        bufferReader.readLine();

                        for (int j = 0; j < CPT.length; j++) {
                            line = bufferReader.readLine();
                            cptLine = line.split(",");
                            int wordsCount = 0;

                            for (int k = 0; k < CPT[0].length; k++) {
                                if(cptLine[wordsCount].contains("=")){
                                    CPT[j][k] = new myParameter(cptLine[wordsCount++].substring(1),cptLine[wordsCount++]);
                                }else {
                                    CPT[j][k] = new myParameter(currentVar.getParents()[k], cptLine[wordsCount++]);
                                }
                            }

                        }
                        currentVar.setCPT(CPT);
                        Vars.put(currentVar.getVarName(),currentVar);
                        bufferReader.readLine();

                    }
                }
            }


            for (Map.Entry<String , Var> varEntry : Vars.entrySet()){
                System.out.println(varEntry.getValue());
            }

            // read all the queries from the file
            if(bufferReader.readLine().contains("Queries")){
                Queries  = new ArrayList<>();
                while((line = bufferReader.readLine()) != null){
                    ArrayList<myParameter> Evidences = new ArrayList<>();
                    String [] queryPart = line.substring(line.indexOf("(")+1,line.indexOf("|")).split("=");
                    String [] evidencePart = line.substring(line.indexOf("|")+1,line.indexOf(")")).split(",");
                    int algoNum = Integer.parseInt(line.substring(line.indexOf(")")+2));


                    for (String evi : evidencePart) {
                        String [] ek = evi.split("=");
                        Evidences.add(new myParameter(ek[0],ek[1]));
                    }

                    Queries.add(new myQuery(new myParameter(queryPart[0],queryPart[1]), Evidences,algoNum));
                }
            }

            for (myQuery query : Queries){
                System.out.println(query);
            }

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


    public HashMap<String, Var> getVars() {
        return Vars;
    }

    public void setVars(HashMap<String, Var> vars) {
        Vars = vars;
    }
}
