package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballFormation;
import com.kok.sport.integration.SyncFootballMatchLineupService;
import com.kok.sport.service.FootballFormationService;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 足球比赛阵容数据
 */
@Service
public class SyncFootballMatchLineupServiceImpl implements SyncFootballMatchLineupService {

    @Autowired
    private FootballFormationService footballFormationService;

    /**
     * 拉取足球比赛阵容数据
     * @param Id
     * @return
     */
    @Override
    public boolean insertMatchLineupData(String Id) {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Football.Analysis.Match_lineup");
            put("id",Id);
        }};
        JSONObject jsonData = JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONObject("data");
        FootballFormation footballFormation = new FootballFormation(){{
            setId(1L);      //主键 Id 非自增

            setMatchId(Long.parseLong(Id));     //比赛Id
            setConfirmed(1);             //是否是正式整容 (0/1)
            setHomeFormation("testData");     //主队阵型
            setAwayFormation("testData");     //客队阵型
            setHomeId(1L);                            //主队Id
            setAwayId(1L);                            //客队Id
            setCreateTime(LocalDateTime.now());                              //创建时间
            setDeleteFlag("0");                                              //是否删除 0 已删除 1未删除

           /* setId(1L);      //主键 Id 非自增

            setMatchId(Long.parseLong(Id));     //比赛Id
            setConfirmed(jsonData.getInteger("confirmed "));             //是否是正式整容 (0/1)
            setHomeFormation(jsonData.getString("home_formation "));     //主队阵型
            setAwayFormation(jsonData.getString("away_formation "));     //客队阵型
            setHomeId(jsonData.getLong(""));                            //主队Id
            setAwayId(jsonData.getLong(""));                            //客队Id
            setCreateTime(LocalDateTime.now());                              //创建时间
            setDeleteFlag("0");                                              //是否删除 0 已删除 1未删除*/

        }};

        return footballFormationService.save(footballFormation);
    }
}
