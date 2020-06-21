package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.BasketballOdds;
import com.kok.sport.integration.SyncBasketballOddsLiveHistoryService;
import com.kok.sport.service.BasketballOddsService;
import com.kok.sport.utils.constant.HttpRequestUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncBasketballOddsLiveHistoryServiceImpl implements SyncBasketballOddsLiveHistoryService {

    private BasketballOddsService basketballOddsService;

    /**
     * 返回实时变动的赔率数据,无赔率变动的比赛不返回
     * @return
     */
    @Override
    public boolean basketballOddsLiveHistory() {
        String url = "http://www.skrsport.live/?service=Basketball.Number.Odds_live_history&username=sport_api&secret=0gclkqzK";
        String result = HttpRequestUtil.doGet(url);
        //解析返回结果
        JSONObject resultObj = JSONObject.parseObject(result);
        if(resultObj.getLong("ret").equals(200)) {
            Map<String, JSONArray> dataMap = JSONObject.parseObject(resultObj.getString("data"), Map.class);
            List<BasketballOdds> oddsList = new ArrayList<>();
            dataMap.forEach((k, v) -> {
                BasketballOdds basketballOdds = new BasketballOdds();
                //公司ID
                basketballOdds.setCompanyId(Long.valueOf(k));
                basketballOdds.setMatcheId(v.getLong(0));
                String oddsType = v.getString(1);
                switch (oddsType) {
                    case "asia":
                        basketballOdds.setOddsType(1);
                    case "bs":
                        basketballOdds.setOddsType(2);
                    case "eu":
                        basketballOdds.setOddsType(3);
                }
                List<String> tempList = JSONObject.parseArray(v.getString(2), String.class);
                basketballOdds.setChangeTime(Long.valueOf(tempList.get(0)));
                String[] oddsStr = tempList.get(2).split(",");
                basketballOdds.setHomeOdds(new BigDecimal(oddsStr[0]));
                basketballOdds.setTieOdds(new BigDecimal(oddsStr[1]));
                basketballOdds.setAwayOdds(new BigDecimal(oddsStr[2]));
                basketballOdds.setLockFlag(Integer.valueOf(oddsStr[3]));
                basketballOdds.setMatchStatus(Integer.valueOf(tempList.get(3)));
                basketballOdds.setRealTimeScore(v.getString(3));
                basketballOdds.setCreateTime(LocalDateTime.now());
                basketballOdds.setDeleteFlag("0");
                oddsList.add(basketballOdds);
            });
            //入库
            basketballOddsService.saveBasketballOdds(oddsList);
            return true;
        }
        return false;
    }
}
