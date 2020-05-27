package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballStats;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球比赛统计表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballStatsDao extends BaseMapper<BasketballStats> {
  /**
    * 篮球比赛统计表简单分页查询
    * @param basketballStats 篮球比赛统计表
    * @return
    */
  IPage<BasketballStats> getBasketballStatsPage(PageVo pagevo, @Param("basketballStats") BasketballStats basketballStats);

  List<BasketballStats> getByMatcheId(@Param("matchId")Long matchId, @Param("statsType")Integer statsType);
}
