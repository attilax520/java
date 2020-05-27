package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballDistributionDao;
import com.kok.sport.entity.FootballDistribution;
import com.kok.sport.service.FootballDistributionService;
import org.springframework.stereotype.Service;

/**
 * 足球进球分布表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballDistributionService")
public class FootballDistributionServiceImpl extends ServiceImpl<FootballDistributionDao, FootballDistribution> implements FootballDistributionService {

  /**
   * 足球进球分布表简单分页查询
   * @param footballDistribution 足球进球分布表
   * @return
   */
  @Override
  public IPage<FootballDistribution> getFootballDistributionPage(PageVo<FootballDistribution> pagevo, FootballDistribution footballDistribution){
      return baseMapper.getFootballDistributionPage(pagevo,footballDistribution);
  }

}
