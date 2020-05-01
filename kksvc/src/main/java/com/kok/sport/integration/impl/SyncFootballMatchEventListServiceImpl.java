package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballEvent;
import com.kok.sport.integration.SyncFootballMatchEventListService;
import com.kok.sport.service.FootballEventService;
import com.kok.sport.service.FootballMatchService;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 足球赛事数据
 */
@Service
public class SyncFootballMatchEventListServiceImpl implements SyncFootballMatchEventListService {

    @Autowired
    private FootballEventService footballEventService;

    @Autowired
    private FootballMatchService footballMatchService;


    /**
     * 拉取足球赛事数据
     * @return
     */
    @Override
    public boolean insertMarchEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("service","Football.Basic.Matchevent_list");
        JSONArray eventData = JSONObject.parseObject(JSONObject.parseObject(HttpUtil.sendGet(map)).getString("data")).getJSONArray("matchevents");
        List<FootballEvent> list = new ArrayList<>();
        for (int i = 0; i < eventData.size(); i++) {    //赛事数据为 null
            JSONObject eventJson = JSONObject.parseObject(eventData.getString(i));
            FootballEvent footballEvent = new FootballEvent();
            footballEvent.setId(eventJson.getLong("id"));                           //赛事Id
            //测试可用
            footballEvent.setSeasonId(eventJson.getLong("id"));                     //赛季Id
            //
            footballEvent.setAreaId(eventJson.getLong("area_id"));                  //地区Id
            footballEvent.setCountryId(eventJson.getLong("country_id"));            //国家Id
            //
            footballEvent.setType(eventJson.getInteger("type"));                    //赛事类型
            footballEvent.setLevel(eventJson.getInteger("level"));                  //忽略兼容使用
            footballEvent.setNameZh(eventJson.getString("name_zh"));                //中文名称
            footballEvent.setShortNameZh(eventJson.getString("short_name_zh"));     //中文名称缩写
            footballEvent.setNameZht(eventJson.getString("name_zht"));              //粤语名称
            footballEvent.setShortNameZht(eventJson.getString("short_name_zht"));   //粤语名称缩写
            footballEvent.setNameEn(eventJson.getString("name_en"));                //英文名称
            footballEvent.setShortNameEn(eventJson.getString("short_name_en"));     //英文名称缩写
            footballEvent.setMatchLogo(eventJson.getString("logo"));                //赛事logo
            footballEvent.setDeleteFlag("0");                                            //是否已删除 0 未删除 1
            footballEvent.setCreateTime(LocalDateTime.now());                            //创建时间

            if (footballEventService.getById(footballEvent.getId()) != null){
                footballEventService.updateById(footballEvent);
            }else {
                list.add(footballEvent);
            }
        }

        return footballEventService.saveBatch(list);
    }

    /**
     * 拉去足球删除比赛数据,判断比赛是否删除
     * @return
     */
    @Override
    public String insertDeleteMarchEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("service","Football.Basic.Match_deleted_ids");
        JSONArray deleteData = JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONArray("data");
        for (int i = 0; i < deleteData.size(); i++) {
            //football_match_t
            //根据比赛id查询是否存在记录
            if (footballMatchService.getById(deleteData.getInteger(i)) != null){
                //修改 是否删除
                footballMatchService.updateDeleteFlagById(deleteData.getInteger(i));
            }
        }
        return "success";
    }
}
