package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballMatch;
import com.kok.sport.service.FootballMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛表",tags = "系统生成 - 足球比赛表")
public class FootballMatchController implements IFootballMatchController {

  private final  FootballMatchService footballMatchService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballMatch 足球比赛表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballMatch>> getFootballMatchPage(PageVo<FootballMatch> pagevo, FootballMatch footballMatch) throws ApplicationException  {
    return  new Result<>(footballMatchService.getFootballMatchPage(pagevo,footballMatch));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballMatch> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballMatchService.getById(id));
  }

  /**
   * 新增记录
   * @param footballMatch
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballMatch footballMatch) throws ApplicationException {
    return new Result<>(footballMatchService.save(footballMatch));
  }

  /**
   * 修改记录
   * @param footballMatch
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballMatch footballMatch) throws ApplicationException {
    return new Result<>(footballMatchService.updateById(footballMatch));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballMatchService.removeById(id));
  }

}
