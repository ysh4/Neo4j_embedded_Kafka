package com.example.GraphEmbedded.openGraph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

public class OpenGraphDb {
    private static GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
    private static GraphDatabaseService graphDb=graphDbFactory.newEmbeddedDatabase(new File("C:/Users/b0v00ao/Desktop/graphdb"));

    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }



}
