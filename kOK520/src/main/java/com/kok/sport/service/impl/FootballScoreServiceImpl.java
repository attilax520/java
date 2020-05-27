package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballScoreDao;
import com.kok.sport.entity.FootballScore;
import com.kok.sport.service.FootballScoreService;
import com.kok.sport.vo.BasketballScoreVO;
import com.kok.sport.vo.FootballScoreVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 足球比赛得分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballScoreService")
public class FootballScoreServiceImpl extends ServiceImpl<FootballScoreDao, FootballScore> implements FootballScoreService {

    /**
     * 足球比赛得分表简单分页查询
     *
     * @param footballScore 足球比赛得分表
     * @return
     */
    @Override
    public IPage<FootballScore> getFootballScorePage(PageVo<FootballScore> pagevo, FootballScore footballScore) {
        return baseMapper.getFootballScorePage(pagevo, footballScore);
    }

    /**
     * 根据比赛ID获取球队ID，用于第三方接口数据更新
     *
     * @param matchId
     * @return
     */
    @Override
    public List<FootballScore> getTeamIdByMatchId(Long matchId) {
        return baseMapper.getTeamIdByMatchId(matchId);
    }

    /**
     * 批量更新足球得分信息
     *
     * @param scoreList
     * @return
     */
    @Override
    public int updateFootballScore(List<FootballScoreVO> scoreList) {
        return baseMapper.updateFootballScore(scoreList);
    }

}
