package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballTeam;
import com.kok.sport.vo.BasketballTeamVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球球队表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballTeamDao extends BaseMapper<BasketballTeam> {
  /**
    * 篮球球队表简单分页查询
    * @param basketballTeam 篮球球队表
    * @return
    */
  IPage<BasketballTeam> getBasketballTeamPage(PageVo pagevo, @Param("basketballTeam") BasketballTeam basketballTeam);

  /**
   * 批量保存篮球球队信息
   * @param teamList
   * @return
   */
  int saveBasketballTeam(@Param("teamList") List<BasketballTeamVO> teamList);

  /**
   * 批量删除篮球球队信息，软删除
   * @param teamList
   * @return
   */
  int deleteBasketballTeam(@Param("teamList")List<Long> teamList);
}
