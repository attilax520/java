package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.base.vo.PageVo;
import com.kok.sport.dao.BasketballEventDao;
import com.kok.sport.dao.BasketballTeamDao;
import com.kok.sport.entity.BasketballEvent;
import com.kok.sport.entity.BasketballTeam;
import com.kok.sport.service.BasketballEventService;
import com.kok.sport.service.BasketballTeamService;
import com.kok.sport.vo.BasketballEventVO;
import com.kok.sport.vo.BasketballTeamVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球赛事表
 */
@Service("basketballEventService")
public class BasketballEventServiceImpl extends ServiceImpl<BasketballEventDao, BasketballEvent> implements BasketballEventService {

    /**
     * 篮球赛事表简单分页查询
     * @param pagevo
     * @param basketballEvent 篮球赛事表
     * @return
     */
    @Override
    public IPage<BasketballEvent> getBasketballEventPage(PageVo<BasketballEvent> pagevo, BasketballEvent basketballEvent) {
        return baseMapper.getBasketballEventPage(pagevo, basketballEvent);
    }

    /**
     * 批量保存篮球赛事信息
     *
     * @param eventList
     * @return
     */
    @Override
    public int saveBasketballEvent(List<BasketballEventVO> eventList) {
        return baseMapper.saveBasketballEvent(eventList);
    }

    /**
     * 批量删除篮球赛事，软删除
     *
     * @param eventList
     * @return
     */
    @Override
    public int deleteBasketballEvent(List<Long> eventList) {
        return baseMapper.deleteBasketballEvent(eventList);
    }
}
