package com.kok.sport.integration.impl;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.kok.sport.integration.SyncFootballEuropeOddseuropelive;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.MybatisMapperCls;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.QlSpelUtil;
@Service
//SyncFootballEuropeOddseuropelive
public class SyncFootballEuropeOddseuropeliveImp implements SyncFootballEuropeOddseuropelive {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CaptchData.class);

	@Autowired
	SqlSession session;
	
	/**
	 * Football.Europe.Odds_europe_live
	 * 
	 * @throws Exception
	 * 
	 *                   CREATE TABLE `football_odds_t` ( `id` bigint(19) NOT NULL
	 *                   COMMENT '主键id', `company_id` bigint(19) NOT NULL COMMENT
	 *                   '公司id', `match_id` bigint(19) NOT NULL COMMENT '比赛id',
	 *                   `odds_type` tinyint(3) NOT NULL COMMENT '指数类型:1,asia-亚盘;
	 *                   2,bs-大小球;3,eu-欧赔', `change_time` bigint(19) NOT NULL
	 *                   COMMENT '变化时间', `happen_time` varchar(50) DEFAULT NULL
	 *                   COMMENT '比赛进行时间', `match_status` tinyint(3) NOT NULL
	 *                   COMMENT '比赛状态', `home_odds` decimal(32,10) NOT NULL COMMENT
	 *                   '主赔率', `tie_odds` decimal(32,10) NOT NULL COMMENT
	 *                   '盘口/和局赔率', `away_odds` decimal(32,10) NOT NULL COMMENT
	 *                   '客赔率', `lock_flag` tinyint(3) DEFAULT NULL COMMENT '是否封盘
	 *                   0-否,1-是', `real_time_score` varchar(20) NOT NULL COMMENT
	 *                   '实时比分', `create_time` datetime NOT NULL COMMENT '创建时间',
	 *                   `delete_flag` char(1) NOT NULL COMMENT '是否删除(1.已删除0.未删除)',
	 *                   PRIMARY KEY (`id`) ) ENGINE=Inno
	 */
	@Override
	public   void Football_Europe_Odds_europe_live() throws Exception {

		SqlSession session = MybatisUtil.getConn();
		List<Map> li = MybatisUtil.executeSql("select * from football_match_t");
		for (Map map : li) {
			try {
				String matchid = map.get("id").toString();
				// "2644547";
				logger.info(map);
				JsonObject json =CaptchData. getJsonRzt("Football.Europe.Odds_europe_history", "&id=" + matchid,
						"Football.Europe.Odds_europe_history_" + matchid);

				Map m = JsonGsonUtil.toMap(json);
				m.forEach(new BiConsumer<String, List>() {

					@Override
					public void accept(String k, List v) {
						for (Object item1 : v) {

							try {

								List it = (List) item1;
								Map param = Maps.newConcurrentMap();
								param.put("company_id", k);
								param.put("match_id", matchid);
								param.put("change_time", it.get(0));

								param.put("home_odds", it.get(1));
								param.put("tie_odds", it.get(2));
								param.put("away_odds", it.get(3));
								param.put("lock_flag", it.get(4));

								System.out.println(session.update("insert_into_football_odds_t", param));
							 
							} catch (Exception e) {
								logger.error(e);
							}

						}

					}

				});
			} catch (Exception e) {
				logger.info(e);
			}

		}

	}
}
