package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.base.exception.ApplicationException;
import com.kok.sport.entity.*;
import com.kok.sport.integration.SyncBasketballMatchAnalysisService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service("syncBasketballMatchAnalysisService")
public class SyncBasketballMatchAnalysisServiceImpl implements SyncBasketballMatchAnalysisService {

    @Autowired
    private BasketballMatchService basketballMatchService;
    @Autowired
    private BasketballScoreService basketballScoreService;
    @Autowired
    private BasketballOddsService basketballOddsService;
    @Autowired
    private BasketballTeamService basketballTeamService;
    @Autowired
    private BasketballEventService basketballEventService;


    /**
     * 篮球分析数据
     */
    @Override
    public boolean matchAnalysis(String matchId) throws ApplicationException {
        Map<String, String> map = new HashMap<String, String>() {{
            put("service", "Basketball.Analysis.Match_analysis");
            put("id", matchId);
        }};
        //获取篮球分析数据返回字符串结果
        String matchMap = HttpUtil.sendGet(map);
        if (matchMap != null && matchMap.length() > 0) {
            JSONObject jsonObject = JSON.parseObject(matchMap);
            //获取篮球比赛详情中data数据
            String data = jsonObject.getString("data");
            if (data != null && !"{}".equals(data)) {
                JSONObject jsonObjectData = JSON.parseObject(data);

                //获取比赛信息字段info
                String info = jsonObjectData.getString("info");
                //获取球队列表teams
                String teams = jsonObjectData.getString("teams");
                //获取赛事列表matchevents
                String matchevents = jsonObjectData.getString("matchevents");
                //获取历史交锋字段history
                String history = jsonObjectData.getString("history");
                //获取进球分布字段goal_distribution
                String goal_distribution = jsonObjectData.getString("goal_distribution");
                //获取伤停情况字段injury
                String injury = jsonObjectData.getString("injury");
                //获取联赛积分字段table
                String table = jsonObjectData.getString("table");

                if (info != null && info.length() > 0) {
                    JSONArray arrayInfo = JSON.parseArray(info);
                    if(arrayInfo.size() > 0){
                        analysisInfo(arrayInfo,matchId);
                    }
                }

                if (teams != null && teams.length() > 0) {
                    if (teams != null && teams.length() > 0) {
                        JSONObject teamsObject = JSON.parseObject(teams);
                        if(teamsObject.size() > 0){
                            for (Map.Entry entry : teamsObject.entrySet()) {
                                BasketballTeam basketballTeam = new BasketballTeam();
                                basketballTeam.setCreateTime(LocalDateTime.now());
                                basketballTeam.setDeleteFlag("0");
                                JSONObject teamsEach = JSON.parseObject(entry.getValue().toString());
                                if(teamsEach.size() > 0){
                                    for (Map.Entry entryEach : teamsEach.entrySet()) {
                                        if ("id".equals(entryEach.getKey())) {
                                            basketballTeam.setId(Long.parseLong(entryEach.getValue().toString()));
                                        }
                                        if ("name_zh".equals(entryEach.getKey())) {
                                            basketballTeam.setNameZh(entryEach.getValue().toString());
                                        }
                                        if ("logo".equals(entryEach.getKey())) {
                                            basketballTeam.setLogo(entryEach.getValue().toString());
                                        }
                                    }
                                }
                                BasketballTeam basketballTeamNew = basketballTeamService.getById(basketballTeam.getId());
                                if (basketballTeamNew != null){
                                    basketballTeamNew.setNameZh(basketballTeam.getNameZh());
                                    basketballTeamNew.setLogo(basketballTeam.getLogo());
                                    basketballTeamService.updateById(basketballTeamNew);
                                }else{
                                    basketballTeamService.save(basketballTeam);
                                }

                            }
                        }

                    }
                }
                if (matchevents != null && matchevents.length() > 0) {
                    if (matchevents != null && matchevents.length() > 0) {
                        JSONObject matcheventsObject = JSON.parseObject(matchevents);
                        if(matcheventsObject.size() > 0){
                            for (Map.Entry entry : matcheventsObject.entrySet()) {
                                BasketballEvent basketballEvent = new BasketballEvent();
                                basketballEvent.setCreateTime(LocalDateTime.now());
                                basketballEvent.setDeleteFlag("0");
                                JSONObject matcheventsEach = JSON.parseObject(entry.getValue().toString());
                                if(matcheventsEach.size() > 0){
                                    for (Map.Entry entryEach : matcheventsEach.entrySet()) {
                                        if ("id".equals(entryEach.getKey())) {
                                            basketballEvent.setId(Long.parseLong(entryEach.getValue().toString()));
                                        }
                                        if ("name_zh".equals(entryEach.getKey())) {
                                            basketballEvent.setNameZh(entryEach.getValue().toString());
                                        }
                                        if ("logo".equals(entryEach.getKey())) {
                                            basketballEvent.setMatchLogo(entryEach.getValue().toString());
                                        }
                                    }
                                }
                                BasketballEvent basketballTeamNew = basketballEventService.getById(basketballEvent.getId());
                                if (basketballTeamNew != null){
                                    basketballTeamNew.setNameZh(basketballEvent.getNameZh());
                                    basketballTeamNew.setMatchLogo(basketballEvent.getMatchLogo());
                                    basketballEventService.updateById(basketballTeamNew);
                                }else{
                                    basketballEventService.save(basketballEvent);
                                }

                            }
                        }

                    }
                }
                if (history != null && history.length() > 0) {
                    JSONObject teamsObject = JSON.parseObject(history);
                    //获取主队历史数据
                    String home = teamsObject.getString("home");
                    //获取客队历史数据
                    String away = teamsObject.getString("away");
                    if (home != null && !"[]".equals(home)) {
                        JSONArray homeArray = JSON.parseArray(home);
                        if(homeArray.size() > 0){
                            for (int i = 0; i < homeArray.size(); i++) {
                                JSONArray arrayEach = new JSONArray();
                                arrayEach = JSON.parseArray(homeArray.get(i).toString());
                                if(arrayEach.size() > 0){
                                    analysisInfo(arrayEach,matchId);
                                }
                            }
                        }
                    }
                    if (away != null && !"[]".equals(away)) {
                        JSONArray awayArray = JSON.parseArray(away);
                        if(awayArray.size() > 0){
                            for (int i = 0; i < awayArray.size(); i++) {
                                JSONArray arrayEach = new JSONArray();
                                arrayEach = JSON.parseArray(awayArray.get(i).toString());
                                if(arrayEach.size() > 0){
                                    analysisInfo(arrayEach,matchId);
                                }
                            }
                        }
                    }
                }
                if (goal_distribution != null && goal_distribution.length() > 0) {

                }
                if (injury != null && injury.length() > 0) {
                    JSONObject injuryObject = JSON.parseObject(injury);
                    //获取主队伤停数据
                    String home = injuryObject.getString("home");
                    //获取客队伤停数据
                    String away = injuryObject.getString("away");
                    //System.out.println(home);
                    if (home != null && !"[]".equals(home)) {
                        JSONArray homeArray = JSON.parseArray(home);
                        if(homeArray.size() > 0){
                            for (int i = 0; i < homeArray.size(); i++) {
                                JSONArray arrayEach = new JSONArray();
                                arrayEach = JSON.parseArray(homeArray.get(i).toString());
                                if(arrayEach.size() > 0){
                                    //System.out.println(arrayEach.get(0));
                                }
                            }
                        }
                    }
                    if (away != null && !"[]".equals(away)) {
                        JSONArray awayArray = JSON.parseArray(away);
                        if(awayArray.size() > 0){
                            for (int i = 0; i < awayArray.size(); i++) {
                                JSONArray arrayEach = new JSONArray();
                                arrayEach = JSON.parseArray(awayArray.get(i).toString());
                                if(arrayEach.size() > 0){
                                    //System.out.println(arrayEach.get(0));
                                }
                            }
                        }
                    }
                }
                if (table != null && table.length() > 0) {

                }

            }
        }
        return false;
    }
    private void analysisInfo(JSONArray arrayEach,String matchId){
        //篮球比赛表
        BasketballMatch basketballMatch = new BasketballMatch();
        basketballMatch.setCreateTime(LocalDateTime.now());
        basketballMatch.setDeleteFlag("0");

        //比赛id
        if(arrayEach.get(0) != null && !"".equals(arrayEach.get(0))){
            basketballMatch.setId(Long.parseLong(arrayEach.get(0).toString()));
        }
        //比赛总节数
        if(arrayEach.get(1) != null && !"".equals(arrayEach.get(1))){
            basketballMatch.setTotalSections(Integer.parseInt(arrayEach.get(1).toString()));
        }
        //赛事id
        if(arrayEach.get(2) != null && !"".equals(arrayEach.get(2))){
            basketballMatch.setMatchEventId(Long.parseLong(arrayEach.get(2).toString()));
        }
        //比赛状态
        if(arrayEach.get(3) != null && !"".equals(arrayEach.get(3))){
            basketballMatch.setMatchStatus(Integer.parseInt(arrayEach.get(3).toString()));
        }
        //比赛时间
        if(arrayEach.get(4) != null && !"".equals(arrayEach.get(4))){
            basketballMatch.setMatchTime(Long.parseLong(arrayEach.get(4).toString()));
        }
        //当前总秒钟数，显示转换成 mm:ss
        if(arrayEach.get(5) != null && !"".equals(arrayEach.get(5))){
            basketballMatch.setCurrentTotalSeconds(Integer.parseInt(arrayEach.get(5).toString()));
        }
        //主队每节得分
        if(arrayEach.get(6) != null && !"".equals(arrayEach.get(6))){
            JSONArray hometeamInfo = JSON.parseArray(arrayEach.get(6).toString());
            if(hometeamInfo.size() > 0){
                //比赛得分表
                BasketballScore basketballScore = new BasketballScore();
                basketballScore.setMatchId(Long.parseLong(matchId));
                basketballScore.setTeamType(1);
                basketballScore.setCreateTime(LocalDateTime.now());
                basketballScore.setDeleteFlag("0");
                //主球队id
                if(hometeamInfo.get(0) != null && !"".equals(hometeamInfo.get(0))){
                    basketballScore.setTeamId(Long.parseLong(hometeamInfo.get(0).toString()));
                }
                //排名，暂时为空
                if(hometeamInfo.get(1) != null && !"".equals(hometeamInfo.get(1))){
                    hometeamInfo.get(1);
                }
                //第1节分数
                if(hometeamInfo.get(2) != null && !"".equals(hometeamInfo.get(2))){
                    basketballScore.setFirstSectionScores(Integer.parseInt(hometeamInfo.get(2).toString()));
                }
                //第2节分数
                if(hometeamInfo.get(3) != null && !"".equals(hometeamInfo.get(3))){
                    basketballScore.setSecondSectionScores(Integer.parseInt(hometeamInfo.get(3).toString()));
                }
                //第3节分数
                if(hometeamInfo.get(4) != null && !"".equals(hometeamInfo.get(4))){
                    basketballScore.setThirdSectionScores(Integer.parseInt(hometeamInfo.get(4).toString()));
                }
                //第4节分数
                if(hometeamInfo.get(5) != null && !"".equals(hometeamInfo.get(5))){
                    basketballScore.setFourthSectionScores(Integer.parseInt(hometeamInfo.get(5).toString()));
                }
                //加时分数
                if(hometeamInfo.get(6) != null && !"".equals(hometeamInfo.get(6))){
                    basketballScore.setOvertimeScores(Integer.parseInt(hometeamInfo.get(6).toString()));
                }
                List<BasketballScore> listScore = basketballScoreService.getByMatcheId(Long.parseLong(matchId),basketballScore.getTeamType());
                if (listScore.size() > 0){
                    basketballScore.setId(listScore.get(0).getId());
                    basketballScoreService.updateById(basketballScore);
                }else{
                    basketballScoreService.save(basketballScore);
                }
            }
        }

        //客队每节得分
        if(arrayEach.get(7) != null && !"".equals(arrayEach.get(7))){
            JSONArray awayteamInfo = JSON.parseArray(arrayEach.get(7).toString());
            if(awayteamInfo.size() > 0){
                //比赛得分表
                BasketballScore basketballScore = new BasketballScore();
                basketballScore.setMatchId(Long.parseLong(matchId));
                basketballScore.setTeamType(2);
                basketballScore.setCreateTime(LocalDateTime.now());
                basketballScore.setDeleteFlag("0");
                //客球队id
                if(awayteamInfo.get(0) != null && !"".equals(awayteamInfo.get(0))){
                    basketballScore.setTeamId(Long.parseLong(awayteamInfo.get(0).toString()));
                }
                //排名，暂时为空
                if(awayteamInfo.get(1) != null && !"".equals(awayteamInfo.get(1))){
                    awayteamInfo.get(1);
                }
                //第1节分数
                if(awayteamInfo.get(2) != null && !"".equals(awayteamInfo.get(2))){
                    basketballScore.setFirstSectionScores(Integer.parseInt(awayteamInfo.get(2).toString()));
                }
                //第2节分数
                if(awayteamInfo.get(3) != null && !"".equals(awayteamInfo.get(3))){
                    basketballScore.setSecondSectionScores(Integer.parseInt(awayteamInfo.get(3).toString()));
                }
                //第3节分数
                if(awayteamInfo.get(4) != null && !"".equals(awayteamInfo.get(4))){
                    basketballScore.setThirdSectionScores(Integer.parseInt(awayteamInfo.get(4).toString()));
                }
                //第4节分数
                if(awayteamInfo.get(5) != null && !"".equals(awayteamInfo.get(5))){
                    basketballScore.setFourthSectionScores(Integer.parseInt(awayteamInfo.get(5).toString()));
                }
                //加时分数
                if(awayteamInfo.get(6) != null && !"".equals(awayteamInfo.get(6))){
                    basketballScore.setOvertimeScores(Integer.parseInt(awayteamInfo.get(6).toString()));
                }
                List<BasketballScore> listScore = basketballScoreService.getByMatcheId(Long.parseLong(matchId),basketballScore.getTeamType());
                if (listScore.size() > 0){
                    basketballScore.setId(listScore.get(0).getId());
                    basketballScoreService.updateById(basketballScore);
                }else{
                    basketballScoreService.save(basketballScore);
                }
            }
        }
        //盘口信息
        if(arrayEach.get(8) != null && !"".equals(arrayEach.get(8))){
            JSONArray awayteamOdds = JSON.parseArray(arrayEach.get(8).toString());
            if(awayteamOdds.size() > 0){
                //亚盘 主,盘口,客,是否封盘
                if(awayteamOdds.get(0) != null && !"".equals(awayteamOdds.get(0))){
                    BasketballOdds basketballOdds = new BasketballOdds();
                    basketballOdds.setMatcheId(Long.parseLong(matchId));
                    basketballOdds.setOddsType(1);
                    basketballOdds.setCreateTime(LocalDateTime.now());
                    basketballOdds.setDeleteFlag("0");
                    List<String> list = Arrays.asList(awayteamOdds.get(0).toString().split(","));
                    if(list.size() > 0 && list.size() == 4){
                        for(int i = 0 ; i < list.size() ; i++) {
                            if(i == 0){
                                basketballOdds.setHomeOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 1){
                                basketballOdds.setTieOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 2){
                                basketballOdds.setAwayOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 3){
                                basketballOdds.setLockFlag(Integer.parseInt(list.get(i)));
                            }
                        }
                        List<BasketballOdds> listOdds = basketballOddsService.getByMatcheId(Long.parseLong(matchId),basketballOdds.getCompanyId(),basketballOdds.getOddsType());
                        if (listOdds.size() > 0){
                            basketballOdds.setId(listOdds.get(0).getId());
                            basketballOdds.setCompanyId(listOdds.get(0).getCompanyId());
                            basketballOddsService.updateById(basketballOdds);
                        }else{
                            basketballOddsService.save(basketballOdds);
                        }
                    }
                }
                //欧盘 胜,平,负,是否封盘
                if(awayteamOdds.get(1) != null && !"".equals(awayteamOdds.get(1))){
                    BasketballOdds basketballOdds = new BasketballOdds();
                    basketballOdds.setMatcheId(Long.parseLong(matchId));
                    basketballOdds.setOddsType(3);
                    basketballOdds.setCreateTime(LocalDateTime.now());
                    basketballOdds.setDeleteFlag("0");
                    List<String> list = Arrays.asList(awayteamOdds.get(1).toString().split(","));
                    if(list.size() > 0 && list.size() == 4){
                        for(int i = 0 ; i < list.size() ; i++) {
                            if(i == 0){
                                basketballOdds.setHomeOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 1){
                                basketballOdds.setTieOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 2){
                                basketballOdds.setAwayOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 3){
                                basketballOdds.setLockFlag(Integer.parseInt(list.get(i)));
                            }
                        }
                        List<BasketballOdds> listOdds = basketballOddsService.getByMatcheId(Long.parseLong(matchId),basketballOdds.getCompanyId(),basketballOdds.getOddsType());
                        if (listOdds.size() > 0){
                            basketballOdds.setId(listOdds.get(0).getId());
                            basketballOdds.setCompanyId(listOdds.get(0).getCompanyId());
                            basketballOddsService.updateById(basketballOdds);
                        }else{
                            basketballOddsService.save(basketballOdds);
                        }
                    }
                }
                //大小盘 大,盘口,小,是否封盘
                if(awayteamOdds.get(2) != null && !"".equals(awayteamOdds.get(2))){
                    BasketballOdds basketballOdds = new BasketballOdds();
                    basketballOdds.setMatcheId(Long.parseLong(matchId));
                    basketballOdds.setOddsType(2);
                    basketballOdds.setCreateTime(LocalDateTime.now());
                    basketballOdds.setDeleteFlag("0");
                    List<String> list = Arrays.asList(awayteamOdds.get(2).toString().split(","));
                    if(list.size() > 0 && list.size() == 4){
                        for(int i = 0 ; i < list.size() ; i++) {
                            if(i == 0){
                                basketballOdds.setHomeOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 1){
                                basketballOdds.setTieOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 2){
                                basketballOdds.setAwayOdds(new BigDecimal(list.get(i)));
                            }
                            if(i == 3){
                                basketballOdds.setLockFlag(Integer.parseInt(list.get(i)));
                            }
                        }
                        List<BasketballOdds> listOdds = basketballOddsService.getByMatcheId(Long.parseLong(matchId),basketballOdds.getCompanyId(),basketballOdds.getOddsType());
                        if (listOdds.size() > 0){
                            basketballOdds.setId(listOdds.get(0).getId());
                            basketballOdds.setCompanyId(listOdds.get(0).getCompanyId());
                            basketballOddsService.updateById(basketballOdds);
                        }else{
                            basketballOddsService.save(basketballOdds);
                        }
                    }
                }
            }
        }

        //比赛详细说明
        if(arrayEach.get(9) != null && !"".equals(arrayEach.get(9))){
            JSONArray detailInfo = JSON.parseArray(arrayEach.get(9).toString());
            //比赛详细说明
            if(detailInfo.get(0) != null && !"".equals(detailInfo.get(0))){
                detailInfo.get(0);
            }
            ////竞彩信息,场次
            if(detailInfo.get(1) != null && !"".equals(detailInfo.get(1))){
                basketballMatch.setMatchDetail(detailInfo.get(1).toString());
            }
        }
        BasketballMatch basketballMatchNew = basketballMatchService.getById(Long.parseLong(matchId));
        if (basketballMatchNew!= null){
            basketballMatchNew.setTotalSections(basketballMatch.getTotalSections());
            basketballMatchNew.setMatchEventId(basketballMatch.getMatchEventId());
            basketballMatchNew.setMatchStatus(basketballMatch.getMatchStatus());
            basketballMatchNew.setMatchTime(basketballMatch.getMatchTime());
            basketballMatchNew.setCompatible(basketballMatch.getCompatible());
            basketballMatchNew.setMatchDetail(basketballMatch.getMatchDetail());
            basketballMatchNew.setCurrentTotalSeconds(basketballMatch.getCurrentTotalSeconds());
            basketballMatchNew.setDeleteFlag("0");
            basketballMatchService.updateById(basketballMatchNew);
        }else{
            basketballMatchService.save(basketballMatch);
        }
    }

}
