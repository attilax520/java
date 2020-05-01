package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.integration.SyncFootballNumberOddsHistoryService;
import com.kok.sport.integration.SyncFootballNumberOddsLiveHistoryService;
import com.kok.sport.service.FootballOddsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球盘口指数表",tags = "系统生成 - 足球盘口指数表")
public class FootballOddsController implements IFootballOddsController {

  private final  FootballOddsService footballOddsService;

  private final SyncFootballNumberOddsHistoryService syncFootballNumberOddsHistoryService;

  private final SyncFootballNumberOddsLiveHistoryService syncFootballNumberOddsLiveHistoryService;
  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballOdds 足球盘口指数表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballOdds>> getFootballOddsPage(PageVo<FootballOdds> pagevo, FootballOdds footballOdds) throws ApplicationException  {
    return  new Result<>(footballOddsService.getFootballOddsPage(pagevo,footballOdds));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballOdds> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballOddsService.getById(id));
  }

  /**
   * 新增记录
   * @param footballOdds
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballOdds footballOdds) throws ApplicationException {
    return new Result<>(footballOddsService.save(footballOdds));
  }

  /**
   * 修改记录
   * @param footballOdds
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballOdds footballOdds) throws ApplicationException {
    return new Result<>(footballOddsService.updateById(footballOdds));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballOddsService.removeById(id));
  }

  /**
   * 拉取单场指数数据
   * @param Id
   * @return
   * @throws ApplicationException
   */
  @Override
  @ApiOperation("拉取足球单场指数数据")
  public Result insertFootballNumberOddsData(Long Id) throws ApplicationException {
    return new Result<>(syncFootballNumberOddsHistoryService.insertFootballNumberOddsData(Id));
  }

  /**
   * 拉取足球实时指数数据
   * @return
   * @throws ApplicationException
   */
  @Override
  @ApiOperation("拉取足球实时指数数据")
  public Result insertFootballLiveOdds() throws ApplicationException {
    return new Result<>(syncFootballNumberOddsLiveHistoryService.insertFootballLiveOdds());
  }

}
