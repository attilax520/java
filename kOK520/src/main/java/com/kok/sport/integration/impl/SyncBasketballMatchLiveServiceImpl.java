package com.kok.sport.integration.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.kok.sport.integration.SyncBasketballMatchLiveService;
import com.kok.sport.integration.SyncBasketballTeamService;
import com.kok.sport.service.BasketballScoreService;
import com.kok.sport.service.BasketballTeamService;
import com.kok.sport.utils.constant.HttpRequestUtil;
import com.kok.sport.vo.BasketballScoreVO;
import com.kok.sport.vo.BasketballTeamVO;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SyncBasketballMatchLiveServiceImpl implements SyncBasketballMatchLiveService {

    private BasketballScoreService basketballScoreService;

    /**
     * 返回实时变动的即时数据信息,无即时数据变动的比赛不返回
     * @return
     */
    @Override
    public boolean basketballMatchLive() {
        String url = "http://www.skrsport.live/?service=Basketball.Live.Match_live&username=sport_api&secret=0gclkqzK";
        String result = HttpRequestUtil.doGet(url);
        //解析返回结果
        JSONObject resultObj = JSONObject.parseObject(result);
        if(resultObj.getLong("ret").equals(200)) {
            JSONArray dataArr = resultObj.getJSONArray("data");
            List<BasketballScoreVO> scoreList = new ArrayList();
            dataArr.forEach(x -> {
                JSONArray data = JSONObject.parseArray(x.toString());
                List<Long> homeList = JSONObject.parseArray(data.getString(3), Long.class);
                List<Long> awayList = JSONObject.parseArray(data.getString(4), Long.class);
                //主队比分
                BasketballScoreVO homeScoreVO = new BasketballScoreVO();
                homeScoreVO.setMatchId(data.getLong(0));
                homeScoreVO.setTeamType(1L);
                homeScoreVO.setMatchStatus(data.getLong(1));
                homeScoreVO.setTime(data.getLong(2));
                homeScoreVO.setFirstSectionScores(homeList.get(0));
                homeScoreVO.setSecondSectionScores(homeList.get(1));
                homeScoreVO.setThirdSectionScores(homeList.get(2));
                homeScoreVO.setFourthSectionScores(homeList.get(3));
                homeScoreVO.setOvertimeScores(homeList.get(4));
                scoreList.add(homeScoreVO);

                //客队比分
                BasketballScoreVO awayScoreVO = new BasketballScoreVO();
                awayScoreVO.setMatchId(data.getLong(0));
                awayScoreVO.setTeamType(2L);
                awayScoreVO.setMatchStatus(data.getLong(1));
                awayScoreVO.setTime(data.getLong(2));
                awayScoreVO.setFirstSectionScores(awayList.get(0));
                awayScoreVO.setSecondSectionScores(awayList.get(1));
                awayScoreVO.setThirdSectionScores(awayList.get(2));
                awayScoreVO.setFourthSectionScores(awayList.get(3));
                awayScoreVO.setOvertimeScores(awayList.get(4));
                scoreList.add(awayScoreVO);
            });
            basketballScoreService.updateBasketballScore(scoreList);
            return true;
        }
        return false;
    }
}
