package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballEvent;
import com.kok.sport.service.BasketballEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球赛事表
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球赛事表",tags = "系统生成 - 篮球赛事表")
public class BasketballEventController implements IBasketballEventController {

  private final  BasketballEventService basketballEventService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballEvent 篮球赛事表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballEvent>> getBasketballEventPage(PageVo<BasketballEvent> pagevo, BasketballEvent basketballEvent) throws ApplicationException  {
    return  new Result<>(basketballEventService.getBasketballEventPage(pagevo,basketballEvent));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballEvent> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballEventService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballEvent
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballEvent basketballEvent) throws ApplicationException {
    return new Result<>(basketballEventService.save(basketballEvent));
  }

  /**
   * 修改记录
   * @param basketballEvent
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballEvent basketballEvent) throws ApplicationException {
    return new Result<>(basketballEventService.updateById(basketballEvent));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballEventService.removeById(id));
  }

}
