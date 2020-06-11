package com.attilax.util.cli;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.attilax.core.Consumer;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

/**
 * java ssh登录linux以后的一些操作方式
 * 
 * @author 牵手无奈
 * @date 2016年12月1日上午10:21:36
 * @version V1.0
 */
public class SSHHelper extends SSHHelperCoreV2 {
	private final static Log logger = LogFactory.getLog(SSHHelper.class);

	private String charset = Charset.defaultCharset().toString();
	 

	public SSHHelper(String host, Integer port, String user, String password) throws Exception {
		connect(host, port, user, password);
	}

	
	/**
	 * 上传本地文件到远程linux上 使用sftp上传
	 * 
	 * @throws Exception
	 */
//	// @Override
//	public boolean uploadLocalFileToRemote(String localFile, String remoteDir) throws Exception {
//
//		// 3、在该session会话中开启一个SFTP通道，之后就可以在该通道中进行文件传输了
//		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
//		channelSftp.connect();
//
//		// 4、进行文件传输操作：put()、get()....
//		channelSftp.put(localFile, remoteDir);
//
//		// 5、操作完毕后，关闭通道并退出本次会话
//		if (channelSftp != null && channelSftp.isConnected()) {
//			channelSftp.disconnect();
//		}
//		return true;
//	}
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
			if (port != null) {
				session = jsch.getSession(user, host, port.intValue());
			} else {
				session = jsch.getSession(user, host);
			}
			session.setPassword(password);
			// 设置第一次登陆的时候提示，可选值:(ask | yes | no)
			// session.setConfig("StrictHostKeyChecking", "no");
			// 30秒连接超时
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(5000);

		} catch (JSchException e) {
			e.printStackTrace();
			System.out.println("SFTPUitl 获取连接发生错误");
			throw e;
		}
		return session;
	}

	/**
	 * 上传本地文件到远程linux上 使用sftp上传
	 * 
	 * @throws Exception
	 */
	// @Override
	public boolean uploadLocalFileToRemote(String localFile, String remoteDir) throws Exception {

		// 3、在该session会话中开启一个SFTP通道，之后就可以在该通道中进行文件传输了
		ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();

		// 4、进行文件传输操作：put()、get()....
		channelSftp.put(localFile, remoteDir);

		// 5、操作完毕后，关闭通道并退出本次会话
		if (channelSftp != null && channelSftp.isConnected()) {
			channelSftp.disconnect();
		}
		return true;
	}

	public SSHResInfo sendCmd(String command) throws Exception {
		return sendCmd(command, 200);
	}

	/*
	 * 执行命令，返回执行结果
	 * 
	 * @param command 命令
	 * 
	 * @param delay 估计shell命令执行时间
	 * 
	 * @return String 执行命令后的返回
	 * 
	 * @throws IOException
	 * 
	 * @throws JSchException
	 */
	public SSHResInfo sendCmd(String command, int delay) throws Exception {
		if (delay < 50) {
			delay = 50;
		}
		SSHResInfo result = null;
		byte[] tmp = new byte[1024]; // 读数据缓存
		StringBuffer strBuffer = new StringBuffer(); // 执行SSH返回的结果
		StringBuffer errResult = new StringBuffer();

		Channel channel = session.openChannel("exec");
		ChannelExec ssh = (ChannelExec) channel;
		// 返回的结果可能是标准信息,也可能是错误信息,所以两种输出都要获取
		// 一般情况下只会有一种输出.
		// 但并不是说错误信息就是执行命令出错的信息,如获得远程java JDK版本就以
		// ErrStream来获得.
		InputStream stdStream = ssh.getInputStream();
		InputStream errStream = ssh.getErrStream();

		ssh.setCommand(command);
		ssh.connect();

		try {

			// 开始获得SSH命令的结果
			while (true) {
				// 获得错误输出
				while (errStream.available() > 0) {
					int i = errStream.read(tmp, 0, 1024);
					if (i < 0)
						break;
					errResult.append(new String(tmp, 0, i));
				}

				// 获得标准输出
				while (stdStream.available() > 0) {
					int i = stdStream.read(tmp, 0, 1024);
					if (i < 0)
						break;
					strBuffer.append(new String(tmp, 0, i));
				}
				if (ssh.isClosed()) {
					int code = ssh.getExitStatus();
					logger.info("exit-status: " + code);
					result = new SSHResInfo(code, strBuffer.toString(), errResult.toString());
					break;
				}
				try {
					Thread.sleep(delay);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		} finally {
			// TODO: handle finally clause
			channel.disconnect();
		}

		return result;
	}

	/**
	 * // 返回的结果可能是标准信息,也可能是错误信息,所以两种输出都要获取 // 一般情况下只会有一种输出. //
	 * 但并不是说错误信息就是执行命令出错的信息,如获得远程java JDK版本就以 // ErrStream来获得.
	 * 
	 * @param command
	 * @param consumer1
	 * @throws Exception
	 */

	
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
	 * @param in
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private String processStream(InputStream in, String charset) throws Exception {
		byte[] buf = new byte[1024];
		StringBuilder sb = new StringBuilder();
		while (in.read(buf) != -1) {
			sb.append(new String(buf, charset));
		}
		return sb.toString();
	}

	// public boolean deleteRemoteFIleOrDir(String remoteFile){
	// ChannelSftp channel=null;
	// try {
	// channel=(ChannelSftp) session.openChannel("sftp");
	// channel.connect();
	// SftpATTRS sftpATTRS= channel.lstat(remoteFile);
	// if(sftpATTRS.isDir()){
	// //目录
	// logger.debug("remote File:dir");
	// channel.rmdir(remoteFile);
	// return true;
	// }else if(sftpATTRS.isReg()){
	// //文件
	// logger.debug("remote File:file");
	// channel.rm(remoteFile);
	// return true;
	// }else{
	// logger.debug("remote File:unkown");
	// return false;
	// }
	// }catch (JSchException e) {
	// if(channel!=null){
	// channel.disconnect();
	// session.disconnect();
	// }
	// logger.error("error",e);
	// return false;
	// } catch (SftpException e) {
	// logger.info("meg"+e.getMessage());
	// logger.error("SftpException",e);
	// return false;
	// }
	//
	// }

	/**
	 * 判断linux下 某文件是否存在
	 */
	// public boolean detectedFileExist(String remoteFile) {
	//
	// ChannelSftp channel=null;
	// try {
	// channel=(ChannelSftp) session.openChannel("sftp");
	// channel.connect();
	// SftpATTRS sftpATTRS= channel.lstat(remoteFile);
	// if(sftpATTRS.isDir()||sftpATTRS.isReg()){
	// //目录 和文件
	// logger.info("remote File:dir");
	// return true;
	// }else{
	// logger.info("remote File:unkown");
	// return false;
	// }
	// }catch (JSchException e) {
	// if(channel!=null){
	// channel.disconnect();
	// session.disconnect();
	// }
	// return false;
	// } catch (SftpException e) {
	// logger.error(e.getMessage());
	// }
	// return false;
	// }

	/**
	 * 用完记得关闭，否则连接一直存在，程序不会退出
	 * 
	 */
	public void close() {
		if (session.isConnected())
			session.disconnect();
	}

@Deprecated
	public String getCmdRet(String cmd) throws Exception {
		final List<String> li=Lists.newArrayList();
		this.sendCmdV2(cmd, new Consumer<String>() {

			@Override
			public void accept(String t) throws Exception {
				li.add(t);
				
			}
		});
		String t = Joiner.on("\n").join(li);
		return t;
		 
	}

}