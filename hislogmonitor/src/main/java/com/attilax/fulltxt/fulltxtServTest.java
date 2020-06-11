package com.attilax.fulltxt;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.Charset.EncodingDetect;
import com.attilax.core.ForeachFunction;
import com.attilax.io.DirTraveService;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.collect.Lists;

public class fulltxtServTest {

	@Test
	public final void test() {
		final Logger logger = Logger.getLogger(Cls1.class);
		final List li = Lists.newArrayList();
		
		String kw = "编码  可视化";
		String dir = "C:\\Users\\attilax\\Documents\\sum doc all txtver  v2 raf ext notbek";
		List rzt=fulltxtServ.searchFromDir(kw,dir);
		
		
		logger.info(rzt.size());
		
		logger.info( JSON.toJSONString(rzt, true));
	}

}
