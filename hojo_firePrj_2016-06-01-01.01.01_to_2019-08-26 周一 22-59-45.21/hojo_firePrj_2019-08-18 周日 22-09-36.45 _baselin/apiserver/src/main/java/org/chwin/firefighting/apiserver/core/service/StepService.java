package org.chwin.firefighting.apiserver.core.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liming on 2017/9/18.
 */

@Service
public class StepService {

    @Autowired
    private JdbcDao dao;

    @Autowired
    private ScriptComponent scriptCmp;

    public List qry(Map prm){
        String code = (String)prm.get("CODE");
        Map data=(Map)prm.get("DATA");
        return dao.loadListByCode(code, data);
    }

    public Map qryp(String code,Map prm){
        return dao.loadPage(code,prm,prm);
    }

    @Transactional
    public Map dui(Map prm){
        String clz=(String)prm.get("CLZ");
        String code=(String)prm.get("CODE");
        String script=(String)prm.get("SCRIPT");
        Map data=(Map)prm.get("DATA");
        Map rtn=new HashMap();
        switch(code){
            case "ADD":
                rtn=dao.add(clz,data);
                break;
            case "UPDATE":
                rtn=dao.update(clz,data);
                break;
            case "DEL":
                dao.del(clz,data);
                rtn.putAll(data);
                break;
            case "REMOVE":
                dao.remove(clz,data);
                rtn.putAll(data);
                break;
            case "RULE":
                Map params=new HashMap();
                params.put("db",dao);
                params.put("prm",data);
                //Object result=JScript.getInstance().execute(script,params);
                rtn.put("RULE",scriptCmp.excute(script,params));
                break;
        }
        return rtn;
    }
}
