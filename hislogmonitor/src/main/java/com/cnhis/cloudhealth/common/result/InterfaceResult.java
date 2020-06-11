package com.cnhis.cloudhealth.common.result;

import java.io.Serializable;

import com.cnhis.cloudhealth.common.result.business.HandlerError;
import com.cnhis.cloudhealth.common.result.business.HandlerSuccess;
import com.cnhis.cloudhealth.common.result.handler.HandlerAbnormal;
import com.cnhis.cloudhealth.common.result.handler.HandlerRepeat;
import com.cnhis.cloudhealth.common.result.handler.HandlerResult;
import com.cnhis.cloudhealth.common.result.handler.HandlerStatus;
import com.cnhis.cloudhealth.common.utils.GsonUtils;

/**
 * 接口返回结果
 * 
 * @author huchaojing
 * 
 * @see 用于接口处理返回结果
 */
public class InterfaceResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private HandlerResult returnCode;// 业务处理编码
	private Object data;// 业务返回数据

	public InterfaceResult() {
	}

	public InterfaceResult(HandlerResult returnCode) {
		this.returnCode = returnCode;
	}

	public InterfaceResult(HandlerResult returnCode, Object data) {
		this.returnCode = returnCode;
		this.data = data;
	}

	public HandlerResult getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(HandlerResult returnCode) {
		this.returnCode = returnCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public HandlerStatus getStatus() {
		return returnCode.getStatus();
	}

	public int getCode() {
		return returnCode.getCode();
	}

	public String getMessage() {
		return returnCode.getMessage();
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		if (HandlerStatus.SUCCESS == returnCode.getStatus()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否异常
	 * 
	 * @return
	 */
	public boolean isError() {
		if (HandlerStatus.ERROR == returnCode.getStatus()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否失败
	 * 
	 * @return
	 */
	public boolean isFailed() {
		if (HandlerStatus.FAILED == returnCode.getStatus()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 处理成功
	 * 
	 * @return
	 */
	public static InterfaceResult success() {
		InterfaceResult handlerResult = new InterfaceResult(HandlerSuccess.SUCCESS);
		return handlerResult;
	}

	/**
	 * 处理成功
	 * 
	 * @param data 业务返回数据
	 * @return
	 */
	public static InterfaceResult success(Object data) {
		InterfaceResult handlerResult = new InterfaceResult(HandlerSuccess.SUCCESS, data);
		return handlerResult;
	}

	/**
	 * 处理异常
	 * 
	 * @return
	 */
	public static InterfaceResult error() {
		InterfaceResult handlerResult = new InterfaceResult(HandlerError.ERROR);
		return handlerResult;
	}
	
	/**
	 * 处理异常
	 * @param errorMsg
	 * @return
	 */
	public static InterfaceResult error(Object errorMsg) {
		InterfaceResult handlerResult = new InterfaceResult(HandlerError.ERROR,errorMsg);
		return handlerResult;
	}

	/**
	 * 处理失败
	 * 
	 * @param result 失败结果
	 * @return
	 */
	public static InterfaceResult failed(HandlerResult result) {
		InterfaceResult handlerResult = new InterfaceResult(result);
		return handlerResult;
	}

	/**
	 * 处理失败
	 * 
	 * @param result 失败结果
	 * @param data 业务返回数据
	 * @return
	 */
	public static InterfaceResult failed(HandlerResult result, Object data) {
		InterfaceResult handlerResult = new InterfaceResult(result, data);
		return handlerResult;
	}

	/**
	 * 重复请求
	 * 
	 * @return
	 */
	public static InterfaceResult repeat() {
		InterfaceResult handlerResult = new InterfaceResult(HandlerRepeat.REPEAT);
		return handlerResult;
	}
	
	/**
	 * 非法请求
	 * 
	 * @return
	 */
	public static InterfaceResult abnormal() {
		InterfaceResult handlerResult = new InterfaceResult(HandlerAbnormal.ABNORMAL);
		return handlerResult;
	}
	
	
	public String toString(){
		return GsonUtils.toJson(this);
	}
}
