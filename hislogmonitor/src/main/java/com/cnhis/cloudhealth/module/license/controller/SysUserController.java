package com.cnhis.cloudhealth.module.license.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnhis.cloudhealth.common.persistence.Page;
import com.cnhis.cloudhealth.module.license.entity.SysUser;
import com.cnhis.cloudhealth.module.license.service.SysUserServiceImpl;

@Controller
@RequestMapping(value="/user")
public class SysUserController {

	@Autowired
	private SysUserServiceImpl sysUserService;
	
	@RequestMapping(value="/get")
	public @ResponseBody Page<SysUser> get(SysUser sysUser,HttpServletRequest request){
		Page<SysUser> user = sysUserService.findAll(new Page<SysUser>(request), sysUser);
		return user;
	}
}
