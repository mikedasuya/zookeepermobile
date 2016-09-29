
package com.zooclient;
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.ZooDefs.Ids;

import com.constants.*;

public class EventsToZooKeeper
{
	static ZooKeeper zk ;
	
	static void functionCreateNode(String path)
	{
		 try {
			if(zk.exists(path, true) == null)
			 {
				 zk.create(path, Constants.data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			 }
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void sendData(String path, String data)
	{
		try {
			if(zk.exists(path, true) != null)
			{
				Stat stat = new Stat();
				zk.getData(path, false, stat);
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
	
	public static void main(String args[])
	{
		 try {
			 	zk = new ZooKeeper(Constants.Server, Constants.port, null);
			 	functionCreateNode(Constants.path);
			 	functionCreateNode(Constants.path1);
			 	sendData(Constants.path, Constants.data);
			 	sendData(Constants.path1, Constants.data);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}