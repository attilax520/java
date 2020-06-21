package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballPlayerIncidentDao;
import com.kok.sport.entity.FootballPlayerIncident;
import com.kok.sport.service.FootballPlayerIncidentService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛球员事件表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballPlayerIncidentService")
public class FootballPlayerIncidentServiceImpl extends ServiceImpl<FootballPlayerIncidentDao, FootballPlayerIncident> implements FootballPlayerIncidentService {

  /**
   * 足球比赛球员事件表简单分页查询
   * @param footballPlayerIncident 足球比赛球员事件表
   * @return
   */
  @Override
  public IPage<FootballPlayerIncident> getFootballPlayerIncidentPage(PageVo<FootballPlayerIncident> pagevo, FootballPlayerIncident footballPlayerIncident){
      return baseMapper.getFootballPlayerIncidentPage(pagevo,footballPlayerIncident);
  }

}
