package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballStageDao;
import com.kok.sport.entity.FootballStage;
import com.kok.sport.service.FootballStageService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛阶段表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballStageService")
public class FootballStageServiceImpl extends ServiceImpl<FootballStageDao, FootballStage> implements FootballStageService {

  /**
   * 足球比赛阶段表简单分页查询
   * @param footballStage 足球比赛阶段表
   * @return
   */
  @Override
  public IPage<FootballStage> getFootballStagePage(PageVo<FootballStage> pagevo, FootballStage footballStage){
      return baseMapper.getFootballStagePage(pagevo,footballStage);
  }

}
