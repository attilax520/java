package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballTeam;
import com.kok.sport.service.FootballTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球球队表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球球队表",tags = "系统生成 - 足球球队表")
public class FootballTeamController implements IFootballTeamController {

  private final  FootballTeamService footballTeamService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballTeam 足球球队表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballTeam>> getFootballTeamPage(PageVo<FootballTeam> pagevo, FootballTeam footballTeam) throws ApplicationException  {
    return  new Result<>(footballTeamService.getFootballTeamPage(pagevo,footballTeam));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballTeam> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballTeamService.getById(id));
  }

  /**
   * 新增记录
   * @param footballTeam
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballTeam footballTeam) throws ApplicationException {
    return new Result<>(footballTeamService.save(footballTeam));
  }

  /**
   * 修改记录
   * @param footballTeam
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballTeam footballTeam) throws ApplicationException {
    return new Result<>(footballTeamService.updateById(footballTeam));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballTeamService.removeById(id));
  }

}
