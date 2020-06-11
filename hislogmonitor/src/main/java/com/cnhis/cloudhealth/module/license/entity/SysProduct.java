package com.cnhis.cloudhealth.module.license.entity;

import java.util.List;

import com.cnhis.cloudhealth.common.persistence.DataEntity;


public class SysProduct extends DataEntity<SysProduct>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String productName; //产品名称
	private String productCode; //产品CODE
	private Integer parentid; //父节点ID
	private String isChecked; //当前节点是否被选中 true 选中 false 否
	
	private List<SysProduct> productList;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public List<SysProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<SysProduct> productList) {
		this.productList = productList;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
}
