package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ceshiService {

    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public List getRouting(){
       List list=(List) jdbcDao.loadListByCode("device_routing_query",new HashMap());
        return list;
    }

    public List getRoutingEvent(String rountId){
        Map param=new HashMap();
        param.put("TROUNT_ID",rountId);
        List list=(List)jdbcDao.loadListByCode("device_routing_id_query",param);
        for(int i=0;i<list.size();i++) {
            Map listMap = (Map) list.get(i);
            list.remove(i);
            List result = (List) jdbcDao.loadListByCode("device_isrounted", listMap);
            Map resultMap = (Map) result.get(0);
            if ((resultMap.get("STATUS")!=null)) {
                if (Float.parseFloat(resultMap.get("STATUS").toString()) >= 1.0) {
                    listMap.put("STATUS", 1);
                } else {
                    listMap.put("STATUS", 0);
                }
                list.add(listMap);
            }else{
                listMap.put("STATUS",2);
                list.add(i,listMap);
            }
        }
        return list;
    }

    public List getDevice(String rountEventId){
        Map map=new HashMap();
        map.put("TROUNT_EVENT_ID",rountEventId);
        List list=(List) jdbcDao.loadListByCode("rounted-device",map);
        return list;
    }

    public Object getRoutingJs(Map param){
        String url=(String)param.get("url");
        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap = scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }
    public Object getRoutingEvent(Map param){
        String url=(String)param.get("url");
        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap = scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }

    public Object getDevicejs(Map param){
        String url=(String)param.get("url");
        Map params = new HashMap();
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap = scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }
    private String getRuleByUrl(String url){
        return "pub.rpt.iot."+url;
    }
}
