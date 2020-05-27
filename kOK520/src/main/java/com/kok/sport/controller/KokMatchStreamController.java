package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.utils.Result;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.KokMatchStream;
import com.kok.sport.service.KokMatchStreamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 比赛直播源表
 *
 * @author martin
 * @date 2020-03-12 15:07:30
 */
@RestController
@AllArgsConstructor
@Api(value = "KOK比赛视频直播源",tags = "KOK比赛视频直播源")
public class KokMatchStreamController implements IKokMatchStreamController {

  private final KokMatchStreamService kokMatchStreamService;

  /**
   * 简单分页查询
   * @param pageVo 分页对象
   * @param kokMatchStream 足球比赛文字直播表
   * @return
   */
  @Override
  @ApiOperation("简单分页查询")
  public Result<IPage<KokMatchStream>> getKokMatchStreamPage(PageVo<KokMatchStream> pageVo, KokMatchStream kokMatchStream) throws ApplicationException {
    return new Result<>(kokMatchStreamService.getKokMatchStreamPage(pageVo, kokMatchStream));
  }

  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<KokMatchStream> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(kokMatchStreamService.getById(id));
  }

  /**
   * 新增记录
   * @param kokMatchStream
   * @return R
   */
  @Override
  @ApiOperation("KOK看球比赛新增直播源")
  public Result save(@RequestBody KokMatchStream kokMatchStream) throws ApplicationException {
    return new Result<>(kokMatchStreamService.save(kokMatchStream));
  }

  /**
   * 修改记录
   * @param kokMatchStream
   * @return R
   */
  @Override
  @ApiOperation("KOK看球比赛修改直播源")
  public Result update(@RequestBody KokMatchStream kokMatchStream) throws ApplicationException {
    return new Result<>(kokMatchStreamService.updateById(kokMatchStream));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("KOK看球比赛删除直播源")
  public Result removeById(@PathVariable("id")Long id) throws ApplicationException {
    return new Result<>(kokMatchStreamService.removeKokMatchStream(id));
  }

}
