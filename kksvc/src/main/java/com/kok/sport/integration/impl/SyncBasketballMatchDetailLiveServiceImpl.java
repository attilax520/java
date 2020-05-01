package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.base.exception.ApplicationException;
import com.kok.sport.entity.*;
import com.kok.sport.integration.SyncBasketballMatchDetailLiveService;
import com.kok.sport.service.*;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("syncBasketballMatchDetailLiveService")
public class SyncBasketballMatchDetailLiveServiceImpl implements SyncBasketballMatchDetailLiveService {

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


    /**
     * 篮球实时统计---返回具体一场比赛的比赛环境、文字直播、技术统计信息；
     */
    @Override
    public boolean matchDetailLive() throws ApplicationException {
        Map<String,String> map = new HashMap<String, String>(){{
            put("service","Basketball.Live.Match_detail_live");
        }};
        //获取篮球实时统计返回字符串结果
        String matchMap = HttpUtil.sendGet(map);
        if(matchMap != null && matchMap.length() > 0){
            JSONObject jsonObject = JSON.parseObject(matchMap);
            //获取篮球实时统计中data数据
            String data = jsonObject.getString("data");
            if (data != null && !"{}".equals(data)) {
                JSONArray arrayData = JSON.parseArray(data);
                if(arrayData.size() > 0){
                    for(int i=0;i<arrayData.size();i++) {
                        JSONObject objectEach = JSON.parseObject(arrayData.get(i).toString());
                        if (objectEach.size() > 0) {
                            //获取纳米比赛id
                            String id = "";
                            for (Map.Entry entry : objectEach.entrySet()) {
                                if ("id".equals(entry.getKey())) {
                                    id = entry.getValue().toString();
                                }
                            }
                            for (Map.Entry entry : objectEach.entrySet()) {
                                if ("players".equals(entry.getKey())) {
                                    //获取比赛阵容字段players
                                    String players = entry.getValue().toString();
                                    playersLive(players,id);
                                }
                                if ("score".equals(entry.getKey())) {
                                    //获取即时比分字段score
                                    String score =entry.getValue().toString();
                                    scoreLive(score,id);
                                }
                                if ("stats".equals(entry.getKey())) {
                                    //获取技术统计字段stats
                                    String stats = entry.getValue().toString();
                                    statsLive(stats,id);
                                }
                                if ("incidents".equals(entry.getKey())) {
                                    //获取比赛事件字段incidents
                                    String incidents = entry.getValue().toString();
                                }
                                if ("tlive".equals(entry.getKey())) {
                                    //获取文字直播字段tlive
                                    String tlive = entry.getValue().toString();
                                    tlive(tlive,id);
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
    private void playersLive(String players,String id){
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
                            basketballPlayer.setMatchId(Long.parseLong(id));
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
                            List<BasketballPlayer> listPlayer = basketballPlayerService.getByMatcheId(Long.parseLong(id),basketballPlayer.getTeamType(),basketballPlayer.getPlayerId());
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
                            basketballPlayer.setMatchId(Long.parseLong(id));
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
                            List<BasketballPlayer> listPlayer = basketballPlayerService.getByMatcheId(Long.parseLong(id),basketballPlayer.getTeamType(),basketballPlayer.getPlayerId());
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
            //篮球比赛表
            BasketballMatch basketballMatch = new BasketballMatch();
            basketballMatch.setId(Long.parseLong(id));
            basketballMatch.setCreateTime(LocalDateTime.now());
            basketballMatch.setDeleteFlag("0");
            //球队数据，主队
            if(arrayPlayers.get(2) != null && !"".equals(arrayPlayers.get(2))){
                basketballMatch.setHomeData(arrayPlayers.get(2).toString());
            }
            //球队数据，客队   0(兼容)^命中次数-投篮次数^三分球投篮命中次数-三分投篮次数^罚球命中次数-罚球投篮次数^进攻篮板^防守篮板^总的篮板^助攻数^抢断数^盖帽数^失误次数^个人犯规次数^0(兼容)^得分
            if(arrayPlayers.get(3) != null && !"".equals(arrayPlayers.get(3))){
                basketballMatch.setAwayData(arrayPlayers.get(3).toString());
            }
            BasketballMatch basketballMatchNew = basketballMatchService.getById(Long.parseLong(id));
            if (basketballMatchNew!= null){
                basketballMatchNew.setHomeData(basketballMatch.getHomeData());
                basketballMatchNew.setAwayData(basketballMatch.getAwayData());
                basketballMatchService.updateById(basketballMatchNew);
            }else{
                basketballMatchService.save(basketballMatch);
            }
        }
    }
    private void tlive(String tlive,String id){
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
                                basketballTlive.setMatchId(Long.parseLong(id));
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

    private void scoreLive(String score,String id){
        BasketballMatch basketballMatch = new BasketballMatch();
        basketballMatch.setCreateTime(LocalDateTime.now());
        basketballMatch.setDeleteFlag("0");
        if(score != null && score.length() > 0){
            JSONArray arrayScore = JSON.parseArray(score);
            if(arrayScore.size() > 0){
                //比赛id
                if(arrayScore.get(0) != null && !"".equals(arrayScore.get(0))){
                    basketballMatch.setId(Long.parseLong(arrayScore.get(0).toString()));
                    id = arrayScore.get(0).toString();
                }
                //比赛状态
                if(arrayScore.get(1) != null && !"".equals(arrayScore.get(1))){
                    basketballMatch.setMatchStatus(Integer.parseInt(arrayScore.get(1).toString()));
                }
                BasketballMatch basketballMatchNew = basketballMatchService.getById(Long.parseLong(id));
                if (basketballMatchNew!= null){
                    basketballMatchNew.setDeleteFlag("0");
                    basketballMatchNew.setMatchStatus(basketballMatch.getMatchStatus());
                    basketballMatchService.updateById(basketballMatchNew);
                }else{
                    basketballMatchService.save(basketballMatch);
                }
                //实时时间
                String liveTime = "";
                if(arrayScore.get(2) != null && !"".equals(arrayScore.get(2))){
                    liveTime = arrayScore.get(2).toString();
                }
                //主队每节得分
                if(arrayScore.get(3) != null && !"".equals(arrayScore.get(3))){
                    JSONArray hometeamInfo = JSON.parseArray(arrayScore.get(3).toString());
                    if(hometeamInfo.size() > 0){
                        //比赛得分表
                        BasketballScore basketballScore = new BasketballScore();
                        basketballScore.setMatchId(Long.parseLong(id));
                        basketballScore.setTeamType(1);
                        basketballScore.setTime(Long.parseLong(liveTime));
                        basketballScore.setCreateTime(LocalDateTime.now());
                        basketballScore.setDeleteFlag("0");
                        //第1节分数
                        if(hometeamInfo.get(0) != null && !"".equals(hometeamInfo.get(0))){
                            basketballScore.setFirstSectionScores(Integer.parseInt(hometeamInfo.get(0).toString()));
                        }
                        //第2节分数
                        if(hometeamInfo.get(1) != null && !"".equals(hometeamInfo.get(1))){
                            basketballScore.setSecondSectionScores(Integer.parseInt(hometeamInfo.get(1).toString()));
                        }
                        //第3节分数
                        if(hometeamInfo.get(2) != null && !"".equals(hometeamInfo.get(2))){
                            basketballScore.setThirdSectionScores(Integer.parseInt(hometeamInfo.get(2).toString()));
                        }
                        //第4节分数
                        if(hometeamInfo.get(3) != null && !"".equals(hometeamInfo.get(3))){
                            basketballScore.setFourthSectionScores(Integer.parseInt(hometeamInfo.get(3).toString()));
                        }
                        //加时分数
                        if(hometeamInfo.get(4) != null && !"".equals(hometeamInfo.get(4))){
                            basketballScore.setOvertimeScores(Integer.parseInt(hometeamInfo.get(4).toString()));
                        }
                        List<BasketballScore> listScore = basketballScoreService.getByMatcheId(Long.parseLong(id),basketballScore.getTeamType());
                        if (listScore.size() > 0){
                            basketballScore.setId(listScore.get(0).getId());
                            basketballScore.setTeamId(listScore.get(0).getTeamId());
                            basketballScoreService.updateById(basketballScore);
                        }else{
                            basketballScoreService.save(basketballScore);
                        }
                    }
                }

                //客队每节得分
                if(arrayScore.get(4) != null && !"".equals(arrayScore.get(4))){
                    JSONArray awayteamInfo = JSON.parseArray(arrayScore.get(4).toString());
                    if(awayteamInfo.size() > 0){
                        //比赛得分表
                        BasketballScore basketballScore = new BasketballScore();
                        basketballScore.setMatchId(Long.parseLong(id));
                        basketballScore.setTeamType(2);
                        basketballScore.setTime(Long.parseLong(liveTime));
                        basketballScore.setCreateTime(LocalDateTime.now());
                        basketballScore.setDeleteFlag("0");
                        //第1节分数
                        if(awayteamInfo.get(0) != null && !"".equals(awayteamInfo.get(0))){
                            basketballScore.setFirstSectionScores(Integer.parseInt(awayteamInfo.get(0).toString()));
                        }
                        //第2节分数
                        if(awayteamInfo.get(1) != null && !"".equals(awayteamInfo.get(1))){
                            basketballScore.setSecondSectionScores(Integer.parseInt(awayteamInfo.get(1).toString()));
                        }
                        //第3节分数
                        if(awayteamInfo.get(2) != null && !"".equals(awayteamInfo.get(2))){
                            basketballScore.setThirdSectionScores(Integer.parseInt(awayteamInfo.get(2).toString()));
                        }
                        //第4节分数
                        if(awayteamInfo.get(3) != null && !"".equals(awayteamInfo.get(3))){
                            basketballScore.setFourthSectionScores(Integer.parseInt(awayteamInfo.get(3).toString()));
                        }
                        //加时分数
                        if(awayteamInfo.get(4) != null && !"".equals(awayteamInfo.get(4))){
                            basketballScore.setOvertimeScores(Integer.parseInt(awayteamInfo.get(4).toString()));
                        }
                        List<BasketballScore> listScore = basketballScoreService.getByMatcheId(Long.parseLong(id),basketballScore.getTeamType());
                        if (listScore.size() > 0){
                            basketballScore.setId(listScore.get(0).getId());
                            basketballScore.setTeamId(listScore.get(0).getTeamId());
                            basketballScoreService.updateById(basketballScore);
                        }else{
                            basketballScoreService.save(basketballScore);
                        }
                    }
                }
            }
        }
    }

    private void statsLive(String stats,String id){
        if(stats != null && stats.length() > 0){
            JSONArray array = JSON.parseArray(stats);
            if(array.size() > 0){
                for(int i=0;i<array.size();i++) {
                    JSONArray arrayEach = new JSONArray();
                    arrayEach = JSON.parseArray(array.get(i).toString());
                    if(arrayEach.size() > 0){
                        //比赛统计表
                        BasketballStats basketballStats = new BasketballStats();
                        basketballStats.setMatchId(Long.parseLong(id));
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
                        basketballStats.setMatchId(Long.parseLong(id));
                        List<BasketballStats> listStats = basketballStatsService.getByMatcheId(Long.parseLong(id),basketballStats.getStatsType());
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
    }
}
