package com.attilax.web;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class HttpClientUtilTest {

	@Test
	public final void testHttpputStringMapString() throws IOException {
String f="C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
String t=FileUtils.readFileToString(new File(f));
Map map=JSON.parseObject(t);

String r=new HttpClientUtil().httpput("http://localhost:9200/accounts/person/1", t, "utf8");
System.out.println(r);

		
	}

	@Test
	public final void testHttpputStringMap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testHttpget() {
		fail("Not yet implemented"); // TODO
	}

}
