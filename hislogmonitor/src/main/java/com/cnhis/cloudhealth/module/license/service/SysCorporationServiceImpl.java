package com.cnhis.cloudhealth.module.license.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnhis.cloudhealth.common.persistence.Page;
import com.cnhis.cloudhealth.common.utils.DateUtils;
import com.cnhis.cloudhealth.common.utils.Global;
import com.cnhis.cloudhealth.common.utils.IdGen;
import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.license.dao.SysCorporationDao;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;
import com.cnhis.cloudhealth.module.license.service.redis.RedisCluster;
import com.cnhis.cloudhealth.module.license.vo.LicenseJSON;
import com.cnhis.cloudhealth.module.license.vo.SysBlocVo;


@Service
@Transactional(readOnly = true)
public class SysCorporationServiceImpl implements SysCorporationService{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysCorporationDao sysCorporationDao;
	
	@Autowired
	private RedisCluster<String, String> redisCluster;
	
	private static final String TOKEN_KEY = "token_key_";
	
	private static final String TIMEOUT = "time_out";
	
	/**
	 * 查询企业医院
	 */
	public Page<SysCorporation> findAll(Page<SysCorporation> page,SysCorporation sysCorporation){
		sysCorporation.setType(1);
		sysCorporation.setPage(page);
		List<SysCorporation> list = sysCorporationDao.findAll(sysCorporation);
		page.setList(list);
		return page;
	}

	/**
	 * 添加企业或集团
	 * @Title: save   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void      
	 * @throws
	 */
	@Transactional(readOnly = false)
	public void save(SysCorporation sysCorporation) {
		sysCorporationDao.save(sysCorporation);
	}

	/**
	 * 根据ID查询企业或集团
	 * @Title: getCorporation   
	 * @Description: TODO(方法功能描述)
	 * @param: @param id
	 * @param: @return
	 * @return: SysCorporation
	 * @throws
	 */
	public SysCorporation getCorporation(Integer id) {
		return sysCorporationDao.getCorporation(id);
	}

	/**
	 * 添加授权证书名称
	 * @Title: addCredentialName   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @return: void      
	 * @throws
	 */
	public void addCredentialName(SysCorporation sysCorporation) {
		sysCorporationDao.addCredentialName(sysCorporation);
	}

	/**
	 * 查询所有集团医院
	 * @Title: findBlocAll   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysBlocVo
	 * @param: @return
	 * @return: Page<SysBlocVo>      
	 * @throws
	 */
	public Page<SysBlocVo> findBlocAll(Page<SysBlocVo> page, SysBlocVo sysBlocVo) {
		sysBlocVo.setPage(page);
		List<SysBlocVo> list = sysCorporationDao.findBlocAll(sysBlocVo);
		for (SysBlocVo sysBlocVo2 : list) {
			SysCorporation sysCorporation = new SysCorporation();
			sysCorporation.setType(0);
			sysCorporation.setName(sysBlocVo.getBlocName());
			sysCorporation.setAccsdate(sysBlocVo.getAccsdate());
			sysCorporation.setAccedate(sysBlocVo.getAccedate());
			sysCorporation.setDay(sysBlocVo.getDay());
			sysCorporation.setParentid(sysBlocVo2.getId());
			List<SysCorporation> corporationList = sysCorporationDao.findAll(sysCorporation);
			sysBlocVo2.setCorporationList(corporationList);
		}
		page.setList(list);
		return page;
	}
	
	/**
	 * 查询某个集团企业医院
	 * @Title: findByBlocCorPage   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: Page<SysCorporation>      
	 * @throws
	 */
	public Page<SysCorporation> findByBlocCorPage(Page<SysCorporation> page, SysCorporation sysCorporation) {
			sysCorporation.setPage(page);
			sysCorporation.setType(0);
			List<SysCorporation> corporationList = sysCorporationDao.findAll(sysCorporation);
			page.setList(corporationList);
		return page;
	}
	
	
	/**
	 * 查询所有集团医院名称
	 * @Title: findBlocAllName   
	 * @Description: TODO(方法功能描述)
	 * @param: @param page
	 * @param: @param sysBlocVo
	 * @param: @return
	 * @return: List<SysBlocVo>      
	 * @throws
	 */
	public List<SysBlocVo> findBlocAllName(SysBlocVo sysBlocVo) {
		return sysCorporationDao.findBlocAll(sysBlocVo);
	}

	/**
	 * 查询需要授权提醒的个数
	 * @Title: remindLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: Integer      
	 * @throws
	 */
	public Integer remindLicense() {
		return sysCorporationDao.remindLicense();
	}

	/**
	 * 
	 * @Title: checkLicenseApi   
	 * @Description: 根据医院编码以及名称查询医院信息 
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: LicenseJSON
	 * @throws
	 */
	public LicenseJSON checkLicenseApi(String code, String name) {
		SysCorporation sysCorporation = sysCorporationDao.findByCorporation(code, name);
		if(sysCorporation == null ){
        	LicenseJSON json = new LicenseJSON();
        	json.setStatus(-1);
        	json.setMessage("当前医院授权信息不存在！");
        	return json;
        }
        
        String nowDate = DateUtils.formatDate(new Date());
        if(sysCorporation.getAccedate() == null){
        	LicenseJSON json = new LicenseJSON();
        	json.setStatus(-1);
        	json.setMessage("授权信息不正确,请联系本司管理员！");
        	return json;
        }
        
        String accedate = DateUtils.formatDate(sysCorporation.getAccedate());
        if(DateUtils.compareDate(DateUtils.parseDate(accedate),DateUtils.parseDate(nowDate)) == -1){
        	LicenseJSON json = new LicenseJSON();
        	json.setStatus(-1);
        	json.setMessage("授权已于"+accedate+"过期，请联系本司重新授权");
        	return json;
        }
        
        String token = IdGen.uuid();
        Integer expireDay = sysCorporation.getExpireDay();
        if(expireDay != null){
        	LicenseJSON json = new LicenseJSON();
        	Date beginDate= DateUtils.parseDate(nowDate);
        	Date endDate= DateUtils.parseDate(accedate); 
        	long day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        	if(day <= expireDay){
        		String str = "您的授权许可证即将到期,您还有"+day+"天使用权。";
 			   		   str+= "为保障您的权益及数据安全，请立即联系您的经销商或软件开发商，获取继续使用许可权。";
 			   		   str+= "如果您院有软件款未支付，请先支付软件款或联系经销商协商。";
        		logger.info(str);
        		json.setStatus(1);
        		json.setMessage(str);
            	json.setData(token);
            	redisCluster.set(TOKEN_KEY+code, token,Long.parseLong(Global.getConfig(TIMEOUT)),TimeUnit.SECONDS);
            	return json;
        	}
        }
        
        String svredate = DateUtils.formatDate(sysCorporation.getSvredate());
        if(DateUtils.compareDate(DateUtils.parseDate(svredate),DateUtils.parseDate(nowDate)) == -1){
        	LicenseJSON json = new LicenseJSON();
        	logger.info("服务时间为："+accedate+"当前时间为："+nowDate);
        	json.setStatus(1);
        	json.setMessage("当前产品售后服务已于"+accedate+"过期，请联系本司重新获取售后服务！");
        	json.setData(token);
        	redisCluster.set(TOKEN_KEY+code, token,Long.parseLong(Global.getConfig(TIMEOUT)),TimeUnit.SECONDS);
        	return json;
        }
        LicenseJSON json = new LicenseJSON();
        json.setStatus(0);
    	json.setData(token);
    	redisCluster.set(TOKEN_KEY+code, token,Long.parseLong(Global.getConfig(TIMEOUT)),TimeUnit.SECONDS);
    	return json;
	}

	/**
	 * 校验子模块是否授权
	 * @Title: checkLicenseToken   
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: boolean      
	 * @throws
	 */
	public boolean checkLicenseToken(String code,String token) {
		
		String centerToken = redisCluster.get(TOKEN_KEY+code);
		
		if(centerToken == null){
			return false;
		}
		
		if(StringUtils.isEmpty(centerToken) || StringUtils.isEmpty(token)){
			return false;
		}
		if(!centerToken.equals(token)){
			return false;
		}
		return true;
	}

	/**
	 * 查询最大code值
	 * @Title: findMaxCode   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	public String findMaxCode() {
		String maxCode = sysCorporationDao.findMaxCode();
		if(StringUtils.isEmpty(maxCode)){
			maxCode = "000001";
		}else{
			Integer num = Integer.parseInt(maxCode)+1;
			maxCode = String.format("%06d", num);
		}
		return maxCode;
	}
	
}
