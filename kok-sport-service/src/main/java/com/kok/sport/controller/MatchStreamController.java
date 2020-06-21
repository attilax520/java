package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.MatchStream;
import com.kok.sport.integration.SyncStreamListService;
import com.kok.sport.service.MatchStreamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 比赛视频源表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "比赛视频源表",tags = "系统生成 - 比赛视频源表")
public class MatchStreamController implements IMatchStreamController {

  private final  MatchStreamService matchStreamService;

  private final SyncStreamListService syncStreamListService;

  /**
   * 简单分页查询
   * @param pagevo 分页对象
   * @param matchStream 比赛视频源表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<MatchStream>> getMatchStreamPage(PageVo<MatchStream> pagevo, MatchStream matchStream) throws ApplicationException  {
    return  new Result<>(matchStreamService.getMatchStreamPage(pagevo,matchStream));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<MatchStream> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(matchStreamService.getById(id));
  }

  /**
   * 新增记录
   * @param matchStream
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result save(@RequestBody MatchStream matchStream) throws ApplicationException {
    return new Result<>(matchStreamService.save(matchStream));
  }

  /**
   * 修改记录
   * @param matchStream
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result update(@RequestBody MatchStream matchStream) throws ApplicationException {
    return new Result<>(matchStreamService.updateById(matchStream));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(matchStreamService.removeById(id));
  }

  /**
   * 拉取视频源数据
   * @return
   * @throws ApplicationException
   */
  @Override
  @ApiOperation("通过App.Site.Stream_list视频源接口拉取数据")
  public Result insertMatchStream() throws ApplicationException {
    return new Result<>(syncStreamListService.insertVideoApi());
  }
}
