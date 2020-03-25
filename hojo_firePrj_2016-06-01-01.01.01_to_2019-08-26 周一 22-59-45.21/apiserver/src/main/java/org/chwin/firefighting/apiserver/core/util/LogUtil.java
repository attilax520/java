package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 *  日志包装类
 * <p>在每个线程日志前增加会话ID--{@code CURR_SID（UUID）}；
 * {@code CURR_SID}在{@code ParamsIntercpetor}中添加；
 * 使用时只需:
 * <blockquote><pre>
 *     LogUtil logger = new LogUtil(this.getClass());
 *     logger.info("....");
 *     logger.error("....");
 *     logger.debug("....");
 *  </pre></blockquote></p>
 *  即可。
 *
 * @Author GJW
 * @since 2017/10/13.
 */
public class LogUtil {

    Logger logger;

    String currentSessionId;
    public LogUtil(Class<?> tClass) {

        logger = LoggerFactory.getLogger(tClass);
    }

    public void info(String msg){
        currentSessionId = getCurrentSessionId();
        logger.info(appendMsg(currentSessionId,msg));
    }

    public void debug(String msg){
        currentSessionId = getCurrentSessionId();
        logger.debug(appendMsg(currentSessionId,msg));
    }

    public void error(String msg){
        currentSessionId = getCurrentSessionId();
        logger.error(appendMsg(currentSessionId,msg));
    }

    private String getCurrentSessionId(){
        currentSessionId = (String)ThreadVariable.getInstance().get(CONSTANTS.CURREN_SID);
        if(StringUtils.isEmpty(currentSessionId)){
            currentSessionId = UUID.randomUUID().toString();
            ThreadVariable.getInstance().put(CONSTANTS.CURREN_SID, currentSessionId);
        }
        return currentSessionId;
    }

    private String appendMsg(String currSID,String msg){
        StringBuffer buffer = new StringBuffer();
        buffer.append("【");
        buffer.append(currSID);
        buffer.append("】");
        buffer.append(msg);
        return buffer.toString();
    }
}
