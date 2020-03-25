package org.chwin.firefighting.apiserver.core.exception;

import org.chwin.firefighting.apiserver.core.util.LogUtil;
import org.chwin.firefighting.apiserver.core.dto.RspBody;
import org.chwin.firefighting.apiserver.core.util.ExceptionUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author liming
 * @date 2017/9/15
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    LogUtil logger = new LogUtil(this.getClass());
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error(ExceptionUtil.getStackTraceAsString(e));
        RspBody body=new RspBody(RspBody.ERROR,e.getMessage());
        return body.output();
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public String busiErrorHandler(HttpServletRequest req, BusinessException e) throws Exception {
        RspBody body=new RspBody(e.getCode(),e.getMessage());
        return body.output();
    }
}
