package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballTlive;
import com.kok.sport.service.BasketballTliveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 篮球比赛文字直播表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球比赛文字直播表",tags = "系统生成 - 篮球比赛文字直播表")
public class BasketballTliveController implements IBasketballTliveController {

  private final  BasketballTliveService basketballTliveService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param basketballTlive 篮球比赛文字直播表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<BasketballTlive>> getBasketballTlivePage(PageVo<BasketballTlive> pagevo, BasketballTlive basketballTlive) throws ApplicationException  {
    return  new Result<>(basketballTliveService.getBasketballTlivePage(pagevo,basketballTlive));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<BasketballTlive> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(basketballTliveService.getById(id));
  }

  /**
   * 新增记录
   * @param basketballTlive
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody BasketballTlive basketballTlive) throws ApplicationException {
    return new Result<>(basketballTliveService.save(basketballTlive));
  }

  /**
   * 修改记录
   * @param basketballTlive
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody BasketballTlive basketballTlive) throws ApplicationException {
    return new Result<>(basketballTliveService.updateById(basketballTlive));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(basketballTliveService.removeById(id));
  }

}
