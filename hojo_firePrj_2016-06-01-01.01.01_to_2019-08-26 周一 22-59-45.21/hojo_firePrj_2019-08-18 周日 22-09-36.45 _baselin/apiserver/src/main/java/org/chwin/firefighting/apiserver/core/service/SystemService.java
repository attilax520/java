package org.chwin.firefighting.apiserver.core.service;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 总体说明
 * <p>具体说明</p>
 *
 * @author cailm
 * @version $Id: org.chwin.firefighting.apiserver.core.service.SystemService,v 0.1 2018/6/23 5:55 Exp $$
 */
@Service
public class SystemService {
    @Autowired
    private JdbcDao dao;
    @Autowired
    private CacheService cache;

    public Map getUserInfo(Map sparm){
        Map info=new HashMap();
        //info.put("uinfo",dao.loadRowByCode("sys_online_user_info",sparm));
        if(sparm.get("sid")!=null){
            return cache.findUInfo((String)sparm.get("sid"));
        }
        return info;
    }

    @Transactional
    public Map doLogin(Map sparm){

        Map param = new HashMap(){{
            put("ACCOUNT",sparm.get("uname"));
        }};
        Map uinfo=dao.loadRowByCode("pub_user_qry",param);

        if (uinfo == null) {
            throw new BusinessException("用户名错误");
        }
        Integer lgtimes=(Integer)uinfo.get("LOGIN_TIMES");
        if(lgtimes==null) lgtimes=0;
        if(lgtimes>3){
            throw new BusinessException("账户已经被锁定,请联系管理员");
        }
        String password=(String)uinfo.get("PASSWORD");
        String salt=(String)uinfo.get("SALT");
        String pwd=(String)sparm.get("pwd");
        Integer finalLgtimes = lgtimes;
        Map logtimes=new HashMap(){{
            put("ID",uinfo.get("ID"));
            put("LOGIN_TIMES", finalLgtimes +1);
            put("TENANT_ID",uinfo.get("TENANT_ID"));
        }};

        if(!StringUtils.equals(Encryption.encryptPWD(pwd+salt),password)){
            dao.update("SYS_USER",logtimes);
            return null;
        }
        logtimes.put("LOGIN_TIMES",0);
        dao.update("SYS_USER",logtimes);

        param.clear();
        param.put("USER_ID",uinfo.get("ID"));
        dao.excuteByCode("pub_online_rmv",param);

        Map online=new HashMap();
        online.put("USER_ID",uinfo.get("ID"));
        online.put("USER_NAME",uinfo.get("NAME"));
        dao.add("SYS_ONLINE_USER",online);

        //获取当前登录账号的角色信息
        Map roleMaps = dao.loadRowByCode("query_role_by_login_user", param);
        //System.out.print("角色信息AAA: "+roleMaps.get("ROLE_ID"));
        if(roleMaps != null &&  roleMaps.size() != 0){
            online.put("ROLE_ID", roleMaps.get("ROLE_ID"));
        }
//        Map map = new HashMap();
//        map.put("ID", uinfo.get("ID"));
//        Map unitObj = dao.loadRowByCode("GET_UNIT_ID_BY_USERID", map);
//        if(unitObj != null){
//            online.put("UNIT_ID",uinfo.get("UNIT_ID"));  //根据当前登录信息获取单位信息
//        }
        //不同的租户中心点位置不一样
        online.put("LONGITUDE",uinfo.get("LONGITUDE"));
        online.put("LATITUDE",uinfo.get("LATITUDE"));
        online.put("IS_SUPER_ADMIN",uinfo.get("IS_SUPER_ADMIN"));
        online.put("WS_URL", SpringUtil.getProp("websocket.ws_url")+"/"+uinfo.get("ROLE_ID"));
        online.put("TENANT_ID", uinfo.get("TENANT_ID"));
        //ThreadVariable.getInstance().put(CONSTANTS.REQ_UKEY, uinfo);
        return online;
    }

    @Transactional
    public void doLogOut(Map sparm){
        Map online=new HashMap();
        online.put("ID",sparm.get("sid"));
        dao.remove("SYS_ONLINE_USER",online);
        //ThreadVariable.getInstance().remove(CONSTANTS.REQ_UKEY);
    }

}
