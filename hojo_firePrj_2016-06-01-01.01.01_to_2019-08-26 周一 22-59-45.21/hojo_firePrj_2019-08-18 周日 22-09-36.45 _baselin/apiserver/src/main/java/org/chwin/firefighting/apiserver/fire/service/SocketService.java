package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SocketService {
    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public Object doProcess(Map param){
        Map params = new HashMap();
        String url = (String)param.get("url");
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap =  scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }
    private String getRuleByUrl(String url){
        return "pub.cc.socket." + url;
    }
}
