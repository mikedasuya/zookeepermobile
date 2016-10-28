package com.kafka;

import java.util.Properties;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.kafka.base.ConsumerBase;
import com.kafka.base.ConsumerInterface;
import com.zookeeper.create.parser.XmlParserForZ;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;


public class ConsumerKafkaShard1  extends ConsumerBase implements ConsumerInterface
{
	
	ConsumerKafkaShard1()
	{
		
	}
	public static void main(String args[])
	{
      
	  String topicName = new String("Yahoo");	
      ConsumerBase base = new ConsumerBase();
           
      //Kafka Consumer subscribes list of topics here.
      
      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(base.getPropsShard1());
      //get the topic from config
      consumer.subscribe(Arrays.asList(topicName));
      //print the topic name
      System.out.println("Subscribed to topic " + topicName);
      int i = 0;
	  while (true) {
         ConsumerRecords<String, String> records = consumer.poll(10000);
         for (ConsumerRecord<String, String> record : records)
         {
         
         // print the offset,key and value for the consumer records.
         //add to thread to add to zookeeper
        	 
        	 System.out.printf("offset = %d, key = :%s:, value = %s\n", 
        			 record.offset(), record.key(), record.value());
        	 String s = record.key();
        	 s.trim();
        	 System.out.println("value" + s);
        	 if(s.equals("Yahoo"))
        	 {
        		 
        		 XmlParserForZ xmlParserForZooKeeper = new XmlParserForZ();
        		 xmlParserForZooKeeper.parseAndSendtoZooKeeoper(record.value());
        	 }
        	
         }
         
      }
	}
	
	
}





	
	
	
   
