package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.BasketballTeamPlayerDao;
import com.kok.sport.entity.BasketballTeamPlayer;
import com.kok.sport.service.BasketballTeamPlayerService;
import org.springframework.stereotype.Service;

/**
 * 篮球球员表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("basketballTeamPlayerService")
public class BasketballTeamPlayerServiceImpl extends ServiceImpl<BasketballTeamPlayerDao, BasketballTeamPlayer> implements BasketballTeamPlayerService {

  /**
   * 篮球球员表简单分页查询
   * @param basketballTeamPlayer 篮球球员表
   * @return
   */
  @Override
  public IPage<BasketballTeamPlayer> getBasketballTeamPlayerPage(PageVo<BasketballTeamPlayer> pagevo, BasketballTeamPlayer basketballTeamPlayer){
      return baseMapper.getBasketballTeamPlayerPage(pagevo,basketballTeamPlayer);
  }

}
