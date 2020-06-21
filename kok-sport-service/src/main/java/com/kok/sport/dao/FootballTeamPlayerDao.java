package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballTeamPlayer;
import org.apache.ibatis.annotations.Param;

/**
 * 足球球队球员表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballTeamPlayerDao extends BaseMapper<FootballTeamPlayer> {
  /**
    * 足球球队球员表简单分页查询
    * @param footballTeamPlayer 足球球队球员表
    * @return
    */
  IPage<FootballTeamPlayer> getFootballTeamPlayerPage(PageVo pagevo, @Param("footballTeamPlayer") FootballTeamPlayer footballTeamPlayer);


}
