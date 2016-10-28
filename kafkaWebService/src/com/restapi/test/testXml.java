package com.restapi.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class testXml
{
	
	public static void main(String args[])
	{
		String str = "<Person name= 'test'> "
				+ " <PhoneNumber number='12345678'>"
				
				+ " <Application name='Yahoo Mail'>"
				+ "<PlayList name='' time='' />"
				+ "</Application>"
				
				+ "<Application name='test'> "
				+ "<PlayList name='' time='' />"
				+ "</Application>"
				
				+ "</PhoneNumber>"
				+ "</Person>";
		
		parseXml(str);
		
	}
	public static void parseXml(String str)
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
			System.out.println(person);
			System.out.println(phoneNumber);
			// Find all nodes with the attribute of type equal to `t1`
			// You could use //*/a[@type='t1'] if you wanted to narrow it down
			// This find ALL matches through out the document...
			String expression = "//Application[@name='Yahoo']";
			XPath xPath = XPathFactory.newInstance().newXPath();
			Object result = xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			NodeList nodesApplication = (NodeList) result;
			
			createDocument(nodesApplication, person, phoneNumber);
		
	        
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	  }

	  private static String getPhoneNumber(org.w3c.dom.Document doc) {
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



	private static String getPerson(Node item) {
		// TODO Auto-generated method stub
		Element element = (Element)item;
		String name = element.getAttribute("name");
		return name;
	}



	private static void createDocument(NodeList nodes, String personName, String phoneNumber ) {
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
			DOMSource domSource = new DOMSource(d2);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			System.out.println("XML IN String format is: \n" + writer.toString());
			
		  
		  }
		  catch(Exception e)
		  {
				System.out.println(e);
		  }
		  
	  
	}



	
	
}