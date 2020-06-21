package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.base.vo.PageVo;
import com.kok.sport.dao.BasketballEventDao;
import com.kok.sport.dao.BasketballMatchDao;
import com.kok.sport.entity.BasketballEvent;
import com.kok.sport.entity.BasketballMatch;
import com.kok.sport.service.BasketballEventService;
import com.kok.sport.service.BasketballMatchService;
import com.kok.sport.vo.BasketballEventVO;
import com.kok.sport.vo.BasketballMatchVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球比赛表
 */
@Service("basketballMatchService")
public class BasketballMatchServiceImpl extends ServiceImpl<BasketballMatchDao, BasketballMatch> implements BasketballMatchService {

    /**
     * 篮球比赛表简单分页查询
     *
     * @param basketballMatch 篮球比赛表
     * @return
     */
    @Override
    public IPage<BasketballMatch> getBasketballMatchPage(PageVo<BasketballMatch> pagevo, BasketballMatch basketballMatch) {
        return baseMapper.getBasketballMatchPage(pagevo, basketballMatch);
    }

    /**
     * 批量保存篮球比赛信息
     * @param matchList
     * @return
     */
    @Override
    public int saveBasketballMatch(List<BasketballMatchVO> matchList) {
        return baseMapper.saveBasketballMatch(matchList);
    }
}
