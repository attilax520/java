package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.BasketballTliveDao;
import com.kok.sport.entity.BasketballTlive;
import com.kok.sport.service.BasketballTliveService;
import org.springframework.stereotype.Service;

/**
 * 篮球比赛文字直播表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("basketballTliveService")
public class BasketballTliveServiceImpl extends ServiceImpl<BasketballTliveDao, BasketballTlive> implements BasketballTliveService {

  /**
   * 篮球比赛文字直播表简单分页查询
   * @param basketballTlive 篮球比赛文字直播表
   * @return
   */
  @Override
  public IPage<BasketballTlive> getBasketballTlivePage(PageVo<BasketballTlive> pagevo, BasketballTlive basketballTlive){
      return baseMapper.getBasketballTlivePage(pagevo,basketballTlive);
  }

}
