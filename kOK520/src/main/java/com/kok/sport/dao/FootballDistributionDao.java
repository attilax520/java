package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballDistribution;
import org.apache.ibatis.annotations.Param;

/**
 * 足球进球分布表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballDistributionDao extends BaseMapper<FootballDistribution> {
  /**
    * 足球进球分布表简单分页查询
    * @param footballDistribution 足球进球分布表
    * @return
    */
  IPage<FootballDistribution> getFootballDistributionPage(PageVo pagevo, @Param("footballDistribution") FootballDistribution footballDistribution);


}
