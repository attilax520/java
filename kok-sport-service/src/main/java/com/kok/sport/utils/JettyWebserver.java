package com.kok.sport.utils;

import org.eclipse.jetty.server.Server;

public class JettyWebserver {
	
	/**
	 * <pre>
	 *     一个简单的 jetty 服务
	 * </pre>
	 *
	 * @author saligia
	 * @date 17-10-10
	 */
 
	    public static void main(String [] args) throws Exception {
	      //  int port = 13140;
	        int port=Integer.parseInt(args[0].trim()) ;
			Server server = new Server(port);  // 创建一个 jetty 服务
	        server.setHandler(new QlSpelHandle());
	        server.start();
	        server.dumpStdErr();
	    
	        server.join();
	    
	}

}
