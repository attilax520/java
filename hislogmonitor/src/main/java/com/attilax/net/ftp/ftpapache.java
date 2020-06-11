package com.attilax.net.ftp;

import java.util.ArrayList;
import java.util.List;
//import java.util.function.Function;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.mina.core.RuntimeIoException;

import com.attilax.collection.listBuilder;
import com.attilax.core.AsynUtil;

// com.attilax.net.ftp.ftpapache
public class ftpapache {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FtpException {

		// BaseUser BaseUser1 =
		// BaseUserBldr.$().setName("anonymous").setHomeDirectory("/")
		// .setAuthorities(listBuilder.<Authority> $().add(new
		// WritePermission()).build()).build();

		openInport21N5221();
		
		System.out.println("--ok from main ftpapache");

		//  iniblockver();

		// unless recyel,if use anoather class ,should be a
		// thred. to use start

		// t2();

	}

	public static void openInport21N5221() {

//		AsynUtil.execMeth_AysIniblock(new RunnableImp() {
//			{
//
//				openInPort21();
//				System.out.println("open21");
//
//			}
//		}

//		, "threadName ftp on port21");

		 AsynUtil.execMeth_Ays(new Runnable() {
		
		 @Override
		 public void run() {
		
		 openInPort21();
		 System.out.println("open21");
		
		 }
		
		 }, "threadName ftp on port21");

		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {

				openInPort5221();

			}
		}, "threadName ftp on port 5221");

	}

	private static void openInPort5221() {

		ListenerFactory factory = new ListenerFactory();

		// 设置监听端口
		factory.setPort(5221);
		// 默认存在一个default的监听端口，这里采用这种方式替换默认的监听端口
		// this.addListener("default", factory.createListener());

		try {
			FtpServerFactoryBld.$().getUserManager()
					.save(BaseUserBldr.$().setName("anonymous").setHomeDirectory("/")
							.setAuthorities(listBuilder.<Authority> $().add(new WritePermission()).build()).build())
					.addListener("default", factory.createListener()).build().createServer().start();
		} catch (FtpException e) {
			throw new RuntimeIoException(e);
		}

	}

	private static void openInPort21() {
		try {
			FtpServerFactoryBld.$().getUserManager()
					.save(BaseUserBldr.$().setName("anonymous").setHomeDirectory("/")
							.setAuthorities(listBuilder.<Authority> $().add(new WritePermission()).build()).build())
					.build().createServer().start();
		} catch (FtpException e) {
			throw new RuntimeIoException(e);
		}
	}

	private static FtpServerFactory build() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void iniblockver() throws FtpException {
		new FtpServerFactory() {
			{
				ListenerFactory factory = new ListenerFactory();

				// 设置监听端口
				factory.setPort(5221);
				// 默认存在一个default的监听端口，这里采用这种方式替换默认的监听端口
				this.addListener("default", factory.createListener());

				this.getUserManager().save(new BaseUser() {
					{
						this.setName("anonymous");
						this.setHomeDirectory("/");

						this.setAuthorities(listBuilder.<Authority> $().add(new WritePermission()).build());
					}
				});
			}
		}.createServer().start();
	}
	
	private static void iniblockverWzpwd() throws FtpException {
		new FtpServerFactory() {
			{
				ListenerFactory factory = new ListenerFactory();

				// 设置监听端口
				factory.setPort(5221);
				// 默认存在一个default的监听端口，这里采用这种方式替换默认的监听端口
				this.addListener("default", factory.createListener());

				this.getUserManager().save(new BaseUser() {
					{
						this.setName("acc");
						this.setHomeDirectory("/");
								this.setPassword("");
						this.setAuthorities(listBuilder.<Authority> $().add(new WritePermission()).build());
					}
				});
			}
		}.createServer().start();
	}

	private static void t2() throws FtpException {
		// FtpServerFactory serverFactory = new FtpServerFactory();
		// serverFactory.getUserManager().save((User) ((Function) (Object) -> {
		// BaseUser user = new BaseUser();
		// user.setName("anonymous");
		// user.setHomeDirectory("c:/");
		//
		// user.setAuthorities(listBuilder.<Authority> $().add(new
		// WritePermission()).build());
		//
		// return user;
		// }).apply(null));
		// FtpServer server = serverFactory.createServer();
		// server.start(); // unless recyel,if use anoather class ,should be a
		// // thred. to use start

		// Function f2=(Object)->{
		// return Object;};
		// Function o=(Object)->{ return null;};
		// Object o2= ((Function)(Object)->{ return null;}).apply(null);
		// Builders.$().build2((Object)->{ return null;});
	}

	public static void openInPort(final int port) {
		
		
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {

				openinport_sync(port);

			}
		}, "threadName fp on port 2121");
		
	}

	private static void openinport_sync(int port) {
		ListenerFactory factory = new ListenerFactory();

		// 设置监听端口
		 
		factory.setPort(port);
		// 默认存在一个default的监听端口，这里采用这种方式替换默认的监听端口
		// this.addListener("default", factory.createListener());

		try {
			FtpServerFactoryBld.$().getUserManager()
					.save(BaseUserBldr.$().setName("anonymous").setHomeDirectory("/")
							.setAuthorities(listBuilder.<Authority> $().add(new WritePermission()).build()).build())
					.addListener("default", factory.createListener()).build().createServer().start();
		} catch (FtpException e) {
			throw new RuntimeIoException(e);
		}
	}

}
