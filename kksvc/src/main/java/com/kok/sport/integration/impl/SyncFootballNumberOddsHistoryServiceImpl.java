package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.integration.SyncFootballNumberOddsHistoryService;
import com.kok.sport.service.FootballOddsService;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 单场指数历史
 *      获取单场比赛所有公司的指数变化，从初盘到请求接口的时刻
 */
@Service
public class SyncFootballNumberOddsHistoryServiceImpl implements SyncFootballNumberOddsHistoryService {

    @Autowired
    private FootballOddsService footballOddsService;

    /**
     * 拉取单场指数历史
     * @param Id
     * @return
     */
    @Override
    public boolean insertFootballNumberOddsData(Long Id) {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Football.Number.Odds_history");
            put("id",Id.toString());
        }};
        JSONObject getData = JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONObject("data");
        Set<String> keySet = getData.keySet();
        //因为这边我无法确定有多少家公司，所以只能每次请求的时候将所有的 key 取出来，以其作为循环添加的基础
        List<FootballOdds> oddsList = new ArrayList<>();
        for (String k : keySet) {
            for (Map.Entry<String, Object> entry : getData.entrySet()) {
                String x = entry.getKey();
                Object y = entry.getValue();
                if (k.equals(x)) {
                    JSONObject yData = JSONObject.parseObject(y.toString());
                    //asia 亚盘
                    if (yData.get("asia") != null) {
                        JSONArray asiaData = yData.getJSONArray("asia");
                        for (int i = 0; i < asiaData.size(); i++) {
                            JSONArray forData = asiaData.getJSONArray(i);
                            FootballOdds footballOdd = new FootballOdds() {{
                                setId(1L);              //主键Id
                                setCompanyId(Long.parseLong(k));         //公司Id
                                setMatchId(Id);         //比赛Id
                                setOddsType(1);         //指数类型:1,asia-亚盘; 2,bs-大小球;3,eu-欧赔
                                setChangeTime(forData.getLong(0));        //变化时间
                                setHappenTime(forData.getString(1));        //比赛进行时间
                                setMatchStatus(forData.getInteger(5));       //比赛状态
                                setHomeOdds(forData.getBigDecimal(2));          //主队赔率
                                setTieOdds(forData.getBigDecimal(3));           //和局赔率
                                setAwayOdds(forData.getBigDecimal(4));          //客队赔率
                                setLockFlag(forData.getInteger(6));          //是否封盘 0-否 1-是
                                setRealTimeScore(forData.getString(7));     //实时比分
                                setCreateTime(LocalDateTime.now());     //创建时间
                                setDeleteFlag("0");     //是否删除 0-未删除 1-已删除
                            }};
                            if (footballOddsService.findRepeatData(footballOdd.getCompanyId(),footballOdd.getMatchId(),footballOdd.getChangeTime()) != null){
                                footballOddsService.updateById(footballOdd);
                            }else {
                                oddsList.add(footballOdd);
                            }
                        }
                    }
                    //eu 欧赔
                    if (yData.get("eu") != null) {
                        JSONArray euData = yData.getJSONArray("eu");
                        for (int i = 0; i < euData.size(); i++) {
                            JSONArray forData = euData.getJSONArray(i);
                            FootballOdds footballOdd = new FootballOdds() {{
                                setId(1L);              //主键Id
                                setCompanyId(Long.parseLong(k));         //公司Id
                                setMatchId(Id);         //比赛Id
                                setOddsType(3);         //指数类型:1,asia-亚盘; 2,bs-大小球;3,eu-欧赔
                                setChangeTime(forData.getLong(0));        //变化时间
                                setHappenTime(forData.getString(1));        //比赛进行时间
                                setMatchStatus(forData.getInteger(5));       //比赛状态
                                setHomeOdds(forData.getBigDecimal(2));          //主队赔率
                                setTieOdds(forData.getBigDecimal(3));           //和局赔率
                                setAwayOdds(forData.getBigDecimal(4));          //客队赔率
                                setLockFlag(forData.getInteger(6));          //是否封盘 0-否 1-是
                                setRealTimeScore(forData.getString(7));     //实时比分
                                setCreateTime(LocalDateTime.now());     //创建时间
                                setDeleteFlag("0");     //是否删除 0-未删除 1-已删除
                            }};
                            if (footballOddsService.findRepeatData(footballOdd.getCompanyId(),footballOdd.getMatchId(),footballOdd.getChangeTime()) != null){
                                footballOddsService.updateById(footballOdd);
                            }else {
                                oddsList.add(footballOdd);
                            }
                        }
                    }
                    //bs 大小球
                    if (yData.get("bs") != null) {
                        JSONArray bsData = yData.getJSONArray("bs");
                        for (int i = 0; i < bsData.size(); i++) {
                            JSONArray forData = bsData.getJSONArray(i);
                            FootballOdds footballOdd = new FootballOdds() {{
                                setId(1L);              //主键Id
                                setCompanyId(Long.parseLong(k));         //公司Id
                                setMatchId(Id);         //比赛Id
                                setOddsType(3);         //指数类型:1,asia-亚盘; 2,bs-大小球;3,eu-欧赔
                                setChangeTime(forData.getLong(0));        //变化时间
                                setHappenTime(forData.getString(1));        //比赛进行时间
                                setMatchStatus(forData.getInteger(5));       //比赛状态
                                setHomeOdds(forData.getBigDecimal(2));          //主队赔率
                                setTieOdds(forData.getBigDecimal(3));           //和局赔率
                                setAwayOdds(forData.getBigDecimal(4));          //客队赔率
                                setLockFlag(forData.getInteger(6));          //是否封盘 0-否 1-是
                                setRealTimeScore(forData.getString(7));     //实时比分
                                setCreateTime(LocalDateTime.now());     //创建时间
                                setDeleteFlag("0");     //是否删除 0-未删除 1-已删除
                            }};
                            if (footballOddsService.findRepeatData(footballOdd.getCompanyId(),footballOdd.getMatchId(),footballOdd.getChangeTime()) != null){
                                footballOddsService.updateById(footballOdd);
                            }else {
                                oddsList.add(footballOdd);
                            }
                        }
                    }
                }
            }
        }

        return footballOddsService.saveBatch(oddsList);
    }


}
