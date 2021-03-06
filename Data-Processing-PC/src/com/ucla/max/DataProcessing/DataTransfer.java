package com.ucla.max.DataProcessing;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class DataTransfer {
	public static void startProducer() {
		System.out.printf("startProducer() called.\n");
		
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092"); // producer port 9092
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 Producer<String, String> producer = new KafkaProducer<>(props);
		 for(int i = 0; i < 100; i++) {
			 System.out.printf("Sending data... i = %d\n", i);
		     producer.send(new ProducerRecord<String, String>("temperature", Integer.toString(1), Integer.toString(i))); // send to topic "temperature"
		 }
		 
		 producer.close();
	}
	
	public static void startConsumer() {
		System.out.printf("startConsumer() called.\n");
		
	     Properties props = new Properties();
	     props.put("bootstrap.servers", "localhost:9092"); // consumer port 9092; in Terminal consumer port is 2181.
	     props.put("group.id", "tempSensor"); // ID of the consumer group
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("temperature")); // subscribe to topic "temperature"
	     
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
	     }
	}
	

	 public static void main(String[] args) {
		System.out.printf("Hello Kafka!\n");

		DataProcessing.processKafkaData();
		
	}
}
