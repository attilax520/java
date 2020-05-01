package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballMatch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 足球比赛表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface FootballMatchDao extends BaseMapper<FootballMatch> {
  /**
    * 足球比赛表简单分页查询
    * @param footballMatch 足球比赛表
    * @return
    */
  IPage<FootballMatch> getFootballMatchPage(PageVo pagevo, @Param("footballMatch") FootballMatch footballMatch);

  @Update("UPDATE `football_match_t` SET `delete_flag` = '1' WHERE `id` = '#{Id}")
  Integer updateDeleteFlagById(Integer Id);
}
