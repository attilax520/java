package com.kok.sport.utils.mockdata;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.JsonGsonUtil;
import com.kok.sport.utils.db.MybatisUtil;
import com.kok.sport.utils.ql.QlSpelUtil;
@SuppressWarnings("all")
public class livedata {

	public static void main(String[] args) throws Exception {

		//1 进球 2 角球  3黄牌 12  下半场结束  10上半场比赛开始  11 上半场结束 16  点球  0 普通事件   4红牌
		groupby();
		// extracted();

		// TODO Auto-generated method stub

//		JsonObject json =CaptchData. getJsonRzt("Football.Live.Match_detail_live");
//		Map m = JsonGsonUtil.toMap(json);
//		String mts_exp = "#root['data']";
//		List<Map> result1 = (List) QlSpelUtil.query(m, mts_exp);
//		

	}

	private static void groupby() throws IOException {
		List<Map> li2 = Lists.newArrayList();
		File[] fa = new File("D:\\cache").listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("Football.Analysis.Match_detail_matchid"))
					return true;
				return false;
			}
		});
		for (File file : fa) {
			try {
				String s = FileUtils.readFileToString(file);
				JsonObject jo = new JsonParser().parse(s).getAsJsonObject();
				Map m = JsonGsonUtil.toMap(jo);
				String mts_exp = "#root['data']['tlive']"; //
				Object query = QlSpelUtil.query(m, mts_exp);
			
				List<Map> result1 = (List) query;
				for (Map file2 : result1) {
					li2.add(file2);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		Map<String, List<Map>> employeesByCity = li2.stream()
				.collect(Collectors.groupingBy(m -> m.get("type").toString()));
		String jsonString = JSON.toJSONString(employeesByCity, true);
		FileUtils.write(new File("d:\\tlivegrp.json"), jsonString);
		System.out.println( jsonString);

	}

	private static void extracted() throws Exception {
		List<String> li_rzt_matchids = matchids();
		System.out.println(li_rzt_matchids);

		for (String matchid : li_rzt_matchids) {
			String svc = "Football.Analysis.Match_detail";
			JsonObject json = CaptchData.getJsonRzt(svc, "&id=" + matchid, svc + "_matchid_" + matchid);
			System.out.println(json);

		}
	}

	public static List<String> matchids() throws Exception {
		SqlSession session = MybatisUtil.getConn();
		List<Map> sList = MybatisUtil.executeSql("select * from football_match_t");
		List<String> li_rzt_matchids = sList.stream().map(s -> s.get("id").toString()).collect(Collectors.toList());
		return li_rzt_matchids;
	}

}
