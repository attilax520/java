package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.MatchSeason;
import org.apache.ibatis.annotations.Param;

/**
 * 赛季信息表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface MatchSeasonDao extends BaseMapper<MatchSeason> {
  /**
    * 赛季信息表简单分页查询
    * @param matchSeason 赛季信息表
    * @return
    */
  IPage<MatchSeason> getMatchSeasonPage(PageVo pagevo, @Param("matchSeason") MatchSeason matchSeason);


}
