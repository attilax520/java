/**
 * 
 */
package com.attilax.shell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author attilax
 *
 */
public class CliShellProcessServiceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.attilax.shell.CliShellProcessService#main(java.lang.String[])}.
	 */
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.attilax.shell.CliShellProcessService#stdout_2str_ByIoutil(java.lang.Process, java.lang.String)}.
	 */
	@Test
	public void testStdout_2str_ByIoutil() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.attilax.shell.CliShellProcessService#Erroutput2strByIoutil(java.lang.Process, java.lang.String)}.
	 */
	@Test
	public void testErroutput2strByIoutil() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test_output () {
		String lastcmd = "cmd /c dir c:\\0logs";
		lastcmd="systeminfo | find \"鍐呭瓨\"";
		lastcmd=" cmd /c   dirx    phpshell.php  /s";
//		lastcmd=" cmd.exe /c dir log4j*.proper* /s";
//		lastcmd="C:\\d\\exesql.bat";
//		lastcmd="notepad";//if notepad 
		String charsetName = "utf8";charsetName="gbk";
		
		String s=	  CliShellProcessService.getOutput(lastcmd, charsetName);
		System.out.println(s);
//		try {
//			String s=	  CliShellProcessService.getOutput(lastcmd, charsetName);
//			System.out.println(s);
//		} catch (Exception e) {
//			//System.out.println(JSON.toJSONString(e));
//		}

	}
	
	

}
