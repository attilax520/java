package com.attilax.util.rm;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class server implements Remote ,Serializable{
	
	
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException, InterruptedException {
	//	server s=new server();
		//s.time();
		 
			int port =Integer.parseInt(args[1]);
			
			String host=args[0];
			Registry    registry = LocateRegistry.createRegistry(port);a
		    server server1 = new server();
		   registry.rebind("timeObj1", server1);
//		    String url = "rmi://@host@:@port@/timeObj1";
//		    url=url.replaceAll("@host@", host);
//		    url=url.replaceAll("@port@", args[1]);
//			Naming.bind(url, server1);  
	//	   Naming.rebind(name, obj);
		    System.out.println(" rmi server is ready ...");
		    while(true)
		    {
		    	Thread.sleep(3000);
		    	System.out.println("...");
		    }
		 
	}

	public long time() {
		   return System.currentTimeMillis();
		
	}

}
