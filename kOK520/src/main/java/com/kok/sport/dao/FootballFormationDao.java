package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballFormation;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛阵型表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballFormationDao extends BaseMapper<FootballFormation> {
  /**
    * 足球比赛阵型表简单分页查询
    * @param footballFormation 足球比赛阵型表
    * @return
    */
  IPage<FootballFormation> getFootballFormationPage(PageVo pagevo, @Param("footballFormation") FootballFormation footballFormation);


}
