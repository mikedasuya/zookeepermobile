/**
 * A simple example program to use DataMonitor to start and
 * stop executables based on a znode. The program watches the
 * specified znode and saves the data that corresponds to the
 * znode in the filesystem. It also starts the specified program
 * with the specified arguments when the znode exists and kills
 * the program if the znode goes away.
 */
package com.zookeeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.constants.Constants;

import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class Executor
    implements Watcher, Runnable
{
    String znode;

    ZooKeeper zk;

    String filename;

    String exec[];

    Process child;
    java.util.concurrent.CountDownLatch connectedSignal = new java.util.concurrent.CountDownLatch(1);

    public Executor(String hostPort) throws KeeperException, IOException {
               
        zk = new ZooKeeper(hostPort, Constants.port, this);
        try {
        	System.out.println("waiting");
        	zk.exists(Constants.path, this);
        	zk.exists(Constants.path1, this);
			//connectedSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String hostPort = Constants.Server;
        try {
            new Executor(hostPort).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("going out");
    }

    /***************************************************************************
     * We do process any events ourselves, we just need to forward them on.
     *
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.proto.WatcherEvent)
     */
    public void process(WatchedEvent event) {
        //dm.process(event);
    	String path = event.getPath();
    	System.out.println("Got event");
    	System.out.println(path);
    	
        
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			synchronized(this)
			{
		
				try {
					this.wait();
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
	}

       
}
