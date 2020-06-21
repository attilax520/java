package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballScore;
import com.kok.sport.service.FootballScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球比赛得分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球比赛得分表",tags = "系统生成 - 足球比赛得分表")
public class FootballScoreController implements IFootballScoreController {

  private final  FootballScoreService footballScoreService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param footballScore 足球比赛得分表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<FootballScore>> getFootballScorePage(PageVo<FootballScore> pagevo, FootballScore footballScore) throws ApplicationException  {
    return  new Result<>(footballScoreService.getFootballScorePage(pagevo,footballScore));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<FootballScore> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(footballScoreService.getById(id));
  }

  /**
   * 新增记录
   * @param footballScore
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody FootballScore footballScore) throws ApplicationException {
    return new Result<>(footballScoreService.save(footballScore));
  }

  /**
   * 修改记录
   * @param footballScore
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody FootballScore footballScore) throws ApplicationException {
    return new Result<>(footballScoreService.updateById(footballScore));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(footballScoreService.removeById(id));
  }

}
