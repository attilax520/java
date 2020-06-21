package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballInjury;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛伤停情况表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballInjuryDao extends BaseMapper<FootballInjury> {
  /**
    * 足球比赛伤停情况表简单分页查询
    * @param footballInjury 足球比赛伤停情况表
    * @return
    */
  IPage<FootballInjury> getFootballInjuryPage(PageVo pagevo, @Param("footballInjury") FootballInjury footballInjury);


}
