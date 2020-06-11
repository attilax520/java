package com.attilax.util.cli;

 

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import com.attilax.core.Consumer;
import com.google.common.collect.Maps;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpProgressMonitor;

public class SSHHelperTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		testSSH();
	}
	
	
	@org.junit.Test
	public void  testuploadLocalFileToRemote_Big()  
	{
		try {
		String linux_password = "cloudhealth";
		SSHHelper helper = new SSHHelper("192.168.1.18", 22, "root", linux_password);
		     
			String bifile = "C:\\atibeks510\\ati foto pic\\000qb foto\\000qb foto.rar";
			bifile="C:\\Users\\attilax\\Desktop\\1.0.102.20180613.zip";
			String dest = "/elk/xxx.tmp";
			//jsch 0.1.27 jar
			helper.uploadLocalFileToRemote(new FileInputStream(new File(bifile)), dest,new SftpProgressMonitor() {
				
				@Override
				public void init(int arg0, String arg1, String arg2, long arg3) {
					//init(int op, String src, String dest, long max)
					//jeig haosyo ma invoke
					Map m=Maps.newConcurrentMap();
					m.put("a0", arg0);m.put("a1",arg1);m.put("a2",arg2);m.put("a3", arg3);
					System.out.println(m);
					
				}
				
				@Override
				public void end() {
					System.out.println("--end");
					
				}
				
				@Override
				public boolean count(long arg0) {
					//32673 mybe 32kb is cache file block per time trans,must ret true to next trans..
					//Will be called periodically as more data is transfered.
					System.out.println(arg0);
					return true;
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	@org.junit.Test
	public void  testuploadLocalFileToRemote()  
	{
		try {
		String linux_password = "cloudhealth";
		SSHHelper helper = new SSHHelper("192.168.1.18", 22, "root", linux_password);
		     
			helper.uploadLocalFileToRemote(new FileInputStream(new File("c:\\msdia80.dll")), "/elk/msdia80.dll1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public   void tedstd()
	{
		
	}

	public static void testSSH() throws Exception {

		String linux_password = "cloudhealth";
		// 使用目标服务器机上的用户名和密码登陆
		SSHHelper helper = new SSHHelper("192.168.1.18", 22, "root", linux_password);
		String command = "echo hello world!";
		command="psx";
		command="  top -b";
		//command="echo $TERM";
//		
//		helper.sendCmdV2("export TERM=dumb", new Consumer<String>() {
//
//			@Override
//			public void accept(String ret_line) throws Exception {
//				System.out.println(ret_line);
//
//			}
//		});
		
		
		helper.sendCmdV2(command, new Consumer<String>() {

			@Override
			public void accept(String ret_line) throws Exception {
				System.out.println(ret_line);

			}
		});

		// System.out.println(helper.deleteRemoteFIleOrDir(command));
		// System.out.println(helper.detectedFileExist(command));
		helper.close();

	}
}