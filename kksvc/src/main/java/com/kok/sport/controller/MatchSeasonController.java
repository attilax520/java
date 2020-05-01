package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.MatchSeason;
import com.kok.sport.service.MatchSeasonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 赛季信息表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "赛季信息表",tags = "系统生成 - 赛季信息表")
public class MatchSeasonController implements IMatchSeasonController {

  private final  MatchSeasonService matchSeasonService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param matchSeason 赛季信息表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<MatchSeason>> getMatchSeasonPage(PageVo<MatchSeason> pagevo, MatchSeason matchSeason) throws ApplicationException  {
    return  new Result<>(matchSeasonService.getMatchSeasonPage(pagevo,matchSeason));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<MatchSeason> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(matchSeasonService.getById(id));
  }

  /**
   * 新增记录
   * @param matchSeason
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody MatchSeason matchSeason) throws ApplicationException {
    return new Result<>(matchSeasonService.save(matchSeason));
  }

  /**
   * 修改记录
   * @param matchSeason
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody MatchSeason matchSeason) throws ApplicationException {
    return new Result<>(matchSeasonService.updateById(matchSeason));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(matchSeasonService.removeById(id));
  }

}
