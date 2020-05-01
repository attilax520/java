package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballEnvironment;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛环境表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballEnvironmentDao extends BaseMapper<FootballEnvironment> {
  /**
    * 足球比赛环境表简单分页查询
    * @param footballEnvironment 足球比赛环境表
    * @return
    */
  IPage<FootballEnvironment> getFootballEnvironmentPage(PageVo pagevo, @Param("footballEnvironment") FootballEnvironment footballEnvironment);


}
