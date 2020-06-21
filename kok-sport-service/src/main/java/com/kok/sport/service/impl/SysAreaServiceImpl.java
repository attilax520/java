package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.SysAreaDao;
import com.kok.sport.entity.SysArea;
import com.kok.sport.service.SysAreaService;
import org.springframework.stereotype.Service;

/**
 * 区域表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@Service("sysAreaService")
public class SysAreaServiceImpl extends ServiceImpl<SysAreaDao, SysArea> implements SysAreaService {

  /**
   * 区域表简单分页查询
   * @param sysArea 区域表
   * @return
   */
  @Override
  public IPage<SysArea> getSysAreaPage(PageVo<SysArea> pagevo, SysArea sysArea){
      return baseMapper.getSysAreaPage(pagevo,sysArea);
  }

}
