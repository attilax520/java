package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballScore;
import com.kok.sport.service.BasketballScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球比赛得分表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球比赛得分表",tags = "系统生成 - 篮球比赛得分表")
public class BasketballScoreController implements IBasketballScoreController {

  private final  BasketballScoreService basketballScoreService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballScore 篮球比赛得分表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballScore>> getBasketballScorePage(PageVo<BasketballScore> pagevo, BasketballScore basketballScore) throws ApplicationException  {
    return  new Result<>(basketballScoreService.getBasketballScorePage(pagevo,basketballScore));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballScore> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballScoreService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballScore
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballScore basketballScore) throws ApplicationException {
    return new Result<>(basketballScoreService.save(basketballScore));
  }

  /**
   * 修改记录
   * @param basketballScore
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballScore basketballScore) throws ApplicationException {
    return new Result<>(basketballScoreService.updateById(basketballScore));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballScoreService.removeById(id));
  }

}
