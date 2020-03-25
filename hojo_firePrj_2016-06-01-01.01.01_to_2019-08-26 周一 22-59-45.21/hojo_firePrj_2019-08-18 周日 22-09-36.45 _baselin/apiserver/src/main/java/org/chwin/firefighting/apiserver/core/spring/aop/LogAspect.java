package org.chwin.firefighting.apiserver.core.spring.aop;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.CacheService;
import org.chwin.firefighting.apiserver.core.service.LogService;
import org.chwin.firefighting.apiserver.core.util.BeanUtil;
import org.chwin.firefighting.apiserver.core.util.ExceptionUtil;
import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志切面，主要针对用户后台操作，POS机操作及APP操作进行的日志记录
 * <p>1、在JdbcAdapter做切面，增加日志代码逻辑植入</p>
 * <p>2、业务代码出异常，日志需记录异常信息</p>
 * <p>3、日志代码逻辑出异常不影响业务代码</p>
 *
 * @author wangjunma
 * @version $Id: LogAspect,v 0.1 2017/10/19 10:07 Exp $$
 */
@Aspect
@Configuration
public class LogAspect {
    /**
     * 日志记录
     */
    private LogUtil logger = new LogUtil(this.getClass());
    /**
     * 日志记录Service
     */
    @Autowired
    private LogService logService;
    /**
     *日志配置记录读取缓存Service
     */
    @Autowired
    private CacheService cacheService;
    @Autowired
    private JdbcDao jdbcDao;

    /**
     * 新增业务逻辑方法切入点
     */
    @Pointcut("execution(* org.chwin.firefighting.apiserver.core.dao.*.add*(..))")
    public void addServiceCall() {
    }

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* org.chwin.firefighting.apiserver.core.dao.*.update*(..))")
    public void updateServiceCall() {
    }

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* org.chwin.firefighting.apiserver.core.dao.*.del*(..)) || execution(* org.chwin.firefighting.apiserver.core.dao.*.remove*(..))")
    public void delServiceCall() {
    }

    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint 连接点
     * @param returnMap 返回内容
     */
    @AfterReturning(value = "addServiceCall()", returning = "returnMap")
    public void afterReturningAddAdvice(JoinPoint joinPoint, Map<String, Object> returnMap) {
        try {
            //判断参数
            Object[] param = joinPoint.getArgs();
            if (null == param) {
                return;
            }
            Map<String, String> paramMap = getParam(param[0].toString(), "ADD", "");
            Map<String, Object> resultMap = cacheService.findLogCfg(paramMap);
            //创建日志对象
            if (MapUtils.isNotEmpty(resultMap)) {
                logService.addLog(resultMap, returnMap);
            }
        } catch (Exception ex) {
            logger.error("add after Aspect error：" + ExceptionUtil.getStackTraceAsString(ex));
        }
    }

    /**
     * 修改操作日志(环绕通知)
     *
     * @param point 环绕通知连接点
     */
    /*@Around(value = "updateServiceCall()")
    public Object aroundUpdateAdvice(ProceedingJoinPoint point) {
        Object thing = null;
        try {
            //判断参数
            Object[] param = point.getArgs();
            if (null == param) {
                return null;
            }
            Map<String, Object> argMap = (Map<String, Object>) param[1];
            String pk = (String) argMap.get(CONSTANTS.C_PK);
            if (StringUtils.isBlank(pk)) {
                return null;
            }
            String tableName = (String) param[0];
            Map<String, Object> resultMap = jdbcDao.loadRT(tableName, pk);
            thing = point.proceed();
            //修改类型比较
            if(null != argMap && null != resultMap) {
                Map<String, Object> compareMap = BeanUtil.compareMap(argMap, resultMap);
                String id = (String) resultMap.get(CONSTANTS.C_PK);
                logCfgMatching(id, tableName, compareMap);
            }
        } catch (Throwable throwable) {
            logger.error("update around aspect error：：" + ExceptionUtil.getStackTraceAsString(throwable));
        }
        return thing;
    }*/

    /**
     * 修改操作日志
     */
    @AfterReturning(value = "updateServiceCall()", returning = "returnMap")
    public void aroundUpdateAdviceNew(JoinPoint joinPoint, Map<String, Object> returnMap) {
        try {
            //判断参数
            Object[] param = joinPoint.getArgs();
            if (null == param) {
                return;
            }
            Map<String, String> paramMap = getParam(param[0].toString(), "UPDATE", "");
            Map<String, Object> resultMap = cacheService.findLogCfg(paramMap);
            //创建日志对象
            if (MapUtils.isNotEmpty(resultMap)) {
                logService.addLog(resultMap, returnMap);
            }
        } catch (Exception ex) {
            logger.error("add after Aspect error：" + ExceptionUtil.getStackTraceAsString(ex));
        }
    }

    /**
     * 删除操作日志(环绕通知)
     *
     * @param point 环绕通知连接点
     */
    @Around(value = "delServiceCall()")
    public Object aroundDeleteAdvice(ProceedingJoinPoint point) {
        Object thing = null;
        try {
            //判断参数
            Object[] param = point.getArgs();
            if (null == param) {
                return null;
            }
            String tableName = (String) param[0];
            Map<String, String> paramMap = getParam(tableName, "DELETE", "");
            Map<String, Object> resultMap = cacheService.findLogCfg(paramMap);
            //创建日志对象
            if (MapUtils.isNotEmpty(resultMap)) {
                Map<String, Object> argMap = (Map<String, Object>) param[1];
                String pk = (String) argMap.get(CONSTANTS.C_PK);
                Map<String, Object> recordMap = jdbcDao.loadRT(tableName, pk);
                if (MapUtils.isNotEmpty(recordMap)) {
                    thing = point.proceed();
                    logService.addLog(resultMap, recordMap);
                }
            } else {
                thing = point.proceed();
            }
        } catch (Throwable throwable) {
            logger.error("delete around aspect error：" + ExceptionUtil.getStackTraceAsString(throwable));
        }
        return thing;
    }

    /**
     * 日志规则匹配，并新增日志
     *
     * @param id         记录ID
     * @param tableName  表名称
     * @param compareMap 修改内容map
     */
    private void logCfgMatching(String id, String tableName, Map<String, Object> compareMap) {
        Map<String, Object> contentMap = new HashMap<>(4);
        Map<String, Object> oldMap = (Map<String, Object>) compareMap.get("oldMap");
        Map<String, Object> newMap = (Map<String, Object>) compareMap.get("newMap");
        for (Map.Entry<String, Object> entry : oldMap.entrySet()) {
            String columnName = entry.getKey();
            Map<String, String> paramMap = getParam(tableName, "UPDATE", columnName);
            Map<String, Object> resultMap = cacheService.findLogCfg(paramMap);
            //创建日志对象
            if (MapUtils.isNotEmpty(resultMap)) {
                Map<String, Object> oldColumnMap = new HashMap(4);
                Map<String, Object> newColumnMap = new HashMap(4);
                oldColumnMap.put(columnName, entry.getValue());
                newColumnMap.put(columnName, newMap.get(columnName));
                contentMap.put("id", id);
                contentMap.put("oldMap", oldColumnMap);
                contentMap.put("newMap", newColumnMap);
                logService.addLog(resultMap, contentMap);
            }
        }
    }

    /**
     * 请求参数
     *
     * @param tableName  表名称
     * @param action     日志类型
     * @param columnName 列名称
     * @return 请求参数
     */
    private Map<String, String> getParam(String tableName, String action, String columnName) {
        Map<String, String> param = new HashMap(8);
        param.put("DELETED", "0");
        param.put("STATUS", "0");
        param.put("TABLE_NAME", tableName);
        param.put("ACTION", action);
        if (!StringUtils.isBlank(columnName)) {
            //param.put("COLUMN_NAME", columnName);
        }
        return param;
    }

}
