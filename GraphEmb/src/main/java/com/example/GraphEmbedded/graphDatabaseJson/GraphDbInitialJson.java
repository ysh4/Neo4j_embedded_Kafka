package com.example.GraphEmbedded.graphDatabaseJson;

import com.example.GraphEmbedded.domain.GraphMap;
import com.example.GraphEmbedded.openGraph.OpenGraphDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphDbInitialJson {

    public GraphDbInitialJson(String str) {




        ObjectMapper obj=new ObjectMapper();

        //map object
        GraphMap map= null;
        try {
            map = obj.readValue(str, GraphMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //check the message received is a GraphMap type or not
        if (map.check()) {

            //check to which type the input data belongs to
            if(map.getType().equals("FC_RDC")){
                implementationFc_Rdc(map);
            }
            else if(map.getType().equals("FC_WPM")){
                implementationFc_Wpm(map);
            }
            else if(map.getType().equals("WPM_RDC")){
                implementationWpm_Rdc(map);
            }
            else if(map.getType().equals("RDC_STORE")){
                implementationRdc_Store(map);
            }

        }

    }




    private void implementationFc_Rdc(GraphMap fc_rdc)  {
     //   GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
     //   GraphDatabaseService graphDb=graphDbFactory.newEmbeddedDatabase(new File("C:/Users/b0v00ao/Desktop/graphdb"));

      GraphDatabaseService graphDb=new OpenGraphDb().getGraphDb();

        //Object obj=new JSONParser().parse(new FileReader("C:/Users/b0v00ao/Desktop/export.json"));

        //JSONObject jo=(JSONObject) obj;
        //String jsonString="{"FCID":"3559","RDCID":"6092","tnt":1,"Start_Date":"26-DEC-15","End_Date":"31-DEC-50","Version":1}";
        Map<String,Object>param=new HashMap<>();
        param.put("fcid",fc_rdc.getFromnode());
        param.put("rdcid",fc_rdc.getTonode());
        param.put("tnt",fc_rdc.getTnt());
        param.put("start_date",fc_rdc.getStart_date());
        param.put("end_date",fc_rdc.getEnd_date());
        param.put("version",fc_rdc.getVersion());

        Transaction tx = graphDb.beginTx();
        try {
            graphDb.execute("MERGE (a:FC{ID:{fcid}})\n" +
                                      "MERGE (b:RDC{ID:{rdcid}})\n",param);

             Result result = graphDb.execute(
                    "OPTIONAL MATCH p=(a:FC{ID:{fcid}})-[r:Map]->(b:RDC{ID:{rdcid}})\n" +
                    "RETURN CASE \n" +
                    "WHEN p is null then 0\n" +
                    "WHEN r.version<{version} THEN 1\n" +
                    "WHEN r.version>={version} then 2\n" +
                    "else 3\n " +
                    "END AS ans", param);

            long ans=0;
            if (result.hasNext()) {
                Map<String,Object>map=result.next();
                ans=(long)map.get("ans");
                //ans=result.next().values().;
            }
            result.close();
            if(ans==0){
                graphDb.execute("match (a:FC{ID:{fcid}})\n" +
                        "match (b:RDC{ID:{rdcid}})\n" +
                        "with a,b\n" +
                        "create (a)-[:Map{Start_Date:{start_date},End_Date:{end_date},version:{version},tnt:{tnt}}]->(b)",param);
            }
            else if(ans==1){
                graphDb.execute("match (a:FC{ID:{fcid}})\n" +
                        "match (b:RDC{ID:{rdcid}})\n" +
                        "with a,b\n" +
                        "match p=(a)-[r:Map]->(b)\n" +
                        "set r.Start_Date={start_date}\n" +
                        "set r.End_Date={end_date}\n" +
                        "set r.version={version}\n" +
                        "set r.tnt={tnt}\n"
                        ,param);
            }

            else if(ans==2){
                System.out.println("RELATION  ALREADY  EXISTS");
            }
            else{
                System.out.println("SOMETHING  WENT  WRONG");
            }
            tx.success();
        }finally {
            tx.close();
        }
      //  graphDb.shutdown();


    }
    private void implementationFc_Wpm(GraphMap fc_wpm)  {
     //   GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
       // GraphDatabaseService graphDb=graphDbFactory.newEmbeddedDatabase(new File("C:/Users/b0v00ao/Desktop/graphdb"));
       GraphDatabaseService graphDb=new OpenGraphDb().getGraphDb();

        Map<String,Object>param=new HashMap<>();
        param.put("fcid",fc_wpm.getFromnode());
        param.put("wpmid",fc_wpm.getTonode());
        param.put("tnt",fc_wpm.getTnt());
        param.put("start_date",fc_wpm.getStart_date());
        param.put("end_date",fc_wpm.getEnd_date());
        param.put("version",fc_wpm.getVersion());
        Transaction tx=graphDb.beginTx();
        try{
            graphDb.execute("MERGE (a:FC{ID:{fcid}})\n" +
                    "MERGE (b:WPM{ID:{wpmid}})\n",param);

            Result result = graphDb.execute(
                    "OPTIONAL MATCH p=(a:FC{ID:{fcid}})-[r:Map]->(b:WPM{ID:{wpmid}})\n" +
                            "RETURN CASE \n" +
                            "WHEN p is null then 0\n" +
                            "WHEN r.version<{version} THEN 1\n" +
                            "WHEN r.version>={version} then 2\n" +
                            "else 3\n " +
                            "END AS ans", param);

            long ans=0;
            if (result.hasNext()) {
                Map<String,Object>map=result.next();
                ans=(long)map.get("ans");
                //ans=result.next().values().;
            }
            result.close();
            if(ans==0){
                graphDb.execute("match (a:FC{ID:{fcid}})\n" +
                        "match (b:WPM{ID:{wpmid}})\n" +
                        "with a,b\n" +
                        "create (a)-[:Map{Start_Date:{start_date},End_Date:{end_date},version:{version},tnt:{tnt}}]->(b)",param);
            }
            else if(ans==1){
                graphDb.execute("match (a:FC{ID:{fcid}})\n" +
                                "match (b:WPM{ID:{wpmid}})\n" +
                                "with a,b\n" +
                                "match p=(a)-[r:Map]->(b)\n" +
                                "set r.Start_Date={start_date}\n" +
                                "set r.End_Date={end_date}\n" +
                                "set r.version={version}\n" +
                                "set r.tnt={tnt}\n"
                        ,param);
            }

            else if(ans==2){
                System.out.println("RELATION  ALREADY  EXISTS");
            }
            else{
                System.out.println("SOMETHING  WENT  WRONG");
            }
            tx.success();
        }finally {
            tx.close();
        }
     //   graphDb.shutdown();


    }
    private void implementationWpm_Rdc(GraphMap wpm_rdc) {
      //  GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
      //  GraphDatabaseService graphDb=graphDbFactory.newEmbeddedDatabase(new File("C:/Users/b0v00ao/Desktop/graphdb"));
        GraphDatabaseService graphDb=new OpenGraphDb().getGraphDb();
        Map<String,Object>param=new HashMap<>();
        param.put("wpmid",wpm_rdc.getFromnode());
        param.put("rdcid",wpm_rdc.getTonode());
        param.put("tnt",wpm_rdc.getTnt());
        param.put("start_date",wpm_rdc.getStart_date());
        param.put("end_date",wpm_rdc.getEnd_date());
        param.put("version",wpm_rdc.getVersion());
        Transaction tx=graphDb.beginTx();
        try{
            graphDb.execute("MERGE (a:WPM{ID:{wpmid}})\n" +
                    "MERGE (b:RDC{ID:{rdcid}})\n",param);

            Result result = graphDb.execute(
                    "OPTIONAL MATCH p=(a:WPM{ID:{wpmid}})-[r:Map]->(b:RDC{ID:{rdcid}})\n" +
                            "RETURN CASE \n" +
                            "WHEN p is null then 0\n" +
                            "WHEN r.version<{version} THEN 1\n" +
                            "WHEN r.version>={version} then 2\n" +
                            "else 3\n " +
                            "END AS ans", param);

            long ans=0;
            if (result.hasNext()) {
                Map<String,Object>map=result.next();
                ans=(long)map.get("ans");
                //ans=result.next().values().;
            }
            result.close();
            if(ans==0){
                graphDb.execute("match (a:WPM{ID:{wpmid}})\n" +
                        "match (b:RDC{ID:{rdcid}})\n" +
                        "with a,b\n" +
                        "create (a)-[:Map{Start_Date:{start_date},End_Date:{end_date},version:{version},tnt:{tnt}}]->(b)",param);
            }
            else if(ans==1){
                graphDb.execute("match (a:WPM{ID:{wpmid}})\n" +
                                "match (b:RDC{ID:{rdcid}})\n" +
                                "with a,b\n" +
                                "match p=(a)-[r:Map]->(b)\n" +
                                "set r.Start_Date={start_date}\n" +
                                "set r.End_Date={end_date}\n" +
                                "set r.version={version}\n" +
                                "set r.tnt={tnt}\n"
                        ,param);
            }

            else if(ans==2){
                System.out.println("RELATION  ALREADY  EXISTS");
            }
            else{
                System.out.println("SOMETHING  WENT  WRONG");
            }
            tx.success();
        }finally {
            tx.close();
        }
      // graphDb.shutdown();


    }

    private void implementationRdc_Store(GraphMap rdc_store) {

      //  GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
        //GraphDatabaseService graphDb=graphDbFactory.newEmbeddedDatabase(new File("C:/Users/b0v00ao/Desktop/graphdb"));
      GraphDatabaseService graphDb=new OpenGraphDb().getGraphDb();
        Map<String,Object>param=new HashMap<>();
        param.put("rdcid",rdc_store.getFromnode());
        param.put("storeid",rdc_store.getTonode());
        param.put("tnt",rdc_store.getTnt());
        param.put("start_date",rdc_store.getStart_date());
        param.put("end_date",rdc_store.getEnd_date());
        param.put("version",rdc_store.getVersion());
        Transaction tx=graphDb.beginTx();
        try{
            graphDb.execute("MERGE (a:RDC{ID:{rdcid}})\n" +
                    "MERGE (b:Store{ID:{storeid}})\n",param);

            Result result = graphDb.execute(
                    "OPTIONAL MATCH p=(a:RDC{ID:{rdcid}})-[r:Map]->(b:Store{ID:{storeid}})\n" +
                            "RETURN CASE \n" +
                            "WHEN p is null then 0\n" +
                            "WHEN r.version<{version} THEN 1\n" +
                            "WHEN r.version>={version} then 2\n" +
                            "else 3\n " +
                            "END AS ans", param);

            long ans=0;
            if (result.hasNext()) {
                Map<String,Object>map=result.next();
                ans=(long)map.get("ans");
                //ans=result.next().values().;
            }
            result.close();
            if(ans==0){
                graphDb.execute("match (a:RDC{ID:{rdcid}})\n" +
                        "match (b:Store{ID:{storeid}})\n" +
                        "with a,b\n" +
                        "create (a)-[:Map{Start_Date:{start_date},End_Date:{end_date},version:{version},tnt:{tnt}}]->(b)",param);
            }
            else if(ans==1){
                graphDb.execute("match (a:RDC{ID:{rdcid}})\n" +
                                "match (b:Store{ID:{storeid}})\n" +
                                "with a,b\n" +
                                "match p=(a)-[r:Map]->(b)\n" +
                                "set r.Start_Date={start_date}\n" +
                                "set r.End_Date={end_date}\n" +
                                "set r.version={version}\n" +
                                "set r.tnt={tnt}\n"
                        ,param);
            }

            else if(ans==2){
                System.out.println("RELATION  ALREADY  EXISTS");
            }
            else{
                System.out.println("SOMETHING  WENT  WRONG");
            }
            tx.success();
        }finally {
            tx.close();
        }
      //  graphDb.shutdown();
    }


}
