package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.base.vo.PageVo;
import com.kok.sport.dao.BasketballOddsDao;
import com.kok.sport.entity.BasketballOdds;
import com.kok.sport.service.BasketballOddsService;
import com.kok.sport.vo.BasketballMatchVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("basketballOddsService")
public class BasketballOddsServiceImpl extends ServiceImpl<BasketballOddsDao, BasketballOdds> implements BasketballOddsService {

    /**
     * 篮球盘口指数表简单分页查询
     *
     * @param basketballOdds 篮球盘口指数表
     * @return
     */
    @Override
    public IPage<BasketballOdds> getBasketballOddsPage(PageVo<BasketballOdds> pagevo, BasketballOdds basketballOdds) {
        return baseMapper.getBasketballOddsPage(pagevo, basketballOdds);
    }

    /**
     * 根据比赛matchId查询该表
     */
    public List<BasketballOdds> getByMatcheId(Long matchId, Long companyId, Integer oddsType) {
        return baseMapper.getByMatcheId(matchId, companyId, oddsType);
    }

    /**
     * 批量保存篮球盘口指数
     *
     * @param oddsList
     * @return
     */
    @Override
    public int saveBasketballOdds(List<BasketballOdds> oddsList) {
        return baseMapper.saveBasketballOdds(oddsList);
    }
}
