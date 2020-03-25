package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by liming on 2017/9/18.
 */
public class SqlParser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public String getPath() {
        return SpringUtil.getProp("spring.application.rule_path")+"/config/pub/sql";
    }

    private static SqlParser instance=null;
    private String encoding = CONSTANTS.UTF8;
    private boolean read=false;
    private Map sqlMap=null;

    public static synchronized SqlParser getInstance(){
        if(instance==null) {
            instance = new SqlParser();
        }

        return instance;
    }


    private Map getSqlContent(){
        Map rtn=new HashMap();
        if(SpringUtil.isDebug() || true){
            FileUtil.ls(getPath(),".xml").forEach(file -> {
                String xml=FileUtil.readTxt(file);
                rtn.putAll( JSONParser.xml2Map(xml) );
            });
        }
        return rtn;
    }

    private Map parse(){
        if(SpringUtil.isDebug() || sqlMap==null  ){
            sqlMap=getSqlContent();
        }
        return sqlMap;
    }

    public Map loadConfByCode(String code,Map param) {
        Map result=new HashMap();
        Map dqry=(Map)parse().get(code);
        if(dqry==null){
            throw new BusinessException(" SQL.NOT.EXIST "+code);
        }

        if (param == null) {
            param = new HashMap(1);
        }
        //添加机构权限过滤（数据机构权限）
        Map loginInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if (loginInfo != null && loginInfo.containsKey(CONSTANTS.REQ_DEPT)) {
            param.put(CONSTANTS.REQ_DEPT, loginInfo.get(CONSTANTS.REQ_DEPT));
        }
        if (loginInfo != null && loginInfo.containsKey(CONSTANTS.TENANT_ID)) {
            if(!loginInfo.get("IS_SUPER_ADMIN").equals("3")){
                param.put(CONSTANTS.TENANT_ID, loginInfo.get(CONSTANTS.TENANT_ID));
            }
        }
        if (loginInfo != null && loginInfo.containsKey("UNIT_ID")) {
            if(!loginInfo.get("IS_SUPER_ADMIN").equals("3")){
                if(loginInfo.get("UNIT_ID") != null && !"null".equals(loginInfo.get("UNIT_ID"))){
                    param.put("UNIT_ID", loginInfo.get("UNIT_ID"));
                    param.put("unit_id", loginInfo.get("UNIT_ID"));
                }
            }
        }
        String sql=(String)dqry.get("sql");
        String tsql=(String)dqry.get("tsql");
        String psql=(String)dqry.get("psql");

        StringBuffer dbuffer=new StringBuffer();
        Map params=(Map)dqry.get("params");

        if(params!=null) {
            Iterator iter = params.keySet().iterator();
            while(iter.hasNext()){
                String prm=(String)iter.next();
                Map item_mp=(Map)params.get(prm);

                String con=(String) item_mp.get("con");
                String type=(String)item_mp.get("type");

                if(StringUtils.isBlank(prm)){
                    dbuffer.append(con);
                    continue;
                }
                if(!param.containsKey(prm)) {
                    continue;
                }
                if (StringUtils.contains(con, ":?".trim())) {
                    dbuffer.append(StringUtils.replace(con, ":?".trim(), " :" + prm + " "));
                }else if (StringUtils.contains(con, ":".trim())) {
                    dbuffer.append(con).append(prm);
                }  else {
                    dbuffer.append(con);
                }
                //if(StringUtils.contains(con.toLowerCase(), " in ") || StringUtils.contains(con.toLowerCase(), " in(")){
                if(StringUtils.equals(type,CONSTANTS.TY_INT_ARR)  || StringUtils.equals(type,CONSTANTS.TY_CHAR_ARR)){
                    List vs;
                    Object plist = param.get(prm);
                    if(plist instanceof String) {
                        vs = JSONParser.json2List((String) plist);
                    } else {
                        vs = (List) plist;
                    }
                    List l = new ArrayList();
                    if( StringUtils.equals(type, CONSTANTS.TY_INT_ARR)){
                        for (Object v : vs) {
                            l.add(new Long(v.toString()));
                        }
                    }else {
                        l.addAll(vs);
                    }
                    param.put(prm, l);
                }else if(StringUtils.equals(type, CONSTANTS.TY_INT)){
                    Object pv = param.get(prm);
                    if(pv instanceof String) {
                        pv = new Long((String) pv);
                    }
                    param.put(prm, pv);
                }else if(StringUtils.equals(type, CONSTANTS.TY_DATE)){
                    Object pv = param.get(prm);
                    if(!(pv instanceof Date)) {

                        param.put(prm, DateUtil.parse((String)param.get(prm)));
                    }
                }
            }
        }

        if(!StringUtils.isBlank(sql)){
            sql = StringUtils.replace(sql, "{1}", dbuffer.toString());
            result.put("sql",sql);
        }

        if(!StringUtils.isBlank(psql)){
            psql=StringUtils.replace(psql, "{1}", dbuffer.toString());
            result.put("psql",psql);
        }

        if(!StringUtils.isBlank(tsql)){
            tsql=StringUtils.replace(tsql, "{1}", dbuffer.toString());
            result.put("tsql", tsql);
        }
        return result;
    }
}