package com.cnhis.cloudhealth.module.license.entity;

import com.cnhis.cloudhealth.common.persistence.DataEntity;

public class SysLicense extends DataEntity<SysLicense>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer productId;//产品ID
	private Integer corporationId;//医院ID
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getCorporationId() {
		return corporationId;
	}
	public void setCorporationId(Integer corporationId) {
		this.corporationId = corporationId;
	}
	
	

}
