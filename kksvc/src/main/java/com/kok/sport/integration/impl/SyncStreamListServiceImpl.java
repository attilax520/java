package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.*;
import com.kok.sport.integration.SyncStreamListService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SyncStreamListServiceImpl implements SyncStreamListService {

    @Autowired
    private MatchStreamService matchStreamService;

    @Override
    public boolean insertVideoApi() {
        Map<String,String> map = new HashMap<>();
        map.put("service","App.Site.Stream_list_new");
        JSONArray apiData = JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONArray("data");

        List<MatchStream> videoList = new ArrayList<>();

        for (int i = 0; i < apiData.size(); i++) {
            JSONObject jsonData = JSONObject.parseObject(apiData.getString(i));
            MatchStream matchStream = new MatchStream();
            matchStream.setSportType(jsonData.getInteger("sport_id"));
            matchStream.setMatchId(jsonData.getLong("match_id"));
            matchStream.setMatchTime(jsonData.getLong("match_time"));
            matchStream.setComp(jsonData.getString("comp"));
            matchStream.setHome(jsonData.getString("home"));
            matchStream.setAway(jsonData.getString("away"));
            matchStream.setMatchStatus(jsonData.getInteger("match_status"));
            matchStream.setPlayUrl(jsonData.getString("play_url "));
            matchStream.setCreateTime(LocalDateTime.now());
            matchStream.setDeleteFlag("0");
            if (matchStreamService.findMatchStreamByMatchId(matchStream.getMatchId()) != null){
                matchStreamService.updateById(matchStream);
            }else{
                videoList.add(matchStream);
            }
        }
        return   matchStreamService.saveBatch(videoList);
    }
}
