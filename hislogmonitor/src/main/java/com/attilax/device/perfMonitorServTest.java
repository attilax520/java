package com.attilax.device;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.attilax.collection.mapBuilder;

public class perfMonitorServTest {

	@Test
	public void testSysinfo() throws Exception {
		
		
		String linux_password = "cloudhealth";
		Map m=mapBuilder.$().put("os","linux").put("host", "192.168.1.18").put("user", "root").put("pwd", linux_password).build();
		System.out.println(new perfMonitorServ().sysinfo(m));

		
		 
	}

}
