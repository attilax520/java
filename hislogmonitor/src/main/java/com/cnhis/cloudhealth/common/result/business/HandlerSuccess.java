package com.cnhis.cloudhealth.common.result.business;

import com.cnhis.cloudhealth.common.result.handler.HandlerResult;
import com.cnhis.cloudhealth.common.result.handler.HandlerStatus;

/**
 * 处理成功定义
 * 
 * @author xiaohui.pu
 *
 */
public enum HandlerSuccess implements HandlerResult {
	SUCCESS(HandlerStatus.SUCCESS, 200, "操作成功");

	private final HandlerStatus status;
	private final int code;
	private final String message;

	private HandlerSuccess(HandlerStatus status, int code, String message) {
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
