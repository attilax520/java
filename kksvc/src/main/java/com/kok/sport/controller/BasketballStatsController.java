package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballStats;
import com.kok.sport.service.BasketballStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球比赛统计表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球比赛统计表",tags = "系统生成 - 篮球比赛统计表")
public class BasketballStatsController implements IBasketballStatsController {

  private final  BasketballStatsService basketballStatsService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballStats 篮球比赛统计表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballStats>> getBasketballStatsPage(PageVo<BasketballStats> pagevo, BasketballStats basketballStats) throws ApplicationException  {
    return  new Result<>(basketballStatsService.getBasketballStatsPage(pagevo,basketballStats));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballStats> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballStatsService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballStats
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballStats basketballStats) throws ApplicationException {
    return new Result<>(basketballStatsService.save(basketballStats));
  }

  /**
   * 修改记录
   * @param basketballStats
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballStats basketballStats) throws ApplicationException {
    return new Result<>(basketballStatsService.updateById(basketballStats));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballStatsService.removeById(id));
  }

}
