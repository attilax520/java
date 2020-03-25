package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.exception.BusinessException;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数验证
 * <p>1、读取规则中心校验json模板文件</p>
 * <p>2、与模板文件匹配，验证请求参数合法性</p>
 *
 * @author wangjunma
 * @version $Id: ValidatePrm,v 0.1 2017/12/22 17:34 Exp $$
 */
public class ValidatePrm {

    public String getPath() {
        return SpringUtil.getProp("spring.application.rule_path") + "/config/validate";
    }

    private Map jsonMap = null;
    private static ValidatePrm instance = null;

    private static final int methodLength = 2;

    public static ValidatePrm getInstance() {
        if (instance == null) {
            synchronized (ValidatePrm.class) {
                if (instance == null) {
                    instance = new ValidatePrm();
                }
            }
        }
        return instance;
    }

    private Map getJsonContent() {
        Map rtn = new HashMap(32);
        if (SpringUtil.isDebug() || true) {
            FileUtil.ls(getPath(), ".json").forEach(file -> {
                String jsonContent = FileUtil.readTxt(file);
                rtn.putAll(JSONParser.json2Map(jsonContent));
            });
        }
        return rtn;
    }

    private Map parse() {
        if (jsonMap == null || SpringUtil.isDebug()) {
            jsonMap = getJsonContent();
        }
        return jsonMap;
    }

    /**
     * 请求参数校验
     *
     * @param paramMap 请求参数
     * @param method   请求方法(服务名称.方法名称) exp: parking.arrive
     * @return
     */
    public boolean verifyParam(Map<String, Object> paramMap, String method) {
        String[] methodArr = method.split("\\.");
        if (null == methodArr || methodArr.length != methodLength) {
            throw new BusinessException("请检查method参数，必须是:服务名称.方法名称");
        }
        /**
         * 获取模板参数
         */
        List<Map> listArray = (List) parse().get(methodArr[0]);
        Map<String, Boolean> templateMap = null;
        String methodKey = methodArr[1];
        if (CollectionUtils.isNotEmpty(listArray)) {
            for (Map result : listArray) {
                if (result.containsKey(methodKey)) {
                    templateMap = (Map<String, Boolean>) result.get(methodKey);
                    break;
                }
            }
        }
        if (null == templateMap) {
            return true;
            // throw new BusinessException("请检查"+method+"在规则json中是否定义");
        }
        /**
         * 只检查必填参数
         */
        for (Map.Entry<String, Boolean> entry1 : templateMap.entrySet()) {
            String column = entry1.getKey();
            Object object = paramMap.get(column);
            boolean isMust = templateMap.get(column) && (null == object || StringUtils.isBlank(object.toString()));
            if (isMust) {
                throw new BusinessException("参数" + column + "为必填字段");
            }
        }
        return true;
    }


    /**
     * 获取前端验证参数
     *
     * @param tableName 表名
     * @return 需要验证的参数
     */
    public Map getAdminVerifyParam(String tableName) {
        Map result = new HashMap(2);
        Map commonParam = (Map) parse().get("adminCommon");
        Map tableParam = (Map) ((Map) parse().get("adminTable")).get(tableName);
        if (tableParam != null) {
            result.putAll(tableParam);
        }
        if (commonParam != null) {
            result.forEach((key, value) -> {
                result.putIfAbsent(key, value);
            });

        }

        return result;
    }
}
