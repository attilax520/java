package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.integration.SyncFootballNumberOddsLiveHistoryService;
import com.kok.sport.service.FootballOddsService;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 足球实时指数
 *      对应接口返回实时变动的赔率数据，无赔率变动的比赛不反回
 */
@Service
public class SyncFootballNumberOddsLiveHistoryServiceImpl implements SyncFootballNumberOddsLiveHistoryService {
    @Autowired
    private FootballOddsService footballOddsService;

    /**
     * 拉取足球实时指数数据
     * @return
     */
    @Override
    public boolean insertFootballLiveOdds() {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Football.Number.Odds_live_history");
        }};
        List<FootballOdds> list = new ArrayList<>();
        JSONObject oddsData = JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONObject("data");
        Set<String> keySet = oddsData.keySet();
        for (String key : keySet) {
            for (Map.Entry<String, Object> entry : oddsData.entrySet()) {
                String k = entry.getKey();
                Object y = entry.getValue();
                if (key.equals(k)) {
                    JSONArray yData = JSONArray.parseArray(y.toString());
                    for (int i = 0; i < yData.size(); i++) {
                        JSONArray array = JSONArray.parseArray(yData.getString(i));
                        JSONArray arrayData = array.getJSONArray(2);
                        // "6.0,4.5,0.11,0",//大球,盘口,小球,是否封盘：1-封盘,0-未封盘
                        String[] str = arrayData.getString(2).split(",");
                        FootballOdds footballOdds = new FootballOdds() {{
//                            setId(1L);              //主键Id
                            setCompanyId(Long.parseLong(key));         //公司Id
                            setMatchId(array.getLong(0));       //比赛Id
                            // 指数类型 1,asia-亚盘
                            if ("asia".equals(array.getString(1))) {
                                setOddsType(1);
                                // 2,bs-大小球
                            } else if ("bs".equals(array.getString(1))) {
                                setOddsType(2);
                                //3,eu-欧赔
                            } else if ("eu".equals(array.getString(1))) {
                                setOddsType(3);
                            } else {
                                //出错
                                setOddsType(0);
                            }
                            setChangeTime(arrayData.getLong(0));        //变化时间
                            setHappenTime(arrayData.getString(1));        //比赛进行时间 未开始时为空
                            setMatchStatus(arrayData.getInteger(3));       //比赛状态
                            setHomeOdds(new BigDecimal(str[0]));          //主队赔率(大球)
                            setTieOdds(new BigDecimal(str[1]));           //和局赔率(盘口)
                            setAwayOdds(new BigDecimal(str[2]));          //客队赔率(小球)
                            setLockFlag(Integer.parseInt(str[3]));          //是否封盘 0-否 1-是
                            setRealTimeScore(array.getString(3));     //实时比分
                            setCreateTime(LocalDateTime.now());     //创建时间
                            setDeleteFlag("0");     //是否删除 0-未删除 1-已删除
                        }};
                        footballOdds.setId((long) i + Long.parseLong(key));              //主键Id
                        if (footballOddsService.findRepeatData(footballOdds.getCompanyId(), footballOdds.getMatchId(), footballOdds.getChangeTime()) != null) {
                            footballOddsService.updateById(footballOdds);
                        } else {
                            list.add(footballOdds);
                        }
                    }
                }
            }
        }
        return footballOddsService.saveBatch(list);
    }

}
