package com.attilax.device;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class perfMonitorServiceTest {
	public  static Logger logger = LoggerFactory.getLogger(perfMonitorService.class);
	public static void main(String[] args) throws IOException {
		logger.info("---");
		new perfMonitorServiceTest(). testCpuuseLinux();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCpuuseLinux() throws IOException {
		String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\toprzt.txt";
		String t=FileUtils.readFileToString(new File(f));
		
		System.out.println(new perfMonitorService().cpuuseLinux(t));
		//fail("Not yet implemented");
	}
	
	
	@Test
	public void testSysinfo() throws IOException {
		String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\toprzt.txt";
		String t=FileUtils.readFileToString(new File(f));
		
	 	System.out.println(new perfMonitorService().sysinfo(null));
		//fail("Not yet implemented");
	}
	
	
	@Test
	public void testioinfo() throws IOException {
		String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\toprzt.txt";
		String t=FileUtils.readFileToString(new File(f));
		
	 	System.out.println(new perfMonitorService().ioinfo(null));
		//fail("Not yet implemented");
	}
	@Test
	public void testcpu() throws IOException {
		String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\toprzt.txt";
		String t=FileUtils.readFileToString(new File(f));
		
	 	System.out.println(new perfMonitorService().cpuuse());
		//fail("Not yet implemented");
	}
	
	
	@Test
	public void testMemLinux() throws IOException {
		String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\toprzt.txt";
		String t=FileUtils.readFileToString(new File(f));
		
	//	System.out.println(new perfMonitorService().memuseLinux(t));
		//fail("Not yet implemented");
	}
	@Test
	public void testininfoLinuxParseCmdRetTxt() throws IOException {
		String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\iorzt.txt";
		String t=FileUtils.readFileToString(new File(f));
		
		System.out.println(new perfMonitorService().ioinfoLinuxParseCmdRetTxt(t));
		//fail("Not yet implemented");
	}
	
	
	
	
	

}
