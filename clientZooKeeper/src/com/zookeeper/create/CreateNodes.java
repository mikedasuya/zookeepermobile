package com.zookeeper.create;

import java.awt.List;
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import com.constants.Constants;

public class CreateNodes
{
	static CreateNodes crNodes = null;
	static ZooKeeper zk = null;
	static ZooNodeHelper zoohelper = null;
	
	public static CreateNodes getSingleton()
	{
		if(crNodes == null)
			{
				crNodes = new CreateNodes();
				
			}
		return crNodes;
	
	}

	private CreateNodes()
	{
		try {
			zk  = new ZooKeeper(Constants.Server, Constants.port, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zoohelper = new ZooNodeHelper(zk);
		
	}
	
	
	public String functionCreateNode(String parent, String child, String data)
	{
		String res = null;
		 try {
			 String childNode = zoohelper.getNode(parent, data);
			 System.out.printf("zoohelper Trying to adding parent :%s: child :%s: \n" , parent, child);
			 if( childNode == null)
			 {
				 String apath = zoohelper.getFullPath(parent, child);
				 res = zk.create(apath, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
				 
			 }
			 else
			 {
				 System.out.println("ZNode already exist :" + childNode + "Value  :" + data);
				 String apath = zoohelper.getFullPath(parent, childNode);
				 res = apath;
			 }
			 
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	 
	public void sendData(String parent, String path, String data)
	{
		try {
			if(zoohelper.getNode(parent, data) != null)
			{
				Stat stat = zk.exists(path, false);
				zk.setData(path, data.getBytes(), stat.getVersion());
			}
			else
			{
				System.out.println("Node not present" + path);
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String createNodesFromData(String parent, String path, String data)
	{
		return functionCreateNode(parent, path, data);
		//sendData(parent, path, data);
	}
}