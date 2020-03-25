package org.chwin.firefighting.apiserver.core.exception;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.service.MessageSourceService;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;


/**
 * Created by liming on 2017/9/15.
 */
public class BusinessException  extends RuntimeException {
    private String msg = "";
    private int code=-1;
    private static MessageSourceService messageSourceService = SpringUtil.getBean("messageSourceService", MessageSourceService.class);
    public BusinessException(String message) {
        super(message);
        msg = getMessageDetail(message,null);
    }
    /**
     * 业务异常
     * @param message 异常code，对应的message.properties
     * @param args 参数
     */
    public BusinessException(String message,String ... args) {
        super(message);
        this.msg =getMessageDetail(message,args);
    }
    public BusinessException(String message, int code) {
        super(message);
        this.msg =getMessageDetail(message,null);
        this.code=code;
    }


    /**
     * 业务异常
     * @param message 异常code，对应的message.properties
     * @param code 错误码
     * @param args 参数
     */
    public BusinessException(String message,int code,String ... args) {
        super(message);
        this.msg = getMessageDetail(message,args);
        this.code=code;
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        msg = getMessageDetail(message,null);
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public int getCode(){
        if(code==1) return 0;
        return code;
    }
    /**
     * 获取日志详细信息
     * @param message 异常信息
     * @param args 动态参数
     * @return 错误描述类型
     */
    private String getMessageDetail(String message, Object[] args) {
        String messageSource=messageSourceService.getMessage(message,args);
        if(StringUtils.isEmpty(messageSource)){
            messageSource =message;
        }
        return messageSource;
    }
}