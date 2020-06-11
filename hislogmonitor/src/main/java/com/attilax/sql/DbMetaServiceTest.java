package com.attilax.sql;

import java.sql.Connection;

import com.attilax.util.MybatisUtil;

public class DbMetaServiceTest {

	public static void main(String[] args) {
		Connection conn= new	MybatisUtil().get_conn();
		System.out.println(new  DbMetaService().	getPrimaryKey (conn,"","vaj1"));
	}

}
