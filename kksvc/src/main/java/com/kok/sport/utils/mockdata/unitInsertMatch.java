package com.kok.sport.utils.mockdata;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.kok.sport.integration.impl.SyncFootballLiveMatchlistServiceImp;
import com.kok.sport.utils.db.MybatisUtil;

public class unitInsertMatch {

	public static void main(String[] args) throws Exception {
		new unitInsertMatch().Football_Live_Match_listInsert();
	}

	@Test
	public void Football_Live_Match_listInsert() throws Exception {
		String mpf = "D:\\prj\\sport-service\\kok-sport-service\\src\\main\\resources\\mapper\\FootballMatchDao.xml";
		mpf = mpf.replaceAll("\\\\", "/");
		// mpf="file://"+mpf;
		List<String> li = Lists.newArrayList();
		// li.add(mpf);
		// li.add("mapper/FootballMatchDao.xml");
		SqlSession conn = MybatisUtil.getConn(li);
		Collection<MappedStatement> mappedStatements = conn.getConfiguration().getMappedStatements();
		for (MappedStatement mappedStatement : mappedStatements) {
			System.out.println("--------------------------");

			System.out.println("StatementType:    " + mappedStatement.getStatementType());
			System.out.println("id:    " + mappedStatement.getId());
			System.out.println("res:    " + mappedStatement.getResource());
			// System.out.println("getBoundSql:
			// "+mappedStatement.getBoundSql(null).getSql());
		}
		System.out.println(mappedStatements);

		SyncFootballLiveMatchlistServiceImp c = new SyncFootballLiveMatchlistServiceImp();
		c.session = conn;
		c.Football_Live_Match_list();
		System.out.println("--f");
	}

}
