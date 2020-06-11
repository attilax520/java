package com.cnhis.cloudhealth;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ftpserver.ftplet.FtpException;

import com.attilax.core.AsynUtil;
import com.attilax.net.ftp.ftpapache;

@WebListener
public class ftpListern implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContex销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContex初始化");
		System.out.println(sce.getServletContext().getServerInfo());
		ftpapache.openInport21N5221();
		ftpapache.openInPort(2121);
		

	}

}
