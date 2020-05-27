package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.SysArea;
import org.apache.ibatis.annotations.Param;

/**
 * 区域表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface SysAreaDao extends BaseMapper<SysArea> {
  /**
    * 区域表简单分页查询
    * @param sysArea 区域表
    * @return
    */
  IPage<SysArea> getSysAreaPage(PageVo pagevo, @Param("sysArea") SysArea sysArea);


}
