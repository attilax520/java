package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.BasketballPlayerDao;
import com.kok.sport.entity.BasketballPlayer;
import com.kok.sport.service.BasketballPlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * string
 *
 * @author martin
 * @date 2020-03-28 22:19:31
 */
@Service("basketballPlayerService")
public class BasketballPlayerServiceImpl extends ServiceImpl<BasketballPlayerDao, BasketballPlayer> implements BasketballPlayerService {

  /**
   * string简单分页查询
   * @param basketballPlayer string
   * @return
   */
  @Override
  public IPage<BasketballPlayer> getBasketballPlayerPage(PageVo<BasketballPlayer> pagevo, BasketballPlayer basketballPlayer){
      return baseMapper.getBasketballPlayerPage(pagevo,basketballPlayer);
  }

    /**
     * 根据比赛matchId查询该表
     *
     */
    public List<BasketballPlayer> getByMatcheId(Long matchId, Integer teamType, Long playerId){
        return baseMapper.getByMatcheId(matchId,teamType,playerId);
    }

}
