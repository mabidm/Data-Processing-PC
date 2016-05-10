package com.ucla.max.DataProducer;

import java.io.*;
import java.net.*;
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
	
	public static void startServer() {
		System.out.printf("Preparing to start server...\n");
		
		ServerSocket echoServer = null;
        String line;
        // DataInputStream is;
        BufferedReader is;
        PrintStream os;
        Socket clientSocket = null;
		// Try to open a server socket on port 9999
		// Note that we can't choose a port less than 1023 if we are not
		// privileged users (root)
        
        System.out.printf("Initializing Socket...\n");
        try {
           echoServer = new ServerSocket(9900);
        } catch (IOException e) {
           System.out.println(e);
        }   
		// Create a socket object from the ServerSocket to listen and accept 
		// connections.
		// Open input and output streams
	    try {
	           clientSocket = echoServer.accept();
	           // is = new DataInputStream(clientSocket.getInputStream());
	           is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	           os = new PrintStream(clientSocket.getOutputStream());
	           // As long as we receive data, echo that data back to the client.
	           
	           System.out.printf("Connection with client established. Listening for incoming messages...\n");
	           while (true) {
	             line = is.readLine();
	             os.println(line); 
	             System.out.printf("Echoed the message from client.\n");
	           }
	     } catch (IOException e) {
	           System.out.println(e);
	     }
	    
	    System.out.printf("Closing sockets...");
	    try {
	        clientSocket.close();
	        echoServer.close();
	    } catch (IOException exception) {
	    	System.out.println(exception);
	    }
		
	}
	
	public static void main(String args[]) {
		
		// startProducer();
		startServer();
	}
}