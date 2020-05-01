package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.SysCountryDao;
import com.kok.sport.entity.SysCountry;
import com.kok.sport.service.SysCountryService;
import org.springframework.stereotype.Service;

/**
 * 国家表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("sysCountryService")
public class SysCountryServiceImpl extends ServiceImpl<SysCountryDao, SysCountry> implements SysCountryService {

  /**
   * 国家表简单分页查询
   * @param sysCountry 国家表
   * @return
   */
  @Override
  public IPage<SysCountry> getSysCountryPage(PageVo<SysCountry> pagevo, SysCountry sysCountry){
      return baseMapper.getSysCountryPage(pagevo,sysCountry);
  }

}
