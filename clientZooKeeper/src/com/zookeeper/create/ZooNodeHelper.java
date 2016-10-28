package com.zookeeper.create;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ZooNodeHelper
{
	ZooKeeper zk = null;
	ZooNodeHelper(ZooKeeper zooNode)
	{
		zk = zooNode;
	}
	
	public String getFullPath(String parent, String child)
	{
		String apath = null;
		if(parent == "/")
		{
			apath = parent + child;
		}
		else
		{
			apath = parent + "/" + child;
		}
		return apath;
	}
	
	public String getNode(String parent, String value)
	{
		if(zk == null)
		{
			System.out.println("getNode zk null call the constructor first ");
			return null;
		}
		String result = null;
		 try {
			java.util.List<String> listChildren = zk.getChildren(parent, false);
			for(int i = 0; i < listChildren.size(); i++)
			{
				String path = listChildren.get(i);
				String apath = getFullPath(parent, path);
				System.out.println(apath);
				byte[] b = zk.getData(apath, null, null);
				if(b != null)
				{
					String val = new String(b);
					System.out.printf("From zoo :%s: From inpt :%s: \n", val, value);
					if(val != null && val.equals(value))
					{
						result = path;
						break;
					}
				}
				
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result == null)
		{
			System.out.println("New Node");
		}
		else
		{
			System.out.println("Existing node : " + result);
		}	
		return result;
                   
	}
	
}