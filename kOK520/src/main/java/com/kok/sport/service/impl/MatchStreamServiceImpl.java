package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.MatchStreamDao;
import com.kok.sport.entity.MatchStream;
import com.kok.sport.service.MatchStreamService;
import org.springframework.stereotype.Service;

/**
 * 比赛视频源表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("matchStreamService")
public class MatchStreamServiceImpl extends ServiceImpl<MatchStreamDao, MatchStream> implements MatchStreamService {

  /**
   * 比赛视频源表简单分页查询
   * @param matchStream 比赛视频源表
   * @return
   */
  @Override
  public IPage<MatchStream> getMatchStreamPage(PageVo<MatchStream> pagevo, MatchStream matchStream){
      return baseMapper.getMatchStreamPage(pagevo,matchStream);
  }

    /**
     * 根据比赛Id查询视屏源信息
     * @param MatchId
     * @return
     */
    @Override
    public MatchStream findMatchStreamByMatchId(Long MatchId) {
        return baseMapper.findMatchStreamByMatchId(MatchId);
    }

}
