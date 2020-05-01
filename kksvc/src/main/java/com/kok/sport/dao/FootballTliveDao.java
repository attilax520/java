package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballTlive;
import org.apache.ibatis.annotations.Param;

/**
 * 足球比赛文字直播表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballTliveDao extends BaseMapper<FootballTlive> {
  /**
    * 足球比赛文字直播表简单分页查询
    * @param footballTlive 足球比赛文字直播表
    * @return
    */
  IPage<FootballTlive> getFootballTlivePage(PageVo pagevo, @Param("footballTlive") FootballTlive footballTlive);


}
