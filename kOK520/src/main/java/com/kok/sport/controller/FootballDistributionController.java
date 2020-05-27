package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballDistribution;
import com.kok.sport.integration.SyncFootballMatchAnalysisService;
import com.kok.sport.service.FootballDistributionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球进球分布表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球进球分布表",tags = "系统生成 - 足球进球分布表")
public class FootballDistributionController implements IFootballDistributionController {

  private final  FootballDistributionService footballDistributionService;

  private final SyncFootballMatchAnalysisService syncFootballMatchAnalysisService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballDistribution 足球进球分布表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballDistribution>> getFootballDistributionPage(PageVo<FootballDistribution> pagevo, FootballDistribution footballDistribution) throws ApplicationException  {
    return  new Result<>(footballDistributionService.getFootballDistributionPage(pagevo,footballDistribution));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballDistribution> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballDistributionService.getById(id));
  }

  /**
   * 新增记录
   * @param footballDistribution
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballDistribution footballDistribution) throws ApplicationException {
    return new Result<>(footballDistributionService.save(footballDistribution));
  }

  /**
   * 修改记录
   * @param footballDistribution
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballDistribution footballDistribution) throws ApplicationException {
    return new Result<>(footballDistributionService.updateById(footballDistribution));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballDistributionService.removeById(id));
  }

  /**
   * 拉取足球比赛进球分布数据
   * @param Id
   * @return
   * @throws ApplicationException
   */
  @Override
  public Result insertFootballDistribution(Long Id) throws ApplicationException {
    return new Result<>(syncFootballMatchAnalysisService.insertFootballDistribution(Id));
  }

}
