package org.chwin.firefighting.apiserver.core.service;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.script.RuleProxyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 总体说明
 * <p>具体说明</p>
 *
 * @author cailm
 * @version $Id: RptService,v 0.1 2017/12/20 18:30 Exp $$
 */
@Service
public class RptService {
    @Autowired
    private JdbcDao dao;

    @Autowired
    private ScriptComponent scriptCmp;

    public String openCfg(String id,String type){
        String result="";
        if(StringUtils.equalsIgnoreCase(id,"0")){
            String template=new RuleProxyClient().readTemplate("pub.vm."+type);
            result=template;
        }else{

        }
        return result;
    }

    public Object pvwDataset(Map data){
        String TYPE = (String)data.get("TYPE");
        String PGFLG= (String)data.get("PGFLG");
        String fn=(String)data.get("FN_CODE");
        String DST_CODE=(String)data.get("DST_CODE");
        String DST_ID=(String)data.get("DST_ID");
        Map prm=(Map)data.get("PRM");

        if(!StringUtils.isBlank(PGFLG)){
            if(data.containsKey("start")) prm.put("start",data.get("start"));
            if(data.containsKey("limit")) prm.put("limit",data.get("limit"));
            if(data.containsKey("tcount"))prm.put("tcount",data.get("tcount"));
        }

        if(StringUtils.isNotBlank(DST_CODE)){
            Map pmp=new HashMap(){{
                put("CODE",DST_CODE);
            }};
            Map dmap=dao.loadRowByCode("data_set_query",pmp);
            if(dmap==null || dmap.isEmpty())
                throw new BusinessException(DST_CODE+"数据集不存在!");

            TYPE=(String)dmap.get("TYPE");
            fn=(String)dmap.get("FN_CODE");
        }

        switch (TYPE){
            case "FN":
                return this.doPvwFn(fn,prm);
            case "SQL_CODE":
                if(StringUtils.isBlank(PGFLG))
                    return dao.loadListByCode(fn,prm);
                else {
                    Map page= dao.loadPage(fn,prm,prm);
                    if(page!=null) return page.get("list");
                }
            case "SCRIPT":
                break;
        }
        return null;
    }

    private Object doPvwFn(String fn,Map prm){
        Map params=new HashMap();
        params.put("db",dao);
        params.put("prm",prm);

        return scriptCmp.excute(fn,params);
    }
}
