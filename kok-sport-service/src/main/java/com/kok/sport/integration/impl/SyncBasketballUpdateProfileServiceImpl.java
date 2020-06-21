package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.kok.sport.integration.SyncBasketballUpdateProfileService;
import com.kok.sport.service.BasketballEventService;
import com.kok.sport.service.BasketballTeamService;
import com.kok.sport.utils.constant.HttpRequestUtil;
import com.kok.sport.vo.BasketballEventVO;
import com.kok.sport.vo.BasketballTeamVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SyncBasketballUpdateProfileServiceImpl implements SyncBasketballUpdateProfileService {

    private BasketballTeamService basketballTeamService;

    private BasketballEventService basketballEventService;

    /**
     * 近2小时内有变动（增、删、改）的球队、赛事信息
     * @return
     */
    @Override
    public boolean basketballUpdateProfileList() {
        String url = "http://www.skrsport.live/?service=Basketball.Basic.Update_profile&username=sport_api&secret=0gclkqzK";
        String result = HttpRequestUtil.doGet(url);
        //解析返回结果
        JSONObject resultObj = JSONObject.parseObject(result);
        if(resultObj.getLong("ret").equals(200)) {
            Map<String, JSONObject> dataMap = JSONObject.parseObject(resultObj.getString("data"), new TypeReference<Map<String, JSONObject>>(){});
            //修改
            Map<String, JSONArray> updateMap = JSONObject.parseObject(dataMap.get("update").toJSONString(), Map.class);
            JSONArray updateTeamList = updateMap.get("teams");
            JSONArray updateCompetitonList = updateMap.get("competitons");
            if(updateTeamList != null && updateTeamList.size() > 0) {
                List<BasketballTeamVO> teamList = new ArrayList();
                for (Object updateTeam : updateTeamList) {
                    JSONObject updateTeamObj = JSONObject.parseObject(updateTeam.toString());
                    BasketballTeamVO teamVO = new BasketballTeamVO();
                    teamVO.setId(updateTeamObj.getLong("id"));
                    teamVO.setMatcheventId(updateTeamObj.getLong("matchevent_id"));
                    teamVO.setConferenceId(updateTeamObj.getLong("conference_id"));
                    teamVO.setNameZh(updateTeamObj.getString("name_zh"));
                    teamVO.setNameZht(updateTeamObj.getString("name_zht"));
                    teamVO.setNameEn(updateTeamObj.getString("name_en"));
                    teamVO.setShortNameZh(updateTeamObj.getString("short_name_zh"));
                    teamVO.setShortNameZht(updateTeamObj.getString("short_name_zht"));
                    teamVO.setShortNameEn(updateTeamObj.getString("short_name_en"));
                    teamVO.setLogo(updateTeamObj.getString("logo"));
                    teamList.add(teamVO);
                }
                basketballTeamService.saveBasketballTeam(teamList);
            }
            if(updateCompetitonList != null && updateCompetitonList.size() > 0) {
                List<BasketballEventVO> eventList = new ArrayList();
                for (Object updateCompetition : updateCompetitonList) {
                    JSONObject updateCompetitionObj = JSONObject.parseObject(updateCompetition.toString());
                    BasketballEventVO eventVO = new BasketballEventVO();
                    eventVO.setId(updateCompetitionObj.getLong("id"));
                    eventVO.setAreaId(updateCompetitionObj.getLong("area_id "));
                    eventVO.setCountryId(updateCompetitionObj.getLong("country_id"));
                    eventVO.setNameZh(updateCompetitionObj.getString("name_zh"));
                    eventVO.setNameZht(updateCompetitionObj.getString("name_zht"));
                    eventVO.setNameEn(updateCompetitionObj.getString("name_en"));
                    eventVO.setShortNameZh(updateCompetitionObj.getString("short_name_zh"));
                    eventVO.setShortNameZht(updateCompetitionObj.getString("short_name_zht"));
                    eventVO.setShortNameEn(updateCompetitionObj.getString("short_name_en"));
                    eventVO.setLogo(updateCompetitionObj.getString("logo"));
                    eventList.add(eventVO);
                }
                basketballEventService.saveBasketballEvent(eventList);
            }

            //删除
            Map<String, JSONArray> deleteMap = JSONObject.parseObject(dataMap.get("delete").toJSONString(), Map.class);
            List<Long> deleteTeamList = JSONObject.parseArray(deleteMap.get("teams").toJSONString(), Long.class);
            List<Long> deleteCompetitonList = JSONObject.parseArray(deleteMap.get("competitons").toJSONString(), Long.class);
            if(deleteTeamList != null && deleteTeamList.size() > 0) {
                basketballTeamService.deleteBasketballTeam(deleteTeamList);
            }
            if(deleteCompetitonList != null && deleteCompetitonList.size() > 0) {
                basketballEventService.deleteBasketballEvent(deleteCompetitonList);
            }
            return true;
        }
        return false;
    }
}
