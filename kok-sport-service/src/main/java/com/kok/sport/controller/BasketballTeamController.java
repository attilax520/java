package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballTeam;
import com.kok.sport.service.BasketballTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球球队表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球球队表",tags = "系统生成 - 篮球球队表")
public class BasketballTeamController implements IBasketballTeamController {

  private final  BasketballTeamService basketballTeamService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballTeam 篮球球队表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballTeam>> getBasketballTeamPage(PageVo<BasketballTeam> pagevo, BasketballTeam basketballTeam) throws ApplicationException  {
    return  new Result<>(basketballTeamService.getBasketballTeamPage(pagevo,basketballTeam));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballTeam> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballTeamService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballTeam
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballTeam basketballTeam) throws ApplicationException {
    return new Result<>(basketballTeamService.save(basketballTeam));
  }

  /**
   * 修改记录
   * @param basketballTeam
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballTeam basketballTeam) throws ApplicationException {
    return new Result<>(basketballTeamService.updateById(basketballTeam));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballTeamService.removeById(id));
  }

}
