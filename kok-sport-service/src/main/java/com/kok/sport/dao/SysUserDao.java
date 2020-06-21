package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.SysUser;
import com.kok.sport.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户表
 * @author martin
 * @date 2020-03-30 14:41:15
 */
public interface SysUserDao extends BaseMapper<SysUser> {
    /**
     * string简单分页查询
     * @param sysUser 系统用户
     * @return
     */
    IPage<SysUser> getSysUserPage(PageVo pagevo, @Param("sysUser") SysUser sysUser);

    /**
     * 根据手机号码查询用户信息
     * @param sysUserVo 系统用户
     * @return
     */
    List<SysUser> getSysUserByCondition(@Param("sysUser") SysUserVo sysUserVo);

    /**
     * 设置新的密码
     * @param sysUserVo 系统用户
     * @return
     */
    int updatePassword(@Param("sysUser") SysUserVo sysUserVo);

    /**
     * 注册
     * @param sysUser 系统用户
     * @return
     */
    int reqister(SysUser sysUser);

    /**
     * 更新用户信息
     * @param sysUserVo 系统用户
     * @return
     */
    int updateUserInfo(@Param("sysUser") SysUserVo sysUserVo);
}
