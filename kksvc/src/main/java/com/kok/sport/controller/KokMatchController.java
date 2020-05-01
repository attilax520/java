package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.utils.Result;
import com.kok.sport.dto.KokMatchDto;
import com.kok.sport.entity.KokMatch;
import com.kok.sport.service.KokMatchService;
import com.kok.sport.vo.KokMatchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * KOK看球比赛
 * @author matin
 * @date 2020-03-11 15:07:30
 */
@RestController
@AllArgsConstructor
@Api(value = "KOK看球比赛查询",tags = "KOK看球比赛查询")
public class KokMatchController implements IKokMatchController {

  private final KokMatchService kokMatchService;

  /**
   * 简单分页查询
   * @param kokMatchDto 足球比赛查询对象
   * @return
   */
  @Override
  @ApiOperation("KOK看球比赛分页查询")
  public Result<IPage<KokMatchVO>> getKokMatchPage(KokMatchDto kokMatchDto) throws ApplicationException  {
    return new Result<>(kokMatchService.getKokMatchPage(kokMatchDto));
  }

  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("通过id查询单条记录")
  public Result<KokMatch> getById(@PathVariable("id") Long id) throws ApplicationException {
    return new Result<>(kokMatchService.getById(id));
  }

  /**
   * 新增记录
   * @param kokMatch
   * @return R
   */
  @Override
  @ApiOperation("新增KOK看球比赛信息")
  public Result save(@RequestBody KokMatch kokMatch) throws ApplicationException {
    return new Result<>(kokMatchService.save(kokMatch));
  }

  /**
   * 修改记录
   * @param kokMatch
   * @return R
   */
  @Override
  @ApiOperation("修改KOK看球比赛信息")
  public Result update(@RequestBody KokMatch kokMatch) throws ApplicationException {
    return new Result<>(kokMatchService.updateById(kokMatch));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @Override
  @ApiOperation("删除KOK看球比赛信息")
  public Result removeById(@PathVariable Long id) throws ApplicationException {
    return new Result<>(kokMatchService.removeById(id));
  }

}
