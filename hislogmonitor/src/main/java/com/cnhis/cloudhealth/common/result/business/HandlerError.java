package com.cnhis.cloudhealth.common.result.business;

import com.cnhis.cloudhealth.common.result.handler.HandlerResult;
import com.cnhis.cloudhealth.common.result.handler.HandlerStatus;

/**
 * 处理异常定义
 * 
 * @author xiaohui.pu
 *
 */
public enum HandlerError implements HandlerResult {
	ERROR(HandlerStatus.ERROR, 500, "系统异常");

	private final HandlerStatus status;
	private final int code;
	private final String message;

	private HandlerError(HandlerStatus status, int code, String message) {
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
