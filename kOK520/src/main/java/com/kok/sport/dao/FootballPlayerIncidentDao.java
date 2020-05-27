package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballPlayerIncident;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛球员事件表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballPlayerIncidentDao extends BaseMapper<FootballPlayerIncident> {
  /**
    * 足球比赛球员事件表简单分页查询
    * @param footballPlayerIncident 足球比赛球员事件表
    * @return
    */
  IPage<FootballPlayerIncident> getFootballPlayerIncidentPage(PageVo pagevo, @Param("footballPlayerIncident") FootballPlayerIncident footballPlayerIncident);


}
