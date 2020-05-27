package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballDistribution;
import com.kok.sport.entity.FootballInjury;
import com.kok.sport.entity.FootballLeagueScore;
import com.kok.sport.integration.SyncFootballMatchAnalysisService;
import com.kok.sport.service.FootballDistributionService;
import com.kok.sport.service.FootballInjuryService;
import com.kok.sport.service.FootballLeagueScoreService;
import com.kok.sport.utils.constant.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 足球分析数据接口 返回数据多为历史数据，变化不频繁
 */
@Service
public class SyncFootballMatchAnalysisServiceImpl implements SyncFootballMatchAnalysisService {

    @Autowired
    private FootballInjuryService footballInjuryService;

    @Autowired
    private FootballDistributionService footballDistributionService;

    @Autowired
    private FootballLeagueScoreService footballLeagueScoreService;


    /**
     * 请求接口方法
     * @param Id
     * @return
     */
    public JSONObject requestData(Long Id){
        Map<String,String> map = new HashMap<String,String>(){{
            put("service","Football.Analysis.Match_analysis");
            put("id",Long.toString(Id));
        }};

        return JSONObject.parseObject(HttpUtil.sendGet(map)).getJSONObject("data");
    }

    /**
     * 拉取足球伤停比赛数据
     *
     *      该方法需要做一个数据重复验证的判断，但是因为没有可以做判断的唯一值
     *
     *
     * @param Id 请求接口所需参数，需要比赛Id
     * @return
     */
    @Override
    public boolean insertFootballInjury(Long Id) {

        //football_injury_t  足球比赛伤停表   injury 伤停情况

        JSONObject injuryT = requestData(Id).getJSONObject("injury");        //足球比赛伤停情况数据

        List<FootballInjury> injuryList = new ArrayList<>();

        //添加 足球比赛伤停表数据
        injuryT.forEach((x,y)->{
            JSONArray dataArray = JSONObject.parseArray(y.toString());
            //主队伤停情况
            if ("home".equals(x)){
                for (int i = 0; i < dataArray.size(); i++) {
                    JSONObject homeJson = dataArray.getJSONObject(i);
                    FootballInjury footballInjury = new FootballInjury();
                    //非自增
                    footballInjury.setId((long) i+1);                                           //主键ID
                    //
                    footballInjury.setMatchId(Id);                                    //赛事Id
                    footballInjury.setTeamType(1);                                    //1-主队 2-客队
                    footballInjury.setPlayerId(homeJson.getLong("id"));          //球员 Id
                    footballInjury.setPlayerName(homeJson.getString("name"));    //球员姓名
                    footballInjury.setLogo(homeJson.getString("logo"));          //球员logo
                    footballInjury.setPosition(homeJson.getString("position"));  //球员位置
                    footballInjury.setReason(homeJson.getString("reason"));      //伤停原因
                    footballInjury.setMissedMatches(homeJson.getInteger("missed_matches"));    //影响场次
                    footballInjury.setStartTime(homeJson.getLong("start_time"));           //开始时间
                    footballInjury.setEndTime(homeJson.getLong("end_time"));             //归队时间
                    footballInjury.setType(homeJson.getInteger("type"));             //0-未知 1-受伤 2-停赛
                    footballInjury.setCreateTime(LocalDateTime.now());                //创建时间
                    footballInjury.setDeleteFlag("0");                                //是否删除 0 未删除 1 已删除
                    injuryList.add(footballInjury);
                }
            }
            //客队伤停情况
            if ("away".equals(x)){
                for (int i = 0; i < dataArray.size(); i++) {
                    JSONObject awayJson = dataArray.getJSONObject(i);
                    FootballInjury footballInjury = new FootballInjury();
                    //非自增
                    footballInjury.setId((long) i+2);                                           //主键ID
                    //
                    footballInjury.setMatchId(Id);                                    //赛事Id
                    footballInjury.setTeamType(2);                                    //1-主队 2-客队
                    footballInjury.setPlayerId(awayJson.getLong("id"));          //球员 Id
                    footballInjury.setPlayerName(awayJson.getString("name"));    //球员姓名
                    footballInjury.setLogo(awayJson.getString("logo"));          //球员logo
                    footballInjury.setPosition(awayJson.getString("position"));  //球员位置
                    footballInjury.setReason(awayJson.getString("reason"));      //伤停原因
                    footballInjury.setMissedMatches(awayJson.getInteger("missed_matches"));    //影响场次
                    footballInjury.setStartTime(awayJson.getLong("start_time"));           //开始时间
                    footballInjury.setEndTime(awayJson.getLong("end_time"));             //归队时间
                    footballInjury.setType(awayJson.getInteger("type"));             //0-未知 1-受伤 2-停赛
                    footballInjury.setCreateTime(LocalDateTime.now());                //创建时间
                    footballInjury.setDeleteFlag("0");                                //是否删除 0 未删除 1 已删除
                    injuryList.add(footballInjury);
                }
            }
        });

        return footballInjuryService.saveBatch(injuryList);    //添加足球比赛伤停情况数据
    }

    /**
     * 拉取进球分布数据
     *
     *
     * @param Id
     * @return
     */
    @Override
    public boolean insertFootballDistribution(Long Id) {
        // football_distribution_t 足球进球分布表   goal_distribution 进球分布

        JSONArray info = requestData(Id).getJSONArray("info");  //当前比赛信息
        Long homeTeamId = info.getJSONArray(5).getLong(0);  //主队Id
        Long awayTeamId = info.getJSONArray(6).getLong(0);  //客队Id
        JSONObject distributionT = requestData(Id).getJSONObject("goal_distribution");    //足球比赛进球分布数据
        JSONObject homeData = distributionT.getJSONObject("home"); //主队数据
        JSONObject awayData = distributionT.getJSONObject("away"); //客队数据
        List<FootballDistribution> distributionList = new ArrayList<>();


        //主队数据
        homeData.forEach((x,y)->{
            JSONObject yJson = JSONObject.parseObject(y.toString());
            JSONArray scoredArray = yJson.getJSONArray("scored");   //进球数据
            JSONArray concededArray = yJson.getJSONArray("conceded");   //失球数据
            //场次类型 all
            if ("all".equals(x)){
                //进球数据
                for (int i = 0; i < scoredArray.size(); i++) {
                    JSONArray dataArray = scoredArray.getJSONArray(i);
                    FootballDistribution footballDistribution = new FootballDistribution(){{

                        setMatchId(Id);     //比赛Id
                        setTeamId(homeTeamId);      //球队Id
                        setTeamType(1);     //1-主队 2-客队
                        setSiteType("all");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(1);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+1);      //主键Id
                    distributionList.add(footballDistribution);
                }
                //失球数据
                for (int i = 0; i < concededArray.size(); i++) {
                    JSONArray dataArray = concededArray.getJSONArray(i);
                    FootballDistribution footballDistribution = new FootballDistribution(){{
                        setMatchId(Id);     //比赛Id
                        setTeamId(homeTeamId);      //球队Id
                        setTeamType(1);     //1-主队 2-客队
                        setSiteType("all");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(2);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+7);      //主键Id
                    distributionList.add(footballDistribution);
                }
            }
            //场次类型 home
            if ("home".equals(x)){
                //进球数据
                for (int i = 0; i < scoredArray.size(); i++) {
                    JSONArray dataArray = scoredArray.getJSONArray(i);
                    FootballDistribution footballDistribution = new FootballDistribution(){{

                        setMatchId(Id);     //比赛Id
                        setTeamId(homeTeamId);      //球队Id
                        setTeamType(1);     //1-主队 2-客队
                        setSiteType("home");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(1);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+13);      //主键Id
                    distributionList.add(footballDistribution);
                }
                //失球数据
                for (int i = 0; i < concededArray.size(); i++) {
                    JSONArray dataArray = concededArray.getJSONArray(i);
                    FootballDistribution footballDistribution = new FootballDistribution(){{
                        setMatchId(Id);     //比赛Id
                        setTeamId(homeTeamId);      //球队Id
                        setTeamType(1);     //1-主队 2-客队
                        setSiteType("home");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(2);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+19);      //主键Id
                    distributionList.add(footballDistribution);
                }
            }

            //场次类型 away
            if ("away".equals(x)){
                //进球数据
                for (int i = 0; i < scoredArray.size(); i++) {
                    JSONArray dataArray = scoredArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{

                        setMatchId(Id);     //比赛Id
                        setTeamId(homeTeamId);      //球队Id
                        setTeamType(1);     //1-主队 2-客队
                        setSiteType("away");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(1);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+25);      //主键Id
                    distributionList.add(footballDistribution);
                }
                //失球数据
                for (int i = 0; i < concededArray.size(); i++) {
                    JSONArray dataArray = concededArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{
                        setMatchId(Id);     //比赛Id
                        setTeamId(homeTeamId);      //球队Id
                        setTeamType(1);     //1-主队 2-客队
                        setSiteType("away");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(2);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+31);      //主键Id
                    distributionList.add(footballDistribution);
                }
            }
        });


        //客队数据
        awayData.forEach((x,y)->{
            JSONObject yJson = JSONObject.parseObject(y.toString());
            JSONArray scoredArray = yJson.getJSONArray("scored");   //进球数据
            JSONArray concededArray = yJson.getJSONArray("conceded");   //失球数据
            //场次类型 all
            if ("all".equals(x)){
                //进球数据
                for (int i = 0; i < scoredArray.size(); i++) {
                    JSONArray dataArray = scoredArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{

                        setMatchId(Id);     //比赛Id
                        setTeamId(awayTeamId);      //球队Id
                        setTeamType(2);     //1-主队 2-客队
                        setSiteType("all");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(1);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+37);      //主键Id
                    distributionList.add(footballDistribution);
                }
                //失球数据
                for (int i = 0; i < concededArray.size(); i++) {
                    JSONArray dataArray = concededArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{
                        setMatchId(Id);     //比赛Id
                        setTeamId(awayTeamId);      //球队Id
                        setTeamType(2);     //1-主队 2-客队
                        setSiteType("all");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(2);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+43);      //主键Id
                    distributionList.add(footballDistribution);
                }
            }
            //场次类型 home
            if ("home".equals(x)){
                //进球数据
                for (int i = 0; i < scoredArray.size(); i++) {
                    JSONArray dataArray = scoredArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{

                        setMatchId(Id);     //比赛Id
                        setTeamId(awayTeamId);      //球队Id
                        setTeamType(2);     //1-主队 2-客队
                        setSiteType("home");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(1);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+49);      //主键Id
                    distributionList.add(footballDistribution);
                }
                //失球数据
                for (int i = 0; i < concededArray.size(); i++) {
                    JSONArray dataArray = concededArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{
                        setMatchId(Id);     //比赛Id
                        setTeamId(awayTeamId);      //球队Id
                        setTeamType(2);     //1-主队 2-客队
                        setSiteType("home");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(2);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+55);      //主键Id
                    distributionList.add(footballDistribution);
                }
            }
            //场次类型 away
            if ("away".equals(x)){
                //进球数据
                for (int i = 0; i < scoredArray.size(); i++) {
                    JSONArray dataArray = scoredArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{

                        setMatchId(Id);     //比赛Id
                        setTeamId(awayTeamId);      //球队Id
                        setTeamType(2);     //1-主队 2-客队
                        setSiteType("away");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(1);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+61);      //主键Id
                    distributionList.add(footballDistribution);
                }
                //失球数据
                for (int i = 0; i < concededArray.size(); i++) {
                    JSONArray dataArray = concededArray.getJSONArray(i);

                    FootballDistribution footballDistribution = new FootballDistribution(){{
                        setMatchId(Id);     //比赛Id
                        setTeamId(awayTeamId);      //球队Id
                        setTeamType(2);     //1-主队 2-客队
                        setSiteType("away");    //场次类型
                        setMatches(yJson.getInteger("matches"));              //比赛场次
                        setType(2);     //1-进球(scored) 2-失球(conceded)
                        setBallNumber(dataArray.getInteger(0));   //球个数
                        setPercentage(dataArray.getBigDecimal(1));   //百分比
                        setStartTime(dataArray.getInteger(2));            //开始时间
                        setEndTime(dataArray.getInteger(3));              //结束时间
                        setDeleteFlag("0");         //已删除
                        setCreateTime(LocalDateTime.now());     //未删除
                    }};
                    footballDistribution.setId((long)i+67);      //主键Id
                    distributionList.add(footballDistribution);
                }
            }
        });

       /*测试数据
        FootballDistribution footballDistribution = new FootballDistribution(){{
            setId(1L);      //主键Id
            setMatchId(Id);     //比赛Id
            setTeamId(1L);      //球队Id
            setTeamType(1);     //1-主队 2-客队
            setSiteType("testData");    //场次类型
            setMatches(1);              //比赛场次
            setType(1);     //1-进球 2-失球
            setBallNumber(1);   //球个数
            setPercentage(new BigDecimal(1));   //百分比
            setStartTime(1);            //开始时间
            setEndTime(1);              //结束时间
            setDeleteFlag("0");         //已删除
            setCreateTime(LocalDateTime.now());     //未删除
        }};*/

        return footballDistributionService.saveBatch(distributionList);
    }

    /**
     * 拉取足球联赛积分数据
     *   数据理解：
     *      全部排名 （这边取的全部排名进行添加）
     *      主场排名
     *      客场排名
     * @param Id
     * @return
     */
    @Override
    public boolean insertFootballLeagueScore(Long Id) {
        // football_league_score_t 足球联赛积分表   table   联赛积分
        JSONObject leagueScoreT = requestData(Id).getJSONObject("table");       //足球联赛积分数据
        JSONArray allJson = leagueScoreT.getJSONObject("data").getJSONArray("all"); //all数据类型
        JSONArray homeJson = leagueScoreT.getJSONObject("data").getJSONArray("home");   //home 数据类型
        JSONArray awayJson = leagueScoreT.getJSONObject("data").getJSONArray("away");   //away 数据类型

        List<FootballLeagueScore> leagueScoreList = new ArrayList<>();
        //all数据类型
        for (int i = 0; i < allJson.size(); i++) {
            JSONObject allData = allJson.getJSONObject(i);
            FootballLeagueScore footballLeagueScore = new FootballLeagueScore(){{
                setMatchId(Id);     //比赛Id
                setSeasonId(leagueScoreT.getLong("season_id"));    //赛季Id
                setStageId(leagueScoreT.getLong("stage_id"));     //阶段Id
                setSeason(leagueScoreT.getString("season"));  //赛季名称
                setEventName(leagueScoreT.getString("event_name")); //联赛名称
                setTeamType(0);         // 1-主队 2-客队
                setDataType("all");    //数据类型 all/home/away
                setPosition(allData.getInteger("position"));             //位置(排名???)
                setPts(allData.getInteger("pts"));              //积分
                setPlayed(allData.getInteger("played"));           // 已赛场数
                setWon(allData.getInteger("won"));              //胜场数
                setDrawn(allData.getInteger("drawn"));            //平场数
                setLost(allData.getInteger("lost"));             //失败场数
                setGoals(allData.getInteger("goals"));            //进球数
                setAwayGoals(allData.getInteger("away_goals"));        //客场进球数
                setAgainst(allData.getInteger("against"));          //失球数
                setDiff(allData.getInteger("diff"));             //净胜球数
                setTeamId(allData.getLong("team_id"));          //球队Id
                setCreateTime(LocalDateTime.now());
                setDeleteFlag("0");     //是否删除
            }};
            footballLeagueScore.setId((long) i+1);      //主键Id
            if (footballLeagueScoreService.findTypeAndTeamId(footballLeagueScore.getDataType(), footballLeagueScore.getTeamId()) != null) {
                footballLeagueScoreService.updateById(footballLeagueScore);
            } else {
                leagueScoreList.add(footballLeagueScore);
            }
        }
        //home 主场数据类型
        for (int i = 0; i < homeJson.size(); i++) {
            JSONObject homeData = homeJson.getJSONObject(i);
            FootballLeagueScore footballLeagueScore = new FootballLeagueScore() {{
                setMatchId(Id);     //比赛Id
                setSeasonId(leagueScoreT.getLong("season_id"));    //赛季Id
                setStageId(leagueScoreT.getLong("stage_id"));     //阶段Id
                setSeason(leagueScoreT.getString("season"));  //赛季名称
                setEventName(leagueScoreT.getString("event_name")); //联赛名称
                setTeamType(0);         // 1-主队 2-客队
                setDataType("home");    //数据类型 all/home/away
                setPosition(homeData.getInteger("position"));             //位置(排名???)
                setPts(homeData.getInteger("pts"));              //积分
                setPlayed(homeData.getInteger("played"));           // 已赛场数
                setWon(homeData.getInteger("won"));              //胜场数
                setDrawn(homeData.getInteger("drawn"));            //平场数
                setLost(homeData.getInteger("lost"));             //失败场数
                setGoals(homeData.getInteger("goals"));            //进球数
                setAwayGoals(homeData.getInteger("away_goals"));        //客场进球数
                setAgainst(homeData.getInteger("against"));          //失球数
                setDiff(homeData.getInteger("diff"));             //净胜球数
                setTeamId(homeData.getLong("team_id"));          //球队Id
                setCreateTime(LocalDateTime.now());
                setDeleteFlag("0");     //是否删除
            }};
            footballLeagueScore.setId((long) (i + allJson.size()+1));      //主键Id
            if (footballLeagueScoreService.findTypeAndTeamId(footballLeagueScore.getDataType(), footballLeagueScore.getTeamId()) != null) {
                footballLeagueScoreService.updateById(footballLeagueScore);
            } else {
                leagueScoreList.add(footballLeagueScore);
            }
        }


        //away 主场数据类型
        for (int i = 0; i < awayJson.size(); i++) {
            JSONObject awayData = awayJson.getJSONObject(i);
            FootballLeagueScore footballLeagueScore = new FootballLeagueScore(){{
                setMatchId(Id);     //比赛Id
                setSeasonId(leagueScoreT.getLong("season_id"));    //赛季Id
                setStageId(leagueScoreT.getLong("stage_id"));     //阶段Id
                setSeason(leagueScoreT.getString("season"));  //赛季名称
                setEventName(leagueScoreT.getString("event_name")); //联赛名称
                setTeamType(0);         // 1-主队 2-客队
                setDataType("away");    //数据类型 all/home/away
                setPosition(awayData.getInteger("position"));             //位置(排名???)
                setPts(awayData.getInteger("pts"));              //积分
                setPlayed(awayData.getInteger("played"));           // 已赛场数
                setWon(awayData.getInteger("won"));              //胜场数
                setDrawn(awayData.getInteger("drawn"));            //平场数
                setLost(awayData.getInteger("lost"));             //失败场数
                setGoals(awayData.getInteger("goals"));            //进球数
                setAwayGoals(awayData.getInteger("away_goals"));        //客场进球数
                setAgainst(awayData.getInteger("against"));          //失球数
                setDiff(awayData.getInteger("diff"));             //净胜球数
                setTeamId(awayData.getLong("team_id"));          //球队Id
                setCreateTime(LocalDateTime.now());
                setDeleteFlag("0");     //是否删除
            }};
            footballLeagueScore.setId((long) (i+allJson.size()+homeJson.size()+1));      //主键Id
            if (footballLeagueScoreService.findTypeAndTeamId(footballLeagueScore.getDataType(), footballLeagueScore.getTeamId()) != null) {
                footballLeagueScoreService.updateById(footballLeagueScore);
            } else {
                leagueScoreList.add(footballLeagueScore);
            }
        }

        //测试数据
        /*FootballLeagueScore footballLeagueScore = new FootballLeagueScore(){{
            setId(1L);      //主键Id
            setMatchId(Id);     //比赛Id
            setSeasonId(Id);    //赛季Id
            setStageId(Id);     //阶段Id
            setSeason("testData");  //赛季名称
            setEventName("testData"); //联赛名称
            setTeamType(1);         // 1-主队 2-客队
            setDataType("testData");    //数据类型 all/home/away
            setPosition(1);             //位置(排名???)
            setPts(1);              //积分
            setPlayed(1);           // 已赛场数
            setWon(1);              //胜场数
            setDrawn(1);            //平场数
            setLost(1);             //失败场数
            setGoals(1);            //进球数
            setAwayGoals(1);        //客场进球数
            setAgainst(1);          //失球数
            setDiff(1);             //净失球数
            setTeamId(1L);          //球队Id
            setCreateTime(LocalDateTime.now());
            setDeleteFlag("0");     //是否删除
        }};*/
        return footballLeagueScoreService.saveBatch(leagueScoreList);
    }

}
