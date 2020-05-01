package com.kok.sport.utils.db;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.kok.sport.utils.ExUtilV2t33;
import com.kok.sport.utils.MapUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.SpringUtil;
import com.kok.sport.utils.UrlUtil;
import com.kok.sport.utils.mybatisdemo;
import com.kok.sport.utils.mockdata.mybatisXmlParser;
import com.mysql.cj.core.conf.url.ConnectionUrlParser;

import io.swagger.annotations.ApiOperation;
import ognl.Ognl;
import ognl.OgnlException;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.velocity.VelocityContext;
//import org.chwin.firefighting.apiserver.dsl.velocityUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * v3 u33
 * 
 * page help 5 ,,,and ex to common ex
 * 
 * @author Administrator
 *
 */
public class MybatisUtil {

	public static void main(String[] args) throws Exception {

		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		MybatisMapper MybatisMapper1 = session.getMapper(MybatisMapper.class);
		Map m = Maps.newConcurrentMap();
		m.put("limit", " limit 2 offset 1");

//		List li = MybatisMapper1.football_team_tlist(m);
//		System.out.println(li);
		String q = "a=1&b=2";
		System.out.println(UrlUtil.parse4Q(q));
		// System.out.println( new MybatisUtil());
//       Map dbcfg=SpringUtil.getDbcfg();
//         SqlSessionFactory sqlSessionFactory =MybatisUtil. getSqlSessionFactory();
//        System.out.println(dbcfg);
		// System.out.println( MybatisUtil.selectList("warning_query",null));
		// MybatisUtil.selectList("variTes33t", null);
		System.out.println((float) (314 * 9 + 381 * 3 - 3500) / 3500f);
		System.out.println((float) (4336 - 3600) / 3600);
//        Map m=Maps.newLinkedHashMap();
//        m.put("TaskName","最近几天通道情况隐患");
//        m.put("HiddenDescript","通道堵塞");
//        m.put("place","余杭区文一西路");
//        m.put("ProblemReporter","刘媛");
//
//      //  System.out.println( MybatisUtil.selectList("上报隐患流程",m));
//
//        m.put("Property","xxx物业公司");
//        m.put("id",1);
//        System.out.println( MybatisUtil.selectList("管委会派单给物业流程",m));
		System.out.println(genetmp("id,name_zh,short_name_zh,name_zht,short_name_zht,name_en,short_name_en,logo"));

	}

	private static char[] genetmp(String string) {
		String[] a = string.split(",");
		for (String c : a) {
			System.out.print("#{" + c + "},");
		}
		return null;
	}

	public static String getTrueSql(Map m) {

		MybatisQueryUtil.paddParam(m);
		Method meth_query;
		try {
			meth_query = MybatisMapper.class.getMethod("query", Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}

		Select slctAnno = meth_query.getAnnotation(Select.class);
		String sqltmplt = slctAnno.value()[0];
		String sql = MapUtil.execTmplt(sqltmplt, m);
		return sql;
	}

	public static SqlSession getConn(String mpXmlPathRes) {
		try {
			// Configuration.addLoadedResource
			SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
			Configuration c = sqlSessionFactory.getConfiguration();

			c.addLoadedResource(mpXmlPathRes);
			SqlSession session = sqlSessionFactory.openSession(true);

			return session;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static SqlSession getConn() throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		// sqlSessionFactory.getConfiguration()
		SqlSession session = sqlSessionFactory.openSession(true);

		return session;
	}

	public static SqlSessionFactory getSqlSessionFactory(List<String> mpList) {
		try {
			String mybatisCfg_result = getCfgTxtV2();

			mybatisCfg_result=	mybatisXmlParser.addmapper2xml(mpList, mybatisCfg_result);
			  System.out.println(mybatisCfg_result);
			InputStream is2 = new ByteArrayInputStream(mybatisCfg_result.getBytes());
			// ����sqlSession �Ĺ���
			return new SqlSessionFactoryBuilder().build(is2);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static SqlSessionFactory getSqlSessionFactory() throws Exception {
		String mybatisCfg_result = getCfgTxtV2();

		// System.out.println(mybatisCfg_result);
		InputStream is2 = new ByteArrayInputStream(mybatisCfg_result.getBytes());
		// ����sqlSession �Ĺ���
		return new SqlSessionFactoryBuilder().build(is2);
	}

	public static String getCfgTxtV2() throws Exception {
		Properties properties = new Properties();
		InputStream inputStream = Object.class.getResourceAsStream("/cfg");

		properties.load(inputStream);

		String mybatisNamespace = "";
		try {
			mybatisNamespace = properties.getProperty("mybatisNamespace");
			if (mybatisNamespace.trim().length() > 0)
				mybatisNamespace = mybatisNamespace.replaceAll("\\.", "/");
			mybatisNamespace = mybatisNamespace + "/";
		} catch (Exception e) {

		}

		String resource = "/" + mybatisNamespace + "mybatis.xml";
		// ����mybatis �������ļ�����Ҳ���ع�����ӳ���ļ���
		// ClassLoader classLoader = .getClassLoader();
		InputStream is = mybatisdemo.class.getResourceAsStream(resource);
		String mybatisCfg_result = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));

		String getCfgFile = SpringUtil.getCfgFile();

		String mysqlConnUrl = properties.get("db").toString();
		ConnectionUrlParser connStringParser = ConnectionUrlParser.parseConnectionString(mysqlConnUrl);
		System.out.println(connStringParser);

		Object url = mysqlConnUrl;
		Object usr = UrlUtil.parse4Q(connStringParser.getQuery()).get("user");
		Object pwd = UrlUtil.parse4Q(connStringParser.getQuery()).get("password");
		if (pwd == null)
			pwd = "";
		url = cn.hutool.core.util.XmlUtil.escape(url.toString() + "&allowMultiQueries=true");

		mybatisCfg_result = mybatisCfg_result.replaceAll("\\$\\{mysql.url}", url.toString());
		mybatisCfg_result = mybatisCfg_result.replaceAll("\\$\\{mysql.username}", usr.toString());

		mybatisCfg_result = mybatisCfg_result.replaceAll("\\$\\{mysql.password}", pwd.toString());
		return mybatisCfg_result;
	}

	@Deprecated
	public static String getCfgTxt() throws Exception {
		String resource = "/mybatis.xml";
		// ����mybatis �������ļ�����Ҳ���ع�����ӳ���ļ���
		// ClassLoader classLoader = .getClassLoader();
		InputStream is = mybatisdemo.class.getResourceAsStream(resource);
		String mybatisCfg_result = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));

		String getCfgFile = SpringUtil.getCfgFile();
		org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
		Object mObject = yaml.load(mybatisdemo.class.getResourceAsStream(getCfgFile));
		Object expression = Ognl.parseExpression("spring.datasource.url");
		Object url = Ognl.getValue(expression, mObject);
		Object usr = Ognl.getValue(Ognl.parseExpression("spring.datasource.username"), mObject);
		Object pwd = Ognl.getValue(Ognl.parseExpression("spring.datasource.password"), mObject);
		if (pwd == null)
			pwd = "";
		url = cn.hutool.core.util.XmlUtil.escape(url.toString() + "&allowMultiQueries=true");

		mybatisCfg_result = mybatisCfg_result.replaceAll("\\$\\{mysql.url}", url.toString());
		mybatisCfg_result = mybatisCfg_result.replaceAll("\\$\\{mysql.username}", usr.toString());

		mybatisCfg_result = mybatisCfg_result.replaceAll("\\$\\{mysql.password}", pwd.toString());
		return mybatisCfg_result;
	}

	public static List executeQueryView(String view) throws Exception {

		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
//"call sp1(2)"
		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

		String sql = "select " + view;
		List<LinkedHashMap> li = mapper.querySqlV2(sql);

		return li;
	}

	public static Object executeSp(String sp) throws Exception {
		// , HttpServletResponse res
		// repalce cur usre info with cookie userinfo
//        if (sp.contains("@当前用户信息@")) {
//
//            String u_col = " '$uname','$usertype' ";
//            //创建context
//            VelocityContext context = new VelocityContext();
////添加数据
//            String uname = CookieUtil.getValue(req, "curUserInfo.uname");
//            context.put("uname", uname);
//            context.put("usertype", CookieUtil.getValue(req, "curUserInfo.usertype"));
//
//            u_col = velocityUtil.getTmpltCalcRzt(u_col, context);
//            sp = sp.replaceAll("@当前用户信息@", u_col);
//
//        }
		sp = "call " + sp;
		return executeSql(sp);

	}

	public static Object executeSqlRetObj(String sql) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
//"call sp1(2)"
		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

		Object li = mapper.executeSqlRetObj(sql);

		session.close();
		return li;
//        res.getWriter().write(rzt);
//        res.getWriter().flush();
//        res.flushBuffer();
	}

	public static List executeSql(String sql) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
//"call sp1(2)"
		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

		List<LinkedHashMap> li = mapper.querySqlV2(sql);

		session.close();
		return li;
//        res.getWriter().write(rzt);
//        res.getWriter().flush();
//        res.flushBuffer();
	}

	public static PageInfo executeQuery(String select_id) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
		// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

		// 设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
		PageHelper.startPage(1, 10, true);
		// PageHelper.startPage(1,10);
//此时已经分页了
		List<Map> rzt_list = session.selectList(select_id, null);

		// 可以使用PageInfo 查看分页信息
		PageInfo pageInfo = new PageInfo<>(rzt_list);
		return pageInfo;
	}

	public static PageInfo executeQuery(String select_id, Map paramMap) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
		// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

		// 设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
		PageHelper.startPage(1, 10, true);
		// PageHelper.startPage(1,10);
//此时已经分页了
		List<Map> rzt_list = session.selectList(select_id, paramMap);

		// 可以使用PageInfo 查看分页信息
		PageInfo pageInfo = new PageInfo<>(rzt_list);
		return pageInfo;

	}

	public static PageInfo selectList(String select_id, Map paramMap, int pageNum, int pagesize) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
		// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

		// 设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
		PageHelper.startPage(pageNum, pagesize, true);
		// PageHelper.startPage(1,10);
//此时已经分页了
		List<Map> rzt_list = session.selectList(select_id, paramMap);

		// 可以使用PageInfo 查看分页信息
		PageInfo pageInfo = new PageInfo<>(rzt_list);
		return pageInfo;

	}

	public static void uniqueIdx(String tab, String col, String val, SqlSession session) throws UniqueEx {

		String sql = "select * from " + tab + " where " + col + "='" + val + "'";

		List<LinkedHashMap> rzt_list = query(sql, session);
		// var rzt = query( sql,connection)
		if (rzt_list.size() > 0) {
//	        ex = {};
//	        ex.sql = sql
//	        ex.name = 'uniqueEx';

			throw new UniqueEx(sql);
		}

	}

//	public static  int queryCount(Map m, MybatisMapper MybatisMapper1 ) {
//		List<Map> cntList= MybatisMapper1.queryCount(m);
//		int cnt = Integer.parseInt(  cntList.get(0).get("cnt").toString());
//		return  cnt;
//	}
	public static List<LinkedHashMap> query(String sql) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		MybatisMapperCls MybatisMapperCls1 = session.getMapper(MybatisMapperCls.class);

		List<LinkedHashMap> rzt_list = MybatisMapperCls1.querySqlV2(sql);
		return rzt_list;
	}

	public static List<LinkedHashMap> query(String sql, SqlSession session) {
		MybatisMapperCls MybatisMapperCls1 = session.getMapper(MybatisMapperCls.class);

		List<LinkedHashMap> rzt_list = MybatisMapperCls1.querySqlV2(sql);
		return rzt_list;
	}

	public static int update(String statement, Map paramMap, SqlSession session) throws Exception {

		return session.update(statement, paramMap);

	}

	public static List selectList(String select_id, Map paramMap) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		// api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession
		// �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

		// session.select();
		List<Map> rzt_list = session.selectList(select_id, paramMap);

		return rzt_list;

	}

	public static int execSqlUpdateNInsert(String sql) throws Exception {
		SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession(true);
		MybatisMapperCls MybatisMapperCls1 = session.getMapper(MybatisMapperCls.class);

		return MybatisMapperCls1.updateV2(sql);

	}

	public static int execSqlUpdateNInsert(String sql, SqlSession session) {
		MybatisMapperCls MybatisMapperCls1 = session.getMapper(MybatisMapperCls.class);

		return MybatisMapperCls1.updateV2(sql);

	}

	public static int execSql(String sql, SqlSession session) {
		MybatisMapperCls MybatisMapperCls1 = session.getMapper(MybatisMapperCls.class);

		return MybatisMapperCls1.updateV2(sql);

	}

	public static MybatisMapper getMybatisMapper() {
		try {
			SqlSession conn = MybatisUtil.getConn();
			return conn.getMapper(MybatisMapper.class);
		} catch (Exception e) {
			ExUtilV2t33.throwExV2(e);
		}
		return null;

	}

	public static SqlSession getConn(List<String> li) {
		try {
			// Configuration.addLoadedResource
			SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory(li);
//		cfg on effect in ini factory
			// Configuration c= sqlSessionFactory.getConfiguration();
//			 for (String mpXmlPathRes : li) {
//				 c.addLoadedResource(mpXmlPathRes);
//			}

			SqlSession session = sqlSessionFactory.openSession(true);

			return session;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static MybatisMapper getMybatisMapper(SqlSessionFactory sqlSessionFactory1) {
		 
		return  sqlSessionFactory1.openSession(true).getMapper(MybatisMapper.class); 
	}

}
