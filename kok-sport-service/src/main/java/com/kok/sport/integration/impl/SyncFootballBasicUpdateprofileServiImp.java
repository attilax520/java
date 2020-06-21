package com.kok.sport.integration.impl;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballBasicUpdateprofileServi;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.SpringUtil;
import com.kok.sport.utils.mybatisdemo;
import com.kok.sport.utils.constant.ServiceT;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.db.MysqlInsertUtil;
import com.kok.sport.utils.db.SqlSessionImpAti;

@Service
@SuppressWarnings("all")
public class SyncFootballBasicUpdateprofileServiImp implements SyncFootballBasicUpdateprofileServi {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	public SqlSession session;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		String basePackages = "com.kok.sport.utils.constant";
		// ctx.scan(basePackages);
		ctx.register(ServiceT.class);

		// ctx.register(org.apache.ibatis.session.defaults.DefaultSqlSession.class);
	//	ctx.register(SqlSessionImpAti.class);
		ctx.registerBean("SqlSession", SqlSession.class,new Supplier<SqlSession>() {

			@Override
			public SqlSession get() {
				 
				try {
					return MybatisUtil.getConn();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		} , SpringUtil.getBeanDef());
		ctx.register(SyncFootballBasicUpdateprofileServiImp.class);

		ctx.refresh();

		SyncFootballBasicUpdateprofileServiImp t = ctx.getBean(SyncFootballBasicUpdateprofileServiImp.class);
		try {
			t.session.getMapper(MybatisMapper.class).querySql("select 2");
			t.Football_Basic_Update_profile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void Football_Basic_Update_profile() throws Exception {
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Basic.Update_profile");
		MybatisMapper mpr = session.getMapper(MybatisMapper.class);
		JsonArray ja = json.getAsJsonObject("data").getAsJsonObject("update").getAsJsonArray("teams");
		for (JsonElement jsonElement : ja) {
			try {

				Map m = JsonGsonUtil.toMap(jsonElement.getAsJsonObject());
				m.put("$insert", "football_team_t");
				logger.info(m);
				logger.info(MysqlInsertUtil.insertV2_faster(null, m, mpr));
			} catch (Exception e) {
				logger.error(e);
			}

		}
//		JsonArray competitons = json.getAsJsonObject("data").getAsJsonObject("delete").getAsJsonArray("competitons");
//		String sql = "insert  into Football.Basic.Update_profile(  id,data  )values( @id@,'@data@')";
		// sql = processVars(sql, json.getAsJsonObject("data") );

//		SqlSession session = MybatisUtil.getConn();
//
//		MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);
//
//		System.out.println(mapper.updateV2(sql));
	}

}
