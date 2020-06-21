package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.SysCountry;
import com.kok.sport.service.SysCountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 国家表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "国家表",tags = "比分项目 - 国家表")
public class SysCountryController implements ISysCountryController {

  private final  SysCountryService sysCountryService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param sysCountry 国家表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<SysCountry>> getSysCountryPage(PageVo<SysCountry> pagevo, SysCountry sysCountry) throws ApplicationException  {
    return  new Result<>(sysCountryService.getSysCountryPage(pagevo,sysCountry));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<SysCountry> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(sysCountryService.getById(id));
  }

  /**
   * 新增记录
   * @param sysCountry
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody SysCountry sysCountry) throws ApplicationException {
    return new Result<>(sysCountryService.save(sysCountry));
  }

  /**
   * 修改记录
   * @param sysCountry
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody SysCountry sysCountry) throws ApplicationException {
    return new Result<>(sysCountryService.updateById(sysCountry));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(sysCountryService.removeById(id));
  }

}
