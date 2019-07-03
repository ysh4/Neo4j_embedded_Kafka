package com.example.GraphEmbedded.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerNeo4j {
    public KafkaProducerNeo4j(String str) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "http://kafka.kafka-cluster-shared.non-prod-1.walmart.com:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "3");
        properties.setProperty("linger.ms", "1");

        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        int key=0;
        //for(int key = 0; key < 8; key++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("quicksilver-qa-wm-jet-dcc-p4",
                    Integer.toString(key), str);
            try {
                RecordMetadata metadata = producer.send(producerRecord).get();
                //check the status
                System.out.println(metadata.offset());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        //}
        producer.close();
    }
}
