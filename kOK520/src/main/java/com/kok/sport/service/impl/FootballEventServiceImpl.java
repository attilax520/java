package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballEventDao;
import com.kok.sport.entity.FootballEvent;
import com.kok.sport.service.FootballEventService;
import org.springframework.stereotype.Service;

/**
 * 足球赛事表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballEventService")
public class FootballEventServiceImpl extends ServiceImpl<FootballEventDao, FootballEvent> implements FootballEventService {

  /**
   * 足球赛事表简单分页查询
   * @param footballEvent 足球赛事表
   * @return
   */
  @Override
  public IPage<FootballEvent> getFootballEventPage(PageVo<FootballEvent> pagevo, FootballEvent footballEvent){
      return baseMapper.getFootballEventPage(pagevo,footballEvent);
  }

}
