package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballTeamPlayerDao;
import com.kok.sport.entity.FootballTeamPlayer;
import com.kok.sport.service.FootballTeamPlayerService;
import org.springframework.stereotype.Service;

/**
 * 足球球队球员表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballTeamPlayerService")
public class FootballTeamPlayerServiceImpl extends ServiceImpl<FootballTeamPlayerDao, FootballTeamPlayer> implements FootballTeamPlayerService {

  /**
   * 足球球队球员表简单分页查询
   * @param footballTeamPlayer 足球球队球员表
   * @return
   */
  @Override
  public IPage<FootballTeamPlayer> getFootballTeamPlayerPage(PageVo<FootballTeamPlayer> pagevo, FootballTeamPlayer footballTeamPlayer){
      return baseMapper.getFootballTeamPlayerPage(pagevo,footballTeamPlayer);
  }

}
