package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.utils.Result;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.SysArea;
import com.kok.sport.service.SysAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 区域表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "区域表",tags = "比分项目 - 区域表")
public class SysAreaController implements ISysAreaController {

  private final  SysAreaService sysAreaService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param sysArea 区域表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<SysArea>> getSysAreaPage(PageVo<SysArea> pagevo, SysArea sysArea) throws ApplicationException  {
    return  new Result<>(sysAreaService.getSysAreaPage(pagevo,sysArea));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<SysArea> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(sysAreaService.getById(id));
  }

  /**
   * 新增记录
   * @param sysArea
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody SysArea sysArea) throws ApplicationException {
    return new Result<>(sysAreaService.save(sysArea));
  }

  /**
   * 修改记录
   * @param sysArea
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody SysArea sysArea) throws ApplicationException {
    return new Result<>(sysAreaService.updateById(sysArea));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(sysAreaService.removeById(id));
  }

}
