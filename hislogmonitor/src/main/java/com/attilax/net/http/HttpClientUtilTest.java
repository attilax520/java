package com.attilax.net.http;

import static org.junit.Assert.*;

import org.junit.Test;

public class HttpClientUtilTest {
	
	
	@Test
	public void httpget_autoEncode() {
		String url="http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/93358b007179626ca3046afbcf252d0797a1ebabb725297fbc5f893b2e92092e/93358b007179626ca3046afbcf252d0797a1ebabb725297fbc5f893b2e92092e-json.log";
	url="http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/6e4436bfe0f45436db9358d167fef02ef75906cd80274b7514b2d102dd0c7742/6e4436bfe0f45436db9358d167fef02ef75906cd80274b7514b2d102dd0c7742-json.log";
	url="http://www.baidu.com";
	System.out.println(new HttpClientUtil().httpget_autoEncode(url));	;
	}

	@Test
	public void testHttpfileSize() {
		String url="http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/93358b007179626ca3046afbcf252d0797a1ebabb725297fbc5f893b2e92092e/93358b007179626ca3046afbcf252d0797a1ebabb725297fbc5f893b2e92092e-json.log";
	url="http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/6e4436bfe0f45436db9358d167fef02ef75906cd80274b7514b2d102dd0c7742/6e4436bfe0f45436db9358d167fef02ef75906cd80274b7514b2d102dd0c7742-json.log";
		System.out.println(new HttpClientUtil().httpfileSize(url));	;
	}

}
