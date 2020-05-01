package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballOdds;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 足球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballOddsDao extends BaseMapper<FootballOdds> {
  /**
    * 足球盘口指数表简单分页查询
    * @param footballOdds 足球盘口指数表
    * @return
    */
  IPage<FootballOdds> getFootballOddsPage(PageVo pagevo, @Param("footballOdds") FootballOdds footballOdds);

  /**
   * 根据公司Id，比赛Id，变化时间查询重复数据
   * @param companyId 公司Id
   * @param matchId 比赛Id
   * @param changeTime  变化时间
   * @return
   */
  @Select("SELECT * FROM `dev_kok_sport`.`football_odds_t` WHERE company_id = #{companyId} AND match_id = #{matchId} AND change_time = #{changeTime}")
  FootballOdds findRepeatData(@Param("companyId") Long companyId,@Param("matchId") Long matchId,@Param("changeTime") Long changeTime);
}
