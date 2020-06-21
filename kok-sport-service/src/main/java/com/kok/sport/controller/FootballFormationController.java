package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballFormation;
import com.kok.sport.integration.SyncFootballMatchLineupService;
import com.kok.sport.service.FootballFormationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛阵型表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛阵型表",tags = "系统生成 - 足球比赛阵型表")
public class FootballFormationController implements IFootballFormationController {

  private final  FootballFormationService footballFormationService;

  private final SyncFootballMatchLineupService syncFootballMatchLineupService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballFormation 足球比赛阵型表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballFormation>> getFootballFormationPage(PageVo<FootballFormation> pagevo, FootballFormation footballFormation) throws ApplicationException  {
    return  new Result<>(footballFormationService.getFootballFormationPage(pagevo,footballFormation));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballFormation> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballFormationService.getById(id));
  }

  /**
   * 新增记录
   * @param footballFormation
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballFormation footballFormation) throws ApplicationException {
    return new Result<>(footballFormationService.save(footballFormation));
  }

  /**
   * 修改记录
   * @param footballFormation
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballFormation footballFormation) throws ApplicationException {
    return new Result<>(footballFormationService.updateById(footballFormation));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballFormationService.removeById(id));
  }

  /**
   * 拉取足球比赛整容数据
   * @param Id
   * @return
   * @throws ApplicationException
   */
  @Override
  @ApiOperation("拉取足球比赛阵容数据")
  public Result insertMatchLineupData(String Id) throws ApplicationException {
    return new Result<>(syncFootballMatchLineupService.insertMatchLineupData(Id));
  }

}
