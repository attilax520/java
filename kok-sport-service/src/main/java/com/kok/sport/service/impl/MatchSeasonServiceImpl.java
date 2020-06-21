package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.MatchSeasonDao;
import com.kok.sport.entity.MatchSeason;
import com.kok.sport.service.MatchSeasonService;
import org.springframework.stereotype.Service;

/**
 * 赛季信息表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("matchSeasonService")
public class MatchSeasonServiceImpl extends ServiceImpl<MatchSeasonDao, MatchSeason> implements MatchSeasonService {

  /**
   * 赛季信息表简单分页查询
   * @param matchSeason 赛季信息表
   * @return
   */
  @Override
  public IPage<MatchSeason> getMatchSeasonPage(PageVo<MatchSeason> pagevo, MatchSeason matchSeason){
      return baseMapper.getMatchSeasonPage(pagevo,matchSeason);
  }

}
