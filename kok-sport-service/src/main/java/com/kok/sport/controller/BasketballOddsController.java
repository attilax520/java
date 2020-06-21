package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballOdds;
import com.kok.sport.service.BasketballOddsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球盘口指数表",tags = "系统生成 - 篮球盘口指数表")
public class BasketballOddsController implements IBasketballOddsController {

  private final  BasketballOddsService basketballOddsService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballOdds 篮球盘口指数表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballOdds>> getBasketballOddsPage(PageVo<BasketballOdds> pagevo, BasketballOdds basketballOdds) throws ApplicationException  {
    return  new Result<>(basketballOddsService.getBasketballOddsPage(pagevo,basketballOdds));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballOdds> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballOddsService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballOdds
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballOdds basketballOdds) throws ApplicationException {
    return new Result<>(basketballOddsService.save(basketballOdds));
  }

  /**
   * 修改记录
   * @param basketballOdds
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballOdds basketballOdds) throws ApplicationException {
    return new Result<>(basketballOddsService.updateById(basketballOdds));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballOddsService.removeById(id));
  }

}
