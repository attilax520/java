package com.cnhis.cloudhealth.module.license.entity;

import java.util.Date;

import com.cnhis.cloudhealth.common.persistence.DataEntity;


public class SysCorporation extends DataEntity<SysCorporation>{
	
	
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
	private String dogid; //加密狗ID
	private Date accsdate; //授权开始时间
	private Date accedate; //授权结束时间
	private Integer expireDay; //到期天数
	private Integer provincial; //地区
	private Integer day;
	private String token; //子目录校验token
	
	
	
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
	public String getDogid() {
		return dogid;
	}
	public void setDogid(String dogid) {
		this.dogid = dogid;
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
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
