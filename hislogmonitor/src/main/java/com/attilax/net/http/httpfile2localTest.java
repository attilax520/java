package com.attilax.net.http;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class httpfile2localTest {

	@Test
	public void test() throws IOException {
		httpfile2local.http2loc("http://192.168.1.18:1314/webdavapp/webdavurl/home/cnhis/cloudhealth/logs/log.log"  ,"c:/tmp/cloudhealthtmp.log");
		
	}

}
