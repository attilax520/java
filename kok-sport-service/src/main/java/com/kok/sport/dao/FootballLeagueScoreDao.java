package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballLeagueScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 足球联赛积分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballLeagueScoreDao extends BaseMapper<FootballLeagueScore> {
  /**
    * 足球联赛积分表简单分页查询
    * @param footballLeagueScore 足球联赛积分表
    * @return
    */
  IPage<FootballLeagueScore> getFootballLeagueScorePage(PageVo pagevo, @Param("footballLeagueScore") FootballLeagueScore footballLeagueScore);

  /**
   * 查询足球重复数据
   * @param dataType
   * @param teamId
   * @return
   */
  @Select("SELECT * FROM `dev_kok_sport`.`football_league_score_t` WHERE data_type = #{dataType} AND team_id = #{teamId}")
  FootballLeagueScore findTypeAndTeamId(@Param("dataType") String dataType,@Param("teamId") Long teamId);

}
