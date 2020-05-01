package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballTeamPlayer;
import com.kok.sport.service.FootballTeamPlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球球队球员表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球球队球员表",tags = "系统生成 - 足球球队球员表")
public class FootballTeamPlayerController implements IFootballTeamPlayerController {

  private final  FootballTeamPlayerService footballTeamPlayerService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballTeamPlayer 足球球队球员表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballTeamPlayer>> getFootballTeamPlayerPage(PageVo<FootballTeamPlayer> pagevo, FootballTeamPlayer footballTeamPlayer) throws ApplicationException  {
    return  new Result<>(footballTeamPlayerService.getFootballTeamPlayerPage(pagevo,footballTeamPlayer));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballTeamPlayer> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballTeamPlayerService.getById(id));
  }

  /**
   * 新增记录
   * @param footballTeamPlayer
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballTeamPlayer footballTeamPlayer) throws ApplicationException {
    return new Result<>(footballTeamPlayerService.save(footballTeamPlayer));
  }

  /**
   * 修改记录
   * @param footballTeamPlayer
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballTeamPlayer footballTeamPlayer) throws ApplicationException {
    return new Result<>(footballTeamPlayerService.updateById(footballTeamPlayer));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballTeamPlayerService.removeById(id));
  }

}
