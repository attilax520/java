package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballTeam;
import org.apache.ibatis.annotations.Param;

/**
 * 足球球队表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballTeamDao extends BaseMapper<FootballTeam> {
  /**
    * 足球球队表简单分页查询
    * @param footballTeam 足球球队表
    * @return
    */
  IPage<FootballTeam> getFootballTeamPage(PageVo pagevo, @Param("footballTeam") FootballTeam footballTeam);


}
