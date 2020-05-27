package com.kok.sport.utils.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CfgDb {

	public static void fldDefValFun(Map kv_frmNet) {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		System.out.println("当前时间为: " + ft.format(dNow));
		kv_frmNet.put("create_time", ft.format(dNow));
		kv_frmNet.put("delete_flag", 0);
		
	}

}
