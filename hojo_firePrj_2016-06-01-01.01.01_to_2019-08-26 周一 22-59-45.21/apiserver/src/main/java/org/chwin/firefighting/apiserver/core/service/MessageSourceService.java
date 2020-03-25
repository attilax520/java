package org.chwin.firefighting.apiserver.core.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * 国际化消息服务
 *
 * @author wangjunma
 * @version $Id: MessageSourceService,v 0.1 2017/10/13 10:58 Exp $$
 */
@Component
public class MessageSourceService {
    @Resource
    private MessageSource messageSource;

    /**
     * 根据code 获取消息
     * @param code ：对应messages配置的key.
     * @return
     */
    public String getMessage(String code){
        return getMessage(code,null);
    }

    /**
     * 根据code和数组参数获取消息
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public String getMessage(String code,Object[] args){
        return getMessage(code, args,"");
    }


    /**
     * 根据用户浏览器语言获取消息
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public String getMessage(String code,Object[] args,String defaultMessage){
        //获取语言
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
