package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballEnvironmentDao;
import com.kok.sport.entity.FootballEnvironment;
import com.kok.sport.service.FootballEnvironmentService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛环境表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballEnvironmentService")
public class FootballEnvironmentServiceImpl extends ServiceImpl<FootballEnvironmentDao, FootballEnvironment> implements FootballEnvironmentService {

  /**
   * 足球比赛环境表简单分页查询
   * @param footballEnvironment 足球比赛环境表
   * @return
   */
  @Override
  public IPage<FootballEnvironment> getFootballEnvironmentPage(PageVo<FootballEnvironment> pagevo, FootballEnvironment footballEnvironment){
      return baseMapper.getFootballEnvironmentPage(pagevo,footballEnvironment);
  }

}
