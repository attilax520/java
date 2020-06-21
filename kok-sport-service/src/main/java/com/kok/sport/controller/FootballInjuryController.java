package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballInjury;
import com.kok.sport.integration.SyncFootballMatchAnalysisService;
import com.kok.sport.service.FootballInjuryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛伤停情况表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛伤停情况表",tags = "系统生成 - 足球比赛伤停情况表")
public class FootballInjuryController implements IFootballInjuryController {

  private final  FootballInjuryService footballInjuryService;

  private final SyncFootballMatchAnalysisService syncFootballMatchAnalysisService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballInjury 足球比赛伤停情况表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballInjury>> getFootballInjuryPage(PageVo<FootballInjury> pagevo, FootballInjury footballInjury) throws ApplicationException  {
    return  new Result<>(footballInjuryService.getFootballInjuryPage(pagevo,footballInjury));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballInjury> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballInjuryService.getById(id));
  }

  /**
   * 新增记录
   * @param footballInjury
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballInjury footballInjury) throws ApplicationException {
    return new Result<>(footballInjuryService.save(footballInjury));
  }

  /**
   * 修改记录
   * @param footballInjury
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballInjury footballInjury) throws ApplicationException {
    return new Result<>(footballInjuryService.updateById(footballInjury));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballInjuryService.removeById(id));
  }

  /**
   * 拉取数据
   * 主要
   *         football_injury_t  足球比赛伤停表   injury 伤停情况
   *         football_distribution_t 足球进球分布表   goal_distribution 进球分布
   *         football_league_score_t 足球联赛积分表   table   联赛积分
   *
   *
   * @param Id
   * @return
   * @throws ApplicationException
   */
  @Override
  public Result insertFootballInjury(Long Id) throws ApplicationException {
    return new Result<>(syncFootballMatchAnalysisService.insertFootballInjury(Id));
  }

}
