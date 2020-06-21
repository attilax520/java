package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballTeamPlayer;
import com.kok.sport.service.BasketballTeamPlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球球员表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球球员表",tags = "系统生成 - 篮球球员表")
public class BasketballTeamPlayerController implements IBasketballTeamPlayerController {

  private final  BasketballTeamPlayerService basketballTeamPlayerService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballTeamPlayer 篮球球员表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballTeamPlayer>> getBasketballTeamPlayerPage(PageVo<BasketballTeamPlayer> pagevo, BasketballTeamPlayer basketballTeamPlayer) throws ApplicationException  {
    return  new Result<>(basketballTeamPlayerService.getBasketballTeamPlayerPage(pagevo,basketballTeamPlayer));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballTeamPlayer> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballTeamPlayerService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballTeamPlayer
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballTeamPlayer basketballTeamPlayer) throws ApplicationException {
    return new Result<>(basketballTeamPlayerService.save(basketballTeamPlayer));
  }

  /**
   * 修改记录
   * @param basketballTeamPlayer
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballTeamPlayer basketballTeamPlayer) throws ApplicationException {
    return new Result<>(basketballTeamPlayerService.updateById(basketballTeamPlayer));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballTeamPlayerService.removeById(id));
  }

}
