package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballIncident;
import org.apache.ibatis.annotations.Param;

/**
 * 比赛发生事件表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballIncidentDao extends BaseMapper<FootballIncident> {
  /**
    * 比赛发生事件表简单分页查询
    * @param footballIncident 比赛发生事件表
    * @return
    */
  IPage<FootballIncident> getFootballIncidentPage(PageVo pagevo, @Param("footballIncident") FootballIncident footballIncident);


}
