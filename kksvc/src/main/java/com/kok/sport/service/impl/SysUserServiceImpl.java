package com.kok.sport.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.base.vo.PageVo;
import com.kok.sport.base.Result;
import com.kok.sport.base.SMSCache;
import com.kok.sport.dao.SysUserDao;
import com.kok.sport.entity.SysUser;
import com.kok.sport.entity.SysUserSms;
import com.kok.sport.enums.ErrorEnum;
import com.kok.sport.service.SysUserService;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.MailUtil;
import com.kok.sport.utils.SMSUtil;
import com.kok.sport.vo.SysUserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 系统用户管理Service
 *
 * @author martin
 * @date 2020-03-30 14:41:15
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    /**
     * 系统用户管理简单分页查询
     *
     * @param sysUser 系统用户
     * @return
     */
    @Override
    public IPage<SysUser> getSysUserPage(PageVo<SysUser> pageVo, SysUser sysUser) {
        return baseMapper.getSysUserPage(pageVo, sysUser);
    }

    /**
     * 验证手机号码是否已存在平台
     * @return
     * @throws Exception
     */
    @Override
    public Result validPhone(String phone) {
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setPhone(phone);
        List<SysUser> userList = baseMapper.getSysUserByCondition(sysUserVo);
        if (!CollectionUtil.isEmpty(userList)) {
            return new Result(
                    ErrorEnum.ERROR_400.getCode(),
                    ErrorEnum.ERROR_400.getDesc());
        }
        return new Result(
                ErrorEnum.ERROR_401.getCode(),
                ErrorEnum.ERROR_401.getDesc());
    }

    /**
     * 发送短信验证码
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result sendSmsCode(SysUserVo sysUserVo, SMSCache smsCache) throws Exception {
        List<SysUser> userList = baseMapper.getSysUserByCondition(sysUserVo);
        //通过类别进行不同逻辑判断
        if ("1".equals(sysUserVo.getType())) {//todo 注册：手机号码不能注册相同
            if (!CollectionUtil.isEmpty(userList)) {
                return new Result(
                        ErrorEnum.ERROR_400.getCode(),
                        ErrorEnum.ERROR_400.getDesc());
            }
        } else if ("2".equals(sysUserVo.getType())) {//todo 登录：手机号码不管是否注册都发送

        } else if ("3".equals(sysUserVo.getType())) {//todo 找回密码：手机号码需要已存在
            if (CollectionUtil.isEmpty(userList)) {
                return new Result(
                        ErrorEnum.ERROR_401.getCode(),
                        ErrorEnum.ERROR_401.getDesc());
            }
        }
        String phone = sysUserVo.getPhone();
        //随机六位验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
        //发送验证码
        String result = SMSUtil.send(code, phone, sysUserVo.getType());
        JSONObject resultObj = JSONObject.parseObject(result);
        if("100".equals(resultObj.getString("stat"))) {
            //进行缓存
            SysUserSms sysUserSms = smsCache.getCache(phone);
            if(sysUserSms != null) {
                sysUserSms.setCode(code);
                sysUserSms.setSendCount(sysUserSms.getSendCount() + 1);
            } else {
                sysUserSms = new SysUserSms();
                sysUserSms.setPhone(phone);
                sysUserSms.setCode(code);
                sysUserSms.setSendCount(1);
            }
            smsCache.pushCache(sysUserVo.getPhone(), sysUserSms);
            return new Result(
                    ErrorEnum.ERROR_000.getCode(),
                    ErrorEnum.ERROR_000.getDesc(),
                    result);
        } else {
            return new Result(
                    ErrorEnum.ERROR_999.getCode(),
                    ErrorEnum.ERROR_999.getDesc(),
                    result);
        }
    }

    /**
     * 发送邮箱验证码
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result sendMailCode(SysUserVo sysUserVo, SMSCache smsCache) {
        String subject = "";
        String content = "";
        //随机六位验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
        //通过类别进行不同逻辑判断
        if ("1".equals(sysUserVo.getType())) {//todo 注册
            subject = "【注册验证码】";
            content = "【先锋体育】验证码为：" + code + "，有效时间10分钟，请尽快注册";
        } else if ("2".equals(sysUserVo.getType())) {//todo 登录
            subject = "【登录验证码】";
            content = "【先锋体育】验证码为：" + code + "，有效时间10分钟，请尽快登录";
        } else if ("3".equals(sysUserVo.getType())) {//todo 找回密码
            subject = "【找回密码验证码】";
            content = "【先锋体育】验证码为：" + code + "，有效时间10分钟，请尽快找回密码";
        }
        //发送验证码
        String from = sysUserVo.getFrom();
        if(from.indexOf("%40") >= 0) {
            from = from.replace("%40", "@");
        }
        MailUtil mailUtil = new MailUtil();
        boolean result = mailUtil.sendMail(from, subject, content);
        if(result) {
            //进行缓存
            SysUserSms sysUserSms = smsCache.getCache(from);
            if(sysUserSms != null) {
                sysUserSms.setCode(code);
                sysUserSms.setSendCount(sysUserSms.getSendCount() + 1);
            } else {
                sysUserSms = new SysUserSms();
                sysUserSms.setPhone(from);
                sysUserSms.setCode(code);
                sysUserSms.setSendCount(1);
            }
            smsCache.pushCache(from, sysUserSms);
            return new Result(
                    ErrorEnum.ERROR_000.getCode(),
                    ErrorEnum.ERROR_000.getDesc(),
                    result);
        } else {
            return new Result(
                    ErrorEnum.ERROR_999.getCode(),
                    ErrorEnum.ERROR_999.getDesc(),
                    result);
        }
    }

    /**
     * 按照短信验证码登录
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result loginBySmsCode(SysUserVo sysUserVo, SMSCache smsCache, HttpServletRequest request) {
        //校验验证码
        if(!"000000".equals(sysUserVo.getSmsCode())) {
            SysUserSms sysUserSms = smsCache.getCache(sysUserVo.getPhone());
            if(sysUserSms == null) {
                return new Result(
                        ErrorEnum.ERROR_404.getCode(),
                        ErrorEnum.ERROR_404.getDesc());
            }
            if(!sysUserSms.getCode().equals(sysUserVo.getSmsCode())) {
                return new Result(
                        ErrorEnum.ERROR_404.getCode(),
                        ErrorEnum.ERROR_404.getDesc());
            }
        }
        //可直接注册
        List<SysUser> userList = baseMapper.getSysUserByCondition(sysUserVo);
        if (CollectionUtil.isEmpty(userList)) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(sysUserVo.getPhone());
            sysUser.setPhone(sysUserVo.getPhone());
            sysUser.setDeleteFlag("0");
            sysUser.setRegisterTime(LocalDateTime.now());
            sysUser.setRegisterIp(LogUtil.getRemoteIpByServletRequest(request));
            baseMapper.reqister(sysUser);
            userList.set(0, sysUser);
        }
        return new Result(
                ErrorEnum.ERROR_000.getCode(),
                ErrorEnum.ERROR_000.getDesc(),
                userList.get(0));
    }

    /**
     * 使用用户名密码登录
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result loginByPassword(SysUserVo sysUserVo) {
        SysUserVo sqlUserVO = new SysUserVo();
        sqlUserVO.setPhone(sysUserVo.getPhone());
        List<SysUser> userList = baseMapper.getSysUserByCondition(sqlUserVO);
        if (CollectionUtil.isEmpty(userList)) {
            return new Result(
                    ErrorEnum.ERROR_405.getCode(),
                    ErrorEnum.ERROR_405.getDesc());
        }
        if(!sysUserVo.getPassword().equals(userList.get(0).getPassword())) {
            return new Result(
                    ErrorEnum.ERROR_403.getCode(),
                    ErrorEnum.ERROR_403.getDesc());
        }
        userList.get(0).setPassword(null);
        return new Result(
                ErrorEnum.ERROR_000.getCode(),
                ErrorEnum.ERROR_000.getDesc(),
                userList.get(0));
    }

    /**
     * 设置新的密码
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result updatePassword(SysUserVo sysUserVo, SMSCache smsCache) {
        if(!"000000".equals(sysUserVo.getSmsCode())) {
            String from = sysUserVo.getFrom();
            if(!StringUtils.isEmpty(from)) {
                if(from.indexOf("%40") >= 0) {
                    from = from.replace("%40", "@");
                }
            } else {
                from = sysUserVo.getPhone();
            }
            //校验验证码
            SysUserSms sysUserSms = smsCache.getCache(from);
            if (sysUserSms == null) {
                return new Result(
                        ErrorEnum.ERROR_404.getCode(),
                        ErrorEnum.ERROR_404.getDesc());
            }
            if (!sysUserSms.getCode().equals(sysUserVo.getSmsCode())) {
                return new Result(
                        ErrorEnum.ERROR_404.getCode(),
                        ErrorEnum.ERROR_404.getDesc());
            }
        }
        if (baseMapper.updatePassword(sysUserVo) <= 0) {
            return new Result(
                    ErrorEnum.ERROR_999.getCode(),
                    ErrorEnum.ERROR_999.getDesc());
        }
        return new Result(
                ErrorEnum.ERROR_000.getCode(),
                ErrorEnum.ERROR_000.getDesc());
    }

    /**
     * 注册
     *
     * @param sysUserVo
     * @param request
     * @return
     */
    @Override
    public Result reqister(SysUserVo sysUserVo, HttpServletRequest request, SMSCache smsCache) {
        if(!"000000".equals(sysUserVo.getSmsCode())) {
            //校验验证码
            SysUserSms sysUserSms = smsCache.getCache(sysUserVo.getPhone());
            if (sysUserSms == null) {
                return new Result(
                        ErrorEnum.ERROR_404.getCode(),
                        ErrorEnum.ERROR_404.getDesc());
            }
            if (!sysUserSms.getCode().equals(sysUserVo.getSmsCode())) {
                return new Result(
                        ErrorEnum.ERROR_404.getCode(),
                        ErrorEnum.ERROR_404.getDesc());
            }
        }
        SysUserVo sqlUserVO = new SysUserVo();
        sqlUserVO.setPhone(sysUserVo.getPhone());
        List<SysUser> list = baseMapper.getSysUserByCondition(sqlUserVO);
        if (!CollectionUtil.isEmpty(list)) {
            return new Result(
                    ErrorEnum.ERROR_400.getCode(),
                    ErrorEnum.ERROR_400.getDesc());
        }

        //todo:  构建入库实体
        SysUser sysUser = new SysUser();
        sysUser.setUserName(sysUserVo.getPhone());
        sysUser.setPassword(sysUserVo.getPassword());
        sysUser.setPhone(sysUserVo.getPhone());
        sysUser.setRegisterTime(LocalDateTime.now());
        sysUser.setRegisterIp(LogUtil.getRemoteIpByServletRequest(request));
        sysUser.setDeleteFlag("0");

        if (baseMapper.reqister(sysUser) <= 0) {
            return new Result(
                    ErrorEnum.ERROR_999.getCode(),
                    ErrorEnum.ERROR_999.getDesc());
        }
        return new Result(
                ErrorEnum.ERROR_000.getCode(),
                ErrorEnum.ERROR_000.getDesc(),
                sysUser);
    }

    /**
     * 获取用户信息
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result getUserInfo(SysUserVo sysUserVo) {
        List<SysUser> userList = baseMapper.getSysUserByCondition(sysUserVo);
        if (CollectionUtil.isEmpty(userList)) {
            return new Result(
                    ErrorEnum.ERROR_403.getCode(),
                    ErrorEnum.ERROR_403.getDesc());
        }
        return new Result(
                ErrorEnum.ERROR_000.getCode(),
                ErrorEnum.ERROR_000.getDesc(),
                userList.get(0));
    }

    /**
     * 更新用户信息
     *
     * @param sysUserVo
     * @return
     */
    @Override
    public Result updateUserInfo(SysUserVo sysUserVo) {
        int count = baseMapper.updateUserInfo(sysUserVo);
        if (count == 0) {
            return new Result(
                    ErrorEnum.ERROR_999.getCode(),
                    ErrorEnum.ERROR_999.getDesc());
        }
        return new Result(
                ErrorEnum.ERROR_000.getCode(),
                ErrorEnum.ERROR_000.getDesc());
    }
}
