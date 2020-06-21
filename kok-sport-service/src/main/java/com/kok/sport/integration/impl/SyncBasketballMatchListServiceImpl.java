package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.kok.sport.dao.*;
import com.kok.sport.integration.SyncBasketballMatchListService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpRequestUtil;
import com.kok.sport.vo.*;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SyncBasketballMatchListServiceImpl implements SyncBasketballMatchListService {

    private BasketballTeamService basketballTeamService;

    private BasketballEventService basketballEventService;

    private BasketballStageService basketballStageService;

    private BasketballMatchService basketballMatchService;

    private BasketballScoreService basketballScoreService;

    /**
     * 前后30天的全量赛程赛果数据,及赛程中比赛涉及到的球队及赛事信息
     * @return
     */
    @Override
    public boolean basketballMatchList() {
        String url = "http://www.skrsport.live/?service=Basketball.Live.Match_list&username=sport_api&secret=0gclkqzK";
        String result = HttpRequestUtil.doGet(url);
        //解析返回结果
        JSONObject resultObj = JSONObject.parseObject(result);
        if(resultObj.getLong("ret").equals(200L)) {
            JSONObject dataObj = resultObj.getJSONObject("data");
            //todo: 球队列表
            JSONObject teamsObj = dataObj.getJSONObject("teams");
            teamsOperate(teamsObj);
            //todo: 赛事列表
            JSONObject eventsObj = dataObj.getJSONObject("events");
            eventsOperate(eventsObj);
            //todo: 赛事阶段列表
            JSONArray stagesArr = dataObj.getJSONArray("stages");
            stagesOperate(stagesArr);
            //todo: 比赛列表
            JSONArray matchesArr = dataObj.getJSONArray("matches");
            matchesOperate(matchesArr);
            return true;
        }
        return false;
    }

    /**
     * 球队列表逻辑处理
     * @param teamsObj
     */
    private void teamsOperate(JSONObject teamsObj) {
        Map<String, JSONObject> teamsMap = JSONObject.parseObject(teamsObj.toJSONString(), new TypeReference<Map<String, JSONObject>>(){});
        List<BasketballTeamVO> teamList = new ArrayList();
        teamsMap.forEach((k, v) -> {
            BasketballTeamVO teamVO = new BasketballTeamVO();
            teamVO.setId(v.getLong("id"));
            teamVO.setNameZh(v.getString("name_zh"));
            teamVO.setNameZht(v.getString("name_zht"));
            teamVO.setNameEn(v.getString("name_en"));
            teamVO.setShortNameZh(v.getString("short_name_zh"));
            teamVO.setShortNameZht(v.getString("short_name_zht"));
            teamVO.setShortNameEn(v.getString("short_name_en"));
            teamVO.setLogo(v.getString("logo"));
            teamVO.setCreateTime(LocalTime.now());
            teamVO.setDeleteFlag('0');
            teamList.add(teamVO);
        });
        //入库
        basketballTeamService.saveBasketballTeam(teamList);
    }

    /**
     * 赛事列表逻辑处理
     * @param eventsObj
     */
    private void eventsOperate(JSONObject eventsObj) {
        Map<String, JSONObject> eventsMap = JSONObject.parseObject(eventsObj.toJSONString(), new TypeReference<Map<String, JSONObject>>(){});
        List<BasketballEventVO> eventList = new ArrayList();
        eventsMap.forEach((k, v) -> {
            BasketballEventVO eventVO = new BasketballEventVO();
            eventVO.setId(v.getLong("id"));
            eventVO.setNameZh(v.getString("name_zh"));
            eventVO.setNameZht(v.getString("name_zht"));
            eventVO.setNameEn(v.getString("name_en"));
            eventVO.setShortNameZh(v.getString("short_name_zh"));
            eventVO.setShortNameZht(v.getString("short_name_zht"));
            eventVO.setShortNameEn(v.getString("short_name_en"));
            eventVO.setLogo(v.getString("logo"));
            eventVO.setCreateTime(LocalTime.now());
            eventVO.setDeleteFlag('0');
            eventList.add(eventVO);
        });
        //入库
        basketballEventService.saveBasketballEvent(eventList);
    }

    /**
     * 赛事阶段列表逻辑处理
     * @param stagesArr
     */
    private void stagesOperate(JSONArray stagesArr) {
        List<BasketballStageVO> stageList = new ArrayList();
        stagesArr.forEach(x -> {
            JSONObject stageObj = JSONObject.parseObject(x.toString());
            BasketballStageVO stageVO = new BasketballStageVO();
            stageVO.setId(stageObj.getLong("id"));
            stageVO.setNameZh(stageObj.getString("name_zh"));
            stageVO.setNameZht(stageObj.getString("name_zht"));
            stageVO.setNameEn(stageObj.getString("name_en"));
            stageVO.setCreateTime(LocalTime.now());
            stageVO.setDeleteFlag('0');
            stageList.add(stageVO);
        });
        //入库
        basketballStageService.saveBasketballStage(stageList);
    }

    /**
     * 比赛列表逻辑处理
     * @param matchesArr
     */
    private void matchesOperate(JSONArray matchesArr) {
        List<BasketballMatchVO> matchList = new ArrayList();
        List<BasketballScoreVO> scoreList = new ArrayList();
        matchesArr.forEach(x -> {
            JSONArray matchArr = JSONObject.parseArray(x.toString());
            BasketballMatchVO matchVO = new BasketballMatchVO();
            matchVO.setId(Long.valueOf(matchArr.get(0).toString()));
            matchVO.setTotalSections(Long.valueOf(matchArr.get(1).toString()));
            matchVO.setMatchEventId(Long.valueOf(matchArr.get(2).toString()));
            matchVO.setMatchStatus(Long.valueOf(matchArr.get(3).toString()));
            matchVO.setMatchTime(Long.valueOf(matchArr.get(4).toString()));
            matchVO.setCurrentTotalSeconds(Long.valueOf(matchArr.get(5).toString()));

            List<String> homeList = JSONObject.parseArray(matchArr.get(6).toString(), String.class);
            matchVO.setHomeId(Long.valueOf(homeList.get(0)));

            List<String> awayList = JSONObject.parseArray(matchArr.get(7).toString(), String.class);
            matchVO.setAwayId(Long.valueOf(awayList.get(0)));

            List<String> detailList = JSONObject.parseArray(matchArr.get(8).toString(), String.class);
            matchVO.setMatchDetail(detailList.get(0));
            matchVO.setCompatible(detailList.get(1));
            matchVO.setAnimation(Long.valueOf(detailList.get(2)));
            matchVO.setIntelligence(Long.valueOf(detailList.get(3)));

            List<String> seasonList = JSONObject.parseArray(matchArr.get(9).toString(), String.class);
            matchVO.setMatchType(Long.valueOf(seasonList.get(0)));
            matchVO.setStagesId(Long.valueOf(seasonList.get(1)));
            matchVO.setSeasonId(Long.valueOf(seasonList.get(2)));

            matchVO.setCreateTime(LocalTime.now());
            matchVO.setDeleteFlag('0');
            matchList.add(matchVO);

            //主队比分
            BasketballScoreVO homeScoreVO = new BasketballScoreVO();
            homeScoreVO.setMatchId(matchVO.getId());
            homeScoreVO.setTeamId(matchVO.getHomeId());
            homeScoreVO.setTeamType(1L);
            homeScoreVO.setMatchStatus(matchVO.getMatchStatus());
            homeScoreVO.setTime(matchVO.getMatchTime());
            homeScoreVO.setFirstSectionScores(Long.valueOf(homeList.get(2)));
            homeScoreVO.setSecondSectionScores(Long.valueOf(homeList.get(3)));
            homeScoreVO.setThirdSectionScores(Long.valueOf(homeList.get(4)));
            homeScoreVO.setFourthSectionScores(Long.valueOf(homeList.get(5)));
            homeScoreVO.setOvertimeScores(Long.valueOf(homeList.get(6)));
            homeScoreVO.setCreateTime(LocalTime.now());
            homeScoreVO.setDeleteFlag('0');
            scoreList.add(homeScoreVO);

            //客队比分
            BasketballScoreVO awayScoreVO = new BasketballScoreVO();
            awayScoreVO.setMatchId(matchVO.getId());
            awayScoreVO.setTeamId(matchVO.getHomeId());
            awayScoreVO.setTeamType(1L);
            awayScoreVO.setMatchStatus(matchVO.getMatchStatus());
            awayScoreVO.setTime(matchVO.getMatchTime());
            awayScoreVO.setFirstSectionScores(Long.valueOf(awayList.get(2)));
            awayScoreVO.setSecondSectionScores(Long.valueOf(awayList.get(3)));
            awayScoreVO.setThirdSectionScores(Long.valueOf(awayList.get(4)));
            awayScoreVO.setFourthSectionScores(Long.valueOf(awayList.get(5)));
            awayScoreVO.setOvertimeScores(Long.valueOf(awayList.get(6)));
            awayScoreVO.setCreateTime(LocalTime.now());
            awayScoreVO.setDeleteFlag('0');
            scoreList.add(awayScoreVO);
        });
        //入库
        basketballMatchService.saveBasketballMatch(matchList);
        basketballScoreService.saveBasketballScore(scoreList);
    }
}
