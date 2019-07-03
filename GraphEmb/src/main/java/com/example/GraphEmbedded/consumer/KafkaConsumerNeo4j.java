package com.example.GraphEmbedded.consumer;
import com.example.GraphEmbedded.graphDatabaseJson.GraphDbInitialJson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerNeo4j {
    //private static boolean shutdownFlag=false;

 //   public KafkaConsumerNeo4j(boolean flag) {
   //     setShutdownFlag(flag);
    //}

  //  public void setShutdownFlag(boolean flag) {
    //    KafkaConsumerNeo4j.shutdownFlag = flag;
    //}

    public static void main(String args[])  {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "http://kafka.kafka-cluster-shared.non-prod-1.walmart.com:9092");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", "test009");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.commit.interval.ms",  "1000");
        properties.setProperty("auto.offset.reset", "earliest");
        /*properties.load(new FileInputStream("/Users/m0r00z7/IdeaProjects/mcse_data_ingestion"
                + "/mcsedata-listener/src/main/resources/environmentConfig/prod/prod-dfw1/kafka-iso-error-consumer.properties"));*/


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("quicksilver-qa-wm-jet-dcc-p4"));
        while(!ConsumerShutdown.isShutdown()) {
            ConsumerRecords<String, String> consumerRecords =  consumer.poll(100);
            for(ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println("Partition: " + consumerRecord.partition() +
                        " Offset: " + consumerRecord.offset() +
                        " Key: " + consumerRecord.key() +
                        " Value: " + consumerRecord.value());
                //send the value to GraphDbInitialJson
                if(consumerRecord.value() instanceof String){
                try {
                    new GraphDbInitialJson(consumerRecord.value());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


  /*  static  void test() throws IOException, InterruptedException {
        Properties properties = new Properties();
        //        properties.setProperty("bootstrap.servers", "kafka-270894369-5-409605651.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw7.prod.walmart.com:9092,kafka-270894388-2-409606266.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw6.prod.walmart.com:9092,kafka-270894388-1-409606263.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw6.prod.walmart.com:9092,kafka-270894388-3-409606269.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw6.prod.walmart.com:9092,kafka-270894382-1-409605950.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw5.prod.walmart.com:9092,kafka-270894388-4-409606272.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw6.prod.walmart.com:9092,kafka-270894388-5-409606275.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw6.prod.walmart.com:9092,kafka-270894382-2-409605953.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw5.prod.walmart.com:9092,kafka-270894382-3-409605956.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw5.prod.walmart.com:9092,kafka-270894369-4-409605648.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw7.prod.walmart.com:9092,kafka-270894369-3-409605645.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw7.prod.walmart.com:9092,kafka-270894382-4-409605959.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw5.prod.walmart.com:9092,kafka-270894369-2-409605642.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw7.prod.walmart.com:9092,kafka-270894382-5-409605963.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw5.prod.walmart.com:9092,kafka-270894369-1-409605639.prod-dfw.kafka-supplychain-shared1-prod.ms-df-messaging.dfw7.prod.walmart.com:9092");
        //        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        //        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        //        properties.setProperty("group.id", "test00");
        //        properties.setProperty("enable.auto.commit", "true");
        //        properties.setProperty("auto.commit.interval.ms",  "1000");
        //        properties.setProperty("auto.offset.reset", "earliest");
        properties.load(new FileInputStream("/Users/m0r00z7/IdeaProjects/mcse_data_ingestion"
                + "/mcsedata-listener/src/main/resources/environmentConfig/prod/prod-dfw1/kafka-iso-error-consumer.properties"));

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit( new KafkaConsumerThread(properties,"mcse-error-gfs-prod",new AtomicBoolean(true),
                new AtomicBoolean(false),
                new LinkedBlockingQueue<>(),true));
//        executorService.awaitTermination(1, TimeUnit.HOURS);

    }*/
}}






