package com.cnhis.cloudhealth.common.result.handler;

/**
 * 业务处理返回接口
 * @Title:  HandlerResult.java   
 * @Package com.cnhis.cloudhealth.common.result.handler   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年1月30日 下午3:45:01   
 * @version V1.0
 */
public interface HandlerResult {

	/**
	 * 获取业务返回状态
	 * 
	 * @return
	 */
	public HandlerStatus getStatus();

	/**
	 * 获取业务返回编码
	 * 
	 * @return
	 */
	public int getCode();

	/**
	 * 获取业务返回消息
	 * 
	 * @return
	 */
	public String getMessage();
	
}
