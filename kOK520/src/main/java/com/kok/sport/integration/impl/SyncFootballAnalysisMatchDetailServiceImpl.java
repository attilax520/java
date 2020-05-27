package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballEnvironment;
import com.kok.sport.integration.SyncFootballAnalysisMatchDetailService;
import com.kok.sport.service.FootballEnvironmentService;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 拉取足球比赛详情接口数据
 */
@Service
public class SyncFootballAnalysisMatchDetailServiceImpl implements SyncFootballAnalysisMatchDetailService {

    @Autowired
    private FootballEnvironmentService footballEnvironmentService;

    /**
     * 拉取足球比赛环境数据
     * @param Id 比赛Id
     * @return
     */
    @Override
    public boolean insertFootballAnalysisApiData(String Id) {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Football.Analysis.Match_detail");
            put("id",Id);
        }};

         JSONObject jsonData = JSONObject.parseObject(HttpUtil.sendGet(map))
                 .getJSONObject("data")
                 .getJSONObject("info")
                 .getJSONObject("environment");
        FootballEnvironment footballEnvironment = new FootballEnvironment(){{
            setId(1L); //主键ID 非自增(暂时)

            setMatchId(Long.parseLong(Id));         //赛事Id
            setPressure(jsonData.getString("pressure"));    //气压
            setTemperature(jsonData.getString("temperature"));     //温度
            setWind(jsonData.getString("wind"));            //风速
            setHumidity(jsonData.getString("humidity"));        //湿度
            setWeatherId(jsonData.getLong("weather_id"));         //天气id
            setWeather(jsonData.getString("weather"));         //天气
            setWeatherImage(jsonData.getString("weather_image"));    //天气 logo
            setCreateTime(LocalDateTime.now());              //创建时间
            setDeleteFlag("0");                              //是否删除 1 已删除  0 未删除
        }};



        return footballEnvironmentService.save(footballEnvironment);
    }
}
