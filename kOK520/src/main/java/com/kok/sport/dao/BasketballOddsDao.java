package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballOdds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballOddsDao extends BaseMapper<BasketballOdds> {
    /**
     * 篮球盘口指数表简单分页查询
     *
     * @param basketballOdds 篮球盘口指数表
     * @return
     */
    IPage<BasketballOdds> getBasketballOddsPage(PageVo pagevo, @Param("basketballOdds") BasketballOdds basketballOdds);

    List<BasketballOdds> getByMatcheId(@Param("matchId") Long matchId, @Param("companyId") Long companyId, @Param("oddsType") Integer oddsType);

  /**
   * 批量保存篮球盘口指数
   * @param oddsList
   * @return
   */
    int saveBasketballOdds(@Param("oddsList") List<BasketballOdds> oddsList);
}
