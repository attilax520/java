package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballOddsDao;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.service.FootballOddsService;
import org.springframework.stereotype.Service;

/**
 * 足球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballOddsService")
public class FootballOddsServiceImpl extends ServiceImpl<FootballOddsDao, FootballOdds> implements FootballOddsService {

  /**
   * 足球盘口指数表简单分页查询
   * @param footballOdds 足球盘口指数表
   * @return
   */
  @Override
  public IPage<FootballOdds> getFootballOddsPage(PageVo<FootballOdds> pagevo, FootballOdds footballOdds){
      return baseMapper.getFootballOddsPage(pagevo,footballOdds);
  }

    /**
     * 根据公司id，比赛id，变化时间，查询重复数据
     * @param companyId 公司Id
     * @param matchId 比赛Id
     * @param changeTime  变化时间
     * @return
     */
    @Override
    public FootballOdds findRepeatData(Long companyId, Long matchId, Long changeTime) {
        return baseMapper.findRepeatData(companyId,matchId,changeTime);
    }

}
