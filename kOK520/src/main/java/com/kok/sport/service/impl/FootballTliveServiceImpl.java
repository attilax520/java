package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballTliveDao;
import com.kok.sport.entity.FootballTlive;
import com.kok.sport.service.FootballTliveService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛文字直播表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballTliveService")
public class FootballTliveServiceImpl extends ServiceImpl<FootballTliveDao, FootballTlive> implements FootballTliveService {

  /**
   * 足球比赛文字直播表简单分页查询
   * @param footballTlive 足球比赛文字直播表
   * @return
   */
  @Override
  public IPage<FootballTlive> getFootballTlivePage(PageVo<FootballTlive> pagevo, FootballTlive footballTlive){
      return baseMapper.getFootballTlivePage(pagevo,footballTlive);
  }

}
