package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballStage;
import com.kok.sport.service.BasketballStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球比赛阶段表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球比赛阶段表",tags = "系统生成 - 篮球比赛阶段表")
public class BasketballStageController implements IBasketballStageController {

  private final  BasketballStageService basketballStageService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballStage 篮球比赛阶段表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballStage>> getBasketballStagePage(PageVo<BasketballStage> pagevo, BasketballStage basketballStage) throws ApplicationException  {
    return  new Result<>(basketballStageService.getBasketballStagePage(pagevo,basketballStage));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballStage> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballStageService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballStage
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballStage basketballStage) throws ApplicationException {
    return new Result<>(basketballStageService.save(basketballStage));
  }

  /**
   * 修改记录
   * @param basketballStage
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballStage basketballStage) throws ApplicationException {
    return new Result<>(basketballStageService.updateById(basketballStage));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballStageService.removeById(id));
  }

}
