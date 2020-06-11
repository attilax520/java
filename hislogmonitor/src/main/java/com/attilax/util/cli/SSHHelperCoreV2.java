package com.attilax.util.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.attilax.core.Consumer;
import com.attilax.io.streamutil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

/**
 * java ssh登录linux以后的一些操作方式
 * 
 * @author attilax s620 牵手无奈
 * @date 2016年12月1日上午10:21:36
 * @version V1.0
 */
public class SSHHelperCoreV2 {

	public SSHHelperCoreV2() {
		// TODO Auto-generated constructor stub
	}

	private final static Log logger = LogFactory.getLog(SSHHelperCoreV2.class);

	private String charset = Charset.defaultCharset().toString();
	public Session session;

	public SSHHelperCoreV2(String host, Integer port, String user, String password) throws  Exception {
		connect(host, port, user, password);
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            远程主机ip地址
	 * @param port
	 *            sftp连接端口，null 时为默认端口
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws JSchException
	 */
	public Session connect(String host, Integer port, String user, String password) throws Exception {
		try {
			JSch jsch = new JSch();
			if (port == null)
				port = 22;
			session = jsch.getSession(user, host, port.intValue());

			session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			// 设置第一次登陆的时候提示，可选值:(ask | yes | no)
			// session.setConfig("StrictHostKeyChecking", "no");
			// 30秒连接超时
			session.connect(5000);

		} catch (JSchException e) {
			e.printStackTrace();
			System.out.println("SFTPUitl 获取连接发生错误");
			throw e;
		}
		return session;
	}
	
	
	public cliRet sendCmd_retImdtly(String command ) throws Exception {
		cliRet cr=new cliRet();
		ChannelExec ChannelExec1 = (ChannelExec) session.openChannel("exec");
		InputStream stdStream = ChannelExec1.getInputStream();
		InputStream errStream = ChannelExec1.getErrStream();
		ChannelExec1.setCommand(command);
		ChannelExec1.connect();
		Thread.sleep(200);
		String str = null;
	 
			// 获得标准输出
			String charsetName = "utf-8";
			if (stdStream.available() > 0) {
				InputStreamReader isreader = new InputStreamReader(stdStream, charsetName);
				 
                cr.stdStr= IOUtils.toString(isreader);
				 
			}
			// 获得错误输出
			if (errStream.available() > 0) {
				  cr.errStr= IOUtils.toString(errStream,charsetName);

			}
			if (ChannelExec1.isClosed()) {
				int code = ChannelExec1.getExitStatus();
				logger.info("exit-status: " + code);
				cr.exitStatCode=code;
				 
			}
			return cr;
			 
		 

	}


	/**
	 * // 返回的结果可能是标准信息,也可能是错误信息,所以两种输出都要获取 // 一般情况下只会有一种输出. //
	 * 但并不是说错误信息就是执行命令出错的信息,如获得远程java JDK版本就以 // ErrStream来获得.
	 * 
	 * @param command
	 * @param consumer1
	 * @throws Exception
	 */
	public cliRet sendCmdV2(String command, Consumer consumer1) throws Exception {
		cliRet cr_sendcmd=new cliRet();
		ChannelExec ChannelExec1 = (ChannelExec) session.openChannel("exec");

		InputStream inputStream = ChannelExec1.getInputStream();
		InputStream errStream = ChannelExec1.getErrStream();
		
		ChannelExec1.setCommand(command);
		ChannelExec1.connect();
		Thread.sleep(200);

		while (true) {
			// 获得标准输出
			streamutil.stream.set("stdStream");  //just for log name def the srteam name diff
		 
			
			streamutil.process(inputStream, consumer1);
			streamutil.stream.set("errStream");
			
			streamutil.process(errStream, consumer1);

			if (ChannelExec1.isClosed()) {
				int code = ChannelExec1.getExitStatus();
				logger.info("exit-status: " + code);
				cr_sendcmd.exitStatCode=code;
				break;
			}
			Thread.sleep(100);
		}
		return cr_sendcmd;

	}
	

	
	/**
	 * 上传本地文件到远程linux上 使用sftp上传
	 * 
	 * @throws Exception
	 */
	// @Override
	public boolean uploadLocalFileToRemote(InputStream localFileInputStream, String remoteDirFile) throws Exception {

		// 3、在该session会话中开启一个SFTP通道，之后就可以在该通道中进行文件传输了
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();

		// 4、进行文件传输操作：put()、get()....
 
		channelSftp.put(localFileInputStream, remoteDirFile);
		 

		// 5、操作完毕后，关闭通道并退出本次会话
		if (channelSftp != null && channelSftp.isConnected()) {
			channelSftp.disconnect();
		}
		return true;
	}
	
	
	public boolean uploadLocalFileToRemote(InputStream localFileInputStream, String remoteDirFile,SftpProgressMonitor SftpProgressMonitor1) throws Exception {

		// 3、在该session会话中开启一个SFTP通道，之后就可以在该通道中进行文件传输了
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();

		// 4、进行文件传输操作：put()、get()....
 
		 
		channelSftp.put(localFileInputStream, remoteDirFile,SftpProgressMonitor1);

		// 5、操作完毕后，关闭通道并退出本次会话
		if (channelSftp != null && channelSftp.isConnected()) {
			channelSftp.disconnect();
		}
		return true;
	}


	/**
	 * 用完记得关闭，否则连接一直存在，程序不会退出
	 * 
	 */
	public void close() {
		if (session.isConnected())
			session.disconnect();
	}
	
	
	@Deprecated
	public cliRet sendCmdV2_longtime(String command, Consumer consumer1) throws Exception {
		cliRet cr_sendcmd=new cliRet();
		ChannelExec ChannelExec1 = (ChannelExec) session.openChannel("exec");
		InputStream stdStream = ChannelExec1.getInputStream();
		InputStream errStream = ChannelExec1.getErrStream();
		ChannelExec1.setCommand(command);
		ChannelExec1.connect();
		Thread.sleep(200);
		String str = null;
		while (true) {
			// 获得标准输出
			String charsetName = "utf-8";
			if (stdStream.available() > 0) {
				BufferedReader br = new BufferedReader(new InputStreamReader(stdStream, charsetName));

				while ((str = br.readLine()) != null) {
					//logger.info("stdStream outptu:");
					cr_sendcmd.stdStr=cr_sendcmd.stdStr+"\n"+str;
					consumer1.accept(str);
				}
			}
			// 获得错误输出
			if (errStream.available() > 0) {
				BufferedReader br = new BufferedReader(new InputStreamReader(errStream, charsetName));
				while ((str = br.readLine()) != null) {
					logger.info("errStream outptu:");
					cr_sendcmd.errStr=cr_sendcmd.errStr+"\n"+str;
					consumer1.accept(str);
				}

			}
			if (ChannelExec1.isClosed()) {
				int code = ChannelExec1.getExitStatus();
				logger.info("exit-status: " + code);
				cr_sendcmd.exitStatCode=code;
				break;
			}
			Thread.sleep(100);
		}
		return cr_sendcmd;
		

	}
	

}