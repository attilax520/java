package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kok.sport.entity.FootballScore;
import com.kok.sport.integration.SyncFootballMatchLiveService;
import com.kok.sport.service.FootballScoreService;
import com.kok.sport.utils.constant.HttpRequestUtil;
import com.kok.sport.vo.BasketballScoreVO;
import com.kok.sport.vo.FootballScoreVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyncFootballMatchLiveServiceImpl implements SyncFootballMatchLiveService {

    private FootballScoreService footballScoreService;

    /**
     * 返回实时变动的即时数据信息,无即时数据变动的比赛不返回
     * @return
     */
    @Override
    public boolean footballMatchLive() {
        String url = "http://www.skrsport.live/?service=Football.Live.Match_live&username=sport_api&secret=0gclkqzK";
        String result = HttpRequestUtil.doGet(url);
        //解析返回结果
        JSONObject resultObj = JSONObject.parseObject(result);
        if(resultObj.getLong("ret").equals(200)) {
            JSONArray dataArr = resultObj.getJSONArray("data");
            List<FootballScoreVO> scoreList = new ArrayList();
            dataArr.forEach(x -> {
                JSONArray data = JSONObject.parseArray(x.toString());
                List<Integer> homeList = JSONObject.parseArray(data.getString(2), Integer.class);
                List<Integer> awayList = JSONObject.parseArray(data.getString(3), Integer.class);
                //获取球队ID
                Long matchId = data.getLong(0);
                List<FootballScore> teamList = footballScoreService.getTeamIdByMatchId(matchId);
                //主队比分
                FootballScoreVO homeScoreVO = new FootballScoreVO();
                homeScoreVO.setMatchId(matchId);
                homeScoreVO.setTeamId(null);
                homeScoreVO.setTeamType(1);
                homeScoreVO.setRegularTimeScore(homeList.get(0));
                homeScoreVO.setHalfCourtScore(homeList.get(1));
                homeScoreVO.setRedCard(homeList.get(2));
                homeScoreVO.setYellowCard(homeList.get(3));
                homeScoreVO.setCornerKick(homeList.get(4));
                homeScoreVO.setOvertimeScore(homeList.get(5));
                homeScoreVO.setPointScore(homeList.get(6));
                homeScoreVO.setRemark(data.getString(5));

                //客队比分
                FootballScoreVO awayScoreVO = new FootballScoreVO();
                awayScoreVO.setMatchId(data.getLong(0));
                awayScoreVO.setTeamId(null);
                awayScoreVO.setTeamType(1);
                awayScoreVO.setRegularTimeScore(awayList.get(0));
                awayScoreVO.setHalfCourtScore(awayList.get(1));
                awayScoreVO.setRedCard(awayList.get(2));
                awayScoreVO.setYellowCard(awayList.get(3));
                awayScoreVO.setCornerKick(awayList.get(4));
                awayScoreVO.setOvertimeScore(awayList.get(5));
                awayScoreVO.setPointScore(awayList.get(6));

                //获取球队ID
                teamList.forEach(team -> {
                    if(team.getTeamType() == 1) {//主队
                        homeScoreVO.setTeamId(team.getTeamId());
                    } else if(team.getTeamType() == 2) {//客队
                        awayScoreVO.setTeamId(team.getTeamId());
                    }
                });
                scoreList.add(homeScoreVO);
                scoreList.add(awayScoreVO);
            });
            footballScoreService.updateFootballScore(scoreList);
            return true;
        }
        return false;
    }
}
