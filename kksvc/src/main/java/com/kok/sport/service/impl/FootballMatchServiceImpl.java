package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.FootballMatchDao;
import com.kok.sport.entity.FootballMatch;
import com.kok.sport.service.FootballMatchService;
import org.springframework.stereotype.Service;

/**
 * 足球比赛表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("footballMatchService")
public class FootballMatchServiceImpl extends ServiceImpl<FootballMatchDao, FootballMatch> implements FootballMatchService {


  /**
   * 足球比赛表简单分页查询
   * @param footballMatch 足球比赛表
   * @return
   */
  @Override
  public IPage<FootballMatch> getFootballMatchPage(PageVo<FootballMatch> pagevo, FootballMatch footballMatch){
      return baseMapper.getFootballMatchPage(pagevo,footballMatch);
  }

    @Override
    public boolean updateDeleteFlagById(Integer Id) {
        return baseMapper.updateDeleteFlagById(Id)>0;
    }

}
