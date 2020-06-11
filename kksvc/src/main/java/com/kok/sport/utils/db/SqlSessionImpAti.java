package com.kok.sport.utils.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

public class SqlSessionImpAti  extends DefaultSqlSession   implements SqlSession{
	
//	public SqlSessionImpAti() {
//		// TODO Auto-generated constructor stub
//	}

	public SqlSessionImpAti(Configuration configuration, Executor executor) {
		super(configuration, executor);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	 

}
