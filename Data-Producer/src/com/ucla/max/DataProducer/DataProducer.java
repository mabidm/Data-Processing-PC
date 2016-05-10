package com.ucla.max.DataProducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class DataProducer {
	
	public static Integer[] num = {14, 32, 50, 68, 86, 90, 108};
	
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
		 for(int i = 0; i < num.length; i++) {
			 System.out.printf("Sending data... num = %d\n", num[i]);
		     producer.send(new ProducerRecord<String, String>("temperature", Integer.toString(i), Integer.toString(num[i]))); // send to topic "temperature"
		 }
		 
		 producer.close();
	}
	
	public static void main(String args[]) {
		startProducer();
	}
}
