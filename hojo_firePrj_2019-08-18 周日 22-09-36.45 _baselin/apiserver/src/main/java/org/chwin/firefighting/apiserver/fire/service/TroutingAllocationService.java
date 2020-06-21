package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TroutingAllocationService {

    @Autowired
    private JdbcDao jdbcDao;

    @Resource
    private ScriptComponent scriptCmp;

    public List getRountingEvent(Map paramMap, String url){

        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", paramMap);
        List rountList = (List) scriptCmp.excute(getRuleByUrl(url),params);
        return rountList;
    }

    public List getSafePerson(Map paramMap){
        List childUserList = jdbcDao.loadListByMap("rount_event_manger",paramMap);
        return childUserList;
    }

    public Map saveTrounInfo(Map param, String url){
        Map params = new HashMap();
        params.put("db",jdbcDao);
        params.put("prm",param);
        Map result = (Map) scriptCmp.excute(getRuleByUrl(url),params);
        return result;
    }


    /**
     * 跳转URL对应的js文件
     * @param url
     * @return
     */
    private String getRuleByUrl(String url){
        return "pub.rpt.iot."+url;
    }
}
