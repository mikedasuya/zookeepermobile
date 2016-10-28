package com.restapi;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


// Extend HttpServlet class

public class PersonalAssistent extends HttpServlet {
 
  private String message;

  public void init() throws ServletException
  {
      // Do required initialization
      message = "Hello World";
  }
  
 
  


public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type
      response.setContentType("text/html");
      HandleXml xmlParse = new HandleXml();
      try {  
          BufferedReader b = new BufferedReader(request.getReader());  
          StringBuffer xmlBuffer = new StringBuffer();    
          String xmlString = "";          
          while((xmlString = b.readLine()) != null) {  
                 xmlBuffer.append(xmlString);  
          }  
          xmlString = xmlBuffer.toString();  
          if (xmlString.length() > 0) {  
            System.out.println("Got XML: " + xmlString);  
          }      
          else {  
               System.out.println("No XML document received");  
          }  
          xmlParse.parseXml(xmlString);
      	} catch (Exception e) {
      		
  		}
      // Actual logic goes here.
      response.setStatus(200);
      //PrintWriter out = response.getWriter();
      //out.println("<h1>" + message + "</h1>");
      
  }
  
  public void destroy()
  {
      // do nothing.
  }
}