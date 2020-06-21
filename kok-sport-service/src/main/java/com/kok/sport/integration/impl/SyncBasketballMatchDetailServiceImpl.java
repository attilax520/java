package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.base.exception.ApplicationException;
import com.kok.sport.entity.*;
import com.kok.sport.integration.SyncBasketballMatchDetailService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service("syncBasketballMatchDetailService")
public class SyncBasketballMatchDetailServiceImpl implements SyncBasketballMatchDetailService {

    @Autowired
    private BasketballMatchService basketballMatchService;
    @Autowired
    private BasketballScoreService basketballScoreService;
    @Autowired
    private BasketballStatsService basketballStatsService;
    @Autowired
    private BasketballPlayerService basketballPlayerService;
    @Autowired
    private BasketballTliveService basketballTliveService;
    @Autowired
    private BasketballEventService basketballEventService;
    @Autowired
    private BasketballTeamService basketballTeamService;


    /**
     * 篮球比赛详情---返回具体一场比赛的比赛环境、文字直播、技术统计信息；
     */
    @Override
    public boolean matchDetail(String matchId) throws ApplicationException {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Basketball.Analysis.Match_detail");
            put("id",matchId);
        }};
        //获取篮球比赛详情返回字符串结果
        String matchMap = HttpUtil.sendGet(map);
        if(matchMap != null && matchMap.length() > 0){
            JSONObject jsonObject = JSON.parseObject(matchMap);
            //获取篮球比赛详情中data数据
            String data = jsonObject.getString("data");
            if (data != null && !"{}".equals(data)) {
                JSONObject jsonObjectData = JSON.parseObject(data);

                //获取比赛信息字段info
                String info = jsonObjectData.getString("info");
                //获取联赛字段event
                String event = jsonObjectData.getString("event");
                //获取主队字段hometeam
                String hometeam = jsonObjectData.getString("hometeam");
                //获取客队字段awayteam
                String awayteam = jsonObjectData.getString("awayteam");

                //调的时候没有下面字段，但是接口返回实例中有。。。。。。

                //获取技术统计字段stats
                String stats = jsonObjectData.getString("stats");
                //获取比赛阵容字段players
                String players = jsonObjectData.getString("players");
                //获取文字直播字段tlive
                String tlive = jsonObjectData.getString("tlive");
                //篮球比赛表
                BasketballMatch basketballMatch = new BasketballMatch();
                basketballMatch.setCreateTime(LocalDateTime.now());
                basketballMatch.setDeleteFlag("0");
                if(info != null && info.length() > 0){
                    JSONArray arrayInfo = JSON.parseArray(info);
                    if(arrayInfo.size() > 0){
                        //比赛id
                        if(arrayInfo.get(0) != null && !"".equals(arrayInfo.get(0))){
                            basketballMatch.setId(Long.parseLong(arrayInfo.get(0).toString()));
                        }
                        //比赛总节数
                        if(arrayInfo.get(1) != null && !"".equals(arrayInfo.get(1))){
                            basketballMatch.setTotalSections(Integer.parseInt(arrayInfo.get(1).toString()));
                        }
                        //赛事id
                        if(arrayInfo.get(2) != null && !"".equals(arrayInfo.get(2))){
                            basketballMatch.setMatchEventId(Long.parseLong(arrayInfo.get(2).toString()));
                        }
                        //比赛状态
                        if(arrayInfo.get(3) != null && !"".equals(arrayInfo.get(3))){
                            basketballMatch.setMatchStatus(Integer.parseInt(arrayInfo.get(3).toString()));
                        }
                        //比赛时间
                        if(arrayInfo.get(4) != null && !"".equals(arrayInfo.get(4))){
                            basketballMatch.setMatchTime(Long.parseLong(arrayInfo.get(4).toString()));
                        }
                        //兼容，可忽略
                        if(arrayInfo.get(5) != null && !"".equals(arrayInfo.get(5))){
                            basketballMatch.setCompatible(arrayInfo.get(5).toString());
                        }
                        //主队每节得分
                        if(arrayInfo.get(6) != null && !"".equals(arrayInfo.get(6))){
                            JSONArray hometeamInfo = JSON.parseArray(arrayInfo.get(6).toString());
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
                        if(arrayInfo.get(7) != null && !"".equals(arrayInfo.get(7))){
                            JSONArray awayteamInfo = JSON.parseArray(arrayInfo.get(7).toString());
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

                        //比赛详细说明
                        if(arrayInfo.get(8) != null && !"".equals(arrayInfo.get(8))){
                            JSONArray detailInfo = JSON.parseArray(arrayInfo.get(8).toString());
                            //比赛详细说明
                            if(detailInfo.get(0) != null && !"".equals(detailInfo.get(0))){
                                detailInfo.get(0);
                            }
                            ////竞彩信息,场次
                            if(detailInfo.get(1) != null && !"".equals(detailInfo.get(1))){
                                basketballMatch.setMatchDetail(detailInfo.get(1).toString());
                            }

                        }
                    }
                }

                if(event != null && event.length() > 0){
                    JSONObject eventObject = JSON.parseObject(event);
                    if(eventObject.size() > 0){
                        //篮球赛事表
                        BasketballEvent basketballEvent = new BasketballEvent();
                        basketballEvent.setCreateTime(LocalDateTime.now());
                        basketballEvent.setDeleteFlag("0");
                        for (Map.Entry entry : eventObject.entrySet()) {
                            if ("id".equals(entry.getKey())) {
                                basketballEvent.setId(Long.parseLong(entry.getValue().toString()));
                            }
                            if ("logo".equals(entry.getKey())) {
                                basketballEvent.setMatchLogo(entry.getValue().toString());
                            }
                            if ("name_zh".equals(entry.getKey())) {
                                basketballEvent.setNameZh(entry.getValue().toString());
                            }
                            if ("name_zht".equals(entry.getKey())) {
                                basketballEvent.setNameZht(entry.getValue().toString());
                            }
                            if ("name_en".equals(entry.getKey())) {
                                basketballEvent.setNameEn(entry.getValue().toString());
                            }
                            if ("short_name_zh".equals(entry.getKey())) {
                                basketballEvent.setShortNameZh(entry.getValue().toString());
                            }
                            if ("short_name_zht".equals(entry.getKey())) {
                                basketballEvent.setShortNameZht(entry.getValue().toString());
                            }
                            if ("short_name_en".equals(entry.getKey())) {
                                basketballEvent.setShortNameEn(entry.getValue().toString());
                            }
                        }
                        if (basketballEventService.getById(basketballEvent.getId()) != null){
                            basketballEventService.updateById(basketballEvent);
                        }else{
                            basketballEventService.save(basketballEvent);
                        }
                    }
                }

                if(hometeam != null && hometeam.length() > 0){
                    JSONObject hometeamObject = JSON.parseObject(hometeam);
                    if(hometeamObject.size() > 0){
                        //球队表
                        BasketballTeam basketballTeam = new BasketballTeam();
                        basketballTeam.setCreateTime(LocalDateTime.now());
                        basketballTeam.setDeleteFlag("0");
                        for (Map.Entry entry : hometeamObject.entrySet()) {
                            if ("id".equals(entry.getKey())) {
                                basketballTeam.setId(Long.parseLong(entry.getValue().toString()));
                            }
                            if ("logo".equals(entry.getKey())) {
                                basketballTeam.setLogo(entry.getValue().toString());
                            }
                            if ("name_zh".equals(entry.getKey())) {
                                basketballTeam.setNameZh(entry.getValue().toString());
                            }
                            if ("name_zht".equals(entry.getKey())) {
                                basketballTeam.setNameZht(entry.getValue().toString());
                            }
                            if ("name_en".equals(entry.getKey())) {
                                basketballTeam.setNameEn(entry.getValue().toString());
                            }
                            if ("short_name_zh".equals(entry.getKey())) {
                                basketballTeam.setShortNameZh(entry.getValue().toString());
                            }
                            if ("short_name_zht".equals(entry.getKey())) {
                                basketballTeam.setShortNameZht(entry.getValue().toString());
                            }
                            if ("short_name_en".equals(entry.getKey())) {
                                basketballTeam.setShortNameEn(entry.getValue().toString());
                            }
                        }
                        if (basketballTeamService.getById(basketballTeam.getId()) != null){
                            basketballTeamService.updateById(basketballTeam);
                        }else{
                            basketballTeamService.save(basketballTeam);
                        }
                    }
                }

                if(awayteam != null && awayteam.length() > 0){
                    JSONObject awayteamObject = JSON.parseObject(awayteam);
                    if (awayteamObject.size() > 0) {
                        //球队表
                        BasketballTeam basketballTeam = new BasketballTeam();
                        basketballTeam.setCreateTime(LocalDateTime.now());
                        basketballTeam.setDeleteFlag("0");
                        for (Map.Entry entry : awayteamObject.entrySet()) {
                            if ("id".equals(entry.getKey())) {
                                basketballTeam.setId(Long.parseLong(entry.getValue().toString()));
                            }
                            if ("logo".equals(entry.getKey())) {
                                basketballTeam.setLogo(entry.getValue().toString());
                            }
                            if ("name_zh".equals(entry.getKey())) {
                                basketballTeam.setNameZh(entry.getValue().toString());
                            }
                            if ("name_zht".equals(entry.getKey())) {
                                basketballTeam.setNameZht(entry.getValue().toString());
                            }
                            if ("name_en".equals(entry.getKey())) {
                                basketballTeam.setNameEn(entry.getValue().toString());
                            }
                            if ("short_name_zh".equals(entry.getKey())) {
                                basketballTeam.setShortNameZh(entry.getValue().toString());
                            }
                            if ("short_name_zht".equals(entry.getKey())) {
                                basketballTeam.setShortNameZht(entry.getValue().toString());
                            }
                            if ("short_name_en".equals(entry.getKey())) {
                                basketballTeam.setShortNameEn(entry.getValue().toString());
                            }
                        }
                        if (basketballTeamService.getById(basketballTeam.getId()) != null){
                            basketballTeamService.updateById(basketballTeam);
                        }else{
                            basketballTeamService.save(basketballTeam);
                        }
                    }
                }

                if(stats != null && stats.length() > 0){
                    JSONArray array = JSON.parseArray(stats);
                    if(array.size() > 0){
                        for(int i=0;i<array.size();i++) {
                            JSONArray arrayEach = new JSONArray();
                            arrayEach = JSON.parseArray(array.get(i).toString());
                            if(arrayEach.size() > 0){
                                //比赛统计表
                                BasketballStats basketballStats = new BasketballStats();
                                basketballStats.setMatchId(Long.parseLong(matchId));
                                basketballStats.setCreateTime(LocalDateTime.now());
                                basketballStats.setDeleteFlag("0");
                                if(arrayEach.get(0) != null && !"".equals(arrayEach.get(0))){
                                    //统计类型，请参考 状态码->篮球技术统计
                                    basketballStats.setStatsType(Integer.parseInt(arrayEach.get(0).toString()));
                                }
                                if(arrayEach.get(1) != null && !"".equals(arrayEach.get(1))){
                                    //主队数值
                                    basketballStats.setHomeData(Integer.parseInt(arrayEach.get(1).toString()));
                                }
                                if(arrayEach.get(2) != null && !"".equals(arrayEach.get(2))){
                                    //客队数值
                                    basketballStats.setAwayData(Integer.parseInt(arrayEach.get(2).toString()));
                                }
                                List<BasketballStats> listStats = basketballStatsService.getByMatcheId(Long.parseLong(matchId),basketballStats.getStatsType());
                                if (listStats.size() > 0){
                                    basketballStats.setId(listStats.get(0).getId());
                                    basketballStatsService.updateById(basketballStats);
                                }else{
                                    basketballStatsService.save(basketballStats);
                                }
                            }
                        }
                    }
                }

                /*比赛阵容和球员数据*/
                if (players != null && players.length() > 0) {
                    JSONArray arrayPlayers = JSON.parseArray(players);
                    //主队
                    if(arrayPlayers.get(0) != null && !"".equals(arrayPlayers.get(0))){
                        JSONArray teamPlayers = JSON.parseArray(arrayPlayers.get(0).toString());
                        if(teamPlayers.size() > 0){
                            for(int i = 0; i< teamPlayers.size(); i++) {
                                JSONArray arrayPlayersEach = new JSONArray();
                                arrayPlayersEach = JSON.parseArray(teamPlayers.get(i).toString());
                                if(arrayPlayersEach.size() > 0){
                                    //篮球比赛阵容表
                                    BasketballPlayer basketballPlayer = new BasketballPlayer();
                                    basketballPlayer.setMatchId(Long.parseLong(matchId));
                                    basketballPlayer.setTeamType(1);
                                    basketballPlayer.setCreateTime(LocalDateTime.now());
                                    basketballPlayer.setDeleteFlag("0");
                                    //球员表
                                    //暂时不写入球员表
                                    //BasketballTeamPlayer teamPlayer = new BasketballTeamPlayer();
                                    //球员id
                                    if(arrayPlayersEach.get(0) != null && !"".equals(arrayPlayersEach.get(0))){
                                        basketballPlayer.setPlayerId(Long.parseLong(arrayPlayersEach.get(0).toString()));
                                        //teamPlayer.setId(Long.parseLong(arrayPlayersEach.get(0).toString()));
                                    }
                                    //球员中文名称
                                    if(arrayPlayersEach.get(1) != null && !"".equals(arrayPlayersEach.get(1))){
                                        basketballPlayer.setPlayerNameZh(arrayPlayersEach.get(1).toString());
                                    }
                                    //球员粤语名称
                                    if(arrayPlayersEach.get(2) != null && !"".equals(arrayPlayersEach.get(2))){
                                        basketballPlayer.setPlayerNameZht(arrayPlayersEach.get(2).toString());
                                    }
                                    //球员英文名称
                                    if(arrayPlayersEach.get(3) != null && !"".equals(arrayPlayersEach.get(3))){
                                        basketballPlayer.setPlayerNameEn(arrayPlayersEach.get(3).toString());
                                    }
                                    //logo 前缀：https://cdn.sportnanoapi.com/basketball/player/
                                    if(arrayPlayersEach.get(4) != null && !"".equals(arrayPlayersEach.get(4))){
                                        // teamPlayer.setLogo(arrayPlayersEach.get(4).toString());
                                    }
                                    //球衣号
                                    if(arrayPlayersEach.get(5) != null && !"".equals(arrayPlayersEach.get(5))){
                                        basketballPlayer.setPlayerNo(arrayPlayersEach.get(5).toString());

                                    }
                                    //在场持续时间^命中次数-投篮次数^三分球投篮命中次数-三分投篮次数^罚球命中次数-罚球投篮次数^进攻篮板^防守篮板^总的篮板^助攻数^抢断数^盖帽数^失误次数^个人犯规次数^+/-值^得分^是否出场(1-出场，0-没出场)^是否在场上（0-在场上，1-没在场上）^是否是替补（1-替补，0-首发）
                                    if(arrayPlayersEach.get(6) != null && !"".equals(arrayPlayersEach.get(6))){
                                        basketballPlayer.setPlayerData(arrayPlayersEach.get(6).toString());
                                    }
                                    List<BasketballPlayer> listPlayer = basketballPlayerService.getByMatcheId(Long.parseLong(matchId),basketballPlayer.getTeamType(),basketballPlayer.getPlayerId());
                                    if (listPlayer.size() > 0){
                                        basketballPlayer.setId(listPlayer.get(0).getId());
                                        basketballPlayerService.updateById(basketballPlayer);
                                    }else{
                                        basketballPlayerService.save(basketballPlayer);
                                    }

                                    /*if (teamPlayerService.getById(teamPlayer.getId()) != null){
                                        teamPlayerService.updateById(teamPlayer);
                                    }else{
                                        teamPlayerService.save(teamPlayer);
                                    }*/
                                }
                            }
                        }
                    }

                    //客队
                    if(arrayPlayers.get(1) != null && !"".equals(arrayPlayers.get(1))){
                        JSONArray awayteamPlayers = JSON.parseArray(arrayPlayers.get(1).toString());
                        if(awayteamPlayers.size() > 0){
                            for(int i=0;i<awayteamPlayers.size();i++) {
                                JSONArray arrayPlayersEach = new JSONArray();
                                arrayPlayersEach = JSON.parseArray(awayteamPlayers.get(i).toString());
                                if(arrayPlayersEach.size() > 0){
                                    //比赛球员数据表
                                    BasketballPlayer basketballPlayer = new BasketballPlayer();
                                    basketballPlayer.setMatchId(Long.parseLong(matchId));
                                    basketballPlayer.setTeamType(2);
                                    basketballPlayer.setCreateTime(LocalDateTime.now());
                                    basketballPlayer.setDeleteFlag("0");
                                    //球员表
                                    //暂时不写入球员表
                                    // BasketballTeamPlayer basketTeamPlayer = new BasketballTeamPlayer();
                                    //球员id
                                    if(arrayPlayersEach.get(0) != null && !"".equals(arrayPlayersEach.get(0))){
                                        basketballPlayer.setPlayerId(Long.parseLong(arrayPlayersEach.get(0).toString()));
                                        //basketTeamPlayer.setId(Long.parseLong(arrayPlayersEach.get(0).toString()));
                                    }
                                    //球员中文名称
                                    if(arrayPlayersEach.get(1) != null && !"".equals(arrayPlayersEach.get(1))){
                                        basketballPlayer.setPlayerNameZh(arrayPlayersEach.get(1).toString());
                                    }
                                    //球员粤语名称
                                    if(arrayPlayersEach.get(2) != null && !"".equals(arrayPlayersEach.get(2))){
                                        basketballPlayer.setPlayerNameZht(arrayPlayersEach.get(2).toString());
                                    }
                                    //球员英文名称
                                    if(arrayPlayersEach.get(3) != null && !"".equals(arrayPlayersEach.get(3))){
                                        basketballPlayer.setPlayerNameEn(arrayPlayersEach.get(3).toString());
                                    }
                                    //logo 前缀：https://cdn.sportnanoapi.com/basketball/player/
                                    if(arrayPlayersEach.get(4) != null && !"".equals(arrayPlayersEach.get(4))){
                                        //basketTeamPlayer.setLogo(arrayPlayersEach.get(4).toString());
                                    }
                                    //球衣号
                                    if(arrayPlayersEach.get(5) != null && !"".equals(arrayPlayersEach.get(5))){
                                        basketballPlayer.setPlayerNo(arrayPlayersEach.get(5).toString());
                                    }
                                    //在场持续时间^命中次数-投篮次数^三分球投篮命中次数-三分投篮次数^罚球命中次数-罚球投篮次数^进攻篮板^防守篮板^总的篮板^助攻数^抢断数^盖帽数^失误次数^个人犯规次数^+/-值^得分^是否出场(1-出场，0-没出场)^是否在场上（0-在场上，1-没在场上）^是否是替补（1-替补，0-首发）
                                    if(arrayPlayersEach.get(6) != null && !"".equals(arrayPlayersEach.get(6))){
                                        basketballPlayer.setPlayerData(arrayPlayersEach.get(6).toString());
                                    }
                                    List<BasketballPlayer> listPlayer = basketballPlayerService.getByMatcheId(Long.parseLong(matchId),basketballPlayer.getTeamType(),basketballPlayer.getPlayerId());
                                    if (listPlayer.size() > 0){
                                        basketballPlayer.setId(listPlayer.get(0).getId());
                                        basketballPlayerService.updateById(basketballPlayer);
                                    }else{
                                        basketballPlayerService.save(basketballPlayer);
                                    }
                                    /*if (teamPlayerService.getById(basketTeamPlayer.getId()) != null){
                                        teamPlayerService.updateById(basketTeamPlayer);
                                    }else{
                                        teamPlayerService.save(basketTeamPlayer);
                                    }*/
                                }
                            }
                        }
                    }
                    //球队数据，主队
                    if(arrayPlayers.get(2) != null && !"".equals(arrayPlayers.get(2))){
                        basketballMatch.setHomeData(arrayPlayers.get(2).toString());
                    }
                    //球队数据，客队   0(兼容)^命中次数-投篮次数^三分球投篮命中次数-三分投篮次数^罚球命中次数-罚球投篮次数^进攻篮板^防守篮板^总的篮板^助攻数^抢断数^盖帽数^失误次数^个人犯规次数^0(兼容)^得分
                    if(arrayPlayers.get(3) != null && !"".equals(arrayPlayers.get(3))){
                        basketballMatch.setAwayData(arrayPlayers.get(3).toString());
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
                    basketballMatchNew.setHomeData(basketballMatch.getHomeData());
                    basketballMatchNew.setAwayData(basketballMatch.getAwayData());
                    basketballMatchNew.setDeleteFlag("0");
                    basketballMatchService.updateById(basketballMatchNew);
                }else{
                    basketballMatchService.save(basketballMatch);
                }
                String time1 = "";
                String time2 = "";
                String time3 = "";
                if(tlive != null && tlive.length() > 0){
                    //tlive字段为列表，表示不同节数的数据
                    JSONArray arrayTlive = JSON.parseArray(tlive);
                    if(arrayTlive.size() > 0){
                        for(int i=0;i<arrayTlive.size();i++) {
                            JSONArray arrayClassEach = new JSONArray();
                            arrayClassEach = JSON.parseArray(arrayClassEach.get(i).toString());
                            if(arrayClassEach.size() > 0){
                                JSONArray arrayClassEachDetail = JSON.parseArray(arrayClassEach.get(i).toString());
                                if(arrayClassEachDetail.size() > 0){
                                    for(int j=0;j<arrayClassEachDetail.size();j++) {
                                        //篮球文字直播表
                                        BasketballTlive basketballTlive = new BasketballTlive();
                                        basketballTlive.setMatchId(Long.parseLong(matchId));
                                        basketballTlive.setSection(i);
                                        basketballTlive.setCreateTime(LocalDateTime.now());
                                        basketballTlive.setDeleteFlag("0");
                                        //_^时间^主客队（0-中立，1-主队，2-客队）^0^客队比分-主队比分^说明^
                                        basketballTlive.setData(arrayClassEachDetail.get(j).toString());
                                        //第一、二位分钟数
                                        time1 = arrayClassEachDetail.get(j).toString().substring(2,4);
                                        //第三、四位分钟数
                                        time2 = arrayClassEachDetail.get(j).toString().substring(5,7);
                                        time3 = time1 + time2;
                                        basketballTlive.setTime(Long.parseLong(time3));
                                        basketballTliveService.save(basketballTlive);
                                    }
                                }
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
