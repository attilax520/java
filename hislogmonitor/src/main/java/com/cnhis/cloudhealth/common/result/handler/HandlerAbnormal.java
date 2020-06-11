package com.cnhis.cloudhealth.common.result.handler;


/**
 * 非法操作定义
 * @Title:  HandlerAbnormal.java   
 * @Package com.cnhis.cloudhealth.common.result.handler   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年1月30日 下午3:44:52   
 * @version V1.0
 */
public enum HandlerAbnormal implements HandlerResult {

	ABNORMAL(HandlerStatus.ABNORMAL, 300, "非法操作");

	private final HandlerStatus status;
	private final int code;
	private final String message;

	private HandlerAbnormal(HandlerStatus status, int code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public HandlerStatus getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
