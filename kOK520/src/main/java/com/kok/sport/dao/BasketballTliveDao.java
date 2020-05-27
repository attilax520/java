package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballTlive;
import org.apache.ibatis.annotations.Param;

/**
 * 篮球比赛文字直播表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballTliveDao extends BaseMapper<BasketballTlive> {
  /**
    * 篮球比赛文字直播表简单分页查询
    * @param basketballTlive 篮球比赛文字直播表
    * @return
    */
  IPage<BasketballTlive> getBasketballTlivePage(PageVo pagevo, @Param("basketballTlive") BasketballTlive basketballTlive);


}
