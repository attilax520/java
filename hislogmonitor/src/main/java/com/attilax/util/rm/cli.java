package com.attilax.util.rm;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class cli {
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		 
		
		String host=args[0];
		 String url = "rmi://@host@:@port@/timeObj1";
 	    url=url.replaceAll("@host@", host);
 		    url=url.replaceAll("@port@", args[1]);
 //			Naming.bind(url, server1);  
		 
		 
		
	 	server handler =(server) Naming.lookup(url); 
					
				 
		    long  count = handler.time();
		    
		    System.out.println(count);
	 
 
	}
}

