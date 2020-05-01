package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballStats;
import com.kok.sport.service.FootballStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛统计表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛统计表",tags = "系统生成 - 足球比赛统计表")
public class FootballStatsController implements IFootballStatsController {

  private final  FootballStatsService footballStatsService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballStats 足球比赛统计表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballStats>> getFootballStatsPage(PageVo<FootballStats> pagevo, FootballStats footballStats) throws ApplicationException  {
    return  new Result<>(footballStatsService.getFootballStatsPage(pagevo,footballStats));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballStats> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballStatsService.getById(id));
  }

  /**
   * 新增记录
   * @param footballStats
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballStats footballStats) throws ApplicationException {
    return new Result<>(footballStatsService.save(footballStats));
  }

  /**
   * 修改记录
   * @param footballStats
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballStats footballStats) throws ApplicationException {
    return new Result<>(footballStatsService.updateById(footballStats));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballStatsService.removeById(id));
  }

}
