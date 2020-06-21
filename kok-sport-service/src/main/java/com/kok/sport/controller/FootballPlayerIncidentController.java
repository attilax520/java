package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballPlayerIncident;
import com.kok.sport.service.FootballPlayerIncidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛球员事件表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛球员事件表",tags = "系统生成 - 足球比赛球员事件表")
public class FootballPlayerIncidentController implements IFootballPlayerIncidentController {

  private final  FootballPlayerIncidentService footballPlayerIncidentService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballPlayerIncident 足球比赛球员事件表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballPlayerIncident>> getFootballPlayerIncidentPage(PageVo<FootballPlayerIncident> pagevo, FootballPlayerIncident footballPlayerIncident) throws ApplicationException  {
    return  new Result<>(footballPlayerIncidentService.getFootballPlayerIncidentPage(pagevo,footballPlayerIncident));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballPlayerIncident> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballPlayerIncidentService.getById(id));
  }

  /**
   * 新增记录
   * @param footballPlayerIncident
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballPlayerIncident footballPlayerIncident) throws ApplicationException {
    return new Result<>(footballPlayerIncidentService.save(footballPlayerIncident));
  }

  /**
   * 修改记录
   * @param footballPlayerIncident
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballPlayerIncident footballPlayerIncident) throws ApplicationException {
    return new Result<>(footballPlayerIncidentService.updateById(footballPlayerIncident));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballPlayerIncidentService.removeById(id));
  }

}
