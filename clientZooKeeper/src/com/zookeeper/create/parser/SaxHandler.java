package com.zookeeper.create.parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
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
*/

public class SaxHandler extends DefaultHandler
{
		
	   boolean bPerson = false;
	   boolean bPhoneNumber = false;
	   boolean bAppName = false;
	   boolean bPlayListName = false;
	   String namePerson = null;
	   String number = null;
	   String nameApplication = null;
	   String namePlayList = null;
	   String time = null;
	   XmlParserForZ zooKeep = new XmlParserForZ();

	   @Override
	   public void startElement(String uri, 
	   String localName, String qName, Attributes attributes)
	      throws SAXException {
	      if (qName.equalsIgnoreCase("Person")) {
	         namePerson = attributes.getValue("name");
	      } else if (qName.equalsIgnoreCase("PhoneNumber")) {
	    	  number = attributes.getValue("number");
	          bPhoneNumber = true;
	      } else if (qName.equalsIgnoreCase("Application")) {
	    	  nameApplication = attributes.getValue("name");
	          bAppName = true;
	      } else if (qName.equalsIgnoreCase("PlayList")) {
	    	  namePlayList = attributes.getValue("name");
	    	  time = attributes.getValue("time");
	          bPlayListName = true;
	      }

	   }

	   @Override
	   public void endElement(String uri, 
	   String localName, String qName) throws SAXException {
		  if (qName.equalsIgnoreCase("PlayList")) {
	    	  zooKeep.addToZooKeeper(namePerson, number, nameApplication, namePlayList, time);
	         
	      }
	   }

	   @Override
	   public void characters(char ch[], 
	      int start, int length) throws SAXException {
	      if (bPhoneNumber) {
	    /*     System.out.println("First Name: " 
	            + new String(ch, start, length));
	    */     bPhoneNumber = false;
	      } else if (bAppName) {
	           bAppName = false;
	      } else if (bPlayListName) {
	    	  bPlayListName = false;
	      } 
	   }
} //end sax parser
	
	
