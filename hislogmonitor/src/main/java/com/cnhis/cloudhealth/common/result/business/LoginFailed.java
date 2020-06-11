package com.cnhis.cloudhealth.common.result.business;

import com.cnhis.cloudhealth.common.result.handler.HandlerResult;
import com.cnhis.cloudhealth.common.result.handler.HandlerStatus;

public enum LoginFailed implements HandlerResult{
	
	SYSUSER_ERROR_FAIL(HandlerStatus.FAILED,10000,"请输入登录信息"),
	ACCOUNT_NULL_FAIL(HandlerStatus.FAILED,10001,"账户名不能为空"),
	PASSWORD_NULL_FALL(HandlerStatus.FAILED,10002,"密码不能为空"),
	OLDPASSWORD_NULL_FALL(HandlerStatus.FAILED,10003,"原密码不正确"),
	ACCOUNT_OR_PASSWORD_ERROR_FAIL(HandlerStatus.FAILED,10004,"账户或者密码错误"),
	UPDATE_ERROR_FAIL(HandlerStatus.FAILED,10005,"参数异常"),
	LICENSE_ERROR(HandlerStatus.FAILED,20001,"授权失败"),
	;
	
	
	private final HandlerStatus status;
    private final int code;
    private final String message;
    
    private LoginFailed(HandlerStatus status,int code, String message) {
    	this.status = status;
        this.code = code;
        this.message = message;
    }
    
    public HandlerStatus getStatus(){
    	return status;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
