package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballScore;
import com.kok.sport.entity.FootballScore;
import com.kok.sport.vo.BasketballScoreVO;
import com.kok.sport.vo.FootballScoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 足球比赛得分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballScoreDao extends BaseMapper<FootballScore> {

    /**
     * 足球比赛得分表简单分页查询
     *
     * @param footballScore 足球比赛得分表
     * @return
     */
    IPage<FootballScore> getFootballScorePage(PageVo pagevo, @Param("footballScore") FootballScore footballScore);

    /**
     * 根据比赛ID获取球队ID，用于第三方接口数据更新
     *
     * @param matchId
     * @return
     */
    List<FootballScore> getTeamIdByMatchId(Long matchId);

    /**
     * 批量更新篮球得分信息
     *
     * @param scoreList
     * @return
     */
    int updateFootballScore(@Param("scoreList") List<FootballScoreVO> scoreList);
}
