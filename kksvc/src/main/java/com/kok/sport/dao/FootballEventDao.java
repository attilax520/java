package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballEvent;
import org.apache.ibatis.annotations.Param;

/**
 * 足球赛事表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballEventDao extends BaseMapper<FootballEvent> {
  /**
    * 足球赛事表简单分页查询
    * @param footballEvent 足球赛事表
    * @return
    */
  IPage<FootballEvent> getFootballEventPage(PageVo pagevo, @Param("footballEvent") FootballEvent footballEvent);


}
