package com.kafka.base;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerBase
{
	public Properties getPropsShard1()
	{
		 Properties props = new Properties();
	      
	      props.put("bootstrap.servers", "localhost:9092");
	      props.put("group.id", "testing");
	      props.put("enable.auto.commit", "true");
	      props.put("auto.commit.interval.ms", "1000");
	      props.put("session.timeout.ms", "30000");
	      props.put("key.deserializer", 
	         "org.apache.kafka.common.serialization.StringDeserializer");
	      props.put("value.deserializer", 
	         "org.apache.kafka.common.serialization.StringDeserializer");
	       return props;
	}
	
	
	
}