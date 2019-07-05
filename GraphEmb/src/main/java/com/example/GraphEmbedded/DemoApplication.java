/*******************************
created by Vyshnavi Bendi on 4/5/2019

*/




package com.example.GraphEmbedded;
import com.example.GraphEmbedded.domain.GraphMap;
import com.example.GraphEmbedded.domain.GraphMapCollection;
import com.example.GraphEmbedded.producer.KafkaProducerNeo4j;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class DemoApplication {




    public static void main(String[] args) throws IOException {
        //INITIALIZE THIS OBJECT!!!!
       /// GraphMap map=new GraphMap();
      ///  String str=map.toJson();
        //NOW CALL PRODUCER SOMEHOW FROM HERE
       /// new KafkaProducerNeo4j(str);


// HERE ONLY I HAVE TO WRITE CODE FOR TAKING INPUT FROM A JSON FILE

    // new KafkaProducerNeo4j("{\"type\":\"FC_RDC\",\"fromnode\":\"8306\",\"tonode\":\"6006\",\"tnt\":2,\"start_date\":\"16-JUL-16\",\"end_date\":\"27-DEC-50\"}");


     if(true) {
         ObjectMapper mapper = new ObjectMapper();
         GraphMapCollection graphcollection = mapper.readValue(new File("C:/Users/b0v00ao/Desktop/export.json"), GraphMapCollection.class);

         for (int i = 0; i < graphcollection.getItems().size(); i++) {

             GraphMap map = graphcollection.getItems().get(i);
             String str = map.toJson();

             //NOW CALL PRODUCER FROM HERE
             new KafkaProducerNeo4j(str);
         }

     }





    }
}
