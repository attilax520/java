package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballOdds;
import com.kok.sport.entity.BasketballPlayer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * string
 *
 * @author martin
 * @date 2020-03-28 22:19:31
 */
public interface BasketballPlayerDao extends BaseMapper<BasketballPlayer> {
  /**
    * string简单分页查询
    * @param basketballPlayer string
    * @return
    */
  IPage<BasketballPlayer> getBasketballPlayerPage(PageVo pagevo, @Param("basketballPlayer") BasketballPlayer basketballPlayer);

  List<BasketballPlayer> getByMatcheId(@Param("matchId")Long matchId, @Param("teamType")Integer teamType, @Param("playerId")Long playerId);

}
