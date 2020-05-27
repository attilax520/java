package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.SysCountry;
import org.apache.ibatis.annotations.Param;

/**
 * 国家表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface SysCountryDao extends BaseMapper<SysCountry> {
  /**
    * 国家表简单分页查询
    * @param sysCountry 国家表
    * @return
    */
  IPage<SysCountry> getSysCountryPage(PageVo pagevo, @Param("sysCountry") SysCountry sysCountry);


}
