package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballFormationDao;
import com.kok.sport.entity.FootballFormation;
import com.kok.sport.service.FootballFormationService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛阵型表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballFormationService")
public class FootballFormationServiceImpl extends ServiceImpl<FootballFormationDao, FootballFormation> implements FootballFormationService {

  /**
   * 足球比赛阵型表简单分页查询
   * @param footballFormation 足球比赛阵型表
   * @return
   */
  @Override
  public IPage<FootballFormation> getFootballFormationPage(PageVo<FootballFormation> pagevo, FootballFormation footballFormation){
      return baseMapper.getFootballFormationPage(pagevo,footballFormation);
  }

}
