package com.kok.sport.utils.db;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;

import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.ql.MethodRunner;

// T(com.kok.sport.utils.db.EventMysqlExt).main()
public class EventMysqlExt {
	public static SqlSessionFactory sqlSessionFactory1 = MybatisUtil.getSqlSessionFactoryRE();
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(EventMysqlExt.class);

	public static void main(String[] args) {
		int minisecEvtRecyl = 3000;
//		Timer tmr = new Timer("time2");
//	
//		tmr.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//
//			
//
//			}
//
//			
//		}, 50, minisecEvtRecyl);
		
		for(int i=0;i<20;i++) {
			try {
				
				String sql = "call exeEvtGet() ";
				logger.info(sql);
				List<LinkedHashMap> es = MybatisUtil.getMybatisMapper(sqlSessionFactory1).querySql(sql);
				for (LinkedHashMap em : es) {

					exeEvent(em);//thread

				}
				Thread.sleep(minisecEvtRecyl);
			} catch (Exception e) {
				logger.error(e);
			}
			System.out.println("getconnStatChkTmr:run");
		}
		System.out.println("f");
	}
	
	public static void exeEvent(LinkedHashMap em) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String e_def = (String) em.get("event_def");
					logger.info("==>exeEvent:"+e_def);
					MethodRunner.main(new String[] { e_def });
					String sql = "call exeEvtFns( " + em.get("id")+")";
					logger.info("==>"+sql);
					logger.info(">>>" + MybatisUtil.getMybatisMapper(sqlSessionFactory1).update(sql));
				} catch (Throwable e) {
					logger.error(e);
				}

			}
		}).start();
	}

}
