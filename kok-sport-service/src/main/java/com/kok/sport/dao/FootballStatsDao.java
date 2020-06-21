package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballStats;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛统计表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballStatsDao extends BaseMapper<FootballStats> {
  /**
    * 足球比赛统计表简单分页查询
    * @param footballStats 足球比赛统计表
    * @return
    */
  IPage<FootballStats> getFootballStatsPage(PageVo pagevo, @Param("footballStats") FootballStats footballStats);


}
