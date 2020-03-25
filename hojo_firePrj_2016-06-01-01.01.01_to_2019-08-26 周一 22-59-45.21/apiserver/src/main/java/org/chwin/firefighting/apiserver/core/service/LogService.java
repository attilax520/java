package org.chwin.firefighting.apiserver.core.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统日志信息实现
 * <p>具体说明</p>
 *
 * @author wangjunma
 * @version $Id: LogService,v 0.1 2017/10/19 10:49 Exp $$
 */
@Service
public class LogService {
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private ScriptComponent scriptCmp;
    /**
     * 日志记录
     */
    private LogUtil logger = new LogUtil(this.getClass());

    /**
     * 新增操作日志 (有日志规则的添加)
     * @param resultMap 日志模板内容
     * @param content   日志内容
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addLog(Map<String, Object> resultMap, Object content) throws BusinessException {
        try {
            String description = resultMap.get("DESCRIPTION") != null ? resultMap.get("DESCRIPTION").toString() : "";
            Map<String, Object> result = new HashMap(4);
            result.put("description", description);
            result.put("content", content);
            String logContent = JSONParser.obj2Json(result);
            //获取登陆的用户信息
            Map uInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
            if (uInfo == null){
                uInfo = jdbcDao.loadRowByCode("pub_user_qry",(Map)content);
            }
            String requestIp = (String) ThreadVariable.getInstance().get(CONSTANTS.REQ_IP);
            Map<String, Object> logParaMap = new HashMap(16);
            logParaMap.put("LOG_CFG_ID", resultMap.get(CONSTANTS.C_PK));
            logParaMap.put("USER_ID", uInfo == null ? "" : uInfo.get("ID"));
            if(resultMap.containsKey("ID")){
                if("1".equals(resultMap.get("ID")) || "2".equals(resultMap.get("ID")) ){
                    logParaMap.put("STATUS", "2");
                }
            }
            logParaMap.put("REQUEST_IP", requestIp);
            //logParaMap.put("TYPE", resultMap.get("ACTION"));
            logParaMap.put("TYPE", description);
            logParaMap.put("USER_NAME", uInfo == null ? "" : uInfo.get("NAME"));
            logParaMap.put("DESCRIPTION", logContent);
            return jdbcDao.add("SYS_LOG", logParaMap);
        } catch (Exception ex) {
            logger.error("日志新增失败：" + ExceptionUtil.getStackTraceAsString(ex));
        }
        return null;
    }

    /**
     * 新增操作日志 (无日志规则的添加)
     */
    @Transactional(rollbackFor = Exception.class)
    public void addLogWithoutRule(Map<String, Object> logParaMap) throws BusinessException {
        try {
            Map contentMap = (Map) ThreadVariable.getInstance().get("_INPUT_DATA");
            //获取登陆的用户信息
            Map uInfo = (Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
            if (uInfo == null){
                uInfo = jdbcDao.loadRowByCode("pub_user_qry",contentMap);
            }
            logParaMap.put("LOG_CFG_ID","0");
            logParaMap.put("USER_ID", uInfo == null ? "" : uInfo.get("ID"));
            logParaMap.put("USER_NAME", uInfo == null ? "" : uInfo.get("NAME"));
            logParaMap.put("TYPE", "接口异常");
            jdbcDao.add("SYS_LOG", logParaMap);
        } catch (Exception ex) {
            logger.error("日志新增失败：" + ExceptionUtil.getStackTraceAsString(ex));
        }
    }

    public Object doProcess(Map param){
        Map map = new HashMap();
        map.put("db", jdbcDao);
        map.put("prm", param);
        String url = (String)param.get("url");
        Object rtmap =  scriptCmp.excute(getRuleByUrl(url), map);
        return rtmap;
    }
    private String getRuleByUrl(String url){
        return "pub.cc." + url;
    }
}

