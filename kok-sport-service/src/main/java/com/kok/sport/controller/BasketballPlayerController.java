package com.kok.sport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.utils.Result;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.BasketballPlayer;
import com.kok.sport.service.BasketballPlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 篮球阵容表
 * @author martin
 * @date 2020-03-28 22:19:31
 */
@RestController
@AllArgsConstructor
@Api(value = "篮球阵容表", tags = "系统生成 - 篮球阵容表")
public class BasketballPlayerController implements IBasketballPlayerController {

    private final BasketballPlayerService basketballPlayerService;

    /**
     * 简单分页查询
     * @param pageVo           分页对象
     * @param basketballPlayer string
     * @return
     */
    @Override
    @ApiOperation("简单分页查询")
    public Result<IPage<BasketballPlayer>> getBasketballPlayerPage(PageVo<BasketballPlayer> pageVo, BasketballPlayer basketballPlayer) throws ApplicationException {
        return new Result<>(basketballPlayerService.getBasketballPlayerPage(pageVo, basketballPlayer));
    }

    /**
     * 通过id查询单条记录
     * @param id
     * @return R
     */
    @Override
    @ApiOperation("通过id查询单条记录")
    public Result<BasketballPlayer> getById(@PathVariable("id") Long id) throws ApplicationException {
        return new Result<>(basketballPlayerService.getById(id));
    }

    /**
     * 新增记录
     * @param basketballPlayer
     * @return R
     */
    @Override
    @ApiOperation("通过id查询单条记录")
    public Result save(@RequestBody BasketballPlayer basketballPlayer) throws ApplicationException {
        return new Result<>(basketballPlayerService.save(basketballPlayer));
    }

    /**
     * 修改记录
     * @param basketballPlayer
     * @return R
     */
    @Override
    @ApiOperation("通过id查询单条记录")
    public Result update(@RequestBody BasketballPlayer basketballPlayer) throws ApplicationException {
        return new Result<>(basketballPlayerService.updateById(basketballPlayer));
    }

    /**
     * 通过id删除一条记录
     * @param id
     * @return R
     */
    @Override
    @ApiOperation("通过id查询单条记录")
    public Result removeById(@PathVariable Long id) throws ApplicationException {
        return new Result<>(basketballPlayerService.removeById(id));
    }

}
