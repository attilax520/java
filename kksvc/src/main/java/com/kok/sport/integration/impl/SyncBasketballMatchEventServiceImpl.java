package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.base.exception.ApplicationException;
import com.kok.sport.entity.*;
import com.kok.sport.integration.SyncBasketballMatchEventService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service("syncBasketballMatchEventService")
public class SyncBasketballMatchEventServiceImpl implements SyncBasketballMatchEventService {

    @Autowired
    private BasketballEventService basketballEventService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysCountryService sysCountryService;

    @Override
    public boolean matchEvent()throws ApplicationException {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Basketball.Basic.Matchevent_list");
        }};
        //获取篮球赛事资料返回字符串结果
        String matchMap = HttpUtil.sendGet(map);
        if(matchMap != null && matchMap.length() > 0){
            JSONObject jsonObject = JSON.parseObject(matchMap);
            //获取篮球赛事资料中data数据
            String data = jsonObject.getString("data");
            if (data != null && !"{}".equals(data)) {
                JSONObject jsonObjectData = JSON.parseObject(data);

                //获取地区信息字段areas
                String areas = jsonObjectData.getString("areas");
                //获取国家字段countrys
                String countrys = jsonObjectData.getString("countrys");
                //获取赛事字段matchevents
                String matchevents = jsonObjectData.getString("matchevents");
                if(areas != null && areas.length() != 0) {
                    JSONObject jsonObjectAreas = JSON.parseObject(areas);
                    if(jsonObjectAreas.size() > 0){
                        for (Object key : jsonObjectAreas.keySet()) {
                            String keyStr = (String)key;
                            Object keyvalue = jsonObjectAreas.get(keyStr);
                            JSONObject objectAreas = (JSONObject) JSON.toJSON(keyvalue);
                            if(objectAreas.size() > 0){
                                //地区表
                                SysArea area = new SysArea();
                                area.setId(Long.parseLong(objectAreas.get("id").toString()));
                                area.setNameEn(objectAreas.get("name_en").toString());
                                area.setNameZh(objectAreas.get("name_zh").toString());
                                area.setNameZht(objectAreas.get("name_zht").toString());
                                area.setCreateTime(LocalDateTime.now());
                                area.setDeleteFlag("0");
                                if (sysAreaService.getById(area.getId()) != null){
                                    sysAreaService.updateById(area);
                                }else{
                                    sysAreaService.save(area);
                                }
                            }

                        }
                    }
                }
                if(countrys != null && countrys.length() != 0) {
                    JSONObject jsonObjectCountrys = JSON.parseObject(countrys);
                    if(jsonObjectCountrys.size() > 0){
                        for (Object key : jsonObjectCountrys.keySet()) {
                            String keyStr = (String)key;
                            Object keyvalue = jsonObjectCountrys.get(keyStr);
                            JSONObject objectCountrys = (JSONObject) JSON.toJSON(keyvalue);
                            if(objectCountrys.size() > 0){
                                //国家表
                                SysCountry sysCountry = new SysCountry();
                                sysCountry.setId(Long.parseLong(objectCountrys.get("id").toString()));
                                sysCountry.setAreaId(Long.parseLong(objectCountrys.get("area_id").toString()));
                                sysCountry.setNameZh(objectCountrys.get("name_zh").toString());
                                sysCountry.setNameZht(objectCountrys.get("name_zht").toString());
                                sysCountry.setNameEn(objectCountrys.get("name_en").toString());
                                sysCountry.setCreateTime(LocalDateTime.now());
                                sysCountry.setDeleteFlag("0");
                                if (sysCountryService.getById(sysCountry.getId()) != null){
                                    sysCountryService.updateById(sysCountry);
                                }else{
                                    sysCountryService.save(sysCountry);
                                }
                            }

                        }
                    }
                }
                if(matchevents != null && matchevents.length() != 0) {
                    JSONArray arrayTlive = JSON.parseArray(matchevents);
                    if(arrayTlive.size() > 0){
                        for(int i=0;i<arrayTlive.size();i++) {
                            JSONObject jsonObjectMatchevents = JSON.parseObject(arrayTlive.get(i).toString());
                            //篮球赛事表
                            BasketballEvent basketballEvent = new BasketballEvent();
                            basketballEvent.setId(Long.parseLong(jsonObjectMatchevents.get("id").toString()));
                            basketballEvent.setAreaId(Long.parseLong(jsonObjectMatchevents.get("area_id").toString()));
                            basketballEvent.setCountryId(Long.parseLong(jsonObjectMatchevents.get("country_id").toString()));
                            basketballEvent.setMatchLogo(jsonObjectMatchevents.get("logo").toString());
                            basketballEvent.setNameZh(jsonObjectMatchevents.get("name_zh").toString());
                            basketballEvent.setNameZht(jsonObjectMatchevents.get("name_zht").toString());
                            basketballEvent.setNameEn(jsonObjectMatchevents.get("name_en").toString());
                            basketballEvent.setShortNameZh(jsonObjectMatchevents.get("short_name_zh").toString());
                            basketballEvent.setShortNameZht(jsonObjectMatchevents.get("short_name_zht").toString());
                            basketballEvent.setShortNameEn(jsonObjectMatchevents.get("short_name_en").toString());
                            basketballEvent.setCreateTime(LocalDateTime.now());
                            basketballEvent.setDeleteFlag("0");
                            if (basketballEventService.getById(basketballEvent.getId()) != null){
                                basketballEventService.updateById(basketballEvent);
                            }else{
                                basketballEventService.save(basketballEvent);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
