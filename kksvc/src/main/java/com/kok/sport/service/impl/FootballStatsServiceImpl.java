package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballStatsDao;
import com.kok.sport.entity.FootballStats;
import com.kok.sport.service.FootballStatsService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛统计表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballStatsService")
public class FootballStatsServiceImpl extends ServiceImpl<FootballStatsDao, FootballStats> implements FootballStatsService {

  /**
   * 足球比赛统计表简单分页查询
   * @param footballStats 足球比赛统计表
   * @return
   */
  @Override
  public IPage<FootballStats> getFootballStatsPage(PageVo<FootballStats> pagevo, FootballStats footballStats){
      return baseMapper.getFootballStatsPage(pagevo,footballStats);
  }

}
