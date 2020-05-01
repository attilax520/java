package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballEvent;
import com.kok.sport.vo.BasketballEventVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球赛事表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballEventDao extends BaseMapper<BasketballEvent> {
  /**
    * 篮球赛事表简单分页查询
    * @param basketballEvent 篮球赛事表
    * @return
    */
  IPage<BasketballEvent> getBasketballEventPage(PageVo pagevo, @Param("basketballEvent") BasketballEvent basketballEvent);

  /**
   * 批量保存篮球赛事信息
   * @param eventList
   * @return
   */
  int saveBasketballEvent(@Param("eventList") List<BasketballEventVO> eventList);

  /**
   * 批量删除篮球赛事，软删除
   * @param eventList
   * @return
   */
  int deleteBasketballEvent(@Param("eventList") List<Long> eventList);
}
