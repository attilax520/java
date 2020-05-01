package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.BasketballTeamDao;
import com.kok.sport.entity.BasketballTeam;
import com.kok.sport.service.BasketballTeamService;
import com.kok.sport.vo.BasketballTeamVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 篮球球队表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("basketballTeamService")
public class BasketballTeamServiceImpl extends ServiceImpl<BasketballTeamDao, BasketballTeam> implements BasketballTeamService {

  /**
   * 篮球球队表简单分页查询
   * @param basketballTeam 篮球球队表
   * @return
   */
  @Override
  public IPage<BasketballTeam> getBasketballTeamPage(PageVo<BasketballTeam> pagevo, BasketballTeam basketballTeam){
      return baseMapper.getBasketballTeamPage(pagevo,basketballTeam);
  }

    @Override
    public int saveBasketballTeam(List<BasketballTeamVO> teamList) {
        return baseMapper.saveBasketballTeam(teamList);
    }

    @Override
    public int deleteBasketballTeam(List<Long> teamList) {
        return baseMapper.deleteBasketballTeam(teamList);
    }

}
