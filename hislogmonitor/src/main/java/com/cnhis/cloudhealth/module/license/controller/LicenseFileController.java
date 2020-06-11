package com.cnhis.cloudhealth.module.license.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnhis.cloudhealth.common.result.ApiResult;
import com.cnhis.cloudhealth.common.utils.BaseInitBinder;
import com.cnhis.cloudhealth.common.utils.DateUtils;
import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;
import com.cnhis.cloudhealth.module.license.entity.SysProduct;
import com.cnhis.cloudhealth.module.license.service.SysCorporationService;
import com.cnhis.cloudhealth.module.license.service.SysLicenseService;
import com.cnhis.cloudhealth.module.license.service.SysProductService;
import com.cnhis.cloudhealth.module.utils.AESUtil;

/**
 * 授权中心首页
 * @Title:  LicenseController.java   
 * @Package com.cnhis.cloudhealth.module.license.controller   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年1月31日 上午11:32:24   
 * @version V1.0
 */
@Controller
@RequestMapping(value="/licenseFileXML")
public class LicenseFileController extends BaseInitBinder{
	
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysCorporationService sysCorporationService;
	
	@Autowired
	private SysProductService sysProductService;
	
	@Autowired
	private SysLicenseService licenseService;
	
	
	private SimpleDateFormat df = new SimpleDateFormat(DateUtils.YYY_MM_DD_HH_MM_SS);//设置日期格式
	
	
	/**
	 * 创建企业授权证书
	 * @Title: goAnewLicense   
	 * @Description: TODO(方法功能描述)
	 * @param: @param id
	 * @param: @param request
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/createLicenseFile")
	public String  goAnewLicense(Integer id,HttpServletRequest request){
		try {
			SysCorporation sysCorporation = sysCorporationService.getCorporation(id);
			List<SysProduct> productList = sysProductService.getLicenseProduct(id);
			
			String dateTime = df.format(sysCorporation.getCreateDate());
			
			String xmlFileName = sysCorporation.getCode()+dateTime+".License";
			Document document = licenseFileXML(sysCorporation,productList);
			//设置文件编码  
	        OutputFormat format = new OutputFormat(); 
	        
	        format.setEncoding("UTF-8"); 
	        // 设置换行 
	        format.setNewlines(true); 
	        // 生成缩进 
	        format.setIndent(true); 
	        // 使用4个空格进行缩进, 可以兼容文本编辑器 
	        format.setIndent("    "); 
			//FileWriter out = null;
			String xmlPath = request.getSession().getServletContext().getRealPath("/") + "/static/xmlfile/";
			
			File file = new File(xmlPath);
			if(!file.exists()){
				file.mkdir();
			}
			 OutputStreamWriter out = null;
			String newXmlPath = xmlPath+xmlFileName;
	        try {
	        	out = new OutputStreamWriter(new FileOutputStream(newXmlPath),"UTF-8");
	        	XMLWriter writer = new XMLWriter(out, format);
	        	writer.write(document);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            if (out!=null) {
	                try {
	                    out.close();
		                    //文件加密
	    	        	String credentialName = AESUtil.encryptfile(newXmlPath,sysCorporation);
	    	        	sysCorporation.setCredentialName(credentialName);
	    	        	sysCorporationService.addCredentialName(sysCorporation);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:../license/enterpriseLicenseList";
	}
	
	/**
	 * 集团添加授权
	 * @Title: createBlocLicenseFile   
	 * @Description: TODO(方法功能描述)
	 * @param: @param id
	 * @param: @param request
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/createBlocLicenseFile")
	public @ResponseBody ApiResult  createBlocLicenseFile(Integer id,HttpServletRequest request){
		try {
			SysCorporation sysCorporation = sysCorporationService.getCorporation(id);
			List<SysProduct> productList = sysProductService.getLicenseProduct(id);
			
			String dateTime = df.format(sysCorporation.getCreateDate());
			
			String xmlFileName = sysCorporation.getCode()+dateTime+".License";
			Document document = licenseFileXML(sysCorporation,productList);
			//设置文件编码  
	        OutputFormat format = new OutputFormat(); 
	        
	        format.setEncoding("UTF-8"); 
	        // 设置换行 
	        format.setNewlines(true); 
	        // 生成缩进 
	        format.setIndent(true); 
	        // 使用4个空格进行缩进, 可以兼容文本编辑器 
	        format.setIndent("    ");
	        OutputStreamWriter out = null;
			String xmlPath = request.getSession().getServletContext().getRealPath("/") + "/static/xmlfile";
			File file = new File(xmlPath);
			if(!file.exists()){
				file.mkdir();
			}
			String newXmlPath = xmlPath+"/"+xmlFileName;
	        try {
	        	out = new OutputStreamWriter(new FileOutputStream(newXmlPath),"UTF-8");
	        	XMLWriter writer = new XMLWriter(out, format);
	        	writer.write(document);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            if (out!=null) {
	                try {
	                    out.close();
	                    //文件加密
	    	        	String credentialName = AESUtil.encryptfile(newXmlPath,sysCorporation);
	    	        	sysCorporation.setCredentialName(credentialName);
	    	        	sysCorporationService.addCredentialName(sysCorporation);
	    	        	return ApiResult.success();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ApiResult.success();
	}
	
	public Document licenseFileXML(SysCorporation sysCorporation,List<SysProduct> productList)throws IOException{       
		try {
			Document document = DocumentHelper.createDocument();
	        Element root = document.addElement("License");
	        root.addAttribute("Status", "0");
	        root.addAttribute("Ver", "1.0");
	        Element regInfo = root.addElement("RegInfo");
    		regInfo.addAttribute("CheckMode","3");
    		regInfo.addAttribute("Edition","beta版");
	    	regInfo.addAttribute("Code",sysCorporation.getCode());
	        regInfo.addAttribute("Name",sysCorporation.getName());
	        regInfo.addAttribute("Type",sysCorporation.getType().toString());
	        regInfo.addAttribute("Svrsdate",DateUtils.formatDate(sysCorporation.getSvrsdate(),"yyyy-MM-dd"));
	        regInfo.addAttribute("Svredate",DateUtils.formatDate(sysCorporation.getSvredate(),"yyyy-MM-dd"));
	        regInfo.addAttribute("Accsdate",DateUtils.formatDate(sysCorporation.getAccsdate(),"yyyy-MM-dd"));
	        regInfo.addAttribute("Accedate",DateUtils.formatDate(sysCorporation.getAccedate(),"yyyy-MM-dd"));
	        regInfo.addAttribute("Svrtype",sysCorporation.getSvrtype());
	        regInfo.addAttribute("ExpireDay",sysCorporation.getExpireDay().toString());
	        regInfo.addAttribute("Dogid",sysCorporation.getDogid());
	        
	        Element product = root.addElement("Product");
	        Element components = root.addElement("Components");
			for (SysProduct sysProduct : productList) {
				if(sysProduct.getId() != 0){
					 product.addElement("Ie")
					 .addAttribute("ID", sysProduct.getId().toString())
					 .addAttribute("Code", sysProduct.getProductCode())
					 .addAttribute("Name", sysProduct.getProductName());
					 for (SysProduct productChild : sysProduct.getProductList()) {
						 components.addElement("Ie")
						 .addAttribute("ID", productChild.getId().toString())
						 .addAttribute("ProductID", productChild.getParentid().toString())
						 .addAttribute("Code", productChild.getProductCode())
						 .addAttribute("Name", productChild.getProductName());
					}
				}
			}
	        return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 文件下载
	 * @Title: downloadLocal   
	 * @Description: TODO(方法功能描述)
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws FileNotFoundException
	 * @return: void      
	 * @throws
	 */
	@RequestMapping(value="/dowloadLicenseFile")
	public String downloadLocal(Integer id,String t,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException {
		try {
			SysCorporation sysCorporation = sysCorporationService.getCorporation(id);
			if(sysCorporation != null){
				// 下载本地文件
				String xmlFileName = sysCorporation.getCredentialName()+".License";
				String xmlPath = request.getSession().getServletContext().getRealPath("/") + "/static/xmlfile/"+xmlFileName;
				File file = new File(xmlPath);
				if(file.exists()){
					xmlFileName = URLEncoder.encode(xmlFileName,"UTF-8");
					xmlFileName = xmlFileName.replace("%5B", "[");
					xmlFileName = xmlFileName.replace("%5D", "]");
					 // 读到流中
			        InputStream inStream = new FileInputStream(xmlPath);// 文件的存放路径
			        // 设置输出的格式
			        response.reset();
			        response.setContentType(request.getServletContext().getMimeType(xmlFileName));
			        response.addHeader("Content-Disposition", "attachment; filename=\"" + xmlFileName + "\"");
			        // 循环取出流中的数据
			        byte[] b = new byte[100];
			        int len;
		            while ((len = inStream.read(b)) > 0){
		            	response.getOutputStream().write(b, 0, len);
		            }
	            	inStream.close();
	            	logger.info("授权文件下载==========================》授权文件下载成功");
				}else{
					logger.info("授权文件下载==========================》文件不存在");
					if(!StringUtils.isEmpty(t)){
						return "redirect:../license/blocLicenseList?error="+t+"&pid="+sysCorporation.getParentid();
					}else{
						return "redirect:../license/enterpriseLicenseList?error=error";
					}
				}
			}
			
        } catch (IOException e) {
        	logger.info("授权文件下载失败:"+e);
            e.printStackTrace();
        }
		return null;
    }
	
}
