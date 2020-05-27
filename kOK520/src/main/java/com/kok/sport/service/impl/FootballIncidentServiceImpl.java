package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballIncidentDao;
import com.kok.sport.entity.FootballIncident;
import com.kok.sport.service.FootballIncidentService;
import org.springframework.stereotype.Service;

/**
 * 比赛发生事件表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballIncidentService")
public class FootballIncidentServiceImpl extends ServiceImpl<FootballIncidentDao, FootballIncident> implements FootballIncidentService {

  /**
   * 比赛发生事件表简单分页查询
   * @param footballIncident 比赛发生事件表
   * @return
   */
  @Override
  public IPage<FootballIncident> getFootballIncidentPage(PageVo<FootballIncident> pagevo, FootballIncident footballIncident){
      return baseMapper.getFootballIncidentPage(pagevo,footballIncident);
  }

}
