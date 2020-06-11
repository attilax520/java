package com.attilax.net;

 
	
	import java.util.HashSet;
import java.util.Set;
//  com.attilax.net.MyApplication
import javax.ws.rs.ApplicationPath;
	import javax.ws.rs.core.Application;

	//@aaaApplicationPath("app")
	public class MyApplication extends Application {
	    public Set<Class<?>> getClasses() {
	        Set<Class<?>> s = new HashSet<Class<?>>();
	      //  s.add(HelloWorldResource.class);
	        return s;
	    }
	}

 
