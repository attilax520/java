package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballLeagueScore;
import com.kok.sport.integration.SyncFootballMatchAnalysisService;
import com.kok.sport.service.FootballLeagueScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球联赛积分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球联赛积分表",tags = "系统生成 - 足球联赛积分表")
public class FootballLeagueScoreController implements IFootballLeagueScoreController {

  private final  FootballLeagueScoreService footballLeagueScoreService;

  private final SyncFootballMatchAnalysisService syncFootballMatchAnalysisService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballLeagueScore 足球联赛积分表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballLeagueScore>> getFootballLeagueScorePage(PageVo<FootballLeagueScore> pagevo, FootballLeagueScore footballLeagueScore) throws ApplicationException  {
    return  new Result<>(footballLeagueScoreService.getFootballLeagueScorePage(pagevo,footballLeagueScore));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballLeagueScore> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballLeagueScoreService.getById(id));
  }

  /**
   * 新增记录
   * @param footballLeagueScore
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballLeagueScore footballLeagueScore) throws ApplicationException {
    return new Result<>(footballLeagueScoreService.save(footballLeagueScore));
  }

  /**
   * 修改记录
   * @param footballLeagueScore
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballLeagueScore footballLeagueScore) throws ApplicationException {
    return new Result<>(footballLeagueScoreService.updateById(footballLeagueScore));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballLeagueScoreService.removeById(id));
  }

  /**
   * 拉取足球联赛积分数据
   * @param Id
   * @return
   * @throws ApplicationException
   */
  @Override
  public Result insertFootballLeagueScore(Long Id) throws ApplicationException {
    return new Result<>(syncFootballMatchAnalysisService.insertFootballLeagueScore(Id));
  }

}
