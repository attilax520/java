package com.kok.sport.utils.mockdata;

import org.apache.logging.log4j.LogManager;

import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.db.MybatisUtil;


//set GLOBAL event_scheduler=1
public class SqlCmdExe {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(SqlCmdExe.class);
	public static void main(String[] args) {
		logger.info(MybatisUtil.getMybatisMapper().update(args[0]));

	}

}
