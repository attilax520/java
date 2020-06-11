package com.cnhis.cloudhealth.module.license.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnhis.cloudhealth.module.license.dao.SysProvincialDao;
import com.cnhis.cloudhealth.module.license.entity.SysProvincial;


@Service
public class SysProvincialServiceImpl implements SysProvincialService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysProvincialDao sysProvincialDao;
	
	
	/**
	 * @Title: findAll   
	 * @Description: 查询所有省
	 * @param: @return
	 * @return: List<SysProvincial>
	 * @throws
	 */
	public List<SysProvincial> findAll(){
		return sysProvincialDao.findAll();
	}

}
