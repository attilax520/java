package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballEvent;
import com.kok.sport.integration.SyncFootballMatchEventListService;
import com.kok.sport.service.FootballEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球赛事表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球赛事表",tags = "系统生成 - 足球赛事表")
public class FootballEventController implements IFootballEventController {

  private final  FootballEventService footballEventService;

  private final SyncFootballMatchEventListService syncFootballMatchEventListService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballEvent 足球赛事表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballEvent>> getFootballEventPage(PageVo<FootballEvent> pagevo, FootballEvent footballEvent) throws ApplicationException  {
    return  new Result<>(footballEventService.getFootballEventPage(pagevo,footballEvent));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballEvent> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballEventService.getById(id));
  }

  /**
   * 新增记录
   * @param footballEvent
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballEvent footballEvent) throws ApplicationException {
    return new Result<>(footballEventService.save(footballEvent));
  }

  /**
   * 修改记录
   * @param footballEvent
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballEvent footballEvent) throws ApplicationException {
    return new Result<>(footballEventService.updateById(footballEvent));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballEventService.removeById(id));
  }

  /**
   * 拉取足球赛事数据
   * @return
   * @throws ApplicationException
   */
  @Override
  public Result insertMarchEvent() throws ApplicationException {
    return new Result<>(syncFootballMatchEventListService.insertMarchEvent());
  }

  /**
   * 拉取足球删除比赛数据
   * @return
   * @throws ApplicationException
   */
  @Override
  public Result insertDeleteMarchEvent() throws ApplicationException {
    return new Result<>(syncFootballMatchEventListService.insertDeleteMarchEvent());
  }

}
