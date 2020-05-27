package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.BasketballStatsDao;
import com.kok.sport.entity.BasketballStats;
import com.kok.sport.service.BasketballStatsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球比赛统计表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("basketballStatsService")
public class BasketballStatsServiceImpl extends ServiceImpl<BasketballStatsDao, BasketballStats> implements BasketballStatsService {

  /**
   * 篮球比赛统计表简单分页查询
   * @param basketballStats 篮球比赛统计表
   * @return
   */
  @Override
  public IPage<BasketballStats> getBasketballStatsPage(PageVo<BasketballStats> pagevo, BasketballStats basketballStats){
      return baseMapper.getBasketballStatsPage(pagevo,basketballStats);
  }

    /**
     * 根据比赛matchId查询该表
     *
     */
    public List<BasketballStats> getByMatcheId(Long matchId, Integer statsType){
        return baseMapper.getByMatcheId(matchId,statsType);
    }

}
