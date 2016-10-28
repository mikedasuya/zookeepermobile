
package com.restapi;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HandleXml
{
	
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
	
	
	public NodeList getNodeListForApplication(String app, org.w3c.dom.Document doc)
	  {
		  String expression = "//Application[@name='"+app+"']";
		  NodeList nodesApplication = null; 
		  XPath xPath = XPathFactory.newInstance().newXPath();
		  Object result;
			try {
				result = xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
				nodesApplication = (NodeList) result;
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return nodesApplication;
	 }
  
	  
	  
	  public void parseXml(String str)
	  {
		  DocumentBuilder db;
		  try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(new InputSource(new StringReader(str)));
			String person = null;
			String phoneNumber = null;
			NodeList nodes = doc.getElementsByTagName("Person");
			if(nodes.getLength() > 0)
			{
				 person = getPerson(nodes.item(0));
				 phoneNumber = getPhoneNumber(doc);
			}
			// Find all nodes with the attribute of type equal to `t1`
			// You could use //*/a[@type='t1'] if you wanted to narrow it down
			// This find ALL matches through out the document...
			
			NodeList nodesApp =  getNodeListForApplication("Yahoo", doc);
			org.w3c.dom.Document appDoc = createDocument(nodesApp, person, phoneNumber);
			String strYahoo = getString(appDoc);
			System.out.println("------str yahoo-" + strYahoo);
			ProducerKafka kaf = new ProducerKafka();
			kaf.sendDataToShard("Yahoo", strYahoo);
	        
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		 
	  }

	  private String getString(Document appDoc) {
		// TODO Auto-generated method stub
		  try
		    {
		       DOMSource domSource = new DOMSource(appDoc);
		       StringWriter writer = new StringWriter();
		       StreamResult result = new StreamResult(writer);
		       TransformerFactory tf = TransformerFactory.newInstance();
		       Transformer transformer = tf.newTransformer();
		       transformer.transform(domSource, result);
		       return writer.toString();
		    }
		    catch(TransformerException ex)
		    {
		       ex.printStackTrace();
		       return null;
		    }
	}



	private String getPhoneNumber(org.w3c.dom.Document doc) {
		// TODO Auto-generated method stub
		NodeList nodes = doc.getElementsByTagName("PhoneNumber");
		if(nodes.getLength() > 0)
		{
			Element element = (Element)nodes.item(0);
			String name = element.getAttribute("number");
			return name;
		}
		return null;
	}



	private String getPerson(Node item) {
		// TODO Auto-generated method stub
		Element element = (Element)item;
		String name = element.getAttribute("name");
		return name;
	}



	private org.w3c.dom.Document createDocument(NodeList nodes, String personName, String phoneNumber ) {
		// TODO Auto-generated method stub
		  DocumentBuilder db;
		  try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			org.w3c.dom.Document d2 = db.newDocument();
			Element ele = (Element)d2.createElement("Person");
			ele.setAttribute("name", personName);
			d2.appendChild(ele);
			
			Element ele1 = (Element)d2.createElement("PhoneNumber");
			ele1.setAttribute("number", phoneNumber);
			ele.appendChild(ele1);
			
			for (int i = 0; i < nodes.getLength(); i++) {
			    Node node = nodes.item(i);
			    d2.adoptNode(node);
			    ele1.appendChild(node);
			}
			return d2;
		  }
		  catch(Exception e)
		  {
				System.out.println(e.getMessage());
		  }
		  return null;
	  
	}

}