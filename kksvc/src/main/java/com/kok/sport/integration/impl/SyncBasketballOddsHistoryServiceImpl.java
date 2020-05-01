package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.BasketballOdds;
import com.kok.sport.integration.SyncBasketballOddsHistoryService;
import com.kok.sport.integration.SyncBasketballOddsLiveHistoryService;
import com.kok.sport.service.BasketballOddsService;
import com.kok.sport.utils.constant.HttpRequestUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncBasketballOddsHistoryServiceImpl implements SyncBasketballOddsHistoryService {

    private BasketballOddsService basketballOddsService;

    /**
     * 获取单场比赛所有指数公司的指数变化历史，从初盘到请求接口的时刻；
     * @return
     */
    @Override
    public boolean basketballOddsHistory() {
        Long matchId = 3517044L;
        String url = "http://www.skrsport.live/?service=Basketball.Number.Odds_history&username=sport_api&secret=0gclkqzK&id=" + matchId;
        String result = HttpRequestUtil.doGet(url);
        //解析返回结果
        JSONObject resultObj = JSONObject.parseObject(result);
        if(resultObj.getLong("ret").equals(200)) {
            Map<String, JSONObject> dataMap = JSONObject.parseObject(resultObj.getString("data"), Map.class);
            List<BasketballOdds> oddsList = new ArrayList<>();
            dataMap.forEach((k, v) -> {
                Map<String, JSONArray> oddsMap = JSONObject.parseObject(v.toJSONString(), Map.class);
                oddsMap.forEach((key, value) -> {
                    value.forEach(z -> {
                        JSONArray oddsArr = JSONObject.parseArray(z.toString());
                        BasketballOdds basketballOdds = new BasketballOdds();
                        //公司ID
                        basketballOdds.setCompanyId(Long.valueOf(k));
                        basketballOdds.setMatcheId(matchId);
                        switch (key) {
                            case "asia":
                                basketballOdds.setOddsType(1);
                            case "bs":
                                basketballOdds.setOddsType(2);
                            case "eu":
                                basketballOdds.setOddsType(3);
                        }
                        basketballOdds.setChangeTime(oddsArr.getLong(0));
                        basketballOdds.setHomeOdds(oddsArr.getBigDecimal(2));
                        basketballOdds.setTieOdds(oddsArr.getBigDecimal(3));
                        basketballOdds.setAwayOdds(oddsArr.getBigDecimal(4));
                        basketballOdds.setMatchStatus(oddsArr.getInteger(5));
                        basketballOdds.setLockFlag(oddsArr.getInteger(6));
                        basketballOdds.setCreateTime(LocalDateTime.now());
                        basketballOdds.setDeleteFlag("0");
                        oddsList.add(basketballOdds);
                    });
                });
            });
            //入库
            basketballOddsService.saveBasketballOdds(oddsList);
            return true;
        }
        return false;
    }
}
