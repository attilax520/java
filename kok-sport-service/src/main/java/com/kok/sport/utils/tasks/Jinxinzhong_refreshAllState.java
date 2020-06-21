package com.kok.sport.utils.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.mockdata.unitRecentlyV2;
import com.kok.sport.utils.ql.QlSpelUtil;


//  com.kok.sport.utils.tasks.Jinxinzhong_refreshAllState
//   T(com.kok.sport.utils.tasks.Jinxinzhong_refreshAllState).extracted()
public class Jinxinzhong_refreshAllState {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(unitRecentlyV2.class);

	public static void main(String[] args) throws Exception {
		// String dt = "20200523";
		while(true) {
			Thread.sleep(1000);
			try {
				extracted();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	

	}

	public static void extracted() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(new Date());
		JsonObject json = CaptchData.getJsonRztFromUrl("Football.Live.Match_list", "&date=" + dt);

		Map m = JsonGsonUtil.toMap(json);
		String mts_exp = "#root['data']['matches']";
		List<List> result1 = (List) QlSpelUtil.query(m, mts_exp);

		SqlSession session = MybatisUtil.getConn();
		int n = 0;
		for (List MatchItem : result1) {

			// param.put("home_id", ((List) MatchItem.get(5)).get(0));3381484
			try {

				java.text.NumberFormat NF = java.text.NumberFormat.getInstance();
//设置数的小数部分所允许的最大位数，避免小数位被舍掉
				NF.setMaximumFractionDigits(0);
//设置数的小数部分所允许的最小位数，避免小数位有多余的0
				NF.setMinimumFractionDigits(0);
//去掉科学计数法显示，避免显示为111,111,111,111
				NF.setGroupingUsed(false);
//System.out.println(NF.format(a));

				LinkedHashMap param = Maps.newLinkedHashMap();
				param.put("id", MatchItem.get(0));
				param.put("match_id", MatchItem.get(0));
				param.put("match_event_id", MatchItem.get(1));
				int stt = ((Double) MatchItem.get(2)).intValue();
			//	if (stt >= 2 && stt <= 7) { // ing match
					n++;
					logger.info("==>n:" + n);
					param.put("match_status", NF.format(MatchItem.get(2)));
					param.put("match_time", ((Double) MatchItem.get(3)).intValue());
					param.put("tee_time", ((Double) MatchItem.get(4)).intValue());

					param.put("tee_time sub match_time",
							((Double) MatchItem.get(4)).intValue() - ((Double) MatchItem.get(3)).intValue());
					param.put("home_id", ((List) MatchItem.get(5)).get(0));
					param.put("away_id", ((List) MatchItem.get(6)).get(0));
					param.put("match_detail", ((List) MatchItem.get(7)).get(0));
					param.put("which_round", ((List) MatchItem.get(7)).get(1));
					param.put("neutral_site", ((List) MatchItem.get(7)).get(2));

					logger.info(param);
					// SqlSession session=SqlSessionFactory1.openSession(true);
					int r = session.insert("com.kok.sport.dao.FootballMatchDao.into_football_match_t", param);
					logger.info("==>ret insert(\"com.kok.sport.dao.FootballMatchDao.into_football_match_t::" + r);

			//	}

			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

}
