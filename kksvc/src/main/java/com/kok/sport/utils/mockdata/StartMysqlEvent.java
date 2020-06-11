package com.kok.sport.utils.mockdata;

import com.kok.sport.utils.db.MybatisUtil;

public class StartMysqlEvent {

	public static void main(String[] args) {
		String sql="SET @@global.event_scheduler = 1;";
		System.out.println(sql);
		System.out.println(MybatisUtil.getMybatisMapper().update(sql));
		

	}

}
