package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballMatch;
import com.kok.sport.vo.BasketballMatchVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球比赛表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballMatchDao extends BaseMapper<BasketballMatch> {
  /**
    * 篮球比赛表简单分页查询
    * @param basketballMatch 篮球比赛表
    * @return
    */
  IPage<BasketballMatch> getBasketballMatchPage(PageVo pagevo, @Param("basketballMatch") BasketballMatch basketballMatch);

  /**
   * 批量保存篮球比赛信息
   * @param matchList
   * @return
   */
  int saveBasketballMatch(@Param("matchList") List<BasketballMatchVO> matchList);
}
