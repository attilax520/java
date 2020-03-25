package org.chwin.firefighting.apiserver.core.dto;

import java.util.HashMap;
import java.util.Map;

import org.chwin.firefighting.apiserver.core.service.MessageSourceService;
import org.chwin.firefighting.apiserver.core.util.DateUtil;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.JSONParser;

/**
 * Created by liming on 2017/9/15.
 */
public class RspBody {
    public static int SUCCESS = 1;
    public static int EXCEPT = 0;
    public static int ERROR = -2;
    private static MessageSourceService messageSourceService = SpringUtil.getBean("messageSourceService", MessageSourceService.class);
    public static String SUMESSAGE = new String(messageSourceService.getMessage("OPERATE.SUCCESS"));

    private int code;
    private String message;
    private Object detail;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public RspBody(int code, String message, Object detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public RspBody(int code, String message) {
        this(code, message, null);
    }

    public RspBody(Object detail) {
        this(SUCCESS, SUMESSAGE, detail);
    }

    public RspBody() {
        this(SUMESSAGE);
    }

    public String toJson() {
        Map result = new HashMap();
        Map rtn = new HashMap();
        rtn.put("errcode", code);
        rtn.put("msg", message);
        rtn.put("data", detail);
        rtn.put("timestamp", DateUtil.getTime(null));
        return JSONParser.obj2Json(rtn);
    }

    public String output() {
        return toJson();
    }
}
