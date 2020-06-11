package com.cnhis.cloudhealth.common.result.handler;

/**
 * 业务处理状态
 * 
 * @author huchaojing
 */
public enum HandlerStatus {
	/**
	 * 处理成功
	 */
	SUCCESS,
	/**
	 * 处理失败
	 */
	FAILED,
	/**
	 * 处理异常
	 */
	ERROR,
	/**
	 * 重复处理
	 */
	REPEAT,
	/**
	 * 非法请求
	 */
	ABNORMAL;
}
