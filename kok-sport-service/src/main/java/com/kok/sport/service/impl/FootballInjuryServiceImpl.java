package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballInjuryDao;
import com.kok.sport.entity.FootballInjury;
import com.kok.sport.service.FootballInjuryService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛伤停情况表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballInjuryService")
public class FootballInjuryServiceImpl extends ServiceImpl<FootballInjuryDao, FootballInjury> implements FootballInjuryService {

  /**
   * 足球比赛伤停情况表简单分页查询
   * @param footballInjury 足球比赛伤停情况表
   * @return
   */
  @Override
  public IPage<FootballInjury> getFootballInjuryPage(PageVo<FootballInjury> pagevo, FootballInjury footballInjury){
      return baseMapper.getFootballInjuryPage(pagevo,footballInjury);
  }

}
