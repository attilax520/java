package com.cnhis.cloudhealth.module.log;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class cli {
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, FileNotFoundException {
		 
		// String port = args[1];
		//String host=args[0];
		String	host="127.0.0.1";
		String	port="80";
		
		
		 String url = "rmi://@host@:@port@/chglog_rebind";
 	    url=url.replaceAll("@host@", host);
 		   
			url=url.replaceAll("@port@", port);
 //			Naming.bind(url, server1);  
			
		 
		
	 Remote 	  Remote1 =  Naming.lookup(url); 
	 chgLogLev chgLogLev1=(chgLogLev) Remote1;
	 chgLogLev1.chglev(org.apache.log4j.Level.DEBUG);
					
		 System.out.println("--");
	 
 
	}
}
