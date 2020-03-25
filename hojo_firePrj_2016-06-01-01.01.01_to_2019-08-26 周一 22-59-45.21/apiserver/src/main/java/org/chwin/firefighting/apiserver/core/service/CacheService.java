package org.chwin.firefighting.apiserver.core.service;

import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.bean.CacheAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by liming on 2017/9/30.
 *
 * @see CacheAspect
 * 缓存切面，监控使用缓存的数据请求，优先加载缓存
 */

@Component
public class CacheService {
    @Autowired
    private JdbcDao dao;

    @Autowired
    ScriptComponent scriptComponent;

    public Map findUInfo(String sid) {
        Map param = new HashMap(){{
            put("SID", sid);
        }};

        Map result=new HashMap();
        Map uinfo = dao.loadRowByCode("sys_online_user_info", param);
        if(uinfo==null)
            throw new BusinessException("用户信息不存在，请重新登录！");
        result.put("uinfo",uinfo);
        result.put("ID",uinfo.get("ID"));
        result.put("NAME",uinfo.get("NAME"));
        result.put("IS_SUPER_ADMIN",uinfo.get("IS_SUPER_ADMIN"));

        Map map = new HashMap();
        map.put("ID", uinfo.get("ID"));
        Map unitObj = dao.loadRowByCode("GET_UNIT_ID_BY_USERID", map);
        if(unitObj != null){
            result.put("UNIT_ID",uinfo.get("UNIT_ID"));  //根据当前登录信息获取单位信息
        }
        result.put(CONSTANTS.TENANT_ID,uinfo.get(CONSTANTS.TENANT_ID));
        param.clear();
        param.put("USER_ID",uinfo.get("ID"));
        List perms=dao.loadListByCode("sys_permission_list",param);
        result.put("perms",perms);
        return result;
    }

    /**
     * 日志规则查询
     *
     * @return 日志规则记录
     */
    public Map<String, Object> findLogCfg(Map<String, String> param) {
       try {
           return dao.loadRowByCode("log_cfg_query", param);

       }catch (Exception e){
           return null;
       }
      }

    public String findSysParams(@NotNull String parameterCode) {
        Map paramKey = dao.loadRowByCode("param_query_by_code", new HashMap() {{
            put("CODE", parameterCode);
        }});
        if (paramKey == null || paramKey.isEmpty()) {
            return null;
        } else {
            return (String) paramKey.get("VALUE");
        }
    }
}
