package com.kok.sport.utils.mockdata;

import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.kok.sport.utils.db.MybatisUtil;

public class saichenTestDAtaInsert {
	/**
	 * 
	 * 
	 * M01-brian [前端], [14.04.20 17:24] 赛程是包含（未开赛， 延迟）这个参数我怎么传
	 * 
	 * M01-brian [前端], [14.04.20 17:25] 赛程的数据是包含未开赛和 延迟的数据的
	 * 
	 * 其中[1] 对应[未开赛]，[2-7] 对应[进行中]，[8] 对应[已结束]，[9-13] 对应 [延迟]
	 * 
	 * select * from foot_match_v_all
	 * 
	 * 
	 * WHERE match_status = 1 OR match_status BETWEEN 9 AND 13;
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String sql = "select * from football_match_t where  id>2700011 limit 30";
		List<LinkedHashMap> li = MybatisUtil.getMybatisMapper().querySql(sql);
		int i = 0;
		for (LinkedHashMap linkedHashMap : li) {
			Date d = DateUtils.addDays(new Date(), i);
			System.out.println(d);
			long timestmp_sec = (d.getTime() / 1000);
			String s = "update football_match_t set match_time= {0} where id=" + linkedHashMap.get("id");
			s = MessageFormat.format(s,String.valueOf(timestmp_sec)  );
			System.out.println(s);
		 System.out.println(MybatisUtil.getMybatisMapper().update(s));	;

			i++;
		}
		System.out.println("f");
	}

}
