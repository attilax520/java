package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballStage;
import com.kok.sport.vo.BasketballStageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 篮球比赛阶段表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface BasketballStageDao extends BaseMapper<BasketballStage> {
    /**
     * 篮球比赛阶段表简单分页查询
     *
     * @param basketballStage 篮球比赛阶段表
     * @return
     */
    IPage<BasketballStage> getBasketballStagePage(PageVo pagevo, @Param("basketballStage") BasketballStage basketballStage);

    /**
     * 批量保存篮球阶段信息
     * @param stageList
     * @return
     */
    int saveBasketballStage(@Param("stageList") List<BasketballStageVO> stageList);
}
