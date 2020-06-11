package com.cnhis.cloudhealth.common.result.handler;


/**
 * 重复处理操作定义
 * @Title:  HandlerRepeat.java   
 * @Package com.cnhis.cloudhealth.common.result.handler   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年1月30日 下午3:44:45   
 * @version V1.0
 */
public enum HandlerRepeat implements HandlerResult {

	REPEAT(HandlerStatus.REPEAT, 100, "重复操作");

	private final HandlerStatus status;
	private final int code;
	private final String message;

	private HandlerRepeat(HandlerStatus status, int code, String message) {
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
