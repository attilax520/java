package com.attilax.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.Charset.EncodingDetect;
import com.attilax.collection.mapBuilder;
import com.attilax.core.ForeachFunction;
import com.attilax.fulltxt.fulltxtServ;
import com.attilax.str.Strutil;
import com.attilax.text.strUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.collect.Lists;
@SuppressWarnings("all")
public class DirTraveServiceTest {

	@Test
	public final void testTraveV4Vs427() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testTrave_throwEx() {
		fail("Not yet implemented"); // TODO
	}

	public static Logger logger = Logger.getLogger(Cls1.class);

	@Test
	public final void testTrave_v5() {
		final Logger logger = Logger.getLogger(Cls1.class);
		final List li = Lists.newArrayList();
		String dir = "C:\\Users\\attilax\\Documents\\sum doc all txtver  v2 raf ext notbek";
		new DirTraveService().traveV5_vS522(new File(dir), new ForeachFunction() {

			@Override
			public void each(int count, File f) throws Exception {
				String code = EncodingDetect.getJavaEncode(f.getAbsolutePath());
				String t = FileUtils.readFileToString(f, code);
				String kw = "编码  可视化";
			//	kw="成年 后世";
				
				List rzt=fulltxtServ.search(kw,t);
			
				
				logger.info( "cn:"+count+f);

			}

		});

	}

}
