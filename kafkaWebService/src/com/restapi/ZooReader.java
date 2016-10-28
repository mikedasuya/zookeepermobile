package com.restapi;

import java.io.*;
import java.util.List;
import java.util.Vector;

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

import com.constants.Constants;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.ZooDefs.Ids;


// Extend HttpServlet class

public class ZooReader extends HttpServlet {
 
  private String message;
  static ZooKeeper zk ;
  List<String> output = new Vector<String>();
  
  public void init() throws ServletException
  {
      // Do required initialization
      message = "Hello World";
  }
  
 
  


public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type
      response.setContentType("text/html");
      try {
		 	zk = new ZooKeeper(Constants.Server, Constants.port, null);
		 	List<String> data = getData(zk);
		 	System.out.println("-------data response--" + data.toString());
		 	response.getWriter().write(data.toString());
		 	response.getWriter().flush();
		 	response.getWriter().close();
			
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
    response.setStatus(200);
    //PrintWriter out = response.getWriter();
    //out.println("<h1>" + message + "</h1>");
      
  }
void addToOutPut(List<String> input, int count)
{
	output.add(String.valueOf(count));
	output.addAll(input);
	output.add("$");
	
}

	String bfs(List<String> input, String root, ZooKeeper zk2, int count)
	{
		addToOutPut(input, count);
		List<String> list = new Vector<String>();
		for(int i = 0; i < input.size(); i++)
		{
			String result1 = root + "/" + input.get(i);
			try {
				
				List<String> input1 = zk2.getChildren(result1, null);
				list.addAll(input1);
				
				
			} catch (KeeperException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}



	String f1DfsString(List<String> input, String root, ZooKeeper zk2)
	{
		
		for(int i = 0; i < input.size(); i++)
		{
			String result1 = root + "/" + input.get(i);
			try {
				if(root.length() == 0)
				{
					output.add("START PERSON");
				}
				List<String> input1 = zk2.getChildren(result1, null);
				String res  = f1DfsString(input1, result1, zk2);
				if(res != null)
				{
					output.add(res);
					output.add("$");
				}
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return root;
	}


  
  private List<String> getData(ZooKeeper zk2) {
	// TODO Auto-generated method stub
	try {
		
		List<String> childList = zk2.getChildren("/", null);
		List<String> actualPerson = new Vector<String>();
		System.out.println(actualPerson.toString());
		for(int i = 0; i < childList.size(); i++)
		{
			System.out.println(childList.get(i));
			if(childList.get(i).contains("person"))
			{
				actualPerson.add(childList.get(i));
			}
			
		}
		System.out.println(actualPerson.toString());
		f1DfsString(actualPerson, "", zk2);
			
	} catch (KeeperException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return output;
	
}





public void destroy()
  {
      // do nothing.
  }
}