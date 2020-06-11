package com.kok.sport.controller;

import com.kok.base.exception.ApplicationException;
import com.kok.sport.base.Result;
import com.kok.sport.base.SMSCache;
import com.kok.sport.entity.SysUser;
import com.kok.sport.enums.ErrorEnum;
import com.kok.sport.service.SysUserService;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MD5;
import com.kok.sport.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 系统用户表
 * @author martin
 * @date 2020-03-30 14:41:15
 */
@RestController
@AllArgsConstructor
@Api(value = "系统用户表",tags = "比分项目 - 系统用户表")
public class SysUserController implements ISysUserController {

    private final SysUserService sysUserService;

    private final SMSCache smsCache;

    /**
     * 验证手机号码是否已存在平台
     * @return
     * @throws Exception
     */
    @Override
    @ApiOperation("验证手机号码是否已存在平台")
    public Result validPhone(String phone)  throws Exception {
        return sysUserService.validPhone(phone);
    }

    /**
     * 发送短信验证码
     * @param sysUserVo
     * @return R
     */
    @Override
    @ApiOperation("发送短信验证码")
    public Result sendSmsCode(SysUserVo sysUserVo) throws Exception {
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        return sysUserService.sendSmsCode(sysUserVo, smsCache);
    }

    /**
     * 发送邮箱验证码
     * @param sysUserVo
     * @return R
     */
    @Override
    @ApiOperation("发送邮箱验证码")
    public Result sendMailCode(SysUserVo sysUserVo) throws Exception {
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if (StringUtils.isEmpty(sysUserVo.getFrom())) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        return sysUserService.sendMailCode(sysUserVo, smsCache);
    }

    /**
     * 系统用户通过短信验证码登录
     * @param sysUserVo
     * @return
     */
    @Override
    @ApiOperation("通过短信验证码登录")
    public Result loginBySmsCode(SysUserVo sysUserVo, HttpServletRequest request) throws ApplicationException {
        /*手机号码或者短信验证码不允许为空*/
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if (StringUtils.isEmpty(sysUserVo.getPhone())
                || StringUtils.isEmpty(sysUserVo.getSmsCode())) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        return sysUserService.loginBySmsCode(sysUserVo, smsCache, request);
    }

    /**
     * 系统用户通过账号密码登录
     * @param sysUserVo
     * @return
     */
    @Override
    @ApiOperation("通过账号密码登录")
    public Result loginByPassword(SysUserVo sysUserVo) throws ApplicationException {
        /*手机号码或者密码不允许为空*/
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if (StringUtils.isEmpty(sysUserVo.getPhone())
                || StringUtils.isEmpty(sysUserVo.getPassword())) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        sysUserVo.setPassword(MD5.EncoderByMd5(sysUserVo.getPassword()));
        return sysUserService.loginByPassword(sysUserVo);
    }

    /**
     * 设置新的密码
     * @param sysUserVo
     * @return
     */
    @Override
    @ApiOperation("设置新的密码")
    public Result updatePassword(SysUserVo sysUserVo) throws ApplicationException {
        /*手机号码,短信验证码,密码不允许为空*/
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if ("1".equals(sysUserVo.getType()) && StringUtils.isEmpty(sysUserVo.getPhone())){
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if ("2".equals(sysUserVo.getType()) && StringUtils.isEmpty(sysUserVo.getFrom())) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if(StringUtils.isEmpty(sysUserVo.getSmsCode()) || StringUtils.isEmpty(sysUserVo.getPassword())) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        sysUserVo.setPassword(MD5.EncoderByMd5(sysUserVo.getPassword()));
        return sysUserService.updatePassword(sysUserVo, smsCache);
    }

    /**
     * 注册
     * @param sysUserVo
     * @return
     */
    @Override
    @ApiOperation("注册")
    public Result reqister(SysUserVo sysUserVo, HttpServletRequest request) throws ApplicationException {
        //todo: 验证非空
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if (StringUtils.isEmpty(sysUserVo.getPhone())
                || StringUtils.isEmpty(sysUserVo.getSmsCode())
                || StringUtils.isEmpty(sysUserVo.getPassword())) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        //todo: 密码加密等其他操作
        sysUserVo.setPassword(MD5.EncoderByMd5(sysUserVo.getPassword()));

        return sysUserService.reqister(sysUserVo, request, smsCache);
    }

    /**
     * 获取用户信息
     * @param sysUserVo
     * @return
     * @throws ApplicationException
     */
    @Override
    @ApiOperation("获取用户信息")
    public Result getUserInfo(SysUserVo sysUserVo) throws ApplicationException {
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if (null != sysUserVo.getUserId()) {
            return sysUserService.getUserInfo(sysUserVo);
        }
        return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
    }

    /**
     * 更新用户信息
     * @param sysUserVo
     * @return
     * @throws ApplicationException
     */
    @Override
    @ApiOperation("更新用户信息")
    public Result updateUserInfo(SysUserVo sysUserVo) throws ApplicationException {
        if(sysUserVo == null) {
            return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
        }
        if (null != sysUserVo.getUserId()) {
            return sysUserService.updateUserInfo(sysUserVo);
        }
        return new Result(ErrorEnum.ERROR_402.getCode(), ErrorEnum.ERROR_402.getDesc());
    }
}
