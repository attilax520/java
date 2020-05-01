package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballStage;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛阶段表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballStageDao extends BaseMapper<FootballStage> {
  /**
    * 足球比赛阶段表简单分页查询
    * @param footballStage 足球比赛阶段表
    * @return
    */
  IPage<FootballStage> getFootballStagePage(PageVo pagevo, @Param("footballStage") FootballStage footballStage);


}
