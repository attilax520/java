package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.base.vo.PageVo;
import com.kok.sport.dao.BasketballScoreDao;
import com.kok.sport.entity.BasketballMatch;
import com.kok.sport.entity.BasketballScore;
import com.kok.sport.service.BasketballScoreService;
import com.kok.sport.vo.BasketballMatchVO;
import com.kok.sport.vo.BasketballScoreVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球比赛表
 */
@Service("basketballScoreService")
public class BasketballScoreServiceImpl extends ServiceImpl<BasketballScoreDao, BasketballScore> implements BasketballScoreService {

    /**
     * 篮球比赛得分表简单分页查询
     *
     * @param basketballScore 篮球比赛得分表
     * @return
     */
    @Override
    public IPage<BasketballScore> getBasketballScorePage(PageVo<BasketballScore> pagevo, BasketballScore basketballScore) {
        return baseMapper.getBasketballScorePage(pagevo, basketballScore);
    }

    /**
     * 批量保存篮球得分信息
     *
     * @param scoreList
     * @return
     */
    @Override
    public int saveBasketballScore(List<BasketballScoreVO> scoreList) {
        return baseMapper.saveBasketballScore(scoreList);
    }

    /**
     * 根据比赛matchId查询该表
     *
     */
    public List<BasketballScore> getByMatcheId(Long matchId, Integer team_type){
        return baseMapper.getByMatcheId(matchId,team_type);
    }

    /**
     * 批量更新篮球得分信息
     *
     * @param scoreList
     * @return
     */
    @Override
    public int updateBasketballScore(List<BasketballScoreVO> scoreList) {
        return baseMapper.updateBasketballScore(scoreList);
    }
}
