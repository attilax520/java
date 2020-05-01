package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballIncident;
import com.kok.sport.service.FootballIncidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 比赛发生事件表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "比赛发生事件表",tags = "系统生成 - 比赛发生事件表")
public class FootballIncidentController implements IFootballIncidentController {

  private final  FootballIncidentService footballIncidentService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballIncident 比赛发生事件表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballIncident>> getFootballIncidentPage(PageVo<FootballIncident> pagevo, FootballIncident footballIncident) throws ApplicationException  {
    return  new Result<>(footballIncidentService.getFootballIncidentPage(pagevo,footballIncident));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballIncident> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballIncidentService.getById(id));
  }

  /**
   * 新增记录
   * @param footballIncident
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballIncident footballIncident) throws ApplicationException {
    return new Result<>(footballIncidentService.save(footballIncident));
  }

  /**
   * 修改记录
   * @param footballIncident
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballIncident footballIncident) throws ApplicationException {
    return new Result<>(footballIncidentService.updateById(footballIncident));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballIncidentService.removeById(id));
  }

}
