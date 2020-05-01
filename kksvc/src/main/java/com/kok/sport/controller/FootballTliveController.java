package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballTlive;
import com.kok.sport.service.FootballTliveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛文字直播表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛文字直播表",tags = "系统生成 - 足球比赛文字直播表")
public class FootballTliveController implements IFootballTliveController {

  private final  FootballTliveService footballTliveService;

  /**
   * 简单分页查询
   * @param pageVo 分页对象
   * @param footballTlive 足球比赛文字直播表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballTlive>> getFootballTlivePage(PageVo<FootballTlive> pageVo, FootballTlive footballTlive) throws ApplicationException  {
    return  new Result<>(footballTliveService.getFootballTlivePage(pageVo,footballTlive));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballTlive> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballTliveService.getById(id));
  }

  /**
   * 新增记录
   * @param footballTlive
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballTlive footballTlive) throws ApplicationException {
    return new Result<>(footballTliveService.save(footballTlive));
  }

  /**
   * 修改记录
   * @param footballTlive
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballTlive footballTlive) throws ApplicationException {
    return new Result<>(footballTliveService.updateById(footballTlive));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballTliveService.removeById(id));
  }

}
