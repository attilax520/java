package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballMatch;
import com.kok.sport.service.BasketballMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球比赛表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球比赛表",tags = "系统生成 - 篮球比赛表")
public class BasketballMatchController implements IBasketballMatchController {

  private final  BasketballMatchService basketballMatchService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballMatch 篮球比赛表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballMatch>> getBasketballMatchPage(PageVo<BasketballMatch> pagevo, BasketballMatch basketballMatch) throws ApplicationException  {
    return  new Result<>(basketballMatchService.getBasketballMatchPage(pagevo,basketballMatch));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballMatch> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballMatchService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballMatch
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballMatch basketballMatch) throws ApplicationException {
    return new Result<>(basketballMatchService.save(basketballMatch));
  }

  /**
   * 修改记录
   * @param basketballMatch
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballMatch basketballMatch) throws ApplicationException {
    return new Result<>(basketballMatchService.updateById(basketballMatch));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballMatchService.removeById(id));
  }

}
