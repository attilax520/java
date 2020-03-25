package org.chwin.firefighting.apiserver.core.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.util.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liming on 2017/9/15.
 */
public class BaseSupport {

    public String getDataformat() {
        return SpringUtil.getProp("dataformat");
    }

    protected String output(Object detail) {
        RspBody result = new RspBody(1, RspBody.SUMESSAGE, detail);
        return result.toJson();
    }

    protected String output() {
        return this.output(null);
    }

    public Map getInputJSON(HttpServletRequest request) {
        Map mp = new HashMap();
        try {
            InputStream in = request.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, CONSTANTS.UTF8));
            StringBuffer buffer = new StringBuffer();
            char[] buff = new char[2048];
            int bytesRead;
            while (-1 != (bytesRead = bf.read(buff, 0, buff.length))) {
                buffer.append(buff, 0, bytesRead);
            }
            return JSONParser.json2Map(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mp;
    }

    protected void putCookie(String key, String value, HttpServletResponse resp) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    protected Map getInputParam() {
        return (Map) ThreadVariable.getInstance().get("_INPUT_DATA");
    }


    public void putInputParam(Map param) {
        ThreadVariable.getInstance().put("_INPUT_DATA", param);
    }


    /**
     * 获取Code内容
     *
     * @return String
     */
    protected String getInputCode() {
        Map inputParam = getInputParam();
        return (String) inputParam.get("CODE");
    }

    /**
     * 获取DATA内容
     *
     * @return Map
     */
    public Map getInputData() {
        Map inputParam = getInputParam();
        if (inputParam == null || !inputParam.containsKey("DATA")) {
            return new HashMap<>(2);
        } else {

            return (Map) inputParam.get("DATA");
        }

    }


}
