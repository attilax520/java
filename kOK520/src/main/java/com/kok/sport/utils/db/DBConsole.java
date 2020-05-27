package com.kok.sport.utils.db;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.io.FileUtils;

public class DBConsole {

	public static void main(String[] args) throws  Exception {
		String f="D:\\prj\\sport-service\\kok-sport-service\\testsql\\qinbao.sql";
		String sql=FileUtils.readFileToString(new File(f));
		sql="select *,scoreObj_byTeamMatch(10000,3380166,1)  as scobj from football_match_t where id=3380166";
	System.out.println(MybatisUtil.getMybatisMapper().querySql(sql));	
	
	//createView("",f)

	}
;
	private static void createView(String vname, String sqlfile) throws IOException {
		String svtmp="CREATE VIEW  {0} AS\r\n";
		svtmp=MessageFormat.format(svtmp, vname);
		String sql=FileUtils.readFileToString(new File(sqlfile));
		sql=svtmp+sql;
		System.out.println(MybatisUtil.getMybatisMapper().querySql(sql));
		
	}

}
