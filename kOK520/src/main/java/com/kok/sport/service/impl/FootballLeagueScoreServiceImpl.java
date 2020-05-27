package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballLeagueScoreDao;
import com.kok.sport.entity.FootballLeagueScore;
import com.kok.sport.service.FootballLeagueScoreService;
import org.springframework.stereotype.Service;

/**
 * 足球联赛积分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballLeagueScoreService")
public class FootballLeagueScoreServiceImpl extends ServiceImpl<FootballLeagueScoreDao, FootballLeagueScore> implements FootballLeagueScoreService {

  /**
   * 足球联赛积分表简单分页查询
   * @param footballLeagueScore 足球联赛积分表
   * @return
   */
  @Override
  public IPage<FootballLeagueScore> getFootballLeagueScorePage(PageVo<FootballLeagueScore> pagevo, FootballLeagueScore footballLeagueScore){
      return baseMapper.getFootballLeagueScorePage(pagevo,footballLeagueScore);
  }

    /**
     * 根据数据类型和球队ID 去除重复数据
     * @param dataType
     * @param teamId
     * @return
     */
    @Override
    public FootballLeagueScore findTypeAndTeamId(String dataType, Long teamId) {
        return baseMapper.findTypeAndTeamId(dataType,teamId);
    }

}
