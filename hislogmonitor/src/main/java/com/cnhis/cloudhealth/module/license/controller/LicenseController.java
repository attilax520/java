package com.cnhis.cloudhealth.module.license.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnhis.cloudhealth.common.persistence.Page;
import com.cnhis.cloudhealth.common.result.ApiCode;
import com.cnhis.cloudhealth.common.result.ApiResult;
import com.cnhis.cloudhealth.common.result.InterfaceResult;
import com.cnhis.cloudhealth.common.utils.BaseInitBinder;
import com.cnhis.cloudhealth.common.utils.DateUtils;
import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;
import com.cnhis.cloudhealth.module.license.entity.SysProduct;
import com.cnhis.cloudhealth.module.license.entity.SysProvincial;
import com.cnhis.cloudhealth.module.license.service.SysCorporationService;
import com.cnhis.cloudhealth.module.license.service.SysLicenseService;
import com.cnhis.cloudhealth.module.license.service.SysProductService;
import com.cnhis.cloudhealth.module.license.service.SysProvincialService;
import com.cnhis.cloudhealth.module.license.vo.LicenseJSON;
import com.cnhis.cloudhealth.module.license.vo.SysBlocVo;
import com.cnhis.cloudhealth.module.utils.UserUtils;

/**
 * @Title:  LicenseController.java   
 * @Package com.cnhis.cloudhealth.module.license.controller   
 * @Description: 授权中心首页
 * @author: huchaojing     
 * @date:   2018年1月31日 上午11:32:24   
 * @version V1.0
 */
@Controller
@RequestMapping(value="/license")
public class LicenseController extends BaseInitBinder{
	

	@Autowired
	private SysCorporationService sysCorporationService;
	
	@Autowired
	private SysProductService sysProductService;
	
	@Autowired
	private SysLicenseService licenseService;
	
	@Autowired
	private SysProvincialService sysProvincialService;
	

	/**
	 * 项目入口
	 * @Title: index   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/licenseList")
	public String  licenseList(SysCorporation sysCorporation,SysBlocVo sysBlocVo,HttpServletRequest request,Model model){
		sysCorporation.setParentid(0);
		Page<SysCorporation> result = sysCorporationService.findAll(new Page<SysCorporation>(request), sysCorporation);
		model.addAttribute("page",result);
		Page<SysBlocVo> blocList = sysCorporationService.findBlocAll(new Page<SysBlocVo>(request), sysBlocVo);
		model.addAttribute("blocList",blocList);
		//查询授权提醒个数
		Integer remindNum = sysCorporationService.remindLicense();
		model.addAttribute("remindNum",remindNum);
		return "manage/index";
	}
	
	/**
	 * 企业授权分页查询
	 * @Title: licensePage   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param sysBlocVo
	 * @param: @param request
	 * @param: @param model
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/licensePage")
	public @ResponseBody ApiResult  licensePage(SysCorporation sysCorporation,HttpServletRequest request,Model model){
		sysCorporation.setParentid(0);
		Page<SysCorporation> result = sysCorporationService.findAll(new Page<SysCorporation>(request), sysCorporation);
		return ApiResult.success(result);
	}
	
	/**
	 * 集团授权分页查询
	 * @Title: licensePage   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param sysBlocVo
	 * @param: @param request
	 * @param: @param model
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/blocPage")
	public @ResponseBody ApiResult  blocPage(SysBlocVo sysBlocVo,HttpServletRequest request,Model model){
		Page<SysBlocVo> blocList = sysCorporationService.findBlocAll(new Page<SysBlocVo>(request), sysBlocVo);
		return ApiResult.success(blocList);
	}
	
	
	/**
	 * 企业授权列表
	 * @Title: enterpriseLicenseList   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param request
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/enterpriseLicenseList",method = {RequestMethod.GET,RequestMethod.POST})
	public String enterpriseLicenseList(SysCorporation sysCorporation,String error,HttpServletRequest request,Model model){
		sysCorporation.setParentid(0);
		Page<SysCorporation> result = sysCorporationService.findAll(new Page<SysCorporation>(request), sysCorporation);
		model.addAttribute("page",result);
		String productList = sysProductService.getZNodes(null);
		List<SysProvincial> provincial = sysProvincialService.findAll();
		Integer remindNum = sysCorporationService.remindLicense();
		model.addAttribute("remindNum",remindNum);
		model.addAttribute("provincial", provincial);
		model.addAttribute("productList", productList);
		model.addAttribute("ininDate", DateUtils.getDate());
		model.addAttribute("error", error);
		return "manage/enterpriseLicense";
	}
	
	/**
	 * 企业授权
	 * @Title: saveLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param productArr
	 * @param: @return
	 * @return: InterfaceResult      
	 * @throws
	 */
	@RequestMapping(value="/saveLicense")
	public @ResponseBody ApiResult saveLicense(SysCorporation sysCorporation,String productArr,HttpSession session){
		try {
			Integer userId = UserUtils.getSysUserId(session);
			
			if(userId == null){
				return ApiResult.fail("-1");
			}
			
			sysCorporation.setCreateBy(userId.toString());
			InterfaceResult result = licenseService.saveLicense(sysCorporation, productArr);
			if(result.isSuccess()){
				return ApiResult.success(sysCorporation.getId());
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	@RequestMapping(value="/anewLicense")
	public @ResponseBody ApiResult anewLicense(SysCorporation sysCorporation,String productArr,HttpSession session){
		try {
			Integer userId = UserUtils.getSysUserId(session);
			
			if(userId == null){
				return ApiResult.fail("-1");
			}
			
			sysCorporation.setCreateBy(userId.toString());
			InterfaceResult result = licenseService.editLicense(sysCorporation, productArr);
			if(result.isSuccess()){
				return ApiResult.success(sysCorporation.getId());
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 查询最大code值
	 * @Title: findMaxCode   
	 * @Description: TODO(方法功能描述)
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/findMaxCode")
	public @ResponseBody ApiResult findMaxCode(){
		try {
			return ApiResult.success(sysCorporationService.findMaxCode());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 集团授权列表
	 * @Title: blocLicenseList   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param request
	 * @param: @param model
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/blocLicenseList",method = {RequestMethod.GET,RequestMethod.POST})
	public String blocLicenseList(SysBlocVo sysBlocVo,String error,String pid,HttpServletRequest request,Model model){
		Page<SysBlocVo> blocList = sysCorporationService.findBlocAll(new Page<SysBlocVo>(request), sysBlocVo);
		model.addAttribute("blocList",blocList);
		String productList = sysProductService.getZNodes(null);
		List<SysProvincial> provincial = sysProvincialService.findAll();
		
		List<SysBlocVo> blocListName = sysCorporationService.findBlocAllName(null);
		
		model.addAttribute("blocListName",blocListName);
		Integer remindNum = sysCorporationService.remindLicense();
		model.addAttribute("remindNum",remindNum);
		model.addAttribute("provincial", provincial);
		model.addAttribute("productList", productList);
		model.addAttribute("ininDate", DateUtils.getDate());
		model.addAttribute("halfAYear", DateUtils.formatDate(DateUtils.addMonth(new Date(),6), "yyyy-MM-dd"));
		model.addAttribute("oneYear", DateUtils.formatDate(DateUtils.addMonth(new Date(),12), "yyyy-MM-dd"));
		model.addAttribute("threeYear", DateUtils.formatDate(DateUtils.addMonth(new Date(),36), "yyyy-MM-dd"));
		model.addAttribute("error",error);
		model.addAttribute("pid",pid);
		return "manage/blocLicense";
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
	@RequestMapping(value="/findByBlocCorPage")
	public @ResponseBody ApiResult findByBlocCorPage(SysCorporation sysCorporation,HttpServletRequest request){
		try {
			Page<SysCorporation> page = sysCorporationService.findByBlocCorPage(new Page<SysCorporation>(request), sysCorporation);
			return ApiResult.success(page);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 集团添加
	 * @Title: saveBloc   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @param productArr
	 * @param: @param session
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/saveBloc")
	public @ResponseBody ApiResult saveBloc(SysCorporation sysCorporation,String productArr,HttpSession session){
		try {
			Integer userId = UserUtils.getSysUserId(session);
			if(userId == null){
				return ApiResult.fail("-1");
			}
			
			sysCorporation.setCreateBy(userId.toString());
			InterfaceResult result = licenseService.saveBloc(sysCorporation);
			if(result.isSuccess()){
				return ApiResult.success(sysCorporation.getId());
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 去重新授权页面加载基础数据
	 * @Title: goLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @param model
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/goAnewLicense")
	public @ResponseBody ApiResult  goAnewLicense(Integer id,Model model){
		SysCorporation sysCorporation = sysCorporationService.getCorporation(id);
		return ApiResult.success(sysCorporation);
	}
	
	/**
	 * 去重新授权页面加载授权产品数据
	 * @Title: notProductList   
	 * @Description: TODO(方法功能描述)
	 * @param: @param id
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/getLicenseProduct")
	public @ResponseBody ApiResult getLicenseProduct(Integer id){
		String productList = sysProductService.getZNodes(id);
		return ApiResult.success(productList);
	}
	
	/**
	 * 授权信息API
	 * @Title: checkLicenseApi   
	 * @Description: TODO(方法功能描述)
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: LicenseJSON      
	 * @throws
	 */
	@RequestMapping(value="/checkLicenseApi",method = {RequestMethod.POST})
	public @ResponseBody LicenseJSON checkLicenseApi(String code,String name){
		LicenseJSON json = sysCorporationService.checkLicenseApi(code, name);
		return json;
	}
	
	/**
	 * 
	 * @Title: findLicenseProduct   
	 * @Description: 根据医院code以及医院名称查询授权模块API
	 * @param: @param code
	 * @param: @param name
	 * @param: @return
	 * @return: List<SysProduct>      
	 * @throws
	 */
	@RequestMapping(value="/findLicenseProduct",method = {RequestMethod.POST})
	public @ResponseBody List<SysProduct> findLicenseProduct(String code,String name){
		List<SysProduct> list = sysProductService.findLicenseProduct(code, name);
		return list;
	}
	
	/**
	 * @Title: checkLicenseToken   
	 * @Description: his子项目授权检测
	 * @param: @param code
	 * @param: @param token
	 * @param: @param session
	 * @param: @return
	 * @return: boolean      
	 * @throws
	 */
	@RequestMapping(value="/checkLicenseToken",method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody boolean checkLicenseToken(String code,String token){
		return sysCorporationService.checkLicenseToken(code,token);
	}
	
	/**
	 * 删除集团
	 * @Title: delBloc   
	 * @Description: TODO(方法功能描述)
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/delBloc")
	public @ResponseBody ApiResult delBloc(SysCorporation sysCorporation){
		try {
			sysCorporation.setDelFlag("1");
			InterfaceResult result = licenseService.updateBloc(sysCorporation);
			if(result.isSuccess()){
				InterfaceResult delResult = licenseService.delBlocCorporation(sysCorporation);
				if(delResult.isSuccess()){
					return ApiResult.success(sysCorporation.getId());
				}
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * @Title: delHospital   
	 * @Description:  删除集团医院
	 * @param: @param sysCorporation
	 * @param: @return
	 * @return: ApiResult      
	 * @throws
	 */
	@RequestMapping(value="/delHospital")
	public @ResponseBody ApiResult delHospital(SysCorporation sysCorporation){
		try {
			sysCorporation.setDelFlag("1");
			InterfaceResult result = licenseService.updateBloc(sysCorporation);
			if(result.isSuccess()){
				return ApiResult.success();
			}
			return ApiResult.fail(result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResult.fail(ApiCode.SYSTEM_ERROR);
		}
	}
	
	
}
