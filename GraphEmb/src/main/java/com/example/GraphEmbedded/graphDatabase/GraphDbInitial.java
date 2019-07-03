package com.example.GraphEmbedded.graphDatabase;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.springframework.stereotype.Component;


import java.io.File;


public class GraphDbInitial {

    public GraphDbInitial() {
        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(new File("C:/Users/b0v00ao/.Neo4jDesktop/neo4jDatabases/database-b85d6e20-5386-4d27-ad31-455e7909abd9/installation-3.5.5/data/databases/graph.db"));


        Transaction tx=graphDb.beginTx();
        try{
        graphDb.execute("LOAD CSV FROM 'file:///C:/Users/b0v00ao/Desktop/neo4j-community-3.5.5/import/FC_RDC.csv' AS line\n" +
                "MERGE (a:FC{ID:line[2],Type:'FC'})\n" +
                "MERGE (b:RDC{ID:line[4],Type:'RDC'})\n" +
                "CREATE  (a)-[c:Map{Carrier_Method:line[1],WPM_No:line[3],TNT:toInteger(line[6]),Start_Date:line[7],End_Date:line[8],version:1}]->(b)\n" +
                "WITH a,b,c\n" +
                "MATCH p=(f:FC)-[m:Map{Carrier_Method:'91'}]->(r:RDC)\n" +
                "SET m.WPM_No='-1'");

        graphDb.execute("LOAD CSV FROM 'file:///C:/Users/b0v00ao/Desktop/neo4j-community-3.5.5/import/RDC_STORE.csv' AS line\n" +
                "MERGE (m:RDC{ID:line[4],Type:'RDC'})\n" +
                "MERGE (n:Store{ID:line[5],Type:'Store'})\n" +
                "CREATE (m)-[:Map{TNT:toInteger(line[6]),Start_Date:line[7],End_Date:line[8],version:1}]->(n)");


        graphDb.execute("LOAD CSV FROM 'file:///C:/Users/b0v00ao/Desktop/neo4j-community-3.5.5/import/WPM_RDC.csv' AS line\n" +
                "MERGE (m:WPM{ID:line[3],Type:'WPM'})\n" +
                "MERGE (n:RDC{ID:line[4],Type:'RDC'})\n" +
                "CREATE (m)-[:Map{TNT:toInteger(line[6]),Start_Date:line[7],End_Date:line[8],version:1}]->(n)");

        graphDb.execute("MATCH (f:FC)-[re:Map{Carrier_Method:'81'}]->(r:RDC)\n" +
                "MERGE (w:WPM{ID:re.WPM_No,Type:'WPM'})\n" +
                "CREATE (f)-[:Map{TNT:re.TNT,Carrier_Method:'81',Start_Date:re.Start_Date,End_Date:re.End_Date,version:1}]->(w)\n" +
                "DELETE re");

        graphDb.execute("match (n:FC)-[r:Map]->(w:RDC)\n" +
                "remove r.WPM_No");

        tx.success();
        }finally {
            tx.close();
        }


        graphDb.shutdown();
    }


    public static void main(String[] args) {
        GraphDbInitial graphdb=new GraphDbInitial();
    }


}
