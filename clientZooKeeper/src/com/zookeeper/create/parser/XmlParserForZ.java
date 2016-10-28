package com.zookeeper.create.parser;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.zookeeper.create.CreateNodes;

public class XmlParserForZ
{
	
	void addToZooKeeper(String namePerson, String number, String nameApplication, String namePlayList, String time)
	{
		System.out.println("Name" + namePerson);
		System.out.println("Number" + number);
		System.out.println("Application" + nameApplication);
		System.out.println("PlayList" + namePlayList);
		System.out.println("time" + time);
		
		CreateNodes crNodes = CreateNodes.getSingleton();
		String pathAddNode  = crNodes.createNodesFromData("/","person", namePerson);
		if(pathAddNode != null)
		{
			pathAddNode = crNodes.createNodesFromData(pathAddNode, "number", number);
		}
		if(pathAddNode != null)
		{
			pathAddNode = crNodes.createNodesFromData(pathAddNode, "application", nameApplication);
		}
		if(pathAddNode != null)
		{
			pathAddNode = crNodes.createNodesFromData(pathAddNode, "playlist", namePlayList);
		}
		if(pathAddNode != null)
		{
			crNodes.createNodesFromData(pathAddNode, "time", time);
		}
		
	}
	
	public void parseAndSendtoZooKeeoper(String xmlString)
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setNamespaceAware(true);
		try {
			SAXParser saxParser = spf.newSAXParser();
			XMLReader xmlReader  = saxParser.getXMLReader();
			xmlReader.setContentHandler(new SaxHandler());
			xmlReader.parse(new InputSource(new StringReader(xmlString)));
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}