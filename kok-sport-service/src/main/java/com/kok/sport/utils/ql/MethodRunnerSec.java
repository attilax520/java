package com.kok.sport.utils.ql;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.SportApplication;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.Util;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisQueryUtil;
import com.kok.sport.utils.db.MybatisUtil;

// "T(com.kok.sport.utils.mockdata.TonzhiJinxinzhongList).main(null)" 9
//双引号转义，就是俩个双引号
//  "T(com.kok.sport.utils.ql.MethodRunner).sql2notifyWebSocket(""select * from football_tlive_v limit 2 ;select 2"",""http://localhost:9601"")"
public class MethodRunnerSec {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(MethodRunnerSec.class);

	// T(com.kok.sport.utils.CaptchData).m1() invoke sttatic method
	// T(com.kok.sport.utils.CaptchData).Football_Basic_Update_profile()
	public static void main(String[] args) throws Throwable {
//		System.out.println(args[0]);
//		System.out.println("d");
		String e = args[0];
		int sec = Integer.parseInt(args[1].trim());
		int times = 60 / sec;
		System.out.println("----times:" + times);
		Util.timeOutExitRuntime(60*1000);
		Timer tmr = new Timer();		
		tmr.schedule(new java.util.TimerTask() {

			@Override
			public void run() {
				logger.info(">>getRuntime().exit");
				Runtime.getRuntime().exit(0);
				

			}
		}, 60L * 1000L);
		for (int i = 0; i < times; i++) {
			System.out.println("times:" + i + "/" + times);
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					logger.info(">>MethodRunner.main:" + e);
					// e="T(com.kok.sport.utils.ql.MethodRunner).sql2notifyWebSocket(\"select * from
					// football_tlive_v limit 2;select 2\",\"http://localhost:9601\")";
//						Interpreter i = new Interpreter(); // Construct an interpreter
//						System.out.println(i.eval(" com.kok.sport.utils.CaptchData.m1()  "));
					ExpressionParser parser = new SpelExpressionParser();

					Expression exp = parser.parseExpression(e);

					Object value = exp.getValue();
					System.out.println(value);
					logger.info(">>MethodRunner.exe:");
					logger.info(value);

				}
			};
			runnable.run();
			// new Thread(runnable).start();
			Thread.sleep(sec * 1000);
		}

	}

}
