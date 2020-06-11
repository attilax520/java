package com.cnhis.cloudhealth.module.license.vo;

import java.util.Date;
import java.util.List;

import com.cnhis.cloudhealth.common.persistence.DataEntity;
import com.cnhis.cloudhealth.module.license.entity.SysCorporation;


/**
 * @Title:  SysBlocVo.java   
 * @Package com.cnhis.cloudhealth.module.license.vo   
 * @Description:   集团医院VO
 * @author: huchaojing     
 * @date:   2018年2月5日 下午2:30:01
 * @version V1.0
 */
public class SysBlocVo extends DataEntity<SysBlocVo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code; //CODE
	private String name; //名称
	private Integer parentid; //父节点ID
	private Integer type; //医院类型(0:集团医院,1:企业医院)
	private String blocName; //集团名称
	private String blocBrief; //集团简介
	private Date svrsdate; //服务起始
	private Date svredate; //服务终止
	private String svrtype ; //服务类型
	private Integer hospitalNum; //医院数量
	private String credentialName; //授权证书文件名
	private String mac; //机器MAC地址
	private Date accsdate; //授权开始时间
	private Date accedate; //授权结束时间
	private Integer expireDay; //到期天数
	private Integer provincial; //地区
	
	//查询条件
	private Integer day;
	
	List<SysCorporation> corporationList; //集团下的医院

	public String getBlocName() {
		return blocName;
	}

	public void setBlocName(String blocName) {
		this.blocName = blocName;
	}

	public String getBlocBrief() {
		return blocBrief;
	}

	public void setBlocBrief(String blocBrief) {
		this.blocBrief = blocBrief;
	}

	public Integer getHospitalNum() {
		return hospitalNum;
	}

	public void setHospitalNum(Integer hospitalNum) {
		this.hospitalNum = hospitalNum;
	}

	public List<SysCorporation> getCorporationList() {
		return corporationList;
	}

	public void setCorporationList(List<SysCorporation> corporationList) {
		this.corporationList = corporationList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getSvrsdate() {
		return svrsdate;
	}

	public void setSvrsdate(Date svrsdate) {
		this.svrsdate = svrsdate;
	}

	public Date getSvredate() {
		return svredate;
	}

	public void setSvredate(Date svredate) {
		this.svredate = svredate;
	}

	public String getSvrtype() {
		return svrtype;
	}

	public void setSvrtype(String svrtype) {
		this.svrtype = svrtype;
	}

	public String getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Date getAccsdate() {
		return accsdate;
	}

	public void setAccsdate(Date accsdate) {
		this.accsdate = accsdate;
	}

	public Date getAccedate() {
		return accedate;
	}

	public void setAccedate(Date accedate) {
		this.accedate = accedate;
	}

	public Integer getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(Integer expireDay) {
		this.expireDay = expireDay;
	}

	public Integer getProvincial() {
		return provincial;
	}

	public void setProvincial(Integer provincial) {
		this.provincial = provincial;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
}
