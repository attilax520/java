package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class IndexService {
    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public Object doProcess(Map param){
        Map map = new HashMap();
        map.put("prm", param);
        map.put("db", jdbcDao);
        String url = (String)param.get("url");
        Object result =  scriptCmp.excute(getRuleByUrl(url), map);
        return result;
    }

    //线程池任务，扫描巡检任务的完成情况
    @Async("getAsyncExecutor")
    @Transactional
    public void routingTask(){
        //获得状态为1即未开始的，状态 1->3
        List<Map> list = jdbcDao.loadListByCode("SP_GET_UNFINISHED", null);
        for (int i=0;i<list.size();i++){
            Map m = list.get(i);
            m.put("ROUTING_STATUS","3");//超时的任务状态置为3
            jdbcDao.update("DEVICE_ROUTING",m);
            System.out.println("巡检任务" + m.get("ID") + "超时了。");
        }
        //获得状态为0即未开始的，状态 0->1
        List<Map> unBegin = jdbcDao.loadListByCode("SP_GET_UNBEGIN", null);
        for (int i=0;i<unBegin.size();i++){
            Map m = unBegin.get(i);
            m.put("ROUTING_STATUS","1");//开始的任务状态置为1
            jdbcDao.update("DEVICE_ROUTING",m);
            System.out.println("巡检任务" + m.get("ID") + "开始了。");
        }
    }


    private String getRuleByUrl(String url){
        return "pub.cc.index." + url;
    }
}
