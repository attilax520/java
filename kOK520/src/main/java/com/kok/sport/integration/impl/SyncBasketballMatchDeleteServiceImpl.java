package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.base.exception.ApplicationException;
import com.kok.sport.entity.*;
import com.kok.sport.integration.SyncBasketballMatchDeleteService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service("syncBasketballMatchDeleteService")
public class SyncBasketballMatchDeleteServiceImpl implements SyncBasketballMatchDeleteService {

    @Autowired
    private BasketballMatchService basketballMatchService;

    @Override
    public boolean matchDelete()throws ApplicationException {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Basketball.Basic.Match_deleted_ids");
        }};
        //获取篮球删除比赛返回字符串结果
        JSONArray apiData = JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONArray("data");
        if(apiData.size() > 0){
            for(int i=0;i<apiData.size();i++) {
                //删除篮球比赛表
                BasketballMatch basketballMatch = basketballMatchService.getById(Integer.parseInt(apiData.get(i).toString()));
                if (basketballMatch != null){
                    basketballMatch.setDeleteFlag("1");
                    basketballMatchService.updateById(basketballMatch);
                }
            }
            return true;
        }
        return false;
    }
}
