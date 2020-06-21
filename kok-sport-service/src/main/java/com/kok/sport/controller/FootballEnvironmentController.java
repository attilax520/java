package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballEnvironment;
import com.kok.sport.integration.SyncFootballAnalysisMatchDetailService;
import com.kok.sport.service.FootballEnvironmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛环境表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛环境表",tags = "系统生成 - 足球比赛环境表")
public class FootballEnvironmentController implements IFootballEnvironmentController {

  private final  FootballEnvironmentService footballEnvironmentService;

  private final SyncFootballAnalysisMatchDetailService syncFootballAnalysisMatchDetailService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballEnvironment 足球比赛环境表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballEnvironment>> getFootballEnvironmentPage(PageVo<FootballEnvironment> pagevo, FootballEnvironment footballEnvironment) throws ApplicationException  {
    return  new Result<>(footballEnvironmentService.getFootballEnvironmentPage(pagevo,footballEnvironment));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballEnvironment> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballEnvironmentService.getById(id));
  }

  /**
   * 新增记录
   * @param footballEnvironment
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballEnvironment footballEnvironment) throws ApplicationException {
    return new Result<>(footballEnvironmentService.save(footballEnvironment));
  }

  /**
   * 修改记录
   * @param footballEnvironment
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballEnvironment footballEnvironment) throws ApplicationException {
    return new Result<>(footballEnvironmentService.updateById(footballEnvironment));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballEnvironmentService.removeById(id));
  }

  /**
   * 拉取足球比赛环境数据
   * @param Id
   * @return
   * @throws ApplicationException
   */
  @Override
  public Result insertFootballAnalysisApiData(@RequestParam String Id) throws ApplicationException {
    return new Result<>(syncFootballAnalysisMatchDetailService.insertFootballAnalysisApiData(Id));
  }

}
