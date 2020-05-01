package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballScore;
import com.kok.sport.vo.BasketballScoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球比赛得分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballScoreDao extends BaseMapper<BasketballScore> {
    /**
     * 篮球比赛得分表简单分页查询
     *
     * @param basketballScore 篮球比赛得分表
     * @return
     */
    IPage<BasketballScore> getBasketballScorePage(PageVo pagevo, @Param("basketballScore") BasketballScore basketballScore);

    /**
     * 批量保存篮球得分信息
     * @param scoreList
     * @return
     */
    int saveBasketballScore(@Param("scoreList") List<BasketballScoreVO> scoreList);

    List<BasketballScore> getByMatcheId(@Param("matchId")Long matchId,@Param("team_type")Integer team_type);

    /**
     * 批量更新篮球得分信息
     * @param scoreList
     * @return
     */
    int updateBasketballScore(@Param("scoreList") List<BasketballScoreVO> scoreList);
}
