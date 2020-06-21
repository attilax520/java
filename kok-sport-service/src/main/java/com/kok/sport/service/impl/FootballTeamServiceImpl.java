package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballTeamDao;
import com.kok.sport.entity.FootballTeam;
import com.kok.sport.service.FootballTeamService;
import org.springframework.stereotype.Service;

/**
 * 足球球队表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballTeamService")
public class FootballTeamServiceImpl extends ServiceImpl<FootballTeamDao, FootballTeam> implements FootballTeamService {

  /**
   * 足球球队表简单分页查询
   * @param footballTeam 足球球队表
   * @return
   */
  @Override
  public IPage<FootballTeam> getFootballTeamPage(PageVo<FootballTeam> pagevo, FootballTeam footballTeam){
      return baseMapper.getFootballTeamPage(pagevo,footballTeam);
  }

}
