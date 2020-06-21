package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.BasketballStageDao;
import com.kok.sport.entity.BasketballStage;
import com.kok.sport.service.BasketballStageService;
import com.kok.sport.vo.BasketballStageVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球比赛阶段表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("basketballStageService")
public class BasketballStageServiceImpl extends ServiceImpl<BasketballStageDao, BasketballStage> implements BasketballStageService {

    /**
     * 篮球比赛阶段表简单分页查询
     *
     * @param basketballStage 篮球比赛阶段表
     * @return
     */
    @Override
    public IPage<BasketballStage> getBasketballStagePage(PageVo<BasketballStage> pagevo, BasketballStage basketballStage) {
        return baseMapper.getBasketballStagePage(pagevo, basketballStage);
    }

    /**
     * 批量保存篮球阶段信息
     *
     * @param stageList
     * @return
     */
    @Override
    public int saveBasketballStage(List<BasketballStageVO> stageList) {
        return baseMapper.saveBasketballStage(stageList);
    }

}
