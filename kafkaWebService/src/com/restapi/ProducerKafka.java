package com.restapi;

//import util.properties packages
import java.util.Properties;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

import org.apache.kafka.clients.producer.Producer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

//Create java class named “SimpleProducer”
public class ProducerKafka {
   
	public static Properties getProperties()
	{
		 Properties props = new Properties();
	      
	      //Assign localhost id
	      props.put("bootstrap.servers", "localhost:9092");
	      
	      //Set acknowledgements for producer requests.      
	      props.put("acks", "all");
	      
	      //If the request fails, the producer can automatically retry,
	      props.put("retries", 0);
	      
	      //Specify buffer size in config
	      props.put("batch.size", 16384);
	      
	      //Reduce the no of requests less than 0   
	      props.put("linger.ms", 1);
	      
	      //The buffer.memory controls the total amount of memory available to the producer for buffering.   
	      props.put("buffer.memory", 33554432);
	      
	      props.put("key.serializer", 
	         "org.apache.kafka.common.serialization.StringSerializer");
	         
	      props.put("value.serializer", 
	         "org.apache.kafka.common.serialization.StringSerializer");
	      return props;
	}
	
	
	public static void sendDataToShard(String topicName, String data)
	{
		Producer<String, String> producer = new KafkaProducer
		         <String, String>(getProperties());
		System.out.println("Kafka lib sending" + data);
		
		 producer.send(new ProducerRecord<String, String>(topicName, 
		            topicName, data));
		 producer.close();
	}
	
   public static void main(String[] args) throws Exception{
      
      // Check arguments length value
          
      //Assign topicName to string variable
      String topicName = "Yahoo";
      
      // create instance for properties to access producer configs   
     
      
      Producer<String, String> producer = new KafkaProducer
         <String, String>(getProperties());
            
      for(int i = 0; i < 10; i++)
      {
         producer.send(new ProducerRecord<String, String>(topicName, 
            Integer.toString(i), Integer.toString(i)));
               System.out.println("Message sent successfully");
      }
      producer.close();
      sendDataToShard("Yahoo", "test");
   }
}