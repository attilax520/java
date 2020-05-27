package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballStage;
import com.kok.sport.service.FootballStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛阶段表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛阶段表",tags = "系统生成 - 足球比赛阶段表")
public class FootballStageController implements IFootballStageController {

  private final  FootballStageService footballStageService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballStage 足球比赛阶段表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballStage>> getFootballStagePage(PageVo<FootballStage> pagevo, FootballStage footballStage) throws ApplicationException  {
    return  new Result<>(footballStageService.getFootballStagePage(pagevo,footballStage));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballStage> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballStageService.getById(id));
  }

  /**
   * 新增记录
   * @param footballStage
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballStage footballStage) throws ApplicationException {
    return new Result<>(footballStageService.save(footballStage));
  }

  /**
   * 修改记录
   * @param footballStage
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballStage footballStage) throws ApplicationException {
    return new Result<>(footballStageService.updateById(footballStage));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballStageService.removeById(id));
  }

}
