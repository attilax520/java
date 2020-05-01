package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballTeamPlayer;
import org.apache.ibatis.annotations.Param;

/**
 * 篮球球员表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballTeamPlayerDao extends BaseMapper<BasketballTeamPlayer> {
  /**
    * 篮球球员表简单分页查询
    * @param basketballTeamPlayer 篮球球员表
    * @return
    */
  IPage<BasketballTeamPlayer> getBasketballTeamPlayerPage(PageVo pagevo, @Param("basketballTeamPlayer") BasketballTeamPlayer basketballTeamPlayer);


}
